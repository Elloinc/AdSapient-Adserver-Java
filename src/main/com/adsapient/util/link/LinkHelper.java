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

import com.adsapient.api.Banner;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.managment.DefaultCampainManager;
import com.adsapient.api_impl.managment.plugin.BannerPluginFactory;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.resource.ResourceImpl;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.share.Type;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.maps.Place2SiteMap;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LinkHelper {
	static Logger logger = Logger.getLogger(LinkHelper.class);

	public static String getHrefByBannerId(Integer bannerId) {
		if (bannerId == null) {
			return AdsapientConstants.EMPTY;
		}

		Banner banner = BannerPluginFactory.loadBannerById(bannerId);

		return banner.getHref();
	}

	public static String getHrefByResourceId(String resourceId) {
		String url = "";
		StringBuffer resultLink = new StringBuffer();

		ResourceImpl res = (ResourceImpl) MyHibernateUtil.loadObject(
				ResourceImpl.class, new Integer(resourceId));

		if (res != null) {
			url = res.getURL();

			if ((Type.HTML_TYPE_ID.equals(res.getTypeId()))) {
				resultLink
						.append("<iframe src=\"image?resourceId=")
						.append(res.getResourceId())
						.append("\" width=")
						.append("600")
						.append("  height=")
						.append("100")
						.append(
								" "
										+ "marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" scrolling=\"yes\"></iframe>");

				return resultLink.toString();
			}

			if (Type.IMAGE_TYPE_ID.equals(res.getTypeId())) {
				resultLink.append("<img src=\"image?resourceId=").append(
						res.getResourceId()).append("\"").append(" border=0")
						.append("/>");

				return resultLink.toString();
			}

			logger.warn("Can't find  resource type= " + res.getTypeId());
		}

		logger.warn("Can't find  resource  it id= " + res.getResourceId());

		return resultLink.toString();
	}

	public static String getHrefBySizeId(String sizeId) {
		StringBuffer resultLink = new StringBuffer();

		Size size = (Size) MyHibernateUtil.loadObject(Size.class, new Integer(
				sizeId));

		if (size != null) {
			BannerImpl banner = DefaultCampainManager
					.getBannerFromSystemDefaultCampaignBySizeId(new Integer(
							sizeId));

			if (banner != null) {
				return getHrefByBannerId(banner.getBannerId());
			} else {
				logger.error("Cant load  banner  for size =" + sizeId);
			}
		} else {
			logger.error("Cant load size with id=" + sizeId);
		}

		return resultLink.toString();
	}

	public static String getUserName(HttpSession session) {
		StringBuffer userName = new StringBuffer("");
		UserImpl userFromHeder = (UserImpl) session.getAttribute("user");

		if (userFromHeder != null) {
			userName.append("(").append(userFromHeder.getRole()).append(")");
		}

		return userName.toString();
	}

	public static String createRunTimeBannerLink(Collection banners,
			Integer placesId, HttpServletRequest request) {
		if (banners.size() == 0) {
			return "";
		}

		BannerImpl banner = (BannerImpl) banners.iterator().next();
		StringBuffer buffer = new StringBuffer();

		Size bannerSize = (Size) MyHibernateUtil.loadObject(Size.class, banner
				.getSizeId());

		PlacesImpl places = Place2SiteMap.getPlaceFromId(placesId);

		if (places == null) {
			return createRunTimeBannerLink(banner, bannerSize, placesId,
					request);
		}

		if ((places.getRowCount() == 1) && (places.getColumnCount() == 1)) {
			return createRunTimeBannerLink(banner, bannerSize, placesId,
					request);
		}

		Iterator bannersIterator = banners.iterator();

		buffer
				.append(
						"<html> <head><meta HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\">")
				.append("</head> <body>");

		buffer.append("<table border=0 cellspacing=0 cellpadding=0>");

		for (int rowCount = 0; rowCount < places.getRowCount(); rowCount++) {
			buffer.append("<tr>");

			for (int columnsCount = 0; columnsCount < places.getColumnCount(); columnsCount++) {
				buffer.append("<td>");

				if (bannersIterator.hasNext()) {
					BannerImpl bannerImpl = (BannerImpl) bannersIterator.next();
					buffer.append(createRunTimeBannerLink(bannerImpl,
							bannerSize, placesId, request));
				} else {
				}

				buffer.append("</td>");
			}

			buffer.append("</tr>");
		}

		buffer.append("</table>");
		buffer.append("</body>");
		buffer.append("</html>");

		return buffer.toString();
	}

	public static String transformRedirectedURL(String url, Integer bannerId,
			Integer placeId) {
		StringBuffer buffer = new StringBuffer(url);

		return buffer.toString();
	}

	private static String createRunTimeBannerLink(BannerImpl banner,
			Size bannerSize, Integer placeId, HttpServletRequest request) {
		StringBuffer buf = new StringBuffer();
		String target;
		PlacesImpl places = (PlacesImpl) Place2SiteMap.getPlaceFromId(placeId);

		if ((places != null)
				&& (places.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_SELF)) {
			target = "_top";
		} else {
			target = "_blank";
		}

		StringBuffer additionalScript = new StringBuffer();

		additionalScript
				.append(
						"<script language=\"JavaScript\" type=\"text/javascript\" ")
				.append(">")
				.append("var rnd = Math.round(Math.random() * 10000000);")
				.append("var im='timeRegister?statisticId=")
				.append(banner.getStatisticId())
				.append("';")
				.append(
						"document.writeln('<SC'+'RIPT language=JavaScript type=text/javascript>');")
				.append(
						"document.writeln('var fun'+rnd+' = window.onunload;');")
				.append(
						"document.writeln('window.onunload = ads_fun'+rnd+';');")
				.append("document.writeln('function ads_fun'+rnd+'() {');")
				.append("document.writeln('document.getElementById(\"ads")
				.append(banner.getStatisticId()).append("\").src=im;');")
				.append("document.writeln('if (fun'+rnd+') fun'+rnd+'();}');")
				.append("document.writeln('</SC'+'RIPT>');")
				.append("</script>");

		String ADS = "ads" + banner.getStatisticId();

		if (Type.FLESH_TYPE_ID.equals(banner.getTypeId())) {
			buf.append("<EMBED  id=\"").append(ADS).append(
					"\"  src=\"image?target=").append(target).append(
					"&bannerId=").append(banner.getBannerId()).append(
					"&clickTAG=http://").append(request.getServerName())
					.append(":").append(request.getServerPort()).append(
							request.getContextPath()).append(
							"/campainRedirect?placeId=").append(placeId)
					.append("_bannerId=").append(banner.getBannerId()).append(
							"\" , WIDTH=").append(bannerSize.getWidth())
					.append(" , HEIGHT=").append(bannerSize.getHeight())
					.append(">").append(additionalScript);

			return buf.toString();
		}

		if (Type.IMAGE_TYPE_ID.equals(banner.getTypeId())) {
			buf.append("<a   href=\"campainRedirect?&bannerId=").append(
					banner.getBannerId()).append("&placeId=").append(placeId)
					.append("\" target=\"").append(target).append("\">");
			buf
					.append("<img id=\"")
					.append(ADS)
					.append("\"  src=\"image?bannerId=")
					.append(banner.getBannerId())
					.append("\" ")
					.append(" width=")
					.append(bannerSize.getWidth())
					.append(" height=")
					.append(bannerSize.getHeight())
					.append(" alt=\'")
					.append(banner.getAltText())
					.append("\' title=\'")
					.append(banner.getAltText())
					.append("\' onMouseover=\"window.status=\'")
					.append(banner.getStatusBartext())
					.append(
							" \';return true;\" onMouseout=\"window.status=window.defaultStatus;return true;\"")
					.append(" border=0>");
			buf.append("</a>").append(additionalScript);

			return buf.toString();
		}

		if (Type.HTML_TYPE_ID.equals(banner.getTypeId())) {
			buf
					.append("<iframe id=\"")
					.append(ADS)
					.append("\" src=\"image?bannerId=")
					.append(banner.getBannerId())
					.append("&")
					.append(AdsapientConstants.TARGET_PARAMETER)
					.append("=")
					.append(target)
					.append("&")
					.append(AdsapientConstants.HREF_PARAMETER)
					.append("=")
					.append("campainRedirect?placeId=")
					.append(placeId)
					.append("_")
					.append("bannerId=")
					.append(banner.getBannerId())
					.append("\" width=")
					.append(bannerSize.getWidth())
					.append("  height=")
					.append(bannerSize.getHeight())
					.append(
							" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" scrolling=\"no\"></iframe>");

			return buf.toString();
		}

		logger.warn("cant resolve banner typeId given type is "
				+ banner.getTypeId());

		return null;
	}
}
