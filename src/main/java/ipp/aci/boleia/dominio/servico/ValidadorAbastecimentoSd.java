package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoAutenticacaoRemota;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Serviços de domínio da entidade Unidade.
 */
@Component
public class ValidadorAbastecimentoSd {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidadorAbastecimentoSd.class);

    @Autowired
    protected IAutorizacaoPagamentoDados repositorioAbastecimento;

    @Autowired
    private IVeiculoDados repositorioVeiculo;

    @Autowired
    protected IMotoristaDados repositorioMotorista;

    @Autowired
    protected IUsuarioDados repositorioUsuario;

    @Autowired
    protected UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private UsuarioSd usuarioSd;

    @Autowired
    protected Mensagens mensagens;

    /**
     * Efetua a validação das condições que permitem a edição de abastecimento.
     * @param abastecimentoOriginal o abastecimento que será validado para edição.
     * @param isEdicaoPendente flag que indica se a validação é de uma aprovação de edição pendente.
     * @throws ExcecaoValidacao caso uma das condições de edição não seja respeitada.
     */
    public void validarPreCondicoesEdicaoAbastecimento(AutorizacaoPagamento abastecimentoOriginal, Boolean isEdicaoPendente) throws ExcecaoValidacao {
        TransacaoConsolidada consolidado = Optional.ofNullable(abastecimentoOriginal.getTransacaoConsolidada()).orElseThrow(() -> new ExcecaoValidacao(Erro.ERRO_EDICAO_SEM_CONSOLIDADO));
        if(!StatusAutorizacao.AUTORIZADO.getValue().equals(abastecimentoOriginal.getStatus())
                || BigDecimal.ZERO.compareTo(abastecimentoOriginal.getValorTotal()) >= 0
                || abastecimentoOriginal.isPostoInterno()
                || (isEdicaoPendente && !StatusEdicao.PENDENTE.getValue().equals(abastecimentoOriginal.getStatusEdicao()))
                || (!isEdicaoPendente && StatusEdicao.PENDENTE.getValue().equals(abastecimentoOriginal.getStatusEdicao()))
                || StatusTransacaoConsolidada.FECHADA.getValue().equals(consolidado.getStatusConsolidacao())
                || !ModalidadePagamento.POS_PAGO.getValue().equals(abastecimentoOriginal.getFrota().getModoPagamento())){
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_PRE_CONDICOES_INVALIDAS, mensagens.obterMensagem(Erro.ERRO_EDICAO_PRE_CONDICOES_INVALIDAS.getChaveMensagem()));
        }
    }

    /**
     * Efetua a validação das condições que permitem a edição de um abastecimento interno.
     * @param abastecimentoOriginal o abastecimento interno que será validado para edição.
     * @throws ExcecaoValidacao caso uma das condições de edição não seja respeitada.
     */
    public void validarPreCondicoesEdicaoAbastecimentoInterno(AutorizacaoPagamento abastecimentoOriginal) throws ExcecaoValidacao {
        if(!StatusAutorizacao.AUTORIZADO.getValue().equals(abastecimentoOriginal.getStatus())
                || !abastecimentoOriginal.isPostoInterno()){
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_PRE_CONDICOES_INVALIDAS, mensagens.obterMensagem(Erro.ERRO_EDICAO_PRE_CONDICOES_INVALIDAS.getChaveMensagem()));
        }
    }

    /**
     * Verifica a senha informada pelo usuario
     *
     * @param senhaUsuario A senha
     * @throws ExcecaoValidacao Caso a senha nao esteja correta
     */
    public void validarSenhaUsuario(String senhaUsuario) throws ExcecaoValidacao {
        try {
            Usuario usuarioLogado = repositorioUsuario.obterPorId(utilitarioAmbiente.getUsuarioLogado().getId());
            boolean senhaValida;
            if (usuarioLogado.isInterno()) {
                senhaValida = usuarioSd.confirmaSenhaUsuarioInterno(senhaUsuario);
            } else {
                senhaValida = usuarioSd.confirmarSenhaUsuarioExterno(usuarioLogado, senhaUsuario);
            }

            if (!senhaValida) {
                throw new ExcecaoValidacao(mensagens.obterMensagem(Erro.AUTENTICACAO_CREDENCIAIS_INVALIDAS.getChaveMensagem()));
            }
        } catch (ExcecaoAutenticacaoRemota | BadCredentialsException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoValidacao(mensagens.obterMensagem(Erro.AUTENTICACAO_CREDENCIAIS_INVALIDAS.getChaveMensagem()));
        }
    }

    /**
     * Efetua a validação dos dados do veículo.
     * @param placa placa do veículo do abastecimento.
     * @param frota frota do abastecimento.
     * @throws ExcecaoValidacao caso o motorista não esteja cadastrado na frota ou esteja inativo.
     */
    public void validarVeiculo(String placa, Frota frota) throws ExcecaoValidacao {
        Veiculo veiculo = repositorioVeiculo.buscarPorPlacaFrotaSemIsolamento(placa, frota.getId());

        if (veiculo == null) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_VEICULO_INVALIDO,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_VEICULO_INVALIDO.getChaveMensagem(),
                            UtilitarioFormatacao.formatarPlacaVeiculo(placa), UtilitarioFormatacao.formatarCnpjApresentacao(frota.getCnpj()), frota.getNomeRazaoFrota()));
        }
        if (!StatusAtivacao.ATIVO.getValue().equals(veiculo.getStatus())) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_VEICULO_INATIVO,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_VEICULO_INATIVO.getChaveMensagem(), UtilitarioFormatacao.formatarPlacaVeiculo(placa), UtilitarioFormatacao.formatarCnpjApresentacao(frota.getCnpj()), frota.getNomeRazaoFrota()));
        }
    }

    /**
     * Efetua a validação dos dados do motorista.
     * @param cpfMotorista cpf do motorista do abastecimento.
     * @param frota frota do abastecimento.
     * @throws ExcecaoValidacao caso o motorista não esteja cadastrado na frota ou esteja inativo.
     */
    public void validarMotorista(Long cpfMotorista, Frota frota) throws ExcecaoValidacao {
        Motorista motorista = repositorioMotorista.obterPorCpfFrotaSemIsolamento(cpfMotorista, frota.getId());
        if (motorista == null) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_MOTORISTA_INVALIDO,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_MOTORISTA_INVALIDO.getChaveMensagem(), UtilitarioFormatacao.formatarCpfOcultoApresentacao(cpfMotorista), UtilitarioFormatacao.formatarCnpjApresentacao(frota.getCnpj()), frota.getRazaoSocial()));
        }
        if (!StatusAtivacao.ATIVO.getValue().equals(motorista.getStatus())) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_MOTORISTA_INATIVO,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_MOTORISTA_INATIVO.getChaveMensagem(), UtilitarioFormatacao.formatarCpfOcultoApresentacao(cpfMotorista), motorista.getNome(), UtilitarioFormatacao.formatarCnpjApresentacao(motorista.getFrota().getCnpj()), motorista.getFrota().getNomeRazaoFrota()));
        }
    }

    /**
     * Verifica se uma frota é válida.
     * @param frota a frota que será validada.
     * @throws ExcecaoValidacao caso a frota não seja válida.
     */
    public void validarFrota(Frota frota) throws ExcecaoValidacao {
        if (!StatusFrota.ATIVO.getValue().equals(frota.getStatus())) {
            String frotaApresentacao = UtilitarioFormatacao.formatarCnpjApresentacao(frota.getCnpj()) + " - " + frota.getNomeFantasia();
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_FROTA_INATIVA,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_FROTA_INATIVA.getChaveMensagem(), frotaApresentacao));
        }
        if (frota.isPrePaga()) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_FROTA_PREPAGA,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_FROTA_PREPAGA.getChaveMensagem()));
        }
    }

    /**
     * Verifica se um ponto de venda é válido.
     * @param pontoDeVenda o ponto de venda que será validado.
     * @throws ExcecaoValidacao caso o ponto de venda não seja válido.
     */
    public void validarPontoVendaAtivo(PontoDeVenda pontoDeVenda) throws ExcecaoValidacao {
        if (!StatusAtivacao.ATIVO.getValue().equals(pontoDeVenda.getStatus())) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_PONTO_VENDA_INATIVO,
                    mensagens.obterMensagem(Erro.ERRO_EDICAO_PONTO_VENDA_INATIVO.getChaveMensagem(), pontoDeVenda.getNome()));
        }
    }

    /**
     * Valida os campos hodometro e horimetro informados
     *
     * @param abastecimentoOriginal o abastecimento interno que será editado.
     * @param isHodometro indica se o valor informado é do hodômetro
     * @param novoValor valor editado do hodômetro/horímetro
     * @param placaVeiculo placa do veículo a ser validado
     * @throws ExcecaoValidacao quando os campos nao atendem as regras especificadas
     */
    public void validarHodometroHorimetro(AutorizacaoPagamento abastecimentoOriginal, boolean isHodometro, String novoValor, String placaVeiculo) throws ExcecaoValidacao{
        AutorizacaoPagamento abastecimentoPosterior = repositorioAbastecimento.obterAutorizacaoPagamentoPosterior(abastecimentoOriginal);
        Veiculo veiculo = repositorioVeiculo.buscarPorPlacaFrota(placaVeiculo, abastecimentoOriginal.getFrota().getId());

        if (isHodometro) {
            Long novoHodometro = novoValor != null ? UtilitarioFormatacao.obterLongMascara(novoValor) : null;
            boolean hodometroValidoInformado = novoHodometro != null && novoHodometro > 0;
            Long hodometroAnterior = abastecimentoOriginal.getHodometroAnterior() != null ? abastecimentoOriginal.getHodometroAnterior() : veiculo.getHodometro();

            if (hodometroValidoInformado && (novoHodometro <= hodometroAnterior)) {
                hodometroValidoInformado = false;
            }

            if(hodometroValidoInformado && abastecimentoPosterior != null) {
                Long hodometroPosterior = abastecimentoPosterior.getHodometro();

                if(hodometroPosterior != null && novoHodometro >= hodometroPosterior) {
                    hodometroValidoInformado = false;
                }
            }

            if (!hodometroValidoInformado) {
                throw new ExcecaoValidacao(Erro.ERRO_EDICAO_HODOMETRO_INVALIDO, mensagens.obterMensagem(Erro.ERRO_EDICAO_HODOMETRO_INVALIDO.getChaveMensagem()));
            }
        } else {
            BigDecimal novoHorimetro = novoValor != null? UtilitarioFormatacao.obterDecimalMascara(novoValor) : null;
            boolean horimetroValidoInformado = novoHorimetro != null && novoHorimetro.compareTo(BigDecimal.ZERO) > 0;
            BigDecimal horimetroAnterior = abastecimentoOriginal.getHorimetroAnterior() != null? abastecimentoOriginal.getHorimetroAnterior() : veiculo.getHorimetro();

            if (horimetroValidoInformado && (novoHorimetro.compareTo(horimetroAnterior) <= 0)) {
                horimetroValidoInformado = false;
            }

            if(horimetroValidoInformado && abastecimentoPosterior != null) {
                BigDecimal horimetroPosterior = abastecimentoPosterior.getHorimetro();

                if(horimetroPosterior != null && novoHorimetro.compareTo(horimetroPosterior) >= 0) {
                    horimetroValidoInformado = false;
                }
            }

            if (!horimetroValidoInformado) {
                throw new ExcecaoValidacao(Erro.ERRO_EDICAO_HORIMETRO_INVALIDO, mensagens.obterMensagem(Erro.ERRO_EDICAO_HORIMETRO_INVALIDO.getChaveMensagem()));
            }
        }
    }
}
