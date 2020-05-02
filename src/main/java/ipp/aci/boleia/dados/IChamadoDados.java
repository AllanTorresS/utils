package ipp.aci.boleia.dados;

/**
 * Contrato para implementacao de repositorios
 * de entidades de chamado
 */
@FunctionalInterface
public interface IChamadoDados {

    /**
     * Abre chamado no SalesForce
     *
     * @param company do chamado
     * @param name do chamado
     * @param email do chamado
     * @param phone do chamado
     * @param idReason do chamado
     * @param subject do chamado
     * @param description do chamado
     * @return true se o chamado for enviado para o SalesForce, false caso contrário
     */
    boolean abrirChamado(String company, String name, String email, String phone, Long idReason, String subject, String description);
}