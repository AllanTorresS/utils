package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPontoRotaDados;
import ipp.aci.boleia.dominio.PontoRota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Ponto Rota
 */
@Repository
public class OraclePontoRotaDados extends OracleRepositorioBoleiaDados<PontoRota> implements IPontoRotaDados {

    /**
     * Instancia o repositorio
     */
    public OraclePontoRotaDados() {
        super(PontoRota.class);
    }

    @Override
    public List<PontoRota> obterPorRota(Long idRota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("rota.id", idRota));
    }
}
