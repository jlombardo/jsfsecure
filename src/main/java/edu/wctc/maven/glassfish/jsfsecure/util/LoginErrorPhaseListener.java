package edu.wctc.maven.glassfish.jsfsecure.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

public class LoginErrorPhaseListener implements PhaseListener
{
    private static final long serialVersionUID = -1216620620302322995L;
    @Override
    public void afterPhase(final PhaseEvent arg0)
    {}
    @Override
    public void beforePhase(final PhaseEvent arg0)
    {
        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(
                WebAttributes.AUTHENTICATION_EXCEPTION);
        if (e instanceof BadCredentialsException)
        {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesUtils.addErrorMessage("Username or password not valid.");
        }
    }
    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
}