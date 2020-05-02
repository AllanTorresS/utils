package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa de transação consolidada
 */
public class FiltroPesquisaTransacaoConsolidadaVo extends BaseFiltroPaginado {

    private Date de;
    private Date ate;
    private String notaFiscal;
    private List<EnumVo> statusEmissaoNF;
    private EntidadeVo frota;
    private EntidadeVo pontoDeVenda;
    private EntidadeVo empresaAgregada;
    private EntidadeVo unidade;

    public Date getDe() {
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

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public List<EnumVo> getStatusEmissaoNF() {
        return statusEmissaoNF;
    }

    public void setStatusEmissaoNF(List<EnumVo> statusEmissaoNF) {
        this.statusEmissaoNF = statusEmissaoNF;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public EntidadeVo getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EntidadeVo empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }
}
