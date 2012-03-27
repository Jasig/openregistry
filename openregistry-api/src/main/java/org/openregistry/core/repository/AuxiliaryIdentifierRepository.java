package org.openregistry.core.repository;

import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/27/11
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AuxiliaryIdentifierRepository {

    AuxiliaryIdentifier findByInternalId(Long id) throws RepositoryAccessException;

    List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValue(String value) throws RepositoryAccessException;

    //List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValueAndType(String value, String type) throws RepositoryAccessException;
    public List<AuxiliaryProgram> findAuxiliaryProgramByIdentifierValueAndType(final String identifierValue,final String identifierType) throws RepositoryAccessException;

    AuxiliaryProgram findProgramOfIdentifier(AuxiliaryIdentifier identifier) throws  RepositoryAccessException;

    public AuxiliaryIdentifier saveAuxiliaryIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;

    public void deleteAuxiliaryIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;

    public void removeProgramOfIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;

    public void assignProgramToIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier,final AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException;

    public boolean identifierExistsForProgramAccount(final String identifier, final String IdentifierType);

}
