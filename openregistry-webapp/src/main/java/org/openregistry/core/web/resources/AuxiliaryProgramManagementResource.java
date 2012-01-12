package org.openregistry.core.web.resources;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 12/12/11
 * Time: 12:03 PM
 * RESTful resource used to expose functionality of managing Auxiliary Programs and their Net IDs
 */

import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramException;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramService;
import org.openregistry.core.web.resources.representations.ErrorsResponseRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Named
@Singleton
@Path("/auxiliaryProgram")
public class AuxiliaryProgramManagementResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Inject
    private AuxiliaryProgramService auxiliaryProgramService;

//    public void setAuxiliaryProgramService(AuxiliaryProgramService auxiliaryProgramService) {
//        this.auxiliaryProgramService = auxiliaryProgramService;
//    }

    @Inject
    public AuxiliaryProgramManagementResource(AuxiliaryProgramService auxiliaryProgramService){
       this.auxiliaryProgramService = auxiliaryProgramService;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response assignNetIdToAuxProgramAccount(
                    @QueryParam("rcpid") String rcpid,
                    @QueryParam("netid") String netid){
        String rcpIDWithoutSpaces = "";
        String netIDWithoutSpaces = "";
        String responseText = "";
        //Do all validations
        if(null== rcpid){
           responseText = "Input data is not acceptable: RCPID cannot be null";
           return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }
        if(null == netid){
            responseText = "Input data is not acceptable: NETID cannot be null";
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }
        //now it is clear that rcpid and netid are not null
        rcpIDWithoutSpaces = rcpid.trim();
        netIDWithoutSpaces = netid.trim().toLowerCase();
        if(rcpIDWithoutSpaces.equalsIgnoreCase("")){
            responseText = "Input data is not acceptable: RCPID cannot be empty or empty spaces";
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }
        if(netIDWithoutSpaces.equalsIgnoreCase("")){
            responseText = "Input data is not acceptable: NETID cannot be empty or empty spaces";
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }
        if(auxiliaryProgramService.netIdExistsForPerson(netIDWithoutSpaces)){
            //1- Verify whether the NetID already Exists for a Person
           responseText = "The provided data could not be processed due to internal state conflict: NETID already exists for person";
           return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }else if(auxiliaryProgramService.netIdExistsForProgram(netIDWithoutSpaces)){
            //2- Verify whether the NetID already exists for a Program
            responseText = "The provided data could not be processed due to internal state conflict: NETID already exists for program";
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }else if(auxiliaryProgramService.providedNetIdIsAnIID(netIDWithoutSpaces)){
            //3- Verify whether the NetID already exists as an IID
            responseText = "The provided data could not be processed due to internal state conflict: NETID exists as an IID";
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList(responseText)))
                    .type(MediaType.APPLICATION_XML).build();
        }else{
            //only execute this portion if all the validations have passed
            try{
                this.auxiliaryProgramService.assignNetIdToProgramUsingRcpid(rcpIDWithoutSpaces,netIDWithoutSpaces);
                responseText = "NETID successfully assigned to Auxiliary Program";
            }catch(AuxiliaryProgramException ape){
                responseText = ape.getMessage();
                return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList("The provided data could not be processed due to internal state conflict: "+responseText)))
                    .type(MediaType.APPLICATION_XML).build();
            }catch (Exception e){
                responseText = e.getMessage();
                return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList("The provided data could not be processed due to internal state conflict: "+responseText)))
                    .type(MediaType.APPLICATION_XML).build();
            }
        }
        return Response.ok(responseText).build();
    }
}
