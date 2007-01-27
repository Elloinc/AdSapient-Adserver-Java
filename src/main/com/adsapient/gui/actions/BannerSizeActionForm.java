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
package com.adsapient.gui.actions;

import com.adsapient.api_impl.share.Size;

import com.adsapient.util.Msg;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class BannerSizeActionForm extends ActionForm {
    private String id = "";
    private String action = "init";
    private Collection bannerSizeCollection = null;
    private int height;
    private int width;
    private int maxsize;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(int maxsize) {
        this.maxsize = maxsize;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Collection getBannerSizeCollection() {
        return bannerSizeCollection;
    }

    public void setBannerSizeCollection(Collection bannerSizeCollection) {
        this.bannerSizeCollection = bannerSizeCollection;
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if ("add".equalsIgnoreCase(this.action) ||
                "edit".equalsIgnoreCase(this.action)) {
            if (this.width <= 0) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.required",
                        Msg.fetch("width", request)));

                return errors;
            }

            if (this.height <= 0) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.required",
                        Msg.fetch("height", request)));

                return errors;
            }

            if (this.maxsize <= 0) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.required",
                        Msg.fetch("max.banner.size", request)));

                return errors;
            }
        }

        return errors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Size getSize(Size bannersize) {
        bannersize.setHeight(height);
        bannersize.setWidth(width);
        bannersize.setMaxBannerSize(maxsize);
        bannersize.setSize(width + "x" + height);

        return bannersize;
    }

    public void init(Size bannersize) {
        this.height = bannersize.getHeight();
        this.width = bannersize.getWidth();
        this.maxsize = bannersize.getMaxBannerSize();
    }
}
