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

import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.LabelValueUtil;
import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.jsp.InputValidator;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;


public class EditPublisherSiteActionForm extends ActionForm {
    private String action = "init";
    private Integer siteId;
    private String description;
    private String url = "http://";
    private Integer userId;
    private String selectedCategorysName;
    private String categoriesPriorities;
    private Integer typeId;
    private ArrayList categorys = new ArrayList();
    private ArrayList categorys2 = new ArrayList();
    private ArrayList selectedCategorys = new ArrayList();
    private Collection categoryCollection;
    private Collection placeCollection = null;
    private String placeId;
    private Collection sitePlacesCollection = new ArrayList();
    private boolean clicksCampainAllow = false;
    private Collection typeCollection;
    private boolean ownCampaigns;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Collection getTypeCollection() {
        if (typeCollection == null) {
            typeCollection = LabelValueUtil.getResourcesTypes();
        }

        return typeCollection;
    }

    public void setTypeCollection(Collection typeCollection) {
        this.typeCollection = typeCollection;
    }

    public boolean isOwnCampaigns() {
        return ownCampaigns;
    }

    public void setOwnCampaigns(boolean ownCampaigns) {
        this.ownCampaigns = ownCampaigns;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SiteImpl getSite(SiteImpl savedSite) {
        savedSite.setDescription(description);
        savedSite.setUrl(url);
        savedSite.setTypeId(typeId);

        savedSite.setCategorys(selectedCategorysName);
        savedSite.setClicksCampainAllow(clicksCampainAllow);

        UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
                savedSite.getUserId());

        if (user.isOwnCampaignsAllow()) {
            savedSite.setOwnCampaigns(this.isOwnCampaigns());
        }

        return savedSite;
    }

    public Collection getCategoryCollection() {
        if (categoryCollection == null) {
            categoryCollection = LabelValueUtil.fillCategorysValueLabel();
        }

        return categoryCollection;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Collection getSitePlacesCollection() {
        return sitePlacesCollection;
    }

    public void setSitePlacesCollection(Collection sitePlacesCollectin) {
        this.sitePlacesCollection = sitePlacesCollectin;
    }

    public boolean isClicksCampainAllow() {
        return clicksCampainAllow;
    }

    public void setClicksCampainAllow(boolean clicksCampainAllow) {
        this.clicksCampainAllow = clicksCampainAllow;
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        InputValidator validator = new InputValidator();

        if ("loh".equalsIgnoreCase(this.url)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.login.requared"));

            return errors;
        }

        if ("add".equalsIgnoreCase(this.action) ||
                "edit".equalsIgnoreCase(this.action)) {
            if (this.typeId != 2) {
                if ((this.url == null) || (this.url.trim().length() == 0) ||
                        this.url.equals("http://")) {
                    propagateCategorys();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.resource.url.required"));

                    return errors;
                }
            }

            if ((this.description == null) ||
                    (this.description.trim().length() == 0)) {
                propagateCategorys();
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("error.resource.description.required"));

                return errors;
            }

            if (this.description.trim().length() > 500) {
                propagateCategorys();
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("",
                        "Description should not exceed 500 charecters"));

                return errors;
            }

            if (!validator.isAlphanumeric(this.description, "_., /:@&")) {
                propagateCategorys();
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphanum",
                        Msg.fetch("description", request)));

                return errors;
            }
        }

        return errors;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSelectedCategorysName() {
        return selectedCategorysName;
    }

    public void setSelectedCategorysName(String selectedCategorysName) {
        this.selectedCategorysName = selectedCategorysName;
    }

    public ArrayList getCategorys2() {
        return categorys2;
    }

    public void setCategorys2(ArrayList categorys2) {
        this.categorys2 = categorys2;
    }

    public ArrayList getSelectedCategorys() {
        return selectedCategorys;
    }

    public void setSelectedCategorys(ArrayList selectedCategorys) {
        this.selectedCategorys = selectedCategorys;
    }

    public ArrayList getCategorys() {
        return categorys;
    }

    public void setCategorys(ArrayList categorys) {
        this.categorys = categorys;
    }

    public String getCategoriesPriorities() {
        return categoriesPriorities;
    }

    public void setCategoriesPriorities(String categoriesPriorities) {
        this.categoriesPriorities = categoriesPriorities;
    }

    private void propagateCategorys() {
        Collection categoryCollection = MyHibernateUtil.viewAll(Category.class);

        Iterator iter = categoryCollection.iterator();

        while (iter.hasNext()) {
            Category category = (Category) iter.next();
            LabelValueBean bean = new LabelValueBean(category.getName(),
                    category.getId().toString());

            StringBuffer categoryStringBufer = new StringBuffer();

            categoryStringBufer.append(":").append(category.getId()).append(":");

            this.getCategorys().add(bean);
        }
    }
}
