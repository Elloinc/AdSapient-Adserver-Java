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
package com.adsapient.api_impl.usermanagment;

public class Financial {
	private Integer advertisingCPCrate = new Integer(0);

	private Integer advertisingCPLrate = new Integer(0);

	private Integer advertisingCPMrate = new Integer(0);

	private Integer advertisingCPSrate = new Integer(0);

	private Integer id;

	private Integer publishingCPCrate = new Integer(0);

	private Integer publishingCPLrate = new Integer(0);

	private Integer publishingCPMrate = new Integer(0);

	private Integer publishingCPSrate = new Integer(0);

	private Integer commissionRate = new Integer(0);

	private Integer userId;

	private String advertisingType = "";

	private String publishingType = "";

	public void setAdvertisingCPCrate(Integer advertisingCPCrate) {
		this.advertisingCPCrate = advertisingCPCrate;
	}

	public Integer getAdvertisingCPCrate() {
		return advertisingCPCrate;
	}

	public void setAdvertisingCPLrate(Integer advertisingCPLrate) {
		this.advertisingCPLrate = advertisingCPLrate;
	}

	public Integer getAdvertisingCPLrate() {
		return advertisingCPLrate;
	}

	public void setAdvertisingCPMrate(Integer advertisingCPMrate) {
		this.advertisingCPMrate = advertisingCPMrate;
	}

	public Integer getAdvertisingCPMrate() {
		return advertisingCPMrate;
	}

	public void setAdvertisingCPSrate(Integer advertisingCPSrate) {
		this.advertisingCPSrate = advertisingCPSrate;
	}

	public Integer getAdvertisingCPSrate() {
		return advertisingCPSrate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setPublishingCPCrate(Integer publishingCPCrate) {
		this.publishingCPCrate = publishingCPCrate;
	}

	public Integer getPublishingCPCrate() {
		return publishingCPCrate;
	}

	public void setPublishingCPLrate(Integer publishingCPLrate) {
		this.publishingCPLrate = publishingCPLrate;
	}

	public Integer getPublishingCPLrate() {
		return publishingCPLrate;
	}

	public void setPublishingCPMrate(Integer publishingCPMrate) {
		this.publishingCPMrate = publishingCPMrate;
	}

	public Integer getPublishingCPMrate() {
		return publishingCPMrate;
	}

	public void setPublishingCPSrate(Integer publishingCPSrate) {
		this.publishingCPSrate = publishingCPSrate;
	}

	public Integer getPublishingCPSrate() {
		return publishingCPSrate;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getAdvertisingType() {
		return advertisingType;
	}

	public void setAdvertisingType(String advertisingType) {
		this.advertisingType = advertisingType;
	}

	public String getPublishingType() {
		return publishingType;
	}

	public void setPublishingType(String publishingType) {
		this.publishingType = publishingType;
	}

	public Integer getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Integer commissionRate) {
		this.commissionRate = commissionRate;
	}
}
