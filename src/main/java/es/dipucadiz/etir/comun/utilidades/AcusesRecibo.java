package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis.encoding.Base64;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.AcuseReciboVO;

final public class AcusesRecibo {

	static public Boolean obtenerAcusesRecibo(final String codigoBarras, final HttpServletResponse response) throws GadirServiceException {
		String urlS = GadirConfig.leerParametro("url.ws.nore");
		Boolean hayAcuse;
		try {
			// ENVÍO
			HttpURLConnection connection = (HttpURLConnection) new URL(urlS).openConnection();
			connection.setRequestMethod("POST");

			String requestContent;
			requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.gdtel.com\">\r\n";
			requestContent += "   <soapenv:Header/>\r\n";
			requestContent += "   <soapenv:Body>\r\n";
			requestContent += "      <ws:obtenerNotificacionPorCodigoBarras>\r\n";
			requestContent += "         <ws:codigoBarras>" + codigoBarras + "</ws:codigoBarras>\r\n";
			requestContent += "      </ws:obtenerNotificacionPorCodigoBarras>\r\n";
			requestContent += "   </soapenv:Body>\r\n";
			requestContent += "</soapenv:Envelope>\r\n";
			requestContent += "\r\n";

			connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
			connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			connection.setRequestProperty("SOAPAction", "");
			connection.setRequestProperty("Content-Length", requestContent.length() + "");
			connection.setRequestProperty("Host", "zenon:7070");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");

			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(requestContent);
			wr.flush();
			wr.close();

			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + urlS);
			System.out.println("Post parameters : " + requestContent);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				res.append(inputLine);
			}
			in.close();

			if (res.toString().contains("<notificacionPDF>")) {
				String[] cad = res.toString().split("<notificacionPDF>");
				String[] pdfBytes = cad[1].split("</notificacionPDF>");
				Fichero.descargar(Base64.decode(pdfBytes[0]), codigoBarras + ".pdf", response, "application/pdf");
				hayAcuse = true;
			} else {
				hayAcuse = false;
			}

		} catch (IOException e) {
			throw new GadirServiceException(e.getMessage(), e);
		}
		return hayAcuse;
	}

	static public List<AcuseReciboVO> obtenerAcusesReciboPorContribuyente(String dni, final HttpServletResponse response) throws SAXException {
		String urlS = GadirConfig.leerParametro("url.ws.nore");
		List<AcuseReciboVO> listaAcuses = new ArrayList<AcuseReciboVO>();
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(urlS).openConnection();
			connection.setRequestMethod("POST");

			String requestContent;
			requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.gdtel.com\">\n";
			requestContent += "   <soapenv:Header/>\n";
			requestContent += "   <soapenv:Body><ws:obtenerNotificaciones>\n";
			requestContent += "         <ws:numExp></ws:numExp>\n";
			requestContent += "         <ws:dni>" + dni + "</ws:dni>\n";
			requestContent += "         <ws:nombre></ws:nombre>\n";
			requestContent += "         <ws:descripcionDoc></ws:descripcionDoc>\n";
			requestContent += "         <ws:codigoBarras></ws:codigoBarras>\n";
			requestContent += "         <ws:zona></ws:zona>\n";
			requestContent += "         <ws:estado></ws:estado>\n";
			requestContent += "         <ws:empresaNotif></ws:empresaNotif>\n";
			requestContent += "         <ws:fecha></ws:fecha>\n";
			requestContent += "         <ws:municipio></ws:municipio>\n";
			requestContent += "         <ws:anio></ws:anio>\n";
			requestContent += "      </ws:obtenerNotificaciones>\n";
			requestContent += "   </soapenv:Body>\n";
			requestContent += "</soapenv:Envelope>\n";
			requestContent += "\r\n";

			connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
			connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			connection.setRequestProperty("SOAPAction", "");
			connection.setRequestProperty("Content-Length", requestContent.length() + "");
			connection.setRequestProperty("Host", "zenon:7070");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
			connection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

			wr.writeBytes(requestContent);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				res.append(inputLine);
			}
			in.close();

			try {

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				InputSource archivo = new InputSource();
				archivo.setCharacterStream(new StringReader(res.toString()));

				Document documento = db.parse(archivo);
				documento.getDocumentElement().normalize();

				NodeList nodeLista = documento.getElementsByTagName("obtenerNotificacionesReturn");
				System.out.println("Informacion de los acuses de recibo");

				for (int s = 0; s < nodeLista.getLength(); s++) {

					Node primerNodo = nodeLista.item(s);

					if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {
						AcuseReciboVO acuseReciboVO = new AcuseReciboVO();

						Element primerElemento = (Element) primerNodo;

						NodeList primerNombreElementoLista = primerElemento.getElementsByTagName("anio");
						Element primerNombreElemento = (Element) primerNombreElementoLista.item(0);
						NodeList anio = primerNombreElemento.getChildNodes();
						acuseReciboVO.setAnio(((Node) anio.item(0)).getNodeValue().toString());

						NodeList codigoBarras = ((Element) primerElemento.getElementsByTagName("codigoBarras").item(0)).getChildNodes();
						acuseReciboVO.setCodigoBarras(((Node) codigoBarras.item(0)).getNodeValue().toString());

						NodeList descripcion = ((Element) primerElemento.getElementsByTagName("descripcionDoc").item(0)).getChildNodes();
						acuseReciboVO.setDescripcion(((Node) descripcion.item(0)).getNodeValue().toString());

						NodeList cliente = ((Element) primerElemento.getElementsByTagName("dni").item(0)).getChildNodes();
						String d = ((Node) cliente.item(0)).getNodeValue().toString();
						acuseReciboVO.setDniContribuyente(((Node) cliente.item(0)).getNodeValue().toString());

						NodeList empresaNotif = ((Element) primerElemento.getElementsByTagName("empresaNotif").item(0)).getChildNodes();
						acuseReciboVO.setEmpresaNotif(((Node) empresaNotif.item(0)).getNodeValue().toString());

						NodeList entidad = ((Element) primerElemento.getElementsByTagName("entidad").item(0)).getChildNodes();
						acuseReciboVO.setEntidad(((Node) entidad.item(0)).getNodeValue().toString());

						NodeList estado = ((Element) primerElemento.getElementsByTagName("estado").item(0)).getChildNodes();
						//No hay que dar formato al estado. Nosotros utilizamos también dos dígitos
						//KeyValue est = TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_RESULTADOS_NOTIFICACION, ((Node) estado.item(0)).getNodeValue().toString().substring(1));
						KeyValue est = TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_RESULTADOS_NOTIFICACION, ((Node) estado.item(0)).getNodeValue().toString());
						acuseReciboVO.setEstado(est.getCodigoDescripcion());
						try {
							NodeList fecha = ((Element) primerElemento.getElementsByTagName("fecha").item(0)).getChildNodes();
							String f = ((Node) fecha.item(0)).getNodeValue().toString();
							acuseReciboVO.setFecha(f.substring(0, 2) + "/" + f.substring(2, 4) + "/20" + f.substring(4, 6));
						} catch (DOMException e) {
							e.printStackTrace();
						}

						NodeList municipio = ((Element) primerElemento.getElementsByTagName("municipio").item(0)).getChildNodes();

						acuseReciboVO.setMunicipio(MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion("11", ((Node) municipio.item(0)).getNodeValue().toString()));

						NodeList nombrePDF = ((Element) primerElemento.getElementsByTagName("nomPdf").item(0)).getChildNodes();
						acuseReciboVO.setNombreAcuseReciboPDF(((Node) nombrePDF.item(0)).getNodeValue().toString());

						NodeList nombreCont = ((Element) primerElemento.getElementsByTagName("nombre").item(0)).getChildNodes();
						String contribuyente = d + " - " + ((Node) nombreCont.item(0)).getNodeValue().toString();
						acuseReciboVO.setNombreContribuyente(contribuyente);

						NodeList acuseReciboPDF = ((Element) primerElemento.getElementsByTagName("notificacionPDF").item(0)).getChildNodes();
						acuseReciboVO.setAcuseReciboPDF(((Node) acuseReciboPDF.item(0)).getNodeValue().toString());

						NodeList expediente = ((Element) primerElemento.getElementsByTagName("numExp").item(0)).getChildNodes();
						acuseReciboVO.setExpediente(((Node) expediente.item(0)).getNodeValue().toString());

						NodeList servicio = ((Element) primerElemento.getElementsByTagName("servicio").item(0)).getChildNodes();
						acuseReciboVO.setServicio(((Node) servicio.item(0)).getNodeValue().toString());

						NodeList tipo = ((Element) primerElemento.getElementsByTagName("tipo").item(0)).getChildNodes();
						acuseReciboVO.setTipo(((Node) tipo.item(0)).getNodeValue().toString());

						NodeList uuidEspacio = ((Element) primerElemento.getElementsByTagName("uuidEspacioNotif").item(0)).getChildNodes();
						acuseReciboVO.setUuidEspacioNotif(((Node) uuidEspacio.item(0)).getNodeValue().toString());

						NodeList uuidFichero = ((Element) primerElemento.getElementsByTagName("uuidFicheroPdf").item(0)).getChildNodes();
						acuseReciboVO.setUuidFicheroPdf(((Node) uuidFichero.item(0)).getNodeValue().toString());

						NodeList zona = ((Element) primerElemento.getElementsByTagName("zona").item(0)).getChildNodes();
						acuseReciboVO.setZona(((Node) zona.item(0)).getNodeValue().toString());

						listaAcuses.add(acuseReciboVO);
					}
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaAcuses;
	}

}