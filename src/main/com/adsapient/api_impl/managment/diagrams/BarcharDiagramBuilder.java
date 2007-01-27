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

import com.adsapient.api.DiagramBuilder;

import com.adsapient.api_impl.usermanagment.AccountSetting;

import com.adsapient.util.Msg;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;

import org.jfree.data.RangeType;
import org.jfree.data.category.CategoryDataset;

import org.jfree.ui.RectangleEdge;

import java.awt.Color;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

public class BarcharDiagramBuilder implements DiagramBuilder {
	public String createDiagram(HttpSession session, PrintWriter pw,
			CategoryDataset categorydataset,
			CategoryDataset secondCategoryDataset, AccountSetting settings,
			String dateTitle, String diagramTitle) throws IOException {
		boolean use3d = false;

		PlotOrientation plotOrientation = PlotOrientation.VERTICAL;

		if (settings.getAxis().intValue() == AccountSetting.HORIZONTAL) {
			plotOrientation = PlotOrientation.HORIZONTAL;
		}

		if (settings.getAxis().intValue() == AccountSetting.VERTICAL) {
			plotOrientation = PlotOrientation.VERTICAL;
		}

		if (settings.getDepth().intValue() == 2) {
			use3d = false;
		}

		if (settings.getDepth().intValue() == 3) {
			use3d = true;
		}

		JFreeChart jfreechart = createBarChartGraphick(categorydataset,
				secondCategoryDataset, session, diagramTitle, dateTitle,
				"value", plotOrientation, use3d);

		return generateChart(pw, jfreechart);
	}

	private JFreeChart createBarChartGraphick(CategoryDataset categorydataset,
			CategoryDataset secondCategoryDataset, HttpSession session,
			String titleKey, String category, String value,
			PlotOrientation plotOrientation, boolean use3d) {
		JFreeChart jfreechart = null;

		if (use3d) {
			jfreechart = ChartFactory.createBarChart3D("", category, value,
					categorydataset, plotOrientation, true, true, false);
			jfreechart.setBackgroundPaint(new Color(204, 255, 204));
			jfreechart.setAntiAlias(true);

			NumberAxis3D numberaxis3dimp = new NumberAxis3D(Msg.fetch(
					"impressions", session));
			numberaxis3dimp.setAutoRange(true);

			numberaxis3dimp.setRangeType(RangeType.POSITIVE);
			numberaxis3dimp.setAutoRangeMinimumSize(10);

			CategoryPlot categoryplot = jfreechart.getCategoryPlot();
			categoryplot.setRangeAxis(0, numberaxis3dimp);

			categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

			CategoryItemRenderer categoryitemrenderer = categoryplot
					.getRenderer();
			categoryitemrenderer.setSeriesPaint(0, Color.red);
			categoryitemrenderer.setSeriesPaint(1, Color.yellow);
			categoryitemrenderer.setSeriesPaint(2, Color.green);
			categoryitemrenderer.setBaseItemLabelsVisible(false);
			categoryitemrenderer.setItemLabelsVisible(false);

			NumberAxis3D numberaxis3d = new NumberAxis3D(Msg.fetch("clicks",
					session));

			numberaxis3d.setRangeType(RangeType.POSITIVE);

			numberaxis3d.setAutoRangeMinimumSize(10);

			categoryplot.setRangeAxis(1, numberaxis3d);
			categoryplot.setDataset(1, secondCategoryDataset);

			categoryplot.mapDatasetToRangeAxis(1, 1);

			LineRenderer3D linerenderer3d = new LineRenderer3D();
			linerenderer3d.setSeriesPaint(0, Color.blue);

			categoryplot.setRenderer(1, linerenderer3d);
			categoryplot
					.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		} else {
			jfreechart = ChartFactory.createBarChart("", category, value,
					categorydataset, plotOrientation, true, true, true);

			jfreechart.setBackgroundPaint(new Color(204, 255, 204));

			LegendTitle legendtitle = (LegendTitle) jfreechart.getSubtitle(0);
			legendtitle.setPosition(RectangleEdge.LEFT);

			NumberAxis numberaxisimp = new NumberAxis(Msg.fetch("impressions",
					session));
			numberaxisimp.setRangeType(RangeType.POSITIVE);
			numberaxisimp.setAutoRangeMinimumSize(10);

			CategoryPlot categoryplot = jfreechart.getCategoryPlot();
			categoryplot.setRangeAxis(0, numberaxisimp);
			categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			categoryplot.getDomainAxis().setMaximumCategoryLabelWidthRatio(10F);

			NumberAxis numberaxis = new NumberAxis(Msg.fetch("clicks", session));
			numberaxis.setRangeType(RangeType.POSITIVE);
			numberaxis.setAutoRangeMinimumSize(10);

			categoryplot.setRangeAxis(1, numberaxis);
			categoryplot.setDataset(1, secondCategoryDataset);
			categoryplot.mapDatasetToRangeAxis(1, 1);
			categoryplot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);

			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();

			lineandshaperenderer.setBaseItemLabelsVisible(true);
			lineandshaperenderer.setUseOutlinePaint(true);

			categoryplot.setRenderer(1, lineandshaperenderer);
			categoryplot
					.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		}

		return jfreechart;
	}

	private String generateChart(PrintWriter pw, JFreeChart jfreechart)
			throws IOException {
		String fileName;

		jfreechart.setBackgroundPaint(new Color(
				DiagramFactory2.BACKGROUND_COLOR));

		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		fileName = ServletUtilities.saveChartAsPNG(jfreechart, 500, 300, info,
				null);

		ChartUtilities.writeImageMap(pw, fileName, info, true);
		pw.flush();

		return fileName;
	}
}
