package ipp.aci.boleia.dominio.servico;


import ipp.aci.boleia.dados.IHistoricoVeiculoDados;
import ipp.aci.boleia.dominio.HistoricoVeiculo;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade {@link HistoricoVeiculo}.
 *
 * @author allan.santos
 */
@Component
public class HistoricoVeiculoSd {

    @Autowired
    private IHistoricoVeiculoDados repositorioHistoricoVeiculoDados;

    /**
     * Cria o historico para um Veiculo
     *
     * @param veiculoEntidade a entidade que deve ter seu historico gravado
     * @param ambiente          contem as informações do usuario logado que realizou a alteração e data atual da alteração
     */
    public void armazenarHistoricoVeiculo(Veiculo veiculoEntidade, UtilitarioAmbiente ambiente) {

        HistoricoVeiculo historicoVeiculo = new HistoricoVeiculo();

        BeanUtils.copyProperties(veiculoEntidade, historicoVeiculo);

        historicoVeiculo.setVeiculo(veiculoEntidade);
        historicoVeiculo.setId(null);
        historicoVeiculo.setAgregado(veiculoEntidade.getAgregado());
        historicoVeiculo.setUsuario(ambiente.getUsuarioLogado());
        historicoVeiculo.setDataAlteracao(ambiente.buscarDataAmbiente());
        repositorioHistoricoVeiculoDados.armazenar(historicoVeiculo);
    }
}