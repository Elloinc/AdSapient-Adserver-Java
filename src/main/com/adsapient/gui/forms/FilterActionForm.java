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

import com.adsapient.shared.mappable.TrafficFilter;
import com.adsapient.shared.mappable.PlaceImpl;
import com.adsapient.shared.mappable.PlacesImpl;
import com.adsapient.shared.mappable.SiteImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.shared.service.LabelService;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;


public class FilterActionForm extends ActionForm {
    private ArrayList categorys = new ArrayList();
    private ArrayList categorys2 = new ArrayList();
    private ArrayList cities = new ArrayList();
    private String categoriesPriorities;
    private ArrayList cities2 = new ArrayList();
    private ArrayList countries = new ArrayList();
    private ArrayList countries2 = new ArrayList();
    private ArrayList selectedCategorys = new ArrayList();
    private Collection user_browsers = new ArrayList();
    private String selectedElementsValue;
    private Collection elements = new ArrayList();
    private Collection selectedElements = new ArrayList();
    private Collection user_browsers2 = new ArrayList();
    private Collection user_systems = new ArrayList();
    private Collection user_systems2 = new ArrayList();
    private Collection campainTemplatesCollection = new ArrayList();
    private Collection cityNameToCodeCollection = new ArrayList();
    private Collection containedCityNames = new ArrayList();
    private Collection containedCountryNames = new ArrayList();
    private Collection countryNameToCodeCollection = new ArrayList();
    private Collection customPeriodDay = new ArrayList();
    private Collection customPeriodHour = new ArrayList();
    private Collection excludeHoursCollection = new ArrayList();
    private Collection pagePositionsCollection1 = new ArrayList();
    private Collection pagePositionsCollection2 = new ArrayList();
    private Collection perHourCollection = new ArrayList();
    private Collection referrersCollection = new ArrayList();
    private Collection templateCollection = new ArrayList();
    private Collection textEnginesCollection = new ArrayList();
    private Collection valuesOfTheDayCollection = new ArrayList();
    private HashMap FiltersMap = new HashMap();
    private String campainId;
    private String bannerId;
    private String dataSource = "campain";
    private String excludeHours = "";
    private String filterAction;
    private String filterId;
    private String filterType = AdsapientConstants.TIME_FILTER;
    private String parametersSource = "";
    private String referrerEngineAction = "add";
    private String referrerId = "";
    private String referrerUrl = "";
    private String registeredPlaces;
    private String trafficShare = "0";
    private String selectedBrowsers;
    private String selectedCategorysName;
    private String selectedCities = "";
    private String selectedCountrys = "";
    private String selectedPositions;
    private String selectedSystems;
    private String selected_browsers;
    private String selected_systems;
    private String templateAction;
    private String templateId;
    private String templateName;
    private String textEngine;
    private String textEngineAction = "add";
    private Integer textEngineId;
    private String valuesOfTheDay = "";
    private boolean allPlaces = true;
    private boolean enableAutoUpdate = false;
    private boolean referrerType;
    private int customPeriodClickDayValue;
    private int customPeriodClickDayValueUnique;
    private int customPeriodClickHourValue;
    private int customPeriodClickHourValueUnique;
    private int customPeriodDayValue;
    private int customPeriodDayValueUnique;
    private int customPeriodHourValue;
    private int customPeriodHourValueUnique;
    private int customPeriodInClickValue;
    private int customPeriodInClickValueUnique;
    private int customPeriodValue;
    private int customPeriodValueUnique;
    private int maxClicksInCampain;
    private int maxClicksInCampainForUniqueUser;
    private int maxClicksInDay;
    private int maxClicksInDayForUniqueUser;
    private int maxClicksInMonthForUniqueUser;
    private int maxImpresionsInDayForUniqueUser;
    private int maxImpressionsInCampain;
    private int maxImpressionsInCampainForUniqueUser;
    private int maxImpressionsInDay;
    private int maxImpressionsInMonthForUniqueUser;
    private int impressions;
    private int clicks;
    private int average;
    private String selected_langs;
    private String selectedLangs;
    private Collection user_langs = new ArrayList();
    private Collection user_langs2 = new ArrayList();

    public String getTrafficShare() {
        return trafficShare;
    }

    public void setTrafficShare(String trafficShare) {
        this.trafficShare = trafficShare;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public void setAllPlaces(boolean allPlaces) {
        this.allPlaces = allPlaces;
    }

    public boolean isAllPlaces() {
        return allPlaces;
    }

    public void setCampainId(String campainId) {
        this.campainId = campainId;
    }

    public String getCampainId() {
        return campainId;
    }

    public void setCampainTemplatesCollection(
        Collection campainTemplatesCollection) {
        this.campainTemplatesCollection = campainTemplatesCollection;
    }

    public Collection getCampainTemplatesCollection() {
        return campainTemplatesCollection;
    }

    public void setCategorys(ArrayList categorys) {
        this.categorys = categorys;
    }

    public ArrayList getCategorys() {
        return categorys;
    }

    public void setCategorys2(ArrayList categorys2) {
        this.categorys2 = categorys2;
    }

    public ArrayList getCategorys2() {
        return categorys2;
    }

    public void setCities(ArrayList cities) {
        this.cities = cities;
    }

    public ArrayList getCities() {
        return cities;
    }

    public void setCities2(ArrayList cities2) {
        this.cities2 = cities2;
    }

    public ArrayList getCities2() {
        return cities2;
    }

    public void setCityNameToCodeCollection(Collection cityNameToCodeCollection) {
        this.cityNameToCodeCollection = cityNameToCodeCollection;
    }

    public Collection getCityNameToCodeCollection() {
        return cityNameToCodeCollection;
    }

    public void setContainedCityNames(Collection containedCityNames) {
        this.containedCityNames = containedCityNames;
    }

    public Collection getContainedCityNames() {
        return containedCityNames;
    }

    public void setContainedCountryNames(Collection containedCountryNames) {
        this.containedCountryNames = containedCountryNames;
    }

    public Collection getContainedCountryNames() {
        return containedCountryNames;
    }

    public void setCountries(ArrayList countries) {
        this.countries = countries;
    }

    public ArrayList getCountries() {
        return countries;
    }

    public void setCountries2(ArrayList countries2) {
        this.countries2 = countries2;
    }

    public ArrayList getCountries2() {
        return countries2;
    }

    public void setCountryNameToCodeCollection(
        Collection countryNameToCodeCollection) {
        this.countryNameToCodeCollection = countryNameToCodeCollection;
    }

    public Collection getCountryNameToCodeCollection() {
        return countryNameToCodeCollection;
    }

    public void setCustomPeriodClickDayValue(int customPeriodClickDayValue) {
        this.customPeriodClickDayValue = customPeriodClickDayValue;
    }

    public int getCustomPeriodClickDayValue() {
        return customPeriodClickDayValue;
    }

    public void setCustomPeriodClickDayValueUnique(
        int customPeriodClickDayValueUnique) {
        this.customPeriodClickDayValueUnique = customPeriodClickDayValueUnique;
    }

    public int getCustomPeriodClickDayValueUnique() {
        return customPeriodClickDayValueUnique;
    }

    public void setCustomPeriodClickHourValue(int customPeriodClickHourValue) {
        this.customPeriodClickHourValue = customPeriodClickHourValue;
    }

    public int getCustomPeriodClickHourValue() {
        return customPeriodClickHourValue;
    }

    public void setCustomPeriodClickHourValueUnique(
        int customPeriodClickHourValueUnique) {
        this.customPeriodClickHourValueUnique = customPeriodClickHourValueUnique;
    }

    public int getCustomPeriodClickHourValueUnique() {
        return customPeriodClickHourValueUnique;
    }

    public void setCustomPeriodDay(Collection customPeriodDay) {
        this.customPeriodDay = customPeriodDay;
    }

    public Collection getCustomPeriodDay() {
        this.customPeriodDay = LabelService.fillCustomWeightCollection(0, 100);

        return customPeriodDay;
    }

    public void setCustomPeriodDayValue(int customPeriodDayValue) {
        this.customPeriodDayValue = customPeriodDayValue;
    }

    public int getCustomPeriodDayValue() {
        return customPeriodDayValue;
    }

    public void setCustomPeriodDayValueUnique(int customPeriodDayValueUnique) {
        this.customPeriodDayValueUnique = customPeriodDayValueUnique;
    }

    public int getCustomPeriodDayValueUnique() {
        return customPeriodDayValueUnique;
    }

    public void setCustomPeriodHour(Collection customPeriodHour) {
        this.customPeriodHour = customPeriodHour;
    }

    public Collection getCustomPeriodHour() {
        this.customPeriodHour = LabelService.fillCustomWeightCollection(0, 24);

        return customPeriodHour;
    }

    public void setCustomPeriodHourValue(int customPeriodHourValue) {
        this.customPeriodHourValue = customPeriodHourValue;
    }

    public int getCustomPeriodHourValue() {
        return customPeriodHourValue;
    }

    public void setCustomPeriodHourValueUnique(int customPeriodHourValueUnique) {
        this.customPeriodHourValueUnique = customPeriodHourValueUnique;
    }

    public int getCustomPeriodHourValueUnique() {
        return customPeriodHourValueUnique;
    }

    public void setCustomPeriodInClickValue(int customPeriodInClickValue) {
        this.customPeriodInClickValue = customPeriodInClickValue;
    }

    public int getCustomPeriodInClickValue() {
        return customPeriodInClickValue;
    }

    public void setCustomPeriodInClickValueUnique(
        int customPeriodInClickValueUnique) {
        this.customPeriodInClickValueUnique = customPeriodInClickValueUnique;
    }

    public int getCustomPeriodInClickValueUnique() {
        return customPeriodInClickValueUnique;
    }

    public void setCustomPeriodValue(int customPeriodValue) {
        this.customPeriodValue = customPeriodValue;
    }

    public int getCustomPeriodValue() {
        return customPeriodValue;
    }

    public void setCustomPeriodValueUnique(int customPeriodValueUnique) {
        this.customPeriodValueUnique = customPeriodValueUnique;
    }

    public int getCustomPeriodValueUnique() {
        return customPeriodValueUnique;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setEnableAutoUpdate(boolean enableAutoUpdate) {
        this.enableAutoUpdate = enableAutoUpdate;
    }

    public boolean isEnableAutoUpdate() {
        return enableAutoUpdate;
    }

    public void setExcludeHours(String excludeHours) {
        this.excludeHours = excludeHours;
    }

    public String getExcludeHours() {
        return excludeHours;
    }

    public void setExcludeHoursCollection(Collection excludeHoursCollection) {
        this.excludeHoursCollection = excludeHoursCollection;
    }

    public Collection getExcludeHoursCollection() {
        this.excludeHoursCollection = new ArrayList();

        for (int i = 1; i < 25; i++) {
            StringBuffer sb = new StringBuffer();
            sb.append(":").append(Integer.toString(i)).append(":");

            if (excludeHours.indexOf(sb.toString()) > -1) {
                excludeHoursCollection.add("y");
            } else {
                excludeHoursCollection.add("n");
            }
        }

        return excludeHoursCollection;
    }

    public void setFilterAction(String filterAction) {
        this.filterAction = filterAction;
    }

    public String getFilterAction() {
        return filterAction;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFiltersMap(HashMap filtersMap) {
        FiltersMap = filtersMap;
    }

    public HashMap getFiltersMap() {
        return FiltersMap;
    }

    public void setMaxClicksInCampain(int maxClicksInCampain) {
        this.maxClicksInCampain = maxClicksInCampain;
    }

    public int getMaxClicksInCampain() {
        return maxClicksInCampain;
    }

    public void setMaxClicksInCampainForUniqueUser(
        int maxClicksInCampainForUniqueUser) {
        this.maxClicksInCampainForUniqueUser = maxClicksInCampainForUniqueUser;
    }

    public int getMaxClicksInCampainForUniqueUser() {
        return maxClicksInCampainForUniqueUser;
    }

    public void setMaxClicksInDay(int maxClicksInDay) {
        this.maxClicksInDay = maxClicksInDay;
    }

    public int getMaxClicksInDay() {
        return maxClicksInDay;
    }

    public void setMaxClicksInDayForUniqueUser(int maxClicksInDayForUniqueUser) {
        this.maxClicksInDayForUniqueUser = maxClicksInDayForUniqueUser;
    }

    public int getMaxClicksInDayForUniqueUser() {
        return maxClicksInDayForUniqueUser;
    }

    public void setMaxClicksInMonthForUniqueUser(
        int maxClicksInMonthForUniqueUser) {
        this.maxClicksInMonthForUniqueUser = maxClicksInMonthForUniqueUser;
    }

    public int getMaxClicksInMonthForUniqueUser() {
        return maxClicksInMonthForUniqueUser;
    }

    public void setMaxImpresionsInDayForUniqueUser(
        int maxImpresionsInDayForUniqueUser) {
        this.maxImpresionsInDayForUniqueUser = maxImpresionsInDayForUniqueUser;
    }

    public int getMaxImpresionsInDayForUniqueUser() {
        return maxImpresionsInDayForUniqueUser;
    }

    public void setMaxImpressionsInCampain(int maxImpressionsInCampain) {
        this.maxImpressionsInCampain = maxImpressionsInCampain;
    }

    public int getMaxImpressionsInCampain() {
        return maxImpressionsInCampain;
    }

    public void setMaxImpressionsInCampainForUniqueUser(
        int maxImpressionsInCampainForUniqueUser) {
        this.maxImpressionsInCampainForUniqueUser = maxImpressionsInCampainForUniqueUser;
    }

    public int getMaxImpressionsInCampainForUniqueUser() {
        return maxImpressionsInCampainForUniqueUser;
    }

    public void setMaxImpressionsInDay(int maxImpressionsInDay) {
        this.maxImpressionsInDay = maxImpressionsInDay;
    }

    public int getMaxImpressionsInDay() {
        return maxImpressionsInDay;
    }

    public void setMaxImpressionsInMonthForUniqueUser(
        int maxImpressionsInMonthForUniqueUser) {
        this.maxImpressionsInMonthForUniqueUser = maxImpressionsInMonthForUniqueUser;
    }

    public int getMaxImpressionsInMonthForUniqueUser() {
        return maxImpressionsInMonthForUniqueUser;
    }

    public void setPagePositionsCollection1(Collection pagePositionsCollection1) {
        this.pagePositionsCollection1 = pagePositionsCollection1;
    }

    public Collection getPagePositionsCollection1() {
        return pagePositionsCollection1;
    }

    public void setPagePositionsCollection2(Collection pagePositionsCollection2) {
        this.pagePositionsCollection2 = pagePositionsCollection2;
    }

    public Collection getPagePositionsCollection2() {
        return pagePositionsCollection2;
    }

    public void setParametersSource(String parametersSource) {
        this.parametersSource = parametersSource;
    }

    public String getParametersSource() {
        return parametersSource;
    }

    public void setPerHourCollection(Collection perHourCollection) {
        this.perHourCollection = perHourCollection;
    }

    public Collection getPerHourCollection() {
        return perHourCollection;
    }

    public void setReferrerEngineAction(String referrerEngineAction) {
        this.referrerEngineAction = referrerEngineAction;
    }

    public String getReferrerEngineAction() {
        return referrerEngineAction;
    }

    public void setReferrerId(String referrerId) {
        this.referrerId = referrerId;
    }

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerType(boolean referrerType) {
        this.referrerType = referrerType;
    }

    public boolean getReferrerType() {
        return referrerType;
    }

    public void setReferrerUrl(String referrerUrl) {
        this.referrerUrl = referrerUrl;
    }

    public String getReferrerUrl() {
        return referrerUrl;
    }

    public void setReferrersCollection(Collection referrersCollection) {
        this.referrersCollection = referrersCollection;
    }

    public Collection getReferrersCollection() {
        return referrersCollection;
    }

    public void setRegisteredPlaces(String registeredPlaces) {
        this.registeredPlaces = registeredPlaces;
    }

    public String getRegisteredPlaces() {
        return registeredPlaces;
    }

    public void setSelectedBrowsers(String selectedBrowsers) {
        this.selectedBrowsers = selectedBrowsers;
    }

    public String getSelectedBrowsers() {
        return selectedBrowsers;
    }

    public void setSelectedCategorys(ArrayList selectedCategorys) {
        this.selectedCategorys = selectedCategorys;
    }

    public ArrayList getSelectedCategorys() {
        return selectedCategorys;
    }

    public void setSelectedCategorysName(String selectedCategorysName) {
        this.selectedCategorysName = selectedCategorysName;
    }

    public String getSelectedCategorysName() {
        return selectedCategorysName;
    }

    public void setSelectedCities(String selectedCities) {
        this.selectedCities = selectedCities;
    }

    public String getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCountrys(String selectedCountrys) {
        this.selectedCountrys = selectedCountrys;
    }

    public String getSelectedCountrys() {
        return selectedCountrys;
    }

    public void setSelectedPositions(String selectedPositions) {
        this.selectedPositions = selectedPositions;
    }

    public String getSelectedPositions() {
        return selectedPositions;
    }

    public void setSelectedSystems(String selectedSystems) {
        this.selectedSystems = selectedSystems;
    }

    public String getSelectedSystems() {
        return selectedSystems;
    }

    public void setSelected_browsers(String selected_browsers) {
        this.selected_browsers = selected_browsers;
    }

    public String getSelected_browsers() {
        return selected_browsers;
    }

    public void setSelected_systems(String selected_systems) {
        this.selected_systems = selected_systems;
    }

    public String getSelected_systems() {
        return selected_systems;
    }

    public void setTemplateAction(String templateAction) {
        this.templateAction = templateAction;
    }

    public String getTemplateAction() {
        return templateAction;
    }

    public void setTemplateCollection(Collection templateCollection) {
        this.templateCollection = templateCollection;
    }

    public Collection getTemplateCollection() {
        return templateCollection;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTextEngine(String textEngine) {
        this.textEngine = textEngine;
    }

    public String getTextEngine() {
        return textEngine;
    }

    public void setTextEngineAction(String textEngineAction) {
        this.textEngineAction = textEngineAction;
    }

    public String getTextEngineAction() {
        return textEngineAction;
    }

    public void setTextEngineId(Integer textEngineId) {
        this.textEngineId = textEngineId;
    }

    public Integer getTextEngineId() {
        return textEngineId;
    }

    public void setTextEnginesCollection(Collection textEnginesCollection) {
        this.textEnginesCollection = textEnginesCollection;
    }

    public Collection getTextEnginesCollection() {
        return textEnginesCollection;
    }

    public void setUser_browsers(ArrayList user_browsers) {
        this.user_browsers = user_browsers;
    }

    public Collection getUser_browsers() {
        return user_browsers;
    }

    public void setUser_browsers2(ArrayList user_browsers2) {
        this.user_browsers2 = user_browsers2;
    }

    public Collection getUser_browsers2() {
        return user_browsers2;
    }

    public void setUser_systems(ArrayList user_systems) {
        this.user_systems = user_systems;
    }

    public Collection getUser_systems() {
        return user_systems;
    }

    public void setUser_systems2(ArrayList user_systems2) {
        this.user_systems2 = user_systems2;
    }

    public Collection getUser_systems2() {
        return user_systems2;
    }

    public void setValuesOfTheDay(String valuesOfTheDay) {
        this.valuesOfTheDay = valuesOfTheDay;
    }

    public String getValuesOfTheDay() {
        return valuesOfTheDay;
    }

    public void setValuesOfTheDayCollection(Collection valuesOfTheDayCollection) {
        this.valuesOfTheDayCollection = valuesOfTheDayCollection;
    }

    public String getCategoriesPriorities() {
        return categoriesPriorities;
    }

    public void setCategoriesPriorities(String categoriesPriorities) {
        this.categoriesPriorities = categoriesPriorities;
    }

    public Collection getValuesOfTheDayCollection() {
        if (valuesOfTheDayCollection.isEmpty()) {
            for (int i = 1; i < 8; i++) {
                if (valuesOfTheDay.indexOf(Integer.toString(i)) > -1) {
                    valuesOfTheDayCollection.add("y");
                } else {
                    valuesOfTheDayCollection.add("n");
                }
            }
        }

        return valuesOfTheDayCollection;
    }

    public String generateSourceStructure() {
        StringBuffer buffer = new StringBuffer();
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection sitesCollection = hibernateEntityDao.viewAll(SiteImpl.class);

        for (Iterator sitesIterator = sitesCollection.iterator();
                sitesIterator.hasNext();) {
            SiteImpl currentSite = (SiteImpl) sitesIterator.next();

            if ((currentSite.getRealPlaces() != null) &&
                    (currentSite.getRealPlaces().size() != 0)) {
                buffer.append(" arr = new Array(");

                for (Iterator placesIterator = currentSite.getRealPlaces()
                                                          .iterator();
                        placesIterator.hasNext();) {
                    PlacesImpl currentPlaces = (PlacesImpl) placesIterator.next();

                    PlaceImpl place = currentPlaces.getPlace();

                    if (place != null) {
                        buffer.append(" new Option('")
                              .append(currentSite.getUrl()).append(":")
                              .append(place.getName()).append("','")
                              .append(currentPlaces.getId()).append("')");

                        if (placesIterator.hasNext()) {
                            buffer.append(",");
                        }
                    }
                }

                buffer.append(");");
                buffer.append("cats.push(new uiOptionTransfer_Category('")
                      .append(currentSite.getUrl()).append("', '")
                      .append(currentSite.getSiteId()).append("', arr));");
            }
        }

        return buffer.toString();
    }

    public String generateTargetStructure() {
        StringBuffer buffer = new StringBuffer();
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection sitesCollection = hibernateEntityDao.viewAll(SiteImpl.class);

        for (Iterator sitesIterator = sitesCollection.iterator();
                sitesIterator.hasNext();) {
            SiteImpl currentSite = (SiteImpl) sitesIterator.next();

            if ((currentSite.getRealPlaces() != null) &&
                    (currentSite.getRealPlaces().size() != 0)) {
                for (Iterator placesIterator = currentSite.getRealPlaces()
                                                          .iterator();
                        placesIterator.hasNext();) {
                    PlacesImpl currentPlaces = (PlacesImpl) placesIterator.next();
                    PlaceImpl place = currentPlaces.getPlace();

                    if (place != null) {
                        StringBuffer placesCode = new StringBuffer(":").append(currentPlaces.getId())
                                                                       .append(":");

                        if ((registeredPlaces != null) &&
                                (registeredPlaces.indexOf(placesCode.toString()) > -1)) {
                            buffer.append("opt = new Option('")
                                  .append(currentSite.getUrl()).append(":")
                                  .append(place.getName()).append("', '")
                                  .append(currentPlaces.getId())
                                  .append("'); tgt.options.add(opt);");
                        }
                    }
                }
            }
        }

        return buffer.toString();
    }

    public void initTrafficFilter(TrafficFilter trFilter) {
        this.filterAction = "update";

        this.maxClicksInCampain = trFilter.getMaxClicksInCampain();
        this.maxClicksInCampainForUniqueUser = trFilter.getMaxClicksInCampainForUniqueUser();
        this.maxClicksInDay = trFilter.getMaxClicksInDay();
        this.maxClicksInDayForUniqueUser = trFilter.getMaxClicksInDayForUniqueUser();
        this.maxClicksInMonthForUniqueUser = trFilter.getMaxClicksInMonthForUniqueUser();
        this.maxImpresionsInDayForUniqueUser = trFilter.getMaxImpresionsInDayForUniqueUser();
        this.maxImpressionsInCampain = trFilter.getMaxImpressionsInCampain();
        this.maxImpressionsInCampainForUniqueUser = trFilter.getMaxImpressionsInCampainForUniqueUser();
        this.maxImpressionsInDay = trFilter.getMaxImpressionsInDay();
        this.maxImpressionsInMonthForUniqueUser = trFilter.getMaxImpressionsInMonthForUniqueUser();

        this.customPeriodValue = trFilter.getCustomPeriodValue();
        this.customPeriodHourValue = trFilter.getCustomPeriodHour();
        this.customPeriodDayValue = trFilter.getCustomPeriodDay();

        this.customPeriodInClickValue = trFilter.getCustomPeriodClickInValue();
        this.customPeriodClickHourValue = trFilter.getCustomPeriodClickInHour();
        this.customPeriodClickDayValue = trFilter.getCustomPeriodClickInDay();

        this.customPeriodValueUnique = trFilter.getCustomPeriodValueUnique();
        this.customPeriodHourValueUnique = trFilter.getCustomPeriodHourUnique();
        this.customPeriodDayValueUnique = trFilter.getCustomPeriodDayUnique();

        this.customPeriodInClickValueUnique = trFilter.getCustomPeriodClickInValueUnique();
        this.customPeriodClickHourValueUnique = trFilter.getCustomPeriodClickInHourUnique();
        this.customPeriodClickDayValueUnique = trFilter.getCustomPeriodClickInDayUnique();

        this.trafficShare = String.valueOf((trFilter.getTrafficShare() == null)
                ? 0 : trFilter.getTrafficShare());
    }

    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
        if (countries == null) {
            countries = new ArrayList();
        }

        if (countries2 == null) {
            countries2 = new ArrayList();
        }
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
        return null;
    }

    public Collection getElements() {
        return elements;
    }

    public void setElements(Collection elements) {
        this.elements = elements;
    }

    public Collection getSelectedElements() {
        return selectedElements;
    }

    public void setSelectedElements(Collection selectedElements) {
        this.selectedElements = selectedElements;
    }

    public String getSelectedElementsValue() {
        return selectedElementsValue;
    }

    public void setSelectedElementsValue(String selectedElementsValue) {
        this.selectedElementsValue = selectedElementsValue;
    }

    public void setUser_browsers(Collection user_browsers) {
        this.user_browsers = user_browsers;
    }

    public void setUser_browsers2(Collection user_browsers2) {
        this.user_browsers2 = user_browsers2;
    }

    public void setUser_systems(Collection user_systems) {
        this.user_systems = user_systems;
    }

    public void setUser_systems2(Collection user_systems2) {
        this.user_systems2 = user_systems2;
    }

    public String getSelected_langs() {
        return selected_langs;
    }

    public void setSelected_langs(String selected_langs) {
        this.selected_langs = selected_langs;
    }

    public Collection getUser_langs2() {
        return user_langs2;
    }

    public void setUser_langs2(Collection user_langs2) {
        this.user_langs2 = user_langs2;
    }

    public String getSelectedLangs() {
        return selectedLangs;
    }

    public void setSelectedLangs(String selectedLangs) {
        this.selectedLangs = selectedLangs;
    }

    public Collection getUser_langs() {
        return user_langs;
    }

    public void setUser_langs(Collection user_langs) {
        this.user_langs = user_langs;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }
}
