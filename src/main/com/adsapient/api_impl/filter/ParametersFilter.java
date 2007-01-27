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
package com.adsapient.api_impl.filter;

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.settings.DynamicParameter;
import com.adsapient.api_impl.settings.ParameterImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;

import com.adsapient.gui.actions.FilterAction;
import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

public class ParametersFilter implements FilterInterface, IMappable {
	public static Logger logger = Logger.getLogger(ParametersFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedParametersFilterIds";

	private Map<String, String> keyValueParametersMap;

	private Integer bannerId;

	private Collection allParameters = null;

	private Integer campainId;

	private Map parametersValues = new TreeMap();

	private Set<DynamicParameter> parameters;

	private Integer parametersFilterId;

	public ParametersFilter() {
	}

	public Collection getAllParameters() {
		if (allParameters == null) {
			allParameters = MyHibernateUtil.viewAll(ParameterImpl.class);
		}

		return allParameters;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public DynamicParameter getParameter(String name) {
		DynamicParameter result = null;

		if ((parameters != null) && (parameters.size() > 0)) {
			for (Iterator<DynamicParameter> dparameters = parameters.iterator(); dparameters
					.hasNext();) {
				DynamicParameter dparam = dparameters.next();

				if (name.equals(dparam.getName())) {
					result = dparam;
				}
			}
		} else {
			for (Iterator dparameters = getAllParameters().iterator(); dparameters
					.hasNext();) {
				ParameterImpl param = (ParameterImpl) dparameters.next();

				if (name.equals(param.getName())) {
					result = new DynamicParameter(param);
				}
			}
		}

		return result;
	}

	public void setParameters(Set<DynamicParameter> parameters) {
		this.parameters = parameters;
	}

	public Set<DynamicParameter> getParameters() {
		return parameters;
	}

	public Integer getParametersFilterId() {
		return parametersFilterId;
	}

	public void setParametersFilterId(Integer parametersFilterId) {
		this.parametersFilterId = parametersFilterId;
	}

	public void add(FilterInterface filter) {
		if (filter instanceof ParametersFilter) {
			ParametersFilter parametersFilterTemplate = (ParametersFilter) filter;
		} else {
			logger.warn("given object isnt instance of ParametersFilter");
		}
	}

	public void addNew() {
		parameters = new HashSet();

		parametersFilterId = MyHibernateUtil.addObject(this);

		for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
				.hasNext();) {
			ParameterImpl param = (ParameterImpl) parametersIterator.next();
		}
	}

	public Object copy() {
		ParametersFilter newParametersFilter = new ParametersFilter();

		newParametersFilter.setCampainId(this.campainId);
		newParametersFilter.setParameters(this.parameters);
		newParametersFilter.setParametersFilterId(this.parametersFilterId);

		return newParametersFilter;
	}

	@Override
	public ParametersFilter clone() throws CloneNotSupportedException {
		ParametersFilter filter = (ParametersFilter) super.clone();

		if (this.parameters == null) {
			filter.parameters = null;
		} else {
			filter.parameters = new HashSet();

			for (Iterator iter = this.parameters.iterator(); iter.hasNext();) {
				DynamicParameter dp = (DynamicParameter) iter.next();
				DynamicParameter n = new DynamicParameter();
				n.setLabel(dp.getLabel());
				n.setName(dp.getName());
				n.setParameterFilterId(dp.getParameterFilterId());
				n.setParameterId(dp.getParameterId());
				n.setRegex(dp.isRegex());
				n.setType(dp.getType());
				n.setValue(dp.getValue());
				filter.parameters.add(n);
			}
		}

		return filter;
	}

	public boolean doFilter(HttpServletRequest request) {
		String requestValue = null;
		boolean result = true;

		for (Iterator parametersIterator = parameters.iterator(); parametersIterator
				.hasNext();) {
			DynamicParameter param = (DynamicParameter) parametersIterator
					.next();
			requestValue = request.getParameter(param.getName());

			if (requestValue != null) {
				if ((param.getType() == param.STRING_TYPE_ID)
						&& param.isRegex()) {
					boolean isRegExpr = true;

					try {
						Pattern pattern = Pattern.compile(param.getValue());
						Matcher matcher = pattern.matcher(requestValue);

						matcher.matches();
						matcher.find();
					} catch (PatternSyntaxException e) {
						isRegExpr = false;
						logger.info(e.getMessage());
					} catch (IllegalStateException e) {
						isRegExpr = false;
						logger.info(e.getMessage());
					}

					if (isRegExpr) {
						logger.info("Using reg expresion rules. RegExpr:"
								+ param.getValue() + " value:" + requestValue);

						Pattern pattern = Pattern.compile(param.getValue());
						Matcher matcher = pattern.matcher(requestValue);

						result = matcher.find();
						logger.info("RegExpr result:" + result);

						return result;
					} else {
						break;
					}
				}

				StringTokenizer tokenizer = new StringTokenizer(param
						.getValue(), ";");
				result = false;

				while (tokenizer.hasMoreTokens()) {
					String paramValue = tokenizer.nextToken();

					if (requestValue.equals(paramValue)) {
						result = true;
					}
				}

				if (!result) {
					showLogInfo(param.getName(), requestValue);

					return false;
				}
			}
		}

		return result;
	}

	public String generateSourceStructure(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();

		for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
				.hasNext();) {
			ParameterImpl param = (ParameterImpl) parametersIterator.next();

			buffer.append("<tr><td class=\"tabledata\">");
			buffer.append(param.getLabel() + "</td>");

			if (param.getType() == param.STRING_TYPE_ID) {
				DynamicParameter dparam = getParameter(param.getName());

				buffer
						.append("<td class=\"tabledata\" colspan=\"3\"><input type=\"text\" name=\""
								+ param.getName()
								+ "\" size=\"60\" value=\""
								+ (((parametersValues.get(param.getName()) == null) || parametersValues
										.get(param.getName()).equals("null")) ? ""
										: parametersValues.get(param.getName()))
								+ "\"></td>");
			}

			if (param.getType() == param.PREDEFINED_TYPE_ID) {
				buffer
						.append("<td class=\"tabledata\">"
								+ Msg.fetch("possible.choices", request)
								+ "</br><select name=\""
								+ param.getName()
								+ "\" multiple=\"multiple\" class=\"uiOptionParameters_target\" id=\""
								+ param.getName() + "_source\">");

				StringTokenizer tokenizer = new StringTokenizer(param
						.getValue(), ";");

				while (tokenizer.hasMoreTokens()) {
					String paramValue = tokenizer.nextToken();
					buffer.append("<option value=\"" + paramValue + "\">"
							+ paramValue + "</option>");
				}

				buffer.append("</select></td>");

				buffer
						.append("<td class=\"tabledata\" width=\"20px\"><input type=\"button\" value=\"&raquo;\"");
				buffer.append("title='"
						+ Msg.fetch("move.all.options.to.target", request)
						+ "' ");
				buffer
						.append("onClick=\"uiOptionTransfer_transferAll("
								+ param.getName()
								+ "_object); return false;\" /><br/>");

				buffer.append("<input type=\"button\" value=\"&gt;\"");
				buffer.append("title='"
						+ Msg.fetch("move.selected.options.to.target.list",
								request) + "' ");
				buffer
						.append("onClick=\"uiOptionTransfer_transfer("
								+ param.getName()
								+ "_object); return false;\" /><br/>");

				buffer.append("<input type=\"button\" value=\"&lt;\"");
				buffer.append("title='"
						+ Msg.fetch("delete.selected.options.from.target",
								request) + "' ");
				buffer
						.append("onClick=\"uiOptionTransfer_return("
								+ param.getName()
								+ "_object); return false;\" /><br/>");

				buffer.append("<input type=\"button\" value=\"&laquo;\"");
				buffer.append("title='"
						+ Msg.fetch("delete.all.options.from.target", request)
						+ "' ");
				buffer.append("onClick=\"uiOptionTransfer_returnAll("
						+ param.getName() + "_object); return false;\" />");
				buffer.append("</td>");

				buffer
						.append("<td class=\"tabledata\">"
								+ Msg.fetch("actual.selection", request)
								+ "</br><select name=\""
								+ param.getName()
								+ "\" multiple=\"multiple\" class=\"uiOptionParameters_target\" id=\""
								+ param.getName() + "_target\">");

				String paramName = (String) parametersValues.get(param
						.getName());

				if (paramName == null) {
					paramName = "";
				}

				tokenizer = new StringTokenizer(paramName, ";");

				while (tokenizer.hasMoreTokens()) {
					String paramValue = tokenizer.nextToken();
					buffer.append("<option value=\"" + paramValue + "\">"
							+ paramValue + "</option>");
				}

				buffer.append("</select></td>");

				buffer.append("<script language=\"javascript\">\n");
				buffer.append("var " + param.getName() + "_object;\n");

				buffer.append("function " + param.getName() + "_init() {\n");
				buffer.append("  var src = document.getElementById('"
						+ param.getName() + "_source');\n");
				buffer
						.append("  "
								+ param.getName()
								+ "_object = new uiOptionTransfer_Globals(null, null, '"
								+ param.getName() + "_source', '"
								+ param.getName() + "_target', '"
								+ param.getName() + "_distance');\n");
				buffer.append("  uiOptionTransfer_fillStates("
						+ param.getName() + "_object);\n");
				buffer.append("  uiOptionTransfer_onSubmit(" + param.getName()
						+ "_object);\n");
				buffer.append("}\n");
				buffer.append(param.getName() + "_init();\n");
				buffer.append("</script>\n");
			}

			buffer.append("</tr>");
		}

		String list_names = "";

		for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
				.hasNext();) {
			ParameterImpl param = (ParameterImpl) parametersIterator.next();

			if (param.getType() == param.PREDEFINED_TYPE_ID) {
				if (list_names.length() > 0) {
					list_names += ",";
				}

				list_names += (param.getName() + "_object");
				buffer.append("<input type=\"hidden\" name=\"selected_"
						+ param.getName() + "\" id=\"selected_"
						+ param.getName() + "_target\" value=\"\"/>\n");
			}
		}

		buffer.append("\n<script language=\"javascript\">\n");
		buffer.append("function transform(){\n");
		buffer.append("	lists = new Array(" + list_names + ");\n");
		buffer.append("	transform_any_categorys(lists);\n");
		buffer.append("}</script>\n");

		return buffer.toString();
	}

	public boolean haveParameter(ParameterImpl param) {
		boolean result = true;

		for (Iterator<DynamicParameter> parametersIterator = parameters
				.iterator(); parametersIterator.hasNext();) {
			DynamicParameter curentParam = parametersIterator.next();

			if (!param.getName().equals(curentParam.getName())) {
				result = false;
			}
		}

		return result;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;

		if ((parameters != null) && (parameters.size() > 0)) {
			for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
					.hasNext();) {
				ParameterImpl param = (ParameterImpl) parametersIterator.next();
				DynamicParameter dparam = getParameter(param.getName());

				if (dparam == null) {
					dparam = new DynamicParameter(param);
					dparam.setParameterFilterId(this.parametersFilterId);
					MyHibernateUtil.addObject(dparam);
					parameters.add(dparam);
				}
			}

			for (Iterator parametersIterator = parameters.iterator(); parametersIterator
					.hasNext();) {
				DynamicParameter param = (DynamicParameter) parametersIterator
						.next();
				parametersValues.put(param.getName(), param.getValue());
			}
		}

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}

		form.setParametersSource(generateSourceStructure(request));
		form.setFilterAction(FilterAction.UPDATE);
	}

	public void reset() {
		MyHibernateUtil.removeWithCriteria(ParametersFilter.class,
				"parameterFilterId", this.getParametersFilterId());

		ParametersFilter filter = (ParametersFilter) MyHibernateUtil
				.loadObject(ParametersFilter.class, this
						.getParametersFilterId());

		if (filter != null) {
			this.setParameters(filter.getParameters());
		}
	}

	public String save() {
		Integer id = MyHibernateUtil.addObject(this);

		if (parameters == null) {
			for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
					.hasNext();) {
				ParameterImpl param = (ParameterImpl) parametersIterator.next();
				DynamicParameter dparam = new DynamicParameter(param);
				dparam.setParameterFilterId(id);
				MyHibernateUtil.addObject(dparam);
			}
		} else {
			for (Iterator parametersIterator = getParameters().iterator(); parametersIterator
					.hasNext();) {
				DynamicParameter param = (DynamicParameter) parametersIterator
						.next();
				param.setParameterFilterId(id);
				MyHibernateUtil.addObject(param);
			}
		}

		return String.valueOf(id);
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateupdate) {
		FilterActionForm form = (FilterActionForm) actionForm;

		String value = null;
		logger.info("enableHibernateupdate is " + enableHibernateupdate);

		Set currentParameters = new HashSet();

		for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
				.hasNext();) {
			ParameterImpl param = (ParameterImpl) parametersIterator.next();
			value = request.getParameter(param.getName());

			if (param.getType() == param.PREDEFINED_TYPE_ID) {
				value = request.getParameter("selected_" + param.getName());
			}

			if (value != null) {
				Collection dparams = MyHibernateUtil.viewWithCriteria(
						DynamicParameter.class, "name", param.getName(),
						"parameterFilterId", parametersFilterId);
				DynamicParameter dparam;

				if ((dparams == null) || dparams.isEmpty()) {
					dparam = new DynamicParameter(param);
					dparam.setParameterFilterId(this.parametersFilterId);
				} else {
					dparam = (DynamicParameter) dparams.iterator().next();
				}

				dparam.setValue(value);

				if (param.getType() == param.STRING_TYPE_ID) {
					String isRegexp = request.getParameter("isRegexp_"
							+ param.getName());

					if ((isRegexp != null) && (isRegexp.length() > 0)) {
						dparam.setRegex(true);
					} else {
						dparam.setRegex(false);
					}
				}

				currentParameters.add(dparam);
				parametersValues.put(dparam.getName(), value);

				if (enableHibernateupdate) {
					MyHibernateUtil.saveOrUpdateObject(dparam);
				}
			}
		}

		if (enableHibernateupdate) {
			MyHibernateUtil.updateObject(this);
		}

		setParameters(currentParameters);

		form.setParametersSource(generateSourceStructure(request));
	}

	private void showLogInfo(String paramName, String paramValue) {
		for (Iterator parametersIterator = getAllParameters().iterator(); parametersIterator
				.hasNext();) {
			ParameterImpl param = (ParameterImpl) parametersIterator.next();

			if (param.getName().equals(paramName)) {
				StringTokenizer tokenizer = new StringTokenizer(param
						.getValue(), ";");

				while (tokenizer.hasMoreTokens()) {
					String defParamValue = tokenizer.nextToken();

					if (paramValue.equals(defParamValue)) {
						logger.info("Parameter " + paramName
								+ " was filtered for value:" + paramValue);

						return;
					}
				}

				logger.info("Parameter " + paramName + " with value:"
						+ paramValue
						+ " not be applied for filter with values:"
						+ param.getValue());
			}
		}
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public Integer getId() {
		return parametersFilterId;
	}

	public Map<String, String> getKeyValueParametersMap() {
		if (keyValueParametersMap == null) {
			keyValueParametersMap = new HashMap<String, String>();

			if (parameters == null) {
				return keyValueParametersMap;
			}

			Iterator it = parameters.iterator();

			while (it.hasNext()) {
				DynamicParameter dp = (DynamicParameter) it.next();
				keyValueParametersMap.put(dp.getName(), dp.getValue());
			}
		}

		return keyValueParametersMap;
	}

	public void setKeyValueParametersMap(
			Map<String, String> keyValueParametersMap) {
		this.keyValueParametersMap = keyValueParametersMap;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getRelatedFiltersQueryName() {
		return relatedFiltersQueryName;
	}
}
