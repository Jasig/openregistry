/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.web.resources;

import com.sun.jersey.api.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.*;
import org.openregistry.core.utils.*;
import org.openregistry.core.web.resources.representations.*;
import org.slf4j.*;

import javax.annotation.*;
import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * Root RESTful resource representing <i>System of Record</i> view of Roles in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @author Nancy Mond
 * @since 1.0
 */
@Named
@Singleton
@Path("/sor/{sorSourceId}/people/{sorPersonId}/roles")
public class SystemOfRecordRolesResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    private final PersonService personService;

    private final ReferenceRepository referenceRepository;

    //JSR-250 injection which is more appropriate here for 'autowiring by name' in the case of multiple types
    //are defined in the app ctx (Strings). The looked up bean name defaults to the property name which
    //needs an injection.
    @Resource
    private String preferredPersonIdentifierType;

    @Inject
    public SystemOfRecordRolesResource(final PersonService personService, final ReferenceRepository referenceRepository) {
        this.personService = personService;
        this.referenceRepository = referenceRepository;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingRole(@PathParam("sorSourceId") final String sorSourceId,
                                        @PathParam("sorPersonId") final String sorPersonId,
                                        final RoleRepresentation roleRepresentation) {

        final SorPerson sorPerson = (SorPerson) findPersonAndRoleOrThrowNotFoundException(sorSourceId, sorPersonId, null).get("person");
        final SorRole sorRole = buildSorRoleFrom(sorPerson, roleRepresentation);
        final ServiceExecutionResult<SorRole> result = this.personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        if (!result.getValidationErrors().isEmpty()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(new ErrorsResponseRepresentation(ValidationUtils.buildValidationErrorsResponseAsList(result.getValidationErrors())))
                    .type(MediaType.APPLICATION_XML).build();
        }

        //HTTP 201
        return Response.created(this.uriInfo.getAbsolutePathBuilder()
                .path(result.getTargetObject().getSorId())
                .build())
                .build();
    }

    @PUT
    @Path("{sorRoleId}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateIncomingRole(@PathParam("sorSourceId") final String sorSourceId,
                                       @PathParam("sorPersonId") final String sorPersonId,
                                       @PathParam("sorRoleId") final String sorRoleId,
                                       final RoleRepresentation roleRepresentation) {

        final Map<String,Object> pair = findPersonAndRoleOrThrowNotFoundException(sorSourceId, sorPersonId, sorRoleId);

        final SorRole sorRole = (SorRole) pair.get("role");
        final SorPerson sorPerson = (SorPerson) pair.get("person");

        updateSorRoleWithIncomingData(sorRole, roleRepresentation);
        ServiceExecutionResult<SorRole> result = this.personService.updateSorRole(sorPerson, sorRole);
        if (!result.getValidationErrors().isEmpty()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(new ErrorsResponseRepresentation(ValidationUtils.buildValidationErrorsResponseAsList(result.getValidationErrors())))
                    .type(MediaType.APPLICATION_XML).build();
        }
        //HTTP 204
        return null;
    }

    @DELETE
    @Path("{sorRoleId}")
    public Response deleteRole(@PathParam("sorSourceId") final String sorSourceId,
                               @PathParam("sorPersonId") final String sorPersonId,
                               @PathParam("sorRoleId") final String sorRoleId,
                               @QueryParam("mistake") @DefaultValue("false") final boolean mistake,
                               @QueryParam("terminationType") @DefaultValue("UNSPECIFIED") final String terminationType) {

        final Map<String, Object> personAndRole = findPersonAndRoleOrThrowNotFoundException(sorSourceId, sorPersonId, sorRoleId);
        final SorPerson sorPerson = (SorPerson) personAndRole.get("person");
        final SorRole sorRole = (SorRole) personAndRole.get("role");
        try {
            //TODO: need to encapsulate the lookups behind the service API
            if (!this.personService.deleteSystemOfRecordRole(sorPerson, sorRole, mistake, terminationType)) {
                throw new WebApplicationException(
                        new RuntimeException(String.format("Unable to Delete SorRole for SoR [ %s ] with ID [ %s ] and Role ID [ %s ]", sorSourceId, sorPersonId, sorRoleId)), 500);
            }
            //HTTP 204
            logger.debug("The SOR Person role has been successfully DELETEd");
            return null;
        }
        catch (final PersonNotFoundException e) {
            throw new NotFoundException(String.format("The system of record role resource identified by /sor/%s/people/%s/%s URI does not exist",
                    sorSourceId, sorPersonId, sorRoleId));
        }
    }

    //Java needs Tuples. Scala, where are you?!!!!!
    private Map<String, Object> findPersonAndRoleOrThrowNotFoundException(final String sorSourceId, final String sorPersonId, final String sorRoleId) {
        //Poor simulation of Tuple 'pair'. Oh, well
        final Map<String, Object> ret = new HashMap<String, Object>(2);
        final SorPerson sorPerson = this.personService.findBySorIdentifierAndSource(sorSourceId, sorPersonId);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException(
                    String.format("The person resource identified by [/sor/%s/people/%s] URI does not exist.",
                            sorSourceId, sorPersonId));
        }
        if (sorRoleId != null) {
            final SorRole sorRole = sorPerson.findSorRoleBySorRoleId(sorRoleId);
            if (sorRole == null) {
                throw new NotFoundException(
                        String.format("The role resource identified by [/sor/%s/people/%s/roles/%s] URI does not exist.",
                                sorSourceId, sorPersonId, sorRoleId));
            }
            ret.put("role", sorRole);
        }
        ret.put("person", sorPerson);
        return ret;
    }

    private void updateSorRoleWithIncomingData(final SorRole sorRole, final RoleRepresentation roleRepresentation) {
        validRoleInfoForCodeOrThrowBadDataException(roleRepresentation.roleCode);
        //TODO discuss with Scott how to 'update' role code???
        //sorRole.setCode(roleRepresentation.roleCode);
        copyBasicRoleDataFromIncomingRepresentation(sorRole, roleRepresentation);

        //Update newEmails
        sorRole.getEmailAddresses().clear();
        if (roleRepresentation.emails != null) {
            copyEmailDataFromIncomingRepresentation(sorRole, roleRepresentation.emails);
        }

        //Update phones
        sorRole.getPhones().clear();
        if (roleRepresentation.phones != null) {
            copyPhoneDataFromIncomingRepresentation(sorRole, roleRepresentation.phones);
        }

        //Update addresses
        sorRole.getAddresses().clear();
        if (roleRepresentation.addresses != null) {
            copyAddressDataFromIncomingRepresentation(sorRole, roleRepresentation.addresses);
        }

    }

    private SorRole buildSorRoleFrom(final SorPerson person, final RoleRepresentation roleRepresentation) {
        RoleInfo roleInfo = validRoleInfoForCodeOrThrowBadDataException(roleRepresentation.roleCode);
        final SorRole sorRole = person.addRole(roleInfo);
        if (roleRepresentation.roleId != null) sorRole.setSorId(roleRepresentation.roleId);
        sorRole.setSourceSorIdentifier(person.getSourceSor());

        copyBasicRoleDataFromIncomingRepresentation(sorRole, roleRepresentation);
        copyEmailDataFromIncomingRepresentation(sorRole, roleRepresentation.emails);
        copyPhoneDataFromIncomingRepresentation(sorRole, roleRepresentation.phones);
        copyAddressDataFromIncomingRepresentation(sorRole, roleRepresentation.addresses);
        return sorRole;
    }

    private RoleInfo validRoleInfoForCodeOrThrowBadDataException(String roleCode) {
        RoleInfo roleInfo = this.referenceRepository.getRoleInfoByCode(roleCode);
        if (roleInfo == null) {
            throw new WebApplicationException(
                    new RuntimeException(String.format("The role identified by role code [%s] does not exist", roleCode)), 400);
        }
        return roleInfo;
    }

    private void copyBasicRoleDataFromIncomingRepresentation(SorRole sorRole, RoleRepresentation roleRepresentation) {
        //TODO: this is questionable - should we hardcode the status here or should it solely depend on the date range?
        sorRole.setPersonStatus(this.referenceRepository.findType(Type.DataTypes.STATUS, Type.PersonStatusTypes.ACTIVE));
        sorRole.setStart(roleRepresentation.startDate);
        if (roleRepresentation.endDate != null) sorRole.setEnd(roleRepresentation.endDate);
        if (roleRepresentation.percentage != null) sorRole.setPercentage(Integer.parseInt(roleRepresentation.percentage));
        setSponsorInfo(sorRole.getSponsor(),
                this.referenceRepository.findType(Type.DataTypes.SPONSOR, roleRepresentation.sponsorType), roleRepresentation);

    }

    private void copyEmailDataFromIncomingRepresentation(SorRole sorRole, List<RoleRepresentation.Email> emailsRepresentation) {
        for (final RoleRepresentation.Email e : emailsRepresentation) {
            final EmailAddress email = sorRole.addEmailAddress();
            email.setAddress(e.address);
            email.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, e.type));
        }

    }

    private void copyPhoneDataFromIncomingRepresentation(SorRole sorRole, List<RoleRepresentation.Phone> phonesRepresentation) {
        for (final RoleRepresentation.Phone ph : phonesRepresentation) {
            final Phone phone = sorRole.addPhone();
            phone.setNumber(ph.number);
            phone.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, ph.addressType));
            phone.setPhoneType(referenceRepository.findType(Type.DataTypes.PHONE, ph.type));
            phone.setCountryCode(ph.countryCode);
            phone.setAreaCode(ph.areaCode);
            phone.setExtension(ph.extension);
        }
    }

    private void copyAddressDataFromIncomingRepresentation(SorRole sorRole, List<RoleRepresentation.Address> addressesRepresentation) {
        for (final RoleRepresentation.Address a : addressesRepresentation) {
            final Address address = sorRole.addAddress();
            address.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, a.type));
            address.setLine1(a.line1);
            address.setLine2(a.line2);
            address.setLine3(a.line3);
            address.setCity(a.city);
            address.setPostalCode(a.postalCode);
            Country country = referenceRepository.getCountryByCode(a.countryCode);
            address.setCountry(country);
            if (country != null) {
                address.setRegion(referenceRepository.getRegionByCodeAndCountryId(a.regionCode, country.getCode()));
            }
        }

    }

    //TODO: NEED TO REVIEW THE IMPLEMENTATION OF THIS METHOD
    private void setSponsorInfo(SorSponsor sponsor, Type type, RoleRepresentation roleRepresentation) {
        sponsor.setType(type);
        if (type.getDescription().equals(Type.SponsorTypes.ORG_UNIT.name())) {
            try {
                OrganizationalUnit org = referenceRepository.getOrganizationalUnitByCode(roleRepresentation.sponsorId);
                sponsor.setSponsorId(org.getId());
            }
            catch (Exception ex) {
                throw new NotFoundException(
                        String.format("The department identified by [%s] does not exist", roleRepresentation.sponsorId));
            }

        }
        if (type.getDescription().equals(Type.SponsorTypes.PERSON.name())) {
            final String sponsorIdType =
                    roleRepresentation.sponsorIdType != null ? roleRepresentation.sponsorIdType : this.preferredPersonIdentifierType;
            try {
                Person person = this.personService.findPersonByIdentifier(sponsorIdType, roleRepresentation.sponsorId);
                sponsor.setSponsorId(person.getId());
            }
            catch (Exception ex) {
                throw new NotFoundException(
                        String.format("The sponsor identified by [%s] does not exist", roleRepresentation.sponsorId));
            }
        }
        //TODO other sponsor types?
    }
}
