package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.util.List;

import es.dipucadiz.etir.comun.bo.ExtraccionTodosBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioCondDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioCondDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionCriterioDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraCampoDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraCampoDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeEstructDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenCampoDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenCampoDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenDTOId;
import es.dipucadiz.etir.comun.dto.ExtraccionTipoRegistroDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionTipoRegistroDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public class ExtraccionTodosBOImpl implements ExtraccionTodosBO, Serializable {
	private static final long serialVersionUID = 4348748466704886886L;

	private DAOBase<ExtraccionDTO, String> extraccionDAO;
	private DAOBase<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> extraccionCriterioDAO;
	private DAOBase<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> extraccionCriterioCondDAO;
	private DAOBase<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId> extraccionEstructuraDAO;
	private DAOBase<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> extraccionEstructuraCampoDAO;
	private DAOBase<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> extraccionOrdenDAO;
	private DAOBase<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> extraccionOrdenCampoDAO;
	private DAOBase<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId> extraccionTipoRegistroDAO;
	private DAOBase<ExtraccionInformeDTO, ExtraccionInformeDTOId> extraccionInformeDAO;
	private DAOBase<ExtraccionInformeEstructDTO, Long> extraccionInformeEstructDAO;
	
	
	public void borraExtraccionCompleta (String coExtraccion) throws GadirServiceException{
		
		//ExtraccionDTO extraccionDTO = extraccionDAO.findById(coExtraccion);
		
		
		
		
		//borrar informes
		List<ExtraccionInformeEstructDTO> listaExtraccionInformeEstructDTO = extraccionInformeEstructDAO.findFiltered("extraccionInformeDTO.id.coExtraccion", coExtraccion);
		extraccionInformeEstructDAO.deleteAll(listaExtraccionInformeEstructDTO);
		List<ExtraccionInformeDTO> listaExtraccionInformeDTO = extraccionInformeDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionInformeDAO.deleteAll(listaExtraccionInformeDTO);
		
		//borrar ordenes
		List<ExtraccionOrdenCampoDTO> listaExtraccionOrdenCampoDTO = extraccionOrdenCampoDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionOrdenCampoDAO.deleteAll(listaExtraccionOrdenCampoDTO);
		List<ExtraccionOrdenDTO> listaExtraccionOrdenDTO = extraccionOrdenDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionOrdenDAO.deleteAll(listaExtraccionOrdenDTO);
		
		//borrar criterios
		List<ExtraccionCriterioCondDTO> listaExtraccionCriterioCondDTO = extraccionCriterioCondDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionCriterioCondDAO.deleteAll(listaExtraccionCriterioCondDTO);
		List<ExtraccionCriterioDTO> listaExtraccionCriterioDTO = extraccionCriterioDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionCriterioDAO.deleteAll(listaExtraccionCriterioDTO);
		
		//borrar estructura
		List<ExtraccionEstructuraCampoDTO> listaExtraccionEstructuraCampoDTO = extraccionEstructuraCampoDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionEstructuraCampoDAO.deleteAll(listaExtraccionEstructuraCampoDTO);
		List<ExtraccionEstructuraDTO> listaExtraccionEstructuraDTO = extraccionEstructuraDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionEstructuraDAO.deleteAll(listaExtraccionEstructuraDTO);
		
		//borrar tipos registro
		List<ExtraccionTipoRegistroDTO> listaExtraccionTipoRegistroDTO = extraccionTipoRegistroDAO.findFiltered("id.coExtraccion", coExtraccion);
		extraccionTipoRegistroDAO.deleteAll(listaExtraccionTipoRegistroDTO);
		
		//borrar estructura
		extraccionDAO.delete(coExtraccion);
	}


	public DAOBase<ExtraccionDTO, String> getExtraccionDAO() {
		return extraccionDAO;
	}


	public void setExtraccionDAO(DAOBase<ExtraccionDTO, String> extraccionDAO) {
		this.extraccionDAO = extraccionDAO;
	}


	public DAOBase<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> getExtraccionCriterioDAO() {
		return extraccionCriterioDAO;
	}


	public void setExtraccionCriterioDAO(
			DAOBase<ExtraccionCriterioDTO, ExtraccionCriterioDTOId> extraccionCriterioDAO) {
		this.extraccionCriterioDAO = extraccionCriterioDAO;
	}


	public DAOBase<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> getExtraccionCriterioCondDAO() {
		return extraccionCriterioCondDAO;
	}


	public void setExtraccionCriterioCondDAO(
			DAOBase<ExtraccionCriterioCondDTO, ExtraccionCriterioCondDTOId> extraccionCriterioCondDAO) {
		this.extraccionCriterioCondDAO = extraccionCriterioCondDAO;
	}


	public DAOBase<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId> getExtraccionEstructuraDAO() {
		return extraccionEstructuraDAO;
	}


	public void setExtraccionEstructuraDAO(
			DAOBase<ExtraccionEstructuraDTO, ExtraccionEstructuraDTOId> extraccionEstructuraDAO) {
		this.extraccionEstructuraDAO = extraccionEstructuraDAO;
	}


	public DAOBase<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> getExtraccionEstructuraCampoDAO() {
		return extraccionEstructuraCampoDAO;
	}


	public void setExtraccionEstructuraCampoDAO(
			DAOBase<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> extraccionEstructuraCampoDAO) {
		this.extraccionEstructuraCampoDAO = extraccionEstructuraCampoDAO;
	}


	public DAOBase<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> getExtraccionOrdenDAO() {
		return extraccionOrdenDAO;
	}


	public void setExtraccionOrdenDAO(
			DAOBase<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> extraccionOrdenDAO) {
		this.extraccionOrdenDAO = extraccionOrdenDAO;
	}


	public DAOBase<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> getExtraccionOrdenCampoDAO() {
		return extraccionOrdenCampoDAO;
	}


	public void setExtraccionOrdenCampoDAO(
			DAOBase<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> extraccionOrdenCampoDAO) {
		this.extraccionOrdenCampoDAO = extraccionOrdenCampoDAO;
	}


	public DAOBase<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId> getExtraccionTipoRegistroDAO() {
		return extraccionTipoRegistroDAO;
	}


	public void setExtraccionTipoRegistroDAO(
			DAOBase<ExtraccionTipoRegistroDTO, ExtraccionTipoRegistroDTOId> extraccionTipoRegistroDAO) {
		this.extraccionTipoRegistroDAO = extraccionTipoRegistroDAO;
	}


	public DAOBase<ExtraccionInformeDTO, ExtraccionInformeDTOId> getExtraccionInformeDAO() {
		return extraccionInformeDAO;
	}


	public void setExtraccionInformeDAO(
			DAOBase<ExtraccionInformeDTO, ExtraccionInformeDTOId> extraccionInformeDAO) {
		this.extraccionInformeDAO = extraccionInformeDAO;
	}


	public DAOBase<ExtraccionInformeEstructDTO, Long> getExtraccionInformeEstructDAO() {
		return extraccionInformeEstructDAO;
	}


	public void setExtraccionInformeEstructDAO(
			DAOBase<ExtraccionInformeEstructDTO, Long> extraccionInformeEstructDAO) {
		this.extraccionInformeEstructDAO = extraccionInformeEstructDAO;
	}


	
	
}