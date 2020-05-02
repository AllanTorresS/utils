package ipp.aci.boleia.dominio.vo.frotista;

import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Representa um resultado paginado de pesquisa
 *
 * @param <T> A classe dos registros obtidos
 */
public class ResultadoPaginadoFrtVo<T> extends ResultadoPaginado<T> {

    private int pagina;
    private int tamanhoPagina;

    /**
     * Construtor padrão, necessário para a serializacao.
     */
    public ResultadoPaginadoFrtVo() {

    }

    /**
     * Método que cria uma resultado paginado frotista a partir de um resultado paginado.
     * 
     * @param resultadoPaginado Resultado paginado.
     * @param pagina Página atual.
     * @param tamanhoPagina Tamanho da página do serviço em registro.
     */
    public ResultadoPaginadoFrtVo(ResultadoPaginado<T> resultadoPaginado, int pagina, int tamanhoPagina) {
        super(resultadoPaginado.getRegistros(), resultadoPaginado.getTotalItems());
        setObservacoes(resultadoPaginado.getObservacoes());
        this.pagina = pagina;
        this.tamanhoPagina = tamanhoPagina;
    }

    /**
     * Método que cria uma resultado paginado frotista a partir de um resultado paginado.
     * 
     * @param registros A lista de registros
     * @param totalItems O total de itens disponíveis na consulta.
     * @param pagina Página atual.
     * @param tamanhoPagina Tamanho da página do serviço em registro.
     */
    public ResultadoPaginadoFrtVo(List<T> registros, int totalItems, int pagina, int tamanhoPagina) {
        super(registros, totalItems);
        this.pagina = pagina;
        this.tamanhoPagina = tamanhoPagina;
    }

    @Override
    public <R> ResultadoPaginadoFrtVo<R> converter(Function<T, R> mapper) {
        List<R> lista = this.getRegistros()
            .stream()
            .map(mapper)
            .collect(Collectors.toList());
        return new ResultadoPaginadoFrtVo<>(lista, getTotalItems(), pagina, tamanhoPagina);
        
    }

	/**
	 * @return O número da página atual começando em 1
	 */
	public int getPagina() {
		return pagina;
	}

	/**
	 * @param pagina O número da página atual começando em 1
	 */
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return O tamanho da pagína em registros.
	 */
	public int getTamanhoPagina() {
		return tamanhoPagina;
	}

	/**
	 * @param tamanhoPagina O tamanho da pagína em registros.
	 */
	public void setTamanhoPagina(int tamanhoPagina) {
		this.tamanhoPagina = tamanhoPagina;
	}

}