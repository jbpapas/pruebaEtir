package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.ProvinciaConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class AyudaProvinciaAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -365187721006541529L;

	protected ProvinciaBO provinciaBO;
	
	protected List<ProvinciaDTO> listaProvincias;
	protected String coProvincia;

	private static final Log LOG = LogFactory.getLog(AyudaProvinciaAction.class);

	public String execute() throws GadirServiceException {

		try{
			super.execute();

			String boFiscal="";

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	boFiscal = valor; break;
				}
			}
			
			if (Utilidades.isEmpty(boFiscal) || !boFiscal.equals("F") && !boFiscal.equals("T")){
				addActionError(Mensaje.getTexto(MensajeConstants.V1, "Argumento de entrada incorrecto"));
			}
			
			if (!hasErrors()){
				listaProvincias = new ArrayList<ProvinciaDTO>();
				if(boFiscal.equals("T"))
					listaProvincias.add(provinciaBO.findById("11"));
				else{
					listaProvincias = provinciaBO.findAll("coProvincia", DAOConstant.ASC_ORDER);
					listaProvincias.remove(ProvinciaConstants.PROVINCIA_GENERICA_DTO);
					listaProvincias.remove(ProvinciaConstants.PROVINCIA_AYUNTAMIENTOS_DTO);
				}
			}			

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de provincia", e);
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
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), provinciaBO.findById(coProvincia).getNombre());
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

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}

	public List<ProvinciaDTO> getListaProvincias() {
		return listaProvincias;
	}

	public void setListaProvincias(List<ProvinciaDTO> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}

	public String getCoProvincia() {
		return coProvincia;
	}

	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}
}
