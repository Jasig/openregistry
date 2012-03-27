package org.openregistry.core.repository.jpa;

import org.openregistry.core.authorization.User;
import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.jpa.JpaAuxiliaryIdentifierImpl;
import org.openregistry.core.domain.jpa.JpaAuxiliaryProgramImpl;
import org.openregistry.core.repository.AuxiliaryIdentifierRepository;
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
@Repository(value = "auxiliaryIdentifierRepository")
public class JpaAuxiliaryIdentifiersRepository implements AuxiliaryIdentifierRepository {
    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;

    @Override
    public AuxiliaryIdentifier findByInternalId(Long id) throws RepositoryAccessException {
        return entityManager.find(JpaAuxiliaryIdentifierImpl.class,id);
    }

    @Override
    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValue(String value) throws RepositoryAccessException {
        return (List<AuxiliaryIdentifier>) this.entityManager.createQuery("Select u from AUX_IDENTIFIERS u where u.value = :value").setParameter("value", value).getResultList();
    }

//    @Override
//    public List<AuxiliaryIdentifier> findByAuxiliaryIdentifierValueAndType(String value, String type) throws RepositoryAccessException {
//        //return (List<AuxiliaryIdentifier>) this.entityManager.createQuery("Select u from AUX_IDENTIFIERS u where u.value = :value and u.").setParameter("value", value).getResultList();
//        return (List<AuxiliaryIdentifier>) this.entityManager.createQuery("Select u from AUX_IDENTIFIERS u join u.value i join i.type t where t.name = :name and i.value = :value").setParameter("name", identifierType).setParameter("value", identifierValue).getSingleResult();
//    }
    @Override
    public List<AuxiliaryProgram> findAuxiliaryProgramByIdentifierValueAndType(final String identifierValue,final String identifierType) throws RepositoryAccessException {
        return (List<AuxiliaryProgram>) this.entityManager.createQuery("Select p from AUX_PROGRAMS p join p.identifiers i join i.type t where t.name = :name and i.value = :value").setParameter("name", identifierType).setParameter("value", identifierValue).getResultList();
    }

    @Override
    public AuxiliaryProgram findProgramOfIdentifier(AuxiliaryIdentifier identifier) throws  RepositoryAccessException{
        return identifier.getProgram();
    }

    @Override
    public AuxiliaryIdentifier saveAuxiliaryIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        AuxiliaryIdentifier auxiliaryIdentifierReturn  = this.entityManager.merge(auxiliaryIdentifier);
        return auxiliaryIdentifierReturn;
    }

    @Override
    public void deleteAuxiliaryIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        this.entityManager.remove(auxiliaryIdentifier);
    }

    @Override
    public void removeProgramOfIdentifier(AuxiliaryIdentifier auxiliaryIdentifier) throws RepositoryAccessException {
        //TODO- 'make the Program null and save the identifier'. Is it right approach?
        auxiliaryIdentifier.setProgram(null);
        this.entityManager.merge(auxiliaryIdentifier);
    }

    @Override
    public void assignProgramToIdentifier(AuxiliaryIdentifier auxiliaryIdentifier, AuxiliaryProgram auxiliaryProgram) throws RepositoryAccessException {
        auxiliaryIdentifier.setProgram(auxiliaryProgram);
        this.entityManager.merge(auxiliaryIdentifier);
    }

    @Override
    public boolean identifierExistsForProgramAccount(final String auxiliaryIdentifier, final String IdentifierType){
      boolean flag= false;
        try{
            List<AuxiliaryIdentifier> auxIdentifierLst = this.findByAuxiliaryIdentifierValue(auxiliaryIdentifier);
            if(null!= auxIdentifierLst && auxIdentifierLst.size() > 0){
                for(AuxiliaryIdentifier ai : auxIdentifierLst){
                    if(ai.getType().getName().equalsIgnoreCase(IdentifierType)){
                        flag=true;
                    }
                }

            }
        }catch(RepositoryAccessException rae){
            flag = false;

        }catch (Exception ex){
            flag = false;
        }
        return flag;
    }

}
