package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPostoInternoTipoCombustivelPrecoDados;
import ipp.aci.boleia.dominio.PostoInternoTipoCombustivelPreco;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades de PostoInternoTipoCombustivelPreco que define relação entre Posto Interno e um Tipo de Combustivel com seu preço
 */
@Repository
public class OraclePostoInternoTipoCombustivelPrecoDados extends OracleRepositorioBoleiaDados<PostoInternoTipoCombustivelPreco> implements IPostoInternoTipoCombustivelPrecoDados {

    /**
     * ConstrutorPostoInternoTipoCombustivelPreco
     */
    public OraclePostoInternoTipoCombustivelPrecoDados() {
        super(PostoInternoTipoCombustivelPreco.class);
    }

    @Override
    public PostoInternoTipoCombustivelPreco obterPorPostoInternoTipoCombustivel(Long idFrota, Long idUnidade, Long idTipoCombustivel) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if (idFrota != null){
            parametros.add(new ParametroPesquisaIgual("frota.id", idFrota));
        }else if (idUnidade != null){
            parametros.add(new ParametroPesquisaIgual("unidade.id", idUnidade));
        }
        parametros.add(new ParametroPesquisaIgual("tipoCombustivel.id", idTipoCombustivel));

        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

}
