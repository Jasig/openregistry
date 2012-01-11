package org.openregistry.core.repository;

import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/25/11
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuxiliaryProgramsRepository {
    AuxiliaryProgram findByInternalId(Long id) throws RepositoryAccessException;

    List<AuxiliaryProgram> findByAuxiliaryProgramName(String name) throws RepositoryAccessException;

    List<AuxiliaryProgram> findAllAuxiliaryPrograms() throws  RepositoryAccessException;

    public AuxiliaryProgram saveAuxiliaryProgram(final AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException;

    public void deleteAuxiliaryProgram(final AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException;

    public void deleteIdentifierOfProgram(final AuxiliaryProgram auxiliaryProgram, final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;

    public void addIdentifierToProgram(final AuxiliaryProgram auxiliaryProgram, final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;

//    public Set<AuxiliaryIdentifier> findIdentifierOfProgram(final AuxiliaryIdentifier identifier) throws RepositoryAccessException;
//
//    public Set<AuxiliaryIdentifier> findIdentifierOfProgram(final Long id) throws RepositoryAccessException;

//    public void deleteGroupsOfUser(User user) throws RepositoryAccessException;

//    public void addGroupsToUser(User user, Set<Group> groups) throws RepositoryAccessException ;
}
