package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IUsuarioMotoristaDados;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUsuarioMotoristaVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioFormatacao.obterLongMascara;

/**
 * Respositorio de entidades de
 * UsuarioMotorista
 */
@Repository
public class OracleUsuarioMotoristaDados extends OracleRepositorioBoleiaDados<UsuarioMotorista> implements IUsuarioMotoristaDados {

    /**
     * Instancia o repositório
     */
    public OracleUsuarioMotoristaDados() {
        super(UsuarioMotorista.class);
    }

    @Override
    public UsuarioMotorista obterPorUsuario(Usuario usuario) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("usuario.id", usuario.getId()));
    }

    @Override
    public ResultadoPaginado<UsuarioMotorista> pesquisaPaginada(FiltroPesquisaUsuarioMotoristaVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroIgual("motoristas.frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        if (filtro.getNome() != null) {
            parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaLike("usuario.nome", filtro.getNome()),
                    new ParametroPesquisaLike("motoristas.apelido", filtro.getNome()))
            );
        }
        povoarParametroIgual("usuario.cpf", obterLongMascara(filtro.getCpf()), parametros);
        if (filtro.getClassificacao() != null && filtro.getClassificacao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("motoristas.agregado", ClassificacaoAgregado.valueOf(filtro.getClassificacao().getName()).getValue()));
        }
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("motoristas.unidade", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("motoristas.unidade"));
            }
        }
        povoarParametroIgual("motoristas.grupo", filtro.getGrupo() != null ? filtro.getGrupo().getId() : null, parametros);
        povoarParametroIgual("motoristas.empresaAgregada", filtro.getEmpresaAgregada() != null ? filtro.getEmpresaAgregada().getId() : null, parametros);
        if (filtro.getStatusNaFrota() != null && filtro.getStatusNaFrota().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("motoristas.status", StatusAtivacao.valueOf(filtro.getStatusNaFrota().getName()).getValue()));
        }

        if(filtro.getPaginacao() != null && (filtro.getPaginacao().getParametrosOrdenacaoColuna() == null || filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty())){
            filtro.getPaginacao().setParametrosOrdenacaoColuna(Arrays.asList(new ParametroOrdenacaoColuna("usuario.nome")));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<UsuarioMotorista> obterUsuariosMotoristas(Usuario usuario) {
        return this.pesquisar(new ParametroOrdenacaoColuna(), new ParametroPesquisa[]{new ParametroPesquisaIgual("usuario", usuario)});
    }
}
