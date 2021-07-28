package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IContaBeneficiosFrotaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;
import ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Servicos de domínio da entidade {@link ContaBeneficiosFrota}.
 *
 * @author lucas.santiago
 */
@Component
public class ContaBeneficiosFrotaSd {

    @Autowired
    private IContaBeneficiosFrotaDados repositorio;

    @Autowired
    private IConfiguracaoSistemaDados repositorioConfiguracaoSistema;

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

        BigDecimal indiceLimite = frota.getLimiteCreditoBeneficiosFrota() != null ?
                frota.getLimiteCreditoBeneficiosFrota().getValorIndiceLimite() :
                new BigDecimal(repositorioConfiguracaoSistema.buscarConfiguracoes(ChaveConfiguracaoSistema.INDICE_LIMITE_BENEFICIOS).getParametro());

        contaBeneficiosFrota.setSaldo(frota.getSaldo().getLimiteCredito().multiply(indiceLimite));

        contaBeneficiosFrota.setDataCriacao(ambiente.buscarDataAmbiente());
        contaBeneficiosFrota.setDataAtualizacao(ambiente.buscarDataAmbiente());
        return contaBeneficiosFrota;
    }
}
