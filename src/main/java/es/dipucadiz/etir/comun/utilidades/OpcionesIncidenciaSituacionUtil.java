package es.dipucadiz.etir.comun.utilidades;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.action.AbstractGadirBaseAction;
import es.dipucadiz.etir.comun.bo.CargoBO;
import es.dipucadiz.etir.comun.bo.CargoSubcargoBO;
import es.dipucadiz.etir.comun.bo.CasillaMunicipioBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.DocumentoLiquidacionBO;
import es.dipucadiz.etir.comun.bo.DocumentoSeguimientoBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.bo.IncidSitCargoBO;
import es.dipucadiz.etir.comun.bo.IncidenciaBO;
import es.dipucadiz.etir.comun.bo.IncidenciaCargoBO;
import es.dipucadiz.etir.comun.bo.IncidenciaSituacionBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ProcesoBO;
import es.dipucadiz.etir.comun.bo.SituacionBO;
import es.dipucadiz.etir.comun.bo.SituacionCargoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.constants.ConceptoConstants;
import es.dipucadiz.etir.comun.constants.ModeloConstants;
import es.dipucadiz.etir.comun.constants.TipoModeloConstants;
import es.dipucadiz.etir.comun.dto.CargoDTO;
import es.dipucadiz.etir.comun.dto.CargoSubcargoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.dto.DocumentoSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTOId;
import es.dipucadiz.etir.comun.dto.IncidSitCargoDTO;
import es.dipucadiz.etir.comun.dto.IncidenciaCargoDTO;
import es.dipucadiz.etir.comun.dto.IncidenciaCargoDTOId;
import es.dipucadiz.etir.comun.dto.IncidenciaDTO;
import es.dipucadiz.etir.comun.dto.IncidenciaSituacionDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.dto.SituacionCargoDTO;
import es.dipucadiz.etir.comun.dto.SituacionCargoDTOId;
import es.dipucadiz.etir.comun.dto.SituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.comun.vo.OpcionIncidenciaVO;
import es.dipucadiz.etir.sb05.action.G5114CasillasMunicipio.G5114AbstractAction;

public class OpcionesIncidenciaSituacionUtil {

	private static IncidenciaBO incidenciaBO;
	private static SituacionBO situacionBO;
	private static IncidenciaSituacionBO incidenciaSituacionBO;
	private static ProcesoBO procesoBO;
	private static IncidSitCargoBO incidSitCargoBO;
	private static IncidenciaCargoBO incidenciaCargoBO;
	private static SituacionCargoBO situacionCargoBO;
	private static DocumentoBO documentoBO;
	private static DocumentoLiquidacionBO documentoLiquidacionBO;
	private static CargoBO cargoBO;
	private static CargoSubcargoBO cargoSubcargoBO;
	private static ModeloBO modeloBO;
	private static DocumentoSeguimientoBO documentoSeguimientoBO;
	private static GenericBO<EjecucionParametroDTO, EjecucionParametroDTOId> ejecucionParametroBO;
	private static CasillaMunicipioBO casillaMunicipioBO;
	//private static ProcesoParametroBO procesoParametroBO;

	private static final Log LOG = LogFactory.getLog(OpcionesIncidenciaSituacionUtil.class);

	/*public static IncidenciaCargoDTO getIncidenciaCargoDTO(String coIncidenciaCargo, String tipo){

		IncidenciaCargoDTO incidenciaCargoDTO=null;

		try{
			IncidenciaCargoDTOId id = new IncidenciaCargoDTOId(coIncidenciaCargo, tipo);
			incidenciaCargoDTO = incidenciaCargoBO.findById(id);
		}catch(Exception e){
			LOG.warn("no se encuentra incidencia de cargo para coIncidenciaCargo=" + coIncidenciaCargo + ", tipo= "+tipo, e);
		}

		return incidenciaCargoDTO;
	}*/



	public static String getCodigoDescripcionIncidenciaCargo(String coIncidenciaCargo, String tipo){

		String result=coIncidenciaCargo;

		try{
			IncidenciaCargoDTOId id = new IncidenciaCargoDTOId(coIncidenciaCargo, tipo);
			IncidenciaCargoDTO incidenciaCargoDTO = incidenciaCargoBO.findById(id);
			result=incidenciaCargoDTO.getCodigoDescripcion();
		}catch(Exception e){
			LOG.warn("no se encuentra siyuacion de cargo para coIncidenciaCargo=" + coIncidenciaCargo + ", tipo= "+tipo, e);
		}

		return result;
	}

	public static String getCodigoDescripcionIncidenciaDocumento(String coIncidencia){
		return coIncidencia + " - " + getDescripcionIncidenciaDocumento(coIncidencia);
	}

	public static String getDescripcionIncidenciaDocumento(String coIncidencia){

		String result=coIncidencia;

		try{
			IncidenciaDTO incidenciaDTO = incidenciaBO.findById(coIncidencia);
			result=incidenciaDTO.getNombre();
		}catch(Exception e){
			LOG.warn("no se encuentra incidencia de documentos para " + coIncidencia, e);
		}

		return result;
	}

	/*public static SituacionCargoDTO getSituacionCargoDTO(String coSituacionCargo, String tipo){

		SituacionCargoDTO situacionCargoDTO=null;

		try{
			SituacionCargoDTOId id = new SituacionCargoDTOId(coSituacionCargo, tipo);
			situacionCargoDTO = situacionCargoBO.findById(id);
		}catch(Exception e){
			LOG.warn("no se encuentra siyuacion de cargo para coSituacionCargo=" + coSituacionCargo + ", tipo= "+tipo, e);
		}

		return situacionCargoDTO;
	}*/

	public static String getCodigoDescripcionSituacionCargo(String coSituacionCargo, String tipo){

		String result="";

		if (coSituacionCargo!=null){
			result=coSituacionCargo;
			try{
				SituacionCargoDTOId id = new SituacionCargoDTOId(coSituacionCargo, tipo);
				SituacionCargoDTO situacionCargoDTO = situacionCargoBO.findById(id);
				result=situacionCargoDTO.getCodigoDescripcion();
			}catch(Exception e){
				LOG.warn("no se encuentra siyuacion de cargo para coSituacionCargo=" + coSituacionCargo + ", tipo= "+tipo, e);
			}
		}

		return result;
	}

	public static String getCodigoDescripcionSituacionDocumento(String coSituacion){

		String result="";

		if (coSituacion!=null){
			result=coSituacion;
			try{
				SituacionDTO situacionDTO = getSituacionDTO(coSituacion);
				result=situacionDTO.getCodigoDescripcion();
			}catch(Exception e){
				LOG.warn("no se encuentra situación de documento para coSituacion=" + coSituacion, e);
			}
		}

		return result;
	}

	public static SituacionDTO getSituacionDTO(String coSituacion) throws GadirServiceException {
		return situacionBO.findById(coSituacion);
	}

	public static boolean isIncidenciable(String coProvincia, String coMunicipio, String coModelo, String coVersion, String situacion, String estado, String modo, String incidencia){
		return getIncidenciaSituacionDTO(coProvincia, coMunicipio, coModelo, coVersion, situacion, estado, modo, incidencia) != null;
	}

	public static IncidenciaSituacionDTO getIncidenciaSituacionDTO(String coProvincia, String coMunicipio, String coModelo, String coVersion, String situacion, String estado, String modo, String incidencia){
		String coProvinciaGenerica = ControlTerritorial.isUsuarioAyuntamiento() ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;

		IncidenciaSituacionDTO res=null;

		List<IncidenciaSituacionDTO> lista = new ArrayList<IncidenciaSituacionDTO>();
		try{
			lista=incidenciaSituacionBO.findByCriteria(getCriterioIncidenciaSituacion(coProvincia, coMunicipio, coModelo, coVersion, situacion, estado, modo, incidencia));
			if (lista.isEmpty()){
				lista=incidenciaSituacionBO.findByCriteria(getCriterioIncidenciaSituacion(coProvincia, coMunicipio, "***", "*", situacion, estado, modo, incidencia));
			}else{
				res=lista.get(0);
			}
			if (lista.isEmpty()){
				lista=incidenciaSituacionBO.findByCriteria(getCriterioIncidenciaSituacion(coProvinciaGenerica, "***", coModelo, coVersion, situacion, estado, modo, incidencia));
			}else{
				res=lista.get(0);
			}
			if (lista.isEmpty()){
				lista=incidenciaSituacionBO.findByCriteria(getCriterioIncidenciaSituacion(coProvinciaGenerica, "***", "***", "*", situacion, estado, modo, incidencia));
			}else{
				res=lista.get(0);
			}	

			if (!lista.isEmpty()){
				res=lista.get(0);
			}

		}catch(Exception e){
			LOG.error("Error calculando getOpcionesIncidencia", e);
		}

		return res;
	}

	public static List<IncidenciaSituacionDTO> getOpcionesIncidencia(String coProvincia, String coMunicipio, String coModelo, String coVersion, String situacion, String estado, String modo) {
		String coProvinciaGenerica = ControlTerritorial.isUsuarioAyuntamiento() ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;

		List<IncidenciaSituacionDTO> resultado = new ArrayList<IncidenciaSituacionDTO>();

		try{

			ModeloDTO modeloDTO = MunicipioConceptoModeloUtil.getModeloDTO(coModelo);

			if (TipoModeloConstants.TIPO_LIQUIDACION.equals(modeloDTO.getTipo()) && !TipoModeloConstants.SUBTIPO_COSTAS.equals(modeloDTO.getSubtipo())){
				resultado.addAll(incidenciaSituacionBO.findByCriteria(getCriterio(coProvincia, coMunicipio, coModelo, coVersion, situacion, estado, modo)));
				// habra que ir añadiendo todos menos el que ya este
				List<IncidenciaSituacionDTO> listaAux = incidenciaSituacionBO.findByCriteria(getCriterio(coProvincia, coMunicipio, "***", "*", situacion, estado, modo));
				resultado = annadirExcluyendoIncidenciaRepetidas(resultado , listaAux);		

				listaAux =incidenciaSituacionBO.findByCriteria(getCriterio(coProvinciaGenerica, "***", coModelo, coVersion, situacion, estado, modo));
				resultado = annadirExcluyendoIncidenciaRepetidas(resultado , listaAux);		 

				listaAux =incidenciaSituacionBO.findByCriteria(getCriterio(coProvinciaGenerica, "***", "***", "*", situacion, estado, modo));
				resultado = annadirExcluyendoIncidenciaRepetidas(resultado , listaAux);		 
			}else{
				resultado.addAll(incidenciaSituacionBO.findByCriteria(getCriterio(coProvincia, coMunicipio, coModelo, coVersion, situacion, estado, modo)));
				List<IncidenciaSituacionDTO> lista = incidenciaSituacionBO.findByCriteria(getCriterio(coProvincia, coMunicipio, coModelo, coVersion, situacion, estado, modo));
				resultado = annadirExcluyendoIncidenciaRepetidas(resultado, lista);
				if (resultado.isEmpty()){
					lista=incidenciaSituacionBO.findByCriteria(getCriterio(coProvincia, coMunicipio, "***", "*", situacion, estado, modo));
					resultado = annadirExcluyendoIncidenciaRepetidas(resultado, lista);
				}
				if (resultado.isEmpty()){
					lista=incidenciaSituacionBO.findByCriteria(getCriterio(coProvinciaGenerica, "***", coModelo, coVersion, situacion, estado, modo));
					resultado = annadirExcluyendoIncidenciaRepetidas(resultado, lista);
				}
				if (resultado.isEmpty()){
					lista=incidenciaSituacionBO.findByCriteria(getCriterio(coProvinciaGenerica, "***", "***", "*", situacion, estado, modo));
					resultado = annadirExcluyendoIncidenciaRepetidas(resultado, lista);
				}
			}
			if (casillaMunicipioBO.existeOperacion(coProvincia, coMunicipio, null, coModelo, coVersion, G5114AbstractAction.OPERACION_GRABAR_CASILLAS)) {
				IncidenciaDTO incidenciaDTO = new IncidenciaDTO();
				incidenciaDTO.setCoIncidencia(IncidenciaConstants.CO_INCIDENCIA_GRABAR_CASILLAS);
				incidenciaDTO.setNombre(TablaGt.getValor(TablaGtConstants.TABLA_PROCESOS_FUNCIONES, G5114AbstractAction.OPERACION_GRABAR_CASILLAS, TablaGt.COLUMNA_DESCRIPCION));
				IncidenciaSituacionDTO incidenciaSituacionDTO = new IncidenciaSituacionDTO();
				incidenciaSituacionDTO.setCoIncidenciaSituacion(Long.parseLong(IncidenciaConstants.CO_INCIDENCIA_GRABAR_CASILLAS));
				incidenciaSituacionDTO.setIncidenciaDTO(incidenciaDTO); 
				resultado.add(incidenciaSituacionDTO);
			}


		}catch(Exception e){
			LOG.error("Error calculando getOpcionesIncidencia", e);
		}

		return ordenar(resultado);
	}

	private static List<IncidenciaSituacionDTO> ordenar(List<IncidenciaSituacionDTO> resultado) {
		for (int i=0; i<resultado.size()-1; i++) {
			for (int j=i+1; j<resultado.size(); j++) {
				if (resultado.get(i).getIncidenciaDTO().getNombre().compareTo(resultado.get(j).getIncidenciaDTO().getNombre()) > 0) {
					IncidenciaSituacionDTO tmp = resultado.get(i);
					resultado.set(i, resultado.get(j));
					resultado.set(j, tmp);
				}
			}
		}
		return resultado;
	}

	public static List<OpcionIncidenciaVO> getOpcionesImpresion(final List<MunicipioDTO> municipiosAccesibles, final String coTerritorialGenerico) {
		List<OpcionIncidenciaVO> result;

		try {
			// Recupero la tabla entera de DOCNOTIF para facilitar accesos futuros
			List<String> elsDocnotif = TablaGt.getListaElementos(TablaGtConstants.TABLA_OPCIONES_IMPRESION, TablaGt.COLUMNA_CODIGO);
			List<Map<String, String>> tabDocnotif = new ArrayList<Map<String, String>>();
			List<String> coIncidencias = new ArrayList<String>();
			for (String coElemento : elsDocnotif) {
				Map<String, String> fila = TablaGt.getValores(TablaGtConstants.TABLA_OPCIONES_IMPRESION, TablaGt.COLUMNA_CODIGO, coElemento);
				String seleccionable = fila.get(TablaGt.COLUMNA_SELECCIONABLE);
				String coIncidencia = fila.get(TablaGt.COLUMNA_INCIDENCIA);
				if ("S".equals(seleccionable) && Utilidades.isNotEmpty(coIncidencia)) {
					// Solo los seleccionables y los que llevan coIncidencia
					tabDocnotif.add(fila);

					if (!coIncidencias.contains(coIncidencia)) {
						// Me quedo también con las distintas coIncidencia para acceder luego a convenios
						coIncidencias.add(coIncidencia);
					}
				}
			}

			if (!coIncidencias.isEmpty()) {
				// Municipio accesibles por el usuario, incluyendo el municipio genérico
				List<MunicipioDTO> municipioDTOs = new ArrayList<MunicipioDTO>(municipiosAccesibles);
				boolean isUsuarioAyuntamiento = coTerritorialGenerico.startsWith(ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS);
				String coProvinciaGenerica = isUsuarioAyuntamiento ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;
				MunicipioDTO municipioGenericoDTO = new MunicipioDTO();
				municipioGenericoDTO.setId(new MunicipioDTOId(coProvinciaGenerica, MunicipioConstants.CO_MUNICIPIO_GENERICO));
				municipioDTOs.add(municipioGenericoDTO);

				// Obtengo incidencias/situaciones filtrando por municipios e incidencias del paso anterior.
				DetachedCriteria criteria = DetachedCriteria.forClass(IncidenciaSituacionDTO.class, "is");
				criteria.add(Restrictions.in("is.municipioDTO", municipioDTOs));
				criteria.add(Restrictions.in("is.incidenciaDTO.coIncidencia", coIncidencias));
				criteria.add(Restrictions.isNotNull("is.estadoSituacionDTOByCoEstadoSituacionOrigen.coEstadoSituacion"));
				List<IncidenciaSituacionDTO> incidenciaSituacionDTOs = incidenciaSituacionBO.findByCriteria(criteria);

				// Recorro esta lista, compruebo si tengo acceso al municipio/modelo de la incidencia/situacion en este ejercicio, sea gestión o recaudación. Si no, lo quito.
				Map<String, Boolean> getModelosCache = new HashMap<String, Boolean>();
				for (int i=incidenciaSituacionDTOs.size()-1; i>=0; i--) {
					String coMunicipioCompleto;
					String coModelo;
					String tiposConvenio;
					if (MunicipioConstants.CO_MUNICIPIO_GENERICO.equals(incidenciaSituacionDTOs.get(i).getMunicipioDTO().getId().getCoMunicipio())) {
						coMunicipioCompleto = null;
					} else {
						coMunicipioCompleto = incidenciaSituacionDTOs.get(i).getMunicipioDTO().getId().getCoProvincia() + incidenciaSituacionDTOs.get(i).getMunicipioDTO().getId().getCoMunicipio();
					}
					if (ModeloConstants.CO_MODELO_GENERICO.equals(incidenciaSituacionDTOs.get(i).getModeloVersionDTO().getId().getCoModelo())) {
						coModelo = null;
					} else {
						coModelo = incidenciaSituacionDTOs.get(i).getModeloVersionDTO().getId().getCoModelo();
					}
					if (EstadoSituacionConstants.VOLUNTARIA.equals(incidenciaSituacionDTOs.get(i).getEstadoSituacionDTOByCoEstadoSituacionOrigen().getCoEstadoSituacion())) {
						tiposConvenio = ControlTerritorial.VOLUNTARIA; // Voluntaria es R(ecaudación) en convenios.
					} else {
						tiposConvenio = incidenciaSituacionDTOs.get(i).getEstadoSituacionDTOByCoEstadoSituacionOrigen().getCoEstadoSituacion();
					}

					// Para los no usuarios expertos, comprobar si tengo acceso a la incidencia.
					List<String> codtersSinControl = new ArrayList<String>();
					codtersSinControl.add("******");
					codtersSinControl.add("RE****");
					codtersSinControl.add("GT****");
					codtersSinControl.add("DS****");
					if (!codtersSinControl.contains(coTerritorialGenerico)) {
						String key = coMunicipioCompleto + "|" + coModelo + "|" + Utilidades.getAnoActual() + "|" + ControlTerritorial.COMPLETO + "|" + tiposConvenio + "|" + coTerritorialGenerico;
						if (!getModelosCache.containsKey(key)) {
							getModelosCache.put(
									key, 
									// Se utiliza un cache de instancia. Si cambia algún parámetro de getModelos, tenerlo en cuenta en el caché también.
									ControlTerritorial.getModelos(coMunicipioCompleto, null, coModelo, (short)Utilidades.getAnoActual(), ControlTerritorial.COMPLETO, tiposConvenio, null, coTerritorialGenerico).isEmpty()
							);
						}
						if (getModelosCache.get(key)) {
							incidenciaSituacionDTOs.remove(i);
						}
					}
				}

				// Vuelvo a recorrer la lista. Elimino las duplicadas y saco la descripción de GA_INCIDENCIA.
				result = new ArrayList<OpcionIncidenciaVO>();
				for (IncidenciaSituacionDTO incidenciaSituacionDTO: incidenciaSituacionDTOs) {
					String coIncidencia = incidenciaSituacionDTO.getIncidenciaDTO().getCoIncidencia();
					String coEstadoSituacion = incidenciaSituacionDTO.getEstadoSituacionDTOByCoEstadoSituacionOrigen().getCoEstadoSituacion();
					OpcionIncidenciaVO opcion = new OpcionIncidenciaVO();
					opcion.setCoIncidencia(coIncidencia);
					opcion.setCoEstadoSituacion(coEstadoSituacion);
					if (!result.contains(opcion)) {
						for (Map<String, String> fila : tabDocnotif) {
							if ("S".equals(fila.get(TablaGt.COLUMNA_SELECCIONABLE)) &&
									coIncidencia.equals(fila.get(TablaGt.COLUMNA_INCIDENCIA)) &&
									coEstadoSituacion.equals(fila.get(TablaGt.COLUMNA_TIPO))) {
								opcion.setPlantilla(fila.get(TablaGt.COLUMNA_PLANTILLA));
								opcion.setNombre(fila.get(TablaGt.COLUMNA_DESCRIPCION));
								opcion.setCoDocnotif(fila.get(TablaGt.COLUMNA_CODIGO));
								result.add(opcion);
								break;
							}
						}
					}
				}
			} else {
				result = Collections.emptyList();
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			result = Collections.emptyList();
		}

		return result;
	}

	private static List<IncidenciaSituacionDTO> annadirExcluyendoIncidenciaRepetidas(List<IncidenciaSituacionDTO> listaActual, List<IncidenciaSituacionDTO> listaNuevas){
		for (IncidenciaSituacionDTO nueva : listaNuevas) {
			if (!contieneIncidencia(listaActual, nueva)) {
				listaActual.add(nueva);
			}
		}
		return listaActual;
	}

	private static boolean contieneIncidencia(List<IncidenciaSituacionDTO> incidenciaSituacionDTOs, IncidenciaSituacionDTO incidenciaSituacionDTO) {
		boolean result = false;
		for (IncidenciaSituacionDTO dtoLista : incidenciaSituacionDTOs) {
			if (dtoLista.getIncidenciaDTO().getCoIncidencia().equals(incidenciaSituacionDTO.getIncidenciaDTO().getCoIncidencia())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static List<IncidSitCargoDTO> getOpcionesIncidenciaCargo(String coProvincia, String coMunicipio, String coModelo, String situacion) {
		String coProvinciaGenerica = ControlTerritorial.isUsuarioAyuntamiento() ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;

		List<IncidSitCargoDTO> resultado = new ArrayList<IncidSitCargoDTO>();

		try{
			resultado.addAll(incidSitCargoBO.findByCriteria(getCriterioIncidSitCargo(coProvincia, coMunicipio, coModelo, situacion, "C")));
			resultado.addAll(incidSitCargoBO.findByCriteria(getCriterioIncidSitCargo(coProvincia, coMunicipio, "***", situacion, "C")));
			resultado.addAll(incidSitCargoBO.findByCriteria(getCriterioIncidSitCargo(coProvinciaGenerica, "***", coModelo, situacion, "C")));
			resultado.addAll(incidSitCargoBO.findByCriteria(getCriterioIncidSitCargo(coProvinciaGenerica, "***", "***", situacion, "C")));

		}catch(Exception e){
			LOG.error("Error calculando getOpcionesIncidencia", e);
		}

		incopatibilidadAceptarEnviarSigre(resultado);

		return resultado;
	}

	private static void incopatibilidadAceptarEnviarSigre(List<IncidSitCargoDTO> lista) {
		int posAcp = -1;
		int posGfs = -1;
		for (int i=0; i<lista.size(); i++) {
			if ("ACP".equals(lista.get(i).getCoIncidenciaCargo())) {
				posAcp = i;
			} else if ("GFS".equals(lista.get(i).getCoIncidenciaCargo())) {
				posGfs = i;
			}
			if (posAcp != -1 && posGfs != -1) {
				lista.remove(posGfs);
				break;
			}
		}
	}

	public static List<IncidSitCargoDTO> getOpcionesIncidenciaPpp(String situacion) {
		String coProvinciaGenerica = ControlTerritorial.isUsuarioAyuntamiento() ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;

		List<IncidSitCargoDTO> resultado = new ArrayList<IncidSitCargoDTO>();

		try{
			resultado=incidSitCargoBO.findByCriteria(getCriterioIncidSitCargo(coProvinciaGenerica, "***", "***", situacion, "P"));
		}catch(Exception e){
			LOG.error("Error calculando getOpcionesIncidencia", e);
		}

		return resultado;
	}

	public static List<IncidSitCargoDTO> getOpcionesIncidenciaFraccionamiento(String situacion) {
		String coProvinciaGenerica = ControlTerritorial.isUsuarioAyuntamiento() ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;

		List<IncidSitCargoDTO> resultado = new ArrayList<IncidSitCargoDTO>();

		try{
			resultado=incidSitCargoBO.findByCriteria(getCriterioIncidSitCargo(coProvinciaGenerica, "***", "***", situacion, "F"));
		}catch(Exception e){
			LOG.error("Error calculando getOpcionesIncidencia", e);
		}

		return resultado;
	}

	public static List<SituacionDTO> getSituacionesOrigenByIncidencia(String coProvincia, String coMunicipio, String coModelo, String coVersion, String coIncidencia) {
		String coProvinciaGenerica = ControlTerritorial.isUsuarioAyuntamiento() ? ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS : ProvinciaConstants.CO_PROVINCIA_GENERICO;

		List<SituacionDTO> resultado = new ArrayList<SituacionDTO>();

		try{
			resultado=situacionBO.findByCriteria(getCriterioSituaciones(coProvincia, coMunicipio, coModelo, coVersion, coIncidencia));
			if (resultado.isEmpty()){
				resultado=situacionBO.findByCriteria(getCriterioSituaciones(coProvincia, coMunicipio, "***", "*", coIncidencia));
			}
			if (resultado.isEmpty()){
				resultado=situacionBO.findByCriteria(getCriterioSituaciones(coProvinciaGenerica, "***", coModelo, coVersion, coIncidencia));
			}
			if (resultado.isEmpty()){
				resultado=situacionBO.findByCriteria(getCriterioSituaciones(coProvinciaGenerica, "***", "***", "*", coIncidencia));
			}

		}catch(Exception e){
			LOG.error("Error calculando getOpcionesIncidencia", e);
		}

		return resultado;
	}

	public static KeyValue ejecutarIncidencia(String coModelo, String coVersion, String coDocumento, String coIncidenciaSituacion, String ejecutarIncidenciaObservaciones, Date ejecutarIncidenciaFechaBaja, String ejecutarIncidenciaEconomica) {
		KeyValue resultado = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, "Error");

		try{
			if("BOP".equals(coModelo)) {
				AccesoPlantillaVO accesoPlantillaVO= new AccesoPlantillaVO();
				
				List<String> parametros = new ArrayList<String>();
				parametros.add(coIncidenciaSituacion);
				parametros.add(ejecutarIncidenciaObservaciones); //observaciones
				parametros.add(Utilidades.dateToDDMMYYYY(ejecutarIncidenciaFechaBaja)); //fecha de baja (dd/mm/aaaa)
				parametros.add(ejecutarIncidenciaEconomica); //bo_economica
				parametros.add(coModelo + coVersion + coDocumento);
				
				String coProceso = "";
				if("B02".equals(coIncidenciaSituacion))
					coProceso = "G7E7_ENVIO_BOP";
				else {
					if("B03".equals(coIncidenciaSituacion))
						coProceso = "G7E7_PUBLICADO_BOP";
				}
				
				Long coEjecucion = Batch.lanzar(coProceso, DatosSesion.getLogin(), parametros, DatosSesion.getImpresora(), accesoPlantillaVO);
				try {
					EjecucionDTO ejecucionDTO = ((EjecucionBO) GadirConfig.getBean("ejecucionBO")).findById(coEjecucion);
					if(coEjecucion != null && ejecucionDTO.getCoTerminacion().longValue() == 0) {
						resultado = new KeyValue(IncidenciaConstants.RESULTADO_CORRECTO, "Incidencia ejecutada correctamente");					
					}else{
						resultado = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, Mensaje.getTexto(ejecucionDTO.getCoTerminacion(), ejecucionDTO.getObservaciones()));
					}
			} catch (Exception e) {resultado = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, "Error lanzando proceso");}
			} else {			
				IncidenciaSituacionDTO incidenciaSituacionDTO;
				if (Long.valueOf(coIncidenciaSituacion) < 0) {
					incidenciaSituacionDTO = new IncidenciaSituacionDTO();
					if (IncidenciaConstants.CO_INCIDENCIA_GRABAR_CASILLAS.equals(coIncidenciaSituacion)) {
						String donde = "mantenimientoCasillas.action";
						donde += "?coModelo={MODELO}";
						donde += "&coVersion={VERSION}";
						donde += "&coDocumento={DOCUMENTO}";
						donde += "&operacion=" + G5114AbstractAction.OPERACION_GRABAR_CASILLAS;
						donde += "&edicion=true";
						donde += "&nuevoDocumento=false";
						donde += "&borrarDocumento=false";
						donde += "&crearHoja=false";
						donde += "&eliminarHoja=false";
						donde += "&guardar=true";
						donde += "&calcular=false";
						donde += "&verTodoElDocumento=true";
						donde += "&mostrarColumnaError=false";
						donde += "&pedirDocOrigen=false";
						ProcesoDTO procesoDTO = new ProcesoDTO();
						procesoDTO.setUrl(donde);
						incidenciaSituacionDTO.setProcesoDTO(procesoDTO);
					}
				} else {
					incidenciaSituacionDTO = incidenciaSituacionBO.findByIdInitialized(Long.valueOf(coIncidenciaSituacion), new String[] {"procesoDTO"});
				}
	
				if (incidenciaSituacionDTO.getProcesoDTO()!=null){
					if (Utilidades.isNotEmpty(incidenciaSituacionDTO.getProcesoDTO().getCoPlsql())) {
						// PROCESO PL/SQL
						List<String> parametros = new ArrayList<String>();
						parametros.add(incidenciaSituacionDTO.getIncidenciaDTO().getCoIncidencia());
						parametros.add(ejecutarIncidenciaObservaciones); //observaciones
						parametros.add(Utilidades.dateToDDMMYYYY(ejecutarIncidenciaFechaBaja)); //fecha de baja (dd/mm/aaaa)
						parametros.add(ejecutarIncidenciaEconomica); //bo_economica
						parametros.add(coModelo + coVersion + coDocumento);
						//martecher
						//					si es mi caso los parametros son distintos en observaciones va el ejercicio y el periodo
						//(String coModelo, String coVersion, String coDocumento, String coIncidenciaSituacion, String ejecutarIncidenciaObservaciones, Date ejecutarIncidenciaFechaBaja, String ejecutarIncidenciaEconomica)					
						if(incidenciaSituacionDTO.getIncidenciaDTO().getCoIncidencia().equals(IncidenciaConstants.CO_INCIDENCIA_GENERAR_RECIBO)){
							parametros.clear(); 
							String [] propiedadesInicializar = new String [2];
							propiedadesInicializar[0]="municipioDTO";
							propiedadesInicializar[1]="conceptoDTO";
	
	
	
							DocumentoDTO d = documentoBO.findByIdInitialized(new DocumentoDTOId(coModelo, coVersion, coDocumento),propiedadesInicializar);
	
							if(ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO.equals(d.getMunicipioDTO())){
								parametros.add(ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA);
							}
							else{
								parametros.add(ValoresParametrosConfig.VALOR_PROVINCIA);
							}
							//Le pasa el codigo completo del Municipio y el concepto ??
							parametros.add(d.getMunicipioDTO().getCodigoCompleto());
							parametros.add(d.getConceptoDTO().getCoConcepto());
							if (Utilidades.isEmpty(coVersion)) {
								parametros.add(coModelo); 
								parametros.add(coVersion); 
								parametros.add("");
							} else {
								parametros.add(coModelo);
								parametros.add(coVersion);
								parametros.add(coDocumento);
							}
							String[] arrayObservaciones = ejecutarIncidenciaObservaciones.split("\\|");
							String ejercicio = arrayObservaciones[0];
							String periodo = arrayObservaciones[1];
							parametros.add(ejercicio);
							parametros.add(periodo);
	
						}
	
						DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(coModelo, coVersion, coDocumento));
	
						AccesoPlantillaVO accesoPlantillaVO= new AccesoPlantillaVO();
						accesoPlantillaVO.setCoProvincia(documentoDTO.getMunicipioDTO().getId().getCoProvincia());
						accesoPlantillaVO.setCoMunicipio(documentoDTO.getMunicipioDTO().getId().getCoMunicipio());
						accesoPlantillaVO.setCoConcepto(documentoDTO.getConceptoDTO().getCoConcepto());
						accesoPlantillaVO.setCoModelo(documentoDTO.getId().getCoModelo());
						accesoPlantillaVO.setCoVersion(documentoDTO.getId().getCoVersion());
	
						String proceso = excepcionesPreIncidencia(incidenciaSituacionDTO, coModelo, coVersion, coDocumento);
						EjecucionDTO ejecucionDTO = Batch.ejecutarDevolverDTO(proceso, parametros, accesoPlantillaVO);
						resultado = excepcionesPostIncidencia(ejecucionDTO, incidenciaSituacionDTO, coModelo, coVersion, coDocumento);
	
					} else if (Utilidades.isNotEmpty(incidenciaSituacionDTO.getProcesoDTO().getUrl())) {
						// PROCESO PANTALLA
						resultado = new KeyValue(IncidenciaConstants.RESULTADO_URL, incidenciaSituacionDTO.getProcesoDTO().getUrl().replace("{MODELO}", coModelo).replace("{VERSION}", coVersion).replace("{DOCUMENTO}", coDocumento));
					} else {
						resultado = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, "No hay programa asociado al proceso");
					}
				}else{
					resultado = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, "No hay proceso asignado a la incidencia");
				}
			}
		}catch(Exception e){
			LOG.error("ejecutarIncidencia", e);
		}

		return resultado;
	}

	private static KeyValue excepcionesPostIncidencia(EjecucionDTO ejecucionDTO, IncidenciaSituacionDTO incidenciaSituacionDTO, String coModelo, String coVersion, String coDocumento) throws GadirServiceException {
		KeyValue result = null;
		if (ejecucionDTO.getCoTerminacion()==0) {
			if(IncidenciaConstants.CO_INCIDENCIA_CAMBIO_TITULARIDAD.equals(incidenciaSituacionDTO.getIncidenciaDTO().getCoIncidencia())) {
				result = excepcionCambioTitularidad(ejecucionDTO, coModelo, coVersion);
			}
			if (result == null) {
				result = new KeyValue(IncidenciaConstants.RESULTADO_CORRECTO, "Incidencia ejecutada correctamente");
			}
		}else{
			result = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, Mensaje.getTexto(ejecucionDTO.getCoTerminacion(), ejecucionDTO.getObservaciones()));
		}
		return result;
	}

	private static KeyValue excepcionCambioTitularidad(EjecucionDTO ejecucionDTO, String coModelo, String coVersion) throws GadirServiceException {
		KeyValue result = null;
		if (Utilidades.isNotEmpty(ejecucionDTO.getObservaciones())) {
			String[] bits = ejecucionDTO.getObservaciones().split(" ");
			String cadenaNuevoDocumento = bits[bits.length-1];
			DocumentoDTOId documentoDTOId = new DocumentoDTOId(cadenaNuevoDocumento.substring(0, 3), cadenaNuevoDocumento.substring(4, 5), cadenaNuevoDocumento.substring(6));
			DocumentoDTO nuevoDocumento = documentoBO.findById(documentoDTOId);

			List<String> parametrosBatchLanzar = new ArrayList<String>();

			parametrosBatchLanzar.add(nuevoDocumento.getMunicipioDTO().getId().getCoProvincia()); 
			parametrosBatchLanzar.add(nuevoDocumento.getMunicipioDTO().getId().getCoMunicipio());
			parametrosBatchLanzar.add(nuevoDocumento.getConceptoDTO().getCoConcepto());
			parametrosBatchLanzar.add(nuevoDocumento.getId().getCoModelo());
			parametrosBatchLanzar.add(nuevoDocumento.getId().getCoVersion());
			parametrosBatchLanzar.add(nuevoDocumento.getId().getCoDocumento());

			// En caso de tener que ir al mantenimiento de casillas, ir aquí:
			String siError = "mantenimientoCasillas.action";
			siError += "?coModelo="+documentoDTOId.getCoModelo();
			siError += "&coVersion="+documentoDTOId.getCoVersion();
			siError += "&coDocumento="+documentoDTOId.getCoDocumento();
			siError += "&operacion=" + G5114AbstractAction.OPERACION_MODIFICA;
			siError += "&edicion=true";
			siError += "&nuevoDocumento=false";
			siError += "&borrarDocumento=false";
			siError += "&crearHoja=false";
			siError += "&eliminarHoja=false";
			siError += "&guardar=true";
			siError += "&calcular=false";
			siError += "&verTodoElDocumento=true";
			siError += "&mostrarColumnaError=true";
			siError += "&pedirDocOrigen=false";

			int res = Batch.ejecutar(Batch.CO_PROCESO_VALIDACION, parametrosBatchLanzar, new AccesoPlantillaVO());
			nuevoDocumento = documentoBO.findById(new DocumentoDTOId(coModelo, coVersion, nuevoDocumento.getId().getCoDocumento()));

			if(res == 0) { //ejecutamos correspondencias
				if(!"B".equals(nuevoDocumento.getEstado()) && !"C".equals(nuevoDocumento.getEstado()))
					result = new KeyValue(IncidenciaConstants.RESULTADO_URL, siError);
				else {
					if("C".equals(nuevoDocumento.getEstado())) {
						res = Batch.ejecutar(Batch.CO_PROCESO_CORRESPONDENCIAS, parametrosBatchLanzar, new AccesoPlantillaVO());
						nuevoDocumento = documentoBO.findById(new DocumentoDTOId(coModelo, coVersion, nuevoDocumento.getId().getCoDocumento()));

						if(res == 0) {  //proceso de correspondencias correcto
							if(!"B".equals(nuevoDocumento.getEstado()))
								result = new KeyValue(IncidenciaConstants.RESULTADO_URL, siError);
							else {
								result = new KeyValue(IncidenciaConstants.RESULTADO_CORRECTO, Mensaje.getTexto(MensajeConstants.V1, "Cambio de titularidad realizado correctamente, nuevo documento: " + coModelo + " " + coVersion + " " + nuevoDocumento.getId().getCoDocumento()));
							}
						}
						else {							
							result = new KeyValue(IncidenciaConstants.RESULTADO_URL, siError);
						}								
					}
					else 
						result = new KeyValue(IncidenciaConstants.RESULTADO_CORRECTO, Mensaje.getTexto(MensajeConstants.V1, "Cambio de titularidad realizado correctamente, nuevo documento: " + coModelo + " " + coVersion + " " + nuevoDocumento.getId().getCoDocumento()));
				}
			}
			else {							
				result = new KeyValue(IncidenciaConstants.RESULTADO_URL, siError);
			}
		} else {
			result = new KeyValue(IncidenciaConstants.RESULTADO_ERROR, "Proceso de cambio de titularidad no devuelve documento creado.");
		}
		return result;
	}

	private static String excepcionesPreIncidencia(IncidenciaSituacionDTO incidenciaSituacionDTO, String coModelo, String coVersion, String coDocumento) throws GadirServiceException {
		String proceso = incidenciaSituacionDTO.getProcesoDTO().getCoProceso();

		//CASOS PARTICULARES

		//Comprobar si los justificantes son de copia o no
		if(incidenciaSituacionDTO.getProcesoDTO().getCoProceso().equals("JUST_PAGO")){

			List<DocumentoSeguimientoDTO> listaDocSegDTO = documentoSeguimientoBO.findFiltered(
					new String[]{"documentoDTO.id.coModelo", "documentoDTO.id.coVersion", "documentoDTO.id.coDocumento", "incidenciaDTO.coIncidencia"}, 
					new Object[]{coModelo, coVersion, coDocumento, "IJP"});

			if(listaDocSegDTO.size()>0){
				boolean encontrado = false;
				int i = 0;
				while(i < listaDocSegDTO.size() && !encontrado) {
					DocumentoSeguimientoDTO  docSeg = listaDocSegDTO.get(i);
					if(docSeg.getCoEjecucion() != null) {
						List<EjecucionParametroDTO> params = ejecucionParametroBO.findFiltered(
								new String[]{"ejecucionDTO.coEjecucion", "id.nuParametro"}, 
								new Object[]{docSeg.getCoEjecucion(), (short)4}); //parámetro con el valor del código del pago parcial
						if(params.size() == 1) {
							if(Utilidades.isEmpty(params.get(0).getValor()))
								encontrado = true;
						}							
					}
					i++;
				}
				if(encontrado)
					proceso = "JUST_PAGO_COPIA";						
			}
		}
		else if(incidenciaSituacionDTO.getProcesoDTO().getCoProceso().equals("JUST_BAJA")){

			List<DocumentoSeguimientoDTO> listaDocSegDTO = documentoSeguimientoBO.findFiltered(
					new String[]{"documentoDTO.id.coModelo", "documentoDTO.id.coVersion", "documentoDTO.id.coDocumento", "incidenciaDTO.coIncidencia"}, 
					new Object[]{coModelo, coVersion, coDocumento, "IJB"});

			if(listaDocSegDTO.size()>0){
				proceso = "JUST_BAJA_COPIA";
			}
		}else if(incidenciaSituacionDTO.getProcesoDTO().getCoProceso().equals(IncidenciaConstants.CO_INCIDENCIA_GENERAR_RECIBO)){ //martecher
			proceso = BatchConstants.CO_PROCESO_GENREC;
		}
		return proceso;
	}

	public static boolean isResultadoURL(KeyValue resultado, AbstractGadirBaseAction action, HttpServletResponse httpServletResponse) {
		boolean result = false;
		if (IncidenciaConstants.RESULTADO_URL.equals(resultado.getKey()) && Utilidades.isNotEmpty(resultado.getValue())) {
			action.apilar();
			try {
				httpServletResponse.sendRedirect(resultado.getValue());
				result = true;
			} catch (IOException e) {
				LOG.error("Error redirigiendo a " + resultado, e);
			}
		}
		return result;
	}

	public static String ejecutarIncidenciaCargoSubcargo(String coCargoSubcargo, String coIncidSitCargo, String ordenacion, Date fechaInicioVoluntaria, Date fechaFinVoluntaria) {
		String resultado="Error";

		try{
			IncidSitCargoDTO incidSitCargoDTO = incidSitCargoBO.findById(Long.valueOf(coIncidSitCargo));

			if (incidSitCargoDTO.getProcesoDTO()!=null){
				CargoSubcargoDTO cargoSubcargoDTO = cargoSubcargoBO.findByIdInitialized(Long.valueOf(coCargoSubcargo), new String[]{"cargoDTO"});

				List<String> parametros = new ArrayList<String>();
				parametros.add(incidSitCargoDTO.getIncidenciaCargoDTO().getId().getCoIncidenciaCargo());
				parametros.add(ordenacion);  //ordenacion (R razon social, D domicilio fiscal, O ref. trib)
				parametros.add(coCargoSubcargo);
				parametros.add(" ");
				parametros.add(Utilidades.dateToDDMMYYYY(fechaInicioVoluntaria)); //fecha de inicio voluntaria (dd/mm/aaaa)
				parametros.add(Utilidades.dateToDDMMYYYY(fechaFinVoluntaria)); //fecha de fin voluntaria (dd/mm/aaaa)

				parametrosAdicionalesCargoSubcargo(incidSitCargoDTO.getProcesoDTO().getCoProceso(), parametros, cargoSubcargoDTO);

				DetachedCriteria criterio = DetachedCriteria.forClass(DocumentoLiquidacionDTO.class);
				criterio.add(Restrictions.eq("cargoSubcargoDTO.coCargoSubcargo", Long.valueOf(coCargoSubcargo)));
				List<DocumentoLiquidacionDTO> listaDocs = documentoLiquidacionBO.findByCriteria(criterio, 0, 1);

				String version = "*";

				if (listaDocs!=null && !listaDocs.isEmpty()){
					DocumentoLiquidacionDTO doc = listaDocs.get(0);
					version=doc.getId().getCoVersion();
				}

				AccesoPlantillaVO accesoPlantillaVO= new AccesoPlantillaVO();
				accesoPlantillaVO.setCoProvincia(cargoSubcargoDTO.getCargoDTO().getMunicipioDTO().getId().getCoProvincia());
				accesoPlantillaVO.setCoMunicipio(cargoSubcargoDTO.getCargoDTO().getMunicipioDTO().getId().getCoMunicipio());
				accesoPlantillaVO.setCoConcepto(cargoSubcargoDTO.getConceptoDTO().getCoConcepto());
				accesoPlantillaVO.setCoModelo(cargoSubcargoDTO.getCargoDTO().getModeloDTO().getCoModelo());
				accesoPlantillaVO.setCoVersion(version);

				try{
					Batch.lanzar(incidSitCargoDTO.getProcesoDTO().getCoProceso(), DatosSesion.getLogin(), parametros, DatosSesion.getImpresora(), accesoPlantillaVO);
					resultado="";
				}catch(Exception e){
					resultado= e.getMessage();
				}
			}else{
				resultado="No hay proceso asignado a la incidencia";
			}


		}catch(Exception e){
			LOG.error("ejecutarIncidencia", e);
		}

		return resultado;
	}

	private static void parametrosAdicionalesCargoSubcargo(String coProceso, List<String> parametros, CargoSubcargoDTO cargoSubcargoDTO) {
		if (BatchConstants.CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFS.equalsIgnoreCase(coProceso) || 
				BatchConstants.CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFI.equals(coProceso) ||
				BatchConstants.CO_PROCESO_G771_RE_FICHERO_INTERCAMBIO.equals(coProceso)) {
			anadirFicheroIntercambio(parametros, cargoSubcargoDTO.getCargoDTO().getNuCargo(), cargoSubcargoDTO.getCargoDTO().getAnoCargo(), cargoSubcargoDTO.getConceptoDTO().getCoConcepto(), cargoSubcargoDTO.getCargoDTO().getModeloDTO().getCoModelo());
		}
	}

	public static String ejecutarIncidenciaCargo(String coCargo, String coIncidSitCargo, String ordenacion, Date fechaInicioVoluntaria, Date fechaFinVoluntaria) {
		String resultado="Error";

		try{
			IncidSitCargoDTO incidSitCargoDTO = incidSitCargoBO.findById(Long.valueOf(coIncidSitCargo));

			if (incidSitCargoDTO.getProcesoDTO()!=null){
				CargoDTO cargoDTO = cargoBO.findById(Long.valueOf(coCargo));

				List<String> parametros = new ArrayList<String>();
				parametros.add(incidSitCargoDTO.getIncidenciaCargoDTO().getId().getCoIncidenciaCargo());
				parametros.add(ordenacion);  //ordenacion (R razon social, D domicilio fiscal, O ref. trib)
				parametros.add(" "); 
				parametros.add(coCargo);
				parametros.add(Utilidades.dateToDDMMYYYY(fechaInicioVoluntaria)); //fecha de inicio voluntaria (dd/mm/aaaa)
				parametros.add(Utilidades.dateToDDMMYYYY(fechaFinVoluntaria)); //fecha de fin voluntaria (dd/mm/aaaa)

				parametrosAdicionalesCargo(incidSitCargoDTO.getProcesoDTO().getCoProceso(), parametros, cargoDTO);

				String version = "*";
				String concepto = "";

				List<CargoSubcargoDTO> listaSubcargos = cargoSubcargoBO.findFiltered("cargoDTO.coCargo", Long.valueOf(coCargo));

				if (listaSubcargos!=null){
					for (CargoSubcargoDTO cargoSubcargoDTO: listaSubcargos){
						if (version.equals("*")){
							DetachedCriteria criterio = DetachedCriteria.forClass(DocumentoLiquidacionDTO.class);
							criterio.add(Restrictions.eq("cargoSubcargoDTO.coCargoSubcargo", cargoSubcargoDTO.getCoCargoSubcargo()));
							List<DocumentoLiquidacionDTO> listaDocs = documentoLiquidacionBO.findByCriteria(criterio, 0, 1);
							if (listaDocs!=null && !listaDocs.isEmpty()){
								DocumentoLiquidacionDTO doc = listaDocs.get(0);
								version=doc.getId().getCoVersion();
							}
						}
						if (concepto.equals("")){
							concepto= cargoSubcargoDTO.getConceptoDTO().getCoConcepto();
						}else{
							if (!concepto.equals("****")){
								if (!concepto.equals(cargoSubcargoDTO.getConceptoDTO().getCoConcepto())){
									concepto = "****";
								}
							}
						}

					}
				}
				if (concepto.equals("")) concepto = "****";

				AccesoPlantillaVO accesoPlantillaVO= new AccesoPlantillaVO();
				accesoPlantillaVO.setCoProvincia(cargoDTO.getMunicipioDTO().getId().getCoProvincia());
				accesoPlantillaVO.setCoMunicipio(cargoDTO.getMunicipioDTO().getId().getCoMunicipio());
				accesoPlantillaVO.setCoConcepto(concepto);
				accesoPlantillaVO.setCoModelo(cargoDTO.getModeloDTO().getCoModelo());
				accesoPlantillaVO.setCoVersion(version);

				try{
					Batch.lanzar(incidSitCargoDTO.getProcesoDTO().getCoProceso(), DatosSesion.getLogin(), parametros, DatosSesion.getImpresora(), accesoPlantillaVO);
					resultado="";
				}catch(Exception e){
					resultado= e.getMessage();
				}
			}else{
				resultado="No hay proceso asignado a la incidencia";
			}


		}catch(Exception e){
			LOG.error("ejecutarIncidencia", e);
		}

		return resultado;
	}

	private static void parametrosAdicionalesCargo(String coProceso, List<String> parametros, CargoDTO cargoDTO) {
		if (BatchConstants.CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFS.equalsIgnoreCase(coProceso) || 
				BatchConstants.CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFI.equals(coProceso) ||
				BatchConstants.CO_PROCESO_G771_RE_FICHERO_INTERCAMBIO.equals(coProceso)) {
			anadirFicheroIntercambio(parametros, cargoDTO.getNuCargo(), cargoDTO.getAnoCargo(), null, cargoDTO.getModeloDTO().getCoModelo());
		}
	}

	private static void anadirFicheroIntercambio(List<String> parametros, Short nuCargo, Short anyoCargo, String coConcepto, String coModelo) {
		while (parametros.size() < 7) { 
			parametros.add(""); 
		}
		parametros.add("FIT-" + nuCargo.toString() + "-" + anyoCargo.toString() + "-" + (coConcepto == null ? "" : coConcepto + "-") + coModelo + "-" + DatosSesion.getLogin() + "-" + Utilidades.dateToYYYYMMDDHHMMSS(Utilidades.getDateActual()) + ".DAT"); // Nombre de fichero, tiene que ser la 8
		parametros.add(DatosSesion.getCarpetaAcceso()); // Ruta destino, tiene que ser la 9
	}

	public static String ejecutarIncidenciaPpp(String coPpp, String coIncidSitCargo, String observaciones) {
		String resultado="Error ejecutando la opción seleccionada";

		try{
			IncidSitCargoDTO incidSitCargoDTO = incidSitCargoBO.findById(Long.valueOf(coIncidSitCargo));

			if (incidSitCargoDTO.getProcesoDTO()!=null){
				List<String> parametros = new ArrayList<String>();
				//parametros.add(incidSitCargoDTO.getIncidenciaCargoDTO().getId().getCoIncidenciaCargo());
				//parametros.add(" ");
				parametros.add(coPpp);

				//Sólo se añaden las observaciones para Anular
				parametros.add(observaciones);

				AccesoPlantillaVO accesoPlantillaVO= new AccesoPlantillaVO();
				accesoPlantillaVO.setCoProvincia(ProvinciaConstants.CO_PROVINCIA_CADIZ);
				accesoPlantillaVO.setCoMunicipio(MunicipioConstants.CO_MUNICIPIO_DIPUTACION_CADIZ);
				accesoPlantillaVO.setCoConcepto(ConceptoConstants.CO_CONCEPTO_PLAZOS);
				accesoPlantillaVO.setCoModelo(ModeloConstants.CO_MODELO_PLAZO_PPP);
				accesoPlantillaVO.setCoVersion("1");				

				int error=Batch.ejecutar(incidSitCargoDTO.getProcesoDTO().getCoProceso(), parametros, accesoPlantillaVO);
				if (error==0){
					resultado = "";
				}else{
					resultado= Mensaje.getTexto(error);
				}
			}else{
				resultado="No hay proceso asignado a la incidencia";
			}

		}catch(Exception e){
			LOG.error("ejecutarIncidencia", e);
		}

		return resultado;
	}

	public static String ejecutarIncidenciaFraccionamiento(String coFraccionamiento, String coIncidSitCargo, String observaciones, String coSituacionDestino, BigDecimal importePlazo ) {
		String resultado="Error";

		try{
			IncidSitCargoDTO incidSitCargoDTO = incidSitCargoBO.findById(Long.valueOf(coIncidSitCargo));

			if (incidSitCargoDTO.getProcesoDTO()!=null){
				List<String> parametros = new ArrayList<String>();
				//Pasamos el coIncidencia para el caso de anular/denegar poder discernir entre ambos
				parametros.add(incidSitCargoDTO.getIncidenciaCargoDTO().getId().getCoIncidenciaCargo());
				parametros.add(coFraccionamiento);

				//Sólo se añaden las observaciones para Anular y Denegar
				if (!Utilidades.isEmpty(observaciones)){
					parametros.add(observaciones);
				}

				if(!Utilidades.isEmpty(coSituacionDestino)){
					parametros.add(coSituacionDestino);
				}
				if(importePlazo!=null){
					parametros.add(Utilidades.bigDecimalToImporteGadir(importePlazo));
				}
				AccesoPlantillaVO accesoPlantillaVO= new AccesoPlantillaVO();
				accesoPlantillaVO.setCoProvincia(ProvinciaConstants.CO_PROVINCIA_CADIZ);
				accesoPlantillaVO.setCoMunicipio(MunicipioConstants.CO_MUNICIPIO_DIPUTACION_CADIZ);
				accesoPlantillaVO.setCoConcepto(ConceptoConstants.CO_CONCEPTO_PLAZOS);
				accesoPlantillaVO.setCoModelo(ModeloConstants.CO_MODELO_PLAZO_FRACCIONAMIENTO);
				accesoPlantillaVO.setCoVersion("1");

				int error=Batch.ejecutar(incidSitCargoDTO.getProcesoDTO().getCoProceso(), parametros, accesoPlantillaVO);
				if (error==0){
					resultado = "";
				}else{
					resultado= Mensaje.getTexto(error);
				}
			}else{
				resultado="No hay proceso asignado a la incidencia";
			}

		}catch(Exception e){
			LOG.error("ejecutarIncidencia", e);
		}

		return resultado;
	}

	private static DetachedCriteria getCriterio(String coProvincia, String coMunicipio, String coModelo, String coVersion, String situacion, String estado, String modo){
		DetachedCriteria criterio = DetachedCriteria.forClass(IncidenciaSituacionDTO.class);

		criterio.createAlias("incidenciaDTO", "i");

		criterio.add(Restrictions.eq("boSeleccionable", true));
		criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
		criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
		criterio.add(Restrictions.eq("modeloVersionDTO.id.coModelo", coModelo));
		criterio.add(Restrictions.eq("modeloVersionDTO.id.coVersion", coVersion));
		criterio.add(Restrictions.eq("situacionDTOByCoSituacionOrigen.coSituacion", situacion));
		criterio.addOrder(Order.asc("incidenciaDTO.coIncidencia"));

		if (Utilidades.isNull(estado)) {
			criterio.add(Restrictions.isNull("estadoSituacionDTOByCoEstadoSituacionOrigen"));
		} else {
			if(!estado.equals("dummy"))
				criterio.add(Restrictions.eq("estadoSituacionDTOByCoEstadoSituacionOrigen.coEstadoSituacion", estado));
		}
		if (Utilidades.isNull(modo)) {
			criterio.add(Restrictions.isNull("modoCobroDTOByCoModoCobroOrigen"));
		} else {
			if(!estado.equals("dummy"))
				criterio.add(Restrictions.eq("modoCobroDTOByCoModoCobroOrigen.coModoCobro", modo));
		}

		return criterio;
	}

	private static DetachedCriteria getCriterioIncidenciaSituacion(String coProvincia, String coMunicipio, String coModelo, String coVersion, String situacion, String estado, String modo, String incidencia){
		DetachedCriteria criterio = DetachedCriteria.forClass(IncidenciaSituacionDTO.class);

		criterio.createAlias("incidenciaDTO", "i");

		criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
		criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
		criterio.add(Restrictions.eq("modeloVersionDTO.id.coModelo", coModelo));
		criterio.add(Restrictions.eq("modeloVersionDTO.id.coVersion", coVersion));
		criterio.add(Restrictions.eq("incidenciaDTO.coIncidencia", incidencia));
		criterio.addOrder(Order.asc("incidenciaDTO.coIncidencia"));

		if (Utilidades.isNull(situacion)) {
			criterio.add(Restrictions.isNull("situacionDTOByCoSituacionOrigen"));
		} else {
			criterio.add(Restrictions.eq("situacionDTOByCoSituacionOrigen.coSituacion", situacion));
		}
		if (Utilidades.isNull(estado)) {
			criterio.add(Restrictions.isNull("estadoSituacionDTOByCoEstadoSituacionOrigen"));
		} else {
			if(!estado.equals("dummy"))
				criterio.add(Restrictions.eq("estadoSituacionDTOByCoEstadoSituacionOrigen.coEstadoSituacion", estado));
		}
		if (Utilidades.isNull(modo)) {
			criterio.add(Restrictions.isNull("modoCobroDTOByCoModoCobroOrigen"));
		} else {
			if(!estado.equals("dummy"))
				criterio.add(Restrictions.eq("modoCobroDTOByCoModoCobroOrigen.coModoCobro", modo));
		}

		return criterio;
	}

	private static DetachedCriteria getCriterioIncidSitCargo(String coProvincia, String coMunicipio, String coModelo, String situacion, String tipo){
		DetachedCriteria criterio = DetachedCriteria.forClass(IncidSitCargoDTO.class);

		criterio.createAlias("incidenciaCargoDTO", "i");

		criterio.add(Restrictions.eq("boSeleccionable", true));
		criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
		criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
		criterio.add(Restrictions.eq("modeloVersionDTO.id.coModelo", coModelo));
		if (situacion==null){
			criterio.add(Restrictions.isNull("situacionCargoDTOOrigen.id.coSituacionCargo"));
		}else{
			criterio.add(Restrictions.eq("situacionCargoDTOOrigen.id.coSituacionCargo", situacion));
		}		
		criterio.add(Restrictions.eq("situacionCargoDTOOrigen.id.tipo", tipo));

		return criterio;
	}

	private static DetachedCriteria getCriterioSituaciones(String coProvincia, String coMunicipio, String coModelo, String coVersion, String coIncidencia){
		DetachedCriteria criterio = DetachedCriteria.forClass(SituacionDTO.class);

		criterio.createAlias("incidenciaSituacionDTOsForCoSituacionOrigen", "is");

		criterio.add(Restrictions.eq("is.municipioDTO.id.coProvincia", coProvincia));
		criterio.add(Restrictions.eq("is.municipioDTO.id.coMunicipio", coMunicipio));
		criterio.add(Restrictions.eq("is.modeloVersionDTO.id.coModelo", coModelo));
		criterio.add(Restrictions.eq("is.modeloVersionDTO.id.coVersion", coVersion));
		criterio.add(Restrictions.eq("is.incidenciaDTO.coIncidencia", coIncidencia));

		return criterio;
	}

	public static List<DocumentoSeguimientoDTO> getListaSeguimiento(String coModelo, String coVersion, String coDocumento, String coIncidencia) {
		DetachedCriteria criterio = DetachedCriteria.forClass(DocumentoSeguimientoDTO.class);
		criterio.add(Restrictions.eq("documentoDTO.id.coModelo", coModelo));
		criterio.add(Restrictions.eq("documentoDTO.id.coVersion", coVersion));
		criterio.add(Restrictions.eq("documentoDTO.id.coDocumento", coDocumento));
		criterio.add(Restrictions.eq("incidenciaDTO.coIncidencia", coIncidencia));
		List<DocumentoSeguimientoDTO> listaSeguimiento;
		try {
			listaSeguimiento = documentoSeguimientoBO.findByCriteria(criterio);
		} catch (GadirServiceException e) {
			LOG.error("Error buscando en seguimiento.", e);
			listaSeguimiento = null;
		}
		return listaSeguimiento;
	}

	public IncidenciaBO getIncidenciaBO() {
		return incidenciaBO;
	}

	public void setIncidenciaBO(IncidenciaBO incidenciaBO) {
		OpcionesIncidenciaSituacionUtil.incidenciaBO = incidenciaBO;
	}

	public SituacionBO getSituacionBO() {
		return situacionBO;
	}

	public void setSituacionBO(SituacionBO situacionBO) {
		OpcionesIncidenciaSituacionUtil.situacionBO = situacionBO;
	}

	public IncidenciaSituacionBO getIncidenciaSituacionBO() {
		return incidenciaSituacionBO;
	}

	public void setIncidenciaSituacionBO(
			IncidenciaSituacionBO incidenciaSituacionBO) {
		OpcionesIncidenciaSituacionUtil.incidenciaSituacionBO = incidenciaSituacionBO;
	}

	public ProcesoBO getProcesoBO() {
		return procesoBO;
	}

	public void setProcesoBO(ProcesoBO procesoBO) {
		OpcionesIncidenciaSituacionUtil.procesoBO = procesoBO;
	}

	public IncidSitCargoBO getIncidSitCargoBO() {
		return incidSitCargoBO;
	}

	public void setIncidSitCargoBO(IncidSitCargoBO incidSitCargoBO) {
		OpcionesIncidenciaSituacionUtil.incidSitCargoBO = incidSitCargoBO;
	}

	public IncidenciaCargoBO getIncidenciaCargoBO() {
		return incidenciaCargoBO;
	}

	public void setIncidenciaCargoBO(IncidenciaCargoBO incidenciaCargoBO) {
		OpcionesIncidenciaSituacionUtil.incidenciaCargoBO = incidenciaCargoBO;
	}

	public SituacionCargoBO getSituacionCargoBO() {
		return situacionCargoBO;
	}

	public void setSituacionCargoBO(SituacionCargoBO situacionCargoBO) {
		OpcionesIncidenciaSituacionUtil.situacionCargoBO = situacionCargoBO;
	}

	public DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	public void setDocumentoBO(DocumentoBO documentoBO) {
		OpcionesIncidenciaSituacionUtil.documentoBO = documentoBO;
	}

	public CargoBO getCargoBO() {
		return cargoBO;
	}

	public void setCargoBO(CargoBO cargoBO) {
		OpcionesIncidenciaSituacionUtil.cargoBO = cargoBO;
	}

	public CargoSubcargoBO getCargoSubcargoBO() {
		return cargoSubcargoBO;
	}

	public void setCargoSubcargoBO(CargoSubcargoBO cargoSubcargoBO) {
		OpcionesIncidenciaSituacionUtil.cargoSubcargoBO = cargoSubcargoBO;
	}

	public ModeloBO getModeloBO() {
		return modeloBO;
	}

	public void setModeloBO(ModeloBO modeloBO) {
		OpcionesIncidenciaSituacionUtil.modeloBO = modeloBO;
	}

	public DocumentoLiquidacionBO getDocumentoLiquidacionBO() {
		return documentoLiquidacionBO;
	}

	public void setDocumentoLiquidacionBO(
			DocumentoLiquidacionBO documentoLiquidacionBO) {
		OpcionesIncidenciaSituacionUtil.documentoLiquidacionBO = documentoLiquidacionBO;
	}

	public DocumentoSeguimientoBO getDocumentoSeguimientoBO() {
		return documentoSeguimientoBO;
	}

	public void setDocumentoSeguimientoBO(
			DocumentoSeguimientoBO documentoSeguimientoBO) {
		OpcionesIncidenciaSituacionUtil.documentoSeguimientoBO = documentoSeguimientoBO;
	}

	public GenericBO<EjecucionParametroDTO, EjecucionParametroDTOId> getEjecucionParametroBO() {
		return ejecucionParametroBO;
	}

	public void setEjecucionParametroBO(
			GenericBO<EjecucionParametroDTO, EjecucionParametroDTOId> ejecucionParametroBO) {
		OpcionesIncidenciaSituacionUtil.ejecucionParametroBO = ejecucionParametroBO;
	}

	public CasillaMunicipioBO getCasillaMunicipioBO() {
		return casillaMunicipioBO;
	}

	public void setCasillaMunicipioBO(CasillaMunicipioBO casillaMunicipioBO) {
		OpcionesIncidenciaSituacionUtil.casillaMunicipioBO = casillaMunicipioBO;
	}

	public static List<IncidenciaSituacionDTO> tratarExcepciones(List<IncidenciaSituacionDTO> listaOpciones, DocumentoDTO documentoDTO, DocumentoLiquidacionDTO documentoLiquidacionDTO) {
		// Excepciones de liquidaciones/recibos
		if (documentoLiquidacionDTO != null) {
			for (int i=listaOpciones.size()-1; i>=0; i--) {
				if (IncidenciaConstants.CO_INCIDENCIA_PASE_EJECUTIVA.equals(listaOpciones.get(i).getIncidenciaDTO().getCoIncidencia())) {
					// Comprobar fecha fin de voluntaria
					if (documentoLiquidacionDTO.getFxFinVoluntario() == null || !documentoLiquidacionDTO.getFxFinVoluntario().before(Utilidades.getFechaActual()) || documentoLiquidacionDTO.getFxFinVoluntario().equals(documentoLiquidacionDTO.getFxIniVoluntario())) {
						listaOpciones.remove(i);
						continue;
					}
				} else if (IncidenciaConstants.CO_FASE_4_BLOQUEAR_DOC.equals(listaOpciones.get(i).getIncidenciaDTO().getCoIncidencia())) {
					// Comprobar si está en Fase 4 no cerrado con importe retenido disponible
					String query = 
							"SELECT 1" +
							"  from ga_c63_cliente cli" +
							"  where fx_cierre_fase4 is null" +
							"    and cli.TO_IMPORTE_RETENIDO is not null" +
							"    and cli.TO_IMPORTE_RETENIDO > 0" +
							"    and cli.co_cliente=" + documentoDTO.getClienteDTO().getCoCliente().toString() +
							"    and exists (select 1" +
							"                  from GA_EXPEDIENTE_CLIENTE_DOC exp" +
							"                  where exp.CO_EXPEDIENTE = cli.CO_EXPEDIENTE" +
							"                    and co_modelo='" + documentoDTO.getId().getCoModelo() + "' and co_version='" + documentoDTO.getId().getCoVersion() + "' and co_documento='" + documentoDTO.getId().getCoDocumento() + "')" +
							"    and cli.TO_IMPORTE_RETENIDO>(select sum(cue.IM_A_LEVANTAR)" +
							"                                  from GA_C63_CUENTA cue" +
							"                                  where cue.CO_C63_CLIENTE = cli.CO_C63_CLIENTE)";
					try {
						@SuppressWarnings("unchecked")
						ArrayList<Object> objects = (ArrayList<Object>) documentoBO.ejecutaQuerySelect(query);
						if (objects.isEmpty()) {
							listaOpciones.remove(i);
							continue;
						}
					} catch (GadirServiceException e) {
						LOG.error(e.getMensaje(), e);
					}
				}
			}
		}
		return listaOpciones;
	}




}
