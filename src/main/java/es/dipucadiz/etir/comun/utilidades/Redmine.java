package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.GestorTareasBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.GestorTareasDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

final public class Redmine {

	static String TABLA_APLICACION = "APLICACI";
	static String TABLA_TIPOS = "TIPTARMA";
	static String TABLA_SUBTIPOS = "SUBTARMA";
	
	static public Boolean altaIncidencia(final String proyecto, String nuTarea, final HttpServletResponse response) throws GadirServiceException, SAXException, IOException{
		boolean incidenciaCreada = false;
		
		AcmUsuarioBO acmUsuarioBO = (AcmUsuarioBO) GadirConfig.getBean("acmUsuarioBO");
		
		URL url = new URL("http://galba2/redmine/projects/" + proyecto + "/issues.xml");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestProperty("X-Redmine-API-Key", "e185a725aac44ee478a37e51f8d131bdf8338d28"); //TODO cambiar por clave del usuario que se cree para gestionar las incidencias
        httpCon.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
		
		OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
        		  	 "<issue>\n"; // +
//        		  	 "  <project_id>262</project_id>\n";
        		  	 
        GestorTareasDTO dto = ((GestorTareasBO) GadirConfig.getBean("gestorTareasBO")).findById(Long.valueOf(nuTarea));
		String titulo = dto.getTitulo();
		xml += "  <tracker_id>13" + "</tracker_id>\n";

		if(!Utilidades.isEmpty(dto.getEstado())) {
			String estado = "1";
			switch (dto.getEstado().charAt(0)) {
			case 'D': 
				estado = "8";
				break;
			case 'E': 
				break;
			case 'F': 
				estado = "3";
				break;
			case 'R': 
				break;    
			case 'T': 
				estado = "3";
				break;
			default:
				break;
			}
			
			xml += "<status_id>" + estado + "</status_id>\n";
		}
		
		String prioridad = "4";
		if(!Utilidades.isEmpty(dto.getPrioridad())) {
			String coPrioridad = dto.getPrioridad();
		
			switch (coPrioridad.charAt(0)) {
				case 'B': prioridad = "3";
						  break;
				case 'N': prioridad = "4";
						  break;
				case 'A': prioridad = "5";
						  break;	
				default: break;
			}
		}
		
		xml += "<priority_id>" + prioridad + "</priority_id>\n";
		
		xml += "  <subject>" + titulo + " (Prueba de webservices)</subject>\n";
		
		String descripcion = "Tarea en eTIR número " + nuTarea + " ";
		
		if(Utilidades.isNotNull(dto.getCoUsuarioEmisor())) {
			AcmUsuarioDTO acmUsuarioEmisorDTO = acmUsuarioBO.findByIdInitialized(dto.getCoUsuarioEmisor(), new String[]{"clienteDTO"});
			
			descripcion += "emitida por el usuario " + acmUsuarioEmisorDTO.getClienteDTO().getRazonSocial();
		}
		else {
			if(!Utilidades.isEmpty(dto.getNombreEmisor())) {
				descripcion += "emitida por el usuario " + dto.getNombreEmisor();
			}
		}
		
		descripcion += "\n";
		
		if(dto.getCoUsuarioAsignado() != null){				
			AcmUsuarioDTO acmUsuarioAsignadoDTO = acmUsuarioBO.findByIdInitialized(dto.getCoUsuarioAsignado(), new String[]{"clienteDTO"});
			String usuarioAsignado = new KeyValue(acmUsuarioAsignadoDTO.getCoAcmUsuario(), acmUsuarioAsignadoDTO.getClienteDTO().getRazonSocial()).getCodigoDescripcion();
			descripcion += "Asignada al usuario: " + usuarioAsignado + "\n";
		}
		
		descripcion += "Categoría: " + TablaGt.getValor(TABLA_APLICACION, dto.getAplicacion(), TablaGt.COLUMNA_DESCRIPCION) + "\n";
		
		if(dto.getFxEntrada() != null)
			descripcion += "Fecha de creación: " + Utilidades.dateToDDMMYYYY(dto.getFxEntrada()) + "\n";			
		
		if(!Utilidades.isEmpty(dto.getCoTipo()))
			descripcion += "Tipo: " + TablaGt.getValor(TABLA_TIPOS, dto.getCoTipo(), TablaGt.COLUMNA_DESCRIPCION) + "\n";
		
		if(!Utilidades.isEmpty(dto.getCoSubtipo()))
			descripcion += "Subtipo: " + TablaGt.getValor(TABLA_SUBTIPOS, dto.getCoSubtipo(), TablaGt.COLUMNA_DESCRIPCION) + "\n";
		
		if (dto.getObservaciones() != null) {
			try {
				descripcion += "Observaciones: " + dto.getObservaciones().getSubString(1,(int)dto.getObservaciones().length()) + "\n";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				descripcion += "\n";
			}
		}
		
		xml += "<description>" + descripcion + "</description>\n";
		//TODO añadir id del usuario al que se le asigne la tarea
		xml += "</issue>";
		out.write(xml);
        out.close();
        
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    
                    try {
                    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    					DocumentBuilder db = dbf.newDocumentBuilder();
    	
    					InputSource archivo = new InputSource();
    					archivo.setCharacterStream(new StringReader(sb.toString()));
    	
    					Document documento = db.parse(archivo);
    					documento.getDocumentElement().normalize();
    	
    					NodeList nodeIncidencia = documento.getElementsByTagName("issue");
    					if(nodeIncidencia.getLength() == 1) {
    						Element nuevaIncidencia = (Element) nodeIncidencia.item(0);
    						NodeList elementoIdIncidencia = nuevaIncidencia.getElementsByTagName("id");
    						if(elementoIdIncidencia.getLength() == 1 && !Utilidades.isEmpty(((Element)elementoIdIncidencia.item(0)).getTextContent()))
    							incidenciaCreada = true;
    					}
                    	
                    } catch (ParserConfigurationException e) {
        				e.printStackTrace();
        			}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(sb.toString());
        return incidenciaCreada;
    }    
}