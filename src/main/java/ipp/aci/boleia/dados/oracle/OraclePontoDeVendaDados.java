package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dominio.AtividadeComponente;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.PerfilPontoDeVenda;
import ipp.aci.boleia.dominio.enums.RestricaoVisibilidadePontoVenda;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.enums.StatusPermissaoPreco;
import ipp.aci.boleia.dominio.enums.StatusPosse;
import ipp.aci.boleia.dominio.enums.StatusVinculoFrotaPontoVenda;
import ipp.aci.boleia.dominio.enums.TipoFiltroPontoVendaPrimario;
import ipp.aci.boleia.dominio.enums.TipoFiltroPontoVendaSecundario;
import ipp.aci.boleia.dominio.enums.TipoServico;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEntre;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.CoordenadaVo;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroAutoCompletePostoRotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialPtovVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRIPontoVendaServicosVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaPontoVendaServicosVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.UtilitarioParse;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Respositorio de entidades PontoDeVenda
 */
@Repository
public class OraclePontoDeVendaDados extends OracleRepositorioBoleiaDados<PontoDeVenda> implements IPontoDeVendaDados {

    private static final String MUNICIPIO = "municipio";
    private static final String UF = "uf";
    private static final String STATUS = "status";
    private static final String FOTOS = "fotos";
    
    /**
     * O resultado dessa consulta sempre precisara dos valores dos relacionamentos com componentes, atividade componente e avaliacao.
     * Sendo assim, para evitar consultas posteriores por registro, ja fazemos o fetch nessas duas entidades.
     */
    private static final String PESQUISA_PARA_AUTOCOMPLETE_VINCULAR = 
        " SELECT " +
        "     p " +
        " FROM PontoDeVenda p " + 
        " INNER JOIN FETCH p.componentes c" +
        " INNER JOIN FETCH c.atividadeComponente " +
        " LEFT JOIN FETCH p.avaliacao " +
        " LEFT JOIN p.negociacoes n WITH n.frota.id = :idFrota" +
        " WHERE " + 
        "     n.statusVinculo IS NULL " +
        "     AND ( " + removerCaseCampo(removerAcentosCampo("p.nome")) + " like :nome " +
        "           OR cast(c.codigoPessoa as string) like :cnpj )" +
        "     %s " +
        " ORDER BY p.nome ";

    /**
     * Instancia o repositorio
     */
    public OraclePontoDeVendaDados() {
        super(PontoDeVenda.class);
    }

    @Override
    public ResultadoPaginado<PontoDeVenda> pesquisar(FiltroPesquisaPontoDeVendaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametroIgual("id", filtro.getId(), parametros);
        povoarParametroIgual("id", filtro.getNomeCnpj() != null ? filtro.getNomeCnpj().getId() : null, parametros);
        povoarParametroLike(MUNICIPIO, filtro.getCidade(), parametros);
        povoarParametroIgual(UF, filtro.getUf() != null ? filtro.getUf().getName() : null, parametros);
        povoarParametroIgual("rede.id", filtro.getRede() !=  null ? filtro.getRede().getId() : null, parametros);
        povoarParametroIgual("rede.nomeRede", filtro.getRede() !=  null ? filtro.getRede().getNome() : null, parametros);
        povoarParametroIgual("usuarios.id", filtro.getIdUsuario(),parametros);
        povoarParametroIgual("negociacoes.frota.id", filtro.getIdFrota(), parametros);
        povoarParametroRevendedor(filtro, parametros);
        povoarParametroFoto(filtro, parametros);

        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual(STATUS, StatusAtivacao.valueOf(filtro.getStatus().getName()).getValue()));
        }

        if(filtro.getSituacao() !=null && filtro.getSituacao().getName()!=null) {
            parametros.add(new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacaoPontoVenda.valueOf(filtro.getSituacao().getName()).getValue()));
        }

        if(filtro.getPermissaoPrecoPosto() !=null && filtro.getPermissaoPrecoPosto().getName()!=null) {
            parametros.add(new ParametroPesquisaIgual("permissaoPrecoPosto", StatusPermissaoPreco.valueOf(filtro.getPermissaoPrecoPosto().getName()).getValue()));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PontoDeVenda> pesquisarPorCnpjRazaoSocial(FiltroPesquisaParcialPtovVo filtro) {
        String termoCnpj = preparaTermoCnpj(filtro.getTermo());
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(
            new ParametroPesquisaOr(
                new ParametroPesquisaLike("componentes.codigoPessoa", termoCnpj),
                new ParametroPesquisaLike("nome", filtro.getTermo())
            )
        );

        if (filtro.getApenasPVsHabilitados()) {
            parametros.add(new ParametroPesquisaIgual(STATUS, StatusAtivacao.ATIVO.getValue()));
            parametros.add(new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
        }
        if (filtro.getIdRede() != null) {
            parametros.add(new ParametroPesquisaIgual("rede.id", filtro.getIdRede()));
        }
        if (filtro.getIdUsuario() != null) {
            parametros.add(new ParametroPesquisaIgual("usuarios.id", filtro.getIdUsuario()));
        }
        if (filtro.getIdFrota() != null) {
            parametros.add(new ParametroPesquisaIgual("negociacoes.frota.id", filtro.getIdFrota()));
        }
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("nome");

        return pesquisar(ordenacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public PontoDeVenda buscarPorCodigoCorporativo(Long codigoCorporativoPv) {
        return pesquisarUnico(new ParametroPesquisaIgual("codigoCorporativo", codigoCorporativoPv));
    }

    @Override
    public PontoDeVenda buscarPorNumeroAbadi(Long numeroAbadi) {
        return pesquisarUnico(new ParametroPesquisaIgual("numeroAbadi", numeroAbadi));
    }

    @Override
    public List<PontoDeVenda> pesquisarParaAutocomplete(String termo) {
        return pesquisar(new ParametroOrdenacaoColuna("nome"), new ParametroPesquisaLike("nome", termo.replaceAll("[-./]+", "")));
    }

    @Override
    public List<PontoDeVenda> obterTodosParaContexto(Usuario usuario, boolean ordenarPorDataPendenciaAceite) {
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna(ordenarPorDataPendenciaAceite ? "dataPendenciaAceite" : "nome");
        if (usuario.isRevendedor() && usuario.isGestor()) {
            ParametroPesquisaIgual paramRede = new ParametroPesquisaIgual("rede.id", usuario.getRede().getId());
            List<Integer> listaStatus = Arrays.asList(StatusHabilitacaoPontoVenda.HABILITADO.getValue(), StatusHabilitacaoPontoVenda.PENDENTE_ACEITE.getValue());
            ParametroPesquisa paramStatus = new ParametroPesquisaIn("statusHabilitacao", listaStatus);
            return pesquisarSemIsolamentoDados(ordenacao, paramStatus, paramRede);
        } else {
            ParametroPesquisa paramStatus = new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue());
            return pesquisar(ordenacao, paramStatus);
        }
    }

    @Override
    public List<PontoDeVenda> obterPontosVendaPorRede(Long idRede) {
        return pesquisar(new ParametroOrdenacaoColuna("nome"), new ParametroPesquisaIgual("rede.id", idRede));
    }

    @Override
    public List<PontoDeVenda> obterPontosVendaParaAtualizacaoJde() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaNulo("numeroJdeInterno",true));
        parametros.add(new ParametroPesquisaIgual("atualizarJde", true));
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("id");
        return pesquisar(ordenacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PontoDeVenda> pesquisarPontosVendaComServicos(FiltroPesquisaRotaPontoVendaServicosVo filtro) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaNulo("latitude", true));
        params.add(new ParametroPesquisaNulo("longitude", true));

        if(StringUtils.isNotBlank(filtro.getNome())) {
            params.add(new ParametroPesquisaOr(new ParametroPesquisaLike("nome", filtro.getNome()), new ParametroPesquisaLike("municipio", filtro.getNome())));
        }

        if(CollectionUtils.isNotEmpty(filtro.getTiposCombustivel())) {
            List<Long> idsCombustiveis = UtilitarioLambda.converterLista(filtro.getTiposCombustivel(), EntidadeVo::getId);

            params.add(new ParametroPesquisaAnd(
                    new ParametroPesquisaIn("precosBase.precoMicromercado.tipoCombustivel.id", idsCombustiveis),
                    new ParametroPesquisaIn("precosBase.status", Arrays.asList(
                            StatusAlteracaoPrecoPosto.VIGENTE.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITO.getValue())),
                    new ParametroPesquisaNulo("precosBase.preco", true)));

        }

        if(CollectionUtils.isNotEmpty(filtro.getOpcoesPrimarias())) {

            List<ParametroPesquisa> servicos = new ArrayList<>();

            filtro.getOpcoesPrimarias().forEach(o->{

                if(TipoFiltroPontoVendaPrimario.RODO_REDE.name().equals(o.getName())) {
                    servicos.add(new ParametroPesquisaIgual("rodoRede", true));
                } else if (TipoFiltroPontoVendaPrimario.FUNCIONAMENTO_24H.name().equals(o.getName())) {
                    servicos.add(new ParametroPesquisaIgual("funcionamento24h", true));
                } else {
                    TipoServico opcaoServico = TipoFiltroPontoVendaPrimario.valueOf(o.getName()).getServico();
                    servicos.add(new ParametroPesquisaIgual("respostaQuestionario.servico.id", opcaoServico.getValue()));
                }
            });

            if(!servicos.isEmpty()) {
                params.add(new ParametroPesquisaOr(servicos.toArray(new ParametroPesquisa[servicos.size()])));
            }
        }

        if(CollectionUtils.isNotEmpty(filtro.getOpcoesSecundarias())) {

            List<ParametroPesquisa> parametrosServicos = new ArrayList<>();

            filtro.getOpcoesSecundarias().forEach(o->{
                TipoFiltroPontoVendaSecundario tipoFiltro = TipoFiltroPontoVendaSecundario.valueOf(o.getName());
                if(tipoFiltro.getServicos().count() > 1) {
                    parametrosServicos.add(new ParametroPesquisaOr(
                            tipoFiltro.getServicos()
                                    .map(t -> new ParametroPesquisaIgual("respostaQuestionario.servico.id", t.getValue()))
                                    .toArray(ParametroPesquisaIgual[]::new)
                    ));
                } else {
                    TipoServico tipoServico = tipoFiltro.getServicos()
                            .findFirst()
                            .get();
                    parametrosServicos.add(
                            new ParametroPesquisaIgual("respostaQuestionario.servico.id", tipoServico.getValue())
                    );
                }
            });

            if(!parametrosServicos.isEmpty()) {
                params.add(new ParametroPesquisaOr(parametrosServicos.toArray(new ParametroPesquisa[parametrosServicos.size()])));
            }
        }

        if(CollectionUtils.isNotEmpty(filtro.getFiltrosCoordenadas())) {
            ParametroPesquisaOr condicoesOr = new ParametroPesquisaOr();
            BigDecimal margem = filtro.getMargemGrausFiltroCoordenadas() != null ? filtro.getMargemGrausFiltroCoordenadas() : BigDecimal.valueOf(0);

            for (List<CoordenadaVo> listaCoordenadas: filtro.getFiltrosCoordenadas()) {
                for (int i = 0; i < listaCoordenadas.size() - 1; i++) {
                    CoordenadaVo ca = listaCoordenadas.get(i);
                    BigDecimal caLat = ca.getLatitude();
                    BigDecimal caLong = ca.getLongitude();
                    CoordenadaVo cb = listaCoordenadas.get(i+1);
                    BigDecimal cbLat = cb.getLatitude();
                    BigDecimal cbLong = cb.getLongitude();
                    BigDecimal xa = (cbLat.compareTo(caLat) > 0 ? caLat : cbLat).subtract(margem);
                    BigDecimal xb = (cbLat.compareTo(caLat) > 0 ? cbLat : caLat).add(margem);
                    BigDecimal ya = (cbLong.compareTo(caLong) > 0 ? caLong : cbLong).subtract(margem);
                    BigDecimal yb = (cbLong.compareTo(caLong) > 0 ? cbLong : caLong).add(margem);

                    condicoesOr.addParametro(new ParametroPesquisaAnd(
                            new ParametroPesquisaEntre("latitude", xa, xb),
                            new ParametroPesquisaEntre("longitude", ya, yb)
                    ));
                }
            }

            params.add(condicoesOr);
        }

        params.add(new ParametroPesquisaFetch("avaliacao"));

        return pesquisar((ParametroOrdenacaoColuna)null, params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public List<PontoDeVenda> pesquisarPontosVendaComServicos(FiltroPesquisaRIPontoVendaServicosVo filtro) {

        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaNulo("latitude", true));
        params.add(new ParametroPesquisaNulo("longitude", true));

        if (filtro.getPostoUrbano() != null && !filtro.getPostoUrbano()){
            params.add(new ParametroPesquisaDiferente("perfilVenda", "Urbano"));
        }

        if (!CollectionUtils.isEmpty(filtro.getPostosParametizados())){
            params.add(new ParametroPesquisaIn("id", filtro.getPostosParametizados()));
        }

        if(CollectionUtils.isNotEmpty(filtro.getTiposCombustivel())) {
            List<Long> idsCombustiveis = UtilitarioLambda.converterLista(filtro.getTiposCombustivel(), EntidadeVo::getId);
            params.add(new ParametroPesquisaIn("precosBase.precoMicromercado.tipoCombustivel.id", idsCombustiveis));
        }

        params.add(new ParametroPesquisaOr(
            new ParametroPesquisaAnd(
                    new ParametroPesquisaIgual("restricaoVisibilidade", RestricaoVisibilidadePontoVenda.SEM_RESTRICAO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.statusBloqueio", StatusBloqueio.DESBLOQUEADO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.frota.id", filtro.getIdFrota())
            ),
            new ParametroPesquisaAnd(
                    new ParametroPesquisaIgual("restricaoVisibilidade", RestricaoVisibilidadePontoVenda.VISIVEL_APENAS_PARA_FROTAS_COM_VINCULO_ATIVO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.statusVinculo", StatusVinculoFrotaPontoVenda.ATIVO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.statusBloqueio", StatusBloqueio.DESBLOQUEADO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.frota.id", filtro.getIdFrota())
            ),
            new ParametroPesquisaAnd(
                    new ParametroPesquisaIgual("restricaoVisibilidade", RestricaoVisibilidadePontoVenda.SEM_RESTRICAO.getValue()),
                    new ParametroPesquisaNulo("negociacoes", false),
                    new ParametroPesquisaIn("precosBase.status", Arrays.asList(
                            StatusAlteracaoPrecoPosto.VIGENTE.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue(),
                            StatusAlteracaoPrecoPosto.ACEITO.getValue()
                    )),
                    new ParametroPesquisaNulo("precosBase.preco", true)
            )
        ));

        params.add(new ParametroPesquisaIgual("status",StatusAtivacao.ATIVO.getValue()));
        params.add(new ParametroPesquisaIgual("statusHabilitacao",StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
        params.add(new ParametroPesquisaIgual("excluido",false));

        if(CollectionUtils.isNotEmpty(filtro.getFiltrosCoordenadas())) {
            ParametroPesquisaOr condicoesOr = povoarParametroLatLongEntreCoordenadas("latitude", "longitude", filtro.getFiltrosCoordenadas(), filtro.getMargemGrausFiltroCoordenadas());
            params.add(condicoesOr);
        }

        return pesquisar((ParametroOrdenacaoColuna)null, params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public PontoDeVenda obterPontoVendaAceitacao(Long idPv, Long idRede) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("rede.id", idRede), new ParametroPesquisaIgual("id", idPv));
    }

    @Override
    public List<PontoDeVenda> pesquisarPorMunicipioNome(FiltroPesquisaParcialPtovVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(
                new ParametroPesquisaOr(
                        new ParametroPesquisaLike("municipio", filtro.getTermo()),
                        new ParametroPesquisaLike("nome", filtro.getTermo())
                )
        );
        if (filtro.getApenasPVsHabilitados()) {
            parametros.add(new ParametroPesquisaIgual(STATUS, StatusAtivacao.ATIVO.getValue()));
            parametros.add(new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
        }
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("nome");

        return pesquisar(ordenacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PontoDeVenda> pesquisarSemVinculoComFrota(FiltroPesquisaParcialPtovVo filtro) {
        String termoCnpj = preparaTermoCnpj(filtro.getTermo());
        String criterioHabilitados = "";
        if (filtro.getApenasPVsHabilitados()){
            criterioHabilitados = "     AND p.statusHabilitacao = " + StatusHabilitacaoPontoVenda.HABILITADO.getValue() 
                                + "     AND p.status = " + StatusAtivacao.ATIVO.getValue();
        }
        String queryString = String.format(PESQUISA_PARA_AUTOCOMPLETE_VINCULAR, criterioHabilitados);
        return pesquisarSemIsolamentoDados( null
            , queryString
            , new ParametroPesquisaIgual("idFrota", UtilitarioParse.tryParseLong(filtro.getIdFrota()))
            , new ParametroPesquisaLike("nome", filtro.getTermo())
            , new ParametroPesquisaLike("cnpj", termoCnpj)
        ).getRegistros();
    }

    @Override
    public List<PontoDeVenda> obterTodosHabilitados() {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaFetch("micromercado"), new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
    }

    @Override
    public PontoDeVenda obterPorCnpjAreaAbastecimento(Long cnpj) {
        return pesquisarUnico(new ParametroPesquisaIgual("componentes.codigoPessoa", cnpj), new ParametroPesquisaIn("componentes.atividadeComponente.codigoCorporativo",  AtividadeComponente.obterCodigosAreaAbastecimento()));
    }
    
    @Override
    public PontoDeVenda obterPorCnpj(Long cnpj) {
        List<PontoDeVenda> pdvs = pesquisarSemIsolamentoDados(
                new ParametroOrdenacaoColuna("componentes.codigoCorporativo", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIgual("componentes.codigoPessoa", cnpj),
                new ParametroPesquisaIn("componentes.atividadeComponente.codigoCorporativo", AtividadeComponente.obterCodigosAreaAbastecimento()));
        if (CollectionUtils.isNotEmpty(pdvs)) {
            return pdvs.get(0);
        }
        return null;
    }

    @Override
    public List<PontoDeVenda> obterPontoDeVendaPorLimitesLocalizacao(FiltroPesquisaLocalizacaoVo filtro) {
        ParametrosPesquisaBuilder parametros = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue())
                );

        if(filtro.getPerfilPontoDeVenda() != null) {
            switch (filtro.getPerfilPontoDeVenda()) {
                case RODOVIA:
                case URBANO:
                    parametros.adicionarParametros(new ParametroPesquisaLike("perfilVenda", filtro.getPerfilPontoDeVenda().name()));
                    break;
                case OUTROS:
                    parametros.adicionarParametros(new ParametroPesquisaOr(
                            new ParametroPesquisaNulo("perfilVenda"),
                            new ParametroPesquisaLike("perfilVenda", PerfilPontoDeVenda.RODO_REDE.name())
                    ));
                    break;
            }
        }

        if(CollectionUtils.isNotEmpty(filtro.getFiltrosCoordenadas())) {
            ParametroPesquisaOr condicoesOr = povoarParametroLatLongEntreCoordenadas("latitude", "longitude", filtro.getFiltrosCoordenadas(), filtro.getMargemGrausFiltroCoordenadas());
            parametros.adicionarParametros(condicoesOr);
        } else {
            parametros.adicionarParametros(povoarParametroLatLongEntreIntervalo("latitude", "longitude", filtro));
        }
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("nome");
        return pesquisar(ordenacao, parametros.buildArray());
    }

    @Override
    public Long obterQuantidadeIntegradaPorCnpj(Long cnpj) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("componentes.codigoPessoa", cnpj));
        parametros.add(new ParametroPesquisaIn("componentes.atividadeComponente.codigoCorporativo",  AtividadeComponente.obterCodigosAreaAbastecimento()));
        parametros.add(new ParametroPesquisaNulo("numeroJdeInterno", true));
        return (long) pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).size();
    }

    @Override
    public PontoDeVenda obterPorNumeroAbadi(Integer numeroAbadi) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("numeroAbadi", numeroAbadi));
    }

    @Override
    public List<PontoDeVenda> pesquisarParaAutocompleteRota(FiltroAutoCompletePostoRotaVo filtro){
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaNulo("latitude", true));
        parametros.add(new ParametroPesquisaNulo("longitude", true));

        if (!filtro.getPostoUrbano()){
            parametros.add(new ParametroPesquisaDiferente("perfilVenda", "Urbano"));
        }

        if (!CollectionUtils.isEmpty(filtro.getPostosParametizados())){
            parametros.add(new ParametroPesquisaIn("id", filtro.getPostosParametizados()));
        }

        parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaAnd(
                    new ParametroPesquisaIgual("restricaoVisibilidade", RestricaoVisibilidadePontoVenda.SEM_RESTRICAO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.statusBloqueio", StatusBloqueio.DESBLOQUEADO.getValue()),
                    new ParametroPesquisaIgual("negociacoes.frota.id", filtro.getIdFrota())
                ),
                new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("restricaoVisibilidade", RestricaoVisibilidadePontoVenda.VISIVEL_APENAS_PARA_FROTAS_COM_VINCULO_ATIVO.getValue()),
                        new ParametroPesquisaIgual("negociacoes.statusVinculo", StatusVinculoFrotaPontoVenda.ATIVO.getValue()),
                        new ParametroPesquisaIgual("negociacoes.statusBloqueio", StatusBloqueio.DESBLOQUEADO.getValue()),
                        new ParametroPesquisaIgual("negociacoes.frota.id", filtro.getIdFrota())
                ),
                new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("restricaoVisibilidade", RestricaoVisibilidadePontoVenda.SEM_RESTRICAO.getValue()),
                        new ParametroPesquisaNulo("negociacoes", false),
                        new ParametroPesquisaIn("precosBase.status", Arrays.asList(
                                StatusAlteracaoPrecoPosto.VIGENTE.getValue(),
                                StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue(),
                                StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue(),
                                StatusAlteracaoPrecoPosto.ACEITO.getValue()
                        )),
                        new ParametroPesquisaNulo("precosBase.preco", true)
                )
        ));

        parametros.add(new ParametroPesquisaIgual("precosBase.precoMicromercado.tipoCombustivel.id", filtro.getTipoCombustivel()));

        parametros.add(new ParametroPesquisaIgual("status",StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual("statusHabilitacao",StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
        parametros.add(new ParametroPesquisaIgual("excluido",false));

        parametros.add(new ParametroPesquisaLike("nome", filtro.getTermo()));


        return pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PontoDeVenda> obterPorIds(List<Long> ids) {
        return pesquisar((InformacaoPaginacao) null, new ParametroPesquisaIn("id", ids)).getRegistros();
    }

    /**
     * Povoa os parametros de pesquisa referentes ao filtro revendedor da consulta
     * @param filtro O filtro da consulta
     * @param parametros A lista atual de parametros
     */
    private void povoarParametroRevendedor(FiltroPesquisaPontoDeVendaVo filtro, List<ParametroPesquisa> parametros) {
        if(StringUtils.isNotBlank(filtro.getRevendedor())) {
            ParametroPesquisa paramCpf = new ParametroPesquisaLike("componentes.codigoPessoa", filtro.getRevendedor().replaceAll("[-./]+", ""));
            ParametroPesquisa paramNome = new ParametroPesquisaLike("componentes.nomePessoa", filtro.getRevendedor());
            parametros.add(new ParametroPesquisaOr(paramNome, paramCpf));
        }
    }

    /**
     * Povoa o parametro com/sem foto da pesquisa
     * @param filtro O filtro da pesquisa
     * @param parametros A lista atual de parametros
     */
    private void povoarParametroFoto(FiltroPesquisaPontoDeVendaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getFotos() != null && filtro.getFotos().getName() != null) {
            Boolean possuiFotos = StatusPosse.POSSUI.equals(StatusPosse.valueOf(filtro.getFotos().getName()));
            parametros.add(new ParametroPesquisaEmpty(FOTOS, possuiFotos));
        }
    }
}
