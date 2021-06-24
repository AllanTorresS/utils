package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.beneficios.ConfiguracaoDistribuicaoAutomatica;
import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;
import ipp.aci.boleia.dominio.vo.beneficios.DistribuicaoBeneficioVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    /**
     * Cria uma nova configuração de distribuição automática.
     *
     * @param frota A frota para a qual se está realizando a configuração.
     * @param status Status da configuração.
     * @param diaDistribuicao Dia de distribuição configurado.
     * @return a configuração preenchida.
     */
    public ConfiguracaoDistribuicaoAutomatica criarConfiguracaoDistribuicaoAutomatica(Frota frota, Integer status, Integer diaDistribuicao) {
        ConfiguracaoDistribuicaoAutomatica configuracao = new ConfiguracaoDistribuicaoAutomatica();
        configuracao.setFrota(frota);
        configuracao.setStatus(status);
        configuracao.setDiaDistribuicao(diaDistribuicao);
        configuracao.setDataDistribuicao(definirProximaDataDistribuicaoAutomatica(diaDistribuicao));
        configuracao.setDataCriacao(ambiente.buscarDataAmbiente());
        configuracao.setDataAtualizacao(ambiente.buscarDataAmbiente());
        return configuracao;
    }

    /**
     * Define a próxima data de distribuição para um dia de distribuição automática.
     *
     * @param diaDistribuicao Dia de distribuição automática.
     * @return Data de distribuição automática
     */
    public Date definirProximaDataDistribuicaoAutomatica(Integer diaDistribuicao) {
        if(diaDistribuicao == null) {
            return null;
        }

        Date hoje = ambiente.buscarDataAmbiente();
        Date dataDistribuicao = UtilitarioCalculoData.definirDiaData(hoje, diaDistribuicao);
        if(UtilitarioCalculoData.obterCampoDia(hoje) <= diaDistribuicao) {
            dataDistribuicao = UtilitarioCalculoData.adicionarMesesData(dataDistribuicao, 1);
        }
        return dataDistribuicao;
    }
}
