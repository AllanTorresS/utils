package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * IValidadorInscricaoEstadual - Interface que define assinatura
 * de validador de inscrição estadual. 
 */
public abstract class ValidadorInscricaoEstadualGenerico {

    /**
     * Valida a inscricao estadual
     *
     * @param inscricaoEstadual a inscricao a ser validada
     * @return true caso valida
     */
    public abstract boolean validar(String inscricaoEstadual);

    /**
     * Metodo abstrato para obter a soma de pesos de inscrições similares
     * @param inscricaoEstadual a inscricao a obter a soma de pesos
     * @return soma de pesos
     */
    protected int obterSomaPeso(String inscricaoEstadual) {
        int soma = 0;
        int peso = 9;
        for (int i = 0; i < inscricaoEstadual.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(inscricaoEstadual.charAt(i))) * peso;
            peso--;
        }
        return soma;
    }

    /**
     * Metodo de pre validacao para quantidade de digitos da inscricao
     * @param inscricaoEstadual a inscricao a validar
     * @param digitos O numero de digitos esperado
     * @return true para quantidade valida
     */
    protected Boolean validarDigitos(String inscricaoEstadual, Integer digitos) {
        return inscricaoEstadual.length() == digitos;
    }

}