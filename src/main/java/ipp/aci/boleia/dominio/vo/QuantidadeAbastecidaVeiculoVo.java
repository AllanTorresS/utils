package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa classe auxiliar para a rotina de autualizacao de cotas veiculos.
 */
public class QuantidadeAbastecidaVeiculoVo implements Comparable<QuantidadeAbastecidaVeiculoVo> {

    private Long idAbastecimento;
    private Long idVeiculo;
    private BigDecimal valor;
    private BigDecimal totalLitros;
    private Date dataAbastecimento;
    private Long hodometro;
    private BigDecimal horimetro;
    private Long primeiroHodometro;
    private BigDecimal primeiroHorimetro;

    /**
     * Construtor default
     */
    public QuantidadeAbastecidaVeiculoVo() {
        // Construtor default
    }

    /**
     * Construtor para pesquisa de cotas de veiculos
     *
     * @param idAbastecimento O id do abastecimento
     * @param idVeiculo id do veiculo
     * @param valor O valor unitario do combustivel
     * @param totalLitros total de litros abastecidos
     * @param dataAbastecimento data do abastecimento
     * @param hodometro O hodometro atual do veiculo
     * @param hodometroAnterior O hodometro anterior ao abastecimento
     * @param horimetro O horimetro atual do veiculo
     * @param horimetroAnterior O horimetro anterior ao abastecimento
     */
    public QuantidadeAbastecidaVeiculoVo(Long idAbastecimento, Long idVeiculo, BigDecimal totalLitros, BigDecimal valor,
                                         Date dataAbastecimento, Long hodometro, BigDecimal horimetro, Long hodometroAnterior,
                                         BigDecimal horimetroAnterior) {
        this.idAbastecimento = idAbastecimento;
        this.idVeiculo = idVeiculo;
        this.valor = valor;
        this.totalLitros = totalLitros;
        this.dataAbastecimento = dataAbastecimento;
        this.hodometro = hodometro;
        this.horimetro = horimetro;
        this.primeiroHodometro = hodometroAnterior;
        this.primeiroHorimetro = horimetroAnterior;
    }

    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTotalLitros() {
        return totalLitros;
    }

    public void setTotalLitros(BigDecimal totalLitros) {
        this.totalLitros = totalLitros;
    }

    public Long getHodometro() {
        return hodometro;
    }

    public void setHodometro(Long hodometro) {
        this.hodometro = hodometro;
    }

    public BigDecimal getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(BigDecimal horimetro) {
        this.horimetro = horimetro;
    }

    public Long getPrimeiroHodometro() {
        return primeiroHodometro;
    }

    public void setPrimeiroHodometro(Long primeiroHodometro) {
        this.primeiroHodometro = primeiroHodometro;
    }

    public BigDecimal getPrimeiroHorimetro() {
        return primeiroHorimetro;
    }

    public void setPrimeiroHorimetro(BigDecimal primeiroHorimetro) {
        this.primeiroHorimetro = primeiroHorimetro;
    }

    public Long getIdAbastecimento() {
        return idAbastecimento;
    }

    public void setIdAbastecimento(Long idAbastecimento) {
        this.idAbastecimento = idAbastecimento;
    }

    @Override
    public int compareTo(QuantidadeAbastecidaVeiculoVo o) {
        //Ordenação Reversa
        int resultadoComparacao = o.dataAbastecimento.compareTo(this.dataAbastecimento);
        return resultadoComparacao != 0 ? resultadoComparacao : o.idAbastecimento.compareTo(this.idAbastecimento);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof QuantidadeAbastecidaVeiculoVo &&
                ((QuantidadeAbastecidaVeiculoVo) obj).getIdAbastecimento().equals(this.idAbastecimento);
    }

    @Override
    public int hashCode() {
        return this.idAbastecimento.intValue();
    }
}
