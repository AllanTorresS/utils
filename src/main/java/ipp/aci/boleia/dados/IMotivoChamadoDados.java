package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MotivoChamado;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades de motivo de chamado
 */
public interface IMotivoChamadoDados extends IRepositorioBoleiaDados<MotivoChamado> {

    /**
     * Pesquisa MotivoChamado a partir do filtro informado
     *
     * @return Uma lista de entidades localizadas
     */
    List<MotivoChamado> obterTodosOrdenadosPelaDescricao();
}
