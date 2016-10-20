package es.dipucadiz.etir.comun.utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSProxy;
import es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSServiceLocator;
 

public  class GeoDirService {
	private static final Log LOG = LogFactory.getLog(GeoDirService.class);

 
	private static InterfazCDAUWSServiceLocator locator = new InterfazCDAUWSServiceLocator();		 	
	private static InterfazCDAUWSProxy accesoWS = new InterfazCDAUWSProxy(locator.getInterfazCDAUWSAddress());	
		
 
	public static  String[] envio (String nombreVia, String numero, String tipoVia, String codINE){		 
		 
		String[] resultado=new String[11];
		
		 try{
 		     
			 resultado=accesoWS.geocoderSrs(nombreVia, numero, tipoVia, codINE,  GadirConfig.leerParametro("srs"));
				  
 				 
				 
		 }catch (Exception e){
			 
		 }
		 return resultado; 
		 
	 }


	public static InterfazCDAUWSServiceLocator getLocator() {
		return locator;
	}


	public static void setLocator(InterfazCDAUWSServiceLocator locator) {
		GeoDirService.locator = locator;
	}


	public static Log getLog() {
		return LOG;
	}

}
