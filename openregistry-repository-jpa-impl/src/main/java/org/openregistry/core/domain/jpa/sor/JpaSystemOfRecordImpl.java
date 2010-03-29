package org.openregistry.core.domain.jpa.sor;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.sor.SystemOfRecord;

import javax.persistence.*;

/**
 * JPA-backed implementation of the SystemOfRecord.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Entity(name = "systemOfRecord")
@Table(name = "prd_system_of_record")
@Audited
public class JpaSystemOfRecordImpl implements SystemOfRecord {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_system_of_record_seq")
    @SequenceGenerator(name="prd_system_of_record_seq",sequenceName="prd_system_of_record_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name = "sor_id", insertable = true, length = 100, nullable = false, unique = true)
    private String sorId;

    @Override
    public String getSorId() {
        return this.sorId;
    }
}
