package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPostoInternoTipoCombustivelPrecoDados;
import ipp.aci.boleia.dominio.PostoInternoTipoCombustivelPreco;
import ipp.aci.boleia.dominio.RotaPostoDesconsiderado;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Rota
 */
@Repository
public class OraclePostoInternoTipoCombustivelPrecoDados extends OracleRepositorioBoleiaDados<PostoInternoTipoCombustivelPreco> implements IPostoInternoTipoCombustivelPrecoDados {

    /**
     * ConstrutorPostoInternoTipoCombustivelPreco
     */
    public OraclePostoInternoTipoCombustivelPrecoDados() {
        super(PostoInternoTipoCombustivelPreco.class);
    }

    public List<PostoInternoTipoCombustivelPreco> obterPorPostoInternoTipoCombustivel(Long idFrota, Long idUnide, Long idTipoCombustivel) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));
        parametros.add(new ParametroPesquisaIgual("idUnidade", idUnide));
        parametros.add(new ParametroPesquisaIgual("idTipoCombustivel", idTipoCombustivel));

        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

}
