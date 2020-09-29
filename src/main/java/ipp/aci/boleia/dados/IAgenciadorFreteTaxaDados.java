package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteTaxa;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;

import java.util.Date;

/**
 * Interface responsável pelo acesso aos dados da Taxa do Agenciador de frete na base
 */
public interface IAgenciadorFreteTaxaDados extends IRepositorioBoleiaDados<AgenciadorFreteTaxa> {

    /**
     * Obtém o agenciador de frete taxa pelo id do agenciador de frete
     * @param idAgenciador O agenciador frete utilizado na busca
     * @param dataVigencia a data de Vigencia
     * @param paginacao a informacao da paginação
     * @return O agente de frete taxa associado
     */
    ResultadoPaginado<AgenciadorFreteTaxa> obterTaxaPorAgenciadorFrete(Long idAgenciador, Date dataVigencia, InformacaoPaginacao paginacao);
}
