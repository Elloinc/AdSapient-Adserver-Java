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

import com.adsapient.adserver.AdserverServlet;
import com.adsapient.adserver.beans.IpToCountryMappingBean;

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.IMappable;

import com.adsapient.shared.service.CookieManagementService;

import com.adsapient.shared.service.CitiesService;
import com.adsapient.shared.service.FiltersService;
import com.adsapient.shared.service.RatesService;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.FilterActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import com.maxmind.geoip.LookupService;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class GeoFilter implements FilterInterface, IMappable {
	static Logger logger = Logger.getLogger(GeoFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedGeoFilterIds";

	public static LookupService cl = null;

	private Integer bannerId;

	private Integer campainId;

	private Integer geoFilterId;

	private String preferCities;

	private String preferCountrys;

	public boolean isApplyFilter(GeoFilter gFilter) {
		StringTokenizer strT = new StringTokenizer(preferCountrys, ",");

		while (strT.hasMoreTokens()) {
			if (gFilter.getPreferCountrys().indexOf(strT.nextToken()) < 0) {
				return false;
			}
		}

		return true;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public Integer getGeoFilterId() {
		return geoFilterId;
	}

	public void setGeoFilterId(Integer geoFilterId) {
		this.geoFilterId = geoFilterId;
	}

	public void setPreferCities(String preferCities) {
		this.preferCities = preferCities;
	}

	public String getPreferCities() {
		if (preferCities == null) {
			preferCities = CitiesService.getAllCitiesIndexes();
		}

		return preferCities;
	}

	public void setPreferCountrys(String preferCountrys) {
		this.preferCountrys = preferCountrys;
	}

	public String getPreferCountrys() {
		if (preferCountrys == null) {
			preferCountrys = FiltersService.getAllCoutryIndexes();
		}

		return preferCountrys;
	}

	public void add(FilterInterface filter) {
		if (filter instanceof GeoFilter) {
			GeoFilter gFilter = (GeoFilter) filter;

			StringBuffer strBuf = new StringBuffer();
			StringBuffer citiesStringBuffer = new StringBuffer();
			StringTokenizer strT = new StringTokenizer(preferCountrys, ",");
			StringTokenizer citiesTokenizer = new StringTokenizer(
					this.preferCities, ",");
			String countryCode;
			String cityCode;

			while (strT.hasMoreTokens()) {
				countryCode = strT.nextToken();

				if (gFilter.getPreferCountrys().indexOf(countryCode) > -1) {
					strBuf.append(countryCode).append(",");
				}
			}

			this.preferCountrys = strBuf.toString();

			while (citiesTokenizer.hasMoreTokens()) {
				cityCode = citiesTokenizer.nextToken();

				if (gFilter.getPreferCities().indexOf(cityCode) > -1) {
					citiesStringBuffer.append(cityCode).append(",");
				}
			}

			this.preferCities = citiesStringBuffer.toString();
		} else {
			logger.warn(" given class isnt instance of geoFilter");
		}
	}

	public void addNew() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        hibernateEntityDao.save(this);
	}

	public boolean containsCity(String cityCode) {
		if ((preferCities == null) || (preferCities.indexOf(cityCode) == -1)) {
			return false;
		}

		return true;
	}

	public boolean containsCountry(String countryCode) {
		if ((preferCountrys == null)
				|| (preferCountrys.indexOf(countryCode) == -1)) {
			return false;
		}

		return true;
	}

	public Object copy() {
		GeoFilter geoFilter = new GeoFilter();

		geoFilter.setPreferCountrys(this.getPreferCountrys());
		geoFilter.setPreferCities(this.getPreferCities());

		return geoFilter;
	}

	public GeoFilter clone() throws CloneNotSupportedException {
		return (GeoFilter) super.clone();
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public boolean doFilter(HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        String visitorCountry = "--";
		String remoteAddress = request.getRemoteAddr();
		StringTokenizer stringTokenizer = new StringTokenizer(remoteAddress,
				".");

		IpToCountryMappingBean ipToCountryMappingBean = (IpToCountryMappingBean) AdserverServlet.appContext
				.getBean("ipToCountryMapping");
		visitorCountry = ipToCountryMappingBean.search(remoteAddress);

		if (logger.isDebugEnabled()) {
			logger.debug("user country is " + visitorCountry);
		}

		if (this.getPreferCountrys().indexOf(visitorCountry) < 0) {
			return false;
		}

		try {
			String ip1 = stringTokenizer.nextToken();
			String ip2 = stringTokenizer.nextToken();
			String ip3 = stringTokenizer.nextToken();
			StringBuffer buffer = new StringBuffer();
			int result;
			buffer
					.append(
							"select count(airport)  from com.adsapient.api_impl.ute.AirportImpl as airport where ")
					.append("airport.ip1='").append(ip1).append("' and ")
					.append("airport.ip2='").append(ip2).append("' and ")
					.append("airport.ip3='").append(ip3).append("'");

			result = hibernateEntityDao.getCount(buffer.toString());

			if (result == 0) {
			} else {
				StringBuffer buffer2 = new StringBuffer();
				buffer2
						.append(
								"select airport  from com.adsapient.api_impl.ute.AirportImpl as airport where ")
						.append("airport.ip1='").append(ip1).append("' and ")
						.append("airport.ip2='").append(ip2).append("' and ")
						.append("airport.ip3='").append(ip3).append("'");

				Collection airports = hibernateEntityDao.getObject(buffer2
						.toString());

			}
		} catch (Exception ex) {
			logger.error("do filter for citie is not successful :(", ex);
		}

		return true;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;

		if (FiltersService.countryIndexArray != null) {
			for (int i = 0; i < FiltersService.countryIndexArray.length; i++) {
				String key = FiltersService.countryIndexArray[i];
				String value = FiltersService.getCountryNameByCode(key);
				LabelValueBean lvb = new LabelValueBean(value, key);

				if (containsCountry(key)) {
					form.getContainedCountryNames().add(lvb);
				}

				form.getCountryNameToCodeCollection().add(lvb);
				form.setFilterAction("update");
			}
		}

		for (Iterator iter = CitiesService.getAllCities().iterator(); iter
				.hasNext();) {
			String cityCode = (String) iter.next();

			if (!CitiesService.getCityNameByCode(cityCode).equals("n/a")) {
				LabelValueBean city = new LabelValueBean(CitiesService
						.getCityNameByCode(cityCode), cityCode);

				if (containsCountry(cityCode)) {
					form.getContainedCityNames().add(city);
				}

				form.getCityNameToCodeCollection().add(city);
			}
		}

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}
	}

	public void reset() {
		this.preferCountrys = FiltersService.getAllCoutryIndexes();
		this.preferCities = CitiesService.getAllCitiesIndexes();
	}

	public String save() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        return String.valueOf(hibernateEntityDao.save(this));
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateUpdate) {
		FilterActionForm form = (FilterActionForm) actionForm;

		this.setPreferCountrys(form.getSelectedCountrys());

		this.setPreferCities(form.getSelectedCities());

		if (enableHibernateUpdate) {
            HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
            hibernateEntityDao.updateObject(this);
		}
	}

	public Integer getId() {
		return geoFilterId;
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
}
