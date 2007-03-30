/*
 * Copyright (C) 2004 Erik Swenson - erik@oreports.com
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

package org.efs.openreports.actions;

import java.util.*;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

import org.efs.openreports.ORStatics;
import org.efs.openreports.objects.*;
import org.efs.openreports.providers.*;
import org.efs.openreports.util.LocalStrings;
import org.hibernate.HibernateException;

public class ExecuteReportAction extends ActionSupport
		implements
			ReportProviderAware,
			ParameterProviderAware,
			UserProviderAware
{
	private int reportId;
	private String reportName;
	private String exportType;
	private String userName;
	private String password;	
	private boolean displayInline;
	private boolean promptForParameters;

	private ParameterProvider parameterProvider;
	private ReportProvider reportProvider;
	private UserProvider userProvider;

	public String execute()
	{
		try
		{
			// get user, validate, and put in session
			ReportUser user = null;
			
			if (userName == null || userName.length() < 1)
			{
				user = (ReportUser) ActionContext.getContext().getSession().get(
					ORStatics.REPORT_USER);		
				if (user != null) password = user.getPassword();
			}
			else
			{
				user = userProvider.getUser(userName);
			}
						 
			if (user == null || !user.getPassword().equals(password))
			{
				addActionError(LocalStrings.getString(LocalStrings.ERROR_LOGIN_INVALID));
				return ERROR;
			}

			ActionContext.getContext().getSession().put(ORStatics.REPORT_USER, user);
						
			Report report = null;			
			if (reportName != null && reportName.length() > 0)
			{
				report = reportProvider.getReport(reportName);
			}
			else
			{
				report = reportProvider.getReport(new Integer(reportId));
			}

			if (report == null)
			{
				addActionError(LocalStrings.getString(LocalStrings.ERROR_REPORT_INVALID));
				return ERROR;
			}
			
			// validate user authorized to view report				
			if (!user.isValidReport(report))
			{
				addActionError(LocalStrings.getString(LocalStrings.ERROR_REPORT_NOTAUTHORIZED));
				return ERROR;
			}

			report.setDisplayInline(displayInline);
			ActionContext.getContext().getSession().put(ORStatics.REPORT, report);
			
			// get exportType, validate, and put in session

			if (exportType == null || exportType.length() < 1)
			{
				if (!report.isQueryReport() && !report.isChartReport())
				{
					addActionError(LocalStrings.getString(LocalStrings.ERROR_EXPORTTYPE_REQUIRED));
					return ERROR;
				}
			}

			ActionContext.getContext().getSession().put(ORStatics.EXPORT_TYPE, exportType);

			List reportParameters = report.getParameters();
			
			if (reportParameters != null && reportParameters.size() > 0
					&& promptForParameters)
			{
				return ORStatics.PROMPT_PARAMETERS_ACTION;
			}
			
			// vaidate parameters, create map of parameters, and put in session			

			parameterProvider.validateParameters(reportParameters, ActionContext.getContext()
					.getParameters());

			Map map = parameterProvider.getReportParametersMap(reportParameters, ActionContext
					.getContext().getParameters());
			map.put(ORStatics.USER_ID, user.getId());
			map.put(ORStatics.EXTERNAL_ID, user.getExternalId());
			map.put(ORStatics.USER_NAME, user.getName());

			ActionContext.getContext().getSession().remove(ORStatics.REPORT_PARAMETERS);
			ActionContext.getContext().getSession().put(ORStatics.REPORT_PARAMETERS, map);		
			
			if (report.isQueryReport()) return ORStatics.QUERY_REPORT_ACTION;
			if (report.isChartReport()) return ORStatics.CHART_REPORT_ACTION;
			if (report.isJXLSReport()) return ORStatics.JXLSREPORT_ACTION;
						
			return SUCCESS;
		}
		catch (ProviderException e)
		{
			if (e.getException() instanceof HibernateException)
			{
				addActionError(LocalStrings.getString(LocalStrings.ERROR_REPORT_INVALID));
				return ERROR;
			}
			
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	public int getReportId()
	{
		return reportId;
	}

	public void setReportId(int reportId)
	{
		this.reportId = reportId;
	}

	public String getExportType()
	{
		return exportType;
	}

	public void setExportType(String exportType)
	{
		this.exportType = exportType;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setParameterProvider(ParameterProvider parameterProvider)
	{
		this.parameterProvider = parameterProvider;
	}

	public void setReportProvider(ReportProvider reportProvider)
	{
		this.reportProvider = reportProvider;
	}

	public void setUserProvider(UserProvider userProvider)
	{
		this.userProvider = userProvider;
	}

	public String getReportName()
	{
		return reportName;
	}

	public void setReportName(String reportName)
	{
		this.reportName = reportName;
	}

	public boolean isDisplayInline()
	{
		return displayInline;
	}

	public void setDisplayInline(boolean displayInline)
	{
		this.displayInline = displayInline;
	}

	public boolean isPromptForParameters()
	{
		return promptForParameters;
	}

	public void setPromptForParameters(boolean promptForParameters)
	{
		this.promptForParameters = promptForParameters;
	}
}