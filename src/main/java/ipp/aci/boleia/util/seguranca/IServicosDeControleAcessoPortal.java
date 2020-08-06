package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.enums.TipoAcesso;
import ipp.aci.boleia.util.negocio.visao.dto.IAcessoDTO;

public interface IServicosDeControleAcessoPortal {
    boolean precisaRecaptchaAcesso(IAcessoDTO dadosAcesso, TipoAcesso tipoAcesso);
    boolean precisaRecaptchaAcesso(String login, TipoAcesso tipoAcesso);
    void registrarTentativaAcesso(String username, TipoAcesso tipoAcesso, boolean acessoInterno);
    void resetarControleAcesso(String username, TipoAcesso tipoAcesso, boolean acessoInterno);
}
