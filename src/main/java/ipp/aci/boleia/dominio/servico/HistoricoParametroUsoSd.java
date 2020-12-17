package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoParametroPostosPermitidosDados;
import ipp.aci.boleia.dados.IHistoricoParametroUsoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.HistoricoParametroPostosPermitidos;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.enums.TipoRestricaoPostosPermitidos;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

import static ipp.aci.boleia.dominio.enums.ParametroSistema.POSTOS_AUTORIZADOS_ABASTECIMENTO;
import static ipp.aci.boleia.util.excecao.Erro.ERRO_HISTORICO_NAO_ENCONTRADO;

/**
 * Serviços de domínio da entidade {@link HistoricoParametroUso}.
 *
 * @author pedro.silva
 */
@Component
public class HistoricoParametroUsoSd {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IHistoricoParametroUsoDados repositorio;

    @Autowired
    private IHistoricoParametroPostosPermitidosDados repositorioHistoricoPostosPermitidos;

    @Autowired
    private Mensagens mensagens;

    /**
     * Verifica no histórico se uma frota tinha permissão para abastecimento
     * em um ponto de venda em uma determinada data.
     *
     * @param autorizacaoPagamento Autorização de pagamento usada na validação.
     * @param data Data a ser consultada no histórico.
     * @throws ExcecaoValidacao Caso algum erro de validação aconteça.
     */
    public void validarHistoricoPostoPermitido(AutorizacaoPagamento autorizacaoPagamento, Date data) throws ExcecaoValidacao {
        HistoricoParametroUso historicoParametroUso = repositorio.obterHistorico(autorizacaoPagamento.getFrota(), POSTOS_AUTORIZADOS_ABASTECIMENTO, data);
        if(historicoParametroUso != null && historicoParametroUso.getRestritivo()){
            HistoricoParametroPostosPermitidos historicoParametroPostosPermitidos = repositorioHistoricoPostosPermitidos.obterHistorico(autorizacaoPagamento.getPontoVenda(), historicoParametroUso, data);
            if(historicoParametroPostosPermitidos == null) {
                throw new ExcecaoValidacao(Erro.ERRO_EDICAO_POSTO_NAO_PERMITIDO, mensagens.obterMensagem("autorizacaopagamento.alteracao.pontovenda.naopermitido"));
            } else if(violouLimiteAbastecimento(autorizacaoPagamento, historicoParametroPostosPermitidos)) {
                throw new ExcecaoValidacao(Erro.ERRO_EDICAO_POSTO_NAO_PERMITIDO, mensagens.obterMensagem("autorizacaopagamento.alteracao.pontovenda.limitepermitidoexcedido"));
            }
        }
    }

    /**
     * Registra no histórico de parametros de uso
     * a ativação de um parametro.
     *
     * @param parametroUso Parametro que está sendo ativado.
     */
    public void registrarAtivacaoParametro(FrotaParametroSistema parametroUso) {
        HistoricoParametroUso historico = new HistoricoParametroUso();
        historico.setFrota(parametroUso.getFrota());
        historico.setParametroSistema(parametroUso.getParametroSistema());
        historico.setRestritivo(parametroUso.getRestritivo());
        historico.setDataAtivacao(utilitarioAmbiente.buscarDataAmbiente());

        repositorio.armazenar(historico);
    }

    /**
     * Registra no histórico de parametros de uso
     * a inativação de um parametro.
     *
     * @param parametroUso Parametro que está sendo inativado.
     * @throws ExcecaoValidacao Exceção lançada caso não seja encontrado um registro de histórico para atualização.
     */
    public void registrarInativacaoParametro(FrotaParametroSistema parametroUso) throws ExcecaoValidacao {
        HistoricoParametroUso historicoAtivacao = repositorio.obterUltimoHistoricoAtivacao(parametroUso);
        if(historicoAtivacao != null) {
            historicoAtivacao.setDataInativacao(utilitarioAmbiente.buscarDataAmbiente());
            repositorio.armazenar(historicoAtivacao);
        } else {
            HistoricoParametroUso historico = new HistoricoParametroUso();
            historico.setFrota(parametroUso.getFrota());
            historico.setParametroSistema(parametroUso.getParametroSistema());
            historico.setRestritivo(parametroUso.getRestritivo());
            historico.setDataAtivacao(utilitarioAmbiente.buscarDataAmbiente());
            historico.setDataInativacao(utilitarioAmbiente.buscarDataAmbiente());
            repositorio.armazenar(historico);
        }
    }

    /**
     * Informa se um abastecimento viola o limite permitido de valor total ou litragem
     * em um histórico de Postos Permitidos.
     *
     * @param autorizacaoPagamento Abastecimento verificado.
     * @param historicoParametroPostosPermitidos Histórico de permissão de abastecimento do posto.
     *
     * @return True, caso tenha ocorrido alguma violação.
     */
    public boolean violouLimiteAbastecimento(AutorizacaoPagamento autorizacaoPagamento, HistoricoParametroPostosPermitidos historicoParametroPostosPermitidos) {
        TipoRestricaoPostosPermitidos tipoRestricao = TipoRestricaoPostosPermitidos.obterPorValor(historicoParametroPostosPermitidos.getTipoRestricao());
        BigDecimal valorMaximoRestricao = historicoParametroPostosPermitidos.getValorMaximoRestricao();
        if(tipoRestricao.isValor()) {
            return valorMaximoRestricao != null && autorizacaoPagamento.getValorTotalAbastecimento() != null &&
                    valorMaximoRestricao.compareTo(BigDecimal.ZERO) > 0 &&
                    autorizacaoPagamento.getValorTotalAbastecimento().compareTo(valorMaximoRestricao) > 0;
        }
        return valorMaximoRestricao != null && autorizacaoPagamento.getTotalLitrosAbastecimento() != null &&
                valorMaximoRestricao.compareTo(BigDecimal.ZERO) > 0 &&
                autorizacaoPagamento.getTotalLitrosAbastecimento().compareTo(valorMaximoRestricao) > 0;
    }
}
