package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.FTPDatosVO;

public class FTPService {
	private static final Log LOG = LogFactory.getLog(FTPService.class);
	
	public final static String FTP_SERVIDOR_SIGRE = "SIGRE";
	
	private final static String FTP_DIRECCION_SIGREETIR = "172.22.8.188";
	private final static String FTP_USUARIO_SIGREETIR = "jonr_eTIR";
	private final static String FTP_PWD_SIGREETIR = "j2410r";
	
	private static String entorno;

	public FTPService() {
		create();
	}

	public static void create() {
		LOG.info("Iniciando FTPService.");
		entorno = GadirConfig.leerParametro("entorno.servidor");
	}
	
	private static FTPDatosVO getDatosFTP(String servidor, String carpetaServidor) {
		FTPDatosVO vo = new FTPDatosVO();

	    // Incluir aquí mas else según necesidades de nuevos servidores FTP.
	    if (FTP_SERVIDOR_SIGRE.equals(servidor)) {
		    vo.setFtp(FTP_DIRECCION_SIGREETIR);
		    vo.setUser(FTP_USUARIO_SIGREETIR);
		    vo.setPassword(FTP_PWD_SIGREETIR);
		    
		    if ("produccion".equals(entorno)) {
		    	vo.setDirectorio(carpetaServidor);
		    } else {
		    	vo.setDirectorio("/prueba/");
		    }
	    }

		return vo;
	}
	
	public static void enviar(String fichero, String servidor, String carpetaLocal, String carpetaServidor, boolean eliminarOrigen) throws GadirServiceException {
		if (!(new File(carpetaLocal + fichero).exists())) {
			throw new GadirServiceException("No se encuentra el fichero " + carpetaLocal + fichero + " para su envío a ftp " + servidor + ", " + carpetaServidor);
		}
		
	    FTPDatosVO ftpDatosVO = getDatosFTP(servidor, carpetaServidor);
	    if (FTP_SERVIDOR_SIGRE.equals(servidor)) {
		    try {
				Fichero.utf8ToAnsi(carpetaLocal + fichero);
			} catch (IOException e) {
				throw new GadirServiceException("Error al convertir fichero " + carpetaLocal + fichero + " a formato Ansi.", e);
			}
	    }

	    if (Utilidades.isEmpty(fichero)) {
	    	throw new GadirServiceException("Nombre de fichero a enviar por FTP " + servidor + " en blanco.");
	    }
	    if (Utilidades.isEmpty(ftpDatosVO.getFtp()) || Utilidades.isEmpty(ftpDatosVO.getUser()) || Utilidades.isEmpty(ftpDatosVO.getPassword()) || Utilidades.isEmpty(ftpDatosVO.getDirectorio())) {
	    	throw new GadirServiceException("Revisa FTP con destino " + servidor + ".");
	    }
	    
	    try {
		    FTPClient client = new FTPClient();
			client.connect(ftpDatosVO.getFtp());
			
			// Login
			boolean login = client.login(ftpDatosVO.getUser(), ftpDatosVO.getPassword());
		    if (!login) {
		    	client.logout();
		    	throw new GadirServiceException("Login del FTP incorrecto.");
		    }
		    int reply = client.getReplyCode();
		    if (!FTPReply.isPositiveCompletion(reply)) {
		    	client.disconnect();
		    	throw new GadirServiceException("FTP login devuelve código " + reply + ".");
		    }
		    
		    // Cambiar directorio
		    client.changeWorkingDirectory(ftpDatosVO.getDirectorio());
		    reply = client.getReplyCode();
		    if (!FTPReply.isPositiveCompletion(reply)) {
		    	client.disconnect();
		    	throw new GadirServiceException("FTP cambiar directorio a " + ftpDatosVO.getDirectorio() + " devuelve código " + reply + ".");
		    }
		    
		    // Copiar fichero
		    InputStream input = new FileInputStream(new File(carpetaLocal + fichero));
		    boolean ficheroCopiado = client.storeFile(fichero, input);
		    if (!ficheroCopiado) {
		    	throw new GadirServiceException("El fichero " + carpetaLocal + fichero + " no se ha podido copiar a " + ftpDatosVO.getDirectorio() + fichero + " en el servidor.");
		    }
		    reply = client.getReplyCode();
		    if (!FTPReply.isPositiveCompletion(reply)) {
		    	client.disconnect();
		    	throw new GadirServiceException("FTP copiar fichero devuelve código " + reply + ".");
		    }
		    input.close();
		    
		    // Logout
		    client.logout();
		    client.disconnect();
		    
		    if (eliminarOrigen) {
		    	String carpetaMovidos = carpetaLocal + "movidos";
		    	Fichero.crearCarpeta(carpetaMovidos);
		    	Fichero.mover(carpetaLocal + fichero, carpetaMovidos);
		    }
	    } catch (IOException e) {
	    	throw new GadirServiceException(e.getMessage(), e);
	    }
	}


	public static List<String> procesarFiles(String servidor, String carpetaServidor, final String filtroBusqueda, String carpetaLocal) throws GadirServiceException {
		List<String> listaFicheros = new ArrayList<String>();
	    FTPDatosVO ftpDatosVO = getDatosFTP(servidor, carpetaServidor);
		
	    if (Utilidades.isEmpty(ftpDatosVO.getFtp()) || Utilidades.isEmpty(carpetaServidor)) {
	    	throw new GadirServiceException("Revisa FTP de " + servidor + ".");
	    }

	    String carpetaProcesados = ftpDatosVO.getDirectorio() + "procesados/";
	    boolean success;
	    
	    try {
		    FTPClient client = new FTPClient();
		    FTPFileFilter filter = new FTPFileFilter() {
				@Override
				public boolean accept(FTPFile ftpFile) {
					boolean result = false;
					if (ftpFile.isFile()) {
						result = Utilidades.isEmpty(filtroBusqueda) || ftpFile.getName().matches("^"+filtroBusqueda.replace("*", ".*")+"$");
					}
					return result;
				}
		    };
		    
			client.connect(ftpDatosVO.getFtp());

			// Login
			boolean login = client.login(ftpDatosVO.getUser(), ftpDatosVO.getPassword());
		    if (!login) {
		    	client.logout();
		    	throw new GadirServiceException("Login del FTP incorrecto.");
		    }
		    int reply = client.getReplyCode();
		    if (!FTPReply.isPositiveCompletion(reply)) {
		    	client.disconnect();
		    	throw new GadirServiceException("FTP login devuelve código " + reply + ".");
		    }

//		    // Cambiar directorio
//		    client.changeWorkingDirectory(ftpDatosVO.getDirectorio());
//		    reply = client.getReplyCode();
//		    if (!FTPReply.isPositiveCompletion(reply)) {
//		    	client.disconnect();
//		    	throw new GadirServiceException("FTP cambiar directorio a " + ftpDatosVO.getDirectorio() + " devuelve código " + reply + ".");
//		    }
		    
		    // Obtener lista de ficheros en carpeta
		    FTPFile[] ftpFiles = client.listFiles(ftpDatosVO.getDirectorio(), filter);
		    if (!FTPReply.isPositiveCompletion(reply)) {
		    	client.disconnect();
		    	throw new GadirServiceException("FTP list files devuelve código " + reply + ".");
		    }
		    
		    // Existe carpeta de procesados
		    if (ftpFiles.length>0) {
		    	success = client.changeWorkingDirectory(carpetaProcesados);
		    	if (!success || !FTPReply.isPositiveCompletion(reply)) {
		    		success = client.makeDirectory(carpetaProcesados);
		    		if (!success || !FTPReply.isPositiveCompletion(reply)) {
		    			throw new GadirServiceException("FTP make directory devuelve código " + reply + ".");
		    		}
		    	}
		    }
		    
		    for (FTPFile ftpFile : ftpFiles) {
		    	LOG.warn("Encuentro fichero " + ftpFile.getName());
		    	
		    	// Copiar a GadirNFS
		    	OutputStream ficheroLocal = new BufferedOutputStream(new FileOutputStream(carpetaLocal + ftpFile.getName()));
		    	success = client.retrieveFile(ftpDatosVO.getDirectorio() + ftpFile.getName(), ficheroLocal);
		    	ficheroLocal.close();
			    if (!success || !FTPReply.isPositiveCompletion(reply)) {
			    	client.disconnect();
			    	throw new GadirServiceException("FTP retrieve file devuelve código " + reply + ".");
			    }
		    	
		    	// Mover a procesados
			    success = client.rename(ftpDatosVO.getDirectorio() + ftpFile.getName(), carpetaProcesados + ftpFile.getName());
			    if (!success || !FTPReply.isPositiveCompletion(reply)) {
			    	client.disconnect();
			    	throw new GadirServiceException("FTP retrieve file devuelve código " + reply + ".");
			    }
		    	
		    	// Añadir a lista de resutlados
		    	listaFicheros.add(ftpFile.getName());
		    	LOG.warn("Fichero procesado correctamente");
		    }
		    
		    
		    
		    // Logout
		    client.logout();
		    client.disconnect();
	    } catch (IOException e) {
	    	throw new GadirServiceException(e.getMessage(), e);
	    }
		
		
		return listaFicheros;
	}


}
