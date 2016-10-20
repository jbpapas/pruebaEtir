package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmUsuarioNotificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public interface AcmUsuarioNotificacionBO extends GenericBO<AcmUsuarioNotificacionDTO, Long>{
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuario(String coAcmUsuario);
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuarioAndTipo(String coAcmUsuario, int tipo);
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuarioAndImportancia3AndTipo(String coAcmUsuario, String tipo);
	
	public List<AcmUsuarioNotificacionDTO> findByAcmUsuarioAndFechaAndTipo(String coAcmUsuario, Date fecha, int tipo);
	void deleteByCoAcmUsuario(String coAcmUsuario) throws GadirServiceException;
	
}
