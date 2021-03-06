package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IPrecoBaseDados;
import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.PrecoMicromercado;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.enums.StatusPreco;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Encapsula as regras de negocio que envolvem a manipulacao de PrecosBase
 */
@Component
public class PrecoSd {

    @Autowired
    private PrecoNegociadoSd precoNegociadoSd;

    @Autowired
    private HistoricoFrotaPtovPrecoSd historicoFrotaPtovPrecoSd;

    @Autowired
    private HistoricoPrecoBaseSd historicoPrecoBaseSd;

    @Autowired
    private IPrecoBaseDados repositorioPrecoBase;

    @Autowired
    private IPrecoDados repositorioPreco;

    @Autowired
    private IFrotaPontoVendaDados frotaPtovDados;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    /**
     * Cria novo Preço Base, com base no Preço Micromercado, para o ponto de venda, caso não exista nenhum Preço Base Vigente.
     *
     * @param precoMicromercado O preco a ser propagado para a revenda
     * @param pontoVenda A revenda
     * @return O preco base atualizado
     */
    public PrecoBase criaPrecoBaseComBaseNoPrecoMicromercado(PrecoMicromercado precoMicromercado, PontoDeVenda pontoVenda) {
        List<PrecoBase> precoBases = repositorioPrecoBase.buscarPrecosAtuais(pontoVenda.getId(), precoMicromercado.getTipoCombustivel().getId());
        Optional<PrecoBase> precoBaseVigente = precoBases.stream()
                .filter(p -> p.getStatus().equals(StatusAlteracaoPrecoPosto.VIGENTE.getValue())
                        || p.getStatus().equals(StatusAlteracaoPrecoPosto.ACEITO.getValue())).findFirst();
        if (precoBaseVigente.isPresent()) {
            PrecoBase precoBase = precoBaseVigente.get();
            precoBase.entrarEmVigencia(ambiente.buscarDataAmbiente(), false);
            return repositorioPrecoBase.armazenarSemIsolamentoDeDados(precoBase);
        }
        precoBases.forEach(p -> {
                    p.sairDeVigencia(ambiente.buscarDataAmbiente());
                    repositorioPrecoBase.armazenarSemIsolamentoDeDados(p);
                });

        PrecoBase novoPreco = new PrecoBase();
        novoPreco.setPontoVenda(pontoVenda);
        novoPreco.setPrecoMicromercado(precoMicromercado);
        novoPreco.setStatus(StatusAlteracaoPrecoPosto.VIGENTE.getValue());
        novoPreco.setPreco(precoMicromercado.getPreco());
        novoPreco.setDataAtualizacao(precoMicromercado.getDataAtualizacao());
        novoPreco = repositorioPrecoBase.armazenarSemIsolamentoDeDados(novoPreco);
        historicoPrecoBaseSd.armazenar(novoPreco);
        return novoPreco;
    }

    /**
     * Propaga as atualizações de preço base para preços praticados pelo PV em relacao as frotas que com ele negociam
     *
     * @param precoBase Preco base a propagar
     * @return Lista de frotas onde o preço foi propagado
     */
    public Set<Long> propagarAtualizacaoPrecoBase(PrecoBase precoBase) {
        return propagarAtualizacaoPrecoBase(precoBase,false);
    }

    /**
     * Propaga a exclusão do Preço Base
     * @param precoBase Preco base a propagar
     */
    public void propagarExclusao(PrecoBase precoBase){
        List<Preco> precoListaAtual = repositorioPreco.buscarPrecosAtuais(precoBase.getIdPontoVenda(), precoBase.getPrecoMicromercado().getTipoCombustivel().getId());
        precoListaAtual.forEach(precos->precoNegociadoSd.criarApartirDaExclusaoDePrecoBase(precos));
    }


    /**
     * Propaga as atualizações de preço base para preços patricados pelo PV em relacao as frotas que com ele negociam
     *
     * @param precoBase Preco base a propagar
     * @param somenteNovos Propaga os preços somente para frotas que não contêm o preço base relacionado
     * @return Lista de frotas onde o preço foi propagado
     */
    public Set<Long> propagarAtualizacaoPrecoBase(PrecoBase precoBase, Boolean somenteNovos) {
        List<Preco> precos = repositorioPreco.buscarPrecosAtuais(precoBase.getPontoVenda().getId(), precoBase.getPrecoMicromercado().getTipoCombustivel().getId());
        Set<Long> idFrotaPtov = new HashSet<>();
        Set<Long> idFrotas = new HashSet<>();
        for(Preco preco:precos) {
            FrotaPontoVenda frotaPtov = preco.getFrotaPtov();
            if(!somenteNovos){
                BigDecimal descontoVigente = preco.getDescontoSolicitado() != null ? preco.getDescontoSolicitado() : preco.getDescontoVigente();

                Preco novoPreco = new Preco();
                novoPreco.setStatus(StatusPreco.VIGENTE.getValue());
                novoPreco.setFrotaPtov(frotaPtov);
                novoPreco.setDescontoVigente(descontoVigente);
                novoPreco.setPreco(descontoVigente != null ? precoBase.getPreco().add(descontoVigente) : precoBase.getPreco());
                novoPreco.setPrecoBase(precoBase);
                novoPreco.setDataVigencia(precoBase.getDataAtualizacao());
                novoPreco.setDataAtualizacao(precoBase.getDataAtualizacao());
                repositorioPreco.armazenarSemIsolamentoDeDados(novoPreco);
                historicoFrotaPtovPrecoSd.armazenar(novoPreco);
            }
            idFrotaPtov.add(preco.getFrotaPtov().getId());
            idFrotas.add(preco.getFrota().getId());
        }
        List<FrotaPontoVenda> frotasPontoVenda = frotaPtovDados.buscarPorPontoVenda(precoBase.getPontoVenda().getId());
        List<FrotaPontoVenda> frotasPontoVendaFiltradas = new ArrayList<>();
        for(FrotaPontoVenda frotaPtov:frotasPontoVenda) {
            if (!idFrotaPtov.contains(frotaPtov.getId())) {
                frotasPontoVendaFiltradas.add(frotaPtov);
            }
        }
        Set<Long> frotasAfetadas = propagarAtualizacaoPrecoBase(precoBase, frotasPontoVendaFiltradas);
        idFrotas.addAll(frotasAfetadas);
        return idFrotas;
    }

    /**
     * Propaga as atualizações de preço base para preços patricados entre Frota e PV
     * @param precoBase Preco base a propagar
     * @param frotasPontoVenda frotas onde o preco deve ser propagado
     * @return Lista de frotas onde o preço foi propagado
     */
    public Set<Long> propagarAtualizacaoPrecoBase(PrecoBase precoBase, List<FrotaPontoVenda> frotasPontoVenda) {
        Set<Long> idFrotas = new HashSet<>();
        for(FrotaPontoVenda frotaPtov:frotasPontoVenda) {
            Preco preco = new Preco();
            preco.setFrotaPtov(frotaPtov);
            preco.setStatus(StatusPreco.VIGENTE.getValue());
            preco.setPrecoBase(precoBase);
            preco.setPreco(precoBase.getPreco());
            preco.setDataAtualizacao(precoBase.getDataAtualizacao());
            preco.setDataVigencia(precoBase.getDataAtualizacao());
            repositorioPreco.armazenarSemIsolamentoDeDados(preco);
            historicoFrotaPtovPrecoSd.armazenar(preco);
            idFrotas.add(frotaPtov.getFrota().getId());
        }
        return idFrotas;
    }

    /**
     * Armazena histórico do preço vigente, aceita e propaga o novo preco
     *
     * @param preco o preco a ser aceito
     * @return lista dos ids das frotas afetadas pela alteração dos precos negociados alterados em decorrencia do novo preco posto
     */
    public Set<Long> aceitarAlteracaoPrecoBase(PrecoBase preco) {
        if(preco.getPreco() != null){
            PrecoBase historico = new PrecoBase();
            historico.setStatus(StatusAlteracaoPrecoPosto.EXPIRADO.getValue());
            historico.setDataSolicitacao(preco.getDataSolicitacao());
            historico.setPreco(preco.getPreco());
            historico.setPrecoNegociado(preco.getPrecoNegociado());
            historico.setJustificativa(preco.getJustificativa());
            historico.setDataAtualizacao(preco.getDataAtualizacao());
            historico.setPrecoAnterior(preco.getPrecoAnterior());
            historico.setPontoVenda(preco.getPontoVenda());
            historico.setPrecoMicromercado(preco.getPrecoMicromercado());
            historico.setIdPontoVenda(preco.getIdPontoVenda());
            historico.setPontoVenda(preco.getPontoVenda());
            historico.setStatusConvertido(preco.getStatusConvertido());
            historico.setDataFimVigencia(ambiente.buscarDataAmbiente());
            repositorioPrecoBase.armazenar(historico);
        }

        preco.entrarEmVigencia(ambiente.buscarDataAmbiente(), true);
        preco = repositorioPrecoBase.armazenarSemIsolamentoDeDados(preco);
        historicoPrecoBaseSd.armazenar(preco);
        return propagarAtualizacaoPrecoBase(preco);
    }

    /**
     * Gera registro de histórico do preço recusado.
     *
     * @param precoAtual preco que sera alterado.
     */
    public void recusarAlteracaoPrecoPosto(PrecoBase precoAtual){
        PrecoBase historico = new PrecoBase();
        historico.setPontoVenda(precoAtual.getPontoVenda());
        historico.setPrecoAnterior(precoAtual.getPreco());
        historico.setPreco(precoAtual.getPrecoNegociado());
        historico.setPrecoMicromercado(precoAtual.getPrecoMicromercado());
        historico.setPrecoNegociado(precoAtual.getPrecoNegociado());
        historico.setDataAtualizacao(ambiente.buscarDataAmbiente());
        historico.setDataSolicitacao(precoAtual.getDataSolicitacao());
        historico.setStatus(StatusAlteracaoPrecoPosto.RECUSADO.getValue());
        precoAtual.setPrecoNegociado(null);
        precoAtual.setDataSolicitacao(null);
        precoAtual.setStatus(StatusAlteracaoPrecoPosto.VIGENTE.getValue());
        repositorioPrecoBase.armazenar(precoAtual);
        repositorioPrecoBase.armazenar(historico);
    }

    /**
     * Valida se o preço é diferente de zero e se é diferente do preço atual
     * @param precoSolicitado preço solicitado
     * @param preco preco atual
     * @throws ExcecaoValidacao caso o preço seja zero ou igual ao preço vigente.
     */
    public void validaPrecoSolicitado(BigDecimal precoSolicitado, PrecoBase preco) throws ExcecaoValidacao {
        if(precoSolicitado.compareTo(BigDecimal.ZERO) == 0 || precoSolicitado.equals(preco.getPreco())){
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("precoBase.aprovacao.validacao.preco.invalido"));
        }
    }

    /**
     * Obtem o preço negociado entre uma frota e um ponto de venda, caso exista.
     * Caso não exista um preço negociado retorna o preço base do combustível caso o mesmo seja comercializado pelo ponto de venda informado.
     * @param idFrota id da frota da negociação
     * @param idPontoDeVenda id do ponto de venda da negociação
     * @param idTipoCombustivel id do combustível a ser consultado
     * @return o preço do combustível
     */
    public BigDecimal obterPrecoPraticadoCombustivel(Long idFrota, Long idPontoDeVenda, Long idTipoCombustivel) {
        List<PrecoBase> precos = repositorioPrecoBase.buscarPrecosVigentes(idPontoDeVenda, idTipoCombustivel);
        if(precos == null || precos.isEmpty()) {
            return null;
        }
        Preco preco = repositorioPreco.obterAtualPorFrotaPvCombustivel(idFrota, idPontoDeVenda, idTipoCombustivel);
        if(preco != null) {
            return preco.getPreco();
        }
        return UtilitarioLambda.obterPrimeiroObjetoDaLista(precos).getPreco();
    }
}
