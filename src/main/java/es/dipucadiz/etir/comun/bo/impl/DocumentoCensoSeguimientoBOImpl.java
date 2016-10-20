package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import es.dipucadiz.etir.comun.bo.DocumentoCensoSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.BancoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCensoSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BancoDTO}.
 * 
 */
public class DocumentoCensoSeguimientoBOImpl extends AbstractGenericBOImpl<DocumentoCensoSeguimientoDTO, Long>
        implements DocumentoCensoSeguimientoBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<DocumentoCensoSeguimientoDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<DocumentoCensoSeguimientoDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<DocumentoCensoSeguimientoDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(DocumentoCensoSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

	public DocumentoCensoSeguimientoDTO getUltimaIncidencia(String coModelo, String coVersion, String coDocumento) {
		DocumentoCensoSeguimientoDTO result;
		String[] propNames = {"coModelo", "coVersion", "coDocumento"};
		Object[] filters = {coModelo, coVersion, coDocumento};
		String[] orderProperty = {"fhActualizacion","coDocumentoCensoSeguimiento"};
		int[] orderType = {DAOConstant.DESC_ORDER,DAOConstant.DESC_ORDER};
		try {
			List<DocumentoCensoSeguimientoDTO> documentoCensoSeguimientoDTOs = findFiltered(propNames, filters, orderProperty, orderType);
			if (documentoCensoSeguimientoDTOs.size() > 0) {
				result = documentoCensoSeguimientoDTOs.get(0);
			} else {
				result = null;
			}
		} catch (GadirServiceException e) {
			log.error(e.getMensaje(), e);
			result = null;
		}
		return result;
	}


}