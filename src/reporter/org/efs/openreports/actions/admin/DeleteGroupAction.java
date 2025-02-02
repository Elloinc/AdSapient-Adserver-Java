/*
 * Copyright (C) 2003 Erik Swenson - eswenson@opensourcesoft.net
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

import org.efs.openreports.objects.ReportGroup;
import org.efs.openreports.providers.GroupProvider;
import org.efs.openreports.providers.GroupProviderAware;

public class DeleteGroupAction extends DeleteAction implements GroupProviderAware
{
	private GroupProvider groupProvider;

	public String execute()
	{	
		try
		{
			ReportGroup reportGroup =
				groupProvider.getReportGroup(new Integer(id));
			
			name = reportGroup.getName();
			description = reportGroup.getDescription();
			
			if (!submitDelete && !submitCancel)
			{
				return INPUT;
			}
			
			if (submitDelete)
			{
				groupProvider.deleteReportGroup(reportGroup);
			}
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			return INPUT;
		}

		return SUCCESS;
	}	

	public void setGroupProvider(GroupProvider groupProvider)
	{
		this.groupProvider = groupProvider;
	}	
}