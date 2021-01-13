package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IHistoricoEmpresaAgregadaDados;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.historico.HistoricoEmpresaAgregada;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade HistoricoEmpresaAgregada.
 */
@Component
public class HistoricoEmpresaAgregadaSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IEmpresaAgregadaDados repositorio;

    @Autowired
    private IHistoricoEmpresaAgregadaDados empresaAgregadaDados;

    /**
     * Armazena os dados de historico de uma empresa agregada
     * @param empresaAgregada A empresa agregada a ter seu historico armazenado
     * @return O historico da empresa agregada armazenada
     */
    public HistoricoEmpresaAgregada armazenar(EmpresaAgregada empresaAgregada) {
        if(empresaAgregada.getId() != null) {
            EmpresaAgregada dadosAntigos = repositorio.obterPorId(empresaAgregada.getId());
            if(dadosAntigos != null) {
                HistoricoEmpresaAgregada historicoEmpresaAgregada = new HistoricoEmpresaAgregada();
                historicoEmpresaAgregada.setEmpresaAgregada(dadosAntigos);
                historicoEmpresaAgregada.setDataHistorico(ambiente.buscarDataAmbiente());
                historicoEmpresaAgregada.setExigeNotaFiscal(dadosAntigos.getExigeNotaFiscal());
                return empresaAgregadaDados.armazenar(historicoEmpresaAgregada);
            }
        }
        return null;
    }
}
