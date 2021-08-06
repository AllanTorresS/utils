package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoMotorista;
import ipp.aci.boleia.dominio.Motorista;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios da entidade {@link HistoricoMotorista}.
 * 
 * @author Allan Torres
 */
public interface IHistoricoMotoristaDados extends IRepositorioBoleiaDados<HistoricoMotorista> {
    
    /**
     * Obtem o historico de alterações de um derterminado motorista através da data.
     *
     * @param cdMotorista Identificador do {@link ipp.aci.boleia.dominio.Motorista} atrelado ao historico.
     * @param data Data a ser verificada.
     * @return O historico encontrado.
     */
    HistoricoMotorista obterHistoricoMotoristaPorData(Long cdMotorista, Date data);

    /**
     * Obtem a lista de historicos de alterações de um derterminado motorista.
     *
     * @param motorista Entidade do {@link ipp.aci.boleia.dominio.Motorista} atrelado ao historico.
     * @return A lista de historicos encontrados.
     */
    List<HistoricoMotorista> obterTodosPorMotorista(Motorista motorista);
    
}
