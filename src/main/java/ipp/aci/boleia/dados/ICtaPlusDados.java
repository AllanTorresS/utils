package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.CtaPlusInformaSincronismoVo;
import ipp.aci.boleia.dominio.vo.CtaPlusSincronizaAbastecimentoVo;

/**
 * Contrato para implementacao de repositórios de conexão com os servicos externos do Connect CTA.
 */
public interface ICtaPlusDados {

    /**
     * Obtém os abastecimentos registrados no CTA Plus
     * @param tokenFrota Token da frota os quais serão buscados os abastecimentos
     * @return vo contendo o status da requisição e os abastecimentos
     */
    CtaPlusSincronizaAbastecimentoVo buscarAbastecimentos(String tokenFrota);


    /**
     * Aciona o EndPoint do Connect Cta para informar o resultado do sincronismo
     * @param tokenFrota Token de acesso do serviço externo
     * @param ctaPlusInformaSincronismoVo Objeto com os dados validados a serem enviados
     * @return vo contendo o status da requisição
     */
    CtaPlusSincronizaAbastecimentoVo informarSincronismo(String tokenFrota, CtaPlusInformaSincronismoVo ctaPlusInformaSincronismoVo);
}
