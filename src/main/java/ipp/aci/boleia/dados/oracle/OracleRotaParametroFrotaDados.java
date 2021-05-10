package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IRotaParametroFrotaDados;
import ipp.aci.boleia.dominio.RotaParametroFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades RotaParametroFrota que define a relação entre Rota e os Parametros de Sistema utilizados na rota
 */
@Repository
public class OracleRotaParametroFrotaDados extends OracleRepositorioBoleiaDados<RotaParametroFrota> implements IRotaParametroFrotaDados {

    /**
     * Construtor Padrão
     */
    public OracleRotaParametroFrotaDados() {
        super(RotaParametroFrota.class);
    }

    @Override
    public List<RotaParametroFrota> obterPorRota(Long idRota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("rota.id", idRota));
    }
}
