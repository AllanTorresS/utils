package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ISaldoFrotaDados;
import ipp.aci.boleia.dominio.SaldoFrota;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaSaldoFrotaVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades TransacaoFrota
 */
@Repository
public class OracleSaldoFrotaDados extends OracleRepositorioBoleiaDados<SaldoFrota> implements ISaldoFrotaDados {

    /**
     * Instancia o repositorio
     */
    public OracleSaldoFrotaDados() {
        super(SaldoFrota.class);
    }


    @Override
    public SaldoFrota obterSaldoPorIdFrota(Long idFrota) {
        return pesquisarUnico(new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public ResultadoPaginado<SaldoFrota> pesquisar(FiltroPesquisaSaldoFrotaVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisaGrid(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Adiciona os parâmetros de pesquisa à query
     *
     * @param filtro o filtro a ser aplicado
     * @return Uma lista de parâmetros de pesquisa
     */
    private List<ParametroPesquisa> criarParametrosPesquisaGrid(FiltroPesquisaSaldoFrotaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getFrota() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }
        if (filtro.getIdsFrotas() != null) {
            parametros.add(new ParametroPesquisaIn("frota.id", filtro.getIdsFrotas()));
        }
        if (filtro.getModalidadePagamento() != null && filtro.getModalidadePagamento().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.modoPagamento", ModalidadePagamento.valueOf(filtro.getModalidadePagamento().getName()).getValue()));
        }
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.status", StatusFrota.valueOf(filtro.getStatus().getName()).getValue()));
        }
        if (StringUtils.isNotBlank(filtro.getCidade())) {
            parametros.add(new ParametroPesquisaLike("frota.municipio", filtro.getCidade()));
        }
        if (filtro.getUf() != null && filtro.getUf().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.unidadeFederativa", filtro.getUf().getName()));
        }
        if (filtro.isPossuiLimite() != null && filtro.isPossuiLimite()) {
            parametros.add(new ParametroPesquisaNulo("frota.saldo.limiteCredito", true));
        }

        return parametros;
    }
}
