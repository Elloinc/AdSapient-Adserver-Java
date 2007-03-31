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
package com.adsapient.reporter;

import com.adsapient.adserver.AdserverServlet;
import com.adsapient.reporter.ReportsPropagator;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReporterUpdateJob implements Job {
    private static Logger logger = Logger.getLogger(ReporterUpdateJob.class);
    private ReportsPropagator reportsPropagator;
    private String pathToDumpFolderAdevents;
    private String pathToDumpFolderUniques;
    private FileSorter fileSorter = new FileSorter();


    private void init() {
        if (reportsPropagator == null && AdserverServlet.appContext != null) {
            reportsPropagator = (ReportsPropagator) AdserverServlet.appContext.getBean("reportsPropagator");
        }
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long t1 = System.currentTimeMillis();
        init();
        try {
            File f = new File(pathToDumpFolderAdevents);
            f.mkdirs();
            File[] files = f.listFiles();
            Arrays.sort(files, fileSorter);
            files = trimFirst(files);
            if (files != null) {
                for (File file : files) {
                    reportsPropagator.processAdeventsFile(file);
                }
            }

            File uniquesFolder = new File(pathToDumpFolderUniques);
            uniquesFolder.mkdirs();
            File[] uniquesFiles = f.listFiles();
            Arrays.sort(files, fileSorter);
            files = trimFirst(files);
            if (uniquesFiles != null) {
                for (File file : files)
                    reportsPropagator.processUniquesFile(file);
            }

            reportsPropagator.dump();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        logger.debug("Running ReporterUpdateJob took:" + (System.currentTimeMillis() - t1));
    }

    private File[] trimFirst(File[] files) {
        List<File> l = Arrays.asList(files);
        List<File> lr = l.subList(1, l.size());
        return (File[]) lr.toArray();
    }

    private class FileSorter implements Comparator {
        public int compare(Object o1, Object o2) {
            File f1 = (File) o1;
            File f2 = (File) o2;
            String ts1 = f1.getName().split("_")[1];
            String ts2 = f2.getName().split("_")[1];
            Long l1 = Long.parseLong(ts1);
            Long l2 = Long.parseLong(ts2);

            return l1.compareTo(l2);
        }
    }
}
