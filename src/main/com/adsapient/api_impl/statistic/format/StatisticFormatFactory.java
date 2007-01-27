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
package com.adsapient.api_impl.statistic.format;

import com.adsapient.api.StatisticFormater;

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.statistic.common.StatisticEntity;

import com.adsapient.util.Msg;
import com.adsapient.util.financial.MoneyTransformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public class StatisticFormatFactory {
	public static final String SIMPLE_FORMATER = "simpleFormater";

	public static final String FORMAT_ID_LABEL = "formatIdLabel";

	public static final String ID_WITH_UNIQUES = "idWithUniques";

	public static final String ID_WITH_CPM_CPC = "idWithCpmCpc";

	public static final String ADVERTISER_DATE_WITH_UNIQUES = "advertiserDateWithUniques";

	public static final String ADVERTISER_CAMPAIGNS = "advertisrCampaigns";

	private static StatisticFormater getFormater(String formaterRules) {
		StatisticFormater formater = new HTMLStatisticFormater(
				getFunctioanalCollection(formaterRules));

		return formater;
	}

	public static StatisticFormater getFormater(String formaterRules,
			HttpServletRequest request) {
		StatisticFormater formater = getFormater(formaterRules);
		formater.setHeaders(getHeaders(formaterRules, request));

		return formater;
	}

	public static StatisticFormater getFormater(String formaterRules,
			HttpServletRequest request, CampainImpl campain) {
		StatisticFormater formater = new CampainHTMLStatisticFormater(campain);
		formater.setHeaders(getHeaders(formaterRules, request));

		return formater;
	}

	private static Collection getHeaders(String formatRulesName,
			HttpServletRequest request) {
		Collection result = new ArrayList();

		if (ADVERTISER_CAMPAIGNS.equalsIgnoreCase(formatRulesName)) {
			result.add(Msg.fetch("id", request));
			result.add(Msg.fetch("campaign.name", request));
			result.add(Msg.fetch("start.date", request));
			result.add(Msg.fetch("end.date", request));
			result.add(Msg.fetch("rate", request));
			result.add(Msg.fetch("adviews.clicks.booked", request));
			result.add(Msg.fetch("adviews.clicks.displayed", request));
			result.add(Msg.fetch("adleads.sales.booked", request));
			result.add(Msg.fetch("adleads.sales.displayed", request));

			return result;
		}

		if (ADVERTISER_DATE_WITH_UNIQUES.equalsIgnoreCase(formatRulesName)) {
			result.add(Msg.fetch("date", request));
			result.add(Msg.fetch("uniques", request));
			result.add(Msg.fetch("adviews", request));
			result.add(Msg.fetch("clicks", request));
			result.add(Msg.fetch("leads", request));
			result.add(Msg.fetch("sales", request));
			result.add(Msg.fetch("ctr", request));
			result.add(Msg.fetch("ltr", request));
			result.add(Msg.fetch("str", request));
			result.add(Msg.fetch("adviews.uniques", request));
			result.add(Msg.fetch("spendings", request));

			return result;
		}

		if (SIMPLE_FORMATER.equalsIgnoreCase(formatRulesName)) {
			result.add("NAME");
			result.add(Msg.fetch("adviews", request));
			result.add(Msg.fetch("clicks", request));
			result.add(Msg.fetch("leads", request));
			result.add(Msg.fetch("sales", request));
			result.add(Msg.fetch("ctr", request));
			result.add(Msg.fetch("ltr", request));
			result.add(Msg.fetch("str", request));
			result.add(Msg.fetch("revenue", request));

			return result;
		}

		if (FORMAT_ID_LABEL.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.LABEL);
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(Msg.fetch("adviews", request));
			result.add(Msg.fetch("clicks", request));
			result.add(Msg.fetch("leads", request));
			result.add(Msg.fetch("sales", request));
			result.add(Msg.fetch("CTR", request));
			result.add(Msg.fetch("ltr", request));
			result.add(Msg.fetch("str", request));
			result.add(Msg.fetch("revenue", request));

			return result;
		}

		if (ID_WITH_UNIQUES.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.LABEL);
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.UNIQUES);
			result.add(Msg.fetch("adviews", request));
			result.add(Msg.fetch("clicks", request));
			result.add(Msg.fetch("leads", request));
			result.add(Msg.fetch("sales", request));
			result.add(StatisticEntity.VIEWS_UNCS);
			result.add(StatisticEntity.UNIQUE_CLICKS);
			result.add(StatisticEntity.UNIQUE_LEADS);
			result.add(StatisticEntity.UNIQUE_SALES);
			result.add(Msg.fetch("revenue", request));

			return result;
		}

		if (ID_WITH_CPM_CPC.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.LABEL);
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.UNIQUES);
			result.add(Msg.fetch("adviews", request));
			result.add(Msg.fetch("clicks", request));
			result.add(Msg.fetch("leads", request));
			result.add(Msg.fetch("sales", request));
			result.add(Msg.fetch("ctr", request));
			result.add(Msg.fetch("ltr", request));
			result.add(Msg.fetch("str", request));
			result.add("label2");
			result.add("label3");
			result.add(Msg.fetch("revenue", request));

			return result;
		}

		return null;
	}

	private static Collection getFunctioanalCollection(String formatRulesName) {
		Collection result = new ArrayList();

		if (ADVERTISER_DATE_WITH_UNIQUES.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.UNIQUES);
			result.add(StatisticEntity.IMPRESSIONS);
			result.add(StatisticEntity.CLICKS);
			result.add(StatisticEntity.LEADS);
			result.add(StatisticEntity.SALES);
			result.add(StatisticEntity.CTR);
			result.add(StatisticEntity.LTR);
			result.add(StatisticEntity.STR);
			result.add(StatisticEntity.VIEWS_UNCS);
			result.add(StatisticEntity.REVENUE_FLOAT);

			return result;
		}

		if (SIMPLE_FORMATER.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.IMPRESSIONS);
			result.add(StatisticEntity.CLICKS);
			result.add(StatisticEntity.LEADS);
			result.add(StatisticEntity.SALES);
			result.add(StatisticEntity.CTR);
			result.add(StatisticEntity.LTR);
			result.add(StatisticEntity.STR);
			result.add(StatisticEntity.REVENUE_FLOAT);

			return result;
		}

		if (FORMAT_ID_LABEL.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.LABEL);
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.IMPRESSIONS);
			result.add(StatisticEntity.CLICKS);
			result.add(StatisticEntity.LEADS);
			result.add(StatisticEntity.SALES);
			result.add(StatisticEntity.CTR);
			result.add(StatisticEntity.LTR);
			result.add(StatisticEntity.STR);
			result.add(StatisticEntity.REVENUE_FLOAT);

			return result;
		}

		if (ID_WITH_UNIQUES.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.LABEL);
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.UNIQUES);
			result.add(StatisticEntity.IMPRESSIONS);
			result.add(StatisticEntity.CLICKS);
			result.add(StatisticEntity.LEADS);
			result.add(StatisticEntity.SALES);
			result.add(StatisticEntity.VIEWS_UNCS);
			result.add(StatisticEntity.UNIQUE_CLICKS);
			result.add(StatisticEntity.UNIQUE_LEADS);
			result.add(StatisticEntity.UNIQUE_SALES);
			result.add(StatisticEntity.REVENUE_FLOAT);

			return result;
		}

		if (ID_WITH_CPM_CPC.equalsIgnoreCase(formatRulesName)) {
			result.add(StatisticEntity.LABEL);
			result.add(StatisticEntity.ENTITY_NAME);
			result.add(StatisticEntity.UNIQUES);
			result.add(StatisticEntity.IMPRESSIONS);
			result.add(StatisticEntity.CLICKS);
			result.add(StatisticEntity.LEADS);
			result.add(StatisticEntity.SALES);
			result.add(StatisticEntity.CTR);
			result.add(StatisticEntity.LTR);
			result.add(StatisticEntity.STR);
			result.add(StatisticEntity.LABEL2);
			result.add(StatisticEntity.LABEL3);
			result.add(StatisticEntity.REVENUE_FLOAT);

			return result;
		}

		return null;
	}
}

class HTMLStatisticFormater implements StatisticFormater {
	private Collection formatRules = new ArrayList();

	private Collection headers = new ArrayList();

	public HTMLStatisticFormater(Collection rules) {
		formatRules = rules;
	}

	private HTMLStatisticFormater() {
	}

	public String format(StatisticEntity entity) {
		StringBuffer buffer = new StringBuffer("<tr>");

		Iterator rulesIterator = formatRules.iterator();

		while (rulesIterator.hasNext()) {
			String metod = (String) rulesIterator.next();
			buffer.append("<td class=\"tabledata-c\">").append(
					entity.getValueByMetodName(metod)).append("</td>");
		}

		buffer.append("</tr>");

		return buffer.toString();
	}

	public Collection getHeaders() {
		return headers;
	}

	public void setHeaders(Collection headers) {
		this.headers = headers;
	}

	public Collection getFormatRules() {
		return formatRules;
	}

	public void setFormatRules(Collection formatRules) {
		this.formatRules = formatRules;
	}

	public Collection getValuesCollection(StatisticEntity entity) {
		Collection resultCollection = new ArrayList();
		Iterator rulesIterator = this.formatRules.iterator();

		while (rulesIterator.hasNext()) {
			String rule = (String) rulesIterator.next();
			resultCollection.add(entity.getValueByMetodName(rule).toString());
		}

		return resultCollection;
	}
}

class CampainHTMLStatisticFormater implements StatisticFormater {
	private CampainImpl campain;

	private Collection headers;

	private Collection formatRules;

	public CampainHTMLStatisticFormater(CampainImpl campain) {
		this.campain = campain;
	}

	public Collection getValuesCollection(StatisticEntity entity) {
		Collection resultCollection = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		StringBuffer result = new StringBuffer();
		StringBuffer imprBuffer = new StringBuffer();
		StringBuffer leadsBuffer = new StringBuffer();

		imprBuffer.append(entity.getImpressions()).append(" / ").append(
				entity.getClicks());
		leadsBuffer.append(entity.getLeads()).append(" / ").append(
				entity.getSales());

		result
				.append(
						MoneyTransformer.transformMoney(campain.getRate()
								.getCpmRate())).append("/").append(
						MoneyTransformer.transformMoney(campain.getRate()
								.getCpcRate()));

		resultCollection.add(campain.getCampainId());
		resultCollection.add(campain.getName());
		resultCollection.add(campain.getStartDate());
		resultCollection.add(campain.getEndDate());
		resultCollection.add(result.toString());
		resultCollection.add("0 / 0");
		resultCollection.add(imprBuffer);
		resultCollection.add("0 / 0");
		resultCollection.add(leadsBuffer);
		resultCollection.add(campain.getCampainId());
		resultCollection.add(campain.getCampainId());
		resultCollection.add(campain.getCampainId());

		return resultCollection;
	}

	public String format(StatisticEntity entity) {
		StringBuffer buffer = new StringBuffer();
		StringBuffer result = new StringBuffer();
		StringBuffer imprBuffer = new StringBuffer();
		StringBuffer leadsBuffer = new StringBuffer();

		imprBuffer.append(entity.getImpressions()).append(" / ").append(
				entity.getClicks());
		leadsBuffer.append(entity.getLeads()).append(" / ").append(
				entity.getSales());

		result
				.append(
						MoneyTransformer.transformMoney(campain.getRate()
								.getCpmRate())).append("/").append(
						MoneyTransformer.transformMoney(campain.getRate()
								.getCpcRate()));

		buffer.append("<tr>");
		buffer.append("<td class=\"tabledata-c\">").append(
				campain.getCampainId()).append("</td>");
		buffer.append("<td class=\"tabledata-c\">").append(campain.getName())
				.append("</td>");
		buffer.append("<td class=\"tabledata-c\">").append(
				campain.getStartDate()).append("</td>");
		buffer.append("<td class=\"tabledata-c\">")
				.append(campain.getEndDate()).append("</td>");
		buffer.append("<td class=\"tabledata-c\">").append(result.toString())
				.append("</td>");
		buffer.append("<td class=\"tabledata-c\">").append("0 / 0").append(
				"</td>");
		buffer.append("<td class=\"tabledata-c\">").append(imprBuffer).append(
				"</td>");
		buffer.append("<td class=\"tabledata-c\">").append("0 / 0").append(
				"</td>");
		buffer.append("<td class=\"tabledata-c\">").append(leadsBuffer).append(
				"</td>");
		buffer.append("</tr>");

		return buffer.toString();
	}

	public Collection getFormatRules() {
		return formatRules;
	}

	public Collection getHeaders() {
		return headers;
	}

	public void setHeaders(Collection headers) {
		this.headers = headers;
	}
}
