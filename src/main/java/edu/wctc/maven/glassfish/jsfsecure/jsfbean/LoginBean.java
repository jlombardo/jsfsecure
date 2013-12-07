package edu.wctc.maven.glassfish.jsfsecure.jsfbean;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import org.springframework.security.authentication.AuthenticationManager;

/**
 *
 * @author jlombardo
 */
@Named
@RequestScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * Note that @SuppressWarnings is only used by a source code analyzer
     * that I use caled "FindBugs". You don't need this unless you do to.
     */
    @SuppressWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
    private transient final Logger LOG = LoggerFactory.getLogger(LoginBean.class);

    private String userName = "";
    private String password = "";
    
    public LoginBean() {
    }

    /**
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public String doLogin() throws IOException, ServletException {
        LOG.debug("**** Executing doLogin method of LoginBean...");
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();
        
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check?j_username=" + userName
                                + "&j_password=" + password);
        
        // Forwards to original destination or to error page
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();

        // It's OK to return null here because Faces is just going to exit.
        return null;
    }
    
    /**
     * @return
     */
    @SuppressWarnings
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param username
     */
    @SuppressWarnings
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }    

    
}
