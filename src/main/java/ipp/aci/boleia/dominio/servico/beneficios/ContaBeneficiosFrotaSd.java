package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IContaBeneficiosFrotaDados;
import ipp.aci.boleia.dados.IPedidoCreditoBeneficiosDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;
import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;
import ipp.aci.boleia.dominio.vo.beneficios.ContaBeneficiosFrotaVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicos de domínio da entidade {@link ContaBeneficiosFrota}.
 *
 * @author lucas.santiago
 */
@Component
public class ContaBeneficiosFrotaSd {

    //Valor padrão para testes
    private static final BigDecimal INDICE_PADRAO_LIMITE_BENEFICIOS = BigDecimal.valueOf(0.1);

    @Autowired
    private IContaBeneficiosFrotaDados repositorio;

    @Autowired
    private IConfiguracaoSistemaDados repositorioConfiguracaoSistema;

    @Autowired
    private IPedidoCreditoBeneficiosDados repositorioPedidoCreditoBeneficios;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria uma entidade de ContaBeneficiosFrota para a frota passada.
     *
     * @param frota Frota ao qual a conta se refere.
     * @return retorna a conta criada.
     */
    public ContaBeneficiosFrota criarContaBeneficiosFrota(Frota frota){
        ContaBeneficiosFrota contaBeneficiosFrota = new ContaBeneficiosFrota();
        contaBeneficiosFrota.setFrota(frota);

        BigDecimal indiceLimite = frota.getLimiteCreditoBeneficiosFrota() != null
                ? frota.getLimiteCreditoBeneficiosFrota().getValorIndiceLimite()
                : INDICE_PADRAO_LIMITE_BENEFICIOS;

        contaBeneficiosFrota.setSaldo(BigDecimal.ZERO);
        contaBeneficiosFrota.setDataCriacao(ambiente.buscarDataAmbiente());
        contaBeneficiosFrota.setDataAtualizacao(ambiente.buscarDataAmbiente());
        return contaBeneficiosFrota;
    }

    /**
     * Obtém um Vo com todos os valores necessários para o cálculo do saldo de benefício da conta.
     *
     * @param conta Conta de benefícios da frota.
     * @return DTO com todos os valores necessários.
     */
    public ContaBeneficiosFrotaVo obterValoresContaBeneficioFrota(ContaBeneficiosFrota conta){
        List<PedidoCreditoBeneficios> pedidosPendentes = repositorioPedidoCreditoBeneficios.obterPedidosCreditoBeneficioAbertos();

        BigDecimal indiceLimiteDisponivel = conta.getFrota().getLimiteCreditoBeneficiosFrota() != null
                ? conta.getFrota().getLimiteCreditoBeneficiosFrota().getValorIndiceLimite()
                : INDICE_PADRAO_LIMITE_BENEFICIOS;

        BigDecimal creditoDisponivelFrota = conta.getFrota().getSaldo().getLimiteCredito();
        BigDecimal saldoDisponivelFrota = conta.getFrota().getSaldo().getSaldoCorrente();

        BigDecimal pagamentoPendente = pedidosPendentes.stream().map(PedidoCreditoBeneficios::getValorPedido).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal limiteDisponivel = creditoDisponivelFrota.multiply(indiceLimiteDisponivel);

        BigDecimal saldoBeneficio = limiteDisponivel.subtract(pagamentoPendente).min(saldoDisponivelFrota);
        BigDecimal saldoDisponivel = conta.getSaldo();

        return new ContaBeneficiosFrotaVo(creditoDisponivelFrota, saldoDisponivelFrota, limiteDisponivel, indiceLimiteDisponivel, saldoBeneficio, saldoDisponivel, pagamentoPendente);
    }
}
