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

import com.adsapient.api_impl.resource.ResourceImpl;

import com.adsapient.util.LabelValueUtil;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class ResourceUploadForm extends ActionForm {
    private static final long serialVersionUID = 3761408603491284017L;
    static Logger logger = Logger.getLogger(ResourceUploadForm.class);
    private Collection typesCollection = null;
    private Collection userResources = new ArrayList();
    private FormFile theFile;
    private int resSize;
    private String URL;
    private String action = "init";
    private String contentType;
    private String file;
    private String orderId = "resourceId";
    private String resourceId;
    private String resourceName = "";
    private String userId;
    private Integer typeId;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setResSize(int resSize) {
        this.resSize = resSize;
    }

    public int getResSize() {
        return resSize;
    }

    public ResourceImpl getResource(ResourceImpl res) {
        res.setFile(file);

        res.setTypeId(typeId);
        res.setUserId(new Integer(userId));
        res.setContentType(contentType);
        res.setResourceName(resourceName);

        if (URL != null) {
            res.setURL(URL);
        }

        return res;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }

    public FormFile getTheFile() {
        return theFile;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Collection getTypesCollection() {
        if (typesCollection == null) {
            typesCollection = LabelValueUtil.fillTypeValueLabel();
        }

        return typesCollection;
    }

    public void setURL(String url) {
        URL = url;
    }

    public String getURL() {
        return URL;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserResources(Collection userResources) {
        this.userResources = userResources;
    }

    public Collection getUserResources() {
        return userResources;
    }

    public void init(ResourceImpl res) {
        this.file = res.getFile();
        this.contentType = res.getContentType();
        this.resourceId = res.getResourceId().toString();
        this.resourceName = res.getResourceName();
        this.resSize = res.getResSize();
        this.userId = res.getUserId().toString();
        this.typeId = res.getTypeId();
        this.URL = res.getURL();
        file = res.getFile();
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
        ActionErrors errors = new ActionErrors();

        if ("add".equalsIgnoreCase(this.action)) {
            if ((this.theFile == null) || (this.theFile.getFileSize() == 0)) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("resource.file.must.spespecific"));
            }
        }

        return errors;
    }
}
