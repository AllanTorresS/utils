package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.enums.StatusPreco;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Encapsula as regras de negocio que envolvem a manipulacao de Precos Negociados
 */
@Component
public class PrecoNegociadoSd {

    @Autowired
    private IPrecoDados repositorioPreco;

    @Autowired
    private HistoricoFrotaPtovPrecoSd historicoFrotaPtovPrecoSd;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    /**
     * Remove o desconto ou o acréscimo do acordo vigente
     * e cria uma nova linha de negociação sem valor, na qual
     * em seguida cria um histórico da negociação antiga.
     *
     * @param precoAtual preco que sera alterado
     * @return Preco novo a partir do base
     */
    public Preco definirPrecoNegociadoApartirDePrecoBase(Preco precoAtual){
        Date dataAtualizacao = ambiente.buscarDataAmbiente();

        Preco novoPreco = new Preco();
        novoPreco.setFrotaPtov(precoAtual.getFrotaPtov());
        novoPreco.setStatus(StatusPreco.VIGENTE.getValue());
        novoPreco.setPreco(precoAtual.getPrecoBase().getPreco());
        novoPreco.setPrecoBase(precoAtual.getPrecoBase());
        novoPreco.setDataAtualizacao(dataAtualizacao);
        novoPreco.setDataVigencia(dataAtualizacao);
        repositorioPreco.armazenar(novoPreco);
        historicoFrotaPtovPrecoSd.armazenar(novoPreco);
        return novoPreco;
    }

    /**
     * Cria um novo acordo zerado vigente
     * e com desconto vigente nulo
     * @param precoAtual preco que sera alterado
     * @return Preco novo a partir do base
     */
    public Preco criarApartirDaExclusaoDePrecoBase(Preco precoAtual){
        Date dataAtualizacao = ambiente.buscarDataAmbiente();

        Preco novoPreco = new Preco();
        novoPreco.setFrotaPtov(precoAtual.getFrotaPtov());
        novoPreco.setStatus(StatusPreco.VIGENTE.getValue());
        novoPreco.setPreco(precoAtual.getPrecoBase().getPreco());
        novoPreco.setPrecoBase(precoAtual.getPrecoBase());
        novoPreco.setDescontoVigente(null);
        novoPreco.setDataAtualizacao(dataAtualizacao);
        novoPreco.setDataVigencia(dataAtualizacao);
        repositorioPreco.armazenar(novoPreco);
        historicoFrotaPtovPrecoSd.armazenar(novoPreco);
        return novoPreco;
    }



    /**
     * Aplica novo preco vigente ou já em aceite, conforme o novo acordo recém aceitado
     * e gera registro de histórico do preço vigente anterior ao aceite.
     *
     * @param novoPreco novo preço a entrar em vigência.
     * @param automatico flag indicando se o aceite foi automatico
     * @return Preco novo
     */
    public Preco aceitarNovoAcordo(Preco novoPreco, boolean automatico) {
        Date dataAtualizacao = ambiente.buscarDataAmbiente();
        return aceitarDesconto(novoPreco, dataAtualizacao, automatico);
    }

    /**
     * Remove um desconto previamente acordado entre frota e PV
     * @param preco O preco
     * @param dataAtualizacao a data de atualizacao do desconto
     * @return Preco cancelado
     */
    public Preco excluirDesconto(Preco preco, Date dataAtualizacao) {
        if(preco.getDataVigencia() != null) {
            preco.setDataAtualizacao(dataAtualizacao);
        }
        preco.setStatus(StatusPreco.CANCELADO.getValue());
        return repositorioPreco.armazenar(preco);
    }

    /**
     * Valida se o desconto é igual a zero
     * @param desconto a ser dado no valor do preço
     * @param possuiDesconto indica se a negociação trata-se de um desconto ou acréscimo.
     * @throws ExcecaoValidacao Caso haja algum erro de validação
     */
    public void validaDesconto(BigDecimal desconto, Boolean possuiDesconto) throws ExcecaoValidacao {
        if(desconto != null){
            int comparacaoDesconto = desconto.compareTo(BigDecimal.ZERO);
            if((possuiDesconto && comparacaoDesconto > 0) || (!possuiDesconto && comparacaoDesconto < 0)){
                throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("precoNegociado.novoAcordo.validacao.desconto.invalido"));
            }
        }
    }

    /**
     * Aprova o desconto solicitado para o acordo
     * @param preco O preço a ter o desconto aprovado
     * @param dataAtualizacao a data de atualizacao
     * @param automatico desconto automatico ou manual
     * @return O preço com o desconto
     */
    private Preco aceitarDesconto(Preco preco, Date dataAtualizacao, boolean automatico) {
        preco.setPreco(preco.getPrecoBase().getPreco().add(preco.getDescontoSolicitado()));
        preco.setDescontoVigente(preco.getDescontoSolicitado());
        preco.setDescontoSolicitado(null);
        preco.setDataAtualizacao(dataAtualizacao);
        if(preco.getDataAgendamento() == null) {
            preco.setDataVigencia(dataAtualizacao);
        }
        preco.setStatus(automatico ? StatusPreco.VIGENTE.getValue() : StatusPreco.ACEITO.getValue());
        preco = repositorioPreco.armazenar(preco);
        historicoFrotaPtovPrecoSd.armazenar(preco);
        return preco;
    }


    /**
     * Reprova o desconto solicitado com dada justificativa
     * @param preco O preço a ter seu desconto rejeitado
     * @param justificativa do revendedor
     * @param dataAtualizacao A data da atualização
     * @return O preço rejeitado
     */
    public Preco rejeitarDesconto(Preco preco, String justificativa, Date dataAtualizacao) {
        preco.setStatus(StatusPreco.REJEITADO.getValue());
        preco.setJustificativa(justificativa);
        if(preco.getDataVigencia() != null) {
            preco.setDataAtualizacao(dataAtualizacao);
        }
        return repositorioPreco.armazenar(preco);
    }
}
