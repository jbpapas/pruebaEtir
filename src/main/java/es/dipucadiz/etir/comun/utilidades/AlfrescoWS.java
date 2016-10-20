package es.dipucadiz.etir.comun.utilidades;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.gdtel.gestor.GestionDocumentosCompleja;
import es.gdtel.gestor.GestionDocumentosSimple;
import es.gdtel.gestor.alfrescoImplWS.InterfazAlfrescoWS;
import es.gdtel.gestor.exceptions.GestorDocumentalException;
import es.gdtel.gestor.util.Fichero;
import es.gdtel.gestor.util.ISO8601DateFormat;

/**
 * Clase para el almacenamiento/recuperación de documentos y sus metadatados de Alfresco
 */
public class AlfrescoWS {
		
	protected final Log LOG = LogFactory.getLog(AlfrescoWS.class);

	private String endPoint;
	private String user;
	private String password;
	private String rootSpace;
	private String spaceStore;

	private final String TIPO_MIME_PDF = "application/PDF";
	private final String[] ALFRESCO_CARACT_NO_PERMITIDOS = {"<", ">", "\\+ ", "\\*", "\\|", "\"", "%", "&", "/", "\\?", "¬", ";", ":"};
	
	// Metadatos Alfresco properties
	public static final String VERSION_NTI = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_version_nti";
	public static final String IDENTIFICATOR = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_identificator";
	public static final String ORGAN = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_organism";
	public static final String CAPTURE_DATE = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_capture_date";
	public static final String ORIGIN = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_origin";
	public static final String PREPARATION_STATE = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_preparation_state";
	public static final String FORMAT_NAME = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_format_name";
	public static final String DOCUMENT_TYPE = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_document_type";
	public static final String SIGN_TYPE = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_sign_type";
	public static final String CSV_VALUE = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_csv_value";
	public static final String CSV_DEFINITION = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_csv_definition";
	public static final String ORIGINAL_DOCUMENT = "{http://eni.guadaltel.es/dc/model/1.0}eDocument_original_document";

	public static final String ETIR_TIPO_DOCUMENTO = "{http://etir.dipucadiz.es/dc/model/1.0}tipo_documento"; //TODO: El tipo de GA_DOC_NOT
	public static final String ETIR_CODIGO_BARRAS = "{http://etir.dipucadiz.es/dc/model/1.0}codigo_barras";
	public static final String ETIR_RAZON_SOCIAL = "{http://etir.dipucadiz.es/dc/model/1.0}razon_social";
	public static final String ETIR_IDENTIFICADOR = "{http://etir.dipucadiz.es/dc/model/1.0}identificador";
	public static final String ETIR_TIPO_IDENTIFICADOR = "{http://etir.dipucadiz.es/dc/model/1.0}tipo_identificador";
	public static final String ETIR_MOVIL = "{http://etir.dipucadiz.es/dc/model/1.0}movil";
	public static final String ETIR_EMAIL = "{http://etir.dipucadiz.es/dc/model/1.0}email";
	public static final String ETIR_CANAL_NOTIFICACION = "{http://etir.dipucadiz.es/dc/model/1.0}canal_notificacion";

	
	
	
//	public static final String ETIR_DIRECCION = "{http://etir.dipucadiz.es/dc/model/1.0}direccion";
//	public static final String ETIR_CODIGO_POSTAL = "{http://etir.dipucadiz.es/dc/model/1.0}codigo_postal";
//	public static final String ETIR_MUNICIPIO = "{http://etir.dipucadiz.es/dc/model/1.0}municipio";
//	public static final String ETIR_PROVINCIA = "{http://etir.dipucadiz.es/dc/model/1.0}provincia";

	
	// Metadatos Alfresco values
	public static final String VERSION_NTI_VALUE = "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e";
	public static final String FORMAT_NAME_VALUE = "PDF";
	public static final String DOCUMENT_TYPE_VALUE = "TD07"; // TD07 = Notificación
	public static final String SIGN_TYPE_VALUE = "TF06"; // TF06 = PaDES
	public static final String ORGAN_DIPUCADIZ = "L02000011"; // L02000011 = Diputación de Cádiz
	public static final String ORIGIN_ADMINISTRACION = "1"; // 1 = Administración
	public static final String PREPARATION_STATE_ORIGINAL = "EE01"; // EE01 = Original
	
	/**
	 * Constructor por defecto que da acceso a los servicios de alfresco.
	 */
	public AlfrescoWS() {
//		System.out.println("Cargando servicio de Alfresco.");
		Properties p = new Properties();
		ResourceBundle bun = ResourceBundle.getBundle("alfresco");
		Enumeration<String> claves = bun.getKeys();
		while(claves.hasMoreElements()) {
			String clave = (String)claves.nextElement();
			String valor = bun.getString(clave);
//			System.out.println("_______________________ " + clave + ": " + valor);
			p.setProperty(clave, valor);
		}     
		endPoint = p.getProperty("repository.location");
		user = p.getProperty("repository.username");
		password = p.getProperty("repository.password");
		rootSpace = p.getProperty("repository.rootSpace");
		spaceStore = p.getProperty("repository.spaceStore");
	}

	public String crearCarpeta(String folderName, GestionDocumentosSimple interfazSimple) throws GadirServiceException {
		String uuidCarpetaActual = "";
		// Montar ruta completa
		folderName = "" + Utilidades.getAnoActual() + '/' + StringUtils.leftPad(String.valueOf(Utilidades.getMesActual()), 2, "0") + '/' + folderName;

		String[] ruta = folderName.split("/");
		String rutaReal = "/";
		for (int i=0; i<ruta.length; i++) {
			String folder;
			try {
				folder = eliminarCaracteresNoValidosCarpeta(ruta[i].trim());
			} catch (Exception e1) {
				throw new GadirServiceException("Error en eliminarCaracteresNoValidosCarpeta", e1);
			}
			if (Utilidades.isNotEmpty(folder)) {
				rutaReal += folder + '/';
				try {
					if (Utilidades.isEmpty(uuidCarpetaActual)) {
						uuidCarpetaActual = interfazSimple.crearCarpeta("", folder, null);
					} else {
						uuidCarpetaActual = interfazSimple.crearCarpeta(uuidCarpetaActual, folder, null);
					}
					if (uuidCarpetaActual == null) {
						throw new GadirServiceException("Error procesando carpeta " + folder + " (" + rutaReal + ")");
					}
				} catch (GestorDocumentalException e) {
					throw new GadirServiceException("Error en llamada interfazSimple.crearCarpeta", e);
				}
			}
		}
		return uuidCarpetaActual;
	}
	
	public String almacenarDocumento(String uuidCarpeta, Fichero fichero, Map<String,Object> datos, GestionDocumentosSimple interfazSimple, Long coBdDocumental) throws GadirServiceException {
//		Map<String,Object> datos = new HashMap<String, Object>();
		/**** Obligatorios ENI ***/
//		datos.put(VERSION_NTI, VERSION_NTI_VALUE);
//		datos.put(IDENTIFICATOR, "ES_" + ORGAN_DIPUCADIZ + "_" + Utilidades.getAnoActual() + "_ETIR" + StringUtils.leftPad(coBdDocumental.toString(), 25, "0"));
//		datos.put(ORGAN, ORGAN_DIPUCADIZ);
//		datos.put(CAPTURE_DATE, Utilidades.dateToYYYYMMDDHHMMSS(Utilidades.getDateActual()).replace('_', 'T'));
//		datos.put(ORIGIN, ORIGIN_ADMINISTRACION);
//		datos.put(PREPARATION_STATE, PREPARATION_STATE_ORIGINAL);
//		datos.put(FORMAT_NAME, FORMAT_NAME_VALUE); // PDF
//		datos.put(DOCUMENT_TYPE, DOCUMENT_TYPE_VALUE); // Edicto
//		datos.put(SIGN_TYPE, SIGN_TYPE_VALUE); // PAdEs

		datos.put(VERSION_NTI, VERSION_NTI_VALUE);
		datos.put(IDENTIFICATOR, "ES_" + ORGAN_DIPUCADIZ + "_" + Utilidades.getAnoActual() + "_ETIR" + StringUtils.leftPad(coBdDocumental.toString(), 25, "0"));
		datos.put(ORGAN, ORGAN_DIPUCADIZ);
		datos.put(CAPTURE_DATE, ISO8601DateFormat.format(Utilidades.getDateActual()));
		datos.put(ORIGIN, ORIGIN_ADMINISTRACION);
		datos.put(PREPARATION_STATE, PREPARATION_STATE_ORIGINAL);
		datos.put(FORMAT_NAME, FORMAT_NAME_VALUE); // PDF
		datos.put(DOCUMENT_TYPE, DOCUMENT_TYPE_VALUE); // Edicto
		datos.put(SIGN_TYPE, SIGN_TYPE_VALUE); // PAdEs
		/**** Propios ***/
		//TODO: Cargar metadatos propios desde fuera.
		Properties properties = new Properties();
		properties = mapToProperties(datos);
		
		String uuidFichero;
		try {
			uuidFichero = interfazSimple.adjuntarFichero(uuidCarpeta, fichero, properties);
		} catch (GestorDocumentalException e) {
			throw new GadirServiceException("Error al adjuntar fichero", e);
		}
		return uuidFichero;
	}

	public GestionDocumentosCompleja obtenerInterfazCompleja() {
		GestionDocumentosCompleja interfazCompleja = null;
		interfazCompleja = new InterfazAlfrescoWS();
		interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
		return interfazCompleja;
	}
	
	
//	/**
//	 * Almacena un documento digitalizado con sus metadatos por defecto en alfresco.
//	 * @param propsFichero - metadatos del documento
//	 * @param fichero Fichero pdf a almacenar
//	 * @return Identificador uuid de la carpeta del documento.
//	 */
//	public final String almacenarDigitalizadoPendiente(Properties propsFichero, Fichero fichero) throws Exception {
//		System.out.println("Dentro de: 'almacenarDigitalizadoPendiente'");
//		GestionDocumentosSimple interfazSimple = new InterfazAlfrescoWS();
//		String uuidCarpetaPendientes, uuidCarpetaDocumento;
//		
//		try {
//			System.out.println("Almacenar documento en Alfresco:\n" + propsFichero);
//			interfazSimple.iniciarSesion();
//			System.out.println("Sesión iniciada");
//			System.out.println("Se almacenó en el espacio de documentos pendientes de revisar.");
//			System.out.println("Crear la estructura: raiz/PENDIENTES si no existe");
//			
//			uuidCarpetaPendientes = interfazSimple.crearCarpeta("", Constants.DOCUMENTOS_PENDIENTES, null);
//			
//			String documento = "edictos_" + String.valueOf(System.currentTimeMillis());
//			
//			System.out.println("Crear la carpeta: raiz/PENDIENTES/" + documento);
//			uuidCarpetaDocumento = interfazSimple.crearCarpeta(uuidCarpetaPendientes,documento, propsFichero);
//			
//			//adjuntar el documento
//			System.out.println("Adjuntar el documento:" + fichero.getNombre());
//			String uuidFichero = interfazSimple.adjuntarFichero(uuidCarpetaDocumento, fichero, null);
//			interfazSimple.terminarSesion();
//			
//			if (uuidFichero != null) {
//				System.out.println("El documento con uuid:" + uuidFichero + " se almacenó correctamente");
//			} else {
//				System.out.println("El documento no se pudo almacenar en Alfresco.");
//			}
//			System.out.println("Sesión terminada");
//			
//		} catch ( Exception e ) {
//			try {
//				interfazSimple.terminarSesion();
//				System.out.println("Sesion terminada");
//			} catch (Exception ex ) {
//				System.out.println("No se ha podido incorporar el documento a la base de datos y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//				throw new Exception("No se ha podido incorporar el documento a la base de datos y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//			}
//			System.out.println("No se ha podido incorporar el documento a la base de datos. Causa: " + e);
//			throw new Exception("No se ha podido incorporar el documento a la base de datos. Causa: " + e);
//		}
//		return uuidCarpetaDocumento;
//	}

//	/**
//	 * Almacena un documento y sus metadatos en alfresco.
//	 * @param datos del documento
//	 * @param listaCarpetas 
//	 * @param ficheroActual Fichero pdf a almacenar
//	 * @param pendiente Indica si está completa el documento o pendiente
//	 * @return Identificador uuid de la carpeta del documento.
//	 */
//	public final String almacenarAlfresco(final Map<String, Object> datos, List<String> listaCarpetas, Fichero fichero, final boolean pendiente) throws Exception {
//		System.out.println("Dentro de: 'almacenarAlfresco'");
//		GestionDocumentosSimple interfazSimple = new InterfazAlfrescoWS();
//		String uuidCarpetaActual, uuidCarpetaDocumento;
//		String uuidFichero = null;
//		try {
//			System.out.println("Almacenar documento en Alfresco:\n" + datos);
//			//comprobar que todos los datos obligatorios están especificados
//			System.out.println("Comprobar que todos los datos obligatorios están especificados");
//
//			interfazSimple.iniciarSesion();
//			System.out.println("Sesión iniciada");
//
//			Properties propsFichero = new Properties();
//			propsFichero = mapToProperties(datos);
//			if (pendiente) { // no se dara el caso 
//				System.out.println("Se almacenará en el espacio de documentos pendientes de revisar.");
//				System.out.println("Crear la estructura: raiz/PENDIENTES si no existe");
//				String documento = "edictos_" + String.valueOf(System.currentTimeMillis());
//				uuidCarpetaActual = interfazSimple.crearCarpeta("", Constants.DOCUMENTOS_PENDIENTES, null);
//				uuidCarpetaDocumento = interfazSimple.crearCarpeta(uuidCarpetaActual,documento, propsFichero);
//			} else {
//				System.out.println("Los datos obligatorios han sido especificados");
//
//				int tam = listaCarpetas.size();
//				String ultimaCarp = eliminarCaracteresNoValidosCarpeta(listaCarpetas.get(tam - 1));
//				listaCarpetas.remove(tam -1);
//				
//				uuidCarpetaActual = interfazSimple.crearCarpeta("", listaCarpetas.get(0), null);
//				if (uuidCarpetaActual == null) {
//					throw new NullPointerException("Error creando carpeta raiz de documentos completos");
//				}else
//					listaCarpetas.remove(0);
//				
//				System.out.println("Creando estructura de carpetas del documento.");
//				Iterator<String> it = listaCarpetas.iterator();
//				while (it.hasNext()) {
//					String s = (String) it.next();
//					String folderName = eliminarCaracteresNoValidosCarpeta(s);
//					uuidCarpetaActual = interfazSimple.crearCarpeta(uuidCarpetaActual, folderName , null);
//					if (uuidCarpetaActual == null) {
//						throw new NullPointerException("Error creando carpeta " + s);
//					} else {
//						System.out.println("--- " + folderName);
//					}
//				}
//				uuidCarpetaDocumento = interfazSimple.crearCarpeta(uuidCarpetaActual,ultimaCarp, propsFichero);
//				System.out.println("---|| " + ultimaCarp);
//			}
//			//Adjuntar el documento
//			System.out.println("Adjuntar el documento:" + fichero.getNombre());
//			try {
//				uuidFichero = interfazSimple.adjuntarFichero(uuidCarpetaDocumento, fichero, null);
//			} catch (Exception e) {
//				System.out.println("No se ha podido incorporar el documento. Causa: " + e.getMessage());
//				//eliminarDocumento(uuidCarpetaDocumento);
//			} finally {
//				
//				if(uuidFichero == null){
//					System.out.println("No se ha podido incorporar el documento.");
//					//eliminarDocumento(uuidCarpetaDocumento);
//				}
//				interfazSimple.terminarSesion();
//			}
//
//			if (uuidFichero != null) {
//				System.out.println("El documento con uuid:" + uuidFichero + " se almacenó correctamente");
//			} else {
//				System.out.println("El documento no se pudo almacenar en Alfresco.");
//			}
//			System.out.println("Sesión terminada");
//
//	    } catch ( Exception e ) {
//			try {
//				interfazSimple.terminarSesion();
//				System.out.println("Sesion terminada");
//			} catch (Exception ex ) {
//				System.out.println("No se ha podido incorporar el documento a la base de datos y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//				throw new Exception("No se ha podido incorporar el documento a la base de datos y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//			}
//			System.out.println("Error al almacenar en alfresco el documento con los datos: " + datos);
//			System.out.println("No se ha podido incorporar el documento a la base de datos. Causa: " + e.getMessage());
//		    throw new Exception("No se ha podido incorporar el documento a la base de datos. Causa: " + e.getMessage());
//		}
//		return uuidFichero;
//	}

//	/**
//	 * Almacena un documento en alfresco.
//	 * @param listaCarpetas 
//	 * @param ficheroActual Fichero pdf a almacenar
//	 * @return Identificador uuid de la carpeta del documento.
//	 */
//	public final String almacenarAlfresco(List<String> listaCarpetas, Fichero fichero) throws Exception {
//		System.out.println("Dentro de: 'almacenarAlfresco'");
//		GestionDocumentosSimple interfazSimple = new InterfazAlfrescoWS();
//		String uuidCarpetaActual, uuidCarpetaDocumento;
//		String uuidFichero = null;
//		try {
//			System.out.println("Almacenar documento en Alfresco");
//			//comprobar que todos los datos obligatorios están especificados
//			System.out.println("Comprobar que todos los datos obligatorios están especificados");
//
//			interfazSimple.iniciarSesion();
//			System.out.println("Sesión iniciada");
//
//			int tam = listaCarpetas.size();
//			String ultimaCarp = eliminarCaracteresNoValidosCarpeta(listaCarpetas.get(tam - 1));
//			listaCarpetas.remove(tam -1);
//			
//			uuidCarpetaActual = interfazSimple.crearCarpeta("", listaCarpetas.get(0), null);
//			if (uuidCarpetaActual == null) {
//				throw new NullPointerException("Error creando carpeta raiz de documentos completos");
//			}else
//				listaCarpetas.remove(0);
//			
//			System.out.println("Creando estructura de carpetas del documento.");
//			Iterator<String> it = listaCarpetas.iterator();
//			while (it.hasNext()) {
//				String s = (String) it.next();
//				String folderName = eliminarCaracteresNoValidosCarpeta(s);
//				uuidCarpetaActual = interfazSimple.crearCarpeta(uuidCarpetaActual, folderName , null);
//				if (uuidCarpetaActual == null) {
//					throw new NullPointerException("Error creando carpeta " + s);
//				} else {
//					System.out.println("--- " + folderName);
//				}
//			}
//			
//			uuidCarpetaDocumento = interfazSimple.crearCarpeta(uuidCarpetaActual,ultimaCarp, null);
//			System.out.println("---|| " + ultimaCarp);
//			
//			//Adjuntar el documento
//			System.out.println("Adjuntar el documento:" + fichero.getNombre());
//			try {
//				uuidFichero = interfazSimple.adjuntarFichero(uuidCarpetaDocumento, fichero, null);
//			} catch (Exception e) {
//				System.out.println("No se ha podido incorporar el documento. Causa: " + e.getMessage());
//				//eliminarDocumento(uuidCarpetaDocumento);
//			} finally {
//				
//				if(uuidFichero == null){
//					System.out.println("No se ha podido incorporar el documento.");
//					//eliminarDocumento(uuidCarpetaDocumento);
//				}
//				interfazSimple.terminarSesion();
//			}
//
//			if (uuidFichero != null) {
//				System.out.println("El documento con uuid:" + uuidFichero + " se almacenó correctamente");
//			} else {
//				System.out.println("El documento no se pudo almacenar en Alfresco.");
//			}
//			System.out.println("Sesión terminada");
//
//	    } catch ( Exception e ) {
//			try {
//				interfazSimple.terminarSesion();
//				System.out.println("Sesion terminada");
//			} catch (Exception ex ) {
//				System.out.println("No se ha podido incorporar el documento a la base de datos y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//				throw new Exception("No se ha podido incorporar el documento a la base de datos y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//			}			
//			System.out.println("No se ha podido incorporar el documento a la base de datos. Causa: " + e.getMessage());
//		    throw new Exception("No se ha podido incorporar el documento a la base de datos. Causa: " + e.getMessage());
//		}
//		return uuidFichero;
//	}
	
	
//	/**
//	 * Devuelve el array de uuids de documentos que cumplan los criterios de búsqueda.
//	 * @param  cadenaBusquedaDocumento - Cadena lucene con criterios de búsqueda
//	 * @return Array con los uuids de los documentos encontrados.
//	 * @throws  Exception
//	 */
//	public String[] buscarReferenciasDocsAlfresco(String cadenaBusquedaDocumento)throws Exception {
//		System.out.println("Dentro de: 'recuperarDocumentosAlfresco'");
//		GestionDocumentosCompleja interfazCompleja = new InterfazAlfrescoWS();
//		String []arrayDocumentosEncontrados = null;
//		try {
//			System.out.println("Recuperar documentos de Alfresco con el filtro:\n" + cadenaBusquedaDocumento);
//			if (interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore)) {
//				System.out.println("Sesión iniciada");
//			} else {
//				throw new Exception("Error al iniciar Sesión con la base de datos.");
//			}
//			arrayDocumentosEncontrados = interfazCompleja.buscar(cadenaBusquedaDocumento);
//			interfazCompleja.terminarSesion();
//			System.out.println("Sesion terminada");
//			
//		} catch (Exception e) {
//			try {
//				System.out.println("Error al buscar documentos. Causa:" + e.getMessage());
//				interfazCompleja.terminarSesion();
//				System.out.println("Sesion terminada");
//			} catch (Exception ex) {
//				System.out.println("No se ha podido recuperar ningún documento de alfresco y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//				throw new Exception("No se ha podido recuperar ningún documento de alfresco y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//			}
//			System.out.println("No se ha podido recuperar ningún documento de alfresco. Causa:" + e.getMessage());
//			throw new Exception("No se ha podido recuperar ningún documento de alfresco. Causa:" + e.getMessage());
//		}
//		return arrayDocumentosEncontrados;
//	}
	
//	/**
//	 * Recupera todos los documentos de alfresco que cumplan los criterios de búsqueda.
//	 * @param  cadenaBusquedaDocumento - Cadena lucene con criterios de búsqueda
//	 * @return Lista de documentos
//	 * @throws  Exception
//	 */
//	public List<Map<String, Object>> recuperarDocumentosAlfresco(String cadenaBusquedaDocumento)throws Exception {
//		System.out.println("Dentro de: 'recuperarDocumentosAlfresco'");
//		GestionDocumentosCompleja interfazCompleja = new InterfazAlfrescoWS();
//		List<Map<String, Object>> documentos = null;
//		List<String> uuidsCarpetasDocumentos = null;
//		Map<String, Object> edoc = null;
//
//		try {
//			System.out.println("Recuperar documentos de Alfresco con el filtro:\n" + cadenaBusquedaDocumento);
//
//			if (interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore)) {
//				System.out.println("Sesión iniciada");
//			} else {
//				throw new Exception("Error al iniciar Sesión con la base de datos.");
//			}
//			String []arrayDocumentosEncontradas = null;
//			arrayDocumentosEncontradas = interfazCompleja.buscar(cadenaBusquedaDocumento);
//			if (arrayDocumentosEncontradas != null && arrayDocumentosEncontradas.length > 0) {
//				System.out.println("Obtener los documentos que cumplen el criterio de búsqueda");
//				uuidsCarpetasDocumentos = new ArrayList<String>();
//				uuidsCarpetasDocumentos.addAll(Arrays.asList(arrayDocumentosEncontradas));
//				documentos = new ArrayList<Map<String, Object>>();
//				for (int i = 0; i < uuidsCarpetasDocumentos.size(); i++) {
//					edoc = recuperarDocumentoByUuid((String) uuidsCarpetasDocumentos.get(i), interfazCompleja);
//					if (edoc != null) {
//						edoc.put("uuidEspacioDocumento", uuidsCarpetasDocumentos.get(i));
//						System.out.println("Añadir documento encontrado a la lista");
//						documentos.add(edoc);
//					}
//				}
//			}
//
//
//			interfazCompleja.terminarSesion();
//			System.out.println("Sesion terminada");
//
//		} catch (Exception e) {
//			try {
//				System.out.println("Error al buscar documentos. Causa:" + e.getMessage());
//				interfazCompleja.terminarSesion();
//				System.out.println("Sesion terminada");
//			} catch (Exception ex) {
//				System.out.println("No se ha podido recuperar ningún documento de alfresco y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//				throw new Exception("No se ha podido recuperar ningún documento de alfresco y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//			}
//			System.out.println("No se ha podido recuperar ningún documento de alfresco. Causa:" + e.getMessage());
//			throw new Exception("No se ha podido recuperar ningún documento de alfresco. Causa:" + e.getMessage());
//		}
//		return documentos;
//	}


//	/**
//	 * Actualizar documento existente en el repositorio
//	 * @param  Datos del documento
//	 * @param  Identificador uuid de la carpeta del documento
//	 * @throws  Exception
//	 */
//
//	public final void actualizarDocumento(final String uuid, final Map<String, Object> datos) throws Exception {
//
//		System.out.println("Dentro de: 'actualizarDocumento'");
//		System.out.println("Actualizar un documento en Alfresco");
//
//		GestionDocumentosCompleja  interfazCompleja = null;
//		try {
//
//			System.out.println("Encontrar el nodo para el uuid:" + uuid);
//			interfazCompleja = new InterfazAlfrescoWS();
//			interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
//			Reference ref = interfazCompleja.getReferenciaPorUuid(uuid);
//			if (ref == null) {
//				String error = "No existe el nodo cuyo identificador se ha especificado";
//				System.out.println(error);
//				throw new Exception(error);
//			}
//
//			Properties propsFichero = new Properties();
//			propsFichero = mapToProperties(datos);
//			System.out.println("Establecer los nuevos datos del espacio del documento:");
//			interfazCompleja.actualizarMetadatos(uuid, propsFichero);
//		} catch (Exception e) {
//			String error = "No se ha podido actualizar el documento con uuid:" + uuid;
//
//			System.out.println(error + ". Causa:" + e);
//			try {
//				interfazCompleja.terminarSesion();
//			} catch (Exception ex) {
//				String error2 = "No se ha podido actualizar el documento con uuid:" + uuid + " y no se ha podido terminar la Sesión";
//				System.out.println(error2 + ". Causa:" + ex.getMessage());
//				throw new Exception(error2);
//			}
//			throw new Exception(error);
//		}
//		interfazCompleja.terminarSesion();
//	}


//	/**
//	 * eliminar documento
//	 * @param uuid identificador del documento en Alfresco
//	 * @return eliminar el documento del alfresco
//	 * @throws  Exception
//	 */
//	public boolean eliminarDocumento(String uuid) throws Exception {
//		System.out.println("Dentro de: 'eliminarDocumento'");
//		GestionDocumentosCompleja interfazCompleja = new InterfazAlfrescoWS();
//		try {
//			if (uuid == null || "".equals(uuid)) {
//				System.out.println("No se ha recibido el identificador del documento a eliminar.");
//				return false;
//			} else {
//				System.out.println("Uuid del documento a eliminar: " + uuid);
//				interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
//				System.out.println("Sesión iniciada");
//				Reference refNodo = interfazCompleja.getReferenciaPorUuid(uuid);
//				System.out.println("Eliminando nodo");
//				interfazCompleja.borrarNodo(refNodo);
//				interfazCompleja.terminarSesion();
//				System.out.println("Sesion terminada");
//				return true;
//			}
//		} catch (Exception e) {
//			try {
//				System.out.println("Error eliminando el documento. Causa: " + e.getMessage());
//				interfazCompleja.terminarSesion();
//				System.out.println("Sesion terminada");
//				return false;
//			} catch (Exception ex) {
//				System.out.println("No se ha podido eliminar la documento de alfresco y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//				throw new Exception("No se ha podido eliminar la documento de alfresco y no se ha podido terminar la Sesión. Causa:" + ex.getMessage());
//			}
//		}
//	}

//	/**
//	 * Recupera los metadatos del documento
//	 * @param uuid identificador del documento en Alfresco
//	 * @param gdc interfaz de Alfresco
//	 * @return El documento con sus metadatos
//	 * @throws Exception
//	 */
//	public Map<String, Object> recuperarDocumentoByUuid(String uuid, GestionDocumentosCompleja gdc) throws Exception {
//		LOG.debug("Dentro de: 'recuperarDocumentoByUuid'");
//		Map<String, Object> edoc = new HashMap<String, Object>();
//		GestionDocumentosCompleja interfazCompleja = null;
//		try {
//			LOG.debug("Recuperar la información para el documento de alfresco con uuid: " + uuid);
//			boolean gdcIsNull = false;
//			if ( gdc == null ) {
//				//Se utiliza en el caso del visor de documentos, que solo se consulta una documento
//				//y todavía no se ha iniciado la Sesión.
//				gdcIsNull = true;
//				interfazCompleja = new InterfazAlfrescoWS();
//				interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
//			} else {
//				//Estamos consultando multiples documentos y la Sesión ya viene iniciada
//				String ticket = gdc.getTicket();
//				if (ticket == null) {
//					gdc.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
//					interfazCompleja = gdc;
//				} else {
//					interfazCompleja = gdc;
//				}
//			}
//
//			Reference ref = interfazCompleja.getReferenciaPorUuid(uuid);
//			if (ref == null) {
//				LOG.debug("No existe ningún documento con el uuid:" + uuid);
//				edoc = null;
//			} else {
//				//Las propiedades del documento están en su carpeta(espacio) y el fichero pdf dentro de este espacio.
//				//Para recuperar los datos de una documento, por un lado tomamos las propiedades del documento de su carpeta
//				//y despues obtenemos los hijos para saber el uuid del fichero pdf que está dentro y que corresponderá a la documento.
//
//				LOG.debug("Obtenemos los metadatos del documento.");
//				Properties propiedades = interfazCompleja.getPropiedades(ref);
//				edoc = propertiesToMap(propiedades);
//
//				//recuperar el fichero pdf del espacio del documento
//				LOG.debug("Obtenemos los hijos del elemento actual, para saber el uuid del fichero pdf del documento");
//				Reference[] documentos = interfazCompleja.getHijos(ref);
//				if (documentos != null && documentos.length > 0) {
//					LOG.debug("Se han encontrado " + documentos.length + " documentos dentro del espacio del documento.");
//					//Tomamos el primero elemento, ya que no se esperan múltiples documentos por espacio.
//					 if (documentos.length == 1) {
//						 edoc.put("uuidFicheroPdf", documentos[0].getUuid());
//					 } else {
//						 LOG.debug("Existen múltiples documentos, no podemos encontrar el que pertenece a esta documento.");
//						 edoc.put("uuidFicheroPdf", "NO_ENCONTRADO");
//					 }
//
//				} else {
//					LOG.debug("No se han encontrado documentos dentro del espacio del documento.");
//					edoc.put("uuidFicheroPdf", "NO_ENCONTRADO");
//				}
//			}
//
//			if ( gdcIsNull ) {
//				//Si la interfaz de alfresco venía null, terminamos la Sesión en el mismo método.
//				//Solo se termina en este caso, ya que para la busqueda de multiples documentos solo
//				//se abre la Sesión una vez y se llama a este método reutilizando la misma Sesión.
//				LOG.debug("Terminando Sesión interfaz compleja");
//				interfazCompleja.terminarSesion();
//			}
//		} catch (Exception e) {
//			LOG.error("No se ha podido recuperar el documento con uuid:" + uuid + ". Causa:" + e.getMessage());
//			LOG.debug("Terminando Sesión interfaz compleja");
//			interfazCompleja.terminarSesion();
//			throw new Exception("No se ha podido recuperar el documento con uuid:" + uuid + ". Causa:" + e.getMessage());
//		}
//		LOG.debug("Documento obtenido:" + edoc);
//		return edoc;
//	}

	/**
	 * Recupera el fichero pdf correspondiente al documento
	 * @param uuid identificador del documento en Alfresco
	 * @param interfazCompleja interfaz de Alfresco
	 * @return El documento con sus metadatos
	 * @throws Exception
	 */
	public Fichero recuperarDocumentoPDF(String uuid) throws Exception {
		try {
			LOG.debug("Dentro de: 'recuperarDocumentoPDF'");
			LOG.debug("uuidDocumento a descargar: " + uuid);
			GestionDocumentosCompleja interfazCompleja = new InterfazAlfrescoWS();
			interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
			Fichero f = interfazCompleja.recuperarFichero(uuid);
			interfazCompleja.terminarSesion();
			if (f.getTipoMime().equalsIgnoreCase(TIPO_MIME_PDF)) {
				LOG.debug("Documento obtenido:" + f.getContenido().length);
				return f;
			}
		} catch (Exception e) {
			LOG.debug("No se ha podido recuperar el documento con uuid:" + uuid + ". Causa:" + e.getMessage());
			return null;
		}
		return null;
		
	}

//	/**
//	 * Recupera el fichero correspondiente al documento
//	 * @param uuid identificador del documento en Alfresco
//	 * @param interfazCompleja interfaz de Alfresco
//	 * @return El documento con sus metadatos
//	 * @throws Exception
//	 */
//	public Fichero recuperarDocumentoByUuid(String uuid) throws Exception {
//		System.out.println("Dentro de: 'recuperarFicheroByUuid'");
//		try {
//			System.out.println("Uuid del documento a descargar: " + uuid);
//			GestionDocumentosCompleja interfazCompleja = new InterfazAlfrescoWS();
//			interfazCompleja.iniciarSesion(endPoint, user, password, rootSpace, spaceStore);
//			Fichero f = interfazCompleja.recuperarFichero(uuid);
//			interfazCompleja.terminarSesion();
//			if (f == null) {
//				System.out.println("No se ha encontrado el fichero en Alfresco.");
//				throw new Exception("No se ha encontrado el fichero en Alfresco.");
//			}
//			System.out.println("Documento obtenido:" + f.getContenido().length);
//			return f;
//		} catch (Exception e) {
//			System.out.println("No se ha podido recuperar el fichero con uuid:" + uuid + ". Causa:" + e.getMessage());
//			return null;
//		}
//	}

	/**
	 * Elimina caracteres no admitidos por alfresco para crear una carpeta
	 * @param cadena -  nombre original de la carpeta
	 * @return cadena modificada sustituyendo los caracteres no válidos por '_'
	 * @throws Exception
	 */
	private String eliminarCaracteresNoValidosCarpeta(String cadena) throws Exception {
		//System.out.println("Dentro de: 'eliminarCaracteresNoValidosCarpeta'");
		StringBuffer sb = new StringBuffer(cadena);
		String cadProcesada = sb.toString();
		String textoNoValido = "";
		try {

			for (int i = 0; i < ALFRESCO_CARACT_NO_PERMITIDOS.length; i++) {
				textoNoValido = ALFRESCO_CARACT_NO_PERMITIDOS[i];
				String[] stTexto = cadProcesada.split(textoNoValido);
				StringBuffer retorno = new StringBuffer("");
				for (int j = 0; j < stTexto.length; j++) {
					retorno.append(stTexto[j]);
					if (j != stTexto.length - 1) {
						retorno.append("_");
					}
				}
				cadProcesada = retorno.toString();
			} 					  
			return cadProcesada;
		} catch (PatternSyntaxException e) {
			String error = "El texto a tratar '"+textoNoValido+"' genera una expresión regular erronea.";
			System.out.println(error);
			throw new Exception(error);
		}
	}
	

	public static Properties mapToProperties(Map<String, Object> map) {
		Properties p = new Properties();
		Set<Map.Entry<String, Object>> set = map.entrySet();
//		System.out.println("-- mapToProperties:");
		for (Map.Entry<String, Object> entry : set) {
//			System.out.println(entry.getKey() + " = " + entry.getValue());
			p.put(entry.getKey(), entry.getValue());
		}
		return p;
	}

	public static Map<String, Object> propertiesToMap(Properties props) {
		HashMap<String, Object> hm = new HashMap<String,Object>();
		Enumeration<Object> e = props.keys();
		while (e.hasMoreElements()) {
			String s = (String)e.nextElement();
			if (s.contains(InterfazAlfrescoWS.PROP_TYPE)) {
				hm.put(InterfazAlfrescoWS.PROP_TYPE, props.getProperty(s));
			} else {
				hm.put(s, props.getProperty(s));
			}
		}
		return hm;
	}
}
