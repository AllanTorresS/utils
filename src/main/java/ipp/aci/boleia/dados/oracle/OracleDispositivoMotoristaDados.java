package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDispositivoMotoristaDados;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDispositivoMotoristaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades DispositivoMotorista
 */
@Repository
public class OracleDispositivoMotoristaDados extends OracleRepositorioBoleiaDados<DispositivoMotorista> implements IDispositivoMotoristaDados {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Instancia o repositorio
     */
    public OracleDispositivoMotoristaDados() {
        super(DispositivoMotorista.class);
    }

    @Override
    public ResultadoPaginado<DispositivoMotorista> pesquisar(FiltroPesquisaDispositivoMotoristaVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroLike("motorista.nome", filtro.getNome(), parametros);
        povoarParametroIgual("motorista.cpf", UtilitarioFormatacao.obterLongMascara(filtro.getCpf()), parametros);
        povoarParametrosStatusHabilitacao(filtro, parametros);
        povoarParametroIgual("motorista.grupo", filtro.getGrupo() != null ? filtro.getGrupo().getId() : null, parametros);
        povoarParametroIgual("motorista.empresaAgregada", filtro.getEmpresaAgregada() != null ? filtro.getEmpresaAgregada().getId() : null, parametros);
        povoarParametroIgual("frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametroNulo("tokenAllowMe", filtro.getSemAllowMe(), false, parametros);
        povoarParametroNulo("idAllowMe", filtro.getSemAllowMe(), false, parametros);

        if (filtro.getStatusBloqueio() != null && filtro.getStatusBloqueio().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusBloqueio", StatusBloqueio.valueOf(filtro.getStatusBloqueio().getName()).getValue()));
        }

        if (filtro.getClassificacao() != null && filtro.getClassificacao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("motorista.agregado", ClassificacaoAgregado.valueOf(filtro.getClassificacao().getName()).getValue()));
        }

        povoarParametroPesquisaUnidade(filtro, parametros);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<DispositivoMotorista> obterPorMotorista(Long idMotorista) {
        return pesquisar((ParametroOrdenacaoColuna) null,new ParametroPesquisaIgual("motorista", idMotorista));
    }

    @Override
    public List<DispositivoMotorista> obterPorMotorista(Motorista motorista) {
        return pesquisar(new ParametroOrdenacaoColuna(),
                new ParametroPesquisaIgual("motorista", motorista));
    }

    @Override
    public List<DispositivoMotorista> obterTodosDispositivosPorCpf(Long cpf) {
        return pesquisar(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("motorista.cpf", cpf));
    }
    @Override
    public void excluirPorMotoristas(Long... idsMotorista) {
        if(idsMotorista != null) {
            List<DispositivoMotorista> dispositivos = pesquisar((ParametroOrdenacaoColuna) null,
                    new ParametroPesquisaIn("motorista", Arrays.asList(idsMotorista)),
                    new ParametroPesquisaEmpty("pedidos"));
            if(dispositivos != null) {
                dispositivos.forEach(d -> excluir(d.getId()));
            }
        }
    }

    @Override
    public DispositivoMotorista obterPorCpfCelularToken(String cpf, String celular, String token) {
        Long cpfConvertido = UtilitarioFormatacao.obterLongMascara(cpf);
        Integer dddTelefoneCelular = UtilitarioFormatacao.obterInteiroMascara(celular.substring(0, 2));
        Integer telefoneCelular = UtilitarioFormatacao.obterInteiroMascara(celular.substring(2));

        return pesquisarUnico(new ParametroPesquisaIgual("motorista.cpf", cpfConvertido), new ParametroPesquisaIgual("motorista.dddTelefoneCelular", dddTelefoneCelular),
                new ParametroPesquisaIgual("motorista.telefoneCelular", telefoneCelular), new ParametroPesquisaIgual("token", token));
    }

    @Override
    public DispositivoMotorista obterPorIdUdidToken(Long id, String udid, String token) {
        return pesquisarUnico(new ParametroPesquisaIgual("id", id), new ParametroPesquisaIgual("udid", udid),
                new ParametroPesquisaIgual("token", token));
    }

    @Override
    public List<Long> obterPendentesCadastroAllowMe() {
        FiltroPesquisaDispositivoMotoristaVo filtro = new FiltroPesquisaDispositivoMotoristaVo();
        filtro.setSemAllowMe(true);
        filtro.setPaginacao(new InformacaoPaginacao(1, 5));
        List<DispositivoMotorista> resultado = pesquisar(filtro).getRegistros();
        return resultado.stream().map(DispositivoMotorista::getId).collect(Collectors.toList());
    }

    @Override
    public DispositivoMotorista obterPorCpfFrota(Long cpf, Long idFrota) {
        return pesquisarUnico(new ParametroPesquisaIgual("motorista.cpf", cpf), new ParametroPesquisaIgual("motorista.frota.id", idFrota));
    }

    @Override
    public DispositivoMotorista obterPorCpfFrotaSemIsolamento(Long cpf, Long idFrota) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("motorista.cpf", cpf), new ParametroPesquisaIgual("motorista.frota.id", idFrota));
    }


    /**
     * Povoa o parametro de consulta referente ao status de habilitacoa do dispositivo
     * @param filtro O filtro de pesquisa
     * @param parametros A lista corrente de parametros
     */
    private void povoarParametrosStatusHabilitacao(FiltroPesquisaDispositivoMotoristaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getStatusHabilitacao() != null && filtro.getStatusHabilitacao().getName() != null) {
            StatusHabilitacao statusFiltro = StatusHabilitacao.valueOf(filtro.getStatusHabilitacao().getName());
            if (StatusHabilitacao.HABILITADO.equals(statusFiltro)) {
                parametros.add(new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacao.HABILITADO.getValue()));
            } else if (StatusHabilitacao.EXPIRADO.equals(statusFiltro)) {
                parametros.add(new ParametroPesquisaOr(new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacao.EXPIRADO.getValue()),
                        new ParametroPesquisaAnd(
                                new ParametroPesquisaDataMenorOuIgual("dataExpiracaoToken", utilitarioAmbiente.buscarDataAmbiente()),
                                new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacao.NAO_HABILITADO.getValue()))));
            } else if (StatusHabilitacao.NAO_HABILITADO.equals(statusFiltro)) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaAnd(new ParametroPesquisaDataMaiorOuIgual("dataExpiracaoToken", utilitarioAmbiente.buscarDataAmbiente()),
                                new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacao.NAO_HABILITADO.getValue())),
                        new ParametroPesquisaIgual("statusHabilitacao", StatusHabilitacao.REGERADO.getValue())));
            }
        }
    }

    /**
     * Povoa os parametros de pesquisa referentes a unidade do motorista
     * @param filtro filtro de pesquisa do dispositivo motorista
     * @param parametros lista de parametro de pesquisa para a consulta
     */
    private void povoarParametroPesquisaUnidade(FiltroPesquisaDispositivoMotoristaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0L) {
                parametros.add(new ParametroPesquisaIgual("motorista.unidade", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("motorista.unidade"));
            }
        }
    }
}
