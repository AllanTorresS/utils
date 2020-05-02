package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IVipDados;
import ipp.aci.boleia.dominio.Vip;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades Vip
 */
@Repository
public class OracleVipDados extends OracleRepositorioBoleiaDados<Vip> implements IVipDados  {

    /**
     * Instancia o repositorio
     */
    public OracleVipDados() {
        super(Vip.class);
    }

    @Override
    public Vip obterVip(String codigoVip, Long codigoPontoDeVenda) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("codigoVip", codigoVip),
                new ParametroPesquisaIgual("codigoCorporativoPV", codigoPontoDeVenda));
    }
}
