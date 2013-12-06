/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.maven.glassfish.jsfsecure.util;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 *
 * @author jlombardo
 */
public class MyAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String memberTargetUrl = "/faces/users/index.xhtml";
        String adminTargetUrl = "/faces/admin/index.xhtml";
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            getRedirectStrategy().sendRedirect(request, response, adminTargetUrl);
        } else if (roles.contains("ROLE_USER")) {
            getRedirectStrategy().sendRedirect(request, response, memberTargetUrl);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
    }
}
