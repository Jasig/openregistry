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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

    @POST    
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingRole(@PathParam("sorSourceId") final String sorSourceId,
                                        @PathParam("sorPersonId") final String sorPersonId,
                                        RoleRepresentation roleRepresentation) {

        final SorPerson sorPerson = this.personService.findBySorIdentifierAndSource(sorSourceId, sorPersonId);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException(
                    String.format("The person resource identified by [/sor/%s/people/%s] URI does not exist.",
                            sorSourceId, sorPersonId));
        }

        final SorRole sorRole = buildSorRoleFrom(sorPerson, roleRepresentation);
        final ServiceExecutionResult result = this.personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        if (result.getValidationErrors().size() > 0) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(ValidationUtils.buildValidationErrorsResponse(result.getValidationErrors())).build();
        }
        //HTTP 201
        return Response.created(this.uriInfo.getAbsolutePath()).build();
    }

    //TODO: what happens if the role (identified by RoleInfo) has been added already?
    private SorRole buildSorRoleFrom(final SorPerson person, final RoleRepresentation roleRepresentation) {
        RoleInfo roleInfo = null;
        try {
            roleInfo = this.referenceRepository.getRoleInfoByCode(roleRepresentation.roleCode);
        } catch (Exception ex){
            throw new NotFoundException(
                    String.format("The role identified by [%s] does not exist", roleRepresentation.roleCode));
        }

        final SorRole sorRole = person.addRole(roleInfo);
        if (roleRepresentation.roleId != null) sorRole.setSorId(roleRepresentation.roleId);
        sorRole.setSourceSorIdentifier(person.getSourceSor());
        sorRole.setPersonStatus(referenceRepository.findType(Type.DataTypes.STATUS, Type.PersonStatusTypes.ACTIVE));
        sorRole.setStart(roleRepresentation.startDate);
        if (roleRepresentation.endDate != null) sorRole.setEnd(roleRepresentation.endDate);
        if (roleRepresentation.percentage != null) sorRole.setPercentage(new Integer(roleRepresentation.percentage).intValue());
        sorRole.setSponsor();
        setSponsorInfo(sorRole.getSponsor(), referenceRepository.findType(Type.DataTypes.SPONSOR, roleRepresentation.sponsorType), roleRepresentation);

        //Emails
        for (final RoleRepresentation.Email e : roleRepresentation.emails) {
            final EmailAddress email = sorRole.addEmailAddress();
            email.setAddress(e.address);
            email.setAddressType(referenceRepository.findType(Type.DataTypes.EMAIL, e.type));
        }

        //Phones
        for (final RoleRepresentation.Phone ph : roleRepresentation.phones) {
            final Phone phone = sorRole.addPhone();
            phone.setNumber(ph.number);
            phone.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, ph.addressType));
            phone.setPhoneType(referenceRepository.findType(Type.DataTypes.PHONE, ph.type));
            phone.setCountryCode(ph.countryCode);
            phone.setAreaCode(ph.areaCode);
            phone.setExtension(ph.extension);
        }

        //Addresses
        for (final RoleRepresentation.Address a : roleRepresentation.addresses) {
            final Address address = sorRole.addAddress();
            address.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, a.type));
            address.setLine1(a.line1);
            address.setLine2(a.line2);
            address.setLine3(a.line3);
            address.setCity(a.city);
            address.setPostalCode(a.postalCode);
            Country country = referenceRepository.getCountryByCode(a.countryCode);
            address.setCountry(country);
            if (country != null) address.setRegion(referenceRepository.getRegionByCodeAndCountryId(a.regionCode, country.getCode()));
        }
        return sorRole;
    }

    private void setSponsorInfo(SorSponsor sponsor, Type type, RoleRepresentation roleRepresentation){
        sponsor.setType(type);
        if (type.getDescription().equals(Type.SponsorTypes.ORG_UNIT.name())){
            try {
                OrganizationalUnit org = referenceRepository.getOrganizationalUnitByCode(roleRepresentation.sponsorId);
                sponsor.setSponsorId(org.getId());
            } catch (Exception ex){
                throw new NotFoundException(
                    String.format("The department identified by [%s] does not exist", roleRepresentation.sponsorId));
            }

        }
        if (type.getDescription().equals(Type.SponsorTypes.PERSON.name())){
            final String sponsorIdType =
                    roleRepresentation.sponsorIdType != null ? roleRepresentation.sponsorIdType : this.preferredPersonIdentifierType;
            try {
                Person person = this.personService.findPersonByIdentifier(sponsorIdType, roleRepresentation.sponsorId);
                sponsor.setSponsorId(person.getId());
            } catch (Exception ex){
                throw new NotFoundException(
                    String.format("The sponsor identified by [%s] does not exist", roleRepresentation.sponsorId));
            }
        }
        //TODO other sponsor types?
    }
}
