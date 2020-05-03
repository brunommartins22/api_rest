/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 *
 * @author Bruno Martins
 */
public class WebService {

    public WebTarget getClientService(String baseUrl) {

        Client result = new ResteasyClientBuilder().build();

        return result.target(baseUrl);

    }

}
