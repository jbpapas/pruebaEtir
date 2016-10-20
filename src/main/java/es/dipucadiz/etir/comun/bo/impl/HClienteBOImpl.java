/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.HClienteBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.HClienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HClienteBOImpl extends AbstractGenericBOImpl<HClienteDTO, Long>
		implements HClienteBO {

	private DAOBase<HClienteDTO, Long> dao;

	public DAOBase<HClienteDTO,Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<HClienteDTO, Long> dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<HClienteDTO> findHClienteById(Long id) throws GadirServiceException {
		List<HClienteDTO> clientes = new ArrayList<HClienteDTO>();
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
			        1);
			params.put("id", id);
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaNamedQuerySelect(
			        		"HCliente.findById",
			                params);
			for (Object[] objeto : result) {
				HClienteDTO cli = new HClienteDTO();
				
				cli.setRowid((objeto[0]!=null)? objeto[0].toString():"");
				cli.setCoCliente((objeto[1]!= null)? new Long(objeto[1].toString()):0);
				cli.setIdentificador((objeto[2]!=null)? (String) objeto[2]:"");
				cli.setRazonSocial((objeto[3]!=null)? (String) objeto[3]:"");
				cli.setAnagrama((objeto[4]!=null)? (String) objeto[4]:"");
				cli.setEtiqueta((objeto[5]!=null)? (String) objeto[5]:"");
				cli.setProcedencia((objeto[6]!=null)? (String) objeto[6]:"");
				cli.setFxAlta((objeto[7]!=null)? (Date) objeto[7]:null);
				cli.setFxBaja((objeto[8]!=null)? (Date) objeto[8]:null);
				cli.setBoActivo((objeto[9]!=null)? ((objeto[9].toString().equals("1")) ? true: false):null);
				cli.setFxNacimiento((objeto[10]!=null)? (Date) objeto[10]:null);
				cli.setFxFallecimiento((objeto[11]!=null)? (Date) objeto[11]:null);
				cli.setEmail((objeto[12]!=null)? (String) objeto[12]:"");
				cli.setMotivoBaja((objeto[13]!=null)? (String) objeto[13]:"");
				cli.setTelefono((objeto[14]!= null)? new Integer(objeto[14].toString()):0);
				cli.setFax((objeto[15]!= null)? new Integer(objeto[15].toString()):0);
				cli.setCoClienteRepresentante((objeto[16]!= null)? new Long(objeto[16].toString()):0);
				cli.setCoClienteAsociado((objeto[17]!= null)? new Long(objeto[17].toString()):0);
				cli.setFhActualizacion((objeto[18]!=null)? (Date) objeto[18]:null);
				cli.setCoUsuarioActualizacion((objeto[19]!= null)? (String)objeto[19].toString():"");
				cli.setBoSms((objeto[20]!=null)? ((objeto[20].toString().equals("1")) ? true: false):null);
				cli.setMovil((objeto[21]!= null)? new Integer(objeto[21].toString()):0);
				cli.setHTipoMovimiento((objeto[22]!= null)? (String)objeto[22].toString():"");
				cli.setCoProceso((objeto[23]!= null)? (String)objeto[23].toString():"");
				cli.setCoEjecucion((objeto[24]!= null)? new Long(objeto[24].toString()):0);
				
				clientes.add(cli);
			}
			return clientes;
		} catch (final Exception e) {
			throw new GadirServiceException(
			        "Error al obtener el listado de clientes.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<HClienteDTO> findHClienteByIdYFecha(Long id, Date fecha)
			throws GadirServiceException {
		List<HClienteDTO> clientes = new ArrayList<HClienteDTO>();
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
			        1);
			params.put("id", id);
			params.put("fecha", fecha);
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaNamedQuerySelect(
			        		"HCliente.findByIdYFecha",
			                params);
			for (Object[] objeto : result) {
				HClienteDTO cli = new HClienteDTO();
				
				cli.setRowid((objeto[0]!=null)? objeto[0].toString():"");
				cli.setCoCliente((objeto[1]!= null)? new Long(objeto[1].toString()):0);
				cli.setIdentificador((objeto[2]!=null)? (String) objeto[2]:"");
				cli.setRazonSocial((objeto[3]!=null)? (String) objeto[3]:"");
				cli.setAnagrama((objeto[4]!=null)? (String) objeto[4]:"");
				cli.setEtiqueta((objeto[5]!=null)? (String) objeto[5]:"");
				cli.setProcedencia((objeto[6]!=null)? (String) objeto[6]:"");
				cli.setFxAlta((objeto[7]!=null)? (Date) objeto[7]:null);
				cli.setFxBaja((objeto[8]!=null)? (Date) objeto[8]:null);
				cli.setBoActivo((objeto[9]!=null)? ((objeto[9].toString().equals("1")) ? true: false):null);
				cli.setFxNacimiento((objeto[10]!=null)? (Date) objeto[10]:null);
				cli.setFxFallecimiento((objeto[11]!=null)? (Date) objeto[11]:null);
				cli.setEmail((objeto[12]!=null)? (String) objeto[12]:"");
				cli.setMotivoBaja((objeto[13]!=null)? (String) objeto[13]:"");
				cli.setTelefono((objeto[14]!= null)? new Integer(objeto[14].toString()):0);
				cli.setFax((objeto[15]!= null)? new Integer(objeto[15].toString()):0);
				cli.setCoClienteRepresentante((objeto[16]!= null)? new Long(objeto[16].toString()):0);
				cli.setCoClienteAsociado((objeto[17]!= null)? new Long(objeto[17].toString()):0);
				cli.setFhActualizacion((objeto[18]!=null)? (Date) objeto[18]:null);
				cli.setCoUsuarioActualizacion((objeto[19]!= null)? (String)objeto[19].toString():"");
				cli.setBoSms((objeto[20]!=null)? ((objeto[20].toString().equals("1")) ? true: false):null);
				cli.setMovil((objeto[21]!= null)? new Integer(objeto[21].toString()):0);
				cli.setHTipoMovimiento((objeto[22]!= null)? (String)objeto[22].toString():"");
				cli.setCoProceso((objeto[23]!= null)? (String)objeto[23].toString():"");
				cli.setCoEjecucion((objeto[24]!= null)? new Long(objeto[24].toString()):0);
				
				clientes.add(cli);
			}
			return clientes;
		} catch (final Exception e) {
			throw new GadirServiceException(
			        "Error al obtener el listado de clientes.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<HClienteDTO> findHClienteByRowid(String rowid)
			throws GadirServiceException {
		List<HClienteDTO> clientes = new ArrayList<HClienteDTO>();
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
			        1);
			params.put("rowid", Utilidades.decodificarRowidFormatoSeguro(rowid));
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaNamedQuerySelect(
			        		"HCliente.findByRowid",
			                params);
			for (Object[] objeto : result) {
				HClienteDTO cli = new HClienteDTO();
				
				cli.setRowid((objeto[0]!=null)? objeto[0].toString():"");
				cli.setCoCliente((objeto[1]!= null)? new Long(objeto[1].toString()):0);
				cli.setIdentificador((objeto[2]!=null)? (String) objeto[2]:"");
				cli.setRazonSocial((objeto[3]!=null)? (String) objeto[3]:"");
				cli.setAnagrama((objeto[4]!=null)? (String) objeto[4]:"");
				cli.setEtiqueta((objeto[5]!=null)? (String) objeto[5]:"");
				cli.setProcedencia((objeto[6]!=null)? (String) objeto[6]:"");
				cli.setFxAlta((objeto[7]!=null)? (Date) objeto[7]:null);
				cli.setFxBaja((objeto[8]!=null)? (Date) objeto[8]:null);
				cli.setBoActivo((objeto[9]!=null)? ((objeto[9].toString().equals("1")) ? true: false):null);
				cli.setFxNacimiento((objeto[10]!=null)? (Date) objeto[10]:null);
				cli.setFxFallecimiento((objeto[11]!=null)? (Date) objeto[11]:null);
				cli.setEmail((objeto[12]!=null)? (String) objeto[12]:"");
				cli.setMotivoBaja((objeto[13]!=null)? (String) objeto[13]:"");
				cli.setTelefono((objeto[14]!= null)? new Integer(objeto[14].toString()):null);
				cli.setFax((objeto[15]!= null)? new Integer(objeto[15].toString()):null);
				cli.setCoClienteRepresentante((objeto[16]!= null)? new Long(objeto[16].toString()):0);
				cli.setCoClienteAsociado((objeto[17]!= null)? new Long(objeto[17].toString()):0);
				cli.setFhActualizacion((objeto[18]!=null)? (Date) objeto[18]:null);
				cli.setCoUsuarioActualizacion((objeto[19]!= null)? (String)objeto[19].toString():"");
				cli.setBoSms((objeto[20]!=null)? ((objeto[20].toString().equals("1")) ? true: false):null);
				cli.setMovil((objeto[21]!= null)? new Integer(objeto[21].toString()):0);
				cli.setHTipoMovimiento((objeto[22]!= null)? (String)objeto[22].toString():"");
				cli.setCoProceso((objeto[23]!= null)? (String)objeto[23].toString():"");
				cli.setCoEjecucion((objeto[24]!= null)? new Long(objeto[24].toString()):0);
				
				clientes.add(cli);
			}
			return clientes;
		} catch (final Exception e) {
			throw new GadirServiceException(
			        "Error al obtener el listado de clientes.", e);
		}
	}
	
	/**
	 * 
	 */
	public void guardarHCliente(ClienteDTO cliente, String tipoMovimiento) throws GadirServiceException{
		HClienteDTO clienteHistorico = new HClienteDTO();
		
		clienteHistorico.setAnagrama(cliente.getAnagrama());
		clienteHistorico.setBoActivo(cliente.isBoActivo());
		clienteHistorico.setCoCliente(cliente.getCoCliente());
		if (cliente.getClienteDTOByCoClienteAsociado() != null){
			clienteHistorico.setCoClienteAsociado(cliente.getClienteDTOByCoClienteAsociado().getCoCliente());
		}
		if (cliente.getClienteDTOByCoClienteRepresentante() != null){
			clienteHistorico.setCoClienteRepresentante(cliente.getClienteDTOByCoClienteRepresentante().getCoCliente());
		}
		clienteHistorico.setEmail(cliente.getEmail());
		clienteHistorico.setEtiqueta(cliente.getEtiqueta());
		clienteHistorico.setFax(cliente.getFax());
		clienteHistorico.setFxAlta(cliente.getFxAlta());
		clienteHistorico.setFxBaja(cliente.getFxBaja());
		clienteHistorico.setFxFallecimiento(cliente.getFxFallecimiento());
		clienteHistorico.setIdentificador(cliente.getIdentificador());
		clienteHistorico.setFxNacimiento(cliente.getFxNacimiento());
		clienteHistorico.setMotivoBaja(cliente.getMotivoBaja());
		clienteHistorico.setProcedencia(cliente.getProcedencia());
		clienteHistorico.setRazonSocial(cliente.getRazonSocial());
		clienteHistorico.setTelefono(cliente.getTelefono());
		clienteHistorico.setHTipoMovimiento(tipoMovimiento);
		
		
		try {
			this.saveOnly(clienteHistorico);
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al registrar el historico:", e);
		}
	}
	public void auditorias(HClienteDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
}
