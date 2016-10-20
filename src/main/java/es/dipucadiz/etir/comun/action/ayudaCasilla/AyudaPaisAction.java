package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.PaisBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.PaisDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;

final public class AyudaPaisAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -365187721006541529L;

	protected PaisBO paisBO;
	
	protected List<PaisDTO> listaPaises;
	protected String coPais;

	private static final Log LOG = LogFactory.getLog(AyudaPaisAction.class);

	public String execute() throws GadirServiceException {

		try{
			super.execute();

			listaPaises = paisBO.findAll("coPais", DAOConstant.ASC_ORDER);		

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de país", e);
		}

		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), paisBO.findById(Short.parseShort(coPais)).getNombre());
					break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de municipio", e);
		}
		
		ajaxData=parseaResultado();
		return AJAX_DATA;
	}

	public PaisBO getPaisBO() {
		return paisBO;
	}

	public void setPaisBO(PaisBO paisBO) {
		this.paisBO = paisBO;
	}

	public List<PaisDTO> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<PaisDTO> listaPaises) {
		this.listaPaises = listaPaises;
	}

	public String getCoPais() {
		return coPais;
	}

	public void setCoPais(String coPais) {
		this.coPais = coPais;
	}

	
}
