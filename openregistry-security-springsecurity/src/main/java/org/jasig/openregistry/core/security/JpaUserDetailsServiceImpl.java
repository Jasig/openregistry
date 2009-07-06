package org.jasig.openregistry.core.security;

import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.domain.Person;
import org.openregistry.security.Permission;
import org.openregistry.security.PermissionRepository;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;

import java.util.List;

/**
 * Implementation of a the Spring Security's {@link org.springframework.security.userdetails.UserDetailsService} that
 * loads an OR User from the database.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@ValidateDefinition
public class JpaUserDetailsServiceImpl implements UserDetailsService {

    @NotEmpty
    private String identifierType;

    @Autowired(required=true)
    @NotNull
    private PersonRepository personRepository;

    @Autowired(required=true)
    @NotNull
    private PermissionRepository permissionRepository;

    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        final Person person = findPersonById(username);
        final List<Permission> permissions = this.permissionRepository.getPermissionsForUser(username, person);
        return new SpringSecurityUserImpl(username, true, permissions);
    }

    protected Person findPersonById(final String username) {
        try {
            return personRepository.findByIdentifier(this.identifierType, username);
        } catch (final Exception e) {
            return null;
        }
    }

    public void setPersonRepository(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void setPermissionRepository(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void setIdentifierType(final String identifierType) {
        this.identifierType = identifierType;
    }
}
