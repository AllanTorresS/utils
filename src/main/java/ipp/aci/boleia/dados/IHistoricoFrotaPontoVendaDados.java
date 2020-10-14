package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoFrotaPontoVenda;
import java.util.Date;

/**
 * Contrato para implementacao de repositorios da entidade {@link HistoricoFrotaPontoVenda}.
 * 
 * @author Rodrigo Salvatore
 */
public interface IHistoricoFrotaPontoVendaDados extends IRepositorioBoleiaDados<HistoricoFrotaPontoVenda> {
    
    /**
     * Obtem o historico da relacao de frota com ponto de venda para uma data especifica.
     *
     * @param cdFrotaPontoDeVenda Identificador do {@link FrotaPontoVenda} atrelado ao historico.
     * @param data Data a ser verificada.
     * @return O historico encontrado.
     */
    HistoricoFrotaPontoVenda obterHistoricoFrotaPontoVendaPorData(Long cdFrotaPontoDeVenda, Date data);
    
}
