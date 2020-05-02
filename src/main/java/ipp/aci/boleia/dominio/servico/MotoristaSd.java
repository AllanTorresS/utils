package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.ITokenDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;

import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCnpjApresentacao;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCpfApresentacao;

/**
 * Implementa as regras de negocio relacionadas a entiadade Motorista
 */
@Component
public class MotoristaSd {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IMotoristaDados repositorio;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IFrotaDados repositorioFrota;

    @Autowired
    private ITokenDados tokenDados;

    @Autowired
    @Qualifier("Redis")
    private Sincronizador sincronizador;

    @Value("${concorrencia.lock.frota.importacao-motorista}")
    private String prefixoLockFrotaImportacaoMotorista;

    private MapeadorLock<Long> mapeadorLockFrotaImportacaoMotorista;

    /**
     * Configura o monitor de autorização de pagamento
     */
    @PostConstruct
    public void inicializador() {
        mapeadorLockFrotaImportacaoMotorista = new MapeadorLock<>(sincronizador, this::construirChaveFrotaImportacaoMotorista);
    }

    /**
     * Cria a chave do lock de importacao motorista por frota
     *
     * @param idFrota id da Frota
     * @return chave do lock para importacao em lote de motorista
     */
    private String construirChaveFrotaImportacaoMotorista(Long idFrota) {
        return String.format("%s:%s", prefixoLockFrotaImportacaoMotorista, idFrota.toString());
    }

    /**
     * Gera o codigo de abastecimento para um motorista
     *
     * @return O codigo
     */
    public String gerarSenhaAbastecimento() {
        return UtilitarioFormatacao.formatarNumeroZerosEsquerda(UtilitarioCriptografia.getRandom().nextInt(1000000), 6);
    }

    /**
     * Gera a mensagem contendo o codigo de abastecimento a ser enviada ao motorista
     *
     * @param entidade A entidade persistida
     * @param senhaAbastecimento A senha de abastecimetno em claro
     * @return A mensagem a ser enviada
     */
    public String obterMensagemSmsSenhaContingencia(Motorista entidade, String senhaAbastecimento) {
        String nomeFrota = StringUtils.isNotBlank(entidade.getFrota().getNomeFantasia()) ? entidade.getFrota().getNomeFantasia() : entidade.getFrota().getRazaoSocial();
        String mensagem = mensagens.obterMensagem("motorista.servico.sns.codigo", entidade.getNome(), utilitarioAmbiente.getNomeSistema(), nomeFrota, senhaAbastecimento);
        return UtilitarioFormatacao.removerAcentos(mensagem);
    }

    /**
     * Gera a mensagem contendo o soft token a ser enviada ao motorista
     *
     * @param token A soft token em claro
     * @return A mensagem a ser enviada
     */
    public String obterMensagemSmsSoftToken(TokenVo token) {
        String mensagem = mensagens.obterMensagem("motorista.servico.sns.token.contigencia", token.getToken());
        return UtilitarioFormatacao.removerAcentos(mensagem);
    }

    /**
     * Verifica se o telefone do motorista foi alterado
     *
     * @param idMotorista O id do motorista
     * @param celular O numero de celular do motorista
     * @return se os telefones sao diferentes
     */
    public boolean isTelefoneAlterado(Long idMotorista, String celular) {
        if (idMotorista != null) {
            Motorista motorista = repositorio.obterPorId(idMotorista);
            String celularCompleto = UtilitarioFormatacao.obterDigitosMascara(celular);
            Integer celularAntigo = motorista.getTelefoneCelular();
            Integer dddAntigo = motorista.getDddTelefoneCelular();
            if (celularAntigo != null) {
                return StringUtils.isBlank(celular) ||
                        !(celularAntigo.equals(UtilitarioFormatacao.obterInteiroMascara(celularCompleto.substring(2)))
                                && dddAntigo.equals(UtilitarioFormatacao.obterInteiroMascara(celularCompleto.substring(0, 2))));
            }
            return !StringUtils.isBlank(celular);
        }
        return false;
    }

    /**
     * Verifica se para dado motorista deve-se inserir seu dispositivo
     *
     * @param idMotorista O id do motorista
     * @param celular O numero de celular do motorista
     * @param statusAtivacao O status de ativacao co motorista
     * @return true se deve inserir
     */
    public boolean deveInserirDispositivo(Long idMotorista, String celular, StatusAtivacao statusAtivacao) {
        if (StatusAtivacao.ATIVO.equals(statusAtivacao) && StringUtils.isNotBlank(celular)) {
            if (idMotorista != null) {
                Motorista motorista = repositorio.obterPorId(idMotorista);
                return (motorista.getTelefoneCelular() == null || motorista.getDddTelefoneCelular() == null ||
                        !UtilitarioFormatacao.obterDigitosMascara(celular)
                                .equals(motorista.getDddTelefoneCelular().toString() + motorista.getTelefoneCelular().toString()) ||
                        StatusAtivacao.INATIVO.equals(StatusAtivacao.obterPorValor(motorista.getStatus())));
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida se o motorista informado pertence a frota e se seu
     * status está ativo e se o ele foi cadastrado como usuario do app
     *
     * @param cpf O CPF do motorista
     * @param idFrota identificador unico da frota
     * @param utilizandoCel Indica se o usuario está utilizando o celular ao pre-autorizar um abastecimento
     * @return O motorista validado
     * @throws ExcecaoValidacao Se o cpf buscado não for encontrado ou não pertencer a frota
     */
    public Motorista validarMotoristaParaContingencia(String cpf, Long idFrota, boolean utilizandoCel) throws ExcecaoValidacao {
        Motorista motorista = validarMotorista(cpf, idFrota);
        if (!motorista.getUtilizaAppMotorista() && !utilizandoCel) {
            throw new ExcecaoValidacao(Erro.MOTORISTA_NAO_UTILIZA_APP_MOTORISTA, mensagens.obterMensagem("motorista.servico.sem.app"));
        }
        return motorista;
    }

    /**
     * Gera um novo token para o motorista
     * @param motorista motorista que precisa do token
     * @return O token
     */
    public TokenVo gerarSoftToken(Motorista motorista) {
        TokenVo token = tokenDados.novoToken(TipoToken.SOFT_TOKEN_AUTORIZACAO);
        byte[] salt = UtilitarioCriptografia.gerarSaltBCrypt();
        byte[] hash = UtilitarioCriptografia.calcularHashBCrypt(token.getToken().getBytes(StandardCharsets.UTF_8), salt);
        motorista.setTokenCriptografado(token.getToken());
        motorista.setTokenCriptografado(UtilitarioCriptografia.toBase64(hash));
        motorista.setSaltTokenCriptografado(UtilitarioCriptografia.toBase64(salt));
        motorista.setDataExpiracaoToken(token.getDataExpiracaoToken());

        repositorio.armazenar(motorista);
        return token;
    }

    /**
     * Gera o relatorio em excel contendo os Motoristas solicitados
     *
     * @param filtro O filtro de pesquisa informado
     * @return Os dados para montagem do relatorio
     */
    public ResultadoPaginado<Motorista> pesquisarMotoristaParaExportacao(FiltroPesquisaMotoristaVo filtro) {
        InformacaoPaginacao informacaoPaginacao = filtro.getPaginacao();
        informacaoPaginacao.setTamanhoPagina(null);
        informacaoPaginacao.setPagina(null);
        filtro.setPaginacao(informacaoPaginacao);
        return repositorio.pesquisar(filtro);
    }

    /**
     * Cria um lock para mutual exclusion de autorização de pagamento em processamento
     *
     * @param idFrota Identificador da Frota
     * @return O Lock criado
     */
    public Lock getLockAutorizacaoPagamento(Long idFrota) {
        return mapeadorLockFrotaImportacaoMotorista.getLock(idFrota);
    }

    /**
     * Valida se o motorista informado pertence a frota e se seu
     * status está ativo
     *
     * @param cpf     O CPF do motorista
     * @param idFrota identificador unico da frota
     * @return O motorista validado
     * @throws ExcecaoValidacao Se o cpf buscado não for encontrado ou não pertencer a frota
     */
    public Motorista validarMotorista(String cpf, Long idFrota) throws ExcecaoValidacao {
        Frota frota = repositorioFrota.obterPorId(idFrota);
        Long cpfLong = UtilitarioFormatacao.obterLongMascara(cpf);
        Motorista motorista = repositorio.obterPorCpfFrotaSemIsolamento(cpfLong, frota.getId());

        if (motorista == null || !motorista.getFrota().getId().equals(idFrota)) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("motorista.servico.pagamento.contingencia.erro.cadastro", formatarCpfApresentacao(cpfLong), formatarCnpjApresentacao(frota.getCnpj()), frota.getNomeFantasia()));
        } else if (motorista.getStatus().equals(StatusAtivacao.INATIVO.getValue())) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("motorista.servico.pagamento.contingencia.inativo", formatarCpfApresentacao(cpfLong), motorista.getNome(), formatarCnpjApresentacao(frota.getCnpj()), frota.getNomeFantasia()));
        }

        return motorista;
    }
}
