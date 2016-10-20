package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTOId;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class EjecucionBOImpl extends AbstractGenericBOImpl<EjecucionDTO, Long> implements EjecucionBO {

	private static final Log LOG = LogFactory.getLog(EjecucionBOImpl.class);

	private DAOBase<EjecucionDTO, Long> dao;
	private DAOBase<EjecucionParametroDTO, EjecucionParametroDTOId> ejecucionParamDao;
	private DAOBase<ProcesoDTO, String> procesoDao;

	public EjecucionDTO insert(final String proceso, final String usuario, final String script, final String cola, final List<String> parametros) 
		throws GadirServiceException {
		LOG.trace("Insertando la ejecución de " + proceso);
		final EjecucionDTO ejecucion = new EjecucionDTO();
		ejecucion.setProcesoDTO(new ProcesoDTO(proceso));
		//final AcmUsuarioDTO usuarioDTO = new AcmUsuarioDTO();
		//usuarioDTO.setCoAcmUsuario(usuario);
		ejecucion.setCoAcmUsuario(usuario);
		ejecucion.setCoUsuarioActualizacion(usuario);
//		ejecucion.setEstado(Character.toString(estado));
		ejecucion.setScript(script);
		ejecucion.setCola(cola);
		ejecucion.setFhActualizacion(new Date());
		ejecucion.setFhEstado(new Date());
		this.save(ejecucion);
		final Set<EjecucionParametroDTO> parametrosSet = new HashSet<EjecucionParametroDTO>();
		short nuParametro=1;
		for (final Iterator<String> i = parametros.iterator(); i.hasNext();) {
			final EjecucionParametroDTO ejecucionParam = new EjecucionParametroDTO();
			final EjecucionParametroDTOId ejecucionParamId = new EjecucionParametroDTOId();
			ejecucionParamId.setCoEjecucion(ejecucion.getCoEjecucion());
			ejecucionParamId.setNuParametro(nuParametro++);
			ejecucionParam.setId(ejecucionParamId);
			ejecucionParam.setCoUsuarioActualizacion(ejecucion.getCoUsuarioActualizacion());
			ejecucionParam.setEjecucionDTO(ejecucion);
			ejecucionParam.setFhActualizacion(ejecucion.getFhActualizacion());
			ejecucionParam.setValor(i.next());
			ejecucionParam.setFhActualizacion(Utilidades.getDateActual());
			ejecucionParam.setCoUsuarioActualizacion(DatosSesion.getLogin());
			ejecucionParamDao.save(ejecucionParam);
			parametrosSet.add(ejecucionParam);
		}
		ejecucion.setEjecucionParametroDTOs(parametrosSet);
		final ProcesoDTO procesoDTO = procesoDao.findById(ejecucion.getProcesoDTO().getCoProceso());
		ejecucion.setProcesoDTO(procesoDTO);
		return ejecucion;
	}
	
	public EjecucionDTO findByCoEjecucionInitialized(final int codigoEjecucion) {
		final EjecucionDTO ejecucion = dao.findById((long)codigoEjecucion);

		Hibernate.initialize(ejecucion.getProcesoDTO());
		/*Hibernate.initialize(ejecucion.getAcmUsuarioDTO());
		Hibernate.initialize(ejecucion.getAcmUsuarioDTO().getCodigoTerritorialDTO());*/
		
		return ejecucion;
	}



	public void setDao(final DAOBase<EjecucionDTO, Long> ejecucionDAO) {
		this.dao = ejecucionDAO;
	}

	public DAOBase<EjecucionDTO, Long> getDao() {
		return dao;
	}

	public void setEjecucionParamDAO(final DAOBase<EjecucionParametroDTO, EjecucionParametroDTOId> ejecucionParamDAO) {
		this.ejecucionParamDao = ejecucionParamDAO;
	}

	public DAOBase<EjecucionParametroDTO, EjecucionParametroDTOId> getEjecucionParamDAO() {
		return ejecucionParamDao;
	}

	public void setProcesoDAO(final DAOBase<ProcesoDTO, String> procesoDAO) {
		this.procesoDao = procesoDAO;
	}

	public DAOBase<ProcesoDTO, String> getProcesoDAO() {
		return procesoDao;
	}

	public void auditorias(EjecucionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
