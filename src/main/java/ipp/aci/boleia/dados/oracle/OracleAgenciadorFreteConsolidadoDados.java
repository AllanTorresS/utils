package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteConsolidadoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.ConstantesNdd;
import ipp.aci.boleia.util.UtilitarioConsolidado;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades Consolidado do Agenciador de frete
 */

@Repository
public class OracleAgenciadorFreteConsolidadoDados extends OracleRepositorioBoleiaDados<Consolidado> implements IAgenciadorFreteConsolidadoDados {

    /**
     * Instancia o repositorio
     */
    public OracleAgenciadorFreteConsolidadoDados() {
        super(Consolidado.class);
    }

    @Override
    public Consolidado obterPorTransacao(Transacao transacao) {
        return pesquisarUnico(new ParametroPesquisaIgual("agenciadorFrete.id", transacao.getMotorista().getAgenciadorFrete().getId()),
                new ParametroPesquisaIgual("posto.id", transacao.getPosto().getId()),
                new ParametroPesquisaIgual("dataInicioPeriodo", UtilitarioConsolidado.calcularDataInicio(transacao.getDataCriacao(), ConstantesNdd.DIAS_CICLO)),
                new ParametroPesquisaIgual("dataFimPeriodo", UtilitarioConsolidado.calcularDataFim(transacao.getDataCriacao(), ConstantesNdd.DIAS_CICLO)));

    }
}