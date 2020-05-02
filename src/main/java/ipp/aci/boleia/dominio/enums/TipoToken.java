package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.dominio.ComandaDigital;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.DispositivoMotoristaPedido;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Tipo de token a ser obtido por integração
 */
public enum TipoToken {

    APP_MOTORISTA(6, 720, DispositivoMotorista.class, "token", false, true),
    APP_COMANDA_DIGITAL(6, 720, ComandaDigital.class, "token", true, false),
    RECUPERAR_SENHA_USUARIO(50, 1440, Usuario.class, "token", true, false),
    SOFT_TOKEN_AUTORIZACAO(6, 10, Motorista.class, "token", false, false),
    DISPOSITIVO_MOTORISTA_TOKEN_PEDIDO(8, 30, DispositivoMotoristaPedido.class, "numero", false, false),
    SENHA_MOTORISTA_SEM_APP(6, null, Motorista.class, "senhaAbastecimentoPdv", false, false);

    private final Integer tamanho;
    private final Integer minutosExpiracao;
    private final Class classeEntidade;
    private final String campoClasse;
    private final Boolean alfanumerico;
    private final Boolean unico;

    TipoToken(Integer tamanho, Integer minutosExpiracao, Class<? extends IPersistente> classeEntidade, String campoClasse, Boolean alfanumerico, Boolean unico) {
        this.tamanho = tamanho;
        this.minutosExpiracao = minutosExpiracao;
        this.classeEntidade = classeEntidade;
        this.campoClasse = campoClasse;
        this.alfanumerico = alfanumerico;
        this.unico = unico;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public Integer getMinutosExpiracao() {
        return minutosExpiracao;
    }

    public Class getClasseEntidade() {
        return classeEntidade;
    }

    public String getCampoClasse() {
        return campoClasse;
    }

    public Boolean getAlfanumerico() {
        return alfanumerico;
    }

    public Boolean getUnico() {
        return unico;
    }
}
