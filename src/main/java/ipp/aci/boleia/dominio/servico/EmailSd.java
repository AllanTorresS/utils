package ipp.aci.boleia.dominio.servico;


import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.CELULAR_CENTRAL_ATENDIMENTO_DDD;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.CELULAR_CENTRAL_ATENDIMENTO_NUMERO;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.TELEFONE_CENTRAL_ATENDIMENTO_DDD;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.TELEFONE_CENTRAL_ATENDIMENTO_NUMERO;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCnpjApresentacao;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCpfApresentacao;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarDecimal;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarDecimalMoedaReal;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarNumeroTelefone;
import static ipp.aci.boleia.util.UtilitarioFormatacao.obterString;
import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataHora;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dados.ITokenDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dados.servicos.aws.AwsEmailEnvioDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.CobrancaConectcar;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.GapPontoDeVenda;
import ipp.aci.boleia.dominio.GapServico;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.TransacaoConectcar;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.TipoItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.vo.EdicaoAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.rotas.Paginas;

/**
 * Oferece funcionalidades para envio de emails
 */
@Component
public class EmailSd {

    @Autowired
    private EdicaoAbastecimentoSd edicaoAbastecimentoSd;

    @Autowired
    private ITokenDados tokenDados;

    @Autowired
    private AwsEmailEnvioDados emailDados;

    @Autowired
    private IUsuarioDados repositorio;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Value("${email.central.atendimento}")
    private String emailCentralAtendimento;

    @Value("${email.anti.fraude}")
    private String emailAntiFraude;

    @Value("${telefone.central.atendimento}")
    private String telefoneCentralAtendimento;

    @Value("${telefone.central.demais.regioes}")
    private String telefoneCentralAtendimentoDemaisRegioes;

    @Value("${telefone.central.whatsapp}")
    private String telefoneCentralAtendimentoWhatsapp;

    @Value("${email.contas.a.receber}")
    private String emailContasAReceber;

    @Value("${pdv.web.url}")
    private String pdvWebUrl;

    @Value("${email.avisos.integracao}")
    private String destinatarioIntegracao;

    private static final String APPLICATION_TYPE_PDF = "application/pdf";

    @Autowired
    private IConfiguracaoSistemaDados configuracaoSistema;

    @Autowired
    private IFrotaPontoVendaDados repositorioFrotaPtov;

    @Autowired
    private IFrotaDados repositorioFrota;

    /**
     * Gera um token e envia um link com token para acesso pelo usuario em email
     *
     * @param usuario O usuario que deve receber o email
     * @param pdv se o sistema de origem é PDV ou Pró-frotas
     */
    public void enviarEmailRecuperacaoSenha(Usuario usuario, boolean pdv) {
        if (usuario != null && usuario.getStatus().equals(StatusAtivacao.ATIVO.getValue())) {
            String assunto = mensagens.obterMensagem("usuario.servico.senha.recuperacao.email.assunto");
            String mensagem;
            if (!usuario.getGestor() || (utilitarioAmbiente.getUsuarioLogado() != null && utilitarioAmbiente.getUsuarioLogado().isInterno())) {
                mensagem = this.mensagemEmailRecuperarSenhaComLink(usuario, pdv);
            } else {
                mensagem = this.mensagemEmailRecuperarSenhaSemLink();
            }
            emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(usuario.getEmail()));
        }
    }

    /**
     * Gera o texto de uma mensagem com o link para a recuperação da senha do usuário
     *
     * @param usuario o usuário que deseja recuperar a senha
     * @param pdv indica se o usuário é do PDV
     *
     * @return o texto da mensagem para a recuperação da senha
     * */
    private String mensagemEmailRecuperarSenhaComLink(Usuario usuario, boolean pdv){
        String dddTelefone = configuracaoSistema.buscarConfiguracoes(TELEFONE_CENTRAL_ATENDIMENTO_DDD).getParametro();
        String numeroTelefone = configuracaoSistema.buscarConfiguracoes(TELEFONE_CENTRAL_ATENDIMENTO_NUMERO).getParametro();
        String dddCelular = configuracaoSistema.buscarConfiguracoes(CELULAR_CENTRAL_ATENDIMENTO_DDD).getParametro();
        String numeroCelular = configuracaoSistema.buscarConfiguracoes(CELULAR_CENTRAL_ATENDIMENTO_NUMERO).getParametro();
        TokenVo token = tokenDados.novoToken(TipoToken.RECUPERAR_SENHA_USUARIO);
        usuario.setToken(token.getToken());
        usuario.setDataExpiracaoToken(token.getDataExpiracaoToken());
        repositorio.armazenar(usuario);
        String url = pdv ? pdvWebUrl + Paginas.REDEFINIR_SENHA_PDV : utilitarioAmbiente.getURLContextoAplicacao() + Paginas.REDEFINIR_SENHA;
        return mensagens.obterMensagem("usuario.servico.senha.recuperacao.email.mensagem", url + "?token=" + token.getToken(),
                                    numeroTelefone, dddTelefone, dddCelular, numeroCelular );
    }

    /**
     * Gera o texto de uma mensagem para a recuperação da senha do usuário
     *
     * @return o texto da mensagem para a recuperação da senha
     * */
    private String mensagemEmailRecuperarSenhaSemLink(){
        String numeroTelefone = configuracaoSistema.buscarConfiguracoes(TELEFONE_CENTRAL_ATENDIMENTO_NUMERO).getParametro();
        return mensagens.obterMensagem("usuario.servico.senha.recuperacao.email.mensagem.gestor", numeroTelefone);
    }

    /**
     * Gera um email de solicitação de ajuda de nota de pagamento
     *
     * @param mensagemAjuda A mensagem de ajuda a ser enviada
     * @param autorizacaoPagamento dados da transacao (nota) para a qual foi solicitada ajuda
     * @param itensTransacao itens da transacao
     * @param abastecimento item da transacao que representa o abastecimento
     */
    public void enviarEmailSolicitacaoAjudaNota(String mensagemAjuda, AutorizacaoPagamento autorizacaoPagamento,
                                                List<ItemAutorizacaoPagamento> itensTransacao, ItemAutorizacaoPagamento abastecimento) {

        Frota frota = autorizacaoPagamento.getFrota();
        Motorista motorista = autorizacaoPagamento.getMotorista();

        String cpfNomeMotorista = formatarCpfApresentacao(motorista.getCpf()) + " - " + motorista.getNome();
        String cnpjNomeFrota = formatarCnpjApresentacao(frota.getCnpj()) + " - " + frota.getRazaoSocial();
        String nomePV = autorizacaoPagamento.getPontoVenda().getNome();
        String valorTotalNota = formatarDecimalMoedaReal(autorizacaoPagamento.getTransacaoFrota().getValorTotal().abs());
        String dataHora = formatarDataHora(utilitarioAmbiente.buscarDataAmbiente());
        String celularMotorista = formatarNumeroTelefone(motorista.getDddTelefoneCelular().toString() + motorista.getTelefoneCelular().toString());

        String abastecimentoNome = abastecimento.getNome();
        String abastecimentoLitragem = formatarDecimal(abastecimento.getQuantidade());
        String abastecimentoValorUnitario = formatarDecimalMoedaReal(abastecimento.getValorUnitario());
        String abastecimentoValorTotal = formatarDecimalMoedaReal(abastecimento.getValorTotal());

        StringBuilder textoProdutosServicos = new StringBuilder();
        itensTransacao.stream()
                .filter(it -> TipoItemAutorizacaoPagamento.isProdutoServico(it.getTipoItem()))
                .forEach(item -> {
                    String nome = item.getNome();
                    String quantidade = obterString(item.getQuantidade());
                    String valorUnitario = formatarDecimalMoedaReal(item.getValorUnitario());
                    String valorTotal = formatarDecimalMoedaReal(item.getValorTotal());

                    String textoItemTransacao = mensagens.obterMensagem(
                            "dispositivo.servico.solicitacaoAjuda.email.itemTransacao",
                            nome, valorUnitario, quantidade, valorTotal);

                    textoProdutosServicos.append(textoItemTransacao);
                });

        String assuntoEmail = mensagens.obterMensagem("dispositivo.servico.solicitacaoAjuda.email.assunto", frota.getRazaoSocial());
        String corpoEmail = mensagens.obterMensagem("dispositivo.servico.solicitacaoAjuda.email.mensagem",
                cpfNomeMotorista, cnpjNomeFrota, nomePV,
                abastecimentoNome, abastecimentoLitragem, abastecimentoValorUnitario, abastecimentoValorTotal,
                textoProdutosServicos.toString(),
                valorTotalNota, dataHora, mensagemAjuda, celularMotorista);

        emailDados.enviarEmail(assuntoEmail, corpoEmail, Collections.singletonList(emailCentralAtendimento));
    }

    /**
     * Envia um email para o responsável com avisos de crédito pré-pago
     * @param pedidos lista contendo os pedidos atualizados
     */
    public void enviarEmailStatusIntegracao(List<PedidoCreditoFrota> pedidos){
        Date date = utilitarioAmbiente.buscarDataAmbiente();
        String assunto = mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.assunto");
        StringBuilder corpo = new StringBuilder(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo", utilitarioAmbiente.getNomeSistema(), date));

        for(PedidoCreditoFrota pedido : pedidos){
            corpo.append(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo1",
                    pedido.getDataPedido(),
                    pedido.getFrotaCnpjRazaoSocial(),
                    UtilitarioFormatacao.formatarDecimal(pedido.getValorPedido())));
        }

        List<PedidoCreditoFrota> sucessos = pedidos.stream().filter(p -> p.getStatusJde().equals(StatusIntegracaoJde.REALIZADO.getValue())).collect(Collectors.toList());
        corpo.append(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo2"));

        String sucesso = sucessos.size() == 1 ? "sucesso" : "sucessos";
        corpo.append(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo2." + sucesso, sucessos.size()));

        pedidos.removeAll(sucessos);
        String erros = pedidos.size() == 1 ? "erro" : "erros";
        corpo.append(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo2." + erros, pedidos.size()));

        if(!pedidos.isEmpty()){
            corpo.append(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo3"));
            for(PedidoCreditoFrota erro : pedidos){
                corpo.append(mensagens.obterMensagem("integracao.servico.email.enviarstatusintegracao.corpo4", erro.getFrotaCnpjRazaoSocial(), erro.getMensagemErroJde()));
            }
        }

        emailDados.enviarEmail(assunto, corpo.toString(), Collections.singletonList(destinatarioIntegracao));
    }

    /**
     * Gera um token e envia um link com token para acesso pelo usuario em email
     *
     * @param email O endereco de email do usuario usuario
     */
    public void enviarEmailPrimeiroAcesso(String email) {
        Usuario usuario = repositorio.obterPorEmail(email);
        if (usuario != null && usuario.getStatus().equals(StatusAtivacao.ATIVO.getValue()) && !usuario.getTipoPerfil().getId().equals(TipoPerfilUsuario.INTERNO.getValue())) {
            String assunto = mensagens.obterMensagem("usuario.servico.senha.definicao.email.assunto");
            String mensagem;
            TokenVo token = tokenDados.novoToken(TipoToken.RECUPERAR_SENHA_USUARIO);
            usuario.setToken(token.getToken());
            usuario.setDataExpiracaoToken(token.getDataExpiracaoToken());
            repositorio.armazenar(usuario);
            mensagem = mensagens.obterMensagem("usuario.servico.senha.definicao.email.mensagem", utilitarioAmbiente.getURLContextoAplicacao() + Paginas.REDEFINIR_SENHA + "?token=" + token.getToken() + "&first=true");
            emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(email));
        }
    }

    /**
     * Envia o email de gap de servico durante roteamento
     * @param gapServico a ser enviado
     */
    public void enviarEmailGapServico(GapServico gapServico) {
        List<Usuario> usuarios = repositorio.obterPorTipoPerfilGestor(TipoPerfilUsuario.INTERNO, false);
        List<String> emails = usuarios.stream().map(Usuario::getEmail).filter(Objects::nonNull).collect(Collectors.toList());
        StringBuilder servicos = new StringBuilder();
        gapServico.getServicos().forEach(servico->{
            if(servicos.length()>0) {
                servicos.append(", ");
            }
            servicos.append(servico.getDescricao());
        });

        String assunto = mensagens.obterMensagem("rota.servico.gap.servicos.assunto");
        String mensagem = mensagens.obterMensagem("rota.servico.gap.servicos.mensagem", utilitarioAmbiente.getFrotaUsuarioLogado().getNomeRazaoFrota(), gapServico.getPontoVenda().getNome(), servicos.toString());
        emailDados.enviarEmail(assunto, mensagem, emails);
    }


    /**
     * Envia o email de gap de PV durante o roteamento
     * @param gapPv a ser enviado
     */
    public void enviarEmailGapPv(GapPontoDeVenda gapPv) {
        List<Usuario> usuarios = repositorio.obterPorTipoPerfilGestor(TipoPerfilUsuario.INTERNO, false);
        List<String> emails = usuarios.stream().map(Usuario::getEmail).filter(Objects::nonNull).collect(Collectors.toList());
        String cnpj = gapPv.getCnpj() != null ? formatarCnpjApresentacao(gapPv.getCnpj()) : "";
        String origem = gapPv.getOrigem() != null ? gapPv.getOrigem() : "";
        String destino = gapPv.getDestino() != null ? gapPv.getDestino() : "";
        String assunto = mensagens.obterMensagem("rota.servico.gap.pv.assunto");
        String mensagem = mensagens.obterMensagem("rota.servico.gap.pv.mensagem", utilitarioAmbiente.getFrotaUsuarioLogado().getNomeRazaoFrota(),
                gapPv.getNome(), cnpj, origem, destino,
                UtilitarioFormatacao.formatarDecimal(gapPv.getVolumeMedio()), UtilitarioFormatacao.formatarDecimal(gapPv.getPreco()),
                UtilitarioFormatacao.formatarInteiro(gapPv.getPrazoPagamento()));
        emailDados.enviarEmail(assunto, mensagem, emails);
    }

    /**
     * Enviar o email de atualizacao de preco medio dos protudos de abastecimento
     * @param abastecimento atualizado
     * @param produto atualizado
     * @param dataAtual do envio
     */
    public void enviarEmailAlteracaoPrecoMedio(String abastecimento, String produto, Date dataAtual) {
        String assunto = mensagens.obterMensagem("servico.precomedio.email.assunto");
        String mensagem = mensagens.obterMensagem("servico.precomedio.email.mensagem", abastecimento, produto, UtilitarioFormatacaoData.formatarDataHora(dataAtual));
        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(emailAntiFraude));
    }

    /**
     * Envia um email aos administradores da solução informando sobre o status da integração com mundipagg
     * sobre a verificação de pagamento de crédito pre-pago
     * @param pedidos Lista de pedidos relacionados com mensagem de erro
     */
    public void enviarEmailAtualizacaoCreditoPrePago(List<PedidoCreditoFrota> pedidos) {
        Integer numPedidosComSucesso = 0;
        Integer numPedidosComErro = 0;

        StringBuilder pedidosComErro = new StringBuilder();
        pedidosComErro.append(mensagens.obterMensagem("frota.credito.pre.pago.email.status.pedido.lista.erros"));

        for(PedidoCreditoFrota pedido : pedidos) {
            String mensagemErro = pedido.getMensagemErroMundiPagg();
            if(mensagemErro != null) {
                numPedidosComErro = numPedidosComErro + 1;
                Frota frota = pedido.getFrota();
                String frotaCnpjRazaoSocial = formatarCnpjApresentacao(frota.getCnpj()) + " - " + frota.getRazaoSocial();
                String codigoPedido = pedido.getCodigoPedido();
                pedidosComErro.append(mensagens.obterMensagem("frota.credito.pre.pago.email.status.pedido.lista.erros.erro",frotaCnpjRazaoSocial,codigoPedido,mensagemErro));
            } else {
                numPedidosComSucesso = numPedidosComSucesso + 1;
            }
        }
        String assunto = mensagens.obterMensagem("frota.credito.pre.pago.email.status.pedido.assunto");
        String corpoEmail = mensagens.obterMensagem("frota.credito.pre.pago.email.status.pedido.mensagem", UtilitarioFormatacaoData.formatarDataHora(utilitarioAmbiente.buscarDataAmbiente()), numPedidosComSucesso, numPedidosComErro);
        if (numPedidosComErro > 0) {
            corpoEmail = corpoEmail + pedidosComErro.toString();
        }
        emailDados.enviarEmail(assunto,corpoEmail, Collections.singletonList(emailContasAReceber));
    }

    /**
     * Envia um email ao gestor da frota avisando que sua primeira compra liberou o acesso ao sistema
     * @param idFrota a enviar o email
     * @param codigoPedido realizado pela frota
     */
    public void enviarEmailPrimeiraCompraFrota(Long idFrota, String codigoPedido) {
        List<Usuario> gestorFrota = repositorio.obterGestorPorFrota(idFrota);
        String assunto = mensagens.obterMensagem("frota.credito.pre.pago.email.compra.creditos.assunto");
        String mensagem = mensagens.obterMensagem("frota.credito.pre.pago.email.primeira.compra.mensagem", codigoPedido, utilitarioAmbiente.getURLContextoAplicacao());
        emailDados.enviarEmail(assunto, mensagem, gestorFrota.stream().map(u->u.getEmail()).collect(Collectors.toList()));
    }

    /**
     * Envia um email ao gestor da frota avisando que a cobranca do pedido realizado ira vencer em 1 dia
     * @param idFrota a enviar o email
     * @param codigoPedido realizado pela frota
     */
    public void enviarEmailLembreteVencimentoCobrancaCredito(Long idFrota, String codigoPedido) {
        List<Usuario> gestorFrota = repositorio.obterGestorPorFrota(idFrota);
        String assunto = mensagens.obterMensagem("frota.credito.pre.pago.email.lembrete.vencimento.assunto", codigoPedido);
        String mensagem = mensagens.obterMensagem("frota.credito.pre.pago.email.lembrete.vencimento.mensagem", codigoPedido);
        emailDados.enviarEmail(assunto, mensagem, gestorFrota.stream().map(u->u.getEmail()).collect(Collectors.toList()));
    }

    /**
     * Envia um email para cada Gestor da Revenda associado aos postos onde a frota inativada realizou os ultimos 30 abastecimentos
     * @param pontosDeVendaUltimos30Abastecimentos lista de postos onde ocorreram os ultimos 30 abastecimentos da frota
     * @param frota a frota inativada
     * @param classificacao A classificacao do email
     * @param descricao A descricao do email
     */
    public void enviarEmailGestoresPVUltimos30Abastecimentos(List<PontoDeVenda> pontosDeVendaUltimos30Abastecimentos, Frota frota, String classificacao, String descricao) {
        List<Long> idRedes = pontosDeVendaUltimos30Abastecimentos.stream().map(p -> p.getRede().getId()).collect(Collectors.toList());
        idRedes = idRedes.stream().distinct().collect(Collectors.toList());
        List<String> emailsGestores = new ArrayList<>();
        for(Long idRede: idRedes) {
            List<Usuario> gestoresRede = repositorio.obterGestorPorRede(idRede);
            List<String> emailGestoresRede = gestoresRede.stream().map(g -> g.getEmail()).collect(Collectors.toList());
            emailsGestores.addAll(emailGestoresRede);
        }
        String assunto = mensagens.obterMensagem("frota.servico.alteracao.email.assunto");
        String dataAmbiente = UtilitarioFormatacaoData.formatarDataHora(utilitarioAmbiente.buscarDataAmbiente());
        String nomeUsuario = mensagens.obterMensagem("frota.servico.alteracao.email.usuario.sistema");
        String mensagem = mensagens.obterMensagem("frota.servico.alteracao.email.gestor.revenda.mensagem", formatarCnpjApresentacao(frota.getCnpj()), frota.getRazaoSocial(),
                StatusFrota.obterPorValor(frota.getStatus()).getLabel(), dataAmbiente, nomeUsuario, classificacao,
                descricao, utilitarioAmbiente.getURLContextoAplicacao(), frota.getId());

        emailDados.enviarEmail(assunto,mensagem,emailsGestores);
    }

    /**
     * Envia um email para o responsavel do sistema externo com as chaves de acesso
     * @param sistemaExterno sistema externo que será enviado as chaves
     */
    public void enviarEmailChavesSistemaExterno(SistemaExterno sistemaExterno){
        String assunto = mensagens.obterMensagem("sistemaexterno.email.novaschavesacesso.assunto");
        String urlSistema = utilitarioAmbiente.getURLContextoAplicacao();
        String mensagem = mensagens.obterMensagem("sistemaexterno.email.novaschavesacesso.corpo",
                sistemaExterno.getNomeContato(), sistemaExterno.getNomeSistema(),
                sistemaExterno.getClient(), sistemaExterno.getSecret(), urlSistema, telefoneCentralAtendimento,
                telefoneCentralAtendimentoDemaisRegioes, telefoneCentralAtendimentoWhatsapp);
        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(sistemaExterno.getEmail()));

    }

    /**
     * Envia um email para o responsavel do sistema externo com as novas chaves de acesso regeradas
     * @param sistemaExterno sistema externo que será enviado as chaves
     */
    public void enviarEmailRegerarChavesSistemaExterno(SistemaExterno sistemaExterno){
        String assunto = mensagens.obterMensagem("sistemaexterno.email.regerar.novaschavesacesso.assunto");
        String urlSistema = utilitarioAmbiente.getURLContextoAplicacao();
        String mensagem = mensagens.obterMensagem("sistemaexterno.email.regerar.novaschavesacesso.corpo",
                sistemaExterno.getNomeContato(), sistemaExterno.getNomeSistema(),
                sistemaExterno.getClient(), sistemaExterno.getSecret(), urlSistema, telefoneCentralAtendimento,
                telefoneCentralAtendimentoDemaisRegioes, telefoneCentralAtendimentoWhatsapp);
        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(sistemaExterno.getEmail()));

    }

    /**
     * Envia um e-mail para o responsável do sistema externo notificando o bloqueio.
     * @param sistemaExterno sistema externo que sofreu bloqueio.
     */
    public void enviarEmailBloqueioSistemaExterno(SistemaExterno sistemaExterno){
        String assunto = mensagens.obterMensagem("sistemaexterno.email.bloqueio.assunto");
        String mensagem = mensagens.obterMensagem("sistemaexterno.email.bloqueio.corpo",
            sistemaExterno.getNomeContato(), sistemaExterno.getNomeSistema(), telefoneCentralAtendimento,
            telefoneCentralAtendimentoDemaisRegioes, telefoneCentralAtendimentoWhatsapp);
        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(sistemaExterno.getEmail()));

    }

    /**
     * Envia um e-mail para o responsável do sistema externo notificando o desbloqueio.
     * @param sistemaExterno sistema externo que sofreu desbloqueio.
     */
    public void enviarEmailDesbloqueioStatusSistemaExterno(SistemaExterno sistemaExterno){
        String assunto = mensagens.obterMensagem("sistemaexterno.email.desbloqueio.assunto");
        String mensagem = mensagens.obterMensagem("sistemaexterno.email.desbloqueio.corpo",
                sistemaExterno.getNomeContato(), sistemaExterno.getNomeSistema(), telefoneCentralAtendimento,
                telefoneCentralAtendimentoDemaisRegioes, telefoneCentralAtendimentoWhatsapp);
        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(sistemaExterno.getEmail()));

    }

    /**
     * Reenvia um email para o responsavel do sistema externo com as chaves de acesso
     * @param sistemaExterno sistema externo que será enviado as chaves
     */
    public void reenviarEmailChavesSistemaExterno(SistemaExterno sistemaExterno){
        String assunto = mensagens.obterMensagem("sistemaexterno.email.reenviarchavesacesso.assunto");
        String urlSistema = utilitarioAmbiente.getURLContextoAplicacao();
        String mensagem = mensagens.obterMensagem("sistemaexterno.email.reenviarchavesacesso.corpo",
                sistemaExterno.getNomeContato(), sistemaExterno.getNomeSistema(),
                sistemaExterno.getClient(), sistemaExterno.getSecret(), urlSistema, telefoneCentralAtendimento,
                telefoneCentralAtendimentoDemaisRegioes, telefoneCentralAtendimentoWhatsapp);
        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(sistemaExterno.getEmail()));
    }

    /**
     * Envia um email ao gestor da revenda informando que ha prazos de emissão de notas fiscais
     * expirados há 24 ou 72 horas
     * @param email lista de email de gestores
     */
    public void enviarEmailGestoresNFAtrasada(List<String> email){
        String assunto = mensagens.obterMensagem("nota.pendente.email.assunto");
        String mensagem = mensagens.obterMensagem("nota.pendente.email.mensagem");
        emailDados.enviarEmail(assunto, mensagem, email);
    }

    /**
     * Envia um email para o responsável e para os usuários responsáveis da frota
     * avisando sobre as cobranças geradas, com o boleto em anexo
     * @param cobranca a cobrança que é referenciada no email
     * @param anexoBytes o arquivo a ser anexado ao email
     */
    public void enviarEmailCobrancaFrota(Cobranca cobranca, byte[] anexoBytes) {
        enviarEmailCobrancaFrota(cobranca, anexoBytes,
                cobranca.getFrota().getNomeResponsavelFrota(), cobranca.getFrota().getEmailResponsavelFrota());
        for (Usuario usuario: repositorio.obterGestorPorFrota(cobranca.getFrota().getId())) {
            String emailDestinatario = usuario.getEmail();
            if (StringUtils.isNotBlank(emailDestinatario) && !emailDestinatario.equals(cobranca.getFrota().getEmailResponsavelFrota())) {
                enviarEmailCobrancaFrota(cobranca, anexoBytes, usuario.getNome(), usuario.getEmail());
            }
        }
    }

    /**
     * Envia um email para o responsável e para os usuários responsáveis da frota
     * avisando sobre as cobranças geradas, com o boleto em anexo
     * @param cobranca a cobrança que é referenciada no email
     * @param anexoBytes o arquivo a ser anexado ao email
     */
    public void enviarEmailCobrancaFrota(CobrancaConectcar cobranca, byte[] anexoBytes) {
        enviarEmailCobrancaFrota(cobranca, anexoBytes,
                cobranca.getFrota().getNomeResponsavelFrota(), cobranca.getFrota().getEmailResponsavelFrota());
        for (Usuario usuario: repositorio.obterGestorPorFrota(cobranca.getFrota().getId())) {
            String emailDestinatario = usuario.getEmail();
            if (StringUtils.isNotBlank(emailDestinatario) && !emailDestinatario.equals(cobranca.getFrota().getEmailResponsavelFrota())) {
                enviarEmailCobrancaFrota(cobranca, anexoBytes, usuario.getNome(), usuario.getEmail());
            }
        }
    }

    /**
     * Envia um e-mail para o responsável e para os usuários responsaveis pela frota
     * avisando sobre um ajuste que tenha ocorrido no boleto
     * @param cobranca a cobrança que foi ajustada
     * @param anexoBytes o anexo do boleto em pdf
     */
    public void enviarEmailAjusteBoleto(Cobranca cobranca, byte[] anexoBytes){
        enviarEmailAjusteBoleto(cobranca, anexoBytes,
                cobranca.getFrota().getNomeResponsavelFrota(), cobranca.getFrota().getEmailResponsavelFrota());
        for (Usuario usuario: repositorio.obterGestorPorFrota(cobranca.getFrota().getId())) {
            String emailDestinatario = usuario.getEmail();
            if (StringUtils.isNotBlank(emailDestinatario) && !emailDestinatario.equals(cobranca.getFrota().getEmailResponsavelFrota())) {
                enviarEmailAjusteBoleto(cobranca, anexoBytes, usuario.getNome(), usuario.getEmail());
            }
        }
    }

    /**
     * Envia um e-mail para um usuário específico sobre o ajuste de boleto, com um boleto em anexo
     * @param cobranca A cobrança que foi ajustada
     * @param anexoBytes O anexo do boleto em pdf
     * @param nomeDestinatario O nome do usuário que receberá o e-mail
     * @param emailDestinatario O e-mail do usuário que recebrá o e-mail
     */
    private void enviarEmailAjusteBoleto (Cobranca cobranca, byte[] anexoBytes, String nomeDestinatario, String emailDestinatario){
        String assunto = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.assunto");
        ByteArrayDataSource anexo = null;
        String nomeAnexo = null;
        String valorTotal = UtilitarioFormatacao.formatarDecimalMoedaReal(cobranca.getValorVigente());
        String informacaoCiclo = UtilitarioFormatacaoData.formatarDataPeriodoMes(cobranca.getDataInicioPeriodo(),cobranca.getDataFimPeriodo());
        Date dataVencimento = cobranca.getDataVencimentoVigente();
        String corpo;

        if (anexoBytes != null) {
            corpo = mensagens.obterMensagem("frota.cobranca.ajuste.boleto.email.corpo",
                        nomeDestinatario, valorTotal, informacaoCiclo,
                        mensagens.obterMensagem(
                                "frota.cobranca.ajuste.boleto.email.dataVencimento",
                                UtilitarioFormatacaoData.formatarDataCurta(dataVencimento)),
                        utilitarioAmbiente.getURLContextoAplicacao());
            anexo = new ByteArrayDataSource(anexoBytes,APPLICATION_TYPE_PDF);
            nomeAnexo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.anexo", UtilitarioFormatacaoData.formatarDataCurtaHifen(dataVencimento));
        } else {
            corpo = mensagens.obterMensagem("frota.cobranca.ajuste.boleto.email.corpo",
                    nomeDestinatario, valorTotal, informacaoCiclo,
                    "",
                    utilitarioAmbiente.getURLContextoAplicacao());
        }

        emailDados.enviarEmail(assunto, corpo, Collections.singletonList(emailDestinatario), anexo, nomeAnexo);
    }

    /**
     * Envia um email de cobrança para um usuário específico, com o boleto em anexo
     * @param cobranca a cobrança que é referenciada no email
     * @param anexoBytes o arquivo a ser anexado no email
     * @param nomeDestinatario nome do destinatário
     * @param emailDestinatario email do destinatário
     */
    private void enviarEmailCobrancaFrota(Cobranca cobranca, byte[] anexoBytes, String nomeDestinatario, String emailDestinatario) {
        String assunto = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.assunto");
        ByteArrayDataSource anexo = null;
        String nomeAnexo = null;
        String valorTotal = UtilitarioFormatacao.formatarDecimalMoedaReal(cobranca.getValorTotal());
        String informacaoCiclo = UtilitarioFormatacaoData.formatarDataPeriodoMes(cobranca.getDataInicioPeriodo(), cobranca.getDataFimPeriodo());
        Date dataVencimento = cobranca.getDataVencimentoPagto();
        String corpo;

        if (dataVencimento != null) {
            corpo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.corpo.sucesso",
                    nomeDestinatario, informacaoCiclo, valorTotal,
                    UtilitarioFormatacaoData.formatarDataCurta(dataVencimento),
                    utilitarioAmbiente.getURLContextoAplicacao());

            anexo = new ByteArrayDataSource(anexoBytes, APPLICATION_TYPE_PDF);
            nomeAnexo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.anexo", UtilitarioFormatacaoData.formatarDataCurtaHifen(dataVencimento));
        } else {
            corpo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.corpo.falha",
                    nomeDestinatario, informacaoCiclo, valorTotal,
                    utilitarioAmbiente.getURLContextoAplicacao());
        }

        emailDados.enviarEmail(assunto, corpo, Collections.singletonList(emailDestinatario), anexo, nomeAnexo);
    }

    /**
     * Envia um email de cobrança para um usuário específico, com o boleto em anexo
     * @param cobranca a cobrança que é referenciada no email
     * @param anexoBytes o arquivo a ser anexado no email
     * @param nomeDestinatario nome do destinatário
     * @param emailDestinatario email do destinatário
     */
    private void enviarEmailCobrancaFrota(CobrancaConectcar cobranca, byte[] anexoBytes, String nomeDestinatario, String emailDestinatario) {
        String assunto = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.assunto");
        ByteArrayDataSource anexo = null;
        String nomeAnexo = null;
        String valorTotal = UtilitarioFormatacao.formatarDecimalMoedaReal(cobranca.getValorTotal());
        String informacaoCiclo = UtilitarioFormatacaoData.formatarDataPeriodoMes(cobranca.getDataInicioPeriodo(), cobranca.getDataFimPeriodo());
        Date dataVencimento = cobranca.getDataVencimentoPagto();
        String corpo;

        if (dataVencimento != null) {
            corpo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.corpo.sucesso",
                    nomeDestinatario, informacaoCiclo, valorTotal,
                    UtilitarioFormatacaoData.formatarDataCurta(dataVencimento),
                    utilitarioAmbiente.getURLContextoAplicacao());

            anexo = new ByteArrayDataSource(anexoBytes, APPLICATION_TYPE_PDF);
            nomeAnexo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.anexo", UtilitarioFormatacaoData.formatarDataCurtaHifen(dataVencimento));
        } else {
            corpo = mensagens.obterMensagem("frota.cobranca.fim.ciclo.email.corpo.falha",
                    nomeDestinatario, informacaoCiclo, valorTotal,
                    utilitarioAmbiente.getURLContextoAplicacao());
        }

        emailDados.enviarEmail(assunto, corpo, Collections.singletonList(emailDestinatario), anexo, nomeAnexo);
    }

    /**
     * Envia um e-mail para o responsável da frota informando a alteração do ciclo da frota.
     * @param frota frota que teve ciclo alterado ou agendado.
     * @param cicloAntigo ciclo antigo da frota.
     * @param dataAlteracao data do início da vigência do novo ciclo da frota.
     */
    public void enviarEmailAlteracaoCicloFrota(Frota frota, ParametroCiclo cicloAntigo, Date dataAlteracao){
        ParametroCiclo novoParametroCiclo = frota.getNovoParametroCiclo() != null ? frota.getNovoParametroCiclo() : frota.getParametroCiclo();

        String assunto = mensagens.obterMensagem("frota.email.alteracao.ciclo.assunto");
        String corpoAgendamentoCiclo = "";
        String frotaCnpjExibicao = frota.getCnpj() + "-" + frota.getRazaoSocial();
        String prazoAntigoFormatado = cicloAntigo.getPrazoCiclo() + " + " + cicloAntigo.getPrazoPagamento();
        String prazoNovoFormatado = novoParametroCiclo.getPrazoCiclo() + " + " + novoParametroCiclo.getPrazoPagamento();

        if(dataAlteracao != null){
            corpoAgendamentoCiclo = mensagens.obterMensagem("frota.email.agendamento.alteracao.ciclo",
                    UtilitarioFormatacaoData.formatarDataCurta(dataAlteracao));
        }

        String mensagem = mensagens.obterMensagem("frota.email.alteracao.ciclo.corpo",
                frota.getNomeResponsavelFrota(), frotaCnpjExibicao, prazoAntigoFormatado,
                prazoNovoFormatado, corpoAgendamentoCiclo);

        emailDados.enviarEmail(assunto, mensagem, Collections.singletonList(frota.getEmail()));
    }

    /**
     * Envia um email quando uma alteração de abastecimento pendente de aprovação é invalidada devido ao fechamento do ciclo
     *
     * @param destinatario destinatário do e-mail
     * @param transacaoConsolidada Ciclo que fechou
     * @param abastecimentos abastecimentos pendentes de autorização do ciclo
     */
    public void enviarEmailCicloFechadoSemAprovacaoDeAlteracao(String destinatario, TransacaoConsolidada transacaoConsolidada, List<AutorizacaoPagamentoEdicao> abastecimentos){
        FrotaPontoVenda frotaPtov = repositorioFrotaPtov.obterPorId(transacaoConsolidada.getFrotaPtov().getId());
        String assunto = mensagens.obterMensagem("email.alteracao.invalidada.assunto");

        StringBuilder corpo = new StringBuilder(mensagens.obterMensagem("email.alteracao.invalidada.corpo1",
                                                    frotaPtov.getFrota().getRazaoSocial(),
                                                    formatarCnpjApresentacao(frotaPtov.getPontoVenda().getComponenteAreaAbastecimento().getCodigoPessoa()),
                                                    frotaPtov.getPontoVenda().getNome().trim()));
        abastecimentos.forEach(abastecimentoPendente -> {
            AutorizacaoPagamento autorizacaoPagamento = abastecimentoPendente.getAutorizacaoPagamento();
            EdicaoAbastecimentoVo edicaoAbastecimentoVo = edicaoAbastecimentoSd.construirEdicaoAbastecimento(abastecimentoPendente, autorizacaoPagamento);
            edicaoAbastecimentoSd.ordenarCamposEdicao(edicaoAbastecimentoVo);
            corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo2", autorizacaoPagamento.getId().toString()));
            edicaoAbastecimentoVo.getCamposEditados().forEach(campoEditado -> {
                String campo = campoEditado.getCategoria() == null ? campoEditado.getCampo() : campoEditado.getCategoria() + " - " + campoEditado.getCampo();
                String valorAntigo = campoEditado.getValorAutorizacao() == null ? "-" : campoEditado.getValorAutorizacao();
                String valorNovo = campoEditado.getValorEdicao() == null ? "-" : campoEditado.getValorEdicao();
                corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo3",
                        campo,
                        valorAntigo,
                        valorNovo));
            });
            corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo4"));
        });
        corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo5"));

        emailDados.enviarEmail(assunto, corpo.toString(), Arrays.asList(destinatario));
    }

    /**
     * Envia um email quando uma alteração de abastecimento pendente de aprovação é invalidada devido ao fechamento do ciclo
     *
     * @param destinatario destinatário do e-mail
     * @param transacaoConsolidada Ciclo que fechou
     * @param abastecimentos abastecimentos pendentes de autorização do ciclo
     */
    public void enviarEmailCicloFechadoSemAprovacaoDeAlteracao(String destinatario, TransacaoConectcar transacaoConsolidada, List<AutorizacaoPagamentoEdicao> abastecimentos){
        Frota frota = repositorioFrota.obterPorId(transacaoConsolidada.getFrota().getId());
        String assunto = mensagens.obterMensagem("email.alteracao.invalidada.assunto");

        StringBuilder corpo = new StringBuilder(mensagens.obterMensagem("email.conectcar.alteracao.invalidada.corpo1", frota.getRazaoSocial()));
        abastecimentos.forEach(abastecimentoPendente -> {
            AutorizacaoPagamento autorizacaoPagamento = abastecimentoPendente.getAutorizacaoPagamento();
            EdicaoAbastecimentoVo edicaoAbastecimentoVo = edicaoAbastecimentoSd.construirEdicaoAbastecimento(abastecimentoPendente, autorizacaoPagamento);
            edicaoAbastecimentoSd.ordenarCamposEdicao(edicaoAbastecimentoVo);
            corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo2", autorizacaoPagamento.getId().toString()));
            edicaoAbastecimentoVo.getCamposEditados().forEach(campoEditado -> {
                String campo = campoEditado.getCategoria() == null ? campoEditado.getCampo() : campoEditado.getCategoria() + " - " + campoEditado.getCampo();
                String valorAntigo = campoEditado.getValorAutorizacao() == null ? "-" : campoEditado.getValorAutorizacao();
                String valorNovo = campoEditado.getValorEdicao() == null ? "-" : campoEditado.getValorEdicao();
                corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo3",
                        campo,
                        valorAntigo,
                        valorNovo));
            });
            corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo4"));
        });
        corpo.append(mensagens.obterMensagem("email.alteracao.invalidada.corpo5"));

        emailDados.enviarEmail(assunto, corpo.toString(), Arrays.asList(destinatario));
    }

}
