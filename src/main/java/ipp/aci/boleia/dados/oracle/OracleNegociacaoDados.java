package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.INegociacaoDados;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.Negociacao;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaNegociacaoVo;
import ipp.aci.boleia.util.seguranca.UtilitarioIsolamentoInformacoes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades de Negociacao
 */
@Repository
public class OracleNegociacaoDados extends OracleRepositorioBoleiaDados<Negociacao> implements INegociacaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleNegociacaoDados() {
        super(Negociacao.class);
    }

    /**
     * Monta os parametros de pesquisa a partir do filtro de busca recebido
     *
     * @param filtro O filtro de busca
     * @return Uma lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaNegociacaoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if(filtro.getFrota() != null && filtro.getFrota().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("frotaPtov.frota", filtro.getFrota().getId()));
        }
        if(filtro.getPontoDeVenda()!=null && filtro.getPontoDeVenda().getId()!=null) {
            parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda", filtro.getPontoDeVenda().getId()));
        }
        if(filtro.getUfPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda.uf", filtro.getUfPontoDeVenda().getName()));
        }
        if(filtro.getMunicipioPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaLike("frotaPtov.pontoVenda.municipio", filtro.getMunicipioPontoDeVenda()));
        }
        return parametros;
    }

    @Override
    public ResultadoPaginado<Negociacao> pesquisaPaginada(FiltroPesquisaNegociacaoVo filtro) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);
        if(filtro.getPaginacao() != null
                && filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty()) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("frotaPtov.frota.nomeFantasia"));
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("frotaPtov.pontoVenda.nome"));
        }
      
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Negociacao> pesquisaPaginadaValidacaoSegregacao(FiltroPesquisaNegociacaoVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);
        if(filtro.getPaginacao() != null
                && filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty()) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("frotaPtov.frota.nomeFantasia"));
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("frotaPtov.pontoVenda.nome"));
        }
        if(UtilitarioIsolamentoInformacoes.isUsuarioInternoAssessorOuCoordenador(usuarioLogado)){
            return pesquisarSemIsolamentoDados(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }else {
            return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }
    }

    @Override
    public Negociacao obterPorFrotaPontoVenda(FrotaPontoVenda frotaPontoVenda) {
        return pesquisarUnico(new ParametroPesquisaIgual("frotaPtov.id", frotaPontoVenda.getId()));
    }


    @Override
    public List<Negociacao> obterPorIdFrota(Long idFrota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("frotaPtov.frota.id", idFrota));
    }
}
