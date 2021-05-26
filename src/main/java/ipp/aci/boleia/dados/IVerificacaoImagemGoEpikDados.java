package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.vo.RespostaValidacaoGoEpik;

/**
 * Contrato para implementacao de repositorio de verificacao de imgens na Api da GoEpik
 */
public interface IVerificacaoImagemGoEpikDados {

    /**
     * Envia foto tirada no app motorista para leitura OCR na api da GoEpik
     *
     * @param imageData código da imagem em Base64
     * @return valor da leitura da API
     */
    RespostaValidacaoGoEpik validarImagemContadorGoEpik(String imageData);

}
