package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IComandaDigitalDados;
import ipp.aci.boleia.dominio.ComandaDigital;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaComandaVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Respositorio de entidades Motorista
 */
@Repository
public class OracleComandaDigitalDados extends OracleRepositorioBoleiaDados<ComandaDigital> implements IComandaDigitalDados {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Instancia o repositorio
     */
    public OracleComandaDigitalDados() {
        super(ComandaDigital.class);
    }

    @Override
    public ResultadoPaginado<ComandaDigital> pesquisar(FiltroPesquisaComandaVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroStatusHabilitacao(filtro, parametros);

        if (filtro.getNome() != null) {
            parametros.add(new ParametroPesquisaLike("nome", filtro.getNome()));
        }
        if(filtro.getPostoInterno() == null || !filtro.getPostoInterno()){
            parametros.add(new ParametroPesquisaNulo("frota"));
            if(filtro.getPontoVenda()!=null && filtro.getPontoVenda().getId()!=null) {
                parametros.add(new ParametroPesquisaIgual("pontoVenda", filtro.getPontoVenda().getId()));
            }
        } else {
            povoarParametroPostoInterno(filtro, parametros);
        }
        if(filtro.getBloqueado()!=null && filtro.getBloqueado().getName()!=null) {
            parametros.add(new ParametroPesquisaIgual("bloqueado", StatusBloqueio.valueOf(filtro.getBloqueado().getName()).getValue()));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ComandaDigital obterPorToken(String token) {
        return pesquisarUnico(new ParametroPesquisaIgual("token",token));
    }

    @Override
    public ComandaDigital obterPorTokenAutenticacao(String tokenJwt) {
        return pesquisarUnico(new ParametroPesquisaIgual("tokenAutenticacao",tokenJwt));
    }

    @Override
    public ComandaDigital obterPorNomePv(String nome, Long idPontoVenda) {
        return pesquisarUnico(new ParametroPesquisaIgualIgnoreCase("nome",nome), new ParametroPesquisaIgual("pontoVenda",idPontoVenda));
    }

    @Override
    public ComandaDigital obterPorNomeUnidade(String nome, Long idUnidade) {
        return pesquisarUnico(new ParametroPesquisaIgualIgnoreCase("nome",nome), new ParametroPesquisaIgual("unidade",idUnidade));
    }

    @Override
    public ComandaDigital obterPorNomeFrota(String nome, Long idFrota) {
        return pesquisarUnico(
                new ParametroPesquisaIgualIgnoreCase("nome",nome),
                new ParametroPesquisaIgual("frota",idFrota),
                new ParametroPesquisaNulo("unidade"));
    }

    @Override
    public void excluirPorUnidade(Long... idsUnidade) {
        if(idsUnidade != null) {
            List<ComandaDigital> comandas = pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIn("unidade",  Arrays.asList(idsUnidade)));
            comandas.forEach(m->excluir(m.getId()));
        }
    }

    @Override
    public void excluirPorFrota(Long... idsFrota) {
        if(idsFrota != null) {
            List<ComandaDigital> comandas = pesquisar(
                    (ParametroOrdenacaoColuna) null,
                    new ParametroPesquisaIn("frota",  Arrays.asList(idsFrota)),
                    new ParametroPesquisaNulo("unidade"));
            comandas.forEach(m->excluir(m.getId()));
        }
    }

    /**
     * Povoa os parametros da consulta referentes ao posto interno da frota
     * @param filtro O filtro de pesquisa
     * @param parametros A lista corrente de parametros
     */
    private void povoarParametroPostoInterno(FiltroPesquisaComandaVo filtro, List<ParametroPesquisa> parametros) {
        parametros.add(new ParametroPesquisaNulo("pontoVenda"));
        if(filtro.getFrota()!=null && filtro.getFrota().getId()!=null) {
            parametros.add(new ParametroPesquisaIgual("frota", filtro.getFrota().getId()));
        }
        if(filtro.getUnidade() != null && filtro.getUnidade().getId() != null ) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("unidade", filtro.getUnidade().getId()));
            }else if(filtro.getUnidade().getId() == -1){
                parametros.add(new ParametroPesquisaNulo("unidade"));
            }
        }
    }

    /**
     * Povoa os parametros da consulta referentes ao status de habilitacao da comanda
     * @param filtro O filtro de pesquisa
     * @param parametros A lista corrente de parametros
     */
    private void povoarParametroStatusHabilitacao(FiltroPesquisaComandaVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getHabilitado()!=null && filtro.getHabilitado().getName()!=null) {
            StatusHabilitacao statusFiltro = StatusHabilitacao.valueOf(filtro.getHabilitado().getName());
            if(StatusHabilitacao.EXPIRADO.equals(statusFiltro)) {
                parametros.add(new ParametroPesquisaDataMenorOuIgual("dataExpiracaoToken", utilitarioAmbiente.buscarDataAmbiente()));
            } else if(StatusHabilitacao.NAO_HABILITADO.equals(statusFiltro)){
                parametros.add(new ParametroPesquisaOr(new ParametroPesquisaIgual("habilitado", StatusHabilitacao.NAO_HABILITADO.getValue()),
                        new ParametroPesquisaIgual("habilitado", StatusHabilitacao.REGERADO.getValue())));
                parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataExpiracaoToken", utilitarioAmbiente.buscarDataAmbiente()));
            } else{
                parametros.add(new ParametroPesquisaIgual("habilitado", statusFiltro.getValue()));
            }
        }
    }
}
