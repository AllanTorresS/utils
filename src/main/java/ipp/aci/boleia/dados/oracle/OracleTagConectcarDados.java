package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ITagConectcarDados;
import ipp.aci.boleia.dominio.TagConectcar;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTagConectcarVo;

/**
 * Repositório de entidades TagConectcar
 */

@Repository
public class OracleTagConectcarDados extends OracleRepositorioBoleiaDados<TagConectcar> implements ITagConectcarDados {

    /**
     * Instancia o repositório
     */
    public OracleTagConectcarDados() {
        super(TagConectcar.class);
    }

    @Override
    public ResultadoPaginado<TagConectcar> pesquisar(FiltroPesquisaTagConectcarVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        
        if (filtro.getId() != null) {
            parametros.add(new ParametroPesquisaIgual("id", filtro.getId()));
        }
        if (filtro.getPlaca() != null) {
            parametros.add(new ParametroPesquisaIgualIgnoreCase("placa", filtro.getPlaca()));
        }
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
        	if(filtro.getStatus().getName().equals(StatusAtivacao.ATIVO.name())) {
        		parametros.add(new ParametroPesquisaNulo("dataAtivacao", true));
        	}else if(filtro.getStatus().getName().equals(StatusAtivacao.INATIVO.name())) {
        		parametros.add(new ParametroPesquisaNulo("dataBloqueio", true));
        	}
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

   
}