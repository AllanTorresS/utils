package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.util.negocio.visao.dto.IModuloInternoAcessoDTO;

public interface IServicosDeModuloInterno {

    byte[] autenticar(IModuloInternoAcessoDTO sistema);

    Usuario obterUsuarioModuloInterno(String client, String secret);

    String obterTokenJwt(IModuloInternoAcessoDTO sistema);
}
