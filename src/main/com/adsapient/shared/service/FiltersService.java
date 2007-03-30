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
package com.adsapient.shared.service;

import com.adsapient.api.AdsapientException;
import com.adsapient.api.FilterInterface;
import com.adsapient.gui.ContextAwareGuiBean;
import com.adsapient.gui.forms.FilterActionForm;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.*;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class FiltersService {
    static Logger logger = Logger.getLogger(FiltersService.class);
    private CookieManagementService cookieManagementService;

    private HibernateEntityDao hibernateEntityDao;
    private Map filtersMap = new LinkedMap();
    private static Map campainImpressions = Collections
            .synchronizedMap(new HashMap());
    private static Map campainClicks = Collections
            .synchronizedMap(new HashMap());
    private static TreeMap<String, String> mapCountries = new TreeMap<String, String>();
    public static String[] countryIndexArray = null;
    public static Map<String, String> BrowsersMap = new TreeMap<String, String>();
    public static Map<String, String> SystemsMap = new TreeMap<String, String>();
    public static Map<String, String> LangsMap = new TreeMap<String, String>();

    public int getExcess(FilterActionForm form) {
        BannerImpl banner = (BannerImpl) hibernateEntityDao.loadObject(
                BannerImpl.class, Integer.valueOf(form.getBannerId()));
        Object[] criteria = new Object[]{"campainId", banner.getCampainId()};
        BannerImpl[] banners = (BannerImpl[]) hibernateEntityDao
                .viewWithCriteria(BannerImpl.class, criteria).toArray(
                new BannerImpl[]{});
        int sum = 0;

        for (BannerImpl b : banners) {
            if (b.getId().equals(banner.getId())) {
                continue;
            }

            criteria = new Object[]{"bannerId", b.getId()};

            Collection c = hibernateEntityDao.viewWithCriteria(
                    TrafficFilter.class, criteria);

            if ((c == null) || (c.size() == 0)) {
                continue;
            }

            TrafficFilter trafficFilter = (TrafficFilter) c.toArray()[0];
            sum += ((trafficFilter.getTrafficShare() == null) ? 0
                    : trafficFilter.getTrafficShare());
        }

        sum += Integer.valueOf(form.getTrafficShare());

        return sum - 100;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public synchronized FiltersService createCampinFilters(
            Integer campainId, Integer bannerId) {
        return constructCampainFilters(campainId, bannerId);
    }

    public synchronized FiltersService createFakeFilters() {
        return constructCampainFilters();
    }

    public synchronized FiltersService createTemplateFilters(
            String templateId) {
        return constructCampainFilters(templateId);
    }

    public static CampainImpl doFiliter(CampainImpl campain,
                                        HttpServletRequest request, PlacesImpl places,
                                        Collection bannersCollectin) {
        if (campain == null) {
            return null;
        }

        if (!campain.isContainRelevantSizeBanner(places, bannersCollectin)) {
            return null;
        }

        if (applyFilters(request, campain)) {
            return campain;
        } else {
            return null;
        }
    }

    private static boolean applyFilters(HttpServletRequest request,
                                        CampainImpl campain) {
        FiltersService filters = null;

        Iterator filtersIterator = filters.getFiltersMap().entrySet()
                .iterator();

        while (filtersIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) filtersIterator.next();

            FilterInterface filter = (FilterInterface) entry.getValue();

            if (!filter.doFilter(request)) {
                return false;
            }
        }

        logger.debug("Camapin with id=" + campain.getCampainId()
                + " do filter check");

        return true;
    }

    public static String getCategorysAllKeys() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection categoryCollection = hibernateEntityDao.viewAll(Category.class);

        Iterator categorysIterator = categoryCollection.iterator();

        StringBuffer resultString = new StringBuffer();

        while (categorysIterator.hasNext()) {
            Category category = (Category) categorysIterator.next();
            resultString.append(":").append(category.getId()).append("-0")
                    .append(":");
        }

        return resultString.toString();
    }

    public static String getPositionsAllKeys() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection categoryCollection = hibernateEntityDao
                .viewAll(PlaceImpl.class);

        Iterator categorysIterator = categoryCollection.iterator();

        StringBuffer resultString = new StringBuffer();

        while (categorysIterator.hasNext()) {
            PlaceImpl place = (PlaceImpl) categorysIterator.next();
            resultString.append(":").append(place.getPlaceId()).append(":");
        }

        return resultString.toString();
    }

    public static String getPlacesAllKeys() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection placesCollection = hibernateEntityDao.viewAll(PlacesImpl.class);
        StringBuffer resultBuffer = new StringBuffer();

        for (Iterator placesIterator = placesCollection.iterator(); placesIterator
                .hasNext();) {
            PlacesImpl currentPlaces = (PlacesImpl) placesIterator.next();
            resultBuffer.append(":").append(currentPlaces.getId()).append(":");
        }

        return resultBuffer.toString();
    }

    public static int getClicksForCampain(Integer campainId,
                                          boolean increaseCache, boolean isImpression) {
        int campainClicksLocal = 0;

        if (campainClicks.containsKey(campainId)) {
            campainClicksLocal = ((Integer) campainClicks.get(campainId))
                    .intValue();
        } else {
            Collection parametersCollection = new ArrayList();


            campainClicks.put(campainId, new Integer(campainClicksLocal));
        }

        if (increaseCache && !isImpression) {
            campainClicksLocal++;
            campainClicks.put(campainId, new Integer(campainClicksLocal));
        }

        return campainClicksLocal;
    }

    public static int getClicksForCampainInDay(Integer campainId,
                                               boolean increaseCache, boolean isImpression) {
        StringBuffer stringBuffer = new StringBuffer();

        Calendar beginDate = Calendar.getInstance();

        int campainImpressionsLocal = 0;
        String key = new StringBuffer("campain").append(campainId)
                .append("day").append(beginDate.get(Calendar.DAY_OF_YEAR))
                .toString();

        if (campainClicks.containsKey(key)) {
            campainImpressionsLocal = ((Integer) campainClicks.get(key))
                    .intValue();
        } else {
            beginDate.add(Calendar.DAY_OF_YEAR, -1);

            Collection parametersCollection = new ArrayList();


            campainClicks.put(key, new Integer(campainImpressionsLocal));
        }

        if (increaseCache && !isImpression) {
            campainImpressionsLocal++;
            campainClicks.put(key, new Integer(campainImpressionsLocal));
        }

        return campainImpressionsLocal;
    }

    public static int getCustomPeriodClick(Map requestMap, boolean unique,
                                           Integer campainId, int hour, int day, boolean increaseCache,
                                           boolean isImpression) {
        return 0;
    }

    public static int getCustomPeriodImpressions(Map requestMap,
                                                 boolean unique, Integer campainId, int hour, int day,
                                                 boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static int getImpressionsForCampain(Integer campainId,
                                               boolean increaseCache, boolean isImpression) {
        int campainImpressionsLocal = 0;

        if (campainImpressions.containsKey(campainId)) {
            campainImpressionsLocal = ((Integer) campainImpressions
                    .get(campainId)).intValue();
        } else {
            Collection parametersCollection = new ArrayList();


            campainImpressions.put(campainId, new Integer(
                    campainImpressionsLocal));
        }

        if (increaseCache && isImpression) {
            campainImpressionsLocal++;
            campainImpressions.put(campainId, new Integer(
                    campainImpressionsLocal));
        }

        return campainImpressionsLocal;
    }

    public static int getImpressionsForCampainInDay(Integer campainId,
                                                    boolean increaseCache, boolean isImpression) {
        StringBuffer stringBuffer = new StringBuffer();

        Calendar beginDate = Calendar.getInstance();

        int campainImpressionsLocal = 0;
        String key = new StringBuffer("campain").append(campainId)
                .append("day").append(beginDate.get(Calendar.DAY_OF_YEAR))
                .toString();

        if (campainImpressions.containsKey(key)) {
            campainImpressionsLocal = ((Integer) campainImpressions.get(key))
                    .intValue();
        } else {
            beginDate.add(Calendar.DAY_OF_YEAR, -1);

            Collection parametersCollection = new ArrayList();


            campainImpressions.put(key, new Integer(campainImpressionsLocal));
        }

        if (increaseCache && isImpression) {
            campainImpressionsLocal++;
            campainImpressions.put(key, new Integer(campainImpressionsLocal));
        }

        return campainImpressionsLocal;
    }

    public static int getUniqueClicksForCampain(Integer campainId,
                                                Map requestMap, boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static int getUniqueClicksForCampainInDay(Integer campainId,
                                                     Map requestMap, boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static int getUniqueClicksForCampainInMonth(Integer campainId,
                                                       Map requestMap, boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static int getUniqueImpressionsForCampain(Integer campainId,
                                                     Map requestMap, boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static int getUniqueImpressionsForCampainInDay(Integer campainId,
                                                          Map requestMap, boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static int getUniqueImpressionsForCampainInMonth(Integer campainId,
                                                            Map requestMap, boolean increaseCache, boolean isImpression) {
        return 0;
    }

    public static void reload() {
        campainImpressions.clear();
        campainClicks.clear();
    }

    public static String getAllCoutryIndexes() {
        StringBuffer sb = new StringBuffer();

        if (countryIndexArray != null) {
            for (int i = 0; i < countryIndexArray.length; i++)
                sb.append(countryIndexArray[i]).append(",");
        }

        return sb.toString();
    }

    public static String getCountryNameByCode(String countryCode) {
        return mapCountries.get(countryCode);
    }

    public void removeAllFiltersForGivenCampain(Integer campainId) {
        FiltersService campainFilters = createCampinFilters(
                campainId, null);

        Iterator campainFiltersIterator = campainFilters.getFiltersMap()
                .entrySet().iterator();

        while (campainFiltersIterator.hasNext()) {
            Map.Entry element = (Map.Entry) campainFiltersIterator.next();
            FilterInterface filter = (FilterInterface) element.getValue();

            if (filter != null) {
                hibernateEntityDao.removeObject(element.getValue());
            } else {
                logger.warn("in remove campain filters filter is null  it key="
                        + element.getKey());
            }
        }
    }

    public void removeAllFiltersForGivenBanner(Integer bannerId) {
        Collection filters = hibernateEntityDao.viewAll(SystemFilter.class);
        Iterator filtersIterator = filters.iterator();
        while (filtersIterator.hasNext()) {
            SystemFilter filterElement = (SystemFilter) filtersIterator.next();
            Class filterClass = null;
            try {
                filterClass = Class.forName(filterElement.getFilterClass());
            } catch (ClassNotFoundException e) {
                logger.error("error in campain filters constructor:", e);
            }
            try {
                Object obj = hibernateEntityDao.loadObjectWithCriteria(filterClass, "bannerId", bannerId);
                hibernateEntityDao.removeObject(obj);
            } catch (Exception e) {
                logger.error("cannot delete filter", e);
            }
        }
    }

    public static void fillCountryMap(List<CountryAbbrEntity> countries) {
        if ((countries == null) || (countries.size() == 0)) {
            logger.warn("Country list is empty");
            mapCountries = new TreeMap<String, String>();
            countryIndexArray = new String[0];

            return;
        }

        ArrayList<String> list = new ArrayList<String>();

        try {
            TreeMap<String, String> map = new TreeMap<String, String>();
            int i = 0;

            for (CountryAbbrEntity country : countries) {
                String key = country.getCountryAbbr2();
                map.put(key, country.getCountryName());

                if (key
                        .equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS)
                        || key
                        .equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_RESERVED)
                        || key
                        .equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND)) {
                    list.add(0, key);
                } else {
                    list.add(key);
                }
            }

            mapCountries = map;

            countryIndexArray = new String[list.size()];
            countryIndexArray = list.toArray(countryIndexArray);
        } catch (Exception e) {
            logger.error("Error getting Countries", e);
        }
    }

    public void addTemplate(Map filtersMap, String templateName,
                            Integer userId) {
        FiltersTemplate fTemplate = new FiltersTemplate();
        Iterator filtersIterator = filtersMap.entrySet().iterator();

        while (filtersIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) filtersIterator.next();

            if (entry.getValue() instanceof FilterInterface) {
                FilterInterface filter = (FilterInterface) entry.getValue();

                String id = filter.save();
                fTemplate.setFilterId4FilterKey((String) entry.getKey(), id);
            } else {
                logger
                        .error("trying to save object for filter template but it isnt Filter");
            }
        }

        fTemplate.setUserId(userId);
        fTemplate.setTemplateName(templateName);
        hibernateEntityDao.save(fTemplate);
    }

    public void perform(FilterActionForm form, HttpServletRequest request)
            throws AdsapientException {
        Integer campainId = new Integer(form.getCampainId());

        FiltersService currentCampainFilters = createCampinFilters(campainId, null);

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        Collection userTemplatesCollection = hibernateEntityDao.viewWithCriteria(
                FiltersTemplate.class, "userId", user.getId(), "templateId");

        Iterator templatesIterator = userTemplatesCollection.iterator();

        while (templatesIterator.hasNext()) {
            FiltersTemplate fTempl = (FiltersTemplate) templatesIterator.next();

            if (request.getParameter(fTempl.getTemplateName()) != null) {
                FiltersService campainsFilter = createTemplateFilters(fTempl.getTemplateId()
                        .toString());

                Iterator filtersIterator = campainsFilter.getFiltersMap()
                        .entrySet().iterator();

                while (filtersIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) filtersIterator.next();

                    FilterInterface filter = (FilterInterface) entry.getValue();

                    FilterInterface campFilter = (FilterInterface) currentCampainFilters
                            .getFiltersMap().get(entry.getKey());
                    campFilter.add(filter);

                    hibernateEntityDao.updateObject(campFilter);
                }
            }
        }
    }

    public void removeTemplate(FiltersTemplate fTemplate) {
        Collection filters = hibernateEntityDao.viewAll(SystemFilter.class);
        Iterator filtersIterator = filters.iterator();

        while (filtersIterator.hasNext()) {
            SystemFilter filter = (SystemFilter) filtersIterator.next();

            Class filterClass = null;

            try {
                filterClass = Class.forName(filter.getFilterClass());
            } catch (ClassNotFoundException e) {
                logger.error("cant find class in remove Template"
                        + filter.getClass() + " " + e);
            }

            if ((filterClass != null)
                    && ((fTemplate.getFilterIdByFilterKey(filter
                    .getFilterName())) != null)) {
                hibernateEntityDao.removeObject(filterClass, fTemplate
                        .getFilterIdByFilterKey(filter.getFilterName()));
            }
        }

        hibernateEntityDao.removeObject(fTemplate);
    }

    public void viewTemplateOptions(FilterActionForm form,
                                    HttpServletRequest request) throws AdsapientException {
        FiltersService filters = createTemplateFilters(form
                .getTemplateId());

        FiltersTemplate fTempl = (FiltersTemplate) hibernateEntityDao.loadObject(
                FiltersTemplate.class, new Integer(form.getTemplateId()));

        request.getSession().setAttribute("filterTemplate", fTempl);

        form.setTemplateName(fTempl.getTemplateName());
        request.getSession().setAttribute("templateName",
                fTempl.getTemplateName());

        Iterator filtersIterator = filters.getFiltersMap().entrySet()
                .iterator();

        while (filtersIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) filtersIterator.next();

            request.getSession().setAttribute(
                    AdsapientConstants.TEMP_FILTER_PREFIX + entry.getKey(),
                    entry.getValue());
        }
    }


    public FiltersService constructCampainFilters() {
        Collection filters = hibernateEntityDao.viewAllWithOrder(
                SystemFilter.class, "id");

        Iterator filtersIterator = filters.iterator();

        while (filtersIterator.hasNext()) {
            SystemFilter filterElement = (SystemFilter) filtersIterator.next();
            Class filterClass = null;

            try {
                filterClass = Class.forName(filterElement.getFilterClass());
            } catch (ClassNotFoundException e) {
                logger.warn("could not find class " + filterElement.getClass());

                continue;
            }

            if (filterClass != null) {
                try {
                    filtersMap.put(filterElement.getFilterName(), filterClass
                            .newInstance());
                } catch (InstantiationException e1) {
                    logger.warn(e1);

                    continue;
                } catch (IllegalAccessException e1) {
                    logger.warn(e1);

                    continue;
                }
            }
        }
        return this;
    }

    public FiltersService constructCampainFilters(Integer campaignId, Integer bannerId) {
        Collection filters = hibernateEntityDao.viewAllWithOrder(
                SystemFilter.class, "id");

        Iterator filtersIterator = filters.iterator();

        while (filtersIterator.hasNext()) {
            SystemFilter filterElement = (SystemFilter) filtersIterator.next();
            Class filterClass = null;

            try {
                filterClass = Class.forName(filterElement.getFilterClass());
            } catch (ClassNotFoundException e) {
                logger.error("error in campain filters constructor:", e);
            }

            FilterInterface filter = null;

            if (bannerId != null) {
                filter = (FilterInterface) hibernateEntityDao
                        .loadObjectWithCriteria(filterClass, "bannerId",
                                bannerId);
            }

            if (filter == null) {
                Collection c = hibernateEntityDao
                        .viewWithCriteria(filterClass, "campainId",
                                campaignId, "bannerId", null);
                if (c != null && c.size() > 0) {
                    filter = (FilterInterface) c.toArray()[0];
                }

            }

            if ((bannerId != null) && (filter != null)
                    && (filter.getBannerId() == null)) {
                try {
                    FilterInterface clonedFilter = (FilterInterface) filter
                            .clone();
                    clonedFilter.setBannerId(bannerId);
                    clonedFilter.save();
                    filtersMap.put(filterElement.getFilterName(), clonedFilter);
                } catch (CloneNotSupportedException e) {
                    logger.error("Error while cloning filter "
                            + filterClass.getName(), e);
                }
            } else {
                filtersMap.put(filterElement.getFilterName(), filter);
            }
        }
        return this;
    }

    public FiltersService constructCampainFilters(String templateIdString) {
        Integer templateId = new Integer(templateIdString);
        FiltersTemplate template = (FiltersTemplate) hibernateEntityDao
                .loadObject(FiltersTemplate.class, templateId);

        Iterator filtersIterator = hibernateEntityDao.viewAll(SystemFilter.class)
                .iterator();

        while (filtersIterator.hasNext()) {
            SystemFilter systemFilter = (SystemFilter) filtersIterator.next();
            Class filterClass = null;

            try {
                filterClass = Class.forName(systemFilter.getFilterClass());
            } catch (ClassNotFoundException e) {
                logger.warn("class not found" + systemFilter.getFilterClass(),
                        e);

                continue;
            }

            if (template.getFilterIdByFilterKey(systemFilter.getFilterName()) != null) {

                Object filter = hibernateEntityDao.loadObject(filterClass,
                        template.getFilterIdByFilterKey(systemFilter
                                .getFilterName()));

                if (filter != null) {
                    this.filtersMap.put(systemFilter.getFilterClass(), filter);
                } else {
                    logger.error("can't load filter with id ="
                            + template.getFilterIdByFilterKey(systemFilter
                            .getFilterName())
                            + " for template with id=" + templateIdString);
                }
            }
        }
        return this;
    }

    public Collection getFiltersKeysCollection() {
        return this.filtersMap.keySet();
    }

    public Map getFiltersMap() {
        return filtersMap;
    }

    public void init(Object filterKey, HttpServletRequest request,
                     ActionForm form) {
        if (this.filtersMap.containsKey(filterKey)) {
            FilterInterface filter = (FilterInterface) filtersMap
                    .get(filterKey);

            filter.init(request, form);
        } else {
            logger.warn("error in CampainFilters init : cant find  key"
                    + filterKey);
        }
    }

    public void reset(Object filterKey, boolean enableHibernateUpdate) {
        if (this.filtersMap.containsKey(filterKey)) {
            FilterInterface filter = (FilterInterface) filtersMap
                    .get(filterKey);

            if (filter != null) {
                filter.reset();

                if (enableHibernateUpdate) {
                    hibernateEntityDao.updateObject(filter);
                }
            } else {
                logger
                        .warn("in CampainFilters metod reset: filter = null his  key id "
                                + filterKey);
            }
        } else {
            logger.warn(" in reset :cant find filter with key" + filterKey);
        }
    }

    public void resetAll(boolean enableHibernateUpdate) {
        Iterator entryIterator = this.filtersMap.entrySet().iterator();

        while (entryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIterator.next();

            this.reset(entry.getKey(), enableHibernateUpdate);
        }
    }

    public boolean update(Object filterKey, HttpServletRequest request,
                          ActionForm form, boolean enableHibernateUpdate) {
        if (this.filtersMap.containsKey(filterKey)) {
            FilterInterface filter = (FilterInterface) filtersMap
                    .get(filterKey);

            filter.update(request, form, enableHibernateUpdate);

            if ((filter != null) && (filter.getBannerId() == null)) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("CAMP", filter.getCampainId());

                Collection list = hibernateEntityDao.executeHQLQuery(filter
                        .getRelatedFiltersQueryName(), params);

                if (list != null) {
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        Object[] ind = (Object[]) iter.next();
                        hibernateEntityDao.removeObject(filter.getClass(),
                                (Integer) ind[1]);

                        try {
                            FilterInterface clonedFilter = (FilterInterface) filter
                                    .clone();
                            clonedFilter.setBannerId((Integer) ind[0]);
                            clonedFilter.save();
                        } catch (Exception e) {
                            logger.error("unable to clone filter", e);
                        }
                    }
                }
            }

            if (filter != null) {
                return (filter.getBannerId() == null) ? true : false;
            }
        } else {
            logger.warn("error in CampainFilters update : cant find  key"
                    + filterKey);
        }

        return false;
    }

    public CookieManagementService getCookieManagementService() {
        return cookieManagementService;
    }

    public void setCookieManagementService(CookieManagementService cookieManagementService) {
        this.cookieManagementService = cookieManagementService;
    }
}
