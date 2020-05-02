package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IMediaConsumoDados;
import ipp.aci.boleia.dados.ISubTipoVeiculoDados;
import ipp.aci.boleia.dominio.SubTipoVeiculo;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMediaConsumoVo;
import ipp.aci.boleia.dominio.vo.MediaConsumoVo;
import ipp.aci.boleia.util.UtilitarioLambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Concentra a lógica de obtenção de faturas
 */
@Component
public class MediaConsumoSd {

    @Autowired
    private IMediaConsumoDados repositorioMediaConsumo;

    @Autowired
    private ISubTipoVeiculoDados repositorioSubtipoVeiculo;

    /**
     * Método que realiza pesquisa de média de consumo.
     *
     * @param filtro Filtro de pesquisa de consumo.
     * @return Lista paginada de média de consumo.
     */
    public ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoVeiculo(FiltroPesquisaMediaConsumoVo filtro) {
        return repositorioMediaConsumo.pesquisarMediaConsumoVeiculo(filtro, this::obterNomesSubtiposVeiculosPorTipo);
    }

    /**
     * Método que realiza pesquisa de média de consumo Motorista x Veículo
     *
     * @param filtro Filtro de pesquisa de consumo
     * @return Lista paginada de média de consumo
     */
    public ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoMotoristaVeiculo(FiltroPesquisaMediaConsumoVo filtro) {
        return repositorioMediaConsumo.pesquisarMediaConsumoMotoristaVeiculo(filtro, this::obterNomesSubtiposVeiculosPorTipo);
    }

    /**
     * Método que realiza pesquisa de média de consumo.
     *
     * @param filtro Filtro de pesquisa de consumo.
     * @return Lista paginada de média de consumo.
     */
    public ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoMotorista(FiltroPesquisaMediaConsumoVo filtro) {
        return repositorioMediaConsumo.pesquisarMediaConsumoMotorista(filtro);
    }

    /**
     * Método para obter função para mapeamento entre o tipo veiculo e seu subtipo.
     *
     * @return A função
     */
    public Function<String, String> obterMapeamentoSubTipoVeiculoTipo() {
        List<SubTipoVeiculo> subTipoVeiculos = repositorioSubtipoVeiculo.obterTodos(new ParametroOrdenacaoColuna("id"));
        Map<String, String> mapa = UtilitarioLambda.mapearLista(subTipoVeiculos, SubTipoVeiculo::getDescricao, SubTipoVeiculo::getTipoVeiculoDescricao);
        return subTipoVeiculo -> mapa.getOrDefault(subTipoVeiculo, null);
    }

    /**
     * Obtém a coleção de subtipos veículos por tipo de veículo
     *
     * @param idTipo Id do Tipo Veículo
     * @return Lista com os nome do subtipo veiculo.
     */
    private Collection<String> obterNomesSubtiposVeiculosPorTipo(Long idTipo) {
        return UtilitarioLambda.converterLista(repositorioSubtipoVeiculo.pesquisarPorTipo(idTipo), SubTipoVeiculo::getDescricao);
    }
}
