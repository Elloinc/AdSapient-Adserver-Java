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

import com.adsapient.api.RateEnableInterface;
import com.adsapient.api.AdsapientException;
import com.adsapient.api.Banner;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.forms.FilterActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

public class LinkHelperService {
    static Logger logger = Logger.getLogger(LinkHelperService.class);
    private HibernateEntityDao hibernateEntityDao;

    private String pathToAdServer;
    private String pathToReporter;

    public String getAdCodeByBannerId(Integer bannerId) {
        return null;
    }

    public String getPlaceWithBannerByBannerId(Integer bannerId,
                                               HttpServletRequest request) {
        try {
            if (bannerId == null) {
                return "";
            }

            URL u = new URL(pathToAdServer
                    + "sv?count=false&eventId=1&bannerId=" + bannerId);
            BufferedReader in = new BufferedReader(new InputStreamReader(u
                    .openStream()));

            String inputLine;
            StringBuffer sb = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }

            in.close();

            return new String(sb.toString().getBytes(), "UTF-8");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return "";
    }

    public String getPlaceCodeByPlaceId(Integer placeId,
                                        HttpServletRequest request) {
        try {
            if (placeId == null) {
                return I18nService.fetch("htmlsource.notavailable", request);
            }

            URL u = new URL(pathToAdServer + "sv?eventId=1&placeId=" + placeId);
            BufferedReader in = new BufferedReader(new InputStreamReader(u
                    .openStream()));

            String inputLine;
            StringBuffer sb = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
                sb.append("\n");
            }

            in.close();

            return new String(sb.toString().getBytes(), "UTF-8").replaceAll(
                    "\\s\\s", " ");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return "";
    }

    public String getPathToAdServer() {
        return pathToAdServer;
    }

    public void setPathToAdServer(String pathToAdServer) {
        this.pathToAdServer = pathToAdServer;
    }

    public String generateCPLRates(HttpSession session,
                                          RateEnableInterface rateSupportObject, HttpServletResponse response) {
        StringBuffer result = new StringBuffer();
        RateImpl rate = (RateImpl) hibernateEntityDao.loadObject(RateImpl.class,
                rateSupportObject.getRateId());

        if (rate == null) {
            logger.error("Cant find rate with id="
                    + rateSupportObject.getRateId());

            return "N/A";
        }

        UserImpl parentUser = (UserImpl) session.getAttribute("parent");
        UserImpl user = (UserImpl) session.getAttribute("user");

        if ((parentUser == null)
                && (!AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole()))) {
            result.append(FinancialsService.transformMoney(rate.getCplRate()))
                    .append(" | ").append(
                            FinancialsService.transformMoney(rate.getCpsRate()));

            return result.toString();
        }

        result.append("<a class=\"tabledata\" href=\"").append(
                response.encodeURL("rateManagement.do?rateId="
                        + rate.getRateId())).append("\"  >").append(
                FinancialsService.transformMoney(rate.getCplRate())).append(
                " | ").append(
                FinancialsService.transformMoney(rate.getCpsRate())).append(
                "</a>");

        return result.toString();
    }

    public String generateFinancialLink(UserImpl user,
                                               HttpServletResponse response) {
        StringBuffer resultBuffer = new StringBuffer();

        Financial userFinancial = (Financial) hibernateEntityDao
                .loadObjectWithCriteria(Financial.class, "userId", user.getId());

        if (userFinancial == null) {
            resultBuffer
                    .append("<td colspan=\"4\" class=\"tabledata-c\"></td>");
            resultBuffer
                    .append("<td colspan=\"4\" class=\"tabledata-c\"></td>");

            return resultBuffer.toString();
        }

        if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())
                || AdsapientConstants.ADVERTISERPUBLISHER.equalsIgnoreCase(user
                        .getRole())
                || AdsapientConstants.HOSTEDSERVICE
                        .equalsIgnoreCase(user.getRole())) {
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPMrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPCrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPLrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPSrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPMrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPCrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPLrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPSrate()), user, response);
        } else if (AdsapientConstants.ADVERTISER.equalsIgnoreCase(user.getRole())) {
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPMrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPCrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPLrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getAdvertisingCPSrate()), user, response);
            resultBuffer
                    .append("<td colspan=\"4\" class=\"tabledata-c\"></td>");
        } else {
            resultBuffer
                    .append("<td colspan=\"4\" class=\"tabledata-c\"></td>");
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPMrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPCrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPLrate()), user, response);
            append(resultBuffer, FinancialsService.transformMoney(userFinancial
                    .getPublishingCPSrate()), user, response);
        }

        return resultBuffer.toString();
    }

    public String generateRates(HttpSession session,
                                       RateEnableInterface rateSupportObject, 
                                       HttpServletResponse response) {
        StringBuffer result = new StringBuffer();
        RateImpl rate = (RateImpl) hibernateEntityDao.loadObject(RateImpl.class,
                rateSupportObject.getRateId());

        if (rate == null) {
            logger.error("Cant find rate with id="
                    + rateSupportObject.getRateId());

            return "n/a";
        }

        UserImpl parentUser = (UserImpl) session
                .getAttribute(AdsapientConstants.PARENT_USER);
        UserImpl user = (UserImpl) session
                .getAttribute(AdsapientConstants.USER);

        if ((parentUser == null)
                && (!AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole()))) {
            result.append(FinancialsService.transformMoney(rate.getCpmRate()))
                    .append(" | ").append(
                            FinancialsService.transformMoney(rate.getCpcRate()));

            return result.toString();
        }

        result.append("<a class=\"tabledata\" href=\"").append(
                response.encodeURL("rateManagement.do?rateId="
                        + rate.getRateId())).append("\"  >").append(
                FinancialsService.transformMoney(rate.getCpmRate())).append(
                " | ").append(
                FinancialsService.transformMoney(rate.getCpcRate())).append(
                "</a>");

        return result.toString();
    }

    private static final void append(StringBuffer buffer, float data,
                                     UserImpl user, HttpServletResponse response) {
        buffer.append("<td class=\"tabledata-c\">").append(
                "<a class=\"tabledata\" href=\"").append(
                response.encodeURL("financialManagement.do?action=view&userId="
                        + user.getId())).append("\" >").append(data).append(
                "</a></td>");
    }

    public static boolean checkResetAccess(HttpServletRequest request) {
        UserImpl user = (UserImpl) request.getSession().getAttribute(
                AdsapientConstants.USER);

        if ((user != null)
                && (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole()))) {
            return true;
        }

        UserImpl parentUser = (UserImpl) request.getSession().getAttribute(
                AdsapientConstants.PARENT_USER);

        if ((parentUser != null)
                && (AdsapientConstants.ADMIN.equalsIgnoreCase(parentUser.getRole()))) {
            return true;
        }

        return false;
    }

    public static String generateAdsapientOptionsCollection(String fieldName,
                                                            String className, Collection labelsCollection) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<select name=\"").append("campainStateId").append(
                "\" class=\"").append("defType").append("\">");

        Iterator labelsCollectionIterator = labelsCollection.iterator();

        while (labelsCollectionIterator.hasNext()) {
            AdsapientLabelValueBean bean = (AdsapientLabelValueBean) labelsCollectionIterator
                    .next();

            buffer.append("<option value=\"").append(bean.getValue()).append(
                    "\"");

            if (bean.isSelected()) {
                buffer.append(" selected=\"selected\"");
            }

            buffer.append(" > ").append(bean.getLabel()).append("</option>");
        }

        buffer.append("</select>");

        return buffer.toString();
    }

    public static String generateFiltersHeader(FilterActionForm form,
                                               HttpServletRequest request, HttpServletResponse response)
            throws NumberFormatException, AdsapientException {
        StringBuffer resultHref = new StringBuffer();

        FiltersService filtersService = (FiltersService) ContextAwareGuiBean.getContext().getBean("filtersService");
        FiltersService filters = filtersService.createFakeFilters();
        Iterator filtersIterator = filters.getFiltersMap().entrySet()
                .iterator();

        while (filtersIterator.hasNext()) {
            Map.Entry filterElement = (Map.Entry) filtersIterator.next();

            String filterType = (String) filterElement.getKey();
            resultHref
                    .append("<td><table cellspacing=0 border=0 cellpadding=0><tr>");

            if (!form.getFilterType().equalsIgnoreCase(filterType)) {
                resultHref
                        .append("<td width=\"9\" height=\"18\" ")
                        .append(
                                "style=\"background-image: url(images/tab4.gif);\"></td>")
                        .append(
                                "<td class=\"tabledata\" style=\"background-image: url(images/tab5.gif);\">");

                StringBuffer sbUrl = new StringBuffer("filter.do?filterType=")
                        .append(filterType).append("&campainId=").append(
                                form.getCampainId());

                if ((form.getBannerId() != null)
                        && (form.getBannerId().length() > 0)) {
                    sbUrl.append("&bannerId=").append(form.getBannerId());
                }

                resultHref
                        .append("<nobr>&nbsp;&nbsp;&nbsp;")
                        .append("<a class=\"tabdata\" href=\"")
                        .append(response.encodeURL(sbUrl.toString()))
                        .append("\">")
                        .append(I18nService.fetch(filterType, request))
                        .append("</a>&nbsp;&nbsp;&nbsp;</nobr></td>")
                        .append(
                                "<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
                        .append("</tr></table></td>");
            } else {
                resultHref
                        .append("<td width=\"12\" height=\"18\" ")
                        .append(
                                "style=\"background-image: url(images/tab1.gif);\"></td>")
                        .append(
                                "<td class=\"tabledata\" style=\"background-image: url(images/tab2.gif);\">");
                resultHref
                        .append("<nobr>&nbsp;&nbsp;&nbsp;")
                        .append(I18nService.fetch(filterType, request))
                        .append("&nbsp;&nbsp;&nbsp;</nobr></td>")
                        .append(
                                "<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
                        .append("</tr></table></td>");
            }
        }

        return resultHref.toString();
    }

    public static String generateFiltersTempletHeader(FilterActionForm form,
                                                      HttpServletRequest request, HttpServletResponse response)
            throws NumberFormatException, AdsapientException {
        StringBuffer resultHref = new StringBuffer();

        FiltersService filtersService = (FiltersService) ContextAwareGuiBean.getContext().getBean("filtersService");
        FiltersService filters = filtersService.createFakeFilters();
        Iterator filtersIterator = filters.getFiltersMap().entrySet()
                .iterator();

        while (filtersIterator.hasNext()) {
            Map.Entry filterElement = (Map.Entry) filtersIterator.next();

            String filterType = (String) filterElement.getKey();
            resultHref
                    .append("<td><table cellspacing=0 border=0 cellpadding=0><tr>");

            if (!form.getFilterType().equalsIgnoreCase(filterType)) {
                resultHref
                        .append("<td width=\"9\" height=\"18\" ")
                        .append(
                                "style=\"background-image: url(images/tab4.gif);\"></td>")
                        .append(
                                "<td class=\"tabledata\" style=\"background-image: url(images/tab5.gif);\">");
                resultHref
                        .append("<nobr>&nbsp;&nbsp;&nbsp;")
                        .append("<a class=\"tabdata\" href=\"")
                        .append(
                                response
                                        .encodeURL("filtersTemplate.do?filterType="
                                                + filterType))
                        .append("\">")
                        .append(I18nService.fetch(filterType, request))
                        .append("</a>&nbsp;&nbsp;&nbsp;</nobr></td>")
                        .append(
                                "<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
                        .append("</tr></table></td>");
            } else {
                resultHref
                        .append("<td width=\"12\" height=\"18\" ")
                        .append(
                                "style=\"background-image: url(images/tab1.gif);\"></td>")
                        .append(
                                "<td class=\"tabledata\" style=\"background-image: url(images/tab2.gif);\">");
                resultHref
                        .append("<nobr>&nbsp;&nbsp;&nbsp;")
                        .append(I18nService.fetch(filterType, request))
                        .append("&nbsp;&nbsp;&nbsp;</nobr></td>")
                        .append(
                                "<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
                        .append("</tr></table></td>");
            }
        }

        return resultHref.toString();
    }

//    public static String getHrefByBannerId(Integer bannerId) {
//        if (bannerId == null) {
//            return AdsapientConstants.EMPTY;
//        }
//
//        Banner banner = PluginService.loadBannerById(bannerId);
//
//        return banner.getHref();
//    }

    public String getHrefByResourceId(String resourceId) {
        String url = "";
        StringBuffer resultLink = new StringBuffer();

        ResourceImpl res = (ResourceImpl) hibernateEntityDao.loadObject(
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

//    public String getHrefBySizeId(String sizeId) {
//        StringBuffer resultLink = new StringBuffer();
//
//        Size size = (Size) hibernateEntityDao.loadObject(Size.class, new Integer(
//                sizeId));
//
//        if (size != null) {
//            BannerImpl banner = CampaignService
//                    .getBannerFromSystemDefaultCampaignBySizeId(new Integer(
//                            sizeId));
//
//            if (banner != null) {
//                return getHrefByBannerId(banner.getBannerId());
//            } else {
//                logger.error("Cant load  banner  for size =" + sizeId);
//            }
//        } else {
//            logger.error("Cant load size with id=" + sizeId);
//        }
//
//        return resultLink.toString();
//    }

    public static String getUserName(HttpSession session) {
        StringBuffer userName = new StringBuffer("");
        UserImpl userFromHeder = (UserImpl) session.getAttribute("user");

        if (userFromHeder != null) {
            userName.append("(").append(userFromHeder.getRole()).append(")");
        }

        return userName.toString();
    }

    public String createRunTimeBannerLink(Collection banners,
                                                 Integer placesId, HttpServletRequest request) {
        if (banners.size() == 0) {
            return "";
        }

        BannerImpl banner = (BannerImpl) banners.iterator().next();
        StringBuffer buffer = new StringBuffer();

        Size bannerSize = (Size) hibernateEntityDao.loadObject(Size.class, banner
                .getSizeId());

        PlacesImpl places = null;//Place2SiteMap.getPlaceFromId(placesId);

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
        PlacesImpl places = null;//(PlacesImpl) Place2SiteMap.getPlaceFromId(placeId);

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

    public String generatePlaceAdCode(Integer placesId,
                                             HttpServletRequest request) {
        PlacesImpl places = (PlacesImpl) hibernateEntityDao.loadObject(
                PlacesImpl.class, placesId);

        Size placeSize = (Size) hibernateEntityDao.loadObject(Size.class, places
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

    public String getPathToReporter() {
        return pathToReporter;
    }

    public void setPathToReporter(String pathToReporter) {
        this.pathToReporter = pathToReporter;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
