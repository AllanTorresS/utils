package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IContaBeneficiarioDados;
import ipp.aci.boleia.dados.IOperacaoContaBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.beneficios.OperacaoContaBeneficiario;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Serviços de domínio da entidade {@link OperacaoContaBeneficiario}.
 *
 * @author pedro.silva
 */
@Component
public class OperacaoContaBeneficiarioSd {

    @Autowired
    private IOperacaoContaBeneficiarioDados operacaoContaBeneficiarioDados;
    @Autowired
    private IContaBeneficiarioDados contaBeneficiarioDados;
    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Cria uma operação de crédito em uma conta de beneficiário.
     *
     * @param contaBeneficiario a conta que sofreu a operação.
     * @param valorOperacao o valor da operação.
     * @return operação criada.
     *
     * @throws ExcecaoValidacao lançada caso seja passado um valor menor ou igual a zero para a criação do crédito.
     */
    public OperacaoContaBeneficiario criarOperacaoCreditoConta(ContaBeneficiario contaBeneficiario, BigDecimal valorOperacao) throws ExcecaoValidacao {
        if(valorOperacao.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcecaoValidacao(Erro.ERRO_GENERICO);
        }

        OperacaoContaBeneficiario operacao = new OperacaoContaBeneficiario();
        operacao.setContaBeneficiario(contaBeneficiario);
        operacao.setValorTotal(valorOperacao);
        operacao.setDataCriacao(utilitarioAmbiente.buscarDataAmbiente());
        operacao.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente());
        operacao.setSaldoResultante(contaBeneficiario.getSaldo().add(valorOperacao));
        operacao = operacaoContaBeneficiarioDados.armazenar(operacao);

        contaBeneficiario.setSaldo(operacao.getSaldoResultante());
        contaBeneficiarioDados.armazenar(contaBeneficiario);

        return operacao;
    }
}
