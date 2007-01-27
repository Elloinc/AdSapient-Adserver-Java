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
package com.adsapient.api_impl.resource;

import com.adsapient.api.AbstractPluginBannerClass;
import com.adsapient.api.PluginInstaller;

import com.adsapient.api_impl.exceptions.PluginInstallerExeption;

public class ResourceImpl extends AbstractPluginBannerClass {
	private static final long serialVersionUID = 1L;

	private Integer resourceId;

	private Integer typeId;

	private Integer userId = new Integer(1);

	private String URL;

	private String contentType;

	private String file;

	private String resourceName = "";

	private int resSize;

	public ResourceImpl() {
		super();
		this.setCampainId(null);
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public String getMappingDocuments() {
		return null;
	}

	public void setResSize(int resSize) {
		this.resSize = resSize;
	}

	public int getResSize() {
		return resSize;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setURL(String url) {
		URL = url;
	}

	public String getURL() {
		return URL;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public boolean init() {
		return true;
	}

	public boolean plugin(PluginInstaller installer)
			throws PluginInstallerExeption {
		return true;
	}
}
