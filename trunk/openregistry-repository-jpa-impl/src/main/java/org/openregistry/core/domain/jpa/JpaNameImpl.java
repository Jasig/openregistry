package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.core.ValidateDefinition;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="name")
@Table(name="prc_names")
@Audited
@ValidateDefinition
public class JpaNameImpl extends Entity implements Name {

    @Id
    @Column(name="name_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_name_seq")
    @SequenceGenerator(name="prc_name_seq",sequenceName="prc_name_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="prefix", nullable=true, length=5)
    private String prefix;

    @Column(name="given_name",nullable=false,length=100)
    @NotNull(customCode = "firstNameRequiredMsg")
    private String given;

    @Column(name="middle_name",nullable=true,length=100)
    private String middle;

    @Column(name="family_name",nullable=true,length=100)
    private String family;

    @Column(name="suffix",nullable=true,length=5)
    private String suffix;

    @OneToOne(mappedBy = "preferredName",optional=true)
    private JpaPersonImpl person1;

    @OneToOne(mappedBy = "officialName", optional=true)
    private JpaPersonImpl person2;

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

    public void setOfficialPerson(final JpaPersonImpl person2) {
        this.person2 = person2;
    }

    public void setPreferredNamePerson(final JpaPersonImpl person1) {
        this.person1 = person1;
    }

    public String getFormattedName(){
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.family, ",");
        construct(builder, "", this.given, "");

        return builder.toString();
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
}
