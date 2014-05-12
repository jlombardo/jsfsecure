package edu.wctc.maven.glassfish.jsfsecure.jsfbean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This JSF/CDI Managed Bean provides a way for users to log out of the
 * application.
 */
@Named(value = "logoutBean")
@RequestScoped
public class LogoutBean {
    // Built in logging is used now, but will be replaced in future with
    // 3rd party solution. Logging does what System.out.println() does and
    // more!
    private static Logger log = Logger.getLogger(LogoutBean.class.getName());

    public String logout() {
        // Notice the redirect syntax. The forward slash means start at
        // the root of the web application.
        String destination = "/index?faces-redirect=true";

        // FacesContext provides access to other container managed objects,
        // such as the HttpServletRequest object, which is needed to perform
        // the logout operation.
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = 
                (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            // added May 12, 2014
            HttpSession session = request.getSession();
            session.invalidate();
            
            // this does not invalidate the session but does null out the user Principle
            request.logout();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            destination = "/loginerror?faces-redirect=true";
        }

        return destination; // go to destination
    }
}
