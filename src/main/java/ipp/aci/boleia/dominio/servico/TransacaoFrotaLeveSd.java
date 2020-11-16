package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IConciliacaoPosAbasteceAiDados;
import ipp.aci.boleia.dados.ITransacaoFrotaLeveDados;
import ipp.aci.boleia.dominio.DispositivoMotoristaPedido;
import ipp.aci.boleia.dominio.TransacaoFrotaLeve;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.StatusConfirmacaoTransacao;
import ipp.aci.boleia.dominio.enums.StatusTransacaoPosAbasteceAi;
import ipp.aci.boleia.dominio.enums.TipoTransacaoFrotaLeve;
import ipp.aci.boleia.dominio.vo.RequisicaoPosAbasteceAiVo;
import ipp.aci.boleia.dominio.vo.RequisicaoServicoConciliacaoPosAbasteceAiVo;
import ipp.aci.boleia.dominio.vo.RespostaPosAbasteceAiVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Implementa as regras de negócio relativas as transacoes frota leve
 */
@Component
public class TransacaoFrotaLeveSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private ITransacaoFrotaLeveDados repositorio;

    @Autowired
    private IConciliacaoPosAbasteceAiDados repositorioConciliacaoAbasteceAi;

    /**
     * Cria uma transacao frota leve de queima (autorizacao) e a persiste no banco
     * @param nsuZacc NSU da POSAbasteceAi correspondente a transacao
     * @param pedido o pedido ao qual a transacao faz parte
     * @param codigoAbastecimento codigo de abastecimento
     * @param nsuProFrotas NSU gerado pelo Pro-Frotas
     * @param dataRequisicao data da transacao
     * @return a transacao de queima criada
     */
    public TransacaoFrotaLeve criarTransacaoQueima(String nsuZacc, DispositivoMotoristaPedido pedido, Integer codigoAbastecimento, String nsuProFrotas, Date dataRequisicao) {
        TransacaoFrotaLeve transacaoFrotaLeve = new TransacaoFrotaLeve(nsuZacc, pedido, TipoTransacaoFrotaLeve.AUTORIZAR.getValue(), codigoAbastecimento, StatusConfirmacaoTransacao.NAO_CONFIRMADO.getValue(), nsuProFrotas, dataRequisicao);
        repositorio.armazenar(transacaoFrotaLeve);

        return transacaoFrotaLeve;
    }

    /**
     * Cria uma nova tentativa de queima referente a uma queima anterior que falhou
     * @param nsuZacc NSU da nova tentativa
     * @param nsuZaccOriginal NSU da queima original
     * @param pedido o pedido
     * @param codigoAbastecimento codigo de abastecimento
     * @param nsuProFrotas NSU gerado pelo Pro-Frotas
     * @param dataRequisicao data da transacao
     * @return a transacao da nova tentativa de queima
     */
    public TransacaoFrotaLeve criarNovaTentativaQueima(String nsuZacc, String nsuZaccOriginal, DispositivoMotoristaPedido pedido, Integer codigoAbastecimento, String nsuProFrotas, Date dataRequisicao) {
        TransacaoFrotaLeve transacaoFrotaLeve = new TransacaoFrotaLeve(nsuZacc, pedido, TipoTransacaoFrotaLeve.AUTORIZAR.getValue(), codigoAbastecimento, StatusConfirmacaoTransacao.NAO_CONFIRMADO.getValue(), nsuProFrotas, dataRequisicao);
        transacaoFrotaLeve.setNsuZaccOriginal(nsuZaccOriginal);
        repositorio.armazenar(transacaoFrotaLeve);

        return transacaoFrotaLeve;
    }

    /**
     * Altera uma transacao frota leve para desfaze-la e a persiste no banco
     * @param transacaoQueima a transacao a ser desfeita
     */
    public void confirmarFalhaTransacaoQueima(TransacaoFrotaLeve transacaoQueima) {
        List<TransacaoFrotaLeve> transacoesQueima = repositorio.obterTransacoesQueimaPorPedido(transacaoQueima.getPedido().getId());
        for(TransacaoFrotaLeve transacao : transacoesQueima) {
            transacao.setNsuZaccOriginal(transacaoQueima.getNsuZacc());
            transacao.setStatusConfirmacao(StatusConfirmacaoTransacao.CONFIRMADO_FALHA.getValue());
            transacao.setDataAtualizacao(ambiente.buscarDataAmbiente());
        }
        repositorio.armazenarLista(transacoesQueima);
    }

    /**
     * Cria uma transacao frota leve de confirmacao, e altera o status de confirmacao da transacao de queima
     * @param tipoConfirmacao tipo de confirmacao (sucesso ou falha)
     * @param transacaoQueima a transacao original de queima
     * @param nsuZacc NSU da POSAbasteceAi
     * @param dataRequisicao data da requisicao
     */
    public void criarTransacaoConfirmacao(Integer tipoConfirmacao, TransacaoFrotaLeve transacaoQueima, String nsuZacc, Date dataRequisicao) {
        List<TransacaoFrotaLeve> transacoesQueima = repositorio.obterTransacoesQueimaPorPedido(transacaoQueima.getPedido().getId());
        Date dataAmbiente = ambiente.buscarDataAmbiente();
        for(TransacaoFrotaLeve transacao : transacoesQueima) {
            transacao.setStatusConfirmacao(tipoConfirmacao);
            transacao.setDataAtualizacao(dataAmbiente);
        }
        repositorio.armazenarLista(transacoesQueima);

        TransacaoFrotaLeve transacaoConfirmacao = new TransacaoFrotaLeve(nsuZacc, transacaoQueima.getPedido(), TipoTransacaoFrotaLeve.CONFIRMAR.getValue(), transacaoQueima.getChaveNumeroPedido(), tipoConfirmacao, UUID.randomUUID().toString(), dataRequisicao);
        transacaoConfirmacao.setStatusConfirmacao(tipoConfirmacao);
        transacaoConfirmacao.setNsuZaccOriginal(transacaoQueima.getNsuZacc());
        repositorio.armazenar(transacaoConfirmacao);
    }

    /**
     * Consulta o status de uma transacao no servico do POS/Abastece aí caso haja uma segunda tentativa de queima
     * @param nsuPosAbasteceAiQueima NSU a ser consultado no servico do POS/Abastece aí
     * @return retorna o status que o abastecimento deve assumir dependendo da resposta do servico do POS/Abastece aí
     */
    public Integer consultarStatusTransacaoPOSAbasteceAi(String nsuPosAbasteceAiQueima) {
        List<RequisicaoServicoConciliacaoPosAbasteceAiVo> listaRequisicao = new ArrayList<>();
        listaRequisicao.add(new RequisicaoServicoConciliacaoPosAbasteceAiVo(Long.parseLong(nsuPosAbasteceAiQueima)));
        RespostaPosAbasteceAiVo resposta = repositorioConciliacaoAbasteceAi.obterStatusTransacoes(listaRequisicao);
        for (RequisicaoPosAbasteceAiVo transacao : resposta.getObjetoRetorno()) {
            if(StatusTransacaoPosAbasteceAi.CONFIRMACAO.getValue().equals(transacao.getStatusCode())) {
                return StatusAutorizacao.NAO_AUTORIZADO.getValue();
            }
            else if(StatusTransacaoPosAbasteceAi.DESFEITA.getValue().equals(transacao.getStatusCode())) {
                return StatusAutorizacao.AUTORIZADO.getValue();
            }
            else {
                return StatusAutorizacao.NAO_AUTORIZADO.getValue();
            }
        }
        return null;
    }
}
