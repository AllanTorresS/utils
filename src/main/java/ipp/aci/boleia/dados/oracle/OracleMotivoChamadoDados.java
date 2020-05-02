package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotivoChamadoDados;
import ipp.aci.boleia.dominio.MotivoChamado;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades de
 * motivos de chamado para o Salesforce
 */
@Repository
public class OracleMotivoChamadoDados extends OracleRepositorioBoleiaDados<MotivoChamado> implements IMotivoChamadoDados {

    private static final String OUTROS = "OUTROS";

    /**
     * Instancia o repositorio
     */
    public OracleMotivoChamadoDados() {
        super(MotivoChamado.class);
    }

    @Override
    public List<MotivoChamado> obterTodosOrdenadosPelaDescricao() {
        List<MotivoChamado> motivoChamado = super.obterTodos(null);
        motivoChamado.sort((m1, m2) -> {
            if(OUTROS.equalsIgnoreCase(m1.getDescricao())) {
                return 1;
            }
            return StringUtils.compareIgnoreCase(m1.getDescricao(), m2.getDescricao());
        });
        return motivoChamado;
    }
}
