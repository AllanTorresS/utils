package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ParametroNotaFiscal;

/**
 * Contrato para implementacao de repositorios de entidades ParametroNotaFiscal
 */
public interface IParametroNotaFiscalDados extends IRepositorioBoleiaDados<ParametroNotaFiscal> {
    /**
     * Obter parâmetro de nota fiscal pela frota
     * @param frotaId Identificador da frota
     * @return O parametro buscado
     */
    ParametroNotaFiscal obterPorFrota(Long frotaId);
}
