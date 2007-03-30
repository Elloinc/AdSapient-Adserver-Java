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

import com.adsapient.api.IMappable;
import com.adsapient.shared.service.FinancialsService;

import com.adsapient.gui.forms.RateManagmentActionForm;

import java.util.StringTokenizer;

public class RateImpl implements IMappable {
	public static final int CPM = 0;

	public static final int CPC = 1;

	public static final int CPL = 2;

	public static final int CPS = 3;

	private Integer cpcRate = new Integer(0);

	private Integer cplRate = new Integer(0);

	private Integer cpmRate = new Integer(0);

	private Integer cpsRate = new Integer(0);

	private Integer rateId;

	private String rateType;

	public void setCpcRate(Integer cpcRate) {
		this.cpcRate = cpcRate;
	}

	public Integer getCpcRate() {
		return cpcRate;
	}

	public void setCplRate(Integer cplRate) {
		this.cplRate = cplRate;
	}

	public Integer getCplRate() {
		return cplRate;
	}

	public void setCpmRate(Integer cpmRate) {
		this.cpmRate = cpmRate;
	}

	public Integer getCpmRate() {
		return cpmRate;
	}

	public void setCpsRate(Integer cpsRate) {
		this.cpsRate = cpsRate;
	}

	public Integer getCpsRate() {
		return cpsRate;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public Integer getRateId() {
		return rateId;
	}

	public String getCPM_CPC() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(FinancialsService.transformMoney(cpmRate)).append("/")
				.append(FinancialsService.transformMoney(cpcRate));

		return buffer.toString();
	}

	public String getCPL_CPS() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(FinancialsService.transformMoney(cplRate)).append("/")
				.append(FinancialsService.transformMoney(cpsRate));

		return buffer.toString();
	}

	public RateImpl createInstance() {
		RateImpl rate = new RateImpl();

		rate.setCpcRate(cpcRate);
		rate.setCpmRate(cpmRate);
		rate.setRateType(rateType);
		rate.setCplRate(cplRate);
		rate.setCpsRate(cpsRate);

		return rate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public boolean isMatching(RateImpl placesRate) {
		boolean oldCPM = RateImpl.parseTypeValue(this.rateType, RateImpl.CPM);
		boolean oldCPC = RateImpl.parseTypeValue(this.rateType, RateImpl.CPC);
		boolean oldCPL = RateImpl.parseTypeValue(this.rateType, RateImpl.CPL);
		boolean oldCPS = RateImpl.parseTypeValue(this.rateType, RateImpl.CPS);

		boolean anotherCPM = RateImpl.parseTypeValue(placesRate.rateType,
				RateImpl.CPM);
		boolean anotherCPC = RateImpl.parseTypeValue(placesRate.rateType,
				RateImpl.CPC);
		boolean anotherCPL = RateImpl.parseTypeValue(placesRate.rateType,
				RateImpl.CPL);
		boolean anotherCPS = RateImpl.parseTypeValue(placesRate.rateType,
				RateImpl.CPS);

		if (anotherCPM && !oldCPM) {
			return false;
		}

		if (anotherCPC && !oldCPC) {
			return false;
		}

		if (anotherCPL && !oldCPL) {
			return false;
		}

		if (anotherCPS && !oldCPS) {
			return false;
		}

		if (oldCPM && anotherCPM) {
			return true;
		}

		if (oldCPC && anotherCPC) {
			return true;
		}

		if (oldCPL && anotherCPL) {
			return true;
		}

		if (oldCPS && anotherCPS) {
			return true;
		}

		return false;
	}

	public boolean isMatching(RateImpl anotherRate, int number) {
		boolean oldValue = parseTypeValue(this.getRateType(), number);
		boolean anotherValue = parseTypeValue(anotherRate.getRateType(), number);

		if (oldValue && anotherValue) {
			return true;
		} else {
			return false;
		}
	}

	public static void parseTypeValue(String typeValue,
			RateManagmentActionForm form) {
		int advertiserTokensCounter = 0;
		StringTokenizer advTokenizer = new StringTokenizer(typeValue, ",");

		while (advTokenizer.hasMoreTokens()) {
			String token = advTokenizer.nextToken();
			boolean checkBoxValue = false;

			if ("1".equalsIgnoreCase(token)) {
				checkBoxValue = true;
			}

			if (0 == advertiserTokensCounter) {
				form.setAdvCPM(checkBoxValue);
			}

			if (1 == advertiserTokensCounter) {
				form.setAdvCPC(checkBoxValue);
			}

			if (2 == advertiserTokensCounter) {
				form.setAdvCPL(checkBoxValue);
			}

			if (3 == advertiserTokensCounter) {
				form.setAdvCPS(checkBoxValue);
			}

			advertiserTokensCounter++;
		}
	}

	public static boolean parseTypeValue(String typeValue, int number) {
		boolean value = false;
		int advertiserTokensCounter = 0;
		StringTokenizer advTokenizer = new StringTokenizer(typeValue, ",");

		while (advTokenizer.hasMoreTokens()) {
			String token = advTokenizer.nextToken();
			boolean checkBoxValue = false;

			if ("1".equalsIgnoreCase(token)) {
				checkBoxValue = true;
			}

			if ((RateImpl.CPM == advertiserTokensCounter)
					&& (RateImpl.CPM == number)) {
				value = checkBoxValue;
			}

			if ((RateImpl.CPC == advertiserTokensCounter)
					&& (RateImpl.CPC == number)) {
				value = checkBoxValue;
			}

			if ((RateImpl.CPL == advertiserTokensCounter)
					&& (RateImpl.CPL == number)) {
				value = checkBoxValue;
			}

			if ((RateImpl.CPS == advertiserTokensCounter)
					&& (RateImpl.CPS == number)) {
				value = checkBoxValue;
			}

			advertiserTokensCounter++;
		}

		return value;
	}

	public Integer getId() {
		return rateId;
	}
}
