package org.openregistry.core.repository.jpa;

import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.domain.jpa.JpaAuxiliaryProgramImpl;
import org.openregistry.core.repository.AuxiliaryProgramsRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/27/11
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "auxiliaryProgramRepository")

public class JpaAuxiliaryProgramsRepository implements AuxiliaryProgramsRepository{
    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;

    @Override
    public AuxiliaryProgram findByInternalId(Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaAuxiliaryProgramImpl.class,id);
    }

    @Override
    public List<AuxiliaryProgram> findByAuxiliaryProgramName(String name) throws RepositoryAccessException {
         return (List<AuxiliaryProgram>) this.entityManager.createQuery("Select u from AUX_PROGRAMS u where u.programName = :name").setParameter("name", name).getResultList();
    }

    @Override
    public List<AuxiliaryProgram> findAllAuxiliaryPrograms() throws RepositoryAccessException {
        return (List<AuxiliaryProgram>) this.entityManager.createQuery("Select u from AUX_PROGRAMS u").getResultList();
    }

    @Override
    public AuxiliaryProgram saveAuxiliaryProgram(AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        AuxiliaryProgram auxiliaryProgramReturn = this.entityManager.merge(auxiliaryProgram);
        return auxiliaryProgramReturn;
    }

    @Override
    public void deleteAuxiliaryProgram(AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        this.entityManager.remove(auxiliaryProgram);
    }

    @Override
    public void deleteIdentifierOfProgram(AuxiliaryProgram auxiliaryProgram, AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        auxiliaryProgram.removeAuxiliaryIdentifier(auxiliaryIdentifier);
        saveAuxiliaryProgram(auxiliaryProgram);
        this.entityManager.flush();
    }

    @Override
    public void addIdentifierToProgram(AuxiliaryProgram auxiliaryProgram, AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        auxiliaryProgram.addAuxiliaryIdentifier(auxiliaryIdentifier);
        saveAuxiliaryProgram(auxiliaryProgram);
        this.entityManager.flush();
    }
}
