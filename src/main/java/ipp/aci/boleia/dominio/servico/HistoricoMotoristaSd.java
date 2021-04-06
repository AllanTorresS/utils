package ipp.aci.boleia.dominio.servico;


import ipp.aci.boleia.dados.IFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dados.IHistoricoMotoristaDados;
import ipp.aci.boleia.dominio.HistoricoMotorista;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Serviços de domínio da entidade {@link HistoricoMotorista}.
 *
 * @author allan.santos
 */
@Component
public class HistoricoMotoristaSd {

    @Autowired
    private IHistoricoMotoristaDados repositorioHistoricoMotoristaDados;

    /**
     * Cria o historico para um motorista
     *
     * @param motoristaEntidade a entidade que deve ter seu historico gravado
     * @param ambiente          contem as informações do usuario logado que realizou a alteração e data atual da alteração
     */
    public void armazenarHistoricoMotorista(Motorista motoristaEntidade, UtilitarioAmbiente ambiente) {

        HistoricoMotorista historicoMotorista = new HistoricoMotorista();

        BeanUtils.copyProperties(motoristaEntidade, historicoMotorista);

        historicoMotorista.setMotorista(motoristaEntidade);
        historicoMotorista.setUsuario(ambiente.getUsuarioLogado());
        historicoMotorista.setDataAlteracao(ambiente.buscarDataAmbiente());
        repositorioHistoricoMotoristaDados.armazenar(historicoMotorista);
    }


    /**
     * Cria o historico para varios motoristas
     *
     * @param motoristaEntidadeLista lista com as entidades que devem ter seu historico gravado
     * @param ambiente               contem as informações do usuario logado que realizou a alteração e data atual da alteração
     */
    public void armazenarHistoricoMotoristaLista(List<Motorista> motoristaEntidadeLista, UtilitarioAmbiente ambiente) {

        for (Motorista motorista : motoristaEntidadeLista) {
            motorista.setUnidade(null);
            this.armazenarHistoricoMotorista(motorista, ambiente);
        }

    }
}