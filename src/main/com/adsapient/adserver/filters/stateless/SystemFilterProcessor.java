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
package com.adsapient.adserver.filters.stateless;

import com.adsapient.adserver.beans.AdserverModel;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.share.Type;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

public class SystemFilterProcessor {
	static Logger logger = Logger.getLogger(SystemFilterProcessor.class);

	private AdserverModel adserverModel;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public boolean doFilter(BannerImpl banner, Map<String, Object> requestParams) {
		try {
			Integer placeId = (Integer) requestParams
					.get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY);
			PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap().get(
					placeId);

			if (!banner.getSizeId().equals(place.getSizeId())
					&& !banner.getTypeId().equals(Type.SUPERSTITIAL_BANNER)) {
				return false;
			}

			CampainImpl campaign = (CampainImpl) adserverModel
					.getCampaignsMap().get(banner.getCampainId());

			if (campaign == null) {
				return false;
			}

			try {
				Date startDate = sdf.parse(banner.getStartDate());
				Date endDate = sdf.parse(banner.getEndDate());
				Date currentTime = new Date();

				if (currentTime.before(startDate) || currentTime.after(endDate)) {
					return false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);

				return false;
			}

			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return true;
	}

	public AdserverModel getAdserverModel() {
		return adserverModel;
	}

	public void setAdserverModel(AdserverModel adserverModel) {
		this.adserverModel = adserverModel;
	}
}
