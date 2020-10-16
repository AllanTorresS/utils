package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFrete;

/**
 * Contrato para implementacao de acesso aos dados de AgenciadorFrete
 */
public interface IAgenciadorFreteDados extends IRepositorioBoleiaDados<AgenciadorFrete> {

    /**
     * Obtém o agenciador de frete pelo sistema externo associado
     * @param sistemaExterno O sistema externo utilizado na busca
     * @return O agente de frete associado
     */
    AgenciadorFrete obterPorSistemaExterno(SistemaExterno sistemaExterno);
}
