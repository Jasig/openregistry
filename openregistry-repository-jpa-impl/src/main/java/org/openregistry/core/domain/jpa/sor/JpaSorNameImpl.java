package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.core.ValidateDefinition;

import javax.persistence.*;

/**
 * Implementation of the Name domain object that conforms to the tables for the Systems of Record
 *
 * TODO: Add Type field.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sorName")
@Table(name="prs_names")
@ValidateDefinition
public class JpaSorNameImpl extends Entity implements Name {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_name_seq")
    @SequenceGenerator(name="prs_name_seq",sequenceName="prs_name_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="prefix", nullable=true, length=5)
    private String prefix;

    @NotEmpty
    @Column(name="given_name",nullable=false,length=100)
    private String given;

    @Column(name="middle_name",nullable=true,length=100)
    private String middle;

    @Column(name="family_name",nullable=true,length=100)
    private String family;

    @Column(name="suffix",nullable=true,length=5)
    private String suffix;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_id", nullable=false)
    private JpaSorPersonImpl person;

    public JpaSorNameImpl() {

    }

    public JpaSorNameImpl(final JpaSorPersonImpl person) {
        this.person = person;
    }

    protected Long getId() {
        return this.id;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getGiven() {
        return this.given;
    }

    public String getMiddle() {
        return this.middle;
    }

    public String getFamily() {
        return this.family;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setGiven(final String given) {
        this.given = given;
    }

    public void setMiddle(final String middle) {
        this.middle = middle;
    }

    public void setFamily(final String family) {
        this.family = family;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
}
