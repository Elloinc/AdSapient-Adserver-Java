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
package com.adsapient.gui.actions;

import com.adsapient.gui.forms.AdminsTuningActionForm;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.Category;
import com.adsapient.shared.mappable.EntityWrap;
import com.adsapient.shared.mappable.PlaceImpl;
import com.adsapient.shared.mappable.WrapperHelper;
import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.service.ValidationService;
import com.adsapient.shared.service.WrapperService;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminsTuningAction extends SecureAction {
    private HibernateEntityDao hibernateEntityDao;

    public ActionForward secureExecute(ActionMapping mapping,
                                       ActionForm actionForm, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        AdminsTuningActionForm form = (AdminsTuningActionForm) actionForm;
        ActionMessages messages = new ActionMessages();

        ActionErrors aes = validate(mapping, request, form);
        if (aes.size() > 0) {
            saveErrors(request, aes);
            return mapping.getInputForward();
        }

        if ("remove".equalsIgnoreCase(form.getAction())) {
            EntityWrap entity = new EntityWrap();

            entity.setEntityId(form.getEntityId());
            entity.setEntityType(form.getEntity());

            WrapperHelper.deleteEntity(entity);

            messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.removed"));
            saveMessages(request, messages);
            form.setAction("view");
        }

        if ("add".equalsIgnoreCase(form.getAction())) {
            EntityWrap entity = new EntityWrap();
            entity.setEntityType(form.getEntity());
            entity.setEntityValue(form.getEntityValue());

            if (WrapperHelper.addEntity(entity) == true) {
                messages.add(Globals.MESSAGE_KEY,
                        new ActionMessage("success.updated"));
                saveMessages(request, messages);
            } else {
                if ("categorys".equalsIgnoreCase(form.getEntity())) {
                    messages.add(Globals.MESSAGE_KEY,
                            new ActionMessage("errors.cat.exists"));
                    saveMessages(request, messages);
                } else if ("positions".equalsIgnoreCase(form.getEntity())) {
                    messages.add(Globals.MESSAGE_KEY,
                            new ActionMessage("errors.pos.exists"));
                    saveMessages(request, messages);
                } else {
                    messages.add(Globals.MESSAGE_KEY,
                            new ActionMessage("errors.exists",
                                    I18nService.fetch(entity.getEntityType(), request)));
                    saveMessages(request, messages);
                }
            }

            form.setAction("view");
            form.setEntityValue("");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");
        }

        if ("categorys".equalsIgnoreCase(form.getEntity())) {
            form.setEntitysCollection(WrapperService.wrapCollection(
                    hibernateEntityDao.viewAll(Category.class)));
        }

        if ("positions".equalsIgnoreCase(form.getEntity())) {
            form.setEntitysCollection(WrapperService.wrapCollection(
                    hibernateEntityDao.viewAll(PlaceImpl.class)));
        }

        return mapping.findForward("view");
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1, AdminsTuningActionForm form) {
        ActionErrors errors = new ActionErrors();
        if ("add".equalsIgnoreCase(form.getAction()) || "edit".equalsIgnoreCase(form.getAction())) {
            ValidationService validator = new ValidationService();

            if ((form.getEntityValue() == null) || (form.getEntityValue().trim().length() == 0)) {
                if ("categorys".equalsIgnoreCase(form.getEntity())) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                            new ActionError("error.category.name.required"));

                    return errors;
                }

                if ("positions".equalsIgnoreCase(form.getEntity())) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                            new ActionError("error.position.name.required"));

                    return errors;
                }
            }

            if ("categorys".equalsIgnoreCase(form.getEntity())) {
                form.setAllowchars("\\s_.,-");
            }

            if ("positions".equalsIgnoreCase(form.getEntity())) {
                form.setAllowchars("-");
            }

            if (!validator.isAlphabets(form.getEntityValue(), form.getAllowchars())) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("errors.alphabets",
                                I18nService.fetch(form.getEntity() + ".name", arg1)));

                return errors;
            }
        }

        return errors;
    }

    protected void checkAccessRestriction(HttpServletRequest request,
                                          ActionForm actionForm) throws AdsapientSecurityException {
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}

