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

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.ObjectFactory;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.*;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.IdentifierChangeService;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.web.resources.representations.LinkRepresentation;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.openregistry.core.web.resources.representations.PersonResponseRepresentation;
import org.openregistry.core.web.resources.representations.RoleRepresentation;
import org.openregistry.core.repository.ReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

import com.sun.jersey.api.NotFoundException;

/**
 * Root RESTful resource representing people in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Component
@Scope("singleton")
@Path("/people")
public final class PeopleResource {

    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    @Autowired
    private PersonService personService;

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    @Qualifier(value = "reconciliationCriteria")
    private ObjectFactory<ReconciliationCriteria> reconciliationCriteriaObjectFactory;

    //JSR-250 injection which is more appropriate here for 'autowiring by name' in the case of multiple types
    //are defined in the app ctx (Strings). The looked up bean name defaults to the property name which
    //needs an injection.
    @Resource
    private String preferredPersonIdentifierType;

    @Autowired
    private IdentifierChangeService identifierChangeService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FORCE_ADD_FLAG = "y";

    /*For testing only */
    private static class NET_ID_TYPE implements IdentifierType {
        public Long getId() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getName() {
            return "NETID";
        }
    }

    ;

    private static class RCP_ID_TYPE implements IdentifierType {
        public Long getId() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getName() {
            return "RCPID";
        }
    }

    ;

    private static class ID implements Identifier {
        public IdentifierType getType() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getValue() {
            return "testing-testing-testing";
        }

        public Boolean isPrimary() {
            return null;
        }

        public Boolean isDeleted() {
            return null;
        }

        public void setType(IdentifierType type) {
        }

        public void setValue(String value) {
        }

        public void setPrimary(Boolean value) {
        }

        public void setDeleted(Boolean value) {
        }

        public ActivationKey addActivationKey() {
            return null;
        }

        public ActivationKey getActivationKey() {
            return null;
        }
    }

    ;

    @PUT
    @Path("camel")
    public void testingCamel() {
        this.identifierChangeService.change(new RCP_ID_TYPE(), new ID(), new NET_ID_TYPE(), new ID());
    }

    @PUT
    @Path("{personIdType}/{personId}/roles/{roleCode}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingRole(@PathParam("personIdType") String personIdType,
                                        @PathParam("personId") String personId,
                                        @PathParam("roleCode") String roleCode,
                                        @QueryParam("sor") String sorSourceId,
                                        RoleRepresentation roleRepresentation) {

        if (sorSourceId == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("The 'sor' query parameter is missing").build());
        }
        SorPerson sorPerson = this.personService.findSorPersonByIdentifierAndSourceIDentifier(personIdType, personId, sorSourceId);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException(
                    String.format("The person resource identified by [%s/%s] URI does not exist for the given [%s] sor id",
                            personIdType, personId, sorSourceId));
        }
        RoleInfo roleInfo = this.referenceRepository.getRoleInfoByCode(roleCode);
        if (roleInfo == null) {
            throw new NotFoundException(
                    String.format("The role identified by [%s] does not exist", roleCode));
        }
        SorRole sorRole = buildSorRoleFrom(sorPerson, roleInfo, roleRepresentation);
        ServiceExecutionResult result = this.personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        if (result.getValidationErrors().size() > 0) {
            throw new WebApplicationException(400);
        }
        //HTTP 201
        return Response.created(this.uriInfo.getAbsolutePath()).build();
    }

    @GET
    @Path("{personIdType}/{personId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //auto content negotiation!
    public PersonResponseRepresentation showPerson(@PathParam("personId") String personId,
                                                   @PathParam("personIdType") String personIdType) {

        logger.info(String.format("Searching for a person with  {personIdType:%s, personId:%s} ...", personIdType, personId));
        Person person = this.personService.findPersonByIdentifier(personIdType, personId);
        if (person == null) {
            //HTTP 404
            logger.info("Person is not found.");
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));
        }
        logger.info("Person is found. Building a suitable representation...");
        //Build activation generator URI
        URI activationGeneratorUri = this.uriInfo.getAbsolutePathBuilder().path("activation").build();

        //Build activation proccess URI - there will need to be an activation token generator service. TBD.
        // TODO there may not be a key.
        URI activationProcessorUri = this.uriInfo.getAbsolutePathBuilder().path("activation").path(person.getCurrentActivationKey().asString())
                .build();
        
        return new PersonResponseRepresentation(
                activationGeneratorUri.toString(),
                activationProcessorUri.toString(),                
                buildPersonIdentifierRepresentations(person.getIdentifiers()));
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingPerson(PersonRequestRepresentation personRequestRepresentation, @QueryParam("force") String forceAdd) {
        Response response = null;
        URI uri = null;
        ReconciliationCriteria reconciliationCriteria = null;
        if (!personRequestRepresentation.checkRequiredData()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).entity("The person entity payload is incomplete.").build();
        }
        reconciliationCriteria = buildReconciliationCriteriaFrom(personRequestRepresentation);
        logger.info("Trying to add incoming person...");

        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        //Now do the branching logic based on the result
        if (result.succeeded()) {
            if (result.getReconciliationResult().noPeopleFound()) {
                uri = buildPersonResourceUri((Person) result.getTargetObject());
                response = Response.created(uri).build();
                logger.info(String.format("Person successfuly created. The person resource URI is %s", uri.toString()));
            }
            else if (result.getReconciliationResult().personAlreadyExists()) {
                uri = buildPersonResourceUri((Person) result.getTargetObject());
                response = Response.temporaryRedirect(uri).build();
                logger.info(String.format("Person already exists. The existing person resource URI is %s.", uri.toString()));
            }
        }
        else {
            if (result.getValidationErrors().size() > 0) {
                logger.info("The incoming person payload did not pass validation. Validation errors: " +
                        result.getValidationErrors());
                return Response.status(Response.Status.BAD_REQUEST).entity("The incoming request is malformed.").build();
            }
            else if (result.getReconciliationResult().multiplePeopleFound()) {
                //Force add scenario
                if (FORCE_ADD_FLAG.equals(forceAdd)) {
                    logger.warn("Multiple people found, but doing a 'force add'");
                    result = this.personService.addPerson(reconciliationCriteria, result.getReconciliationResult());
                    uri = buildPersonResourceUri((Person) result.getTargetObject());
                    response = Response.created(uri).build();
                    logger.info(String.format("Person successfuly created (with 'force add' option). The person resource URI is %s", uri.toString()));
                }
                else {
                    List<PersonMatch> conflictingPeopleFound = result.getReconciliationResult().getMatches();
                    response = Response.status(409).entity(buildLinksToConflictingPeopleFound(conflictingPeopleFound))
                            .type(MediaType.APPLICATION_XHTML_XML).build();
                    logger.info("Multiple people found: " + response.getEntity());
                }
            }
        }
        return response;
    }

    @DELETE
    @Path("{personIdType}/{personId}/roles/{roleCode}")
    public Response deleteRoleForPerson(@PathParam("personIdType") String personIdType,
                                        @PathParam("personId") String personId,
                                        @PathParam("roleCode") String roleCode,
                                        @QueryParam("reason") String terminationReason) {
        logger.info(String.format("Received a request to delete a role for a person with the following params: " +
                "{personIdType:%s, personId:%s, roleCode:%s, reason:%s}", personIdType, personId, roleCode, terminationReason));
        if (terminationReason == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please specify the <reason> for termination.").build();
        }
        logger.info("Searching for a person...");
        Person person = this.personService.findPersonByIdentifier(personIdType, personId);
        if (person == null) {
            logger.info("Person is not found...");
            return Response.status(Response.Status.NOT_FOUND).entity("The specified person is not found in the system").build();
        }
        logger.info("Person is found. Picking out the role for a provided 'roleId'...");
        Role role = person.pickOutRole(roleCode);
        if (role == null) {
            logger.info("The Role with the specified 'roleId' is not found in the collection of Person Roles");
            return Response.status(Response.Status.NOT_FOUND).entity("The specified role is not found for this person").build();
        }
        logger.info("The Role is found");
        if (role.isTerminated()) {
            logger.info("The Role is already terminated.");
            //Results in HTTP 204
            return null;
        }
        try {
            if (!this.personService.deleteSorRole(person, role, terminationReason)) {
                //HTTP 500. Is this OK?
                logger.info("The call to PersonService.deleteSorRole returned <false>. Assuming it's an internal error.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("The operation resulted in an internal error")
                        .build();
            }
        }
        catch (IllegalArgumentException ex) {
            logger.info("The 'terminationReason' did not pass the validation");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        //If we got here, everything went well. HTTP 204
        logger.info("The Role resource has been successfully DELETEd");
        return null;
    }

    @DELETE
    @Path("{personIdType}/{personId}")
    public Response deleteSystemOfRecordPerson(@PathParam("personIdType") String personIdType,
                                               @PathParam("personId") String personId,
                                               @QueryParam("sor") String sorSourceId) {
        if (sorSourceId == null) {
            throw new WebApplicationException(new IllegalArgumentException("'sor' query parameter must be provided"), 400);
        }

        logger.info(String.format("Received a request to delete a system of record person with the following params: " +
                "{sorSourceId:%s, personIdType:%s, personId:%s}", sorSourceId, personIdType, personId));

        logger.info("Searching for SOR Person...");
        SorPerson person = this.personService.findSorPersonByIdentifierAndSourceIDentifier(personIdType,
                personId, sorSourceId);
        if (person == null) {
            logger.info("SOR Person is not found");
            //HTTP 404
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s/sor/%s URI does not exist",
                            personIdType, personId, sorSourceId));
        }
        logger.info("SOR Person is found. Trying do delete...");
        if (!this.personService.deleteSystemOfRecordPerson(person)) {
            logger.info("PersonService.deleteSystemOfRecordPerson call has returned <false>. Assuming an internal error.");
            throw new WebApplicationException(500);
        }
        //HTTP 204
        logger.info("The SOR Person resource has been successfully DELETEd");
        return null;
    }

    //TODO: what happens if the role (identified by RoleInfo) has been added already?
    //NOTE: the sponsor is not set (remains null) as it was not defined in the XML payload as was discussed
    private SorRole buildSorRoleFrom(SorPerson person, RoleInfo roleInfo, RoleRepresentation roleRepresentation) {
        SorRole sorRole = person.addRole(roleInfo);
        sorRole.setSorId("1");  // TODO: what to set here?
        sorRole.setSourceSorIdentifier(person.getSourceSorIdentifier());
        sorRole.setPersonStatus(referenceRepository.findType(Type.DataTypes.STATUS, "active"));
        sorRole.setStart(roleRepresentation.startDate);
        sorRole.setEnd(roleRepresentation.endDate);

        //Emails
        for (RoleRepresentation.Email e : roleRepresentation.emails) {
            EmailAddress email = sorRole.addEmailAddress();
            email.setAddress(e.address);
            email.setAddressType(referenceRepository.findType(Type.DataTypes.EMAIL, e.type));
        }

        //Phones
        for (RoleRepresentation.Phone ph : roleRepresentation.phones) {
            Phone phone = sorRole.addPhone();
            phone.setNumber(ph.number);
            phone.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, ph.addressType));
            phone.setPhoneType(referenceRepository.findType(Type.DataTypes.PHONE, ph.type));
            phone.setCountryCode(ph.countryCode);
            phone.setAreaCode(ph.areaCode);
            phone.setExtension(ph.extension);
        }

        //Addresses
        for (RoleRepresentation.Address a : roleRepresentation.addresses) {
            Address address = sorRole.addAddress();
            address.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, a.type));
            address.setLine1(a.line1);
            address.setLine2(a.line2);
            address.setLine3(a.line3);
            address.setCity(a.city);
            address.setPostalCode(a.postalCode);
            //TODO: how to set Region and Country instances??? Currently there is no way!
        }
        return sorRole;
    }

    private ReconciliationCriteria buildReconciliationCriteriaFrom(PersonRequestRepresentation request) {
        ReconciliationCriteria ps = this.reconciliationCriteriaObjectFactory.getObject();
        ps.getPerson().setSourceSorIdentifier(request.systemOfRecordId);
        ps.getPerson().setSorId(request.systemOfRecordPersonId);
        Name name = ps.getPerson().addName();
        name.setGiven(request.firstName);
        name.setFamily(request.lastName);
        ps.setEmailAddress(request.email);
        ps.setPhoneNumber(request.phoneNumber);
        ps.getPerson().setDateOfBirth(request.dateOfBirth);
        ps.getPerson().setSsn(request.ssn);
        ps.getPerson().setGender(request.gender);
        ps.setAddressLine1(request.addressLine1);
        ps.setAddressLine2(request.addressLine2);
        ps.setCity(request.city);
        ps.setRegion(request.region);
        ps.setPostalCode(request.postalCode);
        return ps;
    }

    private URI buildPersonResourceUri(Person person) {
        for (Identifier id : person.getIdentifiers()) {
            if (this.preferredPersonIdentifierType.equals(id.getType().getName())) {
                return this.uriInfo.getAbsolutePathBuilder().path(this.preferredPersonIdentifierType)
                        .path(id.getValue()).build();
            }
        }
        //Person MUST have at least one id of the preferred configured type. Results in HTTP 500
        throw new IllegalStateException("The person must have at least one id of the preferred configured type " +
                "which is <" + this.preferredPersonIdentifierType + ">");
    }

    private LinkRepresentation buildLinksToConflictingPeopleFound(List<PersonMatch> matches) {
        //A little defensive stuff. Will result in HTTP 500
        if (matches.size() == 0) {
            throw new IllegalStateException("Person matches cannot be empty if reconciliation result is <MAYBE>");
        }
        List<LinkRepresentation.Link> links = new ArrayList<LinkRepresentation.Link>();
        for (PersonMatch match : matches) {
            links.add(new LinkRepresentation.Link("person", buildPersonResourceUri(match.getPerson()).toString()));
        }
        return new LinkRepresentation(links);
    }

    private List<PersonResponseRepresentation.PersonIdentifierRepresentation>
    buildPersonIdentifierRepresentations(Set<Identifier> identifiers) {

        List<PersonResponseRepresentation.PersonIdentifierRepresentation> idsRep =
                new ArrayList<PersonResponseRepresentation.PersonIdentifierRepresentation>();

        for (Identifier id : identifiers) {
            idsRep.add(new PersonResponseRepresentation.PersonIdentifierRepresentation(id.getType().getName(), id.getValue()));
        }
        if (idsRep.isEmpty()) {
            throw new IllegalStateException("Person identifiers cannot be empty");
        }
        return idsRep;
    }
}
