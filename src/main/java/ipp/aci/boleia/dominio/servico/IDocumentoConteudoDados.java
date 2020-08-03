package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IRepositorioBoleiaDados;
import ipp.aci.boleia.dominio.DocumentoConteudo;

/**
 * Implementação do repositório de dados da entidade DocumentoConteudo.
 */
public interface IDocumentoConteudoDados extends IRepositorioBoleiaDados<DocumentoConteudo> {
    /**
     * Obter DocumentoConteudo Pelo identificador do Documento
     *
     * @param documentoId O identificador do documento
     * @return o conteudo do documento
     */
    DocumentoConteudo obterPorDocumento(Long documentoId);
}
