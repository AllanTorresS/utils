package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Classe utilizada para geração do detalhe do relatório de conciliação contábil de cobranças e reembolsos
 */
public class ConciliacaoCobrancasRevendedorVo {


    private String revendedor;
    private String descricaoRevendedor;
    private BigDecimal valor;
    private BigDecimal valorMdr;

    /**
     * Gera a consolidacao de cobrancas para um revendedor
     * @param revendedor O revendedor dado
     * @param descricaoRevendedor A descricao do revendedor
     * @param valor O valor da cobranca
     * @param valorMdr O valor do MDR
     */
    public ConciliacaoCobrancasRevendedorVo(String revendedor, String descricaoRevendedor, BigDecimal valor, BigDecimal valorMdr){
        this.revendedor = revendedor;
        this.descricaoRevendedor = descricaoRevendedor;
        this.valor = valor;
        this.valorMdr = valorMdr;
    }

    public ConciliacaoCobrancasRevendedorVo(){
        // serializacao json
    }

    public String getRevendedor() {
        return revendedor;
    }

    public void setRevendedor(String revendedor) {
        this.revendedor = revendedor;
    }

    public String getDescricaoRevendedor() {
        return descricaoRevendedor;
    }

    public void setDescricaoRevendedor(String descricaoRevendedor) {
        this.descricaoRevendedor = descricaoRevendedor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorMdr() {
        return valorMdr;
    }

    public void setValorMdr(BigDecimal valorMdr) {
        this.valorMdr = valorMdr;
    }
}
