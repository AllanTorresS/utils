package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IContaBeneficiosFrotaDados;
import ipp.aci.boleia.dados.IPedidoCreditoBeneficiosDados;
import ipp.aci.boleia.dados.ISaldoFrotaDados;
import ipp.aci.boleia.dominio.SaldoFrota;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;
import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Servicos de domínio da entidade {@link PedidoCreditoBeneficios}.
 *
 * @author lucas.santiago
 */
@Component
public class PedidoCreditoBeneficiosSd {

    @Autowired
    private IPedidoCreditoBeneficiosDados repositorio;

    @Autowired
    private IContaBeneficiosFrotaDados repositorioContaBeneficiosFrota;

    @Autowired
    private ISaldoFrotaDados repositorioSaldoFrota;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria um pedido de crédito para conta beneficios frota com o valor passado.
     *
     * @param contaBeneficiosFrota Conta benefício a fazer o pedído.
     * @param valor Valor do pedído de crédito.
     * @return retorna o pedido criado.
     */
    public PedidoCreditoBeneficios criarPedidoCreditoBeneficios(ContaBeneficiosFrota contaBeneficiosFrota, BigDecimal valor){
        PedidoCreditoBeneficios pedidoCreditoBeneficios = new PedidoCreditoBeneficios();
        pedidoCreditoBeneficios.setContaBeneficiosFrota(contaBeneficiosFrota);
        pedidoCreditoBeneficios.setValorPedido(valor);
        pedidoCreditoBeneficios.setDataPedido(ambiente.buscarDataAmbiente());
        pedidoCreditoBeneficios.setDataAtualizacao(ambiente.buscarDataAmbiente());
        contaBeneficiosFrota.setSaldo(contaBeneficiosFrota.getSaldo().add(valor));
        contaBeneficiosFrota.setDataAtualizacao(ambiente.buscarDataAmbiente());
        repositorioContaBeneficiosFrota.armazenar(contaBeneficiosFrota);
        SaldoFrota saldoFrota = contaBeneficiosFrota.getFrota().getSaldo();
        saldoFrota.setSaldoCorrente(saldoFrota.getSaldoCorrente().subtract(valor));
        repositorioSaldoFrota.armazenar(saldoFrota);
        return pedidoCreditoBeneficios;
    }

}
