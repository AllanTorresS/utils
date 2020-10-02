package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.TipoCombustivel;

import java.math.BigDecimal;

/**
 * Classe com informações referentes combustivel de um extrato de motorista autonomo
 */
public class CombustivelVo {
    private Long id;
    private String nome;
    private BigDecimal precoUnitario;


    public CombustivelVo() {
        //Construtor default
    }

    public CombustivelVo(TipoCombustivel combustivel, BigDecimal precoUnitario) {
        this.id = combustivel.getId();
        this.nome = combustivel.getDescricao();
        this.precoUnitario = precoUnitario;
    }

    public CombustivelVo(PrecoBase precoBase) {
        TipoCombustivel combustivel = precoBase.getPrecoMicromercado().getTipoCombustivel();
        this.nome = combustivel.getDescricao();
        this.id = combustivel.getId();
        this.precoUnitario = precoBase.getPreco();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

}
