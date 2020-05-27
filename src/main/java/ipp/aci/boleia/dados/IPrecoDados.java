package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Preco
 */
public interface IPrecoDados extends IRepositorioBoleiaDados<Preco> {

    /**
     * Busca os precos atuais para um determinado PontoVenda e tipo combustivel
     * @param pontoVendaId O id da PontoVenda
     * @param idTipoCombustivel O id do tipo de combustivel
     * @return O preco atual, caso exista
     */
    List<Preco> buscarPrecosAtuais(Long pontoVendaId, Long idTipoCombustivel);

    /** Busca as negociações realizadas de acordo com o perfil do usuário
     * @param filtro Filtro de pesquisa do preço negociado
     * @param acordo Define se deve obter apenas precos em solicitacao de acordo
     * @param statusPossiveis status permitidos na consulta
     * @return Uma lista de negociações.
     */
    ResultadoPaginado<Preco> pesquisaPrecoPaginada(FiltroPesquisaPrecoVo filtro, Boolean acordo, Integer... statusPossiveis );

    /**
     * Busca o preco atual para um determinado PontoVenda,Frota e tipo combustivel
     * @param idFrota o id da Frota
     * @param idPontoVenda O id da PontoVenda
     * @param idTipoCombustivel O id do tipo de combustivel
     * @return O preco atual, caso exista
     */
    Preco obterAtualPorFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel);

    /**
     * Busca o preco a partir de uma data para um determinado PontoVenda,Frota e tipo combustível
     * @param idFrota o id da Frota
     * @param idPontoVenda O id da PontoVenda
     * @param idTipoCombustivel O id do tipo de combustível
     * @param dataAbastecimento A data do abastecimento
     * @return O preco atual, caso exista
     */
    Preco obterPorDataFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel, Date dataAbastecimento);

    /**
     * Obtem precos em negociacao novos ou pendentes cuja data expirou em relação a solicitacao
     * @param dataCorte minima aceita para aprovar
     * @return Precos novos ou pendentes que estao apos a data
     */
    List<Preco> buscarAcordosNovosOuPendentesParaVigenciaAutomatica(Date dataCorte);
}
