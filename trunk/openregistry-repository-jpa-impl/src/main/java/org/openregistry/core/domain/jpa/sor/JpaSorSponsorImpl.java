package org.openregistry.core.domain.jpa.sor;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;

/**
 * @author Dave Steiner
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sorSponsor")
@Table(name="prs_sponsors")
@Audited
public class JpaSorSponsorImpl extends Entity implements SorSponsor {

	private static final long serialVersionUID = 3547710151070428086L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_sponsors_seq")
    @SequenceGenerator(name="prs_sponsors_seq",sequenceName="prs_sponsors_seq",initialValue=1,allocationSize=50)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_t")
    private JpaTypeImpl sponsorType;

    @Column(name="sponsor_id")
    private Long sponsorId;
    
    @OneToMany(mappedBy = "sponsor", targetEntity = JpaSorRoleImpl.class)
    private Set<SorRole> roles = new HashSet<SorRole>();

	public JpaSorSponsorImpl() {
		// nothing to do
	}
	
	public JpaSorSponsorImpl(SorRole role) {
		this.roles.add(role);
	}

	protected Long getId() {
		return this.id;
	}

	public Long getSponsorId() {
		return this.sponsorId;
	}

	public Type getType() {
		return this.sponsorType;
	}

	public void setSponsorId(final Long id) {
		this.sponsorId = id;
	}


	public void setType(final Type type) {
	    if (!(type instanceof JpaTypeImpl)) {
	    	throw new IllegalArgumentException("Requires type JpaTypeImpl");
	    }
	    this.sponsorType = (JpaTypeImpl)type;
	}

	public void addRole(final SorRole role) {
		if (!(role instanceof JpaSorRoleImpl)) {
			throw new IllegalArgumentException("role of type JpaSorRoleImpl required");
		}
		this.roles.add(role);
	}

	public Set<SorRole> getRoles() {
		return this.roles;
	}

}
