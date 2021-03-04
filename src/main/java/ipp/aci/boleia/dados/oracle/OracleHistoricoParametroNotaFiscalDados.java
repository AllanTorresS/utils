package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoParametroNotaFiscalDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.ParametroNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscal;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoParametroNotaFiscal}.
 */
@Repository
public class OracleHistoricoParametroNotaFiscalDados extends OracleRepositorioBoleiaDados<HistoricoParametroNotaFiscal> implements IHistoricoParametroNotaFiscalDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoParametroNotaFiscalDados() {
        super(HistoricoParametroNotaFiscal.class);
    }

    @Override
    public HistoricoParametroNotaFiscal buscarUltimoParametroPorData(ParametroNotaFiscal parametroNotaFiscal, Date dataLimite) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("parametroNotaFiscal.id", parametroNotaFiscal.getId()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataHistorico", dataLimite));
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, new ParametroOrdenacaoColuna("dataHistorico", Ordenacao.DECRESCENTE));
        ResultadoPaginado<HistoricoParametroNotaFiscal> historicos = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return historicos != null ? historicos.getRegistros().stream().findFirst().orElse(null) : null;
    }
}
