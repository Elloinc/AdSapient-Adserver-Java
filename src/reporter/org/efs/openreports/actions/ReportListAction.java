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

package org.efs.openreports.actions;

import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

import org.apache.log4j.Logger;

import org.efs.openreports.ORStatics;
import org.efs.openreports.objects.ReportGroup;
import org.efs.openreports.objects.ReportUser;
import org.efs.openreports.providers.GroupProvider;
import org.efs.openreports.providers.GroupProviderAware;
import org.efs.openreports.util.LocalStrings;

public class ReportListAction
	extends ActionSupport
	implements GroupProviderAware
{
	protected static Logger log = Logger.getLogger(ReportListAction.class);

	private int groupId = Integer.MIN_VALUE;

	private List reports;

	private GroupProvider groupProvider;

	public List getReports()
	{
		return reports;
	}

	public String execute()
	{
		try
		{
			ReportGroup reportGroup = null;

			if (groupId == Integer.MIN_VALUE)
			{
				reportGroup =
					(ReportGroup) ActionContext.getContext().getSession().get(
						ORStatics.REPORT_GROUP);
			}
			else
			{
				ReportUser reportUser =
					(ReportUser) ActionContext.getContext().getSession().get(
						ORStatics.REPORT_USER);

				reportGroup =
					groupProvider.getReportGroup(new Integer(groupId));

				if (reportUser.isValidGroup(reportGroup))
				{
					ActionContext.getContext().getSession().put(
						ORStatics.REPORT_GROUP,
						reportGroup);
				}
				else
				{
					addActionError(LocalStrings.getString(LocalStrings.ERROR_REPORTGROUP_NOTAUTHORIZED));
					return ERROR;
				}
			}

			if (reportGroup != null)
			{
				reports = reportGroup.getReportsForDisplay();
				Collections.sort(reports);
			}

			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionError(e.toString());
			return ERROR;
		}
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	public void setGroupProvider(GroupProvider groupProvider)
	{
		this.groupProvider = groupProvider;
	}
}