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

import com.adsapient.gui.forms.ContactForm;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ContactAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm theForm,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ContactForm form = (ContactForm) theForm;

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("inited");

            if ("login.contact".equals(mapping.getInput())) {
                form.setInput("contact");
            } else {
                form.setInput("contactus");
            }

            return mapping.findForward("contact");
        }

        String email = form.getEmail();

        ActionMessages am = new ActionMessages();
        am.add(Globals.MESSAGE_KEY,
                new ActionMessage("login.contact.thankyou.text"));
        saveMessages(request, am);

        return mapping.findForward("success");
    }
}
