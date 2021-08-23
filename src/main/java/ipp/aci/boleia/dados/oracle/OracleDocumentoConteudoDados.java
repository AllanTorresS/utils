package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDocumentoConteudoDados;
import ipp.aci.boleia.dominio.DocumentoConteudo;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades AvaliacaoAbastecimento
 */
@Repository
public class OracleDocumentoConteudoDados extends OracleRepositorioBoleiaDados<DocumentoConteudo> implements IDocumentoConteudoDados {

    /**
     * Instancia o repositorio
     */
    public OracleDocumentoConteudoDados() {
        super(DocumentoConteudo.class);
    }

    @Override
    public DocumentoConteudo obterPorDocumento(Long documentoId) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual( "documento.id", documentoId));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}