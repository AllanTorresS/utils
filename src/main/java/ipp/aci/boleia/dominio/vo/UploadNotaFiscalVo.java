package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Transporta os dados no momento do upload de notas fiscais
 */
public class UploadNotaFiscalVo {

    private List<Long> idsAutorizacoes;
    private FiltroPesquisaAbastecimentoVo filtroPesquisa;
    private List<String> xmlNotasBase64;
    private Long indiceArquivo;

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

    public List<String> getXmlNotasBase64() {
        return xmlNotasBase64;
    }

    public void setXmlNotasBase64(List<String> xmlNotasBase64) {
        this.xmlNotasBase64 = xmlNotasBase64;
    }

    public Long getIndiceArquivo() {
        return indiceArquivo;
    }

    public void setIndiceArquivo(Long indiceArquivo) {
        this.indiceArquivo = indiceArquivo;
    }
}
