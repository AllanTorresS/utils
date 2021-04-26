package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.Unidade;

/**
 * Contrato de implementação para parâmetros de nota fiscal com exceções de UF
 */
public interface IParametroNotaFiscalUf {

    Unidade getUnidadeLocalDestino();

    String getUf();
}
