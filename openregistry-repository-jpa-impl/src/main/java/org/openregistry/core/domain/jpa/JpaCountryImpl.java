package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="country")
@Table(name="ctd_countries")
@Audited
public class JpaCountryImpl extends Entity implements Country {

    @Id
    @Column(name="country_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctd_country_seq")
    @SequenceGenerator(name="ctd_country_seq",sequenceName="ctd_country_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="code",nullable=false,length = 3)
    private String code;

    @Column(name="name",nullable = false, length=100)
    private String name;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="country")
    private List<JpaRegionImpl> regions;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="country")
    private List<JpaAddressImpl> addresses;

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public List<? extends Region> getRegions() {
        return this.regions;
    }
}
