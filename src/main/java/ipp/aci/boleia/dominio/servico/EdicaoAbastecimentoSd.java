package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoEdicaoDados;
import ipp.aci.boleia.dados.IItemAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.enums.CampoEdicaoAbastecimento;
import ipp.aci.boleia.dominio.enums.CampoItemAbastecimento;
import ipp.aci.boleia.dominio.vo.EdicaoAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.EdicaoCampoAbastecimentoVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Mantém a lógica de negócio relacionada a edição de abastecimentos.
 */
@Component
public class EdicaoAbastecimentoSd {
    private static final int SCALE_DOIS = 2;
    private static final int SCALE_TRES = 3;

    @Autowired
    private IItemAutorizacaoPagamentoDados repositorioItemAutorizacaoPagamento;

    @Autowired
    private IAutorizacaoPagamentoEdicaoDados repositorioAutorizacaoPagamentoEdicao;

    /**
     * Monta o objeto que mantém o conjunto de campos de abastecimento que sofreram edição
     *
     * @param autorizacaoPagamentoEdicao Registro da edição da autorização pagamento.
     * @param autorizacaoPagamento Autorização de pagamento que sofreu edição
     * @return Objeto com os campos editados.
     */
    public EdicaoAbastecimentoVo construirEdicaoAbastecimento(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao, AutorizacaoPagamento autorizacaoPagamento) {
        List<ItemAutorizacaoPagamento> itensAutorizacaoPagamento = repositorioItemAutorizacaoPagamento.obterItensPorIdAutorizacao(autorizacaoPagamento.getId());

        List<ItemAutorizacaoPagamentoEdicao> itensAutorizacaoPagamentoEditados = autorizacaoPagamentoEdicao.getItems();

        EdicaoAbastecimentoVo edicaoAbastecimentoVo = new EdicaoAbastecimentoVo();
        preencherAbastecimentoEdicao(edicaoAbastecimentoVo, autorizacaoPagamento, autorizacaoPagamentoEdicao);
        preencherItensAbastecimentoEdicao(edicaoAbastecimentoVo, itensAutorizacaoPagamento, itensAutorizacaoPagamentoEditados);
        return edicaoAbastecimentoVo;
    }

    /**
     * Preenche o {@link EdicaoAbastecimentoVo} com os campos que foram editados em uma Autorização de Pagamento.
     *
     * @param edicaoAbastecimentoVo {@link EdicaoAbastecimentoVo} a ser preenchido
     * @param autorizacaoPagamento autorização de pagamento original
     * @param autorizacaoPagamentoEdicao autorização de pagamento editada
     */
    private void preencherAbastecimentoEdicao(EdicaoAbastecimentoVo edicaoAbastecimentoVo, AutorizacaoPagamento autorizacaoPagamento, AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao) {

        List<EdicaoCampoAbastecimentoVo> camposEditados = new ArrayList<>();

        if(!autorizacaoPagamento.getPontoVenda().getId().equals(autorizacaoPagamentoEdicao.getPontoVenda().getId())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.PONTO_VENDA, autorizacaoPagamento.getPontoVenda().getNomeApresentacao(), autorizacaoPagamentoEdicao.getPontoVenda().getNomeApresentacao()));
        }

        if(!StringUtils.equals(autorizacaoPagamento.getCodigoVip(),autorizacaoPagamentoEdicao.getCodigoVip())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.CODIGO_VIP, autorizacaoPagamento.getCodigoVip(), autorizacaoPagamentoEdicao.getCodigoVip()));
        }

        if(!autorizacaoPagamento.getPlacaVeiculo().equals(autorizacaoPagamentoEdicao.getPlacaVeiculo())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.PLACA_VEICULO, autorizacaoPagamento.getPlacaVeiculo(), autorizacaoPagamentoEdicao.getPlacaVeiculo()));
        }

        if(!autorizacaoPagamento.getFrota().getId().equals(autorizacaoPagamentoEdicao.getFrota().getId())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.FROTA, autorizacaoPagamento.getFrota().getRazaoSocial(), autorizacaoPagamentoEdicao.getFrota().getRazaoSocial()));
        }

        if(!autorizacaoPagamento.getMotorista().getId().equals(autorizacaoPagamentoEdicao.getMotorista().getId())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.CPF_MOTORISTA, UtilitarioFormatacao.formatarCpfApresentacao(autorizacaoPagamento.getMotorista().getCpf()),UtilitarioFormatacao.formatarCpfApresentacao(autorizacaoPagamentoEdicao.getMotorista().getCpf())));
        }

        if(!autorizacaoPagamento.getNomeItemAbastecimento().equals(autorizacaoPagamentoEdicao.getNomeItemAbastecimento())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.COMBUSTIVEL, autorizacaoPagamento.getNomeItemAbastecimento(),autorizacaoPagamentoEdicao.getNomeItemAbastecimento()));
        }

        if(!autorizacaoPagamento.getValorTotal().equals(autorizacaoPagamentoEdicao.getValorTotal())) {
            camposEditados.add(new EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento.VALOR_TOTAL,  UtilitarioFormatacao.formatarDecimalMoedaReal(autorizacaoPagamento.getValorTotal(),SCALE_DOIS),UtilitarioFormatacao.formatarDecimalMoedaReal(autorizacaoPagamentoEdicao.getValorTotal(),SCALE_DOIS)));
        }

        edicaoAbastecimentoVo.getCamposEditados().addAll(camposEditados);
    }

    /**
     * Preenche o {@link EdicaoAbastecimentoVo} com os campos que foram editados em uma lista de itens de uma Autorização de Pagamento.
     * @param edicaoAbastecimentoVo {@link EdicaoAbastecimentoVo} a ser preenchido
     * @param itensAutorizacaoPagamento os itens da autorizacao de pagamento
     * @param itensAutorizacaoPagamentoEdicao os itens da autorizacao de pagamento editada
     */
    private void preencherItensAbastecimentoEdicao(EdicaoAbastecimentoVo edicaoAbastecimentoVo, List<ItemAutorizacaoPagamento> itensAutorizacaoPagamento, List<ItemAutorizacaoPagamentoEdicao> itensAutorizacaoPagamentoEdicao) {
        Map<Long, ItemAutorizacaoPagamento> produtoParaItem = new HashMap<>();
        Map<Long, ItemAutorizacaoPagamentoEdicao> produtoParaItemEdicao = new HashMap<>();
        if(itensAutorizacaoPagamentoEdicao == null) {
            itensAutorizacaoPagamentoEdicao = new ArrayList<>();
        }
        if(itensAutorizacaoPagamento == null) {
            itensAutorizacaoPagamento = new ArrayList<>();
        }
        List<EdicaoCampoAbastecimentoVo> camposEditados = new ArrayList<>();
        itensAutorizacaoPagamento.stream().filter(i ->  i != null && i.getProduto() != null).forEach(i -> produtoParaItem.put(i.getProduto().getId(), i));
        itensAutorizacaoPagamentoEdicao.stream().filter(i ->  i != null && i.getProduto() != null).forEach(i -> produtoParaItemEdicao.put(i.getProduto().getId(), i));

        //Combustivel
        formatarItemEdicao(camposEditados,
                itensAutorizacaoPagamento.stream().filter(i -> i != null && i.getCombustivel() != null).findFirst().orElse(null),
                itensAutorizacaoPagamentoEdicao.stream().filter(i -> i != null && i.getCombustivel() != null).findFirst().orElse(null));

        //itens
        for (Long idProduto : produtoParaItem.keySet()) {
            formatarItemEdicao(camposEditados, produtoParaItem.get(idProduto), produtoParaItemEdicao.getOrDefault(idProduto, null));
        }

        for (Long idProduto : produtoParaItemEdicao.keySet()) {
            if (!produtoParaItem.containsKey(idProduto)) {
                formatarItemEdicao(camposEditados, produtoParaItemEdicao.get(idProduto));
            }
        }
        edicaoAbastecimentoVo.getCamposEditados().addAll(camposEditados);

    }

    /**
     * Formata os itens em edição
     * @param camposEditados a lista de itens em edição
     * @param itemEdicao os itens da autorizacao de pagamento Edicao
     */
    private void formatarItemEdicao(List<EdicaoCampoAbastecimentoVo> camposEditados, ItemAutorizacaoPagamentoEdicao itemEdicao) {
        Long idProduto = itemEdicao.getProduto().getId();
        String categoria = itemEdicao.getProduto().getNome();
        String quantidadeItemEdicao = formatarQuantidadeProduto(itemEdicao.getQuantidade(), idProduto);
        String valorItemEdicao = formatarValorProduto(itemEdicao.getValorUnitario(), idProduto);
        String valorTotalItemEdicao= formatarValorProduto(itemEdicao.getValorTotal(), idProduto);

        camposEditados.add(new EdicaoCampoAbastecimentoVo(categoria, CampoEdicaoAbastecimento.QUANTIDADE, quantidadeItemEdicao));
        camposEditados.add(new EdicaoCampoAbastecimentoVo(categoria, CampoEdicaoAbastecimento.VALOR_UNITARIO, valorItemEdicao));
        camposEditados.add(new EdicaoCampoAbastecimentoVo(categoria, CampoEdicaoAbastecimento.VALOR_TOTAL, valorTotalItemEdicao));
    }

    /**
     * Preenche as informacoes do abastecimento edicao
     * @param camposEditados a lista de itens em edição
     * @param itemAtual os itens da autorizacao de pagamento Atual
     * @param itemEdicao os itens da autorizacao de pagamento Edicao
     */
    private void formatarItemEdicao(List<EdicaoCampoAbastecimentoVo> camposEditados, ItemAutorizacaoPagamento itemAtual, ItemAutorizacaoPagamentoEdicao itemEdicao) {
        if (itemAtual == null && itemEdicao == null) {
            return;
        }

        Long idProduto = itemAtual.getProduto() != null ? itemAtual.getProduto().getId() : Long.valueOf(CampoItemAbastecimento.COMBUSTIVEL.getValue().longValue());
        String categoria = itemAtual.getProduto()!= null ? itemAtual.getProduto().getNome() : CampoEdicaoAbastecimento.COMBUSTIVEL.getLabel();

        if (itemEdicao == null || !itemAtual.getQuantidade().equals(itemEdicao.getQuantidade())) {
            String quantidadeItemAtual = formatarQuantidadeProduto(itemAtual.getQuantidade(), idProduto);
            String quantidadeItemEdicao = itemEdicao != null ? formatarQuantidadeProduto(itemEdicao.getQuantidade(), idProduto) : null;
            camposEditados.add(new EdicaoCampoAbastecimentoVo(categoria, CampoEdicaoAbastecimento.QUANTIDADE, quantidadeItemAtual ,quantidadeItemEdicao));
        }
        if (itemEdicao == null || !itemAtual.getValorUnitario().equals(itemEdicao.getValorUnitario())) {
            String valorItemAtual = formatarValorProduto(itemAtual.getValorUnitario(), idProduto);
            String valorItemEdicao = itemEdicao != null ? formatarValorProduto(itemEdicao.getValorUnitario(), idProduto) : null;
            camposEditados.add(new EdicaoCampoAbastecimentoVo(categoria, CampoEdicaoAbastecimento.VALOR_UNITARIO,valorItemAtual, valorItemEdicao));
        }
        if (itemEdicao == null || !itemAtual.getValorTotal().equals(itemEdicao.getValorTotal())) {
            String valorTotalItemAtual = formatarValorProduto(itemAtual.getValorTotal(), idProduto);
            String valorTotalItemEdicao = itemEdicao != null ? formatarValorProduto(itemEdicao.getValorTotal(), idProduto) : null;
            camposEditados.add(new EdicaoCampoAbastecimentoVo(categoria, CampoEdicaoAbastecimento.VALOR_TOTAL, valorTotalItemAtual, valorTotalItemEdicao));
        }
    }

    /**
     * Formata a quantidade do produto com base no produto
     * @param quantidade do produto
     * @param idProduto o protudo
     * @return  a quantidade formatada
     *
     */
    private String formatarQuantidadeProduto(BigDecimal quantidade, Long idProduto) {
        if (CampoItemAbastecimento.ARLA32GRANEL.equals(CampoItemAbastecimento.obterPorValor(idProduto.intValue())) ||
                CampoItemAbastecimento.TROCAOLEO.equals(CampoItemAbastecimento.obterPorValor(idProduto.intValue())) ||
                CampoItemAbastecimento.COMBUSTIVEL.equals(CampoItemAbastecimento.obterPorValor(idProduto.intValue()))) {
            return UtilitarioFormatacao.formatarDecimalComTresCasas(quantidade);
        }
        return UtilitarioFormatacao.formatarInteiro(quantidade);
    }

    /**
     * Formata o valor do produto com base no produto
     * @param valor do produto
     * @param idProduto o protudo
     * @return  a quantidade formatada
     *
     */
    private String formatarValorProduto(BigDecimal valor, Long idProduto) {
        if (CampoItemAbastecimento.ARLA32GRANEL.equals(CampoItemAbastecimento.obterPorValor(idProduto.intValue())) ||
                CampoItemAbastecimento.TROCAOLEO.equals(CampoItemAbastecimento.obterPorValor(idProduto.intValue())) ||
                CampoItemAbastecimento.COMBUSTIVEL.equals(CampoItemAbastecimento.obterPorValor(idProduto.intValue()))) {
            return UtilitarioFormatacao.formatarDecimalMoedaReal(valor, SCALE_TRES);
        }
        return UtilitarioFormatacao.formatarDecimalMoedaReal(valor, SCALE_DOIS);
    }

    /**
     * Ordena os campos de edição para deixar o valor total da transação no final da lista
     * @param edicaoAbastecimentoVo o Vo que possui os campos a serem ordenados.
     */
    public void ordenarCamposEdicao(EdicaoAbastecimentoVo edicaoAbastecimentoVo){
       List<EdicaoCampoAbastecimentoVo> campos = edicaoAbastecimentoVo.getCamposEditados();
        int indexValorTotal = IntStream.range(0, campos.size())
                .filter(i -> campos.get(i).getCategoria() == null && campos.get(i).getCampo().equals(CampoEdicaoAbastecimento.VALOR_TOTAL.getLabel()))
                .findFirst()
                .orElse(-1);
        EdicaoCampoAbastecimentoVo campoValorTotal = campos.remove(indexValorTotal);
        campos.add(campoValorTotal);
        edicaoAbastecimentoVo.setCamposEditados(campos);
    }

    /**
     * Verifica se um abastecimento possui uma edição aprovada.
     * Obs.: Essa verificação é importante para saber se um abastecimento com edição pendente ou rejeitada já foi editado anteriormente.
     * @param id identificador do abastecimento.
     * @return true caso possua uma edição aprovada e false caso contrário.
     */
    public boolean possuiEdicaoAprovada(Long id) {
        return repositorioAutorizacaoPagamentoEdicao.verificarSeAbastecimentoPossuiEdicaoEfetuada(id);
    }
}
