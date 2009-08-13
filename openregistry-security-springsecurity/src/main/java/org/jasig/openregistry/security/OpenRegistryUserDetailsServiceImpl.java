package org.jasig.openregistry.security;

import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.domain.Person;
import org.openregistry.security.*;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Implementation of a the Spring Security's {@link org.springframework.security.userdetails.UserDetailsService} that
 * loads an OR User from the database.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@ValidateDefinition
public final class OpenRegistryUserDetailsServiceImpl implements UserDetailsService {

    @NotEmpty
    private String identifierType;

    @Autowired(required=true)
    @NotNull
    private PersonRepository personRepository;

    @Autowired(required=true)
    @NotNull
    private PermissionRepository permissionRepository;

    @Autowired(required=true)
    private ExpressionParser expressionParser;

    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        final Person person = findPersonById(username);
        final Set<Privilege> privileges = new HashSet<Privilege>();
        /*
        final List<Privilege> authenticatedUserPrivileges = this.permissionRepository.getPrivilegesFor(Subject.SubjectType.AUTHENTICATED);
        final List<Privilege> everyoneUserPrivileges = this.permissionRepository.getPrivilegesFor(Subject.SubjectType.EVERYONE);
        final List<Privilege> userPrivileges = this.permissionRepository.getPrivilegesForUser(username);
        final List<Privilege> expressionPrivileges = this.permissionRepository.getPrivilegesFor(Subject.SubjectType.EXPRESSION);

        final List<PrivilegeSet> authenticatedPrivilegeSets = this.permissionRepository.getPrivilegeSetsFor(Subject.SubjectType.AUTHENTICATED);
        final List<PrivilegeSet> everyonePrivilegeSets = this.permissionRepository.getPrivilegeSetsFor(Subject.SubjectType.EVERYONE);
        final List<PrivilegeSet> userPrivilegeSets = this.permissionRepository.getPrivilegeSetsForUser(username);
        final List<PrivilegeSet> expressionPrivilegeSets = this.permissionRepository.getPrivilegeSetsFor(Subject.SubjectType.EXPRESSION);


        privileges.addAll(authenticatedUserPrivileges);
        privileges.addAll(everyoneUserPrivileges);
        privileges.addAll(userPrivileges);

        addPrivilegeSetsToListOfPrivileges(privileges, authenticatedPrivilegeSets);
        addPrivilegeSetsToListOfPrivileges(privileges, everyonePrivilegeSets);
        addPrivilegeSetsToListOfPrivileges(privileges, userPrivilegeSets);

        final List<Privilege> parsedExpressionPrivileges = parseExpressionPrivileges(person, expressionPrivileges);
        final List<Privilege> parsedExpressionPrivilegeSets = parseExpressionPrivilegeSets(person, expressionPrivilegeSets);

        privileges.addAll(parsedExpressionPrivileges);
        privileges.addAll(parsedExpressionPrivilegeSets);
        */
        return new SpringSecurityUserImpl(username, true, privileges, this.expressionParser);
    }

    protected void addPrivilegeSetsToListOfPrivileges(final Set<Privilege> privileges, final List<PrivilegeSet> privilegeSets) {
        for (final PrivilegeSet ps : privilegeSets) {
            privileges.addAll(ps.getPrivileges());
        }
    }

    protected List<Privilege> parseExpressionPrivileges(final Person person, List<Privilege> expressionPrivileges) {
        final List<Privilege> privileges = new ArrayList<Privilege>();

        if (person != null) {

            for (final Privilege p : expressionPrivileges) {
                if (this.expressionParser.matches(person, p.getExpression())) {
                    privileges.add(p);
                }
            }
        }

        return privileges;
    }

    protected List<Privilege> parseExpressionPrivilegeSets(final Person person, List<PrivilegeSet> privilegeSets) {
        final List<Privilege> privileges = new ArrayList<Privilege>();

        if (person != null) {
            for (final PrivilegeSet ps : privilegeSets) {
                if (this.expressionParser.matches(person, ps.getExpression())) {
                    privileges.addAll(ps.getPrivileges());
                }
            }
        }

        return privileges;
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

    /**
     * Sets the identifier type.  The identifier type tells the system what type of identifier the passed in username is.
     * <p>
     * This allows the system to determine if there is a matching person in the system.
     *
     * @param identifierType the identifier type.  MUST be one of the ones specified in the identifiers table.
     */
    public void setIdentifierType(final String identifierType) {
        this.identifierType = identifierType;
    }
}
