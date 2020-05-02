package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.NfeArmazem;

import java.util.Date;

/**
 * Contrato para implementação de repositório do armazém de notas fiscais
 */
public interface INfeArmazemDados extends IRepositorioBoleiaDados<NfeArmazem> {

    /**
     * Obtém a data de leitura de lote de e-mails mais recente
     * @return A última data de leitura
     */
    Date obterUltimaDataLeitura();

}
