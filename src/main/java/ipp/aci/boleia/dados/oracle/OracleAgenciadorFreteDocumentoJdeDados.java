package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteDados;
import ipp.aci.boleia.dados.IAgenciadorFreteDocumentoJdeDados;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFrete;
import ipp.aci.boleia.dominio.agenciadorfrete.DocumentoJde;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteDocumentoJdeDados extends OracleRepositorioBoleiaDados<DocumentoJde> implements IAgenciadorFreteDocumentoJdeDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleAgenciadorFreteDocumentoJdeDados() {
        super(DocumentoJde.class);
    }
}
