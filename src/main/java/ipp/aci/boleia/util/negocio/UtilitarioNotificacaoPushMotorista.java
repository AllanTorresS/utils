package ipp.aci.boleia.util.negocio;


import ipp.aci.boleia.dados.IDispositivoMotoristaPedidoDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.DispositivoMotoristaPedido;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.TipoNotificacaoPush;
import ipp.aci.boleia.dominio.vo.BaseNotificacaoPushVo;
import ipp.aci.boleia.dominio.vo.ItemNotaMotoristaVo;
import ipp.aci.boleia.dominio.vo.NotaMotoristaVo;
import ipp.aci.boleia.dominio.vo.NotificacaoPushAcumuloMotoristaVo;
import ipp.aci.boleia.dominio.vo.NotificacaoPushPagamentoMotoristaVo;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.visao.dto.IItemNotaMotoristaDTO;
import ipp.aci.boleia.util.negocio.visao.dto.INotaMotoristaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static ipp.aci.boleia.dominio.enums.FuncionalidadePorVersaoAplicativoMotorista.NOTIFICACAO_ACUMULO_KMV;
import static ipp.aci.boleia.dominio.enums.FuncionalidadePorVersaoAplicativoMotorista.NOTIFICACAO_AVALIAR_POSTO;
import static ipp.aci.boleia.dominio.enums.FuncionalidadePorVersaoAplicativoMotorista.NOTIFICACAO_POR_TOPICO;
import static ipp.aci.boleia.util.UtilitarioComparacao.compararVersoes;

/**
 * Encapsula as regras de negócio que envolvem a manipulação de PrecosBase
 */
@Component
public class UtilitarioNotificacaoPushMotorista extends UtilitarioNotificacaoPushBase {

    private static final String CHAVE_MENSAGEM_PAGAMENTO_AUTORIZADO_TITULO = "motorista.servico.push.pagamento.autorizado.titulo";

    @Autowired
    private IDispositivoMotoristaPedidoDados repositorioDispositivoMotoristaPedido;

    @Autowired
    private IMotoristaDados repositorioMotoristaDados;

    @Autowired
    private Mensagens mensagens;

    /**
     * Envia uma notificação push para o dispositivo do motorista informando que o pagamento da nota foi finalizado com sucesso
     *
     * @param idPedido O código do pedido
     * @param idAbastecimento O codigo do abastecimento
     * @param idMotorista O código do motorista
     * @param idNota O código da nota
     * @param nomePontoVenda O nome do ponto de venda
     */
    @Async
    public void enviarNotificacaoPagamentoMotorista(Long idPedido, Long idMotorista, Long idNota, String nomePontoVenda, Long idAbastecimento) {
        if (idPedido != null) {
            final DispositivoMotoristaPedido pedido = repositorioDispositivoMotoristaPedido.obterPorId(idPedido);
            final DispositivoMotorista dispositivoMotorista = pedido.getDispositivoMotorista();

            NotificacaoPushPagamentoMotoristaVo vo = new NotificacaoPushPagamentoMotoristaVo();
            vo.setIdNota(idNota);
            vo.setIdAbastecimento(idAbastecimento);
            vo.setNomePontoVenda(nomePontoVenda.trim());
            vo.setTipoNotificacao(TipoNotificacaoPush.PAGAMENTO);
            if (dispositivoMotorista != null) {
                String versaoApp = dispositivoMotorista.getVersaoDoApp();
                String mensagem = compararVersoes(versaoApp, NOTIFICACAO_AVALIAR_POSTO.getVersaoAppAndroidIos()) ?
                        mensagens.obterMensagem("motorista.servico.push.pagamento.autorizado.avaliar.mensagem", vo.getNomePontoVenda()) :
                        mensagens.obterMensagem("motorista.servico.push.pagamento.autorizado.mensagem", vo.getNomePontoVenda());
                enviarNotificacaoPush(idMotorista, vo, dispositivoMotorista, mensagem);
            }
        }
    }

    /**
     * Envia uma notificação push para o dispositivo do motorista informando os pontos acumulados no Kmv por um pagamento
     *
     * @param idPedido O código do pedido
     * @param idMotorista O código do motorista
     * @param notaAutorizada a nota cujo pagamento foi autorizado
     * @param pontosAcumuladosKmv pontos acumulados no Kmv pelo pagamento
     */
    @Async
    public void enviarNotificacaoAcumuloKMVMotorista(Long idPedido, Long idMotorista, INotaMotoristaDTO notaAutorizada, Integer pontosAcumuladosKmv) {
        if (idPedido != null) {
            final DispositivoMotoristaPedido pedido = repositorioDispositivoMotoristaPedido.obterPorId(idPedido);
            final DispositivoMotorista dispositivoMotorista = pedido.getDispositivoMotorista();

            NotificacaoPushAcumuloMotoristaVo vo = new NotificacaoPushAcumuloMotoristaVo();
            vo.setNotaMotoristaDTO(criarNotaMotoristaVo(notaAutorizada));
            vo.setTipoNotificacao(TipoNotificacaoPush.KMV_ACUMULADO);
            if (permiteNotificacaoAcumulo(dispositivoMotorista)) {
                String mensagemCorpo = mensagens.obterMensagem("motorista.servico.push.acumuladoKmv.mensagem", pontosAcumuladosKmv.toString());
                enviarNotificacaoPush(idMotorista, vo, dispositivoMotorista, mensagemCorpo);
            }
        }
    }

    /**
     * Envia uma notificação push para o motorista.
     *
     * @param idMotorista Identificador do motorista.
     * @param dados Dados extras enviados na notificação.
     * @param dispositivoMotorista Dispositivo do motorista.
     * @param mensagem Mensagem enviada na notificação.
     */
    private <T extends BaseNotificacaoPushVo> void enviarNotificacaoPush(Long idMotorista, T dados, DispositivoMotorista dispositivoMotorista, String mensagem) {
        String versaoApp = dispositivoMotorista.getVersaoDoApp();
        String mensagemTitulo = mensagens.obterMensagem(CHAVE_MENSAGEM_PAGAMENTO_AUTORIZADO_TITULO);
        if (versaoApp != null && compararVersoes(versaoApp, NOTIFICACAO_POR_TOPICO.getVersaoAppAndroidIos())) {
            Motorista motorista = repositorioMotoristaDados.obterPorId(idMotorista);
            enviarNotificacaoViaTopico(motorista.getCpf().toString(), mensagemTitulo, mensagem, dados);
        } else if (dispositivoMotorista.getTokenPush() != null) {
            enviarNotificacaoViaToken(dispositivoMotorista.getTokenPush(), mensagemTitulo, mensagem, dados);
        }
    }

    /**
     * Verifica se o dispositivo pode receber a notificação de acumulo.
     * A verificação será feita de acordo com a versão do app.
     * @param dispositivoMotorista O dispositivo
     * @return True caso o dispositivo permita notificacao
     */
    private boolean permiteNotificacaoAcumulo(DispositivoMotorista dispositivoMotorista) {
        return dispositivoMotorista != null && dispositivoMotorista.getVersaoDoApp() != null &&
                compararVersoes(dispositivoMotorista.getVersaoDoApp(), NOTIFICACAO_ACUMULO_KMV.getVersaoAppAndroidIos());
    }

    /**
     * Cria um objeto VO com informações da nota de um motorista.
     *
     * @param notaAutorizada Nota da autorização.
     * @return Objeto VO criado.
     */
    private NotaMotoristaVo criarNotaMotoristaVo(INotaMotoristaDTO notaAutorizada) {
        ItemNotaMotoristaVo abastecimento = new ItemNotaMotoristaVo();
        abastecimento.setProdutoNome(notaAutorizada.getAbastecimento().getProdutoNome());
        abastecimento.setProdutoQuant(notaAutorizada.getAbastecimento().getProdutoQuant());
        abastecimento.setProdutoUnidade(notaAutorizada.getAbastecimento().getProdutoUnidade());
        abastecimento.setProdutoValor(notaAutorizada.getAbastecimento().getProdutoValor());

        List<ItemNotaMotoristaVo> listaProdServ = UtilitarioLambda.converterLista(notaAutorizada.getProdServ(), this::retornaListaDeProdutosEServicos);

        NotaMotoristaVo vo = new NotaMotoristaVo();
        vo.setId(notaAutorizada.getId());
        vo.setData(notaAutorizada.getData());
        vo.setPostoId(notaAutorizada.getPostoId());
        vo.setPostoNome(notaAutorizada.getPostoNome());
        vo.setPostoEndereco(notaAutorizada.getPostoEndereco());
        vo.setAbastecimento(abastecimento);
        vo.setProdServ(listaProdServ);
        vo.setTotal(notaAutorizada.getTotal());
        vo.setPlaca(notaAutorizada.getPlaca());
        vo.setKilometragem(notaAutorizada.getKilometragem());
        vo.setKilometragemTipo(notaAutorizada.getKilometragemTipo());
        vo.setAvaliacaoPendente(notaAutorizada.getAvaliacaoPendente());
        vo.setPontosAcumuladosKmv(notaAutorizada.getPontosAcumuladosKmv());
        return vo;
    }

    /**
     * Retorna o vo de um item de produtos e serviços da nota do motorista.
     * @param itemNota item da nota do motorista.
     * @return vo correspondente ao item recebido.
     */
    private ItemNotaMotoristaVo retornaListaDeProdutosEServicos(IItemNotaMotoristaDTO itemNota) {
        ItemNotaMotoristaVo produto = new ItemNotaMotoristaVo();
        produto.setProdutoNome(itemNota.getProdutoNome());
        produto.setProdutoQuant(itemNota.getProdutoQuant());
        produto.setProdutoUnidade(itemNota.getProdutoUnidade());
        produto.setProdutoValor(itemNota.getProdutoValor());
        return produto;
    }
}
