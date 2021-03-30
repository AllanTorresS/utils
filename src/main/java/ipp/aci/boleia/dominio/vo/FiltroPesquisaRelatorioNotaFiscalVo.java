package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Filtro de pesquisa utilizado na geração do relatório de nota fiscal.
 *
 * @author pedro.silva
 */
public class FiltroPesquisaRelatorioNotaFiscalVo {
    private Date de;
    private Date ate;
    private EnumVo statusCiclo;
    private String numeroNotaFiscal;
    private String numeroSerie;
    private EntidadeVo frota;
    private EntidadeVo pontoVenda;
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

    public EnumVo getStatusCiclo() {
        return statusCiclo;
    }

    public void setStatusCiclo(EnumVo statusCiclo) {
        this.statusCiclo = statusCiclo;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
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
