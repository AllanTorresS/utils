package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.TipoItemAutorizacaoPagamento;

import java.math.BigDecimal;

/**
 * Representa o preco de um item de venda
 */
public class PrecoItemVo {

    private String tipo;
    private Long codigo;
    private BigDecimal valorUnitario;

    /**
     * Construtor padrão
     */
    public PrecoItemVo() {
       // Construtor padrão
    }

    /**
     * Construtor completo
     * @param codigo do item
     * @param tipo do item
     * @param valorUnitario do item
     */
    public PrecoItemVo(String codigo, Integer tipo, BigDecimal valorUnitario) {
        this.codigo = Long.valueOf(codigo);
        this.tipo = TipoItemAutorizacaoPagamento.obterPorValor(tipo).name();
        this.valorUnitario = valorUnitario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
