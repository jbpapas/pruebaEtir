package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.BDDocumentalBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.RemesaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTO;
import es.dipucadiz.etir.comun.dto.RemesaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.gdtel.gestor.GestionDocumentosSimple;
import es.gdtel.gestor.alfrescoImplWS.InterfazAlfrescoWS;

final public class NotificacionElectronicaService {

	private static BDDocumentalBO bdDocumentalBO;
	private static EjecucionBO ejecucionBO;
	private static AcmUsuarioBO acmUsuarioBO;
	private static RemesaBO remesaBO;
	static public String tipoSistema = null;

	static private int T2W_NE_POSICION_CO_BDDOC_GRUPO = 1;
	static private int T2W_NE_POSICION_RAZON_SOCIAL = 2;
	static private int T2W_NE_POSICION_CODIGO_POSTAL = 3;
	static private int T2W_NE_POSICION_TIPO_VIA = 4;
	static private int T2W_NE_POSICION_DIRECCION = 5;
	static private int T2W_NE_POSICION_IDENTIFICADOR = 6;
	static private int T2W_NE_POSICION_TIPO_IDENTIFICADOR = 7;
	static private int T2W_NE_POSICION_MUNICIPIO = 8;
	static private int T2W_NE_POSICION_PROVINCIA = 9;
	static private int T2W_NE_POSICION_MOVIL = 10;
	static private int T2W_NE_POSICION_EMAIL = 11;
	static private int T2W_NE_POSICION_CANAL_NOTIFICACION = 12;
	static private int T2W_NE_POSICION_CODIGO_BARRAS = 13;
	static private int T2W_NE_POSICION_TIPO_NOTIFICACION = 14;
	static private int T2W_NE_POSICION_ZONA_OFICINA_VIRTUAL = 15;
	static private int T2W_NE_POSICION_EXPEDIENTE_OFICINA_VIRTUAL = 16;
	static private int T2W_NE_POSICION_MUNICIPIO_OFICINA_VIRTUAL = 17;
	static private String eol = "\n";
	static private String separador = "·";
	static private String ALFRESCO_PREFIJO_NOMBRE_CARPETA = "NOTSEDE_";
	static private String ALFRESCO_SUFIJO_NOMBRE_CARPETA = ".txt";
	static private String ALFRESCO_PREFIJO_NOMBRE_ARCHIVO = "firmado-";
	static private String ALFRESCO_SEPARADOR_NOMBRE_ARCHIVO = "-";

	private static final Log LOG = LogFactory.getLog(NotificacionElectronicaService.class);

	public static void publicar(BufferedReader input, String coProceso, String coEjecucion) {
		GestionDocumentosSimple interfazSimple = null;
		BufferedWriter ficheroPublicacionSede = null;
		int numLinea = 0;
		String hayError = null;
		final String rutaCarpetaTxtTmp = GadirConfig.leerParametro("ruta.informes.merge");
		final String rutaCarpetaTxtOficina = GadirConfig.leerParametro("ruta.oficina.notificaciones");
		String notsedeFilename = ALFRESCO_PREFIJO_NOMBRE_CARPETA + Utilidades.dateToYYYYMMDDHHMMSS(Utilidades.getDateActual()) + ALFRESCO_SUFIJO_NOMBRE_CARPETA;
		
		try {
			final File ficheroSalida = new File(rutaCarpetaTxtTmp + notsedeFilename);
			ficheroPublicacionSede = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroSalida), Impresion.CODIFICACION));
			AlfrescoWS alfrescoWS = new AlfrescoWS();

			//CREAR CARPETA ALFRESCO
			interfazSimple = new InterfazAlfrescoWS();
			interfazSimple.iniciarSesion();
			String uuidCarpetaAlfresco = alfrescoWS.crearCarpeta(coProceso + '/' + coEjecucion + "-" + Utilidades.dateToYYYYMMDDHHMMSS(Utilidades.getDateActual()), interfazSimple);

			String line;
			while ((line = input.readLine()) != null) {
				numLinea++;
				line = line.trim();
				if (Utilidades.isNotEmpty(line)) {
					String[] palabras = line.split(separador);
					boolean incluirEnFichero = true;
					
					// No enviar identificadores erróneos
					if (Utilidades.isEmpty(palabras[T2W_NE_POSICION_TIPO_IDENTIFICADOR])) incluirEnFichero = false;
					// No enviar NIF ficticios
					if ("NIF".equals(palabras[T2W_NE_POSICION_TIPO_IDENTIFICADOR]) && palabras[T2W_NE_POSICION_IDENTIFICADOR].startsWith("W")) incluirEnFichero = false;
					
					BDDocumentalDTO bdDocumentalDTO;
					if (palabras[T2W_NE_POSICION_CO_BDDOC_GRUPO].startsWith("G:")) {
						String[] split = palabras[T2W_NE_POSICION_CO_BDDOC_GRUPO].split(":", 2);
						Long coBDDocumentalGrupo = Long.valueOf(split[1]);
						bdDocumentalDTO = bdDocumentalBO.findFiltered("bdDocumentalGrupoDTO.coBDDocumentalGrupo", coBDDocumentalGrupo, 0, 1).get(0);
					} else {
						Long coBDDocumental = Long.valueOf(palabras[T2W_NE_POSICION_CO_BDDOC_GRUPO]);
						bdDocumentalDTO = bdDocumentalBO.findById(coBDDocumental);
					}

					if (bdDocumentalDTO.getFhPublicado() == null || bdDocumentalDTO.getIdAlfrescoFirmado() == null) {
						DataSource ds = new BDDocumentalDataSource(bdDocumentalDTO);

						//FIRMA DOCUMENTO PADES
						byte[] resultado = FirmaPdf.firmar(ds.getInputStream());
						if (resultado == null || resultado.length == 0) {
							throw new GadirServiceException("FirmaPdf.firmar ha devuelto null (" + bdDocumentalDTO.getCoBDDocumental() + ")");
						}

						// Escribir PDF firmado a disco
						//File fileOut = new File(carpetaOut + fileName);
						//FileUtils.writeByteArrayToFile(fileOut, resultado);

						// GUARDAR PDF EN ALFRESCO
						String fileName = ALFRESCO_PREFIJO_NOMBRE_ARCHIVO + System.currentTimeMillis() + ALFRESCO_SEPARADOR_NOMBRE_ARCHIVO + bdDocumentalDTO.getNombre();
						es.gdtel.gestor.util.Fichero fichero = new es.gdtel.gestor.util.Fichero();
						fichero.setNombre(fileName);
						fichero.setTipoMime("application/pdf");
						fichero.setContenido(resultado);
						Map<String, Object> datos = new HashMap<String, Object>();
						datos.put(AlfrescoWS.ETIR_CODIGO_BARRAS, palabras[T2W_NE_POSICION_CODIGO_BARRAS]);
						datos.put(AlfrescoWS.ETIR_EMAIL, palabras[T2W_NE_POSICION_EMAIL]);
						datos.put(AlfrescoWS.ETIR_IDENTIFICADOR, palabras[T2W_NE_POSICION_IDENTIFICADOR]);
						datos.put(AlfrescoWS.ETIR_MOVIL, palabras[T2W_NE_POSICION_MOVIL]);
						datos.put(AlfrescoWS.ETIR_RAZON_SOCIAL, palabras[T2W_NE_POSICION_RAZON_SOCIAL]);
						datos.put(AlfrescoWS.ETIR_TIPO_DOCUMENTO, palabras[T2W_NE_POSICION_TIPO_NOTIFICACION]);
						datos.put(AlfrescoWS.ETIR_TIPO_IDENTIFICADOR, palabras[T2W_NE_POSICION_TIPO_IDENTIFICADOR]);
						datos.put(AlfrescoWS.ETIR_CANAL_NOTIFICACION, palabras[T2W_NE_POSICION_CANAL_NOTIFICACION]);
						String idAlfresco = alfrescoWS.almacenarDocumento(uuidCarpetaAlfresco, fichero, datos, interfazSimple, bdDocumentalDTO.getCoBDDocumental());
						if (idAlfresco == null) {
							throw new GadirServiceException("AlfrescoService.subirDocumento ha devuelto null (" + palabras[T2W_NE_POSICION_CO_BDDOC_GRUPO] + ")");
						}

						//PUBLICACION EN SEDE
						String apellido1;
						String apellido2;
						String nombre;
						if ("CIF".equals(palabras[T2W_NE_POSICION_TIPO_IDENTIFICADOR])) {
							apellido1 = palabras[T2W_NE_POSICION_RAZON_SOCIAL];
							apellido2 = "";
							nombre = "";
						} else {
							//	String[] nombreSeparado = Utilidades.separarRazonSocial(palabras[T2W_NE_POSICION_RAZON_SOCIAL]);
							@SuppressWarnings("unchecked")
							List<String> tmpNombreSeparado = (ArrayList<String>) bdDocumentalBO
									.ejecutaQuerySelect("SELECT PQ_COMPARA_CADENAS.razon_social_dividida('" + palabras[T2W_NE_POSICION_RAZON_SOCIAL].replace("'", "''") + "') FROM DUAL");
							String[] nombreSeparado = tmpNombreSeparado.get(0).split("·", 3);
							apellido1 = nombreSeparado[0];
							apellido2 = nombreSeparado[1];
							nombre = nombreSeparado[2];
						}

						if (incluirEnFichero) {
							ficheroPublicacionSede.write(
									apellido1 + separador +    // Apellido 1
									apellido2 + separador +    // Apellido 2
									nombre + separador +       // Nombre
									palabras[T2W_NE_POSICION_CODIGO_POSTAL] + separador +  // Código postal
									palabras[T2W_NE_POSICION_TIPO_VIA] + separador +  // Tipo de vía
									palabras[T2W_NE_POSICION_DIRECCION] + separador +  // Dirección
									palabras[T2W_NE_POSICION_IDENTIFICADOR] + separador +  // Identificador
									palabras[T2W_NE_POSICION_TIPO_IDENTIFICADOR] + separador +  // Tipo de identificador ("NIF"/"NIE"/"Pasaporte"/"CIF")
									palabras[T2W_NE_POSICION_MUNICIPIO] + separador +  // Municipio
									palabras[T2W_NE_POSICION_PROVINCIA] + separador +  // Provincia
									palabras[T2W_NE_POSICION_MOVIL] + separador +  // Móvil
									palabras[T2W_NE_POSICION_EMAIL] + separador + // Email
									palabras[T2W_NE_POSICION_CANAL_NOTIFICACION] + separador + // Tipo de notificación - exclusivamente electrónica ("E") o no ("P")
									idAlfresco + separador +           // Identificador de Alfresco del documento a notificar
									palabras[T2W_NE_POSICION_TIPO_NOTIFICACION] + separador +          // Identificador de Alfresco del documento a notificar
									palabras[T2W_NE_POSICION_ZONA_OFICINA_VIRTUAL] + separador +          // Zona (Cádiz)
									palabras[T2W_NE_POSICION_EXPEDIENTE_OFICINA_VIRTUAL] + separador +          // Expediente (7 últimos del código de barras)
									palabras[T2W_NE_POSICION_MUNICIPIO_OFICINA_VIRTUAL] + eol           // Municipio (Cádiz)
							);
						}

						bdDocumentalDTO.setFhPublicado(Utilidades.getDateActual());
						bdDocumentalDTO.setIdAlfrescoFirmado(idAlfresco);
						bdDocumentalBO.save(bdDocumentalDTO);
					}
				}
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			Batch.setEstado(Integer.valueOf(coEjecucion), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), MensajeConstants.ERROR_COMUNICACION_WS_EXTERNO, DatosSesion.getLogin(), e.getMessage());
			hayError = e.getMessage();
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			Batch.setEstado(Integer.valueOf(coEjecucion), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), MensajeConstants.ERROR_COMUNICACION_WS_EXTERNO, DatosSesion.getLogin(), e.getMensaje());
			hayError = e.getMensaje();
		} finally {
			if (interfazSimple != null) {
				interfazSimple.terminarSesion();
			}
			if (ficheroPublicacionSede != null) {
				try {
					ficheroPublicacionSede.close();
					Fichero.copiar(rutaCarpetaTxtTmp + notsedeFilename, rutaCarpetaTxtOficina + notsedeFilename);
				} catch (IOException e) {
					hayError = "ERROR GRAVE " + e.getMessage();
					Batch.setEstado(Integer.valueOf(coEjecucion), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), MensajeConstants.ERROR_COMUNICACION_WS_EXTERNO, DatosSesion.getLogin(), e.getMessage());
					LOG.error(e.getMessage(), e);
				}
			}
		}
		
		try {
			EjecucionDTO ejecucionDTO = ejecucionBO.findByIdInitialized(Long.valueOf(coEjecucion), new String[] {"ejecucionParametroDTOs"});
			// Cambiar estado de remesas
			finalizarPublicacion(ejecucionDTO, hayError);
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
			Batch.setEstado(Integer.valueOf(coEjecucion), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), MensajeConstants.ERROR_COMUNICACION_WS_EXTERNO, DatosSesion.getLogin(), e.getMessage());
		} catch (GadirServiceException e) {
			LOG.error(e.getMessage(), e);
			Batch.setEstado(Integer.valueOf(coEjecucion), ElementoGtConstants.ELEMENTO_GT_ESTEJECU_ERROR_EN_MERGE.charAt(0), MensajeConstants.ERROR_COMUNICACION_WS_EXTERNO, DatosSesion.getLogin(), e.getMensaje());
		}		
		
		LOG.warn("Publicar notificación electrónica terminado: " + System.currentTimeMillis() + ", " + numLinea + " lineas leidas.");
	}

	// Cambiar estado de remesas
	private static void finalizarPublicacion(EjecucionDTO ejecucionDTO, String hayError) throws GadirServiceException {
		List<String> remesas = new ArrayList<String>();
		for (EjecucionParametroDTO paramDTO : ejecucionDTO.getEjecucionParametroDTOs()) {
			remesas.addAll(Arrays.asList(paramDTO.getValor().split("\\|")));
		}
		
		boolean remesaIndividual = false;
		String texto = "El proceso de publicación en sede electrónica ha terminado" + (hayError!=null ? " CON ERRORES. Revise el proceso batch. (" + hayError + ")" : ".") + "\n\n";
		for (String remesa : remesas) {
			remesa = remesa.trim();
			if (Utilidades.isEmpty(remesa)) continue;
			
			remesaIndividual = remesa.charAt(4)=='Z' && remesa.charAt(5)=='Z';
			if (remesaIndividual) continue; // Remesas individuales no cambian de estado
			
			String query = "select count(distinct co_bd_documental) " +
							"from GA_DOCUMENTO_NOTIFICACION notif " +
							"inner join GA_BD_DOCUMENTAL bddoc on notif.CO_BD_DOCUMENTAL_GRUPO = bddoc.CO_BD_DOCUMENTAL_GRUPO " +
							"inner join GA_REMESA_CARGO_CANAL_RES canal on notif.CO_REMESA_CARGO_CANAL_RES = canal.CO_REMESA_CARGO_CANAL_RES " +
							"where canal.CO_REMESA='" + remesa + "' and canal.CANAL_NOTIFICACION in ('P','E') and bddoc.FH_PUBLICADO is null";
			Object object = bdDocumentalBO.ejecutaQuerySelect(query);
			@SuppressWarnings("unchecked")
			List<BigDecimal> lista = (List<BigDecimal>) object;
			int count = lista.get(0).intValue();
			if (count == 0) {
				RemesaDTO remesaDTO = remesaBO.findById(remesa);
				remesaDTO.setCoEstado("L");
				remesaBO.save(remesaDTO);
				texto += "  - Remesa " + remesa + ": No quedan notificaciones por publicar.\n";
			} else {
				texto += "  - Remesa " + remesa + ": Quedan " + count + " notificaciones por publicar.\n";
			}
		}
		
		//Enviar e-mail al usuario de la petición
		if (!remesaIndividual) {
			AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findById(ejecucionDTO.getCoAcmUsuario());
			System.err.println("Para: " + acmUsuarioDTO.getEmail());
			System.err.println("Texto: " + texto);
			Impresion.enviarCorreo(null, acmUsuarioDTO.getEmail(), "Publicación en sede electrónica" + (hayError!=null ? " CON ERRORES" : ""), texto);
		}
	}

	public static BDDocumentalBO getBdDocumentalBO() {
		return bdDocumentalBO;
	}

	public void setBdDocumentalBO(BDDocumentalBO bdDocumentalBO) {
		NotificacionElectronicaService.bdDocumentalBO = bdDocumentalBO;
	}

	public static EjecucionBO getEjecucionBO() {
		return ejecucionBO;
	}

	public void setEjecucionBO(EjecucionBO ejecucionBO) {
		NotificacionElectronicaService.ejecucionBO = ejecucionBO;
	}

	public static AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		NotificacionElectronicaService.acmUsuarioBO = acmUsuarioBO;
	}

	public static RemesaBO getRemesaBO() {
		return remesaBO;
	}

	public void setRemesaBO(RemesaBO remesaBO) {
		NotificacionElectronicaService.remesaBO = remesaBO;
	}

}
