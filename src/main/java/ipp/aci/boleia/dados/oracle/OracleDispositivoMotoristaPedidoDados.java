package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IDispositivoMotoristaPedidoDados;
import ipp.aci.boleia.dominio.DispositivoMotoristaPedido;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.PedidoStatusAutorizacaoVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades DispositivoMotoristaPedido
 */
@Repository
public class OracleDispositivoMotoristaPedidoDados extends OracleRepositorioBoleiaDados<DispositivoMotoristaPedido> implements IDispositivoMotoristaPedidoDados {

    private static final String QUERY_COMBUSTIVEIS_UTILIZADOS_EM_PEDIDOS =
            "SELECT tipoCombustivel.id, MAX(dataCriacao) FROM DispositivoMotoristaPedido " +
            "WHERE motorista.id = :idMotorista AND veiculo.id = :idVeiculo " +
            "GROUP BY tipoCombustivel.id ORDER BY MAX(dataCriacao) DESC";

    private static final String QUERY_PEDIDO_COM_STATUS_AUTORIZACAO =
            "SELECT new ipp.aci.boleia.dominio.vo.PedidoStatusAutorizacaoVo(ab.pedido.numero, ab.status, ab.motivoRecusa, ab.pedido.dataExpiracao) " +
            "FROM AutorizacaoPagamento ab WHERE ab.pedido.numero = :numero ORDER BY ab.dataRequisicao DESC";

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Instancia o repositorio
     */
    public OracleDispositivoMotoristaPedidoDados() {
        super(DispositivoMotoristaPedido.class);
    }

    @Override
    public DispositivoMotoristaPedido pesquisarAtivoPorNumeroPedido(String numero) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("numero", numero));
        parametros.add(new ParametroPesquisaDataMaior("dataExpiracao", utilitarioAmbiente.buscarDataAmbiente()));
        parametros.add(new ParametroPesquisaIgual("habilitado", StatusAtivacao.ATIVO.getValue()));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PedidoStatusAutorizacaoVo> pesquisarStatusPorNumeroPedido(String numero) {
        return pesquisar(QUERY_PEDIDO_COM_STATUS_AUTORIZACAO, PedidoStatusAutorizacaoVo.class, new ParametroPesquisaIgual("numero", numero));
    }

    @Override
    public List<DispositivoMotoristaPedido> pesquisarAtivosPorMotorista(Motorista motorista) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("motorista",motorista));
        parametros.add(new ParametroPesquisaDataMaior("dataExpiracao", utilitarioAmbiente.buscarDataAmbiente()));
        parametros.add(new ParametroPesquisaIgual("habilitado", StatusAtivacao.ATIVO.getValue()));
        return pesquisar(new ParametroOrdenacaoColuna(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

	@Override
	public List<Long> buscarCombustiveisPedidosAnteriores(Long idMotorista, Long idVeiculo) {
        Query query = getGerenciadorDeEntidade().createQuery(QUERY_COMBUSTIVEIS_UTILIZADOS_EM_PEDIDOS);
        query.setParameter("idMotorista", idMotorista);
        query.setParameter("idVeiculo", idVeiculo);
        List<Object[]> result = query.getResultList();
        return result.stream().map(arr -> (Long) arr[0]).collect(Collectors.toList());
	}

}
