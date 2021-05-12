package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.TagConectcar;
import ipp.aci.boleia.dominio.Veiculo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Encapsula as regras de negocio de TagConectcar
 */
@Component
public class TagConectcarSd {

	/**
	 * Obtém o veículo mais recente associado a uma determinada tag.
	 * 
	 * @param tagConectcar o objeto do tipo TagConectcar
	 * @return veiculo o veiculo mais recente associado a tag
	 */
    public Veiculo getVeiculoMaisRecente(TagConectcar tagConectcar) {
    	Veiculo veiculoMaisRecente = null;
		
    	if(tagConectcar != null) {
			Date dataMaisRecente = null;
			List<Veiculo> veiculos = tagConectcar.getVeiculos();
			
			if (veiculos != null && !veiculos.isEmpty()) {
				for (Veiculo veiculo : veiculos) {
					if (veiculoMaisRecente == null) {
						veiculoMaisRecente = veiculo;
						if (veiculo.getDataAtualizacao() != null && veiculo.getDataAtualizacao().after(veiculo.getDataCriacao())) {
							dataMaisRecente = veiculo.getDataAtualizacao();
						} else {
							dataMaisRecente = veiculo.getDataCriacao();
						}
					} else {
						if (veiculo.getDataCriacao().after(dataMaisRecente)) {
							veiculoMaisRecente = veiculo;
							dataMaisRecente = veiculo.getDataCriacao();
						}
						if (veiculo.getDataAtualizacao() != null && veiculo.getDataAtualizacao().after(dataMaisRecente)) {
							veiculoMaisRecente = veiculo;
							dataMaisRecente = veiculo.getDataAtualizacao();
						}
					}
				}
			}
		}
		return veiculoMaisRecente;
	}
    
}
