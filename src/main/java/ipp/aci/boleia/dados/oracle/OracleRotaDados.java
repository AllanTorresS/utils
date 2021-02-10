package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRotaDados;
import ipp.aci.boleia.dominio.Rota;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.RestricaoVisibilidadePontoVenda;
import ipp.aci.boleia.dominio.enums.StatusVinculoFrotaPontoVenda;
import ipp.aci.boleia.dominio.enums.TipoPontoRota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Rota
 */
@Repository
public class OracleRotaDados extends OracleRepositorioBoleiaDados<Rota> implements IRotaDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    private static final String PRINCIPAL_VALUE = "1";
    
    private static final String COUNT_PVS      =
            " (SELECT " +
                    "COUNT(ponto) " +
            "FROM PontoRota ponto " +
            "JOIN ponto.rota rota " +
            "JOIN rota.frota frota " +
            "JOIN ponto.pontoVenda pontoVenda " +
            "WHERE " +
                    "pontoVenda IS NOT NULL " +
                    "AND rota.id = r.id " +
                    "AND (pontoVenda.restricaoVisibilidade <> " + RestricaoVisibilidadePontoVenda.VISIVEL_APENAS_PARA_FROTAS_COM_VINCULO_ATIVO.getValue() +
                    " OR (" +
                        "pontoVenda.restricaoVisibilidade = " + RestricaoVisibilidadePontoVenda.VISIVEL_APENAS_PARA_FROTAS_COM_VINCULO_ATIVO.getValue() +
                        " AND EXISTS (SELECT 1 FROM FrotaPontoVenda negociacoes " +
                        "             WHERE negociacoes.frota.id = frota.id " +
                        "                   AND negociacoes.pontoVenda.id = pontoVenda.id " +
                        "                   AND negociacoes.statusVinculo = " + StatusVinculoFrotaPontoVenda.ATIVO.getValue() + ")" +
                    ")))";
    
    private static final String COUNT_ROTAS_COM_PV = 
            " SELECT " +
            "     COUNT(rota) " +
            " FROM PontoRota ponto " + 
            " JOIN ponto.rota rota " + 
            " JOIN ponto.pontoVenda pontoVenda " +
            " JOIN rota.frota frota " +
            " WHERE " +
            "     pontoVenda.id = :idPontoVenda " +
            "     AND frota.id = :idFrota";

    private static final String NOME_ORIGEM_DESTINO = " CONCAT( " +
            "       (SELECT ponto.nome FROM PontoRota ponto JOIN ponto.rota rota WHERE ponto.tipo = "+TipoPontoRota.ORIGEM.getValue()+" AND rota.id = r.id AND ROWNUM <= 1)," +
            "       ' - ', " +
            "       (SELECT ponto.nome FROM PontoRota ponto JOIN ponto.rota rota WHERE ponto.tipo = "+TipoPontoRota.DESTINO.getValue()+" AND rota.id = r.id AND ROWNUM <= 1) " +
            ") ";

    private static final String CONSULTA_ROTAS =
            " SELECT " +
                    "    r, " + COUNT_PVS + " AS QTD_PVS, " + NOME_ORIGEM_DESTINO + " AS NOME_ORIGEM_DESTINO " +
                    " FROM Rota r " +
                    " JOIN r.frota f " +
                    " WHERE " +
                    "    EXISTS ( " +
                    "       SELECT 1 " +
                    "       FROM " +
                    "           PontoRota p1  " +
                    "           JOIN p1.rota r1  " +
                    "       WHERE " +
                    "           (:nomeDestino IS NULL OR (p1.tipo = " + TipoPontoRota.DESTINO.getValue() + " AND LOWER(" + removerAcentosCampo("p1.nome") + ") like :nomeDestino))" +
                    "           AND r1.id = r.id " +
                    "    ) " +
                    "    AND EXISTS ( " +
                    "       SELECT 1 " +
                    "       FROM " +
                    "           PontoRota p2  " +
                    "           JOIN p2.rota r2  " +
                    "       WHERE " +
                    "           (:nomeOrigem IS NULL OR (p2.tipo = " + TipoPontoRota.ORIGEM.getValue() + " AND LOWER(" + removerAcentosCampo("p2.nome") + ") like :nomeOrigem))" +
                    "           AND r2.id = r.id " +
                    "    ) " +
                    "    AND EXISTS ( " +
                    "       SELECT 1 " +
                    "       FROM " +
                    "           PontoRota p3  " +
                    "           JOIN p3.rota r3  " +
                    "           LEFT JOIN p3.pontoVenda pv3  " +
                    "       WHERE " +
                    "           (:idPontoVenda IS NULL OR pv3.id = :idPontoVenda) " +
                    "           AND r3.id = r.id " +
                    "    ) " +
                    "    AND (:quantidadePvs IS NULL OR " + COUNT_PVS + " > 0) " +
                    "    AND (:nome IS NULL OR LOWER(" + removerAcentosCampo("r.nome") + ") like :nome) " +
                    "    AND (r.excluido = 0) " +
                    "    AND ((:possuiListaFrota = false AND (:idFrota IS NULL OR f.id = :idFrota)) OR (:possuiListaFrota = true AND f.id in (:idFrotasAssociadas))) " +
                    "    AND r.planoViagem IS NULL " +
                    "    %s %s ";


    private static final String ORDER_BY_NOME           = " ORDER BY LOWER(" + removerAcentosCampo("r.nome") + ") ";
    private static final String ORDER_BY_DISTANCIA      = " ORDER BY r.distancia ";
    private static final String ORDER_BY_POSTOS         = " ORDER BY QTD_PVS ";
    private static final String ORDER_BY_TEMPO          = " ORDER BY r.tempo ";
    private static final String ORDER_BY_ORIGEM_DESTINO = " ORDER BY NOME_ORIGEM_DESTINO ";

    private static final String PLANO_VIAGEM_NULL = "AND r.planoViagem IS NULL";
    private static final String PLANO_VIAGEM_EXISTS = "AND r.planoViagem IS NOT NULL AND r.principal = " + PRINCIPAL_VALUE;

    /**
     * Construtor
     */
    public OracleRotaDados() {
        super(Rota.class);
    }

    @Override
    public ResultadoPaginado<Rota> pesquisar(FiltroPesquisaRotaVo filtro) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaIgual("quantidadePvs", filtro.getPossuiPostos() != null && filtro.getPossuiPostos() ? 0 : null));
        params.add(new ParametroPesquisaLike("nomeDestino", StringUtils.isNotBlank(filtro.getDestino()) ? filtro.getDestino() : null));
        params.add(new ParametroPesquisaLike("nomeOrigem", StringUtils.isNotBlank(filtro.getOrigem()) ? filtro.getOrigem() : null));
        params.add(new ParametroPesquisaLike("nome", StringUtils.isNotBlank(filtro.getNome()) ? filtro.getNome() : null));
        params.add(new ParametroPesquisaIgual("idPontoVenda", filtro.getPontoVenda() != null ? filtro.getPontoVenda().getId() : null));
        Usuario usuario = ambiente.getUsuarioLogado();
        if(usuario.getTipoPerfil().isInterno() && !usuario.possuiFrotasAssociadas()){
            params.add(new ParametroPesquisaDiferente("idFrota", null));
            params.add(new ParametroPesquisaIn("idFrotasAssociadas",null));
            params.add(new ParametroPesquisaIgual("possuiListaFrota", false));
        }
        if(usuario.getTipoPerfil().isFrotista()){
            params.add(new ParametroPesquisaIgual("idFrota", usuario.getFrota().getId()));
            params.add(new ParametroPesquisaIgual("idFrotasAssociadas", null));
            params.add(new ParametroPesquisaIgual("possuiListaFrota", false));
        } else if(usuario.getTipoPerfil().isInterno() && usuario.possuiFrotasAssociadas()){
            params.add(new ParametroPesquisaIn("idFrotasAssociadas",usuario.listarIdsFrotasAssociadas()));
            params.add(new ParametroPesquisaIgual("idFrota", null));
            params.add(new ParametroPesquisaIgual("possuiListaFrota", true));
        }

        ResultadoPaginado<Object[]> resultadoBruto = pesquisar(filtro.getPaginacao(), montarClausulaOrdenacaoDinamica(filtro), Object[].class, params.toArray(new ParametroPesquisa[params.size()]));
        return mapearResultadoPesquisa(resultadoBruto);
    }

    @Override
    public Rota buscarPorNome(String nome, Long idFrota) {
        return pesquisarUnico(new ParametroPesquisaIgualIgnoreCase("nome", nome), new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public List<Rota> pesquisarPorNomeFrota(String nome, Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("nome"), new ParametroPesquisaIgualIgnoreCase("nome", nome), new ParametroPesquisaIgual("frota.id", idFrota));
    }

    /**
     * Altera a consulta, adicionando a clausula de ordenacao de acordo com o filtro recebido
     *
     * @param filtro O filtro da consulta
     * @return A consulta com a clausula de ordenacao
     */
    private String montarClausulaOrdenacaoDinamica(FiltroPesquisaRotaVo filtro) {

        String orderBy = "";
        String orderDirection = "";

        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            switch (parametro.getNome()) {
                case "nome":
                    orderBy = ORDER_BY_NOME;
                    break;
                case "distancia":
                    orderBy = ORDER_BY_DISTANCIA;
                    break;
                case "quantidadePostos":
                    orderBy = ORDER_BY_POSTOS;
                    break;
                case "tempo":
                    orderBy = ORDER_BY_TEMPO;
                    break;
                case "origemEDestino":
                    orderBy = ORDER_BY_ORIGEM_DESTINO;
                    break;
                default:
                    orderBy = "";
                    break;
            }
            orderDirection = (parametro.isDecrescente() ? " DESC" : " ASC");
        }

        if (filtro.getRotaInteligente()!=null && filtro.getRotaInteligente()){
            return String.format(CONSULTA_ROTAS.replace(PLANO_VIAGEM_NULL, PLANO_VIAGEM_EXISTS), orderBy, orderDirection);
        }

        return String.format(CONSULTA_ROTAS, orderBy, orderDirection);
    }

    /**
     * Converte o resultado da consulta para um resultado paginado de rotas
     *
     * @param resultadoBruto O resultado bruto da consulta
     * @return O resultado mapeado
     */
    private ResultadoPaginado<Rota> mapearResultadoPesquisa(ResultadoPaginado<Object[]> resultadoBruto) {
        List<Rota> rotas = new ArrayList<>(resultadoBruto.getRegistros().size());
        resultadoBruto.getRegistros().forEach(array -> {
            Rota rota = (Rota) array[0];
            rota.setQuantidadePostos((long) array[1]);
            rotas.add(rota);
        });
        return new ResultadoPaginado<>(rotas, resultadoBruto.getTotalItems());
    }
    
    @Override
    public Long obterQuantidadeDeRotasQueContemPontoDeVenda(long idFrota, long idPontoDeVenda){
        Long result = pesquisarUnicoSemIsolamentoDados(
                COUNT_ROTAS_COM_PV
                , new ParametroPesquisaIgual("idFrota", idFrota)
                , new ParametroPesquisaIgual("idPontoVenda", idPontoDeVenda)
        );
        if (result == null) {
            return 0L;
        }
        return result;
    }
}
