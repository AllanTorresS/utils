package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRotaParametroFrotaDados;
import ipp.aci.boleia.dados.IRotaPostoDesconsideradoDados;
import ipp.aci.boleia.dominio.RotaPostoDesconsiderado;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Rota
 */
@Repository
public class OracleRotaPostoDesconsideradoDados extends OracleRepositorioBoleiaDados<RotaPostoDesconsiderado> implements IRotaPostoDesconsideradoDados {

    /**
     * Construtor
     */
    public OracleRotaPostoDesconsideradoDados() {
        super(RotaPostoDesconsiderado.class);
    }

    public List<RotaPostoDesconsiderado> obterPostoDesconsideradoPorRota(Long idRota) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("idPtov"), new ParametroPesquisaIgual("idRota", idRota));
    }


}
