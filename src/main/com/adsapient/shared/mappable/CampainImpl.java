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

import com.adsapient.api.*;
import com.adsapient.gui.forms.EditCampainActionForm;
import com.adsapient.gui.ContextAwareGuiBean;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.service.*;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.hibernate.HibernateException;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

public class CampainImpl extends VerifyingTargetSupportElement implements
        RateEnableInterface, NameSupportInterface, Campain,
        AdsapientInterceptedSupportEntity, IMappable {
    protected static final long serialVersionUID = 1L;

    protected static Logger logger = Logger.getLogger(CampainImpl.class);

    protected Integer budget = new Integer(0);

    protected List<FilterInterface> filters;

    protected Integer campainId;

    protected Integer rateId;

    protected Integer userId = new Integer(0);

    protected RateImpl rate = null;

    protected Set<BannerImpl> banners = new HashSet<BannerImpl>();

    protected String altText = "";

    protected String endDate;

    protected String name;

    protected String startDate;

    protected String statusBartext = "";

    protected String url = "http://";

    protected String userDefineCampainStateId;

    protected boolean ownCampaigns = false;

    protected int prioritet = 10;

    public CampainImpl() {
        super();

        SimpleDateFormat sdf = new SimpleDateFormat(
                AdsapientConstants.DATE_FORMAT);

        this.startDate = sdf.format(Calendar.getInstance().getTime());

        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.add(Calendar.WEEK_OF_MONTH, 4);
        this.endDate = sdf.format(endDateCalendar.getTime());

        this.userDefineCampainStateId = UserDefineCampainStates.LIVE;

        this.setCampainId(new Integer(0));
    }

    public CampainImpl(HttpServletRequest request) {
        super(request);

        SimpleDateFormat sdf = new SimpleDateFormat(
                AdsapientConstants.DATE_FORMAT);

        this.startDate = sdf.format(Calendar.getInstance().getTime());

        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.add(Calendar.WEEK_OF_MONTH, 4);
        this.endDate = sdf.format(endDateCalendar.getTime());

        this.userDefineCampainStateId = UserDefineCampainStates.LIVE;

        this.setCampainId(new Integer(0));
    }

    public Integer getActualCampaignCost() {
        return 0;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getAltText() {
        return altText;
    }

    public Set<BannerImpl> getBanners() {
        return banners;
    }

    public void setBanners(Set<BannerImpl> banners) {
        this.banners = banners;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setCampainId(Integer campainId) {
        this.campainId = campainId;
    }

    public Integer getCampainId() {
        return this.campainId;
    }

    public String getCampainState(HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        UserDefineCampainStates state = (UserDefineCampainStates) hibernateEntityDao
                .loadObject(UserDefineCampainStates.class, this
                        .getUserDefineCampainStateId());

        return I18nService.fetch(state.getUserDefineCampainState(), request);
    }

    public boolean isContainRelevantSizeBanner(PlacesImpl places,
                                               Collection usedBannersCollection) {
        boolean findBanner = false;
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");

        if (this.budget.intValue() != 0) {
            if (this.getActualCampaignCost().intValue() >= this.getBudget()
                    .intValue()) {
                return false;
            }
        }

        if ((banners != null) && (!banners.isEmpty())) {
            Iterator bannersIterator = banners.iterator();

            while (bannersIterator.hasNext()) {
                BannerImpl banner = (BannerImpl) bannersIterator.next();

                if (banner.isSuitable(places, usedBannersCollection)) {
                    findBanner = true;
                }
            }
        }

        if (findBanner) {
            Account advertiserAccount = (Account) hibernateEntityDao
                    .loadObjectWithCriteria(Account.class, "userId", this
                            .getUserId());

            if (advertiserAccount.getMoney().intValue() <= 0) {
                UserImpl user = (UserImpl) hibernateEntityDao.loadObject(
                        UserImpl.class, this.getUserId());

                if (AdsapientConstants.HOSTEDSERVICE.equalsIgnoreCase(user
                        .getRole())) {
                    return true;
                }

                return false;
            }

            return true;
        }

        return false;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOwnCampaigns(boolean ownCampaigns) {
        this.ownCampaigns = ownCampaigns;
    }

    public boolean isOwnCampaigns() {
        return ownCampaigns;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    public int getPrioritet() {
        return prioritet;
    }

    public void setRate(RateImpl rate) {
        this.rate = rate;
    }

    public RateImpl getRate() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        if (this.getRateId() == null) {
            return null;
        }

        if (this.rate == null) {
            this.rate = (RateImpl) hibernateEntityDao.loadObject(RateImpl.class,
                    rateId);
        }

        return this.rate;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public Integer getRateId() {
        return rateId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStatusBartext(String statusBartext) {
        this.statusBartext = statusBartext;
    }

    public String getStatusBartext() {
        return statusBartext;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUserDefineCampainStateId(String userDefineCampainStateId) {
        this.userDefineCampainStateId = userDefineCampainStateId;
    }

    public String getUserDefineCampainStateId() {
        return userDefineCampainStateId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public boolean addCheck(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;

        if (form != null) {
            form.setAction("init");
        }

        return super.addCheck(actionForm, request);
    }

    public boolean deleteCheck(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;

        if (form != null) {
            form.setAction("init");
        }

        return super.deleteCheck(actionForm, request);
    }

    public boolean editCheck(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;

        if (form != null) {
            form.setAction("init");
        }

        return super.editCheck(actionForm, request);
    }

    public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession().getAttribute(
                AdsapientConstants.USER);
        Integer campainId;

        form.getCampain(this);

        RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        ratesService.createCampainRate(this, user);

        campainId = new Integer((Integer) hibernateEntityDao.save(this));

        FiltersService filtersService = (FiltersService) ContextAwareGuiBean.getContext().getBean("filtersService");
        Iterator campainIterator = filtersService.createFakeFilters()
                .getFiltersMap().entrySet().iterator();

        while (campainIterator.hasNext()) {
            Map.Entry filterEntry = (Map.Entry) campainIterator.next();

            FilterInterface filter = (FilterInterface) filterEntry.getValue();

            filter.setCampainId(campainId);

            filter.addNew();
        }

        return true;
    }

    public boolean resultDelete(ActionForm actionForm,
                                HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        UploadService uploadService = (UploadService) ContextAwareGuiBean.getContext().getBean("uploadService");

        FiltersService filtersService = (FiltersService) ContextAwareGuiBean.getContext().getBean("filtersService");
        filtersService.removeAllFiltersForGivenCampain(campainId);

        Iterator bannersIterator = this.getBanners().iterator();

        while (bannersIterator.hasNext()) {
            BannerImpl banner = (BannerImpl) bannersIterator.next();

            uploadService.removeBanner(banner.getBannerId());
        }


        hibernateEntityDao.removeObject(CampainImpl.class, campainId);
        hibernateEntityDao.removeObject(RateImpl.class, this.getRateId());

        return true;
    }

    public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        try {
            CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(
                    CampainImpl.class, this.getCampainId());
            form.getCampain(campain);
            hibernateEntityDao.updateObject(campain);

            updateReporterDB(campain);
        } catch (HibernateException ex) {
            logger.error("Exeption in edit campain ", ex);
        }

        return true;
    }

    public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession().getAttribute(
                AdsapientConstants.USER);

        form.setAction("add");
        form.init(this);
        form.setUserId(user.getId());

        return true;
    }

    public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
        EditCampainActionForm form = (EditCampainActionForm) actionForm;
        form.init(this);
        form.setAction("edit");

        return false;
    }

    public void udateBanner(Banner banner) {
        banner.setCampainId(this.campainId);

        if ((banner.getUserId() != null)
                && ((banner.getStatus() != BannerImpl.STATUS_DEFAULT) && (banner
                .getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT))) {
            banner.setUserId(this.userId);
        }

        banner.setStartDate(this.startDate);
        banner.setEndDate(this.endDate);
    }

    public Integer getId() {
        return campainId;
    }

    public List<FilterInterface> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterInterface> filters) {
        this.filters = filters;
    }

    private void updateReporterDB(CampainImpl campain) {
        try {
        } catch (Exception ex) {
            logger.info("Reporter SQL Exception " + ex.getMessage());
        }
    }
}
