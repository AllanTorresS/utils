package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.ICampanhaDados;
import ipp.aci.boleia.dados.ICondicaoCampanhaDados;
import ipp.aci.boleia.dados.IParametroCampanhaDados;
import ipp.aci.boleia.dados.IValorCondicaoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.campanha.Campanha;
import ipp.aci.boleia.dominio.campanha.CondicaoCampanha;
import ipp.aci.boleia.dominio.campanha.ValorCondicao;
import ipp.aci.boleia.dominio.enums.Infraestrutura;
import ipp.aci.boleia.dominio.enums.StatusCampanha;
import ipp.aci.boleia.dominio.enums.campanha.TipoDesconto;
import ipp.aci.boleia.dominio.enums.campanha.TipoOperacaoCondicaoCampanha;
import ipp.aci.boleia.dominio.enums.campanha.parametros.CriterioParametroCampanha;
import ipp.aci.boleia.dominio.enums.campanha.parametros.TipoParametroCampanha;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Implementa as regras de negócio relativas ao cálculo de descontos para abastecimentos de frotas leves
 */
@Component
public class CampanhaSd {

    @Autowired
    @Qualifier("Redis")
    private Sincronizador sincronizador;

    @Value("${concorrencia.lock.gerador-campanha-auto-pgto.prefixo}")
    private String prefixoLockAutorizacaoPagamento;

    private MapeadorLock<Long> mapeadorLockAutorizacaoPagamento;

    @Autowired
    private ICampanhaDados repositorio;

    @Autowired
    protected UtilitarioAmbiente ambiente;

    @Autowired
    private ICondicaoCampanhaDados repositorioCondicoes;

    @Autowired
    private IValorCondicaoDados repositorioValorCondicao;

    @Autowired
    private IParametroCampanhaDados repositorioParametroCampanha;

    private Map<String, BiFunction<AutorizacaoPagamento, CondicaoCampanha, Boolean>> mapeamentoValidadoresSemReflexao;

    private Map<Integer, Boolean> parametrosOrdenacaoCampanhas;

    @Autowired
    private Mensagens mensagens;

    /**
     * Retorna o valor padrão de desconto para Frotas Leves.
     *
     * @return Desconto padrão.
     */
    public BigDecimal obterDescontoPadrao() {
        return BigDecimal.valueOf(2.00d);
    }

    /**
     * Configura o monitor de autorização de pagamento
     */
    @PostConstruct
    public void inicializador() {
        mapeadorLockAutorizacaoPagamento = new MapeadorLock<>(sincronizador, this::construirChaveLockAutorizacaoPagamento);
        mapeamentoValidadoresSemReflexao = new HashMap<>();
        mapeamentoValidadoresSemReflexao.put(Infraestrutura.class.getSimpleName(), this::verificarCondicaoInfraestrutura);
    }

    /**
     * Cria a chave do lock de autorização de pagamento para processamento de campanhas
     *
     * @param idAutorizacaoPagamento o identificador da AutorizacaoPagamento
     * @return o lock criado
     */
    private String construirChaveLockAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        return String.format("%s:%s", prefixoLockAutorizacaoPagamento, idAutorizacaoPagamento.toString());
    }

    /**
     * Cria um lock para exclusão mútua de autorização de pagamento em processamento
     *
     * @param idAutorizacaoPagamento Identificador da autorização de pagamento
     * @return O Lock criado
     */
    public Lock getLockAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        return mapeadorLockAutorizacaoPagamento.getLock(idAutorizacaoPagamento);
    }

    /**
     * Retorna a {@link Campanha} mais adequada a uma {@link AutorizacaoPagamento}
     * @param autorizacaoPagamento A autorizacao de pagamento a servir de base
     *
     * @return A campanha mais adequada para a autorizacao de pagamento
     * @throws NoSuchMethodException Caso a reflexão não encontre algum método especificado
     * @throws InvocationTargetException Caso haja erro na chamada de um método
     * @throws IllegalAccessException Caso haja erro ao acessar um método
     */
    public Campanha buscarCampanhaParaAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        if (autorizacaoPagamento != null) {
            List<Campanha> campanhasElegiveis = repositorio.buscarAtivas(autorizacaoPagamento.getDataRequisicao()).getRegistros();
            if (campanhasElegiveis.size() <= 0) {
                return null;
            }

            List<Campanha> campanhasSelecionadas = new ArrayList<>();
            Campanha campanhaSelecionada = null;
            ItemAutorizacaoPagamento itemAbastecimento = autorizacaoPagamento.obterItemAbastecimento();

            this.obterParametrosOrdenacaoCampanhas();

            for (Campanha campanha : campanhasElegiveis) {

                if (campanha.getCombustiveis().stream().noneMatch(c -> itemAbastecimento.getCombustivel().getId().equals(c.getId()))) {
                    continue;
                }

                Boolean campanhaValida = true;

                for (CondicaoCampanha condicao : campanha.getCondicoes()) {
                    campanhaValida &= condicaoCampanhaAtendida(autorizacaoPagamento, condicao);
                }

                if (campanhaValida) {
                    campanhasSelecionadas.add(campanha);
                    campanhaSelecionada = compararCampanhasEmRelacaoAutorizacaoPagamento(campanhaSelecionada, campanha, autorizacaoPagamento);
                }
            }

            if (campanhaSelecionada != null) {
                final Campanha finalCampanhaSelecionada = campanhaSelecionada;
                campanhasSelecionadas = campanhasSelecionadas.stream().filter(c -> !finalCampanhaSelecionada.getNome().equalsIgnoreCase(c.getNome())).collect(Collectors.toList());
            }

            itemAbastecimento.setCampanhasElegiveis(campanhasSelecionadas);

            return campanhaSelecionada;
        } else {
            return null;
        }
    }

    /**
     * Busca os parâmetros para ordenação de campanhas
     */
    private void obterParametrosOrdenacaoCampanhas() {
        if (this.parametrosOrdenacaoCampanhas == null) {
            this.parametrosOrdenacaoCampanhas = new HashMap<>();
        }
        repositorioParametroCampanha.obterTodos(null).forEach(p ->
                this.parametrosOrdenacaoCampanhas
                        .put(
                                TipoParametroCampanha.obterPorValor(p.getTipo()).getValue(),
                                CriterioParametroCampanha.MAIOR.getValue().equals(p.getCriterio())
                        )
        );
    }

    /**
     * Verifica se uma {@link Campanha} é mais adequada para uma {@link AutorizacaoPagamento}
     *
     * @param campanhaCorrente     A campanha previamente selecionada para a autorização de pagamento
     * @param campanhaElegivel     A campanha a ser verificada se é mais adequada do que a corrente
     * @param autorizacaoPagamento A autorização de pagamento a servir de base
     * @return A campanha mais adequada para a autorização de pagamento
     */
    private Campanha compararCampanhasEmRelacaoAutorizacaoPagamento(Campanha campanhaCorrente, Campanha campanhaElegivel, AutorizacaoPagamento autorizacaoPagamento) {

        if (campanhaCorrente == null) {
            return campanhaElegivel;
        } else {
            ItemAutorizacaoPagamento itemAbastecimento = autorizacaoPagamento.obterItemAbastecimento();
            BigDecimal descontoNominalCampanhaCorrente = this.obterDescontoNominalEmRelacaoItemAutorizacaoPagamento(campanhaCorrente, itemAbastecimento);
            BigDecimal descontoNominalCampanhaElegivel = this.obterDescontoNominalEmRelacaoItemAutorizacaoPagamento(campanhaElegivel, itemAbastecimento);

            Integer comparacaoValores = descontoNominalCampanhaCorrente.compareTo(descontoNominalCampanhaElegivel);

            Boolean ordenarPeloMaiorValor = this.parametrosOrdenacaoCampanhas.getOrDefault(TipoParametroCampanha.DESCONTO.getValue(), true);
            Boolean ordenarPelaMaiorData = this.parametrosOrdenacaoCampanhas.getOrDefault(TipoParametroCampanha.ANTIGUIDADE.getValue(), true);

            if (comparacaoValores > 0) {
                return ordenarPeloMaiorValor ? campanhaCorrente : campanhaElegivel;
            } else if (comparacaoValores < 0) {
                return ordenarPeloMaiorValor ? campanhaElegivel : campanhaCorrente;
            } else {
                Integer comparacaoDatas = campanhaCorrente.getDataInicio().compareTo(campanhaElegivel.getDataInicio());
                comparacaoDatas = ordenarPelaMaiorData ? (new BigDecimal(comparacaoDatas)).negate().intValue() :
                        comparacaoDatas;
                return comparacaoDatas > 0 ? campanhaElegivel : campanhaCorrente;
            }
        }
    }

    /**
     * Verifica se uma {@link CondicaoCampanha} é atendida por uma {@link AutorizacaoPagamento}
     *
     * @param autorizacaoPagamento A autorização de pagamento a ser utilizada
     * @param condicaoCampanha     A condição de campanha a ser verificada
     * @return true se a condição for satisfeita, false caso contrário
     * @throws NoSuchMethodException     Caso um método utilizado pela api de reflexão não seja encontrado
     * @throws InvocationTargetException Caso haja um erro na invocação de um método via reflexão
     * @throws IllegalAccessException    Caso haja um erro de permissão ao acessar um método via reflexão
     */
    private Boolean condicaoCampanhaAtendida(AutorizacaoPagamento autorizacaoPagamento, CondicaoCampanha condicaoCampanha) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Boolean resultadoVerificacao = condicaoCampanha.getTipoCondicaoCampanha().getCaminhoCampoABastecimento() != null ?
                condicaoCampanhaComReflexaoAtendida(autorizacaoPagamento, condicaoCampanha) :
                condicaoCampanhaSemReflexaoAtendida(autorizacaoPagamento, condicaoCampanha);

        return TipoOperacaoCondicaoCampanha.INCLUSAO.getValue().equals(condicaoCampanha.getTipoOperacao()) ?
                resultadoVerificacao : !resultadoVerificacao;
    }

    /**
     * Verifica se uma {@link AutorizacaoPagamento} foi processada pelo gerador de campanhas
     *
     * @param autorizacaoPagamento A autorização de pagamento a ser verificada
     *
     * @throws ExcecaoValidacao caso a {@link AutorizacaoPagamento} não tenha sido processada
     */
    public void verificarCampanhaProcessadaParaAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) throws ExcecaoValidacao {
        if (!autorizacaoPagamento.getFoiProcessadoPeloGeradorDeCampanhas()) {
            throw new ExcecaoValidacao(Erro.AUTORIZACAO_NAO_PROCESSADO_PELO_GERADOR_CAMPANHA, mensagens.obterMensagem("Erro.AUTORIZACAO_NAO_PROCESSADO_PELO_GERADOR_CAMPANHA", autorizacaoPagamento.getId()));
        }
    }

    /**
     * Verifica, por meio da api de reflexão,
     * se uma {@link CondicaoCampanha} é atendida por uma {@link AutorizacaoPagamento}
     * @param autorizacaoPagamento A autorização de pagamento a ser utilizada
     * @param condicaoCampanha A condição de campanha a ser verificada
     * @return true se a condição for satisfeita, false caso contrário
     * @throws NoSuchMethodException Caso um método utilizado pela api de reflexão não seja encontrado
     * @throws InvocationTargetException Caso haja um erro na invocação de um método via reflexão
     * @throws IllegalAccessException Caso haja um erro de permissão ao acessar um método via reflexão
     */
    private Boolean condicaoCampanhaComReflexaoAtendida(AutorizacaoPagamento autorizacaoPagamento, CondicaoCampanha condicaoCampanha) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String separadorCaminhoReflection = "\\.";
        String[] caminhoReflection = condicaoCampanha.getTipoCondicaoCampanha().getCaminhoCampoABastecimento().split(separadorCaminhoReflection);

        Object resultadoReflection = autorizacaoPagamento;

        for (String nomeFuncaoReflection : caminhoReflection) {
            resultadoReflection = resultadoReflection.getClass().getMethod(nomeFuncaoReflection).invoke(resultadoReflection);
            if(resultadoReflection == null){
                return false;
            }
        }

        String valorCorrespondenteACondicaoNoAbastecimento = resultadoReflection.toString();

        return condicaoCampanha.getValores().stream()
                .anyMatch(v -> compararValorNaCondicaoComValorNoAbastecimento(valorCorrespondenteACondicaoNoAbastecimento,
                        v.getValor())
                );
    }

    /**
     * Compara um valor de uma condição com o valor correspondente, via reflexão, na {@link AutorizacaoPagamento}
     *
     * @param valorNoAbastecimento O valor correspondente no abastecimento
     * @param valorNaCondicao      Um dos valores da condição
     * @return true se os valores forem iguais, ignorando acentos e maiúsculas/minúsculas
     */
    private Boolean compararValorNaCondicaoComValorNoAbastecimento(String valorNoAbastecimento, String valorNaCondicao) {
        return UtilitarioFormatacao.removerAcentos(valorNoAbastecimento)
                .equalsIgnoreCase(UtilitarioFormatacao.removerAcentos(valorNaCondicao));
    }

    /**
     * Verifica, por meio de um método especializado,
     * se uma {@link CondicaoCampanha} é atendida por uma {@link AutorizacaoPagamento}
     *
     * @param autorizacaoPagamento A autorização de pagamento a ser utilizada
     * @param condicaoCampanha     A condição de campanha a ser verificada
     * @return true se a condição for satisfeita, false caso contrário
     */
    private Boolean condicaoCampanhaSemReflexaoAtendida(AutorizacaoPagamento autorizacaoPagamento, CondicaoCampanha condicaoCampanha) {
        return this.mapeamentoValidadoresSemReflexao.get(condicaoCampanha.getTipoCondicaoCampanha().getNomeEntidade()).apply(autorizacaoPagamento, condicaoCampanha);
    }

    /** Verifica baseada no abastecimento se ele contem alguma de infraestrutura requirida pela condição da campanha
     * @param autorizacaoPagamento o abastecimento realizado
     * @param condicaoCampanha a condição da campanha a ser aplicada.
     * @return true se o abastecimento atender a condição
     */
    private Boolean verificarCondicaoInfraestrutura(AutorizacaoPagamento autorizacaoPagamento, CondicaoCampanha condicaoCampanha) {
        return condicaoCampanha.getValores().stream()
                .filter(v -> Arrays.stream(Infraestrutura.values())
                        .anyMatch(itemInfraestrutura -> itemInfraestrutura.name().equals(v.getValor()))
                ).anyMatch(valorCondicao ->
                        autorizacaoPagamento.getPontoVenda().getRespostaQuestionario().stream().anyMatch(respostaQuestionario ->
                                respostaQuestionario.getId().equals(
                                        Infraestrutura.obterPorNome(valorCondicao.getValor()).getIdOpcaoCorrespondente())
                        )
        );
    }

    /**
     * Altera o status do campanha dependendo do status atual
     *
     * @param campanha o campanha
     * @param status O identificador do novo status
     */
    public void alterarStatusDesconto(Campanha campanha, Integer status) {
        if (StatusCampanha.obterPorValor(status) != null) {
            campanha.setStatus(status);
            repositorio.armazenar(campanha);
        }
    }

    /**
     * Armazena um {@link ValorCondicao} pertencente a uma {@link CondicaoCampanha}
     *
     * @param valor    O valor a ser armazenado
     * @param condicao A condicao a ter o valor atribuido
     */
    private void armazenarValorCondicao(ValorCondicao valor, CondicaoCampanha condicao) {
        valor.setCondicao(condicao);
        repositorioValorCondicao.armazenar(valor);
    }

    /**
     * Armazena uma {@link CondicaoCampanha} e seus respectivos {@link ipp.aci.boleia.dominio.campanha.ValorCondicao}
     * nos respectivos repositórios
     *
     * @param condicao A condição a ser armazenada
     */
    private void armazenarCondicao(CondicaoCampanha condicao) {
        repositorioCondicoes.armazenar(condicao);
        condicao.getValores().forEach(v -> this.armazenarValorCondicao(v, condicao));
    }

    /**
     * Associa condições a uma campanha
     *
     * @param campanha  A campanha a ter condições associadas
     * @param condicoes As condições a serem associadas à campanha
     */
    public void cadastrarCondicoes(Campanha campanha, Set<CondicaoCampanha> condicoes) {
        if (condicoes != null) {
            condicoes.forEach(c -> armazenarCondicao(c));
        }
        repositorio.armazenar(campanha);
    }

	/**
	 * Rejeita uma campanha.
	 *
	 * @param campanha  A campanha a ser rejeitada
	 * @param justificativa a justificativa informada pelo usuário
	 */
	public void rejeitarCampanha(Campanha campanha, String justificativa) {

		campanha.setUltimaJustificativaRejeicao(justificativa);
		campanha.setUsuarioAprovador(ambiente.getUsuarioLogado());
		campanha.setStatus(StatusCampanha.RASCUNHO.getValue());


		repositorio.armazenar(campanha);
	}

	/**
	 * Aprova uma campanha, a deixando agendada caso não esteja no seu periodo de vigência
	 * ou a ativando imediatamente.
	 * @param campanha  A campanha a ser aprovada
	 */
	public void aprovarCampanha(Campanha campanha) {
        campanha.setStatus(StatusCampanha.ATIVO.getValue());
		campanha.setUsuarioAprovador(ambiente.getUsuarioLogado());
		repositorio.armazenar(campanha);
	}

    /**
     * Valida se uma campanha tem a data de início válida
     * @param campanha a campanha a ser validada
     * @return se a campanha tem uma data de início válida
     */
    public Boolean validarDataInicio(Campanha campanha) {
        return UtilitarioCalculoData.isHojeOuPosterior(ambiente.buscarDataAmbiente(), campanha.getDataInicio());
    }

    /**
     * Duplica uma campanha, criando uma cópia com novo nome e datas de vigência, porém
     * com condições e valores de condição idênticos
     * @param campanhaOriginal campanha a ser copiada
     * @param nome nome a ser dado para a campanha duplicada
     * @param dataInicio data de início da campanha duplicada
     * @param dataFim data de fim da campanha duplicada
     * @return o id da cópia recêm criada.
     */
    public Long duplicarCampanha(Campanha campanhaOriginal, String nome, Date dataInicio, Date dataFim) {
        Campanha copia = campanhaOriginal.clone();
        copia.setNome(nome);
        copia.setDataInicio(UtilitarioCalculoData.obterPrimeiroInstanteDia(dataInicio));
        copia.setDataFim(UtilitarioCalculoData.obterUltimoInstanteDia(dataFim));
        copia.setStatus(StatusCampanha.RASCUNHO.getValue());
        copia.setUsuarioCriador(ambiente.getUsuarioLogado());
        repositorio.armazenar(copia);
        return copia.getId();
    }

    /**
     * Calcula o desconto nominal de um {@link ItemAutorizacaoPagamento} com base no desconto fornecido por uma {@link Campanha}
     * @param campanha A campanha que fornece o desconto
     * @param itemAutorizacaoPagamento O item a ter o valor calculado
     * @return O valor nominal do desconto do item a partir da campanha
     */
    public BigDecimal obterDescontoNominalEmRelacaoItemAutorizacaoPagamento(Campanha campanha, ItemAutorizacaoPagamento itemAutorizacaoPagamento) {

        if (campanha == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal descontoPercentualMaximo = BigDecimal.valueOf(100.00d);
        Boolean isDescontoEmMoeda = TipoDesconto.MOEDA.getValue().equals(campanha.getTipoDesconto());

        return itemAutorizacaoPagamento.getValorTotal().min(isDescontoEmMoeda ?
                campanha.getValorTotal() : itemAutorizacaoPagamento.getValorTotal().multiply(campanha.getValorTotal()).divide(descontoPercentualMaximo, 3, RoundingMode.HALF_UP));
    }

    /**
     * Calcula o desconto percentual de um {@link ItemAutorizacaoPagamento} com base no desconto fornecido por uma {@link Campanha}
     * @param campanha A campanha que fornece o desconto
     * @param itemAutorizacaoPagamento O item a ter o valor calculado
     * @return O valor percentual do desconto do item a partir da campanha
     */
    public BigDecimal obterDescontoPercentualEmRelacaoItemAutorizacaoPagamento(Campanha campanha, ItemAutorizacaoPagamento itemAutorizacaoPagamento) {
        if (campanha == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal descontoPercentualMaximo = BigDecimal.valueOf(100.00d);
        boolean isDescontoPercentual = TipoDesconto.PERCENTUAL.getValue().equals(campanha.getTipoDesconto());
        return descontoPercentualMaximo.min(
                isDescontoPercentual ? campanha.getValorTotal() : campanha.getValorTotal().divide(
                        itemAutorizacaoPagamento.getValorTotal(),2, RoundingMode.HALF_UP).multiply(descontoPercentualMaximo).setScale(2, RoundingMode.UNNECESSARY)
        );
    }


    /**
     * Valida a vigência e status de uma campanha a se realizar uma operação.
     *
     * @param campanha     a campanha selecionada
     * @param dataCorrente a data corrente do ambiente
     * @throws ExcecaoValidacao em caso de violação das regras de negócio estabelecidas.
     */
    public void validarCampanhaParaAprovacao(Campanha campanha, Date dataCorrente) throws ExcecaoValidacao {
        if (dataCorrente.after(campanha.getDataFim())) {
            throw new ExcecaoValidacao(Erro.VIGENCIA_CAMPANHA_NAO_PERMITE_OPERACAO);
        }
        if (!StatusCampanha.AGUARDANDO_APROVACAO.getValue().equals(campanha.getStatus())) {
            throw new ExcecaoValidacao(Erro.CAMPANHA_OPERACAO_NAO_PERMITIDA);
        }
    }

    /**
     * Valida se o status de uma {@link Campanha} é valido para que ela seja enviada para aprovação
     *
     * @param campanha A campanha a ter o status validado
     * @throws ExcecaoValidacao Caso o status da campanha não seja válido
     */
    public void validarStatusCampanhaAntesDeEnvioParaAprovacao(Campanha campanha) throws ExcecaoValidacao {
        if (!StatusCampanha.RASCUNHO.getValue().equals(campanha.getStatus())) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("Erro.STATUS_CAMPANHA_APROVACAO_INVALIDO", campanha.getNome(), StatusCampanha.RASCUNHO.getLabel()));
        }
    }

    /**
     * Obtém desconto, conforme campanha ou zero se não houver campanha, de uma autorizção pagamento (abastecimento)
     * @param autorizacaoPagamento de um  abastecimento
     * @return valor do desconto
     */
    public BigDecimal obterDescontoNominalEmRelacaoAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        BigDecimal descontoTotal = BigDecimal.ZERO;
        for (ItemAutorizacaoPagamento item : autorizacaoPagamento.getItems()) {
            descontoTotal = descontoTotal.add(this.obterDescontoNominalEmRelacaoItemAutorizacaoPagamento(item.getCampanha(), item));
        }
        return descontoTotal;
    }

    /**
     * Obtém o percentual de desconto, conforme campanha ou zero se não houver campanha, de uma autorizção pagamento (abastecimento)
     * @param autorizacaoPagamento de um  abastecimento
     * @return valor percentual do desconto
     */
    public BigDecimal obterDescontoPercentualEmRelacaoAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        BigDecimal descontoPercentualTotal = BigDecimal.ZERO;
        for (ItemAutorizacaoPagamento item : autorizacaoPagamento.getItems()) {
            descontoPercentualTotal = descontoPercentualTotal.add(this.obterDescontoPercentualEmRelacaoItemAutorizacaoPagamento(item.getCampanha(), item));
        }
        return descontoPercentualTotal;
    }

}
