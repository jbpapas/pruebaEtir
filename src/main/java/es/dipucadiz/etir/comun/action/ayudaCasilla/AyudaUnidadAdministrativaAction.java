package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CodterrUniadmBO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class AyudaUnidadAdministrativaAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -365187721006541529L;

	//protected UnidadAdministrativaBO unidadAdministrativaBO;
	protected CodterrUniadmBO codterrUniadmBO;
	
	protected List<UnidadAdministrativaDTO> listaUnidadAdministrativas;
	protected String coUnidadAdministrativa;

	private static final Log LOG = LogFactory.getLog(AyudaUnidadAdministrativaAction.class);

	public String execute() throws GadirServiceException {

		try{
			super.execute();

			String coCodigoTerritorial="";

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	coCodigoTerritorial = valor; break;
				}
			}
			
			if (Utilidades.isEmpty(coCodigoTerritorial) || coCodigoTerritorial.length()!=6){
				addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_TERRITORIAL_INEXISTENTE));
			}
			
			if (!hasErrors()){
				listaUnidadAdministrativas = codterrUniadmBO.findByCodter(coCodigoTerritorial);
			}			

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de unidad administrativa", e);
		}

		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 2:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), coUnidadAdministrativa);
					break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de unidad administrativa", e);
		}
		
		ajaxData=parseaResultado();
		return AJAX_DATA;
	}
	
	
	public CodterrUniadmBO getCodterrUniadmBO() {
		return codterrUniadmBO;
	}

	public void setCodterrUniadmBO(CodterrUniadmBO codterrUniadmBO) {
		this.codterrUniadmBO = codterrUniadmBO;
	}

	public List<UnidadAdministrativaDTO> getListaUnidadAdministrativas() {
		return listaUnidadAdministrativas;
	}

	public void setListaUnidadAdministrativas(
			List<UnidadAdministrativaDTO> listaUnidadAdministrativas) {
		this.listaUnidadAdministrativas = listaUnidadAdministrativas;
	}

	public String getCoUnidadAdministrativa() {
		return coUnidadAdministrativa;
	}

	public void setCoUnidadAdministrativa(String coUnidadAdministrativa) {
		this.coUnidadAdministrativa = coUnidadAdministrativa;
	}


}
