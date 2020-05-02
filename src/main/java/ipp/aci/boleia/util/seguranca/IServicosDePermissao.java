package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;

import java.util.List;

public interface IServicosDePermissao {

    List<Permissao> obterPermissoes(TipoPerfilUsuario tipoPerfil, Usuario usuario);
    List<Permissao> obterPermissoes(TipoPerfilUsuario tipoPerfil);
}
