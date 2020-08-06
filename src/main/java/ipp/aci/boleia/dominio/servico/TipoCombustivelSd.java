package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.ITipoCombustivelDados;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementa as regras de negocio relacionadas a entidade TipoCombustivel
 */
@Component
public class TipoCombustivelSd {

    @Autowired
    private ITipoCombustivelDados repositorioTipoCombustivel;

    /**
     * Lista os tipos de combustivel compativeis ao veiculo.
     * @param veiculo veiculo para listar os tipos compativeis.
     * @return os tipos de combustivel compativeis ao veiculo.
     */
    public List<TipoCombustivel> listarTiposCombustivelCompativeis(final Veiculo veiculo) {

        if (veiculo.getCombustivelMotor().isOleoDiesel()) {
            return repositorioTipoCombustivel.buscarPorTipoCombustivelMtec(veiculo.getCombustivelMotor().getTipoCombustivelMtec());
        }
        return veiculo.getCombustivelMotor().getTiposCombustivel();
    }
}
