/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.lob.SerializableClob;

import es.dipucadiz.etir.comun.acegisecurity.GadirUserDetails;
import es.dipucadiz.etir.comun.bo.CodigoTerritorialCompatBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetCodtersCompatsBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetConceptosAnoCursoBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetConceptosBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetConceptosConvenioBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetConceptosGestionBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetConceptosModeloBO;
import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetMunicipiosBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.config.ParametrosConfig;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialCompatDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.PaisDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Controles territoriales y accesos por convenio 
 * @author jronnols y jbenitac
 *
 */
final public class ControlTerritorial {

	private static TerrGetMunicipiosBO codterGetMunicipiosBO;
	private static TerrGetConceptosBO codterGetConceptosBO;
	private static TerrGetConceptosModeloBO codterGetConceptosModeloBO;
	private static TerrGetCodtersCompatsBO codterGetCodtersCompatsBO;
	private static TerrGetConceptosAnoCursoBO codterGetConceptosAnoCursoBO;
	private static MunicipioBO municipioBO;
	private static ModeloBO modeloBO;
	private static TerrGetConceptosConvenioBO codterGetConceptosConvenioBO;
	private static TerrGetConceptosGestionBO codterGetConceptosGestionBO;
	private static CodigoTerritorialCompatBO codigoTerritorialCompatBO;
	// Tipos de acceso
	public static final char CONSULTA='C';
	public static final char EDICION='E';
	public static final char COMPLETO='*';

	// Tipos de convenio
	public static final String GESTION="G";
	public static final String VOLUNTARIA="R";
	public static final String EJECUTIVA="E";
	public static final String VOLUNTARIA_EJECUTIVA="R,E";
	public static final String GESTION_VOLUNTARIA_EJECUTIVA="G,R,E";
	public static final String INSPECCION="I";
	public static final String ENTRADA="5";

	private static Map<String, List<Map<String, Object>>> conceptosUsuarioGestionMap = new HashMap<String, List<Map<String, Object>>>();
	private static Map<String, List<Map<String, Object>>> conceptosUsuarioConvenioMap = new HashMap<String, List<Map<String, Object>>>();

	private static final Log LOG = LogFactory.getLog(ControlTerritorial.class);

	private ControlTerritorial() {}

	/**
	 * Obtiene la lista de municipios del usuario logado. Los busca en los datos de sesión. Si el usuario tiene acceso, añade el muncicpio genérico. En caso contrario no lo hace.
	 * @return Una lista de municipios
	 */

	static public List<MunicipioDTO> getMunicipiosUsuario() {
		return getMunicipiosUsuario(isUsuarioExperto());
	}
	static public List<MunicipioDTO> getMunicipiosUsuario(final boolean incluirGenerico) {
		final List<MunicipioDTO> municipios = new ArrayList<MunicipioDTO>();

		GadirUserDetails gadirUserDetails=null;
		try{
			gadirUserDetails = (GadirUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			municipios.addAll(gadirUserDetails.getMunicipiosAccesibles());
		}catch (Exception e){
			LOG.warn("No hay autenticación", e);
		}
		if (incluirGenerico) {
			try{
				MunicipioDTO municipio = new MunicipioDTO();
				municipio = municipioBO.findById(new MunicipioDTOId("**", "***"));
				municipios.add(0, municipio);
			}catch (Exception e){
				LOG.warn("Error buscando municipio genérico", e);
			}
		}

		return municipios;
	}

	static public List<MunicipioDTO> getMunicipiosCodter(String codter) {
		List<String> listaCodigosTerritoriales = new ArrayList<String>(); 
		  List<MunicipioDTO> municipios = new ArrayList<MunicipioDTO>();
		List<MunicipioDTO> listaMunicipiosOrdenada = new ArrayList<MunicipioDTO>();


		String orgPrimerNivel = codter.substring(0, 2);
		String resto = codter.substring(2);

		if(resto.equals("****")){
			try {
				DetachedCriteria dc = DetachedCriteria.forClass(CodigoTerritorialCompatDTO.class);
				dc.add(Restrictions.eq("o1nUsuario", orgPrimerNivel));

				List<CodigoTerritorialCompatDTO> lista = codigoTerritorialCompatBO.findByCriteria(dc);
				for(CodigoTerritorialCompatDTO codigoTerritorialCompatDTO: lista){
					if(!listaCodigosTerritoriales.contains(codigoTerritorialCompatDTO.getO1nDocumento()+"****")){
						listaCodigosTerritoriales.add(codigoTerritorialCompatDTO.getO1nDocumento()+"****");
					}
					
				}
				if(!listaCodigosTerritoriales.contains(codter)){
					listaCodigosTerritoriales.add(codter);
				}
			} catch (GadirServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			listaCodigosTerritoriales.add(codter);
		}
		for(String codigoTerritorial:listaCodigosTerritoriales){

			final List<Map<String, Object>> lista = codterGetMunicipiosBO.execute(codigoTerritorial);
			if (lista!=null){
				for (final Iterator<Map<String, Object>> i = lista.iterator(); i.hasNext();) {
					municipios.add(resultSetToMunicipio(i.next()));					
				}				
			}
		}	
		if(municipios != null){
			
			
			  List<MunicipioDTO> municipiosSinRepetir = new ArrayList<MunicipioDTO>();
			  
			for(MunicipioDTO muni:municipios){
				if(!municipiosSinRepetir.contains(muni)){
					municipiosSinRepetir.add(muni);
				}
				
				
			}
			
			 
			 municipios.clear();
 			municipios.addAll(municipiosSinRepetir);
			  
		}
		if(municipios != null){
			listaMunicipiosOrdenada = ordenarMunicipios(municipios);
		}
		return listaMunicipiosOrdenada;
	}

	/**
	 * En PL/SQL: comun_util.CtrlTerr_getConceptos
	 * @param municipio
	 * @param codigoTerritorial
	 * @return Lista de conceptos
	 * 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	static public List<ConceptoDTO> getConceptosUsuario(final String coMunicipio) {
		final List<ConceptoDTO> conceptos = new ArrayList<ConceptoDTO>();
		String coProvinciaAux="";
		String coMunicipioAux="";

		if (coMunicipio!=null){

			if (coMunicipio.length()==3){
				coProvinciaAux = GadirConfig.leerParametro(ParametrosConfig.PROVINCIA);
				coMunicipioAux = coMunicipio;
			}else if (coMunicipio.length()==5){
				coProvinciaAux = coMunicipio.substring(0, 2);
				coMunicipioAux = coMunicipio.substring(2, 5);
			}else{
				return conceptos;
			}

			final List<Map<String, Object>> lista = codterGetConceptosBO.execute(coProvinciaAux, coMunicipioAux, DatosSesion.getCodigoTerritorialGenerico());
			for (final Iterator<Map<String, Object>> i = lista.iterator(); i.hasNext();) {
				conceptos.add(resultSetToConcepto(i.next()));
			}
		}

		return conceptos;
	}

	/* 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	static public List<ConceptoDTO> getConceptosUsuarioModelo(final String coMunicipio, final String coModelo) {	
		List<ConceptoDTO> conceptos = new ArrayList<ConceptoDTO>();
		if (coModelo==null) {	
			conceptos=ControlTerritorial.getConceptosUsuario(coMunicipio);
		} else {
			String coProvinciaAux="";
			String coMunicipioAux="";

			if (coMunicipio!=null){

				if (coMunicipio.length()==3){
					coProvinciaAux = GadirConfig.leerParametro(ParametrosConfig.PROVINCIA);
					coMunicipioAux = coMunicipio;
				}else if (coMunicipio.length()==5){
					coProvinciaAux = coMunicipio.substring(0, 2);
					coMunicipioAux = coMunicipio.substring(2, 5);
				}else{
					return conceptos;
				}

				final List<Map<String, Object>> lista = codterGetConceptosModeloBO.execute(coProvinciaAux, coMunicipioAux, DatosSesion.getCodigoTerritorialGenerico(), coModelo);
				if (lista != null && !lista.isEmpty()) {
					for (final Iterator<Map<String, Object>> i = lista.iterator(); i.hasNext();) {
						conceptos.add(resultSetToConcepto(i.next()));
					}
				}
			}
		}
		return conceptos;
	}	

	/* 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	static public List<ConceptoDTO> getConceptosUsuarioConvenio(final String coMunicipio) {	
		List<ConceptoDTO> conceptos = new ArrayList<ConceptoDTO>();
		String coProvinciaAux="";
		String coMunicipioAux="";

		if (coMunicipio!=null){

			if (coMunicipio.length()==3){
				coProvinciaAux = GadirConfig.leerParametro(ParametrosConfig.PROVINCIA);
				coMunicipioAux = coMunicipio;
			}else if (coMunicipio.length()==5){
				coProvinciaAux = coMunicipio.substring(0, 2);
				coMunicipioAux = coMunicipio.substring(2, 5);
			}else{
				return conceptos;
			}

			//Uso de caché para que vaya mas rápido.
			String key = coProvinciaAux + "|" + coMunicipioAux + "|" + DatosSesion.getCodigoTerritorialGenerico();
			List<Map<String, Object>> lista = conceptosUsuarioConvenioMap.get(key);
			if (lista == null) {
				lista = codterGetConceptosConvenioBO.execute(coProvinciaAux, coMunicipioAux, DatosSesion.getCodigoTerritorialGenerico());
				conceptosUsuarioConvenioMap.put(key, lista);
			}
			if (lista != null && !lista.isEmpty()) {
				for (final Iterator<Map<String, Object>> i = lista.iterator(); i.hasNext();) {
					conceptos.add(resultSetToConcepto(i.next()));
				}
			}
		}
		return conceptos;
	}		

	/* 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	public static List<ConceptoDTO> getConceptosUsuarioConvenio(List<MunicipioDTO> municipioDTOs) {
		Map<String, ConceptoDTO> conceptoMap = new HashMap<String, ConceptoDTO>();
		for (MunicipioDTO municipioDTO : municipioDTOs) {
			List<ConceptoDTO> conceptoDTOs = getConceptosUsuarioConvenio(municipioDTO.getCodigoCompleto());
			for (ConceptoDTO conceptoDTO : conceptoDTOs) {
				if (!conceptoMap.containsKey(conceptoDTO.getCoConcepto())) {
					conceptoMap.put(conceptoDTO.getCoConcepto(), conceptoDTO);
				}
			}
		}
		List<ConceptoDTO> resultado = new ArrayList<ConceptoDTO>(conceptoMap.values());
		Collections.sort(resultado);
		return resultado;
	}

	/* 
	 * @Deprecated
	 * @see getModelos()
	 */
	@Deprecated
	public static List<ModeloDTO> getModelosUsuarioConvenio(List<MunicipioDTO> municipioDTOs) {
		List<ModeloDTO> modeloDTOs;
		try {
			modeloDTOs = modeloBO.findModelosByConceptos(getConceptosUsuarioConvenio(municipioDTOs));
			Collections.sort(modeloDTOs);
		} catch (GadirServiceException e) {
			modeloDTOs = new ArrayList<ModeloDTO>();
			LOG.error(e.getMensaje(), e);
		}
		return modeloDTOs;
	}

	public static void vaciarConceptosUsuarioConvenioMap() {
		conceptosUsuarioConvenioMap = new HashMap<String, List<Map<String, Object>>>();
	}

	/**
	 * 
	 * @param coMunicipio
	 * @return Conceptos cuyo convenio de gestión sea para cualquier año
	 * 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	static public List<ConceptoDTO> getConceptosUsuarioGestion(final String coMunicipio) {
		return getConceptosUsuarioGestion(coMunicipio, -1);
	}

	/**
	 * 
	 * @param coMunicipio
	 * @param ejercicio
	 * @return Conceptos cuyo convenio de gestión sea para el año indicado como ejercicio
	 * 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	static public List<ConceptoDTO> getConceptosUsuarioGestion(final String coMunicipio, final int ejercicio) {	
		List<ConceptoDTO> conceptos = new ArrayList<ConceptoDTO>();
		String coProvinciaAux="";
		String coMunicipioAux="";

		if (coMunicipio!=null){

			if (coMunicipio.length()==3){
				coProvinciaAux = GadirConfig.leerParametro(ParametrosConfig.PROVINCIA);
				coMunicipioAux = coMunicipio;
			}else if (coMunicipio.length()==5){
				coProvinciaAux = coMunicipio.substring(0, 2);
				coMunicipioAux = coMunicipio.substring(2, 5);
			}else{
				return conceptos;
			}

			//Uso de caché para que vaya mas rápido.
			String key = coProvinciaAux + "|" + coMunicipioAux + "|" + DatosSesion.getCodigoTerritorialGenerico() + "|" + ejercicio;
			List<Map<String, Object>> lista = conceptosUsuarioGestionMap.get(key);
			if (lista == null) {
				lista = codterGetConceptosGestionBO.execute(coProvinciaAux, coMunicipioAux, DatosSesion.getCodigoTerritorialGenerico(), ejercicio);
				conceptosUsuarioGestionMap.put(key, lista);
			}
			if (lista != null && !lista.isEmpty()) {
				for (final Iterator<Map<String, Object>> i = lista.iterator(); i.hasNext();) {
					conceptos.add(resultSetToConcepto(i.next()));
				}
			}
		}
		return conceptos;
	}		

	public static void vaciarConceptosUsuarioGestionMap() {
		conceptosUsuarioGestionMap = new HashMap<String, List<Map<String, Object>>>();
	}

	/* 
	 * @Deprecated
	 * @see getConceptos()
	 */
	@Deprecated
	static public List<ConceptoDTO> getConceptosUsuarioAnoEnCurso(final String coMunicipio) {
		final List<ConceptoDTO> conceptos = new ArrayList<ConceptoDTO>();
		String coProvinciaAux="";
		String coMunicipioAux="";

		if (coMunicipio!=null){

			if (coMunicipio.length()==3){
				coProvinciaAux = GadirConfig.leerParametro(ParametrosConfig.PROVINCIA);
				coMunicipioAux = coMunicipio;
			}else if (coMunicipio.length()==5){
				coProvinciaAux = coMunicipio.substring(0, 2);
				coMunicipioAux = coMunicipio.substring(2, 5);
			}else{
				return conceptos;
			}

			final List<Map<String, Object>> lista = codterGetConceptosAnoCursoBO.execute(coProvinciaAux, coMunicipioAux, DatosSesion.getCodigoTerritorialGenerico());
			for (final Iterator<Map<String, Object>> i = lista.iterator(); i.hasNext();) {
				conceptos.add(resultSetToConcepto(i.next()));
			}
		}

		return conceptos;
	}

	static public List<MunicipioDTO> ordenarMunicipios(List<MunicipioDTO> listaMunicipios){
		List<MunicipioDTO> listaMunicipiosOrdenada = new ArrayList<MunicipioDTO>();
		List<String> listaAux = new ArrayList<String>();
		
		for(MunicipioDTO municipioDTO:listaMunicipios){	
			if(!listaAux.contains(municipioDTO.getCodigoCompleto()))
				listaAux.add(municipioDTO.getCodigoCompleto());
		}
		Collections.sort(listaAux); 
		for(MunicipioDTO municipioDTO:listaMunicipios){
			for(String codigo : listaAux){
				if(codigo.equals(municipioDTO.getCodigoCompleto())){
					listaMunicipiosOrdenada.add(municipioDTO);
				}
			}
		}
		return listaMunicipiosOrdenada;
	}
	
	static public boolean isUsuarioExperto() {
		return "******".equals(DatosSesion.getCodigoTerritorialGenerico());
	}

	static public boolean isUsuarioAyuntamiento() {
		return "AY".equals(DatosSesion.getCodigoTerritorialGenerico().substring(0, 2));
	}

	static public boolean isUsuarioOT() {
		return "OT".equals(DatosSesion.getCodigoTerritorialGenerico().substring(0, 2));
	}
	
	static public boolean isCodterCompatibleUsuario(final String codter, char modo) {
		boolean res=false;

		List<String> lista=getCodtersCompatsUsuario(modo);
		res = lista.contains(codter);

		return res;
	}

	public static List<String> getCodTersCompatibles(final String codterGenerico, char modo){
		return getCodTersCompatibles(codterGenerico, new ArrayList<String>(), modo);
	}

	public static List<String> getCodTersCompatibles(final String codterGenerico, final List<String> codtersExtra, char modo){
		final List<String> lista = new ArrayList<String>();

		lista.addAll(codterGetCodtersCompatsBO.execute(codterGenerico, modo));

		if (codtersExtra!=null && !codtersExtra.isEmpty()){
			for (String codterExtra: codtersExtra){
				lista.addAll(codterGetCodtersCompatsBO.execute(codterExtra, modo));
			}
		}

		Set<String> s = new HashSet<String>(lista);
		return new ArrayList<String>(s);
	}

	public static String getCadenaCodtersUsuario(char modo){
		List<String> lista=getCodtersCompatsUsuario(modo);
		String cadena = ""+ lista;
		if (cadena.length()>=2){
			cadena=cadena.substring(1, cadena.length()-1);
		}
		return cadena;
	}

	public static boolean accesoTotal(char modo){
		if (isUsuarioExperto()){
			return true;
		}else{
			boolean result = false;
			GadirUserDetails gadirUserDetails=null;
			try{
				gadirUserDetails = (GadirUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (modo==CONSULTA){
					result=gadirUserDetails.isAccesoTotalConsulta();
				}else if (modo==EDICION){
					result=gadirUserDetails.isAccesoTotalEdicion();
				}
			}catch (Exception e){
				LOG.warn("No hay autenticación", e);
			}
			return result;
		}
	}

	public static List<String> getCodtersCompatsUsuario(char modo){
		List<String> codters = new ArrayList<String>();

		GadirUserDetails gadirUserDetails=null;
		try{
			gadirUserDetails = (GadirUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (modo==CONSULTA){
				codters.addAll(gadirUserDetails.getCodtersCompatsConsulta());
			}else if (modo==EDICION){
				codters.addAll(gadirUserDetails.getCodtersCompatsEdicion());
			} else if (modo == '*') {
				codters.addAll(gadirUserDetails.getCodtersCompatsConsulta());
				codters.addAll(gadirUserDetails.getCodtersCompatsEdicion());
				codters = new ArrayList<String>(new HashSet<String>(codters)); // Eliminar modelos duplicados.
			}
		}catch (Exception e){
			LOG.warn("No hay autenticación", e);
		}

		return codters;
	}



	private static MunicipioDTO resultSetToMunicipio(final Map<String, Object> fila) {
		  MunicipioDTO municipio = new MunicipioDTO();
		municipio.setCoUsuarioActualizacion((String) fila.get("CO_USUARIO_ACTUALIZACION"));
		municipio.setFhActualizacion((Timestamp) fila.get("FH_ACTUALIZACION"));
		municipio.setId(new MunicipioDTOId((String) fila.get("CO_PROVINCIA"), (String) fila.get("CO_MUNICIPIO")));
		municipio.setMunicipioAeat((String) fila.get("MUNICIPIO_AEAT"));
		//		municipio.setMunicipioCodters(municipioCodters);
		municipio.setNombre((String) fila.get("NOMBRE"));
		final PaisDTO pais = new PaisDTO();
		if (fila.get("CO_PAIS") != null) {
			pais.setCoPais(Short.valueOf((String) fila.get("CO_PAIS")));
		}
		municipio.setPaisDTO(pais);
		final ProvinciaDTO provincia = new ProvinciaDTO();
		provincia.setCoProvincia((String) fila.get("CO_PROVINCIA"));
		municipio.setProvinciaDTO(provincia);
		municipio.setRowid((String) fila.get("ROWID"));
		
//		try {
//			municipio=	municipioBO.findById(new MunicipioDTOId((String) fila.get("CO_PROVINCIA"), (String) fila.get("CO_MUNICIPIO")));
//		} catch (GadirServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return municipio;
	}

	private static ConceptoDTO resultSetToConcepto(final Map<String, Object> fila) {
		final ConceptoDTO concepto = new ConceptoDTO();
		concepto.setCoConcepto((String) fila.get("CO_CONCEPTO"));
		concepto.setCoUsuarioActualizacion((String) fila.get("CO_USUARIO_ACTUALIZACION"));
		concepto.setDescripcion((String) fila.get("DESCRIPCION"));
		concepto.setFhActualizacion((Timestamp) fila.get("FH_ACTUALIZACION"));
		concepto.setNombre((String) fila.get("NOMBRE"));
		concepto.setRowid((String) fila.get("ROWID"));
		return concepto;
	}

	public static String getTipoConvenio(String coProvincia, String coMunicipio, String coConcepto, String coModelo) {
		String sql = "Select control_territorial.getCasosConvenio('"+coProvincia+"'," + "'"+coMunicipio+"', " + "'"+coConcepto+"', " + "'"+coModelo+"') from dual";
		String res = null;
		try {
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>)municipioBO.ejecutaQuerySelect(sql);
			res = (String)lista.get(0);
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return res;
	}

	/**
	 * 
	 * @param coMunicipioCompleto Opcional. Sin municipio indica todos los municipios asociados al coTerritorialGenerico.
	 * @param coConcepto Opcional. Sin concepto indica que se va a devolver una lista de todos los conceptos que cumplen con las condiciones. Con concepto quiere decir que devolverá el mismo en el caso de que tenga acceso a él.
	 * @param ejercicio Opcional. Sin ejercicio indica que se buscan entre todos los convenios independientemente de su ejercicio.
	 * @param modosAcceso Se acepta null, "C", "E" o "C,E". Con null se entiende Consulta y Edición, al igual que "C,E".
	 * @param tiposConvenio El tipo de convenio, o una combinación de tipos de convenios separado por coma. Los tipos son: "G", "R", "I".
	 * @return Una lista de conceptos que cumplen con las condiciones.
	 */
	public static List<ConceptoDTO> getConceptos(String coMunicipioCompleto, String coConcepto, Short ejercicio, char modoAcceso, String tiposConvenio) {
		List<ConceptoDTO> result = new ArrayList<ConceptoDTO>();
		try {
			String coProvincia = Utilidades.isEmpty(coMunicipioCompleto) ? "" : coMunicipioCompleto.substring(0, 2);
			String coMunicipio = Utilidades.isEmpty(coMunicipioCompleto) ? "" : coMunicipioCompleto.substring(2);
			coConcepto = Utilidades.isEmpty(coConcepto) ? "" : coConcepto;
			String coTerritorialGenerico = DatosSesion.getCodigoTerritorialGenerico();
			String modoAccesoString = modoAcceso == COMPLETO ? "null" : "'"+modoAcceso+"'";
			tiposConvenio = Utilidades.isEmpty(tiposConvenio) ? "" : tiposConvenio;
			String sql = "SELECT CONTROL_ACCESOS.getConceptos('"+coProvincia+"', '"+coMunicipio+"', '"+coConcepto+"', "+ejercicio+", '"+coTerritorialGenerico+"', "+modoAccesoString+", '"+tiposConvenio+"') FROM DUAL";
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>)municipioBO.ejecutaQuerySelect(sql);
			Clob res = (SerializableClob)lista.get(0);
			if (res != null) {
				String[] conceptos = clobToString(res).split("\\|");
				for (int i=0; i<conceptos.length; i++) {
					String[] cachos = conceptos[i].split("\\^");
					ConceptoDTO conceptoDTO = new ConceptoDTO();
					conceptoDTO.setRowid(cachos[0].trim());
					conceptoDTO.setCoConcepto(cachos[1].trim());
					conceptoDTO.setNombre(cachos[2].trim());
					result.add(conceptoDTO);
				}
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return result;
	}

	public static List<ModeloDTO> getModelos(String coMunicipioCompleto, String coConcepto, String coModelo, Short ejercicio, char modoAcceso, String tiposConvenio, String filtroModelos) {
		return getModelos(coMunicipioCompleto, coConcepto, coModelo, ejercicio, modoAcceso, tiposConvenio, filtroModelos, DatosSesion.getCodigoTerritorialGenerico());
	}
	public static List<ModeloDTO> getModelos(String coMunicipioCompleto, String coConcepto, String coModelo, Short ejercicio, char modoAcceso, String tiposConvenio, String filtroModelos, String coTerritorialGenerico) {
		List<ModeloDTO> result = new ArrayList<ModeloDTO>();
		try {
			String coProvincia = Utilidades.isEmpty(coMunicipioCompleto) ? "" : coMunicipioCompleto.substring(0, 2);
			String coMunicipio = Utilidades.isEmpty(coMunicipioCompleto) ? "" : coMunicipioCompleto.substring(2);
			coConcepto = Utilidades.isEmpty(coConcepto) ? "" : coConcepto;
			coModelo = Utilidades.isEmpty(coModelo) ? "" : coModelo;
			String modoAccesoString = modoAcceso == COMPLETO ? "null" : "'"+modoAcceso+"'";
			tiposConvenio = Utilidades.isEmpty(tiposConvenio) ? "" : tiposConvenio;
			filtroModelos = Utilidades.isEmpty(filtroModelos) ? "" : filtroModelos;
			String sql = "SELECT CONTROL_ACCESOS.getModelos('"+coProvincia+"', '"+coMunicipio+"', '"+coConcepto+"', '"+coModelo+"', "+ejercicio+", '"+coTerritorialGenerico+"', "+modoAccesoString+", '"+tiposConvenio+"', '"+filtroModelos+"') FROM DUAL";
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>)municipioBO.ejecutaQuerySelect(sql);
			Clob res = (SerializableClob)lista.get(0);
			if (res != null) {
				String[] modelos = clobToString(res).split("\\|");
				for (int i=0; i<modelos.length; i++) {
					String[] cachos = modelos[i].split("\\^");
					ModeloDTO modeloDTO = new ModeloDTO();
					modeloDTO.setRowid(cachos[0].trim());
					modeloDTO.setCoModelo(cachos[1].trim());
					modeloDTO.setNombre(cachos[2].trim());
					result.add(modeloDTO);
				}
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return result;
	}

	private static String clobToString(Clob data) {
		//	    StringBuilder sb = new StringBuilder();
		//	    try {
		//	        Reader reader = data.getCharacterStream();
		//	        BufferedReader br = new BufferedReader(reader);
		//
		//	        String line;
		//	        while(null != (line = br.readLine())) {
		//	            sb.append(line);
		//	        }
		//	        br.close();
		//	    } catch (SQLException e) {
		//	    	LOG.error(e.getMessage(), e);
		//	    } catch (IOException e) {
		//	    	LOG.error(e.getMessage(), e);
		//	    }
		//	    return sb.toString();
		StringWriter w = new StringWriter();
		try {
			InputStream in = data.getAsciiStream();
			IOUtils.copy(in, w);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return w.toString();
	}

	public static TerrGetMunicipiosBO getCodterGetMunicipiosBO() {
		return codterGetMunicipiosBO;
	}

	public void setCodterGetMunicipiosBO(
			final TerrGetMunicipiosBO codterGetMunicipiosBO) {
		ControlTerritorial.codterGetMunicipiosBO = codterGetMunicipiosBO;
	}


	public static TerrGetConceptosBO getCodterGetConceptosBO() {
		return codterGetConceptosBO;
	}

	public void setCodterGetConceptosBO(
			final TerrGetConceptosBO codterGetConceptosBO) {
		ControlTerritorial.codterGetConceptosBO = codterGetConceptosBO;
	}


	public static MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		ControlTerritorial.municipioBO = municipioBO;
	}

	public static ModeloBO getModeloBO() {
		return modeloBO;
	}

	public void setModeloBO(ModeloBO modeloBO) {
		ControlTerritorial.modeloBO = modeloBO;
	}

	public TerrGetCodtersCompatsBO getCodterGetCodtersCompatsBO() {
		return codterGetCodtersCompatsBO;
	}

	public void setCodterGetCodtersCompatsBO(
			TerrGetCodtersCompatsBO codterGetCodtersCompatsBO) {
		ControlTerritorial.codterGetCodtersCompatsBO = codterGetCodtersCompatsBO;
	}

	public static TerrGetConceptosAnoCursoBO getCodterGetConceptosAnoCursoBO() {
		return codterGetConceptosAnoCursoBO;
	}

	public void setCodterGetConceptosAnoCursoBO(
			final TerrGetConceptosAnoCursoBO codterGetConceptosAnoCursoBO) {
		ControlTerritorial.codterGetConceptosAnoCursoBO = codterGetConceptosAnoCursoBO;
	}

	public static TerrGetConceptosModeloBO getCodterGetConceptosModeloBO() {
		return codterGetConceptosModeloBO;
	}

	public void setCodterGetConceptosModeloBO(
			TerrGetConceptosModeloBO codterGetConceptosModeloBO) {
		ControlTerritorial.codterGetConceptosModeloBO = codterGetConceptosModeloBO;
	}

	public static TerrGetConceptosConvenioBO getCodterGetConceptosConvenioBO() {
		return codterGetConceptosConvenioBO;
	}

	public void setCodterGetConceptosConvenioBO(
			TerrGetConceptosConvenioBO codterGetConceptosConvenioBO) {
		ControlTerritorial.codterGetConceptosConvenioBO = codterGetConceptosConvenioBO;
	}

	public static TerrGetConceptosGestionBO getCodterGetConceptosGestionBO() {
		return codterGetConceptosGestionBO;
	}

	public void setCodterGetConceptosGestionBO(
			TerrGetConceptosGestionBO codterGetConceptosGestionBO) {
		ControlTerritorial.codterGetConceptosGestionBO = codterGetConceptosGestionBO;
	}

	public static CodigoTerritorialCompatBO getCodigoTerritorialCompatBO() {
		return codigoTerritorialCompatBO;
	}

	public void setCodigoTerritorialCompatBO(
			CodigoTerritorialCompatBO codigoTerritorialCompatBO) {
		ControlTerritorial.codigoTerritorialCompatBO = codigoTerritorialCompatBO;
	}



}
