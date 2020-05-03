/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.dto;

/**
 *
 * @author Bruno Martins
 */
public class PermissaoDto {

    private String id;
    private String descricao;
    private boolean possui;
    private Long idEmpresa;
    private String dominioTipoPermissao;

    //**************************** get && sets *********************************
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPossui() {
        return possui;
    }

    public void setPossui(boolean possui) {
        this.possui = possui;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getDominioTipoPermissao() {
        return dominioTipoPermissao;
    }

    public void setDominioTipoPermissao(String dominioTipoPermissao) {
        this.dominioTipoPermissao = dominioTipoPermissao;
    }

}
