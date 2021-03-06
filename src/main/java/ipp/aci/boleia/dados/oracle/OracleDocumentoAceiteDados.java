package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDocumentoAceiteDados;
import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.DocumentoAceite;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.DocumentoTipo;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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

    private static final String EXCLUSAO_POR_ID_USUARIO =
            "DELETE FROM DocumentoAceite da " +
            "WHERE da.usuario.id = :idUsuario";

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

    @Override
    public List<DocumentoAceite> obterPorUsuario(Usuario usuario) {
        return this.pesquisar(new ParametroOrdenacaoColuna(), new ParametroPesquisa[]{new ParametroPesquisaIgual("usuario", usuario)});
    }

    @Override
    public void excluirPermanentementePorIdUsuario(Long idUsuario) {
        Query query = getGerenciadorDeEntidade().createQuery(EXCLUSAO_POR_ID_USUARIO);
        query.setParameter("idUsuario", idUsuario);
        query.executeUpdate();
    }
}
