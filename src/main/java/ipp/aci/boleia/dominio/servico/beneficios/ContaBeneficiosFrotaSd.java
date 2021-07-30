package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IContaBeneficiosFrotaDados;
import ipp.aci.boleia.dados.IPedidoCreditoBeneficiosDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;
import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;
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

        contaBeneficiosFrota.setSaldo(frota.getSaldo().getLimiteCredito().multiply(indiceLimite));

        contaBeneficiosFrota.setDataCriacao(ambiente.buscarDataAmbiente());
        contaBeneficiosFrota.setDataAtualizacao(ambiente.buscarDataAmbiente());
        return contaBeneficiosFrota;
    }

    /**
     * Obtém o saldo benefício de uma conta de benefícios da frota.
     *
     * @param conta Conta de benefícios da frota.
     * @return Retorna o saldo de benefícios.
     */
    public BigDecimal obterSaldoBeneficioConta(ContaBeneficiosFrota conta){
        List<PedidoCreditoBeneficios> pedidosPendentes = repositorioPedidoCreditoBeneficios.obterPedidosCreditoBeneficioAbertosPorFrota(conta.getFrota().getId());

        BigDecimal indiceLimiteDisponivel = conta.getFrota().getLimiteCreditoBeneficiosFrota() != null
                ? conta.getFrota().getLimiteCreditoBeneficiosFrota().getValorIndiceLimite()
                : INDICE_PADRAO_LIMITE_BENEFICIOS;

        BigDecimal creditoDisponivelFrota = conta.getFrota().getSaldo().getLimiteCredito();
        BigDecimal saldoDisponivelFrota = conta.getFrota().getSaldo().getSaldoCorrente();

        BigDecimal pagamentoPendente = pedidosPendentes.stream().map(PedidoCreditoBeneficios::getValorPedido).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal limiteDisponivel = creditoDisponivelFrota.multiply(indiceLimiteDisponivel);
        return limiteDisponivel.subtract(pagamentoPendente).min(saldoDisponivelFrota);
    }
}
