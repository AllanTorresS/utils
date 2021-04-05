package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.*;
import ipp.aci.boleia.dominio.*;
import ipp.aci.boleia.dominio.agenciadorfrete.*;
import ipp.aci.boleia.dominio.enums.*;
import ipp.aci.boleia.util.excecao.*;
import ipp.aci.boleia.util.i18n.*;
import ipp.aci.boleia.util.negocio.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.*;

/**
 * Encapsula as regras de negocio que envolvem a manipulacao de PrecosBase
 */
@Component
public class PrecoFreteSd {

    @Autowired
    private PrecoNegociadoSd precoNegociadoSd;

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
        excluir(precoFrete);
    }

    /**
     * Exclui Preço frete
     * @param precoFrete preço a ser excluido
     */
    public void excluir(PrecoFrete precoFrete){
        Date dataAtualizacao = utilitarioAmbiente.buscarDataAmbiente();
        precoFrete.setStatus(StatusPreco.HISTORICO.getValue());
        repositorioFrete.armazenar(precoFrete);
    }







}
