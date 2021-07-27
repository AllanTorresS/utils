package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.DocumentoAceite;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;

import java.util.Date;

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

}
