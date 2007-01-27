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
package com.adsapient.api_impl.statistic.common;

import com.adsapient.api.StatisticFormater;

import com.adsapient.util.financial.MoneyTransformer;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Collection;

public class StatisticEntity {
	public static final String ID = "getId";

	public static final String CLICKS = "getClicks";

	public static final String CTR = "getCtr";

	public static final String ENTITY_NAME = "getEntityName";

	private static Logger logger = Logger.getLogger(StatisticEntity.class);

	public static final String IMPRESSIONS = "getImpressions";

	public static final String LEADS = "getLeads";

	public static final String LTR = "getLtr";

	public static final String REVENUE = "getRevenue";

	public static final String REVENUE_FLOAT = "getRevenueAsFloat";

	public static final String SALES = "getSales";

	public static final String STR = "getStr";

	public static final String UNIQUES = "getUniques";

	public static final String UNIQUE_CLICKS = "getUnqClicks";

	public static final String UNIQUE_LEADS = "getUnqLeads";

	public static final String UNIQUE_SALES = "getUnqSales";

	public static final String CALCULATE_CTR = "calculateCtr";

	public static final String CALCULATE_RELEVATION = "calculateRelevation";

	public static final String LABEL = "getLabel";

	public static final String LABEL2 = "getLabel2";

	public static final String LABEL3 = "getLabel3";

	public static final String VIEWS_UNCS = "getViewsUncs";

	private NumberFormat formater = new DecimalFormat("0.00");

	private StatisticFormater statisticFormater;

	private String ctr;

	private String label = "";

	private String label2 = "";

	private String Label3 = "";

	private String entityName = "";

	private String ltr;

	private String str;

	private String unqClicks;

	private String unqLeads;

	private String unqSales;

	private String viewsUncs;

	private int clicks = 0;

	private int impressions = 0;

	private int leads = 0;

	private int revenue;

	private int sales = 0;

	private int uniques = 0;

	public StatisticEntity() {
		super();
	}

	public StatisticEntity(StatisticFormater formater) {
		super();
		this.statisticFormater = formater;
	}

	public StatisticEntity(String entityName, int clicks, int impressions) {
		super();
		this.entityName = entityName;
		this.clicks = clicks;
		this.impressions = impressions;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public int getClicks() {
		return clicks;
	}

	public void setCtr(String ctr) {
		this.ctr = ctr;
	}

	public String getCtr() {
		if (this.impressions == 0) {
			this.ctr = formater.format(0);
		} else {
			this.ctr = formater.format((double) clicks / impressions);
		}

		return ctr;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public int getImpressions() {
		return impressions;
	}

	public void setLeads(int leads) {
		this.leads = leads;
	}

	public int getLeads() {
		return leads;
	}

	public void setLtr(String ltr) {
		this.ltr = ltr;
	}

	public String getLtr() {
		if (this.impressions == 0) {
			this.ltr = formater.format(0);
		} else {
			this.ltr = formater.format((double) leads / impressions);
		}

		return ltr;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getRevenue() {
		return revenue;
	}

	public Float getRevenueAsFloat() {
		return new Float(MoneyTransformer.transformMoney(revenue));
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getSales() {
		return sales;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getStr() {
		if (this.impressions == 0) {
			this.str = formater.format(0);
		} else {
			this.str = formater.format((double) sales / impressions);
		}

		return str;
	}

	public void setUniques(int uniques) {
		this.uniques = uniques;
	}

	public int getUniques() {
		return uniques;
	}

	public void setUnqClicks(String unqClicks) {
		this.unqClicks = unqClicks;
	}

	public String getUnqClicks() {
		return calculateRelevation(uniques, clicks);
	}

	public void setUnqLeads(String unqLeads) {
		this.unqLeads = unqLeads;
	}

	public String getUnqLeads() {
		return calculateRelevation(uniques, leads);
	}

	public void setUnqSales(String unqSales) {
		this.unqSales = unqSales;
	}

	public String getUnqSales() {
		return calculateRelevation(uniques, sales);
	}

	public Object getValueByMetodName(String metodName) {
		try {
			Method method = this.getClass()
					.getMethod(metodName, new Class[] {});
			Object result = method.invoke(this, new Object[] {});

			return result;
		} catch (Exception e) {
			logger.error("While invoke metod", e);
		}

		return "n/a";
	}

	public void setViewsUncs(String viewsUncs) {
		this.viewsUncs = viewsUncs;
	}

	public String getViewsUncs() {
		return calculateRelevation(impressions, uniques);
	}

	public String calculateCtr(int impr, int click) {
		if (impr == 0) {
			return formater.format(0);
		} else {
			return formater.format((double) click / impr).toString();
		}
	}

	public String calculateRelevation(int chislitel, int znamenatel) {
		if ((chislitel == 0) || (znamenatel == 0)) {
			return formater.format(0);
		} else {
			return formater.format((double) chislitel / znamenatel).toString();
		}
	}

	public String format() {
		if (this.statisticFormater != null) {
			return statisticFormater.format(this);
		} else {
			return "n/a";
		}
	}

	public void sum(StatisticEntity anotherEntity) {
		this.clicks += anotherEntity.getClicks();

		this.impressions += anotherEntity.getImpressions();

		this.leads += anotherEntity.getLeads();

		this.sales += anotherEntity.getSales();

		this.revenue += anotherEntity.getRevenue();

		this.uniques += anotherEntity.getUniques();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public StatisticFormater getStatisticFormater() {
		return statisticFormater;
	}

	public void setStatisticFormater(StatisticFormater statisticFormater) {
		this.statisticFormater = statisticFormater;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public String getLabel3() {
		return Label3;
	}

	public void setLabel3(String label3) {
		Label3 = label3;
	}

	public Collection getAsFormatedCollection() {
		if (this.statisticFormater == null) {
			logger.warn("formater is not set");

			return new ArrayList();
		} else {
			return statisticFormater.getValuesCollection(this);
		}
	}
}
