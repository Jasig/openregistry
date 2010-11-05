package org.openregistry.core.web.resources;

import org.jasig.openregistry.test.domain.MockSorPerson;
import org.openregistry.core.service.PersonService;
import org.springframework.beans.factory.FactoryBean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @since 1.0
 */
public class ProcessEmailMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() throws Exception {
        final PersonService ps = mock(PersonService.class);
        when(ps.findByIdentifierAndSource("NETID", "existent-person", "existent-sor")).thenReturn(new MockSorPerson());
        mockPersonService = ps;
    }



    @Override
    public PersonService getObject() throws Exception {
        return this.mockPersonService;
    }

    @Override
    public Class<? extends PersonService> getObjectType() {
        return PersonService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
