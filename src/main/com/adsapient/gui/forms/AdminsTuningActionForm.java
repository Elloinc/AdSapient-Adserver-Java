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

import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.service.ValidationService;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class AdminsTuningActionForm extends ActionForm {
    int categoryPosition;
    private String allowchars = "";
    private Collection sortedCategorysCollection = new ArrayList();
    private Collection entitysCollection;
    private String action = "view";
    private String entity;
    private String entityId;
    private String entityValue;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCategoryPosition(int categoryPosition) {
        this.categoryPosition = categoryPosition;
    }

    public int getCategoryPosition() {
        return categoryPosition;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setSortedCategorysCollection(
        Collection sortedCategorysCollection) {
        this.sortedCategorysCollection = sortedCategorysCollection;
    }

    public Collection getEntitysCollection() {
        return entitysCollection;
    }

    public void setEntitysCollection(Collection entitysCollection) {
        this.entitysCollection = entitysCollection;
    }

    public String getEntityValue() {
        return entityValue;
    }

    public void setEntityValue(String entityValue) {
        this.entityValue = entityValue;
    }

    public Collection getSortedCategorysCollection() {
        return sortedCategorysCollection;
    }

    public String getAllowchars() {
        return allowchars;
    }

    public void setAllowchars(String allowchars) {
        this.allowchars = allowchars;
    }
}
