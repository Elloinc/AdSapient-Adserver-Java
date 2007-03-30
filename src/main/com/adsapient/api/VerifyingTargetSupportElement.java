/*
 * AdSapient - Open Source Ad Server
 * http://www.sourceforge.net/projects/adsapient
 * http://www.adsapient.com
 *
 * Copyright (C) 2001-06 Vitaly Sazanovich
 * Vitaly.Sazanovich@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License  as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.adsapient.api;

import com.adsapient.shared.AdsapientConstants;
import com.adsapient.api.AbstractVerifyingElement;
import com.adsapient.api.TargetSupportInterface;

import javax.servlet.http.HttpServletRequest;


public abstract class VerifyingTargetSupportElement
    extends AbstractVerifyingElement implements TargetSupportInterface {
    private int loadingTypeId;
    private int placeTypeId;
    private int targetWindowId;

    public VerifyingTargetSupportElement(HttpServletRequest request) {
        super(request);

        this.loadingTypeId = AdsapientConstants.ANY;

        this.placeTypeId = AdsapientConstants.ANY;

        this.targetWindowId = AdsapientConstants.ANY;
    }

    public VerifyingTargetSupportElement() {
        super();

        this.loadingTypeId = AdsapientConstants.ANY;

        this.placeTypeId = AdsapientConstants.ANY;

        this.targetWindowId = AdsapientConstants.ANY;
    }

    public void setLoadingTypeId(int loadingTypeId) {
        this.loadingTypeId = loadingTypeId;
    }

    public int getLoadingTypeId() {
        return loadingTypeId;
    }

    public void setPlaceTypeId(int placeTypeId) {
        this.placeTypeId = placeTypeId;
    }

    public int getPlaceTypeId() {
        return placeTypeId;
    }

    public void setTargetWindowId(int targetWindowId) {
        this.targetWindowId = targetWindowId;
    }

    public int getTargetWindowId() {
        return targetWindowId;
    }
}
