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
import com.adsapient.api_impl.publisher.PlacesImpl;

import com.adsapient.util.admin.AdsapientConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CliqueMetricsFilterProcessor {
	private AdserverModel adserverModel;

	public CliqueMetricsFilterProcessor() {
	}

	public boolean doFilter(BannerImpl banner, Map<String, Object> requestParams) {
		Integer placeId;
		PlacesImpl place;
		CategoryMembership[] placeMems;
		CategoryMembership[] bannerMems;
		float matchLevel;

		placeId = (Integer) requestParams
				.get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY);
		place = (PlacesImpl) adserverModel.getPlacesMap().get(placeId);

		placeMems = getCategoryMemberships(place);
		bannerMems = getBannerCategories(banner);

		matchLevel = match(placeMems, bannerMems);

		if (matchLevel >= getThreshold()) {
			return true;
		} else {
			return false;
		}
	}

	public AdserverModel getAdserverModel() {
		return adserverModel;
	}

	public void setAdserverModel(AdserverModel adserverModel) {
		this.adserverModel = adserverModel;
	}

	public CategoryMembership[] getCategoryMemberships(PlacesImpl place) {
		return null;
	}

	public CategoryMembership[] getBannerCategories(BannerImpl banner) {
		return null;
	}

	public float match(CategoryMembership[] placeMemberships,
			CategoryMembership[] targetingDescs) {
		float matchLevel = 0;
		Map<String, CategoryMembership> targetingMap = new HashMap<String, CategoryMembership>();
		int i;
		int len;
		Set<String> keySet;
		float minmemPlace;
		float maxmemPlace;
		float thismatch;
		float minmem;
		float maxmem;
		CategoryMembership targetingDesc;

		for (i = 0; i < targetingDescs.length; i++) {
			targetingMap
					.put(targetingDescs[i].getCategory(), targetingDescs[i]);
		}

		matchLevel = 0;
		len = placeMemberships.length;

		for (i = 0; i < len; i++) {
			minmemPlace = placeMemberships[i].getMinMembership();
			maxmemPlace = placeMemberships[i].getMaxMembership();

			targetingDesc = targetingMap.get(placeMemberships[i].getCategory());

			if (targetingDesc == null) {
				continue;
			}

			minmem = targetingDesc.getMinMembership();
			maxmem = targetingDesc.getMaxMembership();

			if (!isWithinUOD(minmemPlace)) {
				throwOutOfUODException("minmemPlace", minmemPlace);
			}

			if (!isWithinUOD(maxmemPlace)) {
				throwOutOfUODException("maxmemPlace", maxmemPlace);
			}

			if (!isOrdered(minmemPlace, maxmemPlace)) {
				throwUnorderedException("place", minmemPlace, maxmemPlace);
			}

			if (!isWithinUOD(minmem)) {
				throwOutOfUODException("minmem", minmem);
			}

			if (!isWithinUOD(maxmem)) {
				throwOutOfUODException("maxmem", maxmem);
			}

			if (!isOrdered(minmem, maxmem)) {
				throwUnorderedException("banner/target", minmem, maxmem);
			}

			thismatch = 0;

			if (isWithin(minmemPlace, minmem, maxmem)
					|| isWithin(maxmemPlace, minmem, maxmem)) {
				thismatch = 1;
			} else if ((minmemPlace < minmem) && (maxmemPlace > maxmem)) {
				thismatch = 1;
			} else if (isWithin((maxmemPlace + getFuzzifySpread()), minmem,
					maxmem)) {
				thismatch = (maxmemPlace - (minmem - getFuzzifySpread()))
						/ getFuzzifySpread();
			} else if (isWithin((minmemPlace - getFuzzifySpread()), minmem,
					maxmem)) {
				thismatch = ((maxmem + getFuzzifySpread()) - minmemPlace)
						/ getFuzzifySpread();
			} else {
				thismatch = 0;
			}

			matchLevel += thismatch;
		}

		matchLevel = matchLevel / placeMemberships.length;

		return matchLevel;
	}

	private static boolean isWithin(float value, float start, float end) {
		return ((value >= start) && (value <= end));
	}

	private static boolean isWithinUOD(float value) {
		return isWithin(value, getLowerUOD(), getUpperUOD());
	}

	private static boolean isOrdered(float first, float second) {
		return first <= second;
	}

	private static void throwOutOfUODException(String name, float value) {
		throw new RuntimeException("passed " + name
				+ " is outside UOD. uod-lower:[" + getLowerUOD()
				+ "], uod-upper:[" + getUpperUOD() + "]");
	}

	private static void throwUnorderedException(String name, float minmem,
			float maxmem) {
		throw new RuntimeException("for [" + name + "] minmem[" + minmem
				+ "] <= maxmem[" + maxmem + "] is not true");
	}

	public static final float getThreshold() {
		return 0.5f;
	}

	public static final float getLowerUOD() {
		return 0f;
	}

	public static final float getUpperUOD() {
		return 10f;
	}

	public static final float getFuzzifySpread() {
		return 0.5f;
	}

	public static class CategoryMembership {
		private String category;

		private float minMembership;

		private float maxMembership;

		public CategoryMembership() {
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public float getMinMembership() {
			return minMembership;
		}

		public void setMinMembership(float minMembership) {
			this.minMembership = minMembership;
		}

		public float getMaxMembership() {
			return maxMembership;
		}

		public void setMaxMembership(float maxMembership) {
			this.maxMembership = maxMembership;
		}
	}
}
