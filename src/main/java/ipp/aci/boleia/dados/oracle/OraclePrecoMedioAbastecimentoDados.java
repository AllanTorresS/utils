package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoMedioAbastecimentoDados;
import ipp.aci.boleia.dominio.PrecoMedioAbastecimento;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Respositorio de entidades PrecoMedioAbastecimento
 */
@Repository
public class OraclePrecoMedioAbastecimentoDados extends OracleRepositorioBoleiaDados<PrecoMedioAbastecimento> implements IPrecoMedioAbastecimentoDados {

    private static final String CONSULTA_ULTIMO_PRECO_MEDIO = "SELECT pma FROM PrecoMedioAbastecimento pma INNER JOIN pma.tipoCombustivel tc WHERE trunc(pma.dataAtualizacao) = trunc(:dataMesAnterior) AND tc.id = :idCombustivel AND ROWNUM = 1";

    /**
     * Instancia o repositorio
     */
    public OraclePrecoMedioAbastecimentoDados() {
        super(PrecoMedioAbastecimento.class);
    }


    @Override
    public PrecoMedioAbastecimento pesquisarUltimoPorCombustivel(Long idCombustivel) {
        Date dataMesAnterior = UtilitarioCalculoData.adicionarMesesData(new Date(), -1);
        return pesquisarUnico(CONSULTA_ULTIMO_PRECO_MEDIO, new ParametroPesquisaIgual("idCombustivel", idCombustivel), new ParametroPesquisaIgual("dataMesAnterior",  dataMesAnterior));
    }
}
