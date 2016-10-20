/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CalleTramoPostalBO;
import es.dipucadiz.etir.comun.bo.ConsultaCodigoPostalBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CalleTramoPostalDTO;
import es.dipucadiz.etir.comun.dto.CalleTramoPostalDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CalleTramoPostalBOImpl extends AbstractGenericBOImpl<CalleTramoPostalDTO, CalleTramoPostalDTOId> implements CalleTramoPostalBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6938286649210081207L;

	private static final Log LOG = LogFactory.getLog(CalleTramoPostalBOImpl.class);
	
	private DAOBase<CalleTramoPostalDTO, CalleTramoPostalDTOId> dao;
	private ConsultaCodigoPostalBO consultaCodigoPostalBO;

	public DAOBase<CalleTramoPostalDTO, CalleTramoPostalDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CalleTramoPostalDTO, CalleTramoPostalDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void setConsultaCodigoPostalBO(ConsultaCodigoPostalBO consultaCodigoPostalBO) {
		this.consultaCodigoPostalBO = consultaCodigoPostalBO;
	}

	public ConsultaCodigoPostalBO getConsultaCodigoPostalBO() {
		return consultaCodigoPostalBO;
	}


	public String findCodigoPostalAsString(Long coCalle, Integer numero) {
		String codigoPostal = String.valueOf(consultaCodigoPostalBO.execute(coCalle, numero));
		while (codigoPostal.length() < 5) {
			codigoPostal = '0' + codigoPostal;
		}
		return codigoPostal;
	}
	public CalleTramoPostalDTO findTramo(Long coCalle,String numero) throws GadirServiceException {
		CalleTramoPostalDTO tramo = null;
		try{
			if(!Utilidades.isEmpty(numero)){
				List<CalleTramoPostalDTO> listaTramos = this.getDao().findFiltered("id.coCalle", coCalle);
				if(listaTramos != null && !listaTramos.isEmpty()){
					boolean enc = false;
					for(int i=0;i<listaTramos.size() && !enc; i++){
						if(listaTramos.get(i).getId().getDesde()<=new Integer(numero) && listaTramos.get(i).getHasta()>=new Integer(numero)){
							enc = true;
							tramo = listaTramos.get(i); 
						}
					}
				}
			}
		}catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el tramo asociado.", e);
		}
		return tramo;
	}

	public void auditorias(CalleTramoPostalDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

}
