package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotivoInativacaoFrotaDados;
import ipp.aci.boleia.dominio.MotivoInativacaoFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio dos motivos de inativacao e ativacao da frota
 */
@Repository
public class OracleMotivoInativacaoFrotaDados extends OracleRepositorioBoleiaDados<MotivoInativacaoFrota> implements IMotivoInativacaoFrotaDados {

    private static final String FROTA = "frota";

    private static final String CONSULTA_MOTIVO_REATIVACAO_ATUAL_POR_FROTA =
            " select m " +
            " from MotivoInativacaoFrota m " +
            "     join m.frota f  " +
            " where " +
            "     f.id = :idFrota  AND " +
            "     f.status = 1 AND " +
            "     m.dataReativacao = " +
            "     ( " +
            "     	  select MAX(mi.dataReativacao) " +
            "         from MotivoInativacaoFrota mi " +
            "    	      join mi.frota fr  " +
            "         where " +
            "    	     fr.id = :idFrota  " +
            "     )";

    /**
     * Instancia o repositorio
     */
    public OracleMotivoInativacaoFrotaDados() {
        super(MotivoInativacaoFrota.class);
    }

    @Override
    public List<MotivoInativacaoFrota> buscarMotivosDaFrota(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIgual(FROTA, idFrota));
    }

    @Override
    public MotivoInativacaoFrota buscarMotivoInativacaoAtualFrota(Long idFrota) {
        return pesquisarUnico(new ParametroPesquisaIgual(FROTA, idFrota), new ParametroPesquisaNulo("dataReativacao"));
    }

    @Override
    public MotivoInativacaoFrota buscarMotivoReativacaoAtualFrota(Long idFrota) {
        return pesquisarUnico(CONSULTA_MOTIVO_REATIVACAO_ATUAL_POR_FROTA, new ParametroPesquisaIgual("idFrota", idFrota));
    }
}