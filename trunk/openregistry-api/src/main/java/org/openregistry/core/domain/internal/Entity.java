package org.openregistry.core.domain.internal;

import java.io.Serializable;

/**
 * Open Registry base Domain Entity class. Any class that should be uniquely identifiable
 * from another should subclass from Entity. More information on this pattern
 * and the difference between Entities and Value Objects can be found in Domain
 * Driven Design by Eric Evans.
 *
 * @since 1.0
 */
public abstract class Entity implements Serializable {

    private Long id;

    /**
     * Default ctor.
     */
    public Entity() {
    }

    public Entity(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass() + ": id=" + this.id;
    }

    /**
     * Attempt to establish identity based on id if exists. If id
     * does not exist use Object.equals().
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Entity)) {
            return false;
        }
        Entity entity = (Entity) other;
        if (id == null || entity.getId() == null) {
            return false;
        }
        return id.equals(entity.getId());
    }

    /**
     * Use ID if it exists to establish hash code, otherwise fall back to
     * Object.hashCode(). Based on the same information as equals, so if that
     * changes, this will. N.B. this follows the contract of Object.hashCode(),
     * but will cause problems for anyone adding an unsaved {@link Entity} to a
     * Set because Set.contains() will almost certainly return false for the
     * {@link Entity} after it is saved.
     * Any entities that need to be added to hash-based collections would need to
     * override a pair of equals and hashCode.
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        return 39 + 87 * id.hashCode();
	}
}
