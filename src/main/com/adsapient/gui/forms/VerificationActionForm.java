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


public class VerificationActionForm extends ActionForm {
    private Collection verifyingCollection = new ArrayList();
    private String action = "view";
    private String elementId;
    private String tableHeader;
    private String type;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setTableHeader(String tableHeader) {
        this.tableHeader = tableHeader;
    }

    public String getTableHeader() {
        return tableHeader;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setVerifyingCollection(Collection verifyingCollection) {
        this.verifyingCollection = verifyingCollection;
    }

    public Collection getVerifyingCollection() {
        return verifyingCollection;
    }
}
