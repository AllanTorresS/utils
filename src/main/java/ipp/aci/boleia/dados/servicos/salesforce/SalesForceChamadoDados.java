package ipp.aci.boleia.dados.servicos.salesforce;

import com.fasterxml.jackson.databind.JsonNode;
import ipp.aci.boleia.dados.IChamadoDados;
import ipp.aci.boleia.dados.IChamadoSistemaDados;
import ipp.aci.boleia.dados.servicos.rest.ConsumidorHttp;
import ipp.aci.boleia.dominio.ChamadoSistema;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.salesforce.CancelamentoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.ChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.ConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.ContatoSalesforceVo;
import ipp.aci.boleia.dominio.vo.salesforce.CriacaoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.CriacaoContatoVo;
import ipp.aci.boleia.dominio.vo.salesforce.EdicaoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.FiltroConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.PicklistVo;
import ipp.aci.boleia.dominio.vo.salesforce.UploadAnexoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.ValorPicklistVo;
import ipp.aci.boleia.util.ConstantesSalesForce;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.ConstantesSalesForce.CAMPO_DONE;
import static ipp.aci.boleia.util.ConstantesSalesForce.CAMPO_ID_CHAMADO_ANEXO;
import static ipp.aci.boleia.util.ConstantesSalesForce.CAMPO_ID_DOCUMENTO;
import static ipp.aci.boleia.util.ConstantesSalesForce.CAMPO_REGISTROS;
import static ipp.aci.boleia.util.ConstantesSalesForce.CAMPO_VISIBILIDADE_ANEXO;
import static ipp.aci.boleia.util.ConstantesSalesForce.SOLICITANTE_FROTA;
import static ipp.aci.boleia.util.ConstantesSalesForce.SOLICITANTE_INTERNO;
import static ipp.aci.boleia.util.ConstantesSalesForce.SOLICITANTE_REVENDA;
import static ipp.aci.boleia.util.ConstantesSalesForce.VALOR_CAMPO_VISIBILIDADE_ANEXO;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCnpjApresentacao;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCpfApresentacao;
import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataIso8601ComTimeZoneMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

/**
 * Respositorio para abertura de chamados Salesforce
 */
@Repository
@Scope("prototype")
public class SalesForceChamadoDados extends AcessoSalesForceBase implements IChamadoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesForceChamadoDados.class);

    private static final String CLAUSULA_CONTATO = " AND Contact.IdExterno__c=':contato' ";
    private static final String CLAUSULA_CONTATO_REVENDA = " AND ContatoDoPosto__r.IdExterno__c IN (:contato) ";
    private static final String CLAUSULA_DATA_ABERTURA = " AND CreatedDate >= :dataAberturaDe AND CreatedDate <= :dataAberturaAte ";
    private static final String ORDER_BY_CONSULTA_CHAMADOS = "ORDER BY :ordenacao ";

    private static final String FROM_CONSULTAR_CHAMADOS =
            "FROM Case " +
            "WHERE CaseNumber LIKE ':numeroChamado' AND " +
            "       Status LIKE ':status' AND " +
            "       Solicitante__c=':solicitante' " +
                    CLAUSULA_CONTATO +
                    CLAUSULA_CONTATO_REVENDA +
                    CLAUSULA_DATA_ABERTURA;

    private static final String SUBQUERY_COMENTARIOS =
            "(SELECT " +
            "   Id,CommentBody,CreatedBy.Name,CreatedBy.Email,CreatedDate " +
            "FROM " +
            "   Casecomments " +
            "WHERE " +
            "   CommentBody LIKE '" + ConstantesSalesForce.PREFIXO_COMENTARIO_PENDENTE_EXTERNO + "%' OR CreatedBy.Email=':emailIntegracao') ";

    private static final String OBTER_CHAMADO_POR_ID =
            "SELECT Id," +
            "       CreatedDate," +
            "       CNPJPosto__c," +
            "       CNPJFrota__c," +
            "       Solicitante__c," +
            "       CaseNumber," +
            "       Status," +
            "       Priority," +
            "       Type," +
            "       ClassificacaoPerfil__c," +
            "       Motivo__c," +
            "       MotivoSolicitacao__c," +
            "       Subject," +
            "       Description," +
                    SUBQUERY_COMENTARIOS +
            "FROM Case " +
            "WHERE Id=':idSalesforce'";

    private static final String CONSULTAR_CHAMADOS =
            "SELECT Id," +
            "       CaseNumber," +
            "       CreatedDate," +
            "       CNPJPosto__c," +
            "       CNPJFrota__c," +
            "       Solicitante__c," +
            "       Motivo__c," +
            "       Status," +
            "       ClassificacaoPerfil__c," +
            "       Type," +
            "       Description," +
            "       MotivoSolicitacao__c," +
            "       Subject," +
                    SUBQUERY_COMENTARIOS +
            FROM_CONSULTAR_CHAMADOS +
            ORDER_BY_CONSULTA_CHAMADOS +
            "LIMIT :limit OFFSET :offset";

    private static final String COUNT_CONSULTAR_CHAMADOS =
            "SELECT COUNT() " + FROM_CONSULTAR_CHAMADOS;

    private static final String CONSULTAR_CHAMADOS_DUPLICADOS =
            "SELECT Id " +
            "FROM Case " +
            "WHERE IsClosed=false AND " +
            "      Type=':tipoChamado' AND " +
            "      ClassificacaoPerfil__c=':sistemaDeOrigem' AND " +
            "      Motivo__c=':motivo' AND " +
            "      MotivoSolicitacao__c=':modulo'";

    private static final String OBTER_ID_CONTATO = "SELECT Id FROM Contact WHERE IdExterno__c=':idExterno'";

    @Value("${salesforce.chamados.authorization.client.id}")
    private String clientId;

    @Value("${salesforce.chamados.authorization.client.secret}")
    private String clientSecret;

    @Value("${salesforce.chamados.authorization.username}")
    private String username;

    @Value("${salesforce.chamados.authorization.password}")
    private String password;

    @Value("${salesforce.chamados.emailIntegracao}")
    private String emailIntegracao;

    @Value("${salesforce.chamados.consulta.url}")
    private String urlConsulta;

    @Value("${salesforce.chamados.criacao.url}")
    private String urlCriacao;

    @Value("${salesforce.chamados.cancelamento.url}")
    private String urlCancelamento;

    @Value("${salesforce.chamados.listar.picklists}")
    private String urlListarPicklists;

    @Value("${salesforce.chamados.cnpj.interno}")
    private String cnpjContatoUsuarioInterno;

    @Value("${salesforce.chamados.edicao.url}")
    private String urlEdicao;

    @Value("${salesforce.chamados.adicionarComentario.url}")
    private String urlAdicionarComentario;

    @Value("${salesforce.chamados.adicionarArquivo}")
    private String urlAdicionarArquivo;

    @Value("${salesforce.chamados.obterArquivo}")
    private String urlObterArquivo;

    @Value("${salesforce.chamados.vincularArquivo}")
    private String urlVincularArquivo;

    @Value("${salesforce.chamados.criarContato}")
    private String urlCriarContato;

    @Autowired
    private IChamadoSistemaDados chamadoSistemaDados;

    @Override
    public ChamadoVo obterPorIdSalesforce(String idSalesforce) {
        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        List<String> contatos = obterContatosPorUsuario(usuarioLogado);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idSalesforce", idSalesforce);
        parametros.put("emailIntegracao", emailIntegracao);

        String query = OBTER_CHAMADO_POR_ID;
        if(usuarioLogado.isFrotista() || usuarioLogado.isInterno()) {
            parametros.put("contato", contatos.stream().findFirst().orElse(""));
            query += CLAUSULA_CONTATO;
        } else if(usuarioLogado.isRevendedor()) {
            parametros.put("contato", contatos.stream().collect(joining("','", "'", "'")));
            query += CLAUSULA_CONTATO_REVENDA;
        }
        List<ChamadoVo> chamados = executarQuerySalesforce(formatarQueryParaConsulta(query, parametros), ChamadoVo.class);
        return chamados != null ? chamados.stream().findFirst().orElse(null) : null;
    }

    @Override
    public ChamadoVo obterPorIdSalesforceSemIsolamento(String idSalesforce) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idSalesforce", idSalesforce);
        List<ChamadoVo> chamados = executarQuerySalesforce(formatarQueryParaConsulta(OBTER_CHAMADO_POR_ID, parametros), ChamadoVo.class);
        return chamados != null ? chamados.stream().findFirst().orElse(null) : null;
    }

    @Override
    public ResultadoPaginado<ChamadoVo> consultarChamados(FiltroConsultaChamadosVo filtro) {
        String queryConsulta = formatarQueryParaPesquisa(CONSULTAR_CHAMADOS, true, filtro);
        String queryCount = formatarQueryParaPesquisa(COUNT_CONSULTAR_CHAMADOS, false, filtro);

        return executarQuerySalesforcePaginada(queryConsulta, queryCount, ChamadoVo.class);
    }

    @Override
    public List<ChamadoVo> buscarChamadosDuplicados(CriacaoChamadoVo criacaoChamadoVo) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tipoChamado", criacaoChamadoVo.getTipoChamado());
        parametros.put("sistemaDeOrigem", criacaoChamadoVo.getSistemaOrigem());
        parametros.put("motivo", criacaoChamadoVo.getMotivo());
        parametros.put("modulo", criacaoChamadoVo.getModulo());

        String solicitante = criacaoChamadoVo.getSolicitante();
        String query = CONSULTAR_CHAMADOS_DUPLICADOS;
        if(solicitante.equals(SOLICITANTE_FROTA) || solicitante.equals(SOLICITANTE_INTERNO)) {
            parametros.put("contato", criacaoChamadoVo.getContactFrota().getIdExterno());
            query += CLAUSULA_CONTATO;
        } else if(solicitante.equals(SOLICITANTE_REVENDA)) {
            parametros.put("contato", "'" + criacaoChamadoVo.getContactPosto().getIdExterno() + "'");
            query += CLAUSULA_CONTATO_REVENDA;
        }
        return executarQuerySalesforce(formatarQueryParaConsulta(query, parametros), ChamadoVo.class);
    }

    @Override
    public String criarChamado(CriacaoChamadoVo chamadoVo) throws ExcecaoBoleiaRuntime {
        prepararRequisicao(urlCriacao, chamadoVo);
        return enviarRequisicaoPost(this::trataRespostaCriacao);
    }

    @Override
    public void editarChamado(EdicaoChamadoVo vo, String idSalesforce) throws ExcecaoBoleiaRuntime {
        String urlEdicaoFormatada = MessageFormat.format(this.urlEdicao, idSalesforce);
        prepararRequisicao(urlEdicaoFormatada, vo);
        enviarRequisicaoPatch(this::trataRespostaEdicao);
    }

    @Override
    public List<ValorPicklistVo> listarStatusChamado() {
        return listarValoresPicklist(ConstantesSalesForce.CAMPO_STATUS, resposta -> {
            prepararResposta(resposta);
            if(this.statusCode == HttpStatus.OK.value()) {
                PicklistVo picklist = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), PicklistVo.class, false);
                return picklist.getValores();
            }
            return new ArrayList<>();
        });
    }

    @Override
    public List<ValorPicklistVo> listarTiposChamado() {
        return listarValoresPicklist(ConstantesSalesForce.CAMPO_TIPO, resposta -> {
            prepararResposta(resposta);
            if(this.statusCode == HttpStatus.OK.value()) {
                PicklistVo picklist = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), PicklistVo.class, false);
                return picklist.getValores();
            }
            return new ArrayList<>();
        });
    }

    @Override
    public List<ValorPicklistVo> listarSistemasDeOrigem() {
        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        List<ChamadoSistema> chamadoSistemas = chamadoSistemaDados.obterSistemasChamado(usuarioLogado.getTipoPerfilUsuario());
        return listarValoresPicklist(ConstantesSalesForce.CAMPO_SISTEMA_ORIGEM, resposta -> {
            prepararResposta(resposta);
            if(this.statusCode == HttpStatus.OK.value()) {
                PicklistVo picklist = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), PicklistVo.class, false);
                return picklist.getValores().stream()
                        .filter(valor -> chamadoSistemas.stream().anyMatch(chamadoSistema -> chamadoSistema.getNome().equals(valor.getLabel())))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        });
    }

    @Override
    public List<ValorPicklistVo> listarMotivosPorSistemaDeOrigem(String sistemaDeOrigem) {
        return listarValoresPicklist(ConstantesSalesForce.CAMPO_MOTIVO, resposta -> {
            prepararResposta(resposta);
            if(this.statusCode == HttpStatus.OK.value()) {
                PicklistVo picklist = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), PicklistVo.class, false);
                Integer valorEntradaSistemaDeOrigem = picklist.getValoresEntrada().get(sistemaDeOrigem);

                return picklist.getValores().stream()
                        .filter(valor -> asList(valor.getValoresEntradaValidos()).contains(valorEntradaSistemaDeOrigem))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        });
    }

    @Override
    public List<ValorPicklistVo> listarModulosPorSistemaDeOrigem(String sistemaDeOrigem) {
        return listarValoresPicklist(ConstantesSalesForce.CAMPO_MODULO, resposta -> {
            prepararResposta(resposta);
            if(this.statusCode == HttpStatus.OK.value()) {
                PicklistVo picklist = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), PicklistVo.class, false);
                Integer valorEntradaSistemaDeOrigem = picklist.getValoresEntrada().get(sistemaDeOrigem);

                return picklist.getValores().stream()
                        .filter(valor -> asList(valor.getValoresEntradaValidos()).contains(valorEntradaSistemaDeOrigem))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        });
    }

    @Override
    public List<ValorPicklistVo> listarMotivosCancelamento() {
        return listarValoresPicklist(ConstantesSalesForce.CAMPO_MOTIVO_CANCELAMENTO, resposta -> {
            prepararResposta(resposta);
            if(this.statusCode == HttpStatus.OK.value()) {
                PicklistVo picklist = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(this.responseBody.toString(), PicklistVo.class, false);
                return picklist.getValores().stream()
                        .filter(p -> !p.getValor().equals(ConstantesSalesForce.CAMPO_MOTIVO_CANCELAMENTO_VALOR_DUPLICADO))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        });
    }

    @Override
    public void cancelarChamado(String idSalesforce, String motivoCancelamento, String descricaoCancelamento) {
        CancelamentoChamadoVo vo = new CancelamentoChamadoVo();
        vo.setStatus(ConstantesSalesForce.STATUS_CANCELAMENTO);
        vo.setMotivoCancelamento(motivoCancelamento);
        vo.setDescricaoCancelamento(descricaoCancelamento);

        String urlCancelamentoComIdSalesforce = format(urlCancelamento, idSalesforce);

        prepararRequisicao(urlCancelamentoComIdSalesforce, vo);
        enviarRequisicaoPatch(this::trataRespostaCancelamento);
    }

    @Override
    public void adicionarComentario(String idSalesforce, String comentario) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put(ConstantesSalesForce.CAMPO_CHAMADO_COMENTARIO, idSalesforce);
        responseBody.put(ConstantesSalesForce.CAMPO_CORPO_COMENTARIO, comentario);

        prepararRequisicao(urlAdicionarComentario, responseBody);
        enviarRequisicaoPost(response -> {
            prepararResposta(response);
            if (this.statusCode != HttpStatus.CREATED.value()) {
                LOGGER.error(this.responseBody.toString());
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.edicao.erro.integracao"));
            }
            return true;
        });
    }

    @Override
    public void adicionarAnexos(String idChamado, List<UploadAnexoChamadoVo> anexos) {
        anexos.forEach(anexo -> {
            String idUpload = realizarUploadAnexo(anexo);
            String idAnexo = obterIdentificadorDocumentoAnexo(idUpload);
            vincularAnexoComChamado(idAnexo, idChamado);
        });
    }

    @Override
    public ContatoSalesforceVo obterContato(String idExterno) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idExterno", idExterno);

        String query = formatarQueryParaConsulta(OBTER_ID_CONTATO, parametros);
        List<ContatoSalesforceVo> contatos = executarQuerySalesforce(query, ContatoSalesforceVo.class);
        return contatos.stream().findFirst().orElse(null);
    }

    @Override
    public void criarContato(CriacaoContatoVo vo) {
        prepararRequisicao(urlCriarContato, vo);
        enviarRequisicaoPost(this::trataRespostaCriacao);
    }

    /**
     * Realiza o upload de um anexo para o salesforce.
     *
     * @param anexo Objeto com os dados do anexo.
     * @return Identificador do anexo.
     */
    private String realizarUploadAnexo(UploadAnexoChamadoVo anexo) {
        prepararRequisicao(urlAdicionarArquivo, anexo);
        return enviarRequisicaoPost(response -> {
            prepararResposta(response);
            if(this.statusCode != HttpStatus.CREATED.value()) {
                LOGGER.error(this.responseBody.toString());
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.edicao.erro.integracao"));
            }
            return this.responseBody.get(ConstantesSalesForce.CAMPO_ID).asText();
        });
    }

    /**
     * Obtém o identificador do documento do anexo utilizado para vincular o anexo com o chamado.
     *
     * @param idUpload Identificador obtido na integração de upload do anexo.
     * @return Identificador do anexo encontrado.
     */
    private String obterIdentificadorDocumentoAnexo(String idUpload) {
        prepararRequisicao(MessageFormat.format(urlObterArquivo, idUpload), null);
        return enviarRequisicaoGet(response -> {
            prepararResposta(response);
            if(this.statusCode != HttpStatus.OK.value() || this.responseBody.get(ConstantesSalesForce.CAMPO_TOTAL_ITEMS).asInt(0) != 1) {
                LOGGER.error(this.responseBody.toString());
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.edicao.erro.integracao"));
            }

            JsonNode registros = this.responseBody.get(ConstantesSalesForce.CAMPO_REGISTROS);
            return registros.get(0).get(ConstantesSalesForce.CAMPO_ID_DOCUMENTO).asText();
        });
    }

    /**
     * Vincula um anexo com um chamado no salesforce.
     *
     * @param idAnexo Identificador do anexo.
     * @param idChamado Identificador do chamado.
     */
    private void vincularAnexoComChamado(String idAnexo, String idChamado) {
        Map<String, String> request = new HashMap<>();
        request.put(CAMPO_ID_DOCUMENTO, idAnexo);
        request.put(CAMPO_ID_CHAMADO_ANEXO, idChamado);
        request.put(CAMPO_VISIBILIDADE_ANEXO, VALOR_CAMPO_VISIBILIDADE_ANEXO);

        prepararRequisicao(urlVincularArquivo, request);
        enviarRequisicaoPost(response -> {
            prepararResposta(response);
            if(this.statusCode != HttpStatus.CREATED.value()) {
                LOGGER.error(this.responseBody.toString());
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.edicao.erro.integracao"));
            }
            return true;
        });
    }


    /**
     * Lista os valores de um picklist do salesforce.
     *
     * @param nomeCampo Nome do campo picklist.
     * @param consumidorHttp Consumidor HTTP da requisição.
     * @return Lista de valores.
     */
    private List<ValorPicklistVo> listarValoresPicklist(String nomeCampo, ConsumidorHttp<List<ValorPicklistVo>> consumidorHttp) {
        String urlPicklists = MessageFormat.format(this.urlListarPicklists, ConstantesSalesForce.RECORD_TYPE_ID, nomeCampo);
        prepararRequisicao(urlPicklists, null);

        return enviarRequisicaoGet(consumidorHttp);
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

    /***
     * Trata a resposta de criacao
     * @param response a resposta
     * @return true em caso de sucesso
     */
    private String trataRespostaCriacao(CloseableHttpResponse response) {
        prepararResposta(response);
        if (this.statusCode != HttpStatus.CREATED.value()) {
            LOGGER.error(this.responseBody.toString());
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.criacao.erro.integracao"));
        }
        return this.responseBody.get(ConstantesSalesForce.CAMPO_ID).asText();
    }

    /***
     * Trata a resposta de criacao
     * @param response a resposta
     * @return true em caso de sucesso
     */
    private boolean trataRespostaEdicao(CloseableHttpResponse response) {
        prepararResposta(response);
        if (this.statusCode != HttpStatus.NO_CONTENT.value()) {
            LOGGER.error(this.responseBody.toString());
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.edicao.erro.integracao"));
        }
        return true;
    }

    /***
     * Trata a resposta do cancelamento de chamado
     * @param response a resposta
     * @return true em caso de sucesso
     */
    private boolean trataRespostaCancelamento(CloseableHttpResponse response) {
        prepararResposta(response);
        if (this.statusCode != HttpStatus.NO_CONTENT.value()) {
            LOGGER.error(this.responseBody.toString());
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("chamado.cancelamento.erro.integracao"));
        }
        return true;
    }

    /**
     * Executa uma consulta SOQL paginada pela API do Salesforce.
     *
     * @param queryConsulta Query utilizada para consultar os dados.
     * @param queryCount Query utilizada para realizar o count total dos dados.
     * @return Resultado paginado da consulta.
     */
    private <T> ResultadoPaginado<T> executarQuerySalesforcePaginada(String queryConsulta, String queryCount, Class<T> classeMapeadora) {
        prepararRequisicao(urlConsulta, null);

        String endpointConsulta = this.instanceUrl.concat(format(this.urlConsulta, queryConsulta));
        String endpointCount = this.instanceUrl.concat(format(this.urlConsulta, queryCount));
        List<T> resultado = enviarRequisicaoGet(endpointConsulta, response -> tratarRespostaConsultaSalesforce(response, classeMapeadora));
        Integer totalItems = enviarRequisicaoGet(endpointCount, this::tratarRespostaCountConsultaSalesforce);

        return new ResultadoPaginado<T>(resultado, totalItems);
    }

    /**
     * Executa uma consulta SOQL pela API do Salesforce.
     *
     * @param queryConsulta Query utilizada para consultar os dados.
     * @return Resultado da consulta.
     */
    private <T> List<T> executarQuerySalesforce(String queryConsulta, Class<T> classeMapeamento) {
        prepararRequisicao(urlConsulta, null);

        String endpointConsulta = this.instanceUrl.concat(format(this.urlConsulta, queryConsulta));
        return enviarRequisicaoGet(endpointConsulta, response -> tratarRespostaConsultaSalesforce(response, classeMapeamento));
    }

    /**
     * Trata a resposta de uma consulta feita para a API do Salesforce.
     *
     * @param response Resposta da integração.
     * @param classeMapeamento Classe utilizada para fazer o mapeamento do json.
     * @return Objeto com o resultado da consulta.
     */
    private <T> List<T> tratarRespostaConsultaSalesforce(CloseableHttpResponse response, Class<T> classeMapeamento) {
        prepararResposta(response);
        if (this.statusCode == HttpStatus.OK.value()) {
            if (this.responseBody.get(CAMPO_DONE) != null && this.responseBody.get(CAMPO_DONE).asBoolean(false)) {
                return UtilitarioJson.toListObjectWithConfigureFailOnUnknowProperties(this.responseBody.get(CAMPO_REGISTROS).toString(), classeMapeamento, false);
            }
            return new ArrayList<>();
        } else {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("Erro.SERVICO_EXTERNO_INDISPONIVEL"));
        }
    }

    /**
     * Trata a resposta do count de uma consulta do salesforce.
     *
     * @param response Response da integração.
     * @return Total de itens encontrados.
     */
    private Integer tratarRespostaCountConsultaSalesforce(CloseableHttpResponse response) {
        prepararResposta(response);
        if (this.statusCode == HttpStatus.OK.value()) {
            if (this.responseBody.get(CAMPO_DONE) != null && this.responseBody.get(CAMPO_DONE).asBoolean(false)) {
                return this.responseBody.get(ConstantesSalesForce.CAMPO_TOTAL_ITEMS).asInt(0);
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
     */
    private String formatarQueryParaPesquisa(String query, boolean possuiPaginacao, FiltroConsultaChamadosVo filtro) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("numeroChamado", tratarParametroLikeNulo(filtro.getNumeroChamado()));
        parametros.put("status", tratarParametroLikeNulo(filtro.getStatus()));
        parametros.put("solicitante", obterParametroSolicitante(filtro));
        parametros.put("emailIntegracao", emailIntegracao);

        List<String> contatos = obterContatosParaConsulta(filtro);
        if(filtro.isContatoFrota() || filtro.isContatoInterno()) {
            query = query.replace(CLAUSULA_CONTATO_REVENDA, "");
            parametros.put("contato", contatos.stream().findFirst().orElse(""));
        } else if(filtro.isContatoRevenda()) {
            query = query.replace(CLAUSULA_CONTATO, "");
            parametros.put("contato", contatos.stream().collect(joining("','", "'", "'")));
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
     * Retorna uma lista com os contatos para uma consulta de um usuário logado.
     *
     * @param usuario Usuário logado.
     * @return Lista com contatos.
     */
    private List<String> obterContatosPorUsuario(Usuario usuario) {
        List<String> contatos = new ArrayList<>();
        String cpfUsuario = formatarCpfApresentacao(usuario.getCpf());
        if(usuario.isFrotista()) {
            contatos.add(cpfUsuario.concat(formatarCnpjApresentacao(usuario.getFrota().getCnpj())));
        } else if(usuario.isRevendedor()) {
            contatos.addAll(
                usuario.getPontosDeVenda().stream()
                    .map(pv -> cpfUsuario.concat(formatarCnpjApresentacao(pv.getCnpj())))
                    .collect(Collectors.toList())
            );
        } else if(usuario.isInterno()) {
            contatos.add(cpfUsuario.concat(formatarCnpjApresentacao(cnpjContatoUsuarioInterno)));
        }
        return contatos;
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
     */
    private String formatarQueryParaConsulta(String query, Map<String, Object> parametros) {
        try {
            for (String parametro : parametros.keySet()) {
                query = query.replace(":" + parametro, parametros.get(parametro).toString());
            }
            return URLEncoder.encode(query, UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(mensagens.obterMensagem("chamado.erro.formatacao.query.integracao"));
            throw new ExcecaoBoleiaRuntime(e);
        }
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
