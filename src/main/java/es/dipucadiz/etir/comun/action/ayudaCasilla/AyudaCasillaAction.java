package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.action.AbstractGadirBaseAction;
import es.dipucadiz.etir.comun.bo.ValidacionArgumentoBO;
import es.dipucadiz.etir.comun.bo.ValidacionBO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.AyudaCasillaVO;

public abstract class AyudaCasillaAction extends AbstractGadirBaseAction {
	
	private static final long serialVersionUID = -785972905522959721L;

	protected ValidacionBO validacionBO;
	protected ValidacionArgumentoBO validacionArgumentoBO;

	private static final Log LOG = LogFactory.getLog(AyudaCasillaAction.class);

	
	public String execute() throws GadirServiceException {
		
		//TODO asegurarme de que tengo el coValidacion, el modelo-version, el documento, el municipio y el concepto
		
		return INPUT;
	}
	
	public String parseaResultado(){
		String resultado="";
		
		try{
			AyudaCasillaVO ayudaCasillaVO=DatosSesion.getAyudaCasillaVO();

			List<ValidacionArgumentoDTO> listaArgumentos=validacionArgumentoBO.findArgumentos(ayudaCasillaVO.getCoValidacion());

			for (ValidacionArgumentoDTO validacionArgumentoDTO : listaArgumentos){
				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S") && !validacionArgumentoDTO.getFuncionArgumentoDTO().getBoEntrada()){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));

					if (!Utilidades.isEmpty(valor)){
						if (!resultado.equals(""))resultado+="@@";
						resultado+=Integer.valueOf(validacionArgumentoDTO.getValor()) + "##" + valor;
					}
				}
			}
		}catch(Exception e){
			LOG.error("Error devolviendo resultado en ayuda de casilla",e);
		}
		
		return resultado;
	}
	
	public abstract String ajaxBotonSeleccionar();

	
	public ValidacionBO getValidacionBO() {
		return validacionBO;
	}


	public void setValidacionBO(ValidacionBO validacionBO) {
		this.validacionBO = validacionBO;
	}


	public ValidacionArgumentoBO getValidacionArgumentoBO() {
		return validacionArgumentoBO;
	}


	public void setValidacionArgumentoBO(ValidacionArgumentoBO validacionArgumentoBO) {
		this.validacionArgumentoBO = validacionArgumentoBO;
	}


	
}
