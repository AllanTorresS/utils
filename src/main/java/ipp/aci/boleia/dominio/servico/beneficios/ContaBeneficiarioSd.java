package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dados.ITipoBeneficioDados;
import ipp.aci.boleia.dados.IContaBeneficiarioDados;
import ipp.aci.boleia.dados.ITipoBeneficioConfiguracaoDados;
import ipp.aci.boleia.dominio.beneficios.Beneficiario;
import ipp.aci.boleia.dominio.beneficios.TipoBeneficio;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.beneficios.TipoBeneficioConfiguracao;
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
    private ITipoBeneficioConfiguracaoDados tipoBeneficioConfiguracaoDados;

    @Autowired
    private ITipoBeneficioDados tipoBeneficioDados;

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
        contaBeneficiario.setTiposBeneficioConfigurados(configurarTiposBeneficioIniciais(contaBeneficiario));
        return contaBeneficiario;
    }

    /**
     * Realiza a configuração padrão de benefícios para uma {@link ContaBeneficiario}.
     *
     * @param contaBeneficiario Conta que será configurada.
     * @return a lista com os vinculos entre {@link ContaBeneficiario} e {@link TipoBeneficio}.
     */
    private List<TipoBeneficioConfiguracao> configurarTiposBeneficioIniciais(ContaBeneficiario contaBeneficiario) {
        List<TipoBeneficio> tiposBeneficio = tipoBeneficioDados.obterTodos(null);
        List<TipoBeneficioConfiguracao> tiposConfigurados = new ArrayList<>();
        tiposBeneficio.forEach(tipoBeneficio -> {
            TipoBeneficioConfiguracao tipoBeneficioConfiguracao = new TipoBeneficioConfiguracao();
            tipoBeneficioConfiguracao.setTipoBeneficio(tipoBeneficio);
            tipoBeneficioConfiguracao.setBeneficioConfigurado(true);
            tipoBeneficioConfiguracao.setContaBeneficiario(contaBeneficiario);
            tipoBeneficioConfiguracao.setDataCriacao(utilitarioAmbiente.buscarDataAmbiente());
            tipoBeneficioConfiguracao.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente());
            tiposConfigurados.add(tipoBeneficioConfiguracao);
        });
        return tipoBeneficioConfiguracaoDados.armazenarLista(tiposConfigurados);
    }
}
