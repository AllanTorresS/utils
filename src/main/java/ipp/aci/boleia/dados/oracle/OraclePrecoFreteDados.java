package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoFreteDados;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoFreteVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Preco Frete
 */
@Repository
public class OraclePrecoFreteDados extends OracleRepositorioBoleiaDados<PrecoFrete> implements IPrecoFreteDados {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Instancia o repositorio
     */
    public OraclePrecoFreteDados() {
        super(PrecoFrete.class);
    }

    @Override
    public PrecoFrete obterPrecoFreteVigente(PontoDeVenda pontoDeVenda, TipoCombustivel combustivel) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE),
                new ParametroPesquisaNulo("dataExclusao"),
                new ParametroPesquisaIgual("posto.id", pontoDeVenda.getId()),
                new ParametroPesquisaIgual("combustivel.id", combustivel.getId())
        ).stream().findFirst().orElse(null);
    }

    /**
     * Realiza uma pesquisa com paginação de uma servicos a partir dos parametros informados
     *
     */
    public ResultadoPaginado<PrecoFrete> pesquisar(FiltroPesquisaPrecoFreteVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        filtro.getPaginacao().getParametrosOrdenacaoColuna().add( new ParametroOrdenacaoColuna("dataVigencia", Ordenacao.DECRESCENTE));
        filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("posto.id"));

        //Tipo Combustível
        if (filtro.getTipoCombustivel() != null) {
            parametros.add(new ParametroPesquisaIgual("combustivel.id", filtro.getTipoCombustivel().getId()));
        }
        //Ponto de Venda
        if (filtro.getPontoVenda() != null) {
            parametros.add(new ParametroPesquisaIgual("posto.id", filtro.getPontoVenda().getId()));
        }
        //Data Vigência
        if (filtro.getDataVigencia() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataVigencia", utilitarioAmbiente.buscarDataAmbiente()));
        }
        //Status
        if (filtro.getStatus() != null) {
            new ParametroPesquisaIgual("status", filtro.getStatus());
        }

        return pesquisar(
                filtro.getPaginacao(),
                parametros.toArray(new ParametroPesquisa[parametros.size()])
        );
    }
}