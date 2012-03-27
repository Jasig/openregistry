package org.jasig.openregistry.test.service;

import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramException;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 1/12/12
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockAuxiliaryProgramService implements AuxiliaryProgramService {

    @Override
    public AuxiliaryProgram findAuxiliaryProgramById(Long id) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AuxiliaryProgram> findByAuxiliaryProgramName(String name) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AuxiliaryProgram> findAllAuxiliaryPrograms() throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AuxiliaryProgram saveAuxiliaryProgram(AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAuxiliaryProgram(AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteIdentifierOfProgram(AuxiliaryProgram auxiliaryProgram, AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addIdentifierToProgram(AuxiliaryProgram auxiliaryProgram, AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AuxiliaryIdentifier findAuxiliaryIdentifierById(Long id) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValue(String value) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValueAndType(String value, String type) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AuxiliaryProgram findProgramOfIdentifier(AuxiliaryIdentifier identifier) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AuxiliaryIdentifier saveAuxiliaryIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAuxiliaryIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeProgramOfIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void assignProgramToIdentifier(AuxiliaryIdentifier auxiliaryIdentifier, AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean netIdExistsForPerson(String netId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean netIdExistsForProgram(String netId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean netIdAlreadyExists(String netId) throws RepositoryAccessException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean providedNetIdIsAnIID(String netId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void assignNetIdToProgramUsingRcpid(String rcpID, String netID) throws AuxiliaryProgramException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AuxiliaryProgram findAuxiliaryProgramAssociatedWithRCPID(String rcpID) throws AuxiliaryProgramException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean iidExistsForAPersonOrProgram(String iid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean iidExistsForProgram(String iid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
