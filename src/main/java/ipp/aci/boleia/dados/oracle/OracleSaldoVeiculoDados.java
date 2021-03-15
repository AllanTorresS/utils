package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ISaldoVeiculoDados;
import ipp.aci.boleia.dominio.SaldoVeiculo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades {@link SaldoVeiculo}
 */
@Repository
public class OracleSaldoVeiculoDados extends OracleRepositorioBoleiaDados<SaldoVeiculo> implements ISaldoVeiculoDados {

    private static final String UPDATE_SALDOS = "update SaldoVeiculo s " +
            " set s.valorConsumido = 0, s.litrosConsumidos = 0 " +
            " where s.veiculo in ( " +
            "     select v from Veiculo v where v.excluido = 0 " +
            " ) ";

    /**
     * Instancia o repositorio
     */
    public OracleSaldoVeiculoDados() {
        super(SaldoVeiculo.class);
    }

    @Override
    public SaldoVeiculo obterPorVeiculo(Long idVeiculo) {
        return pesquisarUnico(new ParametroPesquisaIgual("veiculo.id", idVeiculo));
    }

    @Override
    public void renovarCotasDosVeiculos() {
        getGerenciadorDeEntidade().createQuery(UPDATE_SALDOS).executeUpdate();
    }
}
