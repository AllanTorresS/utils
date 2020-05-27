package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;

/**
 * Contrato para envio de {@link Frota} para alteração de parâmetro de ciclo
 */
public interface IFilaNovoParametroCicloAgendadoDados {

    /**
     * Envia a frota para alteração do parâmetro de ciclo
     *
     * @param frota frota agendada para alteração do parâmetro de ciclo
     */
    void enviarFrotaParaAplicarNovoCiclo(Frota frota);
}
