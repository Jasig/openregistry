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
package org.openregistry.core.service.reconciliation;

import java.util.List;
import java.util.ArrayList;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockMaybeReconciliationResult implements ReconciliationResult {

    public ReconciliationType getReconciliationType() {
        return ReconciliationType.MAYBE;
    }

    public List<PersonMatch> getMatches() {
        final List<PersonMatch> personMatches = new ArrayList<PersonMatch>();
        return personMatches;
    }

    public boolean noPeopleFound() {
        return false;
    }

    public boolean personAlreadyExists() {
        return false;
    }

    public boolean multiplePeopleFound() {
        return true;
    }
}