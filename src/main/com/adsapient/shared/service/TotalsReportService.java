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

import com.adsapient.shared.mappable.SiteImpl;
import com.adsapient.shared.mappable.UserImpl;

import com.adsapient.api.IMappable;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Collection;

public class TotalsReportService {
	static Logger logger = Logger.getLogger(TotalsReportService.class);

	private HibernateEntityDao hibernateEntityDao;
    private CookieManagementService cookieManagementService;

    private NumberFormat doubleFormatter = new DecimalFormat("0.00");

	private NumberFormat ctrFormatter = new DecimalFormat("0.00");

	private NumberFormat intFormatter = new DecimalFormat("0");

	private int precision = 2;

	public String getTotalUnitsByUser(UserImpl user, byte eventType,
			String userRole) {
		try {
			if (!user.getRole().equals(userRole)) {
				return "";
			}

			Object[] criteria = new Object[] { "entityid", user.getId(),
					"entityclass", UserImpl.class.getName() };
			TotalsReport tr = (TotalsReport) hibernateEntityDao
					.viewWithCriteria(TotalsReport.class, criteria).toArray()[0];

			if (eventType == AdsapientConstants.ADVIEW) {
				return Integer.toString(tr.getAdviews());
			} else if (eventType == AdsapientConstants.CLICK) {
				return Integer.toString(tr.getClicks());
			}
		} catch (Exception ex) {
			return intFormatter.format(0);
		}

		return intFormatter.format(0);
	}

	public String getTotalUnitsByEntity(Class clazz, int id, byte eventType) {
		try {
			Object[] criteria = new Object[] { "entityid", id, "entityclass",
					clazz.getName() };
			TotalsReport tr = (TotalsReport) hibernateEntityDao
					.viewWithCriteria(TotalsReport.class, criteria).toArray()[0];

			if (eventType == AdsapientConstants.ADVIEW) {
				int d = (tr.getAdviews() == null) ? 0 : tr.getAdviews();

				return Integer.toString(d);
			} else if (eventType == AdsapientConstants.CLICK) {
				int i = (tr.getClicks() == null) ? 0 : tr.getClicks();

				return Integer.toString(i);
			} else if (eventType == AdsapientConstants.LEAD) {
				int i = (tr.getLeads() == null) ? 0 : tr.getLeads();

				return Integer.toString(i);
			} else if (eventType == AdsapientConstants.SALE) {
				int i = (tr.getSales() == null) ? 0 : tr.getSales();

				return Integer.toString(i);
			} else if (eventType == AdsapientConstants.EARNEDSPENT) {
				double d = (tr.getEarnedspent() == null) ? 0.0 : tr
						.getEarnedspent();

				if (d < 0) {
					d = d * (-1);
				}

				return Double.toString(round(d, precision));
			} else if (eventType == AdsapientConstants.CTR) {
				int d1 = (tr.getClicks() == null) ? 0 : tr.getClicks();
				int d2 = (tr.getAdviews() == null) ? 0 : tr.getAdviews();

				return getCTR(d1, d2);
			}
		} catch (Exception ex) {
			return intFormatter.format(0);
		}

		return intFormatter.format(0);
	}

	public Double getUserBalance(Integer userId) {
		try {
			Object[] criteria = new Object[] { "entityid", userId,
					"entityclass", UserImpl.class.getName() };
			TotalsReport tr = (TotalsReport) hibernateEntityDao
					.viewWithCriteria(TotalsReport.class, criteria).toArray()[0];

			double earnedSpent = (tr.getEarnedspent() == null) ? 0 : tr
					.getEarnedspent();
			double d = round(earnedSpent, precision);

			if (d < 0) {
				d = d * (-1);
			}

			return d;
		} catch (Exception ex) {
			return 0.0;
		}
	}

	private double round(double value, int decimalPlace) {
		double power_of_ten = 1;

		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;

		return Math.round(value * power_of_ten) / power_of_ten;
	}

	public String getCTR(int clicks, int adviews) {
		try {
			if (adviews == 0) {
				return doubleFormatter.format(0);
			}

			return doubleFormatter.format((double) clicks / adviews);
		} catch (Exception ex) {
			return doubleFormatter.format(0);
		}
	}

	public HibernateEntityDao getHibernateEntityDao() {
		return hibernateEntityDao;
	}

	public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
		this.hibernateEntityDao = hibernateEntityDao;
	}

	public void resetTotalsByUserId(Integer userId, UserImpl user) {
		Object[] criteria = new Object[] { "entityid", userId, "entityclass",
				UserImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport tr = (TotalsReport) trs.toArray()[0];
			tr.reset();
			hibernateEntityDao.updateObject(tr);

			hibernateEntityDao.sendUpdateEventToAdserver(tr.getClass(),
					((IMappable) tr).getId(), ("&"
							+ AdsapientConstants.USERID_REQUEST_PARAM_NAME
							+ "=" + userId));

			criteria = new Object[] { "userId", userId };

			CampainImpl[] campaigns = (CampainImpl[]) hibernateEntityDao
					.viewWithCriteria(CampainImpl.class, criteria).toArray(
							new CampainImpl[] {});

			for (CampainImpl campaign : campaigns) {
				criteria = new Object[] { "entityid", campaign.getCampainId(),
						"entityclass", CampainImpl.class.getName() };
				trs = hibernateEntityDao.viewWithCriteria(TotalsReport.class,
						criteria);

				if (trs.size() > 0) {
					tr = (TotalsReport) trs.toArray()[0];
					tr.reset();
					hibernateEntityDao.updateObject(tr);

					criteria = new Object[] { "campainId", campaign.getId() };

					BannerImpl[] banners = (BannerImpl[]) hibernateEntityDao
							.viewWithCriteria(BannerImpl.class, criteria)
							.toArray(new BannerImpl[] {});

					for (BannerImpl banner : banners) {
						criteria = new Object[] { "entityid",
								banner.getCampainId(), "entityclass",
								BannerImpl.class.getName() };
						trs = hibernateEntityDao.viewWithCriteria(
								TotalsReport.class, criteria);

						if (trs.size() > 0) {
							tr = (TotalsReport) trs.toArray()[0];
							tr.reset();
							hibernateEntityDao.updateObject(tr);
						}
					}
				}
			}

			criteria = new Object[] { "userId", userId };

			SiteImpl[] sites = (SiteImpl[]) hibernateEntityDao
					.viewWithCriteria(SiteImpl.class, criteria).toArray(
							new SiteImpl[] {});

			for (SiteImpl site : sites) {
				criteria = new Object[] { "entityid", site.getId(),
						"entityclass", SiteImpl.class.getName() };
				trs = hibernateEntityDao.viewWithCriteria(TotalsReport.class,
						criteria);

				if (trs.size() > 0) {
					tr = (TotalsReport) trs.toArray()[0];
					tr.reset();
					hibernateEntityDao.updateObject(tr);
					criteria = new Object[] { "siteId", site.getId() };

					PlacesImpl[] places = (PlacesImpl[]) hibernateEntityDao
							.viewWithCriteria(PlacesImpl.class, criteria)
							.toArray(new PlacesImpl[] {});

					for (PlacesImpl place : places) {
						criteria = new Object[] { "entityid", place.getId(),
								"entityclass", PlacesImpl.class.getName() };
						trs = hibernateEntityDao.viewWithCriteria(
								TotalsReport.class, criteria);

						if (trs.size() > 0) {
							tr = (TotalsReport) trs.toArray()[0];
							tr.reset();
							hibernateEntityDao.updateObject(tr);
						}
					}
				}
			}
		} else {
		}
	}

	public void resetTotalsByCampaignId(Integer campaignId, UserImpl user) {
		CampainImpl campaign = (CampainImpl) hibernateEntityDao.loadObject(
				CampainImpl.class, campaignId);
		Object[] criteria = new Object[] { "entityid", campaignId,
				"entityclass", CampainImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport campaignTr = (TotalsReport) trs.toArray()[0];
			subtractFromUser(campaign.getUserId(), campaignTr);
			campaignTr.reset();
			hibernateEntityDao.updateObject(campaignTr);
			hibernateEntityDao.sendUpdateEventToAdserver(campaignTr.getClass(),
					((IMappable) campaignTr).getId(), ("&"
							+ AdsapientConstants.CAMPAIGNID_REQUEST_PARAM_NAME
							+ "=" + campaignId));

			if (campaign != null) {
				for (BannerImpl banner : campaign.getBanners()) {
					criteria = new Object[] { "entityid", banner.getId(),
							"entityclass", BannerImpl.class.getName() };
					trs = hibernateEntityDao.viewWithCriteria(
							TotalsReport.class, criteria);

					if (trs.size() > 0) {
						TotalsReport bannerTr = (TotalsReport) trs.toArray()[0];
						bannerTr.reset();
						hibernateEntityDao.updateObject(bannerTr);
					}
				}
			}
		} else {
		}
	}

	public void resetTotalsBySiteId(Integer siteId, UserImpl user) {
		SiteImpl site = (SiteImpl) hibernateEntityDao.loadObject(
				SiteImpl.class, siteId);
		Object[] criteria = new Object[] { "entityid", siteId, "entityclass",
				SiteImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport siteTr = (TotalsReport) trs.toArray()[0];
			subtractFromUser(site.getUserId(), siteTr);
			siteTr.reset();
			hibernateEntityDao.updateObject(siteTr);
			hibernateEntityDao.sendUpdateEventToAdserver(siteTr.getClass(),
					((IMappable) siteTr).getId(), ("&"
							+ AdsapientConstants.SITEID_REQUEST_PARAM_NAME
							+ "=" + siteId));

			if (site != null) {
				for (PlacesImpl place : site.getRealPlaces()) {
					criteria = new Object[] { "entityid", place.getId(),
							"entityclass", PlacesImpl.class.getName() };
					trs = hibernateEntityDao.viewWithCriteria(
							TotalsReport.class, criteria);

					if (trs.size() > 0) {
						TotalsReport placeTr = (TotalsReport) trs.toArray()[0];
						placeTr.reset();
						hibernateEntityDao.updateObject(placeTr);
					}
				}
			}
		} else {
		}
	}

	public void resetTotalsByBannerId(Integer bannerId, UserImpl user) {
		BannerImpl banner = (BannerImpl) hibernateEntityDao.loadObject(
				BannerImpl.class, bannerId);
		Object[] criteria = new Object[] { "entityid", bannerId, "entityclass",
				BannerImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport tr = (TotalsReport) trs.toArray()[0];
			subtractFromUser(banner.getUserId(), tr);
			subtractFromCampaign(banner.getCampainId(), tr);
			tr.reset();
			hibernateEntityDao.updateObject(tr);
			hibernateEntityDao.sendUpdateEventToAdserver(tr.getClass(),
					((IMappable) tr).getId(), ("&"
							+ AdsapientConstants.BANNERID_REQUEST_PARAM_NAME
							+ "=" + bannerId));
		} else {
		}
	}

	public void resetTotalsByPlaceId(Integer placeId, UserImpl user) {
		PlacesImpl place = (PlacesImpl) hibernateEntityDao.loadObject(
				PlacesImpl.class, placeId);
		Object[] criteria = new Object[] { "entityid", placeId, "entityclass",
				PlacesImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport placeTr = (TotalsReport) trs.toArray()[0];
			subtractFromUser(place.getUserId(), placeTr);
			subtractFromSite(place.getSiteId(), placeTr);
			placeTr.reset();
			hibernateEntityDao.updateObject(placeTr);
			hibernateEntityDao.sendUpdateEventToAdserver(placeTr.getClass(),
					((IMappable) placeTr).getId(), ("&"
							+ AdsapientConstants.PLACEID_REQUEST_PARAM_NAME
							+ "=" + place.getId()));
		} else {
		}
	}

	private void subtractFromUser(Integer userId, TotalsReport tr) {
		UserImpl u = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
				userId);
		Object[] criteria = new Object[] { "entityid", u.getId(),
				"entityclass", UserImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport userTr = (TotalsReport) trs.toArray()[0];
			userTr.subtract(tr);

			hibernateEntityDao.updateObject(userTr);
		}
	}

	private void subtractFromCampaign(Integer campaignId, TotalsReport tr) {
		CampainImpl c = (CampainImpl) hibernateEntityDao.loadObject(
				CampainImpl.class, campaignId);
		Object[] criteria = new Object[] { "entityid", c.getId(),
				"entityclass", CampainImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport campaignTr = (TotalsReport) trs.toArray()[0];
			campaignTr.subtract(tr);
			hibernateEntityDao.updateObject(campaignTr);
		}
	}

	private void subtractFromSite(Integer siteId, TotalsReport tr) {
		SiteImpl s = (SiteImpl) hibernateEntityDao.loadObject(SiteImpl.class,
				siteId);
		Object[] criteria = new Object[] { "entityid", s.getId(),
				"entityclass", SiteImpl.class.getName() };
		Collection trs = hibernateEntityDao.viewWithCriteria(
				TotalsReport.class, criteria);

		if (trs.size() > 0) {
			TotalsReport siteTr = (TotalsReport) trs.toArray()[0];
			siteTr.subtract(tr);
			hibernateEntityDao.updateObject(siteTr);
		}
	}

    public CookieManagementService getCookieManagementService() {
        return cookieManagementService;
    }

    public void setCookieManagementService(CookieManagementService cookieManagementService) {
        this.cookieManagementService = cookieManagementService;
    }
}
