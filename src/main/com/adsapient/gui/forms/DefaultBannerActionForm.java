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

import com.adsapient.shared.service.LabelService;
import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.service.ValidationService;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class DefaultBannerActionForm extends ActionForm {
    private Collection banners = new ArrayList();
    private Collection typesCollection = null;
    private FormFile file;
    private String URL = "";
    private String action = "init";
    private String altText = "";
    private int maxBannerSize;
    private Collection sizesCollection = null;
    private Integer sizeId;
    private boolean createAllowed = true;
    private String fileName = "";
    private String id;
    private String sizeName;
    private String statusBartext = "";
    private int height;
    private Integer typeId;
    private int width;

    public boolean isCreateAllowed() {
        return createAllowed;
    }

    public void setCreateAllowed(boolean createAllowed) {
        this.createAllowed = createAllowed;
    }

    public int getMaxBannerSize() {
        return maxBannerSize;
    }

    public void setMaxBannerSize(int maxBannerSize) {
        this.maxBannerSize = maxBannerSize;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getAltText() {
        return altText;
    }

    public void setBanners(Collection banners) {
        this.banners = banners;
    }

    public Collection getBanners() {
        return banners;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setStatusBartext(String statusBartext) {
        this.statusBartext = statusBartext;
    }

    public String getStatusBartext() {
        return statusBartext;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Collection getTypesCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        if (typesCollection == null) {
            typesCollection = labelService.fillTypeValueLabel();
        }

        return typesCollection;
    }

    public void setURL(String url) {
        URL = url;
    }

    public String getURL() {
        return URL;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
        ActionErrors errors = new ActionErrors();
        ValidationService validator = new ValidationService();

        if ("edit".equalsIgnoreCase(this.getAction())) {
            if (!validator.isAlphanumeric(this.altText, "_., /:@&")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphanum",
                        I18nService.fetch("alttext", arg1)));

                return errors;
            }

            if (!validator.isAlphanumeric(this.statusBartext, "_., /:@&")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphanum",
                        I18nService.fetch("statusbartext", arg1)));

                return errors;
            }

            if ((this.URL == null) || (this.URL.trim().length() == 0) ||
                    this.URL.equals("http://")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.url.required"));

                return errors;
            }

            if (!validator.isAlphanumeric(this.URL, "_., /:@&")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphanum",
                        I18nService.fetch("banner.url", arg1)));

                return errors;
            }

            if ((this.file == null) || (this.file.getFileName().length() == 0)) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.required",
                        I18nService.fetch("banner.file", arg1)));

                return errors;
            }

            if (this.file.getFileSize() > this.maxBannerSize) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("big.file.size"));

                return errors;
            }
        }

        return errors;
    }

    public Collection getSizesCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        if (sizesCollection == null) {
            sizesCollection = labelService.fillSizeValueLabel();
        }

        return sizesCollection;
    }

    public void setSizesCollection(Collection sizesCollection) {
        this.sizesCollection = sizesCollection;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }
}
