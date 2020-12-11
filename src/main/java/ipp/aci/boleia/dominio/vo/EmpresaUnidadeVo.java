package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada;

/**
 * Representa uma entidade EmpresaUnidade
 */
public class EmpresaUnidadeVo {
    private Long id;
    private EnumVo tipo;
    private Long idFrota;
    private Long idUnidade;
    private Long idEmpresaAgregada;

    /**
     * Construtor default
     */
    public EmpresaUnidadeVo() {
    }

    /**
     * Construtor da classe.
     *
     * @param id Identificador da {@link ipp.aci.boleia.dominio.EmpresaUnidade}.
     * @param tipo Tipo da entidade.
     * @param idFrota Identificador da frota relacionada.
     * @param idUnidade Identificador da unidade.
     * @param idEmpresaAgregada Identificador da empresa agregada.
     */
    public EmpresaUnidadeVo(Long id, Integer tipo, Long idFrota, Long idUnidade, Long idEmpresaAgregada) {
        this.id = id;
        this.tipo = new EnumVo(TipoEntidadeUnidadeEmpresaAgregada.obterPorValor(tipo));
        this.idFrota = idFrota;
        this.idUnidade = idUnidade;
        this.idEmpresaAgregada = idEmpresaAgregada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumVo getTipo() {
        return tipo;
    }

    public void setTipo(EnumVo tipo) {
        this.tipo = tipo;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Long getIdEmpresaAgregada() {
        return idEmpresaAgregada;
    }

    public void setIdEmpresaAgregada(Long idEmpresaAgregada) {
        this.idEmpresaAgregada = idEmpresaAgregada;
    }
}
