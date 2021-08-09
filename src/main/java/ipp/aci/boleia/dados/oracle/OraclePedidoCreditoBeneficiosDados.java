package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPedidoCreditoBeneficiosDados;
import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;
import ipp.aci.boleia.dominio.enums.StatusPedidoCreditoBeneficios;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositório de entidades de PedidoCreditoBeneficios.
 */
@Repository
public class OraclePedidoCreditoBeneficiosDados extends OracleRepositorioBoleiaDados<PedidoCreditoBeneficios> implements IPedidoCreditoBeneficiosDados {

    /**
     * Instancia o repositório.
     */
    public OraclePedidoCreditoBeneficiosDados() {
        super(PedidoCreditoBeneficios.class);
    }

    @Override
    public List<PedidoCreditoBeneficios> obterPedidosCreditoBeneficioAbertos() {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDiferente("status", StatusPedidoCreditoBeneficios.PAGO.getValue()));
        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PedidoCreditoBeneficios> obterPedidosAbertos() {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaDiferente("status", StatusPedidoCreditoBeneficios.PAGO.getValue()));
    }
}
