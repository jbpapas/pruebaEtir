package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.HDomicilioDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.HGlobalDomiciliosVO;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * modelo {@link HDomicilioDTO}.
 * 
 * @version 1.0 25/01/2010
 * @author SDS[agonzalez]
 */
public interface HDomicilioBO extends
        GenericBO<HDomicilioDTO, Long> {
        
    /**
	 * Método para obtener la lista de domicilios historicos por id
	 * 
	 * @param id
	 * @return
	 * @throws GadirServiceException
	 */
	public List<HDomicilioDTO> findHDomicilioById(Long id) throws GadirServiceException;
	
	/**
	 * Método para obtener la lista de domicilios historicos por id y fecha
	 * 
	 * @param id
	 * @return
	 * @throws GadirServiceException
	 */
	public List<HDomicilioDTO> findHDomicilioByIdYFecha(Long id, Date fecha) throws GadirServiceException;
	
	/**
	 * Método para obtener la lista de domicilios historicos por id y fecha
	 * 
	 * @param domicilio
	 * @param unidadUrbana
	 * @param tipoMovimiento
	 * 
	 * @throws GadirServiceException
	 */
	public void guardarHDomicilio(DomicilioDTO domicilio, UnidadUrbanaDTO unidadUrbana, String tipoMovimiento) throws GadirServiceException;
	
	/**
	 * Método para obtener la lista de domicilios historicos por cliente y/o
	 * municipio, calle, concepto, fecha
	 * 
	 * @param coCliente, codMunicipio, codCalle, fecha
	 * @return
	 * @throws GadirServiceException
	 */
	List<HGlobalDomiciliosVO> findByClienteAndOrMunicipioCalleFecha(final String coCliente, final String codProvincia, final String codMunicipio, 
			final String codCalle, final String fecha) throws GadirServiceException;
	
	/**
	 * Método para obtener la lista de domicilios historicos por cliente y/o
	 * municipio, calle, concepto, fecha o ninguno de ellos.
	 * 
	 * @param coCliente, codMunicipio, codCalle, fecha
	 * @return
	 * @throws GadirServiceException
	 */
	List<HGlobalDomiciliosVO> findByClienteOrMunicipioCalleFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String fecha) throws GadirServiceException;
	
	/**
	 * Método encargado de obtener el historico de domicilio seleccionado
	 * 
	 * @param rowid
	 * @return
	 * @throws GadirServiceException
	 */
	HGlobalDomiciliosVO findByRowId(String rowid) throws GadirServiceException;
	
	
	List<HGlobalDomiciliosVO> findFISCALByClienteOrMunicipioCalleFecha(final String coCliente, final String codProvincia, final String codMunicipio, final String codCalle, final String fecha) throws GadirServiceException;
	
	
}
