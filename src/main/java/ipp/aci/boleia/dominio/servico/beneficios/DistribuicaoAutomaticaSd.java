package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;
import ipp.aci.boleia.dominio.vo.beneficios.DistribuicaoBeneficioVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade {@link DistribuicaoAutomatica}.
 *
 * @author lucas.santiago
 */
@Component
public class DistribuicaoAutomaticaSd {

    @Autowired
    private IBeneficiarioDados repositorio;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria uma nova distribuição automática
     *
     * @param distribuicaoBeneficioVo Objeto com as informações da distribuição.
     * @return a distribuição automática preenchida.
     */
    public DistribuicaoAutomatica criarNovaDistribuicaoAutomatica(DistribuicaoBeneficioVo distribuicaoBeneficioVo) {
        DistribuicaoAutomatica distribuicao = new DistribuicaoAutomatica();
        distribuicao.setBeneficiario(repositorio.obterPorId(distribuicaoBeneficioVo.getIdBeneficiario()));
        distribuicao.setValorBeneficio(distribuicaoBeneficioVo.getValorDistribuicao());
        distribuicao.setDataCriacao(ambiente.buscarDataAmbiente());
        distribuicao.setDataAtualizacao(ambiente.buscarDataAmbiente());

        return distribuicao;
    }
}
