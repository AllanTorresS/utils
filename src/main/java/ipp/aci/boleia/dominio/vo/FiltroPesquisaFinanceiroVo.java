package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.Date;

/**
 * Filtro para pesquisa de transação consolidada
 */
public class FiltroPesquisaFinanceiroVo extends FiltroBasePeriodoPaginado {

    private Date de;
    private Date ate;
    private EntidadeVo frota;
    private String cnpjFrota;
    private EntidadeVo pontoDeVenda;
    private String cnpjPontoDeVenda;
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
}


