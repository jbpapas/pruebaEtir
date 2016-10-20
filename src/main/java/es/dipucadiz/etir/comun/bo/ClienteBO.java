package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.ClienteDomicilioVO;
import es.dipucadiz.etir.sb07.comun.vo.ClienteFicticioVO;
import es.dipucadiz.etir.sb07.comun.vo.ClienteVO;

public interface ClienteBO extends GenericBO<ClienteDTO, Long> {

	ClienteDTO findFirstByNIF(final String identificador) throws GadirServiceException;
	List<ClienteDTO> findByLikeRazonSocial(String razonSocial) throws GadirServiceException;
	ClienteDomicilioVO findByIdClienteYDomicilio(final String id) throws GadirServiceException;
	List<ClienteFicticioVO> busquedaNifFicticiosByRazonSocial(final String filtro, boolean empieza) throws GadirServiceException;
	void DisociarCliente(Long idClienteSel) throws GadirServiceException;
	void ActualizarCliente(ClienteDTO cliente) throws GadirServiceException;
	ClienteVO findClienteVOById(final Long id) throws GadirServiceException;
	boolean tieneDocumentosCensosActivos(final Long id) throws GadirServiceException;
	boolean tieneDocumentosDistintosB(final Long id) throws GadirServiceException;
	
	List<ClienteDomicilioVO> findByAsociadoClienteYDomicilio(final String id) throws GadirServiceException;
	List<String> busquedaListaCodigosNifFicticios(String coUnidadUrbana, final String filtro, boolean empieza) throws GadirServiceException;
	void asociarCliente(List<String> clientes) throws GadirServiceException;
	ClienteDTO buscaCliente(Long coCliente, String razonSocial, String nif) throws GadirServiceException;
	int countByCriteria(String nombre, String filtroNIF, String codProvincia, String codMunicipio, String sigla, String nombreCalle, String numero, String letra, String bloque, String escalera, String planta, String puerta, String km, boolean contiene, boolean incluirAsociados) throws GadirServiceException;
	List<ClienteDTO> findByCriteria(String nombre, String filtroNIF, String codProvincia, String codMunicipio, String sigla, String nombreCalle, String numero, String letra, String bloque, String escalera, String planta, String puerta, String km, boolean contiene, boolean incluirAsociados, int inicio, int limite) throws GadirServiceException;
	boolean existMovilAsociado(String cliente) throws GadirServiceException;
	void save(ClienteDTO cliente, DomicilioDTO domicilio, String telefono, String movil, String fax, String email) throws GadirServiceException ;
}