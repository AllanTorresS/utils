package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
 * Representa um parametro de pesquisa para clausula SQL LIKE, case insensitive,
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaLike extends ParametroPesquisa {

	/**
	 * Construtor
	 * @param nome  O nome do parametro
	 * @param valor O valor desejado
	 */
	public ParametroPesquisaLike(String nome, String valor) {
		super(nome, valor);
	}

	@Override
	public Object getValor() {
		String valor = (String) super.getValor();
		valor = valor != null ? String.format("%%%s%%", valor.toLowerCase()) : null;
        valor = valor != null ? UtilitarioFormatacao.removerAcentos(valor) : null;
		return valor;
	}
}
