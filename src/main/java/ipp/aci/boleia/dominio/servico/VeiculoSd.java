package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IPrecoBaseDados;
import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.AbastecimentoVeiculoMes;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.GrupoExecucaoParametroSistema;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.interfaces.IFluxoAbastecimentoConfig;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarCnpjApresentacao;

/**
 * Implementa as regras de negocio relacionadas a entiadade Veiculo
 */
@Component
public class VeiculoSd {

    @Autowired
    private IFrotaDados frotaDados;

    @Autowired
    private IVeiculoDados veiculoDados;

    @Autowired
    private IPrecoDados precoDados;

    @Autowired
    private IPrecoBaseDados precoBaseDados;

    @Autowired
    protected FluxoAbastecimentoSd fluxoAbastecimentoSd;

    @Autowired
    private ExecucaoParametrosSistemaSd execucaoParametrosSistemaSd;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Autowired
    @Qualifier("Redis")
    private Sincronizador sincronizador;

    @Value("${concorrencia.lock.veiculo.agregado.importacao-cota}")
    private String prefixoLockFrotaImportacaoCotaVeiculoAgregado;

    private MapeadorLock<Long> mapeadorLockFrotaImportacaoCotaVeiculoAgregado;

    /**
     * Configura o monitor de autoriza????o de pagamento
     */
    @PostConstruct
    public void inicializador() {
        mapeadorLockFrotaImportacaoCotaVeiculoAgregado = new MapeadorLock<>(sincronizador, this::construirChaveFrotaImportacaoCotaVeiculoAgregado);
    }

    /**
     * Cria a chave do lock de importacao cota veiculo agregado por frota
     *
     * @param idFrota id da Frota
     * @return chave do lock para importacao em lote de motorista
     */
    private String construirChaveFrotaImportacaoCotaVeiculoAgregado(Long idFrota) {
        return String.format("%s:%s", prefixoLockFrotaImportacaoCotaVeiculoAgregado, idFrota.toString());
    }

    /**
     * Cria um lock para mutual exclusion de autoriza????o de pagamento em processamento
     *
     * @param idFrota Identificador da Frota
     * @return O Lock criado
     */
    public Lock getLockAutorizacaoPagamento(Long idFrota) {
        return mapeadorLockFrotaImportacaoCotaVeiculoAgregado.getLock(idFrota);
    }

    /**
     * M??todo que obt??m valor negociado para abastecimento em um ponto de venda.
     *
     * @param idCombustivel id do combustivel.
     * @param idFrota id da Frota.
     * @param idPontoVenda id do ponto de venda
     * @return Pre??o negociado.
     */
    public BigDecimal obterValorNegociadoAbastecimento(Long idFrota, Long idCombustivel, Long idPontoVenda) {
        Preco preco = null;
        if(idFrota != null && idCombustivel != null && idPontoVenda != null) {
            preco = precoDados.obterAtualPorFrotaPvCombustivel(idFrota, idPontoVenda, idCombustivel);
        }
        return preco != null ? preco.getPrecoComAcordo() : null;
    }

    /**
     * M??todo que obt??m valor negociado para abastecimento em um ponto de venda para determinada data.
     *
     * @param idCombustivel id do combustivel.
     * @param idFrota id da Frota.
     * @param idPontoVenda id do ponto de venda
     * @param dataAbastecimento data que o abastecimento inclu??do ocorreu
     * @return Pre??o negociado.
     */
    public BigDecimal obterValorNegociadoAbastecimento(Long idFrota, Long idCombustivel, Long idPontoVenda, Date dataAbastecimento) {
        Preco preco = null;
        if(idFrota != null && idCombustivel != null && idPontoVenda != null && dataAbastecimento!=null) {
            preco = precoDados.obterPorDataFrotaPvCombustivel(idFrota, idPontoVenda, idCombustivel, dataAbastecimento);
        }
        return preco != null ? preco.getPrecoComAcordo() : null;
    }

    /**
     * M??todo que obt??m valor base para abastecimento em um ponto de venda.
     *
     * @param idCombustivel id do combustivel.
     * @param idPontoVenda id do ponto de venda
     * @return Pre??o negociado.
     */
    public BigDecimal obterValorBaseAbastecimento(Long idCombustivel, Long idPontoVenda) {
        List<PrecoBase> precos = null;
        if(idCombustivel != null && idPontoVenda != null) {
            precos = precoBaseDados.buscarPrecosVigentes(idPontoVenda, idCombustivel);
        }
        return precos != null && !precos.isEmpty() ? precos.get(0).getPreco() : null;
    }

    /**
     * M??todo que obt??m o valor base para abastecimento em um ponto de venda para determinada data.
     *
     * @param idCombustivel id do combust??vel.
     * @param idPontoVenda id do ponto de venda.
     * @param dataAbastecimento data do abastecimento.
     * @return Pre??o negociado.
     */
    public BigDecimal obterValorBaseAbastecimento(Long idCombustivel, Long idPontoVenda,Date dataAbastecimento) {
        PrecoBase preco = null;
        if(idCombustivel != null && idPontoVenda != null) {
            preco = precoBaseDados.buscarPrecosPorData(idPontoVenda, idCombustivel,dataAbastecimento);
        }
        return preco != null && !preco.getInvalido() ? preco.getPreco(): null;
    }

    /**
     * Obtem o consumo medio de um veiculo dado lista de abastecimentos mes
     * @param abastecimentos lista de abastecimentos mes do veiculo
     * @return consumo medio do veiculo
     */
    public BigDecimal obterConsumoMedio(List<AbastecimentoVeiculoMes> abastecimentos) {
        BigDecimal hodometroTotal = BigDecimal.ZERO;
        BigDecimal horimetroTotal = BigDecimal.ZERO;
        BigDecimal litrosTotal = BigDecimal.ZERO;

        for(AbastecimentoVeiculoMes abastecimento : abastecimentos) {
            if(abastecimento.getTotalHodometro() != null) {
                hodometroTotal = hodometroTotal.add(new BigDecimal(abastecimento.getTotalHodometro()));
            }
            if(abastecimento.getTotalHorimetro() != null) {
                horimetroTotal = horimetroTotal.add(abastecimento.getTotalHorimetro());
            }
            litrosTotal = litrosTotal.add(abastecimento.getTotalLitrosAbastecimento());
        }
        if(litrosTotal.compareTo(BigDecimal.ZERO) > 0) {
            if(hodometroTotal.compareTo(BigDecimal.ZERO)>0) {
                return hodometroTotal.divide(litrosTotal, 3, BigDecimal.ROUND_HALF_UP);
            } else if(horimetroTotal.compareTo(BigDecimal.ZERO)>0) {
                return litrosTotal.divide(horimetroTotal, 3, BigDecimal.ROUND_HALF_UP);
            }
        }
        return null;
    }

    /**
     * Obt??m o preco negociado vigente da frota e ponto de venda
     * de um determinado combustivel, caso nao houver, trara o preco base do ponto de venda (RN302)
     *
     * @param codigoFrota Identificador unico da frota
     * @param codigoPV Identificador unico do Ponto de Venda
     * @param codigoCombustivel Identificador unico do combustivel
     * @param dataAbastecimento Data do abastecimento inclu??do
     * @return Preco negociado localizado ou nulo caso n??o encontre
     */
    public BigDecimal obterValorUnitarioAbastecimento(Long codigoFrota, Long codigoPV, Long codigoCombustivel, Date dataAbastecimento) {
        BigDecimal precoNegociado = obterValorNegociadoAbastecimento(codigoFrota,codigoCombustivel,codigoPV, dataAbastecimento);
        if(precoNegociado == null){
           return obterValorBaseAbastecimento(codigoCombustivel,codigoPV,dataAbastecimento);
        }
        return precoNegociado;
    }

    /**
     * Verifica o Status de um ve??culo na Frota para o pdv web
     * @param placa placa do veiculo da frota
     * @param idFrota  o id da frota Uma lista de frotas quem cont??m ve??culos com a placa selecionada.
     * @throws ExcecaoValidacao quando o ve??culo ou a frota est??o inativas.
     */
    public void verificarVeiculoNaFrotaPdv(String placa, Long idFrota) throws ExcecaoValidacao {
        Frota frota = frotaDados.obterPorId(idFrota);
        if(StatusFrota.INATIVO.equals(StatusFrota.obterPorValor(frota.getStatus()))) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("erro.pdv.veiculo.frota.inativa", formatarCnpjApresentacao(frota.getCnpj()), frota.getNomeFantasia()));
        }
        Veiculo veiculo = veiculoDados.buscarPorPlacaFrota(placa, frota.getId());
        if(veiculo == null || StatusAtivacao.INATIVO.equals(StatusAtivacao.obterPorValor(veiculo.getStatus()))){
            throw new ExcecaoValidacao(mensagens.obterMensagem("erro.pdv.veiculo.inativo.na.frota", placa.toUpperCase(), formatarCnpjApresentacao(frota.getCnpj()), frota.getNomeFantasia()));
        }
    }

    /**
     * Verifica o ??ltimo hod??metro de um ve??culo
     *
     * @param idVeiculo id do ve??culo
     * @return ??ltimo hod??metro registrado
     */
    public Long retornarUltimoHodometro(Long idVeiculo) {
        Veiculo veiculo = veiculoDados.obterPorId(idVeiculo);
        return veiculo.getHodometro() != null ? veiculo.getHodometro() : 0L;
    }

    /**
     * Verifica o ??ltimo hor??metro de um ve??culo
     *
     * @param idVeiculo id do ve??culo
     * @return ??ltimo hor??metro registrado
     */
    public BigDecimal retornarUltimoHorimetro(Long idVeiculo) {
        Veiculo veiculo = veiculoDados.obterPorId(idVeiculo);
        return veiculo.getHorimetro() != null ? veiculo.getHorimetro() : BigDecimal.ZERO;
    }

    /**
     * Verifica se o veiculo pertence a frota em questao
     * @param placaVeiculo placa enviado para pr??-autoriza????o
     * @param idFrotaEnviada identificador da frota enviada para pr??-autoriza????o
     * @param frota A frota
     * @return O veiculo, caso localizado
     * @throws ExcecaoValidacao veiculo n??o est?? ativo OU n??o pertence a frota em quest??o
     */
    public Veiculo verificarValidadeVeiculoPreAutorizarAbastecimento(String placaVeiculo, Long idFrotaEnviada, Frota frota) throws ExcecaoValidacao {
        if (StringUtils.isBlank(placaVeiculo)) {
            throw new ExcecaoValidacao(Erro.ERRO_GENERICO);
        }
        Veiculo veiculo = veiculoDados.buscarPorPlacaFrotaSemIsolamento(placaVeiculo, idFrotaEnviada);
        if (veiculo == null) {
            throw new ExcecaoBoleiaRuntime(Erro.VEICULO_NAO_CADASTRADO, placaVeiculo, frota.getNomeFantasia());
        }
        if (!frota.getId().equals(veiculo.getFrota().getId())) {
            throw new ExcecaoBoleiaRuntime(Erro.VEICULO_NAO_CADASTRADO_FROTA, placaVeiculo, frota.getNomeFantasia());
        }
        if (!frota.getStatus().equals(StatusAtivacao.ATIVO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_FROTA_INATIVA, placaVeiculo);
        }
        if (!veiculo.getStatus().equals(StatusAtivacao.ATIVO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_EDICAO_VEICULO_INATIVO, placaVeiculo, frota.getCnpj(), frota.getNomeFantasia());
        }
        return veiculo;
    }

    /**
     * Verifica a validade do contexto de abastecimento das placas com o motorista
     *
     * @param placaVeiculo placa do primeiro veiculo
     * @param segundaPlacaVeiculo placa do segundo veiculo a ser avaliado (opcional)
     * @param motorista motorista a ser validado
     * @param validarParametros flag para executar valida????o de par??metros
     * @throws ExcecaoValidacao quando a verifica????o n??o obteve sucesso
     */
    public void verificarValidadeAbastecimentoPorPlacas(String placaVeiculo, String segundaPlacaVeiculo, Motorista motorista, boolean validarParametros) throws ExcecaoValidacao {
        Date dataCriacao = utilitarioAmbiente.buscarDataAmbiente() ;

        Veiculo primeiroVeiculo = verificarValidadeVeiculoPreAutorizarAbastecimento(placaVeiculo, motorista.getFrota().getId(), motorista.getFrota());
        Veiculo segundoVeiculo = (segundaPlacaVeiculo != null) ? verificarValidadeVeiculoPreAutorizarAbastecimento(segundaPlacaVeiculo, motorista.getFrota().getId(), motorista.getFrota()) : null;

        IFluxoAbastecimentoConfig fluxoAbastecimentoConfig = fluxoAbastecimentoSd.obterParaAbastecimento(motorista.getFrota(), motorista, dataCriacao);
        if (validarParametros) {
            execucaoParametrosSistemaSd.executarParametros(motorista.getFrota(), GrupoExecucaoParametroSistema.PRE_AUTORIZACAO_PLACA, new PreAutorizacaoPedidoVo(motorista.getFrota(), primeiroVeiculo, dataCriacao));
        }
        if (segundoVeiculo == null){
            fluxoAbastecimentoSd.validarVeiculoConformeFluxoMotorista(fluxoAbastecimentoConfig,primeiroVeiculo);
        } else {
            fluxoAbastecimentoSd.validarVeiculosConformeFluxoMotorista(fluxoAbastecimentoConfig,primeiroVeiculo,segundoVeiculo);
            if (validarParametros) {
                execucaoParametrosSistemaSd.executarParametros(motorista.getFrota(), GrupoExecucaoParametroSistema.PRE_AUTORIZACAO_PLACA, new PreAutorizacaoPedidoVo(motorista.getFrota(), segundoVeiculo, dataCriacao));
            }
        }
    }
}
