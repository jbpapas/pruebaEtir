/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.BDDocumentalBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.TrErrorPlBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.sb05.action.G54Extraccion.G54LanzarAction;
import es.dipucadiz.etir.sb07.G747NotificacionAltaCenso.action.G747OpcionesAction;
import es.dipucadiz.etir.sb07.clientes.action.G727SeleccionAction;

/**
 * Ejecución post batch.
 */
public final class PostBatch {
	
	private static EjecucionBO ejecucionBO;
	private static AcmUsuarioBO acmUsuarioBO;
	private static BDDocumentalBO bdDocumentalBO;
	private static UnidadUrbanaBO unidadUrbanaBO;
	private static TrErrorPlBO trErrorPlBO;
	private static boolean isEjecucionBatch = false;
	private static boolean isLogado = false;
	private static String CODIFICACION = "UTF8";
	
	
	
	private PostBatch() {}
	
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		isEjecucionBatch = true;
		final String[] paths = {"classpath:/*/spring*/applicationContext*.xml"}; // Arranca el contexto necesario en modo batch
	//	final String[] paths = {"classpath:/etir/WEB-INF/classes/comun/spring/applicationContext.xml"};
	 
		final ApplicationContext context = new ClassPathXmlApplicationContext(paths);
		// Instanciar BOs necesarios
		setEjecucionBO((EjecucionBO) context.getBean("ejecucionBO"));
		setAcmUsuarioBO((AcmUsuarioBO) context.getBean("acmUsuarioBO"));
		setBdDocumentalBO((BDDocumentalBO) context.getBean("bdDocumentalBO"));
		setUnidadUrbanaBO((UnidadUrbanaBO) context.getBean("unidadUrbanaBO"));
		setTrErrorPlBO((TrErrorPlBO) context.getBean("trErrorPlBO"));
		// Pas 1: Ejecutar comando linux "t2c"
		ejecutarComandos(args, context);
		
		// Paso 2: Copiar ficheros generados
		copiarFicheros(args, context);
		
		// Paso 3: Merge
		iniciarMerge(args, context);
		
		// Paso 4: Enviar correos
		enviarCorreos(args, context, false);
		
		// Paso 5: Enviar a Portafirmas
		enviarPortafirmas(args, context, false);
		
		// Paso 6: Procesos derivados
		lanzarProcesos(args, context, false);
		
		// Paso 7: Carga de ficheros
		cargarFicheros(args, context, false);
		
		// Paso 8: Carga de ficheros
		peticionCoordenadas(args, context, false);
		
		// Paso 9: Insercción envio notificaciones IDE
		insertarEnvNotificacionesIDE(args, context, false);
		
		
		// Paso 10: Insercción resultados notificaciones IDE
		insertarResultNotificacionesIDE(args, context, false);
	 
		// Paso 11: Comunicaciones servicios web
		serviciosWeb(args, context, false);
	}

	private static void copiarFicheros(String[] args, ApplicationContext context) {
		try {
			Long coEjecucion = null;
			String ruta = null;
			String fichero = null;
			List<String> ficheros = new ArrayList<String>();
			if (args.length > 0) {
				coEjecucion = Long.valueOf(args[0]);
			}
			String[] propertiesToInitialize = {"ejecucionParametroDTOs"};
			EjecucionDTO ejecucionDTO = ejecucionBO.findByIdInitialized(coEjecucion, propertiesToInitialize);
			List<EjecucionParametroDTO> parametros = new ArrayList<EjecucionParametroDTO>(ejecucionDTO.getEjecucionParametroDTOs());
			Collections.sort(parametros);
			AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(ejecucionDTO.getCoAcmUsuario());
						
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion.toString() + "fichero.t2f";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;

			if (Fichero.existeFichero(file)) {
				copiarFicheros(file, rutaCarpetaOracle, acmUsuarioDTO.getCarpetaAcceso(), coEjecucion);
			} else if (BatchConstants.CO_PROCESO_LANZAR_PETICION.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(G54LanzarAction.NUM_PARAM_RUTA).getValor();
				fichero = parametros.get(G54LanzarAction.NUM_PARAM_FICHERO).getValor();
			} else if (BatchConstants.CO_PROCESO_IMPR_ALTA_CENSO.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(G747OpcionesAction.NUM_PARAM_RUTA_IMP - 1).getValor();
				fichero = parametros.get(G747OpcionesAction.NUM_PARAM_FICHERO_IMP - 1).getValor();
			} else if (BatchConstants.CO_PROCESO_REIMPR_ALTA_CENSO.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(G747OpcionesAction.NUM_PARAM_RUTA_REIMP - 1).getValor();
				fichero = parametros.get(G747OpcionesAction.NUM_PARAM_FICHERO_REIMP - 1).getValor();
			} else if (BatchConstants.CO_G727_ENVIO_SMS.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(G727SeleccionAction.NUMORDEN_CARPETA - 1).getValor();
				fichero = parametros.get(G727SeleccionAction.NUMORDEN_FICHERO - 1).getValor();
			} else if (BatchConstants.CO_PROCESO_ENVIO_P19.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = GadirConfig.leerParametro("ruta.carpetas.usuarios") + "C19PET/"; // Carpeta destino
				fichero = ejecucionDTO.getObservaciones().substring(ejecucionDTO.getObservaciones().lastIndexOf(' ')).trim();
			} else if (BatchConstants.CO_PROCESO_ENVIO_C19.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				if (acmUsuarioDTO.getCodigoTerritorialDTO().getCoCodigoTerritorial().startsWith(ProvinciaConstants.CO_PROVINCIA_AYUNTAMIENTOS)) {
					ruta = acmUsuarioDTO.getCarpetaAcceso();
				} else {
					ruta = GadirConfig.leerParametro("ruta.carpetas.usuarios") + "C19PET/"; // Carpeta destino
				}
				fichero = ejecucionDTO.getObservaciones().substring(ejecucionDTO.getObservaciones().lastIndexOf(' ')).trim();
			} else if (BatchConstants.CO_PROCESO_ENVIO_F19.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = GadirConfig.leerParametro("ruta.carpetas.usuarios") + "C19PET/"; // Carpeta destino
				fichero = ejecucionDTO.getObservaciones().substring(ejecucionDTO.getObservaciones().lastIndexOf(' ')).trim();
			} else if (BatchConstants.CO_PROCESO_G773_IMPRESION_DIPTICOS.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(1).getValor();
				fichero = parametros.get(0).getValor();
			} else if (BatchConstants.CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFS.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(8).getValor();
				fichero = parametros.get(7).getValor();
			} else if (BatchConstants.CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFI.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(8).getValor();
				fichero = parametros.get(7).getValor();
			} else if (BatchConstants.CO_PROCESO_G771_RE_FICHERO_INTERCAMBIO.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(8).getValor();
				fichero = parametros.get(7).getValor();
			}else if (BatchConstants.CO_PROCESO_G7A2_BATCH.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = acmUsuarioDTO.getCarpetaAcceso();
				fichero = parametros.get(0).getValor();
			} else if (BatchConstants.CO_PROCESO_CRUCES.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(4).getValor();
				for (int i=5; i<parametros.size(); i++) {
					ficheros.add(parametros.get(i).getValor());
				}
			} else if (BatchConstants.CO_PROCESO_G7E7_PETICION_BOP.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(0).getValor();
				fichero = parametros.get(1).getValor();
			} else if (BatchConstants.CO_PROCESO_CARGA_C60.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = GadirConfig.leerParametro("ruta.carpetas.usuarios") + "FICEXT/";
				for (int i=1; i<parametros.size(); i++) {
					ficheros.add(parametros.get(i).getValor());
				}
			} else if (BatchConstants.ENVIO_INFORMACION_AYUNTAMIENTOS.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(5).getValor();
				fichero = parametros.get(4).getValor();
			} else if (BatchConstants.ENVIO_INFORMACION_COMUNIDAD_REGANTES.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(5).getValor();
				fichero = parametros.get(4).getValor();
			} else if (BatchConstants.SOLICITUD_INFORME_EMISORA.equals(ejecucionDTO.getProcesoDTO().getCoProceso()) || 
					BatchConstants.SOLICITUD_INFORME_RESUMEN_EMISORAS.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(5).getValor();
				fichero = parametros.get(3).getValor();
			} else if (BatchConstants.ENVIO_INFORMACION_GENERICO.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(7).getValor();
				ficheros.add(parametros.get(6).getValor());
				ficheros.add(parametros.get(5).getValor());
			} else if (BatchConstants.GENERACION_RGC.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(3).getValor();
				fichero = parametros.get(2).getValor();
			} else if (BatchConstants.CO_PROCESO_CUENTA_GESTION_RECAUDATORIA.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(1).getValor();
				fichero = parametros.get(0).getValor();
			} else if (BatchConstants.CO_PROCESO_INFORME_DETALLE.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				ruta = parametros.get(1).getValor();
				fichero = parametros.get(0).getValor();
			}
		
			// Copiar fichero único
			if (Utilidades.isNotEmpty(ruta) && Utilidades.isNotEmpty(fichero)) {
				String rutaOracle = GadirConfig.leerParametro("ruta.informes.merge");
				
				if (BatchConstants.CO_PROCESO_G773_IMPRESION_DIPTICOS.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
					String fichAux = fichero.substring(0, fichero.length() - 4); //Quitamos el .dat
					final File path = new File(rutaOracle);
					
					for(File ffichero : path.listFiles()) {
			    		if(ffichero.getName().startsWith(fichAux) && !ffichero.getName().equals(fichero)){
			    			copiar(rutaOracle, ffichero.getName(), ruta);
			    		}
			    	}
				}
				else{
					copiar(rutaOracle, fichero, ruta);
				}
			}
			
			// Copiar una lista de ficheros
			if (!ficheros.isEmpty()) {
				String rutaOracle = GadirConfig.leerParametro("ruta.informes.merge");
				for (String unFichero : ficheros) {
					copiar(rutaOracle, unFichero, ruta);
				}
			}

		} catch (GadirServiceException e) {
			System.err.println("Post batch: " + e.getMensaje());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void copiarFicheros(String file, String carpetaOrigen, String carpetaUsuario, Long coEjecucion) {
		try {
			if (isEjecucionBatch) System.out.println("Existe fichero de ficheros para copiar: " + file);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findById(coEjecucion);
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
			String line;
			line = input.readLine();
			int numLinea = 0;
			while (line != null) {
				if (isEjecucionBatch) System.out.println("Interpreto linea para el fichero a copiar: " + line);
				numLinea++;
				line = line.trim();
				if (Utilidades.isNotEmpty(line)) {
					String[] palabras = line.split("·");
					String fichero = null;
					String carpetaDestino = null;
					String servidorFtp = null;
					String carpetaFtp = null;
					boolean renombrarFichero = false;
					for (int i=0; i<palabras.length; i++) {
						if (isEjecucionBatch) System.out.println("Palabra " + i + ": " + palabras[i]);
						switch (i) {
						case 0:
							fichero = palabras[i].trim();
							break;
						case 1:
							carpetaDestino = palabras[i].trim();
							break;
						case 2:
							servidorFtp = palabras[i].trim();
							break;
						case 3:
							carpetaFtp = palabras[i].trim();
							break;
						case 4:
							renombrarFichero = "1".equals(palabras[i].trim());
						}
					}
					if (Utilidades.isEmpty(fichero)) {
						System.err.println("Error en línea " + numLinea + " del fichero " + file + ", falta información.");
					} else {
						if (Utilidades.isEmpty(carpetaDestino)) {
							carpetaDestino = Fichero.asegurarBarraCarpeta(carpetaUsuario);
						} else {
							carpetaDestino = Fichero.asegurarBarraCarpeta(carpetaDestino);
						}
						Fichero.copiar(carpetaOrigen + fichero, carpetaDestino + fichero, renombrarFichero);
						if (Utilidades.isNotEmpty(servidorFtp)) {
							try {
								FTPService.enviar(fichero, servidorFtp, carpetaDestino, carpetaFtp, true);
							} catch (GadirServiceException e) {
								ejecucionDTO.setCoTerminacion(9003);
								ejecucionDTO.setObservaciones(e.getMensaje());
								ejecucionDTO.setFhActualizacion(Utilidades.getDateActual());
								ejecucionBO.save(ejecucionDTO);
							}
						}
					}
				}
				line = input.readLine();
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copiar(String rutaOracle, String fichero, String ruta) {
		try {
			Fichero.copiar(rutaOracle + fichero, ruta + fichero);
		} catch (IOException ioe) {
			System.err.println("Error copiando fichero de " + rutaOracle + fichero + " a " + ruta + fichero + ": " + ioe.getMessage());
			ioe.printStackTrace();
		}
		try {
			Fichero.utf8ToAnsi(ruta + fichero);
		} catch (IOException ioe) {
			System.err.println("Error convertiendo fichero " + ruta + fichero + " de UTF-8 a ISO-8859-1: " + ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	/**
	 * Iniciar el merge
	 * @param args
	 * @param context
	 */
	private static void iniciarMerge(final String[] args, final ApplicationContext context) {
		try {
			Impresion.mainBatch(args, context);
		} catch (GadirServiceException e) {
			System.err.println("Impresion: " + e.getMensaje());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lanzar procesos derivados online (ejecutar)
	 * @param coEjecucion
	 */
	public static void lanzarProcesosOnline(Long coEjecucion) {
		if (ejecucionBO == null ) {
			ejecucionBO = (EjecucionBO) GadirConfig.getBean("ejecucionBO");
		}
		String[] args = {coEjecucion.toString()};

		// Paso 3: Enviar correos
		enviarCorreos(args, null, true);
		
		// Paso 4: Enviar a Portafirmas
		enviarPortafirmas(args, null, true);

		// Paso 5: Procesos derivados
		lanzarProcesos(args, null, true);
	}
	
	/**
	 * Enviar correos derivados online (ejecutar)
	 * @param coEjecucion
	 */
	public static void enviarCorreosOnline(Long coEjecucion) {
		if (ejecucionBO == null) ejecucionBO = (EjecucionBO) GadirConfig.getBean("ejecucionBO");
		if (bdDocumentalBO == null) bdDocumentalBO = (BDDocumentalBO) GadirConfig.getBean("bdDocumentalBO");
		String[] args = {coEjecucion.toString()};
		enviarCorreos(args, null, true);
	}

	
	
	
	public static void serviciosWebOnLine(Long coEjecucion) {
		if (ejecucionBO == null) ejecucionBO = (EjecucionBO) GadirConfig.getBean("ejecucionBO");
		if (bdDocumentalBO == null) bdDocumentalBO = (BDDocumentalBO) GadirConfig.getBean("bdDocumentalBO");
		String[] args = {coEjecucion.toString()};
		serviciosWeb(args, null, true);
	}
	
	
	
	
	/**
	 * Lanzar procesos derivados
	 * @param args
	 * @param context
	 */
	private static void lanzarProcesos(final String[] args, final ApplicationContext context, boolean isOnline) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);
			final String usuario = ejecucionDTO.getCoAcmUsuario();
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "procesos.t2e";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				if (isEjecucionBatch) System.out.println("Existe fichero de procesos derivados: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					logarUsuario(context, usuario, isOnline);
					if (isEjecucionBatch) System.out.println("Interpreto linea para el proceso derivado: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) {
						String[] palabras = line.split("·");
						String coProceso = null;
						String accion = "I";
						String coProvincia = null;
						String coMunicipio = null;
						String coConcepto = null;
						String coModelo = null;
						String coVersion = null;
						String impresora = null;
						List<String> parametros = new ArrayList<String>();
						for (int i=0; i<palabras.length; i++) {
							if (isEjecucionBatch) System.out.println("Palabra " + i + ": " + palabras[i]);
							switch (i) {
							case 0:
								coProceso = palabras[i].trim();
								break;
							case 1:
								coProvincia = palabras[i].trim();
								break;
							case 2:
								coMunicipio = palabras[i].trim();
								break;
							case 3:
								coConcepto = palabras[i].trim();
								break;
							case 4:
								coModelo = palabras[i].trim();
								break;
							case 5:
								coVersion = palabras[i].trim();
								break;
							case 6:
								impresora = palabras[i].trim();
								break;
							default:
								parametros.add(palabras[i].trim());
								break;
							}
						}
						if (Utilidades.isEmpty(coProceso) || Utilidades.isEmpty(accion) || Utilidades.isEmpty(coProvincia) || Utilidades.isEmpty(coMunicipio) || Utilidades.isEmpty(coConcepto) || Utilidades.isEmpty(coModelo) || Utilidades.isEmpty(coVersion) || Utilidades.isEmpty(impresora)) {
							System.err.println("Error en línea " + numLinea + ", falta información.");
						} else {
							AccesoPlantillaVO accesoPlantillaVO = new AccesoPlantillaVO();
							accesoPlantillaVO.setAccion(accion);
							accesoPlantillaVO.setCoProvincia(coProvincia);
							accesoPlantillaVO.setCoMunicipio(coMunicipio);
							accesoPlantillaVO.setCoConcepto(coConcepto);
							accesoPlantillaVO.setCoModelo(coModelo);
							accesoPlantillaVO.setCoVersion(coVersion);
							if (isOnline) {
								Batch.ejecutar(coProceso, parametros, accesoPlantillaVO, true);
							} else {
								Batch.lanzar(coProceso, usuario, parametros, impresora, accesoPlantillaVO, true);
							}
						}
					}
					
					line = input.readLine();
				}
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de procesos derivados.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void peticionCoordenadas(final String[] args, final ApplicationContext context, boolean isOnline) {
		try {
			/*Ejemplo de Envío
			<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://consultas.callejero.juntadeandalucia.es">
			   <soapenv:Header/>
			   <soapenv:Body>
			      <con:geocoderSrs soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
			         <streetname xsi:type="xsd:string">BLAS INFANTE</streetname>
			         <streetnumber xsi:type="xsd:string">36</streetnumber>
			         <streettype xsi:type="xsd:string">AVENIDA</streettype>
			         <locality xsi:type="xsd:string">11018</locality>
			         <srs xsi:type="xsd:string">EPSG:25830</srs>
			      </con:geocoderSrs>
			   </soapenv:Body>
			</soapenv:Envelope>
			*/   
			   
			/*Ejemplo de respuesta   
			   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			   <soapenv:Body>
			      <ns1:geocoderSrsResponse soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:ns1="http://consultas.callejero.juntadeandalucia.es">
			         <geocoderSrsReturn soapenc:arrayType="soapenc:string[1]" xsi:type="soapenc:Array" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
			            <geocoderSrsReturn xsi:type="soapenc:string"><![CDATA[<?xml version="1.0" encoding="UTF-8"?>
							<GeocoderResults>
								<PartialMatch>
									<StreetName>BLAS INFANTE</StreetName>
									<StreetNumber>19</StreetNumber>
									<StreetType>AVENIDA</StreetType>
									<Locality>11018</Locality>
									<Group></Group>
									<Center>EL GASTOR</Center>
									<Rotulo>19</Rotulo>
									<CoordinateX>293035.7082</CoordinateX>
									<CoordinateY>4081493.5273</CoordinateY>
									<MatchLevel>StreetType</MatchLevel>
									<Similarity>1.0</Similarity>
								</PartialMatch>
							</GeocoderResults>]]>
						</geocoderSrsReturn>
			         </geocoderSrsReturn>
			      </ns1:geocoderSrsResponse>
			   </soapenv:Body>
			</soapenv:Envelope>
			*/   
			System.out.println("Empezamos el procesamiento de peticionCoordenadas " + Utilidades.dateToDDMMYYYYHHMM(new Date()));
			 
			final int coEjecucion = Integer.parseInt(args[0]);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);  
			final String usuario = ejecucionDTO.getCoAcmUsuario();
			logarUsuario(context, usuario, isOnline);
			 
 
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "_PETICION_COORDENADAS.pco";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			 
			   
			/*borrar desde aqui
				  int coEjecucion = 2429093;
				
				 
				final String usuario = "martecher";
				final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
				final String nombreTxtProcesos = coEjecucion + "_PETICION_COORDENADAS.pco";
				final String file = rutaCarpetaOracle + nombreTxtProcesos;
			hasta aqui*/
			
			
			String[] datosLinea = new String[10];
			String[] datosLocalizarUbica = new String[10];
			String tipoVia="";
			String nombreVia="";
			String numero="";
			String codINE="";
			String localizar_En_ETIR="";
			String coCalleEtir="";
			String numeroEtir="";
			String fxActualizacion="";
			
			 String[] resultadoXML=new String[11];
			 String[] resultado=new String[11];			
			

			 
			 	String streetName="";
			 	String streetNumber="";
				String streetType="";
				String locality="";
				 String group="";
				 String center="";
				 String rotulo="";
				 String coordinateX="";
				 String coordinateY="";
				 String matchLevel="";
				 String similarity="";
			 
			 
			 
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de carga de ficheros: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					try {
						
					
					//logarUsuario(context, usuario, isOnline);
						//System.out.println("Interpreto linea para carga de ficheros: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) { 
						//Recogemos los datos del fichero
						datosLinea = line.split(";");
						tipoVia=datosLinea[0];
						nombreVia=datosLinea[1];
						numero=datosLinea[2];
						codINE=datosLinea[3];
						localizar_En_ETIR=datosLinea[4];
						datosLocalizarUbica= localizar_En_ETIR.split("#");					
						coCalleEtir=datosLocalizarUbica[0];
						numeroEtir=datosLocalizarUbica[1];
						fxActualizacion=datosLocalizarUbica[2];
 		
		/*				 System.out.println("tipoVia: "+tipoVia);
						System.out.println("nombreVia: "+nombreVia);
						System.out.println("numero: "+numero);
						System.out.println("codINE: "+codINE);
						System.out.println("localizar_En_ETIR: "+localizar_En_ETIR);
						System.out.println("coCalleEtir: "+coCalleEtir);
						System.out.println("numeroEtir: "+numeroEtir);
						System.out.println("fxActualizacion: "+fxActualizacion);
			*/			  
					if(	!Utilidades.isEmpty(tipoVia) && !Utilidades.isEmpty(nombreVia) && !Utilidades.isEmpty(numero) && !Utilidades.isEmpty(codINE) &&
							!Utilidades.isEmpty(coCalleEtir) && !Utilidades.isEmpty(numeroEtir) && !Utilidades.isEmpty(fxActualizacion)){
					//		System.out.println("LLamamos a GeoDirService.envio("+  nombreVia+","+   numero+","+   tipoVia+","+   codINE);
							resultadoXML = GeoDirService.envio (  nombreVia,   numero,   tipoVia,   codINE);
				/*	 		
							System.out.println("Resultado de GeoDirService.envio("+  nombreVia+","+   numero+","+   tipoVia+","+   codINE);
							for(int i=0; i<resultadoXML.length;i++){
								
								System.out.println("resultadoXML["+i+"]: "+resultadoXML[i]);
							}
					*/ 
 							 //ejemplo de resultado 
//							 <GeocoderResults>
//							 	<PartialMatch>
//							 		<StreetName>MOLINO</StreetName>
//							 		<StreetNumber>13</StreetNumber>
//							 		<StreetType>CALLE</StreetType>
//							 		<Locality>11018</Locality>
//							 		<Group></Group>
//							 		<Center>EL GASTOR</Center>
//							 		<Rotulo>13</Rotulo>
//							 		<CoordinateX>292770.8657</CoordinateX>
//							 		<CoordinateY>4081270.4333</CoordinateY>
//							 		<MatchLevel>Unknown</MatchLevel>
//							 		<Similarity>0.9</Similarity>
//							 	</PartialMatch>
//							</GeocoderResults>
							
							/*lo guardamos en 			 	
							 * String streetName="";
							 
			 	String streetNumber="";
				String streetType="";
				String locality="";
				 String group="";
				 String center="";
				 String rotulo="";
				 String coordinateX="";
				 String coordinateY="";
				 String matchLevel="";
				 String similarity="";
					*/		
							DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
							domFactory.setNamespaceAware(true); // never forget this!
							DocumentBuilder builder = domFactory.newDocumentBuilder();
							Document doc = builder.parse(new InputSource(new StringReader(resultadoXML[0])));
							NodeList geoResultsNodeList = doc.getElementsByTagName("GeocoderResults").item(0).getChildNodes();
							for(int i = 0; i < geoResultsNodeList.getLength(); i++) {
								 
								try {
 

									Node partialMatchNode = geoResultsNodeList.item(i);
									NodeList geoDatos = partialMatchNode.getChildNodes();
									if (geoDatos.getLength() == 0) {
										continue;
									}
									for(int j = 0; j < geoDatos.getLength(); j++) {
										if (Utilidades.isNotEmpty(geoDatos.item(j).getTextContent().trim())) {
											if (geoDatos.item(j).getNodeName().equals("StreetName")) {
												streetName = geoDatos.item(j).getTextContent().trim();							
											} else if (geoDatos.item(j).getNodeName().equals("StreetNumber")) {
												streetNumber= geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("StreetType")) {  
												streetType= geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("Locality")) {  
												locality = geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("Group")) {  
												group= geoDatos.item(j).getTextContent().trim();
										    } else if (geoDatos.item(j).getNodeName().equals("Center")) {  
											    center= geoDatos.item(j).getTextContent().trim();
										    }else if (geoDatos.item(j).getNodeName().equals("Rotulo")) {  
										    	rotulo=geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("CoordinateX")) {  
												coordinateX=geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("CoordinateY")) {  
												coordinateY= geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("MatchLevel")) {  
												matchLevel= geoDatos.item(j).getTextContent().trim();
											} else if (geoDatos.item(j).getNodeName().equals("Similarity")) {  
												similarity= geoDatos.item(j).getTextContent().trim();
											}		
											//TODO: Añadir todas las propiedades
											else {
												throw new GadirServiceException("Propiedad desconocida: " + geoDatos.item(j).getNodeName());
											}
										}
									}
								} catch (GadirServiceException e) {
									System.out.println("Error parseando GeocoderResults");
								}
								
								 
							}							
		 	/*			 	System.out.println("streetName= "+streetName);   
							System.out.println("streetNumber= "+streetNumber);  
							System.out.println("streetType= "+streetType);   
							System.out.println("locality= "+locality);   
							 System.out.println("group= "+group);   
							 System.out.println("center= "+center);   
							 System.out.println("rotulo= "+rotulo);    
							 System.out.println("coordinateX= "+coordinateX);   
							 System.out.println("coordinateY= "+coordinateY);   
							 System.out.println("matchLevel= "+matchLevel);   
							 System.out.println("similarity= "+similarity);   
							 
							 System.out.println("coCalleEtir= "+coCalleEtir);
							 
							 System.out.println("numeroEtir= "+numeroEtir);
				 	*/ 
							//System.out.println("Parseo respuesta ok"); 

							
							DetachedCriteria dcUnidUrbana = DetachedCriteria.forClass(UnidadUrbanaDTO.class, "u");
							
							if(streetType.equals("CARRETERA")){
								//	System.out.println("numeroEtir = "+numeroEtir);  
								double numEtirDouble =  Double.valueOf(numeroEtir);
								
				                dcUnidUrbana.createAlias("calleDTO", "c", DetachedCriteria.LEFT_JOIN);
								dcUnidUrbana.add(Restrictions.eq("c.sigla", "CR"));
								dcUnidUrbana.add(Restrictions.eq("c.coCalle", Long.valueOf(coCalleEtir)));
								if(Integer.valueOf(numeroEtir)!=0){
									dcUnidUrbana.add(Restrictions.eq("km",   new BigDecimal(numEtirDouble)));
								}else{
									dcUnidUrbana.add(Restrictions.or(Restrictions.isNull("km"), Restrictions.eq("km",  new BigDecimal("0")))   );
								}
							}else{
								
								
				                dcUnidUrbana.createAlias("calleDTO", "c", DetachedCriteria.LEFT_JOIN);
								dcUnidUrbana.add(Restrictions.ne("c.sigla", "CR"));
								dcUnidUrbana.add(Restrictions.eq("c.coCalle", Long.valueOf(coCalleEtir)));
								if(Integer.valueOf(numeroEtir)!=0){
									dcUnidUrbana.add(Restrictions.eq("numero", Integer.valueOf(numeroEtir)));								 
								}else{
									dcUnidUrbana.add(Restrictions.or(Restrictions.isNull("numero"), Restrictions.eq("numero", 0))    );
								}								
							}
							
							try {
								
							
									List<UnidadUrbanaDTO> unidadesUrb = unidadUrbanaBO.findByCriteria(dcUnidUrbana);
									double dobulePorcentaje =  Double.valueOf(similarity)*100;
								//	System.out.println("dobulePorcentaje="+dobulePorcentaje); 
									for (UnidadUrbanaDTO u:unidadesUrb){

										u.setCoordenadaX(coordinateX);
										u.setCoordenadaY(coordinateY);
										u.setPorcentajeSimilitud( new BigDecimal( String.valueOf(dobulePorcentaje) )); 
										final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", new Locale("es", "ES"));
		
										u.setFhActualizacionCoordenadas(dateFormat.parse(fxActualizacion));
										u.setFhActualizacion(new Date());
										u.setCoUsuarioActualizacion("geodir");
										 
		//								TrErrorPlDTOId id = new TrErrorPlDTOId();
		//								id.setBoPl(true);
		//								id.setCoUsuario("martecher");
		//								id.setFhActualizacion(new Date());
		//								String error =  "OK "+ streetType+" "+streetName+" "+ locality  +" "+ similarity+" "+ Utilidades.YYYYMMDDToDate(fxActualizacion);
		//								ClobImpl errorClob= new ClobImpl(error); 
		//								errorClob.setString(error.length(), error);
		//								id.setError(errorClob);								
		//								TrErrorPlDTO trError = new TrErrorPlDTO(id);
										
										u.setFhActualizacion(new Date());
										u.setCoUsuarioActualizacion("geodir");
										 unidadUrbanaBO.save(u);
										 //		  System.out.println("Actualizamos la unidad urbana con co_unidad_urbana = "+u.getCoUnidadUrbana());   
										 
								    //	trErrorPlBO.save(trError);
								    	
									}
							
							} catch (Exception e) {
								// TODO: handle exception
								System.out.println("No encontramos unidades urbanas para cocalle = "+coCalleEtir+" y numero o km = "+numeroEtir );  
								e.printStackTrace();
							}
							
							
						}else{
							//Escribiremos fichero de error
							
//							TrErrorPlDTOId id = new TrErrorPlDTOId();
//							id.setBoPl(true);
//							id.setCoUsuario("martecher");
//							id.setFhActualizacion(new Date());
//							String error =  "ERROR "+ line;
//							ClobImpl errorClob= new ClobImpl(error);
//
//							errorClob.setString(error.length(), error);
//							id.setError(errorClob);
//							
//							TrErrorPlDTO trError = new TrErrorPlDTO(id);
//							 
//					    	trErrorPlBO.save(trError);
							if(Utilidades.isEmpty(tipoVia)){
								tipoVia="nulo";
							}
							if(Utilidades.isEmpty(nombreVia)){
								nombreVia="nulo";
							}
							if(Utilidades.isEmpty(numero)){
								numero="nulo";
							}
							if(Utilidades.isEmpty(codINE)){
								codINE="nulo";
							}
							if(Utilidades.isEmpty(coCalleEtir)){
								coCalleEtir="nulo";
							}
							if(Utilidades.isEmpty(numeroEtir)){
								numeroEtir="nulo";
							}
							if(Utilidades.isEmpty(fxActualizacion)){
								fxActualizacion="nulo";
							}							
								
								
								
							System.out.println("Alguno de los parámetros necesarios está a nulo");
							System.out.println("tipoVia="+tipoVia
									+ " nombreVia= "+    nombreVia
									+ " numero= "+   numero
									+ " codINE= "+    codINE
									+ " coCalleEtir= "+   coCalleEtir
									+ " numeroEtir= "+   numeroEtir
									+ " fxActualizacion= "+  fxActualizacion);
						}
						
						 
					}
					
					line = input.readLine();
					
					
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("Error tratando la linea "+ line);
						//System.out.println("Resultado obtenido  "+ resultadoXML);
						e.printStackTrace();
						line = input.readLine();
					}
				}
				input.close();
				System.out.println("Terminamos el procesamiento de peticionCoordenadas " + Utilidades.dateToDDMMYYYYHHMM(new Date()));
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de carga de ficheros de FTP.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void insertarEnvNotificacionesIDE(final String[] args, final ApplicationContext context, boolean isOnline) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);
			final String usuario = ejecucionDTO.getCoAcmUsuario();
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "_envioNotifcaciones.ide";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de carga de ficheros: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					logarUsuario(context, usuario, isOnline);
					System.out.println("Interpreto linea para carga de ficheros: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) {
					    //EL FORMATO DEL FICHERO SERÁ CODIGO SOLAMENTE
						InsertarIDECadiz.envio (line, "E");
					}
					
					line = input.readLine();
				}
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de envíos de notificaciones.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private static void insertarResultNotificacionesIDE(final String[] args, final ApplicationContext context, boolean isOnline) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);
			final String usuario = ejecucionDTO.getCoAcmUsuario();
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "_resultadoNotifcaciones.rde";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de carga de ficheros: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					logarUsuario(context, usuario, isOnline);
					System.out.println("Interpreto linea para carga de ficheros: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) {
					    //EL FORMATO DEL FICHERO SERÁ CODIGO SOLAMENTE
						InsertarIDECadiz.envio (line, "R");
					}
					
					line = input.readLine();
				}
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de resultados de notificaciones.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void cargarFicheros(final String[] args, final ApplicationContext context, boolean isOnline) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);
			final String usuario = ejecucionDTO.getCoAcmUsuario();
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String entorno = GadirConfig.leerParametro("entorno.servidor");
			final String nombreTxtProcesos = coEjecucion + "carga.t2c";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de carga de ficheros: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					logarUsuario(context, usuario, isOnline);
					System.out.println("Interpreto linea para carga de ficheros: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) {
						String[] palabras = line.split("·");
						String servidor = null;
						String carpeta = null;
						String filtroBusqueda = null;
						String coProceso = null;
						List<String> parametrosAdicionales = new ArrayList<String>();
						for (int i=0; i<palabras.length; i++) {
							System.out.println("Palabra " + i + ": " + palabras[i]);
							switch (i) {
							case 0:
								servidor = palabras[i].trim();
								break;
							case 1:
								carpeta = palabras[i].trim();
								break;
							case 2:
								filtroBusqueda = palabras[i].trim();
								break;
							case 3:
								coProceso = palabras[i].trim();
								break;
							default:
								parametrosAdicionales.add(palabras[i].trim());
								break;
							}
						}
						if (Utilidades.isEmpty(servidor) || Utilidades.isEmpty(carpeta) || Utilidades.isEmpty(filtroBusqueda) || Utilidades.isEmpty(coProceso)) {
							System.err.println("Error en línea " + numLinea + ", falta información.");
						} else {
							List<String> ficheros;
							if (FTPService.FTP_SERVIDOR_SIGRE.equals(servidor)) {
								ficheros = FTPService.procesarFiles(servidor, carpeta, filtroBusqueda, DatosSesion.getCarpetaAcceso());
							} else {
								ficheros = Fichero.copiarFicheros(carpeta, rutaCarpetaOracle, filtroBusqueda, "produccion".equals(entorno) ? "PROCESADOS/" : null);
							}
							System.out.println("Se encuentran " + ficheros.size() + " ficheros en el origen.");
							for (int i=0; i<ficheros.size(); i++) {
								List<String> parametros = parametrosParticularesSegunProceso(coProceso, ficheros.get(i), i);
								for (String parametroAdicional : parametrosAdicionales) {
									parametros.add(parametroAdicional);
								}
								if (isOnline) {
									System.out.println("Ejecuta proceso " + coProceso + "." + parametros.size());
									Batch.ejecutar(coProceso, parametros, new AccesoPlantillaVO(), true);
								} else {
									System.out.println("Lanza proceso " + coProceso + ". " + parametros.size());
									Batch.lanzar(coProceso, usuario, parametros, "SINDETER", new AccesoPlantillaVO(), true);
								}
							}
						}
					}
					
					line = input.readLine();
				}
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de carga de ficheros de FTP.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<String> parametrosParticularesSegunProceso(String coProceso, String fichero, int iteracion) {
		List<String> parametros = new ArrayList<String>();
		if (BatchConstants.CO_PROCESO_CARGA_C60.equals(coProceso)) {
			parametros.add(fichero);
			List<String> listaEmisoras = TablaGt.getListaElementos(TablaGt.TABLA_EMISORAS_ADICIONALES, TablaGt.COLUMNA_GRUPO_DE_EMISORA);
			listaEmisoras = new ArrayList<String>(new HashSet<String>(listaEmisoras)); // Eliminar duplicados
			for(String listaEmisorasAux : listaEmisoras) {
				String nombre = listaEmisorasAux;
				nombre += Utilidades.dateToYYYYDDDJulian();
				nombre += "_";
				String hora = Utilidades.dateToHHMMSSN(new Date());
				hora = hora.substring(0, hora.length() - 1) + Integer.toString(iteracion);
				nombre += hora;
				nombre += ".DAT";
				parametros.add(nombre);
			}
		} else {
			parametros.add(fichero);
		}
		return parametros;
	}

	private static void logarUsuario(final ApplicationContext context, final String usuario, final boolean isOnline) throws GadirServiceException {
		if (!isLogado && !isOnline) {
			DaoAuthenticationProvider daoAuthenticationProvider = (DaoAuthenticationProvider) context.getBean("daoAuthenticationProvider");
	        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, acmUsuarioBO.findById(usuario).getContrasena());
	        Authentication result = daoAuthenticationProvider.authenticate(auth);
	        SecurityContext ctx = new SecurityContextImpl();
	        ctx.setAuthentication(result);
	        SecurityContextHolder.setContext(ctx);
	        isLogado = true;
		}
	}

	public static void enviarCorreos(String[] args, ApplicationContext context, boolean isOnline) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "email.t2m";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de correos electrónicos: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					System.out.println("Interpreto linea para el correo electrónico: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) {
						String[] palabras = line.split("·");
						String destinatarios = null;
						String asunto = null;
						String mensaje = null;
						String ruta = null;
						for (int i=0; i<palabras.length; i++) {
							System.out.println("Palabra " + i + ": " + palabras[i]);
							switch (i) {
							case 0:
								destinatarios = palabras[i].trim();
								break;
							case 1:
								asunto = palabras[i].trim();
								break;
							case 2:
								mensaje = palabras[i].trim().replace("[NL]", "\n");;
								break;
							case 3:
								ruta = palabras[i].trim();
								break;
							}
						}
						if (Utilidades.isEmpty(destinatarios) || Utilidades.isEmpty(asunto) || Utilidades.isEmpty(mensaje)) {
							System.err.println("Error en línea " + numLinea + ", falta información.");
						} else {
							byte[] byteArray = null;
							if (Utilidades.isNotEmpty(ruta) && ruta.startsWith("BDDOC:")) {
								// Convertir el BLOB a un byteArray.
								String[] bddocSplit = ruta.split(":");
								BDDocumentalDTO bdDocumentalDTO = bdDocumentalBO.findById(Long.valueOf(bddocSplit[1]));
								BufferedInputStream bis = new BufferedInputStream(bdDocumentalDTO.getFichero().getBinaryStream());
								ByteArrayOutputStream bao = new ByteArrayOutputStream(); 
								byte[] buffer = new byte[4096]; 
								int length = 0; 
								while ((length = bis.read(buffer)) != -1) {
									bao.write(buffer, 0, length);
								}
								bao.close();
								bis.close(); 
								byteArray = bao.toByteArray();
								ruta = bdDocumentalDTO.getNombre();
							} else if (Utilidades.isNotEmpty(ruta) && Utilidades.isNumeric(ruta)) {
								// Buscar el informe en GA_INFORME
								int ordenInforme = Integer.parseInt(ruta); // 1 significa primer informe
								@SuppressWarnings("unchecked")
								List<Object> objects = (List<Object>) ejecucionBO.ejecutaQuerySelect("SELECT ruta_pdf FROM GA_INFORME WHERE co_ejecucion=" + coEjecucion + " ORDER BY co_informe");
								if (objects.size() < ordenInforme) {
									System.err.println("El adjunto " + ordenInforme + " indicado en el .t2m no existe, se envía email sin adjunto.");
									ruta = null;
								} else {
									ruta = (String) objects.get(ordenInforme - 1);
								}
							}
							
							Impresion.enviarCorreo(ruta, destinatarios, asunto, mensaje, byteArray, false);
						}
					}
					line = input.readLine();
				}
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de correo electrónico.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void serviciosWeb(String[] args, ApplicationContext context, boolean isOnline) {
		try {
			final long coEjecucion = Integer.parseInt(args[0]);
			EjecucionDTO ejecucionDTO = ejecucionBO.findById(coEjecucion);
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			  String nombreTxtProcesos = coEjecucion + "webservice.t2w";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de servicios web: " + file);
				// Abrir el t2w
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				input.mark(2);
				byte[] bytes = new byte[2];
				bytes[0] = (byte) input.read();
				bytes[1] = (byte) input.read();
				input.reset();
				String type = new String(bytes);
				
				try {
					if ("NE".equals(type)) {
						NotificacionElectronicaService.publicar(input, ejecucionDTO.getProcesoDTO().getCoProceso(), args[0]);
					}
					if ("NO".equals(type)) {
						System.out.println("Encontramos linea que empieza por NO");
						AlmacenarAcuseReciboService.publicar(input, ejecucionDTO.getProcesoDTO().getCoProceso(), args[0]);
					}					
				} catch (GadirServiceException e) {
					System.err.println("Error procesando t2w: " + e.getMensaje());
					e.printStackTrace();
				}
				
				// Cerrar el t2w
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de correo electrónico.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void enviarPortafirmas(final String[] args, final ApplicationContext context, boolean isOnline) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);  
			final String usuario = ejecucionDTO.getCoAcmUsuario();
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "portafirmas.t2s";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de envio a portafirmas: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					logarUsuario(context, usuario, isOnline);
					System.out.println("Interpreto linea para el envio a portafirmas: " + line);
					numLinea++;
					line = line.trim();
					if (Utilidades.isNotEmpty(line)) {
						String[] palabras = line.split("·");
						Long coPortafirmas = null;
						for (int i=0; i<palabras.length; i++) {
							System.out.println("Palabra " + i + ": " + palabras[i]);
							switch (i) {
							case 0:
								coPortafirmas = Long.valueOf(palabras[i].trim());
								break;
							}
						}
						if (Utilidades.isEmpty(coPortafirmas)) {
							System.err.println("Error en línea " + numLinea + ", falta información.");
						} else {
							if (!PortafirmasService.envio(coPortafirmas)) {
								System.err.println("Ha habido elgún error en el servicio de Portafirmas, ver log. coPortafirmas=" + coPortafirmas.toString());
							} else {
								System.out.println("Envío a Portafirmas correcto. coPortafirmas=" + coPortafirmas.toString());
							}
						}
					}
					
					line = input.readLine();
				}
				input.close();
			} else {
				if (isEjecucionBatch) System.out.println("No existe fichero de envio a portafirmas.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void ejecutarComandos(String[] args, ApplicationContext context) {
		try {
			final int coEjecucion = Integer.parseInt(args[0]);
			final String rutaCarpetaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			final String nombreTxtProcesos = coEjecucion + "comando.t2c";
			final String file = rutaCarpetaOracle + nombreTxtProcesos;
			if (Fichero.existeFichero(file)) {
				System.out.println("Existe fichero de comandos linux: " + file);
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				String line;
				line = input.readLine();
				int numLinea = 0;
				while (line != null) {
					numLinea++;
					
				    try {
				    	String[] cmd = { "/bin/sh", "-c", line.trim() };
					    Process p = Runtime.getRuntime().exec(cmd);
						p.waitFor();
						System.out.println("Ejecutado comando " + numLinea + ": " + line);
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
						String lineOutput = "";			
						while ((lineOutput = reader.readLine())!= null) {
							System.out.println("Respuesta: " + lineOutput);
						}
						reader.close();
						
					} catch (InterruptedException e) {
						System.err.println("Error procesando liena " + numLinea + ": " + line);
						e.printStackTrace();
					}
					
					line = input.readLine();
				}
				input.close();
				if (numLinea == 0) {
					System.out.println("El fichero " + file + " no tiene contenido.");
				}
			} else {
				System.out.println("No existe fichero de comandos linux");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}






	public static void setEjecucionBO(EjecucionBO ejecucionBO) {
		PostBatch.ejecucionBO = ejecucionBO;
	}

	public static EjecucionBO getEjecucionBO() {
		return ejecucionBO;
	}

	public static AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public static void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		PostBatch.acmUsuarioBO = acmUsuarioBO;
	}

	public static boolean isEjecucionBatch() {
		return isEjecucionBatch;
	}

	public static void setEjecucionBatch(boolean isEjecucionBatch) {
		PostBatch.isEjecucionBatch = isEjecucionBatch;
	}

	public static BDDocumentalBO getBdDocumentalBO() {
		return bdDocumentalBO;
	}

	public static void setBdDocumentalBO(BDDocumentalBO bdDocumentalBO) {
		PostBatch.bdDocumentalBO = bdDocumentalBO;
	}

	public static UnidadUrbanaBO getUnidadUrbanaBO() {
		return unidadUrbanaBO;
	}

	public static void setUnidadUrbanaBO(UnidadUrbanaBO unidadUrbanaBO) {
		PostBatch.unidadUrbanaBO = unidadUrbanaBO;
	}

	public static TrErrorPlBO getTrErrorPlBO() {
		return trErrorPlBO;
	}

	public static void setTrErrorPlBO(TrErrorPlBO trErrorPlBO) {
		PostBatch.trErrorPlBO = trErrorPlBO;
	}

	
}
