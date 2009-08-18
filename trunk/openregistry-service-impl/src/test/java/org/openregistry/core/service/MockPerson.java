package org.openregistry.core.service;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.SorRole;

import java.util.*;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockPerson implements Person {

    private ActivationKey activationKey = new MockActivationKey(UUID.randomUUID().toString(), new Date(), new Date());

    public Long getId() {
        return 1L;
    }

    public Set<? extends Name> getNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Name addName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Role> getRoles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role addRole(RoleInfo roleInfo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role addRole(RoleInfo roleInfo, SorRole sorRole) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role addRole(Role role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeRole(Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeAllRoles() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<Identifier> getIdentifiers() {
        final Set<Identifier> identifiers = new HashSet<Identifier>();

        final Identifier id = new Identifier() {
            public IdentifierType getType() {
                return new IdentifierType() {
                    public Long getId() {
                        return 1L;
                    }

                    public String getName() {
                        return "NetId";
                    }
                };
            }

            public String getValue() {
                return "testId";
            }

            public Boolean isPrimary() {
                return true;
            }

            public Boolean isDeleted() {
                return false;
            }

            public void setType(final IdentifierType type) {
            }

            public void setValue(final String value) {

            }

            public void setPrimary(Boolean value) {

            }

            public void setDeleted(final Boolean value) {

            }
        };

        identifiers.add(id);
        return identifiers;
    }

    public Name getPreferredName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Name getOfficialName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getGender() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDateOfBirth() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDateOfBirth(Date dateOfBirth) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setGender(String gender) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Identifier addIdentifier() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Name addOfficialName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Name addPreferredName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPreferredName(Name name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role pickOutRole(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Identifier pickOutIdentifier(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ActivationKey generateNewActivationKey(Date start, Date end) {
        this.activationKey = new MockActivationKey(UUID.randomUUID().toString(), start, end);
        return this.activationKey;
    }

    public ActivationKey generateNewActivationKey(Date end) {
        this.activationKey = new MockActivationKey(UUID.randomUUID().toString(), new Date(), end);
        return this.activationKey;
    }

    public ActivationKey getCurrentActivationKey() {
        return this.activationKey;
    }

    public void removeCurrentActivationKey() {
        this.activationKey = null;
    }
}
