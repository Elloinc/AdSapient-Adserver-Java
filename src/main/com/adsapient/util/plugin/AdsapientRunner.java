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
package com.adsapient.util.plugin;

import com.adsapient.api_impl.facade.AdsapientSystemFasade;
import com.adsapient.api_impl.managment.DefaultCampainManager;
import com.adsapient.api_impl.managment.application.ApplicationManagment;
import com.adsapient.api_impl.managment.plugin.PluginManager;

import com.adsapient.util.AdSapientHibernateService;
import com.adsapient.util.replication.ReplicationManager;
import com.adsapient.util.security.SecureCommunicator;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;

public class AdsapientRunner implements PlugIn {
	private static Logger logger = Logger.getLogger(AdsapientRunner.class);

	public void destroy() {
		AdsapientSystemFasade.shutdownTasks();

		logger.info("bye bye ...");
	}

	public void init(ActionServlet actionServlet, ModuleConfig config)
			throws ServletException {
		AdSapientHibernateService.rebuildSessionFactory();

		ApplicationInstaller.setup(actionServlet);

		PluginManager.init();

		System.setProperty("java.awt.headless", "true");

		ApplicationManagment.init();

		DefaultCampainManager.checkDefaultSystemCampain();

		ReplicationManager.start();

		SecureCommunicator.host = null;

	}
}
