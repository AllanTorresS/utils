package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDocumentoAceiteDados;
import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.DocumentoAceite;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.DocumentoTipo;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de dados da entidade Documento.
 */
@Repository
public class OracleDocumentoAceiteDados extends OracleRepositorioBoleiaDados<DocumentoAceite> implements IDocumentoAceiteDados {

    private static final String QUERY_QUANTIDADE_TOTAL_ACEITES_DOCUMENTO =
            "SELECT COUNT(0) " +
            "FROM DocumentoAceite da " +
            "LEFT JOIN da.usuario u " +
            "LEFT JOIN u.frota f " +
            "WHERE da.usuarioAceitou = :usuarioAceitou " +
            "AND u.status = :idStatusUsuario " +
            "AND u.excluido = :excluido " +
            "AND da.documento.id = :idDocumento ";

    /**
     * Construtor default da classe.
     */
    public OracleDocumentoAceiteDados() {
        super(DocumentoAceite.class);
    }

    @Override
    public long obterQuantidadeAceitesDocumento(Documento documento) {
        StringBuilder query = new StringBuilder(QUERY_QUANTIDADE_TOTAL_ACEITES_DOCUMENTO);

        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("idDocumento", documento.getId()));
        parametros.add(new ParametroPesquisaIgual("usuarioAceitou", true));
        parametros.add(new ParametroPesquisaIgual("idStatusUsuario", StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual("excluido", false));

        if (TipoPerfilUsuario.FROTA.getValue().equals(documento.getTipoPerfil().getId()) &&
                DocumentoTipo.INTEGRACAO_KMV.getValor().equals(documento.getTipo())) {
            query.append("AND f.status = :idStatusFrota ");
            query.append("AND u.cpf = f.cpfDonoFrota ");
            parametros.add(new ParametroPesquisaIgual("idStatusFrota", StatusAtivacao.ATIVO.getValue()));
        }

        return pesquisarUnicoSemIsolamentoDados(query.toString(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public DocumentoAceite obterAceite(Documento documento, Usuario usuario) {
        return pesquisarUnico(new ParametroPesquisaIgual("documento.id", documento.getId()),
                new ParametroPesquisaIgual("usuario.id", usuario.getId()));
    }
}
