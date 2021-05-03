package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IMotivoAlteracaoStatusFrotaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ClassificacaoStatusFrota;
import ipp.aci.boleia.dominio.enums.StatusVigenciaAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.enums.TipoAlteracaoStatusFrota;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Implementa as regras de negocio relacionadas a entidade {@link ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota}
 */
@Component
public class MotivoAlteracaoStatusFrotaSd {

    @Autowired
    private IMotivoAlteracaoStatusFrotaDados repositorio;

    @Autowired
    private IFrotaDados repositorioFrota;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Cria um motivo de alteração de status de frota
     *
     * @param frota A frota a ter um motivo associado
     * @param tipoAlteracao O tipo de alteração (ativação ou inativação)
     * @param classificacao O tipo de motivo da inativação
     * @param justificativa A justificativa da alteração de status
     * @param prazoAlteracaoTemporaria Se uma alteração temporária, corresponde à sua data de término, caso contrário, terá valor nulo
     * @param ultimoMotivoDefinitivo O último motivo definitivo associado à frota
     * @return O motivo criado
     */
    public MotivoAlteracaoStatusFrota criarMotivoAlteracaoStatus(Frota frota, TipoAlteracaoStatusFrota tipoAlteracao,
                                                                 ClassificacaoStatusFrota classificacao,
                                                                 String justificativa, Date prazoAlteracaoTemporaria,
                                                                 MotivoAlteracaoStatusFrota ultimoMotivoDefinitivo) {
        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        Date dataAmbiente = ambiente.buscarDataAmbiente();

        MotivoAlteracaoStatusFrota motivo = new MotivoAlteracaoStatusFrota();
        motivo.setFrota(frota);
        motivo.setDataCriacao(dataAmbiente);
        motivo.setMotivo(justificativa);
        motivo.setUsuario(usuarioLogado);
        motivo.setTipoMotivo(TipoAlteracaoStatusFrota.ATIVACAO.equals(tipoAlteracao) ? null : classificacao.getValue());
        motivo.setStatusVigenciaAlteracao(StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue());
        motivo.setTipoAlteracaoStatus(tipoAlteracao.getValue());
        if(prazoAlteracaoTemporaria != null) {
            motivo.setDataInicio(dataAmbiente);
            motivo.setDataFim(prazoAlteracaoTemporaria);
        }
        if(ultimoMotivoDefinitivo != null) {
            motivo.setUltimoMotivoDefinitivo(ultimoMotivoDefinitivo);
        }

        return repositorio.armazenar(motivo);
    }

    /**
     * Altera a vigência de motivos de alteração anteriores a um novo motivo de alteração definitivo
     * @param frota A frota
     */
    public List<MotivoAlteracaoStatusFrota> alterarVigenciaMotivosAnteriores(Frota frota) {
        List<MotivoAlteracaoStatusFrota> motivosAlteracaoStatus = frota.getMotivosAlteracaoStatus();
        motivosAlteracaoStatus.forEach(motivo -> motivo.setStatusVigenciaAlteracao(StatusVigenciaAlteracaoStatusFrota.NAO_VIGENTE.getValue()));
        return repositorio.armazenarLista(motivosAlteracaoStatus);
    }
}
