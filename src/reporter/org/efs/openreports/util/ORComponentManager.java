/*
 * Copyright (C) 2004 Erik Swenson - eswenson@opensourcesoft.net
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 */

package org.efs.openreports.util;

import java.io.IOException;
import java.io.InputStream;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.interceptor.component.*;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class ORComponentManager
{
	protected static Logger log = Logger.getLogger(ORComponentManager.class);

	/* 
	 * creates a new ComponentManager for objects that do not share an
	 * ActionContext with the OpenReports web application such as the
	 * ScheduleReportJob and the SampleDataGenerator
	 */
	public static void initializeObject(Object object)
	{
		ComponentManager container = (ComponentManager) ActionContext.getContext().get(
				"com.opensymphony.xwork.interceptor.component.ComponentManager");

		if (container == null)
		{
			log.info("Creating ComponentManager for: " + object.getClass().getName());

			container = new DefaultComponentManager();

			ComponentConfiguration config = loadConfiguration();
			config.configure(container, "application");

			ActionContext.getContext().put(
					"com.opensymphony.xwork.interceptor.component.ComponentManager", container);
		}

		container.initializeObject(object);
	}

	public static ComponentConfiguration loadConfiguration()
	{
		ComponentConfiguration config = new ComponentConfiguration();

		InputStream configXml = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("components.xml");

		if (configXml == null)
		{
			final String message = "Unable to find the file components.xml in the classpath.";
			log.error(message);
			throw new RuntimeException(message);
		}

		try
		{
			config.loadFromXml(configXml);
		}
		catch (IOException ioe)
		{
			log.error(ioe);
			throw new RuntimeException("Unable to load component configuration");
		}
		catch (SAXException sae)
		{
			log.error(sae);
			throw new RuntimeException("Unable to load component configuration");
		}

		return config;
	}

}