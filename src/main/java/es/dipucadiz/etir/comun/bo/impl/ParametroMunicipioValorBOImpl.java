package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import es.dipucadiz.etir.comun.bo.ParametroMunicipioValorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ParametroMunicipioValorDTO;
import es.dipucadiz.etir.comun.dto.ParametroMunicipioValorDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ParametroMunicipioValorBOImpl extends AbstractGenericBOImpl<ParametroMunicipioValorDTO, ParametroMunicipioValorDTOId>  implements ParametroMunicipioValorBO{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7906613593047467480L;
	
	private DAOBase<ParametroMunicipioValorDTO, ParametroMunicipioValorDTOId> dao;
	
	
	/*
	 * (non-Javadoc)
	 * @see es.dipucadiz.etir.comun.bo.ParametroMunicipioBO#findParametroMunicipioById(java.lang.Long)
	 */
	public List<ParametroMunicipioValorDTO> findByParametroMunicipio(Long codParametroMunicipio) throws GadirServiceException{
		
		if (Utilidades.isEmpty(codParametroMunicipio)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El código recibido es null, no se recupera nada del sistema.");
			}
			return Collections.EMPTY_LIST;
		}
		try {
			return this.getDao().findFiltered(
			        "id.coParametroMunicipio", codParametroMunicipio);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener los valores del parametro municipio: "
			                + codParametroMunicipio, e);
		}
		
	}
	

	public DAOBase<ParametroMunicipioValorDTO, ParametroMunicipioValorDTOId> getDao() {
		return dao;
	}
	
	public void setDao(final DAOBase<ParametroMunicipioValorDTO, ParametroMunicipioValorDTOId> dao) {
		this.dao = dao;
	}
	
	public void auditorias(ParametroMunicipioValorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
}