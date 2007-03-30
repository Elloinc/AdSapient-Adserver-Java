package com.adsapient.gui.actions;

import com.adsapient.gui.ContextAwareGuiBean;
import com.adsapient.shared.service.InstallationService;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetupAction extends Action {
    private static Logger logger = Logger.getLogger(SetupAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm theForm,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String key = request.getParameter("key");
        if ((key != null) && ("jedi".equalsIgnoreCase(key))) {
            try {
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else if ((key != null) && ("vitaly".equalsIgnoreCase(key))) {
            try {
                InstallationService.updateTables();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return mapping.findForward("success");
    }
}
