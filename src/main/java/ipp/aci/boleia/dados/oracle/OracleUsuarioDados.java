package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUsuarioVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Respositorio de entidades de
 * Usuario
 */
@Repository
public class OracleUsuarioDados extends OracleRepositorioBoleiaDados<Usuario> implements IUsuarioDados {

    private static final String QUERY_TIPO_PERFIL_PERMISSAO = " SELECT DISTINCT(u) from Usuario u INNER JOIN u.tipoPerfil t LEFT JOIN u.perfis p LEFT JOIN p.permissoes pe " +
        " WHERE t.id = :tipoPerfil AND (u.gestor = 1 OR pe.chave = :chavePermissao)";

    private static final String QUERY_FROTA_PERMISSAO = "SELECT DISTINCT(u) from Usuario u INNER JOIN u.frota f LEFT JOIN u.perfis p LEFT JOIN p.permissoes pe " +
        " WHERE f.id = :idFrota AND (u.gestor = 1 OR pe.chave  IN (:chavesPermissao))";

    private static final String QUERY_PTOV_PERMISSAO =
        " SELECT DISTINCT(u) " +
        " FROM Usuario u " +
        " INNER JOIN u.pontosDeVenda ptov " +
        " LEFT JOIN u.perfis p " +
        " LEFT JOIN p.permissoes pe " +
        " WHERE ptov.id = :idPtov AND (u.gestor = 1 OR pe.chave  IN (:chavesPermissao))";

    private static final String QUERY_PESQUISA_PAGINADA =
            " SELECT DISTINCT(u) " +
                    " FROM Usuario u " +
                    " LEFT JOIN u.pontosDeVenda ptov " +
                    " LEFT JOIN u.perfis p " +
                    " LEFT JOIN u.coordenadoriasCoordenador coord " +
                    " WHERE u.excluido = 0 ";

    private static final String QUERY_TOTAL_USUARIOS_DONOS_FROTA =
            " SELECT COUNT(0) " +
                    "FROM Usuario u " +
                    "LEFT JOIN u.frota f " +
                    "WHERE u.status = :idStatusUsuario " +
                    "AND u.tipoPerfil.id = :idTipoPerfil " +
                    "AND u.excluido = :excluido " +
                    "AND f.status = :idStatusFrota " +
                    "AND u.cpf = f.cpfDonoFrota";

    /**
     * Instancia o repositório
     */
    public OracleUsuarioDados() {
        super(Usuario.class);
    }

    @Override
    public Usuario obterPorLogin(String login) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("login", login));
    }

    @Override
    public Usuario obterPorEmail(String email) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("email", email));
    }

    @Override
    public Usuario obterPorEmailTipoPerfil(String email, TipoPerfilUsuario tipoPerfil) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("email", email),
                new ParametroPesquisaIgual("tipoPerfil.id", tipoPerfil.getValue()));
    }

    @Override
    public Usuario obterPorToken(String token) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("token", token));
    }

    private ResultadoPaginado<Usuario> pesquisaPaginada(FiltroPesquisaUsuarioVo filtro, boolean isolamentoDados) {
        Map<String, ParametroPesquisa> parametros = montarParametroPesquisa(filtro);
        StringBuilder query = new StringBuilder(QUERY_PESQUISA_PAGINADA);
        povoarParametrosPesquisa(parametros, query);
        montarParametrosOrdenacao(filtro, query);
        return pesquisar(filtro.getPaginacao(), query.toString(),
                parametros.values().toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Usuario> pesquisaPaginada(FiltroPesquisaUsuarioVo filtro) {
        return pesquisaPaginada(filtro, true);
    }

    @Override
    public ResultadoPaginado<Usuario> pesquisaPaginadaSemIsolamento(FiltroPesquisaUsuarioVo filtro) {
        return pesquisaPaginada(filtro, false);
    }

    /**
     * Preenche a consulta com os condições e valores de cada parametro mapeado
     *
     * @param parametros mapa de parametros com condições e valores
     * @param query consulta onde o mapa será aplicado
     */
    private void povoarParametrosPesquisa(Map<String, ParametroPesquisa> parametros, StringBuilder query) {
        for (Map.Entry<String, ParametroPesquisa> parametro : parametros.entrySet()) {
            query.append(parametro.getKey());
            if (parametro.getValue() == null) {
                parametros.remove(parametro.getKey());
            }
        }
    }

    /**
     * Preenche a consulta com o campos de ordenação selecionados no filtro
     *
     * @param filtro filtro com as definições de ordenação
     * @param query consulta onde os parametros de ordenação serão aplicados
     */
    private void montarParametrosOrdenacao(FiltroPesquisaUsuarioVo filtro, StringBuilder query) {
        if (filtro.getPaginacao() != null && filtro.getPaginacao().getParametrosOrdenacaoColuna() != null) {
            StringJoiner orderBy = new StringJoiner(",");
            for (ParametroOrdenacaoColuna p : filtro.getPaginacao().getParametrosOrdenacaoColuna()) {
                if ("perfisDeAcesso".equals(p.getNome()) || "perfis".equals(p.getNome())) {
                    orderBy.add("p.nome".concat(" ").concat(p.isDecrescente() ? "DESC" : ""));
                } else if (Arrays.stream(Usuario.class.getDeclaredFields()).anyMatch(f -> f.getName().equals(p.getNome()))) {
                    orderBy.add("u." + p.getNome().concat(" ").concat(p.isDecrescente() ? "DESC" : ""));
                }
            }

            if(orderBy.length() > 0) {
                query.append(" ORDER BY ").append(orderBy.toString());
            }
        }
    }

    @Override
    public List<Usuario> pesquisarPorNome(FiltroPesquisaUsuarioVo filtro) {
        return this.pesquisar(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaLike("nome", filtro.getNome()),
                new ParametroPesquisaIgual("tipoPerfil.id", filtro.getTipoPerfil().getId()));
    }

    @Override
    public Usuario obterPorCpf(Long cpf, Boolean usuarioMotorista) {
        ParametroPesquisa parametroPesquisaUsuarioMotorista = usuarioMotorista ?
                new ParametroPesquisaIgual("tipoPerfil.id",TipoPerfilUsuario.MOTORISTA.getValue())
                : new ParametroPesquisaDiferente("tipoPerfil.id",TipoPerfilUsuario.MOTORISTA.getValue());
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("cpf", cpf), parametroPesquisaUsuarioMotorista);
    }

    @Override
    public Usuario obterPorCpfTipoPerfil(Long cpf, Long tipoPerfilId) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("cpf", cpf),
                                                new ParametroPesquisaIgual("tipoPerfil.id", tipoPerfilId));
    }

    @Override
    public List<Usuario> obterPorTipoPerfilGestor(TipoPerfilUsuario perfil, Boolean isGestor) {
        ParametroPesquisaIgual parametroPerfil = new ParametroPesquisaIgual("tipoPerfil", perfil.getValue());
        ParametroOrdenacaoColuna ordenacaoColuna = new ParametroOrdenacaoColuna("nome");
        if (isGestor && !TipoPerfilUsuario.INTERNO.equals(perfil)) {
            return pesquisarSemIsolamentoDados(ordenacaoColuna, parametroPerfil, new ParametroPesquisaIgual("gestor", Boolean.TRUE));
        }
        return pesquisarSemIsolamentoDados(ordenacaoColuna, parametroPerfil);
    }

    @Override
    public Long obterTotalUsuariosDonosFrota() {
        return pesquisarUnicoSemIsolamentoDados(QUERY_TOTAL_USUARIOS_DONOS_FROTA,
                new ParametroPesquisaIgual("idTipoPerfil", TipoPerfilUsuario.FROTA.getValue()),
                new ParametroPesquisaIgual("idStatusUsuario", StatusAtivacao.ATIVO.getValue()),
                new ParametroPesquisaIgual("excluido", false),
                new ParametroPesquisaIgual("idStatusFrota", StatusAtivacao.ATIVO.getValue()));
    }

    @Override
    public List<Usuario> obterGestorPorFrota(Long idFrota) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaIgual("frota", idFrota),
                new ParametroPesquisaIgual("tipoPerfil.id", TipoPerfilUsuario.FROTA.getValue()),
                new ParametroPesquisaIgual("gestor", Boolean.TRUE));
    }

    @Override
    public List<Usuario> obterGestorPorRede(Long idRede) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaIgual("rede", idRede),
                new ParametroPesquisaIgual("tipoPerfil.id", TipoPerfilUsuario.REVENDA.getValue()),
                new ParametroPesquisaIgual("gestor", Boolean.TRUE));
    }

    @Override
    public List<Usuario> obterGestorPorRedes(List<Long> idsRedes) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaIn("rede.id", idsRedes),
                new ParametroPesquisaIgual("tipoPerfil.id", TipoPerfilUsuario.REVENDA.getValue()),
                new ParametroPesquisaIgual("gestor", Boolean.TRUE));
    }

    @Override
    public List<Usuario> obterUsuariosPorRede(Long idRede) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("rede", idRede));
    }

    @Override
    public List<Usuario> obterPorTipoPerfilPermissao(Long idTipoPerfil, String chavePermissao) {
        return pesquisarSemIsolamentoDados(
                null,
                QUERY_TIPO_PERFIL_PERMISSAO,
                new ParametroPesquisaIgual("tipoPerfil", idTipoPerfil),
                new ParametroPesquisaIgual("chavePermissao", chavePermissao)).getRegistros();
    }

    @Override
    public List<Usuario> obterPorFrotaPermissoes(Long idFrota, String... chavesPermissao) {
        return pesquisarSemIsolamentoDados(
                null,
                QUERY_FROTA_PERMISSAO,
                new ParametroPesquisaIgual("idFrota", idFrota),
                new ParametroPesquisaIgual("chavesPermissao", Arrays.asList(chavesPermissao))).getRegistros();
    }

    @Override
    public List<Usuario> obterPorPontoVendaPermissoes(Long idPtov, String... chavesPermissao) {
        return pesquisarSemIsolamentoDados(
                null,
                QUERY_PTOV_PERMISSAO,
                new ParametroPesquisaIgual("idPtov", idPtov),
                new ParametroPesquisaIgual("chavesPermissao", Arrays.asList(chavesPermissao))).getRegistros();
    }

    @Override
    public void desvincularUnidades(Long unidadeId) {

        String updateUsuariosSemGrupo = "update Usuario u set u.unidade = null, u.status = 0 where u.unidade.id = :unidadeId and u.grupoOperacional is null";
        Query query = getGerenciadorDeEntidade().createQuery(updateUsuariosSemGrupo);
        query.setParameter("unidadeId", unidadeId);
        query.executeUpdate();

        String updateUsuariosComGrupo = "update Usuario u set u.unidade = null where u.unidade.id = :unidadeId and u.grupoOperacional is not null";
        query = getGerenciadorDeEntidade().createQuery(updateUsuariosComGrupo);
        query.setParameter("unidadeId", unidadeId);
        query.executeUpdate();
    }

    @Override
    public List<Usuario> obterPorFrota(Long idFrota) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaIgual("frota", idFrota));
    }

    @Override
    public Long obterQuantidadeTotal() {
        return pesquisarTotalRegistros();
    }

    @Override
    public Long obterQuantidadeTotalAtivosDeTipoPerfil(TipoPerfilUsuario tipoPerfilUsuario) {
        return pesquisarTotalRegistros(new ParametroPesquisaIgual("tipoPerfil", tipoPerfilUsuario.getValue()),
                new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()),
                new ParametroPesquisaIgual("excluido", false));
    }

    @Override
    public List<Usuario> obterPorCoordenadoria(final Long id) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("coordenadoria.id", id));
    }

    /**
     * Monta os parametros de pesquisa de acordo com o filtro informado
     * @param filtro O filtro de pesquisa
     * @return Mapa de parametros e valores para a consulta
     */
    private Map<String, ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaUsuarioVo filtro) {
        Map<String, ParametroPesquisa> parametros = new HashMap();
        if (filtro.getPontosDeVenda() != null && !filtro.getPontosDeVenda().isEmpty()) {
            parametros.put(" AND ptov.id IN (:ptovs) ",
                    new ParametroPesquisaIgual("ptovs", filtro.getPontosDeVenda()));
        } else if (filtro.getPontoDeVenda() != null && filtro.getPontoDeVenda().getId() != null) {
            parametros.put(" AND ptov.id = :ptov ",
                    new ParametroPesquisaIgual("ptov", obterIdEntidadeFiltro(filtro.getPontoDeVenda())));
        }

        criarParametrosBasicos(filtro, parametros);
        if (!StringUtils.isEmpty(filtro.getNome())) {
            parametros.put(" AND (LOWER(u.nome) LIKE :nome OR LOWER(u.login) LIKE :nome) ",
                    new ParametroPesquisaLike("nome", "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(filtro.getEmail())) {
            parametros.put("  AND u.email LIKE :email ",
                    new ParametroPesquisaLike("email", "%" + filtro.getEmail() + "%"));
        }
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.put(" AND u.unidade.id = :unidade ",
                        new ParametroPesquisaIgual("unidade", filtro.getUnidade().getId()));
            } else {
                parametros.put(" AND u.unidade IS NULL ", null);
            }
        }
        if (filtro.getCoordenadoria() != null && filtro.getCoordenadoria().getId() != null) {
            parametros.put(" AND (u.coordenadoria.id = :coordenadoria OR coord.id = :coordenadoria) ",
                    new ParametroPesquisaIgual("coordenadoria", filtro.getCoordenadoria().getId()));
        }

        return parametros;
    }

    /**
     * Cria os parametros basicos da consulta e injeta-os no mapa de parametros informado
     * @param filtro O filtro de pesquisa
     * @param parametros Mapa de parametros da consulta
     */
    private void criarParametrosBasicos(FiltroPesquisaUsuarioVo filtro, Map<String, ParametroPesquisa> parametros) {
        povoarParametroIgual(" AND u.cpf = :cpf ", "cpf", UtilitarioFormatacao.obterLongMascara(filtro.getCpf()), parametros);
        povoarParametroIgual(" AND u.status = :status ","status", filtro.getStatus() != null
                && filtro.getStatus().getName() != null ? StatusAtivacao.valueOf(filtro.getStatus().getName()).getValue() : null, parametros);
        povoarParametroIgual(" AND u.gestor = :gestor ","gestor", filtro.getGestor() != null
                && filtro.getGestor() ? filtro.getGestor() : null, parametros);
        povoarParametroIgual(" AND u.frota.id = :frotaId ", "frotaId",
                obterIdEntidadeFiltro(filtro.getFrota()), parametros);
        povoarParametroIgual(" AND u.grupoOperacional.id = :grupoOperacionalId ", "grupoOperacionalId",
                obterIdEntidadeFiltro(filtro.getGrupoOperacional()), parametros);
        povoarParametroIgual(" AND u.rede.id = :redeId ", "redeId",
                obterIdEntidadeFiltro(filtro.getRede()), parametros);
        povoarParametroIgual(" AND p.id = :perfisId ", "perfisId",
                obterIdEntidadeFiltro(filtro.getPerfil()), parametros);
        povoarParametroIgual(" AND u.tipoPerfil.id = :tipoPerfilId ", "tipoPerfilId",
                obterIdEntidadeFiltro(filtro.getTipoPerfil()), parametros);
    }

    /**
     * Valida se um parametro pode ser incluido no mapa de parametros da consulta
     *
     * @param condicao da consulta onde o parametro sera utilizado
     * @param nomeCampo nome do campo do parametro dentro da consulta
     * @param valorParametro valor do parametro dentro da consulta
     * @param parametros mapa de parametros para inclusao do parametro avaliado
     */
    private void povoarParametroIgual(String condicao, String nomeCampo, Object valorParametro, Map<String, ParametroPesquisa> parametros) {
        if (valorParametro != null || (valorParametro instanceof String && StringUtils.isNotBlank((String) valorParametro))) {
            parametros.put(condicao, new ParametroPesquisaIgual(nomeCampo, valorParametro));
        }
    }
}
