package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Filtro de pesquisa utilizado para a realização de download de notas fiscais.
 *
 * @author pedro.silva
 */
public class FiltroPesquisaDownloadNotaVo {
    private String formato;
    private Long idAutorizacaoPagamento;
    private List<Long> idsNotas;

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Long getIdAutorizacaoPagamento() {
        return idAutorizacaoPagamento;
    }

    public void setIdAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        this.idAutorizacaoPagamento = idAutorizacaoPagamento;
    }

    public List<Long> getIdsNotas() {
        return idsNotas;
    }

    public void setIdsNotas(List<Long> idsNotas) {
        this.idsNotas = idsNotas;
    }
}
