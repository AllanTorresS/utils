package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.INotificacaoDados;
import ipp.aci.boleia.dados.INotificacaoUsuarioDados;
import ipp.aci.boleia.dados.IRedeDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.ComandaDigital;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.Notificacao;
import ipp.aci.boleia.dominio.NotificacaoUsuario;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.Rede;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.campanha.Campanha;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.enums.StatusCampanha;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.TipoCategoriaNotificacao;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoSubcategoriaNotificacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.negocio.UtilitarioNotificacaoPushGestorFrota;
import ipp.aci.boleia.util.seguranca.ChavePermissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataCurtaHifen;

/**
 * Oferece funcionalidades para o cadastro de Notificacoes de Usuario
 */
@Component
public class NotificacaoUsuarioSd {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IUsuarioDados repositorioUsuarios;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private INotificacaoUsuarioDados repositorio;

    @Autowired
    private INotificacaoDados repositorioNotificacao;

    @Autowired
    private IAutorizacaoPagamentoDados autorizacaoPagamentoDados;

    @Autowired
    private ITransacaoConsolidadaDados transacaoConsolidadaDados;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

    @Autowired
    private IRedeDados repositorioRede;

    @Autowired
    private EmailSd emailSd;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private UtilitarioNotificacaoPushGestorFrota utilitarioNotificacaoPushGestorFrota;

    /**
     * Marca uma notificacao como lida
     *
     * @param idNotificacao id da notificacao a marcar
     * @return notificacao alterada
     */
    public NotificacaoUsuario lerNotificacao(Long idNotificacao) {
        NotificacaoUsuario notificacaoUsuario = repositorio.obterPorId(idNotificacao);
        if(!utilitarioAmbiente.getUsuarioLogado().getId().equals(notificacaoUsuario.getUsuario().getId())) {
            throw new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_USUARIO_INVALIDO);
        }
        notificacaoUsuario.setDataLeitura(utilitarioAmbiente.buscarDataAmbiente());
        return repositorio.armazenar(notificacaoUsuario);
    }

    /**
     * Marca as notificacoes informadas como visualizadas pelo usuario
     * @param usuarioLogado O usuario logado
     * @param categoria A categoria das notificacoes
     */
    public void marcarComoVisualizadas(Usuario usuarioLogado, TipoCategoriaNotificacao categoria) {
        repositorio.visualizarNotificacoesDeUmUsuarioPorCategoria(usuarioLogado, categoria);
    }

    /**
     * Marca as notificacoes informadas como visualizadas pelo usuario
     * @param usuarioLogado O usuario logado
     * @param ids A lista de ids das notificacoes
     */
    public void marcarComoVisualizadas(Usuario usuarioLogado, List<Long> ids) {
        if(ids != null) {
            Date agora = utilitarioAmbiente.buscarDataAmbiente();
            for (Long id : ids) {
                NotificacaoUsuario notif = repositorio.obterPorId(id);
                if(notif.getDataVisualizacao() == null && notif.getUsuario().getId().equals(usuarioLogado.getId())) {
                    notif.setDataVisualizacao(agora);
                    repositorio.armazenar(notif);
                }
            }
        }
    }

    /**
     * Envia uma notificacao de solicitacao de senha de motorista de um dado dispositivo para o gestor da Frota
     * @param dispositivoMotorista Dispositivo do motorista a recuperar senha
     */
    public void enviarNotificacaoSolicitacaoSenhaMotorista(DispositivoMotorista dispositivoMotorista) {
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorFrota(dispositivoMotorista.getFrota().getId());
        Motorista motorista = dispositivoMotorista.getMotorista();
        String celularMotorista = UtilitarioFormatacao.formatarNumeroTelefone(motorista.getDddTelefoneCelular().toString() + motorista.getTelefoneCelular().toString());
        enviarNotificacao(TipoSubcategoriaNotificacao.SOLICITACAO_SENHA_MOTORISTA, usuarios, motorista.getNome(), celularMotorista, dispositivoMotorista.getId().toString());
    }

    /**
     * Envia uma notificacao de habilita????o de nova frota aos administradores da solu??ao
     *
     * @param frota A frota habilitada
     */
    public void enviarNotificacaoNovaFrotaHabilitada(Frota frota) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilGestor(TipoPerfilUsuario.INTERNO, false);
        String nomeFrota = frota.getNomeFantasia();
        if(nomeFrota==null) {
            nomeFrota = frota.getRazaoSocial();
        }
        String nomeAssessor = "";
        if(frota.getAssessorResponsavel()!=null) {
            nomeAssessor = " " + frota.getAssessorResponsavel();
        }
        enviarNotificacao(TipoSubcategoriaNotificacao.NOVA_FROTA, usuarios, nomeFrota, utilitarioAmbiente.getNomeSistema(), nomeAssessor, frota.getId().toString());
    }

    /**
     * Envia uma notificacao de altera????o de status da frota aos administradores da solu??ao e aos
     * Revendedores que abasteceram essa frota atraves de seus PVs nos ??ltimos 30 dias.
     *
     * @param frota A frota habilitada
     */
    public void enviarNotificacaoFrotaAlterada(Frota frota) {
        List<Usuario> adms = repositorioUsuarios.obterPorTipoPerfilGestor(TipoPerfilUsuario.INTERNO, false);
        List<AutorizacaoPagamento> autorizacaoPagamentos = autorizacaoPagamentoDados.obterAutorizadasPorFrotaDataMinima(frota.getId(), UtilitarioCalculoData.adicionarMesesData(utilitarioAmbiente.buscarDataAmbiente(),-1));

        List<Long> idsRedes = new ArrayList<>();
        for(AutorizacaoPagamento autorizacao : autorizacaoPagamentos) {
            if(autorizacao.getPontoVenda() != null && autorizacao.getPontoVenda().getRede() != null) {
                idsRedes.add(autorizacao.getPontoVenda().getRede().getId());
            }
        }


        List<Usuario> gestoresRevenda = idsRedes.isEmpty() ? null : repositorioUsuarios.obterGestorPorRedes(idsRedes);
        String nomeFrota = frota.getNomeFantasia();
        if(nomeFrota==null) {
            nomeFrota = frota.getRazaoSocial();
        }

        if(StatusFrota.ATIVO.getValue().equals(frota.getStatus())) {
            enviarNotificacao(TipoSubcategoriaNotificacao.FROTA_REATIVADA_ADM, adms, nomeFrota, frota.getUnidadeFederativa(), frota.getId().toString());
            if(gestoresRevenda != null) {
                enviarNotificacao(TipoSubcategoriaNotificacao.FROTA_REATIVADA_REVENDA, gestoresRevenda, nomeFrota, frota.getUnidadeFederativa(), frota.getId().toString());
            }
        } else {
            enviarNotificacao(TipoSubcategoriaNotificacao.FROTA_INATIVADA_ADM, adms, nomeFrota, frota.getUnidadeFederativa(), frota.getId().toString());
            if(gestoresRevenda != null) {
                enviarNotificacao(TipoSubcategoriaNotificacao.FROTA_INATIVADA_REVENDA, gestoresRevenda, nomeFrota, frota.getUnidadeFederativa(), frota.getId().toString());
            }
        }
    }

    /**
     * Envia uma notifica????o aos usu??rios Internos e aos Analistas de Pre??o informando que houve altera????es nos pre??os
     */
    public void enviarNotificacaoPrecosMicromercadoAlterados() {
        List<Usuario> todos = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_MICROMERCADO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.PRECO_REFERENCIA_REAJUSTADO, todos);
    }

    /**
     * Envia uma notifica????o aos usuarios da revenda informando que existe uma comanda aguardando reabilitacao
     * @param comanda A comanda em questao
     */
    public void enviarNotificacaoComandaAguardandoReabilitacao(ComandaDigital comanda) {
        List<Usuario> usuarios = null;
        if(comanda.getFrota() != null) {
            usuarios = repositorioUsuarios.obterPorFrotaPermissoes(comanda.getFrota().getId(), ChavePermissao.getChave(ChavePermissao.COMANDA_REGERAR_TOKEN));
        } else if(comanda.getPontoVenda() != null) {
            usuarios = repositorioUsuarios.obterPorPontoVendaPermissoes(comanda.getPontoVenda().getId(), ChavePermissao.getChave(ChavePermissao.COMANDA_REGERAR_TOKEN));
        }
        enviarNotificacao(TipoSubcategoriaNotificacao.COMANDA_AGUARDANDO_REABILITACAO, usuarios, comanda.getId().toString(), comanda.getNome());
    }

    /**
     * Envia uma notifica????o ao gestor de cada grupo economico informando que houve altera????es nos pre??os
     *
     * @param idsRedes Os ids das redes
     */
    public void enviarNotificacaoPrecosRevendaAlterados(Set<Long> idsRedes) {
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRedes(new ArrayList<>(idsRedes));
        enviarNotificacao(TipoSubcategoriaNotificacao.PRECO_BASE_REVENDA_REAJUSTADO, usuarios);
    }

    /**
     * Envia uma notifica????o ao gestor de cada frota informando que houve altera????es nos pre??os
     *
     * @param idsFrota Os ids das frotas
     */
    public void enviarNotificacaoPrecosFrotaAlterados(Set<Long> idsFrota) {
        for (Long idFrota : idsFrota){
            List<Usuario> usuarios = repositorioUsuarios.obterGestorPorFrota(idFrota);
            enviarNotificacao(TipoSubcategoriaNotificacao.PRECO_BASE_FROTA_REAJUSTADO, usuarios);
        }
    }

    /**
     * Envia uma notifica????o aos administradores informando que ha prazos de emiss??o de notas fiscais
     * expirando em 24 horas
     */
    public void enviarNotificacaoCiclosAVencerSolucao() {
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.NOTA_FISCAL_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.NOTA_FISCAL_NAO_EMITIDA_SOLUCAO, usuarios);
    }

    /**
     * Envia uma notifica????o ao gestor da revenda informando que h?? prazos de emiss??o de notas fiscais
     * expirando em 72 horas ou 24 horas
     * @param consolidado O ciclo com prazo de emiss??o prestes a expirar
     */
    public void enviarNotificacaoCiclosAVencerRevenda(TransacaoConsolidada consolidado) {
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(consolidado.getFrotaPtov().getPontoVenda().getRede().getId());
        enviarNotificacao(
                TipoSubcategoriaNotificacao.NOTA_FISCAL_NAO_EMITIDA_REVENDA,
                usuarios,
                UtilitarioFormatacaoData.formatarDataCurta(consolidado.getDataInicioPeriodo()),
                UtilitarioFormatacaoData.formatarDataCurta(consolidado.getDataFimPeriodo()),
                consolidado.getPontoVenda().getId().toString(),
                StatusTransacaoConsolidada.obterPorValor(consolidado.getStatusConsolidacao()).name(),
                UtilitarioFormatacaoData.formatarPeriodoDias(consolidado.getDataInicioPeriodo(), consolidado.getDataFimPeriodo(), false, " de ")
        );
    }

    /**
     * Envia um email ao gestor da revenda informando que ha prazos de emiss??o de notas fiscais
     * expirados h?? 24 ou 72 horas
     *
     * @param idsRedes identificadores das redes cujos gestores devem ser notificados
     */
    public void enviarEmailCiclosAtrasados(Set<Long> idsRedes){
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRedes(new ArrayList<>(idsRedes));

        ArrayList<String> emails = new ArrayList<>();
        Date dataAtual = ambiente.buscarDataAmbiente();
        for(Usuario usuario : usuarios){
            if(usuario.getDataUltimoEmail() != null) {
                long diffInMillies = Math.abs(usuario.getDataUltimoEmail().getTime() - dataAtual.getTime());
                long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff > 24){
                    usuario.setDataUltimoEmail(dataAtual);
                    repositorioUsuarios.armazenarSemIsolamentoDeDados(usuario);
                    emails.add(usuario.getEmail());
                }
            }else{
                usuario.setDataUltimoEmail(dataAtual);
                repositorioUsuarios.armazenarSemIsolamentoDeDados(usuario);
                emails.add(usuario.getEmail());
            }
        }
        if(!emails.isEmpty()){
            emailSd.enviarEmailGestoresNFAtrasada(emails);
        }
    }

    /**
     * Envia uma notifica????o ao gestor da revenda informando que uma nota fiscal foi emitida
     *
     * @param autorizacaoPagamento A autoriza????o de pagamento para qual foi emitida a nota fiscal
     * @param notaFiscal A nota fiscal emitida
     */
    public void enviarNotificacaoNotaFiscalEmitida(AutorizacaoPagamento autorizacaoPagamento, NotaFiscal notaFiscal) {
        Long idFrota = autorizacaoPagamento.getFrota().getId();
        List<Usuario> usuarioFrota = repositorioUsuarios.obterGestorPorFrota(idFrota);
        String numeroNfe = notaFiscal.getNumero();
        TransacaoConsolidada transacaoConsolidada = transacaoConsolidadaDados.obterConsolidadoParaAbastecimento(autorizacaoPagamento.getId());
        Date dataInicioPeriodo = transacaoConsolidada.getDataInicioPeriodo();
        Date dataFimPeriodo = transacaoConsolidada.getDataFimPeriodo();
        String mes = UtilitarioFormatacaoData.formatarDataMes(dataInicioPeriodo);
        String ano = UtilitarioFormatacaoData.formatarDataAno(dataInicioPeriodo);
        String ciclo = UtilitarioFormatacaoData.formatarDataCurta(dataInicioPeriodo)
                + " - " + UtilitarioFormatacaoData.formatarDataCurta(dataFimPeriodo);
        String pontoVenda = autorizacaoPagamento.getPontoVenda().getNome();

        enviarNotificacao(TipoSubcategoriaNotificacao.NOTA_FISCAL_EMITIDA, usuarioFrota, numeroNfe, mes, ano, ciclo, pontoVenda, "frota");
    }

    /**
     * Envia uma notifica????o ao administrador da solu????o ap??s o encerramento do ciclo de abastecimento
     * de uma ou mais frotas.
     *
     * @param dataEncerramento data de encerramento do ciclo de abastecimento
     */
    public void enviarNotificacaoCiclosEncerrados(Date dataEncerramento) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilGestor(TipoPerfilUsuario.INTERNO, true);
        enviarNotificacao(TipoSubcategoriaNotificacao.CICLO_ENCERRADO, usuarios,
                formatarDataCurtaHifen(dataEncerramento), formatarDataCurtaHifen(dataEncerramento), StatusTransacaoConsolidada.FECHADA.name());
    }

    /**
     * Envia uma notifica????o ao administrador da solu????o ap??s um frotista
     * solicitar um novo acordo de pre??o especial.
     * @param preco O pre??o
     */
    public void enviarNotitificacaoNovaSolicitacaoAcordoPrecoEspecialRevenda(Preco preco) {
        Long id = preco.getId();
        String nomeFrota = preco.getFrota().getNomeRazaoFrota();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String descontoSolicitado = UtilitarioFormatacao.formatarDecimalComTresCasas(preco.getDescontoSolicitado() != null ? preco.getDescontoSolicitado() : preco.getDescontoVigente());
        List<Usuario> usuariosPreco = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.NOVA_SOLICITACAO_ACORDO_ESPECIAL, usuariosPreco, nomeFrota, nomeProduto, nomePontoVenda, descontoSolicitado, id.toString());
    }

    /**
     * Envia uma notifica????o ao concluir um relat??rio
     *
     * @param usuario usuario que deve receber a notifica????o
     */
    public void enviarNotificacaoRelatorioConcluido(Usuario usuario){
        List <Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        enviarNotificacao(TipoSubcategoriaNotificacao.RELATORIO_CONCLUIDO,usuarios);
    }

    /**
     * Pesquisa ultimas notificacoes do usuario por Sub Categoria
     * @param usuario autenticado
     * @param tipoSubcategoriaNotificacao Sub Categoria
     * @return ultima notifica????o do usuario daquela sub categoria
     */
    public NotificacaoUsuario obterUltimaNotificacaoUsuarioPorSubCategoria(Usuario usuario, TipoSubcategoriaNotificacao tipoSubcategoriaNotificacao){
        return repositorio.obterUltimaNotificacaoUsuarioPorSubCategoria(usuario, tipoSubcategoriaNotificacao);
    }

    /**
     * Envia uma notifica????o ao Gestor da revenda ap??s a solu????o encaminhar
     * uma solicita????o de acordo especial de uma frota.
     * @param preco O preco da negocia????o
     */
    public void enviarNotitificacaoNovaSolicitacaoAcordoPrecoEspecialSolucao(Preco preco) {
        Long id = preco.getId();
        String nomeFrota = preco.getFrota().getNomeRazaoFrota();
        Long idRede = preco.getPontoVenda().getRede().getId();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String descontoSolicitado = UtilitarioFormatacao.formatarDecimalComTresCasas(preco.getDescontoSolicitado());
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(idRede);
        enviarNotificacao(TipoSubcategoriaNotificacao.NOVA_SOLICITACAO_ACORDO_ESPECIAL, usuarios, nomeFrota, nomeProduto, nomePontoVenda, descontoSolicitado, id.toString());
    }

    /**
     * Envia uma notifica????o para o administrado no momento em que o acordo especial foi
     * aprovado pela revenda.
     * @param preco O pre??o negociado
     */
    public void enviarNotificacaoAcordoPrecoEspecialAceitoSolucao(Preco preco) {
        String nomeFrota = preco.getFrota().getNomeRazaoFrota();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String descontoSolicitado = UtilitarioFormatacao.formatarDecimalComTresCasas(preco.getDescontoSolicitado());
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.ACORDO_ESPECIAL_ACEITO_SOLUCAO, usuarios, nomeFrota, nomeProduto, nomePontoVenda, descontoSolicitado);
    }

    /**
     * Envia uma notifica????o para o gestor da rede no momento em que o pre??o negociado foi
     * aprovado pelo administrador.
     * @param preco Pre??o negociado
     */
    public void enviarNotificacaoAcordoPrecoEspecialAceitoRevendedor(Preco preco) {
        String nomeFrota = preco.getFrota().getNomeRazaoFrota();
        Long idRede = preco.getPontoVenda().getRede().getId();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String descontoSolicitado = UtilitarioFormatacao.formatarDecimalComTresCasas(preco.getDescontoSolicitado());
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(idRede);
        enviarNotificacao(TipoSubcategoriaNotificacao.ACORDO_ESPECIAL_ACEITO_SOLUCAO, usuarios, nomeFrota, nomeProduto, nomePontoVenda, descontoSolicitado);
    }

    /**
     * Envia uma notifica????o para o gestor da rede no momento em que o pre??o negociado foi
     * recusado pelo administrador.
     * @param preco O pre??o negociado
     */
    public void enviarNotificacaoAcordoPrecoEspecialRecusadoRevendedor(Preco preco) {
        String nomeFrota = preco.getFrota().getNomeRazaoFrota();
        Long idRede = preco.getPontoVenda().getRede().getId();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String descontoSolicitado = UtilitarioFormatacao.formatarDecimalComTresCasas(preco.getDescontoSolicitado());
        String justificativa = preco.getJustificativa();
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(idRede);
        enviarNotificacao(TipoSubcategoriaNotificacao.ACORDO_ESPECIAL_REJEITADO_SOLUCAO, usuarios, nomeFrota, nomeProduto, nomePontoVenda, descontoSolicitado, justificativa);
    }

    /**
     * Envia uma notifica????o para o administrador no momento em que o pre??o negociado foi
     * recusado pela revenda.
     * @param preco O pre??o negociado
     */
    public void enviarNotificacaoAcordoPrecoEspecialRecusadoSolucao(Preco preco) {
        String nomeFrota = preco.getFrota().getNomeRazaoFrota();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String descontoSolicitado = UtilitarioFormatacao.formatarDecimalComTresCasas(preco.getDescontoSolicitado());
        String justificativa = preco.getJustificativa();
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.ACORDO_ESPECIAL_REJEITADO_SOLUCAO, usuarios, nomeFrota, nomeProduto, nomePontoVenda, descontoSolicitado, justificativa);
    }

    /**
     * Envia uma notifica??ao para o administrador da solu????o ou para o gestor da revenda quando houver uma inclus??o
     * ou altera????o de pre??o base de um produto, com pend??ncia de aceite.
     * @param precoBase para enviar a notificacao
     */
    public void enviarNotificacaoRenegociacaoPrecoBase(PrecoBase precoBase) {

        List<Usuario> usuarios = null;

        if (precoBase.getStatus().equals(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.getValue())){
            usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_BASE_NEGOCIAR));
            enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO, usuarios);

        } else if(precoBase.getStatus().equals(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue())){
            if (precoBase.getPontoVenda().getRede() != null) {
                usuarios = repositorioUsuarios.obterGestorPorRede(precoBase.getPontoVenda().getRede().getId());
            }
            enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO_REVENDA, usuarios);
        }
    }

    /**
     * Envia uma notifica??ao para o gestor da revenda quando houver uma inclus??o
     * ou altera????o de pre??o base de um produto, sem pend??ncia de aceite.
     * @param precoBase para enviar a notificacao
     */
    public void enviarNotificacaoVigenciaPrecoBaseRevenda(PrecoBase precoBase){
        List<Usuario> usuarios = null;
        if (precoBase.getStatus().equals(StatusAlteracaoPrecoPosto.VIGENTE.getValue())){
            usuarios = repositorioUsuarios.obterGestorPorRede(precoBase.getPontoVenda().getRede().getId());
            enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO_VIGENTE_REVENDA, usuarios);
        }
    }

    /**
     * Envia uma notifica??ao para o administrador da solu????o quando houver uma inclus??o
     * de pre??o base de um produto feita pelo gestor da revenda.
     * @param precoBase para enviar a notificacao
     */
    public void enviarNotificacaoVigenciaPrecoBaseSolucao(PrecoBase precoBase){
        List<Usuario> usuarios = null;
        if (precoBase.getStatus().equals(StatusAlteracaoPrecoPosto.VIGENTE.getValue())){
            usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_BASE_NEGOCIAR));
            enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO_VIGENTE_SOLUCAO, usuarios);
        }
    }

    /**
     * Envia uma notificacao ao gestor da revenda, ap??s um pedido de renegocia????o
     * de pre??o base ser rejeitado em definitivo pela solu????o.
     * @param idRede pertencente ao revendedor.
     * @param nomePontoVenda que vai receber a proposta
     * @param nomeProduto que teve a solicita????o de pre??o renegociado
     * @param valorPrecoPosto o valor do pre??o renegociado*
     */
    public void enviarNotificacaoPrecoBaseRecusado(Long idRede, String nomeProduto, String nomePontoVenda, String valorPrecoPosto) {

        List<Usuario> usuarios = null;

        if (!utilitarioAmbiente.getUsuarioLogado().isInterno()) {
            usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.PRECO_BASE_NEGOCIAR));
        } else {
            usuarios = repositorioUsuarios.obterGestorPorRede(idRede);
        }

        enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO_REJEITADA, usuarios, nomeProduto, nomePontoVenda, valorPrecoPosto);
    }

    /**
     * Envia uma notificacao ao gestor da revenda, ap??s um pedido de renegocia????o
     * de pre??o base ser rejeitado em definitivo pela solu????o
     * Com uma sugest??o de um novo Pre??o
     * @param idRede pertencente ao revendedor.
     * @param nomePontoVenda que vai receber a proposta
     * @param nomeProduto que teve a solicita????o de pre??o renegociado
     */
    public void enviarNotificacaoRenegociacaoPrecoBaseRejeitadoSugestao(Long idRede, String nomeProduto, String nomePontoVenda) {
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(idRede);
        enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO_REJEITADA_SUGESTAO, usuarios, nomeProduto, nomePontoVenda);
    }

    /**
     * Envia uma notifica????o ao gestor da revenda, ap??s uma renegocia????o de
     * pre??o base ser aprovada.
     * @param idRede pertencente ao gestor da revenda
     * @param nomeProduto renegociado
     */
    public void enviarNotificacaoRenegociacaoPrecoBaseAceito(Long idRede, String nomeProduto) {
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(idRede);
        enviarNotificacao(TipoSubcategoriaNotificacao.RENEGOCIACAO_PRECO_APROVADA, usuarios, nomeProduto);
    }

    /**
     * Envia uma notifica????o para o gestor da frota no momento em que o acordo especial foi
     * aprovado pela revenda.
     * @param preco O pre??o negociado
     */
    public void enviarNotificacaoAcordoPrecoEspecialAceito(Preco preco) {
        Long idFrota = preco.getFrota().getId();
        String nomeProduto = preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel().getDescricao();
        String nomePontoVenda = preco.getPontoVenda().getNome();
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorFrota(idFrota);
        enviarNotificacao(TipoSubcategoriaNotificacao.ACORDO_ESPECIAL_ACEITO, usuarios, nomeProduto, nomePontoVenda);
    }

    /**
     * Envia uma notifica????o ao gestor da revenda, o administrador
     * da solu????o habilitar um ponto de venda.
     * @param usuarios Usu??rios gestores da rede do pv habilitado.
     * @param nomePontoVenda nome do ponto de venda habilitado.
     * @param idPontoVenda O id do ponto de venda
     */
    public void enviarNotificacaoHabilitacaoPontoVenda(List<Usuario> usuarios, Long idPontoVenda, String nomePontoVenda) {
        enviarNotificacao(TipoSubcategoriaNotificacao.HABILITACAO_PONTO_VENDA, usuarios, nomePontoVenda, idPontoVenda.toString());
    }

    /**
     * Envia uma notificacao informando que um abastecimento infringiu uma regra/parametro informativo
     * ativado para a frota em questao.
     *
     * @param autorizacaoPagamento A autorizacao em questao
     * @param parametro O parametro violado
     */
    @Transactional(rollbackFor = Exception.class)
    public void enviarNotificacaoViolacaoParametroInformativo(AutorizacaoPagamento autorizacaoPagamento, ParametroSistema parametro) {
        String placa = UtilitarioFormatacao.formatarPlacaVeiculo(autorizacaoPagamento.getVeiculo().getPlaca());
        String posto = autorizacaoPagamento.getNomePosto();
        String nomeParam = mensagens.obterMensagem(parametro.getNome());
        String mensagem = autorizacaoPagamento.isPostoInterno() ?
                mensagens.obterMensagem("parametro.sistema.notificacao.abastecimento.interno.violacao.informativa", placa, nomeParam, posto) :
                mensagens.obterMensagem("parametro.sistema.notificacao.abastecimento.violacao.informativa", placa, nomeParam, posto);
        List<Usuario> usuarios = repositorioUsuarios.obterPorFrotaPermissoes(autorizacaoPagamento.getFrota().getId(), ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.VIOLACAO_PARAMETRO_INFORMATIVO_FROTA, usuarios, autorizacaoPagamento, autorizacaoPagamento.getId().toString(), mensagem);
    }

    /**
     * Envia uma notificacao informando que um abastecimento infringiu uma regra/parametro informativo
     * ativado para a frota em questao.
     *
     * @param autorizacaoPagamento A autorizacao em questao
     * @param numeroViolacoes A quantidade de parametros violados
     */
    @Transactional(rollbackFor = Exception.class)
    public void enviarNotificacaoViolacaoMultiplosParametroInformativos(AutorizacaoPagamento autorizacaoPagamento, int numeroViolacoes) {
        String placa = UtilitarioFormatacao.formatarPlacaVeiculo(autorizacaoPagamento.getVeiculo().getPlaca());
        String posto = autorizacaoPagamento.getNomePosto();
        String mensagem = autorizacaoPagamento.isPostoInterno() ?
                mensagens.obterMensagem("parametro.sistema.notificacao.abastecimento.interno.multiplas.violacoes", placa, numeroViolacoes, posto) :
                mensagens.obterMensagem("parametro.sistema.notificacao.abastecimento.multiplas.violacoes", placa, numeroViolacoes, posto);
        List<Usuario> usuarios = repositorioUsuarios.obterPorFrotaPermissoes(autorizacaoPagamento.getFrota().getId(), ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.VIOLACAO_PARAMETRO_INFORMATIVO_FROTA, usuarios, autorizacaoPagamento, autorizacaoPagamento.getId().toString(), mensagem);
    }

    /**
     * Envia uma notificacao informando que um abastecimento nao pode ser autorizado porque infringiu uma regra/parametro restritivo
     * ativado para a frota em questao.
     *
     * @param autorizacaoPagamento Autorizacao de pagamento negada
     * @param parametro O parametro violado
     */
    @Transactional(rollbackFor = Exception.class)
    public void enviarNotificacaoViolacaoParametroRestritivo(AutorizacaoPagamento autorizacaoPagamento, ParametroSistema parametro) {
        String placa = UtilitarioFormatacao.formatarPlacaVeiculo(autorizacaoPagamento.getVeiculo().getPlaca());
        String nomeParam = mensagens.obterMensagem(parametro.getNome());
        String posto = autorizacaoPagamento.getNomePosto();
        String mensagem = autorizacaoPagamento.isPostoInterno() ?
                mensagens.obterMensagem("parametro.sistema.notificacao.abastecimento.interno.violacao.restritiva", placa, posto, nomeParam) :
                mensagens.obterMensagem("parametro.sistema.notificacao.abastecimento.violacao.restritiva", placa, posto, nomeParam);
        List<Usuario> usuarios = repositorioUsuarios.obterPorFrotaPermissoes(autorizacaoPagamento.getFrota().getId(), ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.VIOLACAO_PARAMETRO_RESTRITIVO_FROTA, usuarios, autorizacaoPagamento, autorizacaoPagamento.getId().toString(), mensagem);
    }

    /**
     * Envia uma notificacao informando que um abastecimento manual esta pendente
     * @param autorizacaoPagamento A autorizacao de pagamento
     */
    public void enviarNotificacaoAbastecimentoManualPendente(AutorizacaoPagamento autorizacaoPagamento) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_AUTORIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.ABASTECIMENTO_MANUAL_APROVACAO, usuarios, autorizacaoPagamento, autorizacaoPagamento.getRazaoSocialFrota());
    }

    /**
     * Envia uma notificacao ao gestor da frota, ao ser realizada uma autorizacao de pagamento
     * para um veiculo cujo o consumo estimado n??o foi cadastrado apesar desta regra estar ativada
     *
     * @param veiculo sem consumo estimado
     */
    public void enviarNotificacaoVeiculoSemConsumoEstimado(Veiculo veiculo) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorFrotaPermissoes(
                veiculo.getFrota().getId(),
                ChavePermissao.getChave(ChavePermissao.PARAMETRO_SISTEMA_CONSULTAR_E_VISUALIZAR),
                ChavePermissao.getChave(ChavePermissao.VEICULO_ALTERAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.VEICULO_SEM_CONSUMO_ESTIMADO, usuarios, veiculo.getId().toString(), veiculo.getPlaca());
    }

    /**
     * Envia uma notificacao a frota, ao ser atualizado os precos maximos praticados nos produtos da frota
     *
     * @param frota com precos maximos atualizados
     */
    public void enviarNotificacaoPrecosMaximosAtualizados(Frota frota) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorFrotaPermissoes(frota.getId(), ChavePermissao.getChave(ChavePermissao.PARAMETRO_SISTEMA_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.PRECOS_MAXIMOS_FROTA_ATUALIZADOS, usuarios);
    }

    /**
     * Envia uma notificacao ao administrador da solu????o para gerar um alerta de poss??vel fraude.
     * @param autorizacaoPagamento A autorizacao pagamento
     */
    public void enviarNotificacaoAlertaPrecosMediosPossibilidadeFraude(AutorizacaoPagamento autorizacaoPagamento) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.ALERTA_MECANISMO_ANTIFRAUDE, usuarios, autorizacaoPagamento, autorizacaoPagamento.getId().toString());
    }

    /**
     * Envia uma notificacao para o getor e colaborador da frota caso sejam
     * encontradas divergencias no hodometro/horimetro do veiculo
     *
     * @param idFrota identificador da frota
     * @param placa placa do veiculo
     * @param posto nome do ponto
     * @param autorizacaoPagamento a autoriza????o
     */
    public void enviarNotificacaoAlertaHodometroHorimetroForaLimite(Long idFrota, String placa, String posto, AutorizacaoPagamento autorizacaoPagamento) {
        List<Usuario> usuarios = repositorioUsuarios.obterPorFrotaPermissoes(idFrota, ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_CONSULTAR_E_VISUALIZAR));
        enviarNotificacao(TipoSubcategoriaNotificacao.HODOMETRO_HORIMETRO_FORA_LIMITE, usuarios, autorizacaoPagamento, placa, posto, autorizacaoPagamento.getId().toString());
    }

    /**
     * Envia uma notificacao para o gestor da frota quando um boleto e ajustado
     *
     * @param frota recebe a frota que recebeu o ajuste no boleto
     */
    public void enviarNotificacaoAjusteDeBoleto(Frota frota) {
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorFrota(frota.getId());
        enviarNotificacao(TipoSubcategoriaNotificacao.ALERTA_AJUSTE_BOLETO, usuarios);
    }

    /**
     * Envia uma notifica????o para todos os usu??rios que realizaram uma altera????o em um abastecimento
     * que foi expirada ao se fechar um ciclo
     *
     * @param usuariosResponsaveisEdicao Lista de usu??rios que realizaram as altera????es nos abastecimentos
     */
    public void enviarNotificacaoEdicaoAbastecimentoExpirado(List<Usuario> usuariosResponsaveisEdicao) {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(usuariosResponsaveisEdicao);
        usuarios.addAll(repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.ABASTECIMENTO_APROVAR_REPROVAR));

        enviarNotificacao(TipoSubcategoriaNotificacao.ALTERACAO_ABASTECIMENTO_EXPIRADA, usuarios);
    }

    /**
     * Envia uma notifica????o para um determinado grupo de usu??rios internos quando ocorre uma invalida????o no saldo da frota,
     * tanto na inclus??o manual quanto na edi????o de um abastecimento
     *
     * @param frota Frota que teve saldo negativado na edi????o do abastecimento
     */
    public void enviarNotificacaoAbastecimentoInvalidarSaldoFrota(Frota frota) {
        String chave = ChavePermissao.getChave(ChavePermissao.ABASTECIMENTO_AUTORIZAR);
        List<Usuario> usuarios = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), chave);
        enviarNotificacao(TipoSubcategoriaNotificacao.ABASTECIMENTO_INVALIDAR_SALDO_FROTA, usuarios, frota.getRazaoSocial(), frota.getId().toString());
    }

    /**
     * Envia uma subcategoria de notificacao para determinados usuarios
     * @param subcategoria Subcategoria da notificacao
     * @param usuarios Usuarios a receber a notificacao
     * @param autorizacaoPagamento a autoriza????o pagamento que gerou a notifica????o
     * @param parametros Parametros do link/titulo da notificacao
     */
    private void enviarNotificacao(TipoSubcategoriaNotificacao subcategoria, List<Usuario> usuarios, AutorizacaoPagamento autorizacaoPagamento, String... parametros) {
        Notificacao notificacao = new Notificacao();
        notificacao.setDataEnvio(utilitarioAmbiente.buscarDataAmbiente());
        notificacao.setLink(mensagens.obterMensagem("notificacao.servico.link." + subcategoria.name(), parametros));
        notificacao.setTitulo(mensagens.obterMensagem("notificacao.servico.titulo." + subcategoria.name(), parametros));
        notificacao.setSubcategoria(subcategoria.obterEntidade());

        if(autorizacaoPagamento != null){
            notificacao.setIdAutorizacaoPagamento(autorizacaoPagamento.getId());
        }

        notificacao = repositorioNotificacao.armazenar(notificacao);
        for (Usuario usuario : usuarios) {
            NotificacaoUsuario notificacaoUsuario = new NotificacaoUsuario();
            notificacaoUsuario.setNotificacao(notificacao);
            notificacaoUsuario.setUsuario(usuario);

            repositorio.armazenar(notificacaoUsuario);
            enviarNotificacaoPushGestorFrota(notificacaoUsuario);
        }
    }

    /**
     * Envia uma subcategoria de notificacao para determinados usuarios
     * @param subcategoria Subcategoria da notificacao
     * @param usuarios Usuarios a receber a notificacao
     * @param parametros Parametros do link/titulo da notificacao
     */
    private void enviarNotificacao(TipoSubcategoriaNotificacao subcategoria, List<Usuario> usuarios, String... parametros) {
        enviarNotificacao(subcategoria, usuarios, null, parametros);
    }

    /**
     * Envia uma notifica????o push com a notifica????o, caso o usu??rio seja gestor da frota.
     *
     * @param notificacaoUsuario notifica????o enviada
     */
    private void enviarNotificacaoPushGestorFrota(NotificacaoUsuario notificacaoUsuario) {
        Usuario usuario = notificacaoUsuario.getUsuario();
        if(usuario.isGestor() && usuario.isFrotista()) {
            if (notificacaoUsuario.getNotificacao().getSubcategoria().getId().equals(TipoSubcategoriaNotificacao.VIOLACAO_PARAMETRO_RESTRITIVO_FROTA.obterEntidade().getId())){
                notificacaoUsuario.getNotificacao().setTitulo(mensagens.obterMensagem("notificacao.servico.titulo.ABASTECIMENTO_NEGADO"));
            }
            utilitarioNotificacaoPushGestorFrota.enviarNotificacaoPortal(notificacaoUsuario);
        }
    }

    /**
     * Envia uma notifica????o para os usu??rios com permiss??o de visualizar e aprova campanhas caso uma {@link Campanha}
     * tenha seu status alterado
     *
     * @param campanha A campanha a ser utilizada na notifica????o
     */
    public void enviarNotificacaoAlteracaoStatusCampanha(Campanha campanha) {
        List<Usuario> destinatariosComPermissaoDeAprovacao = repositorioUsuarios.obterPorTipoPerfilPermissao(TipoPerfilUsuario.INTERNO.getValue(), ChavePermissao.getChave(ChavePermissao.CAMPANHA_APROVAR));

        Boolean alteradoParaRascunho = StatusCampanha.RASCUNHO.equals(StatusCampanha.obterPorValor(campanha.getStatus()));
        if(!alteradoParaRascunho){
            enviarNotificacao(TipoSubcategoriaNotificacao.CAMPANHA_DISPONIVEL_APROVACAO, destinatariosComPermissaoDeAprovacao, campanha.getNome(), campanha.getId().toString());
        } else {
            Set<Usuario> usuariosCriadoresEAprovadores = new HashSet<>();
            usuariosCriadoresEAprovadores.addAll(destinatariosComPermissaoDeAprovacao);
            usuariosCriadoresEAprovadores.add(campanha.getUsuarioCriador());
            List<Usuario> destinatriosFinais = usuariosCriadoresEAprovadores.stream().collect(Collectors.toList());
            enviarNotificacao(TipoSubcategoriaNotificacao.CAMPANHA_REJEITADA, destinatriosFinais, campanha.getNome(), campanha.getId().toString());
        }

    }

    /**
     * Exclu?? todas as notifica????es anteriores a uma data limite.
     *
     * @param dataLimite Data que representa o limite
     */
    public void excluirNotificacoesPorDataLimite(Date dataLimite) {
        repositorio.excluirNotificacoesAteUmaDataLimite(dataLimite);
    }

    /**
     * Notifica o gestor da revenda sobre entrada do ciclo no per??odo de ajuste
     * @param ciclo O ciclo sobre o qual o gestor ser?? notificado
     */
    public void enviarNotificacaoCicloEmAjuste(TransacaoConsolidada ciclo) {
        Rede rede = repositorioRede.obterPorPontoDeVenda(ciclo.getFrotaPtov().getPontoVenda().getId());
        List<Usuario> usuarios = repositorioUsuarios.obterGestorPorRede(rede.getId());
        enviarNotificacao(
                TipoSubcategoriaNotificacao.CICLO_EM_AJUSTE,
                usuarios,
                UtilitarioFormatacaoData.formatarDataCurta(ciclo.getDataInicioPeriodo()),
                UtilitarioFormatacaoData.formatarDataCurta(ciclo.getDataFimPeriodo()),
                UtilitarioFormatacaoData.formatarPeriodoDias(ciclo.getDataInicioPeriodo(), ciclo.getDataFimPeriodo(), false, " de ")
        );
    }
}