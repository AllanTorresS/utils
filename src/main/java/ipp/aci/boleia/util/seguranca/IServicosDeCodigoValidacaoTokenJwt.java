package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.CodigoValidacaoTokenJwt;
import ipp.aci.boleia.dominio.Usuario;

public interface IServicosDeCodigoValidacaoTokenJwt {

    CodigoValidacaoTokenJwt registrarNovoCodigoParaUsuario(Usuario usuario);

}
