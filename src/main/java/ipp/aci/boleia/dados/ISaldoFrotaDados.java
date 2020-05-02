package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.SaldoFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaSaldoFrotaVo;

/**
 * Contrato para implementacao de repositorios de entidades SaldoFrota
 */
public interface ISaldoFrotaDados extends IRepositorioBoleiaDados<SaldoFrota> {

    /**
     * Obtem o saldo de determinada frota
     *
     * @param idFrota o identificador da frota desejada
     * @return o saldo da frota
     */
    SaldoFrota obterSaldoPorIdFrota(Long idFrota);

    /**
     * Pesquisa saldos de frotas a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista contendo as entidades localizadas
     */
    ResultadoPaginado<SaldoFrota> pesquisar(FiltroPesquisaSaldoFrotaVo filtro);
}
