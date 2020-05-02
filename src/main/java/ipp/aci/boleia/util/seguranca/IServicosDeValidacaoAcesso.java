package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.vo.ResultadoValidacaoAcessoVo;

public interface IServicosDeValidacaoAcesso {
    ResultadoValidacaoAcessoVo validarAcesso(Usuario usuario, String fingerprint, String tokenJwtRecebido);
}
