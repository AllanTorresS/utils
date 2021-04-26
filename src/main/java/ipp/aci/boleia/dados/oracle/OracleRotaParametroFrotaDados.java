package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IRotaParametroFrotaDados;
import ipp.aci.boleia.dominio.RotaParametroFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Rota
 */
@Repository
public class OracleRotaParametroFrotaDados extends OracleRepositorioBoleiaDados<RotaParametroFrota> implements IRotaParametroFrotaDados {

    /**
     * Construtor
     */
    public OracleRotaParametroFrotaDados() {
        super(RotaParametroFrota.class);
    }

    public List<RotaParametroFrota> obterPorRota(Long idRota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("rota.id", idRota));
    }
}
