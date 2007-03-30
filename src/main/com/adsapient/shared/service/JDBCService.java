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

import com.adsapient.shared.AdsapientConstants;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

public class JDBCService {
    static Logger logger = Logger.getLogger(JDBCService.class);

    public static final String HIBERNATE_URL = "db.url";

    public static final String HIBERNATE_USERNAME = "db.username";

    public static final String HIBERNATE_PASSWORD = "db.password";

    public static final String HIBERNATE_DRIVER = "db.driver";

    public static final String HIBERNATE_DIALECT = "hibernate.dialect";

    public static boolean test(Collection sqlQuerry) throws Exception {
        InputStream stream = InstallationService.class.getClassLoader()
                .getResourceAsStream("adsapient.properties");
        Properties props = new Properties();
        props.load(stream);

        String url = props.getProperty(HIBERNATE_URL);
        String driver = props.getProperty(HIBERNATE_DRIVER);
        String userName = props.getProperty(HIBERNATE_USERNAME);
        String password = props.getProperty(HIBERNATE_PASSWORD);
        String dialect = props.getProperty(HIBERNATE_DIALECT);

        Iterator querrysIterator = sqlQuerry.iterator();
        Connection con;
        String createString;

        Statement stmt;

        Class.forName(driver);

        String qweryString = "";

        while (querrysIterator.hasNext()) {
            try {
                qweryString = (String) querrysIterator.next();
                con = DriverManager.getConnection(url, userName, password);
                stmt = con.createStatement();

                if (driver.equals(AdsapientConstants.HSSQLDRIVER)) {
                    qweryString = qweryString.replaceFirst("CREATE TABLE", "CREATE CACHED TABLE");
                }
                stmt.executeUpdate(qweryString);
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                logger.error("SQLException: " + ex.getMessage());
                logger.info("the querry is " + qweryString);
            }
        }

        logger.info("finishing sql insert");

        return true;
    }
}
