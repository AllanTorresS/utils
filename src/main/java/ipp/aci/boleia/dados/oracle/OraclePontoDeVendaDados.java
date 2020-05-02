package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dominio.AtividadeComponente;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.enums.StatusPermissaoPreco;
import ipp.aci.boleia.dominio.enums.StatusPosse;
import ipp.aci.boleia.dominio.enums.TipoFiltroPontoVendaPrimario;
import ipp.aci.boleia.dominio.enums.TipoFiltroPontoVendaSecundario;
import ipp.aci.boleia.dominio.enums.TipoServico;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialPtovVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaPontoVendaServicosVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioLambda;
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
        String termoCnpj = (filtro.getTermo() == null) ? null : filtro.getTermo().replaceAll("[-./]+", "")
                                                                                 .replaceFirst("^0+(?!$)", "");
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

        params.add(new ParametroPesquisaFetch("avaliacao"));

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

        ParametroPesquisa[]  parametros = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaMaior("latitude", new BigDecimal(filtro.getLatitudeInicial())),
                        new ParametroPesquisaMenor("latitude", new BigDecimal(filtro.getLatitudeFinal()))
                        )
                .adicionarParametros(
                        new ParametroPesquisaMaior("longitude", new BigDecimal(filtro.getLongitudeInicial())),
                        new ParametroPesquisaMenor("longitude", new BigDecimal(filtro.getLongitudeFinal()))
                )
                .adicionarParametros(
                        new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue())
                )
                .buildArray();


        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("nome");

        return pesquisar(ordenacao, parametros);
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
