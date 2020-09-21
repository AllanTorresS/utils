package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IAutorizacaoPagamentoEdicaoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscalAbastecimento;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAutorizacaoPagamentoEdicaoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMinutosData;

/**
 * Implementa as regras de negocio relacionadas a entidade TransacaoConsolidada
 */
@Component
public class AutorizacaoPagamentoSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private NotificacaoUsuarioSd notificacaoUsuarioSd;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

    @Autowired
    private IAutorizacaoPagamentoEdicaoDados repositorioAutorizacaoPagamentoEdicao;

    /**
     * Obtém lista de abastecimentos para exportação de acordo com o filtro informado.
     *
     * @param filtro O filtro da última busca
     * @return Uma Resultado Paginado de AutorizacaoPagamento
     */
    public ResultadoPaginado<AutorizacaoPagamento> pesquisarAbastecimentosParaExportacao(FiltroPesquisaAbastecimentoVo filtro) {
        return pesquisarAbastecimentosParaExportacao(filtro, Collections.singletonList(new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.DECRESCENTE)));
    }

    /**
     * Obtém lista de abastecimentos para exportação de acordo com o filtro informado.
     *
     * @param filtro O filtro da última busca
     * @param ordenacao Parametros de ordenação da consulta
     * @return Uma Resultado Paginado de AutorizacaoPagamento
     */
    public ResultadoPaginado<AutorizacaoPagamento> pesquisarAbastecimentosParaExportacao(FiltroPesquisaAbastecimentoVo filtro, List<ParametroOrdenacaoColuna> ordenacao) {
        filtro.getPaginacao().setTamanhoPagina(null);
        filtro.getPaginacao().setParametrosOrdenacaoColuna(ordenacao);
        return repositorioAutorizacaoPagamento.pesquisaPaginada(filtro, true);
    }

    /**
     * Realiza as validações necessárias para a autorização de um abastecimento pendente.
     *
     * @param autorizacao Autorização de pagamento do abastecimento.
     * @throws ExcecaoValidacao em caso de erros na validação da autorização de abastecimento.
     */
    public void validarAutorizacaoAbastecimentoPendente(AutorizacaoPagamento autorizacao) throws ExcecaoValidacao {
        if(isPrazoAutorizacaoPendenteExpirado(autorizacao)) {
            throw new ExcecaoValidacao(Erro.PRAZO_AUTORIZACAO_ABASTECIMENTO_PENDENTE_EXPIRADO);
        } else if(!autorizacao.isPendenteAutorizacao()) {
            throw new ExcecaoValidacao(Erro.AUTORIZACAO_ABASTECIMENTO_PENDENTE_NAO_PERMITIDA);
        }
    }

    /**
     * Validar se o horário de uma requisicao de abastecimento de frotas leves está dentro do tempo de tolerância
     * @param dataRequisicao A data da requisicao
     * @return true caso esteja na tolerância
     */
    public Boolean validarHorarioAbastecimentoFrotaLeve(Date dataRequisicao){
        final long TEMPO_MAX_REQUISICAO = 5L;

        return UtilitarioCalculoData.diferencaEmMinutos(dataRequisicao,ambiente.buscarDataAmbiente()) < TEMPO_MAX_REQUISICAO;
    }

    /**
     * Verifica se o prazo de autorização para um abastecimento pendente já expirou.
     * @param autorizacaoPagamento a autorizacao pagamento que tera o prazo checado
     * @return true, caso tenha expirado.
     */
    private boolean isPrazoAutorizacaoPendenteExpirado(AutorizacaoPagamento autorizacaoPagamento) {
        Date dataExpiracao = adicionarMinutosData(autorizacaoPagamento.getDataRequisicao(), 20);
        return ambiente.buscarDataAmbiente().after(dataExpiracao);
    }

    /**
     * Retorna lista de Autorizacao Pagamento Edicao que tem a necessidade de alterar o status
     * de edição de cada registro de Autorizacao Pagamento se o mesmo teve alguma alteracao
     *
     * @param autorizacaoPagamentos Lista de abastecimentos presentes em um ciclo
     * @return Lista de AutorizacaoPagamentoEdicao que precisam ser alteradas
     */
    public List<AutorizacaoPagamentoEdicao> obtemAutorizacoesPendentesDeUmConsolidado(List<AutorizacaoPagamento> autorizacaoPagamentos) {
        List<AutorizacaoPagamentoEdicao> autorizacoesPendentes = new ArrayList<>();

        for (AutorizacaoPagamento autorizacaoPagamento: autorizacaoPagamentos) {
            List<AutorizacaoPagamentoEdicao> edicoesPendentesAbastecimento = repositorioAutorizacaoPagamentoEdicao.pesquisar(
                            new FiltroPesquisaAutorizacaoPagamentoEdicaoVo(autorizacaoPagamento.getId(), StatusEdicao.PENDENTE, new ParametroOrdenacaoColuna[] {
                                    new ParametroOrdenacaoColuna("dataEdicao", Ordenacao.DECRESCENTE),
                            }))
                            .getRegistros();

            if (!edicoesPendentesAbastecimento.isEmpty()) {
                autorizacoesPendentes.add(edicoesPendentesAbastecimento.get(0));
            }
        }

        return autorizacoesPendentes;
    }

    /**
     * Recebe uma AutorizacaoPagamentoEdicao e troca seu status para expirado, em seguida, aramazena
     *
     * @param autorizacaoPagamentoEdicao AutorizacaoPagamentoEdicao a ser expirada
     */
    public void expirarEdicaoAbastecimento(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao) {
        autorizacaoPagamentoEdicao.setStatusEdicao(StatusEdicao.EXPIRADO.getValue());
        repositorioAutorizacaoPagamentoEdicao.armazenar(autorizacaoPagamentoEdicao);
    }

    /**
     * Reverte o status de edição de autorizações de PENDENTE para NAO_EDITADO.
     * @param abastecimentos a lista de autorizações que terão status revertidos.
     * @return a lista de abastecimentos atualizada.
     */
    public List<AutorizacaoPagamento> reverterStatusEdicao(List<AutorizacaoPagamento> abastecimentos) {
        abastecimentos.forEach(abastecimento ->
                abastecimento.setStatusEdicao(StatusEdicao.PENDENTE.getValue().equals(abastecimento.getStatusEdicao()) ?
                        StatusEdicao.EXPIRADO.getValue() : abastecimento.getStatusEdicao()));
        repositorioAutorizacaoPagamento.armazenarLista(abastecimentos);
        return abastecimentos;
    }

    /**
     * Obtém o status de um abastecimento.
     *
     * @param autorizacao Um abastecimento
     * @param tipoPerfilUsuario O tipo perfil do usuário
     * @return O código de status de abastecimento
     */
    public Integer obtemStatusAbastecimento(AutorizacaoPagamento autorizacao, TipoPerfilUsuario tipoPerfilUsuario) {
        return tipoPerfilUsuario.equals(TipoPerfilUsuario.INTERNO) &&
                StatusAutorizacao.AUTORIZADO.getValue().equals(autorizacao.getStatus()) &&
                StatusEdicao.PENDENTE.getValue().equals(autorizacao.getStatusEdicao()) ?
                StatusAutorizacao.AGUARDANDO_APROVACAO_SOLUCAO.getValue() :
                autorizacao.getStatus();
    }

    /**
     * Define o status de emissão de um abastecimento para emitido.
     *
     * @param autorizacao Abastecimento que ficará como emitido.
     * @param notaFiscal Nota fiscal emitida para o abastecimento.
     */
    public void definirAbastecimentoEmitido(AutorizacaoPagamento autorizacao, NotaFiscal notaFiscal) {
        autorizacao.setStatusNotaFiscal(StatusNotaFiscalAbastecimento.EMITIDA.getValue());
        autorizacao = repositorioAutorizacaoPagamento.armazenar(autorizacao);
        notificacaoUsuarioSd.enviarNotificacaoNotaFiscalEmitida(autorizacao, notaFiscal);
    }

    /**
     * Verifica se a transacao positiva ajustada oriunda de um estorno esta no mesmo ciclo da transacao estornada
     *
     * @param autorizacaoOriginal abastecimento estornado
     * @return true, se estiver no mesmo ciclo
     */
    public boolean transacaoAjustadaEstaNoMesmoCicloDaTransacaoEstornadaOriginal(AutorizacaoPagamento autorizacaoOriginal) {

        AutorizacaoPagamento transacaoAjustada = repositorioAutorizacaoPagamento.obterTransacaoAjustadaOriundaDeEstorno(autorizacaoOriginal);

        //verifica se o ciclo em que a transacao positiva ajustada foi criada coincide com o cilo mais atual (original ou de postergacao) da transacao estornada
        return transacaoAjustada != null &&  transacaoAjustada.getTransacaoConsolidada() != null &&
            transacaoAjustada.getTransacaoConsolidada().getId().equals(autorizacaoOriginal.getTransacaoConsolidadaVigente().getId());

    }

    /**
     * Verifica se a transacao negativa oriunda de um estorno esta no mesmo ciclo da transacao estornada
     *
     * @param autorizacaoOriginal abastecimento estornado
     * @return true, se estiver no mesmo ciclo
     */
    public boolean transacaoNegativaEstaNoMesmoCicloDaTransacaoEstornadaOriginal(AutorizacaoPagamento autorizacaoOriginal) {

        AutorizacaoPagamento transacaoNegativa = repositorioAutorizacaoPagamento.obterTransacaoNegativaOriundaDeEstorno(autorizacaoOriginal);

        //verifica se o ciclo em que a transacao negativa foi criada coincide com o cilo mais atual (original ou de postergacao) da transacao estornada
        return transacaoNegativa != null && transacaoNegativa.getTransacaoConsolidada() != null &&
            transacaoNegativa.getTransacaoConsolidada().getId().equals(autorizacaoOriginal.getTransacaoConsolidadaVigente().getId());
    }

    /**
     * Verifica se uma transacao ja estava cancelada ou estornada quando seu ciclo mais atual (original ou de postergacao) foi fechado
     *
     * @param autorizacaoOriginal abastecimento original
     * @return true, se a transacao original ja estava cancelada/estornada quando o ciclo foi fechado
     */
    private boolean transacaoEstavaCanceladaOuEstornadaQuandoCiCloFoiFechado(AutorizacaoPagamento autorizacaoOriginal, AutorizacaoPagamento transacaoNegativa) {

        if(autorizacaoOriginal.estaCancelado()){
            //Nota: se a transacao negativa estiver no ciclo mais atual da transacao original (cancelada/estornada), isso significa que a transacao original ja estava com status CANCELADO quando o ciclo foi fechado
            return transacaoNegativa != null
                    && transacaoNegativa.getTransacaoConsolidada().getId().equals(autorizacaoOriginal.getTransacaoConsolidadaVigente().getId());
        }else{
            return false;
        }

    }

    /**
     * Verifica se o valor de uma transacao cancelada/estornada deve ser descontado em ciclos posteriores
     *
     * @param autorizacaoOriginal abastecimento original
     * @return true, se o valor da transacao tiver sido contemplado em algum reembolso gerado
     */
    public boolean valorDaTransacaoFoiContempladoEmReembolsoGerado(AutorizacaoPagamento autorizacaoOriginal) {

        if(autorizacaoOriginal.estaCancelado()) {

            AutorizacaoPagamento transacaoNegativa = repositorioAutorizacaoPagamento.obterTransacaoNegativaOriundaDeEstorno(autorizacaoOriginal);

            if(transacaoNegativa != null){
                //Nota: se a transacao nao tem pendencia de emissao e se ela estava autorizada no momento do fechamento de seu ciclo mais atual (original ou de postergacao), entao seu valor foi contemplado no reembolso gerado para esse ciclo
                return autorizacaoOriginal.emitidaEmCicloFechado()
                        && (transacaoNegativa.getTransacaoConsolidada() == null || !transacaoEstavaCanceladaOuEstornadaQuandoCiCloFoiFechado(autorizacaoOriginal,transacaoNegativa));
            }else{
                return false;
            }

        }else{
           return false;
        }

    }

    /**
     * Indica se uma transacao cancelada é relevante para exibição no detalhemento de notas fiscais e para contabilização na quantidade de transacoes do ciclo
     * @param transacao transacao cancelada que deve ser avaliada quanto aos criterios de exibicao e contabilização
     * @return true, se a transacao deve ser exibida
     */
    public boolean abastecimentoCanceladoDeveSerExibidoEContabilizado(AutorizacaoPagamento transacao){

        //se o abastecimento for cancelado, o positivo cancelado so deve ser exibido na tela de NF na visao da revenda se seu valor tiver sido considerado para reembolso gerado ou se ele tiver sido postergado
        return valorDaTransacaoFoiContempladoEmReembolsoGerado(transacao)
                || transacao.getTransacaoConsolidadaPostergada() != null;
    }

    /**
     * Indica se uma transacao estornada é relevante para exibição no detalhemento de notas fiscais e para contabilização na quantidade de transacoes do ciclo
     * @param transacao transacao estornada que deve ser avaliada quanto aos criterios de exibicao e contabilização
     * @param idConsolidadoQueEstaSendoVisualizadoOuProcessado identificador do ciclo que está sendo viauslizado ou processado
     * @return true, se a transacao deve ser exibida
     */
    public boolean abastecimentoEstornadoDeveSerExibidoEContabilizado(AutorizacaoPagamento transacao, Long idConsolidadoQueEstaSendoVisualizadoOuProcessado){

        //abastecimentos estornados sempre devem ser exibidos no ciclo de origem
        if(transacao.getTransacaoConsolidada() != null && transacao.getTransacaoConsolidada().getId().equals(idConsolidadoQueEstaSendoVisualizadoOuProcessado)){
            return true;
        }else{
            //se o abastecimento for estornado, o positivo cancelado so vai ser exibido na tela de NF do ciclo de postergacao na visao da revenda se
            //ele nao tiver pendencia de emissao e se a transacao positiva ajustada nao estiver no ciclo mais atual (de origem ou postergacao) do abastecimento estornado que a originou e se a transacao negativa estiver no ciclo mais atual (de origem ou postergacao) do abastecimento estornado que a originou
            //(cenario de estorno de abastecimento postergado com emissao em ciclo em ajuste)
            return !transacao.isPendenteEmissaoNF(true)
                    && !transacaoAjustadaEstaNoMesmoCicloDaTransacaoEstornadaOriginal(transacao)
                    && transacaoNegativaEstaNoMesmoCicloDaTransacaoEstornadaOriginal(transacao);

        }

    }
}
