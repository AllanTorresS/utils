package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.enums.DocumentoStatus;
import ipp.aci.boleia.dominio.enums.DocumentoTipo;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDocumentoVo;

import java.util.List;

/**
 * Implementação do repositório de dados da entidade Documento.
 */
public interface IDocumentoDados extends IRepositorioBoleiaDados<Documento> {
    /**
     * Pesquisa registros a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return lista de registros
     */
    ResultadoPaginado<Documento> pesquisar(FiltroPesquisaDocumentoVo filtro);

    /**
     * Pesquisa documento por tipo, versão e tipo de perfil do usuário.
     *
     * @param tipo O tipo de documento
     * @param versao A versão do documento
     * @param idTipoPerfil O id referente ao tipo de perfil do usuário
     * @return O documento encontrado ou nulo caso não exista
     */
    Documento obterDocumento(DocumentoTipo tipo, String versao, Long idTipoPerfil);

    /**
     * Pesquisa documento por tipo, status e tipo de perfil do usuário.
     *
     * @param tipo O tipo de documento
     * @param status O status do documento
     * @param idTipoPerfil O id referente ao tipo de perfil do usuário
     * @return O documento encontrado ou nulo caso não exista
     */
    ResultadoPaginado<Documento> obterDocumentos(DocumentoTipo tipo, DocumentoStatus status, Long idTipoPerfil);

    /**
     * Obter documentos aguardando publicação que serão publicados.
     *
     * @return Os documentos a serem publicados
     */
    List<Documento> obterDocumentosParaPublicacao();

    /**
     * Retorna lista de documentos por tipo, status e tipo de perfil do usuário.
     *
     * @param tipo O tipo de documento
     * @param idTipoPerfil O id referente ao tipo de perfil do usuário
     * @param status O status do documento
     * @return Lista de documentos encontrados
     */
    List<Documento> obterListaDocumentos(DocumentoTipo tipo, Long idTipoPerfil, DocumentoStatus status);
}
