/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.padrao.rest.models.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

/**
 *
 * @author Bruno Martins
 */
public class JwtToken {

    public static String buildAccessToken(Usuario usuario, List<String> roles, List<String> systems, UUID sessionId) throws Exception {

        JwtClaims claims = new JwtClaims();

        claims.setIssuedAt(NumericDate.fromMilliseconds(new Date().getTime()));

        claims.setSubject(usuario.getNome());
        claims.setClaim("user_id", usuario.getId());
        claims.setClaim("session_id", sessionId);
        claims.setClaim("roles", roles);
        claims.setClaim("systems", systems);
        claims.setClaim("login", usuario.getLogin());

        Key key = PaeHmacSHA256key.getKey();

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);

        return jws.getCompactSerialization();
    }

    public static JwtClaims validate(String jwt) throws UnsupportedEncodingException, InvalidJwtException {
        Key key = PaeHmacSHA256key.getKey();

        JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireSubject().setRequireExpirationTime()
                .setVerificationKey(key).build();

        return jwtConsumer.processToClaims(jwt);
    }

//    public static String buildAccessToken(String sessionID, UserLoggedInDto user) throws Exception {
//
//        JwtClaims claims = new JwtClaims();
//
//        claims.setIssuedAt(NumericDate.fromMilliseconds(new Date().getTime()));
//
//        claims.setSubject(user.getUsers().get(0).getNome());
//
//        claims.setClaim("user_id", user.getUsers().get(0).getCodigo());
//        claims.setClaim("percentuais", percentuaisDeUsuario(user.getUsers().get(0)));
//        claims.setClaim("session_id", sessionID);
//        claims.setClaim("roles", user.getRoles());
//        claims.setClaim("systems", user.getSystems());
//        claims.setClaim("config", user.getConfiguracao());
//        claims.setClaim("arquivo", user.getArquivoLiberacao());
//
//        Key key = PaeHmacSHA256key.getKey();
//
//        JsonWebSignature jws = new JsonWebSignature();
//        jws.setPayload(claims.toJson());
//        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
//        jws.setKey(key);
//
//        return jws.getCompactSerialization();
//    }
    public static JwtClaims getClaims(String jwt, String frase) throws Exception {
        JwtConsumer consumer = new JwtConsumerBuilder().setRequireSubject().setVerificationKey(getKey(frase)).build();
        return consumer.processToClaims(jwt);

    }

    private static Key getKey(String frase) throws Exception {
        return new HmacKey(frase.getBytes("UTF-8"));
    }

//    private static Map<String, Object> percentuaisDeUsuario(Usuario usuario) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("percdescav", usuario.getPercdescav());
//        map.put("percdesccc", usuario.getPercdesccc());
//        map.put("percdescap", usuario.getPercdescap());
//        map.put("percdesccoav", usuario.getPercdesccoav());
//        map.put("percdescatav", usuario.getPercdescatav());
//        map.put("percdesccocc", usuario.getPercdesccocc());
//        map.put("percdescatcc", usuario.getPercdescatcc());
//        map.put("percdesccoap", usuario.getPercdesccoap());
//        map.put("percdescatap", usuario.getPercdescatap());
//        map.put("percdescjur", usuario.getPercdescjur());
//        map.put("percdescmul", usuario.getPercdescmul());
//        map.put("percdesctit", usuario.getPercdesctit());
//        map.put("visualizardebitos", usuario.getVisualizardebitos());
//        return map;
//    }
}
