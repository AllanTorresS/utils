package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICobrancaAgenciadorFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Cobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioCobrancaVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades de Cobrança de Agenciador de Frete
 */
@Repository
public class OracleCobrancaAgenciadorFreteDados extends OracleRepositorioBoleiaDados<Cobranca> implements ICobrancaAgenciadorFreteDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleCobrancaAgenciadorFreteDados() {
        super(Cobranca.class);
    }

    @Override
    public ResultadoPaginado<Cobranca> pesquisar(FiltroRelatorioCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getAte() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataCriacao", filtro.getAte()));
        }
        if (filtro.getDe()!= null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataCriacao", filtro.getDe()));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}
