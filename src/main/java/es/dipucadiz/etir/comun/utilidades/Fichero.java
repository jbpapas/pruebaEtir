/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * @author jronnols
 *
 */
public final class Fichero {

	static final private int BUFFER_SIZE = 128;
	static final private String TIPO_SISTEMA = GadirConfig.leerParametro("entorno.tipo.sistema");
	static final private String LINUX = "linux";

	private Fichero() {}

	static public void copiar(final String srFile, final String dtFile) throws IOException {
		copiar(srFile, dtFile, false);
	}
	static public void copiar(final String srFile, final String dtFile, boolean renombrarFichero) throws IOException {
		final char barra = LINUX.equals(TIPO_SISTEMA) ? '/' : '\\';
		final File file1 = new File(srFile);
		File file2 = new File(dtFile);
		final InputStream inputStream = new FileInputStream(file1);
		try {
			prepararCarpeta(dtFile, "", barra);
		} catch (GadirServiceException e) {
			System.err.println(e.getMensaje());
			e.printStackTrace();
		}
		if (renombrarFichero) {
			int i=0;
			while (file2.exists()) {
				i++;
				String nuevoNombre;
				if (dtFile.contains(".")) {
					int posPunto = dtFile.lastIndexOf(".");
					nuevoNombre = dtFile.substring(0, posPunto) + i + dtFile.substring(posPunto);
				} else {
					nuevoNombre = dtFile + i;
				}
				file2 = new File(nuevoNombre);
			}
		} else {
			if (file2.exists()) {
				file2.delete();
			}
		}
		final OutputStream outputStream = new FileOutputStream(file2);
		final byte[] buf = new byte[1024];
		int len = inputStream.read(buf);
		while (len > 0) {
			outputStream.write(buf, 0, len);
			len = inputStream.read(buf);
		}
		inputStream.close();
		outputStream.flush();
		outputStream.close();
	}

	static public void mover(final String fichero, final String directorioDestino) throws GadirServiceException {
		final File file = new File(fichero);
		final File dir = new File(directorioDestino);
		if (!file.renameTo(new File(dir, file.getName()))) {
			throw new GadirServiceException("No se puede mover el fichero a la carpeta " + directorioDestino);
		}
	}

	static public boolean borrarCarpeta(final File path) throws GadirServiceException {
		if (path.exists()) {
			final File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					borrarCarpeta(files[i]);
				} else {
					if (!files[i].delete()) {
						throw new GadirServiceException("No se pudo borrar " + files[i].getName());
					}
				}
			}
		}
		return path.delete();
	}

	public static void crearCarpeta(String carpeta) {
		new File(carpeta).mkdirs();
	}

	static public List<String> descomprimir(final String origen, final String destino) throws FileNotFoundException, IOException, GadirServiceException {
		List<String> ficherosDescomprimidos = new ArrayList<String>();
		
		final InputStream fis = new FileInputStream(origen);
		final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

		ZipEntry entry = zis.getNextEntry();
		while (entry != null) {
			if (!entry.isDirectory()) {
				String fichero = descomprimirFichero(entry, destino, zis);
				ficherosDescomprimidos.add(fichero);
			}
			entry = zis.getNextEntry();
		}
		zis.close();
		
		return ficherosDescomprimidos;
	}
	static private String descomprimirFichero(final ZipEntry entry, final String destino, final ZipInputStream zis) throws GadirServiceException, IOException {
		final char barra = LINUX.equals(TIPO_SISTEMA) ? '/' : '\\';
		final byte data[] = new byte[BUFFER_SIZE];
		String entryName;
		if (LINUX.equals(TIPO_SISTEMA)) {
			entryName = entry.getName();
		} else {
			entryName = entry.getName().replace('/', barra);
		}
		prepararCarpeta(destino, entryName, barra);
		final String destFN = destino + entryName;
		final FileOutputStream fos = new FileOutputStream(destFN);
		final BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER_SIZE);
		int count = zis.read(data, 0, BUFFER_SIZE);
		while (count != -1) {
			dest.write(data, 0, count);
			count = zis.read(data, 0, BUFFER_SIZE);
		}
		dest.flush();
		dest.close();
		return destFN;
	}
	static public List<String> descomprimirConfiguracionCompleta(final String origen, final String destino, final String destinoCarpetas) throws FileNotFoundException, IOException, GadirServiceException {
		List<String> ficherosBorrables = new ArrayList<String>();
		
		final InputStream fis = new FileInputStream(origen);
		final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

		ZipEntry entry = zis.getNextEntry();
		while (entry != null) {
			if (!entry.isDirectory()) {
				if (entry.getName().contains("/") || entry.getName().contains("\\")) {
					// Es plantilla, imagen o firma.
					descomprimirFichero(entry, destinoCarpetas, zis);
				} else {
					// Es fichero de carga.
					String fichero = descomprimirFichero(entry, destino, zis);
					ficherosBorrables.add(fichero);
				}
			}
			entry = zis.getNextEntry();
		}
		zis.close();
		
		return ficherosBorrables;
	}

	public static void comprimir(List<String> files, String destino, boolean borrar) throws IOException {
		final FileOutputStream dest = new FileOutputStream(destino);
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		for (String file : files) {
			int pos = file.lastIndexOf('/');
			if (pos == -1) pos = file.lastIndexOf('\\');
			if (pos >= 0) {
				String filename = file.substring(pos+1);
				String pathname = file.substring(0, pos+1);
				comprimirFichero(filename, pathname, out);
			}
		}
		out.close();
		// Eliminar ficheros origenes
		if (borrar) {
			for (String filename : files) {
				File file = new File(filename);
				file.delete();
			}
		}
	}
	public static void comprimirConfiguracionCompleta(List<String> files, String carpetaExtras, String txtExtras, String destinoZip, boolean borrar) throws IOException {
		final FileOutputStream dest = new FileOutputStream(destinoZip);
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		for (String file : files) {
			int pos = file.lastIndexOf('/') + 1;
			if (pos == -1) pos = file.lastIndexOf('\\') + 1;
			if (pos >= 0) {
				String filename = file.substring(pos);
				String pathname = file.substring(0, pos);
				comprimirFichero(filename, pathname, out);
			}
		}
		if (txtExtras != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtExtras)));
			String line;
			while ((line = br.readLine()) != null) {
				try {
					comprimirFichero(line, carpetaExtras, out);
				} catch (FileNotFoundException e) {}
			}
		}
		out.close();
		// Eliminar ficheros origenes
		if (borrar) {
			for (String filename : files) {
				File file = new File(filename);
				file.delete();
			}
		}
	}
	static public void comprimirOdt(final String carpetaZip, final String destino, final boolean borrar) throws IOException, GadirServiceException {
		final FileOutputStream dest = new FileOutputStream(destino);
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		final List<String> files = new ArrayList<String>();
		files.add("content.xml");
		files.add("meta.xml");
		files.add("settings.xml");
		files.add("mimetype");
		files.add("styles.xml");
		files.add("Configurations2/accelerator/current.xml");
		files.add("META-INF/manifest.xml");
		files.add("Thumbnails/thumbnail.png");
		final File dir = new File(carpetaZip + "Pictures/");
		if (dir.exists()) {
			final String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				final String filename = children[i];
				files.add("Pictures/" + filename);
			}
		}
		for (final Iterator<String> i = files.iterator(); i.hasNext();) {
			comprimirFichero(i.next(), carpetaZip, out);
		}
		out.close();
		if (borrar && !Fichero.borrarCarpeta(new File(carpetaZip))) {
			throw new GadirServiceException("No se pudo borrar la carpeta temporal " + carpetaZip);
		}
	}
	static private void comprimirFichero(final String filename2, final String carpetaZip, final ZipOutputStream out) throws IOException {
		final byte[] data = new byte[BUFFER_SIZE];
		final FileInputStream fileInputStream = new FileInputStream(carpetaZip + filename2);
		final BufferedInputStream origin = new BufferedInputStream(fileInputStream, BUFFER_SIZE);
		final ZipEntry entry = new ZipEntry(filename2);
		out.putNextEntry(entry);
		int count = origin.read(data, 0, BUFFER_SIZE);
		while (count != -1) {
			out.write(data, 0, count);
			count = origin.read(data, 0, BUFFER_SIZE);
		}
		origin.close();
	}

	static private void prepararCarpeta(final String destination, final String entryName, final char barra) throws GadirServiceException {
		final String outputFileName = destination + entryName;
		final File file = new File(outputFileName.substring(0, outputFileName.lastIndexOf(barra))); 
		if (!file.exists() && !file.mkdirs()) {
			throw new GadirServiceException("No se pudo crear la carpeta " + outputFileName);
		}
	}

	static public String getNombreFichero(final String rutaCompleta) {
		int indexOfBarra = rutaCompleta.lastIndexOf('/');
		if (indexOfBarra == -1) {
			indexOfBarra = rutaCompleta.lastIndexOf('\\');
		}
		return rutaCompleta.substring(indexOfBarra + 1, rutaCompleta.length());
	}

	static public void xmlToFichero(final Document xmlDocument, final String nombreFichero) throws IOException {
		final char barra = LINUX.equals(TIPO_SISTEMA) ? '/' : '\\';

		String ruta;
		if (nombreFichero.indexOf(barra) == -1) {
			ruta = DatosSesion.getCarpetaAcceso() + barra + nombreFichero;
		} else {
			ruta = nombreFichero;
		}

		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ruta),"UTF-8");
		xmlDocument.setXMLEncoding("UTF-8");
		xmlDocument.write(writer);
		writer.close();
	}

	static public Document ficheroToXml(final String nombreFichero) throws DocumentException {
		final char barra = LINUX.equals(TIPO_SISTEMA) ? '/' : '\\';

		String ruta;
		if (nombreFichero.indexOf(barra) == -1) {
			ruta = DatosSesion.getCarpetaAcceso() + barra + nombreFichero;
		} else {
			ruta = nombreFichero;
		}

		SAXReader saxReader = new SAXReader();
		return saxReader.read(ruta);
	}

	static public String validarNombreFichero(String nombreFichero, String extension) {
//		char[] caracteresSeguros = {'A'}
		String caracteresSeguros = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.";
		String result = nombreFichero;
		int extensionLength = extension.length()+1;
		if (extension != null && 
				(result.length() <= extensionLength || !("."+extension).equals(result.substring(result.length()-extensionLength).toLowerCase()))) {
			result += "." + extension;
		}
		result = result.replace(' ', '_');
		StringBuffer resultSeguro = new StringBuffer();
		for (int i=0; i<result.length(); i++) {
			if (caracteresSeguros.indexOf(result.toUpperCase().charAt(i)) >= 0) {
				resultSeguro.append(result.charAt(i));
			}
		}
		return resultSeguro.toString();

	}

	static public boolean existeFichero(String nombreFichero) {
		final char barra = LINUX.equals(TIPO_SISTEMA) ? '/' : '\\';

		String ruta;
		if (nombreFichero.indexOf(barra) == -1) {
			ruta = DatosSesion.getCarpetaAcceso() + barra + nombreFichero;
		} else {
			ruta = nombreFichero;
		}
		File file = new File(ruta);
		return file.exists();
	}

	public static void descargar(final String rutaFichero, final HttpServletResponse response, final String contentType) {
		try {
			FileInputStream fileInputStream = null;
			fileInputStream = new FileInputStream(rutaFichero);
			final int numberBytes = fileInputStream.available();
			final byte byteArray[] = new byte[numberBytes];
			fileInputStream.read(byteArray);
			descargar(byteArray, Fichero.getNombreFichero(rutaFichero), response, contentType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void descargar(final byte[] byteArray, final String nombreFichero, final HttpServletResponse response, final String contentType) {
		OutputStream outputStream = null;
		try {
			final int contentLength = byteArray.length;
			response.setContentType(contentType);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreFichero + "\"");
			if (response.isCommitted()) {
				System.err.println("response.isCommitted() == true");
			} else {
				response.setContentLength(contentLength);
				outputStream = new BufferedOutputStream(response.getOutputStream());
				outputStream.write(byteArray, 0, contentLength);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
				}
			} catch (Exception e) {
				System.err.println(e);
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public void mostrarFirma(final String rutaImagen, HttpServletResponse response) {
		response.setContentType("image/jpeg");
		OutputStream output = null;
		FileInputStream input = null;
		File file = new File(rutaImagen);
		try {
			output = response.getOutputStream();
			input = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			input.read(buffer);
			response.setContentLength(buffer.length);
			output.write(buffer);
		} catch (Exception e) {
		} finally {
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void utf8ToAnsi(String ficheroConRuta) throws IOException {
		final String ficheroTemporal = ficheroConRuta + ".tmp";
		
		final InputStream inputStream = new FileInputStream(ficheroConRuta);
		final OutputStream outputStream = new FileOutputStream(ficheroTemporal);
		final InputStreamReader in = new InputStreamReader(inputStream, "UTF-8");
		final OutputStreamWriter out = new OutputStreamWriter(outputStream, "ISO-8859-1");
		
		int data = in.read();
		while(data != -1){
		    out.write((char) data);
		    data = in.read();
		}
		
		in.close();
		out.close();
		inputStream.close();
		outputStream.close();
		
		File f1 = new File(ficheroConRuta);
		f1.delete();
		File f2 = new File(ficheroTemporal);
		f2.renameTo(new File(ficheroConRuta));
	}

	/**
	 * Añade una barra "/" al final, si no lo tiene ya.
	 * @param carpeta
	 * @return Carpeta con barra añadida, si no lo tenía ya. 
	 */
	public static String asegurarBarraCarpeta(String carpeta) {
		final char barra = LINUX.equals(TIPO_SISTEMA) ? '/' : '\\';
		if (!carpeta.endsWith(String.valueOf(barra))) {
			carpeta += barra;
		}
		return carpeta;
	}
	
	public static List<String> copiarFicheros(String carpetaOrigen, String carpetaDestino, String filtroBusqueda, String carpetaCopiaSeguridad) throws IOException, GadirServiceException {
		List<String> ficheros = new ArrayList<String>();
	    final File folder = new File(carpetaOrigen);
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				if (Utilidades.isEmpty(filtroBusqueda) || fileEntry.getName().matches("^"+filtroBusqueda.replace("*", ".*")+"$")) {
					// Copiar fichero
					Fichero.copiar(carpetaOrigen + fileEntry.getName(), carpetaDestino + fileEntry.getName());
					// Mover fichero para copias de seguridad
					if (Utilidades.isNotEmpty(carpetaCopiaSeguridad)) {
						if (!new File(carpetaOrigen + carpetaCopiaSeguridad).exists()) {
							crearCarpeta(carpetaOrigen + carpetaCopiaSeguridad);
						}
						Fichero.mover(carpetaOrigen + fileEntry.getName(), carpetaOrigen + carpetaCopiaSeguridad);
					}
					ficheros.add(fileEntry.getName());
				}
			}
	    }
		return ficheros;
	}

}
