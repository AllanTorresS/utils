package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.jde.ConstantesJde;

import java.math.BigDecimal;

import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataCurta;
import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarPeriodoDiasMes;

/**
 * Classe utilizada para geração do detalhe do relatório de conciliação contábil de cobranças e reembolsos
 */
public class ConciliacaoCobrancasFrotaRevendedorVo {

    private static final String SUFIXO_CONTA_BOLEIA_JDE = ".21101.0026";
    private static final String CONTA_CONTABIL = ConstantesJde.FILIAL + SUFIXO_CONTA_BOLEIA_JDE;

    private String dataContabil;
    private String periodoCiclo;
    private String conta;
    private String tipoDocumento;
    private String numeroDocumento;
    private String frotista;
    private String descricaoFrotista;
    private String postoRevendedor;
    private String descricaoPostoRevendedor;
    private BigDecimal valor;
    private String mdr;
    private BigDecimal valorMdr;

    /**
     * Gera a conciliacao de cobrancas para a transacao consolidada entre frota e revendedor
     * @param transacaoConsolidada A transacao consolidada da frota e do revendedor
     */
    public ConciliacaoCobrancasFrotaRevendedorVo(TransacaoConsolidada transacaoConsolidada){
        this.dataContabil = formatarDataCurta(transacaoConsolidada.getDataFimPeriodo());
        this.periodoCiclo = formatarPeriodoDiasMes(transacaoConsolidada.getDataInicioPeriodo(),transacaoConsolidada.getDataFimPeriodo(), true);
        this.conta = CONTA_CONTABIL;
        this.tipoDocumento = transacaoConsolidada.getCobranca().getTipoDocumento();
        this.numeroDocumento = transacaoConsolidada.getCobranca().getNumeroDocumento().toString();
        this.frotista = transacaoConsolidada.getFrota().getNumeroJdeInterno().toString();
        this.descricaoFrotista = transacaoConsolidada.getFrota().getNomeRazaoFrota();
        this.postoRevendedor = transacaoConsolidada.getPontoVenda().getNumeroJdeInterno().toString();
        this.descricaoPostoRevendedor = transacaoConsolidada.getPontoVenda().getNome();
        this.valor = transacaoConsolidada.getValorTotal().negate();
        this.mdr = UtilitarioFormatacao.formatarDecimalPercentual(transacaoConsolidada.getMdr().divide(new BigDecimal("100")));
        this.valorMdr = transacaoConsolidada.getValorTotal().subtract(transacaoConsolidada.getValorReembolso()).negate();
    }

    public ConciliacaoCobrancasFrotaRevendedorVo(){
        // serializacao json
    }

    public String getDataContabil() {
        return dataContabil;
    }

    public void setDataContabil(String dataContabil) {
        this.dataContabil = dataContabil;
    }

    public String getPeriodoCiclo() {
        return periodoCiclo;
    }

    public void setPeriodoCiclo(String periodoCiclo) {
        this.periodoCiclo = periodoCiclo;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFrotista() {
        return frotista;
    }

    public void setFrotista(String frotista) {
        this.frotista = frotista;
    }

    public String getDescricaoFrotista() {
        return descricaoFrotista;
    }

    public void setDescricaoFrotista(String descricaoFrotista) {
        this.descricaoFrotista = descricaoFrotista;
    }

    public String getPostoRevendedor() {
        return postoRevendedor;
    }

    public void setPostoRevendedor(String postoRevendedor) {
        this.postoRevendedor = postoRevendedor;
    }

    public String getDescricaoPostoRevendedor() {
        return descricaoPostoRevendedor;
    }

    public void setDescricaoPostoRevendedor(String descricaoPostoRevendedor) {
        this.descricaoPostoRevendedor = descricaoPostoRevendedor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMdr() {
        return mdr;
    }

    public void setMdr(String mdr) {
        this.mdr = mdr;
    }

    public BigDecimal getValorMdr() {
        return valorMdr;
    }

    public void setValorMdr(BigDecimal valorMdr) {
        this.valorMdr = valorMdr;
    }
}
