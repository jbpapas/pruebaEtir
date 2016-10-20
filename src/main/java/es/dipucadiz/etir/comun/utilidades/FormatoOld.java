/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Controlar formatos y longitudes de datos.
 * @author JRonnols
 */
public final class FormatoOld {
	FormatoOld() {}
	/**
	 * En PL/Java: comun_util.Formato_formatearDato.
	 * El parámetro opcion puede ser:
	 * ' ' - FORMATO DE GRABACIÓN (PARA CUALQUIER TIPO)
	 * 'V' - FORMATO DE VISUALIZACIÓN (PARA CUALQUIER TIPO)
	 * 'A' - FORMATO PARA CONVERTIR EL TIPO FECHA (VÁLIDA) EN AAAMMDD
	 * 'X' - FORMATO DE EXTRACCIÓN. 
	 
	Obsoletos:
	 * 'E' - SALIDA CON MASCARA DE EDICION.
	 * 'N' - SIN CONTROL DE ERRORES.
	 * 'C' - PARA LOS NUMERICOS PARA LA EM=9999999999.9999 (NO SE TRUNCA NI SUS ENTEROS NI SUS DECIMALES).
	 * @param dato
	 * @param formato
	 * @param longitud
	 * @param opcion
	 * @return El dato formateado.
	 * @throws GadirServiceException
	 */
	public static String formatearDato(String datoParam, String formato, double longitud, String opcion) throws GadirServiceException {
		String opcion_inicial = opcion;
		
		if(opcion == null || opcion.length() == 0)
			opcion = " ";
		else
			opcion = opcion.toUpperCase();
		
		if(!(formato == null || formato.length() == 0))
			formato = formato.toUpperCase();

		if( !(opcion.equals(" ") || opcion.equals("V") || (opcion.equals("A") && formato.equals("D") && formato.equals("X"))) &&
			!(formato.equals("A") || formato.equals("D") || formato.equals("T") || formato.equals("N") || formato.equals("I") || formato.equals("K") || formato.equals("E"))
		  ) throw new GadirServiceException(Integer.toString(70143));
		
		// Equivalencias
		if(opcion.equals("X")) {
//			if(formato.equals("N") || formato.equals("I") || formato.equals("K") || formato.equals("E"))
//				formato = "N";
			if(formato.equals("I"))
				formato = "N";
			
			if(formato.equals("N")) {
				if(datoParam == null)
					datoParam = "0";
				formato = "E";
				opcion = "V";
			}
			else
				opcion = "V";
		}
		
		longitud = roundDouble(longitud, 1);
		
		final String dato = datoParam == null ? "" : datoParam;
		String devolver;
		final char formatoChar = formato.charAt(0);
		
		switch (formatoChar) {
		case 'A':
			//old//case 'X':
			if(opcion_inicial.equalsIgnoreCase("X"))
				devolver = formatearAlfa(dato, longitud, opcion.charAt(0));
			else
				devolver = formatearAlfa(dato.trim(), longitud, opcion.charAt(0));
			break;
		case 'N':
		case 'I':	 
		case 'K':	 
		case 'E':	 
			devolver = formatearNumerico(formatoChar, dato.trim(), longitud, opcion.charAt(0));
			break;
		case 'D':
			//old//case 'F':
			//old//case 'G':
			devolver = formatearFecha(dato.trim(), formatoChar, opcion.charAt(0));
			break;
		case 'T':
			devolver = formatearHora(dato.trim());
			break;
		default:
			devolver = dato;
			break;
		}
		
		if(devolver != null) {
			// Equivalencias
			if(opcion_inicial.equals("X")) {
				devolver = UtilidadesFormato.replace(devolver, ",", "");	// No existe replace(String,String) en Java 1.4.x
				if(formato.equals("A"))
					return devolver;
			}
			return devolver.trim();
		}
		else
			return devolver;
	}
	
	private static String formatearHora(String entrada) throws GadirServiceException {
		String horas;
		String minutos;
		String segundos;
		if (entrada.indexOf(':') > -1) {
			String[] trozos = entrada.split(":");
			if (trozos.length >= 1) horas = trozos[0];
			else horas = "00";
			if (trozos.length >= 2) minutos = trozos[1];
			else minutos = "00";
			if (trozos.length >= 3) segundos = trozos[2];
			else segundos = "00";
		} else {
			if (entrada.length() >= 2) horas = entrada.substring(0, 2);
			else horas = "00";
			if (entrada.length() >= 4) minutos = entrada.substring(2, 4);
			else minutos = "00";
			if (entrada.length() >= 6) segundos = entrada.substring(4, 6);
			else segundos = "00";
		}
		horas = formatearNumerico('E', horas, 2, ' ');
		minutos = formatearNumerico('E', minutos, 2, ' ');
		segundos = formatearNumerico('E', segundos, 2, ' ');
		String salida = horas + ":" + minutos + ":" + segundos;
		return salida;
	}
	private static String formatearAlfa(final String dato, final double longitud, final char opcion) throws GadirServiceException {
		int entero;
		String devolver;
		final int wInt = (int) longitud;
		entero = dato.length();
		if (wInt < entero && opcion != 'N' && opcion != ' ' && opcion != 'V') {
			throw new GadirServiceException(Integer.toString(5518));
		}
		if (wInt < entero) {
			devolver = dato.substring(0, wInt);
		} else {
			devolver = dato;
		}
		
		return devolver;
	}
	
	private static boolean perdidaEnFormateoNumerico(String dato, double longitud) {
		boolean bRes = false;
		String[] mat = dato.split(",");
		if(mat.length > 1) {
			if(mat[1].replaceAll("0", "").length() > 0 && longitud - (long)longitud == 0) {
				bRes = true;
			}
		}

		return bRes;
	}
	
	private static String formatearNumerico(char formato, String dato, double longitud, char opcion) throws GadirServiceException {
		longitud = roundDouble(longitud, 1);
		
		dato = dato.replace('.', ',');		//Compatibilidad con datos decimales con punto en lugar de coma
		if(dato.startsWith(","))
			dato = "0" + dato;
		if(dato.equals("0,"))
			dato = "0";
				
		if(perdidaEnFormateoNumerico(dato, longitud))
			throw new GadirServiceException(Integer.toString(20052));
		
		String datoSinComaConPunto = dato.replace(',', '.');

		// Equivalencias
		if(opcion == ' ' && formato == 'E') {
			opcion = 'C';
			formato = 'N';
			if(UtilidadesFormato.isEmpty(dato))
				return null;
		}
		else if(opcion == 'V' && formato == 'I') {
			opcion = 'E';
			formato = 'N';
			if(UtilidadesFormato.isEmpty(dato))
				return null;
		}
		
		//Salidas por defecto
		if(opcion == ' ' && formato == 'K' && UtilidadesFormato.isEmpty(dato))
			return null;
		else if( (opcion == 'V' && 
				 (formato == 'E' || formato == 'N' || formato == 'K') 
				) || 
				(opcion == ' ' && formato == 'N') || 
				(opcion == ' ' && formato == 'I') 	
		  	   )
			if (UtilidadesFormato.isEmpty(dato) ||  dato.length() == 0 || 
				(formato != 'E' && 
				  ( 
					( !UtilidadesFormato.contains(datoSinComaConPunto, ".") && UtilidadesFormato.isNumeric(datoSinComaConPunto) && UtilidadesFormato.isBigIntegerZero(datoSinComaConPunto) ) ||
					( UtilidadesFormato.contains(datoSinComaConPunto, ".") && UtilidadesFormato.isNumericDecimal(datoSinComaConPunto) && UtilidadesFormato.isBigDecimalZero(datoSinComaConPunto) )
				  )
			     )
			 	)
				return null;
		
		String tempString, devolver;
		BigDecimal tempDouble = null;
		boolean formatoErroneo;
		// Comprobación de si realmente es numerico.
		try {
			tempDouble = new BigDecimal(dato.replace(',', '.'));
			formatoErroneo = false;
		} catch (NumberFormatException e) {
			if (opcion != 'N') {
				throw new GadirServiceException(Integer.toString(54), e);
			}
			formatoErroneo = true;
		}
		if (formatoErroneo) {
			devolver = dato;
		} else {
			tempString = tempDouble.toString();
			String enteros, decimales = null;
			
			// Analizo si numero negativo
			boolean negativo = false;
			if (tempString.charAt(0) == '-') {
				negativo = true;
				tempString = dato.substring(1);
			} else {
				if(opcion == 'E' && (formato == 'N'))
					tempString = tempString.replace('.', ',');
				else
					tempString = dato;
			}
			
			// Busco numero de enteros y numero de decimales
			String[] numsLongitud = Double.toString(longitud).split("\\."); 
			if (numsLongitud[1] == null) {
				numsLongitud[1] = "0";
			}
			if (numsLongitud[0].length() > 2 || numsLongitud[1].length() > 1) {
				// Sólo se permite hasta 99 enteros y hasta 9 decimales.
				throw new GadirServiceException(Integer.toString(5518));
			}
			
			final String[] numsValor = tempString.split("\\,");
			
			if(((formato == 'N' || formato == 'I' || formato == 'K') && (opcion == ' ' || opcion == 'V')))
				if( ((formato == 'K' || formato == 'N') && Integer.parseInt(numsLongitud[1]) >= 0) || (formato == 'I' && opcion == ' ')) 
					decimales = formatearNumericoDecimales(numsValor, Integer.parseInt(numsLongitud[1]));
				else
					if(numsValor.length > 1 && Long.parseLong(numsValor[1]) > 0)
						decimales = numsValor[1];
					else
						decimales = "";
			else
				decimales = formatearNumericoDecimales(numsValor, Integer.parseInt(numsLongitud[1]));	

			if(((formato == 'N' || formato == 'I' || formato == 'K') && (opcion == ' ' || opcion == 'V')))
				if((formato == 'K' || formato == 'N') && Integer.parseInt(numsLongitud[1]) > 0 && UtilidadesFormato.isBigDecimalZero(datoSinComaConPunto))
					enteros = formatearNumericoEnteros(opcion, numsValor, Integer.parseInt(numsLongitud[0]));
				else
					enteros = new BigInteger(numsValor[0]).toString();
			else {
				enteros = formatearNumericoEnteros(opcion, numsValor, Integer.parseInt(numsLongitud[0]));
				if((formato == 'I' && opcion == 'V'))
					enteros = new BigInteger(enteros).toString();
			}
			
			devolver = formatearNumericoResultado(enteros, decimales, negativo);
		}

		return devolver;	//Devolver validado y formateado
	}
	
	private static String formatearNumericoDecimales(String[] nums, final int decimales) {	//Nota que el vector se puede modificar por referencia (si hay que redondear)
		final StringBuffer stringBuffer = new StringBuffer();
 		if (decimales >= 0) {
			if (nums.length < 2 || nums[1] == null) {
				for (int i=0; i<decimales; i++)
					stringBuffer.append('0');
				
			} else {
				//Redondeo
				String cad = new BigDecimal(nums[0] + "." + nums[1]).setScale(decimales, BigDecimal.ROUND_HALF_UP).toString();
				
				//Se vuelve a conseguir la parte entera y decimal (¡atención que se modifica nums pasado por parámetro!)
				String[] nuevoNums = cad.split("\\.");
				nums[0] = nuevoNums[0];				
				if(nuevoNums.length > 1) 
					nums[1] = nuevoNums[1];
				else
					nums[1] = "";
				
				//Devuelve la parte decimal
				if(nums.length > 1) 
					stringBuffer.append(nums[1]);

			}
 		} 
		return stringBuffer.toString();
	}
	
	private static String formatearNumericoEnteros(final char opcion, final String[] nums, final int enteros) {
		int inicio;
		StringBuffer stringBuffer;
		String devolver;
		switch (opcion) {
		case 'C':
		case 'V':
			stringBuffer = new StringBuffer(nums[0]);
			inicio = enteros - nums[0].length();
			for (int i=inicio; i>0; i--) {
				stringBuffer.insert(0, '0');
			}
			devolver = stringBuffer.toString();
			break;
		case 'E':
			stringBuffer = new StringBuffer();
			inicio = nums[0].length() - 1;
			for (int i=inicio; i>=0; i--) {
				stringBuffer.insert(0, nums[0].charAt(i));
				if (i!=0 && (inicio - i + 1)%3 == 0) {
					stringBuffer.insert(0, '.');
				}
			}
			devolver = stringBuffer.toString();
			break;
		default:
			devolver = nums[0];
			break;
		}
		return devolver;
	}
	
	private static String formatearNumericoResultado(final String enteros, final String decimales, final boolean negativo) {
		String devolver;
		final StringBuffer stringBuffer = new StringBuffer(enteros);
		if (!"".equals(decimales)) {
			stringBuffer.append(',').append(decimales);
		}
		devolver = stringBuffer.toString();
		if (negativo) {
			if (devolver.charAt(0) == '0') {
				devolver = '-' + devolver.substring(1);
			} else {
				devolver = new StringBuffer("-").append(devolver).toString(); 
			}
		}
		return devolver;
	}

	private static String formatearFecha(String dato, final char formato, final char opcion) throws GadirServiceException {
		if (dato.length() < 8) {
			if(dato.length()==0)
				return null;
			else
				throw new GadirServiceException(Integer.toString(1007));
		}

		if(dato.indexOf("/") != -1 && dato.length() >= 8 && dato.length() <= 9) {	//Compatibilidad con una cifra en día o mes
			String[] arrayDato = dato.split("/");
			StringBuffer strb = new StringBuffer();
			for (int i = 0; i < arrayDato.length; i++) {
				if(arrayDato[i].length() == 1)
					strb.append("0");
				strb.append(arrayDato[i] + (i == (arrayDato.length - 1) ? "" : "/"));
			}
			dato = strb.toString();
		}
		
		String devolver = dato.replaceAll("/", "");
		final String mitad1 = devolver.substring(0,4);
		final String mitad2 = devolver.substring(4,8);
		String ano, mes, dia;
		if (Integer.parseInt(mitad2) > 1231) {
			if (opcion != 'N' && formato == 'F') {
				throw new GadirServiceException(Integer.toString(1007));
			}
			dia = mitad1.substring(0,2);
			mes = mitad1.substring(2,4);
			ano = mitad2;
		} else {
			if (opcion != 'N' && formato == 'G') {
				throw new GadirServiceException(Integer.toString(1007));
			}
			ano = mitad1;
			mes = mitad2.substring(0,2);
			dia = mitad2.substring(2,4);
		}
		if (opcion != 'N' && !isFecha(ano, mes, dia)) {
			throw new GadirServiceException(Integer.toString(1007));
		}
		switch (opcion) {
		case 'E':
		case ' ':
		case 'V':
			devolver = new StringBuffer(dia).append('/').append(mes).append('/').append(ano).toString();
			break;
		case 'A':
			devolver = new StringBuffer(ano).append(mes).append(dia).toString();
			break;
		default:
			devolver = new StringBuffer(dia).append(mes).append(ano).toString();
			break;
		}
		return devolver;
	}
	
	/**
	 * Comprueba si el conjunto año, mes, día compone una fecha correcta&#46;<br>En PL/Java: comun_util.Formato_isFecha
	 * @param año
	 * @param mes
	 * @param dia
	 * @return True / false
	 */
	public static boolean isFecha(final String ano, final String mes, final String dia) {
		boolean devolver = !(Integer.parseInt(ano) > 2099 || Integer.parseInt(ano) < 1900);
		if (devolver) { 
			try {
				final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", new Locale("es", "ES"));
				dateFormat.setLenient(false);
				dateFormat.parse(
						ano + 
						(mes.length() == 1 ? "0" + mes : mes) + 
						(dia.length() == 1 ? "0" + dia : dia));
			} catch (Exception e) {
				devolver = false;
			}
		}
		return devolver;
	}
	
	/**
	 * En PL/Java: comun_util.Formato_validar.
	 * Comprueba si formatearDato() da excepciones.
	 * @param dato
	 * @param formato
	 * @param longitud
	 * @return True o false
	 */
	public static boolean validar(final String dato, final String formato, double longitud) {
		longitud = roundDouble(longitud, 1);

		boolean devolver = true;
		try {
			formatearDato(dato, formato, longitud, " ");
		} catch (GadirServiceException e) {
			devolver = false;
		}
		return devolver;
	}
	
	/**
	 * 
	 * @param dato
	 * @param formato
	 * @param longitud
	 * @return True o false
	 */
	public static boolean validar(final String dato, final String formato, final int longitud) {
		return validar(dato, formato, (double) longitud);
	}

	
	/**
	 * En PL/Java: comun_util.Formato_subcadena.
	 * @param dato
	 * @param formato
	 * @param inicio
	 * @param fin
	 * @return Una parte del dato enviado.
	 * @throws GadirServiceException 
	 */
	public static String subcadena(final String dato, final String formato, final int inicio, final int fin) throws GadirServiceException {
		String devolver = dato.trim().substring(Math.max(inicio - 1, 0), Math.min(fin, dato.length()));
		final char formatoChar = formato.charAt(0);
		if (formatoChar == 'N') {
			devolver = subcadenaNumerico(devolver);
		}
		return devolver;
	}
	
	private static String subcadenaNumerico(final String dato) throws GadirServiceException {
		String devolver = dato;
		if (",".equals(devolver)) {
			devolver = "0";
		} else if(devolver.charAt(0) == ',') {
			devolver = new StringBuffer("0").append(devolver).toString();
		} else if (devolver.charAt(devolver.length() - 1) == ',') {
			devolver = devolver.substring(0, devolver.length() - 1);
		}
		return devolver;
	}
	

	public static double roundDouble(double d, int places) {
	    return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double)places);
	}
	
	
	private static final HashMap pesos = new HashMap(){ 
        { 
            put(new Integer(1), new Integer(6)); 	put(new Integer(2), new Integer(3));	put(new Integer(3), new Integer(7));	put(new Integer(4), new Integer(9));	put(new Integer(5), new Integer(10));	
            put(new Integer(6), new Integer(5));	put(new Integer(7), new Integer(8));	put(new Integer(8), new Integer(4));	put(new Integer(9), new Integer(2));	put(new Integer(10), new Integer(1));
        } 
    }; 

    
	public static int calculoDigitoControl(final String numero){
		int total = 0;
		
		int posicion = numero.length();
		
		for(int i=0; i<numero.length(); i++){
			total = total + ((Integer)pesos.get(new Integer(posicion))).intValue() * Integer.parseInt(numero.substring(i, i+1));
			posicion--;
		}
		
		total = total % 11; 
		total = 11 - total;
		
		if(total == 10)
			total = 1;
		else if(total == 11)
			total = 0;
		
		return total;
	}

	
}
