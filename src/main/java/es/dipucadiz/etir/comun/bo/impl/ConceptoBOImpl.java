/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ConceptoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ConceptoBOImpl extends AbstractGenericBOImpl<ConceptoDTO, String> implements ConceptoBO {

	private static final Log LOG = LogFactory.getLog(ConceptoBOImpl.class);
	
	private DAOBase<ConceptoDTO, String> dao;

	
	
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
	}*/
	
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
	
	
	public DAOBase<ConceptoDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ConceptoDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public List<ConceptoDTO> findConceptoByModelo(String coModelo) throws GadirServiceException{
		
		List<ConceptoDTO> listaConceptos;
		try{
				final Map<String, Object> params = new HashMap<String, Object>();
			
				params.put("coModelo", coModelo);
				listaConceptos = this.getDao().findByNamedQuery(QueryName.CONCEPTOS_BY_MODELO, params);
			
				return listaConceptos;
		}catch(Exception e){
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al obtener los conceptos.", e);
		}
		
	}

	public void auditorias(ConceptoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
