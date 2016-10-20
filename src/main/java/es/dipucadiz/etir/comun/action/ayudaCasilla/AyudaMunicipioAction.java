package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class AyudaMunicipioAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -365187721006541529L;

	protected MunicipioBO municipioBO;
	protected ProvinciaBO provinciaBO;
	
	protected List<MunicipioDTO> listaMunicipios;
	protected String municipioRowid;

	private static final Log LOG = LogFactory.getLog(AyudaMunicipioAction.class);

	public String execute() throws GadirServiceException {

		try{
			super.execute();

			String provincia="";

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 2:	provincia = valor; break;
				}
			}
			
			if (Utilidades.isEmpty(provincia) || provinciaBO.findFiltered("nombre", provincia, 0, 1).isEmpty()){
				addActionError(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "Provincia"));
			}
			
			if (!hasErrors()){
				String coProvincia = provinciaBO.findFiltered("nombre", provincia, 0, 1).get(0).getCoProvincia();
				listaMunicipios = municipioBO.findByProvincia(coProvincia);
			}			

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de municipio", e);
		}

		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 3:	
					MunicipioDTO m = municipioBO.findByRowid(municipioRowid);
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), m.getNombre());
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

	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		this.municipioBO = municipioBO;
	}

	public List<MunicipioDTO> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<MunicipioDTO> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public String getMunicipioRowid() {
		return municipioRowid;
	}

	public void setMunicipioRowid(String municipioRowid) {
		this.municipioRowid = municipioRowid;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}
	
}
