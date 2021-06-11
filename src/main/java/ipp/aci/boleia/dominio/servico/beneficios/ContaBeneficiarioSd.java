package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dados.IContaBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.Beneficiario;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Serviços de domínio da entidade {@link ContaBeneficiario}.
 *
 * @author pedro.silva
 */
@Component
public class ContaBeneficiarioSd {

    @Autowired
    private IBeneficiarioDados beneficiarioDados;
    @Autowired
    private IContaBeneficiarioDados contaBeneficiarioDados;
    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Cria uma conta para um beneficiário.
     *
     * @param idBeneficiario Identificador do beneficiario.
     * @return A conta criada
     */
    public ContaBeneficiario criarContaBeneficiario(Long idBeneficiario) {
        Beneficiario beneficiario = beneficiarioDados.obterPorId(idBeneficiario);
        ContaBeneficiario contaBeneficiario = new ContaBeneficiario();
        contaBeneficiario.setBeneficiario(beneficiario);
        contaBeneficiario.setSaldo(BigDecimal.ZERO);
        contaBeneficiario.setDataCriacao(utilitarioAmbiente.buscarDataAmbiente());
        contaBeneficiario.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente());
        return contaBeneficiarioDados.armazenar(contaBeneficiario);
    }
}
