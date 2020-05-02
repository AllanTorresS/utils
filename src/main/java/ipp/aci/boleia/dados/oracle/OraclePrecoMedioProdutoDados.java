package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoMedioProdutoDados;
import ipp.aci.boleia.dominio.PrecoMedioProduto;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Respositorio de entidades PrecoMedioProduto
 */
@Repository
public class OraclePrecoMedioProdutoDados extends OracleRepositorioBoleiaDados<PrecoMedioProduto> implements IPrecoMedioProdutoDados {

    private static final String CONSULTA_ULTIMO_PRECO_MEDIO = "SELECT pmp FROM PrecoMedioProduto pmp INNER JOIN pmp.produto prod WHERE trunc(pmp.dataAtualizacao) = trunc(:dataMesAnterior) AND prod.id = :idProduto AND ROWNUM = 1";

    /**
     * Instancia o repositorio
     */
    public OraclePrecoMedioProdutoDados() {
        super(PrecoMedioProduto.class);
    }

    @Override
    public PrecoMedioProduto pesquisarUltimoPorProduto(Long idProduto) {
        Date dataMesAnterior = UtilitarioCalculoData.adicionarMesesData(new Date(), -1);
        return pesquisarUnico(CONSULTA_ULTIMO_PRECO_MEDIO, new ParametroPesquisaIgual("idProduto", idProduto), new ParametroPesquisaIgual("dataMesAnterior", dataMesAnterior));
    }
}
