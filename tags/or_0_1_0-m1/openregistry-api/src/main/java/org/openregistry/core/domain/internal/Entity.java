/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    /**
     * Default actor.
     */
    public Entity() {
    }

    protected abstract Long getId();


    @Override
    public String toString() {
        return getClass() + ": id=" + getId();
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
        if (getId() == null || entity.getId() == null) {
            return false;
        }
        return getId().equals(entity.getId());
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
        if (getId() == null) {
            return super.hashCode();
        }
        return 39 + 87 * getId().hashCode();
	}
}
