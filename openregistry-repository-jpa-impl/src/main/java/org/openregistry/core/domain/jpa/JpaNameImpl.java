package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity
@Table(name="prs_names")
public class JpaNameImpl extends Entity implements Name {

    @Column(name="prefix", nullable=true, length=5)
    private String prefix;

    @Column(name="given_name",nullable=true,length=100)
    private String given;

    @Column(name="middle_name",nullable=true,length=100)
    private String middle;

    @Column(name="family_name",nullable=true,length=100)
    private String family;

    @Column(name="suffix",nullable=true,length=5)
    private String suffix;

    @OneToOne(optional=false, mappedBy="name")
    private JpaPersonImpl person;

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

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        construct(builder, "", this.middle, " ");
        construct(builder, "", this.family, "");
        construct(builder, ",", this.suffix, "");

        return builder.toString();
    }

    protected void construct(final StringBuilder builder, final String prefix, final String string, final String delimiter) {
        if (string != null) {
            builder.append(prefix);
            builder.append(string);
            builder.append(delimiter);
        }
    }

    public Person getPerson() {
        return this.person;
    }
}
