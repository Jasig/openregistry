package org.openregistry.core.domain.jpa;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 11, 2009
 * Time: 4:04:43 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="activationKey")
@Table(name="kr_activation_keys")
@Audited
public class JpaActivationKeyImpl extends Entity implements ActivationKey {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "kr_activation_keys_seq")
    @SequenceGenerator(name="kr_activation_keys_seq",sequenceName="kr_activation_keys_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name = "activation_key", nullable = false)
    private String activationKey;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "activation_date")
    @Temporal(TemporalType.DATE)
    private Date activationDate;

    @OneToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    protected Long getId() {
        return this.id;
    }

    public String getValue(){
        return this.activationKey;
    }

    public void setValue(String value){
        this.activationKey = value;
    }

    public Person getPerson(){
        return this.person;
    }

    public void setPerson(Person person){
        this.person = (JpaPersonImpl)person;
    }

    public Date getExpirationDate(){
        return this.expirationDate;
    }

    public void setExpirationDate(Date date){
        this.expirationDate = date;
    }

    public Date getActivitationDate(){
        return this.activationDate;
    }

    public void setActivationDate(Date date){
        this.activationDate = date;
    }
}
