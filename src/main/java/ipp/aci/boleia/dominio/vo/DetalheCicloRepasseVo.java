package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.enums.StatusCicloRepasse;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.util.MesAno;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Vo para informações de detalhe do ciclo de repasse na exportação
 */
public class DetalheCicloRepasseVo {
    private String ano;
    private String mes;
    private String periodo;
    private Integer codigoABADI;
    private String cnpjPosto;
    private String nomePosto;
    private String nomeProduto;
    private BigDecimal percentualRepasse;
    private BigDecimal valorTotalAbastecimentosAgrupados;
    private BigDecimal totalRepasse;
    private Date vencimento;
    private String statusIntegracao;
    private Integer numeroDocumento;
    private String statusPagamento;
    private Date dataPagamento;
    private static final BigDecimal MAXIMO_PERCENTUAL = new BigDecimal(100);
    private static final Integer VALOR_MAXIMO_NA_FORMA_NAO_PERCENTUAL = 1;

    /**
     * Construtor default
     */
    public DetalheCicloRepasseVo() {

    }

    /**
     * Gera as linhas da tabela do relatorio agrupadas por Produto
     * @param dataBase a data Mês / Ano
     * @param nomeProduto o nome do produto o qual os precos e detalhes são agrupados
     * @param  precosAgrupados o preço de todos os produtos iguais para um mesmo posto e ciclo
     * @param  repasse o Ciclo de Repasse
     */
    public DetalheCicloRepasseVo(Date dataBase , String nomeProduto, BigDecimal precosAgrupados, CicloRepasse repasse){
        if(dataBase != null){
            this.periodo = UtilitarioFormatacaoData.formatarDataMesAno(dataBase);
            this.mes = MesAno.obterNomePorMes(Integer.parseInt(UtilitarioFormatacaoData.formatarDataMes(dataBase))-1);
            this.ano = UtilitarioFormatacaoData.formatarDataAno(dataBase);
        }
        this.codigoABADI = repasse.getPontoDeVenda().getNumeroAbadi();
        this.cnpjPosto = repasse.getPontoDeVenda().getComponenteAreaAbastecimento().getCodigoPessoa().toString();
        this.nomePosto = repasse.getPontoDeVenda().getComponenteAreaAbastecimento().getNomePessoa();
        this.nomeProduto = nomeProduto;
        this.percentualRepasse = repasse.getValorPercentualRepasse().doubleValue() >= VALOR_MAXIMO_NA_FORMA_NAO_PERCENTUAL ? repasse.getValorPercentualRepasse().divide(MAXIMO_PERCENTUAL) : repasse.getValorPercentualRepasse();
        this.valorTotalAbastecimentosAgrupados = precosAgrupados.setScale(2,BigDecimal.ROUND_HALF_EVEN);
        this.totalRepasse = precosAgrupados.multiply(this.percentualRepasse).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.vencimento = repasse.getDataVencimento();
        this.numeroDocumento = repasse.getNumeroDocumento();

        if(repasse.getStatus() != null){
            this.statusPagamento = StatusCicloRepasse.obterPorValor(repasse.getStatus()).getLabel();
        }

        if(repasse.getStatusIntegracaoJDE() != null){
            this.statusIntegracao = StatusIntegracaoJde.obterPorValor(repasse.getStatusIntegracaoJDE()).getLabel();
        }
        this.dataPagamento = repasse.getDataPagamento();
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getCodigoABADI() {
        return codigoABADI;
    }

    public void setCodigoABADI(Integer codigoABADI) {
        this.codigoABADI = codigoABADI;
    }

    public String getCnpjPosto() {
        return cnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        this.cnpjPosto = cnpjPosto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getPercentualRepasse() {
        return percentualRepasse;
    }

    public void setPercentualRepasse(BigDecimal percentualRepasse) {
        this.percentualRepasse = percentualRepasse;
    }

    public BigDecimal getTotalRepasse() {
        return totalRepasse;
    }

    public void setTotalRepasse(BigDecimal totalRepasse) {
        this.totalRepasse = totalRepasse;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(String statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorTotalAbastecimentosAgrupados() {
        return valorTotalAbastecimentosAgrupados;
    }

    public void setValorTotalAbastecimentosAgrupados(BigDecimal valorTotalAbastecimentosAgrupados) {
        this.valorTotalAbastecimentosAgrupados = valorTotalAbastecimentosAgrupados;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNomePosto() {
        return nomePosto;
    }

    public void setNomePosto(String nomePosto) {
        this.nomePosto = nomePosto;
    }
}
