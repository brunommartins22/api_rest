/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.padrao.rest.services.SessionService;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author Bruno Martins
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    private final SessionService sessionService;

    public JWTAuthenticationFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String sessionId = httpServletRequest.getHeader("sessionId");

        Authentication authentication = null;

        if (sessionId != null) {

            if (sessionService.refreshSessionTimeout(sessionId)) {
                authentication = TokenAuthenticationService
                        .getAuthentication(httpServletRequest, sessionService);
            }

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
