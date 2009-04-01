package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.Name;

import java.util.Set;
import java.util.Date;

/**
 * An Sor Person is the representation of the person as the System of Record knows the person.   This is in isolation
 * to the other systems of record.  The {@link org.openregistry.core.domain.Person} interface represents the final
 * calculation of the person from all the SoRs.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface SorPerson {

    /**
     * Identifier as assigned from the System of Record
     *
     * @return the identifier from the system of record.  CANNOT be null.
     */
    String getSorId();

    /**
     * Sets the Identifier for this Person WITHIN the system of record.
     * @param id the identifier. CANNOT be null, if setting.
     */
    void setSorId(String id);

    /**
     * The identifier for the System of Record that is asserting this person.  CANNOT be null.
     *
     * @return the identifier for the System of Record
     */
    String getSourceSorIdentifier();

    /**
     * Sets the identifier for the system of record.
     *
     * @param sorIdentifier the system of record identifier.
     */
    void setSourceSorIdentifier(String sorIdentifier);

    /**
     * Returns the set of names the System of Record knows about the Person.
     * <p>
     * There MUST be at least ONE name returned.
     * @return the names, minimum of one.  CANNOT be null.
     */
    Set<? extends Name> getNames();

    /**
     * The official date of birth that this System of Record is aware of.
     * @return the date of birth that this system is aware of.  CAN be null.
     */
    Date getDateOfBirth();

    /**
     * Sets the date of birth for this person, as known by the System of Record.
     *
     * @param date the date of birth.
     */
    void setDateOfBirth(Date date);

    /**
     * Retrieves the gender of the person, as known by the System of Record.  CAN be NULL.
     * @return the gender.
     */
    String getGender();

    /**
     * Sets the gender of the person, as known by the System of Record.
     * @param gender the gender.
     */
    void setGender(String gender);

    /**
     * Adds a name with the correct type to the set of names.
     *
     * @return the name that was added.
     */
    Name addName();

    /**
     * The SSN of the person.   Can be null.
     * @return the SSN of the person.
     */
    String getSsn();

    /**
     * Sets the SSN of the person.
     * 
     * @param ssn the SSN of the person.
     */
    void setSsn(String ssn);
}
