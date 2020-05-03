/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author Bruno Martins
 */
public class RestClient {

    Client c = Client.create();

    public ClientResponse get(String url) throws Exception {

        WebResource r = c.resource(url);
        ClientResponse response = r.get(ClientResponse.class);

        int resp = response.getStatus();

        if (resp == 204) {//não teve nenhum objeto localizado
            return null;
        }

        if (resp != 200) {//200 = teve resposta com dados
            throw new Exception("Falha ao Carregar o Serviço (Status " + resp + ")");
        }

        return response;

    }
}
