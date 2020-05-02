package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaDados;
import ipp.aci.boleia.dados.IFrotaParametroSistemaIntervaloAbastecimentoDados;
import ipp.aci.boleia.dados.IFrotaParametroSistemaProdutoAbastecimentoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProdutoAbastecimento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaIntervaloAbastecimentoDados}
 */
@Repository
public class OracleFrotaParametroSistemaProdutoAbastecimentoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaProdutoAbastecimento> implements IFrotaParametroSistemaProdutoAbastecimentoDados {

    @Autowired
    private IFrotaParametroSistemaDados repositorioParamSistema;
    /**
     * Instancia o repositorio
     */
    public OracleFrotaParametroSistemaProdutoAbastecimentoDados() {
        super(FrotaParametroSistemaProdutoAbastecimento.class);
    }

    @Override
    public List<FrotaParametroSistemaProdutoAbastecimento> obterPorVeiculo(Long idVeiculo){
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("veiculo", idVeiculo));
    }

    @Override
    public void incluirVeiculo(Veiculo veiculo) {
        FrotaParametroSistema frotaParametroSistema = repositorioParamSistema.obterPorParametroSistema(veiculo.getFrota().getId(), ParametroSistema.PRODUTO_ABASTECIMENTO);
        if (frotaParametroSistema != null && frotaParametroSistema.getAtivo()) {
            List<FrotaParametroSistemaProdutoAbastecimento> frotaParametroSistemaProdutoAbastecimentosList = veiculo.getCombustivelMotor().getTiposCombustivel().stream()
                    .map(tc -> new FrotaParametroSistemaProdutoAbastecimento(frotaParametroSistema, veiculo, tc, true)).collect(Collectors.toList());
            this.armazenarLista(frotaParametroSistemaProdutoAbastecimentosList);
        }
    }
}
