package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteReembolsoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteReembolso;
import ipp.aci.boleia.dominio.enums.agenciadorfrete.StatusDocumento;
import ipp.aci.boleia.dominio.enums.agenciadorfrete.TipoReembolso;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenorOuIgual;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoAgenciadorFreteVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteReembolsoDados extends OracleRepositorioBoleiaDados<AgenciadorFreteReembolso> implements IAgenciadorFreteReembolsoDados {

    public static final BigDecimal NUMERO_MAXIMO_TENTATIVAS_ENVIO = BigDecimal.valueOf(4L);
    /**
     * Instancia o repositorio
     *
     */
    public OracleAgenciadorFreteReembolsoDados() {
        super(AgenciadorFreteReembolso.class);
    }

    @Override
    public ResultadoPaginado<AgenciadorFreteReembolso> pesquisar(FiltroPesquisaReembolsoAgenciadorFreteVo filtro) {
        List<ParametroPesquisa> parametros = montarParametrosPesquisa(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Monta a lista com os parametros de pesquisa que serão aplicados na consulta.
     *
     * @param filtro Filtro utilizado pelo usuário.
     * @return Lista com os parametros de pesquisa.
     */
    private List<ParametroPesquisa> montarParametrosPesquisa(FiltroPesquisaReembolsoAgenciadorFreteVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if(filtro.getIdsPontoVenda() != null){
            parametros.add(new ParametroPesquisaIn("consolidados.posto.id", filtro.getIdsPontoVenda()));
        }

        if (filtro.getDe()!= null) {
            Date data = UtilitarioFormatacaoData.lerDataIso8601(filtro.getDe());
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataCriacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(data)));
        }

        if (filtro.getAte() != null) {
            Date data = UtilitarioFormatacaoData.lerDataIso8601(filtro.getAte());
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataCriacao", UtilitarioCalculoData.obterUltimoInstanteDia(data)));
        }

        montarParametroPesquisaTipoEntidade(filtro.getTipoEntidade(), parametros);
        return parametros;
    }

    /**
     * Inclui na lista de parametros o parametro de pesquisa por tipo de entidade.
     *
     * @param filtroTipoEntidade Filtro de tipo de entidade.
     * @param parametros Lista de parametros.
     */
    private void montarParametroPesquisaTipoEntidade(EnumVo filtroTipoEntidade, List<ParametroPesquisa> parametros) {
        TipoReembolso tipoEntidade = null;
        if(filtroTipoEntidade != null && filtroTipoEntidade.getName() != null) {
            tipoEntidade = TipoReembolso.valueOf(filtroTipoEntidade.getName());
        }
        parametros.add(new ParametroPesquisaIgual("tipo", tipoEntidade != null ? tipoEntidade.getValue() : null));
    }


    @Override
    public List<AgenciadorFreteReembolso> obterReembolsosSemDocumentos() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("documentoJde.status", StatusDocumento.NAO_INTEGRADO.getValue()));
        parametros.add(new ParametroPesquisaMenorOuIgual("documentoJde.numeroTentativasEnvio", NUMERO_MAXIMO_TENTATIVAS_ENVIO));
        return pesquisar((InformacaoPaginacao) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<AgenciadorFreteReembolso> obterReembolsosPorStatus(StatusDocumento status) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("documentoJde.status", status.getValue()));
        parametros.add(new ParametroPesquisaMenorOuIgual("documentoJde.numeroTentativasEnvio", NUMERO_MAXIMO_TENTATIVAS_ENVIO));
        return pesquisar((InformacaoPaginacao)null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
