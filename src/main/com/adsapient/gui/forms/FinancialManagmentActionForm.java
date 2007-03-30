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
package com.adsapient.gui.forms;

import com.adsapient.shared.mappable.Financial;
import com.adsapient.shared.service.FinancialsService;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;


public class FinancialManagmentActionForm extends ActionForm {
    private static final long serialVersionUID = 1L;
    private String action = "view";
    private String head;
    private String userId = null;
    private float advertisingCPCrate;
    private float advertisingCPMrate;
    private float advertisingCPLrate;
    private float advertisingCPSrate;
    private Integer commissionRate;
    private float publishingCPCrate;
    private float publishingCPMrate;
    private float publishingCPLrate;
    private float publishingCPSrate;
    private boolean pubCPC;
    private boolean pubCPM;
    private boolean pubCPL;
    private boolean pubCPS;
    private boolean advCPC;
    private boolean advCPM;
    private boolean advCPL;
    private boolean advCPS;
    private String advertiserType;
    private String publisherType;

    public String getAdvertiserType() {
        return advertiserType;
    }

    public void setAdvertiserType(String advertiserType) {
        this.advertiserType = advertiserType;
    }

    public String getPublisherType() {
        return publisherType;
    }

    public void setPublisherType(String publisherType) {
        this.publisherType = publisherType;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAdvertisingCPCrate(float advertisingCPCrate) {
        this.advertisingCPCrate = advertisingCPCrate;
    }

    public float getAdvertisingCPCrate() {
        return advertisingCPCrate;
    }

    public void setAdvertisingCPMrate(float advertisingCPMrate) {
        this.advertisingCPMrate = advertisingCPMrate;
    }

    public float getAdvertisingCPMrate() {
        return advertisingCPMrate;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setPublishingCPCrate(float publishingCPCrate) {
        this.publishingCPCrate = publishingCPCrate;
    }

    public float getPublishingCPCrate() {
        return publishingCPCrate;
    }

    public void setPublishingCPMrate(float publishingCPMrate) {
        this.publishingCPMrate = publishingCPMrate;
    }

    public float getPublishingCPMrate() {
        return publishingCPMrate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void initForm(Financial financOptions) {
        int publisherTokensCounter = 0;
        int advertiserTokensCounter = 0;

        this.setAdvertisingCPCrate(FinancialsService.transformMoney(
                financOptions.getAdvertisingCPCrate()));
        this.setAdvertisingCPMrate(FinancialsService.transformMoney(
                financOptions.getAdvertisingCPMrate()));
        this.setAdvertisingCPLrate(FinancialsService.transformMoney(
                financOptions.getAdvertisingCPLrate()));
        this.setAdvertisingCPSrate(FinancialsService.transformMoney(
                financOptions.getAdvertisingCPSrate()));

        this.setPublishingCPCrate(FinancialsService.transformMoney(
                financOptions.getPublishingCPCrate()));
        this.setPublishingCPMrate(FinancialsService.transformMoney(
                financOptions.getPublishingCPMrate()));
        this.setPublishingCPLrate(FinancialsService.transformMoney(
                financOptions.getPublishingCPLrate()));
        this.setPublishingCPSrate(FinancialsService.transformMoney(
                financOptions.getPublishingCPSrate()));

        this.setCommissionRate(financOptions.getCommissionRate());

        this.setUserId(financOptions.getUserId().toString());

        this.publisherType = financOptions.getPublishingType();
        this.advertiserType = financOptions.getAdvertisingType();

        StringTokenizer tokenizer = new StringTokenizer(financOptions.getPublishingType(),
                ",");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            boolean checkBoxValue = false;

            if ("1".equalsIgnoreCase(token)) {
                checkBoxValue = true;
            }

            if (0 == publisherTokensCounter) {
                pubCPM = checkBoxValue;
            }

            if (1 == publisherTokensCounter) {
                pubCPC = checkBoxValue;
            }

            if (2 == publisherTokensCounter) {
                pubCPL = checkBoxValue;
            }

            if (3 == publisherTokensCounter) {
                pubCPS = checkBoxValue;
            }

            publisherTokensCounter++;
        }

        StringTokenizer advTokenizer = new StringTokenizer(financOptions.getAdvertisingType(),
                ",");

        while (advTokenizer.hasMoreTokens()) {
            String token = advTokenizer.nextToken();
            boolean checkBoxValue = false;

            if ("1".equalsIgnoreCase(token)) {
                checkBoxValue = true;
            }

            if (0 == advertiserTokensCounter) {
                advCPM = checkBoxValue;
            }

            if (1 == advertiserTokensCounter) {
                advCPC = checkBoxValue;
            }

            if (2 == advertiserTokensCounter) {
                advCPL = checkBoxValue;
            }

            if (3 == advertiserTokensCounter) {
                advCPS = checkBoxValue;
            }

            advertiserTokensCounter++;
        }
    }

    public float getAdvertisingCPLrate() {
        return advertisingCPLrate;
    }

    public void setAdvertisingCPLrate(float advertisingCPLrate) {
        this.advertisingCPLrate = advertisingCPLrate;
    }

    public float getAdvertisingCPSrate() {
        return advertisingCPSrate;
    }

    public void setAdvertisingCPSrate(float advertisingCPSrate) {
        this.advertisingCPSrate = advertisingCPSrate;
    }

    public float getPublishingCPLrate() {
        return publishingCPLrate;
    }

    public void setPublishingCPLrate(float publishingCPLrate) {
        this.publishingCPLrate = publishingCPLrate;
    }

    public float getPublishingCPSrate() {
        return publishingCPSrate;
    }

    public void setPublishingCPSrate(float publishingCPSrate) {
        this.publishingCPSrate = publishingCPSrate;
    }

    public boolean isAdvCPC() {
        return advCPC;
    }

    public void setAdvCPC(boolean advCPC) {
        this.advCPC = advCPC;
    }

    public boolean isAdvCPL() {
        return advCPL;
    }

    public void setAdvCPL(boolean advCPL) {
        this.advCPL = advCPL;
    }

    public boolean isAdvCPM() {
        return advCPM;
    }

    public void setAdvCPM(boolean advCPM) {
        this.advCPM = advCPM;
    }

    public boolean isAdvCPS() {
        return advCPS;
    }

    public void setAdvCPS(boolean advCPS) {
        this.advCPS = advCPS;
    }

    public boolean isPubCPC() {
        return pubCPC;
    }

    public void setPubCPC(boolean pubCPC) {
        this.pubCPC = pubCPC;
    }

    public boolean isPubCPL() {
        return pubCPL;
    }

    public void setPubCPL(boolean pubCPL) {
        this.pubCPL = pubCPL;
    }

    public boolean isPubCPM() {
        return pubCPM;
    }

    public void setPubCPM(boolean pubCPM) {
        this.pubCPM = pubCPM;
    }

    public boolean isPubCPS() {
        return pubCPS;
    }

    public void setPubCPS(boolean pubCPS) {
        this.pubCPS = pubCPS;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
    }

    public ActionErrors validate(ActionMapping actionMapping,
        HttpServletRequest httpServletRequest) {
        ActionErrors errors = new ActionErrors();

        if ("defaultEdit".equalsIgnoreCase(this.action)) {
            if (this.advertisingCPMrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("defaultCPMrate.required"));

                return errors;
            }

            if (this.advertisingCPCrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("defaultCPCrate.required"));

                return errors;
            }

            if (this.advertisingCPLrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("defaultCPLrate.required"));

                return errors;
            }

            if (this.advertisingCPSrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("defaultCPSrate.required"));

                return errors;
            }

            if (this.publishingCPMrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("publishingCPMrate.required"));

                return errors;
            }

            if (this.publishingCPCrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("publishingCPCrate.required"));

                return errors;
            }

            if (this.publishingCPLrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("publishingCPLrate.required"));

                return errors;
            }

            if (this.publishingCPSrate <= 0.0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("publishingCPSrate.required"));

                return errors;
            }
        }

        return errors;
    }
}
