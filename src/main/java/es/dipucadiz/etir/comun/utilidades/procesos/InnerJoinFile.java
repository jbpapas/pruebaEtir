package es.dipucadiz.etir.comun.utilidades.procesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import es.dipucadiz.etir.comun.utilidades.Impresion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class InnerJoinFile {
	/**
	 * Fusión de dos ficheros a través de un campo común
	 * @author MAFuentes
	 */
	
	private static boolean ok = false;
	private static long totalNulas = 0;
	/* Se define como fichero izquierda aquél que contiene solo las claves, el fichero izquierda el que contiene la relación claves-descripción. 
	 * Este programara cogerá las descripciones del fichero derecha obtenidas a partir de las claves en común del ficheros derecha e izquierda volcando el resultado en uno nuevo */
	private static int par1;	// Posición de inicio de clave en fichero derecha
	private static int par5;	// Posición de fin de clave en fichero derecha
	private static int par86;	// Posición de inicio de clave (también inicio de descripción excepto si "cambiaInSitu" en cuyo caso será al final de la línea) en fichero izquierda (fin de clave se calculará a partir de par1, par5 y par86)
	private static int par124;	// Posición de fin de descripción en fichero izquierda
	private static int par800;	// Ancho de línea mínimo
	//                 parTamañoDescripcionClave = par124 - par86 - par5 + par1
	private static boolean cambiaInSitu = false;	// Reemplazar código por descripción
	private static boolean ignorarDescripcionIzquiera = false;	// Ignorar descripción del fichero izquierda
	private static int vuelta = 0;
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		doLog("Iniciando InnerJoinFile...", null, false);	
		
		if(args == null || args.length != 4) {
			doLog("ERROR: Número de parámetros incorrecto", null, true);
			doLog("INFO: Sintaxis: ficheroIzq ficheroDer ficheroNuevo", null, false);
		
		} else {
			String nombreFicheroIzquierda = args[0];
			String nombreFicheroDerecha= args[1];
			String nombreFicheroResultado = args[2];
			String nombreFicheroResultadoTMP = nombreFicheroResultado + "tmp";
			String opcion = args[3];

			// Prepara caché
			preparaCache();
			
			// TASACON
			if(opcion.equals("1")) {
				// 1ª Vuelta para Calles de Direcciones Fiscales
				par1 = 1;
				par5 = 5;
				par86 = 86;
				par124 = 124;
				par800 = 800;
				vuelta = 1;
				cambiaInSitu = false;
				ignorarDescripcionIzquiera = false;
				ok = innerJoinFile(nombreFicheroIzquierda, nombreFicheroDerecha, nombreFicheroResultado);	

				// 2ª Vuelta para Calles de Direcciones Tributarias
				if(ok) {
					par1 = 1;
					par5 = 5;
					par86 = 157;
					par124 = 195;
					//par561 = 561;
					vuelta = 2;
					cambiaInSitu = false;
					ignorarDescripcionIzquiera = true;
					ok = innerJoinFile(nombreFicheroResultado, nombreFicheroDerecha, nombreFicheroResultadoTMP);
					
					(new File(nombreFicheroResultado)).delete();
					(new File(nombreFicheroResultadoTMP)).renameTo(new File(nombreFicheroResultado));	
				}
			
			} 
			
//			// PRUEBAS (¡¡no borrar!! sirve para pruebas concretas -desplazamiento del tasacón-)
//			else if(opcion.equals("2")) {	 
//				// 1ª Vuelta 
//				par1 = 4;
//				par5 = 8;
//				par86 = 87;
//				par124 = 120;
//				par561 = 561;			
//				vuelta = 1;			
//				cambiaInSitu = false;
//				ignorarDescripcionIzquiera = false;				
//				ok = innerJoinFile(nombreFicheroIzquierda, nombreFicheroDerecha, nombreFicheroResultado);
//				
//				// 2ª Vuelta 
//				if(ok) {
//					par1 = 4;
//					par5 = 8;
//					par86 = 158;
//					par124 = 196;
//					//par561 = 561;			
//					vuelta = 2;			
//					cambiaInSitu = false;
//					ignorarDescripcionIzquiera = true;					
//					ok = innerJoinFile(nombreFicheroResultado, nombreFicheroDerecha, nombreFicheroResultadoTMP);
//					
//					(new File(nombreFicheroResultado)).delete();
//					(new File(nombreFicheroResultadoTMP)).renameTo(new File(nombreFicheroResultado));
//				}
//				
//			} 
			
			else {
				doLog("ERROR: Tipo de InnerJoinFile incorrecto", null, true);
			}
		}
		
		if(ok) {
			doLog("InnerJoinFile finalizado correctamente.", null, false);
			if(totalNulas > 0)
				doLog("INFO: No se han podido encontrar " + totalNulas + " claves mediante innerJoinBusca", null, true);
		} else
			doLog("InnerJoinFile finalizado con errores.", null, false);
	}
	

	/******************* Funciones comunes ******************/
	
	private static Log log = null;
	private static void doLog(String msg, final Exception exception, final boolean isError) {
		msg = "InnerJoinFile: " + new Date() + ": " + msg;
		if (log == null) {
			System.out.println(msg);
			if (isError) 
				System.err.println(msg);
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
	
	private static String fuerzaAncho(String cadena, int ancho, boolean derecha) {
		String ret = null;
		if(cadena.trim().length() > ancho) {
			doLog("ERROR: fuerzaAncho encontró cadena demasiado grande y se ha truncado: Tengo " + cadena + " y tengo que conseguir " + ancho, null, false);
			ret = cadena.substring(0, ancho);
		} else {
			if(derecha)
				ret = Utilidades.padRight(cadena, ancho);
			else
				ret = Utilidades.padLeft(cadena, ancho);
		}
		
		
		return ret;	
	}

	private static void cierraFicheros(PrintWriter pwDer, BufferedReader brIzq) {
		try {
			if(pwDer != null)
				pwDer.close();
		} catch (Exception e) {}
		try {
			if(brIzq != null)
				brIzq.close();
		} catch (Exception e) {}
	}
	
	
	/******************* InnerJoinFile ******************/
	private static boolean bAñadeEspaciosADerecha = true;
	private static boolean innerJoinFile(String ficheroIzq, String ficheroDer, String ficheroNuevo) throws UnsupportedEncodingException, FileNotFoundException {
        // Lectura del fichero de la izquierda
		BufferedWriter archivoDer = null;
        PrintWriter pwDer = null;
		try {
			archivoDer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroNuevo), Impresion.CODIFICACION));
	        pwDer = new PrintWriter(archivoDer);
		} catch (IOException e1) {
			doLog("ERROR: No se puede abrir el fichero " + ficheroNuevo, e1, true);
			cierraFicheros(pwDer, null);
			
			return false;
		}

		BufferedReader brIzq = null;
		try {
			brIzq = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroIzq), Impresion.CODIFICACION));
		} catch (Exception e) {
			doLog("ERROR: No se puede abrir el fichero " + ficheroIzq, e, true);
			return false;
		}
		
        try {
            String linea = null;
            while((linea = brIzq.readLine()) != null) {
            	String primerCaracter = linea.substring(0,1);
            	if(!primerCaracter.matches("[a-zA-Z0-9 ]"))
            		linea = linea.substring(1);
            	if(vuelta == 1)
            		linea = fuerzaAncho(linea, par800, true).substring(0, par800);
            	
            	// Búsqueda de la parte de la cadena relacionada
            	try {
	            	String temp = innerJoinBusca(ficheroDer, linea.substring(par86 - 1, par86 + par5 - par1));	
	            	if(Utilidades.isEmpty(temp)) {
	    				temp = "";
	    				totalNulas ++;
	
	            		// 	Montaje de la variable con la parte encontrada
	    				if(cambiaInSitu) {
	    					linea = linea.substring(0, par86 - 1) + fuerzaAncho(temp, par5, true) + linea.substring(par86 + par5 - par1);	 
	    				} else {
	    					if(ignorarDescripcionIzquiera)
	    						linea = linea + fuerzaAncho(temp, par124 - par86 + par1 - par5, true);
	    					else
	    						linea = linea + linea.substring(par86 + par5 - par1, par124);	
	    				}
	    				
	            	} else {
    					// Montaje de la variable con la parte encontrada
	    				if(cambiaInSitu) {
	    					linea = linea.substring(0, par86 - 1) + fuerzaAncho(temp, par124 - par86 + par1, true) + linea.substring(par124);	
	    				} else {
	    					linea = linea + fuerzaAncho(temp, par124 - par86 + par1 - par5, true);  	
	    				}
	            	}
	            	
            	} catch (Exception e) {
            		doLog("INFO: Línea demasiado pequeña obtenida en innerJoinBusca", null, true);
            	}
            	
            	try {
            		pwDer.println(linea);
            		//archivoDer.write(linea);
	    		} catch (Exception e1) {
	    			doLog("ERROR: No se puede escribir en el fichero " + ficheroNuevo, e1, bAñadeEspaciosADerecha);
	    			cierraFicheros(pwDer, brIzq);
//	    			cierraFicheros(null, brIzq);
//	    			archivoDer.close();
	    			return false;
	    		}
            }
            
		} catch (IOException e) {
			doLog("ERROR: No se puede leer del fichero " + ficheroIzq, e, true);
			return false;
		}
		cierraFicheros(pwDer, brIzq);
//		cierraFicheros(null, brIzq);
//		try {
//			archivoDer.close();
//		} catch (IOException e) {
//			return false;
//		}
		
		return true;		
	}
	
	private static String innerJoinBusca(final String ficheroDer, final String parteIzq) {
		String lineaDerEncontrada = null, tmpParteIzq = parteIzq.trim(), tmpParteDer = null;

		// Búsqueda en caché
		lineaDerEncontrada = buscaEnCache(parteIzq);
		if(lineaDerEncontrada != null)
			return lineaDerEncontrada;
		
		BufferedReader brDer = null;
		try {
			brDer = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroDer), Impresion.CODIFICACION));	//"UTF-8"
		} catch (Exception e) {
			doLog("ERROR: No se puede abrir el fichero " + ficheroDer, e, true);
			return null;
		}
		
        try {
            String linea = null;
            while((linea = brDer.readLine()) != null) {
            	try {
	            	tmpParteDer = linea.substring(par1 - 1, par5).trim();				
	            	if(tmpParteDer.equals(tmpParteIzq)) {	
	            		lineaDerEncontrada = linea.substring(par5, par124 - par86 + par1);		
	            		anadeCache(tmpParteIzq, lineaDerEncontrada);
	            		break;
	            	}
            	} catch (Exception e) {}
            }
		} catch (IOException e) {
			doLog("ERROR: No se puede leer del fichero " + ficheroDer, e, true);
			return null;
		}
		cierraFicheros(null, brDer);		
		
		return lineaDerEncontrada;
	}

	
	/******************* Caché ******************/
	
	private static Map<String, String> cache = null;
	private final static int MAX_ELEMENTOS_CACHE = 50000000;
	private static void preparaCache() {
		cache = new HashMap<String, String>();		
	}
	private static String lastParteIzq = null;
	private static String lastRes = null;
	private static String buscaEnCache(String parteIzq) {
		if(parteIzq == null)
			return null;

		// Búsqueda en mini caché (o sea, mira el último elemento tratado)
		if(parteIzq.equals(lastParteIzq))
			return lastRes;
		
		// Búsqueda en caché
		String res = cache.get(parteIzq);
		
		lastParteIzq = parteIzq;
		lastRes = res;

		return res;
	}
	private static void anadeCache(String parteIzq, String parteDer) {
		// Si ya está no se añade a la caché
		String enCache = buscaEnCache(parteIzq);
		if(enCache != null) {
			if(!parteDer.equals(enCache))
				doLog("ERROR: Se han encontrado al menos dos datos para la clave " + parteIzq + ": " + parteDer + " y " + enCache, null, true);
			return;
		}
		
		// Controla el máximo de elementos en la caché
		if(cache.size() <= MAX_ELEMENTOS_CACHE) {
			// Añade a caché
			cache.put(parteIzq, parteDer);
		}
		
	}
}


