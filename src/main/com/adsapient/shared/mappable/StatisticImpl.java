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

import com.adsapient.api.StatisticInterface;

public class StatisticImpl implements StatisticInterface {
	private Integer advertizerId;

	private Integer bannerId;

	private Integer bannerRate;

	private Integer campainId;

	private Integer categoryId;

	private Integer placeId;

	private Integer placesRate;

	private Integer positionId;

	private Integer publisherId;

	private Integer siteId;

	private Integer userId;

	private String country;

	private Integer statisticId;

	private long time;

	private long endTime;

	private String keyWords;

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public void setAdvertizerId(Integer advertizerId) {
		this.advertizerId = advertizerId;
	}

	public Integer getAdvertizerId() {
		return advertizerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerRate(Integer bannerRate) {
		this.bannerRate = bannerRate;
	}

	public Integer getBannerRate() {
		return bannerRate;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlacesRate(Integer placesRate) {
		this.placesRate = placesRate;
	}

	public Integer getPlacesRate() {
		return placesRate;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public Integer getStatisticId() {
		return statisticId;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setUserId(Integer UserId) {
		this.userId = UserId;
	}

	public Integer getUserId() {
		return userId;
	}

	private void setStatisticId(Integer statisticId) {
		this.statisticId = statisticId;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}
