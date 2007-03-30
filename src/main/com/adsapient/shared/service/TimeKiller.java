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
package com.adsapient.shared.service;

public class TimeKiller implements Runnable {

    private Thread targetThread;
    private long millis;
    private Thread watcherThread;
    private boolean loop;
    private boolean enabled;

    public TimeKiller(Thread targetThread, long millis) {
        this.targetThread = targetThread;
        this.millis = millis;
        watcherThread = new Thread(this);
        enabled = true;
        watcherThread.start();
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
        }
    }

    public TimeKiller(long millis) {
        this(Thread.currentThread(), millis);
    }

    public synchronized void done() {
        loop = false;
        enabled = false;
        notify();
    }

    public synchronized void reset() {
        loop = true;
        notify();
    }

    public synchronized void reset(long millis) {
        this.millis = millis;
        reset();
    }

    public synchronized void run() {
        Thread me = Thread.currentThread();
        me.setPriority(Thread.MAX_PRIORITY);
        if (enabled) {
            do {
                loop = false;
                try {
                    wait(millis);
                }
                catch (InterruptedException e) {
                }
            }
            while (enabled && loop);
        }
        if (enabled && targetThread.isAlive())
            targetThread.stop();
    }

}

