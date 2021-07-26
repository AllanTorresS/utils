package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Objeto utilizado para a realização de upload de anexos para o salesforce.
 *
 * @author pedro.silva
 */
public class UploadAnexoChamadoVo {
    @JsonProperty("Title")
    private String titulo;
    @JsonProperty("PathOnClient")
    private String nomeArquivo;
    @JsonProperty("ContentLocation")
    private String localizacaoConteudo;
    @JsonProperty("VersionData")
    private String conteudoAnexo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getLocalizacaoConteudo() {
        return localizacaoConteudo;
    }

    public void setLocalizacaoConteudo(String localizacaoConteudo) {
        this.localizacaoConteudo = localizacaoConteudo;
    }

    public String getConteudoAnexo() {
        return conteudoAnexo;
    }

    public void setConteudoAnexo(String conteudoAnexo) {
        this.conteudoAnexo = conteudoAnexo;
    }
}
