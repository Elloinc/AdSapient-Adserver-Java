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

import com.adsapient.shared.mappable.PluginsImpl;
import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.exceptions.PluginInstallerExeption;
import com.adsapient.shared.service.PluginService;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.PluginManagerActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PluginManagerAction extends SecureAction {
    private static Logger logger = Logger.getLogger(PluginManagerAction.class);
    private HibernateEntityDao hibernateEntityDao;
    private PluginService pluginService;


    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        PluginManagerActionForm form = (PluginManagerActionForm) actionForm;

        ActionMessages messages = new ActionMessages();

        if ("del".equalsIgnoreCase(form.getAction())) {
            pluginService.unpluginAddon(form);
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.removed"));
            saveMessages(request, messages);
            form.setAction("list");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            PluginsImpl plugin = (PluginsImpl) hibernateEntityDao.loadObject(PluginsImpl.class,
                    form.getId());
            form.update(plugin);
            hibernateEntityDao.updateObject(plugin);
            form.setAction("list");
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);
        }

        if ("add".equalsIgnoreCase(form.getAction())) {
            try {
                PluginService.pluginAddon(form);
                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.plugin.added"));
                saveMessages(request, messages);
            } catch (PluginInstallerExeption ex) {
                messages.add(Globals.ERROR_KEY,
                    new ActionMessage(ex.getMessage(), false));
                saveErrors(request, messages);

                return mapping.findForward("edit");
            }

            form.setAction("list");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            form.setAction("edit");

            PluginsImpl plugin = (PluginsImpl) hibernateEntityDao.loadObject(PluginsImpl.class,
                    form.getId());
            form.init(plugin);

            return mapping.findForward("edit");
        }

        if ("list".equalsIgnoreCase(form.getAction())) {
            form.setAction("init");
            form.setCollection(hibernateEntityDao.viewWithCriteria(
                    PluginsImpl.class, "present", new Boolean(true), "id"));

            return mapping.findForward("list");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");

            return mapping.findForward("edit");
        }

        return mapping.findForward("edit");
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public PluginService getPluginService() {
        return pluginService;
    }

    public void setPluginService(PluginService pluginService) {
        this.pluginService = pluginService;
    }
}
