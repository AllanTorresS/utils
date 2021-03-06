package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteTransacaoDados;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioTransacaoVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades Transacao do Agenciador de frete
 */

@Repository
public class OracleAgenciadorFreteTransacaoDados extends OracleRepositorioBoleiaDados<Transacao> implements IAgenciadorFreteTransacaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleAgenciadorFreteTransacaoDados() {
        super(Transacao.class);
    }

    @Override
    public List<Transacao> obterPorPedido(String cpfMotorista, String numeroPedido) {
        return pesquisar(new ParametroOrdenacaoColuna("dataCriacao"),
                new ParametroPesquisaIgual("motorista.cpf", cpfMotorista),
                new ParametroPesquisaIgual("pedido.numero", numeroPedido));
    }

    @Override
    public List<Transacao> obterPorPedido(String numeroPedido) {
        return pesquisar(new ParametroOrdenacaoColuna("dataCriacao"),
                new ParametroPesquisaIgual("pedido.numero", numeroPedido));
    }

    @Override
    public ResultadoPaginado<Transacao> obterPorCpf(String cpfMotorista, Integer pagina, Integer tamanho) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(pagina);
        paginacao.setTamanhoPagina(tamanho);
        return pesquisar(paginacao, new ParametroPesquisaIgual("motorista.cpf", cpfMotorista));
    }

    @Override
    public List<Transacao> obterTransacao(PontoDeVenda pontoDeVenda, MotoristaAutonomo motorista, String placaVeiculo, StatusAutorizacao status, BigDecimal litragem, BigDecimal precoCombustivel) {
        return pesquisar(new ParametroOrdenacaoColuna("dataCriacao"),
                new ParametroPesquisaIgual("posto.id", pontoDeVenda.getId()),
                new ParametroPesquisaIgual("motorista.id", motorista.getId()),
                new ParametroPesquisaIgual("placaVeiculo", placaVeiculo),
                new ParametroPesquisaIgual("status", status.getValue()),
                new ParametroPesquisaIgual("abastecimento.litragem", litragem),
                new ParametroPesquisaIgual("abastecimento.precoCombustivel", precoCombustivel));
    }

    @Override
    public ResultadoPaginado<Transacao> pesquisar(FiltroRelatorioTransacaoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getDe()!= null) {
            Date data = filtro.getDe();
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataCriacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(data)));
        }
        if (filtro.getAte() != null) {
            Date data = filtro.getAte();
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataCriacao", UtilitarioCalculoData.obterUltimoInstanteDia(data)));
        }
        if (filtro.getIdsPontoVenda() != null) {
            parametros.add(new ParametroPesquisaIn("posto.id", filtro.getIdsPontoVenda()));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

}
