package ipp.aci.boleia.dominio.vo;


import java.util.List;

/**
 * Classe com informações referentes à um aviso de débido de um boleto
 */
public class AvisoDebitoConectcarVo {

    private String emitente;
    private String razaoSocialFrota;
    private String localEmitente;
    private String numeroLocalEmitente;
    private String cnpjEmitente;
    private String cnpjFrota;
    private String aviso;
    private String enderecoFrota;
    private String municipioFrota;
    private String cepFrota;
    private String ufFrota;
    private String dataLancamento;
    private String documentoReferencia;
    private String valorAviso;
    private String valorDevido;
    private String valorPorExtenso;
    private String ciclo;
    private String dataEmissao;
    private String codigoCliente;
    private List<DetalheTransacaoConectcarVo> detalhes;
    private String valorDescontoAbastecimentos;
    private String valorAbastecimentosTotalComDesconto;
    private String valorMensalidade;
    private String valorTransacoes;

    public String getEmitente() {
        return emitente;
    }

    public void setEmitente(String emitente) {
        this.emitente = emitente;
    }

    public String getRazaoSocialFrota() {
        return razaoSocialFrota;
    }

    public void setRazaoSocialFrota(String razaoSocialFrota) {
        this.razaoSocialFrota = razaoSocialFrota;
    }

    public String getLocalEmitente() {
        return localEmitente;
    }

    public void setLocalEmitente(String localEmitente) {
        this.localEmitente = localEmitente;
    }

    public String getCnpjEmitente() {
        return cnpjEmitente;
    }

    public void setCnpjEmitente(String cnpjEmitente) {
        this.cnpjEmitente = cnpjEmitente;
    }

    public String getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(String cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public String getEnderecoFrota() {
        return enderecoFrota;
    }

    public void setEnderecoFrota(String enderecoFrota) {
        this.enderecoFrota = enderecoFrota;
    }

    public String getMunicipioFrota() {
        return municipioFrota;
    }

    public void setMunicipioFrota(String municipioFrota) {
        this.municipioFrota = municipioFrota;
    }

    public String getCepFrota() {
        return cepFrota;
    }

    public void setCepFrota(String cepFrota) {
        this.cepFrota = cepFrota;
    }

    public String getUfFrota() {
        return ufFrota;
    }

    public void setUfFrota(String ufFrota) {
        this.ufFrota = ufFrota;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDocumentoReferencia() {
        return documentoReferencia;
    }

    public void setDocumentoReferencia(String documentoReferencia) {
        this.documentoReferencia = documentoReferencia;
    }

    public String getValorAviso() {
        return valorAviso;
    }

    public void setValorAviso(String valorAviso) {
        this.valorAviso = valorAviso;
    }

    public String getValorPorExtenso() {
        return valorPorExtenso;
    }

    public void setValorPorExtenso(String valorPorExtenso) {
        this.valorPorExtenso = valorPorExtenso;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public List<DetalheTransacaoConectcarVo> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(List<DetalheTransacaoConectcarVo> detalhes) {
        this.detalhes = detalhes;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getNumeroLocalEmitente() {
        return numeroLocalEmitente;
    }

    public void setNumeroLocalEmitente(String numeroLocalEmitente) {
        this.numeroLocalEmitente = numeroLocalEmitente;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getValorDescontoAbastecimentos() {
        return valorDescontoAbastecimentos;
    }

    public void setValorDescontoAbastecimentos(String valorDescontoAbastecimentos) {
        this.valorDescontoAbastecimentos = valorDescontoAbastecimentos;
    }

    public String getValorAbastecimentosTotalComDesconto() {
        return valorAbastecimentosTotalComDesconto;
    }

    public void setValorAbastecimentosTotalComDesconto(String valorAbastecimentosTotalComDesconto) {
        this.valorAbastecimentosTotalComDesconto = valorAbastecimentosTotalComDesconto;
    }

    public String getValorDevido() {
        return valorDevido;
    }

    public void setValorDevido(String valorDevido) {
        this.valorDevido = valorDevido;
    }

    public String getValorMensalidade() { return valorMensalidade; }

    public void setValorMensalidade(String valorMensalidade) { this.valorMensalidade = valorMensalidade; }

    public String getValorTransacoes() { return valorTransacoes; }

    public void setValorTransacoes(String valorTransacoes) { this.valorTransacoes = valorTransacoes; }
}