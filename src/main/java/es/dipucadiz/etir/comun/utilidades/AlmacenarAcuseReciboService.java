package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.gdtel.goncedc.domain.DatosNotificacion;
import com.gdtel.goncedc.domain.Domicilio;
import com.gdtel.goncedc.domain.Georef;
import com.gdtel.ws.NoreWS;
import com.gdtel.ws.NoreWSServiceLocator;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

final public class AlmacenarAcuseReciboService {
 
    protected static Logger LOG = LoggerFactory.getLogger(AlmacenarAcuseReciboService.class);

	static public String nombre;
	static public String apellido1;
	static public String apellido2;
	static public Long codigoPostal;
	static public String tipoVia;
	static public String direccion;
	static public String identificador;
	static public String tipoIdentificador;
	static public String iNEMunicipio;
	static public String iNEProvincia;
	static public Long movil;
	static public String email;
	static public String nombreDocNotificacion;
	static public byte[] docNotificacion;
	static public String docNotificacionBase64;
	static public String anio;
	static public String codigoBarras;
	static public String fecha;
	static public String hora;
	static public String descripcionNotificacion;	
	static public String estado;		
	static public String tipo;		
 
	static public File file;
	static public String rutaAcuseRecibo;		
	static public String numExp;
	static public String acuseReciboleido;
	public static void publicar(BufferedReader input, String coProceso, String coEjecucion) throws GadirServiceException {
		try {

			/*	0	NO·
				1	SANTIAGO NIETO TRINIDAD·
				2	11200·
				3	CL PERLA DE CADIZ Nº24·
				4	31819030W·
				5	NIF·
				6	ALGECIRAS·
				7	CADIZ·
				8	·
				9	·
				10	2016·
				11	NT11000001600R000013400
				12	·
				13 	18/08/16·01·
				14	/opt/editran/RECEPCION/IMAGENES/NT11000001600R000013400-000200008.jpg
			*/
			
		/*	 	0	NO·
		 * 		1	SANTIAGO NIETO TRINIDAD·
		 * 		2	11200·
		 * 		3	CL·
		 * 		4	PERLA DE CADIZ Nº24·
		 * 		5	31819030W·
		 * 		6	NIF·
		 * 		7	ALGECIRAS·
		 * 		8	CADIZ·
		 * 		9	·
		 * 		10	·
		 * 		11	2016·
		 * 		12	NT11000001600R000013400Â·
		 * 		13	18/08/16·
		 * 		14	01·
		 * 		15	/opt/editran/RECEPCION/IMAGENES/NT11000001600R000013400-000200008.jpg·
		 * 		16	178676
		 
		 
		 
		 */

 			String line = input.readLine();
			int numLinea = 0;
			while (line != null) {
				
				try{
						System.out.println("Entra a procesar\t" + System.currentTimeMillis());
						System.out.println("Leo: " + line);
						numLinea++;
						line = line.trim();
						if (Utilidades.isNotEmpty(line)) {
							acuseReciboleido = "false";
							docNotificacion = new byte[1];
							String[] palabras = line.split("·");
							String[] razonSocial = Utilidades.separarRazonSocial(palabras[1]);
					 
							apellido1 = razonSocial[0];
							apellido2 = razonSocial[1];
							nombre=razonSocial[2]; 
							codigoPostal = Long.valueOf(palabras[2]);
							tipoVia = palabras[3];
							direccion = palabras[4];
							identificador = palabras[5];
							tipoIdentificador = palabras[6];
							iNEMunicipio = palabras[7];
							iNEProvincia = palabras[8];
							if(!Utilidades.isEmpty(palabras[9])){
								movil= Long.valueOf(palabras[9]);
							}
							if(!Utilidades.isEmpty(palabras[10])){							
								email = palabras[10];
							}			
							anio = palabras[11];	
							codigoBarras = palabras[12];	
							fecha = palabras[13];	//formato ddMMyy  Ej 190115
							fecha = fecha.replaceAll("/","");
							estado = palabras[14];	 
							rutaAcuseRecibo= palabras[15];	 
							numExp = palabras[16];	 
							String[] rutaFichero = rutaAcuseRecibo.split("/");
							nombreDocNotificacion = rutaFichero[rutaFichero.length-1];
							
							descripcionNotificacion = palabras[17];	  // ="NOT. CORREOS";
							tipo  = palabras[18];	//	 = "PROVIDENCIA_DE_APREMIO";
							hora  = palabras[19];	// ="111500";
							 
 
							rescatarAcuseRecibo();
							if(acuseReciboleido.equals("true")){
								 darDeAltaNORECliente();
							}else{
								System.out.println("No encontramos el acuse de recibo de la notificación "+nombreDocNotificacion);
							}
							
							line = input.readLine();
						}
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			System.out.println("Terminado\t" + System.currentTimeMillis());
			System.out.println(numLinea + " lineas leidas.");
		} catch (IOException e) {
			throw new GadirServiceException("Error procesando notificaciones electrónicas.", e);
		}
	}
	
	
	 

	private static  String darDeAltaNORECliente(){
		String respuesta="";
		
  	    try
	    {
	    	
	    	String noreURL = GadirConfig.leerParametro("url.ws.nore");
		      
		  	NoreWSServiceLocator locator = new NoreWSServiceLocator();
		    NoreWS  servicio = locator.getNoreWS(new URL(noreURL));
		    

	    	DatosNotificacion df2 = new DatosNotificacion();
	    	Georef georef = new Georef();
	    	georef.setAltura("0");
	    	georef.setLatitud("0");
	    	georef.setLongitud("0");
	    	int intentos = 1;
	    	Domicilio domAlternativo = new Domicilio();
	    	String emails = GadirConfig.leerParametro("url.ws.nore.emailAviso");
	    	
	    	df2.setFecha(fecha);	
			df2.setHora(hora);
			df2.setTipo(tipo);
			df2.setMunicipio(iNEMunicipio);
			df2.setAnio(anio);
			df2.setEmpresaNotif("CO"); //"OV"
			df2.setZona("01");
			df2.setCodigoBarras(codigoBarras);
			df2.setDescripcionDoc(descripcionNotificacion);
			df2.setNombre(apellido1  + " "+ apellido2  + " "+ nombre);
			df2.setDni(identificador);
			df2.setNumExp(numExp);
			df2.setNomPdf(nombreDocNotificacion);
			df2.setEstado(estado);
			df2.setEntidad("4");
			df2.setServicio("1");
			df2.setNotificacionPDF(docNotificacion);
 	
	    	System.out.println("Intentamos crear notificacion");
	    	respuesta = servicio.crearNotificacion(df2, georef, intentos, domAlternativo, emails);
	    	System.out.println("Notificacion dada de alta correctamente");	    	
	   
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage());
	      e.printStackTrace();
	    }
  	    
    	System.out.println("respuesta webService " +respuesta);
    	return (respuesta);
	 }
	
	
		private static  String darDeAltaNORE() throws GadirServiceException{
			String urlS = GadirConfig.leerParametro("url.ws.nore");
			System.out.println("atacamos  "+urlS);
			String resultado ="";
			try{
				
				
//				TrErrorPlDTO errorTr = new TrErrorPlDTO();				
//				TrErrorPlDTOId idError = new TrErrorPlDTOId();
//				
//				idError.setBoPl(false);
//				idError.setCoUsuario(DatosSesion.getLogin());
//				idError.setFhActualizacion(new Date());
//				
//				
//				Clob error=  new ClobImpl("vamos a construir xml");
//				idError.setError(error);			
//					
//				errorTr.setId(idError);
//				
//				PostBatch.getTrErrorPlBO().save(errorTr);
				
				System.out.println("vamos a construir xml");
				
					// ENVÍO
					HttpURLConnection connection = (HttpURLConnection) new URL(urlS).openConnection();
					connection.setRequestMethod("POST");
		
					String requestContent;
					requestContent  = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.gdtel.com\" xmlns:dom=\"http://domain.goncedc.gdtel.com\">\r\n";	
					requestContent += "   <soapenv:Header/>\r\n";
					requestContent += "   <soapenv:Body>\r\n";
					requestContent += "      <ws:crearNotificacion>\r\n";
					requestContent += "      	<ws:datosNotif>\r\n";					
					requestContent += "      		<dom:anio>" + anio + "</dom:anio>\r\n";	
					requestContent += "         	<dom:codigoBarras>" + codigoBarras + "</dom:codigoBarras>\r\n";
					requestContent += "         	<dom:descripcionDoc>" + descripcionNotificacion + "</dom:descripcionDoc>\r\n";
					requestContent += "         	<dom:dni>" + identificador + "</dom:dni>\r\n";
					requestContent += "         	<dom:empresaNotif>" + "CO" + "</dom:empresaNotif>\r\n";					
					requestContent += "         	<dom:entidad>" + "4" + "</dom:entidad>\r\n";		
					requestContent += "         	<dom:estado>" + estado + "</dom:estado>\r\n";			
					requestContent += "         	<dom:fecha>" + fecha + "</dom:fecha>\r\n";			
					requestContent += "         	<dom:hora>"+hora+"</dom:hora>\r\n";
		           
					requestContent += "         	<dom:id>201</dom:id>\r\n";		            
					requestContent += "         	<dom:municipio>" + iNEMunicipio + "</dom:municipio>\r\n";		
					requestContent += "         	<dom:nomPdf>" + nombreDocNotificacion + "</dom:nomPdf>\r\n";							
					requestContent += "         	<dom:nombre>" +apellido1  + " "+ apellido2  + " "+ nombre + "</dom:nombre>\r\n";						
					requestContent += "         	<dom:notificacionPDF>" + docNotificacionBase64 + "</dom:notificacionPDF>\r\n";						
					requestContent += "         	<dom:numExp>" + numExp + "</dom:numExp>\r\n";						
					requestContent += "         	<dom:servicio>" + "1" + "</dom:servicio>\r\n";						 
					requestContent += "         	<dom:tipo>" + tipo + "</dom:tipo>\r\n";						 
					requestContent += "         	<dom:uuidEspacioNotif></dom:uuidEspacioNotif>\r\n";	
					requestContent += "         	<dom:uuidFicheroPdf></dom:uuidFicheroPdf>\r\n";						
					requestContent += "         	<dom:zona>01</dom:zona>\r\n";					
					requestContent += "      	</ws:datosNotif>\r\n";	
					requestContent += "      	<ws:georef>\r\n";	
					requestContent += "      		<dom:altura>0</dom:altura>\r\n";	
					requestContent += "      		<dom:id>200</dom:id>\r\n";	
					requestContent += "      		<dom:latitud>0</dom:latitud>\r\n";	
					requestContent += "      		<dom:longitud>0</dom:longitud>\r\n";	
					requestContent += "      	</ws:georef>\r\n";				         
					requestContent += "      	<ws:intentos>1</ws:intentos>\r\n";	
					requestContent += "      	<ws:domAlternativo>\r\n";
					requestContent += "      	<dom:bloque></dom:bloque>\r\n";
					requestContent += "      	<dom:codPostal></dom:codPostal>\r\n";
					requestContent += "      	<dom:escalera></dom:escalera>\r\n";
					requestContent += "      	<dom:id></dom:id>\r\n";
					requestContent += "      	<dom:letra></dom:letra>\r\n";
					requestContent += "      	<dom:municipio></dom:municipio>\r\n";
					requestContent += "      	<dom:numero></dom:numero>\r\n";
					requestContent += "      	<dom:planta></dom:planta>\r\n";
					requestContent += "      	<dom:provincia></dom:provincia>\r\n";
					requestContent += "      	<dom:puerta></dom:puerta>\r\n";
					requestContent += "      	<dom:siglas></dom:siglas>\r\n";
					requestContent += "      	<dom:ubicacion></dom:ubicacion>\r\n";
					requestContent += "      	<dom:via></dom:via>\r\n";
					requestContent += "      	</ws:domAlternativo>\r\n";					
					requestContent += "      	<ws:emails>"+ GadirConfig.leerParametro("url.ws.nore.emailAviso")+"</ws:emails>\r\n";							
					requestContent += "      </ws:crearNotificacion>\r\n";
					requestContent += "   </soapenv:Body>\r\n";
					requestContent += "</soapenv:Envelope>\r\n";
					requestContent += "\r\n";
		
					
//					TrErrorPlDTO errorTr2 = new TrErrorPlDTO();				
//					TrErrorPlDTOId idError2 = new TrErrorPlDTOId();
//					
//					idError2.setBoPl(false);
//					idError2.setCoUsuario(DatosSesion.getLogin());
//					idError2.setFhActualizacion(new Date());
//					
//					
//					Clob error2=  new ClobImpl("requestContent = "+requestContent);
//					idError2.setError(error2);			
//						
//					errorTr2.setId(idError2);
//					
//					PostBatch.getTrErrorPlBO().save(errorTr2);
					
//					System.out.println(requestContent);
					
					
					connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
					connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
					connection.setRequestProperty("SOAPAction", "");
					connection.setRequestProperty("Content-Length", requestContent.length() + "");
					connection.setRequestProperty("Host", GadirConfig.leerParametro("url.ws.nore.host"));
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
					System.out.println("Response Message : " + connection.getResponseMessage());		
					
					
					 
					 
					
					
					
					
					
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String inputLine;
					StringBuffer res = new StringBuffer();
		
					while ((inputLine = in.readLine()) != null) {
						res.append(inputLine);
					}
					in.close();
		
					if(res.toString().contains("<crearNotificacionReturn>")){
						String[] cad = res.toString().split("<crearNotificacionReturn>");
						String resultadoRespons = cad[1];
						System.out.println("Response resultado : " + resultadoRespons);
						 
					}else{
						//hayAcuse = false;
					}


		} catch (IOException e) {
	 
			LOG.error("Error al insertar acuse de recibo", e); 
			 
		}
			return resultado;
			
		}

		private static void rescatarAcuseRecibo() throws GadirServiceException{
		try {
				//TODO:
				//hay que ver donde se deja la imagen con el acuse de recibo 
				String rutaFile  = GadirConfig.leerParametro("url.ws.nore.rutaImagenes");    
				rutaFile = Fichero.asegurarBarraCarpeta(rutaFile);
				rutaAcuseRecibo = rutaFile+nombreDocNotificacion;
	 			System.out.println("vamos a tratar de leer "+rutaAcuseRecibo);
			
			
			
			if(  extensionFicheroPDF(rutaAcuseRecibo)){
				
				file = new File(rutaAcuseRecibo);
  

				
				//File length
		        int size = (int) file.length();
		        if (size > Integer.MAX_VALUE) {
		            System.out.println("File is to larger");
		        }
		        docNotificacion = new byte[size];
		        DataInputStream dis = new DataInputStream(new FileInputStream(file));
		        int read = 0;
		        int numRead = 0;
		        while (read < docNotificacion.length && (numRead = dis.read(docNotificacion, read,
		        		docNotificacion.length - read)) >= 0) {
		            read = read + numRead;
		        }
		        dis.close();
		        
		        docNotificacion= Base64.encodeBase64(docNotificacion);
		        
/*		        byte[] docNotificacion2 = new byte[size];
		        docNotificacion2= Base64.decodeBase64(docNotificacion);
		         
		        
		        String rutaNuevoPDFReconst =  GadirConfig.leerParametro("url.ws.nore.rutaImagenes")+"/"+ nombreDocNotificacion.substring(0, nombreDocNotificacion.indexOf('.'))+"Reconstr.pdf";
		        DataOutputStream out = new DataOutputStream(new FileOutputStream(rutaNuevoPDFReconst));
		        out.write(docNotificacion2);
		        out.close();
		        System.out.println("File size: " + read);		        
*/		        
				System.out.println("terminamos de leer el pdf y vamos a pasar a base 64");
					
//		 		docNotificacionBase64 = new String (Base64.encodeBase64(docNotificacion));
		 				 
				acuseReciboleido = "true";
				}else{	
						//sino es un pdf es algún tipo de imagen por lo que crearemos un pdf insertandole la imagen
						System.out.println("No es un pdf. Vamos a convertir de imagen a pdf");
 
				        PDDocument doc = null;
				        try {
				        	
 
					             

				        	  doc = new PDDocument();
				        	   
				        	  PDPage page = new PDPage();
				        	  
				        	  doc.addPage(page);
 				            
				            
				            System.out.println("vamos a intentar rescatar "+ rutaAcuseRecibo);
				            PDImageXObject pdImage = PDImageXObject.createFromFile(rutaAcuseRecibo, doc);
				            System.out.println("referenciamos imagen");					            
				            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				            System.out.println("creamos el contentStream");					 
				            //  contentStream.drawImage(ximage, 20, 20 );
				            // better method inspired by http://stackoverflow.com/a/22318681/535646
				            // reduce this value if the image is too large
				            float scale = 1f;
				            contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth()*scale, pdImage.getHeight()*scale);
	
				             
				            contentStream.close();
	
				           String rutaNuevoPDF =  GadirConfig.leerParametro("url.ws.nore.rutaImagenes")+"/"+ nombreDocNotificacion.substring(0, nombreDocNotificacion.indexOf('.'))+".pdf";
				           doc.save(rutaNuevoPDF);
				           System.out.println("Grabamos fichero "+rutaNuevoPDF );
				           doc.close();

					                    
					            
					            
					            file = new File(rutaNuevoPDF);
 
								
								//File length
						        int size = (int) file.length();
						        if (size > Integer.MAX_VALUE) {
						            System.out.println("File is to larger");
						        }
						        docNotificacion = new byte[size];
						        DataInputStream dis = new DataInputStream(new FileInputStream(file));
						        int read = 0;
						        int numRead = 0;
						        while (read < docNotificacion.length && (numRead = dis.read(docNotificacion, read,
						        		docNotificacion.length - read)) >= 0) {
						            read = read + numRead;
						        }
						        dis.close();
			
						        docNotificacion= Base64.encodeBase64(docNotificacion);
						        
						        
/*						        byte[] docNotificacion2 = new byte[size];
						        docNotificacion2= Base64.decodeBase64(docNotificacion);
						        String rutaNuevoPDFReconst =  GadirConfig.leerParametro("url.ws.nore.rutaImagenes")+"/"+ nombreDocNotificacion.substring(0, nombreDocNotificacion.indexOf('.'))+"Reconstr.pdf";
						        DataOutputStream out = new DataOutputStream(new FileOutputStream(rutaNuevoPDFReconst));
						        out.write(docNotificacion2);
						        out.close();
						        System.out.println("File size: " + read);
*/
						        
						        
//								System.out.println("Escribimos en el array de bytes el contenido dle pdf");
//								FileInputStream  fin= new fileinputstream 
//								FileOutputStream fos = new FileOutputStream(file);
//								fos.write(docNotificacion);
//								fos.flush();
//								fos.close();
//							 
//					            ByteArrayOutputStream out = new ByteArrayOutputStream();
//						         try {
//						        	 doc.save(out);  System.out.println("Guardamos el doc en ByteArrayOutputStream");
//						        	 doc.close(); System.out.println("Cerramos el doc");
//						             } catch (Exception ex) {
//						            	 
//						            	 ex.printStackTrace();
//						            }  
						                      
//						         docNotificacion=  out.toByteArray(); System.out.println(" Pasamos a byte[] y vamos a pasar a base 64");
 									
//								docNotificacionBase64 = new String (Base64.encodeBase64(docNotificacion));
								System.out.println("ya hemos convertido a base 64" );

								
								acuseReciboleido = "true";
								System.out.println("acuseReciboleido = true" );
				        }
				        finally
				        {
				            if( doc != null )
				            {
				                doc.close();
				            }
				        }		 		
	
			}
	        }catch(Exception e){
	        	e.printStackTrace();
//	        	LOG.error(msg, e);
//	        	System.out.println("Error leyendo fichero: " + e.getStackTrace().toString() );
	        }
			
		}


		private static Boolean 	extensionFicheroPDF( String rutaAcuseRecibo){
			Boolean esPdf=false;
			if( "PDF".equalsIgnoreCase(FilenameUtils.getExtension(rutaAcuseRecibo)) ){
				esPdf=true;
			}
			return esPdf;
 		}
		
		
		public static String getNombre() {
			return nombre;
		}




		public static void setNombre(String nombre) {
			AlmacenarAcuseReciboService.nombre = nombre;
		}




		public static String getApellido1() {
			return apellido1;
		}




		public static void setApellido1(String apellido1) {
			AlmacenarAcuseReciboService.apellido1 = apellido1;
		}




		public static String getApellido2() {
			return apellido2;
		}




		public static void setApellido2(String apellido2) {
			AlmacenarAcuseReciboService.apellido2 = apellido2;
		}




		public static Long getCodigoPostal() {
			return codigoPostal;
		}




		public static void setCodigoPostal(Long codigoPostal) {
			AlmacenarAcuseReciboService.codigoPostal = codigoPostal;
		}




		public static String getTipoVia() {
			return tipoVia;
		}




		public static void setTipoVia(String tipoVia) {
			AlmacenarAcuseReciboService.tipoVia = tipoVia;
		}




		public static String getDireccion() {
			return direccion;
		}




		public static void setDireccion(String direccion) {
			AlmacenarAcuseReciboService.direccion = direccion;
		}




		public static String getIdentificador() {
			return identificador;
		}




		public static void setIdentificador(String identificador) {
			AlmacenarAcuseReciboService.identificador = identificador;
		}




		public static String getTipoIdentificador() {
			return tipoIdentificador;
		}




		public static void setTipoIdentificador(String tipoIdentificador) {
			AlmacenarAcuseReciboService.tipoIdentificador = tipoIdentificador;
		}




		public static String getiNEMunicipio() {
			return iNEMunicipio;
		}




		public static void setiNEMunicipio(String iNEMunicipio) {
			AlmacenarAcuseReciboService.iNEMunicipio = iNEMunicipio;
		}




		public static String getiNEProvincia() {
			return iNEProvincia;
		}




		public static void setiNEProvincia(String iNEProvincia) {
			AlmacenarAcuseReciboService.iNEProvincia = iNEProvincia;
		}




		public static Long getMovil() {
			return movil;
		}




		public static void setMovil(Long movil) {
			AlmacenarAcuseReciboService.movil = movil;
		}




		public static String getEmail() {
			return email;
		}




		public static void setEmail(String email) {
			AlmacenarAcuseReciboService.email = email;
		}




		public static String getNombreDocNotificacion() {
			return nombreDocNotificacion;
		}




		public static void setNombreDocNotificacion(String nombreDocNotificacion) {
			AlmacenarAcuseReciboService.nombreDocNotificacion = nombreDocNotificacion;
		}




		public static byte[] getDocNotificacion() {
			return docNotificacion;
		}




		public static void setDocNotificacion(byte[] docNotificacion) {
			AlmacenarAcuseReciboService.docNotificacion = docNotificacion;
		}




		public static String getAnio() {
			return anio;
		}




		public static void setAnio(String anio) {
			AlmacenarAcuseReciboService.anio = anio;
		}




		public static String getCodigoBarras() {
			return codigoBarras;
		}




		public static void setCodigoBarras(String codigoBarras) {
			AlmacenarAcuseReciboService.codigoBarras = codigoBarras;
		}




		public static String getFecha() {
			return fecha;
		}




		public static void setFecha(String fecha) {
			AlmacenarAcuseReciboService.fecha = fecha;
		}




		public static String getDescripcionNotificacion() {
			return descripcionNotificacion;
		}




		public static void setDescripcionNotificacion(String descripcionNotificacion) {
			AlmacenarAcuseReciboService.descripcionNotificacion = descripcionNotificacion;
		}




		public static String getEstado() {
			return estado;
		}




		public static void setEstado(String estado) {
			AlmacenarAcuseReciboService.estado = estado;
		}




		public static String getDocNotificacionBase64() {
			return docNotificacionBase64;
		}




		public static void setDocNotificacionBase64(String docNotificacionBase64) {
			AlmacenarAcuseReciboService.docNotificacionBase64 = docNotificacionBase64;
		}




		public static String getTipo() {
			return tipo;
		}




		public static void setTipo(String tipo) {
			AlmacenarAcuseReciboService.tipo = tipo;
		}




	 



		public static File getFile() {
			return file;
		}




		public static void setFile(File file) {
			AlmacenarAcuseReciboService.file = file;
		}




		public static String getRutaAcuseRecibo() {
			return rutaAcuseRecibo;
		}




		public static void setRutaAcuseRecibo(String rutaAcuseRecibo) {
			AlmacenarAcuseReciboService.rutaAcuseRecibo = rutaAcuseRecibo;
		}




		public static String getNumExp() {
			return numExp;
		}




		public static void setNumExp(String numExp) {
			AlmacenarAcuseReciboService.numExp = numExp;
		}




		public static String getAcuseReciboleido() {
			return acuseReciboleido;
		}




		public static void setAcuseReciboleido(String acuseReciboleido) {
			AlmacenarAcuseReciboService.acuseReciboleido = acuseReciboleido;
		}




		public static String getHora() {
			return hora;
		}




		public static void setHora(String hora) {
			AlmacenarAcuseReciboService.hora = hora;
		}



 
  
 	 
 
		
}
