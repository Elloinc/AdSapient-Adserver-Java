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
package com.adsapient.adserver.reporter;

import com.adsapient.adserver.beans.AdserverModel;
import com.adsapient.adserver.beans.EventObject;
import com.adsapient.adserver.beans.ReporterEntityObject;
import com.adsapient.adserver.beans.ReporterModel;
import com.adsapient.adserver.beans.TotalsReporterModel;
import com.adsapient.adserver.beans.UniqueVisitorObject;
import com.adsapient.adserver.beans.VisitorObjectImpl;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.shared.mappable.TotalsReport;

import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporterModelBuilder {
	private static Logger logger = Logger.getLogger(ReporterModelBuilder.class);

	private TotalsReporterModel totalsReporterModel;

	private ReporterModel reporterModel;

	private AdsapientReporter adsapientReporter;

	private Map<Byte, Byte> requestEventIdToAdEventTypeMap;

	private AdserverModel adserverModel;

	private double precision = 1000;

	public ReporterModelBuilder() {
		requestEventIdToAdEventTypeMap = new HashMap<Byte, Byte>();
		requestEventIdToAdEventTypeMap.put(
				AdsapientConstants.GETAD_ADSERVEREVENT_TYPE,
				AdsapientConstants.ADVIEW);
		requestEventIdToAdEventTypeMap.put(
				AdsapientConstants.GETADCODE_ADSERVEREVENT_TYPE,
				AdsapientConstants.ADVIEW);
		requestEventIdToAdEventTypeMap.put(
				AdsapientConstants.CLICK_ADSERVEREVENT_TYPE,
				AdsapientConstants.CLICK);
		requestEventIdToAdEventTypeMap.put(
				AdsapientConstants.LEAD_ADSERVEREVENT_TYPE,
				AdsapientConstants.LEAD);
		requestEventIdToAdEventTypeMap.put(
				AdsapientConstants.SALE_ADSERVEREVENT_TYPE,
				AdsapientConstants.SALE);
	}

	public void registerEvent(Map<String, Object> requestParams) {
		try {
			Map<String, String> customParams = (Map<String, String>) requestParams
					.get(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY);

			if ((customParams == null)
					|| ((customParams
							.get(AdsapientConstants.COUNT_REQUEST_PARAM_NAME) != null) && customParams
							.get(AdsapientConstants.COUNT_REQUEST_PARAM_NAME)
							.equals("false"))) {
				return;
			}

			BannerImpl banner = (BannerImpl) requestParams
					.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);
			PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap().get(
					requestParams
							.get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY));

			if ((place == null) || (banner == null)) {
				return;
			}

			CampainImpl campaign = (CampainImpl) adserverModel
					.getCampaignsMap().get(banner.getCampainId());
			SiteImpl site = (SiteImpl) adserverModel.getSitesMap().get(
					place.getSiteId());

			if ((campaign == null) || (site == null)) {
				return;
			}

			UserImpl advertiser = (UserImpl) adserverModel.getUsersMap().get(
					banner.getUserId());
			UserImpl publisher = (UserImpl) adserverModel.getUsersMap().get(
					place.getUserId());
			UserImpl admin = (UserImpl) adserverModel.getUsersMap().get(
					ConfigurationConstants.ADMIN_ID);

			if (advertiser == null) {
				advertiser = publisher;
			}

			if ((advertiser == null) || (publisher == null) || (admin == null)) {
				return;
			}

			RateImpl bannerRate = (RateImpl) adserverModel.getRatesMap().get(
					banner.getRateId());
			RateImpl placeRate = (RateImpl) adserverModel.getRatesMap().get(
					place.getRateId());

			byte eventType = requestEventIdToAdEventTypeMap.get(requestParams
					.get(AdsapientConstants.EVENT_TYPE_REQUEST_PARAM_KEY));

			boolean isUnique = false;
			VisitorObjectImpl uniqueVisitor = adsapientReporter
					.getVisitorObject(requestParams);

			if (uniqueVisitor == null) {
				isUnique = true;
			}

			Integer uniqueVisitorId = adsapientReporter
					.saveOrUpdateUniqueVisitor(requestParams, eventType);

			if (uniqueVisitorId == null) {
				return;
			}

			requestParams.put(
					AdsapientConstants.COOKIE_UNIQUE_ID_REQUEST_PARAM_KEY,
					uniqueVisitorId);

			double earned = getTransactionValue(placeRate, eventType);
			double spent = getTransactionValue(bannerRate, eventType);
			double delta = spent - earned;
			double earnedSpent = 0.0;

			TotalsReport tr = null;
			ReporterEntityObject reo = null;

			String key = banner.getBannerId().toString()
					+ BannerImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), eventType);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent - spent);
			tr.setEntityclass(BannerImpl.class.getName());
			tr.setEntityid(banner.getBannerId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			key = place.getId().toString() + PlacesImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), eventType);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent + earned);
			tr.setEntityclass(PlacesImpl.class.getName());
			tr.setEntityid(place.getId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			key = campaign.getCampainId().toString()
					+ CampainImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), eventType);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent - spent);
			tr.setEntityclass(CampainImpl.class.getName());
			tr.setEntityid(campaign.getCampainId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			key = site.getSiteId().toString() + SiteImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), eventType);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent + earned);
			tr.setEntityclass(SiteImpl.class.getName());
			tr.setEntityid(site.getSiteId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			key = advertiser.getId().toString() + UserImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), eventType);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent - spent);
			tr.setEntityclass(UserImpl.class.getName());
			tr.setEntityid(advertiser.getId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			key = admin.getId().toString() + UserImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), AdsapientConstants.NOEVENT);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent + delta);
			tr.setEntityclass(UserImpl.class.getName());
			tr.setEntityid(admin.getId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			key = publisher.getId().toString() + UserImpl.class.getName();
			tr = updateTotalsReport(totalsReporterModel.getEntityObjects().get(
					key), eventType);
			earnedSpent = (tr.getEarnedspent() == null) ? 0.0 : tr
					.getEarnedspent();
			tr.setEarnedspent(earnedSpent + earned);
			tr.setEntityclass(UserImpl.class.getName());
			tr.setEntityid(publisher.getId());
			totalsReporterModel.getEntityObjects().put(key, tr);

			long ts = System.currentTimeMillis();
			EventObject eo = new EventObject();
			eo.setTs(ts);
			eo.setBannerid(banner.getBannerId());
			eo.setEventid(eventType);
			eo.setPlaceid(place.getId());
			eo.setUniqueid(uniqueVisitorId);

			reporterModel.registerEvent(eo);

			if (isUnique) {
				UniqueVisitorObject uniqueVisitorObject = new UniqueVisitorObject();
				uniqueVisitorObject.setUniqueid(uniqueVisitorId);
				uniqueVisitorObject.setBrowserid((Integer) requestParams
						.get(AdsapientConstants.BROWSER_ID_REQUEST_PARAM_KEY));
				uniqueVisitorObject.setLangid((Integer) requestParams
						.get(AdsapientConstants.LANGUAGE_ID_REQUEST_PARAM_KEY));
				uniqueVisitorObject.setOsid((Integer) requestParams
						.get(AdsapientConstants.OS_ID_REQUEST_PARAM_KEY));

				if (adserverModel
						.getGeoLocationsReverseMap()
						.get(
								(String) requestParams
										.get(AdsapientConstants.GEOLOCATION_NAME_REQUEST_PARAM_KEY)) != null) {
					uniqueVisitorObject
							.setGeolocationid(adserverModel
									.getGeoLocationsReverseMap()
									.get(
											(String) requestParams
													.get(AdsapientConstants.GEOLOCATION_NAME_REQUEST_PARAM_KEY))
									.getId());
				}

				reporterModel.registerNewUniqueVisitor(uniqueVisitorObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private double getTransactionValue(RateImpl placeRate, byte eventType) {
		double costPerUnit = 0;

		if (eventType == AdsapientConstants.ADVIEW) {
			costPerUnit = placeRate.getCpmRate().intValue() / 1000 / precision;
		} else if (eventType == AdsapientConstants.CLICK) {
			costPerUnit = placeRate.getCpcRate().intValue() / precision;
		} else if (eventType == AdsapientConstants.LEAD) {
			costPerUnit = placeRate.getCplRate().intValue() / precision;
		} else if (eventType == AdsapientConstants.SALE) {
			costPerUnit = placeRate.getCpsRate().intValue() / precision;
		}

		return costPerUnit;
	}

	private TotalsReport updateTotalsReport(TotalsReport totalsReport,
			byte eventType) {
		if (totalsReport == null) {
			totalsReport = new TotalsReport();
		}

		if (eventType == AdsapientConstants.ADVIEW) {
			int numberOfGeneratedUnits = (totalsReport.getAdviews() == null) ? 0
					: totalsReport.getAdviews().intValue();
			totalsReport.setAdviews(numberOfGeneratedUnits + 1);
		} else if (eventType == AdsapientConstants.CLICK) {
			int numberOfGeneratedUnits = (totalsReport.getClicks() == null) ? 0
					: totalsReport.getClicks().intValue();
			totalsReport.setClicks(numberOfGeneratedUnits + 1);
		} else if (eventType == AdsapientConstants.LEAD) {
			int numberOfGeneratedUnits = (totalsReport.getLeads() == null) ? 0
					: totalsReport.getLeads();
			totalsReport.setLeads(numberOfGeneratedUnits + 1);
		} else if (eventType == AdsapientConstants.SALE) {
			int numberOfGeneratedUnits = (totalsReport.getSales() == null) ? 0
					: totalsReport.getSales();
			totalsReport.setSales(numberOfGeneratedUnits + 1);
		}

		return totalsReport;
	}

	public TotalsReporterModel getTotalsReporterModel() {
		return totalsReporterModel;
	}

	public void setTotalsReporterModel(TotalsReporterModel totalsReporterModel) {
		this.totalsReporterModel = totalsReporterModel;
	}

	public ReporterModel getReporterModel() {
		return reporterModel;
	}

	public void setReporterModel(ReporterModel reporterModel) {
		this.reporterModel = reporterModel;
	}

	public AdsapientReporter getAdsapientReporter() {
		return adsapientReporter;
	}

	public void setAdsapientReporter(AdsapientReporter adsapientReporter) {
		this.adsapientReporter = adsapientReporter;
	}

	public AdserverModel getAdserverModel() {
		return adserverModel;
	}

	public void setAdserverModel(AdserverModel adserverModel) {
		this.adserverModel = adserverModel;
	}

	public void resetTotalsReport(Class clazz, Integer id,
			Map<String, Object> requestParams) {
		if (clazz.getName().equals(TotalsReport.class.getName())) {
			TotalsReport tr = findTotalsReportById(id);

			if (tr != null) {
				Map<String, String> customRequestParams = (Map<String, String>) requestParams
						.get(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY);

				if (customRequestParams
						.get(AdsapientConstants.USERID_REQUEST_PARAM_NAME) != null) {
					resetUser(tr);

					return;
				} else if (customRequestParams
						.get(AdsapientConstants.CAMPAIGNID_REQUEST_PARAM_NAME) != null) {
					resetCampaign(tr);

					return;
				} else if (customRequestParams
						.get(AdsapientConstants.SITEID_REQUEST_PARAM_NAME) != null) {
					resetSite(tr);

					return;
				} else if (customRequestParams
						.get(AdsapientConstants.BANNERID_REQUEST_PARAM_NAME) != null) {
					resetBanner(tr);

					return;
				} else if (customRequestParams
						.get(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME) != null) {
					resetPlace(tr);

					return;
				}
			} else {
				logger.warn("TotalsReport not found:" + id);
			}
		}
	}

	private void resetCampaign(TotalsReport tr) {
		int entityId = tr.getEntityid();

		if (tr.getEntityclass().equals(CampainImpl.class.getName())) {
			TotalsReport campaignTr = totalsReporterModel.getEntityObjects()
					.get(tr.getEntityid() + CampainImpl.class.getName());
			CampainImpl campaign = (CampainImpl) adserverModel
					.getCampaignsMap().get(campaignTr.getEntityid());
			TotalsReport userTr = totalsReporterModel.getEntityObjects().get(
					campaign.getUserId() + UserImpl.class.getName());
			userTr.subtract(campaignTr);

			List<BannerImpl> banners = findBannerByCampaignId(entityId);

			for (BannerImpl banner : banners) {
				TotalsReport bannerTr = totalsReporterModel.getEntityObjects()
						.get(banner.getId() + BannerImpl.class.getName());

				if (bannerTr != null) {
					bannerTr.reset();
				}
			}

			campaignTr.reset();
		}
	}

	private void resetSite(TotalsReport tr) {
		int entityId = tr.getEntityid();

		if (tr.getEntityclass().equals(SiteImpl.class.getName())) {
			TotalsReport siteTr = totalsReporterModel.getEntityObjects().get(
					tr.getEntityid() + SiteImpl.class.getName());
			SiteImpl site = (SiteImpl) adserverModel.getSitesMap().get(
					siteTr.getEntityid());
			TotalsReport userTr = totalsReporterModel.getEntityObjects().get(
					site.getUserId() + UserImpl.class.getName());
			userTr.subtract(siteTr);

			List<PlacesImpl> places = findPlacesBySiteId(entityId);

			for (PlacesImpl place : places) {
				TotalsReport placeTr = totalsReporterModel.getEntityObjects()
						.get(place.getId() + PlacesImpl.class.getName());

				if (placeTr != null) {
					placeTr.reset();
				}
			}

			siteTr.reset();
		}
	}

	private void resetBanner(TotalsReport tr) {
		if (tr.getEntityclass().equals(BannerImpl.class.getName())) {
			TotalsReport bannerTr = totalsReporterModel.getEntityObjects().get(
					tr.getEntityid() + BannerImpl.class.getName());
			BannerImpl banner = (BannerImpl) adserverModel.getBannersMap().get(
					bannerTr.getEntityid());
			TotalsReport userTr = totalsReporterModel.getEntityObjects().get(
					banner.getUserId() + UserImpl.class.getName());
			userTr.subtract(bannerTr);

			TotalsReport campaignTr = totalsReporterModel.getEntityObjects()
					.get(banner.getCampainId() + CampainImpl.class.getName());
			campaignTr.subtract(bannerTr);

			bannerTr.reset();
		}
	}

	private void resetPlace(TotalsReport tr) {
		if (tr.getEntityclass().equals(PlacesImpl.class.getName())) {
			TotalsReport placeTr = totalsReporterModel.getEntityObjects().get(
					tr.getEntityid() + PlacesImpl.class.getName());
			PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap().get(
					placeTr.getEntityid());
			TotalsReport userTr = totalsReporterModel.getEntityObjects().get(
					place.getUserId() + UserImpl.class.getName());
			userTr.subtract(placeTr);

			TotalsReport siteTr = totalsReporterModel.getEntityObjects().get(
					place.getSiteId() + SiteImpl.class.getName());
			siteTr.subtract(placeTr);

			placeTr.reset();
		}
	}

	private void resetUser(TotalsReport tr) {
		int entityId = tr.getEntityid();

		if (tr.getEntityclass().equals(UserImpl.class.getName())) {
			TotalsReport userTr = totalsReporterModel.getEntityObjects().get(
					tr.getEntityid() + UserImpl.class.getName());

			if (userTr != null) {
				userTr.reset();
			}

			List<CampainImpl> campaigns = findCampaignsByUserId(entityId);

			for (CampainImpl campaign : campaigns) {
				TotalsReport campaignTr = totalsReporterModel
						.getEntityObjects().get(
								campaign.getId() + CampainImpl.class.getName());

				if (campaignTr != null) {
					if (campaignTr != null) {
						campaignTr.reset();
					}

					List<BannerImpl> banners = findBannersByCampaignId(campaign
							.getId());

					for (BannerImpl banner : banners) {
						TotalsReport bannerTr = totalsReporterModel
								.getEntityObjects().get(
										banner.getId()
												+ BannerImpl.class.getName());

						if (bannerTr != null) {
							bannerTr.reset();
						}
					}
				}
			}

			List<SiteImpl> sites = findSitesByUserId(entityId);

			for (SiteImpl site : sites) {
				TotalsReport siteTr = totalsReporterModel.getEntityObjects()
						.get(site.getId() + SiteImpl.class.getName());

				if (siteTr != null) {
					if (siteTr != null) {
						siteTr.reset();
					}

					List<PlacesImpl> places = findPlacesBySiteId(site.getId());

					for (PlacesImpl place : places) {
						TotalsReport placeTr = totalsReporterModel
								.getEntityObjects().get(
										place.getId()
												+ PlacesImpl.class.getName());

						if (placeTr != null) {
							placeTr.reset();
						}
					}
				}
			}
		}
	}

	private List<BannerImpl> findBannersByCampaignId(Integer id) {
		List<BannerImpl> l = new ArrayList<BannerImpl>();

		for (Integer bannerId : adserverModel.getBannersMap().keySet()) {
			BannerImpl banner = (BannerImpl) adserverModel.getBannersMap().get(
					bannerId);

			if (banner.getCampainId().equals(id)) {
				l.add(banner);
			}
		}

		return l;
	}

	private List<PlacesImpl> findPlacesBySiteId(int entityId) {
		List<PlacesImpl> l = new ArrayList<PlacesImpl>();

		for (Integer id : adserverModel.getPlacesMap().keySet()) {
			PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap()
					.get(id);

			if (place.getSiteId().equals(entityId)) {
				l.add(place);
			}
		}

		return l;
	}

	private List<BannerImpl> findBannerByCampaignId(int entityId) {
		List<BannerImpl> l = new ArrayList<BannerImpl>();

		for (Integer id : adserverModel.getBannersMap().keySet()) {
			BannerImpl banner = (BannerImpl) adserverModel.getBannersMap().get(
					id);

			if (banner.getCampainId().equals(entityId)) {
				l.add(banner);
			}
		}

		return l;
	}

	private List<SiteImpl> findSitesByUserId(int userId) {
		List<SiteImpl> l = new ArrayList<SiteImpl>();

		for (Integer id : adserverModel.getSitesMap().keySet()) {
			SiteImpl site = (SiteImpl) adserverModel.getSitesMap().get(id);

			if (site.getUserId().equals(userId)) {
				l.add(site);
			}
		}

		return l;
	}

	private List<CampainImpl> findCampaignsByUserId(int userId) {
		List<CampainImpl> l = new ArrayList<CampainImpl>();

		for (Integer id : adserverModel.getCampaignsMap().keySet()) {
			CampainImpl campaign = (CampainImpl) adserverModel
					.getCampaignsMap().get(id);

			if (campaign.getUserId().equals(userId)) {
				l.add(campaign);
			}
		}

		return l;
	}

	private TotalsReport findTotalsReportById(Integer id) {
		for (String key : totalsReporterModel.getEntityObjects().keySet()) {
			TotalsReport tr = totalsReporterModel.getEntityObjects().get(key);

			if (tr.getId().equals(id)) {
				return tr;
			}
		}

		return null;
	}
}
