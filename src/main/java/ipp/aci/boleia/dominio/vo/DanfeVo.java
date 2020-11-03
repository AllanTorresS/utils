package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe com informações referentes à uma nota fiscal.
 */
public class DanfeVo {

    private String emitNome;
    private BigDecimal valorTotalNota;
    private String numeroDaNota;
    private String serieNota;
    private String dhEmiDataEmissao;
    private String emitNumeroEndereco;
    private String emitMunicipio;
    private String emitComplemento;
    private String emitLogradouro;
    private String emitBairro;
    private String emitUf;
    private String emitCep;
    private String emitTelefone;

    private String tipoOperacao;
    private String codigoBarra;
    private String chaveAcesso;
    private String naturezaOperacao;
    private String protocoloAutorizacao;
    private String emitInscEstadual;
    private String emitInscEstadualSubsTributario;
    private String emitCnpj;

    private String destNome;
    private String destCnpjCpf;
    private String destLogradouro;
    private String destNumero;
    private String destBairro;
    private String destCep;
    private String destDataSaida;
    private String destMunicipio;
    private String destUf;
    private String destTelefone;
    private String destInscricaoEstadual;
    private String destHoraSaida;

    private String baseCalcIcms;
    private BigDecimal valorIcms;
    private String baseCalcIcmsSubst;
    private BigDecimal valorIcmsSubst;
    private String valorAproxTributos;
    private BigDecimal valorTotalProdutos;
    private BigDecimal valorFrete;
    private BigDecimal valorSeguro;
    private String desconto;
    private String outraDespesasAcessorias;
    private BigDecimal valorIpi;

    private String transpNome;
    private String fretePorConta;
    private String veicTranspCodigoAntt;
    private String placaVeiculo;
    private String veicTranspUf;
    private String transpCnpjCpf;
    private String transpEndereco;
    private String transpMunicipio;
    private String transpUf;
    private String transpInscricaoEstadual;

    private String infCpl;
    private String obsCont;
    private String infAdFisco;
    private String dataHoraImpressao;
    private List<ItemDanfeVo> dadosDanfe = new ArrayList<>();

    public DanfeVo(){
        //Construtor default
    }

    public String getEmitNome() {
        return emitNome;
    }

    public void setEmitNome(String emitNome) {
        this.emitNome = emitNome;
    }

    public BigDecimal getValorTotalNota() {
        return valorTotalNota;
    }

    public void setValorTotalNota(BigDecimal valorTotalNota) {
        this.valorTotalNota = valorTotalNota;
    }

    public String getNumeroDaNota() {
        return numeroDaNota;
    }

    public void setNumeroDaNota(String numeroDaNota) {
        this.numeroDaNota = numeroDaNota;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getEmitNumeroEndereco() {
        return emitNumeroEndereco;
    }

    public void setEmitNumeroEndereco(String emitNumeroEndereco) {
        this.emitNumeroEndereco = emitNumeroEndereco;
    }

    public String getEmitMunicipio() {
        return emitMunicipio;
    }

    public void setEmitMunicipio(String emitMunicipio) {
        this.emitMunicipio = emitMunicipio;
    }

    public String getEmitComplemento() {
        return emitComplemento;
    }

    public void setEmitComplemento(String emitComplemento) {
        this.emitComplemento = emitComplemento;
    }

    public String getEmitLogradouro() {
        return emitLogradouro;
    }

    public void setEmitLogradouro(String emitLogradouro) {
        this.emitLogradouro = emitLogradouro;
    }

    public String getEmitBairro() {
        return emitBairro;
    }

    public void setEmitBairro(String emitBairro) {
        this.emitBairro = emitBairro;
    }

    public String getEmitUf() {
        return emitUf;
    }

    public void setEmitUf(String emitUf) {
        this.emitUf = emitUf;
    }

    public String getEmitCep() {
        return emitCep;
    }

    public void setEmitCep(String emitCep) {
        this.emitCep = emitCep;
    }

    public String getEmitTelefone() {
        return emitTelefone;
    }

    public void setEmitTelefone(String emitTelefone) {
        this.emitTelefone = emitTelefone;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(String naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public String getProtocoloAutorizacao() {
        return protocoloAutorizacao;
    }

    public void setProtocoloAutorizacao(String protocoloAutorizacao) {
        this.protocoloAutorizacao = protocoloAutorizacao;
    }

    public String getEmitInscEstadual() {
        return emitInscEstadual;
    }

    public void setEmitInscEstadual(String emitInscEstadual) {
        this.emitInscEstadual = emitInscEstadual;
    }

    public String getEmitInscEstadualSubsTributario() {
        return emitInscEstadualSubsTributario;
    }

    public void setEmitInscEstadualSubsTributario(String emitInscEstadualSubsTributario) {
        this.emitInscEstadualSubsTributario = emitInscEstadualSubsTributario;
    }

    public String getEmitCnpj() {
        return emitCnpj;
    }

    public void setEmitCnpj(String emitCnpj) {
        this.emitCnpj = emitCnpj;
    }

    public String getDestNome() {
        return destNome;
    }

    public void setDestNome(String destNome) {
        this.destNome = destNome;
    }

    public String getDestCnpjCpf() {
        return destCnpjCpf;
    }

    public void setDestCnpjCpf(String destCnpjCpf) {
        this.destCnpjCpf = destCnpjCpf;
    }

    public String getDhEmiDataEmissao() {
        return dhEmiDataEmissao;
    }

    public void setDhEmiDataEmissao(String dhEmiDataEmissao) {
        this.dhEmiDataEmissao = dhEmiDataEmissao;
    }

    public String getDestLogradouro() { return destLogradouro; }

    public void setDestLogradouro(String destLogradouro) {
        this.destLogradouro = destLogradouro;
    }

    public String getDestNumero() { return destNumero; }

    public void setDestNumero(String destNumero) {
        this.destNumero = destNumero;
    }

    public String getDestBairro() {
        return destBairro;
    }

    public void setDestBairro(String destBairro) {
        this.destBairro = destBairro;
    }

    public String getDestCep() {
        return destCep;
    }

    public void setDestCep(String destCep) {
        this.destCep = destCep;
    }

    public String getDestDataSaida() {
        return destDataSaida;
    }

    public void setDestDataSaida(String destDataSaida) {
        this.destDataSaida = destDataSaida;
    }

    public String getDestMunicipio() {
        return destMunicipio;
    }

    public void setDestMunicipio(String destMunicipio) {
        this.destMunicipio = destMunicipio;
    }

    public String getDestUf() {
        return destUf;
    }

    public void setDestUf(String destUf) {
        this.destUf = destUf;
    }

    public String getDestTelefone() {
        return destTelefone;
    }

    public void setDestTelefone(String destTelefone) {
        this.destTelefone = destTelefone;
    }

    public String getDestInscricaoEstadual() {
        return destInscricaoEstadual;
    }

    public void setDestInscricaoEstadual(String destInscricaoEstadual) {
        this.destInscricaoEstadual = destInscricaoEstadual;
    }

    public String getDestHoraSaida() {
        return destHoraSaida;
    }

    public void setDestHoraSaida(String destHoraSaida) {
        this.destHoraSaida = destHoraSaida;
    }

    public String getBaseCalcIcms() {
        return baseCalcIcms;
    }

    public void setBaseCalcIcms(String baseCalcIcms) {
        this.baseCalcIcms = baseCalcIcms;
    }

    public BigDecimal getValorIcms() {
        return valorIcms;
    }

    public void setValorIcms(BigDecimal valorIcms) {
        this.valorIcms = valorIcms;
    }

    public String getBaseCalcIcmsSubst() {
        return baseCalcIcmsSubst;
    }

    public void setBaseCalcIcmsSubst(String baseCalcIcmsSubst) {
        this.baseCalcIcmsSubst = baseCalcIcmsSubst;
    }

    public BigDecimal getValorIcmsSubst() {
        return valorIcmsSubst;
    }

    public void setValorIcmsSubst(BigDecimal valorIcmsSubst) {
        this.valorIcmsSubst = valorIcmsSubst;
    }

    public String getValorAproxTributos() {
        return valorAproxTributos;
    }

    public void setValorAproxTributos(String valorAproxTributos) {
        this.valorAproxTributos = valorAproxTributos;
    }

    public BigDecimal getValorTotalProdutos() {
        return valorTotalProdutos;
    }

    public void setValorTotalProdutos(BigDecimal valorTotalProdutos) {
        this.valorTotalProdutos = valorTotalProdutos;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getOutraDespesasAcessorias() {
        return outraDespesasAcessorias;
    }

    public void setOutraDespesasAcessorias(String outraDespesasAcessorias) {
        this.outraDespesasAcessorias = outraDespesasAcessorias;
    }

    public BigDecimal getValorIpi() {
        return valorIpi;
    }

    public void setValorIpi(BigDecimal valorIpi) {
        this.valorIpi = valorIpi;
    }

    public String getTranspNome() {
        return transpNome;
    }

    public void setTranspNome(String transpNome) {
        this.transpNome = transpNome;
    }

    public String getFretePorConta() {
        return fretePorConta;
    }

    public void setFretePorConta(String fretePorConta) {
        this.fretePorConta = fretePorConta;
    }

    public String getVeicTranspCodigoAntt() {
        return veicTranspCodigoAntt;
    }

    public void setVeicTranspCodigoAntt(String veicTranspCodigoAntt) {
        this.veicTranspCodigoAntt = veicTranspCodigoAntt;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getVeicTranspUf() {
        return veicTranspUf;
    }

    public void setVeicTranspUf(String veicTranspUf) {
        this.veicTranspUf = veicTranspUf;
    }

    public String getTranspCnpjCpf() {
        return transpCnpjCpf;
    }

    public void setTranspCnpjCpf(String transpCnpjCpf) {
        this.transpCnpjCpf = transpCnpjCpf;
    }

    public String getTranspEndereco() {
        return transpEndereco;
    }

    public void setTranspEndereco(String transpEndereco) {
        this.transpEndereco = transpEndereco;
    }

    public String getTranspMunicipio() {
        return transpMunicipio;
    }

    public void setTranspMunicipio(String transpMunicipio) {
        this.transpMunicipio = transpMunicipio;
    }

    public String getTranspUf() {
        return transpUf;
    }

    public void setTranspUf(String transpUf) {
        this.transpUf = transpUf;
    }

    public String getTranspInscricaoEstadual() {
        return transpInscricaoEstadual;
    }

    public void setTranspInscricaoEstadual(String transpInscricaoEstadual) {
        this.transpInscricaoEstadual = transpInscricaoEstadual;
    }

    public String getInfCpl() {
        return infCpl;
    }

    public void setInfCpl(String infCpl) {
        this.infCpl = infCpl;
    }

    public String getDataHoraImpressao() {
        return dataHoraImpressao;
    }

    public void setDataHoraImpressao(String dataHoraImpressao) { this.dataHoraImpressao = dataHoraImpressao; }

    public List<ItemDanfeVo> getDadosDanfe() {
        return dadosDanfe;
    }

    public void setDadosDanfe(List<ItemDanfeVo> dadosDanfe) {
        this.dadosDanfe = dadosDanfe;
    }

    public String getObsCont() {
        return obsCont;
    }

    public void setObsCont(String obsCont) {
        this.obsCont = obsCont;
    }

    public String getInfAdFisco() {
        return infAdFisco;
    }

    public void setInfAdFisco(String infAdFisco) {
        this.infAdFisco = infAdFisco;
    }
}
