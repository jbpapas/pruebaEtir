/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.springframework.context.ApplicationContext;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.InformeBO;
import es.dipucadiz.etir.comun.bo.ProcesoAccionBO;
import es.dipucadiz.etir.comun.boStoredProcedure.GuardarDocumentacionBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.InformeDTO;
import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.dto.ProcesoAccionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.ImpresionInformeVO;
import es.dipucadiz.etir.comun.vo.ImpresionParametroVO;

/**
 * Generación de informes
 * @author jronnols
 *
 */
final public class Impresion {
	static final public int ACT_NADA     = -1;
	static final public int ACT_GUARDAR  = 1;
	static final public int ACT_ABRIR    = 2;
	static final public int ACT_IMPRIMIR = 3;
	static final public int ACT_EMAIL    = 4;
	static final public int ACT_ALFRESCO = 5;
	static final public int ACT_REMESA   = 6;

	static final public  String ETIQUETA_CABEZA   = "CABEZA";
	static final private String ETIQUETA_PIE	  = "PIE";
	static final public  String ETIQUETA_CUERPO   = "CUERPO";
	static final private String ETIQUETA_FIN      = "FIN";
	static final public  String ETIQUETA_LINEA	  = "LINEA";
	static final public  String ETIQUETA_FINTABLA = "FINTABLA";
	static final private String ETIQUETA_FECHA	  = "FECHA";
	static final private String ETIQUETA_HORA	  = "HORA";
	static final private String ETIQUETA_USUARIO  = "USUAR";
	static final private String ETIQUETA_AMBITO   = "AMBIT";
	static final private String ETIQUETA_ORGANO   = "ORGAN";
	static final private String ETIQUETA_ESCUDO   = "IMA99";
	static final public  String ETIQUETA_EJECUCIO = "EJECU";
	static final public  String ETIQUETA_ORDEN    = "NUORD";
	static final private String ETIQUETA_SALTOPAG = "SALTOPAGINA";
	static final public  String ETIQUETA_CONFIG   = "CONFIG";
	static final private String ETIQUETA_EMAIL    = "EMAIL";
	static final private String ETIQUETA_ASUNTO_EMAIL = "AMAIL";
	static final private String ETIQUETA_CUERPO_EMAIL = "CMAIL";
	static final private String ETIQUETA_NCOPY    = "NCOPY";
	static final private String ETIQUETA_PRINT    = "PRINT";
	static final private String ETIQUETA_BDDOC    = "BDDOC";
	static final public  String ETIQUETA_BDGRU    = "BDGRU";
	static final public  String ETIQUETA_BDEXP    = "BDEXP";
	static final public  String ETIQUETA_NLIPA    = "NLIPA";
	static final public  String ETIQUETA_NLISA    = "NLISA";
	static final public  String ETIQUETA_NLIPP    = "NLIPP";
	static final private String ETIQUETA_ABRIR    = "ABRIR";
	static final private String ETIQUETA_GUARDAR  = "GUARD";
	static final public  String ETIQUETA_NOM_PDF  = "DSPDF";
	static final private String ETIQUETA_INODT    = "INODT"; // Informe ODT
	static final public  String ETIQUETA_AUPLA    = "AUPLA"; // Nombre plantilla adicional
	static final public  String ETIQUETA_XXPLA    = "XXPLA"; // S = usar plantilla adicional
	static final public  String ETIQUETA_DSZIP    = "DSZIP"; // Ruta en caso de que los informes vayan enpaquetados
	static final private char   ETIQUETA_CHAR     = '#';
	
	static final public  String LINUX             = "linux";
	static final public  String CODIFICACION      = "UTF8";
	static final private String ACCION_INFORME    = "I";
	static final private String TABLA_BREAK       = "GADIR_BREAK_";
	static final private String CONTENT_XML       = "content.xml";
	static final private int    LIMITE_LINEAS     = 999999999;//2500;
	static final private String ROMPE_LINEA       = "[NL]";
	
	static final private String EMAIL_FROM = "etir@dipucadiz.es";
	static final private String EMAIL_PROTOCOLO = "smtp";
	static final private String EMAIL_HOST = "neron5.dipucadiz.dom";
	static final private String EMAIL_USER = "etir";
	static final private String EMAIL_PASSWORD = "RXRpci4yNDM";
	
	static final private String ODT_NOMBRE_TABLA = "<table:table table:name=\"";
	static final private String ODT_ESTILO_TABLA = "style:family=\"table\"";
	static final private String ODT_FIN_ESTILO = "/></style:style>";
	static final private String ODT_ESTILO_BREAK_PAGE = " fo:break-before=\"page\"";
	static final private String ODT_ESTILO_BREAK = "fo:break-before";
	static final private String ODT_TABLE_NAME = "table:name=\"";
	static final private String ODT_ESTILO_NAME = "table:style-name=\"";

	static final private boolean BORRAR_TEMPORALES = false;
	static final private String EXPRESION_IMAGEN = "(IMA|RUT)\\d\\d";
	static final private String EXTENSION_PDF = ".pdf";
	
	static public  String tipoSistema = null;
	static private String eol = null;
	static private String rutaRemesa = null;

	private static EjecucionBO ejecucionBO;
	private static AcmUsuarioBO acmUsuarioBO;
	private static ProcesoAccionBO procesoAccionBO;
	private static InformeBO informeBO;
	private static DocumentoBO documentoBO;
	private static GuardarDocumentacionBO guardarDocumentacionBO;
	private static Log log = null;
	static private List<String> etiquetasFijas = new ArrayList<String>();

	private Impresion() {}
	
	private static void iniciarVariables(final boolean conLog) {
		if (tipoSistema == null) {
			tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		}
		if (eol == null) {
			eol = (LINUX.equals(tipoSistema) ? "\n" : "\r\n");
		}
		if (conLog && log == null) {
			log = LogFactory.getLog(Impresion.class);
		}
		if (etiquetasFijas.isEmpty()) {
			etiquetasFijas.add(ETIQUETA_CABEZA);
			etiquetasFijas.add(ETIQUETA_PIE);
			etiquetasFijas.add(ETIQUETA_CUERPO);
			etiquetasFijas.add(ETIQUETA_LINEA);
			etiquetasFijas.add(ETIQUETA_FECHA);
			etiquetasFijas.add(ETIQUETA_HORA);
			etiquetasFijas.add(ETIQUETA_USUARIO);
			etiquetasFijas.add(ETIQUETA_AMBITO);
			etiquetasFijas.add(ETIQUETA_ORGANO);
			etiquetasFijas.add(ETIQUETA_ESCUDO);
			etiquetasFijas.add(ETIQUETA_EJECUCIO);
			etiquetasFijas.add(ETIQUETA_ORDEN);
			etiquetasFijas.add(ETIQUETA_CONFIG);
			etiquetasFijas.add(ETIQUETA_EMAIL);
			etiquetasFijas.add(ETIQUETA_ASUNTO_EMAIL);
			etiquetasFijas.add(ETIQUETA_CUERPO_EMAIL);
			etiquetasFijas.add(ETIQUETA_BDDOC);
			etiquetasFijas.add(ETIQUETA_INODT);
			etiquetasFijas.add(ETIQUETA_NCOPY);
			etiquetasFijas.add(ETIQUETA_PRINT);
			etiquetasFijas.add(ETIQUETA_NLIPA);
			etiquetasFijas.add(ETIQUETA_NLISA);
			etiquetasFijas.add(ETIQUETA_NLIPP);
			etiquetasFijas.add(ETIQUETA_ABRIR);
			etiquetasFijas.add(ETIQUETA_GUARDAR);
			etiquetasFijas.add(ETIQUETA_SALTOPAG);
			etiquetasFijas.add(ETIQUETA_FINTABLA);
		}
	}
	
	/**
	 * Realiza el merge después de la ejecución de un proceso batch.
	 * @param args Código de ejecución es argumento único.
	 * @param context 
	 * @throws GadirServiceException
	 */
	public static void mainBatch(final String[] args, final ApplicationContext context) throws GadirServiceException {
		iniciarVariables(false);
		ejecucionBO = (EjecucionBO) context.getBean("ejecucionBO");
		procesoAccionBO = (ProcesoAccionBO) context.getBean("procesoAccionBO");
		informeBO = (InformeBO) context.getBean("informeBO");
		documentoBO = (DocumentoBO) context.getBean("documentoBO");
		mainComun(args, true, false);
	}
	
	/**
	 * Realiza el merge online después de la ejecución de un proceso batch.
	 * @param args
	 * @throws GadirServiceException
	 */
	public static void mainOnline(final String[] args) throws GadirServiceException {
		iniciarVariables(true);
		ejecucionBO = (EjecucionBO) GadirConfig.getBean("ejecucionBO");
		procesoAccionBO = (ProcesoAccionBO) GadirConfig.getBean("procesoAccionBO");
		informeBO = (InformeBO) GadirConfig.getBean("informeBO");
		documentoBO = (DocumentoBO) GadirConfig.getBean("documentoBO");
		mainComun(args, false, true);
	}
	
	private static void mainComun(final String[] args, final boolean isBatch, final boolean isServidorAplicaciones) throws GadirServiceException {
		String coEjecucion = "0";
		String impresora = "";
		final List<String> listaTxt = new ArrayList<String>();
		final List<String> listaOdt = new ArrayList<String>();
		final List<String> listaCoPlantilla = new ArrayList<String>();
		for (int i=0; i<args.length; i++) {
			doLog("Parámetro " + i + ": " + args[i], null, false);
			switch (i) {
			case 0:
				coEjecucion = args[i];
				break;
			case 1:
				impresora = args[i];
				break;
			default:
				if (i%3 == 2) {
					listaTxt.add(args[i]);
				} else if (i%3 == 0) {
					listaOdt.add(args[i]);
				} else if (i%3 == 1) {
					listaCoPlantilla.add(args[i]);
				}
				break;
			}
		}
		mergeEjecucion(Integer.parseInt(coEjecucion), impresora, listaTxt, listaOdt, listaCoPlantilla, isBatch, isServidorAplicaciones);
	}

	static private void mergeEjecucion(final int coEjecucion, final String impresora, final List<String> listaTxt, final List<String> listaOdt, final List<String> listaCoPlantilla, final boolean isBatch, final boolean isServidorAplicaciones) throws GadirServiceException {
		EjecucionDTO ejecucionDTO = ejecucionBO.findByCoEjecucionInitialized(coEjecucion);
		final String usuario = ejecucionDTO.getCoAcmUsuario();
		final String rutaPlantillas = GadirConfig.leerParametro("ruta.informes.plantillas");
		final EjecucionDTO ejecucionEstDTO = new EjecucionDTO();
		//AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findByIdInitialized(usuario, new String[]{"codigoterritorialDTO"});
		ejecucionEstDTO.setCoAcmUsuario(usuario);
		ejecucionEstDTO.setCoEjecucion(ejecucionDTO.getCoEjecucion());
		ejecucionEstDTO.setCoUsuarioActualizacion(usuario);
		ejecucionEstDTO.setProcesoDTO(ejecucionDTO.getProcesoDTO());
		
		// El merge se realiza a partir de los parámetros del main
		final List<ImpresionInformeVO> listaInformes = new ArrayList<ImpresionInformeVO>();
		doLog("Realizo merge para " + listaTxt.size() + " t2p", null, false);
		for (int i=0; i<listaTxt.size(); i++) {
			String nombreOdt = null;
			String nombreTxt = null;
			Long coPlantilla = null;
			try {
				nombreOdt = rutaPlantillas + listaOdt.get(i);
				nombreTxt = listaTxt.get(i);
				coPlantilla = Long.decode(listaCoPlantilla.get(i));
				listaInformes.addAll(mergeTxt(nombreOdt, nombreTxt, ejecucionDTO, coPlantilla, i + 1, isBatch));
			} catch (FileNotFoundException e) {
				doLog("No encuentra el fichero: " + nombreOdt + ", " + nombreTxt, e, false);
//				errorEnMerge(ejecucionDTO.getCoTerminacion(), ejecucionEstDTO, e, false);
			} catch (Exception e) {
				doLog("Fallo en merge: " + nombreOdt + ", " + nombreTxt, e, true);
				errorEnMerge(MensajeConstants.ERROR_EN_MERGE, ejecucionEstDTO, e, true);
			}
		}
		doLog("Merge finalizado.", null, false);
		
		
		if (listaInformes.isEmpty()) {
			doLog("No hay nada que imprimir.", null, false);
		} else {
			ejecucionDTO = ejecucionBO.findById(Long.valueOf(coEjecucion));
			Batch.setEstado(coEjecucion, ElementoGtConstants.ELEMENTO_GT_ESTEJECU_IMPRIMIENDO.charAt(0), ejecucionDTO.getCoTerminacion(), usuario, "Informes enviados a la impresora " + impresora + ": " + listaInformes.size());
			imprimirInformes(listaInformes, impresora, ejecucionEstDTO, isServidorAplicaciones);
		}
	}
	
	private static void imprimirInformes(final List<ImpresionInformeVO> listaInformes, final String impresora, final EjecucionDTO ejecucionEstDTO, final boolean isServidorAplicaciones) {
		doLog("Imprimir " + listaInformes.size() + " informes en la impresora " + impresora, null, false);
		for (ImpresionInformeVO impresionInformeVO : listaInformes) {
			try {
				imprimirPdf(impresionInformeVO.getPdf(), impresora, impresionInformeVO.getCopias(), isServidorAplicaciones);
			} catch (GadirServiceException e) {
				doLog("Fallo en impresión: " + impresionInformeVO.getPdf() + ", " + impresora + ", " + impresionInformeVO.getCopias(), e, true);
				errorEnMerge(MensajeConstants.ERROR_EN_MERGE_IMPRESION, ejecucionEstDTO, e, true);
			}
		}
	}
	
	private static void errorEnMerge(final int error, final EjecucionDTO ejecucionEstDTO, final Exception exception, final boolean isError) {
		if (isError) {
			System.err.println("detenerScriptCMD:1");
		}
		String mensaje = null;
		if (!Utilidades.isEmpty(exception.getMessage())) {
			mensaje = exception.getMessage();
		}
		Batch.setEstado(ejecucionEstDTO.getCoEjecucion().intValue(), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), error, ejecucionEstDTO.getCoAcmUsuario(), mensaje);
	}

	static private List<ImpresionInformeVO> mergeTxt(
			final String plantillaOO, 
			final String nombreTxt, 
			final EjecucionDTO ejecucionDTO,
			final Long coPlantilla,
			final int ordenTxt,
			final boolean isBatch) throws FileNotFoundException, IOException, GadirServiceException {
		ImpresionParametroVO auxiliar = getAuxiliar();

		final String rutaTemporal = calcularSubcarpetaTemporal(auxiliar);
		final String rutaCarpetaTxt = GadirConfig.leerParametro("ruta.informes.merge");
		String rutaTxt = rutaCarpetaTxt + nombreTxt;

		try {
			// Mover el fichero a su carpeta temporal
			Fichero.mover(rutaTxt, rutaTemporal);
		} catch (GadirServiceException e) {
			// No ha podido mover, intentamos copiar (¿Sigue abierto en el origen?)
			Fichero.copiar(rutaTxt, rutaTemporal + nombreTxt);
			new File(rutaTxt).delete(); // Intentamos borrar
		}
		rutaTxt = rutaTemporal + nombreTxt; // Apuntamos hacia la carpeta temporal en lugar de GadirNFS

		auxiliar.setEjecucionDTO(ejecucionDTO);
		auxiliar.setOrdenTxt(ordenTxt);
		auxiliar.setBatch(isBatch);
		
		if (plantillaOO.toLowerCase().endsWith(".pdf")) {
			doLog("Utilizando PDFBoxService con plantilla PDF " + plantillaOO, null, false);
			List<ImpresionInformeVO> informesImprimir = PDFBoxService.procesarT2p(rutaTxt, plantillaOO, ordenTxt, auxiliar, coPlantilla);
			auxiliar.setInformesImprimir(informesImprimir);
		} else {
			final File file = new File(rutaTxt);
			String line;
			int lineasTotal = 0; 
			boolean tieneCuerpo = false;
			boolean txtVacio = true;
			boolean enCuerpo = false;
			boolean enTabla = false;
			final File fileTratado = new File(Utilidades.sustituir(rutaTxt, ".", "_tratado.", 1));

			final BufferedWriter bWriterTratado = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTratado), CODIFICACION));
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
			line = input.readLine();
			boolean despuesFinTabla=false;
			List<String> listaEtiquetasBlanqueables = new ArrayList<String>();
			while (line != null) {
				// Calculo de lineas para tratamiento NLIxx
				txtVacio = false;
				if (line.length() >= ETIQUETA_LINEA.length() && ETIQUETA_LINEA.equals(line.subSequence(0, ETIQUETA_LINEA.length()))) {
					lineasTotal++;
				} else if (!tieneCuerpo && ETIQUETA_CUERPO.equals(line)) {
					tieneCuerpo = true;
				}
				
				// Reorganizar txt, para poner etiquetas sueltas antes que tablas
				if (ETIQUETA_PIE.equals(line)) enCuerpo = false;
				if (line.length() >= ETIQUETA_LINEA.length() && ETIQUETA_LINEA.equals(line.subSequence(0, ETIQUETA_LINEA.length()))) enTabla = true;
				if (!(enCuerpo && enTabla)) {
					bWriterTratado.write(line + eol);
					if (enCuerpo && !enTabla && despuesFinTabla){
						listaEtiquetasBlanqueables.add(line.substring(0, ETIQUETA_LINEA.length()));
					}
				}
				if (ETIQUETA_CUERPO.equals(line)) enCuerpo = true;
				if (ETIQUETA_FINTABLA.equals(line)) {
					enTabla = false;
					despuesFinTabla=true;
				}
				
				line = input.readLine();
			}
			
			input.close();
			auxiliar.setLineasTotal(lineasTotal);
			
			enCuerpo = false;
			enTabla = false;
			input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
			line = input.readLine();
			while (line != null) {
				// Seguir reorganizar txt, para poner etiquetas sueltas antes que tablas
				if (ETIQUETA_PIE.equals(line)) enCuerpo = false;
				if (line.length() >= ETIQUETA_LINEA.length() && ETIQUETA_LINEA.equals(line.subSequence(0, ETIQUETA_LINEA.length()))) enTabla = true;
				if (enCuerpo && enTabla) {
					bWriterTratado.write(line + eol);
				}
				if (ETIQUETA_CUERPO.equals(line)) enCuerpo = true;
				if (ETIQUETA_FINTABLA.equals(line)) enTabla = false;
				line = input.readLine();
			}
			input.close();
			bWriterTratado.close();
			
			if (!txtVacio) {
				// Tratar etiquetas
				if (isG2R(ejecucionDTO)) {
					input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
				} else {
					input = new BufferedReader(new InputStreamReader(new FileInputStream(file), CODIFICACION));
				}
		
				if (!tieneCuerpo) { // Si el txt no tiene la etiqueta CUERPO, entendemos que se debe añadir como primera etiqetua
					auxiliar = tratarEtiqueta(ETIQUETA_CUERPO, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO, null);
				}
				
				line = input.readLine();
				while (line != null) {
					auxiliar = tratarEtiqueta(line, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO,listaEtiquetasBlanqueables);
					line = input.readLine();
				}
				input.close();
				
				auxiliar = insertarEtiquetas(auxiliar);
				// Finaliza el úlitmo informe abierto
				auxiliar = tratarEtiqueta(ETIQUETA_FIN, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO, null);
			} else {
				doLog(" ---- El t2p " + rutaTxt + " no tiene contenido.", null, false);
			}
		}
		
		return (List<ImpresionInformeVO>) auxiliar.getInformesImprimir();
	}
	
	static private void insertarFilas(final String carpetaTemporal, final Map<String, String> etiquetasTabla) throws GadirServiceException {
		final String fichero = carpetaTemporal + CONTENT_XML;
		final String ficheroTemporal = carpetaTemporal + "temp_" + CONTENT_XML;
		final File ficheroXML = new File(fichero);

		try {
			final BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroXML), CODIFICACION));
			final BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroTemporal), CODIFICACION));
	
			String sEntrada = bReader.readLine();
			while (sEntrada != null) {
				if (sEntrada.length() >= 6 && "TABLA#".equals(sEntrada.substring(0, 6))) {
					final String tabla = sEntrada.substring(6);
					if (etiquetasTabla.containsKey(tabla)) {
						final BufferedReader bReaderTabla = new BufferedReader(new InputStreamReader(new FileInputStream(carpetaTemporal + tabla + "_" + CONTENT_XML), CODIFICACION));					
						String sCadenaTabla = bReaderTabla.readLine();
						while (sCadenaTabla != null) {
							bWriter.write(sCadenaTabla + eol);
							sCadenaTabla = bReaderTabla.readLine();
						}
						bReaderTabla.close();
					}
				} else {
					bWriter.write(sEntrada + eol);
				}
				sEntrada = bReader.readLine();
			}
			
			bReader.close();
			bWriter.close();
			if (ficheroXML.delete()) {
				Fichero.copiar(ficheroTemporal, fichero);
			}
		} catch (IOException e) {
			throw new GadirServiceException(e);
		}
	}
	
	static private boolean realizarSaltoPaginaDespuesDeEstaFila(final String line, final ImpresionParametroVO auxiliar) {
		boolean result = false;
		if (auxiliar.isControlSaltoPagina()) {
			if (line.length() >= ETIQUETA_LINEA.length() && ETIQUETA_LINEA.equals(line.subSequence(0, ETIQUETA_LINEA.length()))) {
				boolean isUltimaFila = auxiliar.getLineasInsertadas() == auxiliar.getLineasTotal() - 1;
				if (isUltimaFila) {
					result = false;
				} else {
					int filasEnEstaPagina = auxiliar.getLineasPaginaActual() + 1;
					if (auxiliar.getPaginaActual() == 1) {
						// Para la primera página, comprobar con el valor de lineasPrimeraPagina.
						result = filasEnEstaPagina >= auxiliar.getLineasPrimeraPagina();
					} else {
						// Para las demás páginas, comprobar con el valor de lineasPorPagina.
						result = filasEnEstaPagina >= auxiliar.getLineasPorPagina();
						if (!result && auxiliar.isSaltarUltimaFila()) {
							// También, comprobar si es penúltima fila y hay que saltar antes de la última fila.
							boolean isPenultimaFila = auxiliar.getLineasInsertadas() == auxiliar.getLineasTotal() - 2;
							result = isPenultimaFila;
						}
					}
				}
			}
		}
		return result;
	}
	
	static private ImpresionParametroVO tratarEtiqueta(
			final String line, 
			final String rutaTemporal, 
			final ImpresionParametroVO auxiliarParam, 
			final EjecucionDTO ejecucionDTO, 
			final Long coPlantilla,
			final String plantillaOO,
			final List<String> listaEtiquetasBlanquear) throws GadirServiceException {
		ImpresionParametroVO auxiliar;
		
		// Comprobar si es necesario saltar página
		if (realizarSaltoPaginaDespuesDeEstaFila(line, auxiliarParam)) {
			auxiliar = tratarEtiqueta(ETIQUETA_SALTOPAG, rutaTemporal, auxiliarParam, ejecucionDTO, coPlantilla, plantillaOO, listaEtiquetasBlanquear);
			auxiliarParam.setLineasPaginaActual(0);
			auxiliarParam.addPaginaActual();
		} else {
			auxiliar = auxiliarParam;
		}
		
		String ubicacionEtiqueta = (String) auxiliar.getUbicacionEtiqueta();
		final String ubicacionAnterior = ubicacionEtiqueta;
		String ubicacionValor = (String) auxiliar.getUbicacionValor();
		
		final HashMap<String, String> etiquetas = (HashMap<String, String>) auxiliar.getEtiquetas();
		
		final KeyValue etiquetaValor = getEtiquetaValor(line);
		final String etiqueta = etiquetaValor.getKey();
		final String valor = etiquetaValor.getValue();
		
		// Si procede, guardar la nomenclatura del PDF para su uso al crear el PDF
		if (ETIQUETA_NOM_PDF.equals(etiqueta)) {
			auxiliar.setNombrePdf(valor);
		}
		// Si procede, guardar el código de agrupación para uso en base de datos documental
		if (ETIQUETA_BDGRU.equals(etiqueta)) {
			auxiliar.setCoBDDocumentalGrupo(valor);
		}
		// Si procede, guardar el código de agrupación para uso en base de datos documental
		if (ETIQUETA_BDEXP.equals(etiqueta)) {
			auxiliar.setCoExpediente(valor);
		}
		
		// Si procede, añade linea a cola de etiquetas para poder reutilizarlos en caso de informes fraccionados.
		if (ETIQUETA_CUERPO.equals(ubicacionEtiqueta) || ETIQUETA_CABEZA.equals(ubicacionEtiqueta)) {
			if (!ETIQUETA_CUERPO.equals(etiqueta)) {
				auxiliar.pushListaLineas(line);
			}
		}
		
		auxiliar.setEtiquetaActual(etiqueta);
		
		if (ETIQUETA_CABEZA.equals(etiqueta) || 
				ETIQUETA_PIE.equals(etiqueta) || 
				ETIQUETA_CUERPO.equals(etiqueta) || 
				ETIQUETA_LINEA.equals(etiqueta) ||
				ETIQUETA_CONFIG.equals(etiqueta) ||
				ETIQUETA_SALTOPAG.equals(etiqueta) ||
				ETIQUETA_FINTABLA.equals(etiqueta)) {
			if (!etiquetas.isEmpty()) {
				auxiliar = insertarEtiquetas(auxiliar);
				etiquetas.clear();
			}
			ubicacionEtiqueta = etiqueta;
			ubicacionValor = valor;
		} else if (!"".equals(etiqueta.trim())){
			String valorEscapado = rompeLineas(StringEscapeUtils.escapeXml(valor));
			if (isG2R(ejecucionDTO)) {
				valorEscapado = Utilidades.sustituir(valorEscapado, "&lt;text:s text:c=&quot;", "<text:s text:c=\"");
				valorEscapado = Utilidades.sustituir(valorEscapado, "&quot;/&gt;", "\"/>");
			}
			etiquetas.put(etiqueta.trim(), valorEscapado);
		}
		
		if (ETIQUETA_CUERPO.equals(etiqueta) || ETIQUETA_CABEZA.equals(etiqueta) || ETIQUETA_FIN.equals(etiqueta)) {
			if (!Utilidades.isEmpty(ubicacionAnterior) && !ETIQUETA_CONFIG.equals(ubicacionAnterior) && !ETIQUETA_CABEZA.equals(ubicacionAnterior)) {
				auxiliar = finalizarInforme(rutaTemporal, auxiliar, ejecucionDTO, coPlantilla);
			}
			if (!ETIQUETA_FIN.equals(etiqueta) && !ETIQUETA_CABEZA.equals(ubicacionAnterior)) {
				auxiliar = iniciarInforme(auxiliar, ejecucionDTO, plantillaOO);
			}
		}
		if (ETIQUETA_FINTABLA.equals(ubicacionEtiqueta)) {
			ubicacionEtiqueta = ETIQUETA_CUERPO;
		}
		auxiliar.setUbicacionEtiqueta(ubicacionEtiqueta);
		auxiliar.setUbicacionValor(ubicacionValor);
		auxiliar.setEtiquetas(etiquetas);
		
		// Forzar CUERPO nuevo si hemos superado el limite maximo de tamaño de un documento, informe fraccionado.
		if (auxiliar.getLineasInsertadas() >= LIMITE_LINEAS) {
			auxiliar.setLineasInsertadas(0);
			if (listaEtiquetasBlanquear!=null){
				auxiliar = tratarEtiqueta(ETIQUETA_FINTABLA, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO, null);
				for (String etiquetaBlanquear:listaEtiquetasBlanquear){
					auxiliar = tratarEtiqueta(etiquetaBlanquear, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO, null);
				}
			}
			
			List<String> listaLineas = auxiliar.getListaLineas();
			auxiliar = tratarEtiqueta(ETIQUETA_CUERPO, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO, null);
			while (!listaLineas.isEmpty()) {
				String etiquetaLista = listaLineas.remove(0);
				if (Utilidades.isNotEmpty(etiquetaLista)) {
					auxiliar = tratarEtiqueta(etiquetaLista, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO, null);
				}
			}
//			auxiliar = tratarEtiqueta(line, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, plantillaOO);
		}
		
		return auxiliar;
	}
	
	private static String rompeLineas(final String valor) {
		return valor.replace(ROMPE_LINEA, "<text:line-break/>");
	}

	private static boolean isG2R(final EjecucionDTO ejecucionDTO) {
		return ejecucionDTO.getCoEjecucion() == 999;
	}

	private static ImpresionParametroVO iniciarInforme(
			final ImpresionParametroVO auxiliar, 
			final EjecucionDTO ejecucionDTO, 
			final String plantillaOO) throws GadirServiceException {
		final StringBuffer orden = new StringBuffer();
		orden.append((Integer) auxiliar.getOrdenTxt());
		orden.append('_');
		final int ordenPdf = auxiliar.getOrdenPdf() + 1;
		orden.append(ordenPdf);
		final String[] rutas = iniciarMerge(plantillaOO, Long.toString(ejecucionDTO.getCoEjecucion()), ejecucionDTO, orden.toString(), auxiliar);
		final ImpresionParametroVO nuevoAuxiliar = getAuxiliar(auxiliar);
		nuevoAuxiliar.setRutas(rutas);
		nuevoAuxiliar.setOrdenPdf(ordenPdf);
		return nuevoAuxiliar;
	}


	private static ImpresionParametroVO finalizarInforme(
			final String rutaTemporal, 
			final ImpresionParametroVO auxiliar, 
			final EjecucionDTO ejecucionDTO, 
			final Long coPlantilla) throws GadirServiceException {
		final String[] rutas = (String[]) auxiliar.getRutas();
		final String carpetaTemporal = rutas[0];
		String rutaPdfFinal;
		if (Utilidades.isEmpty(auxiliar.getNombrePdf())) {
			rutaPdfFinal = rutas[1];
		} else {
			final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';
			final String carpeta = rutas[1].substring(0, rutas[1].lastIndexOf(barra) + 1);
			rutaPdfFinal = carpeta + 
					soloCaracteresSeguros(Utilidades.sustituir(auxiliar.getNombrePdf(), " ", "_"), false) + "-" + 
					auxiliar.getOrdenTxt() + "_" +
					ejecucionDTO.getCoAcmUsuario() + "_" + 
					Utilidades.dateToYYYYMMDDHHMMSS(new Date()) + "_" + auxiliar.getOrdenTxt() + "_" + auxiliar.getOrdenPdf() +
					EXTENSION_PDF;
		}

		// Insertar tablas
		final Map<String, String> etiquetasTabla = (Map<String, String>) auxiliar.getEtiquetasTabla();
		if (!etiquetasTabla.isEmpty()) {
			insertarFilas(carpetaTemporal, etiquetasTabla);
		}
		
		// Finalizar merge
		final ImpresionInformeVO informeVO = finalizarMerge(carpetaTemporal, rutaPdfFinal, rutaTemporal, ejecucionDTO, BORRAR_TEMPORALES, auxiliar, coPlantilla, ejecucionDTO.getCoAcmUsuario());
		if (informeVO != null) {
			final List<ImpresionInformeVO> listaInformes = (List<ImpresionInformeVO>) auxiliar.getInformesImprimir();
			listaInformes.add(informeVO);
			auxiliar.setInformesImprimir(listaInformes);
		}
		
		return auxiliar;
	}

	private static KeyValue getEtiquetaValor(final String line) {
		final KeyValue result = new KeyValue();
		boolean isResultado = false;
		final int lineLength = line.length();
		for (String etiquetaFija : etiquetasFijas) {
			// Línea sólo contiene etiqueta fija
			if (etiquetaFija.equals(line)) {
				result.setKey(line);
				isResultado = true;
				break;
			}
			// Línea contiene etiqueta fija + un valor
			final int etiqLength = etiquetaFija.length();
			if (lineLength > etiqLength && line.substring(0, etiqLength).equals(etiquetaFija)) {
				result.setKey(etiquetaFija);
				result.setValue(parsearLenguajillo(line.substring(etiqLength)));
				isResultado = true;
				break;
			}
		}
		if (!isResultado) {
			// Línea contiene etiqueta + un valor
			result.setKey(line.substring(0, Math.min(lineLength, 5)));
			if (lineLength > 5) {
				result.setValue(parsearLenguajillo(line.substring(5)));
			}
		}
		return result;
	}

	private static String parsearLenguajillo(String valorEtiqueta) {
		return LenguajilloUtil.parsearLenguajillo(valorEtiqueta);
	}

	static private String[] iniciarMerge(final String rutaPlantillaOO, final String carpetaPDF, final EjecucionDTO ejecucionDTO, final String orden, final ImpresionParametroVO impresionParamVO) throws GadirServiceException {
		final String rutaTemporal = calcularSubcarpetaTemporal(impresionParamVO);
		final String rutaGenerados = GadirConfig.leerParametro("ruta.informes.generados");
		final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';

		String[] rutas = new String[2];
		rutas[0] = rutaTemporal + 'E' + ejecucionDTO.getCoEjecucion() + '_' + System.currentTimeMillis() + rutaPlantillaOO.hashCode() + "_" + orden + barra;
		rutas[1] = rutaGenerados + carpetaPDF + barra + 
			ejecucionDTO.getCoEjecucion() + "_" + 
			orden + "_" +
			rutaPlantillaOO.substring(rutaPlantillaOO.lastIndexOf(barra) + 1, rutaPlantillaOO.lastIndexOf('.')) + "_" + 
			ejecucionDTO.getCoAcmUsuario() + "_" + 
			Utilidades.dateToYYYYMMDDHHMMSS(new Date()) +
			EXTENSION_PDF;
		rutas[1] = soloCaracteresSeguros(Utilidades.sustituir(rutas[1], " ", "_"), true);

		try {
			Fichero.descomprimir(rutaPlantillaOO, rutas[0]);
		} catch (FileNotFoundException e) {
			throw new GadirServiceException("No existe el ODT " + rutaPlantillaOO, e);
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		añadirConBreak(rutas[0]);
		insertarEtiquetasFijas(ejecucionDTO, rutas[0], impresionParamVO);
		return rutas;
	}

	private static void añadirConBreak(final String carpetaTemporal) throws GadirServiceException {
		try {
			final String fichero = carpetaTemporal + CONTENT_XML;
			final String ficheroTemporal = carpetaTemporal + "break_" + CONTENT_XML;
			final File ficheroXML = new File(fichero);
			final BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroXML), CODIFICACION));
			final BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroTemporal), CODIFICACION));
			
			String sEntrada = bReader.readLine();
			while (sEntrada != null) {
				if (sEntrada.indexOf(ODT_ESTILO_TABLA) > 0) {
					final String[] cachos = sEntrada.split(ODT_ESTILO_TABLA);
					for (int i=0; i<cachos.length-1; i++) {
						int pos = cachos[i].lastIndexOf('>');
						final String antes = cachos[i].substring(0, pos);
						final String despues = cachos[i].substring(pos + 1);
						bWriter.write(antes + '>');
						final StringBuffer nuevoEstilo = new StringBuffer(despues);
						nuevoEstilo.append(ODT_ESTILO_TABLA);
						
						pos = cachos[i+1].indexOf(ODT_FIN_ESTILO);
						final String antes2 = cachos[i+1].substring(0, pos);
						nuevoEstilo.append(antes2);
						if (nuevoEstilo.indexOf(ODT_ESTILO_BREAK) == -1) {
							nuevoEstilo.append(ODT_ESTILO_BREAK_PAGE);
						}
						nuevoEstilo.append(ODT_FIN_ESTILO);
						
						final String nuevoEstiloString = Utilidades.sustituir(nuevoEstilo.toString(), "style:name=\"", "style:name=\"" + TABLA_BREAK);
						
						bWriter.write(nuevoEstiloString);
						bWriter.write(despues);
						bWriter.write(ODT_ESTILO_TABLA);
					}
					bWriter.write(cachos[cachos.length-1]);
				} else {
					bWriter.write(sEntrada + eol);
				}
				sEntrada = bReader.readLine();
			}
			
			bReader.close();
			bWriter.close();
			
			if (ficheroXML.delete()) {
				Fichero.copiar(ficheroTemporal, fichero);
			}

		} catch (UnsupportedEncodingException e) {
			throw new GadirServiceException(e);
		} catch (FileNotFoundException e) {
			throw new GadirServiceException(e);
		} catch (IOException e) {
			throw new GadirServiceException(e);
		}
	}

	static private void insertarEtiquetasFijas(final EjecucionDTO ejecucionDTO, final String carpetaTemporal, final ImpresionParametroVO impresionParamVO) throws GadirServiceException {
		final Locale localeEs = new Locale("es", "ES");
		final DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", localeEs);
		final DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss", localeEs);
		final java.util.Date date = new java.util.Date();
		
		AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findByIdInitialized(ejecucionDTO.getCoAcmUsuario(), new String[]{"codigoTerritorialDTO"});
		
		final String coCodter = acmUsuarioDTO.getCodigoTerritorialDTO().getCoCodigoTerritorial();
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(ETIQUETA_ORGANO, TablaGt.getValor(TablaGtConstants.TABLA_ORGANO_PRIMER_NIVEL, coCodter.substring(0,2), "DESCRIPCION"));
		hashMap.put(ETIQUETA_AMBITO, TablaGt.getValor(TablaGtConstants.TABLA_AMBITO_TERRITORIAL, coCodter.substring(2,4), "DESCRIPCION"));
		hashMap.put(ETIQUETA_USUARIO, ejecucionDTO.getCoAcmUsuario() + getEntorno());
		hashMap.put(ETIQUETA_FECHA, formatoFecha.format(date));
		hashMap.put(ETIQUETA_HORA, formatoHora.format(date));
		hashMap.put(ETIQUETA_ESCUDO, getNombreImagenEscudo(coCodter));
		tratarEtiquetas(hashMap, ETIQUETA_CABEZA, carpetaTemporal, impresionParamVO);
		hashMap = new HashMap<String, String>();
		hashMap.put(ETIQUETA_EJECUCIO, Long.toString(ejecucionDTO.getCoEjecucion()));
		hashMap.put(ETIQUETA_ORDEN, Integer.toString(impresionParamVO.getOrdenPdf()+1));
		tratarEtiquetas(hashMap, ETIQUETA_CUERPO, carpetaTemporal, impresionParamVO);
	}

	private static String getEntorno() {
		String entorno = GadirConfig.leerParametro("entorno.servidor");
		String result = "";
		if (!"produccion".equals(entorno)) {
			if (entorno.length() > 5) {
				entorno = entorno.substring(0, 5);
			}
			result += " (" + entorno + ")";
		}
		return result;
	}

	private static String getNombreImagenEscudo(String coCodter) {
		final String rutaImagenes = GadirConfig.leerParametro("ruta.informes.imagenes");
		String nombreImagen = coCodter + ".jpg";
		File imagen = new File(rutaImagenes + nombreImagen);
		if (!imagen.exists()) {
			if (nombreImagen.startsWith("RE") || nombreImagen.startsWith("GT") || nombreImagen.startsWith("UT")) {
				nombreImagen = "RE0000.jpg";
			} else {
				nombreImagen = "ET9999.jpg";
			}
		}
		return nombreImagen;
	}

	static private ImpresionParametroVO insertarEtiquetas(final ImpresionParametroVO auxiliar2) throws GadirServiceException {
		ImpresionParametroVO auxiliar = auxiliar2;
		final String ubicacion = (String)auxiliar.getUbicacionEtiqueta();
		final HashMap<String, String> etiquetas = (HashMap<String, String>) auxiliar.getEtiquetas();
		
		if (!"".equals(ubicacion) && !etiquetas.isEmpty()) {
			if (ETIQUETA_LINEA.equals(ubicacion)) {
				auxiliar = etiquetasFila(etiquetas, auxiliar);
			} else if (ETIQUETA_CONFIG.equals(ubicacion)) {
				auxiliar.setEtiquetasConfig(etiquetas);
			} else {
				tratarEtiquetas(etiquetas, ubicacion, auxiliar.getRutas()[0], auxiliar);
			}
		}
		return auxiliar;
	}

	static private String sustituirEtiquetas(final String entrada, final Map<String, String> etiquetas, final String carpetaTemporal, final ImpresionParametroVO impresionParamVO) throws GadirServiceException {
		String sCadena = entrada;
		for (final Iterator<Map.Entry<String, String>> i = etiquetas.entrySet().iterator(); i.hasNext();) {
			final Map.Entry<String, String> etiqueta = i.next();
			final String eti = ETIQUETA_CHAR + etiqueta.getKey() + ETIQUETA_CHAR;
			if (sCadena.contains(eti)) {
				if (eti.matches(ETIQUETA_CHAR + EXPRESION_IMAGEN + ETIQUETA_CHAR)) {
					if (etiqueta.getValue().trim().length() == 0) {
//						Eliminando esto, la ruta de la imagen puede venir en blanco en caso de romper pdf y la imagen tiene que estar solamente en el último.
//						registrarObservaciones("Etiqueta " + eti + " sin ruta de imagen.", impresionParamVO);
						sCadena = Utilidades.sustituir(sCadena, eti, "");
					} else {
						impresionParamVO.pushItemManifest(etiqueta.getValue()); // Añade el valor de la etiqueta a la lista de imagenes para luego incluir en el manifest antes de la creación del ODT.
						sCadena = Utilidades.sustituir(sCadena, eti, insertarImagen(etiqueta, carpetaTemporal, eti.startsWith(ETIQUETA_CHAR + "RUT"), impresionParamVO));
					}
				} else {
					sCadena = Utilidades.sustituir(sCadena, eti, trataEspacios(etiqueta.getValue()));
				}
			}
		}
		return sCadena;
	}
	
	static private String trataEspacios(final String valorEtiqueta) {
		StringBuffer result = new StringBuffer();
		StringBuffer spaceBuffer = new StringBuffer();
		boolean hayAlgo = false;
		for (int i = 0; i<valorEtiqueta.length(); i++) {
			char thisChar = valorEtiqueta.charAt(i);
			if (thisChar == ' ') {
				spaceBuffer.append(' ');
			} else {
				result.append(trataSpaceBuffer(spaceBuffer, hayAlgo)).append(thisChar);
				spaceBuffer.setLength(0);
				hayAlgo = true;
			}
		}
		result.append(trataSpaceBuffer(spaceBuffer, hayAlgo));
		return result.toString();
	}
	static private String trataSpaceBuffer(final StringBuffer spaceBuffer, final boolean hayAlgo) {
		String result;
		if (spaceBuffer.length() > 1) {
			if (hayAlgo) {
				result = " <text:s text:c=\"" + (spaceBuffer.length() - 1) + "\"/>";
			} else {
				result = "<text:s text:c=\"" + spaceBuffer.length() + "\"/>";
			}
		} else if (spaceBuffer.length() == 1) {
			result = " ";
		} else {
			result = "";
		}
		return result;
	}

//	private static void registrarObservaciones(final String observaciones, final ImpresionParametroVO impresionParamVO) {
//		doLog(observaciones, null, false);
//		Batch.setEstado(
//				impresionParamVO.getEjecucionDTO().getCoEjecucion().intValue(), 
//				ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), 
//				MensajeConstants.ADVERTENCIA_EN_MERGE,
//				impresionParamVO.getEjecucionDTO().getCoAcmUsuario(), 
//				observaciones
//		);
//	}

	private static String insertarImagen(final Entry<String, String> etiquetaImagen, final String carpetaTemporal, final boolean isFirma, ImpresionParametroVO impresionParamVO) throws GadirServiceException {
		final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';
		final String nombreImagen = etiquetaImagen.getValue();
		String rutaImagen;
		if (isFirma) {
			rutaImagen = GadirConfig.leerParametro("ruta.informes.firmas") + nombreImagen;
		} else {
			rutaImagen = GadirConfig.leerParametro("ruta.informes.imagenes") + nombreImagen;
		}
		final String nombreEtiqueta = etiquetaImagen.getKey();
		
		double anchoCm;
		double altoCm;
		
		final ImageIcon imageIcon = new ImageIcon(rutaImagen);
		final int anchoPx = imageIcon.getIconWidth();
		final int altoPx = imageIcon.getIconHeight();
		final double divisorX = 118.1102362; // Corresponde a 300 px por pulgada
		final double divisorY = 118.1102362; // Posible mejora: Investigar imagen para calcular los divisores según su DPI.
		anchoCm = anchoPx / divisorX;
		altoCm = altoPx / divisorY;
		
		Fichero.crearCarpeta(carpetaTemporal + "Pictures" + barra);
		try {
			Fichero.copiar(rutaImagen, carpetaTemporal + "Pictures" + barra + nombreImagen);
		} catch (IOException e) {
			throw new GadirServiceException("No puede copiar imagen: " + rutaImagen + ", " + carpetaTemporal + "Pictures" + barra + nombreImagen, e);
		}
		
		return "<draw:frame draw:name=\"" + nombreEtiqueta + "\" text:anchor-type=\"paragraph\" svg:width=\"" + anchoCm + "cm\" svg:height=\"" + altoCm + "cm\" draw:z-index=\"100\"><draw:image xlink:href=\"Pictures/" + nombreImagen + "\" xlink:type=\"simple\" xlink:show=\"embed\" xlink:actuate=\"onLoad\"/><svg:desc>Imagen: " + nombreEtiqueta + ", " + nombreImagen + "</svg:desc></draw:frame>";
	}

	static private String quitarEtiquetasSobrantes(final String sCadena) {
		final StringBuffer result = new StringBuffer();
		final StringBuffer descartar = new StringBuffer();
		boolean incluir = true;
		for (int i = 0; i<sCadena.length(); i++) {
			final char charActual = sCadena.charAt(i);
			if (charActual == ETIQUETA_CHAR) {
				if (incluir) {
					incluir = false;
					descartar.setLength(0);
				} else {
					incluir = true;
					if (descartar.length() > 5 || descartar.indexOf(";") != -1) {
						result.append(ETIQUETA_CHAR).append(descartar).append(ETIQUETA_CHAR);
						descartar.setLength(0);
					}
				}
			} else {
				if (incluir) {
					result.append(charActual);
				} else {
					descartar.append(charActual);
				}
			}
			if (descartar.length() > 5 || descartar.indexOf(";") != -1) {
				result.append(ETIQUETA_CHAR).append(descartar);
				descartar.setLength(0);
				incluir = true;
			}
		}
		if (!incluir && descartar.length() > 5) {
			result.append(ETIQUETA_CHAR).append(descartar);
		}
		return result.toString();
	}
	

	static private ImpresionParametroVO etiquetasFila(final Map<String, String> etiquetas, final ImpresionParametroVO auxiliar2) throws GadirServiceException {
		final ImpresionParametroVO auxiliar = auxiliar2;
		final String[] rutas = (String[]) auxiliar.getRutas();
		final String carpetaTemporal = rutas[0];
		final String fichero = carpetaTemporal + CONTENT_XML;

		String nombreTabla;
		final String tabla = (String) auxiliar.getUbicacionValor();
		String tablaUnica = (String) auxiliar.getTablaUnica();
		if (tabla == null || "".equals(tabla)) {
			// Buscar nombre de tabla unica en plantilla odt
			if (tablaUnica == null) {
				try {
					final BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), CODIFICACION));
					String sCadena = bReader.readLine();
					String[] sCadenas, sCadenas2;
					while (sCadena != null) {
						if (sCadena.indexOf(ODT_NOMBRE_TABLA) > 0) {
							sCadenas = sCadena.split(ODT_NOMBRE_TABLA, 2);
							if (sCadenas.length == 2 && sCadenas[1] != null && !"".equals(sCadenas[1])) {
								sCadenas2 = sCadenas[1].split("\"", 2);
								if (sCadenas2.length == 2 && sCadenas2[0] != null && !"".equals(sCadenas2[0])) {
									tablaUnica = sCadenas2[0];
									break;
								}
							}
						}
						sCadena  = bReader.readLine();
					}
					bReader.close();
				} catch (IOException e) {
					throw new GadirServiceException(e);
				}
				if (tablaUnica == null) {
					throw new GadirServiceException("La plantilla indicada no tiene tabla.");
				}
			}
			nombreTabla = tablaUnica;
		} else {
			nombreTabla = tabla;
		}
		
		final HashMap<String, String> etiquetasTabla = (HashMap<String, String>) auxiliar.getEtiquetasTabla();
		try {
			if (!etiquetasTabla.containsKey(nombreTabla)) {
				final BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), CODIFICACION));
				final StringBuffer filaConEtiquetas = new StringBuffer();
				final StringBuffer filaDespues = new StringBuffer();
				final StringBuffer filaAntes = new StringBuffer();
				String division = ODT_NOMBRE_TABLA + nombreTabla + "\" table:style-name=\"" + nombreTabla + "\"";
				String sCadena = bReader.readLine();
				while (sCadena != null) {
					if (filaConEtiquetas.length() == 0) {
						if (sCadena.indexOf(division) > 0) {
							String[] sCadenas = null;
							sCadenas = sCadena.split(division, 2);
							filaAntes.append(sCadenas[0] + division);
							filaDespues.append(sCadenas[1]);

							// Si contiene filas de cabecera, buscarlas y añadirlas al "antes". También se guarda en auxiliar.
							division = "</table:table-header-rows>";
							if (sCadenas[1].contains(division)) {
								sCadenas = filaDespues.toString().split(division, 2);
								filaAntes.append(sCadenas[0] + division);
								final String cabezaTabla = parsearCabezaTabla(filaAntes.toString());
								auxiliar.putSaltoTabla(nombreTabla, cabezaTabla);
								auxiliar.putNumCeldas(nombreTabla, Integer.valueOf(contarCeldasFila(cabezaTabla)));
								filaDespues.replace(0, filaDespues.length(), sCadenas[1]);
							} else {
								// Si no contiene filas de cabecera, añadir la definición de las columnas en auxiliar pero dejando filaAntes/filaDespues intactos.
								division = "<table:table-row";
								sCadenas = filaDespues.toString().split(division, 2);
								final String cabezaTabla = parsearCabezaTabla(filaAntes.toString() + sCadenas[0]);
								auxiliar.putSaltoTabla(nombreTabla, cabezaTabla);
								auxiliar.putNumCeldas(nombreTabla, -1);
							}
							
							division = "<table:table-row";
							sCadenas = filaDespues.toString().split(division, 2);
							filaAntes.append(sCadenas[0]);
							filaDespues.replace(0, filaDespues.length(), division + sCadenas[1]);
			
							division = "<table:table-cell";
							sCadenas = filaDespues.toString().split(division, 2);
							filaConEtiquetas.append(sCadenas[0]);
							filaDespues.replace(0, filaDespues.length(), division + sCadenas[1]);
			
							division = "</table:table>";
							sCadenas = filaDespues.toString().split(division, 2);
							filaConEtiquetas.append(sCadenas[0]);
							filaDespues.replace(0, filaDespues.length(), division + sCadenas[1] + eol);
						} else {
							filaAntes.append(sCadena);
							filaAntes.append(eol);
						}
					} else {
						filaDespues.append(sCadena);
						filaDespues.append(eol);
					}
					sCadena = bReader.readLine();
				}
				bReader.close();
				etiquetasTabla.put(nombreTabla, filaConEtiquetas.toString());
				
				final BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), CODIFICACION));
				bWriter.write(filaAntes + eol + "TABLA#" + nombreTabla + eol + filaDespues);
				bWriter.close();
			}

			final String ficheroTemporal = carpetaTemporal + nombreTabla + "_" + CONTENT_XML;
			final BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroTemporal, true), CODIFICACION));
			final String fila = quitarFilasSobrantes(
					quitarEtiquetasSobrantes(
							sustituirEtiquetas(etiquetasTabla.get(nombreTabla), etiquetas, carpetaTemporal, auxiliar)
					), nombreTabla, auxiliar);
			bWriter.append(fila + eol);
			if (ETIQUETA_SALTOPAG.equals(auxiliar.getEtiquetaActual())) {
				final String cabezaTabla = (String) auxiliar.getSaltoTabla(nombreTabla);
				bWriter.append(cabezaTabla);
			}
			bWriter.close();
		} catch (IOException e) {
			throw new GadirServiceException(e);
		}
		
		auxiliar.setEtiquetasTabla(etiquetasTabla);
		auxiliar.addLineasPaginaActual();
		auxiliar.addLineasInsertadas();
		return auxiliar;
	}

	private static int contarCeldasFila(final String cabezaTabla) {
		return cabezaTabla.split("</table:table-row>")[0].split("</table:table-cell").length - 1;
	}

	private static String quitarFilasSobrantes(final String sCadena, final String nombreTabla, final ImpresionParametroVO impresionParamVO) {
		final StringBuffer result = new StringBuffer(sCadena.length());
		final String[] filas = sCadena.split("</table:table-row>");
		for (int i=0; i<filas.length; i++) {
			final String[] celdas = filas[i].split("</table:table-cell");
			boolean incluir;
			if (Utilidades.isNull(impresionParamVO.getNumCeldas(nombreTabla)) || 
					impresionParamVO.getNumCeldas(nombreTabla).intValue() == celdas.length - 1) {
				incluir = true;
			} else {
				incluir = false;
			}
			cells: for (int j=0; j<celdas.length && !incluir; j++) {
				boolean esTag = false;
				for (int k=0; k<celdas[j].length(); k++) {
					final char cha = celdas[j].charAt(k);
					if (cha == ' ') continue;
					if (cha == '<') {
						esTag = true;
					} else if (cha == '>') {
						esTag = false;
					} else if (!esTag) {
						incluir = true;
						break cells;
					}
				}
			}
			if (incluir) {
				result.append(filas[i]).append("</table:table-row>");
			}
		}
		return result.toString();
	}

	private static String parsearCabezaTabla(final String sCadena) throws GadirServiceException {
		String result = "";
		final String busco = ODT_NOMBRE_TABLA;
		final int pos = sCadena.lastIndexOf(busco);
		if (pos > -1) {
			result = sCadena.substring(pos);
			result = Utilidades.sustituir(result, ODT_TABLE_NAME, ODT_TABLE_NAME + TABLA_BREAK);
			result = Utilidades.sustituir(result, ODT_ESTILO_NAME, ODT_ESTILO_NAME + TABLA_BREAK, 1);
		} else {
			throw new GadirServiceException("No encuentra inicio de tabla");
		}
		return "</table:table>" + result;
	}

	static private void tratarEtiquetas(final Map<String, String> etiquetas, final String ubicacion, final String carpetaTemporal, final ImpresionParametroVO impresionParamVO) throws GadirServiceException {
		final List<String> listaFichero = new ArrayList<String>(2);
		final List<String> listaFicheroTemp = new ArrayList<String>(2);
		
		listaFichero.add(carpetaTemporal + "styles.xml");
		listaFicheroTemp.add(carpetaTemporal + "temp_styles.xml");

		if (!(ETIQUETA_CABEZA.equals(ubicacion) || ETIQUETA_PIE.equals(ubicacion))) {
			listaFichero.add(carpetaTemporal + CONTENT_XML);
			listaFicheroTemp.add(carpetaTemporal + "temp_" + CONTENT_XML);
		}
		
		try {
			for (int i=0; i<listaFichero.size(); i++) {
				final File ficheroXML = new File(listaFichero.get(i));
				final BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroXML), CODIFICACION));
				final BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(listaFicheroTemp.get(i)), CODIFICACION));
		
				String sCadena = bReader.readLine();
				while (sCadena != null) {
					bWriter.write(sustituirEtiquetas(sCadena, etiquetas, carpetaTemporal, impresionParamVO) + eol);
					sCadena = bReader.readLine();
				}
				
				bReader.close();
				bWriter.close();
				if (ficheroXML.delete()) {
					Fichero.copiar(listaFicheroTemp.get(i), listaFichero.get(i));
				}
			}
		} catch (IOException e) {
			throw new GadirServiceException(e);
		}
	}
	
	static private ImpresionInformeVO finalizarMerge(
			final String carpetaTemporal, 
			String rutaPdfFinal, 
			final String rutaTemporal, 
			final EjecucionDTO ejecucionDTO, 
			final boolean borrar, 
			final ImpresionParametroVO auxiliar,
			final Long coPlantilla,
			final String coAcmUsuario) throws GadirServiceException {
		ImpresionInformeVO informeVO = null;
		final String rutaOdtFinal = rutaTemporal + 'E' + ejecucionDTO.getCoEjecucion() + '_' + System.currentTimeMillis() + ".odt";
		boolean isInformeOdt = auxiliar.getEtiquetasConfig().containsKey(ETIQUETA_INODT) && "S".equals(auxiliar.getEtiquetasConfig().get(ETIQUETA_INODT));

		try {
			incluirImagenesManifest(carpetaTemporal, auxiliar.getListaItemsManifest());
			auxiliar.setListaItemsManifest(new ArrayList<String>()); // Imagenes ya incluidas en manifest, resetear lista. 
			Fichero.comprimirOdt(carpetaTemporal, rutaOdtFinal, borrar);
			if (isInformeOdt) {
				rutaPdfFinal = rutaPdfFinal.replace(EXTENSION_PDF, ".odt");
				Fichero.copiar(rutaOdtFinal, rutaPdfFinal);
			} else {
				crearPDF(rutaOdtFinal, rutaPdfFinal, borrar, utilizarWs(auxiliar.isBatch()));
			}
			informeVO = realizarActuaciones(rutaPdfFinal, auxiliar, coAcmUsuario);
			altaInforme(ejecucionDTO, rutaPdfFinal, coPlantilla, coAcmUsuario);
		} catch (IOException e) {
			doLog(e.getMessage(), e, true);
			throw new GadirServiceException(e);
		}
		
		return informeVO;
	}
	
	private static void incluirImagenesManifest(final String carpetaTemporal, final List<String> listaItemsManifest) {
		if (!listaItemsManifest.isEmpty()) {
			// Eliminar duplicados.
			HashSet<String> hashSet = new HashSet<String>();
			hashSet.addAll(listaItemsManifest);
			listaItemsManifest.clear();
			listaItemsManifest.addAll(hashSet);
			
			final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';
			final String fileName = carpetaTemporal + "META-INF" + barra + "manifest.xml";
	        List<String> lines = new ArrayList<String>();
	        try {
				FileReader fileReader = new FileReader(fileName);
		        BufferedReader bufferedReader = new BufferedReader(fileReader);
		        String line = null;
		        while ((line = bufferedReader.readLine()) != null) {
		            if ("</manifest:manifest>".equals(line)) {
		            	for (String itemManifest : listaItemsManifest) {
		            		lines.add(" <manifest:file-entry manifest:media-type=\"" + URLConnection.getFileNameMap().getContentTypeFor(itemManifest) + "\" manifest:full-path=\"Pictures/" + itemManifest + "\"/>");
		            	}
		            }
		        	lines.add(line);
		        }
				bufferedReader.close();
			} catch (IOException e) {
				doLog("Error leyendo manifest.xml", e, true);
			}
	        
			BufferedWriter out;
			try {
				File file = new File(fileName);
			    out = new BufferedWriter(new FileWriter(file));
			    for (String line : lines) {
					out.write(line);
					out.newLine();
			    }
			    out.close();
			} catch(IOException e) {
				doLog("Error escribiendo manifest.xml", e, true);
			}
		}
	}

	static public void altaInforme(
			final EjecucionDTO ejecucionDTO, 
			final String rutaPdf, 
			final Long coPlantilla, 
			final String coAcmUsuario) throws GadirServiceException {
		final InformeDTO informeDTO = new InformeDTO();
		informeDTO.setCoUsuarioActualizacion(coAcmUsuario);
		if (ejecucionDTO.getCoEjecucion() != 0) {
			informeDTO.setEjecucionDTO(ejecucionDTO);
			informeDTO.setCoAcmUsuario(ejecucionDTO.getCoAcmUsuario());
		}
		else{
			informeDTO.setCoAcmUsuario(coAcmUsuario);
		}
		informeDTO.setFhActualizacion(new Date());
		informeDTO.setCoPlantilla(coPlantilla);
		informeDTO.setRutaPdf(rutaPdf);
		informeBO.save(informeDTO);
	}
	
	static public void crearPDF(final String inputODT, final String outputPDF, final boolean borrar, final boolean utilizarWs) throws ConnectException, GadirServiceException {
		if (utilizarWs) {
			crearPDFWs(inputODT, outputPDF, borrar, "url.webservice.openoffice");
		} else {
			boolean utilizarWsLocal = "webservicelocal".equals(GadirConfig.leerParametro("metodo.crear.pdf.online"));
			if (utilizarWsLocal) {
				crearPDFWs(inputODT, outputPDF, borrar, "url.webservicelocal.openoffice");
			} else {
				crearPDFLocal(inputODT, outputPDF, borrar);
			}
		}
	}
	
	/**
	 * Decide, según properties, si el informe debe ir por webservice o por servidor de aplicaciones.
	 * @param isBatch
	 * @return
	 */
	static private boolean utilizarWs(final boolean isBatch) {
		boolean resultado;
		doLog("metodo.crear.pdf.online: " + GadirConfig.leerParametro("metodo.crear.pdf.online"), null, false);
		if (isBatch) {
			resultado = true;
		} else {
			resultado = "webservice".equals(GadirConfig.leerParametro("metodo.crear.pdf.online"));
		}
		return resultado;
	}
	
	/**
	 * Convertir ODT a PDF en local.
	 * @param inputODT
	 * @param outputPDF
	 * @param borrar
	 * @throws GadirServiceException
	 */
	static private void crearPDFLocal(final String inputODT, final String outputPDF, final boolean borrar) throws GadirServiceException {
		File inputFile = new File(inputODT);
		File outputFile = new File(outputPDF);
		OfficeDocumentConverter converter = JodconverterService.getDocumentConverter();
		String outputExtension = FilenameUtils.getExtension(outputPDF);
        String inputExtension = FilenameUtils.getExtension(inputODT);

        try {
        	long startTime = System.currentTimeMillis();
        	converter.convert(inputFile, outputFile);
        	long conversionTime = System.currentTimeMillis() - startTime;
        	doLog(String.format("successful conversion: %s [%db] to %s in %dms", inputExtension, inputFile.length(), outputExtension, conversionTime), null, false);
			if (borrar && !inputFile.delete()) {
				throw new GadirServiceException("No se pudo borrar el ODT.");
			}
        } catch (Exception exception) {
            doLog(String.format("failed conversion: %s [%db] to %s; %s; input file: %s", inputExtension, inputFile.length(), outputExtension, exception, inputFile.getName()), exception, true);
        	throw new GadirServiceException("Conversion failed", exception);
        }
//		doLog("CREANDO PDF EN LOCAL TERMINÓ", null, true);
	}
	
	/**
	 * Convertir ODT a PDF mediante webservice.
	 * @param inputODT
	 * @param outputPDF
	 * @param borrar
	 * @param string 
	 * @throws GadirServiceException
	 */
	static private void crearPDFWs(final String inputODT, final String outputPDF, final boolean borrar, String urlProperty) throws GadirServiceException {
		String urlS = GadirConfig.leerParametro(urlProperty);
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String charset = "UTF-8";
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		File inputFile = new File(inputODT);
		
		try {
			// ENVÍO
			URLConnection connection = new URL(urlS).openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			PrintWriter writer = null;
			try {
				OutputStream output = connection.getOutputStream();
				writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!

			    // Send outputFormat param.
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"outputFormat\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF);
			    writer.append("pdf").append(CRLF).flush();
				
			    // Send binary file.
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"inputDocument\"; filename=\"" + inputFile.getName() + "\"").append(CRLF);
			    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(inputFile.getName())).append(CRLF);
			    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
			    writer.append(CRLF).flush();
			    InputStream input = null;
			    try {
			        input = new FileInputStream(inputFile);
			        byte[] buffer = new byte[1024];
			        for (int length = 0; (length = input.read(buffer)) > 0;) {
			            output.write(buffer, 0, length);
			        }
			        output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
			    } finally {
			        if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
			    }
			    writer.append(CRLF).flush(); // CRLF is important! It indicates end of binary boundary.
				
			    // End of multipart/form-data.
			    writer.append("--" + boundary + "--").append(CRLF);
			} finally {
			    if (writer != null) writer.close();
			}

			// RESPUESTA
			final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';
			final File file = new File(outputPDF.substring(0, outputPDF.lastIndexOf(barra))); 
			if (!file.exists() && !file.mkdirs()) {
				throw new GadirServiceException("No se pudo crear la carpeta " + file.getName());
			}
			DataInputStream inStream = new DataInputStream ( connection.getInputStream() );
			final OutputStream outputStream = new FileOutputStream(outputPDF);
			final byte[] buf = new byte[1024];
			int len = inStream.read(buf);
			while (len > 0) {
				outputStream.write(buf, 0, len);
				len = inStream.read(buf);
			}
			inStream.close();
			outputStream.close();

			// BORRADO
			if (borrar && !inputFile.delete()) {
				throw new GadirServiceException("No se pudo borrar el ODT.");
			}
		} catch (IOException e) {
			throw new GadirServiceException(e.getMessage(), e);
		}
//		doLog("CREANDO PDF POR WEBSERVICE TERMINÓ", null, true);
	}

	public static String soloCaracteresSeguros(final String string, final boolean contieneRutaCompleta) {
		String caracteresSeguros;
		if (contieneRutaCompleta) {
//			caracteresSeguros = "aábcçdeéfghiíjklmnñoópqrstuúvwxyzAÁBCÇDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ0123456789-_./\\:";
			caracteresSeguros = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_./\\:";
		} else {
//			caracteresSeguros = "aábcçdeéfghiíjklmnñoópqrstuúvwxyzAÁBCÇDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ0123456789-_.";
			caracteresSeguros = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.";
		}
		StringBuffer result = new StringBuffer(string.length());
		for (int i=0; i<string.length(); i++) {
			if (caracteresSeguros.indexOf(string.charAt(i)) != -1) {
				result.append(string.charAt(i));
			}
		}
		return result.toString();
	}

	/**
	 * Se utiliza el método merge() que recibe el proceso en vez de éste.
	 * @param string
	 * @param etiquetas2
	 * @param login
	 * @param informeActuacion
	 * @param servletResponse
	 * @param informeParametro
	 * @throws GadirServiceException
	 * 
	 * @Deprecated
	 * @see merge(List listaEtiquetas, String coProcesoActual, String login, int informeActuacion, String informeParametro, HttpServletResponse servletResponse)
	 */
	@Deprecated
	public static void merge(final String plantillaOO, final List<?> listaEtiquetas, final String usuario, final int informeActuacion, final HttpServletResponse servletResponse, final String informeParametro) throws GadirServiceException {
		mergeLista(plantillaOO, null, listaEtiquetas, usuario, informeActuacion, servletResponse, informeParametro, false, true);
	}

	private static void mergeLista(final String plantillaOO,
			final Long coPlantilla,
			final List<?> listaEtiquetas, 
			final String usuario, 
			final int actuacion, 
			final HttpServletResponse response,
			final String parametro, 
			final boolean isBatch,
			final boolean isServidorAplicaciones) throws GadirServiceException {
		iniciarVariables(true);
		
		if (actuacion == ACT_ABRIR && response == null) {
			throw new GadirServiceException("Abrir necesita el objeto response");
		}
		if (actuacion == ACT_EMAIL && parametro == null) {
			throw new GadirServiceException("Actuacion e-mail necesita destinatario");
		}
		
		ImpresionParametroVO auxiliar = getAuxiliar();

		final String rutaPlantillas = GadirConfig.leerParametro("ruta.informes.plantillas");
		final String rutaTemporal = calcularSubcarpetaTemporal(auxiliar);
		
		// Iniciar merge
		final EjecucionDTO ejecucionDTO = new EjecucionDTO();
		ejecucionDTO.setCoEjecucion(Long.valueOf(0));
		AcmUsuarioDTO usuarioDTO=null;
		try {
			usuarioDTO = acmUsuarioBO.findById(usuario);
			ejecucionDTO.setCoAcmUsuario(usuarioDTO.getCoAcmUsuario());
		} catch (GadirServiceException e) {
			doLog("En merge, usuario no existe: " + usuario, e, true);
		}
		auxiliar = anadirActuacion(auxiliar, actuacion, parametro);
		auxiliar.setEjecucionDTO(ejecucionDTO);
		auxiliar.setOrdenTxt(1);
		auxiliar.setBatch(isBatch);
		
		// Tratar etiquetas
		for (final Iterator<?> i = listaEtiquetas.iterator(); i.hasNext();) {
			String line = null;
			final Object object = i.next();
			if (object instanceof String) {
				line = (String) object;
				if (line.length()>5 && line.substring(5).equals("null")) {
					line = line.substring(0, 5);
				}
			} else if (object instanceof KeyValue) {
				final KeyValue keyValue = (KeyValue) object;
				final StringBuffer lineBuffer = new StringBuffer(keyValue.getKey());
				for (int i1 = lineBuffer.length(); i1 < 5; i1++) {
					lineBuffer.append(' ');
				}
				if (Utilidades.isNull(keyValue.getValue())) {
					line = lineBuffer.toString();
				} else {
					line = lineBuffer + keyValue.getValue();
				}
			} else {
				throw new GadirServiceException("Etiquetas para el merge deben ser String o KeyValue.");
			}
			auxiliar = tratarEtiqueta(line, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, rutaPlantillas + plantillaOO, null);
		}
		
		auxiliar = insertarEtiquetas(auxiliar);
		
		// Finaliza el úlitmo informe abierto
		auxiliar.setServletResponse(response);
		auxiliar = tratarEtiqueta(ETIQUETA_FIN, rutaTemporal, auxiliar, ejecucionDTO, coPlantilla, rutaPlantillas + plantillaOO, null);
		
		final List<ImpresionInformeVO> listaInformes = (List<ImpresionInformeVO>) auxiliar.getInformesImprimir();
		if (listaInformes != null && !listaInformes.isEmpty()) {
			for (ImpresionInformeVO impresionInformeVO : listaInformes) {
				imprimirPdf(impresionInformeVO.getPdf(), parametro, impresionInformeVO.getCopias(), isServidorAplicaciones);
			}
		}
	}

	private static String calcularSubcarpetaTemporal(ImpresionParametroVO impresionParamVO) throws GadirServiceException {
		String subcarpetaTemporal;
		if (Utilidades.isEmpty(impresionParamVO.getSubcarpetaTemporal())) {
			// Calculamos el nombre de la subcarpeta temporal
			final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';
			final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddkk", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			subcarpetaTemporal = GadirConfig.leerParametro("ruta.temporal") + dateFormat.format(Utilidades.getDateActual()) + barra;
			impresionParamVO.setSubcarpetaTemporal(subcarpetaTemporal);
			// Hay que crear la carpeta si no existe
			final File file = new File(subcarpetaTemporal); 
			if (!file.exists()) {
				if (file.mkdirs()) {
					// Se cambia los permisos de la carpeta
					if (LINUX.equals(tipoSistema)) {
						try {
							final Runtime runtime = Runtime.getRuntime();
							Process proc1;
							proc1 = runtime.exec("/bin/chmod 777 " + subcarpetaTemporal);
							proc1.waitFor();
						} catch (IOException e) {
							throw new GadirServiceException("IOException al realizar chmod a la carpeta temporal " + subcarpetaTemporal, e);
						} catch (InterruptedException e) {
							throw new GadirServiceException("InterruptedException al realizar chmod a la carpeta temporal " + subcarpetaTemporal, e);
						}
					}
				} else {
					throw new GadirServiceException("No puede crear la carpeta temporal " + subcarpetaTemporal);
				}
			}
		} else {
			// La carpeta ya estaba asignada, por lo que sabemos que ya existe
			subcarpetaTemporal = impresionParamVO.getSubcarpetaTemporal();
		}
		return subcarpetaTemporal;
	}

	private static ImpresionParametroVO anadirActuacion(final ImpresionParametroVO auxiliar, final int actuacion, final String parametro) {
		final HashMap<String, String> etiquetas = new HashMap<String, String>();
		switch (actuacion) {
		case Impresion.ACT_ABRIR:
			etiquetas.put(ETIQUETA_ABRIR, parametro);
			break;
		case Impresion.ACT_EMAIL:
			etiquetas.put(ETIQUETA_EMAIL, parametro);
			break;
		case Impresion.ACT_GUARDAR:
			etiquetas.put(ETIQUETA_GUARDAR, parametro);
			break;
		case Impresion.ACT_IMPRIMIR:
			etiquetas.put(ETIQUETA_PRINT, "S");
			etiquetas.put(ETIQUETA_NCOPY, "1");
			break;
		default:
			break;
		}
		auxiliar.setEtiquetasConfig(etiquetas);
		return auxiliar;
	}
	
	/**
	 * Realizar el merge.
	 * @param listaEtiquetas
	 * @param coProcesoActual
	 * @param login
	 * @param informeActuacion
	 * @param informeParametro
	 * @param servletResponse
	 * @throws GadirServiceException
	 */
	public static void merge(
			final List<?> listaEtiquetas, 
			final String coProcesoActual,
			final String login, 
			final int informeActuacion, 
			final String informeParametro,
			final HttpServletResponse servletResponse) throws GadirServiceException {
		final ProcesoAccionDTO procesoAccionDTO = procesoAccionBO.findByProcesoAccionInitialized(coProcesoActual, ACCION_INFORME);
		
		boolean existePlantilla = true;
		PlantillaDTO plantillaDTO = null;
		
		if (procesoAccionDTO == null || procesoAccionDTO.getPlantillaDTOs().isEmpty()) {
			existePlantilla = false;
		} else {
			final List<PlantillaDTO> plantillaDTOs = new ArrayList<PlantillaDTO>(procesoAccionDTO.getPlantillaDTOs());
			plantillaDTO = plantillaDTOs.get(0);
			if (plantillaDTOs.get(0).getPlantillaOdtDTO() == null) {
				existePlantilla = false;
			}
		}
		
		if (!existePlantilla) {
			throw new GadirServiceException("No existe plantilla odt para proceso \"" + coProcesoActual + "\" y accion \"" + ACCION_INFORME + "\"");
		}
		
		Impresion.mergeLista(plantillaDTO.getPlantillaOdtDTO().getRuta(), plantillaDTO.getCoPlantilla(), listaEtiquetas, login, informeActuacion, servletResponse, informeParametro, false, true);
	}

	@SuppressWarnings("unchecked")
	public static ImpresionInformeVO realizarActuaciones(final String rutaPdf, final ImpresionParametroVO auxiliar, final String coAcmUsuario) throws GadirServiceException {
		final char barra = LINUX.equals(tipoSistema) ? '/' : '\\';
		final Map<Integer, Object> mapActuaciones = new HashMap<Integer, Object>();
		
		if (auxiliar.getEtiquetasConfig().isEmpty()) {
			// Si no hay etiquetas de configuración, se entiende "Imprimir una copia".
			mapActuaciones.put(ACT_IMPRIMIR, "1");
		} else {
			// Si hay etiquetas de configuración se interpretan.
			if (auxiliar.getEtiquetasConfig().containsKey(ETIQUETA_ABRIR)) {
				mapActuaciones.put(ACT_ABRIR, null);
			}
			if (auxiliar.getEtiquetasConfig().containsKey(ETIQUETA_GUARDAR)) {
				mapActuaciones.put(ACT_GUARDAR, auxiliar.getEtiquetasConfig().get(ETIQUETA_GUARDAR));
			} 
			if (auxiliar.getEtiquetasConfig().containsKey(ETIQUETA_EMAIL) && !"N".equals(auxiliar.getEtiquetasConfig().get(ETIQUETA_EMAIL)) && Utilidades.isNotEmpty(auxiliar.getEtiquetasConfig().get(ETIQUETA_EMAIL))) {
				Map<String, String> valoresEmail = new HashMap<String, String>();
				valoresEmail.put(ETIQUETA_EMAIL, auxiliar.getEtiquetasConfig().get(ETIQUETA_EMAIL));
				valoresEmail.put(ETIQUETA_ASUNTO_EMAIL, auxiliar.getEtiquetasConfig().get(ETIQUETA_ASUNTO_EMAIL));
				valoresEmail.put(ETIQUETA_CUERPO_EMAIL, auxiliar.getEtiquetasConfig().get(ETIQUETA_CUERPO_EMAIL));
				mapActuaciones.put(ACT_EMAIL, valoresEmail);
			} 
			if (auxiliar.getEtiquetasConfig().containsKey(ETIQUETA_BDDOC) && !"N".equals(auxiliar.getEtiquetasConfig().get(ETIQUETA_BDDOC))) {
				mapActuaciones.put(ACT_ALFRESCO, auxiliar.getEtiquetasConfig().get(ETIQUETA_BDDOC));
			} 
			if (auxiliar.getEtiquetasConfig().containsKey(ETIQUETA_PRINT) && "S".equals(auxiliar.getEtiquetasConfig().get(ETIQUETA_PRINT))) {
				String valor = auxiliar.getEtiquetasConfig().get(ETIQUETA_NCOPY);
				if (Utilidades.isEmpty(valor)) valor = "1";
				mapActuaciones.put(ACT_IMPRIMIR, valor);
			}
			if (auxiliar.getEtiquetas().containsKey(ETIQUETA_DSZIP)) { // Guardar en remesa
				mapActuaciones.put(ACT_REMESA, auxiliar.getEtiquetas().get(ETIQUETA_DSZIP));
			}
		}
//		for (
//			doLog(entry.getKey() + ": " + entry.getValue(), null, false);
//		}
		
		HttpServletResponse response = null;
		if (auxiliar.getServletResponse() != null) {
			response = (HttpServletResponse) auxiliar.getServletResponse();
		}
		
		ImpresionInformeVO informeVO = null;
		for (Entry<Integer, Object> actuacion : mapActuaciones.entrySet()) {
			switch (actuacion.getKey()) {
			case Impresion.ACT_ABRIR:
				Fichero.descargar(rutaPdf, response, "application/pdf");
				break;
			case Impresion.ACT_EMAIL:
				Map<String, String> valoresEmail = (Map<String, String>) actuacion.getValue();
				String para = valoresEmail.get(ETIQUETA_EMAIL);
				String asunto = valoresEmail.get(ETIQUETA_ASUNTO_EMAIL);
				String cuerpo = valoresEmail.get(ETIQUETA_CUERPO_EMAIL);
				if (asunto == null) asunto = "Informe eTIR";
				if (cuerpo == null) cuerpo = "Ver informe adjunto.";
				enviarCorreo(rutaPdf, para, asunto, cuerpo);
				setResponse(response, 204);
				break;
			case Impresion.ACT_IMPRIMIR:
				informeVO = new ImpresionInformeVO(rutaPdf, Integer.valueOf((String) actuacion.getValue()));
				setResponse(response, 204);
				break;
			case Impresion.ACT_GUARDAR:
				if (actuacion.getValue() != null) {
					try {
						if (Utilidades.isEmpty((String) actuacion.getValue())) {
							Fichero.copiar(rutaPdf, DatosSesion.getCarpetaAcceso() + barra + Fichero.getNombreFichero(rutaPdf));
						} else {
							Fichero.copiar(rutaPdf, ((String) actuacion.getValue()) + barra + Fichero.getNombreFichero(rutaPdf));
						}
					} catch (IOException e) {
						throw new GadirServiceException(e);
					}
				}
				setResponse(response, 204);
				break;
			case Impresion.ACT_ALFRESCO:
				guardarBDDocumental(rutaPdf, auxiliar.getCoBDDocumentalGrupo(), auxiliar.getEjecucionDTO(), "F".equals(actuacion.getValue()), auxiliar.getCoExpediente());
				break;
			case Impresion.ACT_REMESA:
				guardarEnRemesa(rutaPdf, (String) actuacion.getValue());
				break;
			default:
				break;
			}
		}
		return informeVO;
	}
	
	private static void guardarEnRemesa(String rutaPdf, String rutaDestino) throws GadirServiceException {
		if (rutaRemesa == null) {
			rutaRemesa = GadirConfig.leerParametro("ruta.remesas");
		}
		try {
			String rutaFinal = rutaRemesa + rutaDestino;
			if (!LINUX.equals(tipoSistema)) rutaFinal = rutaFinal.replace('/', '\\');
			rutaFinal = Fichero.asegurarBarraCarpeta(rutaFinal);
			Fichero.copiar(rutaPdf, rutaFinal + Fichero.getNombreFichero(rutaPdf));
		} catch (IOException e) {
			throw new GadirServiceException(e);
		}
	}

	private static void setResponse(final HttpServletResponse response, final int codigo) {
		if (response != null) {
			response.setStatus(codigo);
		}
	}
	
	public static void imprimirPdf(final String rutaPdf, final String impresora, final int copias, final boolean isServidorAplicaciones) throws GadirServiceException {
		final String cadenaConexionBatch = GadirConfig.leerParametro("servidor.batch.cadena.conexion");
		final Runtime runtime = Runtime.getRuntime();
		String prefijo = "";
		if (Utilidades.isNotEmpty(cadenaConexionBatch) && isServidorAplicaciones) {
			prefijo = "/usr/bin/ssh " + cadenaConexionBatch + " ";
		}
		final String comando = prefijo + "lpr -P" + (impresora == null ? DatosSesion.getImpresora() : impresora) + " -# " + copias + " -o sides=two-sided-long-edge " + rutaPdf;
		try {
			runtime.exec(comando);
		} catch (IOException e) {
			throw new GadirServiceException(e);
		}
		doLog("Ejecutado en el runtime: " + comando, null, false);
	}
	
	private static void guardarBDDocumental(final String rutaPdf, final String coBDDocumentalGrupo, final EjecucionDTO ejecucionDTO, final boolean isPortafirmas, final String coExpediente) {
		Long coBDDocumentalGrupoLong = guardarTablaEtir(rutaPdf, coBDDocumentalGrupo, ejecucionDTO);
		if (isPortafirmas) {
			String nombrePdf = Fichero.getNombreFichero(rutaPdf);
			PortafirmasService.nuevo(coBDDocumentalGrupoLong, nombrePdf, Long.valueOf(coExpediente));
		}
	}
	
	private static Long guardarTablaEtir(final String rutaPdf, final String coBDDocumentalGrupo, final EjecucionDTO ejecucionDTO) {
		Long grupo = null;
		try {
			String nombrePdf = Fichero.getNombreFichero(rutaPdf);
			String rutaGadirNFS = GadirConfig.leerParametro("ruta.informes.merge");
			Fichero.copiar(rutaPdf, rutaGadirNFS + nombrePdf);
			if (Utilidades.isEmpty(coBDDocumentalGrupo) || !Utilidades.isNumeric(coBDDocumentalGrupo)) {
				grupo = (long) -1;
			} else {
				grupo = Long.parseLong(coBDDocumentalGrupo);
			}
			grupo = guardarDocumentacionBO.execute(grupo, null, nombrePdf, ejecucionDTO.getCoEjecucion(), ejecucionDTO.getCoAcmUsuario());
		} catch (IOException e) {
			doLog(e.getMessage(), e, true);
		} catch (GadirServiceException e) {
			doLog(e.getMensaje(), e, true);
		}
		return grupo;
	}

//	private static void guardarAlfresco(final String rutaPdf, final String nuDocumentoCompleto) {
//		doLog("El documento " + rutaPdf + " debe guardarse en Alfresco.", null, false);
//		
//		if (Utilidades.isEmpty(nuDocumentoCompleto) || nuDocumentoCompleto.length() != 13) {
//			doLog("El valor de la etiqueta BDDOC (" + nuDocumentoCompleto + ") no es un número de documento.", null, false);
//		} else {
//			try{
////				DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(nuDocumentoCompleto.substring(1, 3), nuDocumentoCompleto.substring(4, 4), nuDocumentoCompleto.substring(5, 13)));
//				//TOxDO Utilizar datos del documento en los meta datos.
//			
//				String nombre=rutaPdf.substring(rutaPdf.lastIndexOf('\\') + 1);
//				nombre=nombre.substring(nombre.lastIndexOf('/') + 1);
//	
//				final InterfazAlfresco interfazAlfresco= new InterfazAlfresco();
//				interfazAlfresco.inicializar();
//				final Reference ref=interfazAlfresco.getGadirReference();
//				
//				//TOxDO: separar por espacios en alfresco
//				interfazAlfresco.createContent(ref, nombre, "UTF-8", rutaPdf);
//				
//				interfazAlfresco.terminarSesion();
//				
//			}catch(Exception e){
//				log.error("Error guardando en Alfresco", e);
//			}
//		}
//	}
	
	public static void enviarCorreo(final String rutaPdf, final String para, final String asunto, final String texto, boolean isHtml) throws GadirServiceException {
		enviarCorreo(rutaPdf, para, asunto, texto, null, true);
	}

	public static void enviarCorreo(final String rutaPdf, final String para, final String asunto, final String texto) throws GadirServiceException {
		enviarCorreo(rutaPdf, para, asunto, texto, null, false);
	}
	public static void enviarCorreo(final String rutaPdf, final String para, String asunto, final String texto, final byte[] contenidoAdjunto, boolean isHtml) throws GadirServiceException {
		final boolean isConAdjuntos = rutaPdf != null;
		DataSource fileDataSource = null;
		if (isConAdjuntos && contenidoAdjunto == null) {
			fileDataSource = new FileDataSource(rutaPdf);
		} else if (isConAdjuntos) {
        	MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
			fileDataSource = new ByteArrayDataSource(contenidoAdjunto, mimetypesFileTypeMap.getContentType(rutaPdf));
		}
		final Properties props = System.getProperties();
		final Session session = Session.getInstance(props, null);
		
		try {
			final MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(EMAIL_FROM));
			StringTokenizer stringTokenizer = new StringTokenizer(para,";");
			List<String> incluidos = new ArrayList<String>();
			while (stringTokenizer.hasMoreTokens()) {
				String to = stringTokenizer.nextToken();
				if (Utilidades.isNotEmpty(para) && !incluidos.contains(to)) {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					incluidos.add(to);
				}
			}
			
			String entorno = GadirConfig.leerParametro("entorno.servidor");
			if (!"produccion".equals(entorno)) {
				asunto += " [" + entorno + "]";
			}
			
			msg.setSubject(StringEscapeUtils.unescapeHtml(asunto));

			final MimeBodyPart bp1 = new MimeBodyPart();
			String cuerpo = texto.replace("<text:line-break/>", "\n");
			cuerpo = "Estimado/a,\n\n" +
					StringEscapeUtils.unescapeHtml(cuerpo) +
					"\n\nUn cordial saludo.\n\nEste es un mensaje automático y no contestará a las respuestas que se envíen a este buzón.";

			if(isHtml)
				bp1.setContent(cuerpo, "text/html");
			else
				bp1.setText(cuerpo, CODIFICACION);
 
			MimeBodyPart bp2 = null;
			if (isConAdjuntos) {
				bp2 = new MimeBodyPart();
				bp2.setDataHandler(new DataHandler(fileDataSource));
				bp2.setFileName(Fichero.getNombreFichero(rutaPdf));
			}

			final Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bp1);
			if (isConAdjuntos) {
				multipart.addBodyPart(bp2);
			}

			msg.setContent(multipart);
 			msg.setSentDate(new Date());

			final Transport transport = session.getTransport(EMAIL_PROTOCOLO);
			transport.connect(EMAIL_HOST, EMAIL_USER, new String(Base64.decodeBase64(EMAIL_PASSWORD)));
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			doLog("Correo enviado!", null, false);
 		} catch (MessagingException mex) {
 			doLog("MessageinException!", mex, true);
			throw new GadirServiceException(mex);
		}
	}
	
	private static ImpresionParametroVO getAuxiliar() {
		return getAuxiliar(null);
	}
	
	private static ImpresionParametroVO getAuxiliar(final ImpresionParametroVO auxiliarParam) {
		final ImpresionParametroVO auxiliar = new ImpresionParametroVO();
		auxiliar.setUbicacionEtiqueta("");
		auxiliar.setUbicacionValor("");
		auxiliar.setTablaUnica(null);
		auxiliar.setEtiquetasTabla(new HashMap<String, String>(10));
		auxiliar.setEtiquetas(new HashMap<String, String>());
		if (auxiliarParam != null) {
			auxiliar.setOrdenTxt(auxiliarParam.getOrdenTxt());
			auxiliar.setLineasTotal(auxiliarParam.getLineasTotal());
			auxiliar.setEtiquetasConfig(auxiliarParam.getEtiquetasConfig());
			auxiliar.setInformesImprimir(auxiliarParam.getInformesImprimir());
			auxiliar.setEjecucionDTO(auxiliarParam.getEjecucionDTO());
			auxiliar.setBatch(auxiliarParam.isBatch());
			auxiliar.setListaItemsManifest(auxiliarParam.getListaItemsManifest());
			auxiliar.setSubcarpetaTemporal(auxiliarParam.getSubcarpetaTemporal());
		}
		return auxiliar;
	}

	private static void doLog(final String msg, final Exception exception, final boolean isError) {
		if (log == null) {
			if (isError) {
				System.err.println(msg);
			} else {
				System.out.println(msg);
			}
			if (exception != null) {
				exception.printStackTrace();
			}
		} else {
			if (isError) {
				log.error(msg, exception);
			} else {
				log.debug(msg, exception);
			}
		}
	}



	
	
	
	

	public static AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(final AcmUsuarioBO acmUsuarioBO) {
		Impresion.acmUsuarioBO = acmUsuarioBO;
	}

	public static EjecucionBO getEjecucionBO() {
		return ejecucionBO;
	}

	public void setEjecucionBO(final EjecucionBO ejecucionBO) {
		Impresion.ejecucionBO = ejecucionBO;
	}

	public static InformeBO getInformeBO() {
		return informeBO;
	}

	public void setInformeBO(final InformeBO informeBO) {
		Impresion.informeBO = informeBO;
	}

	public static ProcesoAccionBO getProcesoAccionBO() {
		return procesoAccionBO;
	}

	public void setProcesoAccionBO(final ProcesoAccionBO procesoAccionBO) {
		Impresion.procesoAccionBO = procesoAccionBO;
	}

	public static DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	public static void setDocumentoBO(final DocumentoBO documentoBO) {
		Impresion.documentoBO = documentoBO;
	}

	public static GuardarDocumentacionBO getGuardarDocumentacionBO() {
		return guardarDocumentacionBO;
	}

	public void setGuardarDocumentacionBO(
			GuardarDocumentacionBO guardarDocumentacionBO) {
		Impresion.guardarDocumentacionBO = guardarDocumentacionBO;
	}
	
}
