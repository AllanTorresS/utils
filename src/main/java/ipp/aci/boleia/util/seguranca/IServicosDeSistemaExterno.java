package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Usuario;

public interface IServicosDeSistemaExterno {
    Usuario obterUsuarioSistemaExterno(String client, String secret);
}
