package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMunicipioDados;
import ipp.aci.boleia.dominio.Municipio;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Municipio
 */
@Repository
public class OracleMunicipioDados extends OracleRepositorioBoleiaDados<Municipio> implements IMunicipioDados {

    /**
     * Instancia o repositorio
     */
    public OracleMunicipioDados() {
        super(Municipio.class);
    }


    @Override
    public List<Municipio> pesquisarPorNomeUf(FiltroPesquisaParcialVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);
        return pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Povoa os parametros de autocomplete
     * @param filtro filtro com o termo a pesquisar
     * @param parametros lista de parametros a ser povoada
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialVo filtro, List<ParametroPesquisa> parametros) {
        ParametroPesquisa paramNome = new ParametroPesquisaLike("nome", filtro.getTermo());
        ParametroPesquisa paramUf = new ParametroPesquisaLike("siglaUf", filtro.getTermo());

        parametros.add(new ParametroPesquisaOr(paramNome, paramUf));
    }

    @Override
    public Municipio obterPorIdentificadorPadrao(String identificador) {
        String[] parametrosMunicipio = identificador.split(" - ");
        String nomeMunicipio = parametrosMunicipio[0];
        String siglaUf = parametrosMunicipio[1];

        ParametroPesquisa parametroNome = new ParametroPesquisaIgual("nome", nomeMunicipio);
        ParametroPesquisa parametroSiglaUf = new ParametroPesquisaIgual("siglaUf", siglaUf);

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(parametroNome);
        parametros.add(parametroSiglaUf);

        return pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .stream().findFirst().orElse(null);
    }
}
