package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.Motorista;

/**
 * Interface de abstracao para objetos do dominio pertencentes a um motorista,
 * para automatizacao do isolamento de dados
 */
public interface IPertenceMotorista {

    /**
     * Nomes dos atributos na entidade
     */
    String CAMPO_MOTORISTA = "motorista.id";
    String CAMPO_MOTORISTA_ID = "id";

    /**
     * Retorna o motorista associado a entidade
     * @return O motorista associado
     */
    Motorista getMotorista();

    /**
     * Obtem o caminho para obtencao do motorista atraves da entidade informada
     * @param clazz Entidade a obter o motorista
     * @return Caminho
     */
    static String obterCaminhoMotorista(Class<?> clazz) {
        if(clazz.equals(Motorista.class)) {
            return CAMPO_MOTORISTA_ID;
        }
        return CAMPO_MOTORISTA;
    }

}
