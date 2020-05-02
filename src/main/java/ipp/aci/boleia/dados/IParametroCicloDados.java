package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ParametroCiclo;

/**
 * Contrato para implementacao de repositorios de entidades de ParametroCiclo
 */
public interface IParametroCicloDados extends IRepositorioBoleiaDados<ParametroCiclo> {

    /**
     * Obtem o parametro de ciclo da frota
     * @param idFrota id da frota
     * @return parametro de ciclo da frota
     */
    ParametroCiclo obterParametroCicloDaFrota(Long idFrota);

    /**
     * Obtem o parametro de ciclo pelo prazo ciclo e prazo pagamento
     * @param prazoCiclo o prazo de ciclo
     * @param prazoPagamento o prazo de pagamento
     * @return parametro ciclo com estes parametros, se existir
     */
    ParametroCiclo obterPorPrazoCicloPrazoPagamento(Long prazoCiclo, Long prazoPagamento);

    @Override
    ParametroCiclo obterPorIdentificadorPadrao(String identificador);
}
