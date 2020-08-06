package ipp.aci.boleia.dominio.vo;

/**
 * Classe para retornar link pré-assinado para download de arquivos
 */
public class UrlS3PreAssinadaVo {

    private String url;

    /**
     * Construtor
     * @param url A URL a ser retornada
     */
    public UrlS3PreAssinadaVo(String url) {
        this.url = url;
    }

    /**
     * Construtor padrão
     */
    public UrlS3PreAssinadaVo(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
