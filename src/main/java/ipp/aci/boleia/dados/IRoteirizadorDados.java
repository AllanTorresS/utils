package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.RequisicaoRoteirizadorVo;
import ipp.aci.boleia.dominio.vo.RespostaRoteirizadorVo;

/**
 * Interação com serviço de roteirizador inteligente
 */
public interface IRoteirizadorDados {
    /**
     * Calcula rota com base e parâmetros informados
     * @param requisicao Parâmetros e serem considerados para o cálculo
     * @return Pontos geográficos da rota e informações adicionais
     */
    public RespostaRoteirizadorVo calcularRota(RequisicaoRoteirizadorVo requisicao);

}
