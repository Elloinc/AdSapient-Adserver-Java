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
package com.adsapient.api_impl.publisher;

import com.adsapient.shared.api.entity.IMappable;

import org.apache.log4j.Logger;

public class KeywordsRealtimeImpl implements IMappable {
	public static Logger logger = Logger.getLogger(KeywordsRealtimeImpl.class);

	private Integer id;

	private String publisherUrl;

	private String realTimeKeyWords;

	public void setPublisherUrl(String publisherUrl) {
		this.publisherUrl = publisherUrl;
	}

	public String getPublisherUrl() {
		return publisherUrl;
	}

	public void setRealTimeKeyWords(String realTimeKeyWords) {
		this.realTimeKeyWords = realTimeKeyWords;
	}

	public String getRealTimeKeyWords() {
		return realTimeKeyWords;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
