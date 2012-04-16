package org.jasig.openregistry.test.repository;

import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.repository.AuxiliaryIdentifierRepository;
import org.openregistry.core.repository.RepositoryAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 4/15/12
 * Time: 8:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockAuxiliaryIdentifierRepository implements AuxiliaryIdentifierRepository{

    @Override
    public AuxiliaryIdentifier findByInternalId(Long id) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValue(String value) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AuxiliaryProgram> findAuxiliaryProgramByIdentifierValueAndType(String identifierValue, String identifierType) throws RepositoryAccessException {
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
    public boolean identifierExistsForProgramAccount(String identifier, String IdentifierType) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
