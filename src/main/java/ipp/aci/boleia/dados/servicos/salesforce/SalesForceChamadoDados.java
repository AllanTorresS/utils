package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.dados.IChamadoDados;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IMotivoChamadoDados;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.salesforce.ChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.ConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.FiltroConsultaChamadosVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.ConstantesSalesForce.SOLICITANTE_FROTA;
import static ipp.aci.boleia.util.ConstantesSalesForce.SOLICITANTE_INTERNO;
import static ipp.aci.boleia.util.ConstantesSalesForce.SOLICITANTE_REVENDA;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;
import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataIso8601ComTimeZoneMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;

/**
 * Respositorio para abertura de chamados Salesforce
 */
@Repository
public class SalesForceChamadoDados extends AcessoSalesForceBase implements IChamadoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesForceChamadoDados.class);

    private static final String CLAUSULA_CONTATO_FROTA = " AND Contact.IdExterno__c=':contatoFrota' ";
    private static final String CLAUSULA_CONTATO_REVENDA = " AND ContatoDoPosto__r.IdExterno__c IN (:contatoPosto) ";
    private static final String CLAUSULA_DATA_ABERTURA = " AND CreatedDate >= :dataAberturaDe AND CreatedDate <= :dataAberturaAte ";
    private static final String ORDER_BY_CONSULTA_CHAMADOS = "ORDER BY :ordenacao ";

    private static final String FROM_CONSULTAR_CHAMADOS =
            "FROM Case " +
            "WHERE CaseNumber LIKE ':numeroChamado' AND " +
            "       Status LIKE ':status' AND " +
            "       Solicitante__c=':solicitante' " +
                    CLAUSULA_CONTATO_FROTA +
                    CLAUSULA_CONTATO_REVENDA +
                    CLAUSULA_DATA_ABERTURA;

    private static final String CONSULTAR_CHAMADOS =
            "SELECT Id,CaseNumber,CreatedDate,CNPJPosto__c,CNPJFrota__c,Solicitante__c,Motivo__c,Status " +
            FROM_CONSULTAR_CHAMADOS +
            ORDER_BY_CONSULTA_CHAMADOS +
            "LIMIT :limit OFFSET :offset";

    private static final String COUNT_CONSULTAR_CHAMADOS =
            "SELECT COUNT() " + FROM_CONSULTAR_CHAMADOS;

    private static final String ALIAS_ORGID = "orgid";
    private static final String ALIAS_COMPANY = "company";
    private static final String ALIAS_NAME = "name";
    private static final String ALIAS_EMAIL = "email";
    private static final String ALIAS_PHONE = "phone";
    private static final String ALIAS_REASON = "00N0v0000018AKZ";
    private static final String ALIAS_SUBJECT = "subject";
    private static final String ALIAS_DESCRIPTION = "description";

    private static final int LIMITE_DEFAULT = 80;
    private static final int LIMITE_PHONE = 15;
    private static final int LIMITE_DESCRIPTION = 1024;

    private static final String CAMPO_TOTAL_ITEMS = "totalSize";
    private static final String CAMPO_SUCESSO = "done";

    @Value("${salesforce.chamados.authorization.client.id}")
    private String clientId;
    @Value("${salesforce.chamados.authorization.client.secret}")
    private String clientSecret;
    @Value("${salesforce.chamados.authorization.username}")
    private String username;
    @Value("${salesforce.chamados.authorization.password}")
    private String password;

    @Value("${salesforce.chamado.orgid}")
    private String orgid;

    @Value("${salesforce.chamado.url}")
    private String endereco;

    @Value("${salesforce.chamados.consulta.url}")
    private String urlConsulta;

    @Autowired
    private IClienteHttpDados restDados;

    @Autowired
    private IMotivoChamadoDados motivoChamadoDados;

    @Override
    public ResultadoPaginado<ChamadoVo> consultarChamados(FiltroConsultaChamadosVo filtro) {
        try {
            String queryConsulta = formatarQueryParaPesquisa(CONSULTAR_CHAMADOS, true, filtro);
            String queryCount = formatarQueryParaPesquisa(COUNT_CONSULTAR_CHAMADOS, false, filtro);

            return realizarConsultaChamados(queryConsulta, queryCount);
        } catch (UnsupportedEncodingException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    @Override
    public boolean abrirChamado(String company ,String name, String email, String phone, Long idReason, String subject, String description) {

        Map<String, String> form = new LinkedHashMap<>();
        form.put(ALIAS_ORGID, orgid);
        form.put(ALIAS_COMPANY, limitar(company, LIMITE_DEFAULT));
        form.put(ALIAS_NAME, limitar(name, LIMITE_DEFAULT));
        form.put(ALIAS_EMAIL, limitar(email, LIMITE_DEFAULT));
        form.put(ALIAS_PHONE, limitar(phone, LIMITE_PHONE));
        form.put(ALIAS_REASON, motivoChamadoDados.obterPorId(idReason).getDescricao());
        form.put(ALIAS_SUBJECT, limitar(subject, LIMITE_DEFAULT));
        form.put(ALIAS_DESCRIPTION, limitar(description, LIMITE_DESCRIPTION));

        try {
            return restDados.doPostFormEncoded(endereco, form, resp -> resp.getStatusLine().getStatusCode() == 200);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Realiza as integrações da consulta de chamados.
     *
     * @param queryConsulta Query utilizada para consultar os dados.
     * @param queryCount Query utilizada para realizar o count total dos dados.
     * @return Resultado paginado da consulta.
     */
    private ResultadoPaginado<ChamadoVo> realizarConsultaChamados(String queryConsulta, String queryCount) throws UnsupportedEncodingException {
        prepararRequisicao(urlConsulta, null);

        String endpointConsulta = this.instanceUrl.concat(format(this.urlConsulta, queryConsulta));
        String endpointCount = this.instanceUrl.concat(format(this.urlConsulta, queryCount));
        ConsultaChamadosVo consultaChamadosVo = restDados.doGet(endpointConsulta, this.authorizationHeaders, this::tratarRespostaConsultaChamados);
        Integer totalItems = restDados.doGet(endpointCount, this.authorizationHeaders, this::tratarRespostaCountChamados);

        ResultadoPaginado<ChamadoVo> resultadoPaginado = new ResultadoPaginado<>();
        resultadoPaginado.setTotalItems(totalItems);
        resultadoPaginado.setRegistros(consultaChamadosVo.getChamados() != null ? consultaChamadosVo.getChamados() : new ArrayList<>());
        return resultadoPaginado;
    }

    /**
     * Trata a resposta da consulta de chamados realizada pela API do Salesforce.
     *
     * @param response Resposta da integração.
     * @return Objeto com o resultado da consulta.
     */
    private ConsultaChamadosVo tratarRespostaConsultaChamados(CloseableHttpResponse response) {
        prepararResposta(response);
        if (this.statusCode == HttpStatus.OK.value()) {
            if (this.responseBody.get(CAMPO_SUCESSO) != null && this.responseBody.get(CAMPO_SUCESSO).asBoolean(false)) {
                return UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), ConsultaChamadosVo.class, false);
            } else {
                return new ConsultaChamadosVo();
            }
        }
        return null;
    }

    /**
     * Trata a resposta do count de chamados.
     *
     * @param response Response da integração.
     * @return Total de itens encontrados.
     */
    private Integer tratarRespostaCountChamados(CloseableHttpResponse response) {
        prepararResposta(response);
        if (this.statusCode == HttpStatus.OK.value()) {
            if (this.responseBody.get(CAMPO_SUCESSO) != null && this.responseBody.get(CAMPO_SUCESSO).asBoolean(false)) {
                return this.responseBody.get(CAMPO_TOTAL_ITEMS).asInt(0);
            }
        }
        return 0;
    }

    /**
     * Formata uma query de consulta que será utilizada na integração com a API do Salesforce.
     *
     * @param query Query a ser formatada.
     * @param possuiPaginacao Informa se a query possui informações de paginação.
     * @param filtro Filtro de consulta.
     * @return Query formatada.
     *
     * @throws UnsupportedEncodingException Exceção lançada caso não seja possível codificar o conteúdo da
     * query de consulta para ser usado na url de integração.
     */
    private String formatarQueryParaPesquisa(String query, boolean possuiPaginacao, FiltroConsultaChamadosVo filtro) throws UnsupportedEncodingException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("numeroChamado", tratarParametroLikeNulo(filtro.getNumeroChamado()));
        parametros.put("status", tratarParametroLikeNulo(filtro.getStatus()));
        parametros.put("solicitante", obterParametroSolicitante(filtro));

        List<String> contatos = obterContatosParaConsulta(filtro);
        if(filtro.isContatoFrota()) {
            query = query.replace(CLAUSULA_CONTATO_REVENDA, "");
            parametros.put("contatoFrota", contatos.stream().findFirst().orElse(""));
        } else if(filtro.isContatoRevenda()) {
            query = query.replace(CLAUSULA_CONTATO_FROTA, "");
            parametros.put("contatoPosto", contatos.stream().collect(joining("','", "'", "'")));
        }

        if(filtro.getDataAbertura() != null) {
            parametros.put("dataAberturaDe", formatarDataIso8601ComTimeZoneMillis(obterPrimeiroInstanteDia(filtro.getDataAbertura())));
            parametros.put("dataAberturaAte", formatarDataIso8601ComTimeZoneMillis(obterUltimoInstanteDia(filtro.getDataAbertura())));
        } else {
            query = query.replace(CLAUSULA_DATA_ABERTURA, "");
        }

        if(possuiPaginacao) {
            adicionarParametrosPaginacao(filtro, parametros);

            List<ParametroOrdenacaoColuna> parametrosOrdenacao = filtro.getPaginacao().getParametrosOrdenacaoColuna();
            if(parametrosOrdenacao != null && !parametrosOrdenacao.isEmpty()) {
                adicionarParametrosOrdenacao(parametrosOrdenacao, parametros);
            } else {
                query = query.replace(ORDER_BY_CONSULTA_CHAMADOS, "");
            }
        }
        return formatarQueryParaConsulta(query, parametros);
    }

    /**
     * Adiciona os parametros de ordenação no mapa de parametros da consulta.
     *  @param parametrosOrdenacao Parametros de ordenação a serem aplicados na consulta.
     * @param parametros Map com os parametros da consulta.
     */
    private void adicionarParametrosOrdenacao(List<ParametroOrdenacaoColuna> parametrosOrdenacao, Map<String, Object> parametros) {
        String parametroOrdenacao = parametrosOrdenacao.stream()
                                                        .map(p -> p.getNome() + " " + p.getSentidoOrdenacao().getTermoSql())
                                                        .collect(joining(","));
        parametros.put("ordenacao", parametroOrdenacao);
    }

    /**
     * Adiciona os parametros de paginação no mapa de parametros da consulta.
     *
     * @param filtro Filtro de pesquisa.
     * @param parametros Map com os parametros da consulta.
     */
    private void adicionarParametrosPaginacao(FiltroConsultaChamadosVo filtro, Map<String, Object> parametros) {
        Integer limit = filtro.getPaginacao().getTamanhoPagina();
        Integer offset = filtro.getPaginacao().getOffset();
        parametros.put("limit", limit);
        parametros.put("offset", offset);
    }

    /**
     * Retorna uma lista com os contatos utilizados na consulta.
     *
     * @param filtro Filtro de busca.
     * @return Lista com contatos.
     */
    private List<String> obterContatosParaConsulta(FiltroConsultaChamadosVo filtro) {
        String cpfContato = filtro.getCpfContato();
        List<String> cnpjsContato = filtro.getCnpjsContato();
        if(cpfContato != null && cnpjsContato != null && !cnpjsContato.isEmpty()) {
            return cnpjsContato.stream().map(cnpj -> cpfContato + cnpj).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Obtém o valor do parametro solicitante utilizado na busca por chamados no salesforce.
     *
     * @param filtro Filtro de consulta.
     * @return Valor do solicitante.
     */
    private String obterParametroSolicitante(FiltroConsultaChamadosVo filtro) {
        if(filtro.isContatoFrota()) {
            return SOLICITANTE_FROTA;
        } else if(filtro.isContatoRevenda()) {
            return SOLICITANTE_REVENDA;
        } else if(filtro.isContatoInterno()) {
            return SOLICITANTE_INTERNO;
        }
        return null;
    }

    /**
     * Trata o valor de um parâmetro like que pode ser nulo.
     *
     * @param parametro Valor do parâmetro like que pode ser nulo.
     * @return O valor do parâmetro que deverá ser utilizado na consulta.
     */
    private String tratarParametroLikeNulo(String parametro) {
        if(parametro == null) {
            return "%%";
        }
        return parametro;
    }

    /**
     * Formata uma query soql para a realização de uma consulta pela API do salesforce.
     *
     * @param query Query soql.
     * @param parametros Parametros da consulta.
     * @return Query formatada.
     *
     * @throws UnsupportedEncodingException Exceção lançada caso não seja possível codificar o conteúdo da
     * query de consulta para ser usado na url de integração.
     */
    private String formatarQueryParaConsulta(String query, Map<String, Object> parametros) throws UnsupportedEncodingException {
        for (String parametro : parametros.keySet()) {
            query = query.replace(":" + parametro, parametros.get(parametro).toString());
        }
        return URLEncoder.encode(query, UTF_8.displayName());
    }

    /**
     * Limita um campo a ser enviado no chamado
     *
     * @param campo O valor do campo
     * @param limite O limite de caracteres desejado
     * @return O campo limitado, caso necessario
     */
    private String limitar(String campo, int limite) {
        if(campo != null && campo.length() > limite) {
            campo = campo.substring(0, limite);
        }
        return campo;
    }
}
