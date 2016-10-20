package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.ClienteContactoBO;
import es.dipucadiz.etir.comun.bo.HClienteBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ClienteContactoDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.HClienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.ClienteDomicilioVO;
import es.dipucadiz.etir.sb07.comun.vo.ClienteFicticioVO;
import es.dipucadiz.etir.sb07.comun.vo.ClienteVO;

public class ClienteBOImpl extends AbstractGenericBOImpl<ClienteDTO, Long> implements ClienteBO {
	private static final long serialVersionUID = 1340169969023198258L;

	private static final Log LOG = LogFactory.getLog(ClienteBOImpl.class);

	private DAOBase<HClienteDTO, Long> hClienteDAO;
	
	private HClienteBO hclienteBO;
	private ClienteContactoBO clienteContactoBO;

	private DAOBase<ClienteDTO, Long> dao;
	private DAOBase<DomicilioDTO, Long> daoDomicilio;
	private DAOBase<ClienteContactoDTO, Long> daoClienteContacto;
	

	public DAOBase<ClienteDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<ClienteDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public DAOBase<DomicilioDTO, Long> getDaoDomicilio() {
		return daoDomicilio;
	}

	
	public void setDaoDomicilio(DAOBase<DomicilioDTO, Long> daoDomicilio) {
		this.daoDomicilio = daoDomicilio;
	}

	
	public DAOBase<ClienteContactoDTO, Long> getDaoClienteContacto() {
		return daoClienteContacto;
	}

	
	public void setDaoClienteContacto(DAOBase<ClienteContactoDTO, Long> daoClienteContacto) {
		this.daoClienteContacto = daoClienteContacto;
	}

	public ClienteDTO findFirstByNIF(final String identificador) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("identificador", identificador);
		try {
			return findByNamedQuery("Cliente.findFirstClienteByNIF", parametros)
					.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ClienteDomicilioVO findByIdClienteYDomicilio(final String id)
			throws GadirServiceException {
		List<Object> temporal = new ArrayList<Object>();
		ClienteDomicilioVO cliente = null;

		try {
			final HashMap map = new HashMap();

			map.put("coCliente", id);
			temporal = (List<Object>) this.getDao().ejecutaNamedQuerySelect(
					"Cliente.findByIdClienteYDomicilio", map);

			if (null != temporal && temporal.size() > 0) {
				final Object[] tupla = (Object[]) temporal.get(0);

				cliente = new ClienteDomicilioVO();

				cliente.setClienteCodigo(tupla[0].toString());
				cliente.setClienteNif(null != tupla[1] ? tupla[1].toString()
						: "");
				cliente.setClienteNombreRazon(null != tupla[2] ? tupla[2]
						.toString() : "");
				cliente.setDomicilioCoSigla(null != tupla[3] ? tupla[3]
						.toString() : "");
				cliente.setDomicilioNombreVia(null != tupla[4] ? tupla[4]
						.toString() : "");
				cliente.setDomicilioNum(null != tupla[5] ? tupla[5].toString()
						: "");
				cliente.setDomicilioLtr(null != tupla[6] ? tupla[6].toString()
						: "");
				cliente.setDomicilioBq(null != tupla[7] ? tupla[7].toString()
						: "");
				cliente.setDomicilioEsc(null != tupla[8] ? tupla[8].toString()
						: "");
				cliente.setDomicilioPla(null != tupla[9] ? tupla[9].toString()
						: "");
				cliente.setDomicilioPta(null != tupla[10] ? tupla[10]
						.toString() : "");
				cliente.setDomicilioKm(null != tupla[11] ? tupla[11].toString()
						: "");
				cliente.setClienteCodigoPostal(null != tupla[12] ? tupla[12]
						.toString() : "");
				cliente.setClienteMunicipio(null != tupla[13] ? tupla[13]
						.toString() : "");
				cliente.setClienteProvincia(null != tupla[14] ? tupla[14]
						.toString() : "");
				cliente.setClienteUbicacion(null != tupla[15] ? tupla[15]
						.toString() : "");
				cliente.setClienteDomicilioAEAT(null != tupla[16] ? Boolean
						.valueOf((tupla[16].toString().equals("1")) ? "true"
								: "false") : false);
				cliente
						.setClienteDomicilioNotificacion(null != tupla[18] ? Boolean
								.valueOf((tupla[18].toString().equals("1")) ? "true"
										: "false")
								: false);
				cliente.setDomicilioCod(null != tupla[19] ? tupla[19]
						.toString() : "");

				cliente.setDomicilioCoCalle(null != tupla[20] ? tupla[20].toString() : "");
				cliente.setClienteCoMunicipio(null != tupla[21] ? tupla[21].toString() : "");
				cliente.setClienteCoProvincia(null != tupla[22] ? tupla[22].toString() : "");
				cliente.setClienteCoUbicacion(null != tupla[23] ? tupla[23].toString() : "");
				cliente.setDomicilioSigla(TablaGt.getCodigoDescripcion(TablaGt.TABLA_TIPO_VIA_PUBLICA, cliente.getDomicilioCoSigla()).getCodigoDescripcion());
				
				
				// Miramos el especial caso de domicilio tributario
				if (Utilidades.isNotEmpty(cliente.getDomicilioCod())) {
					map.clear();
					map.put("coDomicilio", new Long(cliente.getDomicilioCod()));
					temporal = (List<Object>) this
							.getDao()
							.ejecutaNamedQuerySelect(
									"Cliente.findByIdClienteYDomicilioTributario",
									map);

					if (null != temporal && temporal.size() > 0)
						cliente.setClienteDomicilioTributario(true);
					else
						cliente.setClienteDomicilioTributario(false);
				} else
					cliente.setClienteDomicilioTributario(false);
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el cliente seleccionado", e);
		}

		return cliente;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ClienteDTO> findByLikeRazonSocial(String razonSocial)
			throws GadirServiceException {
		Criterion criterions[] = { Restrictions.like("razonSocial",
				razonSocial, MatchMode.START).ignoreCase() };
		String orderPropertys[] = { "razonSocial", "coCliente" };
		int orderTypes[] = { DAOConstant.ASC_ORDER, DAOConstant.ASC_ORDER };
		int firstResult = 0;
		int maxResults = 50;
		return dao.findFiltered(criterions, orderPropertys, orderTypes,
				firstResult, maxResults);
	}

	public ClienteVO findClienteVOById(Long id) throws GadirServiceException {
		ClienteDomicilioVO cdVO = findByIdClienteYDomicilio(id.toString());
		ClienteVO cliente = new ClienteVO();
		cliente.setCoCliente(id);
		cliente.setIdentificador(cdVO.getClienteNif());
		cliente.setRazonSocial(cdVO.getClienteNombreRazon());
		if (Utilidades.isNumeric(cdVO.getDomicilioNum())) {
			cliente.setNumero(Integer.valueOf(cdVO.getDomicilioNum()));
		}
		if (Utilidades.isNumeric(cdVO.getClienteCodigoPostal())) {
			cliente.setCp(Integer.valueOf(cdVO.getClienteCodigoPostal()));
		}
		cliente.setSigla(cdVO.getDomicilioCoSigla());
		cliente.setNombreCalle(cdVO.getDomicilioNombreVia());
		cliente.setMunicipio(cdVO.getClienteMunicipio());
		cliente.setProvincia(cdVO.getClienteProvincia());
		cliente.setKm(cdVO.getDomicilioKm());
		cliente.setLetra(cdVO.getDomicilioLtr());
		cliente.setBloque(cdVO.getDomicilioBq());
		cliente.setEscalera(cdVO.getDomicilioEsc());
		cliente.setPlanta(cdVO.getDomicilioPla());
		cliente.setPuerta(cdVO.getDomicilioPta());
		return cliente;
	}

	@SuppressWarnings("unchecked")
	public boolean tieneDocumentosCensosActivos(final Long id)
			throws GadirServiceException {

		try {
			final Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("coCliente", id);

			List<BigDecimal> result = (List<BigDecimal>) this.getDao()
					.ejecutaNamedQuerySelect(
							QueryName.CLIENTE_TIENE_DOC_CENSOS_ACTIVOS, params);

			if (result.get(0) != null && result.get(0).intValue() > 0) {
				return true;
			} else {
				return false;
			}

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el listado de plantillaes.", e);
		}

	}

	@SuppressWarnings("unchecked")
	public boolean tieneDocumentosDistintosB(final Long id)
			throws GadirServiceException {

		try {
			final Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("coCliente", id);

			List<BigDecimal> result = (List<BigDecimal>) this.getDao()
					.ejecutaNamedQuerySelect(
							QueryName.CLIENTE_TIENE_DOC_DISTINTOS_B, params);

			if (result.get(0) != null && result.get(0).intValue() > 0) {
				return true;
			} else {
				return false;
			}

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el listado de plantillaes.", e);
		}

	}

	@SuppressWarnings("unchecked")
	public void DisociarCliente(Long idClienteSel) throws GadirServiceException {
		Map parametros = new HashMap<String, Object>();
		parametros.put("id", idClienteSel);

		List<ClienteDTO> listaCliente = getDao().findByNamedQuery(
				"Cliente.findByIdFetch", parametros);

		if (null != listaCliente && listaCliente.size() > 0) {
			ClienteDTO cliente = listaCliente.get(0);

			// Disociamos
			cliente.setClienteDTOByCoClienteAsociado(null);
			save(cliente);

			// Registramos el cambio en el histórico
			HClienteDTO hCliente = new HClienteDTO();

			hCliente.setCoCliente(cliente.getCoCliente());
			hCliente.setAnagrama(cliente.getAnagrama());
			hCliente.setBoActivo(cliente.isBoActivo());
			hCliente.setCoClienteRepresentante((null != cliente
					.getClienteDTOByCoClienteRepresentante()) ? cliente
					.getClienteDTOByCoClienteRepresentante().getCoCliente()
					: null);
			hCliente.setCoClienteAsociado((null != cliente
					.getClienteDTOByCoClienteAsociado()) ? cliente
					.getClienteDTOByCoClienteAsociado().getCoCliente() : null);
			hCliente.setEtiqueta(cliente.getEtiqueta());
			
			//afranco tarea 16055: estos datos ya no se guardan en cliente, sino en clienteContacto.
//			hCliente.setEmail(cliente.getEmail());
//			hCliente.setFax(cliente.getFax());
//			hCliente.setTelefono(cliente.getTelefono());
			
			hCliente.setFxAlta(cliente.getFxAlta());
			hCliente.setFxBaja(cliente.getFxBaja());
			hCliente.setFxFallecimiento(cliente.getFxFallecimiento());
			hCliente.setFxNacimiento(cliente.getFxNacimiento());
			hCliente.setHTipoMovimiento("M");
			hCliente.setIdentificador(cliente.getIdentificador());
			hCliente.setMotivoBaja(cliente.getMotivoBaja());
			hCliente.setProcedencia(cliente.getProcedencia());
			hCliente.setRazonSocial(cliente.getRazonSocial());
			hCliente.setCoUsuarioActualizacion(DatosSesion.getLogin());
			hCliente.setFhActualizacion(Utilidades.getDateActual());

			hClienteDAO.saveOnly(hCliente);
		}
	}

	public void ActualizarCliente(ClienteDTO cliente) throws GadirServiceException {
		this.save(cliente);
	}

	public DAOBase<HClienteDTO, Long> getHClienteDAO() {
		return hClienteDAO;
	}

	public void setHClienteDAO(DAOBase<HClienteDTO, Long> clienteDAO) {
		hClienteDAO = clienteDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ClienteFicticioVO> busquedaNifFicticiosByRazonSocial(
			final String filtro, boolean empieza) throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
						.debug("El filtro definido es null, se devuelve una lista vacia.");
			}
			return Collections.emptyList();
		} else {
			try {
				
				String sql = "select * from ( " +
								"select ga_unidad_urbana.co_unidad_urbana as unidad, " +
									   "count (ga_cliente.identificador) as cont, " +
									   "ga_provincia.NOMBRE as provincia, " + 
									   "ga_municipio.NOMBRE as municipio, " +
									   "ga_calle.SIGLA, " +
									   "ga_calle.NOMBRE_CALLE, " +
									   "ga_unidad_urbana.numero, " +
									   "ga_unidad_urbana.letra, " +
									   "ga_unidad_urbana.bloque, " +
									   "ga_unidad_urbana.escalera, " +
									   "ga_unidad_urbana.planta, " +
									   "ga_unidad_urbana.puerta, " +
									   "ga_unidad_urbana.km, " +
									   "ROWIDTOCHAR(ga_unidad_urbana.rowid) r, "+
									   "ga_cliente.razon_social "+
									   "from ga_cliente, ga_domicilio, ga_unidad_urbana, ga_calle, ga_provincia, ga_municipio " +
								
							    "where ga_unidad_urbana.CO_CALLE = ga_calle.CO_CALLE " +
							 	  "and ga_calle.CO_MUNICIPIO = ga_municipio.CO_MUNICIPIO " +
								  "and ga_calle.CO_PROVINCIA = ga_municipio.CO_PROVINCIA " +
								  "and ga_municipio.CO_PROVINCIA = ga_provincia.CO_PROVINCIA " +
								  "and ga_domicilio.CO_CLIENTE=ga_cliente.CO_CLIENTE  " +
								  "and ga_domicilio.CO_UNIDAD_URBANA=ga_unidad_urbana.CO_UNIDAD_URBANA  " +
								  "and ga_domicilio.BO_FISCAL_MUNICIPAL=1 " +
								  "and (SELECT VALIDA_NIF_FICTICIO (IDENTIFICADOR) FROM DUAL ) = 0 " +
								  "and ga_cliente.CO_CLIENTE_ASOCIADO is null ";
								 
				if (empieza) {
					sql+="and razon_social like '"+filtro.toUpperCase()+"%' ";

				} else {
					sql+="and razon_social like '%"+filtro.toUpperCase()+"%'";
				}
				
				sql+= " group by ga_unidad_urbana.co_unidad_urbana, " +
					   "ga_provincia.NOMBRE, " +
					   "ga_municipio.NOMBRE, " +
					   "ga_calle.SIGLA,  " +
					   "ga_calle.NOMBRE_CALLE, " +
					   "ga_unidad_urbana.numero, " +
					   "ga_unidad_urbana.letra, " +
					   "ga_unidad_urbana.bloque, " +
					   "ga_unidad_urbana.escalera, " +
					   "ga_unidad_urbana.planta, " +
					   "ga_unidad_urbana.puerta, " +
					   "ga_unidad_urbana.km, " +
					   "ga_unidad_urbana.rowid, " +
					   "ga_cliente.razon_social) " +
					   "where cont >1 ";
				sql+= "order by provincia,municipio,SIGLA,NOMBRE_CALLE";
					
				List<Object[]> result = (List<Object[]>) this.getDao().ejecutaSQLQuerySelect(sql);
				List<ClienteFicticioVO> clientes = new ArrayList<ClienteFicticioVO>();
				for (Object[] objeto : result) {
					ClienteFicticioVO cli = new ClienteFicticioVO();
					cli.setCoUnidadUrbana((objeto[0] != null) ? objeto[0].toString(): "");
					cli.setNumClientes(Integer.parseInt(objeto[1].toString()));
					cli.setClienteProvincia(((String) objeto[2] != null) ? (String) objeto[2]: "");
					cli.setClienteMunicipio(((String) objeto[3] != null) ? (String) objeto[3]: "");
					cli.setDomicilioSigla(((String) objeto[4] != null) ? (String) objeto[4]: "");
					cli.setDomicilioNombreVia(((String) objeto[5] != null) ? (String) objeto[5]: "");
					cli.setDomicilioNum(( objeto[6] != null) ? Integer.parseInt(objeto[6].toString()) : 0);
					cli.setDomicilioLtr(((String) objeto[7] != null) ? (String) objeto[7]: "");
					cli.setDomicilioBq(((String) objeto[8] != null) ? (String) objeto[8]: "");
					cli.setDomicilioEsc(((String) objeto[9] != null) ? (String) objeto[9]: "");	
					cli.setDomicilioPla(((String) objeto[10] != null) ? (String) objeto[10]: "");
					cli.setDomicilioPta(((String) objeto[11] != null) ? (String) objeto[11]: "");
					cli.setDomicilioKm(((BigDecimal) objeto[12] != null) ? (BigDecimal) objeto[12]: new BigDecimal(0));
					cli.setRowid((objeto[0] != null) ? objeto[0].toString(): "");
					cli.setClienteNombreRazon(((String) objeto[14] != null) ? (String) objeto[14]: "");
					clientes.add(cli);
				}
			
				return clientes;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de clientes.", e);
			}
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public List<ClienteDomicilioVO> findByAsociadoClienteYDomicilio(String id)
			throws GadirServiceException {
		List<Object> temporal = new ArrayList<Object>();
		List<ClienteDomicilioVO> listadoCliente = new ArrayList<ClienteDomicilioVO>();

		try {
			final HashMap map = new HashMap();

			map.put("coCliente", id);
			temporal = (List<Object>) this.getDao().ejecutaNamedQuerySelect(
					"Cliente.findByAsociadoClienteYDomicilio", map);

			for (Object registro : temporal) {
				final Object[] tupla = (Object[]) registro;
				if(listadoCliente.isEmpty() || (!listadoCliente.isEmpty() && !listadoCliente.get(listadoCliente.size()-1).getClienteNif().equals(tupla[1]))) {				
					ClienteDomicilioVO cliente = new ClienteDomicilioVO();
		
					cliente.setClienteCodigo(tupla[0].toString());
					cliente.setClienteNif(null != tupla[1] ? tupla[1].toString()
							: "");
					cliente.setClienteNombreRazon(null != tupla[2] ? tupla[2]
							.toString() : "");
					cliente.setDomicilioSigla(null != tupla[3] ? tupla[3]
							.toString() : "");
					cliente.setDomicilioNombreVia(null != tupla[4] ? tupla[4]
							.toString() : "");
					cliente.setDomicilioNum(null != tupla[5] ? tupla[5].toString()
							: "");
					cliente.setDomicilioLtr(null != tupla[6] ? tupla[6].toString()
							: "");
					cliente.setDomicilioBq(null != tupla[7] ? tupla[7].toString()
							: "");
					cliente.setDomicilioEsc(null != tupla[8] ? tupla[8].toString()
							: "");
					cliente.setDomicilioPla(null != tupla[9] ? tupla[9].toString()
							: "");
					cliente.setDomicilioPta(null != tupla[10] ? tupla[10]
							.toString() : "");
					cliente.setDomicilioKm(null != tupla[11] ? tupla[11].toString()
							: "");
					cliente.setClienteCodigoPostal(null != tupla[12] ? tupla[12]
							.toString() : "");
					cliente.setClienteMunicipio(null != tupla[13] ? tupla[13]
							.toString() : "");
					cliente.setClienteProvincia(null != tupla[14] ? tupla[14]
							.toString() : "");
					cliente.setClienteUbicacion(null != tupla[15] ? tupla[15]
							.toString() : "");
					cliente.setClienteDomicilioAEAT(null != tupla[16] ? Boolean
							.valueOf((tupla[16].toString().equals("1")) ? "true"
									: "false") : false);
					cliente
							.setClienteDomicilioTributario(null != tupla[17] ? Boolean
									.valueOf((tupla[17].toString().equals("1")) ? "true"
											: "false")
									: false);
					cliente
							.setClienteDomicilioNotificacion(null != tupla[18] ? Boolean
									.valueOf((tupla[18].toString().equals("1")) ? "true"
											: "false")
									: false);
	
					listadoCliente.add(cliente);
				}
			}
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los cliente asociados", e);
		}

		return listadoCliente;
	}
	public List<String> busquedaListaCodigosNifFicticios(String coUnidadUrbana, final String filtro, boolean empieza) throws GadirServiceException{
		
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
						.debug("El filtro definido es null, se devuelve una lista vacia.");
			}
			return Collections.emptyList();
		} else {
			List<String> result = new ArrayList<String>();
			try {
				String sql ="select ga_cliente.CO_CLIENTE from " +
							"ga_cliente, ga_domicilio " +
							"where ga_domicilio.CO_CLIENTE=ga_cliente.CO_CLIENTE  " +
							"and ga_domicilio.BO_FISCAL_MUNICIPAL=1 " +
							"and (SELECT VALIDA_NIF_FICTICIO (IDENTIFICADOR) FROM DUAL ) = 0 " +
							"and ga_domicilio.CO_UNIDAD_URBANA = "+coUnidadUrbana +" ";

				if (empieza) {
					sql+="and ga_cliente.razon_social like '"+filtro.toUpperCase()+"%' ";

				} else {
					sql+="and ga_cliente.razon_social like '%"+filtro.toUpperCase()+"%'";
				}
				List<BigDecimal> lista = (List<BigDecimal>) this.getDao().ejecutaSQLQuerySelect(sql);
				
				for (BigDecimal decimal : lista) {
					result.add(decimal.toString());
				}
				
				return result;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de clientes.", e);
			}
		}
		
	}
	
	public void asociarCliente(List<String> clientes) throws GadirServiceException{
		try {
			//recuperamos la primera posicion del listado donde estara el id del cliente destino
			ClienteDTO clienteDestino = this.findById(Long.parseLong(clientes.get(0)));
			//recuperamos los clientes origen del resto de la lista y a cada uno de ellos le miramos si es destino de
			//otro cliente, en ese caso, los asociariamos tambien al destino.
			for (int i = 1; i < clientes.size(); i++){
				
				List<ClienteDTO> clienteAsociadosAOrigen = this.findFiltered("clienteDTOByCoClienteAsociado.coCliente", Long.parseLong(clientes.get(i)));
				for (ClienteDTO cl : clienteAsociadosAOrigen){
					cl.setClienteDTOByCoClienteAsociado(clienteDestino);
					this.save(cl);
					hclienteBO.guardarHCliente(cl, "M");
				}
				ClienteDTO clienteOrigen = this.findById(Long.parseLong(clientes.get(i)));
				clienteOrigen.setClienteDTOByCoClienteAsociado(clienteDestino);
				this.save(clienteOrigen);
				hclienteBO.guardarHCliente(clienteOrigen, "M");
				
			}
			
			
			
		} catch (Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al asociar el listado de clientes.", e);
		}
		
	
	}
	
	
	public ClienteDTO buscaCliente(Long coCliente, String razonSocial, String nif) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ClienteDTO.class);
		if (Utilidades.isNotEmpty(nif)){
			criteria.add(Restrictions.eq("identificador",nif));
		}
		if (Utilidades.isNotEmpty(razonSocial)){
			criteria.add(Restrictions.eq("razonSocial",razonSocial.toUpperCase()));
		}
		if (coCliente != null){
			criteria.add(Restrictions.eq("coCliente",coCliente));
		}
		
		 List<ClienteDTO> lista  = this.getDao().findByCriteria(criteria);
		 if (lista.size() == 1){
			 return lista.get(0);
		 }else {
			 return null;
		 }
		
	}
	
	private DetachedCriteria getCriterio(boolean ordenar, String nombre, String filtroNIF, String codProvincia, String codMunicipio, String sigla, String nombreCalle, String numero, String letra, String bloque, String escalera, String planta, String puerta, String km, boolean contiene, boolean incluirAsociados) throws GadirServiceException {
		DetachedCriteria dc = DetachedCriteria.forClass(ClienteDTO.class, "clienteAlias");

		try {
			
			if (Utilidades.isEmpty(nombre) &&
					Utilidades.isEmpty(filtroNIF) &&
					Utilidades.isEmpty(codProvincia) && 
					Utilidades.isEmpty(codMunicipio) && 
					Utilidades.isEmpty(sigla) && 
					Utilidades.isEmpty(nombreCalle) && 
					Utilidades.isEmpty(numero) &&
					Utilidades.isEmpty(letra) &&
					Utilidades.isEmpty(bloque) &&
					Utilidades.isEmpty(escalera) && 
					Utilidades.isEmpty(planta) && 
					Utilidades.isEmpty(puerta) && 
					Utilidades.isEmpty(km)) {
				throw new GadirServiceException("Introduzca, al menos, un criterio de búsqueda");
			}
			if (!incluirAsociados) {
				dc.add(Restrictions.isNull("clienteDTOByCoClienteAsociado"));
			}
			
			if (Utilidades.isNotEmpty(nombre)) {
				if (contiene) {
					dc.add(Restrictions.sqlRestriction("translate(UPPER(this_.razon_social), 'ÁÉÍÓÚÖ', 'AEIOUO') like translate('%" + nombre.toUpperCase() + "%', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
//					dc.add(Restrictions.sqlRestriction("contains(this_.razon_social, '%" + nombre + "%')>0"));
				} else {
					dc.add(Restrictions.sqlRestriction("translate(UPPER(this_.razon_social), 'ÁÉÍÓÚÖ', 'AEIOUO') like translate('" + nombre.toUpperCase() + "%', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
				}
			}
			
			if (Utilidades.isNotEmpty(filtroNIF)) {
				dc.add(Restrictions.eq("identificador", filtroNIF.toUpperCase()));
			}
			
			if (Utilidades.isNotEmpty(codProvincia) || 
					Utilidades.isNotEmpty(codMunicipio) || 
					Utilidades.isNotEmpty(sigla) || 
					Utilidades.isNotEmpty(nombreCalle) || 
					Utilidades.isNotEmpty(numero) || 
					Utilidades.isNotEmpty(letra) || 
					Utilidades.isNotEmpty(bloque) || 
					Utilidades.isNotEmpty(escalera) || 
					Utilidades.isNotEmpty(planta) || 
					Utilidades.isNotEmpty(puerta) || 
					Utilidades.isNotEmpty(km)) {
				DetachedCriteria otro = DetachedCriteria.forClass(DomicilioDTO.class, "otroAlias");
				otro.setProjection(Projections.property("coDomicilio"));
				otro.createAlias("otroAlias.unidadUrbanaDTO", "uu");
				otro.createAlias("uu.calleDTO", "c");
				otro.add(Restrictions.eqProperty("otroAlias.clienteDTO.coCliente", "clienteAlias.coCliente"));
				otro.add(Restrictions.eq("boFiscalMunicipal",new Boolean(true)));
				
				if (Utilidades.isNotEmpty(codProvincia)) {
					otro.add(Restrictions.eq("c.municipioDTO.id.coProvincia",codProvincia));
				}
				if (Utilidades.isNotEmpty(codMunicipio)) {
					otro.add(Restrictions.eq("c.municipioDTO.id.coMunicipio",codMunicipio));
				}
				if (Utilidades.isNotEmpty(sigla)) {
					otro.add(Restrictions.eq("c.sigla",sigla));
				}
				if (Utilidades.isNotEmpty(nombreCalle)) {
					otro.add(Restrictions.sqlRestriction("translate(UPPER(c2_.nombre_calle), 'ÁÉÍÓÚÖ', 'AEIOUO') like translate('%" + nombreCalle.toUpperCase() + "%', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
				}
				if (Utilidades.isNotEmpty(numero)) {
					otro.add(Restrictions.eq("uu.numero",Integer.valueOf(numero)));
				}
				if (Utilidades.isNotEmpty(letra)) {
					otro.add(Restrictions.eq("uu.letra",letra));
				}
				if (Utilidades.isNotEmpty(bloque)) {
					otro.add(Restrictions.eq("uu.bloque",bloque));
				}
				if (Utilidades.isNotEmpty(escalera)) {
					otro.add(Restrictions.eq("uu.escalera",escalera));
				}
				if (Utilidades.isNotEmpty(planta)) {
					otro.add(Restrictions.eq("uu.planta",planta));
				}
				if (Utilidades.isNotEmpty(puerta)) {
					otro.add(Restrictions.eq("uu.puerta",puerta));
				}
				if (Utilidades.isNotEmpty(km)) {
					otro.add(Restrictions.eq("uu.km",new BigDecimal(km)));
				}
				
				dc.add(Subqueries.exists(otro));
			}
	
			if (ordenar) {
				dc.addOrder(Order.asc("razonSocial"));
			}
		} catch (Exception e) {
			LOG.error("Error al calcular el criterio de busqueda en selección de cliente.", e);
			throw new GadirServiceException(e.getMessage());
		}
		return dc;
	}
	
	public int countByCriteria(String nombre, String filtroNIF, String codProvincia, String codMunicipio, String sigla, String nombreCalle, String numero, String letra, String bloque, String escalera, String planta, String puerta, String km, boolean contiene, boolean incluirAsociados) throws GadirServiceException {
		return countByCriteria(getCriterio(false, nombre, filtroNIF, codProvincia, codMunicipio, sigla, nombreCalle, numero, letra, bloque, escalera, planta, puerta, km, contiene, incluirAsociados));
	}

	public List<ClienteDTO> findByCriteria(String nombre, String filtroNIF, String codProvincia, String codMunicipio, String sigla, String nombreCalle, String numero, String letra, String bloque, String escalera, String planta, String puerta, String km, boolean contiene, boolean incluirAsociados, int inicio, int limite) throws GadirServiceException {
		return findByCriteria(getCriterio(true, nombre, filtroNIF, codProvincia, codMunicipio, sigla, nombreCalle, numero, letra, bloque, escalera, planta, puerta, km, contiene, incluirAsociados), inicio, limite);
	}

	public boolean existMovilAsociado(String cliente) throws GadirServiceException{
		
		if(clienteContactoBO.getContactoPreferenteByTipo(new ClienteDTO(Long.valueOf(cliente)), "M") != null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void save(ClienteDTO cliente, DomicilioDTO domicilio, String telefono, String movil, String fax, String email) throws GadirServiceException {
		try {
			
			this.save(cliente);
			
			daoDomicilio.save(domicilio);
			
			if(!Utilidades.isEmpty(telefono)){
				daoClienteContacto.save(new ClienteContactoDTO(cliente, ClienteContactoBO.TIPO_TELEFONO, telefono, true));
			}
			
			if(!Utilidades.isEmpty(movil)){
				daoClienteContacto.save(new ClienteContactoDTO(cliente, ClienteContactoBO.TIPO_MOVIL, movil, true));
			}
			
			if(!Utilidades.isEmpty(fax)){
				daoClienteContacto.save(new ClienteContactoDTO(cliente, ClienteContactoBO.TIPO_FAX, fax, true));
			}
			
			if(!Utilidades.isEmpty(email)){
				daoClienteContacto.save(new ClienteContactoDTO(cliente, ClienteContactoBO.TIPO_EMAIL, email, true));
			}
		} catch (GadirServiceException e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al asociar el listado de clientes.", e);
		}
	}
	
	public void auditorias(ClienteDTO transientObject, Boolean saveOnly)
			throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());

		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	/**
	 * @return the hclienteBO
	 */
	public HClienteBO getHclienteBO() {
		return hclienteBO;
	}

	/**
	 * @param hclienteBO the hclienteBO to set
	 */
	public void setHclienteBO(HClienteBO hclienteBO) {
		this.hclienteBO = hclienteBO;
	}

	public ClienteContactoBO getClienteContactoBO() {
		return clienteContactoBO;
	}

	public void setClienteContactoBO(ClienteContactoBO clienteContactoBO) {
		this.clienteContactoBO = clienteContactoBO;
	}
}
