package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.TablaGt;

final public class AyudaTipoBienInmuebleAction extends AyudaCasillaAction {

	private static final long serialVersionUID = 249587080032617485L;
	private static final Log LOG = LogFactory.getLog(AyudaTipoBienInmuebleAction.class);
	
	protected String key;
	protected List<KeyValue> listaEstablecimientos;
	
	public String execute() throws GadirServiceException {

		listaEstablecimientos = TablaGt.getListaCodigoDescripcion("TEST014");
		
		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			
			String est = TablaGt.getValor("TEST014", key, TablaGt.COLUMNA_DESCRIPCION);
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), key);
					break;
				case 2:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), est);
					break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de tipo de bien imm. basura", e);
		}
		
		ajaxData=parseaResultado();
		
		return AJAX_DATA;
	}

	public List<KeyValue> getListaEstablecimientos() {
		return listaEstablecimientos;
	}

	public void setListaEstablecimientos(List<KeyValue> listaEstablecimientos) {
		this.listaEstablecimientos = listaEstablecimientos;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
