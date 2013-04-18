package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.domain.IdCard;
import org.openregistry.core.domain.Person;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.identitycard.IdCardManagementService;
import org.openregistry.core.web.resources.representations.ErrorsResponseRepresentation;
import org.openregistry.core.web.resources.representations.IdCardRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * ID CARD Resource In Openregistry
 *  This class contains all put,post and delete methods for Id cards
 *   for generating new id card, assigning id card, expiring id card (expiring)
 */
@Named
@Singleton
@Path("/people/{personId}/idcard")
public class IdCardResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private  PersonService personService;

    @Inject
    IdCardManagementService idCardService;

    private String idBasedOn="RCPID";
    /**
     * this method  generates a new Id card and return it in xml format
     */
    @POST
    @Produces({MediaType.APPLICATION_XML})
    @Consumes({MediaType.TEXT_XML})
    public IdCardRepresentation generateIdCard(@PathParam("personId") String personId){
        final Person person = findPersonOrThrowNotFoundException(personId);

        //todo need to check service execution result and return appropriate response code
        //return 200 a good day scenario
        return buildIdCardRepresentation(person, idCardService.generateNewIdCard(person).getTargetObject());
    }

    /**
     * this method updates the existing card with the proximith number
     * It id idempotent ..so it can be safely invoked as many times as needed
     * @param personId personID
     * @param proximityNumber  proximityNumber to be assigned
     * @return
     */
     @PUT
     @Produces({MediaType.APPLICATION_XML})
     @Consumes({MediaType.TEXT_XML})
     @Path("{proximityNumber}")
    public IdCardRepresentation assignProximityNumber(@PathParam("personId") String personId,@PathParam("proximityNumber") String proximityNumber){
         final Person person = findPersonOrThrowNotFoundException(personId);
         if( person.getIdCards().size()==0){
             //HTTP 404
             logger.info("ID card Not not found.");
             throw new NotFoundException(
                     String.format("The Id card for person identified by /people/%s URI does not exist",
                              personId));
         }
         if(proximityNumber==null|| proximityNumber.equals("")){
                  //HTTP 400
             throw new WebApplicationException(Response.Status.BAD_REQUEST);
         }
           return this.buildIdCardRepresentation(person,   idCardService.assignProximityNumber(person,proximityNumber).getTargetObject());

    }
    /**
     * this method  deletes (expire a card)
     */
    @DELETE
    @Produces({MediaType.TEXT_XML})
    @Consumes({MediaType.TEXT_XML})
    public void deleteIdCard(@PathParam("personId") String personId){
        final Person person = findPersonOrThrowNotFoundException(personId);

        if( person.getIdCards().size()==0){
            //HTTP 404
            logger.info("ID card Not not found.");
            throw new NotFoundException(
                    String.format("The Id card for person identified by /people/%s URI does not exist",
                            personId));
        }
        //return 200 a good day scenario
        idCardService.expireIdCard(person);
    }

    /**
     * a method to get xml representation of primary id card attached to a person
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    @Consumes({MediaType.TEXT_XML})
    public IdCardRepresentation getIdCard(@PathParam("personId") String personId){
        final Person person = findPersonOrThrowNotFoundException(personId);
        if( person.getIdCards().size()==0){
            //HTTP 404
            logger.info("ID card Not not found.");
            throw new NotFoundException(
                    String.format("The Id card for person identified by /people/%s URI does not exist",
                            personId));
        }
         return buildIdCardRepresentation(person,person.getIdCards().iterator().next());

    }






    private Person findPersonOrThrowNotFoundException( final String personId) {
        logger.info(String.format("Searching for a person with  {personIdType:%s, personId:%s} ...", idBasedOn, personId));
        final Person person = this.personService.findPersonByIdentifier(idBasedOn, personId);
        if (person == null) {
            //HTTP 404
            logger.info("Person is not found.");
            throw new NotFoundException(
                    String.format("The person identified by /people/%s URI does not exist",
                            personId));
        }
        return person;
    }

    private IdCardRepresentation buildIdCardRepresentation(Person p ,IdCard card){
        String rcpid=p.getPrimaryIdentifiersByType().get(idBasedOn).getValue();

        return new IdCardRepresentation(rcpid,card.getCardNumber(),card.getProximityNumber(),card.getBarCode(),card.getCardSecurityValue());

    }

}
