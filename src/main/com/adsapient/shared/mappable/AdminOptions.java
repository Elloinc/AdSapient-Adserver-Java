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
package com.adsapient.shared.mappable;

import com.adsapient.shared.api.entity.IMappable;


public class AdminOptions implements IMappable {
    public static final Integer MAIL_SERVER = new Integer(1);
    public static final Integer MAIL_SERVER_LOGIN = new Integer(2);
    public static final Integer MAIL_SERVER_PASSWORD = new Integer(3);
    public static final Integer TARGET_WINDOW = new Integer(4);
    public static final Integer LOADING_TYPE = new Integer(5);
    public static final Integer DATABASE_LOGIN = new Integer(7);
    public static final Integer DATABASE_PASSWORD = new Integer(8);
    public static final Integer DATABASE_URL = new Integer(6);
    public static final Integer DATABASE_CLASS = new Integer(9);
    private Integer itemId;
    private String itemName;
    private String itemValue;

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemValue() {
        return itemValue;
    }

    public Integer getId() {
        return itemId;
    }
}
