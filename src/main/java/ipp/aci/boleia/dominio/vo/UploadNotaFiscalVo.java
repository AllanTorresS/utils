package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Transporta os dados no momento do upload de notas fiscais
 */
public class UploadNotaFiscalVo {

    private List<Long> idsAutorizacoes;
    private FiltroPesquisaAbastecimentoVo filtroPesquisa;
    private String xmlNotaBase64;

    public UploadNotaFiscalVo() {
        // serializacao json
    }

    public List<Long> getIdsAutorizacoes() {
        return idsAutorizacoes;
    }

    public void setIdsAutorizacoes(List<Long> idsAutorizacoes) {
        this.idsAutorizacoes = idsAutorizacoes;
    }

    public FiltroPesquisaAbastecimentoVo getFiltroPesquisa() {
        return filtroPesquisa;
    }

    public void setFiltroPesquisa(FiltroPesquisaAbastecimentoVo filtroPesquisa) {
        this.filtroPesquisa = filtroPesquisa;
    }

    public String getXmlNotaBase64() {
        return xmlNotaBase64;
    }

    public void setXmlNotaBase64(String xmlNotaBase64) {
        this.xmlNotaBase64 = xmlNotaBase64;
    }
}
