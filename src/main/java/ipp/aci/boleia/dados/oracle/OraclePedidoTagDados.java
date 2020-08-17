package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.IPedidoTagDados;
import ipp.aci.boleia.dominio.PedidoTag;
import ipp.aci.boleia.dominio.enums.StatusPedidoTag;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPedidoTagVo;

/**
 * Repositório de entidades PedidoTag
 */

@Repository
public class OraclePedidoTagDados extends OracleRepositorioBoleiaDados<PedidoTag> implements IPedidoTagDados {

    /**
     * Instancia o repositório
     */
    public OraclePedidoTagDados() {
        super(PedidoTag.class);
    }

    @Override
    public ResultadoPaginado<PedidoTag> pesquisar(FiltroPesquisaPedidoTagVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        
        if(filtro.getFrota() != null) {
        	parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }
        
        if(filtro.getDe() != null) {
        	parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataPedido", filtro.getDe()));
        }
        
        if(filtro.getAte() != null) {
        	parametros.add(new ParametroPesquisaDataMenorOuIgual("dataPedido", filtro.getAte()));
        }
        
        if (filtro.getId() != null) {
            parametros.add(new ParametroPesquisaIgual("id", filtro.getId()));
        }
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusPedidoTag.valueOf(filtro.getStatus().getName()).getValue()));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

   
}