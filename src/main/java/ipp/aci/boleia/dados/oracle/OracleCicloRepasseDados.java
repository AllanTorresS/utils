package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICicloRepasseDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoCobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCicloRepasseVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repositório de entidades CicloRepasse
 */
@Repository
public class OracleCicloRepasseDados extends OracleRepositorioBoleiaDados<CicloRepasse> implements ICicloRepasseDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Construtor padrão
     */
    public OracleCicloRepasseDados(){super(CicloRepasse.class);}

    @Override
    public CicloRepasse obterCicloRepassePorDataEEntidade(Date data, Long idEntidadeRepasse, Long idPtov){
        return pesquisarUnico(new ParametroPesquisaDataMenorOuIgual("dataInicio",data),
                              new ParametroPesquisaDataMaiorOuIgual("dataFim",data),
                              new ParametroPesquisaIgual("configuracaoRepasse.entidadeRepasse.id",idEntidadeRepasse),
                              new ParametroPesquisaIgual("pontoDeVenda.id", idPtov),
                              new ParametroPesquisaNulo("statusIntegracaoJDE"));
    }

    @Override
    public CicloRepasse obterUltimoCicloRepasseAnteriorAData(Date data, Long idEntidadeRepasse, Long idPtov) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataFim", Ordenacao.DECRESCENTE));

        ParametroPesquisa[] parametros = {new ParametroPesquisaDataMenorOuIgual("dataFim",data),
                new ParametroPesquisaIgual("configuracaoRepasse.entidadeRepasse.id",idEntidadeRepasse),
                new ParametroPesquisaIgual("pontoDeVenda.id", idPtov)};

        return pesquisar(paginacao, parametros).getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public List<CicloRepasse> obterCiclosRepasseFechadosNaoEnviados() {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaNulo("numeroDocumento"),
                new ParametroPesquisaDataMenor("dataFim", ambiente.buscarDataAmbiente()),
                new ParametroPesquisaMaior("valorNominalRepasse", BigDecimal.ZERO)
        );
    }

    @Override
    public List<CicloRepasse> buscarCiclosRepasseParaConsultarAvisoDebito(){
        return pesquisar(new ParametroOrdenacaoColuna("dataVencimento", Ordenacao.DECRESCENTE),
            new ParametroPesquisaNulo("numeroDocumento", true),
            new ParametroPesquisaDiferente("status", StatusPagamentoCobranca.PAGO.getValue()));
    }

	@Override
	public ResultadoPaginado<CicloRepasse> pesquisarRepasse(FiltroPesquisaCicloRepasseVo filtro){
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        parametros.add(new ParametroPesquisaDiferente("valorTotal", BigDecimal.ZERO));
        filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataFim", Ordenacao.CRESCENTE));
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public CicloRepasse obterCicloRepassePorAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        return pesquisarUnico(new ParametroPesquisaIgual("autorizacaoPagamentos.id", autorizacaoPagamento.getId()));
    }

    /**
     * Cria uma lista de parametros para a montagem da consulta de repasses a ser exibida
     *
     * @param filtro O filtro recebido
     * @return Uma lista de parametros
     */
    private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaCicloRepasseVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroDataMaiorIgual("dataInicio", UtilitarioCalculoData.obterPrimeiroDiaMes(filtro.getDe()), parametros);
        povoarParametroDataMenorIgual("dataFim", UtilitarioCalculoData.obterUltimoDiaMes(filtro.getAte()), parametros);

        povoarParametroIgual("pontoDeVenda.id", filtro.getPontoDeVenda() != null ? String.valueOf(filtro.getPontoDeVenda().getId()) : null, parametros);

        if (StringUtils.isNotBlank(filtro.getNumeroDocumento())) {
            parametros.add(new ParametroPesquisaLike("numeroDocumento", filtro.getNumeroDocumento()));
        }

        if (filtro.getStatusPagamento() != null && filtro.getStatusPagamento().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusPagamentoCobranca.valueOf(filtro.getStatusPagamento().getName()).getValue()));
        }

        if (filtro.getStatusIntegracao() != null && filtro.getStatusIntegracao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusIntegracaoJDE", StatusIntegracaoJde.valueOf(filtro.getStatusIntegracao().getName()).getValue()));
        }

        return parametros;
    }
}
