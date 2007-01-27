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
package com.adsapient.api_impl.advertizer;

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.PlacesInterface;

import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.share.Type;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;

import com.adsapient.gui.forms.BannerUploadFormAction;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class BannerImpl extends CampainImpl implements Banner, Cloneable {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(BannerImpl.class);

	public static final int STATUS_ENABLED = 1;

	public static final int STATUS_DISABLED = 0;

	public static final int STATUS_DEFAULT = 2;

	public static final int STATUS_PUBLISHER_DEFAULT = 3;

	protected Integer bannerId;

	protected Integer sizeId;

	protected Integer typeId = Type.IMAGE_TYPE_ID;

	protected Size size;

	protected String contentType;

	protected String file;

	protected String fileName;

	protected String placeId;

	protected String statisticId;

	protected List<FilterInterface> filters;

	protected int status = STATUS_ENABLED;

	protected String externalURL;

	private String bannerText;

	private String SMSNumber;

	private String callNumber;

	private String smsText;

	private String listText;

	public BannerImpl() {
		super();
		this.setName("");
	}

	public BannerImpl(BannerUploadFormAction form) {
		super();
		this.setTypeId(form.getTypeId());
		this.setName(form.getBannerName());
		this.setSizeId(form.getSizeId());
		this.setBannerText(form.getBannerText());
		this.setSMSNumber(form.getSMSNumber());
		this.setCallNumber(form.getCallNumber());
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getListText() {
		return listText;
	}

	public void setListText(String listText) {
		this.listText = listText;
	}

	public String getExternalURL() {
		return externalURL;
	}

	public void setExternalURL(String externalURL) {
		this.externalURL = externalURL;
	}

	public File getBannerFile() {
		return BannerUploadUtil.getBannerFile(this);
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public CampainImpl getCampaign() {
		CampainImpl campain = null;

		logger.info("get campain by Hibernate load.Campain id is "
				+ this.getCampainId());
		campain = (CampainImpl) MyHibernateUtil.loadObject(CampainImpl.class,
				this.getCampainId());

		return campain;
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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getHref() {
		return "";
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Size getSize() {
		return size;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setStatisticId(String statisticId) {
		this.statisticId = statisticId;
	}

	public String getStatisticId() {
		return statisticId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusText(HttpServletRequest request) {
		String result = "";

		if (this.status == STATUS_ENABLED) {
			result = Msg.fetch("enabled.label", request);
		}

		if (this.status == STATUS_DISABLED) {
			result = Msg.fetch("disabled", request);
		}

		if ((this.status == STATUS_DEFAULT)
				|| (this.status == STATUS_PUBLISHER_DEFAULT)) {
			result = Msg.fetch("banner.default", request);
		}

		return result;
	}

	public boolean isSuitable(PlacesInterface places,
			Collection bannersCollection) {
		if (places == null) {
			logger.error("given places ==null");

			return false;
		}

		if (bannersCollection.contains(this.bannerId)) {
			return false;
		}

		if (places.isOwnCampaign()) {
			if (!places.getUserId().equals(this.getUserId())) {
				if (!this.getCampainId().equals(
						ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID)) {
					return false;
				}
			} else {
			}
		}

		if ((places.getPlaceTypeId() == this.getPlaceTypeId())
				|| (this.getPlaceTypeId() == AdsapientConstants.ANY)) {
		} else {
			return false;
		}

		if ((places.getLoadingTypeId() == this.getLoadingTypeId())
				|| (this.getLoadingTypeId() == AdsapientConstants.ANY)) {
		} else {
			return false;
		}

		if ((places.getTargetWindowId() == this.getTargetWindowId())
				|| (this.getTargetWindowId() == AdsapientConstants.ANY)) {
		} else {
			return false;
		}

		if (!this.getSizeId().equals(places.getSizeId())) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.DATE_FORMAT);

		if (this.getCampaign().getStateId() == ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID) {
			return true;
		}

		if ((places.getUserId().equals(this.getUserId()))
				&& (this.getCampaign().getStateId() != ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID)) {
			UserImpl user = (UserImpl) MyHibernateUtil.loadObject(
					UserImpl.class, this.getUserId());

			if (!RoleController.HOSTEDSERVICE.equalsIgnoreCase(user.getRole())) {
				return false;
			}
		}

		try {
			Date bannerBeginDate = sdf.parse(this.getStartDate());
			Date bannerEndDate = sdf.parse(this.getEndDate());

			Date currentDate = Calendar.getInstance().getTime();

			if ((currentDate.before(bannerBeginDate))
					|| (currentDate.after(bannerEndDate))) {
				return false;
			}
		} catch (java.text.ParseException e) {
			logger.error("while trying to parse banner date. The banner id is "
					+ bannerId + " the startDate=" + this.getStartDate()
					+ " the end dat =" + this.getEndDate(), e);
		}

		if (!this.getRate().isMatching(places.getRate())) {
			return false;
		}

		if (this.status == STATUS_ENABLED) {
			return true;
		}

		return false;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public boolean add(BannerUploadFormAction form) {
		form.setContentType(form.getTheFile().getContentType());

		this.update(form, false);
		this.setFile("");

		this.setFileName(form.getTheFile().getFileName());

		CampainImpl campainImpl = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class, form.getCampainId());
		campainImpl.udateBanner(this);

		if (this.getAltText() == null) {
			logger.warn("banner alt text=null hotia alt text campanii="
					+ campainImpl.getAltText());
			this.setAltText("");
		}

		String bannerId = String.valueOf(MyHibernateUtil.addObject(this));

		this.setBannerId(new Integer(bannerId));
		this.initForm(form);

		BannerUploadUtil.saveFile(form);
		this.update(form, true);
		MoneyManager.createBannerRate(this, form.getCampainId());

		MyHibernateUtil.updateObject(this);

		form.setAction("init");

		return true;
	}

	public boolean addCheck(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		form.setAction("init");

		return super.addCheck(actionForm, request);
	}

	public boolean deleteCheck(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		form.setAction("init");
		form.setCampainId(null);

		return super.deleteCheck(actionForm, request);
	}

	public boolean editCheck(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		form.setAction("init");

		return super.editCheck(actionForm, request);
	}

	public String generateCustomHTML(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();

		return buffer.toString();
	}

	public void initForm(BannerUploadFormAction form) {
		form.setBannerId(this.getBannerId());
		form.setCampainId(this.getCampainId());
		form.setFile(this.getFile());
		form.setSizeId(this.getSizeId());
		form.setTypeId(this.getTypeId());
		form.setUserId(this.getUserId());
		form.setUrl(this.getUrl());
		form.setContentType(this.getContentType());
		form.setStatus(this.getStatus());
		form.setStatusBartext(this.getStatusBartext());
		form.setAltText(this.getAltText());
		form.setPlaceTypeId(this.getPlaceTypeId());
		form.setTargetWindowId(this.getTargetWindowId());
		form.setLoadingTypeId(this.getLoadingTypeId());
		form.setBannerWeightId(this.getPrioritet());
		form.setBannerName(this.getName());
		form.setStartDate(this.getStartDate());
		form.setEndDate(this.getEndDate());
		form.setOwnCampaigns(this.isOwnCampaigns());
		form.setHtml(this.generateCustomHTML(form.getHsr()));
		form.setExternalURL(this.getExternalURL());
		form.setOwnCampaigns(this.isOwnCampaigns());
		form.setHtml(this.generateCustomHTML(form.getHsr()));
		form.setExternalURL(this.getExternalURL());
		form.setBannerText(this.getBannerText());
		form.setSMSNumber(this.getSMSNumber());
		form.setCallNumber(this.callNumber);
		form.setSmsText(this.smsText);
		form.setListText(this.listText);
	}

	public void remove(BannerUploadFormAction form) {
		BannerUploadUtil.removeBanner(form.getBannerId());

	}

	public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		this.add(form);

		return true;
	}

	public boolean resultDelete(ActionForm actionForm,
			HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		this.remove(form);

		return true;
	}

	public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		this.update(form, true);

		return true;
	}

	public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;

		form.setAction("afterupload");

		return true;
	}

	public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
		BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
		form.setAction("edit");

		Size size = (Size) MyHibernateUtil.loadObject(Size.class, this
				.getSizeId());

		form.setMaxBannerSize(size.getMaxBannerSize());

		this.initForm(form);

		return true;
	}

	public void update(BannerUploadFormAction form,
			boolean enableHibernateUpdate) {
		Size size = (Size) MyHibernateUtil.loadObject(Size.class, form
				.getSizeId());

		if (!"".equalsIgnoreCase(form.getTheFile().getFileName())
				&& (form.getTheFile().getFileSize() != 0)) {
			if (form.getTheFile().getFileSize() > size.getMaxBannerSize()) {
				ActionMessages messages = new ActionMessages();
				messages.add(Globals.ERROR_KEY, new ActionMessage(
						"big.file.size"));
			} else {
				BannerUploadUtil.removeFile(form.getFile());

				BannerUploadUtil.saveFile(form);

				this.setFileName(form.getTheFile().getFileName());

				this.setContentType(form.getTheFile().getContentType());
			}
		}

		this.setCampainId(form.getCampainId());
		this.setFile(form.getFile());
		this.setSizeId(form.getSizeId());
		this.setTypeId(form.getTypeId());
		this.setUserId(form.getUserId());
		this.setContentType(form.getContentType());
		this.setStatus(form.getStatus());
		this.setPrioritet(form.getBannerWeightId());
		this.setName(form.getBannerName());
		this.setStartDate(form.getStartDate());
		this.setEndDate(form.getEndDate());
		this.setBannerText(form.getBannerText());
		this.setSMSNumber(form.getSMSNumber());
		this.setCallNumber(form.getCallNumber());
		this.setSmsText(form.getSmsText());
		this.setListText(form.getListText());

		this.setAltText(form.getAltText());

		this.setStatusBartext(form.getStatusBartext());

		this.setUrl(form.getUrl());

		this.setExternalURL(form.getExternalURL());

		if (form.getLoadingTypeId() != 0) {
			this.setLoadingTypeId(form.getLoadingTypeId());
		}

		if (form.getTargetWindowId() != 0) {
			this.setTargetWindowId(form.getTargetWindowId());
		}

		if (form.getPlaceTypeId() != 0) {
			this.setPlaceTypeId(form.getPlaceTypeId());
		}

		if (enableHibernateUpdate) {
			MyHibernateUtil.updateObject(this);
		}

		updateReporterDB();

	}

	public Integer getId() {
		return bannerId;
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

	@Override
	public BannerImpl clone() {
		try {
			return (BannerImpl) super.clone();
		} catch (Exception e) {
			BannerImpl b = new BannerImpl();
			b.setAltText(altText);
			b.setBannerId(bannerId);
			b.setBannerText(bannerText);
			b.setBudget(budget);
			b.setCallNumber(callNumber);
			b.setCampainId(campainId);
			b.setContentType(contentType);
			b.setExternalURL(externalURL);
			b.setFile(file);
			b.setFileName(fileName);
			b.setListText(listText);
			b.setName(name);
			b.setPlaceId(placeId);
			b.setRate(rate);
			b.setSMSNumber(SMSNumber);
			b.setSmsText(smsText);
			b.setSizeId(sizeId);
			b.setTypeId(typeId);
			b.setUrl(url);

			return b;
		}
	}

	private void updateReporterDB() {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}

	public List<FilterInterface> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterInterface> filters) {
		this.filters = filters;
	}
}
