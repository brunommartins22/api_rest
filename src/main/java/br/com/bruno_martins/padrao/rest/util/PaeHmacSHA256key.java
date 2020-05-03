/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.jose4j.keys.HmacKey;

/**
 *
 * @author Bruno Martins
 */
public class PaeHmacSHA256key {

    private static final String SECRET = "SsxsdVRVfvGTgtgHYYHyhUjujujMumumJukkL<OLolKikiJunhHNHNyjHGBBgbb";

    public static Key getKey() throws UnsupportedEncodingException {
        return new HmacKey(SECRET.getBytes("UTF-8"));
    }
}
