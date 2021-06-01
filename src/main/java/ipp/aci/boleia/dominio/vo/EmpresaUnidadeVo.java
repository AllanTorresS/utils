package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.EmpresaUnidade;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada;
import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
 * Representa uma entidade EmpresaUnidade
 */
public class EmpresaUnidadeVo {
    private Long id;
    private EnumVo tipo;
    private Long idFrota;
    private Long idUnidade;
    private Long idEmpresaAgregada;
    private String label;

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

    /**
     * Construtor da classe.
     *
     * @param empresaUnidade Entidade {@link ipp.aci.boleia.dominio.EmpresaUnidade}.
     */
    public EmpresaUnidadeVo(EmpresaUnidade empresaUnidade) {
        Unidade unidade = empresaUnidade.getUnidade();
        EmpresaAgregada empresaAgregada = empresaUnidade.getEmpresaAgregada();
        this.id = empresaUnidade.getId();
        this.tipo = new EnumVo(empresaUnidade.getTipoEntidadeEnum());
        this.idFrota = empresaUnidade.getFrota().getId();
        this.idUnidade = unidade != null ? unidade.getId() : null;
        this.idEmpresaAgregada = empresaAgregada != null ? empresaAgregada.getId() : null;
        this.label = unidade != null ? unidade.getNome() + " - " + UtilitarioFormatacao.formatarCnpjApresentacao(unidade.getCnpj()) : empresaAgregada.getRazaoSocial() + " - " + UtilitarioFormatacao.formatarCnpjApresentacao(empresaAgregada.getCnpj());
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
