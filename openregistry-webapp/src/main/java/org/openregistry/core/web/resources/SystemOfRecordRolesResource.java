package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.utils.ValidationUtils;
import org.openregistry.core.web.resources.representations.RoleRepresentation;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;


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
@Component
@Scope("singleton")
@Path("/sor/{sorSourceId}/people/{sorPersonId}/roles")
public class SystemOfRecordRolesResource {

    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    @Autowired(required = true)
    private PersonService personService;

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    //JSR-250 injection which is more appropriate here for 'autowiring by name' in the case of multiple types
    //are defined in the app ctx (Strings). The looked up bean name defaults to the property name which
    //needs an injection.
    @Resource
    private String preferredPersonIdentifierType;

    @Resource(name = "sorRoleFactory")
    private ObjectFactory<SorRole> sorRoleFactory;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingRole(@PathParam("sorSourceId") final String sorSourceId,
                                        @PathParam("sorPersonId") final String sorPersonId,
                                        final RoleRepresentation roleRepresentation) {

        final SorPerson sorPerson = findPersonOrThrowNotFoundException(sorSourceId, sorPersonId);
        final SorRole sorRole = buildSorRoleFrom(sorPerson, roleRepresentation);
        final ServiceExecutionResult<SorRole> result = this.personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        if (!result.getValidationErrors().isEmpty()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(ValidationUtils.buildValidationErrorsResponse(result.getValidationErrors())).build();
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

        final SorPerson sorPerson = findPersonOrThrowNotFoundException(sorSourceId, sorPersonId);
        final SorRole sorRole = sorPerson.findSorRoleBySorRoleId(sorRoleId);
        if (sorRole == null) {
            throw new NotFoundException(
                    String.format("The role resource identified by [/sor/%s/people/%s/roles/%s] URI does not exist.",
                            sorSourceId, sorPersonId, sorRoleId));
        }        
        updateSorRoleWithIncomingData(sorRole, roleRepresentation);
        ServiceExecutionResult<SorRole> result = this.personService.updateSorRole(sorRole);
        if (!result.getValidationErrors().isEmpty()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(ValidationUtils.buildValidationErrorsResponse(result.getValidationErrors())).build();
        }
        //HTTP 204
        return null;
    }

    private SorPerson findPersonOrThrowNotFoundException(String sorSourceId, String sorPersonId) {
        final SorPerson sorPerson = this.personService.findBySorIdentifierAndSource(sorSourceId, sorPersonId);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException(
                    String.format("The person resource identified by [/sor/%s/people/%s] URI does not exist.",
                            sorSourceId, sorPersonId));
        }
        return sorPerson;
    }

    private void updateSorRoleWithIncomingData(SorRole sorRole, RoleRepresentation roleRepresentation) {
        validRoleInfoForCodeOrThrowBadDataException(roleRepresentation.roleCode);
        //Update roleCode
        sorRole.setCode(roleRepresentation.roleCode);
        copyBasicRoleDataFromIncomingRepresentation(sorRole, roleRepresentation);

        List<EmailAddress> newEmails = new ArrayList<EmailAddress>();
        List<Phone> newPhones = new ArrayList<Phone>();
        List<Address> newAddresses = new ArrayList<Address>();
        SorRole tempSorRole = this.sorRoleFactory.getObject();

        //Update newEmails
        for (final RoleRepresentation.Email e : roleRepresentation.emails) {
            final EmailAddress email = tempSorRole.addEmailAddress();
            copyEmailDataFromIncomingRepresentation(email, e);
            newEmails.add(email);
        }
        //swap the emails - the domain model needs more encapsulation!
        List<EmailAddress> currentEmails = sorRole.getEmailAddresses();
        currentEmails = newEmails;

        //Update phones
        for (final RoleRepresentation.Phone ph : roleRepresentation.phones) {
            final Phone phone = tempSorRole.addPhone();
            copyPhoneDataFromIncomingRepresentation(phone, ph);
            newPhones.add(phone);
        }
        //swap the phones
        List<Phone> currentPhones = sorRole.getPhones();
        currentPhones = newPhones;

        //Update addresses
        for (final RoleRepresentation.Address a : roleRepresentation.addresses) {
            final Address address = tempSorRole.addAddress();
            copyAddressDataFromIncomingRepresentation(address, a);
            newAddresses.add(address);
        }
        //swap the addresses
        List<Address> currentAddresses = sorRole.getAddresses();
        currentAddresses = newAddresses;
    }

    private SorRole buildSorRoleFrom(final SorPerson person, final RoleRepresentation roleRepresentation) {
        RoleInfo roleInfo = validRoleInfoForCodeOrThrowBadDataException(roleRepresentation.roleCode);
        final SorRole sorRole = person.addRole(roleInfo);
        if (roleRepresentation.roleId != null) sorRole.setSorId(roleRepresentation.roleId);
        sorRole.setSourceSorIdentifier(person.getSourceSor());

        copyBasicRoleDataFromIncomingRepresentation(sorRole, roleRepresentation);

        //Emails
        for (final RoleRepresentation.Email e : roleRepresentation.emails) {
            final EmailAddress email = sorRole.addEmailAddress();
            copyEmailDataFromIncomingRepresentation(email, e);
        }

        //Phones
        for (final RoleRepresentation.Phone ph : roleRepresentation.phones) {
            final Phone phone = sorRole.addPhone();
            copyPhoneDataFromIncomingRepresentation(phone, ph);
        }

        //Addresses
        for (final RoleRepresentation.Address a : roleRepresentation.addresses) {
            final Address address = sorRole.addAddress();
            copyAddressDataFromIncomingRepresentation(address, a);
        }
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
        if (roleRepresentation.percentage != null) sorRole.setPercentage(new Integer(roleRepresentation.percentage).intValue());
        sorRole.setSponsor();
        setSponsorInfo(sorRole.getSponsor(),
                this.referenceRepository.findType(Type.DataTypes.SPONSOR, roleRepresentation.sponsorType), roleRepresentation);

    }

    private void copyEmailDataFromIncomingRepresentation(EmailAddress email, RoleRepresentation.Email emailRepresentation) {
        email.setAddress(emailRepresentation.address);
        email.setAddressType(referenceRepository.findType(Type.DataTypes.EMAIL, emailRepresentation.type));
    }

    private void copyPhoneDataFromIncomingRepresentation(Phone phone, RoleRepresentation.Phone phoneRepresentation) {
        phone.setNumber(phoneRepresentation.number);
        phone.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, phoneRepresentation.addressType));
        phone.setPhoneType(referenceRepository.findType(Type.DataTypes.PHONE, phoneRepresentation.type));
        phone.setCountryCode(phoneRepresentation.countryCode);
        phone.setAreaCode(phoneRepresentation.areaCode);
        phone.setExtension(phoneRepresentation.extension);
    }

    private void copyAddressDataFromIncomingRepresentation(Address address, RoleRepresentation.Address addressRepresentation) {
        address.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, addressRepresentation.type));
        address.setLine1(addressRepresentation.line1);
        address.setLine2(addressRepresentation.line2);
        address.setLine3(addressRepresentation.line3);
        address.setCity(addressRepresentation.city);
        address.setPostalCode(addressRepresentation.postalCode);
        Country country = referenceRepository.getCountryByCode(addressRepresentation.countryCode);
        address.setCountry(country);
        if (country != null) {
            address.setRegion(referenceRepository.getRegionByCodeAndCountryId(addressRepresentation.regionCode, country.getCode()));
        }
    }

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
