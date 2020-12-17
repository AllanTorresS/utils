package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoParametroPostosPermitidosDados;
import ipp.aci.boleia.dados.IHistoricoParametroUsoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.HistoricoParametroPostosPermitidos;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.enums.TipoRestricaoPostosPermitidos;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Serviços de domínio da entidade {@link HistoricoParametroPostosPermitidos}.
 *
 * @author pedro.silva
 */
@Component
public class HistoricoParametroPostosPermitidosSd {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IHistoricoParametroUsoDados repositorioHistoricoParametroUso;

    @Autowired
    private IHistoricoParametroPostosPermitidosDados repositorio;

    /**
     * Registra no histórico uma autorização de abastecimentos em um determinado posto.
     *
     * @param parametroUsoPostoPermitido Informações de posto autorizado para abastecer pelo parametro de uso.
     * @throws ExcecaoValidacao Exceção lançada caso não seja encontrado um registro de histórico.
     */
    public void registrarAutorizacaoPosto(FrotaParametroSistemaPostoAutorizadoAbastecimento parametroUsoPostoPermitido) throws ExcecaoValidacao {
        TipoRestricaoPostosPermitidos tipoRestricaoPostoPermitido = parametroUsoPostoPermitido.getTipoRestricaoPostoPermitido();
        HistoricoParametroUso historicoParametroUso = repositorioHistoricoParametroUso.obterUltimoHistoricoAtivacao(parametroUsoPostoPermitido.getFrotaParametroSistema());
        if(historicoParametroUso == null) {
            throw new ExcecaoValidacao(Erro.ERRO_HISTORICO_NAO_ENCONTRADO);
        }

        BigDecimal maximoValor = parametroUsoPostoPermitido.getMaximoValor();
        BigDecimal maximoLitros = parametroUsoPostoPermitido.getMaximoLitros();

        HistoricoParametroPostosPermitidos historico = new HistoricoParametroPostosPermitidos();
        historico.setPontoVenda(parametroUsoPostoPermitido.getPontoVenda());
        historico.setDataAutorizacao(utilitarioAmbiente.buscarDataAmbiente());
        historico.setHistoricoParametroUso(historicoParametroUso);
        historico.setTipoRestricao(tipoRestricaoPostoPermitido.getValor());
        historico.setValorMaximoRestricao(tipoRestricaoPostoPermitido.isValor() ? maximoValor : maximoLitros);

        repositorio.armazenar(historico);
    }

    /**
     * Registra no histórico de postos permitidos a desautorização de um posto.
     *
     * @param parametroUsoPostoPermitido Parametro de Postos Permitidos.
     * @throws ExcecaoValidacao Exceção lançada caso não seja encontrado um registro de histórico para atualização.
     */
    public void registrarDesautorizacaoPosto(FrotaParametroSistemaPostoAutorizadoAbastecimento parametroUsoPostoPermitido) throws ExcecaoValidacao {
        HistoricoParametroUso historicoParametroUso = repositorioHistoricoParametroUso.obterUltimoHistoricoAtivacao(parametroUsoPostoPermitido.getFrotaParametroSistema());
        if(historicoParametroUso == null) {
            throw new ExcecaoValidacao(Erro.ERRO_HISTORICO_NAO_ENCONTRADO);
        }

        HistoricoParametroPostosPermitidos historicoParametroPostosPermitidos = repositorio.obterUltimoHistoricoAutorizacao(parametroUsoPostoPermitido, historicoParametroUso);
        if(historicoParametroPostosPermitidos != null) {
            historicoParametroPostosPermitidos.setDataDesautorizacao(utilitarioAmbiente.buscarDataAmbiente());
            repositorio.armazenar(historicoParametroPostosPermitidos);
        } else {
            TipoRestricaoPostosPermitidos tipoRestricaoPostoPermitido = parametroUsoPostoPermitido.getTipoRestricaoPostoPermitido();
            BigDecimal maximoValor = parametroUsoPostoPermitido.getMaximoValor();
            BigDecimal maximoLitros = parametroUsoPostoPermitido.getMaximoLitros();

            HistoricoParametroPostosPermitidos historico = new HistoricoParametroPostosPermitidos();
            historico.setPontoVenda(parametroUsoPostoPermitido.getPontoVenda());
            historico.setDataAutorizacao(utilitarioAmbiente.buscarDataAmbiente());
            historico.setDataDesautorizacao(utilitarioAmbiente.buscarDataAmbiente());
            historico.setHistoricoParametroUso(historicoParametroUso);
            historico.setTipoRestricao(tipoRestricaoPostoPermitido.getValor());
            historico.setValorMaximoRestricao(tipoRestricaoPostoPermitido.isValor() ? maximoValor : maximoLitros);
            repositorio.armazenar(historico);
        }
    }

}
