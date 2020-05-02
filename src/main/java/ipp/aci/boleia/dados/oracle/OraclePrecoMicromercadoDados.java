package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoMicromercadoDados;
import ipp.aci.boleia.dominio.PrecoMicromercado;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoMicromercadoVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades
 * PrecoBase
 */
@Repository
public class OraclePrecoMicromercadoDados extends OracleRepositorioBoleiaDados<PrecoMicromercado> implements IPrecoMicromercadoDados {

    private static final String QUERY_PRECOS_MICOMERCADO_ATUAIS =
        " SELECT " +
        "     pm " +
        " FROM " +
        "     PrecoMicromercado pm " +
        "     JOIN FETCH pm.micromercado mm " +
        "     JOIN FETCH pm.tipoCombustivel tc " +
        " WHERE " +
        "     pm.dataAtualizacao = ( " +
        "         SELECT " +
        "             MAX(pm2.dataAtualizacao) " +
        "         FROM " +
        "             PrecoMicromercado pm2 " +
        "         WHERE " +
        "             pm2.micromercado = pm.micromercado " +
        "             AND pm2.tipoCombustivel = pm.tipoCombustivel " +
        "     )";

    private static final String QUERY_PREDICATE_COMBUSTIVEL = " AND tc.id = :idCombustivel ";
    private static final String QUERY_PREDICATE_MICROMERCADO = " AND mm.id = :idMicromercado ";

    /**
     * Instancia o repositorio
     */
    public OraclePrecoMicromercadoDados() {
        super(PrecoMicromercado.class);
    }

    /**
     * Monta os parametros de pesquisa a partir do filtro de busca recebido
     *
     * @param filtro O filtro de busca
     * @return Uma lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaPrecoMicromercadoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if(filtro.getMicromercado() != null) {
            parametros.add(new ParametroPesquisaIgual("micromercado.id", filtro.getMicromercado().getId()));
        }
        if(filtro.getProduto() != null) {
            parametros.add(new ParametroPesquisaIgual("tipoCombustivel.id", filtro.getProduto().getId()));
        }

        if(filtro.getDataAtualizacao() != null ) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAtualizacao", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataAtualizacao())));
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataAtualizacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataAtualizacao())));
        }

        if(filtro.getUfPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIgual("micromercado.pontosDeVenda.uf", filtro.getUfPontoDeVenda().getName()));
        }

        if(filtro.getMunicipioPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaLike("micromercado.pontosDeVenda.municipio", filtro.getMunicipioPontoDeVenda()));
        }

        return parametros;
    }

    @Override
    public ResultadoPaginado<PrecoMicromercado> pesquisaPaginada(FiltroPesquisaPrecoMicromercadoVo filtro) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PrecoMicromercado> obterAtuais() {
        return pesquisar(null, QUERY_PRECOS_MICOMERCADO_ATUAIS).getRegistros();
    }

    @Override
    public List<PrecoMicromercado> obterAtuaisPorMicromercado(Long idMicromercado) {
        return pesquisar(null, QUERY_PRECOS_MICOMERCADO_ATUAIS + QUERY_PREDICATE_MICROMERCADO,
                new ParametroPesquisaIgual("idMicromercado", idMicromercado)).getRegistros();
    }

    @Override
    public PrecoMicromercado obterAtual(Long idMicromercado, Long idCombustivel) {
        return pesquisarUnico(QUERY_PRECOS_MICOMERCADO_ATUAIS + QUERY_PREDICATE_COMBUSTIVEL + QUERY_PREDICATE_MICROMERCADO,
                new ParametroPesquisaIgual("idMicromercado", idMicromercado), new ParametroPesquisaIgual("idCombustivel", idCombustivel));
    }

}
