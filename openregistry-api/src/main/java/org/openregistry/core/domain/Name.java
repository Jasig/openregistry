package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Name {

    String getPrefix();

    String getGiven();

    String getMiddle();

    String getFamily();

    String getSuffix();
    
    String toString();
}
