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
package com.adsapient.adserver;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.SiteImpl;

import com.adsapient.test.BaseTestHelper;
import com.adsapient.test.SpringTestCase;

import com.adsapient.util.admin.AdsapientConstants;

import java.util.Map;
import java.util.Random;

public class AdserverDecisionProviderTest extends SpringTestCase {
	public double globalFieldVar;

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testBannerIteration() {
	}

	public static void main(String[] args) {
		new AdserverDecisionProviderTest().testLocalVariablesWithinThread();
	}

	public void testGetLoadedBanner() {
		CampainImpl campaign = BaseTestHelper.createActiveCampaignWithBanner();
		SiteImpl site = BaseTestHelper.createActiveSiteWithAdplace();
		Map<String, Object> requestParams = BaseTestHelper
				.createDefaultRequestParams();
		requestParams.put(AdsapientConstants.EVENT_TYPE_REQUEST_PARAM_KEY,
				AdsapientConstants.GETADCODE_ADSERVEREVENT_TYPE);
		requestParams.put(AdsapientConstants.ADPLACE_REQUEST_PARAM_KEY, site
				.getPlaces());

		modelUpdater.setup();

		try {
			BannerImpl banner = adserverDecisionProvider.getAd(requestParams);
			assertBannersAreEqual(
					(BannerImpl) campaign.getBanners().toArray()[0], banner);
		} catch (Exception e) {
		}
	}

	public void testLocalVariablesWithinThread() {
		Thread t = new ThreadTest();
		t.start();

		Thread t2 = new ThreadTest2();
		t2.start();
	}

	public void method() {
		double locVar = Math.random();
		Random r = new Random(System.currentTimeMillis());
		globalFieldVar = r.nextDouble();

		System.out.println("Initialized locVar in current thread:"
				+ Thread.currentThread().toString() + ": to :" + locVar);
		System.out
				.println("Initialized globalFieldVar in current thread:"
						+ Thread.currentThread().toString() + ": to :"
						+ globalFieldVar);

		System.out.println("After some time locVar:"
				+ Thread.currentThread().toString() + ":" + locVar);
		System.out.println("After some time globalFieldVar:"
				+ Thread.currentThread().toString() + ":" + globalFieldVar);
	}

	private class ThreadTest extends Thread {
		double fieldVar;

		public void run() {
			someMethod();
		}

		void someMethod() {
			method();
		}
	}

	private class ThreadTest2 extends Thread {
		double fieldVar;

		public void run() {
			someMethod();
		}

		void someMethod() {
			method();
		}
	}
}
