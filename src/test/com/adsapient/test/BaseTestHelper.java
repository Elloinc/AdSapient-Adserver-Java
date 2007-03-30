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
package com.adsapient.test;

import com.adsapient.shared.mappable.PlacesImpl;
import com.adsapient.shared.mappable.SiteImpl;
import com.adsapient.shared.mappable.UserImpl;

import com.adsapient.shared.mappable.*;
import com.adsapient.shared.AdsapientConstants;

import com.adsapient.shared.service.TimeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseTestHelper extends SpringTestCase {
	public static PlacesImpl createPlace() {
		PlacesImpl place = new PlacesImpl();
		place.setRateId(0);

		place.setSizeId(1);

		place.setLoadingTypeId(AdsapientConstants.LOADING_TYPE_IMMEDIATE);

		place.setTargetWindowId(AdsapientConstants.TARGET_WINDOW_SELF);
		place.setPlaceTypeId(AdsapientConstants.PLACE_TYPE_ORDINARY);
		place.setRowCount(1);
		place.setColumnCount(1);
		place.setSorting(false);
		place.setUserId(1);
		place.setKeywords(false);
		place.setOwnCampaign(false);
		place.setSiteId(1);
		place.setPlaceId(1);
		place.setCategorys(":1:2:");

		return place;
	}

	public static SiteImpl createSite(UserImpl user) {
		SiteImpl site = new SiteImpl();
		site.setUserId(user.getId());
		site.setStartDate("");
		site.setDescription("com.adsapient.adserver.test site");
		site.setUrl("http://www.com.adsapient.adserver.test.com");

		site.setStateId(0);
		site.setRateId(0);

		return site;
	}

	public static UserImpl createUser() {
		UserImpl user = new UserImpl();

		user.setName("testuser");
		user.setEmail("testemail@email.com");
		user.setPassword("testpass");
		user.setLogin("testlogin");

		return user;
	}

	public static CampainImpl createActiveCampaignWithBanner() {
		CampainImpl campaign = createCampaign();
		BannerImpl banner = createBanner();
		Set<BannerImpl> banners = new HashSet<BannerImpl>();
		banners.add(banner);
		campaign.setBanners(banners);

		return campaign;
	}

	public static CampainImpl createCampaign() {
		CampainImpl campaign = new CampainImpl();
		campaign.setName("campaign name");
		campaign.setUserId(1);
		campaign.setStateId(AdsapientConstants.DEFAULT_VERIFYING_STATE_ID);
		campaign
				.setUserDefineCampainStateId(String
						.valueOf(AdsapientConstants.DEFAULT_ACTIVE_CAMPAIN_STATE_ID));
		campaign.setStartDate("");
		campaign.setEndDate("");
		campaign.setUrl("http://google.com");
		campaign.setRateId(1);
		campaign.setBudget(100);
		campaign.setStatusBartext("status bar text");
		campaign.setAltText("alt text");
		campaign.setLoadingTypeId(AdsapientConstants.LOADING_TYPE_IMMEDIATE);
		campaign.setPlaceTypeId(1);
		campaign.setTargetWindowId(AdsapientConstants.TARGET_WINDOW_SELF);

		return campaign;
	}

	public static BannerImpl createBanner() {
		BannerImpl banner = new BannerImpl();
		banner.setUserId(AdsapientConstants.ADMIN_ID);

		banner.setSizeId(1);
		banner.setTypeId(1);
		banner.setStateId(AdsapientConstants.DEFAULT_VERIFYING_STATE_ID);
		banner.setFileName("test banner created by BaseTestHelper");
		banner.setFile("adnetwork/banners/1/image.png");
		banner.setPrioritet(10);
		banner.setStatusBartext("statusbar test");
		banner.setAltText("alt text");
		banner.setLoadingTypeId(AdsapientConstants.LOADING_TYPE_IMMEDIATE);
		banner.setPlaceTypeId(1);
		banner.setTargetWindowId(AdsapientConstants.TARGET_WINDOW_SELF);
		banner.setStartDate(TimeService.getNormalizedDateString());
		banner.setEndDate(TimeService.getNormalizedDateString());
		banner.setName("test name");

		return banner;
	}

	public static Map<String, Object> createDefaultRequestParams() {
		Map<String, Object> params = new HashMap<String, Object>();

		return params;
	}

	public static SiteImpl createActiveSiteWithAdplace() {
		SiteImpl site = new SiteImpl();

		PlacesImpl place = createPlace();
		List<PlaceImpl> l = new ArrayList<PlaceImpl>();

		return site;
	}

	public static Map<String, Object> createGetPlaceCodeRequestParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY,
				AdsapientConstants.PLACE_TYPE_ORDINARY);

		return params;
	}

	public static Map<String, Object> createGetAdCodeRequestParams() {
		Map<String, Object> params = new HashMap<String, Object>();

		return params;
	}

	public static TotalsReport createTotalsReport() {
		TotalsReport tr = new TotalsReport();
		tr.setEntityclass(BannerImpl.class.getName());
		tr.setAdviews(10);
		tr.setEntityid(1);

		return tr;
	}
}
