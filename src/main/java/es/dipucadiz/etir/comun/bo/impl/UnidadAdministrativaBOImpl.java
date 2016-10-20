/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.UnidadAdministrativaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Formato;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class UnidadAdministrativaBOImpl extends AbstractGenericBOImpl<UnidadAdministrativaDTO, String> implements UnidadAdministrativaBO {

	private static final Log LOG = LogFactory.getLog(UnidadAdministrativaBOImpl.class);
	
	private DAOBase<UnidadAdministrativaDTO, String> dao;
	private DAOBase<AcmUsuarioDTO, String> acmUsuarioDao;
	private DAOBase<ClienteDTO, Long> clienteDao;

	public DAOBase<UnidadAdministrativaDTO, String> getDao() {
		return dao;
	}

	public void setDao(DAOBase<UnidadAdministrativaDTO, String> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public DAOBase<AcmUsuarioDTO, String> getAcmUsuarioDao() {
		return acmUsuarioDao;
	}

	public void setAcmUsuarioDao(DAOBase<AcmUsuarioDTO, String> dao) {
		acmUsuarioDao = dao;
	}

	public void setClienteDao(DAOBase<ClienteDTO, Long> clienteDao) {
		this.clienteDao = clienteDao;
	}

	public DAOBase<ClienteDTO, Long> getClienteDao() {
		return clienteDao;
	}

	
	
	
	
	
	
	
	public List<KeyValue> findUsuariosRecaudacion() {
		List<KeyValue> listaUsuariosRecaudacion = new ArrayList<KeyValue>();
		List<AcmUsuarioDTO> listaAcmUsuarioDTO = acmUsuarioDao.findAll("coAcmUsuario", 	1);
		for (Iterator<AcmUsuarioDTO> i = listaAcmUsuarioDTO.iterator(); i.hasNext();) {
			AcmUsuarioDTO acmUsuarioDTO = i.next();
			ClienteDTO clienteDTO = acmUsuarioDTO.getClienteDTO();
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(acmUsuarioDTO.getCoAcmUsuario());
			if (clienteDTO != null) {
				keyValue.setValue(clienteDTO.getRazonSocial());
			} else {
				keyValue.setValue(keyValue.getKey());
			}
			listaUsuariosRecaudacion.add(keyValue);
		}
		return listaUsuariosRecaudacion;
	}

	public void validarCoUnidadAdministrativa(String coUnidadAdministrativa, boolean isAlta) throws GadirServiceException {
		if (Utilidades.isEmpty(coUnidadAdministrativa)) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
		} 
		if (!Formato.validar(coUnidadAdministrativa, "N", 4)) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
		} 
		if (coUnidadAdministrativa.length() != 4) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.LONGITUD_DEL_CAMPO_INCORRECTA));
		} 
		if (dao.findById(coUnidadAdministrativa) == null) {
			if (!isAlta) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "LA UNIDAD ADMINISTRATIVA"));
			}
		} else {
			if (isAlta) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1_YA_EXISTE, "LA UNIDAD ADMINISTRATIVA"));
			}
		}
	}

	public void validarNombre(String coUnidadAdministrativa, String nombre) throws GadirServiceException {
		if (Utilidades.isEmpty(nombre)) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
		}
		List<UnidadAdministrativaDTO> listaUnidadAdministrativaDTOs = dao.findFiltered("nombre", nombre);
		for (Iterator<UnidadAdministrativaDTO> i = listaUnidadAdministrativaDTOs.iterator(); i.hasNext();) {
			UnidadAdministrativaDTO unidadAdministrativaDTO = i.next();
			if (coUnidadAdministrativa == null || !coUnidadAdministrativa.equals(unidadAdministrativaDTO.getCoUnidadAdministrativa())) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1_YA_EXISTE, "EL NOMBRE DE LA UNIDAD ADMINISTRATIVA"));
			}
		}
	}

//	public List<UnidadAdministrativaDTO> validarUnidadRegistral(String unidadRegistral, String coUnidadAdministrativa, String unidadRegistralQuePertenece, String unidadAdministrativaQueDepende) throws GadirServiceException {
//		UnidadAdministrativaDTO unidadPerteneceDTO = null;
//		UnidadAdministrativaDTO unidadDependeDTO = null;
//		if (!"R".equals(unidadRegistral) && !"N".equals(unidadRegistral)) {
//			throw new GadirServiceException(34, null, "unidadRegistralValue");
//		} else if ("R".equals(unidadRegistral)) {
//			if (!Utilidades.isEmpty(unidadRegistralQuePertenece)) {
//				throw new GadirServiceException(41, null, "unidadRegistralQuePerteneceValue");
//			}
//			if (!Utilidades.isEmpty(unidadAdministrativaQueDepende)) {
//				throw new GadirServiceException(41, null, "unidadAdministrativaQueDependeValue");
//			}
//		} else if ("N".equals(unidadRegistral)) {
//			if (Utilidades.isEmpty(unidadRegistralQuePertenece)) {
//				throw new GadirServiceException(2, null, "unidadRegistralQuePerteneceValue");
//			} else if ((unidadPerteneceDTO = dao.findById(unidadRegistralQuePertenece)) == null) {
//				throw new GadirServiceException(147, "UNIDAD ADMINSITRATIVA", "unidadRegistralQuePerteneceValue");
//			}
//			if (Utilidades.isEmpty(unidadAdministrativaQueDepende)) {
//				throw new GadirServiceException(2, null, "unidadAdministrativaQueDependeValue");
//			} else if (coUnidadAdministrativa.equals(unidadAdministrativaQueDepende)) {
//				throw new GadirServiceException(4658, "EL VALOR NO DEBE COINCIDIR CON LA UNIDAD ADMINISTRATIVA.", "unidadAdministrativaQueDependeValue");
//			} else if ((unidadDependeDTO = dao.findById(unidadAdministrativaQueDepende)) == null) {
//				throw new GadirServiceException(147, "UNIDAD ADMINISTRATIVA", "unidadAdministrativaQueDependeValue");
//			} else if ("R".equals(unidadDependeDTO.getTipo())) {
//				throw new GadirServiceException(4658, "NO DEBE SER UNA UNIDAD ADMINSITRATIVA REGISTRAL.", "unidadAdministrativaQueDependeValue");
//			}
//		}
//		
//		List<UnidadAdministrativaDTO> devolver = new ArrayList<UnidadAdministrativaDTO>(2);
//		devolver.add(unidadPerteneceDTO);
//		devolver.add(unidadDependeDTO);
//		return devolver;
//	}
	
	private void validarUnidadRegistral(String unidadRegistral) throws GadirServiceException {
		if (!"R".equals(unidadRegistral) && !"N".equals(unidadRegistral)) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.VALORES_VALIDOS_S_N));
//			throw new GadirServiceException(34, null, "unidadRegistralValue");
		}
	}
	
	public void validarPrincipal(String principal) throws GadirServiceException {
		if (!"true".equals(principal) && !"false".equals(principal)) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.VALORES_VALIDOS_S_N));
//			throw new GadirServiceException(34, null, "unidadRegistralValue");
		}
	}
	
	public UnidadAdministrativaDTO validarUnidadAdministrativaQueDepende(String unidadRegistral, String coUnidadAdministrativa, String unidadAdministrativaQueDepende) throws GadirServiceException {
//		validarUnidadRegistral(unidadRegistral);
		UnidadAdministrativaDTO unidadDependeDTO = null;
		if ("R".equals(unidadRegistral)) {
			if (!Utilidades.isEmpty(unidadAdministrativaQueDepende)) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.EL_CAMPO_NO_DEBE_TENER_VALOR));
			}
		} else if ("N".equals(unidadRegistral)) {
			if (Utilidades.isEmpty(unidadAdministrativaQueDepende)) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
			} else if (coUnidadAdministrativa.equals(unidadAdministrativaQueDepende)) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1, "El valor no debe coincidir con la unidad administrativa."));
			} else if ((unidadDependeDTO = dao.findById(unidadAdministrativaQueDepende)) == null) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "Unidad administrativa"));
			} else if ("R".equals(unidadDependeDTO.getTipo())) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1, "No debe ser una unidad administrativa registral."));
			}
		}
		return unidadDependeDTO;
	}

	public UnidadAdministrativaDTO validarUnidadRegistralQuePertenece(String unidadRegistral, String coUnidadAdministrativa, String unidadRegistralQuePertenece) throws GadirServiceException {
		validarUnidadRegistral(unidadRegistral);
		UnidadAdministrativaDTO unidadPerteneceDTO = null;
		if ("R".equals(unidadRegistral)) {
			if (!Utilidades.isEmpty(unidadRegistralQuePertenece)) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.EL_CAMPO_NO_DEBE_TENER_VALOR));
			}
		} else if ("N".equals(unidadRegistral)) {
			if (Utilidades.isEmpty(unidadRegistralQuePertenece)) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
			} else if ((unidadPerteneceDTO = dao.findById(unidadRegistralQuePertenece)) == null) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "Unidad administrativa"));
			}
		}
		return unidadPerteneceDTO;
	}

	public AcmUsuarioDTO validarUsuarioRecaudacion(String usuarioRecaudacion) throws GadirServiceException {
		AcmUsuarioDTO acmUsuarioDTO = null;
		if (!Utilidades.isEmpty(usuarioRecaudacion) && (acmUsuarioDTO = acmUsuarioDao.findById(usuarioRecaudacion)) == null) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "El usuario"));
		}
		return acmUsuarioDTO;
	}

	public void validarEmail(String email) throws GadirServiceException {
		if (!Utilidades.isEmpty(email) && !Utilidades.isFormatoEmail(email)) {
			throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
		}
	}

	public Integer validarTelefono(String telefono) throws GadirServiceException {
		Integer integer = null;
		if (!Utilidades.isEmpty(telefono)) {
			if (!Formato.validar(telefono, "N", 9)) {
				throw new GadirServiceException(Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
			} else {
				integer = Integer.decode(telefono);
			}
		}
		return integer;
	}

	public KeyValue findUsuarioRecaudacion(String coUnidadAdministrativa)  throws GadirServiceException {
		UnidadAdministrativaDTO unidadAdministrativaDTO = findById(coUnidadAdministrativa);
		KeyValue keyValue = new KeyValue();
		keyValue.setKey(unidadAdministrativaDTO.getAcmUsuarioDTO().getCoAcmUsuario());
//		keyValue.setValue(unidadAdministrativaDTO.getAcmUsuarioDTO().getClienteDTO().getRazonSocial());
		keyValue.setValue(acmUsuarioDao.findById(unidadAdministrativaDTO.getAcmUsuarioDTO().getCoAcmUsuario()).getClienteDTO().getRazonSocial());
			// acmUsuarioDao.findById(unidadAdministrativaDTO.getAcmUsuarioDTO().getCoAcmUsuario());
//		ClienteDTO clienteDTO = clienteDao.findById(acmUsuarioDTO.getClienteDTO().getCoCliente());
//		acmUsuarioDTO.setClienteDTO(clienteDTO);
		return keyValue;
	}

	public UnidadAdministrativaDTO findByIdInitialized(final String codigoUnidadAdministrativa){
		final UnidadAdministrativaDTO unidad = dao.findById(codigoUnidadAdministrativa);
		
		Hibernate.initialize(unidad.getUnidadUrbanaDTO());
		if(!Utilidades.isNull(unidad.getUnidadUrbanaDTO())){
			Hibernate.initialize(unidad.getUnidadUrbanaDTO().getCalleDTO());
			Hibernate.initialize(unidad.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO());
			Hibernate.initialize(unidad.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getProvinciaDTO());
		}
		return unidad;
	}
	
	public void auditorias(UnidadAdministrativaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
