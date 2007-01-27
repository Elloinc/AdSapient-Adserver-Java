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
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.servlet.ServletUtilities;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import org.jfree.util.Rotation;

import java.awt.Color;
import java.awt.Font;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

public class PiechartDiagramBuilder implements DiagramBuilder {
	public String createDiagram(HttpSession session, PrintWriter pw,
			CategoryDataset categorydataset,
			CategoryDataset secondCategoryDataset, AccountSetting settings,
			String dateTitle, String diagramTitle) throws IOException {
		boolean use3d = true;

		if (settings.getDepth().intValue() == 2) {
			use3d = false;
		}

		if (settings.getDepth().intValue() == 3) {
			use3d = true;
		}

		JFreeChart jfreechart = createPieChartGraphick(
				(PieDataset) categorydataset, session, diagramTitle, use3d);

		return generateChart(pw, jfreechart);
	}

	private JFreeChart createPieChartGraphick(PieDataset pieDataset,
			HttpSession session, String titleKey, boolean use3d) {
		JFreeChart jfreechart;

		if (use3d) {
			jfreechart = ChartFactory.createPieChart3D("", pieDataset, true,
					true, false);

			PiePlot3D pieplot3d = (PiePlot3D) jfreechart.getPlot();
			pieplot3d.setStartAngle(290D);
			pieplot3d.setDirection(Rotation.CLOCKWISE);
			pieplot3d.setForegroundAlpha(0.6F);
			pieplot3d
					.setNoDataMessage(Msg.fetch("no.data.to.display", session));
		} else {
			jfreechart = ChartFactory.createPieChart("", pieDataset, true,
					true, false);

			PiePlot pieplot = (PiePlot) jfreechart.getPlot();
			pieplot.setLabelFont(new Font("SansSerif", 0, 12));
			pieplot.setNoDataMessage(Msg.fetch("no.data.available", session));
			pieplot.setCircular(false);
			pieplot.setLabelGap(0.02D);
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
