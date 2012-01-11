package org.openregistry.core.service;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.jpa.JpaAuxiliaryIdentifierImpl;
import org.openregistry.core.domain.jpa.JpaIdentifierTypeImpl;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramException;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 11/1/11
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
@Named("auxiliaryProgramService")
@Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultAuxiliaryProgramService implements AuxiliaryProgramService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final AuxiliaryProgramsRepository auxProgramRepository;
    private final AuxiliaryIdentifierRepository auxIdentifierRepository;
    private final PersonRepository personRepository;

    private final ReferenceRepository referenceRepository;

    @Inject
    public DefaultAuxiliaryProgramService(AuxiliaryProgramsRepository auxProgramRepository, AuxiliaryIdentifierRepository auxIdentifierRepository, PersonRepository personRepository, ReferenceRepository referenceRepository) {
        this.auxProgramRepository = auxProgramRepository;
        this.auxIdentifierRepository = auxIdentifierRepository;
        this.personRepository = personRepository;
        this.referenceRepository = referenceRepository;
    }

    @Override
    public AuxiliaryProgram findAuxiliaryProgramById(Long id) throws RepositoryAccessException {
        return this.auxProgramRepository.findByInternalId(id);
    }

    @Override
    public List<AuxiliaryProgram> findByAuxiliaryProgramName(String name) throws RepositoryAccessException {
        return this.auxProgramRepository.findByAuxiliaryProgramName(name);
    }

    @Override
    public List<AuxiliaryProgram> findAllAuxiliaryPrograms() throws RepositoryAccessException {
        return this.auxProgramRepository.findAllAuxiliaryPrograms();
    }

    @Override
    public AuxiliaryProgram saveAuxiliaryProgram(AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        return this.auxProgramRepository.saveAuxiliaryProgram(auxiliaryProgram);
    }

    @Override
    public void deleteAuxiliaryProgram(AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        this.auxProgramRepository.deleteAuxiliaryProgram(auxiliaryProgram);
    }

    @Override
    public void deleteIdentifierOfProgram(AuxiliaryProgram auxiliaryProgram, AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        this.auxProgramRepository.deleteIdentifierOfProgram(auxiliaryProgram,auxiliaryIdentifier);
    }

    @Override
    public void addIdentifierToProgram(AuxiliaryProgram auxiliaryProgram, AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        this.auxProgramRepository.addIdentifierToProgram(auxiliaryProgram,auxiliaryIdentifier);
    }

    @Override
    public AuxiliaryIdentifier findAuxiliaryIdentifierById(Long id) throws RepositoryAccessException {
        return this.auxIdentifierRepository.findByInternalId(id);
    }

    @Override
    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValue(String value) throws RepositoryAccessException {
        return this.auxIdentifierRepository.findByAuxiliaryIdentifierValue(value);
    }

    @Override
    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValueAndType(String value, String type) throws RepositoryAccessException {
        return this.auxIdentifierRepository.findByAuxiliaryIdentifierValue(value);
    }

    @Override
    public AuxiliaryProgram findProgramOfIdentifier(AuxiliaryIdentifier identifier) throws RepositoryAccessException {
        return this.auxIdentifierRepository.findProgramOfIdentifier(identifier);
    }

    @Override
    public AuxiliaryIdentifier saveAuxiliaryIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        return this.auxIdentifierRepository.saveAuxiliaryIdentifier(auxiliaryIdentifier);
    }

    @Override
    public void deleteAuxiliaryIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        this.auxIdentifierRepository.deleteAuxiliaryIdentifier(auxiliaryIdentifier);
    }

    @Override
    public void removeProgramOfIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        this.auxIdentifierRepository.removeProgramOfIdentifier(auxiliaryIdentifier);
    }

    @Override
    public void assignProgramToIdentifier(AuxiliaryIdentifier auxiliaryIdentifier, AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        this.auxIdentifierRepository.assignProgramToIdentifier(auxiliaryIdentifier,auxiliaryProgram);
    }

    @Override
    public boolean netIdExistsForPerson(String netId){
        boolean flag = false;
        try{
            Person person = this.personRepository.findByIdentifier(Type.IdentifierTypes.NETID.toString(),netId);
            if (null!= person){
                flag=true;
            }
        }catch(RepositoryAccessException rae){
            flag = false;

        }catch(Exception ex){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean providedNetIdIsAnIID(String netId){
        String iid = netId.toUpperCase();
        boolean flag = false;
        try{
            Person person = this.personRepository.findByIdentifier(Type.IdentifierTypes.IID.toString(),iid);
            if (null!= person){
                flag=true;
            }
        }catch(RepositoryAccessException rae){
            flag = false;

        }catch(Exception ex){
            flag=false;
        }
        return flag;
    }


    @Override
    public boolean netIdExistsForProgram(String netId){
        boolean flag= false;
        try{
            List<AuxiliaryIdentifier> auxIdentifierLst = auxIdentifierRepository.findByAuxiliaryIdentifierValue(netId);
            if(null!= auxIdentifierLst && auxIdentifierLst.size() > 0){
                flag=true;
            }
        }catch(RepositoryAccessException rae){
            flag = false;

        }catch (Exception ex){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean netIdAlreadyExists(String netId) throws RepositoryAccessException {

        return (netIdExistsForPerson(netId) || netIdExistsForProgram(netId));
    }

    public AuxiliaryProgram findAuxiliaryProgramAssociatedWithRCPID(String rcpID) throws AuxiliaryProgramException{
        AuxiliaryProgram ap = null;
        try{
            List<AuxiliaryIdentifier> listAuxIdentifier = auxIdentifierRepository.findByAuxiliaryIdentifierValue(rcpID);
            for(AuxiliaryIdentifier auxIdent: listAuxIdentifier){
                if(auxIdent.getType().getName().equalsIgnoreCase(Type.IdentifierTypes.RCPID.toString())){
                   ap =  auxIdent.getProgram();
                   break;
                }
            }
//            ap = auxIdentifierRepository.findAuxiliaryProgramByIdentifierValueAndType(rcpID, Type.IdentifierTypes.RCPID.toString());
        }catch(Exception e){
            throw new AuxiliaryProgramException("Could not find a program account associated with RCP ID");
        }
        return ap;
    }

    @Override
    public void assignNetIdToProgramUsingRcpid(String rcpID, String netID) throws AuxiliaryProgramException{
        AuxiliaryProgram ap = null;
        AuxiliaryIdentifier ai =null;

        //Find Auxiliary Program Associated with an RCPID
        ap = findAuxiliaryProgramAssociatedWithRCPID(rcpID);

        if(null!= ap){
            try{
                //create an Identifier Object and put the NET ID
                ai = createNewAuxiliaryIdentifierWithNetId(netID);
                ai.setProgram(ap);
                addIdentifierToProgram(ap, ai);
                this.auxProgramRepository.saveAuxiliaryProgram(ap);
            }catch(RepositoryAccessException rae){
                throw new AuxiliaryProgramException("Error in assigning NETID to the AuxiliaryProgram");
            }catch(Exception e){
                throw new AuxiliaryProgramException("Error in assigning NETID to the AuxiliaryProgram");
            }
        }else{
            throw new AuxiliaryProgramException("Could not find a program account associated with RCPID");
        }
    }

    private AuxiliaryIdentifier createNewAuxiliaryIdentifierWithNetId(String netID) throws Exception{
        JpaAuxiliaryIdentifierImpl ai = new JpaAuxiliaryIdentifierImpl();
        ai.setPrimary(false);
        ai.setValue(netID);
        ai.setCreationDate(new Date(System.currentTimeMillis()));
        ai.setDeleted(false);
        ai.setDeletedDate(null);
        ai.setNotificationDate(null);
        //the newly created identifier should be the NET, not RCPID
        IdentifierType it =   referenceRepository.findIdentifierType(Type.IdentifierTypes.NETID.toString());
        ai.setType((JpaIdentifierTypeImpl) it);

        return ai;
    }

//    @Override
//    public Type findType(Type.DataTypes type, Enum value) {
//        return referenceRepository.findType(type,value);
//    }
//
//    @Override
//    public Type findType(Type.DataTypes type, String value) {
//        return referenceRepository.findType(type,value);
//    }
}
