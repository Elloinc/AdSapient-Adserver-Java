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
package com.adsapient.api;

import com.adsapient.api.RateEnableInterface;
import com.adsapient.api.AdsapientException;

import com.adsapient.shared.mappable.RateImpl;

import com.adsapient.gui.forms.BannerUploadActionForm;

import java.io.File;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public interface Banner extends AdsapientInterceptedSupportEntity,
    RateEnableInterface, IMappable {
    public void setAltText(String altText);

    public String getAltText();

    public File getBannerFile();

    public Integer getBannerId();

    public void setCampainId(Integer campaignId);

    public Integer getCampainId();

    public String getContentType();

    public void setEndDate(String endDate);

    public String getEndDate();

    public String getFile();

    public String getHref();

    public void setLoadingTypeId(int loadingTypeId);

    public int getLoadingTypeId();

    public String getName();

    public void setName(String name);

    public void setOwnCampaigns(boolean ownCamapigns);

    public boolean isOwnCampaigns();

    public void setPlaceTypeId(int placeTypeId);

    public int getPlaceTypeId();

    public void setPrioritet(int prioritet);

    public int getPrioritet();

    public RateImpl getRate();

    public Integer getSizeId();

    public void setStartDate(String startDate);

    public String getStartDate();

    public void setStatisticId(String statisticId);

    public int getStatus();

    public void setStatusBartext(String statusBartext);

    public String getStatusBartext();

    public boolean isSuitable(PlacesInterface places,
        Collection bannersCollection);

    public void setTargetWindowId(int targetWindowId);

    public int getTargetWindowId();

    public Integer getTypeId();

    public void setUrl(String url);

    public String getUrl();

    public void setUserId(Integer userId);

    public Integer getUserId();

    public boolean add(BannerUploadActionForm form) throws AdsapientException;

    public String generateCustomHTML(HttpServletRequest request);

    public void initForm(BannerUploadActionForm form);

    public void remove(BannerUploadActionForm form) throws AdsapientException;

    public void update(BannerUploadActionForm form,
        boolean enableHiberanteUpdate);

    public String getExternalURL();

    public void setExternalURL(String externalURL);

    public String getBannerText();

    public String getSMSNumber();

    public String getCallNumber();
}
