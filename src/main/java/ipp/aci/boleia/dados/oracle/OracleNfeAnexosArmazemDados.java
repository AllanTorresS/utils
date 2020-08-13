package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.INfeAnexosArmazemDados;
import ipp.aci.boleia.dominio.NfeAnexosArmazem;
import ipp.aci.boleia.dominio.enums.StatusAnexo;
import ipp.aci.boleia.dominio.enums.StatusConciliacaoNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositório de entidades NfeAnexosArmazem
 */
@Repository
public class OracleNfeAnexosArmazemDados extends OracleRepositorioBoleiaDados<NfeAnexosArmazem> implements INfeAnexosArmazemDados {

    /**
     * Instancia o repositorio
     */
    public OracleNfeAnexosArmazemDados() {
        super(NfeAnexosArmazem.class);
    }

    @Override
    public List<NfeAnexosArmazem> buscarAnexosProcessadosParaImportacao() {
        return pesquisar((InformacaoPaginacao)null,
                new ParametroPesquisaIgual("status", StatusAnexo.PROCESSADO.getValue()),
                new ParametroPesquisaFetch("arquivo"))
                .getRegistros();
    }

    @Override
    public List<NfeAnexosArmazem> buscarAnexosPendentesDeEmissao() {
        final BigDecimal limiteTentativas = new BigDecimal(5);
        return pesquisar((InformacaoPaginacao)null,
                new ParametroPesquisaOr(
                    new ParametroPesquisaIgual("ciclo.statusNotaFiscal", StatusNotaFiscal.PENDENTE.getValue()),
                    new ParametroPesquisaIgual("ciclo.statusNotaFiscal", StatusNotaFiscal.ATRASADA.getValue())
                ),
                new ParametroPesquisaMenor("numeroTentativasAtualizacao", limiteTentativas),
                new ParametroPesquisaFetch("ciclo"))
                .getRegistros();
    }

    @Override
    public List<NfeAnexosArmazem> buscarAnexosImportadosParaConciliacao() {
        return pesquisar((InformacaoPaginacao) null,
                new ParametroPesquisaIgual("status", StatusAnexo.IMPORTADO.getValue()),
                new ParametroPesquisaIgual("notaFiscal.statusConciliacao", StatusConciliacaoNotaFiscal.IMPORTADO.getValue()),
                new ParametroPesquisaFetch("notaFiscal"),
                new ParametroPesquisaFetch("notaFiscal.autorizacoesPagamento"))
                .getRegistros();
    }

    @Override
    public NfeAnexosArmazem obterPorNotaFiscal(Long idNota) {
        return pesquisarUnico(new ParametroPesquisaIgual("notaFiscal.id", idNota));
    }
}
