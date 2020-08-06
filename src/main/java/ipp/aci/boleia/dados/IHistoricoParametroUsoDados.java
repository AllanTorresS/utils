package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.enums.ParametroSistema;

import java.util.Date;

/**
 * Repositório de dados da classe {@link HistoricoParametroUso}.
 *
 * @author pedro.silva
 */
public interface IHistoricoParametroUsoDados extends IRepositorioBoleiaDados<HistoricoParametroUso> {

    /**
     * Retornar o último histórico de um parametro de uso.
     *
     * @param parametroUso Parametro de uso utilizado na consulta.
     * @return o histórico encontrado.
     */
    HistoricoParametroUso obterUltimoHistoricoAtivacao(FrotaParametroSistema parametroUso);

    /**
     * Busca um histórico de ativação de um parametro de uso do sistema.
     *
     * @param frota Frota dona do parametro de uso.
     * @param parametroUso Parametro de uso buscado no histórico.
     * @param data Data utilizada na busca.
     * @return Registro histórico encontrado.
     */
    HistoricoParametroUso obterHistorico(Frota frota, ParametroSistema parametroUso, Date data);
}
