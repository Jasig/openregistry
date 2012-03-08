package org.jasig.openregistry.test.service;

import org.jasig.openregistry.test.domain.MockIdentifier;
import org.jasig.openregistry.test.domain.MockIdentifierType;
import org.jasig.openregistry.test.domain.MockPerson;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.identifier.NetIdManagementService;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: msidd
 * Date: 3/8/12
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockNetIdManagementService implements NetIdManagementService{
    @Override
    public ServiceExecutionResult<Identifier> changePrimaryNetId(final String currentNetIdValue, final String newNetIdValue) throws IllegalArgumentException, IllegalStateException {
        if(currentNetIdValue ==null)
            throw new IllegalArgumentException("currnet Netid must not be null");
        if(newNetIdValue==null)
            throw new IllegalArgumentException("new NEtID must not be null");

        ServiceExecutionResult<Identifier> result = new ServiceExecutionResult<Identifier>() {
            @Override
            public Date getExecutionDate() {
                return new Date();  
            }

            @Override
            public boolean succeeded() {
                return true;  
            }

            @Override
            public Identifier getTargetObject() {
                return new MockIdentifier(new MockPerson(),new MockIdentifierType("NETID",false),currentNetIdValue);
            }

            @Override
            public Set<ConstraintViolation> getValidationErrors() {
                return Collections.emptySet();
            }
        };
        return result;
    }

    @Override
    public ServiceExecutionResult<Identifier> addNonPrimaryNetId(final String primaryNetIdValue, String newNonPrimaryNetIdValue) throws IllegalArgumentException, IllegalStateException {
        if(primaryNetIdValue==null)
            throw new IllegalArgumentException("primary net id may not be null");
        if(newNonPrimaryNetIdValue==null)
            throw new IllegalArgumentException("non primary netid value may not be null") ;

        return new ServiceExecutionResult<Identifier>() {
            @Override
            public Date getExecutionDate() {
                return new Date();
            }

            @Override
            public boolean succeeded() {
                return true;
            }

            @Override
            public Identifier getTargetObject() {
                return new MockIdentifier(new MockPerson(),new MockIdentifierType("NETID",false),primaryNetIdValue);
            }

            @Override
            public Set<ConstraintViolation> getValidationErrors() {
                return Collections.emptySet();
            }
        };

    }
}
