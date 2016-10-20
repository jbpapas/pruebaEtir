package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ClienteContactoDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.ClienteContactoVO;

public interface ClienteContactoBO extends GenericBO<ClienteContactoDTO, Long>{

	public final static String TIPO_TELEFONO = "F";
	public final static String TIPO_MOVIL = "M";
	public final static String TIPO_FAX = "X";
	public final static String TIPO_EMAIL = "E";
	public List<ClienteContactoVO> findFilteredVO(final String propName, final Object filter) throws GadirServiceException;
	public ClienteContactoDTO getContactoPreferenteByTipo(ClienteDTO cliente, String tipo) throws GadirServiceException;
	public ClienteContactoDTO getFirstContactoByTipo(ClienteDTO cliente, String tipo) throws GadirServiceException;
}
 