package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Transporta os dados no momento do upload de notas fiscais
 */
public class UploadJustificativaNotaFiscalVo {

    private List<Long> idsAutorizacoes;
    private String nomeArquivo;
    private FiltroPesquisaAbastecimentoVo filtroPesquisa;
    private String arquivoNotaBase64;
    private String justificativa;

    public UploadJustificativaNotaFiscalVo() {
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

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getArquivoNotaBase64() {
        return arquivoNotaBase64;
    }

    public void setArquivoNotaBase64(String arquivoNotaBase64) {
        this.arquivoNotaBase64 = arquivoNotaBase64;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}
