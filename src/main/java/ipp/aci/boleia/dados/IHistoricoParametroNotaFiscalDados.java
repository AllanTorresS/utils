package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.ParametroNotaFiscal;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscal;

import java.util.Date;

/**
 * Contrato para implementação de repositórios da entidade {@link HistoricoParametroNotaFiscal}.
 */
public interface IHistoricoParametroNotaFiscalDados extends IRepositorioBoleiaDados<HistoricoParametroNotaFiscal> {

    /**
     * Busca o último parâmetro salvo no histórico a partir de uma data limite
     * @param parametroNotaFiscal O parâmetro a ter seu histórico consultado
     * @param dataLimite A data de corte para a busca
     * @return O histórico buscado
     */
    HistoricoParametroNotaFiscal buscarUltimoParametroPorData(ParametroNotaFiscal parametroNotaFiscal, Date dataLimite);
    
}
