package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteCobrancaDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.enums.agenciadorfrete.StatusDocumento;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenorOuIgual;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioCobrancaVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades de Cobrança de Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteCobrancaDados extends OracleRepositorioBoleiaDados<AgenciadorFreteCobranca> implements IAgenciadorFreteCobrancaDados {

    public static final BigDecimal NUMERO_MAXIMO_TENTATIVAS_ENVIO = BigDecimal.valueOf(4L);

    /**
     * Instancia o repositorio
     *
     */
    public OracleAgenciadorFreteCobrancaDados() {
        super(AgenciadorFreteCobranca.class);
    }

    @Override
    public ResultadoPaginado<AgenciadorFreteCobranca> pesquisar(FiltroRelatorioCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getAte() != null) {
            Date data = UtilitarioCalculoData.obterUltimoDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getAte()));
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
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("documentoJde.status", StatusDocumento.NAO_INTEGRADO.getValue()));
        parametros.add(new ParametroPesquisaMenorOuIgual("documentoJde.numeroTentativasEnvio", NUMERO_MAXIMO_TENTATIVAS_ENVIO));
        return pesquisar((InformacaoPaginacao) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<AgenciadorFreteCobranca> obterCobrancasPorStatus(StatusDocumento status) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("documentoJde.status", status));
        parametros.add(new ParametroPesquisaMenorOuIgual("documentoJde.numeroTentativasEnvio", NUMERO_MAXIMO_TENTATIVAS_ENVIO));
        return pesquisar((InformacaoPaginacao)null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
