/*
 * Copyright (C) 2005 Erik Swenson - erik@oreports.com
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

package org.efs.openreports.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.efs.openreports.ORStatics;
import org.efs.openreports.objects.ReportUser;
import org.efs.openreports.providers.UserProvider;
import org.efs.openreports.providers.UserProviderAware;
import org.efs.openreports.util.LocalStrings;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.interceptor.component.ComponentManager;

public class WebAppSecurityInterceptor implements Interceptor, UserProviderAware
{
	protected static Logger log = Logger.getLogger(WebAppSecurityInterceptor.class);

	private UserProvider userProvider;	

	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		ComponentManager container = (ComponentManager) ActionContext.getContext().get(

		"com.opensymphony.xwork.interceptor.component.ComponentManager");

		if (container != null)
		{
			container.initializeObject(this);
		}				
		
		ReportUser user = (ReportUser) actionInvocation.getInvocationContext().getSession().get("user");

		if (user == null)
		{
			HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
			if (httpServletRequest != null)
			{
				String userName = httpServletRequest.getRemoteUser();

				user = userProvider.getUser(userName);
				if (user != null &&  httpServletRequest.isUserInRole(ORStatics.OPENREPORTS_ROLE))
				{					
					actionInvocation.getInvocationContext().getSession().put("user", user);
				}
				else
				{
					ActionSupport action = (ActionSupport) actionInvocation.getAction();
					action.addActionError(LocalStrings.getString(LocalStrings.ERROR_INVALID_USER));

					return Action.ERROR;
				}
			}
		}

		ActionContext.getContext().getValueStack().push(this);

		return actionInvocation.invoke();
	}

	public void destroy()
	{
	}

	public void init()
	{		
	}

	public void setUserProvider(UserProvider userProvider)
	{
		this.userProvider = userProvider;
	}	
}