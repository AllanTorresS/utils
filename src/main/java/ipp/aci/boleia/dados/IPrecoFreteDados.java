package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;

/**
 * Contrato para implementacao de repositorios de entidades PrecoFrete
 */
public interface IPrecoFreteDados extends IRepositorioBoleiaDados<PrecoFrete> {

    PrecoFrete obterPrecoFreteVigente(PontoDeVenda pontoDeVenda, TipoCombustivel combustivel);
}
