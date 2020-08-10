package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteConsolidadoDados;
import ipp.aci.boleia.dados.IAmbienteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.util.ConstantesNdd;
import ipp.aci.boleia.util.UtilitarioConsolidado;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Consolidado do Agenciador de frete
 */

@Repository
public class OracleAgenciadorFreteConsolidadoDados extends OracleRepositorioBoleiaDados<Consolidado> implements IAgenciadorFreteConsolidadoDados {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

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

    @Override
    public List<Consolidado> obterAbertosParaCobranca() {
        return pesquisar(new ParametroOrdenacaoColuna(),
                new ParametroPesquisaNulo("cobranca"),
                new ParametroPesquisaDataMenor("dataFimPeriodo", utilitarioAmbiente.buscarDataAmbiente()));
    }

    @Override
    public List<Consolidado> obterAbertosParaReembolsos() {
        return pesquisar(new ParametroOrdenacaoColuna(),
                new ParametroPesquisaNulo("reembolso"),
                new ParametroPesquisaDataMenor("dataFimPeriodo", utilitarioAmbiente.buscarDataAmbiente()));

    }


}