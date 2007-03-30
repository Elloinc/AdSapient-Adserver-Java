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

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.IMappable;
import com.adsapient.gui.ContextAwareGuiBean;
import com.adsapient.gui.forms.FilterActionForm;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.service.FiltersService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContentFilter implements FilterInterface, IMappable {
    static Logger logger = Logger.getLogger(ContentFilter.class);

    private static final String relatedFiltersQueryName = "getRelatedContentFilterIds";

    private Integer bannerId;

    private Integer campainId;

    private String categorys;

    private Integer contentFilterId;

    private String places;

    private String positions;

    private boolean useAllPlaces = true;

    private List<String> categoriesList;

    public boolean isApplyFilter(ContentFilter contFilter) {

        return true;
    }

    public void setCampainId(Integer campainId) {
        this.campainId = campainId;
    }

    public Integer getCampainId() {
        return campainId;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getCategorys() {
        if (categorys == null) {
            categorys = FiltersService.getCategorysAllKeys();
        }
        return categorys;
    }

    public Integer getContentFilterId() {
        return contentFilterId;
    }

    public void setContentFilterId(Integer contentFilterId) {
        this.contentFilterId = contentFilterId;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getPlaces() {
        if (places == null) {
            places = FiltersService.getPlacesAllKeys();
        }
        return places;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getPositions() {
        if (positions == null) {
            positions = FiltersService.getPositionsAllKeys();
        }
        return positions;
    }

    public void setUseAllPlaces(boolean useAllPlaces) {
        this.useAllPlaces = useAllPlaces;
    }

    public boolean isUseAllPlaces() {
        return useAllPlaces;
    }

    public void add(FilterInterface filter) {
        if (filter instanceof ContentFilter) {
            HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
            ContentFilter contFilterTemplate = (ContentFilter) filter;

            StringBuffer resultCategorysBuffer = new StringBuffer();
            StringBuffer resultPositionsBuffer = new StringBuffer();
            StringBuffer resultPlacesBuffer = new StringBuffer();

            Collection categorysCollection = hibernateEntityDao
                    .viewAll(Category.class);

            if (categorysCollection != null) {
                Iterator categorysIter = categorysCollection.iterator();

                while (categorysIter.hasNext()) {
                    Category category = (Category) categorysIter.next();
                    StringBuffer strBuffer = new StringBuffer();
                    strBuffer.append(":").append(category.getId().toString())
                            .append(":");

                    if ((contFilterTemplate.getCategorys().indexOf(
                            strBuffer.toString()) > -1)
                            | (this.categorys.indexOf(strBuffer.toString()) > -1)) {
                        resultCategorysBuffer.append(strBuffer.toString());
                    }
                }
            }

            this.categorys = resultCategorysBuffer.toString();

            Collection positionsCollection = hibernateEntityDao
                    .viewAll(PlaceImpl.class);

            for (Iterator positionsIterator = positionsCollection.iterator(); positionsIterator
                    .hasNext();) {
                PlaceImpl currentPlace = (PlaceImpl) positionsIterator.next();

                StringBuffer placeIndex = new StringBuffer();
                placeIndex.append(":").append(currentPlace.getPlaceId())
                        .append(":");

                if ((this.positions.indexOf(placeIndex.toString()) > -1)
                        || ((contFilterTemplate.getPositions()
                        .indexOf(placeIndex.toString())) > -1)) {
                    resultPositionsBuffer.append(placeIndex);
                }
            }

            this.positions = resultPositionsBuffer.toString();

            Collection placesCollection = (hibernateEntityDao
                    .viewAll(PlacesImpl.class));

            for (Iterator placesIterator = placesCollection.iterator(); placesIterator
                    .hasNext();) {
                PlacesImpl currentPlace = (PlacesImpl) placesIterator.next();

                StringBuffer placeIndex = new StringBuffer();
                placeIndex.append(":").append(currentPlace.getId()).append(":");

                if ((this.places.indexOf(placeIndex.toString()) > -1)
                        || ((contFilterTemplate.getPlaces().indexOf(placeIndex
                        .toString())) > -1)) {
                    resultPlacesBuffer.append(currentPlace);
                }
            }

            this.places = resultPlacesBuffer.toString();
        } else {
            logger.warn("given object isnt instance of contentfilter");
        }
    }

    public void addNew() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        hibernateEntityDao.save(this);
    }

    public Object copy() {
        ContentFilter newContentFilter = new ContentFilter();

        newContentFilter.setCategorys(this.getCategorys());
        newContentFilter.setPlaces(this.getPlaces());
        newContentFilter.setPositions(this.getPositions());
        newContentFilter.setUseAllPlaces(this.useAllPlaces);

        return newContentFilter;
    }

    public ContentFilter clone() throws CloneNotSupportedException {
        ContentFilter filter = (ContentFilter) super.clone();

        return filter;
    }

    public void doAfterFilter(Banner banner, PlacesImpl places,
                              StatisticImpl statistic, Map requestMap) {
    }

    public boolean doFilter(HttpServletRequest request) {
        return true;
    }

    public void init(HttpServletRequest request, ActionForm actionForm) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        FilterActionForm form = (FilterActionForm) actionForm;

        Collection categoryCollection = hibernateEntityDao.viewAll(Category.class);

        Iterator iter = categoryCollection.iterator();

        while (iter.hasNext()) {
            Category category = (Category) iter.next();
            LabelValueBean bean = new LabelValueBean(category.getName(),
                    category.getId().toString());

            StringBuffer categoryStringBufer = new StringBuffer();

            categoryStringBufer.append(":").append(category.getId()).append(
                    "-0").append(":");

            if (this.getCategorys().indexOf(":" + category.getId() + "-") > -1) {
                form.getCategorys2().add(bean);
            }

            form.getCategorys().add(bean);
        }

        form.setCategoriesPriorities(this.getCategorys());

        Collection pagePositionsCollection = hibernateEntityDao
                .viewAll(PlaceImpl.class);

        Iterator pagePositionIterator = pagePositionsCollection.iterator();

        while (pagePositionIterator.hasNext()) {
            PlaceImpl currentPlace = (PlaceImpl) pagePositionIterator.next();

            LabelValueBean placeBean = new LabelValueBean(currentPlace
                    .getName(), currentPlace.getPlaceId().toString());

            StringBuffer positionsStringBufer = new StringBuffer();
            positionsStringBufer.append(":").append(currentPlace.getPlaceId())
                    .append(":");

            if (this.getPositions().indexOf(positionsStringBufer.toString()) > -1) {
                form.getPagePositionsCollection2().add(placeBean);
            }

            form.getPagePositionsCollection1().add(placeBean);
        }

        form.setRegisteredPlaces(this.getPlaces());

        if (this.bannerId != null) {
            form.setBannerId(this.bannerId.toString());
        }

        form.setAllPlaces(this.useAllPlaces);
        form.setFilterAction("update");
    }

    public void reset() {
        this.categorys = FiltersService.getCategorysAllKeys();
        this.positions = FiltersService.getPositionsAllKeys();
        this.useAllPlaces = true;
    }

    public String save() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        return String.valueOf(hibernateEntityDao.save(this));
    }

    public void update(HttpServletRequest request, ActionForm actionForm,
                       boolean enableHibernateUpdate) {
        FilterActionForm form = (FilterActionForm) actionForm;

        this.setCategorys(form.getSelectedCategorysName());

        this.setPositions(form.getSelectedPositions());

        this.setPlaces(form.getRegisteredPlaces());

        this.setUseAllPlaces(form.isAllPlaces());

        if (enableHibernateUpdate) {
            HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
            hibernateEntityDao.updateObject(this);
        }
    }

    public Integer getId() {
        return contentFilterId;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getRelatedFiltersQueryName() {
        return relatedFiltersQueryName;
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<String> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
