package org.jasig.openregistry.test.service;

import org.jasig.openregistry.test.domain.MockSorPerson;
import org.jasig.openregistry.test.util.MockitoUtils;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.service.EmailService;
import org.openregistry.core.service.ServiceExecutionResult;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @since 1.0
 */
public class MockEmailService implements EmailService {

    private static final String WELL_FORMED_EMAIL = "good@email.com";

    private static final String MALFORMED_EMAIL = "bad-email.com";

    private static final String EMAIL_WITH_CONFLICT = "conflict@email.com";

    @Override
    public ServiceExecutionResult<SorPerson> saveOrCreateEmailForSorPersonWithRoleIdentifiedByAffiliation
            (SorPerson sorPerson, String emailAddress, Type emailType, Type affiliationType) {
        if (WELL_FORMED_EMAIL.equals(emailAddress)) {
            return new ServiceExecutionResult<SorPerson>() {
                @Override
                public Date getExecutionDate() {
                    return null;
                }

                @Override
                public boolean succeeded() {
                    return true;
                }

                @Override
                public SorPerson getTargetObject() {
                    return new MockSorPerson();
                }

                @Override
                public Set<ConstraintViolation> getValidationErrors() {
                    return null;
                }
            };
        }
        else if (MALFORMED_EMAIL.equals(emailAddress)) {
            return new ServiceExecutionResult<SorPerson>() {
                @Override
                public Date getExecutionDate() {
                    return null;
                }

                @Override
                public boolean succeeded() {
                    return false;
                }

                @Override
                public SorPerson getTargetObject() {
                    return null;
                }

                @Override
                public Set<ConstraintViolation> getValidationErrors() {
                    return MockitoUtils.oneMinimalisticMockConstraintViolation();
                }
            };
        }
        else if (EMAIL_WITH_CONFLICT.equals(emailAddress)) {
            return new ServiceExecutionResult<SorPerson>() {
                @Override
                public Date getExecutionDate() {
                    return null;
                }

                @Override
                public boolean succeeded() {
                    return true;
                }

                @Override
                public SorPerson getTargetObject() {
                    return null;
                }

                @Override
                public Set<ConstraintViolation> getValidationErrors() {
                    return new HashSet<ConstraintViolation>();
                }
            };
        }
        //Should only happen when invoked with not pre-defined email values
        throw new IllegalArgumentException("TEST INVOCATION ERROR: invoke with pre-defined test email values");

    }
}
