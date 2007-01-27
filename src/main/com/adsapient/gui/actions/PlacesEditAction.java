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

import com.adsapient.api.AdsapientInterceptedSupportEntity;

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.managment.StatisticManager;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.PlacesEditActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PlacesEditAction extends SecureAction {
    static Logger logger = Logger.getLogger(PlacesEditAction.class);

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm theForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        UserImpl user;
        user = (UserImpl) request.getSession().getAttribute("user");

        PlacesEditActionForm form = (PlacesEditActionForm) theForm;
        form.setRequest(request);

        ActionMessages messages = new ActionMessages();

        if ("remove".equalsIgnoreCase(form.getPlaceAction())) {
            AdsapientInterceptedSupportEntity places = (PlacesImpl) MyHibernateUtil.loadObject(PlacesImpl.class,
                    form.getPlacesId());
            places.delete(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.removed"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("resetStatistic".equalsIgnoreCase(form.getAction())) {
            PlacesImpl placesImpl = (PlacesImpl) MyHibernateUtil.loadObject(PlacesImpl.class,
                    form.getPlacesId());

            if (placesImpl != null) {
                StatisticManager.remove(placesImpl);
                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.stat.reset"));
                saveMessages(request, messages);
            } else {
                logger.error(
                    "trying to remove statistic but cant load campaign with id=" +
                    form.getPlacesId());
            }

            return mapping.findForward("success");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity places = (PlacesImpl) MyHibernateUtil.loadObject(PlacesImpl.class,
                    form.getPlacesId());
            SiteImpl site = (SiteImpl) MyHibernateUtil.loadObject(SiteImpl.class,
                    ((PlacesImpl) places).getSiteId());
            form.setTypeId(site.getTypeId());
            form.setUrl(site.getUrl());
            places.view(form, request);

            return mapping.findForward("placeEdit");
        }

        if ("viewSitePlaces".equalsIgnoreCase(form.getAction())) {
            if (form.getSiteId() != null) {
                SiteImpl siteWithPlaces = (SiteImpl) MyHibernateUtil.loadObject(SiteImpl.class,
                        form.getSiteId());

                form.getSitesCollection().add(siteWithPlaces);

                form.setSelectedCategorysName(siteWithPlaces.getCategorys());

                form.setAction("init");
            }

            return mapping.findForward("viewSitePlacess");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity currentPlace = (PlacesImpl) MyHibernateUtil.loadObject(PlacesImpl.class,
                    form.getPlacesId());
            currentPlace.edit(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity places = new PlacesImpl();
            places.init(form, request);

            return mapping.findForward("placeAdd");
        }

        if ("add".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity places = new PlacesImpl();
            places.add(form, request);

            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
