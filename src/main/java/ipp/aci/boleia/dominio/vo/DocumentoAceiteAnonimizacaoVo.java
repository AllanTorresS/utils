package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.DocumentoAceite;

/**
 * Classe de exibição que representa um usuário motorista anonimização
 */
public class DocumentoAceiteAnonimizacaoVo {

    private Long id;
    private Boolean usuarioAceitou;
    private Long idDocumento;
    private Long versao;
    private Integer sistema;
    private String sistemaConvertido;

    /**
     * Construtor básico para serialização JSON
     */
    public DocumentoAceiteAnonimizacaoVo() {

    }

    /**
     * Constrói o DocumentoAceite com os dados fornecidos
     *
     * @param documentoAceite a entidade contendo informações
     */
    public DocumentoAceiteAnonimizacaoVo(DocumentoAceite documentoAceite) {
        if(documentoAceite == null){
            return;
        }
        this.idDocumento = documentoAceite.getDocumento() != null ? documentoAceite.getDocumento().getId() : null;
        this.id = documentoAceite.getId();
        this.usuarioAceitou = documentoAceite.getUsuarioAceitou();
        this.versao = documentoAceite.getVersao();
        this.sistema = documentoAceite.getSistema();
        this.sistemaConvertido = documentoAceite.getSistemaConvertido();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUsuarioAceitou() {
        return usuarioAceitou;
    }

    public void setUsuarioAceitou(Boolean usuarioAceitou) {
        this.usuarioAceitou = usuarioAceitou;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Integer getSistema() {
        return sistema;
    }

    public void setSistema(Integer sistema) {
        this.sistema = sistema;
    }

    public String getSistemaConvertido() {
        return sistemaConvertido;
    }

    public void setSistemaConvertido(String sistemaConvertido) {
        this.sistemaConvertido = sistemaConvertido;
    }
}
