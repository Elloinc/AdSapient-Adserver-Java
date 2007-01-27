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
package com.adsapient.shared.mappable;

import com.adsapient.shared.api.entity.IMappable;

import java.sql.Blob;

import java.util.Map;

public class TotalsReport implements IMappable {
	private Integer id;

	private Integer entityid;

	private String entityclass;

	private Integer adviews;

	private Integer clicks;

	private Integer leads;

	private Integer sales;

	private Double earnedspent;

	private Integer uniques;

	private Blob uniquesBlog;

	private byte[] uniquesBytes;

	private Map<Byte, Integer> adEventTypeToNumberOfUnits;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntityid() {
		return entityid;
	}

	public void setEntityid(Integer entityid) {
		this.entityid = entityid;
	}

	public String getEntityclass() {
		return entityclass;
	}

	public void setEntityclass(String entityclass) {
		this.entityclass = entityclass;
	}

	public Integer getAdviews() {
		return adviews;
	}

	public void setAdviews(Integer adviews) {
		this.adviews = adviews;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getLeads() {
		return leads;
	}

	public void setLeads(Integer leads) {
		this.leads = leads;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Double getEarnedspent() {
		return earnedspent;
	}

	public void setEarnedspent(Double earnedspent) {
		this.earnedspent = earnedspent;
	}

	public Integer getUniques() {
		return uniques;
	}

	public void setUniques(Integer uniques) {
		this.uniques = uniques;
	}

	public Blob getUniquesBlog() {
		return uniquesBlog;
	}

	public void setUniquesBlog(Blob uniquesBlog) {
		this.uniquesBlog = uniquesBlog;
	}

	public byte[] getUniquesBytes() {
		return uniquesBytes;
	}

	public void setUniquesBytes(byte[] uniquesBytes) {
		this.uniquesBytes = uniquesBytes;
	}

	public Map<Byte, Integer> getAdEventTypeToNumberOfUnits() {
		return adEventTypeToNumberOfUnits;
	}

	public void setAdEventTypeToNumberOfUnits(
			Map<Byte, Integer> adEventTypeToNumberOfUnits) {
		this.adEventTypeToNumberOfUnits = adEventTypeToNumberOfUnits;
	}

	public void reset() {
		adviews = 0;
		clicks = 0;
		leads = 0;
		sales = 0;
		earnedspent = 0.0;
		uniques = 0;
	}

	public void init() {
		if (adviews == null) {
			adviews = 0;
		}

		if (clicks == null) {
			clicks = 0;
		}

		if (leads == null) {
			leads = 0;
		}

		if (sales == null) {
			sales = 0;
		}

		if (earnedspent == null) {
			earnedspent = 0.0;
		}

		if (uniques == null) {
			uniques = 0;
		}
	}

	public void subtract(TotalsReport tr) {
		adviews = ((adviews == null) ? 0 : adviews)
				- ((tr.getAdviews() == null) ? 0 : tr.getAdviews());
		clicks = ((clicks == null) ? 0 : clicks)
				- ((tr.getClicks() == null) ? 0 : tr.getClicks());
		leads = ((leads == null) ? 0 : leads)
				- ((tr.getLeads() == null) ? 0 : tr.getLeads());
		sales = ((sales == null) ? 0 : sales)
				- ((tr.getSales() == null) ? 0 : tr.getSales());
		earnedspent = ((earnedspent == null) ? 0 : earnedspent)
				- ((tr.getEarnedspent() == null) ? 0 : tr.getEarnedspent());
		uniques = ((uniques == null) ? 0 : uniques)
				- ((tr.getUniques() == null) ? 0 : tr.getUniques());
	}

	public void adjustValues() {
		init();

		if (adviews < 0) {
			adviews = adviews * (-1);
		}

		if (clicks < 0) {
			clicks = clicks * (-1);
		}

		if (leads < 0) {
			leads = leads * (-1);
		}

		if (sales < 0) {
			sales = sales * (-1);
		}

		if (earnedspent < 0) {
			earnedspent = earnedspent * (-1);
		}

		if (uniques < 0) {
			uniques = uniques * (-1);
		}
	}
}
