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
package com.adsapient.api_impl.facade;

import com.adsapient.api.DiagramFactory;
import com.adsapient.api.Queue;
import com.adsapient.api.RunnableTask;

import com.adsapient.api_impl.managment.diagrams.DiagramFactory2;
import com.adsapient.api_impl.thread.ThreadWorker;

import java.io.PrintWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

public class AdsapientSystemFasade {
	private static Queue queue = new ThreadWorker();

	private static DiagramFactory diagramFactory = new DiagramFactory2();

	private static ExecutorService pool = Executors.newCachedThreadPool();

	public static void addTask(RunnableTask task) {
		pool.execute(task);
	}

	public static String generateDiagram(HttpServletRequest request,
			PrintWriter pw) throws Exception {
		return diagramFactory.generateDiagram(request, pw);
	}

	public static void shutdownTasks() {
		queue.shutdown();
	}
}
