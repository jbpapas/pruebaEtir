package es.dipucadiz.etir.comun.utilidades;

/**
 * 
 */
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
public final class Formato {
	Formato() {}
	/**
	 * En PL/Java: comun_util.Formato_formatearDato.

	 * El parámetro tipo puede ser:
	 * 'N' - NUMÉRICO SIN EDITAR DISTINTO DE CERO
	 * 'I' - NUMÉRICO EDITADO
	 * 'K' - NUMÉRICO SIN EDITAR
	 * 'E' - NUMÉRICO SIN EDITAR CON CEROS SIGNIFICATIVOS 
	 * 'A' - ALFANUMÉRICO 
	 * 'D' - FECHA 

	 * El parámetro opción puede ser:
	 * ' ' - FORMATO DE GRABACIÓN (PARA CUALQUIER TIPO)
	 * 'V' - FORMATO DE VISUALIZACIÓN (PARA CUALQUIER TIPO)
	 * 'A' - FORMATO PARA CONVERTIR EL TIPO FECHA (VÁLIDA) EN AAAMMDD
	 * 'X' - FORMATO DE EXTRACCIÓN 
	 * 'C' - CARGA DESDE FICHERO DE TEXTO
	 
	NOTA: Opciones Obsoletas:
	 * 'E' - SALIDA CON MASCARA DE EDICION.
	 * 'N' - SIN CONTROL DE ERRORES.
	 * 'C' - PARA LOS NUMERICOS PARA LA EM=9999999999.9999 (NO SE TRUNCA NI SUS ENTEROS NI SUS DECIMALES).

	 *
	 * @param dato
	 * @param formato
	 * @param longitud
	 * @param opcion
	 * @return El dato formateado.
	 * @throws GadirServiceException
	 */
	public static String formatearDato(String datoParam, String formato, double longitud, String opcion) throws GadirServiceException {
		/******************** PRE ******************/
		longitud = roundDouble(longitud, 1);
		String opcion_inicial = opcion;
		//String formato_inicial = formato;
		boolean tratamientoZeroUno = false;
		
		
		if(opcion == null || opcion.length() == 0)
			opcion = " ";
		else
			opcion = opcion.toUpperCase();
		
		if(!(formato == null || formato.length() == 0))
			formato = formato.toUpperCase();
		
		
		if( !(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A") || opcion.equals("C") || opcion.equals("X")) ||
			!(formato.equals("A") || formato.equals("D") || formato.equals("N") || formato.equals("I") || formato.equals("K") || formato.equals("E"))
		  ) throw new GadirServiceException(Integer.toString(70143));
		
		
		if(UtilidadesFormato.getParteEntera(longitud) == 0 && !formato.equals("D")){
			throw new GadirServiceException(Integer.toString(20052));
		}
		
		/**************** VALIDACIONES NUEVAS *************/
		
		//Valiaciones previas antes de formatear el dato
		validacionesPreviasSegunFormatoYTipo(datoParam, formato, longitud, opcion);
		
		//Casos especiales de null, '' y 0
		String res = comprobarCasosEspecialesDato(datoParam, formato, opcion);
		
		if(res == null || !res.equals("")){
			return res;
		}
		
		/*************************************************/
		
		// Equivalencias
		
		if(!formato.equals("D") && !formato.equals("A")){
			if(opcion.equals("C") && formato.equals("I") && UtilidadesFormato.isZero(datoParam)) {
				formato = "N";
				tratamientoZeroUno = true;
			}
			if(opcion.equals("A"))
				opcion = " ";
			else if(opcion.equals("C")) {
				int PE = UtilidadesFormato.getParteEntera(longitud);
				int PD = UtilidadesFormato.getParteDecimal(longitud);
				datoParam = UtilidadesFormato.padLeft(datoParam, PE + PD, '0');
				if(PD > 0) {
					datoParam = UtilidadesFormato.appendIn(datoParam, PE, ',');
					if(datoParam.endsWith(","))
						datoParam += "0";
				}
				opcion = " ";
			}
			else if(opcion.equals("X")) {
				if(formato.equals("I") || formato.equals("K") || formato.equals("E"))
					formato = "N";
				
				if(formato.equals("N")) {
					if((datoParam == null || datoParam.length() == 0))
						datoParam = "0";
					formato = "E";
					opcion = "V";
				}
				else
					opcion = "V";
			}
		}
		/********************************************/
		
		/**************** TRATAMIENTO ***************/
	
		if(longitud == 0 && !formato.equals("D"))
			datoParam = null;

		String dato = datoParam;
		dato = datoParam == null ? "" : datoParam;
		
		String devolver;
		final char formatoChar = formato.charAt(0);
		
		switch (formatoChar) {
		case 'A':
			devolver = formatearAlfa(dato.trim(), longitud, opcion.charAt(0));
			break;
		case 'N':
		case 'I':	 
		case 'K':	 
		case 'E':	 			
			if(tratamientoZeroUno)
				dato = dato.replaceAll("0,", "9,");
			
			devolver = formatearNumerico(formatoChar, dato.trim(), longitud, opcion.charAt(0));
			
			if(tratamientoZeroUno)
				devolver = devolver.replaceAll("9,", "0,");
			break;
		case 'D':
			//old//case 'F':
			//old//case 'G':
			devolver = formatearFecha(dato.trim(), formatoChar, opcion.charAt(0));
			break;
//		case 'T':
//			devolver = formatearHora(dato.trim());
//			break;
		default:
			devolver = dato;
			break;
		}
		/********************************************/
		
		
		/******************** POST ******************/
		if(devolver != null) {
			// Equivalencias
			if(opcion_inicial.equals("X")) {
				if(formato.equals("A"))
					return devolver;
				
				devolver = UtilidadesFormato.replace(devolver, ",", "");	// No existe replace(String,String) en Java 1.4.x
			}
			return devolver.trim();
		}
		else
			return devolver;
		/********************************************/
	}
	
	
	private static void validacionesPreviasSegunFormatoYTipo(String datoParam, String formato, double longitud, String opcion) throws GadirServiceException{
		
		//Valiaciones previas antes de formatear el dato
		
		if(!UtilidadesFormato.isEmpty(datoParam)){
		
			String datoAux = datoParam.trim();
			
			if(formato.equals("N") || formato.equals("I") || formato.equals("K") || formato.equals("E")){
								
				//int LE = UtilidadesFormato.getParteEntera(longitud);
				//int LD = UtilidadesFormato.getParteDecimal(longitud);
				
				String[] numsLongitud = Double.toString(longitud).split("\\.");

				int LE = Integer.parseInt(numsLongitud[0]);
				int LD = 0;
				
				if (!UtilidadesFormato.isEmpty(numsLongitud[1]))
					LD = new BigDecimal(numsLongitud[1]).intValue();
							
				if(opcion.equals("C")){
					
					if(!UtilidadesFormato.isNumeric(datoAux)) throw new GadirServiceException(Integer.toString(28));
					
					if(LE + LD < datoAux.length()) 
						throw new GadirServiceException(Integer.toString(20052));
					
				}
				else{ //opciones V, A, X, ' '
					
					if(formato.equals("I")){
						//Quitamos los puntos
						datoAux = datoAux.replaceAll("\\"+UtilidadesFormato.separadorNumerico, "");
					}
					else{
						if(datoAux.indexOf(".")  != -1)
							throw new GadirServiceException(Integer.toString(28));
					}

					//Comprobamos que sea un dato numérico
					
					
					if(LD == 0){
					
						if(!UtilidadesFormato.isNumeric(datoAux)) throw new GadirServiceException(Integer.toString(28));
						
						if(datoAux.length() > (int) longitud)
							throw new GadirServiceException(Integer.toString(20052));
					
					}
					else{
						
						datoAux = datoAux.replaceAll(",",""+UtilidadesFormato.separadorNumerico);
						
						if(!UtilidadesFormato.isNumericDecimal(datoAux)) throw new GadirServiceException(Integer.toString(28));
						
						int DE = datoAux.length();
						
						if(datoAux.indexOf(".") != -1){
							DE = datoAux.substring(0, datoAux.indexOf(".")).length();
						}
						
						if(DE > LE){
							throw new GadirServiceException(Integer.toString(20052));
						}
					}
				
				}
				
			}else if(formato.equals("A")){
				
				String[] numsLongitud = Double.toString(longitud).split("\\.");
			
				if (UtilidadesFormato.isEmpty(numsLongitud[1])) {
					numsLongitud[1] = "0";
				}
				
				//Comprobación de que la longitud es correcta
				if(!numsLongitud[1].equals("0")){
					throw new GadirServiceException(Integer.toString(20052));
				}
				
				if(opcion.equals("A") || opcion.equals(" ") || opcion.equals("C")){
						
					if(datoAux.length() > (int) longitud)
						throw new GadirServiceException(Integer.toString(20052));
				}
				//V y X se dan por buenos
			}
			
		}
		
	}
	
	private static String comprobarCasosEspecialesDato(String datoParam, String formato, String opcion) throws GadirServiceException{
		
		//Casos especiales de null, '' y 0
		
		String resultado = "";
		
		if(UtilidadesFormato.isEmpty(datoParam)){
			
			if(formato.equals("I")){
				if(opcion.equals("C"))
					resultado = null;
				else if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A"))
					resultado = null;
			}
			else if(formato.equals("N")){
				if(opcion.equals("C"))
					resultado = null;
				else if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A"))
					resultado = null;
			}
			else if(formato.equals("K")){
				if(opcion.equals("C"))
					resultado = null;
				else if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A"))
					resultado = null;
			}
			else if(formato.equals("E")){
				if(opcion.equals("C"))
					resultado = null;
				else if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A"))
					resultado = null;
			}
			else if(formato.equals("A")){
				if(!opcion.equals("X"))
					resultado = null;
			}
			else if(formato.equals("D")){
				if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("C")){
					resultado = null;
				}
				else{ //opciones A y X
					return "        "; //8 espacios en blanco
				}
			}
			
		}
		else if(UtilidadesFormato.isZero(datoParam)){
			
			if(formato.equals("I")){
				//Sigue el flujo
			}
			else if(formato.equals("N")){
				if(opcion.equals("C"))
					resultado = null;
				else if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A"))
					resultado = null;
			}
			else if(formato.equals("K")){
				if(opcion.equals(" ") || opcion.equals("V") || opcion.equals("A"))
					resultado = null;
			}
			else if(formato.equals("E")){
				if(opcion.equals("C"))
					resultado = null;
			}
		}
		
		return resultado;
	}
	
	
//	private static String formatearHora(String entrada) throws GadirServiceException {
//		String horas;
//		String minutos;
//		String segundos;
//		if (entrada.indexOf(':') > -1) {
//			String[] trozos = entrada.split(":");
//			if (trozos.length >= 1) horas = trozos[0];
//			else horas = "00";
//			if (trozos.length >= 2) minutos = trozos[1];
//			else minutos = "00";
//			if (trozos.length >= 3) segundos = trozos[2];
//			else segundos = "00";
//		} else {
//			if (entrada.length() >= 2) horas = entrada.substring(0, 2);
//			else horas = "00";
//			if (entrada.length() >= 4) minutos = entrada.substring(2, 4);
//			else minutos = "00";
//			if (entrada.length() >= 6) segundos = entrada.substring(4, 6);
//			else segundos = "00";
//		}
//		horas = formatearNumerico('E', horas, 2, ' ');
//		minutos = formatearNumerico('E', minutos, 2, ' ');
//		segundos = formatearNumerico('E', segundos, 2, ' ');
//		String salida = horas + ":" + minutos + ":" + segundos;
//		return salida;
//	}
	
	private static String formatearAlfa(final String dato, final double longitud, final char opcion) throws GadirServiceException {
		int entero;
		String devolver = dato;
		final int wInt = (int) longitud;
		entero = dato.length();
		
		if(opcion == 'X'){
			devolver = UtilidadesFormato.padRight(devolver, wInt, ' ');
		}
		
		if (wInt < entero) 
			devolver = devolver.substring(0, wInt);
				
		return devolver;
	}
	
//	private static boolean perdidaEnFormateoNumerico(String dato, double longitud) {
//		boolean bRes = false;
//		String[] mat = dato.split(",");
//		if(mat.length > 1) {
//			if(mat[1].replaceAll("0", "").length() > 0 && longitud - (long)longitud == 0) {
//				bRes = true;
//			}
//		}
//
//		return bRes;
//	}
//	
	private static String formatearNumerico(char formato, String dato, double longitud, char opcion) throws GadirServiceException {
		longitud = roundDouble(longitud, 1);
		
		if(formato == 'I' || formato == 'E')
			dato = dato.replaceAll("\\"+UtilidadesFormato.separadorNumerico, "");	
		else
			dato = dato.replace(UtilidadesFormato.separadorNumerico, ',');		//Compatibilidad con datos decimales con punto en lugar de coma
		
		if(dato.startsWith(","))
			dato = "0" + dato;
		if(dato.equals("0,"))
			dato = "0";
				
//		if(perdidaEnFormateoNumerico(dato, longitud))
//			throw new GadirServiceException(Integer.toString(20052));
		
		String datoSinComaConPunto = dato.replace(',', UtilidadesFormato.separadorNumerico);
		
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
		if((opcion == ' ' || opcion== 'A' || opcion == 'V') && formato == 'K' && UtilidadesFormato.isEmpty(dato))
			return null;
		else if( (opcion == 'V' && 
				 (formato == 'E' || formato == 'N' || formato == 'K') 
				) || 
				(opcion == ' ' && formato == 'N') || 
				(opcion == ' ' && formato == 'I') 	
		  	   ) 
		{
			if (UtilidadesFormato.isEmpty(dato) ||  dato.length() == 0 || 
				(formato != 'E' && 
				  ( 
					( !UtilidadesFormato.contains(datoSinComaConPunto, UtilidadesFormato.separadorNumerico+"") && UtilidadesFormato.isNumeric(datoSinComaConPunto) && UtilidadesFormato.isBigIntegerZero(datoSinComaConPunto) ) ||
					( UtilidadesFormato.contains(datoSinComaConPunto, UtilidadesFormato.separadorNumerico+"") && UtilidadesFormato.isNumericDecimal(datoSinComaConPunto) && UtilidadesFormato.isBigDecimalZero(datoSinComaConPunto) )
				  )
			     )
			 	) 
			{
				if(!(opcion == ' ' && formato == 'I' && UtilidadesFormato.isZero(dato)))
					return null;
			}
		}
		
		String tempString, devolver;
		BigDecimal tempDouble = null;
		boolean formatoErroneo;
		// Comprobación de si realmente es numerico.
		try {
			tempDouble = new BigDecimal(dato.replace(',', UtilidadesFormato.separadorNumerico));
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
					tempString = tempString.replace(UtilidadesFormato.separadorNumerico, ',');
				else
					tempString = dato;
			}
			
			// Busco numero de enteros y numero de decimales
			String[] numsLongitud = Double.toString(longitud).split("\\" + UtilidadesFormato.separadorNumerico); 
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
				String cad = new BigDecimal(nums[0] + UtilidadesFormato.separadorNumerico + nums[1]).setScale(decimales, BigDecimal.ROUND_HALF_UP).toString();
				
				//Se vuelve a conseguir la parte entera y decimal (¡atención que se modifica nums pasado por parámetro!)
				String[] nuevoNums = cad.split("\\" + UtilidadesFormato.separadorNumerico);
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
					stringBuffer.insert(0, UtilidadesFormato.separadorNumerico);
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
		
//		if(dato.indexOf("/") != -1 && dato.length() >= 8 && dato.length() <= 9) {	//Compatibilidad con una cifra en día o mes
//			String[] arrayDato = dato.split("/");
//			StringBuffer strb = new StringBuffer();
//			for (int i = 0; i < arrayDato.length; i++) {
//				if(arrayDato[i].length() == 1)
//					strb.append("0");
//				strb.append(arrayDato[i] + (i == (arrayDato.length - 1) ? "" : "/"));
//			}
//			dato = strb.toString();
//		}
		
		
		//Comprobamos que la longitud es correcta quitando / o - separadores de día, mes y año
		
		if(dato.indexOf("/") != -1){
			
			if(dato.length() == 10) //Comprobamos que en caso de que tenga /, tenga exactamente 2
				dato = dato.replaceAll("/", "");
			else
				throw new GadirServiceException(Integer.toString(1007));
		}
		else if(dato.indexOf("-") != -1){
			
			if(dato.length() == 10) //Comprobamos que en caso de que tenga -, tenga exactamente 2
				dato = dato.replaceAll("-", "");
			else
				throw new GadirServiceException(Integer.toString(1007));
		}
		//Una vez quitados / y - (si es que los tenía), el dato tiene que tener 8 caracteres y ser numérico
		if(dato.length() != 8 || !UtilidadesFormato.isNumeric(dato))
			throw new GadirServiceException(Integer.toString(1007));
		
		final String mitad1 = dato.substring(0,4);
		final String mitad2 = dato.substring(4,8);
		String ano, mes, dia;
		
		if (Integer.parseInt(mitad2) > 1231) {
			dia = mitad1.substring(0,2);
			mes = mitad1.substring(2,4);
			ano = mitad2;
		} else {
			ano = mitad1;
			mes = mitad2.substring(0,2);
			dia = mitad2.substring(2,4);
		}
		
		if(!isFecha(ano, mes, dia)){
			throw new GadirServiceException(Integer.toString(1007));
		}

		String devolver ="";
		
		switch (opcion) {
			case ' ':
			case 'V':
			case 'C':	
				devolver = new StringBuffer(dia).append('/').append(mes).append('/').append(ano).toString();
				break;
			case 'A':
			case 'X':
				devolver = new StringBuffer(ano).append(mes).append(dia).toString();
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
 
