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

import java.math.BigDecimal;
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
     * @param idBeneficiario Identificador do beneficiário que terá a distribuição.
     * @param valorDistribuicao Valor da distribuição automática.
     * @return a distribuição automática preenchida.
     */
    public DistribuicaoAutomatica criarNovaDistribuicaoAutomatica(Long idBeneficiario, BigDecimal valorDistribuicao) {
        DistribuicaoAutomatica distribuicao = new DistribuicaoAutomatica();
        distribuicao.setBeneficiario(repositorio.obterPorId(idBeneficiario));
        distribuicao.setValorBeneficio(valorDistribuicao);
        distribuicao.setDataCriacao(ambiente.buscarDataAmbiente());
        distribuicao.setDataAtualizacao(ambiente.buscarDataAmbiente());
        distribuicao.setAutor(ambiente.getUsuarioLogado());
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

        Date mesProximaDistribuicao = definirMesProximaDataDistribuicaoAutomatica(diaDistribuicao);
        Integer ultimoDiaMesProximaDistribuicao = UtilitarioCalculoData.obterCampoDia(UtilitarioCalculoData.obterUltimoDiaMes(mesProximaDistribuicao));
        if(diaDistribuicao > ultimoDiaMesProximaDistribuicao) {
            return UtilitarioCalculoData.definirDiaData(mesProximaDistribuicao, ultimoDiaMesProximaDistribuicao);
        }
        return UtilitarioCalculoData.definirDiaData(mesProximaDistribuicao, diaDistribuicao);
    }

    /**
     * Define o mês da próxima data de distribuição automática.
     *
     * @param diaDistribuicao Dia configurado para a distribuição automática.
     * @return Objeto date com o mês da próxima data de distribuição automática.
     */
    private Date definirMesProximaDataDistribuicaoAutomatica(Integer diaDistribuicao) {
        Date hoje = ambiente.buscarDataAmbiente();
        Integer diaHoje = UtilitarioCalculoData.obterCampoDia(hoje);
        Integer ultimoDiaMesAtual = UtilitarioCalculoData.obterCampoDia(UtilitarioCalculoData.obterUltimoDiaMes(hoje));

        if(diaHoje >= diaDistribuicao || (diaDistribuicao > ultimoDiaMesAtual && diaHoje == ultimoDiaMesAtual)) {
            return UtilitarioCalculoData.adicionarMesesData(hoje, 1);
        }
        return hoje;
    }
}
