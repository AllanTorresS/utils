package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IPrecoBaseDados;
import ipp.aci.boleia.dados.IPrecoDados;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dados.IPrecoFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import ipp.aci.boleia.dominio.enums.StatusPrecoFrete;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Encapsula as regras de negocio que envolvem a manipulacao de PrecosBase
 */
@Component
public class PrecoFreteSd {

    @Autowired
    private IPrecoBaseDados repositorioPrecoBase;

    @Autowired
    private IPrecoDados repositorioPreco;

    @Autowired
    private IPrecoFreteDados repositorioFrete;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;


    /**
     * Propaga a exclusão do Preço Base
     * @param precoBase Preco base a propagar
     */
    public void propagarExclusao(PrecoBase precoBase){
        PrecoFrete precoFrete = repositorioFrete.obterPrecoFreteVigente(precoBase.getIdPontoVenda(), precoBase.getPrecoMicromercado().getTipoCombustivel().getId());
        if(precoFrete != null){
            excluir(precoFrete);
        }
    }

    /**
     * Exclui Preço frete
     * @param precoFrete preço a ser excluido
     */
    public void excluir(PrecoFrete precoFrete){
        precoFrete.setStatus(StatusPrecoFrete.HISTORICO.getValue());
        repositorioFrete.armazenar(precoFrete);
    }
}
