package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.NotaFiscal;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades nota fiscal
 */
public interface INotaFiscalDados extends IRepositorioBoleiaDados<NotaFiscal> {

    /**
     * Obtem as notas fiscais dos abastecimentos informados
     * @param idsAutorizacoes Os abastecimentos
     * @return A lista de notas fiscais
     */
    List<NotaFiscal> obterNotaDeVariosAbastecimentos(List<Long> idsAutorizacoes);

    /**
     * Obtem as notas fiscais que possuam o numero informado
     * @param numero O numero da nota
     * @return Uma lista de notas que possuam aquele numero
     */
    List<NotaFiscal> obterNotaPorNumero(String numero);
}
