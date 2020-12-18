package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ParametroNotaFiscal;

/**
 * Contrato para implementacao de repositorios de entidades ParametroNotaFiscal
 */
public interface IParametroNotaFiscalDados extends IRepositorioBoleiaDados<ParametroNotaFiscal> {
    ParametroNotaFiscal obterPorFrota(Long frotaId);
}
