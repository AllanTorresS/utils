package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.HistoricoBloqueioFrotaPontoVenda;

import java.util.Date;

/**
 * Repositório de dados da entidade {@link HistoricoBloqueioFrotaPontoVenda}.
 */
public interface IHistoricoBloqueioFrotaPontoVendaDados extends IRepositorioBoleiaDados<HistoricoBloqueioFrotaPontoVenda> {

    /**
     * Retornar o último histórico de bloqueio de um {@link FrotaPontoVenda}.
     *
     * @param frotaPontoVenda Entidade verificada na consulta.
     * @return Histórico encontrado.
     */
    HistoricoBloqueioFrotaPontoVenda obterUltimoHistoricoBloqueio(FrotaPontoVenda frotaPontoVenda);

    /**
     * Obtém um histórico de bloqueio para uma data específica.
     *
     * @param frotaPtovId Identificador da {@link FrotaPontoVenda} atrelado ao histórico.
     * @param data Data a ser verificada.
     * @return O histórico encontrado.
     */
    HistoricoBloqueioFrotaPontoVenda obterBloqueioPorData(Long frotaPtovId, Date data);
}
