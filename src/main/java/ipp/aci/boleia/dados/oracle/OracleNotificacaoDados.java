package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.INotificacaoDados;
import ipp.aci.boleia.dominio.Notificacao;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades Notificacao
 */
@Repository
public class OracleNotificacaoDados extends OracleRepositorioBoleiaDados<Notificacao> implements INotificacaoDados {
    /**
     * Instancia o repositorio
     */
    public OracleNotificacaoDados() {
        super(Notificacao.class);
    }
}
