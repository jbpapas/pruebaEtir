/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ClienteContactoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.ClienteContactoDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.CopyPropertiesList;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ClienteContactoVO;

public class ClienteContactoBOImpl extends AbstractGenericBOImpl<ClienteContactoDTO, Long> implements ClienteContactoBO {

	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(ClienteContactoBOImpl.class);
	
	private DAOBase<ClienteContactoDTO, Long> dao;
	
	public DAOBase<ClienteContactoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ClienteContactoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	
	public void auditorias(ClienteContactoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public List<ClienteContactoVO> findFilteredVO(String propName, Object filter) throws GadirServiceException {
		
		List<ClienteContactoDTO> listDTO = findFiltered(propName, filter, "boPreferente", DAOConstant.DESC_ORDER);
		
		List<ClienteContactoVO> listVO;
		try {
			listVO = (new CopyPropertiesList<ClienteContactoDTO, ClienteContactoVO>()).convertListDTOtoVO(listDTO, ClienteContactoVO.class);
		} catch (Exception e) {
			LOG.error("Se ha producido un error al convertir List DTO a VO", e);
			throw new GadirServiceException(e);
		} 
		
		return listVO;
	}
	
	/**
	 * Método que retorno el contacto preferente para el cliente y tipo pasado como argumentos.
	 * 
	 * @param cliente 
	 * @param tipo
	 * @return retorna ClienteContactoDTO marcado como preferente para el tipo y cliente pasado, si no existe retorna null.
	 * @throws GadirServiceException 
	 */
	public ClienteContactoDTO getContactoPreferenteByTipo(ClienteDTO cliente, String tipo) throws GadirServiceException{
		String[] filterColumns = { "clienteDTO", "tipo", "boPreferente" };
		Object[] parameters = { cliente, tipo, true };

		List<ClienteContactoDTO> list = findFiltered(filterColumns, parameters);
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * Método que retorno el primer contacto que se dio de alta para el cliente y tipo pasado como argumentos.
	 * 
	 * @param cliente 
	 * @param tipo
	 * @return retorna ClienteContactoDTO marcado como preferente para el tipo y cliente pasado, si no existe retorna null.
	 * @throws GadirServiceException 
	 */
	public ClienteContactoDTO getFirstContactoByTipo(ClienteDTO cliente, String tipo) throws GadirServiceException{
		String[] filterColumns = { "clienteDTO", "tipo" };
		Object[] parameters = { cliente, tipo};

		List<ClienteContactoDTO> list = findFiltered(filterColumns, parameters, "fhActualizacion", DAOConstant.ASC_ORDER);
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}else{
			return null;
		}
	}

}
