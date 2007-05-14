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
package com.adsapient.adserver;

import com.adsapient.adserver.reporter.ReporterScheduler;
import com.adsapient.adserver.requestprocessors.*;
import com.adsapient.shared.AdsapientConstants;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AdserverServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(AdserverServlet.class);

    public static ApplicationContext appContext;

    private GetPlaceCodeProcessor getPlaceCodeProcessor;

    private GetAdCodeProcessor getAdCodeProcessor;

    private GetAdProcessor getAdProcessor;

    private LeadSaleProcessor leadSaleProcessor;

    private ClickProcessor clickProcessor;

    private OnUnloadProcessor onUnloadProcessor;

    private UpdateEntityProcessor updateEntityProcessor;

    private AdserverModelBuilder adserverModelBuilder;

    private int requestsCounter = 0;

    private static AdserverServlet instance;

    private void bustCache(HttpServletResponse response) {
        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");

        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");

        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        response.setHeader("Pragma", "no-cache");

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "private");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "max-stale=0");
        response.setHeader("Cache-Control", "post-check=0");
        response.setHeader("Cache-Control", "pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Keep-Alive", "timeout=3, max=993");
        response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
    }

    private void invalidatePreviousSessions(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        session = request.getSession(true);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        ++requestsCounter;

        Map<String, Object> requestParams = adserverModelBuilder.buildParamsFromRequest(request, response);
        Byte eventType = (Byte) requestParams
                .get(AdsapientConstants.EVENT_TYPE_REQUEST_PARAM_KEY);

        switch (eventType) {
            case AdsapientConstants.GETPLACECODE_ADSERVEREVENT_TYPE:
                getPlaceCodeProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.GETADCODE_ADSERVEREVENT_TYPE:
                getAdCodeProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.GETAD_ADSERVEREVENT_TYPE:
                getAdProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.CLICK_ADSERVEREVENT_TYPE:
                clickProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.LEAD_ADSERVEREVENT_TYPE:
                leadSaleProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.SALE_ADSERVEREVENT_TYPE:
                leadSaleProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.ONUNLOAD_ADSERVEREVENT_TYPE:
                onUnloadProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.VIDEOAD_ADSERVEREVENT_TYPE:
                getAdCodeProcessor.doGet(requestParams, response);

                return;

            case AdsapientConstants.UPDATE_ENTITY_ADSERVEREVENT_TYPE:
                updateEntityProcessor.doGet(requestParams, response);

                return;

            default:
                logger.error("Unidentified event type:" + eventType);
                getAdCodeProcessor.doGet(requestParams, response);

                return;
        }
    }

    private String printOutRequestInfo(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");

        for (Object key : request.getParameterMap().keySet()) {
            String value = ((String[]) request.getParameterMap().get(key))[0];
            sb.append(key + ":" + value);
            sb.append("\n");
        }

        sb.append("****************************");

        return sb.toString();
    }

    public void init() throws ServletException {
        instance = this;
        if (appContext == null) {
            appContext = new ClassPathXmlApplicationContext(getFileList());
        }


        getPlaceCodeProcessor = (GetPlaceCodeProcessor) appContext
                .getBean("getPlaceCodeProcessor");
        getAdCodeProcessor = (GetAdCodeProcessor) appContext
                .getBean("getAdCodeProcessor");
        getAdProcessor = (GetAdProcessor) appContext.getBean("getAdProcessor");
        clickProcessor = (ClickProcessor) appContext.getBean("clickProcessor");
        leadSaleProcessor = (LeadSaleProcessor) appContext
                .getBean("leadSaleProcessor");
        onUnloadProcessor = (OnUnloadProcessor) appContext
                .getBean("onUnloadProcessor");
        adserverModelBuilder = (AdserverModelBuilder) appContext
                .getBean("adserverModelBuilder");
        updateEntityProcessor = (UpdateEntityProcessor) appContext
                .getBean("updateEntityProcessor");
    }

    public void destroy() {
        ReporterScheduler reporterScheduler = (ReporterScheduler) appContext
                .getBean("reporterScheduler");
        try {
            reporterScheduler.getSched().shutdown();
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected String[] getFileList() {
        return new String[]{getServletConfig().getInitParameter(
                "pathToContext")};
    }

    public int getRequestsCounter() {
        return requestsCounter;
    }

    public void setRequestsCounter(int requestsCounter) {
        this.requestsCounter = requestsCounter;
    }

    public static AdserverServlet getInstance() {
        return instance;
    }
}
