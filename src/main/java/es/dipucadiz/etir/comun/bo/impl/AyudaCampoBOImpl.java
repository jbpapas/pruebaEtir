package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import es.dipucadiz.etir.comun.bo.AyudaCampoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.AyudaCampoDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class AyudaCampoBOImpl extends AbstractGenericBOImpl<AyudaCampoDTO, Long>
        implements AyudaCampoBO {



	private DAOBase<AyudaCampoDTO, Long> dao;
	
	
	
	public List<AyudaCampoDTO> findByProcesoAndCampo(String coProceso, String campo) throws GadirServiceException {
		
		List<AyudaCampoDTO> result = dao.findFiltered(new String[]{"procesoDTO","campo"}, new Object[]{new ProcesoDTO(coProceso), campo}, "coAyudaCampo", DAOConstant.ASC_ORDER);
		
		return result;		
	}
	


	
	@Override
	public DAOBase<AyudaCampoDTO, Long> getDao() {
		return this.dao;
	}
	
	public void setDao(final DAOBase<AyudaCampoDTO, Long> dao) {
		this.dao = dao;
	}
	
	public void auditorias(AyudaCampoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
