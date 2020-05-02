package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMediaConsumoVo;
import ipp.aci.boleia.dominio.vo.MediaConsumoVo;
import ipp.aci.boleia.dominio.vo.VolumeAbastecidoTipoCombustivelVo;

import java.util.Collection;
import java.util.function.Function;

/**
 * Contrato para implementacao de acesso aos dados de MediaConsumo
 */
public interface IMediaConsumoDados extends IRepositorioBoleiaDados<AutorizacaoPagamento> {

    /**
     * Método que realiza a pesquisa por consumo de motorista.
     *
     * @param filtro Filtro de pesquisa
     * @return O resultado pagina com as médias de consumo do motorista
     */
    ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoMotorista(FiltroPesquisaMediaConsumoVo filtro);


    /**
     * Método que realiza a pesquisa por consumo de Veiculo.
     *
     * @param filtro Filtro de pesquisa
     * @param obterSubtiposVeiculosPorTipo Função que obtém uma coleção de subtipos veículos por id de tipo
     * @return O resultado pagina com as médias de consumo do Veiculo
     */
    ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoVeiculo(FiltroPesquisaMediaConsumoVo filtro,
                                                                   Function<Long, Collection<String>> obterSubtiposVeiculosPorTipo);

    /**
     * Método que realiza a pesquisa por consumo de Motorista x Veículo.
     *
     * @param filtro Filtro de pesquisa
     * @param obterSubtiposVeiculosPorTipo Função que obtém uma coleção de subtipos veículos por id de tipo
     * @return O resultado pagina com as médias de consumo de Motorista x Veiculo
     */
    ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoMotoristaVeiculo(FiltroPesquisaMediaConsumoVo filtro,
                                                                            Function<Long, Collection<String>> obterSubtiposVeiculosPorTipo);

    /**
     * Método que obtém percentual de variação no consumo de combustível num dado período.
     * @param usuarioFrotista O usuário frotista autenticado
     * @param periodo O período para analizar a variação
     * @return ResultadoPaginado com combustíveis abastecidos pela frota e seus percentuais de variação entre o período
     * informado e o anterior.
     */
    ResultadoPaginado<VolumeAbastecidoTipoCombustivelVo> pesquisarVolumeAbastecidoPorTipooCombustivel(Usuario usuarioFrotista,
                                                                                                      int periodo);
}
