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
package com.adsapient.api_impl.usability.advertizer;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.resource.ResourceImpl;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.statistic.advertizer.BannerStatistic;
import com.adsapient.api_impl.statistic.common.StatisticEntity;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BannerRepresentation extends BannerImpl {
	private static Logger logger = Logger.getLogger(BannerRepresentation.class);

	private StatisticEntity statistic;

	private String bannerSize;

	private String campainName;

	protected String deleteHref;

	private String reportsHref;

	private String resetStatisticHref;

	protected String settingsHref;

	public BannerRepresentation(ResourceImpl banner) {
	}

	public BannerRepresentation(BannerImpl banner) {
		if (banner.getSize() != null) {
			this.bannerSize = banner.getSize().getSize();
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("SIZEID", banner.getSizeId());

			List names = (List) MyHibernateUtil.executeHQLQuery("getSizeName",
					params);

			if ((names != null) && (names.size() != 0)) {
				this.bannerSize = (String) names.get(0);
			}
		}

		this.setStatus(banner.getStatus());
		this.setCampainId(banner.getCampainId());
		this.deleteHref = new StringBuffer(
				"upload.do?action=removeBanner&bannerId=").append(
				banner.getBannerId()).toString();
		this.settingsHref = new StringBuffer("upload.do?action=view&bannerId=")
				.append(banner.getBannerId()).append("&userId=").append(
						banner.getUserId()).toString();

		this.reportsHref = new StringBuffer(
				"advertizerStatistic.do?type=bannerAll&bannerId=").append(
				banner.getBannerId()).append("&userId=").append(
				banner.getUserId()).toString();

		if ((banner.getStatus() != BannerImpl.STATUS_DEFAULT)
				&& (banner.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT)) {
			this.statistic = BannerStatistic.getBannerStatisticAsText(banner
					.getBannerId());
			this.resetStatisticHref = new StringBuffer(
					"upload.do?action=resetStatistic&bannerId=").append(
					banner.getBannerId()).append("&userId=").append(
					banner.getUserId()).toString();
		}

		this.setBannerId(banner.getBannerId());
		this.setRateId(banner.getRateId());
		this.setName(banner.getName());

		if (!banner.getCampainId().equals(
				ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID)) {
			CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
					CampainImpl.class, banner.getCampainId());

			if (campain != null) {
				if (ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID == campain
						.getStateId()) {
					this.deleteHref = null;
				}

				this.setCampainName(campain.getName());
			}
		}
	}

	public BannerRepresentation(Size size) {
		Integer defaultBannerId = (Integer) getDefaultBannerIdBySizeId(size
				.getSizeId());
		this.bannerSize = size.getSize();

		this.settingsHref = new StringBuffer(
				"defaultBanners.do?action=view&id=").append(defaultBannerId)
				.toString();
		this.reportsHref = new StringBuffer(
				"advertizerStatistic.do?type=bannerAll&bannerId=").append(
				defaultBannerId).toString();
		this.setFile(size.getDefaultFileName());
		this.setFileName("default");
		this.setTypeId(size.getDefaultFileTypeId());
		this.setBannerId(size.getSizeId());
		this.statistic = BannerStatistic
				.getBannerStatisticAsText(defaultBannerId);
	}

	public void setBannerSize(String bannerSize) {
		this.bannerSize = bannerSize;
	}

	public String getBannerSize() {
		return bannerSize;
	}

	public void setCampainName(String campainName) {
		this.campainName = campainName;
	}

	public String getCampainName() {
		return campainName;
	}

	public void setDeleteHref(String deleteHref) {
		this.deleteHref = deleteHref;
	}

	public String getDeleteHref() {
		return deleteHref;
	}

	public void setReportsHref(String reportsHref) {
		this.reportsHref = reportsHref;
	}

	public String getReportsHref() {
		return reportsHref;
	}

	public void setResetStatisticHref(String resetStatisticHref) {
		this.resetStatisticHref = resetStatisticHref;
	}

	public String getResetStatisticHref() {
		return resetStatisticHref;
	}

	public void setSettingsHref(String settingsHref) {
		this.settingsHref = settingsHref;
	}

	public String getSettingsHref() {
		return settingsHref;
	}

	public void setStatistic(StatisticEntity statistic) {
		this.statistic = statistic;
	}

	public StatisticEntity getStatistic() {
		return statistic;
	}

	private Integer getDefaultBannerIdBySizeId(Integer sizeId) {
		Collection resultCollection = MyHibernateUtil.viewWithCriteria(
				BannerImpl.class, "campainId",
				ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID, "sizeId",
				sizeId);

		if (resultCollection.isEmpty()) {
			logger.warn("Cant find default banner for sizeId:" + sizeId);

			return null;
		}

		BannerImpl banner = (BannerImpl) resultCollection.iterator().next();

		return banner.getBannerId();
	}
}
