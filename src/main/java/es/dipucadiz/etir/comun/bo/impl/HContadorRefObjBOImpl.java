package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.HContadorRefObjBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ContadorRefObjDTO;
import es.dipucadiz.etir.comun.dto.HContadorRefObjDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class HContadorRefObjBOImpl extends AbstractGenericBOImpl<HContadorRefObjDTO, Long> implements HContadorRefObjBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO.
	 */
	private DAOBase<HContadorRefObjDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<HContadorRefObjDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<HContadorRefObjDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(HContadorRefObjDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

	public void guardarHContadorRefObj(ContadorRefObjDTO contadorRefObjDTO, String movimiento) throws GadirServiceException {
		HContadorRefObjDTO hContadorRefObjDTO = new HContadorRefObjDTO();
		
		if(contadorRefObjDTO.getConceptoDTO() != null) {
			hContadorRefObjDTO.setCoConcepto(contadorRefObjDTO.getConceptoDTO().getCoConcepto());
		}
		
		hContadorRefObjDTO.setCoContadorRefObj(contadorRefObjDTO.getCoContadorRefObj());
		
		if(contadorRefObjDTO.getModeloDTO() != null) {
			hContadorRefObjDTO.setCoModelo(contadorRefObjDTO.getModeloDTO().getCoModelo());
		}
		
		if(contadorRefObjDTO.getMunicipioDTO() != null) {
			hContadorRefObjDTO.setCoMunicipio(contadorRefObjDTO.getMunicipioDTO().getId().getCoMunicipio());
			hContadorRefObjDTO.setCoProvincia(contadorRefObjDTO.getMunicipioDTO().getId().getCoProvincia());
		}
		
		hContadorRefObjDTO.setContador(contadorRefObjDTO.getContador());
		hContadorRefObjDTO.setCoUsuarioActualizacion(contadorRefObjDTO.getCoUsuarioActualizacion());
		hContadorRefObjDTO.setEjercicio(contadorRefObjDTO.getEjercicio());
		hContadorRefObjDTO.setFhActualizacion(contadorRefObjDTO.getFhActualizacion());
		hContadorRefObjDTO.setHTipoMovimiento(movimiento);
		hContadorRefObjDTO.setLongitud(contadorRefObjDTO.getLongitud());
		hContadorRefObjDTO.setPrefijo(contadorRefObjDTO.getPrefijo());
		this.save(hContadorRefObjDTO);
	}


}
