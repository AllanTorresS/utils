package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IChamadoSistemaDados;
import ipp.aci.boleia.dados.IChamadoTipoDados;
import ipp.aci.boleia.dominio.ChamadoSistema;
import ipp.aci.boleia.dominio.ChamadoTipo;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades ChamadoSistema
 */
@Repository
public class OracleChamadoSistamaDados extends OracleRepositorioBoleiaDados<ChamadoSistema> implements IChamadoSistemaDados {


    /**
     * Instancia o repositorio
     */
    public OracleChamadoSistamaDados() {
        super(ChamadoSistema.class);
    }

    @Override
    public List<ChamadoSistema> obterSistemasChamado(TipoPerfilUsuario tipoPerfil) {
        List<ParametroPesquisa> params = new ArrayList<>();
        if(TipoPerfilUsuario.FROTA.equals(tipoPerfil)){
            params.add(new ParametroPesquisaIgual("portalFrotista",  Boolean.TRUE));
        }else if(TipoPerfilUsuario.REVENDA.equals(tipoPerfil)){
            params.add(new ParametroPesquisaIgual("portalRevendedor",  Boolean.TRUE));
        }else if (TipoPerfilUsuario.INTERNO.equals(tipoPerfil)){
            params.add(new ParametroPesquisaIgual("portalSolucao", Boolean.TRUE));
        }

        return pesquisar(new ParametroOrdenacaoColuna("nome"), params.toArray(new ParametroPesquisa[params.size()]));
    }
}
