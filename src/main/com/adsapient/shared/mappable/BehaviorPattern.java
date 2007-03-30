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

import com.adsapient.api.StatisticInterface;
import com.adsapient.api.IMappable;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class BehaviorPattern  implements IMappable {
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
		return true;
	}
}
