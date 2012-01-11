package org.openregistry.core.service.auxiliaryprograms;

import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.domain.Type;
import org.openregistry.core.repository.RepositoryAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 11/1/11
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AuxiliaryProgramService {
        AuxiliaryProgram findAuxiliaryProgramById(Long id) throws RepositoryAccessException;
        List<AuxiliaryProgram> findByAuxiliaryProgramName(String name) throws RepositoryAccessException;
        List<AuxiliaryProgram> findAllAuxiliaryPrograms() throws  RepositoryAccessException;
        public AuxiliaryProgram saveAuxiliaryProgram(final AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException;
        public void deleteAuxiliaryProgram(final AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException;
        public void deleteIdentifierOfProgram(final AuxiliaryProgram auxiliaryProgram, final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;
        public void addIdentifierToProgram(final AuxiliaryProgram auxiliaryProgram, final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;

        AuxiliaryIdentifier findAuxiliaryIdentifierById(Long id) throws RepositoryAccessException;
        List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValue(String value) throws RepositoryAccessException;
        public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValueAndType(String value, String type) throws RepositoryAccessException ;
        AuxiliaryProgram findProgramOfIdentifier(AuxiliaryIdentifier identifier) throws  RepositoryAccessException;
        public AuxiliaryIdentifier saveAuxiliaryIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;
        public void deleteAuxiliaryIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;
        public void removeProgramOfIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException;
        public void assignProgramToIdentifier(final AuxiliaryIdentifier auxiliaryIdentifier,final AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException;

        public boolean netIdExistsForPerson(final String netId) ;
        public boolean netIdExistsForProgram(final String netId);

        public boolean netIdAlreadyExists(final String netId) throws RepositoryAccessException;
        public boolean providedNetIdIsAnIID(String netId);
        public void assignNetIdToProgramUsingRcpid(String rcpID, String netID) throws AuxiliaryProgramException;
        public AuxiliaryProgram findAuxiliaryProgramAssociatedWithRCPID(String rcpID) throws AuxiliaryProgramException;

//        public Type findType(Type.DataTypes type, String value);
//        public Type findType(Type.DataTypes type, Enum value);

}
