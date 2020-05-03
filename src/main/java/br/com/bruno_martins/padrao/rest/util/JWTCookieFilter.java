package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.padrao.rest.services.SessionService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Bruno Martins
 */
public class JWTCookieFilter extends AbstractAuthenticationProcessingFilter {

    private final SessionService sessionService;

    public JWTCookieFilter(String url,
            SessionService sessionService) {

        super(new AntPathRequestMatcher(url));
        this.sessionService = sessionService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String sessionId = request.getHeader("sessionId");

        Authentication authentication = null;

        if (sessionId != null) {

            if (sessionService.refreshSessionTimeout(sessionId)) {
                authentication = TokenAuthenticationService
                        .getAuthentication(request, sessionService);
            }

        }

        return authentication;

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) throws IOException, ServletException {

        TokenAuthenticationService.addAuthentication(
                response,
                sessionService,
                (String) auth.getPrincipal()
        );

    }

}
