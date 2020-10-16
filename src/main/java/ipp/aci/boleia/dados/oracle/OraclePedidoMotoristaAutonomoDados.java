package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IPedidoMotoristaAutonomoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades Motorista Autonomo
 */
@Repository
public class OraclePedidoMotoristaAutonomoDados extends OracleRepositorioBoleiaDados<Pedido> implements IPedidoMotoristaAutonomoDados {
    private static final String QUERY_EXCLUSAO_PEDIDO = "DELETE FROM Pedido p " +
            "WHERE p.id in " +
            "(SELECT pedido.id " +
            "FROM Pedido pedido " +
            "JOIN pedido.motorista motorista " +
            "WHERE motorista.id = :idMotorista) ";

    /**
     * Instancia o repositorio
     *
     */
    public OraclePedidoMotoristaAutonomoDados() {
        super(Pedido.class);
    }


    @Override
    public Pedido obterPorNumero(String numero) {
        return pesquisarUnico(new ParametroPesquisaIgual("numero", numero));
    }

    @Override
    public ResultadoPaginado<Pedido> obterPorCpf(String cpf, int pagina, int tamanho) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(pagina);
        paginacao.setTamanhoPagina(tamanho);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataCriacao", Ordenacao.DECRESCENTE));
        return pesquisar(paginacao, new ParametroPesquisaIgual("motorista.cpf", cpf));
    }

    @Override
    public void excluirPedidoPorMotorista(MotoristaAutonomo motoristaAutonomo) {
        Query query = getGerenciadorDeEntidade().createQuery(QUERY_EXCLUSAO_PEDIDO);
        query.setParameter("idMotorista", motoristaAutonomo.getId());
        query.executeUpdate();
    }


    @Override
    public List<Pedido> obterPorCpfDataExpiracao(String cpf, Date dataExpiracao) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id"),
                new ParametroPesquisaIgual("motorista.cpf", cpf),
                new ParametroPesquisaDataMaior("dataExpiracao", dataExpiracao));
    }
}
