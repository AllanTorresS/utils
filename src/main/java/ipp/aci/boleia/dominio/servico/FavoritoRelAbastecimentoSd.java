package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFavoritoRelAbastecimentoDados;
import ipp.aci.boleia.dominio.FavoritoRelAbastecimento;
import ipp.aci.boleia.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Oferece a funcionalidade de controle dos campos selecionados na emissão do relatório de abastecimento
 */

@Component
public class FavoritoRelAbastecimentoSd {

    private static final String CAMPOS_SEPARADOR = ";";

    @Autowired
    private IFavoritoRelAbastecimentoDados repositorio;

    /**
     * Armazena a última lista de campos selecionados.
     *
     * @param campos os campos selecionados
     * @param usuarioLogado usuario logado no contexto
     */
    public void armazenar(int[] campos, Usuario usuarioLogado) {
        removerFavoritosCadastrado(usuarioLogado);
        repositorio.armazenar(new FavoritoRelAbastecimento(Arrays.stream(campos)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(CAMPOS_SEPARADOR)), usuarioLogado));
    }

    /**
     * Remove os últimos favoritos cadastrados.
     *
     * @param usuarioLogado usuario logado no contexto
     */
    private void removerFavoritosCadastrado(Usuario usuarioLogado) {
        FavoritoRelAbastecimento atual = repositorio.pesquisarPorUsuario(usuarioLogado.getId());
        if (atual != null) {
            repositorio.excluir(atual.getId());
        }
    }

}
