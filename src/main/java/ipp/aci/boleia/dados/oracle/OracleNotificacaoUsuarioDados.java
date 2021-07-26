package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.INotificacaoUsuarioDados;
import ipp.aci.boleia.dominio.NotificacaoUsuario;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusLeitura;
import ipp.aci.boleia.dominio.enums.TipoCategoriaNotificacao;
import ipp.aci.boleia.dominio.enums.TipoSubcategoriaNotificacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaNotificacaoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades Motorista
 */
@Repository
public class OracleNotificacaoUsuarioDados extends OracleRepositorioBoleiaDados<NotificacaoUsuario> implements INotificacaoUsuarioDados {
    private static final String VISUALIZAR_NOTIFICACOES_POR_CATEGORIA = "UPDATE ipp.aci.boleia.dominio.NotificacaoUsuario AS nu " +
            "SET nu.dataVisualizacao = :dataVisualizacao " +
            "WHERE nu.usuario.id = :usuarioId AND " +
            "nu.notificacao.id in (SELECT n.id " +
            "FROM ipp.aci.boleia.dominio.Notificacao n " +
            "WHERE n.subcategoria.categoria.id = :categoriaId ) ";

    private static final String EXCLUIR_NOTIFICACOES_POR_DATA_LIMITE = "UPDATE ipp.aci.boleia.dominio.NotificacaoUsuario AS nu " +
            "SET nu.excluido = 1 " +
            "WHERE nu.notificacao.id in (SELECT n.id " +
            "FROM ipp.aci.boleia.dominio.Notificacao n " +
            "WHERE n.dataEnvio <= :dataLimite ) ";

    private static final String EXCLUIR_NOTIFICACOES_POR_USUARIO = "DELETE FROM ipp.aci.boleia.dominio.NotificacaoUsuario AS nu " +
            "WHERE nu.usuario.id = :idUsuario ";

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Instancia o repositorio
     */
    public OracleNotificacaoUsuarioDados() {
        super(NotificacaoUsuario.class);
    }

    @Override
    public ResultadoPaginado<NotificacaoUsuario> pesquisar(FiltroPesquisaNotificacaoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("usuario",filtro.getIdUsuario()));

        if(filtro.getCategoria()==null) {
            return new ResultadoPaginado<>(new ArrayList<>(), 0);
        }
        TipoCategoriaNotificacao categoria = TipoCategoriaNotificacao.valueOf(filtro.getCategoria().getName());
        parametros.add(new ParametroPesquisaIgual("notificacao.subcategoria.categoria", categoria.getValue()));

        if(filtro.getAte()!=null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("notificacao.dataEnvio",UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }
        if(filtro.getDe()!=null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("notificacao.dataEnvio",UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        }
        if(StringUtils.isNotBlank(filtro.getTitulo())) {
            parametros.add(new ParametroPesquisaLike("notificacao.titulo",filtro.getTitulo()));
        }
        if(filtro.getStatus()!=null && filtro.getStatus().getName()!=null) {
            StatusLeitura statusLeitura = StatusLeitura.valueOf(filtro.getStatus().getName());
            parametros.add(new ParametroPesquisaNulo("dataLeitura",StatusLeitura.LIDO.equals(statusLeitura)));
        }

        List<ParametroOrdenacaoColuna> ordenacao = Collections.singletonList(new ParametroOrdenacaoColuna("notificacao.dataEnvio", Ordenacao.DECRESCENTE));
        filtro.getPaginacao().setParametrosOrdenacaoColuna(ordenacao);
        return pesquisar(filtro.getPaginacao(),parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<NotificacaoUsuario> pesquisarUltimas(Usuario usuario, TipoCategoriaNotificacao categoria, Integer numero) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1,numero);
        paginacao.setParametrosOrdenacaoColuna(Collections.singletonList(new ParametroOrdenacaoColuna("notificacao.dataEnvio", Ordenacao.DECRESCENTE)));

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("usuario.id", usuario.getId()));
        parametros.add(new ParametroPesquisaIgual("notificacao.subcategoria.categoria", categoria.getValue()));

        ResultadoPaginado<NotificacaoUsuario> ultimas = pesquisar(paginacao,parametros.toArray(new ParametroPesquisa[parametros.size()]));

        parametros.add(new ParametroPesquisaNulo("dataLeitura"));
        parametros.add(new ParametroPesquisaNulo("dataVisualizacao"));
        ultimas.setTotalItems(pesquisarTotalRegistros(parametros.toArray(new ParametroPesquisa[parametros.size()])).intValue());

        return ultimas;
    }

    @Override
    public List<NotificacaoUsuario> listarNaoLidas(Usuario usuario, TipoCategoriaNotificacao categoria) {
        return pesquisar(
                new ParametroOrdenacaoColuna(),
                new ParametroPesquisaIgual("usuario.id", usuario.getId()),
                new ParametroPesquisaIgual("notificacao.subcategoria.categoria", categoria.getValue()),
                new ParametroPesquisaNulo("dataVisualizacao")
        );
    }

    @Override
    public void visualizarNotificacoesDeUmUsuarioPorCategoria(Usuario usuario, TipoCategoriaNotificacao categoria){
       Query query = getGerenciadorDeEntidade().createQuery(VISUALIZAR_NOTIFICACOES_POR_CATEGORIA);
       query.setParameter("dataVisualizacao", utilitarioAmbiente.buscarDataAmbiente());
       query.setParameter("usuarioId", usuario.getId());
       query.setParameter("categoriaId",categoria.getValue());
       query.executeUpdate();
    }

    @Override
    public NotificacaoUsuario obterUltimaNotificacaoUsuarioPorSubCategoria(Usuario usuario, TipoSubcategoriaNotificacao tipoSubcategoriaNotificacao){
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1);
        paginacao.setParametrosOrdenacaoColuna(Collections.singletonList(new ParametroOrdenacaoColuna("notificacao.dataEnvio", Ordenacao.DECRESCENTE)));

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("usuario.id", usuario.getId()));
        parametros.add(new ParametroPesquisaIgual("notificacao.subcategoria", tipoSubcategoriaNotificacao.getValue()));

        ResultadoPaginado<NotificacaoUsuario> resultadoPaginado = pesquisar(paginacao,parametros.toArray(new ParametroPesquisa[parametros.size()]));

        List<NotificacaoUsuario> ultimasNotificacoes = resultadoPaginado.getRegistros();
        if(ultimasNotificacoes != null && !ultimasNotificacoes.isEmpty()){
            return ultimasNotificacoes.get(0);
        }
        return null;
    }

    @Override
    public void excluirNotificacoesAteUmaDataLimite(Date dataLimite) {
        Query query = getGerenciadorDeEntidade().createQuery(EXCLUIR_NOTIFICACOES_POR_DATA_LIMITE);
        query.setParameter("dataLimite", dataLimite);
        query.executeUpdate();
    }

    @Override
    public void excluirNotificacoesPorUsuario(Usuario usuario) {
        Query query = getGerenciadorDeEntidade().createQuery(EXCLUIR_NOTIFICACOES_POR_USUARIO);
        query.setParameter("idUsuario", usuario.getId());
        query.executeUpdate();
    }
}
