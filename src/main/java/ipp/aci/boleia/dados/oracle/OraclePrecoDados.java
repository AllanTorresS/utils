package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusPreco;
import ipp.aci.boleia.dominio.enums.StatusPrecoNegociacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;

import ipp.aci.boleia.util.seguranca.UtilitarioIsolamentoInformacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades
 * Preco
 */
@Repository
public class OraclePrecoDados extends OracleOrdenacaoPrecosDados<Preco> implements IPrecoDados {

    private static final String CONSULTA_NEGOCIACOES =
    " SELECT p " +
            " FROM Preco p " +
            "     JOIN p.precoBase pb " +
            "     JOIN pb.precoMicromercado pm " +
            "     JOIN pm.tipoCombustivel tc " +
            "     JOIN p.frotaPtov fptov " +
            "     JOIN fptov.frota f " +
            "     JOIN fptov.pontoVenda pv " +
            " WHERE " +
            "     tc.id = :idCombustivel " +
            "     AND pv.id = :idPontoVenda " +
            "     AND (f.id = :idFrota OR :idFrota IS NULL) " +
            "     AND p.status IN :statusValidos " +
            "     AND (p.dataVigencia <= :dataAbastecimento OR (p.dataAtualizacao <= :dataAbastecimento AND p.dataVigencia IS NULL)) " +            
            "     AND p.status IN :statusValidos  " +
            "     ORDER BY  " +
            "     (CASE WHEN p.dataVigencia IS NULL THEN 0 ELSE 1 END) DESC,  " +
            "     p.dataVigencia DESC , p.dataAtualizacao DESC ";

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositório
     */
    public OraclePrecoDados() {
        super(Preco.class);
    }

    @Override
    public ResultadoPaginado<Preco> pesquisaPrecoPaginada(FiltroPesquisaPrecoVo filtro, Boolean acordo, Integer... statusPossiveis) {

        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro, acordo, statusPossiveis);

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Preco> pesquisaPrecoPaginadaValidacaoSegregacao(FiltroPesquisaPrecoVo filtro, Boolean acordo, Usuario usuarioLogado, Integer... statusPossiveis) {

        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro, acordo, statusPossiveis);

        if(UtilitarioIsolamentoInformacoes.isUsuarioInternoAssessorOuCoordenador(usuarioLogado)){
            return pesquisarSemIsolamentoDados(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }else {
            return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }
    }

    @Override
    public Preco obterAtualPorFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);

        List<Integer> statusValidos = new ArrayList<>();
        statusValidos.add(StatusPreco.VIGENTE.getValue());
        statusValidos.add(StatusPreco.ACEITO.getValue());
        statusValidos.add(StatusPreco.PENDENTE.getValue());
        statusValidos.add(StatusPreco.NOVO.getValue());

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idCombustivel", idTipoCombustivel));
        parametros.add(new ParametroPesquisaIgual("idPontoVenda", idPontoVenda));
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAbastecimento", ambiente.buscarDataAmbiente()));
        parametros.add(new ParametroPesquisaIn("statusValidos", statusValidos));

        List<Preco> precosAcordo = pesquisar(paginacao, CONSULTA_NEGOCIACOES, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        return precosAcordo.stream().findFirst().orElse(null);
    }

    @Override
    public Preco obterPorDataFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel, Date dataAbastecimento) {
        
        List<Integer> statusValidos = new ArrayList<>();
        statusValidos.add(StatusPreco.VIGENTE.getValue());
        statusValidos.add(StatusPreco.ACEITO.getValue());
        statusValidos.add(StatusPreco.HISTORICO.getValue());
        statusValidos.add(StatusPreco.PENDENTE.getValue());
        statusValidos.add(StatusPreco.NOVO.getValue());

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idCombustivel", idTipoCombustivel));
        parametros.add(new ParametroPesquisaIgual("idPontoVenda", idPontoVenda));
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAbastecimento", dataAbastecimento));
        parametros.add(new ParametroPesquisaIn("statusValidos", statusValidos));

        List<Preco> precosAcordo = pesquisar(null, CONSULTA_NEGOCIACOES, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        return precosAcordo.stream().findFirst().orElse(null);
    }

    @Override
    public List<Preco> obterParaVigenciaAutomatica(Date dataCorte) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIn("status", Arrays.asList(StatusPreco.NOVO.getValue(), StatusPreco.PENDENTE.getValue())),
                new ParametroPesquisaDataMenorOuIgual("dataSolicitacao", dataCorte),
                new ParametroPesquisaNulo("dataVigencia"));     
    }

    @Override
    public List<Preco> obterAgendamentosParaVigenciaAutomatica() {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIn("status", Arrays.asList(StatusPreco.NOVO.getValue(), StatusPreco.PENDENTE.getValue())),
                new ParametroPesquisaDataMenorOuIgual("dataVigencia", ambiente.buscarDataAmbiente()));    
    }

    @Override
    public List<Preco> obterPrecos(Frota frota, PontoDeVenda posto, TipoCombustivel tipoCombustivel, List<StatusPreco> status) {
        List<Integer> statusValues = status.stream().map(StatusPreco::getValue).collect(Collectors.toList());
        return pesquisar(
                new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIgual("precoBase.precoMicromercado.tipoCombustivel.id", tipoCombustivel.getId()),
                new ParametroPesquisaIgual("frotaPtov.pontoVenda.id", posto.getId()),
                new ParametroPesquisaIgual("frotaPtov.frota.id", frota.getId()),
                new ParametroPesquisaIn("status", statusValues)
        );
    }

    @Override
    public List<Preco> buscarPrecosAtuais(Long idPontoVenda, Long idTipoCombustivel) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        List<Integer> statusValidos = new ArrayList<>();
        statusValidos.add(StatusPreco.VIGENTE.getValue());
        statusValidos.add(StatusPreco.ACEITO.getValue());
        statusValidos.add(StatusPreco.PENDENTE.getValue());
        statusValidos.add(StatusPreco.NOVO.getValue());

        parametros.add(new ParametroPesquisaIgual("idCombustivel", idTipoCombustivel));
        parametros.add(new ParametroPesquisaIgual("idPontoVenda", idPontoVenda));
        parametros.add(new ParametroPesquisaIgual("idFrota", null));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAbastecimento", ambiente.buscarDataAmbiente()));
        parametros.add(new ParametroPesquisaIn("statusValidos", statusValidos));

        return pesquisarSemIsolamentoDados(null, CONSULTA_NEGOCIACOES, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }



    /**
     * Monta os parametros de pesquisa a partir do filtro de busca recebido
     *
     * @param filtro O filtro de busca
     * @param acordo Informa se será considerado os acordos na pesquisa
     * @param statusPossiveis status possiveis
     * @return Uma lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaPrecoVo filtro, Boolean acordo, Integer... statusPossiveis) {

        List<ParametroPesquisa> parametros = new ArrayList<>();
        if(filtro.getId() != null){
            parametros.add(new ParametroPesquisaIgual("id", filtro.getId()));
        } else {
            if (filtro.getFrota() != null && filtro.getFrota().getId() != null) {
                parametros.add(new ParametroPesquisaIgual("frotaPtov.frota", filtro.getFrota().getId()));
            }
            if (filtro.getPontoDeVenda() != null && filtro.getPontoDeVenda().getId() != null) {
                parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda", filtro.getPontoDeVenda().getId()));
            }
            if (filtro.getTipoCombustivel() != null && filtro.getTipoCombustivel().getId() != null) {
                parametros.add(new ParametroPesquisaIgual("precoBase.precoMicromercado.tipoCombustivel", filtro.getTipoCombustivel().getId()));
            }
            montarParametroStatus(filtro, parametros, statusPossiveis);
            if (filtro.getUfPontoDeVenda() != null) {
                parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda.uf", filtro.getUfPontoDeVenda().getName()));
            }
            if (filtro.getMunicipioPontoDeVenda() != null) {
                parametros.add(new ParametroPesquisaLike("frotaPtov.pontoVenda.municipio", filtro.getMunicipioPontoDeVenda()));
            }
            if (filtro.getNomeRede() != null) {
                parametros.add(new ParametroPesquisaLike("frotaPtov.pontoVenda.rede.nomeRede", filtro.getNomeRede()));
            }
            if (filtro.getDataAtualizacao() != null) {
                parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaAnd(new ParametroPesquisaDataMaiorOuIgual("dataAtualizacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataAtualizacao())), new ParametroPesquisaDataMenorOuIgual("dataAtualizacao", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataAtualizacao())), new ParametroPesquisaNulo("dataVigencia")),
                    new ParametroPesquisaAnd(new ParametroPesquisaDataMaiorOuIgual("dataVigencia", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataAtualizacao())), new ParametroPesquisaDataMenorOuIgual("dataVigencia", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataAtualizacao()))))
                );
            }
        }
        montarParametroOrdenacaoPreco(filtro.getPaginacao(), parametros);

        if(acordo) {
            parametros.add(new ParametroPesquisaOr(new ParametroPesquisaNulo("descontoVigente", true), new ParametroPesquisaNulo("descontoSolicitado", true)));
        }

        return parametros;
    }

    @Override
    public Preco obterAgendamentoPorFrotaPvCombustivelDataVigencia(Long idFrota, Long idPosto, Long idTipoCombustivel, Date dataVigencia){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda.id", idPosto));
        parametros.add(new ParametroPesquisaIgual("frotaPtov.frota.id", idFrota));
        parametros.add(new ParametroPesquisaIgual("precoBase.precoMicromercado.tipoCombustivel.id", idTipoCombustivel));
        parametros.add(new ParametroPesquisaDiferente("status", StatusPreco.CANCELADO.getValue()));
        parametros.add(new ParametroPesquisaDiferente("status", StatusPreco.REJEITADO.getValue()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataVigencia", dataVigencia));
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataVigencia", dataVigencia));

        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).stream().findFirst().orElse(null);
    }

    private void montarParametroStatus(FiltroPesquisaPrecoVo filtro, List<ParametroPesquisa> parametros, Integer... statusPossiveis) {
        List<Integer> statusVigentes = Arrays.asList(
            StatusPreco.VIGENTE.getValue(), StatusPreco.ACEITO.getValue(), StatusPreco.PENDENTE.getValue(), StatusPreco.NOVO.getValue() 
        );

        if (filtro.getStatus() != null && filtro.getStatus().getLabel() != null && filtro.getStatus().getName() != null) {
            if (filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.AGENDADO.getLabel())) {
                parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaIgual("status", StatusPreco.VIGENTE.getValue()),
                new ParametroPesquisaIgual("status", StatusPreco.ACEITO.getValue())));
                parametros.add(new ParametroPesquisaDataMaior("dataAgendamento", ambiente.buscarDataAmbiente()));
            } else if(filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.AGENDADO_PENDENTE.getLabel())) {
                parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaIgual("status", StatusPreco.PENDENTE.getValue()),
                new ParametroPesquisaIgual("status", StatusPreco.NOVO.getValue())));
                parametros.add(new ParametroPesquisaDataMaior("dataAgendamento", ambiente.buscarDataAmbiente()));
            } else if(filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.NEGOCIACAO.getLabel())) {
                parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaIgual("status", StatusPreco.PENDENTE.getValue()),
                new ParametroPesquisaIgual("status", StatusPreco.NOVO.getValue())));
                parametros.add(new ParametroPesquisaDataMaior("dataVigencia", ambiente.buscarDataAmbiente()));
                parametros.add(new ParametroPesquisaNulo("dataAgendamento"));
            } else if(filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.VIGENTE.getLabel())) {
                parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaNulo("dataVigencia"),
                    new ParametroPesquisaDataMenorOuIgual("dataVigencia", ambiente.buscarDataAmbiente())
                ));
                parametros.add(new ParametroPesquisaIn("status", statusVigentes));
                parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaNulo("dataFim"),
                    new ParametroPesquisaDataMaiorOuIgual("dataFim", ambiente.buscarDataAmbiente())
                ));
            } else if(filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.HISTORICO.getLabel())) {
                parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaIgual("status", StatusPreco.HISTORICO.getValue()),
                    new ParametroPesquisaAnd(
                        new ParametroPesquisaDataMenorOuIgual("dataFim", ambiente.buscarDataAmbiente()),
                        new ParametroPesquisaIn("status", statusVigentes)
                    )
                ));
            } else if(filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.CANCELADO.getLabel()) || 
                filtro.getStatus().getLabel().equals(StatusPrecoNegociacao.REJEITADO.getLabel())) {
                parametros.add(new ParametroPesquisaIn("status", Arrays.asList(statusPossiveis)));
            } else {
                parametros.add(new ParametroPesquisaIn("status", Arrays.asList(statusPossiveis)));
                parametros.add(new ParametroPesquisaOr(new ParametroPesquisaDataMenorOuIgual("dataVigencia", ambiente.buscarDataAmbiente()),
                new ParametroPesquisaNulo("dataVigencia")));
            }
        } else {
            parametros.add(new ParametroPesquisaIn("status", Arrays.asList(statusPossiveis)));
        }
    }


    @Override
    protected String getPrefixoCampoFrotaPontoVenda() {
        return "frotaPtov";
    }

    @Override
    protected String getPrefixoCampoPrecoBase() {
        return "precoBase";
    }
}
