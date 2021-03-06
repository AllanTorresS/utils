package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutenticacaoUsuarioDados;
import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.ITokenDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dados.IUsuarioMotoristaDados;
import ipp.aci.boleia.dados.IVerificacaoDispositivoDados;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoAutenticacaoRemota;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import ipp.aci.boleia.util.validador.ValidadorCpf;
import ipp.aci.boleia.util.validador.ValidadorEmail;
import ipp.aci.boleia.util.validador.ValidadorEntidade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.TELEFONE_CENTRAL_ATENDIMENTO_NUMERO;
import static ipp.aci.boleia.dominio.enums.FuncionalidadePorVersaoAplicativoMotorista.FLUXO_USUARIO_MOTORISTA;
import static ipp.aci.boleia.util.UtilitarioComparacao.compararVersoes;

/**
 * Implementa as regras de negocio relacionadas a entidade Usu??rio
 */
@Component
public class UsuarioSd {

    // 30 minutos (em milissegundos)
    private static final long TEMPO_BLOQUEIO_TEMPORARIO = 1800000;

    private static final int MAX_TENTATIVAS_LOGIN = 5;

    private static final String MARCA_APPLE = "ios";

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IUsuarioDados repositorio;

    @Autowired
    private IMotoristaDados repositorioMotorista;

    @Autowired
    private IUsuarioMotoristaDados usuarioMotoristaDados;

    @Autowired
    private IVerificacaoDispositivoDados verificacaoDispositivoDados;

    @Autowired
    private ITokenDados tokenDados;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private EmailSd emailSd;

    @Autowired
    private SmsSd smsSd;

    @Autowired
    private IAutenticacaoUsuarioDados autenticacaoDados;

    @Autowired
    private IConfiguracaoSistemaDados configuracaoSistema;

    @Autowired
    private ValidadorEntidade validadorEntidade;

    /**
     * Exclui o usu??rio com valida????o de tipo de perfil
     *
     * @param id identificador do usu??rio
     */
    public void validarExclusaoPerfil(long id) {
        Usuario usuarioAExcluir = repositorio.obterPorId(id);
        usuarioAExcluir.setPerfis(null);
        repositorio.armazenar(usuarioAExcluir);
        repositorio.excluir(id);
    }

    /**
     * Registra uma tentativa frustrada de autenticacao do usuario. Caso ultrapasse o limite de
     * MAX_TENTATIVAS_LOGIN, bloqueia o usuario por TEMPO_BLOQUEIO_TEMPORARIO minutos
     *
     * @param usuario O usuario
     * @return O usuario
     */
    public Usuario registrarErroAutenticacao(Usuario usuario) {
        Integer erros = usuario.getNumeroErrosLogin() != null ? usuario.getNumeroErrosLogin() : 0;
        usuario.setNumeroErrosLogin(erros + 1);
        if (usuario.getNumeroErrosLogin() >= MAX_TENTATIVAS_LOGIN) {
            usuario.setBloqueioTemporario(utilitarioAmbiente.buscarDataAmbiente());
        }
        return usuario;
    }

    /**
     * Registra uma tentativa bem sucedida de login na aplicacao
     *
     * @param usuario O usuario
     * @return O usuario
     */
    public Usuario registrarSucessoAutenticacao(Usuario usuario) {
        usuario = removerBloqueioTemporario(usuario);
        usuario.setDataUltimoLogin(utilitarioAmbiente.buscarDataAmbiente());
        return usuario;
    }

    /**
     * Verifica se o usuario correspondente as credenciais informadas eh valido.
     * Lanca excecao caso o usuario nao tenha sido localizado ou caso esteja desativado
     * ou excluido logicamente.
     *
     * @param usuario O usuario, caso localizado
     * @param pdv se o sistema validando o usuario ?? pdv ou outro
     * @return true caso o usuario esteja valido
     * @throws ExcecaoValidacao quando o usuario esta inativo ou bloqueado
     */
    public Boolean validarUsuarioParaRecuperarSenha(Usuario usuario, Boolean pdv) throws ExcecaoValidacao {
        if(usuario == null  || !StatusAtivacao.ATIVO.getValue().equals(usuario.getStatus())
                || (usuario.getExcluido() != null && usuario.getExcluido())) {
            throw new ExcecaoValidacao(Erro.AUTENTICACAO_USUARIO_INVALIDO);
        }
        if(!usuario.isPrecos() && pdv && possuiBloqueioTemporario(usuario)) {
            throw new ExcecaoValidacao(Erro.AUTENTICACAO_USUARIO_BLOQUEADO);
        }
        return true;
    }

    /**
     * Verifica se o usuario esta bloqueado temporariamente por ter excedido o limite de tentativa
     * frustradas de login na aplicacao.
     *
     * @param usuario O usuario
     * @return true caso o usuario esteja bloqueado
     */
    public boolean possuiBloqueioTemporario(Usuario usuario) {
        Date bloqueadoEm = usuario.getBloqueioTemporario();
        if (bloqueadoEm != null) {
            Date agora = utilitarioAmbiente.buscarDataAmbiente();
            return agora.getTime() - bloqueadoEm.getTime() < TEMPO_BLOQUEIO_TEMPORARIO;
        }
        return false;
    }

    /**
     * Remove o bloqueio temporario do usuario
     *
     * @param usuario O usuario a ser desbloqueado
     * @return O usuario desbloqueado
     */
    public Usuario removerBloqueioTemporario(Usuario usuario) {
        usuario.setBloqueioTemporario(null);
        usuario.setNumeroErrosLogin(0);
        return usuario;
    }

    /**
     * Armazena um usuario no sistema. Caso seja um novo usuario, enviando a ele um email de boas vindas
     * com instrucoes para acesso caso seja indicado.
     *
     * @param usuario O usuario a armazenar
     * @param enviarEmail Indica se deve ser enviado email de boas vindas
     * @return O usuario armazenado
     * @throws ExcecaoValidacao Em caso de violacao nas regras de preenchimento dos dados
     */
    public Usuario armazenarUsuario(Usuario usuario, boolean enviarEmail) throws ExcecaoValidacao {

        boolean enviarEmailPrimeiroAcesso = usuario.getId() == null;
        boolean persistirSenha = usuario.getId() != null && usuario.getSenhaHash() == null;

        if (persistirSenha) {
            Usuario existente = repositorio.obterPorId(usuario.getId());
            usuario.setSenhaHash(existente.getSenhaHash());
            usuario.setSenhaSalt(existente.getSenhaSalt());
        }

        if (usuario.getGestor()) {
            usuario.setPerfis(new ArrayList<>());
        }

        validadorEntidade.validarDados(usuario, false);
        existenteCpf(usuario.getId(), usuario.getCpf() != null ? usuario.getCpf().toString() : null, usuario.isMotorista());
        if (!usuario.isMotorista()) {
            verificarEmailObrigatorio(usuario.getEmail());
        }
        existenteEmail(usuario.getId(), usuario.getEmail());

        usuario = usuario.isMotorista() ?
                repositorio.armazenarSemIsolamentoDeDados(usuario) :
                repositorio.armazenar(usuario);

        if (enviarEmailPrimeiroAcesso && enviarEmail) {
            emailSd.enviarEmailPrimeiroAcesso(usuario.getEmail());
        }

        return usuario;
    }

    /**
     * Valida as informa????es pertinentes ao usu??rio do credenciamento de postos.
     *
     * @param usuario O usuario a validar.
     * @return O usuario validado.
     * @throws ExcecaoValidacao Em caso de violacao nas regras de preenchimento dos dados.
     */
    public Usuario validarUsuarioCredenciamento(Usuario usuario) throws ExcecaoValidacao {
        validadorEntidade.validarDados(usuario, false);
        existenteCpf(usuario.getId(), usuario.getCpf() != null ? usuario.getCpf().toString() : null, usuario.isMotorista());
        verificarEmailObrigatorio(usuario.getEmail());
        existenteEmail(usuario.getId(), usuario.getEmail());

        return usuario;
    }

    /**
     * Valida a senha do usu??rio logado no sistema
     *
     * @param senha Senha informada para valida????o
     * @return True se for v??lida, False se inv??lida
     * @throws ExcecaoAutenticacaoRemota Se ocorrer erro na comunicacao com um sistema de autenticacao remota
     */
    public Boolean confirmaSenhaUsuarioInterno(String senha) throws ExcecaoAutenticacaoRemota {
        Usuario usuarioBase = repositorio.obterPorId(utilitarioAmbiente.getUsuarioLogado().getId());
        return autenticacaoDados.autenticar(usuarioBase.getLogin(), senha);
    }

    /**
     * Verifica se existe um usuario com mesmo email e id diferente
     * @param id do usuario
     * @param email do usuario
     * @throws ExcecaoValidacao caso exista com mesmo email
     */
    public void existenteEmail(Long id, String email) throws ExcecaoValidacao {
        if (email != null) {
            if (!ValidadorEmail.validar(email)) {
                throw new ExcecaoValidacao(mensagens.obterMensagem("usuario.servico.validacao.email.invalido"));
            }
            Usuario usuario = repositorio.obterPorEmail(email);
            if ((id == null && usuario != null) || (usuario != null && !usuario.getId().equals(id))) {
                throw new ExcecaoValidacao(mensagens.obterMensagem(usuario.isInterno() ? "usuario.servico.validacao.logineemail.duplicado" : "usuario.servico.validacao.cpfemail.duplicado"));
            }
        }
    }

    /**
     * Verifica se o e-mail foi informado.
     *
     * @param email o e-mail fornecido
     * @throws ExcecaoValidacao em caso de e-mail inv??lido
     */
    private void verificarEmailObrigatorio(String email) throws ExcecaoValidacao {
        if (email == null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("usuario.servico.validacao.email.obrigatorio"));
        }
    }

    /**
     * Verifica se existe um usuario com mesmo cpf e id diferente
     * @param id do usuario
     * @param cpf do usuario
     * @throws ExcecaoValidacao caso exista com mesmo cpf
     */
    public void existenteCpf(Long id, String cpf) throws ExcecaoValidacao {
        existenteCpf(id,cpf,false);
    }

    /**
     * Verifica se existe um usuario com mesmo cpf e id diferente
     * @param id do usuario
     * @param cpf do usuario
     * @param usuarioMotorista Flag que indica se o usu??rio atual a ser persistido ?? do tipo motorista
     * @throws ExcecaoValidacao caso exista com mesmo cpf
     */
    public void existenteCpf(Long id, String cpf, Boolean usuarioMotorista) throws ExcecaoValidacao {
        if(cpf != null) {
            if (ValidadorCpf.invalidCPF(cpf)) {
                throw new ExcecaoValidacao(mensagens.obterMensagem("usuario.servico.validacao.cpf.invalido"));
            }
            Usuario usuarioEncontrado = repositorio.obterPorCpf(UtilitarioFormatacao.obterLongMascara(cpf), usuarioMotorista);
            if (usuarioEncontrado != null && !usuarioEncontrado.getId().equals(id)) {
                throw new ExcecaoValidacao(mensagens.obterMensagem("usuario.servico.validacao.cpfemail.duplicado"));
            }
        }
    }

    /**
     * Cria o usu??rio de um motorista.
     *
     * @param motorista o motorista cadastrado na solu????o
     * @param usuario o usuario do motorista cadastrado no sistema
     * @return O usuarioMotorista criado
     * @throws ExcecaoValidacao em caso de erro de valida????o
     */
    public UsuarioMotorista criarUsuarioMotorista(Motorista motorista, Usuario usuario) throws ExcecaoValidacao {
        TokenVo token = criarSenhaTemporariaUsuarioMotorista();
        salvarSenhaTemporariaUsuarioMotorista(usuario, token.getToken());

        UsuarioMotorista usuarioMotorista = new UsuarioMotorista(usuario);
        usuarioMotorista.setDataExpiracaoSenhaTemporaria(token.getDataExpiracaoToken());
        armazenarUsuario(usuario, false);
        usuarioMotoristaDados.armazenar(usuarioMotorista);

        if (motorista.getUtilizaAppMotorista()) {
            smsSd.enviarSenhaTemporariaMotorista(motorista, token.getToken());
        }

        return usuarioMotorista;
    }

    /**
     * Regera o token do allow me utilizado pelo usu??rio de um motorista.
     *
     * @param usuarioMotorista Usuario do motorista
     */
    public void gerarTokenAllowMeUsuarioMotorista(UsuarioMotorista usuarioMotorista) {
        if (usuarioMotorista != null) {
            Motorista motorista = UtilitarioLambda.obterPrimeiroObjetoDaLista(usuarioMotorista.getMotoristas());
            boolean usuarioPossuiCadastro = verificacaoDispositivoDados.buscarCadastroUsuarioAllowMe(usuarioMotorista);

            if (usuarioMotorista.getTokenAllowMe() == null && !usuarioPossuiCadastro) {
                verificacaoDispositivoDados.cadastrarUsernameUsuarioETokenDispositivoMotorista(usuarioMotorista, motorista);
            } else {
                verificacaoDispositivoDados.excluirDispositivoAllowMe(usuarioMotorista, motorista);
                verificacaoDispositivoDados.cadastrarTokenUsuarioMotorista(usuarioMotorista, motorista);
            }
            usuarioMotoristaDados.armazenar(usuarioMotorista);
        }
    }

    /**
     * Realiza a ativa????o do OTP para um usu??rio do app Motorista.
     *
     * @param usuarioMotorista O usuario motorista
     * @param plataforma A plataforma do dispositivo
     * @param versao A vers??o do dispositivo
     */
    public void ativarOtpUsuarioMotorista(UsuarioMotorista usuarioMotorista, String plataforma, String versao) {
        String versaoComAtivacaoOtp = FLUXO_USUARIO_MOTORISTA.getVersaoAppAndroid();
        plataforma = plataforma.toLowerCase();
        if(plataforma.equals(MARCA_APPLE)) {
            versaoComAtivacaoOtp = FLUXO_USUARIO_MOTORISTA.getVersaoAppIos();
        }
        if(compararVersoes(versao, versaoComAtivacaoOtp)) {
            verificacaoDispositivoDados.ativarOtpUsuarioMotorista(usuarioMotorista);
        }
        usuarioMotoristaDados.armazenar(usuarioMotorista);
    }

    /**
     * Redefine a senha tempor??ria do usu??rio quando solicitado pelo motorista
     *
     * @param usuario o usu??rio que ter?? a senha redefinida
     */
    public void redefinirSenhaTemporariaUsuarioMotorista(Usuario usuario){
        TokenVo token = criarSenhaTemporariaUsuarioMotorista();
        salvarSenhaTemporariaUsuarioMotorista(usuario, token.getToken());
        salvarDataExpiracaoSenhaTemporaria(usuario, token.getDataExpiracaoToken());
        usuario.setBloqueioTemporario(null);
        usuario.setNumeroErrosLogin(0);
        repositorio.armazenarSemIsolamentoDeDados(usuario);
        Motorista motorista = repositorioMotorista.obterUnicoPorCpfSemIsolamento(usuario.getCpf());

        if (motorista.getUtilizaAppMotorista()) {
            smsSd.enviarSenhaTemporariaMotorista(motorista, token.getToken());
        }
    }

    /**
     * Cria a senha tempor??ria do usu??rio de um motorista.
     *
     * @return o token contendo a senha tempor??ria e sua data de expira????o
     */
    private TokenVo criarSenhaTemporariaUsuarioMotorista() {
        return tokenDados.novoToken(TipoToken.APP_MOTORISTA);
    }


    /**
     * Salva a senha tempor??ria do usu??rio de um motorista.
     *
     * @param usuario o usu??rio
     * @param senhaTemporaria sua senha tempor??ria
     */
    private void salvarSenhaTemporariaUsuarioMotorista(Usuario usuario, String senhaTemporaria) {
        String salt = UtilitarioCriptografia.toBase64(UtilitarioCriptografia.gerarSaltBCrypt());
        usuario.setSenhaSalt(salt);
        usuario.setSenhaHash(UtilitarioCriptografia.calcularHashBCrypt(UtilitarioCriptografia.toBase64(senhaTemporaria.getBytes()), salt));
        usuario.setDataUltimoLogin(null);
    }

    /**
     * Atualiza a data de expira????o da senha tempor??ria
     *
     * @param usuario o usu??rio que ter?? a senha atualizada
     * @param dataExpiracaoSenhaTemporaria a data de expira????o da nova senha
     */
    private void  salvarDataExpiracaoSenhaTemporaria(Usuario usuario, Date dataExpiracaoSenhaTemporaria){
        UsuarioMotorista usuarioMotorista = usuarioMotoristaDados.obterPorUsuario(usuario);
        usuarioMotorista.setDataExpiracaoSenhaTemporaria(dataExpiracaoSenhaTemporaria);
        usuarioMotoristaDados.armazenar(usuarioMotorista);
    }

    /**
     * Caso o ponto de venda j?? exista no usu??rio, atualiza-a.
     * Caso contrario insere-a como um novo relacionamento.
     *
     * @param usuario O usu??rio.
     * @param pontoDeVenda Ponto de venda do usu??rio.
     * @return {@link Usuario} Usuario.
     */
    public Usuario atualizarPontosDeVenda(Usuario usuario, PontoDeVenda pontoDeVenda) {
        if (pontoDeVenda != null) {
            if(usuario.getPontosDeVenda() == null) {
                usuario.setPontosDeVenda(new ArrayList<>());
            }

            int posicao = -1;
            for(int i = 0; i < usuario.getPontosDeVenda().size(); i++) {
                PontoDeVenda pdv = usuario.getPontosDeVenda().get(i);
                if(pdv.getId().equals(pontoDeVenda.getId())) {
                    posicao = i;
                    break;
                }
            }

            if(posicao >= 0) {
                usuario.getPontosDeVenda().set(posicao, pontoDeVenda);
            } else {
                usuario.getPontosDeVenda().add(pontoDeVenda);
            }
        }
        return usuario;
    }

    /**
     * Obt??m a quantidade total de usuarios do sistema.
     *
     * @return Quantidade total de usuarios
     */
    public long obterQuantidadeTotal(){
        return repositorio.obterQuantidadeTotal();
    }

    /**
     * Obt??m a quantidade total de usuarios de um tipo de perfil.
     * @param tipoPerfilUsuario Tipo de perfil
     * @return Quantidade total de usuarios de um tipo de perfil
     */
    public long obterQuantidadeTotalAtivosDeTipoPerfil(TipoPerfilUsuario tipoPerfilUsuario){
        return repositorio.obterQuantidadeTotalAtivosDeTipoPerfil(tipoPerfilUsuario);
    }

    /**
     * Retorna true caso as credenciais informadas estejam compativeis com aquelas armazenadas em banco de dados
     *
     * @param usuario o usuario localizado no banco de dados
     * @param credenciais A senha informada pelo usuario
     * @return True caso as credenciais estejam de acordo com o esperado
     */
    public boolean confirmarSenhaUsuarioExterno(Usuario usuario, String credenciais) {
        if (StringUtils.isBlank(usuario.getSenhaHash()) || StringUtils.isBlank(usuario.getSenhaSalt())) {
            String numeroTelefone = configuracaoSistema.buscarConfiguracoes(TELEFONE_CENTRAL_ATENDIMENTO_NUMERO).getParametro();
            throw new BadCredentialsException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_CREDENCIAIS_INDEFINIDAS, numeroTelefone));
        }
        byte[] senha = converterCredenciaisParaByteArray(credenciais);
        byte[] salt = UtilitarioCriptografia.fromBase64(usuario.getSenhaSalt());
        byte[] hashEsperado = UtilitarioCriptografia.fromBase64(usuario.getSenhaHash());
        return UtilitarioCriptografia.verificarHashBCrypt(senha, salt, hashEsperado);
    }

    /**
     * Le a senha do usuario como um vetor de bytes
     * @param credenciais A senha do usuario
     * @return A senha em byte array
     */
    private byte[] converterCredenciaisParaByteArray(String credenciais)  {
        return credenciais.getBytes(StandardCharsets.UTF_8);
    }
}