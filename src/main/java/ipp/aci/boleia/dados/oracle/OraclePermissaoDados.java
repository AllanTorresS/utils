package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPermissaoDados;
import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Permissao
 */
@Repository
public class OraclePermissaoDados extends OracleRepositorioBoleiaDados<Permissao> implements IPermissaoDados {

    /**
     * Instancia o repositorio
     */
    public OraclePermissaoDados() {
        super(Permissao.class);
    }

    @Override
    public List<Permissao> obterPermissoesPortais(Boolean portalFrotista, Boolean portalRevendedor, Boolean portalSolucao, Boolean portalPrecos, Boolean sistemaExterno) {

        List<ParametroPesquisa> params = new ArrayList<>();

        if(Boolean.TRUE.equals(portalFrotista)) {
            params.add(new ParametroPesquisaIgual("portalFrotista", portalFrotista));
        }

        if(Boolean.TRUE.equals(portalRevendedor)) {
            params.add(new ParametroPesquisaIgual("portalRevendedor", portalRevendedor));
        }

        if(Boolean.TRUE.equals(portalSolucao)) {
            params.add(new ParametroPesquisaIgual("portalSolucao", portalSolucao));
        }

        if(Boolean.TRUE.equals(portalPrecos)) {
            params.add(new ParametroPesquisaIgual("portalPrecos", portalPrecos));
        }

        if(Boolean.TRUE.equals(sistemaExterno)) {
            params.add(new ParametroPesquisaIgual("sistemaExterno", sistemaExterno));
        }

        return pesquisar(new ParametroOrdenacaoColuna("nome"), params.toArray(new ParametroPesquisa[params.size()]));
    }
    
    @Override
    public List<Permissao> obterPermissoesFrota() {
    	return pesquisar(new ParametroOrdenacaoColuna("nome"), new ParametroPesquisaIgual("permissaoFrota", Boolean.TRUE));
    }

}