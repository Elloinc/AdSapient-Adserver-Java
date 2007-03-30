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

import com.adsapient.shared.mappable.PlacesImpl;
import com.adsapient.shared.mappable.Category;
import com.adsapient.shared.mappable.Size;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.service.LinkHelperService;
import com.adsapient.shared.service.LabelService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.shared.service.I18nService;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public class PlacesEditActionForm extends ActionForm
    implements TargetSupportInterface {
    private static Logger logger = Logger.getLogger(PlacesEditActionForm.class);
    private String selectedCategorysName;
    private Collection SitesCollection = new ArrayList();
    private String categoriesPriorities;
    private Collection categoryCollection = null;
    private ArrayList categorys2 = new ArrayList();
    private Collection loadingTypeCollection = null;
    private Collection placeCollection = null;
    private Collection placeTypeCollection = null;
    private Collection possibleCounts = new ArrayList();
    private Collection sizesCollection;
    private boolean ownCampaigns;
    private Collection targetWindowCollection = null;
    private HttpServletRequest request;
    private String action = "view";
    private String bannerSrc;
    private Collection categorys = new ArrayList();
    private ArrayList selectedCategorys = new ArrayList();
    private String placeAction = "nothink";
    private Integer placeId;
    private Integer placesId;
    private Integer siteId;
    private Integer sizeId;
    private boolean sort = false;
    private boolean keywords = false;
    private Integer typeId;
    private String url;
    private int columnCount;
    private int loadingTypeId;
    private int placeTypeId;
    private int rowCount;
    private int targetWindowId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isKeywords() {
        return keywords;
    }

    public void setKeywords(boolean keywords) {
        this.keywords = keywords;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public String getBannerSrc() {
        LinkHelperService linkHelperService = (LinkHelperService) ContextAwareGuiBean.getContext().getBean("linkHelperService");
        if ((this.placesId != null) && (this.siteId != null)) {
            return linkHelperService.generatePlaceAdCode(placesId,
                this.request);
        } else {
            return (I18nService.fetch("htmlsource.notavailable", request));
        }
    }

    public Collection getCategoryCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        if (categoryCollection == null) {
            categoryCollection = labelService.fillCategorysValueLabel();
        }

        return categoryCollection;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public String getDemoHref() {
        StringBuffer buffer = new StringBuffer();

        if (this.getRequest() == null) {
            return buffer.toString();
        }

        if (this.getPlacesId() == null) {
            return buffer.toString();
        }

        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        PlacesImpl places = (PlacesImpl) hibernateEntityDao.loadObject(PlacesImpl.class,
                this.getPlacesId());

        Size placeSize = (Size) hibernateEntityDao.loadObject(Size.class,
                places.getSizeId());

        buffer.append("'http://").append(request.getServerName()).append(":")
              .append(request.getServerPort()).append(request.getContextPath())
              .append("/mapping2?placeId=").append(placesId).append("&")
              .append(AdsapientConstants.DEMO).append("=demo")
              .append("','_blank','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=")
              .append(placeSize.getWidth() * places.getColumnCount())
              .append(",height=")
              .append(placeSize.getHeight() * places.getRowCount())
              .append(",left=200,top=200'");

        return buffer.toString();
    }

    public Collection getLoadingTypeCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        this.loadingTypeCollection = labelService.fillTargetingCollection(AdsapientConstants.LOADING_TYPE_TYPE,
                false, this.request);

        return loadingTypeCollection;
    }

    public void setLoadingTypeId(int loadingTypeId) {
        this.loadingTypeId = loadingTypeId;
    }

    public int getLoadingTypeId() {
        return loadingTypeId;
    }

    public void setPlaceAction(String placeAction) {
        this.placeAction = placeAction;
    }

    public String getPlaceAction() {
        return placeAction;
    }

    public Collection getPlaceCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        if (placeCollection == null) {
            placeCollection = labelService.fillSitePlacesValueLabel();
        }

        return placeCollection;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public Collection getPlaceTypeCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        this.placeTypeCollection = labelService.fillTargetingCollection(AdsapientConstants.PLACE_TYPE_TYPE,
                false, this.request);

        return placeTypeCollection;
    }

    public void setPlaceTypeId(int placeTypeId) {
        this.placeTypeId = placeTypeId;
    }

    public int getPlaceTypeId() {
        return placeTypeId;
    }

    public void setPlacesId(Integer placesId) {
        this.placesId = placesId;
    }

    public Integer getPlacesId() {
        return placesId;
    }

    public Collection getPossibleCounts() {
        this.possibleCounts = LabelService.fillCustomWeightCollection(1, 10);

        return possibleCounts;
    }

    public void setRequest(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Collection getSitesCollection(HttpServletRequest request) {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        if (SitesCollection.isEmpty()) {
            SitesCollection = labelService.getSitesCollection(request);
        }

        return SitesCollection;
    }

    public Collection getSitesCollection() {
        return SitesCollection;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public Collection getSizesCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        if (sizesCollection == null) {
            sizesCollection = labelService.fillSizeValueLabel();
        }

        return sizesCollection;
    }

    public void setSort(boolean sort) {
        this.sort = sort;
    }

    public boolean isSort() {
        return sort;
    }

    public Collection getTargetWindowCollection() {
        LabelService labelService = (LabelService) ContextAwareGuiBean.getContext().getBean("labelService");
        this.targetWindowCollection = labelService.fillTargetingCollection(AdsapientConstants.TARGET_WINDOW_TYPE,
                true, request);

        return targetWindowCollection;
    }

    public void setTargetWindowId(int targetWindowId) {
        this.targetWindowId = targetWindowId;
    }

    public int getTargetWindowId() {
        return targetWindowId;
    }

    public Collection getCategorys() {
        return categorys;
    }

    public void setCategorys(Collection categorys) {
        this.categorys = categorys;
    }

    public String getSelectedCategorysName() {
        return selectedCategorysName;
    }

    public void setSelectedCategorysName(String selectedCategorysName) {
        this.selectedCategorysName = selectedCategorysName;
    }

    public void initForm(PlacesImpl places) {
        if ((places.getSiteId() != null) && (places.getSiteId() != 0)) {
            this.setSiteId(places.getSiteId());
        }

        this.setSizeId(places.getSizeId());
        this.setPlaceId(places.getPlaceId());
        this.setPlacesId(places.getId());

        this.setCategorys(extractCategorysFromString(places.getCategorys()));

        this.setTargetWindowId(places.getTargetWindowId());
        this.setLoadingTypeId(places.getLoadingTypeId());
        this.setPlaceTypeId(places.getPlaceTypeId());
        this.setRowCount(places.getRowCount());
        this.setColumnCount(places.getColumnCount());
        this.setSort(places.isSorting());
        this.setKeywords(places.isKeywords());
        this.setOwnCampaigns(places.isOwnCampaign());
        this.setCategoriesPriorities(places.getCategorys());
    }

    public void updatPlace(PlacesImpl places) {
        places.setPlaceId(placeId);
        places.setSizeId(sizeId);
        places.setSiteId(siteId);

        places.setCategorys(selectedCategorysName);
        places.setTargetWindowId(this.targetWindowId);
        places.setLoadingTypeId(loadingTypeId);
        places.setPlaceTypeId(placeTypeId);
        places.setRowCount(this.getRowCount());
        places.setColumnCount(this.getColumnCount());
        places.setSorting(this.isSort());
        places.setKeywords(this.keywords);
        places.setCreationDate(new Date());

        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);

        int userId = user.getId();

        if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
            try {
                userId = Integer.parseInt(request.getParameter("userId"));
            } catch (Exception e) {
                try {
                    userId = (Integer) request.getAttribute("userId");
                } catch (Exception e1) {
                }
            }
        }

        if (user.isOwnCampaignsAllow()) {
            places.setOwnCampaign(this.ownCampaigns);
        }

        if (AdsapientConstants.HOSTEDSERVICE.equalsIgnoreCase(user.getRole())) {
            places.setOwnCampaign(true);
        }
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
        ActionErrors errors = new ActionErrors();

        if ("view".equalsIgnoreCase(action) || "init".equalsIgnoreCase(action) ||
                "resetStatistic".equalsIgnoreCase(action)) {
            return null;
        }

        if (siteId == null) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.first.required.site.register"));
        }

        return errors;
    }

    public boolean isOwnCampaigns() {
        return ownCampaigns;
    }

    public void setOwnCampaigns(boolean ownCampaigns) {
        this.ownCampaigns = ownCampaigns;
    }

    private ArrayList extractCategorysFromString(String catsString) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        ArrayList l = new ArrayList();

        if (catsString != null) {
            String[] catIds = catsString.split(":");

            for (String catId : catIds) {
                try {
                    int catIdInt = Integer.parseInt(catId.split("-")[0]);
                    Category category = (Category) hibernateEntityDao.loadObject(Category.class,
                            catIdInt);
                    LabelValueBean bean = new LabelValueBean(category.getName(),
                            category.getId().toString());
                    l.add(bean);
                } catch (Exception ex) {
                }
            }
        }

        return l;
    }

    public ArrayList getSelectedCategorys() {
        return selectedCategorys;
    }

    public void setSelectedCategorys(ArrayList selectedCategorys) {
        this.selectedCategorys = selectedCategorys;
    }

    public ArrayList getCategorys2() {
        return categorys2;
    }

    public void setCategorys2(ArrayList categorys2) {
        this.categorys2 = categorys2;
    }

    public String getCategoriesPriorities() {
        return categoriesPriorities;
    }

    public void setCategoriesPriorities(String categoriesPriorities) {
        this.categoriesPriorities = categoriesPriorities;
    }
}
