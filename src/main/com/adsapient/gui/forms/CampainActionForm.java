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
package com.adsapient.gui.forms;

import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.Collection;


public class CampainActionForm extends ActionForm {
    private Collection banners = new ArrayList();
    private Collection campains = new ArrayList();
    private String campainId = "0";
    private String tableRateHeader;
    private String userId;

    public void setBanners(Collection banners) {
        this.banners = banners;
    }

    public Collection getBanners() {
        return banners;
    }

    public void setCampainId(String campainId) {
        this.campainId = campainId;
    }

    public String getCampainId() {
        return campainId;
    }

    public void setCampains(Collection campains) {
        if (campains != null) {
            this.campains = campains;
        } else {
        }
    }

    public Collection getCampains() {
        return campains;
    }

    public void setTableRateHeader(String tableRateHeader) {
        this.tableRateHeader = tableRateHeader;
    }

    public String getTableRateHeader() {
        return tableRateHeader;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
