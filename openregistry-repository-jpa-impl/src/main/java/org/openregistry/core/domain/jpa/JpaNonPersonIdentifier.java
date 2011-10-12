package org.openregistry.core.domain.jpa;

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.NonPersonIdentifier;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 8/23/11
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name="aux_identifiers", uniqueConstraints= @UniqueConstraint(columnNames={"identifier_t", "identifier"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "aux_identifiers", indexes = {
        @Index(name="AUX_ID_TYPE_INDEX", columnNames = {"identifier", "identifier_t"}),
        @Index(name="AUX_IDENTIFIER_IDX", columnNames = {"identifier"}),
        @Index(name = "AUX_IDENTIF_NPERSON_IDX", columnNames = "aux_nonperson_id")
    })
public class JpaNonPersonIdentifier extends Entity implements NonPersonIdentifier{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_identifiers_seq")
    @SequenceGenerator(name="prc_identifiers_seq",sequenceName="prc_identifiers_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="aux_nonperson_id")
    private JpaNonPersonImpl nonperson;

    @ManyToOne(optional=false)
    @JoinColumn(name="identifier_t")
    private JpaIdentifierTypeImpl type;

    @Column(name="identifier", length=100, nullable=false)
    private String value;

    @Column(name="is_primary", nullable=false)
    private boolean primary = true;

    @Column(name="is_deleted", nullable=false)
    private boolean deleted = false;

    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(name="deleted_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name="notification_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationDate;

    @Override
    public Long getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getCreationDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isDeleted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getDeletedDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getNotificationDate() throws IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPrimary() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IdentifierType getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCreationDate(Date originalCreationDate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDeleted(boolean value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDeletedDate(Date date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setNotificationDate(Date date) throws IllegalStateException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPrimary(boolean value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
