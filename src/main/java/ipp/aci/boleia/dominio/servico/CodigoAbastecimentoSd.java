package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IChaveValorDados;
import ipp.aci.boleia.dados.IPedidoMotoristaAutonomoDados;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.CodigoAbastecimentoVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementa as regras de negócio relativas ao código de abastecimento utilizado pelo Agenciador de Frete
 */
@Component
public class CodigoAbastecimentoSd {

    private final String PEDIDO_CODIGO_ABASTECIMENTO = "PEDIDO_CODIGO_ABASTECIMENTO";

    @Autowired
    private IPedidoMotoristaAutonomoDados pedidoMotoristaAutonomoDados;

    @Autowired
    private IChaveValorDados<List<CodigoAbastecimentoVo>> chaveValorDados;

    @Autowired
    protected UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o código de abastecimento pertencente a um pedido é valido
     * @param numeroPedido O número de pedido
     * @param codigoAbastecimento O código de abastecimento a ser validado
     * @throws ExcecaoValidacao Caso seja inválido
     */
    public void validarCodigoAbastecimento(String numeroPedido, String codigoAbastecimento) throws ExcecaoValidacao {
        List<CodigoAbastecimentoVo> codigos = chaveValorDados.obter(PEDIDO_CODIGO_ABASTECIMENTO, numeroPedido);
        if(codigos == null){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.abastecimento.codigo.invalido"));
        }

        CodigoAbastecimentoVo codigoAbastecimentoVo = codigos.stream().filter(c -> c.getCodigo().equals(codigoAbastecimento)).findFirst().orElse(null);
        if(codigoAbastecimentoVo == null || codigoAbastecimentoVo.getDataExpiracao().compareTo(ambiente.buscarDataAmbiente()) < 0){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.abastecimento.codigo.invalido"));
        }
    }

    /**
     * Armazena o código de abastecimento
     * @param numeroPedido O número de pedido
     * @param codigoAbastescimento O código de abastecimento a ser armazenado
     */
    public void armazenarCodigoAbastecimento(String numeroPedido, CodigoAbastecimentoVo codigoAbastescimento) {
        List<CodigoAbastecimentoVo> codigosAbastecimento = chaveValorDados.obter(PEDIDO_CODIGO_ABASTECIMENTO, numeroPedido);
        if(codigosAbastecimento == null) {
            codigosAbastecimento = new ArrayList<>();
        }
        codigosAbastecimento.add(codigoAbastescimento);
        chaveValorDados.inserir(PEDIDO_CODIGO_ABASTECIMENTO, numeroPedido, codigosAbastecimento);
    }

    /**
     * Gera um numero de código de abastecimento aleatoriamente
     * @return Uma string contendo o código
     */
    public String gerarNumeroDoCodigoAbastecimento() {
        Random aleatorio = new Random();
        int tamanhoCodigoABastecimento = 6;
        int moduloDaUnidade=10;
        StringBuilder codigoAbastecimento = new StringBuilder();
        for(int i=0; i<tamanhoCodigoABastecimento; i++){
            codigoAbastecimento.append(Math.abs(aleatorio.nextInt()) % moduloDaUnidade);
        }
        return codigoAbastecimento.toString();
    }
}
