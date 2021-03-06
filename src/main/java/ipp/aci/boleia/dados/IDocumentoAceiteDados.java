package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.DocumentoAceite;
import ipp.aci.boleia.dominio.Usuario;

import java.util.List;

/**
 * Implementação do repositório de dados da entidade Documento.
 */
public interface IDocumentoAceiteDados extends IRepositorioBoleiaDados<DocumentoAceite> {

    /**
     * Pesquisa a quantidade total de aceites de um documento
     *
     * @param documento O documento que será usado como filtro
     * @return lista de registros
     */
    long obterQuantidadeAceitesDocumento(Documento documento);

    /**
     * Obtém o aceite por documento e usuário
     *
     * @param documento O documento que será usado como filtro
     * @param usuario O usuario que será usado como filtro
     * @return O aceite de um documento por um usuário
     */
    DocumentoAceite obterAceite(Documento documento, Usuario usuario);

    /**
     * Obter todos os documentos do usuário
     *
     * @param usuario O usuario que será usado como filtro
     * @return A lista de documentos
     */
    List<DocumentoAceite> obterPorUsuario(Usuario usuario);

    /**
     * Exclui registros dos aceites de documento do usuário.
     *
     * @param idUsuario que será usado como filtro
     */
    void excluirPermanentementePorIdUsuario(Long idUsuario);
}
