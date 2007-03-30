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

import com.adsapient.api.TargetSupportInterface;

import com.adsapient.shared.mappable.CampainImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.*;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.shared.service.LabelService;
import com.adsapient.shared.service.I18nService;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public class EditCampainActionForm extends ActionForm
    implements TargetSupportInterface {
    private Collection banners = new ArrayList();
    private boolean ownCampaigns;
    private Collection bannersCollection = null;
    private float budget;
    private Collection loadingTypeCollection = null;
    private Collection placeTypeCollection = null;
    private Collection statesCollection = new ArrayList();
    private Collection targetWindowCollection = null;
    private HttpServletRequest hsr = null;
    private String action = "init";
    private String altText;
    private Integer bannerId;
    private Integer campainId;
    private String campainName = "";
    private String campainStateId;
    private String campainUrl;
    private String endDate = "";
    private String startDate = "";
    private String statusBartext;
    private Integer userId;
    private boolean defaultCampain = false;
    private int clicks = 0;
    private int loadingTypeId = 0;
    private int placeTypeId = 0;
    private int prioritet;
    private int targetWindowId = 0;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAltText(String alttext) {
        this.altText = alttext;
    }

    public String getAltText() {
        return altText;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBanners(Collection banners) {
        this.banners = banners;
    }

    public Collection getBanners() {
        return banners;
    }

    public CampainImpl getCampain(CampainImpl camp) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        camp.setUserId(userId);
        camp.setEndDate(endDate);
        camp.setStartDate(startDate);
        camp.setName(campainName);
        camp.setUserDefineCampainStateId(campainStateId);
        camp.setAltText(altText);
        camp.setStatusBartext(statusBartext);

        camp.setUrl(campainUrl);
        camp.setUserDefineCampainStateId(campainStateId);
        camp.setLoadingTypeId(loadingTypeId);
        camp.setPlaceTypeId(placeTypeId);
        camp.setTargetWindowId(targetWindowId);

        UserImpl user = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
                camp.getUserId());

        camp.setBudget(FinancialsService.convert2Money(this.getBudget()));

        if (user.isOwnCampaignsAllow()) {
            camp.setOwnCampaigns(this.ownCampaigns);
        }

        if (AdsapientConstants.HOSTEDSERVICE.equalsIgnoreCase(user.getRole())) {
            camp.setOwnCampaigns(true);
        }

        return camp;
    }

    public void setCampainId(Integer campainId) {
        this.campainId = campainId;
    }

    public Integer getCampainId() {
        return campainId;
    }

    public void setCampainName(String campainName) {
        this.campainName = campainName;
    }

    public String getCampainName() {
        return campainName;
    }

    public void setCampainStateId(String campainStateId) {
        this.campainStateId = campainStateId;
    }

    public String getCampainStateId() {
        return campainStateId;
    }

    public void setCampainUrl(String campainUrl) {
        this.campainUrl = campainUrl;
    }

    public String getCampainUrl() {
        return campainUrl;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getClicks() {
        return clicks;
    }

    public void setDefaultCampain(boolean defaultCampain) {
        this.defaultCampain = defaultCampain;
    }

    public boolean isDefaultCampain() {
        return defaultCampain;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setHsr(HttpServletRequest req) {
        this.hsr = req;
    }

    public HttpServletRequest getHsr() {
        return this.hsr;
    }

    public Collection getLoadingTypeCollection() {
        LabelService labelService = (LabelService)ContextAwareGuiBean.getContext().getBean("labelService");
        this.loadingTypeCollection = labelService.fillTargetingCollection(AdsapientConstants.LOADING_TYPE_TYPE,
                true, this.hsr);

        return loadingTypeCollection;
    }

    public String generateCampainStates() {
        return LinkHelperService.generateAdsapientOptionsCollection("campainStateId",
            "defType", this.getStatesCollection());
    }

    public void setLoadingTypeId(int loadingTypeId) {
        this.loadingTypeId = loadingTypeId;
    }

    public int getLoadingTypeId() {
        return loadingTypeId;
    }

    public Collection getPlaceTypeCollection() {
        LabelService labelService = (LabelService)ContextAwareGuiBean.getContext().getBean("labelService");
        this.placeTypeCollection = labelService.fillTargetingCollection(AdsapientConstants.PLACE_TYPE_TYPE,
                true, this.hsr);

        return placeTypeCollection;
    }

    public void setPlaceTypeId(int placeTypeId) {
        this.placeTypeId = placeTypeId;
    }

    public int getPlaceTypeId() {
        return placeTypeId;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    public int getPrioritet() {
        return prioritet;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public Collection getStatesCollection() {
        LabelService labelService = (LabelService)ContextAwareGuiBean.getContext().getBean("labelService");
        if (statesCollection.isEmpty()) {
            statesCollection = labelService.fillUserDefineCampainStatesValueLabel(this);
        }

        return statesCollection;
    }

    public void setStatusBartext(String statusbartext) {
        this.statusBartext = statusbartext;
    }

    public String getStatusBartext() {
        return statusBartext;
    }

    public Collection getTargetWindowCollection() {
        LabelService labelService = (LabelService)ContextAwareGuiBean.getContext().getBean("labelService");
        this.targetWindowCollection = labelService.fillTargetingCollection(AdsapientConstants.TARGET_WINDOW_TYPE,
                true, hsr);

        return targetWindowCollection;
    }

    public void setTargetWindowId(int targetWindowId) {
        this.targetWindowId = targetWindowId;
    }

    public int getTargetWindowId() {
        return targetWindowId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void init(CampainImpl tempCampain) {
        this.campainId = tempCampain.getCampainId();

        this.budget = FinancialsService.transformMoney(tempCampain.getBudget());
        this.userId = tempCampain.getUserId();
        this.startDate = tempCampain.getStartDate();
        this.endDate = tempCampain.getEndDate();

        this.banners = tempCampain.getBanners();

        this.campainName = tempCampain.getName();

        this.campainStateId = tempCampain.getUserDefineCampainStateId();
        this.prioritet = tempCampain.getPrioritet();

        this.campainUrl = tempCampain.getUrl();

        this.statusBartext = tempCampain.getStatusBartext();
        this.altText = tempCampain.getAltText();

        this.setLoadingTypeId(tempCampain.getLoadingTypeId());
        this.setPlaceTypeId(tempCampain.getLoadingTypeId());
        this.setTargetWindowId(tempCampain.getTargetWindowId());
        this.ownCampaigns = tempCampain.isOwnCampaigns();
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        ValidationService validator = new ValidationService();

        if ("add".equalsIgnoreCase(this.action) ||
                "edit".equalsIgnoreCase(this.action)) {
            if ((campainName == null) || (campainName.trim().length() == 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("error.campain.name.required"));

                return errors;
            }

            if (!validator.isAlphanumeric(campainName, "_., /:@&")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphanum",
                        I18nService.fetch("campaign.name", request)));

                return errors;
            }

            Date stDate;
            Date edDate;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                stDate = sdf.parse(this.getStartDate());
                edDate = sdf.parse(this.getEndDate());

                if (edDate.before(stDate)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("end.date.invalid"));

                    return errors;
                }
            } catch (Exception e) {
                System.out.println("EndDate Exception " + e);
            }
        }

        return errors;
    }

    public boolean isOwnCampaigns() {
        return ownCampaigns;
    }

    public void setOwnCampaigns(boolean ownCampaigns) {
        this.ownCampaigns = ownCampaigns;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
}
