package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoPontoVenda;
import java.util.Date;

/**
 * Contrato para implementacao de repositorios da entidade {@link HistoricoPontoVenda}.
 * 
 * @author Rodrigo Salvatore
 */
public interface IHistoricoPontoVendaDados extends IRepositorioBoleiaDados<HistoricoPontoVenda> {
    
    /**
     * Obtém o histórico do ponto de venda para uma data específica.
     *
     * @param cdPontoDeVenda Identificador do {@link PontoDeVenda} atrelado ao histórico.
     * @param data Data a ser verificada.
     * @return O histórico encontrado.
     */
    HistoricoPontoVenda obterHistoricoPontoVendaPorData(Long cdPontoDeVenda, Date data);
    
}
