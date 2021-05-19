package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ChamadoTipo;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialPtovVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaPontoVendaServicosVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ChamadoTipo
 */
public interface IChamadoTipoDados extends IRepositorioBoleiaDados<ChamadoTipo> {

    /**
     * Pesquisa os tipos de chamado com base no perfil
     *
     * @param tipoPerfil tipo perfil do usuario
     * @return Uma lista dos tipos de chamado.
     */
    List<ChamadoTipo> obterTiposChamado(TipoPerfilUsuario tipoPerfil);


}
