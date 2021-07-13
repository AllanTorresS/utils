package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.RequisicaoRoteirizadorValidarVo;
import ipp.aci.boleia.dominio.vo.RequisicaoRoteirizadorVo;
import ipp.aci.boleia.dominio.vo.RespostaRoteirizadorValidarVo;
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
    RespostaRoteirizadorVo calcularRota(RequisicaoRoteirizadorVo requisicao);

    /**
     * Valida rota com base e parâmetros informados e postos escolhidos
     * @param requisicao Rota a ser validada
     * @return Status se a rota informada é uma rota valida
     */
    RespostaRoteirizadorValidarVo validarRota(RequisicaoRoteirizadorValidarVo requisicao);

}
