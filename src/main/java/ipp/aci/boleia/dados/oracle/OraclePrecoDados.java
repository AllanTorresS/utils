package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.enums.StatusPreco;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

            "     AND (f.id IS NULL OR f.id = :idFrota ) " +
            "     AND (p.dataVigencia <= :dataAbastecimento OR p.dataAtualizacao <= :dataAbastecimento) " +
            "     AND p.status IN :status " +
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
    public Preco obterAtualPorFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idCombustivel", idTipoCombustivel));
        parametros.add(new ParametroPesquisaIgual("idPontoVenda", idPontoVenda));
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAtual", ambiente.buscarDataAmbiente()));
        parametros.add(new ParametroPesquisaIn("status", Arrays.asList(StatusPreco.VIGENTE.getValue(), StatusPreco.ACEITO.getValue())));

        List<Preco> precosAcordo = pesquisar(paginacao, CONSULTA_NEGOCIACOES, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        return precosAcordo.stream().findFirst().orElse(null);
    }

    @Override
    public Preco obterPorDataFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel, Date dataAbastecimento) {
        
        List<Integer> statusValidos = new ArrayList<>();
        statusValidos.add(StatusPreco.VIGENTE.getValue());
        statusValidos.add(StatusPreco.ACEITO.getValue());
        statusValidos.add(StatusPreco.HISTORICO.getValue());

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idCombustivel", idTipoCombustivel));
        parametros.add(new ParametroPesquisaIgual("idPontoVenda", idPontoVenda));
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAbastecimento", dataAbastecimento));
        parametros.add(new ParametroPesquisaIn("status", statusValidos));

        List<Preco> precosAcordo = pesquisar(null, CONSULTA_NEGOCIACOES, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        return precosAcordo.stream().findFirst().orElse(null);
    }

    @Override
    public List<Preco> buscarAcordosNovosOuPendentesParaVigenciaAutomatica(Date dataCorte) {
        return pesquisar((ParametroOrdenacaoColuna)null,
                new ParametroPesquisaIn("status", Arrays.asList(StatusPreco.NOVO.getValue(), StatusPreco.PENDENTE.getValue())),
                new ParametroPesquisaDataMenorOuIgual("dataSolicitacao", dataCorte));
    }

    @Override
    public Preco obterNovoOuPendentePorFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel) {
        List<Preco> precosAcordo = pesquisar(
                new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIgual("precoBase.precoMicromercado.tipoCombustivel.id", idTipoCombustivel),
                new ParametroPesquisaIgual("frotaPtov.pontoVenda.id", idPontoVenda),
                new ParametroPesquisaIgual("frotaPtov.frota.id", idFrota),
                new ParametroPesquisaIn("status", Arrays.asList(StatusPreco.NOVO.getValue(), StatusPreco.PENDENTE.getValue())));
        return precosAcordo.stream().findFirst().orElse(null);
    }

    @Override
    public List<Preco> buscarPrecosAtuais(Long idPontoVenda, Long idTipoCombustivel) {
         List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idCombustivel", idTipoCombustivel));
        parametros.add(new ParametroPesquisaIgual("idPontoVenda", idPontoVenda));
        parametros.add(new ParametroPesquisaIgual("idFrota", null));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAtual", ambiente.buscarDataAmbiente()));
        parametros.add(new ParametroPesquisaIn("status", Arrays.asList(StatusPreco.VIGENTE.getValue(), StatusPreco.ACEITO.getValue())));

        return pesquisar(null, CONSULTA_NEGOCIACOES, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
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
            if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
                parametros.add(new ParametroPesquisaIgual("status", StatusPreco.valueOf(filtro.getStatus().getName()).getValue()));
            } else {
                parametros.add(new ParametroPesquisaIn("status", Arrays.asList(statusPossiveis)));
            }
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
    protected String getPrefixoCampoFrotaPontoVenda() {
        return "frotaPtov";
    }

    @Override
    protected String getPrefixoCampoPrecoBase() {
        return "precoBase";
    }
}
