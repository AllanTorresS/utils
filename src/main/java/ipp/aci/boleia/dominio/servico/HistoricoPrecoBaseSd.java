package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoPrecoBaseDados;
import ipp.aci.boleia.dominio.HistoricoPrecoBase;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade {@link ipp.aci.boleia.dominio.HistoricoPrecoBase}.
 *
 * */
@Component
public class HistoricoPrecoBaseSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IHistoricoPrecoBaseDados repositorio;

    /**
     * Armazena no histórico um novo registro de PrecoBase
     * @param precoBase A entidade que deve ter registro histórico armazenado
     * @return A entidade armazenada
     */
    public HistoricoPrecoBase armazenar(PrecoBase precoBase){
        HistoricoPrecoBase historico = new HistoricoPrecoBase();

        historico.setDataVigencia(precoBase.getDataAtualizacao());
        historico.setPreco(precoBase.getPreco());
        historico.setPrecoBase(precoBase);
        historico.setTipoCombustivel(precoBase.getPrecoMicromercado().getTipoCombustivel());
        historico.setUsuario(ambiente.getUsuarioLogado());

        return repositorio.armazenarSemIsolamentoDeDados(historico);
    }
}
