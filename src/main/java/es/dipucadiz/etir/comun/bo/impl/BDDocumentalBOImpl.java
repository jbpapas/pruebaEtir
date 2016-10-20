package es.dipucadiz.etir.comun.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import es.dipucadiz.etir.comun.bo.BDDocumentalBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;
import es.dipucadiz.etir.comun.dto.BancoDTO;
import es.dipucadiz.etir.comun.dto.ExpedienteSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb06.vo.AdjuntoVO;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BancoDTO}.
 * 
 */
public class BDDocumentalBOImpl extends AbstractGenericBOImpl<BDDocumentalDTO, Long> implements BDDocumentalBO {
	private static final long serialVersionUID = 6879318165367306079L;
	protected final Log LOG = LogFactory.getLog(getClass());
	
	
	public DetachedCriteria getCriterioAdjuntosByExpediente(Long coExpediente, Long coExpedienteSeguimientoActual, boolean filtrarSeguimiento, boolean ordenar, boolean limitarRownum) {
		DetachedCriteria criterio = DetachedCriteria.forClass(BDDocumentalDTO.class, "bddoc");
		criterio.createAlias("bddoc.bdDocumentalGrupoDTO", "grupo");
		DetachedCriteria criterioExp = DetachedCriteria.forClass(ExpedienteSeguimientoDTO.class, "expseg");
		criterioExp.createAlias("expseg.bdDocumentalGrupoDTO", "grupo2");
		criterioExp.add(Restrictions.eqProperty("grupo.coBDDocumentalGrupo", "grupo2.coBDDocumentalGrupo"));
		criterioExp.add(Restrictions.eq("expseg.expedienteDTO.coExpediente", coExpediente));
		if (filtrarSeguimiento && coExpedienteSeguimientoActual != null) {
			criterioExp.add(Restrictions.eq("expseg.coExpedienteSeguimiento", coExpedienteSeguimientoActual));
		}
		criterioExp.setProjection(Projections.property("coExpedienteSeguimiento"));
		criterio.add(Subqueries.exists(criterioExp));
		if (limitarRownum) {
			criterio.add(Restrictions.sqlRestriction("ROWNUM=1"));
		}
		if (ordenar) {
			criterio.addOrder(Order.desc("bddoc.coBDDocumental"));
		}
		return criterio;
	}
	
	public List<AdjuntoVO> findAdjuntosByExpediente(Long coExpediente, Long coExpedienteSeguimientoActual, boolean filtrarSeguimiento) throws GadirServiceException {
		List<BDDocumentalDTO> bdDocumentalDTOs = findByCriteria(getCriterioAdjuntosByExpediente(coExpediente, coExpedienteSeguimientoActual, filtrarSeguimiento, true, false));

		List<AdjuntoVO> listaAdjuntos = new ArrayList<AdjuntoVO>(bdDocumentalDTOs.size());
		for (BDDocumentalDTO bdDocumentalDTO : bdDocumentalDTOs) {
			AdjuntoVO adjuntoVO = new AdjuntoVO();
			if (bdDocumentalDTO.getDocumentoDTO() != null) {
				adjuntoVO.setCoModelo(bdDocumentalDTO.getDocumentoDTO().getId().getCoModelo());
				adjuntoVO.setCoVersion(bdDocumentalDTO.getDocumentoDTO().getId().getCoVersion());
				adjuntoVO.setCoDocumento(bdDocumentalDTO.getDocumentoDTO().getId().getCoDocumento());
			}
			adjuntoVO.setNombre(bdDocumentalDTO.getNombre());
			adjuntoVO.setObservaciones(bdDocumentalDTO.getObservaciones());
			adjuntoVO.setFxPresentacion(Utilidades.dateToDDMMYYYYHHMMSS(bdDocumentalDTO.getFhActualizacion()));
			adjuntoVO.setCoBDDocumental(bdDocumentalDTO.getCoBDDocumental());
			try {
				adjuntoVO.setTamano(Utilidades.getFormatoTamanoFichero(Long.toString(bdDocumentalDTO.getFichero().length()), 1));
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
				adjuntoVO.setTamano("");
			}
			
			Hibernate.initialize(bdDocumentalDTO.getBdDocumentalGrupoDTO().getExpedienteSeguimientoDTOs());
			ExpedienteSeguimientoDTO expedienteSeguimientoDTO = bdDocumentalDTO.getBdDocumentalGrupoDTO().getExpedienteSeguimientoDTOs().toArray(new ExpedienteSeguimientoDTO[0])[0];
			if (coExpedienteSeguimientoActual == null || coExpedienteSeguimientoActual.intValue() == expedienteSeguimientoDTO.getCoExpedienteSeguimiento().intValue()) {
				adjuntoVO.setBorrable(true);
			} else {
				adjuntoVO.setBorrable(false);
			}
			Hibernate.initialize(expedienteSeguimientoDTO.getTramiteDTO());
			adjuntoVO.setTramite(expedienteSeguimientoDTO.getTramiteDTO().getCodigoDescripcion());
			listaAdjuntos.add(adjuntoVO);
		}
		return listaAdjuntos;
	}

	@Override
	public String getIdAlfrescoFirmado(Long coBdDocumentalGrupo) throws GadirServiceException {
		
		List<BDDocumentalDTO> documentos = super.findFiltered("bdDocumentalGrupoDTO.coBDDocumentalGrupo", coBdDocumentalGrupo);
		
		//sólo debe haber un documento asociado a la notificación
		if(documentos != null && !documentos.isEmpty()){
			if(!Utilidades.isEmpty(documentos.get(0).getFhPublicado()) && !Utilidades.isEmpty(documentos.get(0).getIdAlfrescoFirmado())){
				return documentos.get(0).getIdAlfrescoFirmado();
			}
		}
		
		return null;
	}
	

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<BDDocumentalDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<BDDocumentalDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BDDocumentalDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BDDocumentalDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

}
