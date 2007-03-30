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

import com.adsapient.shared.mappable.PlacesImpl;
import com.adsapient.shared.mappable.StatisticImpl;
import com.adsapient.api.Banner;

import org.apache.struts.action.ActionForm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface FilterInterface extends Cloneable {
    public Integer getBannerId();

    public void setBannerId(Integer bannerId);

    public void setCampainId(Integer campainId);

    public Integer getCampainId();

    public void add(FilterInterface filterInterface);

    public void addNew();

    public Object copy() throws CloneNotSupportedException;

    public Object clone() throws CloneNotSupportedException;

    public void doAfterFilter(Banner banner, PlacesImpl places,
        StatisticImpl statistic, Map requestMap);

    public boolean doFilter(HttpServletRequest request);

    public void init(HttpServletRequest request, ActionForm actionForm);

    public void reset();

    public String save();

    public void update(HttpServletRequest request, ActionForm actionForm,
        boolean enableHibernateupdate);

    public String getRelatedFiltersQueryName();
}
