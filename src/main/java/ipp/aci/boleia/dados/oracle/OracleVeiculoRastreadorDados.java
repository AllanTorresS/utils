package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IVeiculoRastreadorDados;
import ipp.aci.boleia.dominio.VeiculoRastreador;
import ipp.aci.boleia.dominio.enums.TipoRastreador;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementação do repositório de {@link VeiculoRastreador}
 *
 */
@Repository
public class OracleVeiculoRastreadorDados extends OracleRepositorioBoleiaDados<VeiculoRastreador> implements IVeiculoRastreadorDados {

    private static final String CAMPO_ID = "id";
    private static final String CAMPO_PLACA = "placa";

    /**
     * Construtor
     */
    public OracleVeiculoRastreadorDados() {
        super(VeiculoRastreador.class);
    }

    @Override
    public List<VeiculoRastreador> obterPorTipoRastreador(TipoRastreador tipoRastreador) {
        return pesquisar(new ParametroOrdenacaoColuna(CAMPO_ID), new ParametroPesquisaIgual("tipoRastreador",tipoRastreador.getValue()));
    }

    @Override
    public VeiculoRastreador obterPorIdVeiculoSistema(Integer idVeiculoSistema) {
        return pesquisarUnico(new ParametroPesquisaIgual("idVeiculoSistema",idVeiculoSistema));
    }

    @Override
    public boolean existeVeiculoComPlaca(String placa) {
        return pesquisarTotalRegistros(new ParametroPesquisaIgual(CAMPO_PLACA, placa)) > 0;
    }

}
