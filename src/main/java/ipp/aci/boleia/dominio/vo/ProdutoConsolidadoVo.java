package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Classe auxiliar no processo de consolidacao das notas fiscais,
 * responsavel pelo agrupamento das entidades ItemTransacao
 */
public class ProdutoConsolidadoVo {

    private Long codigo;
    private BigDecimal valorUnitario;

    /**
     * Construtor default
     */
    public ProdutoConsolidadoVo() {
        //construtor default
    }

    /**
     * O construtor do produto consolidado
     * @param codigo O codigo do produto
     * @param valorUnitario O valor unitario do produto
     */
    public ProdutoConsolidadoVo(Long codigo, BigDecimal valorUnitario) {
        this.codigo = codigo;
        this.valorUnitario = valorUnitario;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ProdutoConsolidadoVo p = (ProdutoConsolidadoVo) obj;
        return this.codigo.equals(p.getCodigo()) && this.valorUnitario.compareTo(p.getValorUnitario()) == 0;
    }

    @Override
    public int hashCode() {
        return (this.codigo == null ? 0 : this.codigo.hashCode()) ^ (this.valorUnitario == null ? 0 : this.valorUnitario.hashCode());
    }
}