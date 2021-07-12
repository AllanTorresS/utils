package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dados.IBeneficioDados;
import ipp.aci.boleia.dados.IContaBeneficiarioDados;
import ipp.aci.boleia.dados.IContaBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.Beneficiario;
import ipp.aci.boleia.dominio.beneficios.Beneficio;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficio;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private IContaBeneficioDados contaBeneficioDados;

    @Autowired
    private IBeneficioDados beneficioDados;

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
        contaBeneficiario = contaBeneficiarioDados.armazenar(contaBeneficiario);
        contaBeneficiario.setContasBeneficio(configurarBeneficiosIniciais(contaBeneficiario));
        return contaBeneficiario;
    }

    /**
     * Realiza a configuração padrão de benefícios para uma {@link ContaBeneficiario}.
     *
     * @param contaBeneficiario Conta que será configurada.
     * @return a lista com os vinculos entre {@link ContaBeneficiario} e {@link Beneficio}.
     */
    private List<ContaBeneficio> configurarBeneficiosIniciais(ContaBeneficiario contaBeneficiario) {
        List<Beneficio> beneficios = beneficioDados.obterTodos(null);
        List<ContaBeneficio> listaContaBeneficio = new ArrayList<>();
        beneficios.forEach(beneficio -> {
            ContaBeneficio contaBeneficio = new ContaBeneficio();
            contaBeneficio.setBeneficio(beneficio);
            contaBeneficio.setBeneficioConfigurado(true);
            contaBeneficio.setContaBeneficiario(contaBeneficiario);
            contaBeneficio.setDataCriacao(utilitarioAmbiente.buscarDataAmbiente());
            contaBeneficio.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente());
            listaContaBeneficio.add(contaBeneficio);
        });
        return contaBeneficioDados.armazenarLista(listaContaBeneficio);
    }
}
