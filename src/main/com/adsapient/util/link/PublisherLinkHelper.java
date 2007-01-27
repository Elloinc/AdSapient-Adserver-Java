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
package com.adsapient.util.link;

import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.share.Size;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import javax.servlet.http.HttpServletRequest;

public class PublisherLinkHelper {
	public static String generatePlaceAdCode(Integer placesId,
			HttpServletRequest request) {
		PlacesImpl places = (PlacesImpl) MyHibernateUtil.loadObject(
				PlacesImpl.class, placesId);

		Size placeSize = (Size) MyHibernateUtil.loadObject(Size.class, places
				.getSizeId());

		if (places.getLoadingTypeId() == AdsapientConstants.LOADING_TYPE_IMMEDIATE) {
			if (places.getPlaceTypeId() == AdsapientConstants.PLACE_TYPE_ORDINARY) {
				StringBuffer buf = new StringBuffer();

				buf
						.append("<iframe src=\"http://")
						.append(request.getServerName())
						.append(":")
						.append(request.getServerPort())
						.append(request.getContextPath())
						.append("/mapping2?placeId=")
						.append(placesId)
						.append(
								"\"  marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" scrolling=\"no\"")
						.append(" width=").append(
								placeSize.getWidth() * places.getColumnCount())
						.append(" height=").append(
								placeSize.getHeight() * places.getRowCount())
						.append("></iframe>");

				return buf.toString();
			}

			if (places.getPlaceTypeId() == AdsapientConstants.PLACE_TYPE_POPUP) {
				StringBuffer buf = new StringBuffer();
				buf
						.append(
								"<script language=\"JavaScript\" type=\"text/javascript\">")
						.append(
								"document.writeln('<SC'+'RIPT language=JavaScript type=text/javascript>');")
						.append("document.writeln(\"open(\'http://")
						.append(request.getServerName())
						.append(":")
						.append(request.getServerPort())
						.append(request.getContextPath())
						.append("/mapping2?placeId=")
						.append(placesId)
						.append(
								"\',\'_blank\',\'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=468,height=60,left=200,top=200\');\");")
						.append("document.writeln('</SC'+'RIPT>');").append(
								"app</script>");

				return buf.toString();
			}

			if (places.getPlaceTypeId() == AdsapientConstants.PLACE_TYPE_POPUNDER) {
				StringBuffer buf = new StringBuffer();
				buf
						.append(
								"<script language=\"JavaScript\" type=\"text/javascript\">")
						.append(
								"document.writeln('<SC'+'RIPT language=JavaScript type=text/javascript>');")
						.append("document.writeln('open(\'http://")
						.append(request.getServerName())
						.append(":")
						.append(request.getServerPort())
						.append(request.getContextPath())
						.append("/mapping2?placeId=")
						.append(placesId)
						.append(
								"\',\'_blank\',\'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=468,height=60,left=200,top=200\');');")
						.append("document.writeln('focus();');").append(
								"document.writeln('</SC'+'RIPT>');").append(
								"app</script>");

				return buf.toString();
			}
		} else {
			String loadEvent = (places.getLoadingTypeId() == AdsapientConstants.LOADING_TYPE_ON_PAGE_LOAD) ? "window.onload"
					: "window.onunload";

			if (places.getPlaceTypeId() == AdsapientConstants.PLACE_TYPE_POPUNDER) {
				StringBuffer buf = new StringBuffer();

				buf
						.append(
								"<script language=\"JavaScript\" type=\"text/javascript\">")
						.append(
								"var rnd = Math.round(Math.random() * 10000000);")
						.append(
								"document.writeln('<SC'+'RIPT language=JavaScript type=text/javascript>');")
						.append(
								"document.writeln('var fun'+rnd+' = "
										+ loadEvent + ";');")
						.append(
								"document.writeln('" + loadEvent
										+ " = ads_fun'+rnd+';');")
						.append(
								"document.writeln('function ads_fun'+rnd+'() {');")
						.append("document.writeln(\"open(\'http://")
						.append(request.getServerName())
						.append(":")
						.append(request.getServerPort())
						.append(request.getContextPath())
						.append("/mapping2?placeId=")
						.append(placesId)
						.append(
								"\',\'_blank\',\'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=468,height=60,left=200,top=200\');\");")
						.append("document.writeln('focus();');")
						.append(
								"document.writeln('if (fun'+rnd+') fun'+rnd+'();}');")
						.append("document.writeln('</SC'+'RIPT>');").append(
								"</script>");

				return buf.toString();
			}

			if (places.getPlaceTypeId() == AdsapientConstants.PLACE_TYPE_POPUP) {
				StringBuffer buf = new StringBuffer();
				buf
						.append(
								"<script language=\"JavaScript\" type=\"text/javascript\">")
						.append(
								"var rnd = Math.round(Math.random() * 10000000);")
						.append(
								"document.writeln('<SC'+'RIPT language=JavaScript type=text/javascript>');")
						.append(
								"document.writeln('var fun'+rnd+' = "
										+ loadEvent + ";');")
						.append(
								"document.writeln('" + loadEvent
										+ " = ads_fun'+rnd+';');")
						.append(
								"document.writeln('function ads_fun'+rnd+'() {');")
						.append("document.writeln(\"open(\'http://")
						.append(request.getServerName())
						.append(":")
						.append(request.getServerPort())
						.append(request.getContextPath())
						.append("/mapping2?placeId=")
						.append(placesId)
						.append(
								"\',\'_blank\',\'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=468,height=60,left=200,top=200\');\");")
						.append(
								"document.writeln('if (fun'+rnd+') fun'+rnd+'();}');")
						.append("document.writeln('</SC'+'RIPT>');").append(
								"</script>");

				return buf.toString();
			}

			if (places.getPlaceTypeId() == AdsapientConstants.PLACE_TYPE_ORDINARY) {
				StringBuffer buf = new StringBuffer();

				buf
						.append(
								"<script language=\"JavaScript\" type=\"text/javascript\">")
						.append(
								"var rnd = Math.round(Math.random() * 10000000);")
						.append(
								"document.writeln('<SC'+'RIPT language=JavaScript type=text/javascript>');")
						.append(
								"document.writeln('var fun'+rnd+' = "
										+ loadEvent + ";');")
						.append(
								"document.writeln('" + loadEvent
										+ " = ads_fun'+rnd+';');")
						.append(
								"document.writeln('function ads_fun'+rnd+'() {');")
						.append(
								"document.writeln('document.writeln(\\'<html><body><iframe src=\"http://")
						.append(request.getServerName())
						.append(":")
						.append(request.getServerPort())
						.append(request.getContextPath())
						.append("/mapping2?placeId=")
						.append(placesId)
						.append(
								"\"  marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" scrolling=\"no\"")
						.append(" width=")
						.append(placeSize.getWidth())
						.append(" height=")
						.append(placeSize.getHeight())
						.append("></iframe>\\');');")
						.append(
								"document.writeln('if (fun'+rnd+') fun'+rnd+'();}');")
						.append("document.writeln('</SC'+'RIPT>');").append(
								"</script>");

				return buf.toString();
			}
		}

		return "not support";
	}
}
