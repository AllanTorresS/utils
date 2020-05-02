package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCicloRepasseVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato de implementação de repositorios de entidades CicloRepasse
 */
public interface ICicloRepasseDados extends IRepositorioBoleiaDados<CicloRepasse> {

    /**
     * Obtem o ciclo de repasse cujo período de duração contém a data especificada
     *
     * @param data A data de referência
     * @param idEntidadeRepasse O identificador da entidade de repasse
     * @param idPtov O identificador do ponto de venda
     * @return O ciclo de repasse correspondente à data e à entidade de repasse
     */
     CicloRepasse obterCicloRepassePorDataEEntidade(Date data, Long idEntidadeRepasse, Long idPtov);

    /**
     * Obtem, para uma entidade de repasse,  o último ciclo de repasse anterior à autorizacao de pagamento informada
     *
     * @param data A data de
     * @param idEntidadeRepasse O identificador da entidade de repasse
     * @param idPtov O identificador do ponto de venda
     * @return O último ciclo de repasse anterior à data e à entidade de repasse
     */
     CicloRepasse obterUltimoCicloRepasseAnteriorAData(Date data, Long idEntidadeRepasse, Long idPtov);

    /**
     * Obtem os ciclos de repasse fechados pendentes de envio ao JDE
     * @return a lista de ciclos de repasse
     */
    List<CicloRepasse> obterCiclosRepasseFechadosNaoEnviados();

    /**
     * Pesquisar as cobranças candidatas a realizar consulta no JDE
     * para consultar o status do aviso do débito
     * @return Os ciclos candidatos a consulta de aviso de débito
     */
    List<CicloRepasse> buscarCiclosRepasseParaConsultarAvisoDebito();

    /**
     * Obtem os ciclos de Repasses para o relatorio de repasse Ipiranga a partir do filtro informado
     * @param filtro O filtro da busca
     * @return Uma lista de entidades de Ciclo de Repasse
     */
     List<CicloRepasse> pesquisarParaRelatorioRepasse(FiltroPesquisaCicloRepasseVo filtro);

    /**
     * Obtém um {@link CicloRepasse} a partir de uma {@link AutorizacaoPagamento}.
     *
     * @param autorizacaoPagamento Autorização de pagamento utilizada na pesquisa.
     * @return o {@link CicloRepasse} encontrado.
     */
     CicloRepasse obterCicloRepassePorAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento);
}
