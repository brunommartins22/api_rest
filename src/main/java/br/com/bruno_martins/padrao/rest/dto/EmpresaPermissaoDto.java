package br.com.bruno_martins.padrao.rest.dto;

import java.util.List;


/**
 *
 * @author Bruno Martins
 */
public class EmpresaPermissaoDto {

    private Long idEmpresa;

    private final List<String> permissoes;

    private final List<Long> sistemas;

    public EmpresaPermissaoDto(List<String> permissoes, List<Long> idSistemas) {

        this.permissoes = permissoes;
        this.sistemas = idSistemas;

    }

    //**************************** get && sets *********************************
    public List<Long> getSistemas() {
        return sistemas;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
