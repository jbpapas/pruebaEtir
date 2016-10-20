/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.BatchSetEstadoBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.PlantillaBO;
import es.dipucadiz.etir.comun.bo.ProcesoAccionBO;
import es.dipucadiz.etir.comun.bo.ProcesoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.PlantillaOdtConstants;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.dto.ProcesoAccionDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.comun.vo.DatosScriptVO;
import es.dipucadiz.etir.sb05.action.G523CargaDinamica.G523CargaDinamicaAction;

/**
 * Lanzar procesos masivos.
 * @author jronnols
 *
 */
final public class Batch {
	
	private static BatchSetEstadoBO batchSetEstadoBO;
	private static EjecucionBO ejecucionBO;
	private static ProcesoAccionBO procesoAccionBO;
	private static PlantillaBO plantillaBO;
	
	private static ProcesoBO procesoBO;
	private static AcmUsuarioBO acmUsuarioBO;
	
	static final private String ETIR = "etir";
	static final private String WEB_INF = "WEB-INF";
	static final private String LINUX = "linux";
	static final private String WINDOWS = "windows";
	static final private String LG_RUN_PL_FUNC_N_CATCH = "lgRunPLFuncNCatch";
	static final private String EXTENSION_T2P = "t2p";
	
	String coProceso;
	String proceso;
	static ProcesoDTO procesoDTO;
	
	/**
	 * Constante que almacena el valor que indica el código del proceso de
	 * Lanzar Actualización de Censo IBI
	 */
	static final public String CO_PROCESO_ACTUALIZACION_CENSO_IBI = "G535_IBI";
	
	/**
	 * Constante que almacena el valor que indica el código del proceso de
	 * Lanzar Actualización de Censo IVTM
	 */
	static final public String CO_PROCESO_ACTUALIZACION_CENSO_IVTM = "G535_IVTM";
	
	/**
	 * Constante que almacena el valor que indica el código del proceso de
	 * Lanzar Actualización de Censo IAE
	 */
	static final public String CO_PROCESO_ACTUALIZACION_CENSO_IAE = "G535_IAE";
	static final public String CO_PROCESO_ACTUALIZACION_CENSO_BICE = "G535_BICE";
	
	/**
	 * Constante que almacena el valor que indica el código del proceso
	 *  de Correspondencias Masivas
	 */
	static final public String CO_PROCESO_CORRESPONDENCIAS = "G5341_CORRESPOND";
	
	/**
	 * Constante que almacena el valor que indica el código del proceso
	 *  de Validación Individual Masiva
	 */
	static final public String CO_PROCESO_ACTUALIZACION_CASILLAS= "G5331_ACT_CASILLAS";
	
	/**
	 * Constante que almacena el valor que indica el código del proceso
	 *  de Validación Individual Masiva
	 */
	static final public String CO_PROCESO_VALIDACION = "G5323_VALIDACION";
	/**
	 * Constante que almacena el valor que indica el código del proceso
	 *  de Rechazar control de recepción
	 */
	static final public String CO_PROCESO_RECHAZAR_CONTROL = "G521_RECEPCION";
	
	static final public String CO_PROCESO_INFORME_DOMICILIACIONES = "G7A1_INFORME";

	private static final Log LOG = LogFactory.getLog(Batch.class);

	private Batch() {}
	
	/**
	 * Lanzar proceso directamente, sin usar la cola de ejecución
	 * @param proceso
	 * @param parametros
	 * @return
	 * @throws GadirServiceException
	 */
	public static EjecucionDTO ejecutarDevolverDTO(final String proceso, final List<String> parametros, final AccesoPlantillaVO accesoPlantillaVO) throws GadirServiceException {
		return ejecutarDevolverDTO(proceso, parametros, accesoPlantillaVO, false);
	}
	public static EjecucionDTO ejecutarDevolverDTO(final String proceso, final List<String> parametros, final AccesoPlantillaVO accesoPlantillaVO, final boolean isPostBatch) throws GadirServiceException {
		EjecucionDTO ejecucionDTO;
		try {
			if(Utilidades.isEmpty(DatosSesion.getLogin()) || "anonymous".equalsIgnoreCase(DatosSesion.getLogin())) {
				AcmUsuarioDTO usuarioAcerca = acmUsuarioBO.findById("acerca");
				ejecucionDTO = lanzar(proceso, usuarioAcerca.getCoAcmUsuario(), parametros, usuarioAcerca.getImpresora(), null, false, accesoPlantillaVO, null, isPostBatch);
			}
			else
				ejecucionDTO = lanzar(proceso, DatosSesion.getLogin(), parametros, DatosSesion.getImpresora(), null, false, accesoPlantillaVO, null, isPostBatch);
		} catch (Exception e) {
			throw new GadirServiceException(e);
		} 
		final EjecucionDTO ejecucionResultadoDTO = ejecucionBO.findById(ejecucionDTO.getCoEjecucion());
		return ejecucionResultadoDTO;
	}
	
	public static int ejecutar(final String proceso, final List<String> parametros, final AccesoPlantillaVO accesoPlantillaVO) throws GadirServiceException {
		return ejecutar(proceso, parametros, accesoPlantillaVO, false);
	}
	
	public static int ejecutar(final String proceso, final List<String> parametros, final AccesoPlantillaVO accesoPlantillaVO, final boolean isPostBatch) throws GadirServiceException {
		return ejecutarDevolverDTO(proceso, parametros, accesoPlantillaVO, isPostBatch).getCoTerminacion();
	}

	

	/**
	 * @deprecated Usar lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, AccesoPlantillaVO accesoPlantillaVO)
	 */
	@Deprecated
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, DatosSesion.getImpresora(), null, true, new AccesoPlantillaVO(), null, isPostBatch).getCoEjecucion();
	}

	/**
	 * Lanzar un proceso batch
	 * @param proceso
	 * @param usuario
	 * @param parametros
	 * @param impresora
	 * @param accesoPlantillaVO
	 * @return Código de ejecución
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws GadirServiceException
	 */
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final AccesoPlantillaVO accesoPlantillaVO) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, accesoPlantillaVO, false);
	}

	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final AccesoPlantillaVO accesoPlantillaVO, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, accesoPlantillaVO, null, isPostBatch);
	}
	
	/**
	 * 
	 * @param proceso
	 * @param usuario
	 * @param parametros
	 * @param impresora
	 * @param accesoPlantillaVO
	 * @param fichero
	 * @return Código de ejecución
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws GadirServiceException
	 */
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final AccesoPlantillaVO accesoPlantillaVO, final String fichero, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, null, true, accesoPlantillaVO, fichero, isPostBatch).getCoEjecucion();
	}
	
	/**
	 * @deprecated Usar lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, AccesoPlantillaVO accesoPlantillaVO)
	 */
	@Deprecated
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, new AccesoPlantillaVO(), isPostBatch);
	}

	/**
	 * 
	 * @param proceso
	 * @param usuario
	 * @param parametros
	 * @param impresora
	 * @param plantillasOdt
	 * @return Código de ejecución
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws GadirServiceException
	 */
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final List<String> plantillasOdt) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, plantillasOdt, false);
	}
	
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final List<String> plantillasOdt, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, plantillasOdt, null, isPostBatch);
	}

	/**
	 *
	 * @param proceso
	 * @param usuario
	 * @param parametros
	 * @param impresora
	 * @param plantillasOdt
	 * @param fichero
	 * @return Código de ejecución
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws GadirServiceException
	 */
	public static Long lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final List<String> plantillasOdt, final String fichero, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {
		return lanzar(proceso, usuario, parametros, impresora, plantillasOdt, true, new AccesoPlantillaVO(), fichero, isPostBatch).getCoEjecucion();
	}

	private static EjecucionDTO lanzar(final String proceso, final String usuario, final List<String> parametros, final String impresora, final List<String> plantillasOdt, final boolean conTs, final AccesoPlantillaVO accesoPlantillaVO, final String fichero, final boolean isPostBatch) throws IOException, InterruptedException, GadirServiceException {		
		String rutaFicheroCopiado=ficherosCompartidos(proceso, parametros);

		// Asignación variables entorno
		final String carpetaScripts = GadirConfig.leerParametro("ruta.batch.script");
		final String tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		final String cadenaTime = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS", new Locale("es", "ES")).format(new GregorianCalendar().getTime());
		
		String procesoAux = proceso;
		String usuarioAux = usuario;
		procesoAux = procesoAux.toUpperCase().replace("Á", "A").replace("Á", "A").replace("É", "E").replace("Ë", "E").replace("Í", "I").replace("Ï", "I").replace("Ó", "O").replace("Ö", "O").replace("Ú", "U").replace("Ü", "U").replace("Ñ", "N");
	
	 	usuarioAux = usuarioAux.toUpperCase().replace("Á", "A").replace("Á", "A").replace("É", "E").replace("Ë", "E").replace("Í", "I").replace("Ï", "I").replace("Ó", "O").replace("Ö", "O").replace("Ú", "U").replace("Ü", "U").replace("Ñ", "N");		
//		iterar cada caracter si no casa con patron y es uno de los caracteres tildes se sustituye x eso sino x vacio
//		[a-zA-Z0-9-_.] 
	 	Pattern pat = Pattern.compile("[A-Z0-9_\\.-]");
	 	int cont = 0;
	 	 
	 	List<String> posiciones = new   ArrayList<String>();
	 	while (cont<procesoAux.length()){	 		 
		     Matcher mat = pat.matcher(""+procesoAux.charAt(cont));
		     if (!mat.matches()) {
		    	 posiciones.add(""+procesoAux.charAt(cont)); 		    	 
		     }
		     cont++;
	 	}
	     for(String cambiar:posiciones){
	    	 procesoAux= procesoAux.replace(cambiar, "");	    	 
	     }
	     
	     
	      cont = 0;
	 	 
		  posiciones = new   ArrayList<String>();
		 	while (cont<usuarioAux.length()){	 		 
			     Matcher mat = pat.matcher(""+usuarioAux.charAt(cont));
			     if (!mat.matches()) {
			    	 posiciones.add(""+usuarioAux.charAt(cont)); 		    	 
			     }
			     cont++;
		 	}
		     for(String cambiar:posiciones){
		    	 usuarioAux= usuarioAux.replace(cambiar, "");	    	 
		     }
		     
	     
		final String nombreArchivo = procesoAux + "-" + usuarioAux + "-" + cadenaTime;

		// Asignación del nombre de la cola
		String cola = "";
		if(conTs){
			if(Utilidades.isEmpty(DatosSesion.getColaEjecucion())) {
				try {
					AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(usuario);
					cola = "g" + acmUsuarioDTO.getUnidadAdministrativaDTO().getCoUnidadAdministrativa();
				} catch (Exception e) {
					cola = "";
				}
			}
			else {
				ProcesoDTO procesoDTO = ProcesoUtil.getProcesoByCoProceso(proceso);
				if (Utilidades.isNotNull(procesoDTO)) {
					if (procesoDTO.getPrioridad() == null || procesoDTO.getPrioridad() == 0)	
						cola = "g" + DatosSesion.getColaEjecucion().substring(1);
					else
						cola = "e" + DatosSesion.getColaEjecucion().substring(1);
				}	
			}
		} else {
			cola = DatosSesion.getColaEjecucion();
		}
		
		// Asignación del nombre del script
		String script = "";
		String eol = ""; 
		if (WINDOWS.equals(tipoSistema)) {
			script = carpetaScripts + nombreArchivo + ".cmd";
			eol = "\r\n";
		} else if (LINUX.equals(tipoSistema)) {
			script = carpetaScripts + nombreArchivo + ".sh";
			eol = "\n";
		} else {
			throw new GadirServiceException("Tipo de sistema desconocido");
		}
			
		EjecucionDTO ejecucionDTO = ejecucionBO.insert(proceso, usuario, script, (conTs ? cola : ""), parametros);
		Batch.setEstado(ejecucionDTO.getCoEjecucion().intValue(), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_PETICION.charAt(0));
		final DatosScriptVO datosScriptVO = crearScript(ejecucionDTO, impresora, plantillasOdt, conTs, accesoPlantillaVO, fichero, rutaFicheroCopiado, script, eol, tipoSistema, nombreArchivo);
		
		ejecutarScript(script, conTs, cola, isPostBatch);
		if (!conTs) {
			// Ejecutar merge ya que no se ejecuta mediante script en este caso	
			final List<String> parametrosMerge = datosScriptVO.getParametrosJava();
			String[] args = new String[parametrosMerge.size() + 1];
			args[0] = ejecucionDTO.getCoEjecucion().toString();
			for (int i=0; i<parametrosMerge.size(); i++) {
				args[i + 1] = parametrosMerge.get(i);
			}
			// Realizar merge
			Impresion.mainOnline(args);
			// Ejecutar procesos derivados
			PostBatch.lanzarProcesosOnline(ejecucionDTO.getCoEjecucion());
			// Enviar correos
			PostBatch.enviarCorreosOnline(ejecucionDTO.getCoEjecucion());
			// Enviar correos
			PostBatch.serviciosWebOnLine(ejecucionDTO.getCoEjecucion());			
			// Marcar como finalizado
			Batch.setEstado(ejecucionDTO.getCoEjecucion().intValue(), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_FINALIZADO.charAt(0), ejecucionBO.findById(ejecucionDTO.getCoEjecucion()).getCoTerminacion(), usuario);
		}
		return ejecucionDTO;
		
	}
	
	public static Long lanzarFusion(String archivo1, String archivo2, String archivoResultado) throws IOException, GadirServiceException, InterruptedException{
		final String script = crearScriptFusion(archivo1, archivo2, archivoResultado);

		ejecutarScript(script, true, DatosSesion.getColaEjecucion(), false);
		
		return (long)0;
	}
	
	private static String crearScriptFusion(String archivo1, String archivo2, String archivoResultado) throws IOException, GadirServiceException {
		final String carpetaScripts = GadirConfig.leerParametro("ruta.batch.script");
		final String tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		final String rutaTomcat = GadirConfig.leerParametro("ruta.tomcat").trim();
		String rutaUsuario = DatosSesion.getCarpetaAcceso();
		if (Utilidades.isEmpty(rutaUsuario)) {
			rutaUsuario = GadirConfig.leerParametro("ruta.carpetas.usuarios");
		}

		final String cadenaTime = new SimpleDateFormat("-yyyy.MM.dd-HH.mm.ss", new Locale("es", "ES")).format(new GregorianCalendar().getTime());
		final String nombreArchivo = "FUSION" + "-" + DatosSesion.getLogin() + cadenaTime;

		/*
		 * 12/08/2010: Comentamos la línea para que todos los lanzamientos de Batch
		 * se realicen con la inicialización del Array de ficheros de datos
		 */
		
		String rutaScript, eol;
		if (WINDOWS.equals(tipoSistema)) {
			rutaScript = carpetaScripts + nombreArchivo + ".cmd";
			eol = "\r\n";
		} else if (LINUX.equals(tipoSistema)) {
			rutaScript = carpetaScripts + nombreArchivo + ".sh";
			eol = "\n";
		} else {
			throw new GadirServiceException("Tipo de sistema desconocido");
		}

		final File file = new File(rutaScript);
		if (file.exists()) {
			throw new GadirServiceException("El script ya existe.");
		}

		final FileOutputStream fos = new FileOutputStream(file);
		final DataOutputStream dos = new DataOutputStream(fos);

		// Empezamos a escribir en el sh.
		String e_co_Acm_Usuario = DatosSesion.getLogin();
		String e_mensaje;
		int e_importancia = 1;
		String e_tipo = "B";
		String e_co_Acm_Usuario_Remitente = DatosSesion.getLogin();
		if (!Utilidades.isEmpty(archivo1) || !Utilidades.isEmpty(archivo2)){
			dos.writeBytes("#!/bin/bash" + eol);
			dos.writeBytes("function mifuncion () {" + eol);
			e_mensaje = "No se ha podido convertir el fichero $ficheroConvertido";
			dos.writeBytes("   lgRunPLProcNCatch \"comun_util.enviarNotificacion('"+e_co_Acm_Usuario+"', '"+e_mensaje+"', '"+e_importancia+"', '"+e_tipo+"', '"+e_co_Acm_Usuario_Remitente+"')\";" + eol);
			dos.writeBytes("   exit;" + eol);
			dos.writeBytes("}" + eol);
		}
		
		dos.writeBytes("source /home/gadir/script/lib/libGadir.sh;" + eol); 
			
		String archivo1Name = Utilidades.Last(archivo1.split(Utilidades.getFileSeparator()));
		String archivo2Name = Utilidades.Last(archivo2.split(Utilidades.getFileSeparator()));
		if (!Utilidades.isEmpty(archivo1)){
			dos.writeBytes("trap mifuncion ERR;" + eol);
			dos.writeBytes("export ficheroConvertido=\"" + archivo1Name + "\";" + eol);
			dos.writeBytes("/home/gadir/script/lib/conversor_utf8.sh \"" + archivo1 + "\";"  + eol);
		}
		if (!Utilidades.isEmpty(archivo2)){
			dos.writeBytes("trap mifuncion ERR;"  + eol);
			dos.writeBytes("export ficheroConvertido=\"" + archivo2Name + "\";" + eol);
			dos.writeBytes("/home/gadir/script/lib/conversor_utf8.sh \"" + archivo2 + "\";"  + eol);
		}
			
		if (LINUX.equals(tipoSistema)) {
			dos.writeBytes("export CATALINA_HOME=" + (rutaTomcat.charAt(rutaTomcat.length() - 1) == '/' ? rutaTomcat.substring(0, rutaTomcat.length() - 1) : rutaTomcat) + ";" + eol);
		}
		dos.writeBytes(llamadaJavaFusion("es.dipucadiz.etir.comun.utilidades.procesos.InnerJoinFile", archivo1, archivo2, archivoResultado, nombreArchivo) + eol);

		e_mensaje = "Se ha realizado la fusión del Tasacón " + archivo1Name + " con el de Calles " + archivo2Name;
		dos.writeBytes("lgRunPLProcNCatch \"comun_util.enviarNotificacion('"+e_co_Acm_Usuario+"', '"+e_mensaje+"', '"+e_importancia+"', '"+e_tipo+"', '"+e_co_Acm_Usuario_Remitente+"')\";" + eol);
		
		dos.close();
		fos.close();

		LOG.trace("rutaScript: " + rutaScript);

		return rutaScript;
	}
	
	private static String llamadaJavaFusion(final String claseString, final String archivo1, final String archivo2, final String archivoResultado, final String nombreArchivos) {
		final String carpetaScripts = GadirConfig.leerParametro("ruta.batch.script");
		final String rutaJava = GadirConfig.leerParametro("ruta.java");
		final String rutaTomcat = GadirConfig.leerParametro("ruta.tomcat");
		final String tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		final String barra = LINUX.equals(tipoSistema) ? "/" : "\\";
		final String separador = LINUX.equals(tipoSistema) ? ":" : ";";
		String carpetaClasses = rutaTomcat + "webapps" + barra + ETIR + barra + WEB_INF + barra + "classes";
		
		File carpetaLib = new File(rutaTomcat + "webapps" + barra + ETIR + barra + WEB_INF + barra + "lib");
		File[] archivosLib = carpetaLib.listFiles();

		if (archivosLib == null) {
			carpetaClasses = rutaTomcat + "wtpwebapps" + barra + ETIR + barra + WEB_INF + barra + "classes";
			carpetaLib = new File(rutaTomcat + "wtpwebapps" + barra + ETIR + barra + WEB_INF + barra + "lib");
			archivosLib = carpetaLib.listFiles();
		}
		
		final StringBuffer devolver = new StringBuffer(200);
		devolver.append('"');
		devolver.append(rutaJava);
		devolver.append("\" -classpath \"");
		devolver.append(carpetaClasses);
		if (archivosLib == null) {
			LOG.warn("No existen librerias en carpeta: " + carpetaLib.getAbsolutePath());
		} else {
			for (int i = 0; i < archivosLib.length; i++) {
				devolver.append(separador);
				devolver.append(archivosLib[i].getAbsolutePath());
			}
		}
		
		//jbenitac: meto a mano la libreria servlet-api, que no esta en la carpeta lib de gadir
		if (devolver.indexOf("servlet-api")<0){
			devolver.append(separador);
			devolver.append(rutaTomcat+"common/lib/servlet-api.jar");
		}
		//jbenitac: hasta aqui
		
		devolver.append("\" ");
		devolver.append(claseString);
		devolver.append(' ');
		devolver.append("\""+archivo1+"\"");
		devolver.append(' ');
		devolver.append("\""+archivo2+"\"");
		devolver.append(' ');
		devolver.append("\""+archivoResultado+"\"");
		devolver.append(" 1");
		devolver.append(" > " + carpetaScripts + nombreArchivos + ".out 2> ");
		devolver.append(carpetaScripts);
		devolver.append(nombreArchivos);
		devolver.append(".error;");
		
		return devolver.toString();
	}

	private static String ficherosCompartidos(String proceso, List<String> parametros) throws IOException {
		
		String result="";
		
		String nombre = null;
		String ruta = null;
		if (BatchConstants.CO_PROCESO_CARGA_DINAMICA.equals(proceso)) {
			ruta = parametros.get(G523CargaDinamicaAction.NUM_PARAM_RUTA);
			nombre = parametros.get(G523CargaDinamicaAction.NUM_PARAM_FICHERO);
		} else if (BatchConstants.CO_PROCESO_CARGA_JUNTA.equals(proceso)) {
			String rutaNombre = parametros.get(6);
			String[] fichero = rutaNombre.split("/");
			nombre = fichero[fichero.length - 1];
			ruta = "/";
			for (int i=0; i<fichero.length-1; i++) {
				ruta += fichero[i] + "/";
			}
		} else if (BatchConstants.CO_PROCESO_CARGA_BANCOS.equals(proceso) || BatchConstants.CO_PROCESO_ERRORES_R.equals(proceso)  || BatchConstants.CO_PROCESO_EXLCUIR_ERRORES_RGC.equals(proceso)  ) {
			nombre = parametros.get(0);
			ruta = parametros.get(1);
		} else if (BatchConstants.CO_PROCESO_CARGA_C60.equals(proceso) || BatchConstants.CO_PROCESO_CARGA_C60_COBROS_DUPLICADOS.equals(proceso) || BatchConstants.CO_PROCESO_CARGA_D19.equals(proceso) 
				|| BatchConstants.CO_PROCESO_G7A5_CARGA_DOMS.equals(proceso) || BatchConstants.CO_PROCESO_CARGA_C43.equals(proceso) || BatchConstants.CO_PROCESO_CARGA_C63.equals(proceso) 
				|| BatchConstants.CO_PROCESO_CARGA_SEGURIDADSOCIAL.equals(proceso)|| BatchConstants.CO_PROCESO_CARGA_D19_NUEVO.equals(proceso) || BatchConstants.CO_PROCESO_PAGAR_DOCUMENTOS_DEPOSITOS_FICHERO.equals(proceso) ) {
			nombre = parametros.get(0);
			ruta =Utilidades.appendIfNotYet(DatosSesion.getCarpetaAcceso(), Utilidades.getFileSeparator());
		} else if (BatchConstants.CO_PROCESO_G743_BAJA_MASIVA.equals(proceso) || BatchConstants.CO_PROCESO_G743_SUSPENSION_MASIVA.equals(proceso))  {
			nombre = parametros.get(2);
			ruta =Utilidades.appendIfNotYet(DatosSesion.getCarpetaAcceso(), Utilidades.getFileSeparator());
		} else if (BatchConstants.CO_PROCESO_G7EB_CALCULO_INTERESES_MASIVO.equals(proceso)) {
			nombre = parametros.get(1);
			ruta = Utilidades.appendIfNotYet(DatosSesion.getCarpetaAcceso(), Utilidades.getFileSeparator());
		}
		
		if (Utilidades.isNotNull(nombre)) {
			final String rutaOracle = GadirConfig.leerParametro("ruta.informes.merge");
			// Si el fichero ya existe en carpeta destino, y no existe en carpeta origen, se trata de un proceso automático que ya ha copiado el fichero previamente a la carpeta destino
			if (Fichero.existeFichero(ruta + nombre) || !Fichero.existeFichero(rutaOracle + nombre)) {
				Fichero.copiar(ruta + nombre, rutaOracle + nombre);
			}
			result = rutaOracle + nombre;
		}
		
		return result;
	}

	
	private static DatosScriptVO crearScript(final EjecucionDTO ejecucion, final String impresoraParam, final List<String> plantillasOdt, final boolean conTs, final AccesoPlantillaVO accesoPlantillaVO, final String fichero, String rutaFicheroCopiado, String rutaScript, String eol, String tipoSistema, String nombreArchivo) throws IOException, GadirServiceException {
		final String impresora = Utilidades.isNull(impresoraParam) ? DatosSesion.getImpresora() : impresoraParam;
		final String rutaTomcat = GadirConfig.leerParametro("ruta.tomcat").trim();
		final String rutaOracle = GadirConfig.leerParametro("ruta.informes.merge");
		String rutaUsuario = DatosSesion.getCarpetaAcceso();
		if (Utilidades.isEmpty(rutaUsuario)) {
			rutaUsuario = GadirConfig.leerParametro("ruta.carpetas.usuarios");
		}

		/*
		 * 12/08/2010: Comentamos la línea para que todos los lanzamientos de Batch
		 * se realicen con la inicialización del Array de ficheros de datos
		 */
		//final boolean noUsarArray = analizarExcepciones(ejecucion.getProcesoDTO().getCoProceso());
		final boolean noUsarArray = false;
		
		final File file = new File(rutaScript);
		if (file.exists()) {
			throw new GadirServiceException("El script ya existe.");
		}

		final FileOutputStream fos = new FileOutputStream(file);
		final DataOutputStream dos = new DataOutputStream(fos);

		final List<String> parametrosJava = new ArrayList<String>();
		
		parametrosJava.add(impresora);
		
		// Iterar los informes para añadirlos a los parametros.
		boolean isConInforme = false;
		boolean isConFichero = false;
		if (BatchConstants.CO_PROCESO_LANZAR_PETICION.equals(ejecucion.getProcesoDTO().getCoProceso()) ||
				BatchConstants.CO_PROCESO_CRUCES.equals(ejecucion.getProcesoDTO().getCoProceso())) {
			isConFichero = true;
		}
		boolean isProcesoCircuito = "A".equals(ejecucion.getProcesoDTO().getTipo()) || "C".equals(ejecucion.getProcesoDTO().getTipo()) || "L".equals(ejecucion.getProcesoDTO().getTipo());
		
		final List<String> shLineasLlamadaBatch = new ArrayList<String>();

		if (!noUsarArray) {
			shLineasLlamadaBatch.add("lgBeginArray arrayTxt;");
			shLineasLlamadaBatch.add("lgBeginArray arrayTipo;");
		}
		
		final StringBuffer parametrosPL = new StringBuffer(Long.toString(ejecucion.getCoEjecucion()));
		int indice=1;
		if (plantillasOdt == null || plantillasOdt.isEmpty()) {
			final String[] propNames = {"procesoDTO", "accion"};
			final Object[] filters = {ejecucion.getProcesoDTO(), accesoPlantillaVO.getAccion()};
			final List<ProcesoAccionDTO> procesoAccionDTOs = procesoAccionBO.findFiltered(propNames, filters);
			List<PlantillaDTO> plantillaDTOs;
			if (procesoAccionDTOs.isEmpty()) {
				plantillaDTOs = new ArrayList<PlantillaDTO>(0);
			} else {
				final ProcesoAccionDTO procesoAccionDTO = procesoAccionDTOs.get(0);
				plantillaDTOs = plantillaBO.findByAccesoPlantillaVO(procesoAccionDTO.getCoProcesoAccion(), accesoPlantillaVO);
			}
			if (!plantillaDTOs.isEmpty()) {
				isConInforme = true;
				Collections.sort(plantillaDTOs);
				for (PlantillaDTO plantillaDTO : plantillaDTOs) {
					final String nombreTxt = Long.toString(ejecucion.getCoEjecucion()) + "_" + accesoPlantillaVO.getAccion() + "_" + plantillaDTO.getCoPlantilla() + "." + EXTENSION_T2P;
					final String nombreOdt = plantillaDTO.getPlantillaOdtDTO().getRuta();
					if (noUsarArray) {
						parametrosPL.append(", '" + nombreTxt + "'");
					} else {
						String tipo;
						if ("E".equals(plantillaDTO.getPlantillaOdtDTO().getTipo())) {
							tipo = "\"1\"";
						} else {
							tipo = "\"0\"";
						}
						shLineasLlamadaBatch.add("lgSetArrayValue arrayTxt " + (indice) + " \"'" + nombreTxt + "'\";");
						shLineasLlamadaBatch.add("lgSetArrayValue arrayTipo " + (indice) + " " + tipo + ";");
						indice++;
					}
					parametrosJava.add(nombreTxt);
					if (conTs) {
						parametrosJava.add("\"" + nombreOdt + "\"");
					} else {
						parametrosJava.add(nombreOdt);
					}
					parametrosJava.add(plantillaDTO.getCoPlantilla().toString());
				}
			}
		} else {
			for (int i=0; i<plantillasOdt.size(); i++) {
				isConInforme = true;
				final String nombreTxt = Long.toString(ejecucion.getCoEjecucion()) + "_" + i + "." + EXTENSION_T2P;
				final String nombreOdt = plantillasOdt.get(i);
				if (noUsarArray) {
					parametrosPL.append(", '" + nombreTxt + "'");
				} else {
					shLineasLlamadaBatch.add("lgSetArrayValue arrayTxt " + (i + 1) + " \"'" + nombreTxt + "'\";");
					shLineasLlamadaBatch.add("lgSetArrayValue arrayTipo " + (i + 1) + " 0;");
				}
				parametrosJava.add(nombreTxt);
				parametrosJava.add("\"" + nombreOdt + "\"");
				parametrosJava.add("0");
			}
		}
		
		// Excepciones plantillas inicio
		if (BatchConstants.CO_PROCESO_IMPR_ALTA_CENSO.equals(ejecucion.getProcesoDTO().getCoProceso()) || BatchConstants.CO_PROCESO_REIMPR_ALTA_CENSO.equals(ejecucion.getProcesoDTO().getCoProceso())) {
			isConInforme = true;
			String nombreTxt = Long.toString(ejecucion.getCoEjecucion()) + "_" + indice + "." + EXTENSION_T2P;
			shLineasLlamadaBatch.add("lgSetArrayValue arrayTxt " + indice + " \"'" + nombreTxt + "'\";");
			shLineasLlamadaBatch.add("lgSetArrayValue arrayTipo " + indice + " 0;");
			parametrosJava.add(nombreTxt);
			parametrosJava.add("\"" + PlantillaOdtConstants.EXTRACCION_APAISADO + "\"");
			parametrosJava.add("0");
			indice++;
		}
		// Excepciones plantillas fin
		
		if (noUsarArray) {
			shLineasLlamadaBatch.add("lgRunPLProcNCatch \"" + ejecucion.getProcesoDTO().getCoPlsql() + "(" + parametrosPL + ")\";");
		} else {
			shLineasLlamadaBatch.add("lgRunPLProcDArrayNCatch lgArrayParams_arrayTxt lgArrayParams_arrayTipo \"" + ejecucion.getProcesoDTO().getCoPlsql() + "(" + Long.toString(ejecucion.getCoEjecucion()) + ", lgArrayParams_arrayTxt, lgArrayParams_arrayTipo)\";");
			shLineasLlamadaBatch.add("lgPurgeArray arrayTxt;");
			shLineasLlamadaBatch.add("lgPurgeArray arrayTipo;");
		}

		// Empezamos a escribir en el sh.
		if (!Utilidades.isEmpty(rutaFicheroCopiado)){
			dos.writeBytes("#!/bin/bash" + eol);
			dos.writeBytes("function mifuncion () {" + eol);
			dos.writeBytes(LG_RUN_PL_FUNC_N_CATCH + " \"comun_util.Batch_setEstado(" + ejecucion.getCoEjecucion() + ", 'F', '9500', 'Codificacion de archivo incorrecta', '" + ejecucion.getCoAcmUsuario() + "')" + "\";" + eol);
			dos.writeBytes("exit;" + eol);
			dos.writeBytes("}" + eol);
		}
		
		dos.writeBytes("source /home/gadir/script/lib/libGadir.sh;" + eol);
			
		if (!Utilidades.isEmpty(rutaFicheroCopiado)){
			dos.writeBytes("trap mifuncion ERR;"  + eol);
			dos.writeBytes("/home/gadir/script/lib/conversor_utf8.sh \"" + rutaFicheroCopiado + "\";"  + eol);
			//dos.writeBytes("dos2unix \"" + rutaFicheroCopiado + "\";"  + eol);
			/*dos.writeBytes("rm \"" + rutaFicheroCopiado + "\" > a3.txt;"  + eol);
			dos.writeBytes("mv \"tmp_" + rutaFicheroCopiado + "\" \"" + rutaFicheroCopiado + "\" > a4.txt;"  + eol);*/
		}
		
		
		dos.writeBytes(LG_RUN_PL_FUNC_N_CATCH + " \"comun_util.Batch_setEstado(" + ejecucion.getCoEjecucion() + ", 'I', '" + ejecucion.getCoAcmUsuario() + "')" + "\";" + eol);
		for (String shLineaLlamadaBatch : shLineasLlamadaBatch) {
			dos.writeBytes(shLineaLlamadaBatch + eol);
		}
		// Si el proceso tiene informes, añadir pasos necesarios al sh
		if (isConInforme || isConFichero || isProcesoCircuito) {
			dos.writeBytes("lgRunPLFuncNCatch \"comun_util.Batch_setEstado(" + ejecucion.getCoEjecucion() + ", 'M', '" + ejecucion.getCoAcmUsuario() + "')\";" + eol);
			if (conTs) {
				if (LINUX.equals(tipoSistema)) {
					dos.writeBytes("export CATALINA_HOME=" + (rutaTomcat.charAt(rutaTomcat.length() - 1) == '/' ? rutaTomcat.substring(0, rutaTomcat.length() - 1) : rutaTomcat) + ";" + eol);
				}
				dos.writeBytes(llamadaJava("es.dipucadiz.etir.comun.utilidades.PostBatch", nombreArchivo, ejecucion, parametrosJava) + eol);
				dos.writeBytes("lgTestFile \"" + nombreArchivo + ".error\";" + eol);
			}
		}
		
		// Mover fichero a la carpeta del usuario
		if (Utilidades.isNotNull(fichero)) {
			dos.writeBytes("mv " + rutaOracle + fichero + " " + rutaUsuario + fichero + eol);
		}
		
		if (conTs) {
			dos.writeBytes("lgRunPLFuncNCatch \"comun_util.Batch_setEstado(" + ejecucion.getCoEjecucion() + ", 'F', '" + ejecucion.getCoAcmUsuario() + "')\";" + eol);
		}
		dos.writeBytes("lgExitOK;" + eol);

		dos.close();
		fos.close();

		LOG.trace("rutaScript: " + rutaScript);
		
		final DatosScriptVO datosScriptVO = new DatosScriptVO();
		datosScriptVO.setRuta(rutaScript);
		datosScriptVO.setParametrosJava(parametrosJava);
		return datosScriptVO;
	}
	
	/*private static DatosScriptVO crearScript(final EjecucionDTO ejecucion, final String impresoraParam, final List<String> plantillasOdt, final boolean conTs, final AccesoPlantillaVO accesoPlantillaVO, final String fichero) throws IOException, GadirServiceException {
		return crearScript(ejecucion, impresoraParam, plantillasOdt, conTs, accesoPlantillaVO, fichero, "");
	}*/

	/*
	private static boolean analizarExcepciones(final String coProceso) {
		boolean result;
		if ("G5".equals(coProceso.substring(0, 2))) {
			if (coProceso.equals(BatchConstants.CO_PROCESO_CARGA_DINAMICA) ||
					coProceso.equals(BatchConstants.CO_PROCESO_DATOS_ANOS_ANTERIORES) ||
					coProceso.equals(Batch.CO_ACT_MASIVA_DOCUMENTOS)) {
				result = false;
			} else {
				result = true;
			}
		} else {
			result = false;
		}
		return result;
	}
	*/

	private static String llamadaJava(final String claseString, final String nombreArchivos, final EjecucionDTO ejecucion, final List<String> parametrosJava) {
		final String carpetaScripts = GadirConfig.leerParametro("ruta.batch.script");
		final String rutaJava = GadirConfig.leerParametro("ruta.java");
		final String rutaTomcat = GadirConfig.leerParametro("ruta.tomcat");
		final String tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		final String barra = LINUX.equals(tipoSistema) ? "/" : "\\";
		final String separador = LINUX.equals(tipoSistema) ? ":" : ";";
		String carpetaClasses = rutaTomcat + "webapps" + barra + ETIR + barra + WEB_INF + barra + "classes";
		
		File carpetaLib = new File(rutaTomcat + "webapps" + barra + ETIR + barra + WEB_INF + barra + "lib");
		File[] archivosLib = carpetaLib.listFiles();

		if (archivosLib == null) {
			carpetaClasses = rutaTomcat + "wtpwebapps" + barra + ETIR + barra + WEB_INF + barra + "classes";
			carpetaLib = new File(rutaTomcat + "wtpwebapps" + barra + ETIR + barra + WEB_INF + barra + "lib");
			archivosLib = carpetaLib.listFiles();
		}
		
		final StringBuffer devolver = new StringBuffer(200);
		devolver.append('"');
		devolver.append(rutaJava);
		devolver.append("\" -classpath \"");
		devolver.append(carpetaClasses);
		if (archivosLib == null) {
			LOG.warn("No existen librerias en carpeta: " + carpetaLib.getAbsolutePath());
		} else {
			for (int i = 0; i < archivosLib.length; i++) {
				devolver.append(separador);
				devolver.append(archivosLib[i].getAbsolutePath());
			}
		}
		
		//jbenitac: meto a mano la libreria servlet-api, que no esta en la carpeta lib de gadir
		if (devolver.indexOf("servlet-api")<0){
			devolver.append(separador);
			devolver.append(rutaTomcat+"common/lib/servlet-api.jar");
		}
		//jbenitac: hasta aqui
		
		devolver.append("\" ");
		devolver.append(claseString);
		devolver.append(' ');
		devolver.append(ejecucion.getCoEjecucion());
		for (String parametro : parametrosJava) {
			devolver.append(' ');
			devolver.append(parametro);
		}
		devolver.append(" > " + carpetaScripts + nombreArchivos + ".out 2> ");
		devolver.append(carpetaScripts);
		devolver.append(nombreArchivos);
		devolver.append(".error;");
		
		return devolver.toString();
	}
	
	/**
	 * Ejecuta el script, con o sin ts (cola de ejecución).
	 * @param rutaScript
	 * @param conTs
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static int ejecutarScript(final String rutaScript, final boolean conTs, final String cola, final boolean isPostBatch) throws IOException, InterruptedException {
		return ejecutarScript(rutaScript, conTs, true, cola, isPostBatch);
	}
	public static int ejecutarScript(final String rutaScript, final boolean conTs, final boolean conSudo, final String cola, final boolean isPostBatch) throws IOException, InterruptedException {
		final String tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");

		final Runtime runtime = Runtime.getRuntime();

		if (LINUX.equals(tipoSistema)) {
			final Process proc1 = runtime.exec("/bin/chmod 777 " + rutaScript);
			proc1.waitFor();
		}

		final StringBuffer exec = new StringBuffer();
		if (WINDOWS.equals(tipoSistema)) {
			exec.append("cmd /c start ");
			exec.append(rutaScript);
		} else {
			if (conTs && !isPostBatch) {
				final String cadenaConexionBatch = GadirConfig.leerParametro("servidor.batch.cadena.conexion");
				if (Utilidades.isNotEmpty(cadenaConexionBatch)) {
					exec.append("/usr/bin/ssh ");
					exec.append(cadenaConexionBatch);
					exec.append(" ");
				}
			}
			if(conSudo && !isPostBatch && conTs) {
				exec.append("/usr/bin/sudo -u ");
				exec.append(cola);
				exec.append(' ');
			};
			if (conTs) {
				exec.append("/usr/local/bin/ts ");
			}
			exec.append(rutaScript);
		}
//		LOG.error("###########################################################################");
		LOG.warn(exec.toString());
//		LOG.error("###########################################################################");
		LOG.debug("Ejecuta script: " + exec.toString());
		final Process process = runtime.exec(exec.toString());
		if (process.waitFor() != 0) {
			LOG.error("exitValue: " + process.exitValue());
		}
		return process.exitValue();	// 0 = correcto
	}
	
	/**
	 * En PL/SQL: comun_util.Batch_setEstado
	 * @param codigoEjecucion
	 * @param estado
	 */
	public static void setEstado(final int codigoEjecucion, final char estado) {
		setEstado(codigoEjecucion, estado, 0);
	}
	/**
	 * En PL/SQL: comun_util.Batch_setEstado
	 * @param codigoEjecucion
	 * @param estado
	 * @param codigoError
	 */
	public static void setEstado(final int codigoEjecucion, final char estado, final int codigoError) {
		setEstado(codigoEjecucion, estado, codigoError, DatosSesion.getLogin());
	}
	/**
	 * En PL/SQL: comun_util.Batch_setEstado
	 * @param codigoEjecucion
	 * @param estado
	 * @param codigoError
	 * @param usuario
	 */
	public static void setEstado(final int codigoEjecucion, final char estado, final int codigoError, final String usuario) {
		setEstado(codigoEjecucion, estado, codigoError, usuario, null);
	}

	/**
	 * En PL/SQL: comun_util.Batch_setEstado
	 * @param codigoEjecucion
	 * @param estado
	 * @param codigoError
	 * @param usuario
	 * @param observaciones
	 */
	public static void setEstado(final int codigoEjecucion, final char estado, final int codigoError, final String usuario, final String observaciones) {
		batchSetEstadoBO.execute(codigoEjecucion, estado, codigoError, usuario, observaciones);
	}

	
	public static BatchSetEstadoBO getBatchSetEstadoBO() {
		return batchSetEstadoBO;
	}
	public void setBatchSetEstadoBO(final BatchSetEstadoBO batchSetEstadoBO) {
		Batch.batchSetEstadoBO = batchSetEstadoBO;
	}
	public static EjecucionBO getEjecucionBO() {
		return ejecucionBO;
	}
	public void setEjecucionBO(final EjecucionBO ejecucionBO) {
		Batch.ejecucionBO = ejecucionBO;
	}

	public void setProcesoAccionBO(final ProcesoAccionBO procesoAccionBO) {
		Batch.procesoAccionBO = procesoAccionBO;
	}

	public static ProcesoAccionBO getProcesoAccionBO() {
		return procesoAccionBO;
	}

	public void setPlantillaBO(final PlantillaBO plantillaBO) {
		Batch.plantillaBO = plantillaBO;
	}

	public static PlantillaBO getPlantillaBO() {
		return plantillaBO;
	}

	public static void setProcesoBO(ProcesoBO procesoBO) {
		Batch.procesoBO = procesoBO;
	}

	public static ProcesoBO getProcesoBO() {
		return procesoBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		Batch.acmUsuarioBO = acmUsuarioBO;
	}

	public static AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}



}


