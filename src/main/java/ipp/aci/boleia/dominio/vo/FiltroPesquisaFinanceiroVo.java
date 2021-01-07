package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa de transação consolidada
 */
public class FiltroPesquisaFinanceiroVo extends FiltroBasePeriodoPaginado {

    private Date de;
    private Date ate;
    private EntidadeVo frota;
    private String razaoSocialFrota;
    private String cnpjFrota;
    private EntidadeVo pontoDeVenda;
    private String nomePontoDeVenda;
    private String cnpjPontoDeVenda;
    private EnumVo statusCiclo;
    private List<EnumVo> statusPagamento;
    private EmpresaUnidadeVo empresaUnidade;

    public Date getDe(){
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }

    public EntidadeVo getFrota() {
        return frota;
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
}


