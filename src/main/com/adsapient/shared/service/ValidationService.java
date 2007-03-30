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
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.SiteImpl;
import com.adsapient.shared.mappable.CampainImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.gui.forms.VerificationActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;
import java.util.Collection;

import org.apache.log4j.Logger;

public class ValidationService {
    public static Logger logger = Logger.getLogger(ValidationService.class);
    private HibernateEntityDao hibernateEntityDao;
    public boolean isAlphabets(String field, String splchars) {
        if (Pattern.matches("^[a-zA-Z" + splchars + "]*$", field.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAlphanumeric(String field, String splchars) {
        if (Pattern.matches("^[0-9a-zA-Z" + splchars + "]*$", field.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNumeric(String field, String splchars) {
        if (Pattern.matches("^[0-9" + splchars + "]*$", field.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNumeric(int field, String splchars) {
        Integer intField = new Integer(field);

        if (Pattern.matches("^[0-9" + splchars + "]*$", intField.toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static String getCount(String type) {
        int count = 0;
        StringBuffer buffer = new StringBuffer("(");

        if (AdsapientConstants.SITE.equalsIgnoreCase(type)) {
            count = loadCollection(SiteImpl.class, "siteId").size();
        }

        if (AdsapientConstants.CAMPAIGN.equalsIgnoreCase(type)) {
            count = loadCollection(CampainImpl.class, "campainId").size();
        }

        if (AdsapientConstants.USER.equalsIgnoreCase(type)) {
            count = loadCollection(UserImpl.class, "id").size();
        }

        buffer.append(count);

        return buffer.append(")").toString();
    }

    public static void initVeriFyingForm(VerificationActionForm form,
                                         HttpServletRequest request) {
        if (AdsapientConstants.SITE.equalsIgnoreCase(form.getType())) {
            Collection resultCollection = loadCollection(SiteImpl.class,
                    "siteId");

            form.setVerifyingCollection(resultCollection);
        }

        if (AdsapientConstants.USER.equalsIgnoreCase(form.getType())) {
            Collection resultCollection = loadCollection(UserImpl.class, "id");

            form.setVerifyingCollection(resultCollection);
        }

        if (AdsapientConstants.CAMPAIGN.equalsIgnoreCase(form.getType())) {
            Collection resultCollection = loadCollection(CampainImpl.class,
                    "campainId");

            form.setVerifyingCollection(resultCollection);
        }
    }

    public void verifyState(VerificationActionForm form) {
        if (AdsapientConstants.CAMPAIGN.equalsIgnoreCase(form.getType())) {
            if (form.getElementId() != null) {
                CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(
                        CampainImpl.class, new Integer(form.getElementId()));

                if (campain != null) {
                    CampaignService.updateCampainState(campain,
                            AdsapientConstants.DEFAULT_VERIFYING_STATE_ID);
                } else {
                    logger.warn("trying to verify campain with id that not exist="
                                    + form.getElementId());
                }
            } else {
                logger.warn("trying to call verify but campainId was not specifik");
            }
        }

        if (AdsapientConstants.SITE.equalsIgnoreCase(form.getType())) {
            if (form.getElementId() != null) {
                SiteImpl site = (SiteImpl) hibernateEntityDao.loadObject(
                        SiteImpl.class, new Integer(form.getElementId()));

                if (site != null) {
                    site
                            .setStateId(AdsapientConstants.DEFAULT_VERIFYING_STATE_ID);

                    hibernateEntityDao.updateObject(site);

//                    Place2SiteMap.reload();
                }
            }
        }

        if (AdsapientConstants.USER.equalsIgnoreCase(form.getType())) {
            if (form.getElementId() != null) {
                UserImpl user = (UserImpl) hibernateEntityDao.loadObject(
                        UserImpl.class, new Integer(form.getElementId()));

                if (user != null) {
                    user
                            .setStateId(AdsapientConstants.DEFAULT_VERIFYING_STATE_ID);

                    hibernateEntityDao.updateObject(user);
                }
            }
        }
    }

    private static Collection loadCollection(Class verifyongClass,
                                             String orderField) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection resultCollection = hibernateEntityDao.viewWithCriteria(
                verifyongClass, AdsapientConstants.STATE_ID, new Integer(
                        AdsapientConstants.DEFAULT_NOT_VERIFY_STATE_ID),
                orderField);

        return resultCollection;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
