package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.ITipoBeneficioDados;
import ipp.aci.boleia.dados.IContaBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficio;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Servicos de domínio da entidade {@link ContaBeneficio}.
 *
 * @author lucas.santiago
 */
@Component
public class ContaBeneficioSd {

    @Autowired
    private ITipoBeneficioDados repositorioBeneficios;
    @Autowired
    private IContaBeneficioDados repositorio;
    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria uma entidade ContaBeneficio para um Beneficiário específico com um Benefício específico.
     * @param contaBeneficiario Conta do beneficiário.
     * @param idBeneficio Id do benefício desejado.
     * @return A ContaBeneficio criada.
     */
    public ContaBeneficio criarContaBeneficio(ContaBeneficiario contaBeneficiario, Long idBeneficio){
        ContaBeneficio contaBeneficio = new ContaBeneficio();
        contaBeneficio.setContaBeneficiario(contaBeneficiario);
        contaBeneficio.setTipoBeneficio(repositorioBeneficios.obterPorId(idBeneficio));
        contaBeneficio.setDataCriacao(ambiente.buscarDataAmbiente());
        contaBeneficio.setDataAtualizacao(ambiente.buscarDataAmbiente());
        contaBeneficio.setBeneficioConfigurado(false);
        repositorio.armazenar(contaBeneficio);
        return contaBeneficio;
    }

}
