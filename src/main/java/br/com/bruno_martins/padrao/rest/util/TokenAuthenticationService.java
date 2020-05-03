/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.padrao.rest.models.Usuario;
import br.com.bruno_martins.padrao.rest.models.UsuarioLogado;
import br.com.bruno_martins.padrao.rest.services.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.UUID;

/**
 *
 * @author Bruno Martins
 */
public class TokenAuthenticationService {

    // EXPIRATION_TIME = 10 dias
    static final long EXPIRATION_TIME = 860_000_000;
    static final String SECRET = "SsxsdVRVfvGTgtgHYYHyhUjujujMumumJukkL<OLolKikiJunhHNHNyjHGBBgbb";
    static final String TOKEN_PREFIX = "Basic";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(
            HttpServletResponse response,
            SessionService sessionService,
            Usuario usuario) {

        UUID randomUUID = UUID.randomUUID();

        String sessionId = randomUUID.toString();

        UsuarioLogado usuarioLogado = sessionService.add(sessionId, usuario);

        String JWT = Jwts.builder()
                .setSubject(usuario.getNome())
                .setExpiration(usuarioLogado.getDataValidade())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .claim("sessionId", sessionId)
                .claim("user_id", usuario.getId())
                .claim("user_name", Utils.alterTextExtesion(usuario.getNome()))
                .claim("login", Utils.alterTextExtesion(usuario.getLogin()))
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);

    }

    static void addAuthentication(HttpServletResponse response, SessionService sessionService, String sessionId) {

        UsuarioLogado usuarioLogado = sessionService.getUsuarioLogado(sessionId);

        String JWT = Jwts.builder()
                .setSubject(usuarioLogado.getNome())
                .setExpiration(usuarioLogado.getDataValidade())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .claim("sessionId", sessionId)
                .claim("user_id", usuarioLogado.getUsuarioId())
                .claim("user_name",Utils.alterTextExtesion(usuarioLogado.getNome()))
                .claim("login", usuarioLogado.getLogin())
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);

    }

    static Authentication getAuthentication(HttpServletRequest request, SessionService sessionService) {
        String sessionId = request.getHeader("sessionId");
        return new UsernamePasswordAuthenticationToken(sessionId, null, Collections.emptyList());
    }

}
