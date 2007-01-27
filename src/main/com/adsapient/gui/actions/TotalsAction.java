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
package com.adsapient.gui.actions;

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.shared.service.TotalsReportService;

import com.adsapient.util.Msg;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TotalsAction extends Action {
	static Logger logger = Logger.getLogger(TotalsAction.class);

	private TotalsReportService totalsReportService;

	public ActionForward execute(ActionMapping mapping, ActionForm theForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserImpl user = (UserImpl) request.getSession().getAttribute("user");
		Integer userId = ((request
				.getParameter(AdsapientConstants.USERID_REQUEST_PARAM_NAME) == null) ? null
				: Integer
						.parseInt(request
								.getParameter(AdsapientConstants.USERID_REQUEST_PARAM_NAME)));
		Integer bannerId = ((request
				.getParameter(AdsapientConstants.BANNERID_REQUEST_PARAM_NAME) == null) ? null
				: Integer
						.parseInt(request
								.getParameter(AdsapientConstants.BANNERID_REQUEST_PARAM_NAME)));
		Integer siteId = ((request
				.getParameter(AdsapientConstants.SITEID_REQUEST_PARAM_NAME) == null) ? null
				: Integer
						.parseInt(request
								.getParameter(AdsapientConstants.SITEID_REQUEST_PARAM_NAME)));
		Integer placeId = ((request
				.getParameter(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME) == null) ? null
				: Integer
						.parseInt(request
								.getParameter(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME)));
		Integer campaignId = ((request
				.getParameter(AdsapientConstants.CAMPAIGNID_REQUEST_PARAM_NAME) == null) ? null
				: Integer
						.parseInt(request
								.getParameter(AdsapientConstants.CAMPAIGNID_REQUEST_PARAM_NAME)));

		if (user != null) {
			if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
				if (userId == null) {
					throw new AdsapientSecurityException(Msg.fetch(
							"error.wrongpage", request));
				}

				totalsReportService.resetTotalsByUserId(userId, user);

				return mapping.findForward("userList");
			} else if (RoleController.ADVERTISER.equalsIgnoreCase(user
					.getRole())) {
				if ((campaignId == null) && (bannerId == null)) {
					throw new AdsapientSecurityException(Msg.fetch(
							"error.wrongpage", request));
				}

				if (campaignId != null) {
					totalsReportService.resetTotalsByCampaignId(campaignId,
							user);

					return mapping.findForward("campaignsList");
				}

				if (bannerId != null) {
					totalsReportService.resetTotalsByBannerId(bannerId, user);

					return mapping.findForward("bannersList");
				}
			} else if (RoleController.PUBLISHER
					.equalsIgnoreCase(user.getRole())) {
				if ((siteId == null) && (placeId == null)) {
					throw new AdsapientSecurityException(Msg.fetch(
							"error.wrongpage", request));
				}

				if (siteId != null) {
					totalsReportService.resetTotalsBySiteId(siteId, user);

					return mapping.findForward("sitesList");
				}

				if (placeId != null) {
					totalsReportService.resetTotalsByPlaceId(placeId, user);

					return mapping.findForward("placesList");
				}
			}
		} else {
			throw new AdsapientSecurityException(Msg.fetch("error.wrongpage",
					request));
		}

		return mapping.findForward("login");
	}

	public TotalsReportService getTotalsReportService() {
		return totalsReportService;
	}

	public void setTotalsReportService(TotalsReportService totalsReportService) {
		this.totalsReportService = totalsReportService;
	}
}
