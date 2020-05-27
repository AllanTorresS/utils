package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaIntervaloAbastecimento;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;

import java.util.List;

/**
 * Repositório da entidade {@link FrotaParametroSistemaIntervaloAbastecimento}
 */
public interface IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados extends IRepositorioBoleiaDados<FrotaParametroSistemaPostoAutorizadoAbastecimento> {

    /**
     * Obtém uma lista com todos os postos autorizados para abastecimento dado um parametro de uso.
     *
     * @param parametroUso Parametro de uso utilizado na consulta.
     * @return Lista de postos autorizados.
     */
    List<FrotaParametroSistemaPostoAutorizadoAbastecimento> obterAutorizadosPorParametroUso(FrotaParametroSistema parametroUso);
}
