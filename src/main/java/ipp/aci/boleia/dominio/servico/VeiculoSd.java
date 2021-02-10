package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IPrecoBaseDados;
import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.AbastecimentoVeiculoMes;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private Mensagens mensagens;

    /**
     * Método que obtém valor negociado para abastecimento em um ponto de venda.
     *
     * @param idCombustivel id do combustivel.
     * @param idFrota id da Frota.
     * @param idPontoVenda id do ponto de venda
     * @return Preço negociado.
     */
    public BigDecimal obterValorNegociadoAbastecimento(Long idFrota, Long idCombustivel, Long idPontoVenda) {
        Preco preco = null;
        if(idFrota != null && idCombustivel != null && idPontoVenda != null) {
            preco = precoDados.obterAtualPorFrotaPvCombustivel(idFrota, idPontoVenda, idCombustivel);
        }
        return preco != null ? preco.getPrecoComAcordo() : null;
    }

    /**
     * Método que obtém valor negociado para abastecimento em um ponto de venda para determinada data.
     *
     * @param idCombustivel id do combustivel.
     * @param idFrota id da Frota.
     * @param idPontoVenda id do ponto de venda
     * @param dataAbastecimento data que o abastecimento incluído ocorreu
     * @return Preço negociado.
     */
    public BigDecimal obterValorNegociadoAbastecimento(Long idFrota, Long idCombustivel, Long idPontoVenda, Date dataAbastecimento) {
        Preco preco = null;
        if(idFrota != null && idCombustivel != null && idPontoVenda != null && dataAbastecimento!=null) {
            preco = precoDados.obterPorDataFrotaPvCombustivel(idFrota, idPontoVenda, idCombustivel, dataAbastecimento);
        }
        return preco != null ? preco.getPrecoComAcordo() : null;
    }

    /**
     * Método que obtém valor base para abastecimento em um ponto de venda.
     *
     * @param idCombustivel id do combustivel.
     * @param idPontoVenda id do ponto de venda
     * @return Preço negociado.
     */
    public BigDecimal obterValorBaseAbastecimento(Long idCombustivel, Long idPontoVenda) {
        List<PrecoBase> precos = null;
        if(idCombustivel != null && idPontoVenda != null) {
            precos = precoBaseDados.buscarPrecosVigentes(idPontoVenda, idCombustivel);
        }
        return precos != null && !precos.isEmpty() ? precos.get(0).getPreco() : null;
    }

    /**
     * Método que obtém o valor base para abastecimento em um ponto de venda para determinada data.
     *
     * @param idCombustivel id do combustível.
     * @param idPontoVenda id do ponto de venda.
     * @param dataAbastecimento data do abastecimento.
     * @return Preço negociado.
     */
    public BigDecimal obterValorBaseAbastecimento(Long idCombustivel, Long idPontoVenda,Date dataAbastecimento) {
        PrecoBase preco = null;
        if(idCombustivel != null && idPontoVenda != null) {
            preco = precoBaseDados.buscarPrecosPorData(idPontoVenda, idCombustivel,dataAbastecimento);
        }
        return preco != null ? preco.getPreco(): null;
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
     * Obtém o preco negociado vigente da frota e ponto de venda
     * de um determinado combustivel, caso nao houver, trara o preco base do ponto de venda (RN302)
     *
     * @param codigoFrota Identificador unico da frota
     * @param codigoPV Identificador unico do Ponto de Venda
     * @param codigoCombustivel Identificador unico do combustivel
     * @param dataAbastecimento Data do abastecimento incluído
     * @return Preco negociado localizado ou nulo caso não encontre
     */
    public BigDecimal obterValorUnitarioAbastecimento(Long codigoFrota, Long codigoPV, Long codigoCombustivel, Date dataAbastecimento) {
        BigDecimal precoNegociado = obterValorNegociadoAbastecimento(codigoFrota,codigoCombustivel,codigoPV, dataAbastecimento);
        BigDecimal precoBase = null;
        if(precoNegociado == null){
            precoBase = obterValorBaseAbastecimento(codigoCombustivel,codigoPV,dataAbastecimento);
        }
        return precoNegociado != null ? precoNegociado : precoBase;
    }

    /**
     * Verifica o Status de um veículo na Frota para o pdv web
     * @param placa placa do veiculo da frota
     * @param idFrota  o id da frota Uma lista de frotas quem contém veículos com a placa selecionada.
     * @throws ExcecaoValidacao quando o veículo ou a frota estão inativas.
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
     * Verifica o último hodômetro de um veículo
     *
     * @param idVeiculo id do veículo
     * @return último hodômetro registrado
     */
    public Long retornarUltimoHodometro(Long idVeiculo) {
        Veiculo veiculo = veiculoDados.obterPorId(idVeiculo);
        return veiculo.getHodometro() != null ? veiculo.getHodometro() : 0L;
    }

    /**
     * Verifica o último horímetro de um veículo
     *
     * @param idVeiculo id do veículo
     * @return último horímetro registrado
     */
    public BigDecimal retornarUltimoHorimetro(Long idVeiculo) {
        Veiculo veiculo = veiculoDados.obterPorId(idVeiculo);
        return veiculo.getHorimetro() != null ? veiculo.getHorimetro() : BigDecimal.ZERO;
    }

}
