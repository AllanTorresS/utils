package ipp.aci.boleia.dominio.servico;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IDispositivoMotoristaDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.ITokenDados;
import ipp.aci.boleia.dados.IVerificacaoDispositivoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.SaldoFrota;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.enums.TipoItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.dominio.vo.CoordenadaGeograficaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa as regras de negocio relacionadas a entidade DispositivoMotorista
 */
@Component
public class DispositivoMotoristaSd {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispositivoMotoristaSd.class);

    @Autowired
    private IDispositivoMotoristaDados repositorio;

    @Autowired
    private IMotoristaDados repositorioMotorista;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

    @Autowired
    private IPontoDeVendaDados repositorioPontoDeVenda;

    @Autowired
    private ITokenDados tokenDados;

    @Autowired
    private IVerificacaoDispositivoDados verificacaoDispositivoDados;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Autowired
    private FrotaPontoVendaSd frotaPontoVendaSd;

    /**
     * Cria um novo dispositivo para um motorista
     *
     * @param motorista o dono do dispositivo
     * @return o dispositivo cadastrado
     * @throws ExcecaoValidacao quando ocorrer
     */
    public DispositivoMotorista criarNovoDispositivo(Motorista motorista) throws ExcecaoValidacao {

        if (StatusAtivacao.INATIVO.getValue().equals(motorista.getStatus())
                || motorista.getDddTelefoneCelular() == null || motorista.getTelefoneCelular() == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("dispositivo.incluir.validacao.motoristaInvalido"));
        }

        DispositivoMotorista dispositivo = new DispositivoMotorista();
        dispositivo.setFrota(motorista.getFrota());
        dispositivo.setMotorista(motorista);
        dispositivo.setStatusHabilitacao(StatusHabilitacao.NAO_HABILITADO.getValue());
        dispositivo.setStatusBloqueio(StatusBloqueio.DESBLOQUEADO.getValue());

        return gerarArmazenarTokenHabilitacao(dispositivo);
    }

    /**
     * Gera um novo token de curta duracao a ser utilizadao par aa habilitacao do dispositivo
     *
     * @param dispositivo dispositivo a atualizar com novo token
     * @return O dispositivo alterado
     */
    public DispositivoMotorista gerarArmazenarTokenHabilitacao(DispositivoMotorista dispositivo) {
        TokenVo token = tokenDados.novoToken(TipoToken.APP_MOTORISTA);
        dispositivo.setToken(token.getToken());
        dispositivo.setDataExpiracaoToken(token.getDataExpiracaoToken());
        if(!dispositivo.getStatusHabilitacao().equals(StatusHabilitacao.REGERADO.getValue())) {
            dispositivo.setStatusHabilitacao(StatusHabilitacao.NAO_HABILITADO.getValue());
        }
        dispositivo.setIdAllowMe(null);
        dispositivo.setTokenAllowMe(null);
        return repositorio.armazenar(dispositivo);
    }

    /**
     * Habilita um dado dispositivo no cadastro do revendedor
     *
     * @param cpfMotorista O cpf do motorista
     * @param celular O celular
     * @param tokenHabilitacao O token de habilitacao
     * @param udid O UDID do aparelho
     * @param numeroSerie  O numero de serie do dispositivo
     * @param modelo O modelo do aparelho
     * @param marca A marca do aparelho
     * @param sistemaOperacional O SO do aparelho
     * @param tokenPush o tokenPush do aparelho
     * @param versaoApp A versao do app do motorista
     * @return O dispositivo encontrado e habilitado
     * @throws ExcecaoValidacao Em caso de erro de validacao do token ou token não encontrado
     */
    public DispositivoMotorista habilitar(String cpfMotorista, String celular, String tokenHabilitacao, String udid, String numeroSerie, String modelo, String marca, String sistemaOperacional, String tokenPush, String versaoApp) throws ExcecaoValidacao {
        DispositivoMotorista dispositivo = null;
        if (StringUtils.isNotBlank(cpfMotorista)
            && StringUtils.isNotBlank(celular)
            && StringUtils.isNotBlank(tokenHabilitacao)) {
            dispositivo = repositorio.obterPorCpfCelularToken(cpfMotorista, celular, tokenHabilitacao);
        }
        if (dispositivo == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO,
                    mensagens.obterMensagem("dispositivo.servico.validacao.token.invalido"));
        }
        validarDispositivo(dispositivo);
        if (dispositivo.getTokenAllowMe() == null || dispositivo.getIdAllowMe() == null) {
            throw new ExcecaoValidacao(Erro.DISPOSITIVO_SEM_INTEGRACAO);
        }
        if (dispositivo.getStatusHabilitacao().equals(StatusHabilitacao.HABILITADO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_JA_HABILITADO, mensagens.obterMensagem("dispositivo.servico.validacao.token.habilitado"));
        }
        if (dispositivo.getDataExpiracaoToken().getTime() < ambiente.buscarDataAmbiente().getTime()) {
            dispositivo.setStatusHabilitacao(StatusHabilitacao.EXPIRADO.getValue());
            repositorio.armazenar(dispositivo);
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_TOKEN_EXPIRADO, mensagens.obterMensagem("dispositivo.servico.validacao.token.expirado"));
        }

        dispositivo.setUdid(udid);
        dispositivo.setNumeroSerie(numeroSerie);
        dispositivo.setModelo(modelo);
        dispositivo.setMarca(marca);
        dispositivo.setSistemaOperacional(sistemaOperacional);
        dispositivo.setStatusHabilitacao(StatusHabilitacao.HABILITADO.getValue());
        dispositivo.setDataExpiracaoToken(null);
        dispositivo.setTokenAutenticacao(utilitarioJwt.criarTokenDispositivoMotorista(dispositivo));
        dispositivo.setTokenPush(tokenPush);
        dispositivo.setVersaoDoApp(versaoApp);

        return repositorio.armazenar(dispositivo);
    }

    /**
     * Bloqueia/Desbloqueia um dado dispositivo no cadastro
     *
     * @param id O id do dispositivo
     * @param status O status de bloqueio desejado
     * @return O dispositivo alterado
     * @throws ExcecaoValidacao Em caso de erro para dispositivo não encontrado
     */
    public DispositivoMotorista editarBloqueio(Long id, StatusBloqueio status) throws ExcecaoValidacao {
        DispositivoMotorista dispositivo = repositorio.obterPorId(id);
        if (dispositivo != null) {
            dispositivo.setStatusBloqueio(status.getValue());
            return repositorio.armazenar(dispositivo);
        } else {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("dispositivo.servico.validacao.id.inexistente"));
        }
    }

    /**
     * Remove um dispositivo do AllowMe
     *
     * @param dispositivo o dispositivo a ser removido
     */
    public void removerDispositivoAllowMe(DispositivoMotorista dispositivo) {
        verificacaoDispositivoDados.excluirDispositivo(dispositivo);
    }

    /**
     * Gera um novo token para dispositivo
     *
     * @param cpfMotorista O CPF do motorista
     * @param celular O numero do celular
     * @param tokenHabilitacao O token antigo
     */
    public void reenviarToken(String cpfMotorista, String celular, String tokenHabilitacao) {
        DispositivoMotorista dispositivo = null;
        if (StringUtils.isNotBlank(cpfMotorista)
                && StringUtils.isNotBlank(celular) && StringUtils.isNotBlank(tokenHabilitacao)) {
            dispositivo = repositorio.obterPorCpfCelularToken(cpfMotorista, celular, tokenHabilitacao);
        }
        if (dispositivo != null) {
            try {
                validarDispositivo(dispositivo);
                if (!dispositivo.getMotorista().getStatus().equals(StatusAtivacao.INATIVO.getValue()) &&
                        (dispositivo.getDataReenvioToken() == null
                         || UtilitarioCalculoData.adicionarDiasData(dispositivo.getDataReenvioToken(), 1).getTime() <= ambiente.buscarDataAmbiente().getTime())
                    ) {
                    dispositivo.setDataReenvioToken(ambiente.buscarDataAmbiente());
                    gerarArmazenarTokenHabilitacao(dispositivo);
                }
            } catch (ExcecaoValidacao e) {
                // Suprimindo o lançamento de exceção para evitar enumeração de usuários.
                // Caso seja informado, por exemplo, um CPF não cadastrado no sistema,
                // a resposta não pode deixar transparecer essa informação, para evitar que
                // um ofensor seja capaz de descobrir motoristas válidos no sistema.
                LOGGER.error("Erro de validacao ao tentar reenviar token do dispositivo do motorista", e);
            }
        }
    }

    /**
     * Realiza as validações comuns ao dispositivo
     *
     * @param dispositivo O dispositivo do motorista
     * @throws ExcecaoValidacao caso ocorra
     */
    private void validarDispositivo(DispositivoMotorista dispositivo) throws ExcecaoValidacao {
        if (dispositivo.getStatusBloqueio() != null && dispositivo.getStatusBloqueio().equals(StatusBloqueio.BLOQUEADO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_BLOQUEADO,
                    mensagens.obterMensagem("dispositivo.servico.validacao.bloqueado"));
        }
        if (dispositivo.getMotorista().getStatus().equals(StatusAtivacao.INATIVO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_INATIVO,
                    mensagens.obterMensagem("dispositivo.servico.validacao.motorista.inativo"));
        }
    }

    /**
     * Verifica se a frota e motorista encontram-se ativos.<br>
     * Caso o motorista possua um registro de {@link DispositivoMotorista} associado,
     * este também é validado.
     *
     * @param idFrota a frota selecionada no app motorista
     * @param cpf o cpf do usuário do app
     * @return retorna o motorista validado
     * @throws ExcecaoValidacao caso o motorista esteja inativo na frota
     */
    public Motorista validarFrotaMotorista(Long idFrota, Long cpf) throws ExcecaoValidacao {
        Motorista motorista = repositorioMotorista.obterPorCpfFrotaSemIsolamento(cpf, idFrota);
        if (motorista == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("dispositivo.servico.validacao.motorista.nao.encontrado"));
        }
        if (!motorista.getUtilizaAppMotorista()){
            throw new ExcecaoValidacao(Erro.MOTORISTA_NAO_UTILIZA_APP_MOTORISTA, mensagens.obterMensagem("dispositivo.servico.validacao.motorista.nao.utilizaAppMotorista"));
        }
        if (motorista.getStatus().equals(StatusAtivacao.INATIVO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_INATIVO, mensagens.obterMensagem("dispositivo.servico.validacao.motorista.inativo"));
        }
        if (motorista.getFrota().getStatus().equals(StatusAtivacao.INATIVO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_INATIVO, mensagens.obterMensagem("dispositivo.servico.validacao.frota.inativa"));
        }
        return motorista;
    }

    /**
     * Obtem o dispositivo motorista atraves do token JWT da requisicao
     *
     * @param tokenAutenticacao token jwt da requisicao
     * @return dipositivo motorista do token
     */
    public DispositivoMotorista obterPorTokenAutenticacao(String tokenAutenticacao) {
        DecodedJWT jwt = JWT.decode(tokenAutenticacao);
        if (TipoTokenJwt.DISPOSITIVO_MOTORISTA.equals(utilitarioJwt.getTipoToken(jwt))) {
            Long id = utilitarioJwt.getIdDispositivoMotorista(jwt);
            return repositorio.obterPorId(id);
        }
        return null;
    }

    /**
     * Realiza as validações dos dados fornecidos na solicitação de ajuda
     *
     * @param mensagem A mensagem de ajuda
     * @param idNota O id da nota
     * @param idFrota O id da frota
     * @param idMotorista O id do motorista
     * @return dados da transacao (nota) para qual se está solicitando ajuda
     * @throws ExcecaoValidacao em caso de erro na validação dos dados fornecidos
     */
    public AutorizacaoPagamento validarRequisicaoSolicitacaoAjuda(String mensagem, Long idNota, Long idFrota, Long idMotorista) throws ExcecaoValidacao {
        if (StringUtils.isBlank(mensagem)) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("dispositivo.servico.validacao.solicitacaoAjuda.mensagem.invalida"));
        }
        AutorizacaoPagamento transacao = repositorioAutorizacaoPagamento.obterNotaParaSolicitacaoAjudaMotorista(idNota, idFrota, idMotorista);
        if (transacao == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO,mensagens.obterMensagem("dispositivo.servico.validacao.solicitacaoAjuda.nota.invalida"));
        }
        return transacao;
    }

    /**
     * Verifica se há um item do tipo Abastecimento na lista de itens pertencentes à transação. Retorna este item
     * caso ele seja encontrado.
     *
     * @param itensTransacao itens da transaçao
     * @return item de transação do tipo abastecimento
     * @throws ExcecaoValidacao Caso não haja item do tipo Abastecimento
     */
    public ItemAutorizacaoPagamento validarItensTransacao(List<ItemAutorizacaoPagamento> itensTransacao) throws ExcecaoValidacao {

        ItemAutorizacaoPagamento abastecimento = itensTransacao.stream()
                .filter(it -> TipoItemAutorizacaoPagamento.isAbastecimento(it.getTipoItem()))
                .findFirst().orElse(null);

        if (abastecimento == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO,
                    mensagens.obterMensagem("dispositivo.servico.validacao.solicitacaoAjuda.nota.sem.abastecimento"));
        }

        return abastecimento;
    }

    /**
     * Verifica se um motorista possui dispositivo
     *
     * @param idMotorista id do motorista
     * @return true se motorista possui dispositivo, false caso contrário
     */
    public boolean motoristaPossuiDispositivo(Long idMotorista) {
        List<DispositivoMotorista> dispositivoMotoristas = repositorio.obterPorMotorista(idMotorista);
        return dispositivoMotoristas != null && !dispositivoMotoristas.isEmpty();
    }

    /**
     * Verifica se um motorista possui dispositivo habilitado
     *
     * @param idMotorista id do motorista
     * @return true se motorista possui dispositivo, false caso contrário
     */
    public boolean motoristaPossuiDispositivoHabilitado(Long idMotorista) {
        List<DispositivoMotorista> dispositivoMotoristas = repositorio.obterPorMotorista(idMotorista);
        for (DispositivoMotorista dispositivo : dispositivoMotoristas) {
            if (StatusHabilitacao.HABILITADO.getValue().equals(dispositivo.getStatusHabilitacao())){
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se um veiculo possui saldo suficiente
     * @param restricaoSaldoVeiculo configuracao da restricao de uso
     * @param veiculo veiculo
     * @param saldoFrota a entidade saldoFrota
     * @return True caso o veiculo nao possua saldo suficiente
     */
    public boolean validarSaldoFrotaCota(FrotaParametroSistema restricaoSaldoVeiculo, SaldoFrota saldoFrota, Veiculo veiculo) {
        boolean restricaoViolada = false;
        if (saldoFrota == null || saldoFrota.getSaldoCorrente().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcecaoBoleiaRuntime(Erro.FROTA_SALDO_INSUFICIENTE);
        }
        if (restricaoSaldoVeiculo != null && restricaoSaldoVeiculo.isAtivo() && restricaoSaldoVeiculo.getCotaVeiculoVisivelMotorista()) {
            restricaoViolada = verificarSaldoCotaSuficienteParaVeiculoAtivo(restricaoSaldoVeiculo, veiculo);
        }
        return restricaoViolada;
    }

    /**
     * Verifica se um veiculo ativo dado possui saldo suficiente
     *
     * @param restricaoSaldoVeiculo Configuracao da restricao de uso
     * @param veiculo O veiculo a ser verificado
     * @return True caso o veiculo nao possua saldo suficiente
     */
    private boolean verificarSaldoCotaSuficienteParaVeiculoAtivo(FrotaParametroSistema restricaoSaldoVeiculo, Veiculo veiculo) {
        boolean restricaoViolada = false;
        if (veiculo.isAgregado() && veiculo.getSaldoVeiculo() == null) {
            restricaoViolada = true;
        } else if (veiculo.getSaldoVeiculo() != null) {
            boolean cotaMensal = restricaoSaldoVeiculo.getCotaVeiculoPorAbastecimento() == null || !restricaoSaldoVeiculo.getCotaVeiculoPorAbastecimento();
            boolean cotaEmLitros = restricaoSaldoVeiculo.getEmLitros() != null && restricaoSaldoVeiculo.getEmLitros();
            if (!veiculo.getSaldoVeiculo().isSaldoSuficienteParaPreAutorizar(cotaEmLitros, cotaMensal)) {
                restricaoViolada = true;
            }
        }
        if (restricaoViolada && restricaoSaldoVeiculo.isRestritivo()) {
            throw new ExcecaoBoleiaRuntime(Erro.VEICULO_SALDO_COTA_INSUFICIENTE);
        }
        return restricaoViolada;
    }

    /**
     * Verifica se há algum posto habilitado próximo à localização do pedido, e se o parâmetro de uso de Postos Permitidos
     * estiver ativo, verifica se há algum posto permitido para abastecimento próximo à localização do pedido.
     *
     * @param frota Frota do motorista
     * @param ponto A localizacao
     * @param precisao Precisão da Localização do GPS
     * @return A lista dos pontos de venda próximos habilitados
     * @throws ExcecaoValidacao Caso nao exista nenhum posto autorizado proximo
     */
    public List<PontoDeVenda>  validarPostoAutorizado(Frota frota, CoordenadaGeograficaVo ponto, BigDecimal precisao) throws ExcecaoValidacao {
        List<PontoDeVenda> postosHabilitados = temPostoHabilitadoProximo(ponto, precisao, frota);
        validarPostosBloqueados(frota, postosHabilitados);
        return postosHabilitados;
    }

    /**
     * Verifica se existe algum posto bloqueado para abastecimentos próximo ao motorista.
     *
     * @param frota Frota do motorista
     * @param postosHabilitados Lista de postos habilitados próximos ao motorista
     * @throws ExcecaoValidacao Exceção lançada caso possua algum posto bloqueado na lista dos habilitados
     *
     */
    private void validarPostosBloqueados(Frota frota, List<PontoDeVenda> postosHabilitados) throws ExcecaoValidacao {
        List<PontoDeVenda> pvsBloqueados = postosHabilitados.stream().filter(pv -> frotaPontoVendaSd.validarBloqueio(frota.getId(), pv.getId())).collect(Collectors.toList());
        if(!pvsBloqueados.isEmpty()) {
            if(pvsBloqueados.size() < postosHabilitados.size()) {
                throw new ExcecaoValidacao(Erro.FROTA_PONTO_VENDA_BLOQUEADO_NA_REGIAO);
            } else {
                throw new ExcecaoValidacao(Erro.FROTA_PONTO_VENDA_BLOQUEADO);
            }
        }
    }

    /**
     * Verifica se há algum posto habilitado dentro de um determinado raio de proximidade da localização do pedido
     * @param ponto O posto em questão
     * @param precisao Precisão da Localização do GPS
     * @param frota A frota do motorista
     * @return A lista dos pontos de venda próximos
     * @throws ExcecaoValidacao Quando não há nenhum posto dentro do raio de proximidade, ou nenhum habilitado.
     */
    private List<PontoDeVenda> temPostoHabilitadoProximo(CoordenadaGeograficaVo ponto, BigDecimal precisao, Frota frota) throws ExcecaoValidacao {
        double distancia = precisao != null && precisao.doubleValue() <= 250 ? 0.5 : 1.0;
        List<PontoDeVenda> postosProximos;
        FiltroPesquisaLocalizacaoVo filtro;
        filtro = new FiltroPesquisaLocalizacaoVo(ponto, distancia, null);
        postosProximos = repositorioPontoDeVenda.obterPontoDeVendaPorLimitesLocalizacao(filtro);
        postosProximos = postosProximos.stream().filter(p-> frotaPontoVendaSd.validarVisibilidade(frota.getId(), p)).collect(Collectors.toList());
        List<PontoDeVenda> postosHabilitados = new ArrayList<>();
        if (postosProximos.isEmpty()) {
            throw new ExcecaoValidacao(Erro.SEM_PV_PROXIMO);
        } else {
            for (PontoDeVenda pv : postosProximos){
                if (pv.isHabilitado()) {
                    postosHabilitados.add(pv);
                }
            }
        }

        if (postosHabilitados.isEmpty()) {
            throw new ExcecaoValidacao(Erro.SEM_PV_HABILITADO_PROXIMO);
        }

        return postosHabilitados;
    }

    /**
     * Valida o dispositivo para usuários da versão antiga do aplicativo do motorista,
     * onde o JWT está vinculado a um registro de {@link DispositivoMotorista}.
     *
     * @param tokenAutenticacao o token de autenticação
     * @return o dispositivo do motorista
     * @throws ExcecaoValidacao caso ocorra algum erro de validação
     */
    public DispositivoMotorista validarTokenAutenticacaoDispositivoMotorista(String tokenAutenticacao) throws ExcecaoValidacao {
        DispositivoMotorista dispositivo = obterPorTokenAutenticacao(tokenAutenticacao);
        if (dispositivo == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_TOKEN_AUTENTICACAO);
        } else if (dispositivo.getStatusHabilitacao() != null && dispositivo.getStatusHabilitacao().equals(StatusHabilitacao.NAO_HABILITADO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_NAO_HABILITADO,
                    mensagens.obterMensagem("dispositivo.servico.validacao.naoHabilitado"));
        }
        validarDispositivo(dispositivo);
        return dispositivo;
    }
}

