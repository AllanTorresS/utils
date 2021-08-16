package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade Empresa Agregada.
 */
@Component
public class EmpresaAgregadaSd {

    @Autowired
    private IEmpresaAgregadaDados repositorio;

    @Autowired
    private IMotoristaDados motoristaDados;

    @Autowired
    private IVeiculoDados veiculoDados;

    @Autowired
    private HistoricoMotoristaSd historicoMotoristaSd;

    @Autowired
    private FluxoAbastecimentoSd fluxoAbastecimentoSd;

    @Autowired
    private HistoricoVeiculoSd historicoVeiculoSd;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Exclui uma lista de empresas agregadas.
     *
     * @param ids Lista dos identificadores das empresas agregadas a serem excluídas.
     */
    public void excluir(Long... ids) {
        corrigirDadosRelacionados(ids);
        repositorio.excluir(ids);
    }

    /**
     * Obtem o número de registros relacionados
     *
     * @param id id do registro
     * @return Quantidade
     */
    public Long obterQuantidadeRelacionados(Long id) {
        EmpresaAgregada empresaAgregada = repositorio.obterPorId(id);
        return obterQuantidadeRelacionados(empresaAgregada);
    }

    /**
     * Obtem o número de registros relacionados
     *
     * @param empresaAgregada Empresa Agregada a ser usada na busca.
     * @return Quantidade
     */
    public Long obterQuantidadeRelacionados(EmpresaAgregada empresaAgregada) {
        long quantidade = 0L;
        quantidade += empresaAgregada.getVeiculos().size();
        quantidade += empresaAgregada.getMotoristas().size();

        return quantidade;
    }

    /**
     * Corrige as entidades relacionadas em relação a hierarquia da frota
     * (Motorista,Veiculo e Grupo Operacional)
     * @param id id da unidade
     */
    private void corrigirDadosRelacionados(Long... id) {
        for (Long empresasAgregadaseId : id) {
            EmpresaAgregada empresaAgregada = repositorio.obterPorId(empresasAgregadaseId);
            empresaAgregada.getMotoristas().forEach(n -> {
                motoristaDados.excluir(n.getId());
                historicoMotoristaSd.armazenarHistoricoMotorista(n, utilitarioAmbiente);
                fluxoAbastecimentoSd.excluirFluxoAbastecimentoMotorista(n,utilitarioAmbiente.getUsuarioLogado(),utilitarioAmbiente.buscarDataAmbiente());
            });
            empresaAgregada.getVeiculos().forEach(n -> {
                veiculoDados.excluir(n.getId());
                historicoVeiculoSd.armazenarHistoricoVeiculo(n,utilitarioAmbiente);
            });
        }
    }
}
