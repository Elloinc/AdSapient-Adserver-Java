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

import com.adsapient.shared.mappable.AdminOptions;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;


public class SimpleTargetSupportElement implements TargetSupportInterface {
    private Integer loadingTypeId;
    private Integer placeTypeId;
    private Integer targetWindowId;

    public SimpleTargetSupportElement() {
        this.loadingTypeId = AdsapientConstants.LOADING_TYPE_IMMEDIATE;
        this.placeTypeId = AdsapientConstants.PLACE_TYPE_ORDINARY;
        this.targetWindowId = AdsapientConstants.TARGET_WINDOW_SELF;
    }

    public void setLoadingTypeId(int loadingTypeId) {
        this.loadingTypeId = loadingTypeId;
    }

    public int getLoadingTypeId() {
        if (loadingTypeId == null) {
            loadingTypeId = Integer.parseInt(((AdminOptions) MyHibernateUtil.loadObject(
                        AdminOptions.class, AdminOptions.LOADING_TYPE)).getItemValue());
        }

        return loadingTypeId;
    }

    public void setPlaceTypeId(int placeTypeId) {
        this.placeTypeId = placeTypeId;
    }

    public int getPlaceTypeId() {
        if (this.placeTypeId == null) {
            this.placeTypeId = AdsapientConstants.PLACE_TYPE_ORDINARY;
        }

        return placeTypeId;
    }

    public void setTargetWindowId(int targetWindowId) {
        this.targetWindowId = targetWindowId;
    }

    public int getTargetWindowId() {
        if (targetWindowId == null) {
            targetWindowId = Integer.parseInt(((AdminOptions) MyHibernateUtil.loadObject(
                        AdminOptions.class, AdminOptions.TARGET_WINDOW)).getItemValue());
        }

        return targetWindowId;
    }
}
