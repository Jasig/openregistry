package org.openregistry.security;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: scottbattaglia
 * Date: Jul 21, 2009
 * Time: 8:18:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface User {

    /**
     * Returns the set of allowed System of Records.
     *
     * @return the allowed set of System of Records.  CANNOT be NULL.  CAN be EMPTY.
     */
    Set<SystemOfRecord> getSystemOfRecords();
            

}
