package ipp.aci.boleia.util.negocio.visao.dto;

import java.math.BigDecimal;

/**
 * Representa a interface para um item de uma nota de compra exibida no aplicativo do motorista
 */
public interface IItemNotaMotoristaDTO {

    String getProdutoNome();
    String getProdutoUnidade();
    BigDecimal getProdutoQuant();
    BigDecimal getProdutoValor();
}
