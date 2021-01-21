package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusPreco;
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
    ResultadoPaginado<Preco> pesquisaPrecoPaginadaValidacaoSegregacao(FiltroPesquisaPrecoVo filtro, Boolean acordo, Usuario usuarioLogado, Integer... statusPossiveis );

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
     * Busca os preços negociados cuja data expirou em relação a solicitação de alteração
     * @param dataCorte Data minima aceita para aprovação
     * @return Preços em negociação que estão após a data
     */
    List<Preco> obterParaVigenciaAutomatica(Date dataCorte);

    /**
     * Busca os preços negociados agendados cuja data de vigência é anterior à atual
     * @return Preços em negociação que devem entrar em vigência
     */
    List<Preco> obterAgendamentosParaVigenciaAutomatica();

    /**
     * Busca o preco pendente ou novo para um determinado PontoVenda, Frota e tipo combustivel
     * @param frota a Frota a ser filtrada
     * @param posto O Ponto de Venda a ser filtrado
     * @param tipoCombustivel O id do tipo de combustivel
     * @param status Lista de status a serem filtrados
     * @return O preco pendente ou novo, caso exista
     */
    List<Preco> obterPrecos(Frota frota, PontoDeVenda posto, TipoCombustivel tipoCombustivel, List<StatusPreco> status);

    /**
     * Busca o preco agendado a partir de uma data de vigência para um determinado PontoVenda,Frota e tipo combustível
     * @param idFrota O id da Frota a ser filtrada
     * @param idPosto O id do Ponto de Venda a ser filtrado
     * @param idTipoCombustivel O id do tipo de combustivel
     * @param dataVigencia A data de vigência
     * @return O preco agendado
     */
    Preco obterAgendamentoPorFrotaPvCombustivelDataVigencia(Long idFrota, Long idPosto, Long idTipoCombustivel, Date dataVigencia);
}
