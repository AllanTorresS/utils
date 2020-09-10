package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICobrancaAgenciadorFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.enums.StatusAnexo;
import ipp.aci.boleia.dominio.enums.agenciadorfrete.StatusDocumento;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioCobrancaVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades de Cobrança de Agenciador de Frete
 */
@Repository
public class OracleCobrancaAgenciadorFreteDados extends OracleRepositorioBoleiaDados<AgenciadorFreteCobranca> implements ICobrancaAgenciadorFreteDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleCobrancaAgenciadorFreteDados() {
        super(AgenciadorFreteCobranca.class);
    }

    @Override
    public ResultadoPaginado<AgenciadorFreteCobranca> pesquisar(FiltroRelatorioCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getAte() != null) {
            Date data = UtilitarioCalculoData.obterPrimeiroDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getAte()));
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataCriacao", data));
        }
        if (filtro.getDe()!= null) {
            Date data = UtilitarioCalculoData.obterPrimeiroDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getDe()));
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataCriacao", data));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<AgenciadorFreteCobranca> obterCobrancasSemDocumentos() {
        return pesquisar((InformacaoPaginacao)null,
                new ParametroPesquisaIgual("status", StatusDocumento.NAO_INTEGRADO.getValue()))
                .getRegistros();
    }

    @Override
    public List<AgenciadorFreteCobranca> obterCobrancasPorStatus(StatusDocumento status) {
        return pesquisar((InformacaoPaginacao)null,
                new ParametroPesquisaIgual("status", status.getValue()))
                .getRegistros();
    }
}
