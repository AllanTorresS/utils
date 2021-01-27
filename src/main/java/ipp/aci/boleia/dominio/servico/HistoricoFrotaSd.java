package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IHistoricoFrotaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.historico.HistoricoFrota;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa as regras de negocio relacionadas a entidade Frota
 */
@Component
public class HistoricoFrotaSd {

    @Autowired
    private IFrotaDados repositorio;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IHistoricoFrotaDados historicoFrotaDados;

    /**
     * Armazena os dados de uma frota
     *
     * @param frota A frota  ser armazenada
     * @return A frota armazenada
     */
    public HistoricoFrota armazenar(Frota frota) {
        if (frota.getId() != null) {
            Frota dadosAntigos = repositorio.obterPorId(frota.getId());
            if (dadosAntigos != null) {
                HistoricoFrota historicoFrota = new HistoricoFrota();
                historicoFrota.setFrota(dadosAntigos);
                historicoFrota.setDataHistorico(ambiente.buscarDataAmbiente());
                historicoFrota.setExigeNotaFiscal(dadosAntigos.exigeNotaFiscal());
                historicoFrota.setLocalDestinoPadraoNfeUf(dadosAntigos.getLocalDestinoPadraoNfeUf());
                historicoFrota.setLembrarParametrizacaoNf(dadosAntigos.getLembrarParametrizacaoNf());
                return historicoFrotaDados.armazenar(historicoFrota);
            }
        }
        return null;
    }
}