package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoFrotaPtovPrecoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaPtovPreco;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUltimosPrecosVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Serviços de domínio da entidade HistoricoFrotaPtovPrecoSd.
 */
@Component
public class HistoricoFrotaPtovPrecoSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IHistoricoFrotaPtovPrecoDados repositorio;

    /**
     * Armazena o histórico de uma parâmetro nota fiscal
     * @param preco O preço negociado a ter seu historico armazenado
     * @return O historico armazenado
     */
    public HistoricoFrotaPtovPreco armazenar(Preco preco){
        HistoricoFrotaPtovPreco historico = new HistoricoFrotaPtovPreco();

        historico.setPrecoNegociado(preco);
        historico.setPreco(preco.getPreco());
        historico.setDataVigencia(preco.getDataVigencia());
        historico.setDescontoSolicitado(preco.getDescontoSolicitado());
        historico.setDescontoVigente(preco.getDescontoVigente());
        historico.setTipoCombustivel(preco.getPrecoBase().getPrecoMicromercado().getTipoCombustivel());
        historico.setFrotaPtov(preco.getFrotaPtov());

        return repositorio.armazenarSemIsolamentoDeDados(historico);
    }

    /**
     * Realiza uma pesquisa paginada sobre o histórico de preços acordados entre uma frota e pv
     * @param filtro Filtro de pesquisa
     * @param usuario Usuario usuário logado no sistema
     * @return Os registros de histórico de preço encontrados
     */
    public List<HistoricoFrotaPtovPreco> pesquisar(FiltroPesquisaUltimosPrecosVo filtro, Usuario usuario){
        return repositorio.pesquisar(filtro, usuario);
    }
}
