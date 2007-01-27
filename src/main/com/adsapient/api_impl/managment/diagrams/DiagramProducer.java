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
package com.adsapient.api_impl.managment.diagrams;

import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.statistic.advertizer.AdvertizerStatistic;
import com.adsapient.api_impl.statistic.common.StatisticEntity;
import com.adsapient.api_impl.statistic.publisher.PublisherStatistic;
import com.adsapient.api_impl.usermanagment.AccountSetting;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import org.jfree.text.TextBlockAnchor;

import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import org.jfree.util.Rotation;

import java.awt.Color;
import java.awt.Font;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class DiagramProducer {
	private static float color = 0;

	public static String generateDiagram(HttpSession session, String type,
			PrintWriter pw) {
		String resultfileName;

		UserImpl user = (UserImpl) session.getAttribute("user");

		AccountSetting settings = (AccountSetting) MyHibernateUtil
				.loadObjectWithCriteria(AccountSetting.class, "userId", user
						.getId());

		if ((settings != null) && ("barchart".equalsIgnoreCase(type))) {
			try {
				return generateBarchar(session, pw, user, settings);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if ((settings != null) && ("piechart".equalsIgnoreCase(type))) {
			try {
				return generatePieChart(session, pw, user, settings);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static JFreeChart createBarChartGraphick(
			CategoryDataset categorydataset, HttpSession session,
			String titleKey, String category, String value,
			PlotOrientation plotOrientation, boolean use3d) {
		JFreeChart jfreechart;

		if (use3d) {
			jfreechart = ChartFactory.createBarChart3D(Msg.fetch(titleKey,
					session), category, value, categorydataset,
					plotOrientation, true, true, false);
		} else {
			jfreechart = ChartFactory.createBarChart(Msg.fetch(titleKey,
					session), category, value, categorydataset,
					plotOrientation, true, true, false);
		}

		CategoryPlot categoryplot = jfreechart.getCategoryPlot();

		categoryplot.setForegroundAlpha(1.0F);
		categoryplot.setBackgroundAlpha(1.0F);

		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		CategoryLabelPositions categorylabelpositions = categoryaxis
				.getCategoryLabelPositions();
		CategoryLabelPosition categorylabelposition = new CategoryLabelPosition(
				RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT,
				TextAnchor.CENTER_LEFT, 0.0D, CategoryLabelWidthType.RANGE,
				0.3F);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.replaceLeftPosition(categorylabelpositions,
						categorylabelposition));

		return jfreechart;
	}

	private static CategoryDataset createDataset(
			Collection statisticCollection, HttpSession session) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		Iterator statisticCollectioniterator = statisticCollection.iterator();

		while (statisticCollectioniterator.hasNext()) {
			StatisticEntity statistic = (StatisticEntity) statisticCollectioniterator
					.next();

			defaultcategorydataset.addValue(statistic.getImpressions(), Msg
					.fetch("impressions", session), Msg.fetch("day"
					+ statistic.getEntityName(), session));

			defaultcategorydataset.addValue(statistic.getClicks(), Msg.fetch(
					"clicks", session), Msg.fetch("day"
					+ statistic.getEntityName(), session));
		}

		return defaultcategorydataset;
	}

	private static PieDataset createDatasetForPieChart(Map sitesStatisticMap,
			HttpSession session) {
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();

		Iterator sitesStatisticIterator = sitesStatisticMap.entrySet()
				.iterator();

		while (sitesStatisticIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) sitesStatisticIterator.next();

			defaultpiedataset.setValue((String) entry.getKey(),
					((StatisticEntity) entry.getValue()).getImpressions());
		}

		return defaultpiedataset;
	}

	private static JFreeChart createPieChartGraphick(PieDataset pieDataset,
			HttpSession session, String titleKey, boolean use3d) {
		JFreeChart jfreechart;

		if (use3d) {
			jfreechart = ChartFactory.createPieChart3D(Msg.fetch(titleKey,
					session), pieDataset, true, true, false);

			PiePlot3D pieplot3d = (PiePlot3D) jfreechart.getPlot();
			pieplot3d.setStartAngle(290D);
			pieplot3d.setDirection(Rotation.CLOCKWISE);
			pieplot3d.setForegroundAlpha(0.6F);
			pieplot3d
					.setNoDataMessage(Msg.fetch("no.data.to.display", session));
		} else {
			jfreechart = ChartFactory.createPieChart(Msg.fetch(titleKey,
					session), pieDataset, true, true, false);

			PiePlot pieplot = (PiePlot) jfreechart.getPlot();
			pieplot.setLabelFont(new Font("SansSerif", 0, 12));
			pieplot.setNoDataMessage(Msg.fetch("no.data.available", session));
			pieplot.setCircular(false);
			pieplot.setLabelGap(0.02D);
		}

		return jfreechart;
	}

	private static String generateBarchar(HttpSession session, PrintWriter pw,
			UserImpl user, AccountSetting settings) throws IOException {
		String fileName;
		Collection statisticCollection = AdvertizerStatistic
				.getAdvertiserWeekStatistic(user.getId(), Calendar
						.getInstance());

		boolean use3d = false;

		CategoryDataset categorydataset = createDataset(statisticCollection,
				session);

		PlotOrientation plotOrientation = PlotOrientation.VERTICAL;

		if (settings.getAxis().intValue() == 1) {
			plotOrientation = PlotOrientation.HORIZONTAL;
		}

		if (settings.getAxis().intValue() == 2) {
			plotOrientation = PlotOrientation.VERTICAL;
		}

		if (settings.getDepth().intValue() == 2) {
			use3d = false;
		}

		if (settings.getDepth().intValue() == 3) {
			use3d = true;
		}

		JFreeChart jfreechart = createBarChartGraphick(categorydataset,
				session, "campaigns.activity", "category", "value",
				plotOrientation, use3d);

		jfreechart.setBackgroundPaint(new Color(14737632));

		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		fileName = ServletUtilities.saveChartAsPNG(jfreechart, 500, 300, info,
				null);

		ChartUtilities.writeImageMap(pw, fileName, info, true);
		pw.flush();

		return fileName;
	}

	private static String generatePieChart(HttpSession session, PrintWriter pw,
			UserImpl user, AccountSetting settings) throws IOException {
		String fileName = null;

		Collection sitesCollection = MyHibernateUtil.viewWithCriteria(
				SiteImpl.class, "userId", user.getId(), "siteId");
		Map sitesStatisticMap = new HashMap();
		Iterator sitesIterator = sitesCollection.iterator();

		while (sitesIterator.hasNext()) {
			SiteImpl site = (SiteImpl) sitesIterator.next();

			StatisticEntity statistic = PublisherStatistic
					.getSiteStatisticAsText(site.getSiteId());

			sitesStatisticMap.put(site.getUrl(), statistic);
		}

		boolean use3d = true;

		if (settings.getDepth().intValue() == 2) {
			use3d = false;
		}

		if (settings.getDepth().intValue() == 3) {
			use3d = true;
		}

		PieDataset dataset = createDatasetForPieChart(sitesStatisticMap,
				session);

		JFreeChart jfreechart = createPieChartGraphick(dataset, session,
				"sites.activity", use3d);
		jfreechart.setBackgroundPaint(new Color(14737632));

		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		fileName = ServletUtilities.saveChartAsPNG(jfreechart, 500, 300, info,
				null);

		ChartUtilities.writeImageMap(pw, fileName, info, true);
		pw.flush();

		return fileName;
	}
}
