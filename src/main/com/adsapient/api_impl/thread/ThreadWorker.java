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
package com.adsapient.api_impl.thread;

import com.adsapient.api.Queue;
import com.adsapient.api.RunnableTask;

import org.apache.log4j.Logger;

import java.util.Vector;

public class ThreadWorker implements Queue {
	private static Logger logger = Logger.getLogger(ThreadWorker.class);

	private Vector tasks = new Vector();

	private boolean shutdown;

	private boolean waiting = false;

	public ThreadWorker() {
		new Thread(new Worker()).start();
	}

	public void put(RunnableTask task) {
		tasks.add(task);

		if (waiting) {
			synchronized (this) {
				notifyAll();
			}
		}
	}

	public void shutdown() {
		logger.info("shutdown theard worker ...");
		logger.info("theara  " + tasks.size() + " unfinished");
		this.shutdown = true;
	}

	public RunnableTask take() {
		if (tasks.isEmpty()) {
			synchronized (this) {
				waiting = true;

				try {
					wait();
				} catch (InterruptedException e) {
					waiting = false;
				}
			}
		}

		return (RunnableTask) tasks.remove(0);
	}

	private class Worker implements Runnable {
		public void run() {
			while (!shutdown) {
				RunnableTask task = take();

				try {
				} catch (Exception ex) {
					logger.error("task cannot be done", ex);
				}
			}
		}
	}
}
