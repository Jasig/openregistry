package org.jasig.openregistry.core.security;

import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Implementation of a the Spring Security's {@link org.springframework.security.userdetails.UserDetailsService} that
 * loads an OR User from the database.
 * <p>
 * The or_users table looks similar to this (for MySQL):
 *
 * CREATE TABLE or_users (
 *    username VARCHAR(100) NOT NULL UNIQUE,
 *    nickname VARCHAR(100),
 *    last_logged_in DATETIME,
 *    last_logged_in_host VARCHAR(150),
 *    person_id NUMERIC(10),
 *    enabled BOOLEAN
 * )
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaUserDetailsServiceImpl implements UserDetailsService {

    @Autowired(required=true)
    private PersonRepository personRepository;

    @Autowired(required=true)
    private SimpleJdbcTemplate jdbcTemplate;

    private final String SQL_USER_QUERY = "Select nickname, last_logged_in, last_logged_in_host, person_id, enabled from or_users where username = ?";

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {

        return this.jdbcTemplate.queryForObject(SQL_USER_QUERY, new ParameterizedRowMapper<SpringSecurityUserImpl>() {
            public SpringSecurityUserImpl mapRow(final ResultSet resultSet, final int i) throws SQLException {
                final String nickname = resultSet.getString("nickname");
                final Date last_logged_in = resultSet.getDate("last_logged_in");
                final String lastLoggedInHost = resultSet.getString("last_logged_in_host");
                final long personId = resultSet.getLong("person_id");
                final boolean enabled = resultSet.getBoolean("enabled");
                final Person person;

                if (personId != -1) {
                    person = personRepository.findByInternalId(personId);
                } else {
                    person = null;
                }

                return new SpringSecurityUserImpl(username, nickname, person, null, last_logged_in, lastLoggedInHost, enabled);
            }
        }, username);
    }
}
