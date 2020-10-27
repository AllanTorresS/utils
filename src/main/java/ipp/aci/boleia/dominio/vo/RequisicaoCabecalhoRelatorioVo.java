package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Objeto que representa uma requisição de preenchimento de cabeçalho de relatórios com cabeçalhos dinâmicos
 * @param <F> O tipo do objeto de filtro
 */
public class RequisicaoCabecalhoRelatorioVo<F extends FiltroBasePeriodoPaginado> {
    private String conteudoCabecalho;
    private F filtro;

    public RequisicaoCabecalhoRelatorioVo() {
        // construtor padrão
    }

    /**
     * Construtor
     * @param conteudoCabecalho A string que representa o conteúdo pré-formatado do cabeçalho
     * @param filtro O filtro usado para gerar o relatório
     */
    public RequisicaoCabecalhoRelatorioVo(String conteudoCabecalho, F filtro) {
        this.conteudoCabecalho = conteudoCabecalho;
        this.filtro = filtro;
    }

    public String getConteudoCabecalho() {
        return conteudoCabecalho;
    }

    public void setConteudoCabecalho(String conteudoCabecalho) {
        this.conteudoCabecalho = conteudoCabecalho;
    }

    public F getFiltro() {
        return filtro;
    }

    public void setFiltro(F filtro) {
        this.filtro = filtro;
    }
}
