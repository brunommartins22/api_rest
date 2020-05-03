package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.padrao.rest.models.Usuario;
import br.com.bruno_martins.padrao.rest.services.SessionService;
import br.com.bruno_martins.padrao.rest.services.UsuarioService;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Bruno Martins
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final SessionService sessionService;

    public JWTLoginFilter(String url,
            AuthenticationManager authManager,
            UsuarioService usuarioService,
            SessionService sessionService) {

        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);

        this.sessionService = sessionService;
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String authorization = request.getHeader("Authorization");

        String[] credentials = getCredentials(authorization);
        //System.out.println(Utils.Encriptar(credentials[1]));

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials[0],
                        Utils.Encriptar(credentials[1]),
                        Collections.emptyList()
                )
        );
    }

    private String[] getCredentials(String authorizationHeader) {

        try {

            if (authorizationHeader != null) {
                String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                return credentials.split(":", 2);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                (Usuario) auth.getPrincipal()
        );

    }

}
