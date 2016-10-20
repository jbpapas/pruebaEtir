/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import es.dipucadiz.etir.comun.bo.TablaGtGetListaElementosBO;
import es.dipucadiz.etir.comun.bo.TablaGtGetValorBO;
import es.dipucadiz.etir.comun.bo.TablaGtGetValoresBO;
import es.dipucadiz.etir.comun.bo.TablaGtIsElementoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.TablaGtColumnaDTO;
import es.dipucadiz.etir.comun.dto.TablaGtDTO;
import es.dipucadiz.etir.comun.dto.TablaGtElementoDTO;
import es.dipucadiz.etir.comun.dto.TablaGtUsuarioDTO;

/**
 * Acceso a tablas GT
 * @author jronnols
 *
 */
final public class TablaGt {

	private static TablaGtIsElementoBO tablaGtIsElementoBO;
	private static TablaGtGetListaElementosBO tablaGtGetListaElementosBO;
	private static TablaGtGetValoresBO tablaGtGetValoresBO;
	private static TablaGtGetValorBO tablaGtGetValorBO;
	
	private static DAOBase<TablaGtDTO, String> tablaGtDAO;
	private static DAOBase<TablaGtUsuarioDTO, Long> tablaGtUsuarioDAO;

	private static Map<String, String> valorTablaMap = new HashMap<String, String>();
	
	private final static String VALOR = "VALOR";
	
	public final static String COLUMNA_CODIGO = "CODIGO";
	public final static String COLUMNA_DESCRIPCION = "DESCRIPCION";
	public final static String COLUMNA_EXENCION_BONIFICACION = "EXENCIÓN/BONIFICACIÓN";
	public final static String COLUMNA_POR_LEY_POR_ORDENANZA = "POR LEY/POR ORDENANZA";
	public final static String COLUMNA_CONCEPTO = "CONCEPTO";
	public final static String COLUMNA_INCIDENCIA = "INCIDENCIA";
	public final static String COLUMNA_PLANTILLA = "PLANTILLA";
	public final static String COLUMNA_BOP = "BOP";
	public static final String COLUMNA_TIPO = "TIPO";
	public static final String COLUMNA_CRITERIO = "CRITERIO";
	public static final String COLUMNA_BOP_VOLUNTARIA = "PERIODO VOLUNTARIO";
	public static final String COLUMNA_NOTIFICACION = "NOTIFICACION";
	public static final String COLUMNA_BOP_NOT_APREMIO = "PROVIDENCIA APREMIO";
	public static final String COLUMNA_SELECCIONABLE = "SELECCIONABLE";
	public static final String COLUMNA_GRUPO_DE_EMISORA = "GRUPO DE EMISORA";
	public static final String COLUMNA_RES_REMESA_CANAL = "RESULTADO REMESA CANAL";
	public final static String TABLA_TIPO_ANTICIPO = "TANTICI";
	public final static String TABLA_TIPO_CONVENIO = "TAB0176";
	public final static String TABLA_PERIODO = "TPERIOD";
	public final static String TABLA_OTR = "TDESOTR";
	public final static String TABLA_PERIODOS_GADIR = "TPERIOD";
	public final static String TABLA_PERIODOS_SIGRE = "PERSIGRE";
	public final static String TABLA_TIPO_TRAMO_CALLE = "TIPTRAM";
	public final static String TABLA_FUENTE_PROCEDENCIA = "TFUEPRO";
	public final static String TABLA_TIPO_VIA_PUBLICA = "TSIGVIA";
	public final static String TABLA_CARGOS = "TAB0010";
	public final static String TABLA_ORGANO_PRIMER_NIVEL = "TORGPRI";
	public final static String TABLA_AMBITO_TERRITORIAL = "TAB0006";
	public final static String TABLA_ORGANO_TERCER_NIVEL = "TORGTER";
	public final static String TABLA_TIPO_DIA_FESTIVO = "TIDIAFES";
	public final static String TABLA_ESTRUCTURAS_CARGA_DESCARGA = "TCARDESC";
	public final static String TABLA_ESTADO_ACM_PERFIL = "TESTPERF";
	public final static String TABLA_PROCESOS_FUNCIONES = "TFUNCIO";
	public final static String TABLA_TIPO_VALORES = "TIPOVAL0";
	public final static String TABLA_TIPO_CONECTORES = "TCONECT";
	public final static String TABLA_TIPO_OFICINA = "TIPOFICI";
	public final static String TABLA_TIPO_OPERADORES = "TAB0196";
	public final static String TABLA_TIPO_NOTIFICACION_GESTION = "TNOTGES";
	public final static String TABLA_TIPO_RESULTADOS = "TTIPRES";
	public final static String TABLA_SITUACIONES = "TSITCAR";
	public final static String TABLA_RESULTADO_NOTIFICACION = "TRESNOT";
	public final static String TABLA_TIPO_OPERADOR = "TIPOVAL3";
	public final static String TABLA_TIPO_VALORES_ARGUMENTOS = "TIPOVAL1";
	public final static String TABLA_TIPO_VALORES_PARAMETROS = "TAB0135";
	public final static String TABLA_TIPO_VALORES_RESULTADOS = "TIPOVAL2";
	public final static String TABLA_TIPO_BONIFICACIONES = "TIPBONIF";
	public final static String TABLA_TIPO_DEPOSITOS = "TTIPDEP";
	public final static String TABLA_ACCIONES_EN_DEPOSITOS = "TACCDEP";
	public final static String TABLA_TIPO_MOVIMIENTOS = "TTIPMOV";
	public final static String TABLA_ESTADO_DOCUMENTOS = "TAB0026";
	public final static String TABLA_TIPOS_ICEUSO = "TIPOUSO";
	public final static String TABLA_PERIODICIDAD = "TAB0189";
	public final static String TABLA_EMISORAS_ADICIONALES = "TEMIADIC";
	public final static String TABLA_TIPOS_DOCUMENTACION = "TIPODOCU";
	
//	private static final Log LOG = LogFactory.getLog(TablaGt.class);

	private TablaGt() {}
	
	/**
	 * 
	 * @return Una lista de todas las tablas GT.
	 */
	public static List<TablaGtDTO> getTablas() {
		return tablaGtDAO.findAll("coTablaGt", 1);
	}
//	/**
//	 * 
//	 * @param usuario
//	 * @return Una lista de tablas GT donde el usuario tiene acceso.
//	 */
//	private static List<TablaGtDTO> getTablas(final AcmUsuarioDTO usuario) {
//		//final TablaGtUsuarioDTO exampleInstance = new TablaGtUsuarioDTO();
//		//exampleInstance.setAcmUsuarioDTO(usuario);
//		//final List<TablaGtUsuarioDTO> lista = tablaGtUsuarioDAO.findByExample(exampleInstance);
//		final List<TablaGtUsuarioDTO> lista = tablaGtUsuarioDAO.findFiltered("acmUsuarioDTO", usuario);
//		final List<TablaGtDTO> resultado = new ArrayList<TablaGtDTO>();
//		for (final Iterator<TablaGtUsuarioDTO> i = lista.iterator(); i.hasNext();) {
//			resultado.add(i.next().getTablaGtDTO());
//		}
//		return resultado;
//	}
//	/**
//	 * 
//	 * @param usuario
//	 * @return Una lista de tablas GT.
//	 */
//	private static List<TablaGtDTO> getTablas(final String usuario) {
//		final AcmUsuarioDTO acmUsuarioDTO = new AcmUsuarioDTO();
//		acmUsuarioDTO.setCoAcmUsuario(usuario);
//		return getTablas(acmUsuarioDTO);
//	}
	/**
	 * 
	 * @param tabla
	 * @return Si la tabla existe
	 */
	public static boolean isTabla(final String tabla) {
		return isTabla(tabla, null);
	}
	/**
	 * 
	 * @param tabla
	 * @param usuario
	 * @return Si la tabla existe y si el usuario tiene permisos
	 */
	public static boolean isTabla(final String tabla, final String usuario) {
		boolean resultado = false;
		TablaGtDTO tablaGtDTO = tablaGtDAO.findById(tabla);
		if (tablaGtDTO != null) {
			if (usuario != null) {
				//TablaGtUsuarioDTO exampleInstance = new TablaGtUsuarioDTO();
				AcmUsuarioDTO acmUsuarioDTO = new AcmUsuarioDTO();
				acmUsuarioDTO.setCoAcmUsuario(usuario);
				//exampleInstance.setAcmUsuarioDTO(acmUsuarioDTO);
				//exampleInstance.setTablaGtDTO(tablaGtDTO);
				if (tablaGtUsuarioDAO.findFiltered(new String[]{"acmUsuarioDTO, tablaGtDTO"},new Object[]{acmUsuarioDTO, tablaGtDTO}).size() > 0) {
					resultado = true;
				}
			} else {
				resultado = true;
			}
		}
		return resultado;
	}
	/**
	 * En PL/SQL: comun_util.TablaGt_getListaElementos
	 * @param tabla
	 * @param columna
	 * @return Una lista de elementos.
	 */
	public static List<TablaGtElementoDTO> getListaElementos(final TablaGtDTO tabla, final TablaGtColumnaDTO columna) {
		final List<TablaGtElementoDTO> resultado = new ArrayList<TablaGtElementoDTO>();
		final List<Map<String, Object>> elementos = tablaGtGetListaElementosBO.execute(tabla.getCoTablaGt(), columna.getNombre());
		for (final Iterator<Map<String, Object>> i = elementos.iterator(); i.hasNext();) {
			resultado.add(resultSetToElemento(i.next()));
		}
		return resultado;
	}
	
	private static TablaGtElementoDTO resultSetToElemento(final Map<String, Object> fila) {
		final TablaGtElementoDTO elemento = new TablaGtElementoDTO();
		elemento.setCoTablaGtElemento(Long.parseLong((String) fila.get("CO_TABLA_GT_ELEMENTO")));
		elemento.setCoUsuarioActualizacion((String) fila.get("CO_USUARIO_ACTUALIZACION"));
		elemento.setFhActualizacion((java.sql.Timestamp)fila.get("FH_ACTUALIZACION"));
		elemento.setFila(Integer.parseInt((String) fila.get("FILA")));
		elemento.setValor((String) fila.get(VALOR));
		return elemento;
	}
	
	/**
	 * 
	 * @param tabla
	 * @param columna
	 * @return
	 */
	public static List<String> getListaElementos(final String tabla, final String columna) {
		final List<String> resultado = new ArrayList<String>();
		final List<Map<String, Object>> elementos = tablaGtGetListaElementosBO.execute(tabla, columna);
		for (final Iterator<Map<String, Object>> i = elementos.iterator(); i.hasNext();) {
			final Map<String, Object> elemento = i.next();
			resultado.add((String) elemento.get(VALOR));
		}
		return resultado;
	}
	/**
	 * En PL/SQL: comun_util.TablaGt_getValores
	 * @param tabla
	 * @param elemento
	 * @return Devolver una lista de valores (columnas).
	 */
	public static List<TablaGtElementoDTO> getValores(final TablaGtDTO tabla, final TablaGtElementoDTO elemento) {
		final List<TablaGtElementoDTO> resultado = new ArrayList<TablaGtElementoDTO>();
		final List<Map<String, Object>> elementos = tablaGtGetValoresBO.execute(tabla.getCoTablaGt(), elemento.getValor());
		for (final Iterator<Map<String, Object>> i = elementos.iterator(); i.hasNext();) {
			resultado.add(resultSetToElemento(i.next()));
		}
		return resultado;
	}
	/**
	 * 
	 * @param tabla
	 * @param elemento
	 * @return
	 */
	public static List<String> getValores(final String tabla, final String elemento) {
		final List<String> resultado = new ArrayList<String>();
		final List<Map<String, Object>> elementos = tablaGtGetValoresBO.execute(tabla, elemento);
		for (final Iterator<Map<String, Object>> i = elementos.iterator(); i.hasNext();) {
			final Map<String, Object> elem = i.next();
			resultado.add((String) elem.get(VALOR));
		}
		return resultado;
	}

	/**
	 * Devuelve todas las celdas de la fila de una tabla, cuya columna tenga el valor elemento.
	 * @param tabla Donde buscar.
	 * @param columna Columna para comparar valor.
	 * @param elemento Valor de la columna indicada.
	 * @return Un Map de las columnas de la fila encontrada.
	 */
	public static Map<String, String> getValores(String tabla, String columna, String elemento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TablaGtElementoDTO.class, "el");
		criteria.createAlias("el.tablaGtColumnaDTO", "co"); // Para luego tener acceso al nombre de la columna
		criteria.add(Restrictions.eq("el.tablaGtColumnaDTO.id.coTablaGt", tabla));
		DetachedCriteria criteriaFila = DetachedCriteria.forClass(TablaGtElementoDTO.class, "el2");
		criteriaFila.createAlias("el2.tablaGtColumnaDTO", "co2");
		criteriaFila.add(Restrictions.eq("co2.id.coTablaGt", tabla));
		criteriaFila.add(Restrictions.eq("co2.nombre", columna));
		criteriaFila.add(Restrictions.eq("el2.valor", elemento));
		criteriaFila.setProjection(Projections.min("el2.fila")); // Me quedo con la primera fila, por si la incidencia estuviera varias veces.
		criteria.add(Subqueries.propertyEq("el.fila", criteriaFila));
		@SuppressWarnings("unchecked")
		List<TablaGtElementoDTO> tablaGtElementoDTOs = tablaGtDAO.findByCriteriaGenerico(criteria);
		Map<String, String> result = new HashMap<String, String>(tablaGtElementoDTOs.size());
		for (TablaGtElementoDTO tablaGtElementoDTO : tablaGtElementoDTOs) {
			result.put(tablaGtElementoDTO.getTablaGtColumnaDTO().getNombre(), tablaGtElementoDTO.getValor());
		}
		return result;
	}
	
	/**
	 * En PL/SQL: comun_util.TablaGt_getValor
	 * @param tabla
	 * @param elemento
	 * @param columna
	 * @return Devolver un valor.
	 */
	public static TablaGtElementoDTO getValor(final TablaGtDTO tabla, final TablaGtElementoDTO elemento, final TablaGtColumnaDTO columna) {
		final TablaGtElementoDTO elem = new TablaGtElementoDTO();
		final Map<String, Object> fila = tablaGtGetValorBO.execute(tabla.getCoTablaGt(), elemento.getValor(), columna.getNombre());
		elem.setCoTablaGtElemento(Long.parseLong((String) fila.get("CO_TABLA_GT_ELEMENTO")));
		elem.setCoUsuarioActualizacion((String) fila.get("CO_USUARIO_ACTUALIZACION"));
		elem.setFhActualizacion((java.sql.Timestamp)fila.get("FH_ACTUALIZACION"));
		elem.setFila(Integer.parseInt((String) fila.get("FILA")));
		elem.setValor((String) fila.get(VALOR));
		return elem;
	}
	/**
	 * 
	 * @param tabla
	 * @param elemento
	 * @param columna
	 * @return
	 */
	public static String getValor(final String tabla, final String elemento, final String columna) {
		final String key = tabla + "|" + columna + "|" + elemento;
		String value = valorTablaMap.get(key);
		if (value == null) {
			final Map<String, Object> elem = tablaGtGetValorBO.execute(tabla, elemento, columna);
			value = (String) elem.get(VALOR);
			valorTablaMap.put(key, value);
		}
		return value;
	}
	/**
	 * En PL/SQL: comun_util.TablaGt_isElemento
	 * @param tabla
	 * @param elemento
	 * @return Comprobar la existencia de un elemento en una tabla.
	 */
	public static boolean isElemento(final TablaGtDTO tabla, final TablaGtElementoDTO elemento) {
		return isElemento(tabla.getCoTablaGt(), elemento.getValor());
	}

	/**
	 * 
	 * @param tabla
	 * @param elemento
	 * @return
	 */
	public static boolean isElemento(final String tabla, final String elemento) {
		return tablaGtIsElementoBO.execute(tabla, elemento);
	}
	
	/**
	 * 
	 * @param tabla
	 * @return
	 */
	public static List<KeyValue> getListaCodigoDescripcion(final String tabla) {
		final List<KeyValue> lista = new ArrayList<KeyValue>();
		final List<String> codigos = getListaElementos(tabla, "CODIGO");
		for (final Iterator<String> i = codigos.iterator(); i.hasNext();) {
			final String codigo = i.next();
			final String descripcion = getValor(tabla, codigo, "DESCRIPCION");
			final KeyValue codigoDescripcion = new KeyValue(codigo, descripcion);
			lista.add(codigoDescripcion);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param tabla
	 * @param elemento
	 * @return
	 */
	public static KeyValue getCodigoDescripcion(final String tabla, final String elemento) {
		return new KeyValue(elemento, getValor(tabla, elemento, "DESCRIPCION"));
	}
	
	public static void vaciarValorTablaMap() {
		valorTablaMap = new HashMap<String, String>();
	}

	public static DAOBase<TablaGtDTO, String> getTablaGtDAO() {
		return tablaGtDAO;
	}
	public void setTablaGtDAO(final DAOBase<TablaGtDTO, String> tablaGtDAO) {
		TablaGt.tablaGtDAO = tablaGtDAO;
	}
	public static DAOBase<TablaGtUsuarioDTO, Long> getTablaGtUsuarioDAO() {
		return tablaGtUsuarioDAO;
	}
	public void setTablaGtUsuarioDAO(final DAOBase<TablaGtUsuarioDTO, Long> tablaGtUsuarioDAO) {
		TablaGt.tablaGtUsuarioDAO = tablaGtUsuarioDAO;
	}

	public static TablaGtIsElementoBO getTablaGtIsElementoBO() {
		return tablaGtIsElementoBO;
	}

	public void setTablaGtIsElementoBO(
			final TablaGtIsElementoBO tablaGtIsElementoBO) {
		TablaGt.tablaGtIsElementoBO = tablaGtIsElementoBO;
	}

	public static TablaGtGetListaElementosBO getTablaGtGetListaElementosBO() {
		return tablaGtGetListaElementosBO;
	}

	public void setTablaGtGetListaElementosBO(
			final TablaGtGetListaElementosBO tablaGtGetListaElementosBO) {
		TablaGt.tablaGtGetListaElementosBO = tablaGtGetListaElementosBO;
	}

	public static TablaGtGetValoresBO getTablaGtGetValoresBO() {
		return tablaGtGetValoresBO;
	}

	public void setTablaGtGetValoresBO(
			final TablaGtGetValoresBO tablaGtGetValoresBO) {
		TablaGt.tablaGtGetValoresBO = tablaGtGetValoresBO;
	}

	public static TablaGtGetValorBO getTablaGtGetValorBO() {
		return tablaGtGetValorBO;
	}

	public void setTablaGtGetValorBO(final TablaGtGetValorBO tablaGtGetValorBO) {
		TablaGt.tablaGtGetValorBO = tablaGtGetValorBO;
	}
	

}
