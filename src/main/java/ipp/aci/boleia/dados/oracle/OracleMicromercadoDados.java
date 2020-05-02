package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMicromercadoDados;
import ipp.aci.boleia.dominio.Micromercado;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Respositorio de entidades Micromercado
 */
@Repository
public class OracleMicromercadoDados extends OracleRepositorioBoleiaDados<Micromercado> implements IMicromercadoDados {

    /**
     * Instancia o repositorio
     */
    public OracleMicromercadoDados() {
        super(Micromercado.class);
    }

    @Override
    public List<Micromercado> pesquisar(String chave) {
        return pesquisar(new ParametroOrdenacaoColuna("chave"), new ParametroPesquisaLike("chave", chave.replaceAll("[-./]+", "")));
    }

	@Override
	public Micromercado obterPorChave(String chave) {
        if(StringUtils.isNotBlank(chave)) {
            return pesquisarUnico(new ParametroPesquisaIgual("chave", chave));
        }
        return null;
	}
}
