package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.Unidade;

/**
 * Interface comum entre o parametro nota fiscal uf e seu histórico
 */
public interface IParametroNotaFiscalUf {

    Unidade getUnidadeLocalDestino();

    String getUf();
}
