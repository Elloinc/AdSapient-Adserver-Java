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

import com.adsapient.api.TargetSupportInterface;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.share.Size;

import com.adsapient.util.LabelValueUtil;
import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.jsp.InputValidator;

import com.adsapient.gui.forms.EditCampainActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public class BannerUploadFormAction extends EditCampainActionForm
    implements TargetSupportInterface {
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(BannerUploadFormAction.class);
    private Collection bannerWeightCollection = null;
    private Collection sizesCollection = null;
    private Collection statesCollection = new ArrayList();
    private Collection typesCollection = null;
    private Collection userBanners = new ArrayList();
    private Collection userCampainsCollection = new ArrayList();
    private FormFile theFile;
    private String url;
    private String action = "init";
    private String altText;
    private String bannerName = "";
    private String contentType;
    private String file;
    private Integer sizeId;
    private String statusBartext;
    private String html;
    private int status;
    private int bannerWeightId;
    private int loadingTypeId;
    private int maxBannerSize;
    private int placeTypeId;
    private int targetWindowId;
    private Integer typeId;
    private String externalURL;
    private String bannerText;
    private String SMSNumber;
    private String callNumber;
    private String listText;
    private String smsText;

    public String getListText() {
        return listText;
    }

    public void setListText(String listText) {
        this.listText = listText;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public String getSMSNumber() {
        return SMSNumber;
    }

    public void setSMSNumber(String SMSNumber) {
        this.SMSNumber = SMSNumber;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getAltText() {
        return altText;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerName() {
        return bannerName;
    }

    public Collection getBannerWeightCollection() {
        if (bannerWeightCollection == null) {
            bannerWeightCollection = LabelValueUtil.fillCustomWeightCollection(1,
                    10);
        }

        return bannerWeightCollection;
    }

    public void setBannerWeightId(int bannerWeightId) {
        this.bannerWeightId = bannerWeightId;
    }

    public int getBannerWeightId() {
        return bannerWeightId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public void setLoadingTypeId(int loadingTypeId) {
        this.loadingTypeId = loadingTypeId;
    }

    public int getLoadingTypeId() {
        return loadingTypeId;
    }

    public void setMaxBannerSize(int maxBannerSize) {
        this.maxBannerSize = maxBannerSize;
    }

    public int getMaxBannerSize() {
        return maxBannerSize;
    }

    public void setPlaceTypeId(int placeTypeId) {
        this.placeTypeId = placeTypeId;
    }

    public int getPlaceTypeId() {
        return placeTypeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public Collection getSizesCollection() {
        if (sizesCollection == null) {
            sizesCollection = LabelValueUtil.fillSizeValueLabel();
        }

        return sizesCollection;
    }

    public void setSizesCollection(Collection sizesCollection) {
        this.sizesCollection = sizesCollection;
    }

    public void setStatusBartext(String statusBartext) {
        this.statusBartext = statusBartext;
    }

    public String getStatusBartext() {
        return statusBartext;
    }

    public void setTargetWindowId(int targetWindowId) {
        this.targetWindowId = targetWindowId;
    }

    public int getTargetWindowId() {
        return targetWindowId;
    }

    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }

    public FormFile getTheFile() {
        return theFile;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Collection getTypesCollection() {
        if (typesCollection == null) {
            typesCollection = LabelValueUtil.fillTypeValueLabel();
        }

        return typesCollection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserBanners(Collection userBanners) {
        this.userBanners = userBanners;
    }

    public Collection getUserBanners() {
        return userBanners;
    }

    public Collection getUserCampainsCollection() {
        if (userCampainsCollection.isEmpty()) {
            userCampainsCollection = LabelValueUtil.fillCampainsValueLabel(this.getHsr());
        }

        return userCampainsCollection;
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
        ActionErrors errors = new ActionErrors();
        InputValidator validator = new InputValidator();

        if ("campainBanners".equalsIgnoreCase(this.action)) {
            String str = super.getAction();

            if ("init".equalsIgnoreCase(str)) {
                return errors;
            }
        }

        if ("afterupload".equalsIgnoreCase(this.action)) {
        }

        if ("add".equalsIgnoreCase(this.action) ||
                "edit".equalsIgnoreCase(this.action)) {
            if ((this.getCampainId() == null)) {
                errors.add(Globals.ERROR_KEY,
                    new ActionMessage("first.register.campain"));
            }

            if ((this.bannerName == null) ||
                    (this.bannerName.trim().length() == 0)) {
                errors.add(Globals.ERROR_KEY,
                    new ActionMessage("error.bannername.required"));

                return errors;
            }

            if (!validator.isAlphanumeric(bannerName, "_., /:@&")) {
                errors.add(Globals.ERROR_KEY,
                    new ActionMessage("errors.alphanum",
                        Msg.fetch("banner.name", arg1)));

                return errors;
            }

            if ((this.getStatus() != BannerImpl.STATUS_DEFAULT) &&
                    (this.getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT) &&
                    ((this.getStartDate() != null) &&
                    (this.getEndDate() != null) &&
                    (this.getStartDate().length() > 0) &&
                    (this.getEndDate().length() > 0))) {
                Date stDate;
                Date edDate;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    stDate = sdf.parse(this.getStartDate());
                    edDate = sdf.parse(this.getEndDate());

                    if (edDate.before(stDate)) {
                        errors.add(Globals.ERROR_KEY,
                            new ActionMessage("end.date.invalid"));

                        return errors;
                    }
                } catch (Exception e) {
                    logger.error("", e);
                    errors.add(Globals.ERROR_KEY,
                        new ActionMessage("date.invalid"));

                    return errors;
                }
            }

            if ((this.url == null) || (this.url.trim().length() == 0) ||
                    this.url.equals("http:")) {
                errors.add(Globals.ERROR_KEY,
                    new ActionMessage("error.url.required"));

                return errors;
            }

            if ("add".equalsIgnoreCase(this.action)) {
                if ((this.theFile.getFileName() == null) ||
                        (this.theFile.getFileName().length() == 0)) {
                    errors.add(Globals.ERROR_KEY,
                        new ActionMessage("error.file.required"));

                    return errors;
                }
            }

            Size bannerSize = (Size) MyHibernateUtil.loadObject(Size.class,
                    this.getSizeId());

            if (bannerSize != null) {
                if (this.theFile.getFileSize() > bannerSize.getMaxBannerSize()) {
                    errors.add(Globals.ERROR_KEY,
                        new ActionMessage("big.file.size"));
                }
            } else {
                logger.error("cant load size for id=" + this.getSizeId());
            }
        }

        return errors;
    }

    public Collection getStatesCollection() {
        if (statesCollection.isEmpty()) {
            statesCollection = LabelValueUtil.fillBannerStatesValueLabel(this.getHsr());
        }

        return statesCollection;
    }

    public void setStatesCollection(Collection statesCollection) {
        this.statesCollection = statesCollection;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }
}
