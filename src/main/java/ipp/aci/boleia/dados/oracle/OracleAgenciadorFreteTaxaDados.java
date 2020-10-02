package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteTaxaDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteTaxa;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteTaxaDados extends OracleRepositorioBoleiaDados<AgenciadorFreteTaxa> implements IAgenciadorFreteTaxaDados {

    /**
     * Instancia o repositorio
     */
    public OracleAgenciadorFreteTaxaDados() {
        super(AgenciadorFreteTaxa.class);
    }


    @Override
    public ResultadoPaginado<AgenciadorFreteTaxa> obterTaxaPorAgenciadorFrete(Long idAgenciador, Date dataVigencia, InformacaoPaginacao paginacao) {
        if (paginacao == null){
            paginacao = new InformacaoPaginacao();
        }

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("agenciadorFrete.id", idAgenciador));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataVigencia",
                UtilitarioCalculoData.obterUltimoInstanteDia(dataVigencia)));

        return pesquisar(paginacao,
                parametros.toArray(new ParametroPesquisa[parametros.size()]));

    }
}