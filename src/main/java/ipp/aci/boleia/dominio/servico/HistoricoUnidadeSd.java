package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoUnidadeDados;
import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.historico.HistoricoUnidade;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade HistoricoUnidade.
 */
@Component
public class HistoricoUnidadeSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IUnidadeDados repositorio;
    
    @Autowired
    private IHistoricoUnidadeDados historicoUnidadeDados;

    /**
     * Armazena o histórico de uma unidade
     * @param unidade A unidade a ter seu historico armazenado
     * @return O historico armazenado
     */
    public HistoricoUnidade armazenar(Unidade unidade) {
        if(unidade.getId() != null) {
            Unidade dadosAntigos = repositorio.obterPorId(unidade.getId());
            if(dadosAntigos != null) {
                HistoricoUnidade historicoUnidade = new HistoricoUnidade();
                historicoUnidade.setUnidade(dadosAntigos);
                historicoUnidade.setDataHistorico(ambiente.buscarDataAmbiente());
                historicoUnidade.setExigeNotaFiscal(dadosAntigos.getExigeNotaFiscal());
                historicoUnidade.setLocalDestinoPadraoNfeUf(dadosAntigos.getLocalDestinoPadraoNfeUf());
                return historicoUnidadeDados.armazenar(historicoUnidade);
            }
        }
        return null;
    }
}
