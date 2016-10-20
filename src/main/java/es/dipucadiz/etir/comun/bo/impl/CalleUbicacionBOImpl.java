/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CalleUbicacionBOImpl extends AbstractGenericBOImpl<CalleUbicacionDTO, Long> implements CalleUbicacionBO {

	private static final Log LOG = LogFactory.getLog(CalleBOImpl.class);
	
	private DAOBase<CalleUbicacionDTO, Long> dao;

	public DAOBase<CalleUbicacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CalleUbicacionDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public CalleUbicacionDTO obtenerUbicacionByCalle(Long codCalle) throws GadirServiceException{
		CalleUbicacionDTO resultado= new CalleUbicacionDTO();
		List <CalleUbicacionDTO> listaResultado=null;
		if (codCalle == null) {
			if (log.isDebugEnabled()) {
				log
						.debug("El código de calle es null, se devuelve una lista vacía.");
			}
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
						1);
				params.put("coCalle", codCalle);

				listaResultado = this.getDao().findByNamedQuery(
						QueryName.UBICACION_BY_CALLE, params);

				
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener La ubicación:", e);
			}
		}
		if(listaResultado!= null && listaResultado.size()>0){
			resultado=listaResultado.get(0);
		}
		return resultado;
	}
	
	public void auditorias(CalleUbicacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
