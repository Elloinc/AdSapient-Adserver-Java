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
package com.adsapient.adserver.requestprocessors;

import com.adsapient.adserver.ModelUpdater;

import com.adsapient.api.FilterInterface;
import com.adsapient.api.IMappable;

import com.adsapient.shared.mappable.*;
import com.adsapient.shared.mappable.GeoFilter;
import com.adsapient.shared.mappable.KeywordsFilter;
import com.adsapient.shared.mappable.ParametersFilter;
import com.adsapient.shared.mappable.SystemsFilter;
import com.adsapient.shared.mappable.TimeFilter;
import com.adsapient.shared.mappable.PlacesImpl;

import com.adsapient.shared.AdsapientConstants;

import com.adsapient.test.BaseTestHelper;
import com.adsapient.test.SpringTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAdCodeProcessorTest extends SpringTestCase {
	GetAdCodeProcessor getAdCodeProcessor;

	PlacesImpl place;

	List<IMappable> entitiesList = new ArrayList<IMappable>();

	protected void setUp() throws Exception {
		super.setUp();
		getAdCodeProcessor = (GetAdCodeProcessor) appContext
				.getBean("getAdCodeProcessor");
		modelUpdater = (ModelUpdater) appContext.getBean("modelUpdater");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		destroyEntities();
	}

	public void testGetRegularPlaceCode() {
		createEntities();

		modelUpdater.setup();

		Map<String, Object> requestParams = BaseTestHelper
				.createGetAdCodeRequestParams();
		requestParams.put(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY, place
				.getId());
		requestParams.put(AdsapientConstants.REQUESTURL_REQUEST_PARAM_KEY,
				"http:");

		long t1 = System.currentTimeMillis();
		BannerImpl banner = getAdCodeProcessor.getAd(requestParams);
		System.out.println("DONE IN: " + (System.currentTimeMillis() - t1));
		System.out.println(banner);
	}

	private void destroyEntities() {
		for (IMappable entity : entitiesList) {
			try {
				hibernateEntityDao.removeObject(entity);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	private void createEntities() {
		place = BaseTestHelper.createPlace();

		CampainImpl campaign = BaseTestHelper.createActiveCampaignWithBanner();

		hibernateEntityDao.save(place);
		entitiesList.add(place);
		hibernateEntityDao.save(campaign);
		entitiesList.add(campaign);
		hibernateEntityDao.updateObject(place);

		for (BannerImpl banner : campaign.getBanners()) {
			banner.setCampainId(campaign.getCampainId());
			hibernateEntityDao.updateObject(banner);
		}

		List<FilterInterface> filterInstances = new ArrayList<FilterInterface>();

		try {
			filterInstances.add(TimeFilter.class.newInstance());
			filterInstances.add(DateFilter.class.newInstance());
			filterInstances.add(SystemsFilter.class.newInstance());
			filterInstances.add(ParametersFilter.class.newInstance());
			filterInstances.add(KeywordsFilter.class.newInstance());
			filterInstances.add(GeoFilter.class.newInstance());
			filterInstances.add(ContentFilter.class.newInstance());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		for (FilterInterface filter : filterInstances) {
			filter.setCampainId(campaign.getCampainId());

			hibernateEntityDao.save(filter);
			entitiesList.add((IMappable) filter);
		}
	}
}
