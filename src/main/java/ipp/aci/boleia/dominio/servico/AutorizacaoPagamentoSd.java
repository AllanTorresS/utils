package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMinutosData;

/**
 * Implementa as regras de negocio relacionadas a entidade TransacaoConsolidada
 */
@Component
public class AutorizacaoPagamentoSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

    /**
     * Obtem lista de abastecimentos para exportação de acordo com o filtro informado.
     *
     * @param filtro O filtro da ultima busca
     * @return Uma Resultado Paginado de AutorizacaoPagamento
     */
    public ResultadoPaginado<AutorizacaoPagamento> pesquisarAbastecimentosParaExportacao(FiltroPesquisaAbastecimentoVo filtro) {
        filtro.getPaginacao().setTamanhoPagina(null);
        filtro.getPaginacao().setParametrosOrdenacaoColuna(Arrays.asList(new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.DECRESCENTE)));
        return repositorioAutorizacaoPagamento.pesquisaPaginada(filtro);
    }

    /**
     * Realiza as validações necessárias para a autorização de um abastecimento pendente.
     *
     * @param autorizacao Autorização de pagamento do abastecimento.
     * @throws ExcecaoValidacao em caso de erros na validação da autorização de abastecimento.
     */
    public void validarAutorizacaoAbastecimentoPendente(AutorizacaoPagamento autorizacao) throws ExcecaoValidacao {
        if(isPrazoAutorizacaoPendenteExpirado(autorizacao)) {
            throw new ExcecaoValidacao(Erro.PRAZO_AUTORIZACAO_ABASTECIMENTO_PENDENTE_EXPIRADO);
        } else if(!autorizacao.isPendenteAutorizacao()) {
            throw new ExcecaoValidacao(Erro.AUTORIZACAO_ABASTECIMENTO_PENDENTE_NAO_PERMITIDA);
        }
    }

    /**
     * Validar se o horário de uma requisicao de abastecimento de frotas leves está dentro do tempo de tolerância
     * @param dataRequisicao A data da requisicao
     * @return true caso esteja na tolerância
     */
    public Boolean validarHorarioAbastecimentoFrotaLeve(Date dataRequisicao){
        final Long TEMPO_MAX_REQUISICAO = 5L;

        return UtilitarioCalculoData.diferencaEmMinutos(dataRequisicao,ambiente.buscarDataAmbiente()) < TEMPO_MAX_REQUISICAO;
    }

    /**
     * Verifica se o prazo de autorização para um abastecimento pendente já expirou.
     * @param autorizacaoPagamento a autorizacao pagamento que tera o prazo checado
     * @return true, caso tenha expirado.
     */
    private boolean isPrazoAutorizacaoPendenteExpirado(AutorizacaoPagamento autorizacaoPagamento) {
        Date dataExpiracao = adicionarMinutosData(autorizacaoPagamento.getDataRequisicao(), 20);
        return ambiente.buscarDataAmbiente().after(dataExpiracao);
    }
}
