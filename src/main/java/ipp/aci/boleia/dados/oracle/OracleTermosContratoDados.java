package ipp.aci.boleia.dados.oracle;

import com.amazonaws.util.CollectionUtils;
import ipp.aci.boleia.dados.ITermosContratoDados;
import ipp.aci.boleia.dominio.TermosContrato;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Respositorio de entidades TermosContrato
 */
@Repository
public class OracleTermosContratoDados extends OracleRepositorioBoleiaDados<TermosContrato> implements ITermosContratoDados {

    /**
     * Instancia o repositorio
     */
    public OracleTermosContratoDados() {
        super(TermosContrato.class);
    }
    
    @Override
    public TermosContrato obterVersaoAtual() {
    	List<TermosContrato> result = obterTodos(new ParametroOrdenacaoColuna("versao", Ordenacao.DECRESCENTE));
    	return CollectionUtils.isNullOrEmpty(result) ? null : result.get(0);
    }
}
