package ipp.aci.boleia.dados.oracle;

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

    public List<RotaPostoDesconsiderado> obterPorRota(Long idRota) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("idRota", idRota));
    }


}
