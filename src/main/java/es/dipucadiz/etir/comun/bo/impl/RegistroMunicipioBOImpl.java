/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.RegistroMunicipioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.RegistroMunicipioDTO;
import es.dipucadiz.etir.comun.dto.RegistroMunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class RegistroMunicipioBOImpl extends AbstractGenericBOImpl<RegistroMunicipioDTO, RegistroMunicipioDTOId> implements RegistroMunicipioBO {

	private static final Log LOG = LogFactory.getLog(RegistroMunicipioBOImpl.class);
	
	private DAOBase<RegistroMunicipioDTO, RegistroMunicipioDTOId> dao;

	
	
	/*public List<ConceptoDTO> findConceptosDistintosByMunicipio(MunicipioDTO municipioDTO)
	throws GadirServiceException {
		List<ConceptoDTO> resultado;
		try {

			resultado = this.findByNamedQuery(
					"Convenio.findConceptosDistintosByMunicipio",
					new String[] { "coProvincia", "coMunicipio"},
					new Object[] { municipioDTO.getId().getCoProvincia(), municipioDTO.getId().getCoMunicipio()});

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los conceptos.", e);
		}
		return resultado;
	}
	
	public List<ConceptoDTO> findConceptosDistintosByMunicipioEjercicio(MunicipioDTO municipioDTO, short ejercicio)
	throws GadirServiceException {
		List<ConceptoDTO> resultado;
		try {

			resultado = this.findByNamedQuery(
					"Convenio.findConceptosDistintosByMunicipioEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "ejercicio"}, 
					new Object[]{municipioDTO.getId().getCoProvincia(), municipioDTO.getId().getCoMunicipio(), Short.valueOf(ejercicio)});

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los conceptos.", e);
		}
		return resultado;
	}
	
	public List<ConceptoDTO> findConceptosByTasa(String coTasa) throws GadirServiceException {
		List<ConceptoDTO> resultado;
		try {

			resultado = this.findByNamedQuery(
					"Concepto.findConceptosByTasa",
					new String[]{"coTasa"},	new Object[]{coTasa});

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los conceptos.", e);
		}
		return resultado;
	}
	*/
	
	public DAOBase<RegistroMunicipioDTO, RegistroMunicipioDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<RegistroMunicipioDTO, RegistroMunicipioDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	 
	public void auditorias(RegistroMunicipioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
