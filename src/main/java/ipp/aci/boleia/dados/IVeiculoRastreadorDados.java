package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.VeiculoRastreador;
import ipp.aci.boleia.dominio.enums.TipoRastreador;

import java.util.List;

/**
 * Repositório para objetos da classe {@link VeiculoRastreador}
 *
 */
public interface IVeiculoRastreadorDados extends IRepositorioBoleiaDados<VeiculoRastreador> {

    /**
     * Método que obtém todos os veículos de um determinado tipo de rastreador
     *
     * @param tipoRastreador O tipo de rastreador cadastrado
     * @return Uma lista de veículos cadastrados
     */
    List<VeiculoRastreador> obterPorTipoRastreador(TipoRastreador tipoRastreador);

    /**
     * Método que retorna um veículo a partir de um idVeiculoSistema
     * @param idVeiculoSistema O idVeiculoSistema pesquisado.
     * @return O VeiculoRastreador procurado.
     */
    VeiculoRastreador obterPorIdVeiculoSistema(Integer idVeiculoSistema);

    /**
     * Método que verifica se existe veículo com a placa referenciada
     * @param placa Placa do veículo sem '-'
     * @return Se existe veiculo com placa
     */
    boolean existeVeiculoComPlaca(String placa);
}
