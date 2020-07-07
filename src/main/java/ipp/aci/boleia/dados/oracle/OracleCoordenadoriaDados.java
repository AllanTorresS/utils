package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICoordenadoriaDados;
import ipp.aci.boleia.dominio.Coordenadoria;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCoordenadoriaVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Coordenadoria
 */
@Repository
public class OracleCoordenadoriaDados extends OracleRepositorioBoleiaDados<Coordenadoria> implements ICoordenadoriaDados {

    /**
     * Instancia o repositorio
     */
    public OracleCoordenadoriaDados() {
        super(Coordenadoria.class);
    }

    @Override
    public ResultadoPaginado<Coordenadoria> pesquisar(final FiltroPesquisaCoordenadoriaVo filtro, final Usuario usuarioLogado) {

        final List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro, usuarioLogado);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria uma lista de parametros para a montagem da consulta de coordenadoria a ser exibida
     *
     * @param filtro O filtro recebido
     * @param usuarioLogado usuário que está realizando a consulta
     * @return Uma lista de parametros
     */
    private List<ParametroPesquisa> criarParametrosPesquisa(final FiltroPesquisaCoordenadoriaVo filtro, final Usuario usuarioLogado) {
        final List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getNome() != null) {
            parametros.add(new ParametroPesquisaLike("nome", filtro.getNome()));
        }
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusAtivacao.valueOf(filtro.getStatus().getName()).getValue()));
        }
        if (filtro.getNomeLoginCoordenador() != null) {
            parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaLike("coordenador.nome", filtro.getNomeLoginCoordenador()),
                    new ParametroPesquisaLike("coordenador.login", filtro.getNomeLoginCoordenador()))
            );
        }
        if (!usuarioLogado.isGestor()) {
            parametros.add(new ParametroPesquisaIgual("coordenador.id", usuarioLogado.getId()));
        }
        return parametros;
    }

    @Override
    public List<Coordenadoria> pesquisarAtivas() {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()));
    }

    @Override
    public List<Coordenadoria> pesquisarAtivasComFrotas() {
        return pesquisar(
                (ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()),
                new ParametroPesquisaNulo("assessores.frotasAssessoradas", true)
        );
    }

    @Override
    public Coordenadoria obterPorNome(final String nome, final Long notId) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("nome", nome),
                new ParametroPesquisaDiferente("id", notId)
        );
    }

    @Override
    public Coordenadoria obterPorCoordenador(final String coordenador, final Long notId) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("coordenador.email", coordenador),
                new ParametroPesquisaDiferente("id", notId)
        );
    }
}
