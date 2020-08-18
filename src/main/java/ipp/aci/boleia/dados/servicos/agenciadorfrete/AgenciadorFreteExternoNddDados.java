package ipp.aci.boleia.dados.servicos.agenciadorfrete;

import ipp.aci.boleia.dados.IAgenciadorFreteExternoDados;
import ipp.aci.boleia.dados.IChaveValorDados;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.servicos.chavevalor.ChaveValorDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Abastecimento;
import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.RespostaTransacaoNddVo;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.SaldoNddVo;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.TokenNddBodyVo;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.TokenNddResponseVo;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.TokenNddVo;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.TransacaoNddVo;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd.TransacaoSaldoNddVo;
import ipp.aci.boleia.util.ConstantesNdd;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.ExcecaoServicoIndisponivel;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.negocio.UtilitarioSessao;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsável pela integração com a NDD
 */
@Repository
public class AgenciadorFreteExternoNddDados implements IAgenciadorFreteExternoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgenciadorFreteExternoNddDados.class);

    private static final String CHAVE_TOKEN_AUTENTICACAO = "NDD_TOKEN_AUTENTICACAO";
    private static final String NOME_CHAVE_NDD = "NDD";

    @Value("${ndd.api.endereco.url}")
    private String nddBaseUrl;

    @Value("${ndd.api.autenticacao.url}")
    private String nddAutenticacaoBaseUrl;

    @Value("${ndd.api.client.id}")
    private String clientId;

    @Value("${ndd.api.client.secret}")
    private String clientSecret;

    @Value("${ndd.api.path}")
    private String apiPath;

    @Value("${ndd.api.auth.path}")
    private String autenticacaoPath;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IClienteHttpDados clienteRest;

    @Autowired
    private IChaveValorDados<TokenNddVo> chaveValorDados;

    @Override
    public BigDecimal obterSaldoDeSaqueDisponivel(Transacao transacao) throws ExcecaoServicoIndisponivel, ExcecaoValidacao {
        String token = obterTokenAutenticacao();
        this.atualizarSaldo(transacao, token);
        String url = nddBaseUrl + apiPath + ConstantesNdd.SALDO_API_ENDPOINT.replace("{orderNumber}", transacao.getPedido().getNumero());
        SaldoNddVo saldo = clienteRest.doGet(url, montarHeader(token), this::obterSaldoNdd);
        if(saldo == null || saldo.getAmountAvailable() == null) {
            throw new ExcecaoServicoIndisponivel(mensagens.obterMensagem("agenciador.frete.ndd.servico.inexistente"));
        }
        return saldo.getAmountAvailable();
    }

    /**
     * Atualiza o saldo no fornecedor através da transação
     * @param transacao A Tramsação a ser atualizada
     * @param token o token para atualizar o saldo
     * @throws ExcecaoValidacao Caso os dados de entrada estejam inconsistentes
     * @throws ExcecaoServicoIndisponivel Caso o fornecedor esteja indisponível
     */
    private void atualizarSaldo(Transacao transacao , String token) throws ExcecaoValidacao, ExcecaoServicoIndisponivel {
        validaTransacao(transacao);
        TransacaoSaldoNddVo body = new TransacaoSaldoNddVo(transacao);
        String url = nddBaseUrl + apiPath + ConstantesNdd.ATUALIZAR_TRANSACAO;
        trataResposta(clienteRest.doPutJson(url, body, montarHeader(token), this::obterRespostaTransacao));
    }

    @Override
    public void confirmarTransacao(Transacao transacao) throws ExcecaoValidacao, ExcecaoServicoIndisponivel {
        validaTransacao(transacao);
        TransacaoNddVo body = new TransacaoNddVo(transacao);
        String token = obterTokenAutenticacao();
        String url = nddBaseUrl + apiPath + ConstantesNdd.CONFIRMAR_TRANSACAO;
        trataResposta(clienteRest.doPostJson(url, body, montarHeader(token), this::obterRespostaTransacao));
    }

    @Override
    public void cancelarTransacao(Pedido pedido) throws ExcecaoValidacao, ExcecaoServicoIndisponivel {
        TransacaoNddVo body = new TransacaoNddVo(pedido);
        String token = obterTokenAutenticacao();
        String url = nddBaseUrl + apiPath + ConstantesNdd.CANCELAR_TRANSACAO;
        trataResposta(clienteRest.doPostJson(url, body, montarHeader(token), this::obterRespostaTransacao));
    }

    /***
     * Trata a resposta da NDD
     * @param resposta
     * @throws ExcecaoServicoIndisponivel
     */
    private void trataResposta(RespostaTransacaoNddVo resposta) throws ExcecaoServicoIndisponivel {
        if(resposta == null || resposta.getCode() == null) {
            throw new ExcecaoServicoIndisponivel(mensagens.obterMensagem("agenciador.frete.ndd.servico.inexistente"));
        }
    }

    /***
     * Valida a transação
     * @param transacao a ser validada
     * @throws ExcecaoValidacao
     */
    private void validaTransacao(Transacao transacao) throws ExcecaoValidacao {
        if(transacao == null || transacao.getPedido() == null || transacao.getAbastecimento() == null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.pedido.inexistente"));
        }
        Abastecimento abastecimento = transacao.getAbastecimento();
        String numero = transacao.getPedido().getNumero();
        if(numero == null || abastecimento.getPrecoCombustivel() == null || abastecimento.getLitragem() == null){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.pedido.invalido"));
        }
    }

    /**
     * Monta o header do request com o token associado
     * @param token O token de autenticação da Ndd
     * @return O header completo
     */
    private Header[] montarHeader(String token) {
        String Authorization = "Authorization";
        String tipoToken = "Bearer ";
        return new Header[]{new BasicHeader(Authorization, tipoToken + token)};
    }

    /**
     * Obtém o saldo através de um request a Ndd
     * @param response O retorno da Ndd
     * @return Um vo que representa o saldo na Ndd
     */
    private SaldoNddVo obterSaldoNdd(CloseableHttpResponse response)  {
        try {
            return UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(response, SaldoNddVo.class, false);
        } catch (Exception e) {
            String prefixoErroLog = "NDD - Erro: ";
            LOGGER.error(prefixoErroLog, e);
            return null;
        }
    }

    /**
     * Obtém o saldo através de um request a Ndd
     * @param response O retorno da Ndd
     * @return Um vo que representa o saldo na Ndd
     */
    private RespostaTransacaoNddVo obterRespostaTransacao(CloseableHttpResponse response)  {
        try {
            return UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(response, RespostaTransacaoNddVo.class, false);
        } catch (Exception e) {
            String prefixoErroLog = "NDD - Erro: ";
            LOGGER.error(prefixoErroLog, e);
            return null;
        }
    }

    /**
     * Obtém o token de autenticação
     * @return O token de autenticação
     * @throws ExcecaoServicoIndisponivel Caso a NDD não responda corretamente
     */
    private String obterTokenAutenticacao() throws ExcecaoServicoIndisponivel {
        TokenNddVo token = chaveValorDados.obter(NOME_CHAVE_NDD ,CHAVE_TOKEN_AUTENTICACAO);
        if(token == null || token.getToken() == null) {
            TokenNddBodyVo body = new TokenNddBodyVo(clientId, ConstantesNdd.TIPO_AUTENTICACAO_CRIACAO);
            body.setClientSecret(clientSecret);
            token = obterTokenNdd(body);
        } else if(DateUtils.addMinutes(ambiente.buscarDataAmbiente(), ConstantesNdd.MINUTOS_EXPIRACAO_TOKEN).compareTo(token.getDataExpiracao())>0){
            TokenNddBodyVo body = new TokenNddBodyVo(clientId, ConstantesNdd.TIPO_AUTENTICACAO_ATUALIZACAO);
            body.setRefreshToken(token.getRefreshToken());
            token = obterTokenNdd(body);
        }
        return token.getToken();
    }

    /**
     * Obtém o token da Ndd através de um request
     * @param body O corpo da requisição
     * @return O vo que representa o token da Ndd
     * @throws ExcecaoServicoIndisponivel Caso a ndd não responda corretamente
     */
    private TokenNddVo obterTokenNdd(TokenNddBodyVo body) throws ExcecaoServicoIndisponivel {
        String url = nddAutenticacaoBaseUrl + autenticacaoPath;
        Map<String, String> form = converteBodyParaMap(body);
        TokenNddResponseVo response = clienteRest.doPostFormEncoded(url, form, this::obterTokenNdd);
        if(response == null) {
            throw new ExcecaoServicoIndisponivel(mensagens.obterMensagem("agenciador.frete.ndd.servico.inexistente"));
        }
        String token = response.getAccessToken() ;
        String refreshToken = response.getRefreshToken();
        try {
            Date dataExpiracao = new Date(Integer.parseInt(response.getExpiresIn()));
            TokenNddVo tokenNdd = new TokenNddVo(token, refreshToken, dataExpiracao);
            chaveValorDados.inserir(NOME_CHAVE_NDD ,CHAVE_TOKEN_AUTENTICACAO, tokenNdd);
            return tokenNdd;
        } catch (NumberFormatException e) {
            throw new ExcecaoServicoIndisponivel(mensagens.obterMensagem("agenciador.frete.ndd.servico.inexistente"));
        }
    }

    /**
     * Converte o body da autentocação para enviar como form encoded
     * @param body o corpo da requisição
     * @return O Map convertido
     */
    private Map<String, String> converteBodyParaMap(TokenNddBodyVo body) {
        Map<String,String> map = new HashMap<>();
        map.put("grant_type", body.getGrantType());
        map.put("client_id", body.getClientId());
        if(body.getClientSecret() != null) {
            map.put("client_secret", body.getClientSecret());
        }
        if(body.getRefreshToken() != null) {
            map.put("refresh_token", body.getRefreshToken());
        }
        return map;
    }

    /**
     * Obter o token da NDD a partir do retorno de um request
     * @param response O retorno do request de autenticação
     * @return Um vo que representa o retorno
     */
    private TokenNddResponseVo obterTokenNdd(CloseableHttpResponse response)  {
        try {
            return UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(response, TokenNddResponseVo.class, false);
        } catch (Exception e) {
            String prefixoErroLog = "NDD - Erro: ";
            LOGGER.error(prefixoErroLog, e);
            return null;
        }
    }
}
