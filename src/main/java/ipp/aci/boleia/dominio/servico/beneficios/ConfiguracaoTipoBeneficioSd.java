package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.ITipoBeneficioDados;
import ipp.aci.boleia.dados.IConfiguracaoTipoBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.ConfiguracaoTipoBeneficio;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Servicos de domínio da entidade {@link ConfiguracaoTipoBeneficio}.
 *
 * @author lucas.santiago
 */
@Component
public class ConfiguracaoTipoBeneficioSd {

    @Autowired
    private ITipoBeneficioDados repositorioBeneficios;
    @Autowired
    private IConfiguracaoTipoBeneficioDados repositorio;
    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria uma entidade ConfiguracaoTipoBeneficio para um Beneficiário específico com um Benefício específico.
     * @param contaBeneficiario Conta do beneficiário.
     * @param idBeneficio Id do benefício desejado.
     * @return A ConfiguracaoTipoBeneficio criada.
     */
    public ConfiguracaoTipoBeneficio criarContaBeneficio(ContaBeneficiario contaBeneficiario, Long idBeneficio){
        ConfiguracaoTipoBeneficio configuracaoTipoBeneficio = new ConfiguracaoTipoBeneficio();
        configuracaoTipoBeneficio.setContaBeneficiario(contaBeneficiario);
        configuracaoTipoBeneficio.setTipoBeneficio(repositorioBeneficios.obterPorId(idBeneficio));
        configuracaoTipoBeneficio.setDataCriacao(ambiente.buscarDataAmbiente());
        configuracaoTipoBeneficio.setDataAtualizacao(ambiente.buscarDataAmbiente());
        configuracaoTipoBeneficio.setConfigurado(false);
        repositorio.armazenar(configuracaoTipoBeneficio);
        return configuracaoTipoBeneficio;
    }

}
