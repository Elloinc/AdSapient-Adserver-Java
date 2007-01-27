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

import com.adsapient.api_impl.usermanagment.RateImpl;

import com.adsapient.util.financial.MoneyTransformer;

import org.apache.struts.action.ActionForm;


public class RateManagmentActionForm extends ActionForm {
    private static final long serialVersionUID = 1L;
    private String action = "view";
    private String head = "";
    private float cpcRate;
    private float cplRate;
    private float cpmRate;
    private float cpsRate;
    private int rateId;
    private boolean advCPC;
    private boolean advCPM;
    private boolean advCPL;
    private boolean advCPS;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCpcRate(float cpcRate) {
        this.cpcRate = cpcRate;
    }

    public float getCpcRate() {
        return cpcRate;
    }

    public void setCplRate(float cplRate) {
        this.cplRate = cplRate;
    }

    public float getCplRate() {
        return cplRate;
    }

    public void setCpmRate(float cpmRate) {
        this.cpmRate = cpmRate;
    }

    public float getCpmRate() {
        return cpmRate;
    }

    public void setCpsRate(float cpsRate) {
        this.cpsRate = cpsRate;
    }

    public float getCpsRate() {
        return cpsRate;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getRateId() {
        return rateId;
    }

    public void initForm(RateImpl rate) {
        this.cpcRate = MoneyTransformer.transformMoney(rate.getCpcRate());
        this.cpmRate = MoneyTransformer.transformMoney(rate.getCpmRate());
        this.cpsRate = MoneyTransformer.transformMoney(rate.getCpsRate());
        this.cplRate = MoneyTransformer.transformMoney(rate.getCplRate());

        this.rateId = rate.getRateId().intValue();

        RateImpl.parseTypeValue(rate.getRateType(), this);
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
}
