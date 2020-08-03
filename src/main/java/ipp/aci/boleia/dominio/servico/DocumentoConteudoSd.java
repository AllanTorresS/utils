package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.DocumentoConteudo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade {@link DocumentoConteudo}.
 */
@Component
public class DocumentoConteudoSd {
    @Autowired
    private IDocumentoConteudoDados repositorio;

    @Autowired
    private DocumentoSd documentoSd;

    /**
     * Obter o conteúdo de um documento pelo seu identificador
     * @param documentoId o identificador do documento
     * @return o conteudo
     */
    public DocumentoConteudo obterConteudoDoDocumento(Long documentoId){
        return repositorio.obterPorDocumento(documentoId);
    }

    /**
     * Armazenar o conteúdo de um documento
     * @param documento o documento a ter seu conteudo armazenado
     * @param texto o texto do conteudo
     * @return o conteudo
     */
    public DocumentoConteudo armazenarConteudo(Documento documento, String texto) {
        DocumentoConteudo documentoConteudo = new DocumentoConteudo();
        documentoConteudo.setTexto(texto);
        documentoConteudo.setDocumento(documento);
        documentoConteudo = repositorio.armazenar(documentoConteudo);
        documento.setConteudo(documentoConteudo);
        documento = documentoSd.armazenar(documento);
        documentoConteudo.setDocumento(documento);
        return documentoConteudo;
    }
}
