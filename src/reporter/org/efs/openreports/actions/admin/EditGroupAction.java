/*
 * Copyright (C) 2002 Erik Swenson - eswenson@opensourcesoft.net
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 */

package org.efs.openreports.actions.admin;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

import org.efs.openreports.ORStatics;
import org.efs.openreports.objects.Report;
import org.efs.openreports.objects.ReportGroup;
import org.efs.openreports.objects.ReportUser;
import org.efs.openreports.providers.*;

public class EditGroupAction extends ActionSupport implements ReportProviderAware, GroupProviderAware, UserProviderAware
{
	private String command;
	private String submitType;

	private int id;
	private String name;
	private String description;

	private ReportGroup reportGroup;
	private int[] reportIds;

	private GroupProvider groupProvider;
	private ReportProvider reportProvider;
	private UserProvider userProvider;

	public String execute()
	{
		try
		{
			if (command.equals("edit"))
			{
				reportGroup = groupProvider.getReportGroup(new Integer(id));
			}
			else
			{
				reportGroup = new ReportGroup();
			}

			if (command.equals("edit") && submitType == null)
			{
				name = reportGroup.getName();
				description = reportGroup.getDescription();
				reportIds = null;
			}

			if (submitType == null)
				return INPUT;

			reportGroup.setName(name);
			reportGroup.setDescription(description);
			reportGroup.setReports(convertIdsToReports(reportIds));

			if (command.equals("edit"))
			{
				groupProvider.updateReportGroup(reportGroup);
			}

			if (command.equals("add"))
			{
				groupProvider.insertReportGroup(reportGroup);
			}
			
			// refresh current user 
			ReportUser user = (ReportUser) ActionContext.getContext().getSession().get(ORStatics.REPORT_USER);		
			if (user != null)
			{
				user = userProvider.getUser(user.getId());
				ActionContext.getContext().getSession().put(ORStatics.REPORT_USER, user);
			}

			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionError(e.toString());
			return INPUT;
		}
	}

	public String getCommand()
	{
		return command;
	}

	public String getDescription()
	{
		return description;
	}

	public String getName()
	{
		return name;
	}

	public String getSubmitType()
	{
		return submitType;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSubmitType(String submitType)
	{
		this.submitType = submitType;
	}

	public List getReports()
	{
		try
		{
			return reportProvider.getReports();
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			return null;
		}
	}

	public List getReportsInGroup()
	{
		return reportGroup.getReports();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int[] getReportIds()
	{
		return reportIds;
	}

	public void setReportIds(int[] reportIds)
	{
		this.reportIds = reportIds;
	}

	private List convertIdsToReports(int[] ids)
	{
		if (ids == null)
			return null;

		List reports = new ArrayList();

		try
		{
			for (int i = 0; i < ids.length; i++)
			{
				Report report = reportProvider.getReport(new Integer(ids[i]));
				reports.add(report);
			}
		}
		catch (Exception e)
		{
			addActionError(e.toString());
		}

		return reports;
	}

	public void setGroupProvider(GroupProvider groupProvider)
	{
		this.groupProvider = groupProvider;
	}

	public void setReportProvider(ReportProvider reportProvider)
	{
		this.reportProvider = reportProvider;
	}

	public void setUserProvider(UserProvider userProvider)
	{
		this.userProvider = userProvider;
	}

}