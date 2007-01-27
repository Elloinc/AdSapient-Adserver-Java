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
package com.adsapient.api_impl.filter;

import com.adsapient.api.StatisticInterface;

import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class KeyWordsFilterElement implements Cloneable, IMappable {
	static Logger logger = Logger.getLogger(KeyWordsFilterElement.class);

	public Integer id;

	public Integer keywordsFilterId;

	public String keyWordSet;

	public int average;

	public int clicks;

	public int impressions;

	public KeyWordsFilterElement() {
		super();
	}

	public void setAverage(int average) {
		this.average = average;
	}

	public int getAverage() {
		Collection parameters = new ArrayList();
		KeywordsFilter filter = (KeywordsFilter) MyHibernateUtil.loadObject(
				KeywordsFilter.class, this.getKeywordsFilterId());
		parameters.add(new StatisticRequestParameter(
				StatisticInterface.CAMPAIGN_ID_COLUMN,
				StatisticRequestParameter.EQUAL, filter.getCampainId()
						.toString()));

		return StatisticBuilder.getStatistic(StatisticBuilder.IMPRESSION_CLASS,
				StatisticBuilder.COUNT_TYPE,
				StatisticInterface.CAMPAIGN_ID_COLUMN, parameters);
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public int getClicks() {
		Collection parameters = new ArrayList();
		KeywordsFilter filter = (KeywordsFilter) MyHibernateUtil.loadObject(
				KeywordsFilter.class, this.getKeywordsFilterId());
		parameters.add(new StatisticRequestParameter(
				StatisticInterface.CAMPAIGN_ID_COLUMN,
				StatisticRequestParameter.EQUAL, filter.getCampainId()
						.toString()));

		return StatisticBuilder.getStatistic(StatisticBuilder.CLICK_CLASS,
				StatisticBuilder.COUNT_TYPE,
				StatisticInterface.CAMPAIGN_ID_COLUMN, parameters);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public int getImpressions() {
		return impressions;
	}

	public void setKeyWordSet(String keyWordSet) {
		this.keyWordSet = keyWordSet;
	}

	public String getKeyWordSet() {
		return keyWordSet;
	}

	public Integer getKeywordsFilterId() {
		return keywordsFilterId;
	}

	public void setKeywordsFilterId(Integer keywordsFilterId) {
		this.keywordsFilterId = keywordsFilterId;
	}

	public Object clone() {
		KeyWordsFilterElement filter = new KeyWordsFilterElement();

		filter.setKeyWordSet(this.getKeyWordSet());

		return filter;
	}
}
