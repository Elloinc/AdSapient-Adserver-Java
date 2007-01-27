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

import com.adsapient.api_impl.managment.CookieManager;
import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class BehaviorPattern {
	private static Logger logger = Logger.getLogger(BehaviorPattern.class);

	public static final int DESABLE_VALUE = 0;

	private Integer id;

	private String Name;

	private String keyWords = "";

	private String selectedCategorys = "";

	private int duration;

	private int frequencyDay;

	private int frequencyNumbers;

	private int recency;

	private int userId;

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setFrequencyDay(int frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	public int getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyNumbers(int frequencyNumbers) {
		this.frequencyNumbers = frequencyNumbers;
	}

	public int getFrequencyNumbers() {
		return frequencyNumbers;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setRecency(int recency) {
		this.recency = recency;
	}

	public int getRecency() {
		return recency;
	}

	public void setSelectedCategorys(String selectedCategorys) {
		this.selectedCategorys = selectedCategorys;
	}

	public String getSelectedCategorys() {
		return selectedCategorys;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public boolean doFilter(HttpServletRequest request) {
		Integer userId = CookieManager.getUniqueUserId(request);

		if ("".equalsIgnoreCase(this.keyWords)
				&& "".equalsIgnoreCase(this.selectedCategorys)) {
			return false;
		}

		if (!"".equalsIgnoreCase(this.selectedCategorys)) {
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.add(Calendar.DATE, recency * (-1));

			StringBuffer buffer = new StringBuffer();
			StringTokenizer tokenizer = new StringTokenizer(
					this.selectedCategorys, ":");
			buffer.append("  ( ");
			buffer.append(StatisticInterface.CATEGORY_ID_COLUMN).append("=")
					.append(tokenizer.nextToken()).append(" ");

			while (tokenizer.hasMoreElements()) {
				buffer.append(" or ").append(
						StatisticInterface.CATEGORY_ID_COLUMN).append("=")
						.append(tokenizer.nextToken());
			}

			buffer.append(")");

			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDate, endDate,
							StatisticInterface.UNIQUE_USER_COLUMN, userId
									.toString());

			StatisticRequestParameter categorysParameter = new StatisticRequestParameter(
					"", "", buffer.toString());

			requestParameters.add(categorysParameter);

			int matchCount = StatisticBuilder.getStatistic(
					StatisticBuilder.IMPRESSION_CLASS,
					StatisticBuilder.COUNT_TYPE,
					StatisticInterface.UNIQUE_USER_COLUMN, requestParameters);

			if (matchCount < this.getFrequencyNumbers()) {
				return false;
			}

			if (DESABLE_VALUE != this.duration) {
				requestParameters.add(new StatisticRequestParameter(
						StatisticInterface.END_TIME_COLUMN,
						StatisticRequestParameter.MORE, Integer.toString(this
								.getDuration() * 60000)));

				int durationMatchCount = StatisticBuilder.getStatistic(
						StatisticBuilder.IMPRESSION_CLASS,
						StatisticBuilder.COUNT_TYPE,
						StatisticInterface.UNIQUE_USER_COLUMN,
						requestParameters);

				if (durationMatchCount == 0) {
					return false;
				}
			}

			if (DESABLE_VALUE != this.frequencyDay) {
				Calendar periodStartDate = Calendar.getInstance();
				Calendar periodEndDate = Calendar.getInstance();
				periodStartDate.add(Calendar.DATE, recency * (-1));
				periodEndDate.add(Calendar.DATE, recency * (-1));
				periodEndDate.add(Calendar.DATE, this.frequencyDay);

				while (endDate.after(periodEndDate)) {
					Collection additionalRequestParameters = CommonStatisticAgregator
							.getParametersCollection(periodStartDate,
									periodEndDate,
									StatisticInterface.UNIQUE_USER_COLUMN,
									userId.toString());

					additionalRequestParameters.add(categorysParameter);

					int periodMatchCount = StatisticBuilder.getStatistic(
							StatisticBuilder.IMPRESSION_CLASS,
							StatisticBuilder.COUNT_TYPE,
							StatisticInterface.UNIQUE_USER_COLUMN,
							additionalRequestParameters);

					if (periodMatchCount < this.getFrequencyNumbers()) {
						return false;
					}

					periodStartDate.add(Calendar.DATE, this.frequencyDay);
					periodEndDate.add(Calendar.DATE, this.frequencyDay);
				}
			}
		}

		if (!"".equalsIgnoreCase(this.getKeyWords())) {
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.add(Calendar.DATE, recency * (-1));

			StringBuffer buffer = new StringBuffer();
			StringTokenizer tokenizer = new StringTokenizer(this.keyWords, ";");
			buffer.append("  ( ");
			buffer.append(StatisticInterface.KEYWORDS_COLUMN).append("=")
					.append(tokenizer.nextToken()).append(" ");

			while (tokenizer.hasMoreElements()) {
				buffer.append(" or ")
						.append(StatisticInterface.KEYWORDS_COLUMN).append("=")
						.append(tokenizer.nextToken());
			}

			buffer.append(")");

			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDate, endDate,
							StatisticInterface.UNIQUE_USER_COLUMN, userId
									.toString());

			StatisticRequestParameter keyWordsRequest = new StatisticRequestParameter(
					"", "", buffer.toString());

			requestParameters.add(keyWordsRequest);

			int matchCount = StatisticBuilder.getStatistic(
					StatisticBuilder.IMPRESSION_CLASS,
					StatisticBuilder.COUNT_TYPE,
					StatisticInterface.UNIQUE_USER_COLUMN, requestParameters);

			if (matchCount < this.getFrequencyNumbers()) {
				return false;
			}

			if (DESABLE_VALUE != this.frequencyDay) {
				Calendar periodStartDate = Calendar.getInstance();
				Calendar periodEndDate = Calendar.getInstance();
				periodStartDate.add(Calendar.DATE, recency * (-1));
				periodEndDate.add(Calendar.DATE, recency * (-1));
				periodEndDate.add(Calendar.DATE, this.frequencyDay);

				while (endDate.after(periodEndDate)) {
					Collection additionalRequestParameters = CommonStatisticAgregator
							.getParametersCollection(periodStartDate,
									periodEndDate,
									StatisticInterface.UNIQUE_USER_COLUMN,
									userId.toString());

					additionalRequestParameters.add(keyWordsRequest);

					int periodMatchCount = StatisticBuilder.getStatistic(
							StatisticBuilder.IMPRESSION_CLASS,
							StatisticBuilder.COUNT_TYPE,
							StatisticInterface.UNIQUE_USER_COLUMN,
							additionalRequestParameters);

					if (periodMatchCount < this.getFrequencyNumbers()) {
						return false;
					}

					periodStartDate.add(Calendar.DATE, this.frequencyDay);
					periodEndDate.add(Calendar.DATE, this.frequencyDay);
				}
			}

			logger.info("Theris " + matchCount + " matches for this keyword ");
		}

		return true;
	}
}
