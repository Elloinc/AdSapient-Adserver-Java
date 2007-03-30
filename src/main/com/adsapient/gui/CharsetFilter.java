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
package com.adsapient.gui;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CharsetFilter implements Filter {
    static private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CharsetFilter.class);
    static final String REQUEST_LANGUAGE = "l";
    static final String SESSION_LANGUAGE = "com.cards2connect.langId";
    static final String COOKIE_LANGUAGE = "l";

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
        FilterChain filterChain) throws IOException, ServletException {
        try {
            if (!(req instanceof HttpServletRequest)) {
                return;
            }

            if (!(res instanceof HttpServletResponse)) {
                return;
            }

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            doSelectLanguage(request, response);

            if (log.isDebugEnabled()) {
            }
        } finally {
            filterChain.doFilter(req, res);
        }
    }

    public void doSelectLanguage(HttpServletRequest request,
        HttpServletResponse response) throws ServletException {
        if (request.getAttribute(REQUEST_LANGUAGE) != null) {
            return;
        }

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
        }

        response.setContentType("text/html; charset=UTF-8");
    }

    public void destroy() {
    }
}
