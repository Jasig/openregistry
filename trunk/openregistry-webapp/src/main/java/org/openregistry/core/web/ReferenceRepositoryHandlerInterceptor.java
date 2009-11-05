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
package org.openregistry.core.web;

import org.openregistry.core.domain.Type;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version $Revision$ $Date$
 * @since 0.1-M2
 */
public final class ReferenceRepositoryHandlerInterceptor extends HandlerInterceptorAdapter {

    private final ReferenceRepository referenceRepository;

    @Autowired(required=true)
    public ReferenceRepositoryHandlerInterceptor(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse httpServletResponse, final Object o) throws Exception {
        request.setAttribute("countries", this.referenceRepository.getCountries());
        request.setAttribute("regions", this.referenceRepository.getRegions());
        request.setAttribute("campuses", this.referenceRepository.getCampuses());
        request.setAttribute("identifierTypes", this.referenceRepository.getIdentifierTypes());
        request.setAttribute("organizationalUnits", this.referenceRepository.getOrganizationalUnits());
        request.setAttribute("roleInfos", this.referenceRepository.getRoleInfos());
        request.setAttribute("addressTypes", this.referenceRepository.getTypesBy(Type.DataTypes.ADDRESS));
        request.setAttribute("affiliationTypes", this.referenceRepository.getTypesBy(Type.DataTypes.AFFILIATION));
        request.setAttribute("campusTypes", this.referenceRepository.getTypesBy(Type.DataTypes.CAMPUS));
        request.setAttribute("emailTypes", this.referenceRepository.getTypesBy(Type.DataTypes.EMAIL));
        request.setAttribute("nameTypes", this.referenceRepository.getTypesBy(Type.DataTypes.NAME));
        request.setAttribute("organizationalUnitTypes", this.referenceRepository.getTypesBy(Type.DataTypes.ORGANIZATIONAL_UNIT));
        request.setAttribute("personTypes", this.referenceRepository.getTypesBy(Type.DataTypes.PERSON));
        request.setAttribute("personStatusTypes", this.referenceRepository.getTypesBy(Type.DataTypes.PERSON_STATUS));
        request.setAttribute("phoneTypes", this.referenceRepository.getTypesBy(Type.DataTypes.PHONE));
        request.setAttribute("sponsorTypes", this.referenceRepository.getTypesBy(Type.DataTypes.SPONSOR));
        request.setAttribute("statusTypes", this.referenceRepository.getTypesBy(Type.DataTypes.STATUS));
        request.setAttribute("terminationTypes", this.referenceRepository.getTypesBy(Type.DataTypes.TERMINATION));
        request.setAttribute("urlTypes", this.referenceRepository.getTypesBy(Type.DataTypes.URL));

        return true;
    }
}
