package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoBaseDados;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAlteracaoPrecoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioLambda;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades
 * Preco Base
 */
@Repository
public class OraclePrecoBaseDados extends OracleOrdenacaoPrecosDados<PrecoBase> implements IPrecoBaseDados {

    /**
     * Instancia o repositorio
     */
    public OraclePrecoBaseDados() {
        super(PrecoBase.class);
    }

    /**
     * Monta os parametros de pesquisa a partir do filtro de busca recebido
     *
     * @param filtro O filtro de busca
     * @param statusPossiveis A lista de status a serem considerados na busca
     * @return Uma lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaAlteracaoPrecoVo filtro, Integer... statusPossiveis) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        if (filtro.getDataVigencia() != null) {
            parametros.add(new ParametroPesquisaAnd(new ParametroPesquisaDataMenorOuIgual("dataAtualizacao", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataVigencia())),
                    new ParametroPesquisaOr(
                            new ParametroPesquisaDataMaior("dataFimVigencia", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataVigencia())),
                            new ParametroPesquisaNulo("dataFimVigencia", false)),
                    new ParametroPesquisaIn("status", Arrays.asList(
                            StatusAlteracaoPrecoPosto.VIGENTE.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITO.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue(),
                            StatusAlteracaoPrecoPosto.EXPIRADO.getValue()))));
        } else{
            if(filtro.getStatus()!=null && filtro.getStatus().getName()!=null) {
                parametros.add(new ParametroPesquisaIgual("status", StatusAlteracaoPrecoPosto.valueOf(filtro.getStatus().getName()).getValue()));
            } else if (statusPossiveis != null){
                parametros.add(new ParametroPesquisaIn("status", Arrays.asList(statusPossiveis)));
            }
        }
        if(filtro.getPontoVenda()!=null && filtro.getPontoVenda().getId()!=null) {
            parametros.add(new ParametroPesquisaIgual("pontoVenda", filtro.getPontoVenda().getId()));
        }
        if(filtro.getProduto()!=null && filtro.getProduto().getId()!=null) {
            parametros.add(new ParametroPesquisaIgual("precoMicromercado.tipoCombustivel", filtro.getProduto().getId()));
        }
        if(filtro.getUfPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIgual("pontoVenda.uf", filtro.getUfPontoDeVenda().getName()));
        }
        if(filtro.getMunicipioPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaLike("pontoVenda.municipio", filtro.getMunicipioPontoDeVenda()));
        }

        if(filtro.getPaginacao() != null && (filtro.getPaginacao().getParametrosOrdenacaoColuna() == null || filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty())) {
            filtro.getPaginacao().setParametrosOrdenacaoColuna(
                    Arrays.asList(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE, true),
                            new ParametroOrdenacaoColuna("pontoVenda.municipio", Ordenacao.CRESCENTE),
                            new ParametroOrdenacaoColuna("pontoVenda.uf", Ordenacao.CRESCENTE),
                            new ParametroOrdenacaoColuna("statusConvertido", Ordenacao.CRESCENTE))
            );
        }

        return parametros;
    }


    @Override
    public List<PrecoBase> listarPrecosPorFrotaLocalizacao(FiltroPesquisaLocalizacaoVo filtro){

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaMaior("pontoVenda.latitude", new BigDecimal(filtro.getLatitudeInicial())));
        parametros.add(new ParametroPesquisaMenor("pontoVenda.latitude", new BigDecimal(filtro.getLatitudeFinal())));

        parametros.add(new ParametroPesquisaMaior("pontoVenda.longitude", new BigDecimal(filtro.getLongitudeInicial())));
        parametros.add(new ParametroPesquisaMenor("pontoVenda.longitude", new BigDecimal(filtro.getLongitudeFinal())));

        parametros.add(new ParametroPesquisaIgual("pontoVenda.status", StatusAtivacao.ATIVO.getValue()));

        parametros.add(new ParametroPesquisaIgual("pontoVenda.statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));

        parametros.add(new ParametroPesquisaIn("status", Arrays.asList(StatusAlteracaoPrecoPosto.VIGENTE.getValue(),
                StatusAlteracaoPrecoPosto.ACEITO.getValue(),
                StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue(),
                StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue())));
        parametros.add(new ParametroPesquisaNulo("preco", true));

        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("id");

        return pesquisar(ordenacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<PrecoBase> pesquisaPaginada(FiltroPesquisaAlteracaoPrecoVo filtro, Integer... statusPossiveis) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro, statusPossiveis);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PrecoBase> buscarPrecosNovosParaVigenciaAutomatica(Date dataAtualizacao) {
        return pesquisar((InformacaoPaginacao) null,
                new ParametroPesquisaDataMenorOuIgual("dataAtualizacao", dataAtualizacao),
                new ParametroPesquisaIn("status", Arrays.asList(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue(), StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue())))
            .getRegistros();
    }

    @Override
    public List<PrecoBase> buscarPrecosVigentes(Long idPtov, Long idTipoCombustivel) {
        List<Integer> statusVigentes = new ArrayList<>();
        statusVigentes.add(StatusAlteracaoPrecoPosto.VIGENTE.getValue());
        statusVigentes.add(StatusAlteracaoPrecoPosto.ACEITO.getValue());
        statusVigentes.add(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue());
        statusVigentes.add(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue());
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("pontoVenda.id",idPtov), new ParametroPesquisaIgual("precoMicromercado.tipoCombustivel.id",idTipoCombustivel), new ParametroPesquisaIn("status",statusVigentes));
    }

    @Override
    public PrecoBase buscarPrecosPorData(Long idPtov, Long idTipoCombustivel, Date dataAbastecimento) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.setParametrosOrdenacaoColuna(
                Arrays.asList(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE), new ParametroOrdenacaoColuna("status", Ordenacao.CRESCENTE))
        );

        List<Integer> statusValidos = new ArrayList<>();
        statusValidos.add(StatusAlteracaoPrecoPosto.VIGENTE.getValue());
        statusValidos.add(StatusAlteracaoPrecoPosto.EXPIRADO.getValue());

        ResultadoPaginado<PrecoBase>  result =  pesquisar(paginacao,
                new ParametroPesquisaIgual("pontoVenda.id",idPtov),
                new ParametroPesquisaIgual("precoMicromercado.tipoCombustivel.id",idTipoCombustivel),
                new ParametroPesquisaDataMenorOuIgual("dataAtualizacao", dataAbastecimento),
                new ParametroPesquisaIn("status",statusValidos));
        return result.getRegistros().isEmpty() ? null : UtilitarioLambda.obterPrimeiroObjetoDaLista(result.getRegistros());
    }

    @Override
    public List<PrecoBase> buscarPendentes(List<Long> idsPv) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIn("pontoVenda.id",idsPv), new ParametroPesquisaIn("status", Arrays.asList(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue())));
    }

    @Override
    public List<PrecoBase> buscarPrecosAtuais() {
        return pesquisar((ParametroOrdenacaoColuna) null,
            new ParametroPesquisaFetch("precoMicromercado.micromercado"),
            new ParametroPesquisaFetch("precoMicromercado.tipoCombustivel"),
            new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.EXPIRADO.getValue()),
            new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.RECUSADO.getValue()));
    }

    @Override
    public List<PrecoBase> buscarPrecosAtuaisPorPV(Long idPV) {
        return pesquisar(new ParametroOrdenacaoColuna("status"), new ParametroPesquisaIgual("pontoVenda.id",idPV),
                new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.EXPIRADO.getValue()),
                new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.RECUSADO.getValue()));
    }

    @Override
    public List<PrecoBase> buscarPrecosAtuais(Long idPtov, Long idTipoCombustivel) {
        return pesquisar(new ParametroOrdenacaoColuna("status"), new ParametroPesquisaIgual("pontoVenda.id",idPtov),
                new ParametroPesquisaIgual("precoMicromercado.tipoCombustivel.id",idTipoCombustivel),
                new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.EXPIRADO.getValue()),
                new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.RECUSADO.getValue()));
    }

    @Override
    public List<PrecoBase> buscarPrecosAgendadosPorData(Date data) {

        List<Integer> statusPesquisar = new ArrayList<>();
        statusPesquisar.add(StatusAlteracaoPrecoPosto.AGENDADO_COM_ACEITE_REVENDA.getValue());
        statusPesquisar.add(StatusAlteracaoPrecoPosto.AGENDADO_SEM_ACEITE_REVENDA.getValue());


        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaDataMaiorOuIgual("dataAgendamento", UtilitarioCalculoData.obterPrimeiroInstanteDia(data)),
                new ParametroPesquisaDataMenorOuIgual("dataAgendamento", UtilitarioCalculoData.obterUltimoInstanteDia(data)),
                new ParametroPesquisaIn("status", statusPesquisar));
    }

    @Override
    public List<PrecoBase> buscarPrecosPorPontoDeVendaCombustivelSemAgendamento(Long idPtov, Long idCombustivel) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaFetch("precoMicromercado"),
                new ParametroPesquisaFetch("precoMicromercado.tipoCombustivel"),
                new ParametroPesquisaIgual("pontoVenda.id",idPtov),
                new ParametroPesquisaIgual("precoMicromercado.tipoCombustivel.id",idCombustivel),
                new ParametroPesquisaNulo("dataAgendamento"),
                new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.AGENDADO_COM_ACEITE_REVENDA.getValue()),
                new ParametroPesquisaDiferente("status", StatusAlteracaoPrecoPosto.AGENDADO_SEM_ACEITE_REVENDA.getValue())
        );
    }


    @Override
    protected String getPrefixoCampoFrotaPontoVenda() {
        return null;
    }

    @Override
    protected String getPrefixoCampoPrecoBase() {
        return null;
    }

}
