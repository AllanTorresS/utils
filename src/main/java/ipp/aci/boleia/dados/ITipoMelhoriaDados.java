package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TipoMelhoria;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades dos tipos de melhorias.
 */
public interface ITipoMelhoriaDados extends IRepositorioBoleiaDados<TipoMelhoria> {


    /**
     * Busca os tipos de melhoria pelos IDs informados
     *
     * @param idsTipoMelhoria Os ids informados
     * @return Uma lista de tipos de melhoria
     */
    List<TipoMelhoria> obterPorIds(List<Long> idsTipoMelhoria);

}
