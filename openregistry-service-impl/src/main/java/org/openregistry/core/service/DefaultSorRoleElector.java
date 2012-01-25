package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;

import java.util.List;

/**
 * Example implementation of SorRoleElector
 */
public class DefaultSorRoleElector implements  SorRoleElector{
    @Override
    public boolean addSorRole(SorRole newSorRole, Person person) {
        for(Role role:person.getRoles()){
             if(  role.getAffiliationType().isSameAs(newSorRole.getAffiliationType())){
                 //found a calculated role that matches to the new sor roles affiliation , lets not add it
                 return false;
             }
        }
        //if none of calculated roles' affiliation matches to  the newSorRole then add new sor role
        person.addRole(newSorRole);
        return true;
    }
    
    public boolean removeCalculatedRole(Person person,Role roleToDelete,List<SorPerson> sorPersons){
        boolean isNewSORRoleAdded =false;
          //iterate over all the sor persons attached to this calculated
        for(SorPerson sorPerson:sorPersons){
            //try to find a sor role that that has same affiliation type as the one we are deleting from different sor
            for(SorRole sorRole:sorPerson.getRoles()){
                if(roleToDelete.getAffiliationType().isSameAs(sorRole.getAffiliationType())&&( !roleToDelete.getSorRoleId().equals(sorRole.getId()))  ){
                    //if found same role from another sor convert it to calculated one
                    person.addRole(sorRole);
                    isNewSORRoleAdded=true;
                    break;
                    
                }
            }

        }
        //delete the calculated role in any case
        person.getRoles().remove(roleToDelete);
        return isNewSORRoleAdded;
        
    }
}
