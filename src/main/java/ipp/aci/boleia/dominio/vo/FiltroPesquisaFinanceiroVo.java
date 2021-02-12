package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa de transação consolidada
 */
public class FiltroPesquisaFinanceiroVo extends FiltroBasePeriodoPaginado {
    private EntidadeVo frota;
    private String razaoSocialFrota;
    private String cnpjFrota;
    private EntidadeVo unidade;
    private EntidadeVo empresaAgregada;
    private EntidadeVo pontoDeVenda;
    private String nomePontoDeVenda;
    private String cnpjPontoDeVenda;
    private EnumVo statusCiclo;
    private List<EnumVo> statusPagamento;
    private EmpresaUnidadeVo empresaUnidade;
    private Long idCobranca;
    private EnumVo statusIntegracao;
    private List<EnumVo> listaStatusIntegracao;
    private Long numeroDocumento;
    private List<EnumVo> statusNotaFiscal;
    

    public EntidadeVo getFrota() {
        return frota;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }

    public EntidadeVo getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EntidadeVo empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public String getRazaoSocialFrota() {
        return razaoSocialFrota;
    }

    public void setRazaoSocialFrota(String razaoSocialFrota) {
        this.razaoSocialFrota = razaoSocialFrota;
    }

    public String getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(String cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public String getNomePontoDeVenda() {
        return nomePontoDeVenda;
    }

    public void setNomePontoDeVenda(String nomePontoDeVenda) {
        this.nomePontoDeVenda = nomePontoDeVenda;
    }

    public String getCnpjPontoDeVenda() {
        return cnpjPontoDeVenda;
    }

    public void setCnpjPontoDeVenda(String cnpjPontoDeVenda) {
        this.cnpjPontoDeVenda = cnpjPontoDeVenda;
    }

    public EmpresaUnidadeVo getEmpresaUnidade() {
        return empresaUnidade;
    }

    public void setEmpresaUnidade(EmpresaUnidadeVo empresaUnidade) {
        this.empresaUnidade = empresaUnidade;
    }

    public EnumVo getStatusCiclo() {
        return statusCiclo;
    }

    public void setStatusCiclo(EnumVo statusCiclo) {
        this.statusCiclo = statusCiclo;
    }

    public List<EnumVo> getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(List<EnumVo> statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }

    public EnumVo getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(EnumVo statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public List<EnumVo> getListaStatusIntegracao() {
        return listaStatusIntegracao;
    }

    public void setListaStatusIntegracao(List<EnumVo> listaStatusIntegracao) {
        this.listaStatusIntegracao = listaStatusIntegracao;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public List<EnumVo> getStatusNotaFiscal() {
        return statusNotaFiscal;
    }

    public void setStatusNotaFiscal(List<EnumVo> statusNotaFiscal) {
        this.statusNotaFiscal = statusNotaFiscal;
    }
}


