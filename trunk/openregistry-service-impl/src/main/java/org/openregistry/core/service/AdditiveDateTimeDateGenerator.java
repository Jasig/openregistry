/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.service;

import java.util.Date;
import java.util.Calendar;

/**
 * Constructs a new date by taking the future date and adding to it based on the supplied
 * parameters (that are greater than zero).
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class AdditiveDateTimeDateGenerator implements DateGenerator{

    private int numberOfHours = 0;

    private int numberOfDays = 0;

    private int numberOfYears = 0;

    private int numberOfMinutes = 0;

    private int numberOfSeconds = 0;

    public AdditiveDateTimeDateGenerator() {
        // nothing to do
    }

    public AdditiveDateTimeDateGenerator(int type, int value) {
        switch (type) {
            case Calendar.HOUR_OF_DAY:
                this.numberOfHours = value;
                break;

            case Calendar.DAY_OF_MONTH:
                this.numberOfDays = value;
                break;

            case Calendar.YEAR:
                this.numberOfYears = value;
                break;

            case Calendar.MINUTE:
                this.numberOfMinutes = value;
                break;

            case Calendar.SECOND:
                this.numberOfSeconds = value;
                break;
        }
    }

    public Date getNewDate() {
        return getNewDate(new Date());
    }

    public Date getNewDate(final Date futureDate) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(futureDate);

        updateCalendar(calendar, Calendar.HOUR_OF_DAY, numberOfHours);
        updateCalendar(calendar, Calendar.DAY_OF_MONTH, numberOfDays);
        updateCalendar(calendar, Calendar.YEAR, numberOfYears);
        updateCalendar(calendar, Calendar.MINUTE, numberOfMinutes);
        updateCalendar(calendar, Calendar.SECOND, numberOfSeconds);

        return calendar.getTime();
    }

    protected void updateCalendar(final Calendar calendar, final int type, final int add) {
        if (add > 0) {
            calendar.add(type, add);
        }
    }

    public void setNumberOfHours(final int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public void setNumberOfDays(final int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public void setNumberOfMinutes(final int numberOfMinutes) {
        this.numberOfMinutes = numberOfMinutes;
    }

    public void setNumberOfSeconds(final int numberOfSeconds) {
        this.numberOfSeconds = numberOfSeconds;
    }
}
