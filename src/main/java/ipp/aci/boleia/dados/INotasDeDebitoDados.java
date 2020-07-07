package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Cobranca;

/**
 * Contrato para implementacao de reposiórios de Notas de Débito do JDE.
 *
 */
public interface INotasDeDebitoDados {

    /**
     * Verifica se uma cobrança está sem faturas no JDE
     *
     * @param cobranca a cobrança que deve ser verificada
     * @return true caso o serviço retorne 0 faturas para a cobrança, false caso contrário
     */
     boolean verificarCobrancaSemFatura(Cobranca cobranca);
}