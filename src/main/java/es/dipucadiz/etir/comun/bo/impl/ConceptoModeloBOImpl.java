/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ConceptoModeloBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConceptoModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ConceptoModeloBOImpl extends AbstractGenericBOImpl<ConceptoModeloDTO, Long> implements ConceptoModeloBO {

	private static final long serialVersionUID = 3553640908242103210L;
	private static final Log LOG = LogFactory.getLog(ConceptoModeloBOImpl.class);
	
	private DAOBase<ConceptoModeloDTO, Long> dao;

	
	public DAOBase<ConceptoModeloDTO, Long> getDao() {
		return dao;
	}
	
	public List<ConceptoModeloDTO> findConceptoModelos(List<ConceptoDTO> listaConceptos, String codModelo) throws GadirServiceException{
		List<ConceptoModeloDTO> listaConceptosModelos = new ArrayList<ConceptoModeloDTO>();
		try{
			Iterator<ConceptoDTO> itConceptos = listaConceptos.iterator();
			while(itConceptos.hasNext()){
				ConceptoDTO aux = itConceptos.next();
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("coConcepto", aux.getCoConcepto());
				params.put("coModelo", codModelo);
				listaConceptosModelos.addAll(this.getDao().findByNamedQuery(QueryName.FIND_CONCEPTOS_LISTAS_COBRATORIAS, params));
	//			obtieneConceptos(listaConceptosModelos,this.getDao().findByNamedQuery(QueryName.FIND_CONCEPTOS_LISTAS_COBRATORIAS));
			}
		}catch(Exception e){
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al obtener los conceptos.", e);
		}
		return listaConceptosModelos;
	}
	

	
	private void obtieneConceptos(List<ConceptoModeloDTO> listaConceptos,List<ConceptoModeloDTO> listaConceptosAnadir){
		Iterator<ConceptoModeloDTO> it = listaConceptosAnadir.iterator();
		while(it.hasNext()){
			ConceptoModeloDTO concepto = it.next();
			boolean enc = false;
			for(int i=0;i<listaConceptos.size() && !enc;i++){
				if(listaConceptos.get(i).getConceptoDTO().getCoConcepto().equals(concepto.getConceptoDTO().getCoConcepto())){
					enc = true;
				}
			}
			if(!enc){
				listaConceptos.add(concepto);
			}
		}
	}

	public void setDao(final DAOBase<ConceptoModeloDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(ConceptoModeloDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public boolean isMultiConcepto(String coModelo) throws GadirServiceException {
		return findFiltered("modeloDTO.coModelo", coModelo, 0, 2).size() > 1;
	}	

}
