package ipp.aci.boleia.dominio;

import java.io.Serializable;

/**
 * Classe para manter as chaves primárias da entidade {@link EmpresaAgregadaUnidade}.
 */
public class EmpresaAgregadaUnidadePk implements Serializable {
    private static final long serialVersionUID = 1890396646571977146L;

    private Long idEmpresaAgregada;
    private Long idUnidade;

    /**
     * Construtor default da classe.
     */
    public EmpresaAgregadaUnidadePk() {
    }

    /**
     * Construtor da classe.
     *
     * @param idEmpresaAgregada Identificador da empresa agregada.
     * @param idUnidade         Identificador da Unidade.
     */
    public EmpresaAgregadaUnidadePk(Long idEmpresaAgregada, Long idUnidade) {
        this.idEmpresaAgregada = idEmpresaAgregada;
        this.idUnidade = idUnidade;
    }

    public Long getIdEmpresaAgregada() {
        return idEmpresaAgregada;
    }

    public void setIdEmpresaAgregada(Long idEmpresaAgregada) {
        this.idEmpresaAgregada = idEmpresaAgregada;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }
}
