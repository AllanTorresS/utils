package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoFrotaPontoVenda;
import ipp.aci.boleia.dominio.HistoricoMotorista;

import java.util.Date;

/**
 * Contrato para implementacao de repositorios da entidade {@link HistoricoMotorista}.
 * 
 * @author Allan Torres
 */
public interface IHistoricoMotoristaDados extends IRepositorioBoleiaDados<HistoricoMotorista> {
    
    /**
     * Obtem o historico da relacao de frota com ponto de venda para uma data especifica.
     *
     * @param cdMotorista Identificador do {@link ipp.aci.boleia.dominio.Motorista} atrelado ao historico.
     * @param data Data a ser verificada.
     * @return O historico encontrado.
     */
    HistoricoMotorista obterHistoricoMotoristaPorData(Long cdMotorista, Date data);
    
}
