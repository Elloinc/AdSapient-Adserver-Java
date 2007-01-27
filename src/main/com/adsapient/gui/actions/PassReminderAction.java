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

import com.adsapient.util.admin.email.EmailSender;
import com.adsapient.gui.forms.PassReminderForm;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PassReminderAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm theForm,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        PassReminderForm form = (PassReminderForm) theForm;

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("send");

            return mapping.findForward("remind");
        }

        if ("send".equalsIgnoreCase(form.getAction())) {
            String email = form.getEmail();

            ActionMessages messages = new ActionMessages();

            if (EmailSender.sendUserCredentional(email, request)) {
                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("login.forgotyourpassword.emailsent"));
                saveMessages(request, messages);

                return mapping.findForward("success");
            } else {
                messages.add(Globals.ERROR_KEY,
                    new ActionMessage("email.failed"));
                saveErrors(request, messages);

                return mapping.findForward("remind");
            }
        }

        return mapping.findForward("success");
    }
}
