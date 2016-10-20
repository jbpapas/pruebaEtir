package es.dipucadiz.etir.comun.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.bo.DocumentoNotificacionBO;
import es.dipucadiz.etir.comun.bo.NotificacionPeticionBO;
import es.dipucadiz.etir.comun.bo.PortafirmasBO;
import es.dipucadiz.etir.comun.bo.RemesaCargoCanalResBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.DocumentoNotificacionDTO;
import es.dipucadiz.etir.comun.dto.NotificacionPeticionDTO;
import es.dipucadiz.etir.comun.dto.RemesaCargoCanalResDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
 

public  class InsertarIDECadiz {
	private static final Log LOG = LogFactory.getLog(InsertarIDECadiz.class);


	private static DocumentoNotificacionBO documentoNotificacionBO ;
	private static NotificacionPeticionBO notificacionPeticionBO ;
	private static RemesaCargoCanalResBO remesaCargoCanalResBO;
		 
 		public static  String  envio (String coDocumentoNotificacion, String envioRecepcion){
		//recibiremos una E si es un dato de un Envio de notificación o una R si es la recepción de los datos 
		// cuando sera de tipo recepción en coDocumentoNotificacion recibiremos el coNotificacionPeticion
		 
//		INSERT INTO georrepositorio.sig_notifrecau(geom, _dtcreate, _dtmodify, _unid, _usrcreate, _usrmodify, _codigo, _estado, _fbaja, _nombre, _entidad_fk, _municipio_fk) VALUES (ST_GeomFromText('POINT()', 25830), ?, ?, ?, ?, ?, ?, …)
//
//		Ejemplo de uso de la función de transformación de coordenadas:
//
//		ST_GeomFromText('POINT(-71.064544 42.28787)');
//		CREATE TABLE georrepositorio.sig_notif_recau
//		(
//		  id bigint NOT NULL DEFAULT nextval('georrepositorio.sig_notif_recau_secuence'::regclass),
//		  _dtcreate timestamp without time zone NOT NULL DEFAULT ('now'::text)::date,
//		  _dtmodify timestamp without time zone NOT NULL DEFAULT ('now'::text)::date,
//		  _unid character varying(32) NOT NULL DEFAULT 'SIG'::character varying,
//		  _usrcreate character varying(255) NOT NULL DEFAULT 'SIG'::character varying,
//		  _usrmodify character varying(255) NOT NULL DEFAULT 'SIG'::character varying,
//		  _codigo character varying(100) NOT NULL,
//		  _estado integer NOT NULL,
//		  _fbaja timestamp without time zone,
//		  _nombre character varying(250) NOT NULL,
//		  _entidad_fk bigint NOT NULL,
//		  _municipio_fk bigint,
//		  geom geometry,
//		  dni character varying(500),
//		  figura character varying(500),
//		  documento character varying(500),
//		  co_resultado character varying(500),
//		  fx_resultado date,
//		  tipo_notificacion character varying(500),
//		  codigo_barras character varying(500),
//		  codigo_barras_not character varying(500),
//		  co_remesa_cargo_canal_res integer,
//		  texto_domicilio character varying(500),
//		  importe double precision,
		

		
		String error = "0"; 
		Connection conn =conectarPostGres();
		Statement st = null;
		 try{  
				  conn =conectarPostGres();
				  st = conn.createStatement();
			  
				if(envioRecepcion.equals("E")){ 
				   
   			        DetachedCriteria dcDocNot = DetachedCriteria.forClass(DocumentoNotificacionDTO.class);
					dcDocNot.createAlias("unidadUrbanaDTO", "u", DetachedCriteria.LEFT_JOIN); 
					dcDocNot.createAlias("u.calleDTO", "c", DetachedCriteria.LEFT_JOIN); 
					dcDocNot.createAlias("c.municipioDTO", "m", DetachedCriteria.LEFT_JOIN); 
					dcDocNot.createAlias("m.provinciaDTO", "p", DetachedCriteria.LEFT_JOIN);  
					dcDocNot.createAlias("documentoDTO", "d", DetachedCriteria.LEFT_JOIN); 
					dcDocNot.createAlias("d.clienteDTO", "cl", DetachedCriteria.LEFT_JOIN); 				
//					dcDocNot.createAlias("remesaCargoCanalResDTO", "rc", DetachedCriteria.LEFT_JOIN); 		
//					dcDocNot.createAlias("rc.remesaDTO", "r"); 					
					dcDocNot.add(Restrictions.eq("coDocumentoNotificacion", Long.valueOf(coDocumentoNotificacion)));
					List<DocumentoNotificacionDTO> listaDocNot = documentoNotificacionBO.findByCriteria(dcDocNot);
 					
 					if(listaDocNot.size()>0){
	 						DocumentoNotificacionDTO docNot = listaDocNot.get(0);
							
 							
 						 	if(conn!=null){ 							
									
									//si la cadena coordenadasUnidUrb esta vacia no llamaremos a la función ST_GeomFromText,
									//geom
									Date _dtcreate = Utilidades.getDateActual();
									String _dtmodify="";
									String _unid ="";
									String _usrcreate = DatosSesion.getLogin();
									String _usrmodify="";
									String _codigo= String.valueOf(docNot.getCoDocumentoNotificacion());
									Integer _estado=2;
									String _fbaja = "null";
									String _nombre = docNot.getDocumentoDTO().getClienteDTO().getNifNombre();
									Integer _entidad_fk= 1811;
									Integer _municipio_fk = 11000;									
									if( docNot.getUnidadUrbanaDTO()!=null){
										_municipio_fk	=	Integer.valueOf(docNot.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getId().getCoProvincia()+""+docNot.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getId().getCoMunicipio());
									}
									String dni   = docNot.getDocumentoDTO().getClienteDTO().getIdentificador(); 
 									String figura= Utilidades.figuraFisicaJuridica(dni);
									String documento  = docNot.getDocumentoDTO().getCodigoConEspacios();
									String co_resultado  ="";
									
									String textoResultado ="";
									if (docNot.getCoResultado()==null){
										co_resultado="00"; textoResultado="Petición";
									}
									Date fx_resultado = null;				 
									
									String tipo_notificacion  = docNot.getTipoNotificacion()+ " - "+ TablaGt.getValor("DOCNOTIF", docNot.getTipoNotificacion(), "DESCRIPCION");  
									String codigo_barras = docNot.getCodigoBarras();
									String codigo_barras_not = docNot.getCodigoBarrasNot();
									String co_remesa_cargo_canal_res  = "";
									if (docNot.getRemesaCargoCanalResDTO()!=null){
										DetachedCriteria dcRemesaCar = DetachedCriteria.forClass(RemesaCargoCanalResDTO.class);
										dcRemesaCar.createAlias("remesaDTO", "r");
										dcRemesaCar.add(Restrictions.eq("coRemesaCargoCanalRes", docNot.getRemesaCargoCanalResDTO().getCoRemesaCargoCanalRes()));
										
										List<RemesaCargoCanalResDTO> listRem =  remesaCargoCanalResBO.findByCriteria(dcRemesaCar);
										if(listRem.size()>0){
											co_remesa_cargo_canal_res  = listRem.get(0).getRemesaDTO().getCoRemesa(); 
													 
										}
						 			  
									}
									String texto_domicilio ="";
									if(docNot.getUnidadUrbanaDTO()!=null){ 
										texto_domicilio= getDescripcionDomicilio(docNot.getUnidadUrbanaDTO(), true);									
									}
									String importe =""; //El ultimo digito del codigo de barras es de control
									//los otros 8 son importe y lleva 2 decimales
									
									
									importe=codigo_barras.substring(codigo_barras.length()-9, codigo_barras.length()-1);
																		 
									Double importeDouble = Double.valueOf(importe)/100;
 			 						
									String insert ="INSERT INTO georrepositorio.sig_notif_recau(_dtcreate, _dtmodify, _unid, _usrcreate, _usrmodify, _codigo, _estado, _fbaja, _nombre, _entidad_fk, _municipio_fk,"
											         + " dni , figura ,  documento ,co_resultado ,fx_resultado ,tipo_notificacion,codigo_barras,codigo_barras_not ,co_remesa_cargo_canal_res,texto_domicilio,importe,geom, resultado) "
												 + "VALUES (to_date('"+ Utilidades.dateToStrutsFormat(_dtcreate)+"','YYYY-MM-DD'),"+ 
											         
													"to_date('"+ Utilidades.dateToStrutsFormat(_dtcreate)+"','YYYY-MM-DD'),'"+  _unid   +"','"+     _usrcreate       +"','"+     _usrmodify     +"','"+  _codigo    +"','"+ _estado												
													+"',null,'"+  _nombre  +"','"+  _entidad_fk  +"','"+  _municipio_fk  +"','"+ dni   +"','"+   figura  +"','"+  documento  +"','"+ co_resultado   +"',";
 									 
											insert = insert+"null";
 													
										insert=insert+",'"+ tipo_notificacion   +"','"+      codigo_barras   +"','"+ codigo_barras_not  +"','"+    co_remesa_cargo_canal_res +"','"+    texto_domicilio        
											        +"','"+  importeDouble.toString() ;
 									
			 						String coordenadasUnidUrb="";
			 						if (envioRecepcion.equals("E")){
			 							//es un envio de una notificación por lo que los datos de coordenadas estan en la Unidad Urbana de documentoNotificación
				 						if(!Utilidades.isEmpty(docNot.getUnidadUrbanaDTO().getCoordenadaX()) && !Utilidades.isEmpty(docNot.getUnidadUrbanaDTO().getCoordenadaY())){
				 							  coordenadasUnidUrb=docNot.getUnidadUrbanaDTO().getCoordenadaX()+" "+docNot.getUnidadUrbanaDTO().getCoordenadaY();
				 							 insert = insert+"',ST_GeomFromText('POINT("+coordenadasUnidUrb+")', 25830)";
				 						}else{
				 							
				 							 insert = insert+"',null";
				 						} 
			 						}
			 						insert = insert+",'"+textoResultado +"'";
									insert = insert+")";																		
									
									Integer resultado = st.executeUpdate(insert);
									if(resultado!=1){
										 error = "Error insertando fila";  					
									} 
								  
 						 	} 

 					}else{
 						 error = "No se encuentra DocumentoNotificacionDTO con código "+coDocumentoNotificacion; 
 					}

		 }else{

				 DetachedCriteria dcNotifPet = DetachedCriteria.forClass(NotificacionPeticionDTO.class);
				 dcNotifPet.add(Restrictions.eq("coNotificacionPeticion", Long.valueOf(coDocumentoNotificacion)));
						
				List<NotificacionPeticionDTO> listNotPet = notificacionPeticionBO.findByCriteria(dcNotifPet);
				
 				if(listNotPet.size()>0){
 					    NotificacionPeticionDTO notifPet = listNotPet.get(0);						
						 
					 	if(conn!=null){ 
					 		    String codigoBarrasPet = notifPet.getCodigoBarras();
					 		    String codigoUnificado = CodigoBarrasUtil.getCodBarras(codigoBarrasPet);
					 		    		 
					 		 	DetachedCriteria dcDocumentoNot =DetachedCriteria.forClass(DocumentoNotificacionDTO.class);
					 		 	//			 		 	dcDocumentoNot.createAlias("unidadUrbanaDTO", "u", DetachedCriteria.LEFT_JOIN); 
//					 		 	dcDocumentoNot.createAlias("u.calleDTO", "c", DetachedCriteria.LEFT_JOIN); 
//					 		 	dcDocumentoNot.createAlias("c.municipioDTO", "m", DetachedCriteria.LEFT_JOIN); 
//					 		 	dcDocumentoNot.createAlias("m.provinciaDTO", "p", DetachedCriteria.LEFT_JOIN);  
					 		 	dcDocumentoNot.createAlias("documentoDTO", "d", DetachedCriteria.LEFT_JOIN); 
					 		 	dcDocumentoNot.createAlias("d.clienteDTO", "cl", DetachedCriteria.LEFT_JOIN); 				
//					 		 	dcDocumentoNot.createAlias("remesaCargoCanalResDTO", "rc", DetachedCriteria.LEFT_JOIN); 		
//					 		 	dcDocumentoNot.createAlias("rc.remesaDTO", "r"); 					
					 		 	dcDocumentoNot.add(Restrictions.eq("codigoBarras", codigoUnificado));
					 		 	List<DocumentoNotificacionDTO> listaDocNot = documentoNotificacionBO.findByCriteria(dcDocumentoNot);
								
					 		 	if(listaDocNot.size()>0){
						 		 	DocumentoNotificacionDTO	docNot = listaDocNot.get(0);
						 		 	
						 		  
									//si la cadena coordenadasUnidUrb esta vacia no llamaremos a la función ST_GeomFromText,
									//geom
									Date _dtcreate = Utilidades.getDateActual();
									String _dtmodify="";
									String _unid ="";
									String _usrcreate = DatosSesion.getLogin();
									String _usrmodify="";
									String _codigo= String.valueOf(notifPet.getCoNotificacionPeticion());
									Integer _estado=2;
									String _fbaja = "null";
									String _nombre = docNot.getDocumentoDTO().getClienteDTO().getNifNombre();
									Integer _entidad_fk= 1811;
									Integer _municipio_fk = 11000;									
									if( docNot.getUnidadUrbanaDTO()!=null){
										_municipio_fk	=	Integer.valueOf(docNot.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getId().getCoProvincia()+""+docNot.getUnidadUrbanaDTO().getCalleDTO().getMunicipioDTO().getId().getCoMunicipio());
									}
									String dni   = docNot.getDocumentoDTO().getClienteDTO().getIdentificador(); 
									String figura= Utilidades.figuraFisicaJuridica(dni);
									String documento  = docNot.getDocumentoDTO().getCodigoConEspacios();
									String co_resultado  ="";
									String textoResultado ="";
									if (docNot.getCoResultado()==null){
										co_resultado="00"; textoResultado="Petición";
									}else{
										co_resultado=docNot.getCoResultado();
										textoResultado =TablaGt.getValor("TRESNOT", co_resultado, "DESCRIPCION");
									}
									
							
									
									
									
									
									Date fx_resultado = null;
									if (envioRecepcion.equals("R")){
										  fx_resultado = docNot.getFxResultado();
									}
										 
									
									String tipo_notificacion  = docNot.getTipoNotificacion()+ " - "+ TablaGt.getValor("DOCNOTIF", docNot.getTipoNotificacion(), "DESCRIPCION");  
									String codigo_barras = docNot.getCodigoBarras();
									String codigo_barras_not = docNot.getCodigoBarrasNot();
									String co_remesa_cargo_canal_res  = "";
									if (docNot.getRemesaCargoCanalResDTO()!=null){
										DetachedCriteria dcRemesaCar = DetachedCriteria.forClass(RemesaCargoCanalResDTO.class);
										dcRemesaCar.createAlias("remesaDTO", "r");
										dcRemesaCar.add(Restrictions.eq("coRemesaCargoCanalRes", docNot.getRemesaCargoCanalResDTO().getCoRemesaCargoCanalRes()));
										
										List<RemesaCargoCanalResDTO> listRem =  remesaCargoCanalResBO.findByCriteria(dcRemesaCar);
										if(listRem.size()>0){
											co_remesa_cargo_canal_res  = listRem.get(0).getRemesaDTO().getCoRemesa(); 
													 
										}
						 			  
									}
									String texto_domicilio = "";
									if(docNot.getUnidadUrbanaDTO()!=null){ 
										texto_domicilio= getDescripcionDomicilio(docNot.getUnidadUrbanaDTO(), true);									
									}
									String importe =""; //El ultimo digito del codigo de barras es de control
									//los otros 8 son importe y lleva 2 decimales
									
									

									importe=codigo_barras.substring(codigo_barras.length()-9, codigo_barras.length()-1);
									
									 
									Double importeDouble = Double.valueOf(importe)/100;
									
	 								String insert ="INSERT INTO georrepositorio.sig_notif_recau(_dtcreate, _dtmodify, _unid, _usrcreate, _usrmodify, _codigo, _estado, _fbaja, _nombre, _entidad_fk, _municipio_fk,"
											         + " dni , figura ,  documento ,co_resultado ,fx_resultado ,tipo_notificacion,codigo_barras,codigo_barras_not ,co_remesa_cargo_canal_res,texto_domicilio,importe,geom, resultado) "
												 + "VALUES (to_date('"+ Utilidades.dateToStrutsFormat(_dtcreate)+"','YYYY-MM-DD'),"+ 
											         
													"to_date('"+ Utilidades.dateToStrutsFormat(_dtcreate)+"','YYYY-MM-DD'),'"+  _unid   +"','"+     _usrcreate       +"','"+     _usrmodify     +"','"+  _codigo    +"','"+ _estado												
													+"',null,'"+  _nombre  +"','"+  _entidad_fk  +"','"+  _municipio_fk  +"','"+ dni   +"','"+   figura  +"','"+  documento  +"','"+ co_resultado   +"',";
													
													
										if(fx_resultado!=null){			
											insert=insert+  "to_date('"+Utilidades.dateToStrutsFormat(fx_resultado)+"','YYYY-MM-DD')";
										}else{
											insert = insert+"null";
											
										}			
													
										insert=insert+",'"+ tipo_notificacion   +"','"+      codigo_barras   +"','"+ codigo_barras_not  +"','"+    co_remesa_cargo_canal_res +"','"+    texto_domicilio        
											        +"','"+  importeDouble.toString() ;
									
									
									
			 						String coordenadasUnidUrb="";
			 						if (envioRecepcion.equals("E")){
			 							//es un envio de una notificación por lo que los datos de coordenadas estan en la Unidad Urbana de documentoNotificación
				 						if(!Utilidades.isEmpty(docNot.getUnidadUrbanaDTO().getCoordenadaX()) && !Utilidades.isEmpty(docNot.getUnidadUrbanaDTO().getCoordenadaY())){
				 							  coordenadasUnidUrb=docNot.getUnidadUrbanaDTO().getCoordenadaX()+" "+docNot.getUnidadUrbanaDTO().getCoordenadaY();
				 							 insert = insert+"',ST_GeomFromText('POINT("+coordenadasUnidUrb+")', 25830)";
				 						}else{
				 							
				 							 insert = insert+"',null";
				 						} 
			 						}else {
			 							//Si es una recepción los datos de donde se ha producido la entrega o el intento de entrega están en GA_
			 							
			 							if(listNotPet.size()>0){
			 								NotificacionPeticionDTO notPet = listNotPet.get(0);
					 						if(!Utilidades.isEmpty(notPet.getLatitud()) && !Utilidades.isEmpty(notPet.getLongitud())){
					 							//ST_SetSRID(ST_MakePoint(longitude, latitude), 25830),
					 							coordenadasUnidUrb = Utilidades.Deg2UTM( Double.valueOf(notPet.getLatitud()),   Double.valueOf(notPet.getLongitud())  );
					 							// insert = insert+"',ST_GeomFromText(ST_MakePoint("+notPet.getLongitud()+","+ notPet.getLatitud()+"), 25830)"; 
					 							 
					 							 insert = insert+"', ST_GeomFromText('POINT("+coordenadasUnidUrb+")', 4326)";
					 						}else{
					 							
					 							 insert = insert+"',null";
					 						}
			 								
			 							}
			 						}
			 						insert = insert+",'"+textoResultado +"'";
									insert = insert+")";
	 								Integer resultado = st.executeUpdate(insert);
									if(resultado!=1){
										 error = "Error insertando fila";  					
									} 
									 
									
						 		}else{
						 		 		error = "Error recuperando documento notificado";  		
						 		}
							
					 	} 
		 }
		 }
 					
		 }catch (Exception e){
			 
			 error = "Excepción durante la ejecución "+ e.getMessage(); 
		 }finally{
			 try {
				 System.out.println(error);
				st.close();
		
			   conn.close();  
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
		 }		 
		 return error; 		 
	 }


	 private static Connection conectarPostGres(){
		 Connection conn = null;  
		 
		 try{
				 Class.forName("org.postgresql.Driver");    
				
	//		 conn = DriverManager.getConnection("jdbc:postgresql://" + GadirConfig.leerParametro("server") + ":" 
	//		+ GadirConfig.leerParametro("puerto") + "/" + GadirConfig.leerParametro("dataBaseName"),GadirConfig.leerParametro("user"), GadirConfig.leerParametro("password"));  
			 
			 conn = DriverManager.getConnection("jdbc:postgresql://172.22.9.177:5432/georeferenciacion_uno","postgres", "postgres"); 
			 
		 	}catch (Exception e){
		 
		 		conn = null; 
		 	 
		 	}
		 return conn;
	 }

	 
	 
	 private static String getDescripcionDomicilio(UnidadUrbanaDTO unidadUrbanaDTO, boolean conMunicipio) {
			StringBuffer result = new StringBuffer();
			if (Utilidades.isNotEmpty(unidadUrbanaDTO.getCalleDTO().getSigla())){
				result.append(unidadUrbanaDTO.getCalleDTO().getSigla());
			}
			if (Utilidades.isNotEmpty(unidadUrbanaDTO.getCalleDTO().getNombreCalle())){
				result.append(" ").append(unidadUrbanaDTO.getCalleDTO().getNombreCalle());
			}
			
			result.append(" ").append(unidadUrbanaDTO.getCadenaCompleta());
			 
			
			if (unidadUrbanaDTO.getCp() != null && !unidadUrbanaDTO.getCp().equals(0)) {
				result.append(", ").append(unidadUrbanaDTO.getCp());
			}
			if (conMunicipio){
				if (unidadUrbanaDTO.getCalleDTO().getMunicipioDTO() != null){
					result.append(", ").append(unidadUrbanaDTO.getCalleDTO().getMunicipioDTO().getNombre());
					if(unidadUrbanaDTO.getCalleDTO().getCalleUbicacionDTO() != null) {
						String ubicacion = "";
						try {
							CalleUbicacionDTO calleUbicacionDTO = ((CalleUbicacionBO) GadirConfig.getBean("calleUbicacionBO")).findById(unidadUrbanaDTO.getCalleDTO().getCalleUbicacionDTO().getCoCalleUbicacion());
							ubicacion = calleUbicacionDTO.getUbicacion();
						} catch(Exception e) {
							ubicacion = "";
						}
						result.append(" - ").append(ubicacion);
					}
					result.append(" (").append(unidadUrbanaDTO.getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre()).append(")");
				}
			}
			
			String resultString;
			if (result.length() == 0){
				resultString = "";
			} else {
				resultString = result.toString();
			}
			return resultString;
		}
	 
	public static Log getLog() {
		return LOG;
	}


	public InsertarIDECadiz() {
		super();
	}


	public static DocumentoNotificacionBO getDocumentoNotificacionBO() {
		return documentoNotificacionBO;
	}


	public   void setDocumentoNotificacionBO(
			DocumentoNotificacionBO documentoNotificacionBO) {
		InsertarIDECadiz.documentoNotificacionBO = documentoNotificacionBO;
	}


	public static NotificacionPeticionBO getNotificacionPeticionBO() {
		return notificacionPeticionBO;
	}


	public   void setNotificacionPeticionBO(
			NotificacionPeticionBO notificacionPeticionBO) {
		InsertarIDECadiz.notificacionPeticionBO = notificacionPeticionBO;
	}


	public static RemesaCargoCanalResBO getRemesaCargoCanalResBO() {
		return remesaCargoCanalResBO;
	}


	public   void setRemesaCargoCanalResBO(
			RemesaCargoCanalResBO remesaCargoCanalResBO) {
		InsertarIDECadiz.remesaCargoCanalResBO = remesaCargoCanalResBO;
	}


 
	
//	public static PortafirmasBO getPortafirmasBO() {
//		return portafirmasBO;
//	}
//
//	public void setPortafirmasBO(PortafirmasBO portafirmasBO) {
//		PortafirmasService.portafirmasBO = portafirmasBO;
//	}
	

}
