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

import com.adsapient.shared.mappable.AccountSetting;

import org.apache.struts.action.ActionForm;


public class AccountProfileActionForm extends ActionForm {
    private String action = "init";
    private int axis;
    private int deph;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAxis(int axis) {
        this.axis = axis;
    }

    public int getAxis() {
        return axis;
    }

    public void setDeph(int deph) {
        this.deph = deph;
    }

    public int getDeph() {
        return deph;
    }

    public AccountSetting getValues(AccountSetting settings) {
        settings.setAxis(new Integer(this.axis));
        settings.setDepth(new Integer(this.deph));

        return settings;
    }
}
