package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.internal.OracleTypes;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.BienEmbargableBO;
import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.bo.CargoSubcargoBO;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.HCalleBO;
import es.dipucadiz.etir.comun.bo.HClienteBO;
import es.dipucadiz.etir.comun.bo.HUnidadUrbanaBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.bo.UnidadAdministrativaBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.BienEmbargableDTO;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CargoSubcargoDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.HCalleDTO;
import es.dipucadiz.etir.comun.dto.HClienteDTO;
import es.dipucadiz.etir.comun.dto.HUnidadUrbanaDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Formato;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.HistoricoGenericoVO;
import es.dipucadiz.etir.sb07.comun.vo.DomicilioVO;


public class HistoricoGenericoBOImpl extends StoredProcedure implements Serializable {
	private static final long serialVersionUID = -4153149574621248766L;

	private DAOBase<HistoricoGenericoVO, Long> dao;
	
	public DAOBase<HistoricoGenericoVO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<HistoricoGenericoVO, Long> dao) {
		this.dao = dao;
	}
	
	public HistoricoGenericoBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.HistoricoGenerico");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", OracleTypes.CURSOR,
				new RowMapper() {
					public Map<String, Object> mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
						final Map<String, Object> result = new HashMap<String, Object>();
						final ResultSetMetaData metaData = resultSet.getMetaData();
						int columna = 0;
						for (int i=1; i<=metaData.getColumnCount(); i++) {
							if ("FH_ACTUALIZACION".equals(metaData.getColumnName(i))) {
								result.put("FXH", resultSet.getTimestamp(i));
							} else if ("CO_USUARIO_ACTUALIZACION".equals(metaData.getColumnName(i))) {
								result.put("USU", resultSet.getString(i));
							} else if ("H_TIPO_MOVIMIENTO".equals(metaData.getColumnName(i))) {
								result.put("MOV", resultSet.getString(i));
							} else {
								columna++;
								result.put("COL" + columna, resultSet.getString(i));
							}
						}
						return result;
					}
				}));
		declareParameter(new SqlParameter("e_tabla", Types.VARCHAR));
		declareParameter(new SqlParameter("e_select", Types.VARCHAR));
		declareParameter(new SqlParameter("e_where", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoGenericoVO> execute(String tabla, String select, String where, Date fechaDesde, Date fechaHasta, String usuario, String movimiento, int porPagina, int page) {
		final Map<String, String> params = new HashMap<String, String>();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmSS");
		
		select = select.trim();
		String select_res;
		
		if (Utilidades.isNotEmpty(select))
			select_res = select + ", FH_ACTUALIZACION, CO_USUARIO_ACTUALIZACION, H_TIPO_MOVIMIENTO ";
		else
			select_res = "FH_ACTUALIZACION, CO_USUARIO_ACTUALIZACION, H_TIPO_MOVIMIENTO ";
		
		String select_general = "(SELECT a.*, ROWNUM rn FROM";
		String select_anidado;
		
		if (Utilidades.isNotEmpty(select))
			select_anidado = "(SELECT " + select + ", ";
		
		else
			select_anidado = "(SELECT ";
		
		select_anidado += "FH_ACTUALIZACION, CO_USUARIO_ACTUALIZACION, H_TIPO_MOVIMIENTO FROM " + tabla;
		
		if (Utilidades.isNotEmpty(usuario)) {
			where += " AND CO_USUARIO_ACTUALIZACION='" + usuario + "'";
		}
		if (Utilidades.isNotEmpty(fechaDesde)) {
			StringBuilder fecha_d = new StringBuilder( dateformat.format( fechaDesde ) );
			where += " AND FH_ACTUALIZACION >=to_timestamp('" + fecha_d + "', 'YYYYMMDDHH24MISS')";
		}
		if (Utilidades.isNotEmpty(fechaHasta)) {
			StringBuilder fecha_h = new StringBuilder( dateformat.format( fechaHasta ) );
			where += " AND FH_ACTUALIZACION <=to_timestamp('" + fecha_h + "', 'YYYYMMDDHH24MISS')";
		}
		
		if(Utilidades.isNotEmpty(movimiento)) {
			where += " AND H_TIPO_MOVIMIENTO ='" + movimiento + "'";		
		}
		
		where += " order by FH_ACTUALIZACION desc, id desc"; //caca
		
		select_anidado += " WHERE " + where + ") a";
		select_anidado += " WHERE rownum <= " + page*porPagina +")";
		
		String where_final = "rn >= " + ((page-1)*porPagina + 1);
		
		select_general += select_anidado;
		
		params.put("e_tabla", select_general);
		params.put("e_select", select_res);
		params.put("e_where", where_final);

		final Map<?, ?> results = execute(params);
		List<Map<String, Object>> resultMap = (List<Map<String, Object>>) results.get("result");
		List<HistoricoGenericoVO> resultado = new ArrayList<HistoricoGenericoVO>();
		for (Map<String, Object> result : resultMap) {
			HistoricoGenericoVO historicoGenericoVO = new HistoricoGenericoVO();
			List<String> columnas = new ArrayList<String>();
			
			String ejecucion = "";
			
			for (int i=1; i<100; i++) {
				if (result.containsKey("COL" + i)) {
					columnas.add((String) result.get("COL" + i));
				} else {
					break;
				}
			}
			
			String[] campos = select.split(",");
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			
			List<Integer> columnasAlinearDerecha = new ArrayList<Integer>();
			
			EjecucionBO ejecucionBO = null;
			ClienteBO clienteBO = null;
			HClienteBO hClienteBO = null;
			DomicilioBO domicilioBO = null;
			BienEmbargableBO bienEmbargableBO = null;
			CargoSubcargoBO cargoSubcargoBO = null;
			
			//MIRAMOS CASOS ESPECIALES A TRATAR DE NOMBRES DE COLUMNAS (booleano, fechas, ejecución, cliente, domicilio...) 
			
			for(int j=0; j< campos.length; j++){
				if (campos[j] != null) campos[j] = campos[j].trim();
				
				if(campos[j].startsWith("BO_") && Utilidades.isNotNull(columnas.get(j))){
					if(columnas.get(j).equals("1")){
						columnas.set(j, "Sí");					
					}
					else{
						columnas.set(j, "No");		
					}
				}
				else if(campos[j].startsWith("FX_") || campos[j].startsWith("FH_")){
				
					if(Utilidades.isNotNull(columnas.get(j)))
						columnas.set(j, formateador.format(Utilidades.strutsFormatToDate(columnas.get(j))));
					else
						columnas.set(j, "");
				}
				else if(campos[j].startsWith("CO_EJECUCION") && Utilidades.isNotNull(columnas.get(j))){
					
					if (ejecucionBO == null) {
						ejecucionBO = (EjecucionBO) GadirConfig.getBean("ejecucionBO");
					}
					EjecucionDTO ejDTO = null;
					
					try {
						 ejDTO = ejecucionBO.findById(Long.parseLong(columnas.get(j)));
					} catch (GadirServiceException e) {
						e.printStackTrace();
					}
					
					if(ejDTO != null)
						ejecucion = ejDTO.getRowid();
				}
				
				else if(campos[j].startsWith("IMPORTE") || campos[j].startsWith("IM_")){
						
					 if(Utilidades.isNotNull(columnas.get(j))){
						try {
							 columnas.set(j, Formato.formatearDato(columnas.get(j).replace('.', ','), "I", 13.2, "V"));
						} catch (GadirServiceException e) {
							e.printStackTrace();
						}
					 }
					 else{
						 columnas.set(j, "");
					 }
					 
					 if(!columnasAlinearDerecha.contains(j))
						 columnasAlinearDerecha.add(j);
				}
				else if((campos[j].startsWith("CO_CLIENTE") || campos[j].equals("CO_REPRESENTANTE")) && Utilidades.isNotNull(columnas.get(j))){
					
					ClienteDTO clDTO = null;
					Long coCliente =  Long.parseLong(columnas.get(j));
					if (clienteBO == null) {
						clienteBO = (ClienteBO) GadirConfig.getBean("clienteBO");
					}
					try {
						clDTO = clienteBO.findById(coCliente);
					} catch (GadirServiceException e) {
						e.printStackTrace();
					}
					
					if(clDTO != null){
						columnas.set(j, clDTO.getIdentificador());
					}
					else{
						
						List<HClienteDTO> listaHClDTO = new ArrayList<HClienteDTO>();
						if (hClienteBO == null) {
							hClienteBO = (HClienteBO) GadirConfig.getBean("hClienteBO");
						}
						try {
							listaHClDTO = hClienteBO.findFiltered("coCliente", coCliente);
						} catch (GadirServiceException e) {
							e.printStackTrace();
						}
						
						if(!listaHClDTO.isEmpty()){
							columnas.set(j, listaHClDTO.get(0).getIdentificador());
						}
						else{
							columnas.set(j, "");
						}
					}
					
				}
				
				else if(campos[j].startsWith("CO_UNIDAD_URBANA") && Utilidades.isNotNull(columnas.get(j))){
					columnas.set(j, getDescripctionUnidadUrbana(columnas.get(j), result));
				}

				else if(campos[j].startsWith("CO_DOMICILIO") && Utilidades.isNotNull(columnas.get(j))){
					// Se busca la descripción no estructurada de la unidad urbana del domicilio.
					// Se puede buscar la coUnidadUrbana directamente en GA_DOMICILIO ya que este dato nunca se modifica.
					if (domicilioBO == null) {
						domicilioBO = (DomicilioBO) GadirConfig.getBean("domicilioBO");
					}
					Long coDomicilio = Long.parseLong(columnas.get(j));
					try {
						DomicilioDTO domicilioDTO = domicilioBO.findById(coDomicilio);
						columnas.set(j, getDescripctionUnidadUrbana(domicilioDTO.getUnidadUrbanaDTO().getCoUnidadUrbana().toString(), result));
					} catch (GadirServiceException e) {
						e.printStackTrace();
					}
				}
				
				else if (campos[j].startsWith("CO_BIEN_EMBARGABLE")) {
					if (bienEmbargableBO == null) {
						bienEmbargableBO = (BienEmbargableBO) GadirConfig.getBean("bienEmbargableBO");
					}
					if (Utilidades.isEmpty(columnas.get(j))) {
						columnas.set(j, "Sin bien embargable");
					} else {
						try {
							Long coBienEmbargable = Long.parseLong(columnas.get(j));
							BienEmbargableDTO bienEmbargableDTO = bienEmbargableBO.findById(coBienEmbargable);
							columnas.set(j, bienEmbargableDTO.getIdentificacion());
						} catch (GadirServiceException e) {
							columnas.set(j, columnas.get(j));
						}
					}
				}
				
				else if (campos[j].startsWith("CO_BD_DOCUMENTAL_GRUPO")) {
					if (Utilidades.isNotEmpty(columnas.get(j))) {
						columnas.set(j, "Sí");
					}
				}
				
				else if (campos[j].startsWith("CO_CARGO_SUBCARGO")) {
					if (Utilidades.isNotEmpty(columnas.get(j))) {
						try {
							if (cargoSubcargoBO == null) {
								cargoSubcargoBO = (CargoSubcargoBO) GadirConfig.getBean("cargoSubcargoBO");
							}
							CargoSubcargoDTO cargoSubcargoDTO = cargoSubcargoBO.findByIdInitialized(Long.parseLong(columnas.get(j)), new String[] {"cargoDTO"});
							if (cargoSubcargoDTO != null && cargoSubcargoDTO.getCargoDTO() != null && cargoSubcargoDTO.getCargoDTO().getNuCargo() != 9999) {
								columnas.set(j, cargoSubcargoDTO.getCargoDTO().getNuCargo() + "/" + cargoSubcargoDTO.getCargoDTO().getAnoCargo() + "/" + cargoSubcargoDTO.getCargoDTO().getMunicipioDTO().getId().getCoProvincia() + cargoSubcargoDTO.getCargoDTO().getMunicipioDTO().getId().getCoMunicipio());
							} else {
								columnas.set(j, "");
							}
						} catch (GadirServiceException e) {
							columnas.set(j, "");
						}
					}
				}
			}
			
			
			
			
			historicoGenericoVO.setColumnas(columnas);
			historicoGenericoVO.setColumnasAlinearDerecha(columnasAlinearDerecha);
			Date fhActualizacion = (Date) result.get("FXH");
			historicoGenericoVO.setFecha(Utilidades.dateToDDMMYYYY(fhActualizacion));
			historicoGenericoVO.setHora(Utilidades.dateToHHMM(fhActualizacion));
			historicoGenericoVO.setMovimiento((String) result.get("MOV"));
			historicoGenericoVO.setUsuario((String) result.get("USU"));
			historicoGenericoVO.setEjecucion(ejecucion);
			
			resultado.add(historicoGenericoVO);
		}
		
		return resultado;
	}
	
	private String getDescripctionUnidadUrbana(String valorColumna, Map<String, Object> valoresFila) {
		String resultadoMetodo = valorColumna;
		
		DomicilioVO domicilioVO = null;
		
		MunicipioBO municipioBO = (MunicipioBO) GadirConfig.getBean("municipioBO");
		ProvinciaBO provinciaBO = (ProvinciaBO) GadirConfig.getBean("provinciaBO");
		UnidadAdministrativaBO unidadAdministrativaBO = (UnidadAdministrativaBO) GadirConfig.getBean("unidadAdministrativaBO");
		CalleUbicacionBO calleUbicacionBO = (CalleUbicacionBO) GadirConfig.getBean("calleUbicacionBO");
		
		HUnidadUrbanaBO hUnidadUrbanaBO = (HUnidadUrbanaBO) GadirConfig.getBean("hunidadUrbanaBO");
		Long coUnidadUrbana =  Long.parseLong(valorColumna);
		
		//Vamos a buscar aquellos registros del histórico con esa coUnidadUrbana que sean anteriores a la fecha del 
		//registro de GA_H_DOMICILIO. Nos conviene coger el más reciente entre ellos.
		
		DetachedCriteria criterioHUU = DetachedCriteria.forClass(HUnidadUrbanaDTO.class);
		
		criterioHUU.add(Restrictions.eq("coUnidadUrbana", coUnidadUrbana));
		criterioHUU.add(Restrictions.lt("fhActualizacion", (Date) valoresFila.get("FXH")));
		criterioHUU.addOrder(Order.desc("fhActualizacion"));  
		criterioHUU.addOrder(Order.desc("id"));
		
		List<HUnidadUrbanaDTO> listaHUnidadUrbana = new ArrayList<HUnidadUrbanaDTO>();
		
		try {
			listaHUnidadUrbana = hUnidadUrbanaBO.findByCriteria(criterioHUU);
		} catch (GadirServiceException e) {
			e.printStackTrace();
		}
		
		if(!listaHUnidadUrbana.isEmpty()){
			HUnidadUrbanaDTO unidadUrbanaDTO = listaHUnidadUrbana.get(0);
			Long coCalle = unidadUrbanaDTO.getCoCalle();
			
			HCalleBO hCalleBO = (HCalleBO) GadirConfig.getBean("hcalleBO");
			List<HCalleDTO> listaHCalle = new ArrayList<HCalleDTO>();
			
			DetachedCriteria criteriaHC = DetachedCriteria.forClass(HCalleDTO.class);
			
			criteriaHC.add(Restrictions.eq("coCalle", coCalle));
			criteriaHC.add(Restrictions.lt("fhActualizacion", (Date) valoresFila.get("FXH")));
			criteriaHC.addOrder(Order.desc("fhActualizacion"));  
			criteriaHC.addOrder(Order.desc("id"));  
			
			try {
				listaHCalle = hCalleBO.findByCriteria(criteriaHC);
			} catch (GadirServiceException e) {
				e.printStackTrace();
			}
			
			if(!listaHCalle.isEmpty()){
				
				//Rellenamos con los datos del registro de GA_H_UNIDAD_URBANA y GA_H_CALLE el domicilioVO
				
				HCalleDTO calleDTO = listaHCalle.get(0);
				domicilioVO = new DomicilioVO();
				domicilioVO.setDomicilioKm(unidadUrbanaDTO.getKm());
				domicilioVO.setDomicilioNum(unidadUrbanaDTO.getNumero());
				domicilioVO.setDomicilioNombreVia(calleDTO.getNombreCalle());
				domicilioVO.setDomicilioSigla(calleDTO.getSigla());

				try {
					domicilioVO.setMunicipio(municipioBO.findById(new MunicipioDTOId(calleDTO.getCoProvincia(), calleDTO.getCoMunicipio())).getNombre());
					domicilioVO.setProvincia(provinciaBO.findById(calleDTO.getCoProvincia()).getNombre());
				} catch (GadirServiceException e) {
					e.printStackTrace();
				}
				
				domicilioVO.setDomicilioLetra(unidadUrbanaDTO.getLetra());
				domicilioVO.setDomicilioBloque(unidadUrbanaDTO.getBloque());
				domicilioVO.setDomicilioEscalera(unidadUrbanaDTO.getEscalera());
				domicilioVO.setDomicilioPlanta(unidadUrbanaDTO.getPlanta());
				domicilioVO.setDomicilioPuerta(unidadUrbanaDTO.getPuerta());
				domicilioVO.setDomicilioCp(unidadUrbanaDTO.getCp());
				
				if (Utilidades.isNotEmpty(unidadUrbanaDTO.getProcedencia())) {
					KeyValue tfuepro = TablaGt.getCodigoDescripcion(TablaGt.TABLA_FUENTE_PROCEDENCIA, unidadUrbanaDTO.getProcedencia());
					if(tfuepro != null){
						domicilioVO.setProcedencia(tfuepro.getValue());
					}
					if (Utilidades.isEmpty(domicilioVO.getProcedencia())) {

						UnidadAdministrativaDTO unidad = null;
						try {
							unidad = unidadAdministrativaBO.findById(unidadUrbanaDTO.getProcedencia());
						} catch (GadirServiceException e) {
							e.printStackTrace();
						}
						if (Utilidades.isNotEmpty(unidad.getNombre())) {
							domicilioVO.setProcedencia(unidad.getNombre());
						}
					}
				}	
				
				if (Utilidades.isNotNull(calleDTO.getCoCalleUbicacion())) {
											
					try {
						domicilioVO.setUbicacion(calleUbicacionBO.findById(calleDTO.getCoCalleUbicacion()).getUbicacion());
					} catch (GadirServiceException e) {
						e.printStackTrace();
					}
				}

				//FINALMENTE PONEMOS LA DESCRIPCIÓN EN EL DOMICILIO
				resultadoMetodo = domicilioVO.getDescripcionDomicilio();
				
			}
		}
		
		if(domicilioVO == null){
		
			//En caso de que no encontremos ningún registro, lo trataremos de buscar directamente en GA_UNIDAD_URBANA
			
			UnidadUrbanaBO unidadUrbanaBO = (UnidadUrbanaBO) GadirConfig.getBean("unidadUrbanaBO");
			UnidadUrbanaDTO unidadUrbanaDTO = null;
			try {
				unidadUrbanaDTO = unidadUrbanaBO.findById(coUnidadUrbana);
			} catch (GadirServiceException e) {
				e.printStackTrace();
			}
			
			if(unidadUrbanaDTO != null){
				
				Long coCalle = unidadUrbanaDTO.getCalleDTO().getCoCalle();
				
				//En caso de que no encontremos ningún registro, lo trataremos de buscar directamente en GA_CALLE
				CalleBO calleBO = (CalleBO) GadirConfig.getBean("calleBO");
				CalleDTO calleDTO = null;
				try {
					calleDTO = calleBO.findById(coCalle);
				} catch (GadirServiceException e) {
					e.printStackTrace();
				}
				
				if(calleDTO != null){
					
					domicilioVO = new DomicilioVO();
					domicilioVO.setDomicilioKm(unidadUrbanaDTO.getKm());
					domicilioVO.setDomicilioNum(unidadUrbanaDTO.getNumero());
					domicilioVO.setDomicilioNombreVia(calleDTO.getNombreCalle());
					domicilioVO.setDomicilioSigla(calleDTO.getSigla());
					
					try {
						domicilioVO.setMunicipio(municipioBO.findById(new MunicipioDTOId(calleDTO.getMunicipioDTO().getId().getCoProvincia(), calleDTO.getMunicipioDTO().getId().getCoMunicipio())).getNombre());
						domicilioVO.setProvincia(provinciaBO.findById(calleDTO.getMunicipioDTO().getId().getCoProvincia()).getNombre());
					} catch (GadirServiceException e) {
						e.printStackTrace();
					}
					
					domicilioVO.setDomicilioLetra(unidadUrbanaDTO.getLetra());
					domicilioVO.setDomicilioBloque(unidadUrbanaDTO.getBloque());
					domicilioVO.setDomicilioEscalera(unidadUrbanaDTO.getEscalera());
					domicilioVO.setDomicilioPlanta(unidadUrbanaDTO.getPlanta());
					domicilioVO.setDomicilioPuerta(unidadUrbanaDTO.getPuerta());
					domicilioVO.setDomicilioCp(unidadUrbanaDTO.getCp());
					
					if (Utilidades.isNotEmpty(unidadUrbanaDTO.getProcedencia())) {
						KeyValue tfuepro = TablaGt.getCodigoDescripcion(TablaGt.TABLA_FUENTE_PROCEDENCIA, unidadUrbanaDTO.getProcedencia());
						if(tfuepro != null){
							domicilioVO.setProcedencia(tfuepro.getValue());
						}
						if (Utilidades.isEmpty(domicilioVO.getProcedencia())) {

							UnidadAdministrativaDTO unidad = null;
							try {
								unidad = unidadAdministrativaBO.findById(unidadUrbanaDTO.getProcedencia());
							} catch (GadirServiceException e) {
								e.printStackTrace();
							}
							if (Utilidades.isNotEmpty(unidad.getNombre())) {
								domicilioVO.setProcedencia(unidad.getNombre());
							}
						}
					}	
					
					if (Utilidades.isNotNull(calleDTO.getCalleUbicacionDTO())) {
						
						try {
							domicilioVO.setUbicacion(calleUbicacionBO.findById(calleDTO.getCalleUbicacionDTO().getCoCalleUbicacion()).getUbicacion());
						} catch (GadirServiceException e) {
							e.printStackTrace();
						}
					}
					
					//FINALMENTE PONEMOS LA DESCRIPCIÓN EN EL DOMICILIO
					resultadoMetodo = domicilioVO.getDescripcionDomicilio();
					
				}
				else{
					resultadoMetodo = "";
				}
				
			}
			else{
				resultadoMetodo = "";
			}
		}
		
		return resultadoMetodo;
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoGenericoVO> executeCasillas(String tabla, String select, String where, Date fechaDesde, Date fechaHasta, String usuario, String movimiento, int porPagina, int page) {
		final Map<String, String> params = new HashMap<String, String>();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmSS");
		
		select = select.trim();
		String select_res;
		
		if (Utilidades.isNotEmpty(select))
			select_res = select + ", FH_ACTUALIZACION, CO_USUARIO_ACTUALIZACION ";
		else
			select_res = "FH_ACTUALIZACION, CO_USUARIO_ACTUALIZACION ";
		
		String select_general = "(SELECT a.*, ROWNUM rn FROM";
		String select_anidado;
		
		if (Utilidades.isNotEmpty(select))
			select_anidado = "(SELECT " + select + ", ";
		
		else
			select_anidado = "(SELECT ";
		
		select_anidado += "FH_ACTUALIZACION, CO_USUARIO_ACTUALIZACION FROM " + tabla;
		
		if (Utilidades.isNotEmpty(usuario)) {
			where += " AND CO_USUARIO_ACTUALIZACION='" + usuario + "'";
		}
		if (Utilidades.isNotEmpty(fechaDesde)) {
			StringBuilder fecha_d = new StringBuilder( dateformat.format( fechaDesde ) );
			where += " AND FH_ACTUALIZACION >=to_timestamp('" + fecha_d + "', 'YYYYMMDDHH24MISS')";
		}
		if (Utilidades.isNotEmpty(fechaHasta)) {
			StringBuilder fecha_h = new StringBuilder( dateformat.format( fechaHasta ) );
			where += " AND FH_ACTUALIZACION <=to_timestamp('" + fecha_h + "', 'YYYYMMDDHH24MISS')";
		}
		
		where += " order by FH_ACTUALIZACION desc, id desc";  
		
		select_anidado += " WHERE " + where + ") a";
		select_anidado += " WHERE rownum <= " + page*porPagina +")";
		
		String where_final = "rn >= " + ((page-1)*porPagina + 1);
		
		select_general += select_anidado;
		
		params.put("e_tabla", select_general);
		params.put("e_select", select_res);
		params.put("e_where", where_final);

		final Map<?, ?> results = execute(params);
		List<Map<String, Object>> resultMap = (List<Map<String, Object>>) results.get("result");
		List<HistoricoGenericoVO> resultado = new ArrayList<HistoricoGenericoVO>();
		for (Map<String, Object> result : resultMap) {
			HistoricoGenericoVO historicoGenericoVO = new HistoricoGenericoVO();
			List<String> columnas = new ArrayList<String>();
			for (int i=1; i<100; i++) {
				if (result.containsKey("COL" + i)) {
					columnas.add((String) result.get("COL" + i));
				} else {
					break;
				}
			}
			
			String[] campos = select.split(",");
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			for(int j=0; j< campos.length; j++){
				if (campos[j] != null) campos[j] = campos[j].trim();
				
				if(campos[j].startsWith("BO_") && Utilidades.isNotNull(columnas.get(j))){
					if(columnas.get(j).equals("1")){
						columnas.set(j, "Sí");					
					}
					else{
						columnas.set(j, "No");		
					}
				}
				else if(campos[j].startsWith("FX_") || campos[j].startsWith("FH_")){
					
					if(Utilidades.isNotNull(columnas.get(j)))
						columnas.set(j, formateador.format(Utilidades.strutsFormatToDate(columnas.get(j))));
					else
						columnas.set(j, "");
				}

			}
			
			historicoGenericoVO.setColumnas(columnas);
			Date fhActualizacion = (Date) result.get("FXH");
			historicoGenericoVO.setFecha(Utilidades.dateToDDMMYYYY(fhActualizacion));
			historicoGenericoVO.setHora(Utilidades.dateToHHMM(fhActualizacion));
			historicoGenericoVO.setMovimiento((String) result.get("MOV"));
			historicoGenericoVO.setUsuario((String) result.get("USU"));
			resultado.add(historicoGenericoVO);
		}
		
		return resultado;
	}
		
	@SuppressWarnings("unchecked")
	public Integer execute(String tabla, String select, String where, Date fechaDesde, Date fechaHasta, String usuario, String movimiento) {
		final Map<String, String> params = new HashMap<String, String>();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmSS");
		
		select = select.trim();
		if (Utilidades.isNotEmpty(select)) {
			select += ", ";
		}
		select += "COUNT(*)";
		if (Utilidades.isNotEmpty(usuario)) {
			where += " AND CO_USUARIO_ACTUALIZACION='" + usuario + "'";
		}
		if (Utilidades.isNotEmpty(fechaDesde)) {
			StringBuilder fecha_d = new StringBuilder( dateformat.format( fechaDesde ) );
			where += " AND FH_ACTUALIZACION >=to_timestamp('" + fecha_d + "', 'YYYYMMDDHH24MISS')";
		}
		if (Utilidades.isNotEmpty(fechaHasta)) {
			StringBuilder fecha_h = new StringBuilder( dateformat.format( fechaHasta ) );
			where += " AND FH_ACTUALIZACION <=to_timestamp('" + fecha_h + "', 'YYYYMMDDHH24MISS')";
		}
		
		if(Utilidades.isNotEmpty(movimiento)) {
			where += " AND H_TIPO_MOVIMIENTO ='" + movimiento + "'";		
		}
		
		params.put("e_tabla", tabla);
		params.put("e_select", select);
		params.put("e_where", where);
		final Map<?, ?> results = execute(params);
		List<Map<String, Object>> resultMap = (List<Map<String, Object>>) results.get("result");
				
		int resultado = Integer.parseInt((String) resultMap.get(0).get("COL1"));
		
		return resultado;
	}
	 
}
