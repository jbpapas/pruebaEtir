package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmUsuarioBOImpl extends AbstractGenericBOImpl<AcmUsuarioDTO, String>  implements AcmUsuarioBO {

	private static final Log LOG = LogFactory.getLog(AcmUsuarioBOImpl.class);

	private DAOBase<AcmUsuarioDTO, String> dao;


	public DAOBase<AcmUsuarioDTO, String> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<AcmUsuarioDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public AcmUsuarioDTO findByIdInitialized(String id) throws GadirServiceException {
		AcmUsuarioDTO result=null;
		
		result = findById(id);
		
		if (result!=null){
			Hibernate.initialize(result.getCodigoTerritorialDTO());
			Hibernate.initialize(result.getUnidadAdministrativaDTO());
		}
		
		return result;
	}
	
	public List<AcmUsuarioDTO> findByCodigoTerritorialStartsWith(String prefijoCodigoTerritorial){
		List<AcmUsuarioDTO> lista;
		
		Criterion criterions[] = { Restrictions.like("codigoTerritorialDTO", new CodigoTerritorialDTO(prefijoCodigoTerritorial + "%")) };
		String orderPropertys[] = { "coAcmUsuario" };
		int orderTypes[] = { DAOConstant.ASC_ORDER };
		int firstResult = -1;
		int maxResults = -1;
		lista=dao.findFiltered(criterions, orderPropertys, orderTypes, firstResult, maxResults);
		
		return lista;		
	}
	
	public List<AcmUsuarioDTO> findByCodigoTerritorialStartsWithInitialized(String prefijoCodigoTerritorial){
		List<AcmUsuarioDTO> lista;
		
		Criterion criterions[] = { Restrictions.like("codigoTerritorialDTO", new CodigoTerritorialDTO(prefijoCodigoTerritorial + "%")) };
		String orderPropertys[] = { "coAcmUsuario" };
		int orderTypes[] = { DAOConstant.ASC_ORDER };
		int firstResult = -1; 
		int maxResults = -1; 
		lista=dao.findFiltered(criterions, orderPropertys, orderTypes, firstResult, maxResults);
		
		for (AcmUsuarioDTO acmUsuarioDTO : lista){
			Hibernate.initialize(acmUsuarioDTO.getCodigoTerritorialDTO());
			Hibernate.initialize(acmUsuarioDTO.getUnidadAdministrativaDTO());
		}
		
		return lista;		
	}
	
	public List<AcmUsuarioDTO> findByUnidadAdminAndCodigoTerritorialStartsWith(String prefijoCodigoTerritorial, String coUnidadAdministrativa) throws GadirServiceException{
		List<AcmUsuarioDTO> lista;
		
		Criterion criterions[] = { 
				Restrictions.like("codigoTerritorialDTO", new CodigoTerritorialDTO(prefijoCodigoTerritorial + "%")),  
				Restrictions.eq("unidadAdministrativaDTO", new UnidadAdministrativaDTO(coUnidadAdministrativa))
				};
		String orderPropertys[] = { "coAcmUsuario" };
		int orderTypes[] = { DAOConstant.ASC_ORDER };
		int firstResult = -1; 
		int maxResults = -1; 
		lista=dao.findFiltered(criterions, orderPropertys, orderTypes, firstResult, maxResults);
		
		for (AcmUsuarioDTO acmUsuarioDTO : lista){
			Hibernate.initialize(acmUsuarioDTO.getCodigoTerritorialDTO());
			Hibernate.initialize(acmUsuarioDTO.getUnidadAdministrativaDTO());
		}
		
		return lista;	
	}
	
	public void auditorias(AcmUsuarioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
