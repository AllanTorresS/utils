package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.ITipoBeneficioDados;
import ipp.aci.boleia.dados.ITipoBeneficioConfiguracaoDados;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.beneficios.TipoBeneficioConfiguracao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Servicos de domínio da entidade {@link TipoBeneficioConfiguracao}.
 *
 * @author lucas.santiago
 */
@Component
public class TipoBeneficioConfiguracaoSd {

    @Autowired
    private ITipoBeneficioDados repositorioTipoBeneficio;
    @Autowired
    private ITipoBeneficioConfiguracaoDados repositorio;
    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria uma entidade TipoBeneficioConfiguracao para um Beneficiário específico com um Tipo de Benefício específico.
     * @param contaBeneficiario Conta do beneficiário.
     * @param idTipoBeneficio Id do benefício desejado.
     * @return A TipoBeneficioConfiguracao criada.
     */
    public TipoBeneficioConfiguracao criarContaBeneficio(ContaBeneficiario contaBeneficiario, Long idTipoBeneficio){
        TipoBeneficioConfiguracao tipoBeneficioConfiguracao = new TipoBeneficioConfiguracao();
        tipoBeneficioConfiguracao.setContaBeneficiario(contaBeneficiario);
        tipoBeneficioConfiguracao.setTipoBeneficio(repositorioTipoBeneficio.obterPorId(idTipoBeneficio));
        tipoBeneficioConfiguracao.setDataCriacao(ambiente.buscarDataAmbiente());
        tipoBeneficioConfiguracao.setDataAtualizacao(ambiente.buscarDataAmbiente());
        tipoBeneficioConfiguracao.setBeneficioConfigurado(false);
        repositorio.armazenar(tipoBeneficioConfiguracao);
        return tipoBeneficioConfiguracao;
    }

}
