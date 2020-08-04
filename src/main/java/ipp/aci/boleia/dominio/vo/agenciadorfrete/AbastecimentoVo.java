package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Abastecimento;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informações referentes do abastecimento de um motorista autonomo
 */
public class AbastecimentoVo {
    private Long id;
    private Date dataTransacao;
    private BigDecimal litragem;
    private CombustivelVo combustivel;
    private BigDecimal precoCombustivel;

    public AbastecimentoVo() {
        //Construtor default
    }

    public AbastecimentoVo(Abastecimento abastecimento) {
        if(abastecimento != null) {
            this.id = abastecimento.getId();
            this.dataTransacao = abastecimento.getDataCriacao();
            this.litragem = abastecimento.getLitragem();
            this.combustivel = new CombustivelVo(abastecimento.getCombustivel(), abastecimento.getPrecoCombustivel());
        }
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public CombustivelVo getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(CombustivelVo combustivel) {
        this.combustivel = combustivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecoCombustivel() {
        return precoCombustivel;
    }

    public void setPrecoCombustivel(BigDecimal precoCombustivel) {
        this.precoCombustivel = precoCombustivel;
    }
}
