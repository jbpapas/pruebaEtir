/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;
import com.opensymphony.xwork2.DefaultTextProvider;

import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


/**
 * @author AJDIONISIO
 * Clase con métodos de uso común
 */
final public class Utilidades {
	private enum Meses {ENERO, FEBRERO, MARZO, ABRIL, MAYO, JUNIO, JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICIEMBRE};
	private static ResourceBundle resourceBundle;
	
	private Utilidades() {}
	
	/**
	 * Devuelve true si la cadena es nula o está vacía
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(final String string){
		return (string == null || "".equals(string.trim()) || string.equals("null"));
	}

	public static boolean isEmpty(final char chr){
		return (chr == ' ');
	}
	
	public static boolean isEmpty(final Integer string){
		return (string == null);
	}

	public static boolean isEmpty(final Short string){
		return (string == null);
	}

	public static boolean isEmpty(final Long string){
		return (string == null);
	}

	public static boolean isEmpty(final Double string){
		return (string == null);
	}

	public static boolean isEmpty(final Float string){
		return (string == null);
	}
	
	public static boolean isEmpty(final Date date){
		return (date == null);
	}
	
	
	/**
	 * Métodos que se encargan de comprobar si los tipos numéricos BigInteger y BigDecimal son cero
	 * 
	 * @param obj
	 *            Objeto a comprobar.
	 * @return True si el objeto es null y false si no lo es.
	 */	
	public static boolean isBigIntegerZero(final String bi){
		return 	(new BigInteger(bi)).equals(BigInteger.ZERO);
	}
	
	public static boolean isBigDecimalZero(final String bd){
		return (new BigDecimal(bd)).compareTo(new BigDecimal(0)) == 0;
	}

	
	/**
	 * Método que se encarga de comprobar si el objeto dado es null.
	 * 
	 * @param obj
	 *            Objeto a comprobar.
	 * @return True si el objeto es null y false si no lo es.
	 */
	public static boolean isNull(final Object object) {
		return object==null;
	}

	public static String sustituir(final String texto, final String buscar, final String sustitucion) {
		return sustituir(texto, buscar, sustitucion, -1);
	}
	public static String sustituir(final String texto, final String buscar, final String sustitucion, int limite) {
		final StringBuffer result = new StringBuffer();
		if ("".equals(buscar)) {
			result.append(texto);
		} else {
			int startIdx = 0;
			int idxOld = texto.indexOf(buscar, startIdx);
			while (idxOld >= 0 && limite != 0) {
				result.append(texto.substring(startIdx, idxOld));
				result.append(sustitucion);
				startIdx = idxOld + buscar.length();
				idxOld = texto.indexOf(buscar, startIdx);
				limite--;
			}
			result.append( texto.substring(startIdx) );
		}
		return result.toString();
	}
	

	/**
	 * Método que se encarga de comprobar si una cadena no es null ni vacio.
	 * 
	 * @param str
	 *            Cadena a comprobar.
	 * @return true si la cadena es distinta de null y cadena vacia.
	 */
	public static boolean isNotEmpty(final String str) {
		boolean result;
		if (str == null || "".equals(str.trim())) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * Comprobar si una fecha tiene contenido o no
	 * @param date
	 * @return date != null
	 */
	public static boolean isNotEmpty(Date date) {
		return (date != null);
	}
	
	/**
	 * Método que se encarga de si un objeto no es null.
	 * 
	 * @param obj
	 *            Objeto a comprobar.
	 * @return True si el objeto no es null y false si lo es.
	 */
	public static boolean isNotNull(final Object obj) {
		return obj != null;
	}

	/**
	 * Método que se encarga de comprobar si una String esta compuesta por
	 * numeros positivos sin decimales. Para ello hace uso de commons-lang.
	 * 
	 * @param str
	 *            Cadena a validar.
	 * @return True si esta compuesta por numeros y false si no lo esta.
	 */
	public static boolean isNumeric(final String str) {
		if (isNull(str) || str.equals("")) {
			return false;
		}
		return StringUtils.isNumeric(str);
	}

	public static boolean isNumericEvenNegative(final String str) {
		if (isNull(str) || str.equals("")) {
			return false;
		}
		if(str.substring(0,1).equals("-") && str.length() > 1)
			return StringUtils.isNumeric(str.substring(1));
		else
			return StringUtils.isNumeric(str);
	}
	
	/**
	 * Método que se encarga de comprobar si una String esta compuesta por
	 * numeros con y sin decimales. Para ello hace uso de commons-lang.
	 * 
	 * @param str
	 *            Cadena a validar.
	 * @return True si esta compuesta por numeros y false si no lo esta.
	 */
	public static boolean isNumericDecimal(final String str) {
		if (isNull(str) || str.equals("")) {
			return false;
		}
		return NumberUtils.isNumber(str);
	}
	
	/**
	 * Método que se encarga de comprobar si el numero dado es un valor entero.
	 * 
	 * @param numero
	 *            Nuemero a validar.
	 * @return true si es entero y false si no lo es.
	 */
	public static boolean isEntero(final Number numero) {
		boolean entero = true;

		final int parteEntera = numero.intValue();

		if (numero instanceof Double) {
			if ((Double) numero / parteEntera > 1) {
				entero = false;
			}
		} else if (numero instanceof Float) {
			if ((Float) numero / parteEntera > 1) {
				entero = false;
			}
		}
		return entero;
	}
	
	/**
	* Metodo que convertir un String en BigDecimal
	* @param s, el String
	* @param defaultValue, valor por defecto
	* @return BigDecimal, el String convertido en BigDecimal
	*/
	public static BigDecimal normalizeBigDecimal(String s) {
		try {
			s = s.replace(',', '.');
			BigDecimal result = new BigDecimal(new Double(s));
			if (result.intValue()<= 999){
				return result;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean isNumeroPar(final String str) {
		boolean resultado=false;
		try{
			int valor=Integer.parseInt(str);
			if (valor%2==0)resultado=true;
		}catch(Exception e){
			resultado=false;
		}
		return resultado;
	}
	
	public static boolean isNumeroImpar(final String str) {
		boolean resultado=false;
		try{
			int valor=Integer.parseInt(str);
			if (valor%2!=0)resultado=true;
		}catch(Exception e){
			resultado=false;
		}
		return resultado;
	}

	public static Short addInt2Int(int a, int b) {
		return (Short) ((short) (((short) a + (short) b)));
	}
	
	public static Short addShort2Short(Short a, Short b) {
		return (Short) ((short) ((a.shortValue() + b.shortValue())));
	}
	
	public static Short addInt2Short(Short a, int b) {
		return (Short) ((short) ((a.shortValue() + (short) b)));
	}
	
	/**
	 * Método que se encarga de calcular la longitud del dato que le pasamos. La
	 * longitud se calculara en funcion de que se define a su vez la longitud
	 * como si fuera un campo de BD. <br/>
	 * <code>
	 * Por ejemplo: Se define que una casilla tendra una longitud de 5.2.
	 * <ul>
	 * 	<li>De 5 caracteres 2 son decimales.</li>
	 * </ul>
	 * </code>
	 * 
	 * @param numero
	 *            Numero a comprobar.
	 * @return Longitud.
	 */
	public static int getLongitudCalculada(final Number numero) {
		int longitudAux = 0;

		if (Utilidades.isEntero(numero)) {
			longitudAux = numero.intValue();
		} else {
			longitudAux = numero.intValue() + 1;
		}
		return longitudAux;
	}
	
	/**
	 * Método que se encarga de comprobar si un valor cumple una expresion
	 * regular.
	 * 
	 * @param valor
	 * @param regex
	 * @return
	 */
	public static boolean isValidForExpression(final String valor,
	        final String regex) {
		final Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(valor).matches();
	}
	
	public static Date getDateActual(){
		return new Date();
	}
	
	public static Date getFechaActual() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static int getAnoActual(){
		return (new GregorianCalendar()).get(Calendar.YEAR);
	}
	public static int getMesActual(){
		return (new GregorianCalendar()).get(Calendar.MONTH) +1;
	}

	public static boolean isYear(final String ano) {
		return (ano != null && isNumeric(ano) && Short.valueOf(ano) >= 1970 && Short.valueOf(ano) <= 2100);
	}

	public static String dateToDDMMYYYY(final Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}

	public static String dateToDDMMYYYYSinBarras(final Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}
	
	public static String dateToYYYYDDDJulian() {
		String result = "";
		int diaJuliana = ((new GregorianCalendar()).get(GregorianCalendar.DAY_OF_YEAR));
		if (isNotNull(diaJuliana) && diaJuliana < 10)
			result = "" + getAnoActual() + "00" + diaJuliana;
		else if(isNotNull(diaJuliana) && diaJuliana < 100)
			result = "" + getAnoActual() + "0" + diaJuliana;
		else
			result = "" + getAnoActual() + diaJuliana;		
		return result;
	}

	public static int dateToWeekday(final Date date) {
		int result = -1;
		if (isNotNull(date)) {
			Date fechaActual = date;
			Calendar fechaActualUltimaHora = Calendar.getInstance();
			fechaActualUltimaHora.setTime(fechaActual);
		    result = Math.max(fechaActualUltimaHora.get(Calendar.DAY_OF_WEEK) - 1, 1);	//Lunes = 1, Martes = 2, ... Domingo = 7
		}
		return result;
	}
	
	public static String dateToYYYYMMDD(final Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}
	
	public static String dateToYYYYMMDDHHMMSS(final Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_kkmmss", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}

	public static String dateToYYYYMMDDHHMMSSformateado(final Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}
	
	public static String dateToHHMM(final Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}
	
	public static String dateToHHMMSSN(final Date date) {
		String result = "";
		
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.S", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		result = result.replaceAll("[:|.]", "");
		result = result.substring(0, 7);
		return result;
	}
	
	public static Date YYYYMMDDToDate(final String string) throws ParseException {
		Date result = null;
		if (isNotNull(string)) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result = dateFormat.parse(string);
		}
		return result;
	}

	public static Date YYYYMMDDHHMMSSToDate(final String string) throws ParseException {
		Date result = null;
		if (isNotNull(string)) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSSSSS");
			result = dateFormat.parse(string);
		}
		return result;
	}
	
	public static Date DDMMYYYYToDate(final String string) throws ParseException {
		Date result = null;
		if (isNotNull(string)) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			result = dateFormat.parse(string);
		}
		return result;
	}
	
	public static String YYYYMMDDtoDDMMYYYY(final String string) {
		String aux = "";
		if(!isEmpty(string))
			aux=string.substring(8,10)+"/"+string.substring(5,7)+"/"+string.substring(0,4);
		return aux;
	}
	
	public static String YYYYMMDDtoDDMMYYYYLimitado(final String string) {
		String aux = "";
		if(!isEmpty(string) && string.length()==10)
			aux=string.substring(8,10)+"/"+string.substring(5,7)+"/"+string.substring(0,4);
		return aux;
	}
	
	public static String DDMMYYYYtoYYYYMMDD(final String string) {
		String aux;
		aux=string.substring(6,10)+"-"+string.substring(3,5)+"-"+string.substring(0,2);
		return aux;
	}
	
	public static String DDMMYYYYtoHHMMSS(final String string) {
		String aux;
		aux=string.substring(11,19);
		return aux;
	}
	
	public static Date strutsFormatToDate(final String string) {
		
		//2009-11-24T00:00:00+01:00
		//2010-03-30T00:00:00+02:00
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss+01:00");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		if(isNotNull(string)){
		
	    	try {
				date = dateFormat.parse(string);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}
	
	public static Date strutsFormatToDateHora(final String string) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss+01:00");
		Date date = null;
		
		if(isNotNull(string)){
			try {
				date = dateFormat.parse(string);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}
	
	@SuppressWarnings("deprecation")
	public static Date strutsFormatToDateHasta(final String string) {
		
		//2009-11-24T00:00:00+01:00
		//2010-03-30T00:00:00+02:00
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss+01:00");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		if(isNotNull(string)){
		
	    	try {
				date = dateFormat.parse(string);
			} catch (ParseException e) {
				date = null;
			}
		}
		
		if(date != null)
		{
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
		}
		
		return date;
	}
	
	public static String dateToStrutsFormat(final Date date) throws ParseException {
		
		//2009-11-24T00:00:00+01:00
		//2010-03-30T00:00:00+02:00
		
		String result = "";
		if (isNotNull(date)) {
			//final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss+01:00");
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}
	
	public static String strutsFormatToDDMMYYYY(final String string) throws ParseException {
		
		//2009-11-24T00:00:00+01:00
		//2010-03-30T00:00:00+02:00 2014-03-01T00:00:00+01:00
		
		String aux = "";
		
		if(isNotNull(string))
			aux=string.substring(8,10)+"/"+string.substring(5,7)+"/"+string.substring(0,4);
		return aux;           
	}

	public static String strutsFormatToDDMMYYYYHHMMSS(final String string) throws ParseException {
		String aux = "";
		if(isNotNull(string)) {
			aux=string.substring(8,10)+"/"+string.substring(5,7)+"/"+string.substring(0,4)+" "+string.substring(11, 19);
		}
		return aux;           
	}

	public static String dateToDDMMYYYYHHMMSS(Date fecha) {
		String valorInicial = String.valueOf(fecha);
		String valor = valorInicial.substring(0, 10);
		valor=Utilidades.YYYYMMDDtoDDMMYYYY(valor);
		valor=valor + " " + valorInicial.substring(11, 19);
		return valor;
	}
	
	public static String dateToDDMMYYYYHHMM(Date date) {
		String result = "";
		if (isNotNull(date)) {
			final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
			dateFormat.setLenient(false);
			result = dateFormat.format(date);
		}
		return result;
	}
	
	public static String MMToMes(final int m) {
		Meses[] meses = Meses.values();
		
		return meses[m - 1].toString();
	}
	
	public static int MesToMM(final String mes) {
		return Meses.valueOf(mes.toUpperCase()).ordinal()+1; 
	}




	/**
	 * Método que se encarga de comprobar si una String es un numero
	 * 
	 * @param str
	 *            Cadena a validar.
	 * @return True si esta compuesta por numeros y false si no lo esta.
	 */
	public static boolean esNumerico(final String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	

	
	/**
	 * Método que se encarga de comprobar si una String es una fecha
	 * 
	 * @param str
	 *            Cadena a validar.
	 * @return True si esta compuesta por numeros y false si no lo esta.
	 */
	public static boolean isFecha(String fecha) {

		SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");	
		try {
			formato2.setLenient(false);
			formato2.parse(fecha);
			
			return true;
		} catch (ParseException e) {
			return false;
		
		}
	}

	public static Object pinta(Object obj) {

		if (obj == null) {
			return "";
		} else {
			return obj;
		}

	}

	@SuppressWarnings("unchecked")
	public static boolean validaIdentificador(String identificador) throws Exception {
		String sql = "Select FU_GA_VALNIF_JAVA('"+identificador+"') from dual";
		
		DomicilioBO domicilioBO = (DomicilioBO) GadirConfig.getBean("domicilioBO");
		
		List<Object> lista = (List<Object>)domicilioBO.ejecutaQuerySelect(sql);  	

		BigDecimal res = (BigDecimal)lista.get(0);
	
		if(res.intValue() == 1)
			return true;
		else
			return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean validaCif(String identificador) throws Exception {
		String sql = "Select VALIDA_CIF('"+identificador+"') from dual";
		
		DomicilioBO domicilioBO = (DomicilioBO) GadirConfig.getBean("domicilioBO");
		
		List<Object> lista = (List<Object>)domicilioBO.ejecutaQuerySelect(sql);  	

		BigDecimal res = (BigDecimal)lista.get(0);
	
		if(res.intValue() == 1)
			return true;
		else
			return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public static String formateaIdentificador(String identificador) throws Exception {
		String sql = "Select fu_normalizar_nif('"+identificador+"') from dual";
		DomicilioBO domicilioBO = (DomicilioBO) GadirConfig.getBean("domicilioBO");
		List<Object> lista = (List<Object>)domicilioBO.ejecutaQuerySelect(sql);  
		String res = (String)lista.get(0);
		return res;
	}
	
	/**
	 * Método que se encarga de calcular la letra final que correspondería a un
	 * cif dado sin letra final.
	 * 
	 * @param cif
	 *            Sin letra final
	 * @return String Letra que correspondería.
	 */
	public static String calculaLetraCIF(String cif) {

		int pares = 0;
		int impares = 0;
		int dControl = 0;
		String aux;
		String suma;
		String letra[] = new String[] { "J", "A", "B", "C", "D", "E", "F", "G",
		        "H", "I" };

		cif = cif.toUpperCase();

		if (cif.matches("^[ABCDEFGHJPQRSUVNW]\\d\\d\\d\\d\\d\\d\\d[0-9,A-J]$")) {
			for (int i = 1; i < 7; i = i + 2) {
				aux = String.valueOf(2 * Integer.parseInt(cif.substring(i,
				        i + 1)));
				impares += aux.length() == 1 ? Integer.parseInt(aux) : Integer
				        .parseInt(aux.substring(0, 1))
				        + Integer.parseInt(aux.substring(1, 2));
				pares += Integer.parseInt(cif.substring(i + 1, i + 2));
			}

			aux = String.valueOf(2 * Integer.parseInt(cif.substring(7, 8)));
			impares += aux.length() == 1 ? Integer.parseInt(aux) : Integer
			        .parseInt(aux.substring(0, 1))
			        + Integer.parseInt(aux.substring(1, 2));

			suma = String.valueOf(pares + impares);

			dControl = 10 - Integer.parseInt(suma.substring(suma.length() - 1,
			        suma.length()));

			if (dControl == 10) {
				dControl = 0;
			}
			aux = cif.substring(cif.length() - 1, cif.length());

		}

		return letra[dControl];
	}
	
	
	  /**
	   * Devuelve un NIF completo a partir de un DNI. Es decir, añade la letra del NIF
	   * @param dni dni al que se quiere añadir la letra del NIF
	   * @return NIF completo.
	   */
	  public static char nifFromDni(int dni) {
		  String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKET";
	    return NIF_STRING_ASOCIATION.charAt(dni % 23);
	  }


	  
	  /**
	   * Método que comprueba si el valor no es nulo y si no es un valor por defecto
	   * Util para comprobar si en una lista desplegable se ha seleccionado un valor
	   * @param valor
	   * @param porDefecto
	   * @return
	   */
	  public static boolean esValorSeleccionado(String valor, String porDefecto)
	  {
		  return !isEmpty(valor) && !porDefecto.equals(valor);
	  }
	  
	  /**
	   * Método que comprueba si una referencia catastral es válida
	   * @param String Referencia catastral
	   * @return
	   */
	  public static boolean isValidRefCatastral(String valor)
	  {
			final Pattern pattern = Pattern
	        .compile("[A-Za-z0-9]{18}[A-Za-z]{2}");
			return pattern.matcher(valor).matches();		  
	  }
	  
	  /**
	   * Método que comprueba si una referencia de domiciliación es válida
	   * @param String Referencia de domiciliación
	   * @return
	   */
	  public static boolean isValidRefDomiciliacion(String valor)
	  {
			final Pattern pattern = Pattern
	        .compile("[0-9]{12}");
			return pattern.matcher(valor).matches();		  
	  }	  

	  /**
	   * Método que comprueba si un año es bisiesto
	   * @param int Año a comprobar
	   * @return
	   */
	  public static boolean isBisiesto(int nYYYY)
	  {
			return ((nYYYY % 4 == 0 && nYYYY % 100 != 0) || nYYYY % 400 == 0);
	  }
	  
	  /**
	   * Método que comprueba si un año es bisiesto
	   * @param String Año a comprobar
	   * @return
	   */
	  public static boolean isBisiesto(String YYYY)
	  {
			return isBisiesto(Integer.parseInt(YYYY));
	  }
	  
	  public static String codificarParametros(List<String> listaParametros) {
		  listaParametros.add("EOL");
		  StringBuffer stringBuffer = new StringBuffer();
		  for (String parametro : listaParametros) {
			  if (Utilidades.isNotEmpty(parametro)) {
				  stringBuffer.append(parametro);
			  }
			  stringBuffer.append("!¬¬!");
		  }
		  int checksum = 0;
		  for (int i=0; i<stringBuffer.length(); i++) {
			  checksum += stringBuffer.charAt(i);
		  }
		  checksum %= 256;
		  String checksumString = Integer.toHexString(checksum);
		  while (checksumString.length()<2) checksumString = "0" + checksumString;
		  String result = checksumString + stringBuffer;
		  return Base64.encodeBase64URLSafeString(result.getBytes());
	  }
	  public static List<String> descodificarParametros(String parametros) throws GadirServiceException {
		  String descodificado = new String(Base64.decodeBase64(parametros));
		  String checksumStringEntrada = descodificado.substring(0, 2);
		  int checksum = 0;
		  for (int i=2; i<descodificado.length(); i++) {
			  checksum += descodificado.charAt(i);
		  }
		  checksum %= 256;
		  String checksumString = Integer.toHexString(checksum);
		  while (checksumString.length()<2) checksumString = "0" + checksumString;
		  if (!checksumStringEntrada.equals(checksumString)) {
			  throw new GadirServiceException("Error en el checksum de parámetros.");
		  }
		  String[] arrayDescodificado = descodificado.split("!¬¬!");
		  List<String> listaParametros = Arrays.asList(arrayDescodificado);
		  listaParametros.set(0, listaParametros.get(0).substring(2));
		  return listaParametros;
	  }  
	  
	public static String normalizarCoDocumento(String codDocumento) {
		codDocumento = codDocumento.trim();
		while (codDocumento.length() < 9) codDocumento = "0" + codDocumento;
		return codDocumento;
	}

	public static String normalizarNumExpediente(String numExpediente) {
		numExpediente = numExpediente.trim();
		while(numExpediente.length() < 13) numExpediente = "0" + numExpediente;
		return numExpediente;
	}
	
//	public static String normalizarDocExpediente(String docExpediente) {
//		docExpediente = docExpediente.trim();
//		while(docExpediente.length() < 8) docExpediente = "0" + docExpediente;
//		return docExpediente;
//	}
	
	//////////////////FORMATOS////////////////////
	public static String ultimoFormatoError = "";
	
	public static boolean isFormatoEmail(final String email) {
		final Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		boolean ret = pattern.matcher(email).matches();
		ultimoFormatoError = ret?"":"Campo Email no válido";
		return ret;
	}

	public static boolean isFormatoTelefono(final String telefono) {
		boolean ret = Formato.validar(telefono, "N", 10);
		ultimoFormatoError = ret?"":"Campo Teléfono no válido";
		return ret;
	}

	public static boolean isFormatoFax(final String fax) {
		boolean ret = isFormatoTelefono(fax);
		ultimoFormatoError = ret?"":"Campo Fax debe ser numérico";
		return ret;
	}
	
	public static boolean isFormatoTelex(final String telex) {
		final Pattern pattern = Pattern.compile("^[A-Za-z0-9]");
		boolean ret = pattern.matcher(telex).matches();
		ultimoFormatoError = ret?"":"Campo Télex no válido";
		return ret;
	}
	
	public static boolean isFormatoCodigoPostal(final String cp) {
		boolean ret = Formato.validar(cp, "N", 5);
		ultimoFormatoError = ret?"":"Campo Código Postal no válido";
		return ret;
	}
	
	public static boolean isFormatoSwift(final String swift) {
		boolean ret = Formato.validar(swift, "A", 11);
		ultimoFormatoError = ret?"":"Campo Swift no válido";
		return ret;
	}

	public static boolean isFormatoAgenciaUrbana(final String agenciaUrbana) {
		boolean ret = Formato.validar(agenciaUrbana, "N", 3);
		ultimoFormatoError = ret?"":"Campo Agencia Urbana no válido";
		return ret;
	}
	
	public static String formateaNombreProvinciaMunicipio(String sProv, String sMuni, String sDesc) {
		return sProv + sMuni + " - " + sDesc;
	}
	
	public static boolean isFormatoCodigoBanco(final String codigo) {
		boolean ret =  codigo.length() == 4 && Formato.validar(codigo, "N", 4);
		ultimoFormatoError = ret?"":"Campo Código de Banco debe ser numérico de 4 dígitos";
		return ret;
	}

	public static boolean isFormatoCodigoSucursal(final String codigo) {
		boolean ret =  codigo.length() == 4 && Formato.validar(codigo, "N", 4);
		ultimoFormatoError = ret?"":"Campo Código de Sucursal debe ser numérico de 4 dígitos";
		return ret;
	}
	
	public static boolean isFormatoCodigoCuentaBancaria(final String cuenta) {
		boolean ret = cuenta.length() == 10 && Formato.validar(cuenta, "N", 10);
		
		if(cuenta.equals("0000000000"))
			ret = false;
			
		ultimoFormatoError = ret?"":"Campo Cuenta Bancaria no válido (número de 10 dígitos)";
		return ret;
	}
	
	public static boolean isFormatoDigitoControlDosDigitos(final String digitosControl){
		boolean ret =  digitosControl.length() == 2 && Formato.validar(digitosControl, "N", 2);
		
		ultimoFormatoError = ret?"":"Campo dígito de control debe ser númerico de 2 dígitos";
		
		return ret;
	}
	
	public static boolean isFormatoDigitoControlSucursal(final String dc, String entidad, String oficina) {
		
		//Formatear entidad
    	entidad = entidad.trim();
		while (entidad.length() < 4) entidad = "0" + entidad;
    	//Formatear oficina
		oficina = oficina.trim();
		while (oficina.length() < 4) oficina = "0" + oficina;
		
		boolean ret = Formato.validar(dc, "N", 1);
		
		if(ret){
		
			ret = false;
			
			 if(isFormatoCodigoBanco(entidad)){ 
				 if(isFormatoCodigoSucursal(oficina)){
			
					String entidadOficina = entidad + oficina;
					
					int digitoControl = Formato.calculoDigitoControl(entidadOficina);
					
					if(digitoControl == Integer.parseInt(dc)){
						ret = true;
					}
					else
						ultimoFormatoError = "Campo Dígito de Control de Sucursal no válido";
				}
			 }			 
		}
		else
			ultimoFormatoError = "Campo Dígito de Control de Sucursal debe ser numérico";
		
		return ret;
	}

	public static boolean isFormatoDigitoControlCuenta(final String dc, String entidad, String oficina, String cuenta) {
		
		boolean ret = isFormatoDigitoControlSucursal(dc.substring(0,1), entidad, oficina);
		
		if(ret){
			
			//Formatear cuenta
			cuenta = cuenta.trim();
			while (cuenta.length() < 10) cuenta = "0" + cuenta;
		
			ret = (dc.length() == 2) && Formato.validar(dc.substring(1,2), "N", 1);
			
			 if(ret){ 
				
				ret = false;
				 
				if(isFormatoCodigoCuentaBancaria(cuenta)){ 
				 
					int digitoControl = Formato.calculoDigitoControl(cuenta);
					
					if(digitoControl == Integer.parseInt(dc.substring(1, 2))){
						ret = true;
					}
					else
						ultimoFormatoError = "Campo Dígito de Control de Cuenta no válido";
				}
			 }		
			 else
				ultimoFormatoError = "Campo Dígito de Control de Cuenta debe tener dos dígitos numéricos";
		}
	
		return ret;
	}

	
    
	public static boolean isFormatoFecha(final String fecha) {
		boolean ret = isFecha(fecha);
		ultimoFormatoError = ret?"":"Fecha no válida";
		return ret;
	}

	public static boolean isFormatoFechaTagGADIRFecha(final String fecha) {
		return (strutsFormatToDate(fecha) != null);
	}

	public static boolean isFormatoEjercicio(final Short ejercicio) {
		return isFormatoEjercicio(ejercicio.toString());
	}
	
	public static boolean isFormatoEjercicio(final String ejercicio) {
		boolean ret = Formato.validar(ejercicio, "N", 4);
		if(ret) {
			Short s = Short.parseShort(ejercicio);
			ret = ((s >= 1980) && (s <= 2100));
		}
		ultimoFormatoError = ret?"":"Campo Ejercicio no válido";
		return ret;
	}

	public static boolean isFormatoOtr(final String otr) {
		boolean ret = false;
		
		if (!Utilidades.isEmpty(otr) && otr.equals("***")){
			ret=true;
		}else{
			ret = Formato.validar(otr, "N", 3);
		}
		
		ultimoFormatoError = ret?"":"Campo OTR no válido";
		return ret;
	}
	
	public static String getNumber2Formato(String str) {
		String tmp = str.replaceAll("[.]","").replaceAll(",", ".");
		String res = (new BigDecimal(tmp)).toString();
		return res.replace('.', ',');
	}
	public static boolean validarFormatoNumber(String str, double tamano) {
		tamano = Formato.roundDouble(tamano, 1);
		boolean devolver = true;
		try {
			Formato.formatearDato(str.replace('.',','), "N", tamano, " ");
		} catch (GadirServiceException e) {
			devolver = false;
		}
		return devolver;
	}
	public static String getFormato2Number(BigDecimal bd, double tamano) throws GadirServiceException {
		return Formato.formatearDato((bd.toString()).replace('.',','), "I", tamano, "V");
	}
	public static String getFormato2Number(String str, double tamano) throws GadirServiceException {
		return Formato.formatearDato(str.replace('.',','), "I", tamano, "V");
	}
	public static String getFormato2Save(String str) {
		return str.replaceAll(",", ".");
	}
	
	public static String eliminaNulos(final String cadena) {
		return cadena.trim().replaceAll("null", "");
	}
	
	// Posibles formatos son: A, D, N, I, E, K. (Tabla GT: TAB0106)
	public static boolean isFormatosCompatibles(String formatoEntrada, BigDecimal longitudEntrada, String formatoSalida, BigDecimal longitudSalida) {
		char fEntrada = formatoEntrada.charAt(0);
		char fSalida  = formatoSalida.charAt(0);
		int lEntrada  = longitudEntrada.intValue();
		int lSalida   = longitudSalida.intValue();
		
		//Separamos la longitud de entrada y salida en parte entera y decimal
		String le = ""+longitudEntrada.floatValue();
		String ls = ""+longitudSalida.floatValue();
		
		int enteraE = Integer.parseInt(le.substring(0,le.indexOf('.')));
		int enteraS = Integer.parseInt(ls.substring(0,ls.indexOf('.')));
		
		int decimalE = Integer.parseInt(le.substring(le.indexOf('.')+1,le.indexOf('.')+2));
		int decimalS = Integer.parseInt(ls.substring(ls.indexOf('.')+1,ls.indexOf('.')+2));
		
		
		switch (fSalida) {
		case 'A':
			if(lEntrada <= lSalida)
				return true;
			break;
		case 'D':
			if(fEntrada =='D')
				return true;
			break;
		case 'N':
		case 'I':
		case 'E':
		case 'K':
			if (fEntrada == 'N' || fEntrada == 'I' || fEntrada == 'E' || fEntrada == 'K')
			{
				if(enteraE <= enteraS && decimalE <= decimalS)
					return true;
				else
					return false;
			}
			break;
		}
		return false;
	}
	
	public static String bigDecimalToString(BigDecimal bigDecimal){
		return bigDecimal.toString().replace('.', ',');
	}
	public static BigDecimal importeGadirToBigdecimal(String importe){
		return new BigDecimal(importe.replaceAll("\\.", "").replace(',', '.'));
	}
	public static String bigDecimalToImporteGadir(BigDecimal imAplicado) {
		String result = "";
		try {
			result = Formato.formatearDato(imAplicado.toString().replace('.',','), "I", 15.2, "V");
		} catch (GadirServiceException e) {}
		return result;
	}
	
	public static boolean isImporteGadir(String importe){
		boolean res=true;
		try{
			String sinPuntos=importe.replaceAll("\\.", "").replaceAll("_", "");
			res=Formato.validar(sinPuntos, "I", 15.2);
		}catch(Exception e){
			res=false;
		}
		//res=validarFormatoNumber(importe, 15.2);
		return res;
	}
	
	public static BigDecimal max(BigDecimal a, BigDecimal b) {
		boolean ret = a.compareTo(b) > 0; 
		return  ret ? a : b;
	}

	public static BigDecimal BG100 = new BigDecimal("100");
	public static BigDecimal addPercent(BigDecimal num, BigDecimal porcCrecimiento) {
		return num.add((num.multiply(porcCrecimiento)).divide(BG100));
	}

	public static BigDecimal subtractPercent(BigDecimal num, BigDecimal porcCrecimiento) {
		return num.subtract((num.multiply(porcCrecimiento)).divide(BG100));
	}
	
	public static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);     
	}

	public static boolean isWindows() {
		//return GadirConfig.leerParametro("entorno.tipo.sistema").startsWith("w");
		return System.getProperty("os.name" ).startsWith("W"); 
	}
	
	public static boolean isLinux() {
		//return GadirConfig.leerParametro("entorno.tipo.sistema").startsWith("l");
		return !System.getProperty("os.name" ).startsWith("W");		
	}

	public static String getFileSeparator() {
		if(isWindows())
			return "\\";
		else
			return "/";	
					
	}

	public static String getPropertyValue(final String provider, final String property) {
		if(!isEmpty(provider)) {
			DefaultTextProvider defaultTextProvider = new DefaultTextProvider();
			resourceBundle = defaultTextProvider.getTexts(provider);	// provider puede ser "struts", "comun/i18n/pestanas" y otros
			
			Enumeration<String> keysEnum = resourceBundle.getKeys(); 
			String string = "";
			while (keysEnum.hasMoreElements()) {
				String key = keysEnum.nextElement();
				if (property.equals(key)) {
					string = resourceBundle.getString(property);
					break;
				}
			}
			return string;
		} else 
			return System.getProperty(property);
	}

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static void setResourceBundle(ResourceBundle resourceBundle) {
		Utilidades.resourceBundle = resourceBundle;
	}

	public static String getFormatoTamanoFichero(final String stamano, int decimales) {
		String sret = "0 B";
		if(isEmpty(stamano))
			return sret;
		
		double t = 0, unidad = 0, izq = 0;
		String[] sunidad = {"B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
		for(int i = 8; i >= 0; i--) {
			t = Double.parseDouble(stamano);
			unidad = (long) Math.pow(1024, i);
			if(t >= unidad || (i == 0 && t == 0)) {
				izq = (long) (t / unidad);
				t = izq + (t - izq * unidad) / unidad;
				try {
					if(i==0 && decimales>0)	// Los bytes no pueden tener decimales
						decimales = 0;
					
					String tFormato = String.valueOf(t).replace(".", ",");
					if(decimales != 0)
						sret = Formato.formatearDato(tFormato, "I", 99 + (double)(decimales) / 10, "V") + " " + sunidad[i];
					else{
						sret = Formato.formatearDato(tFormato.substring(0,tFormato.indexOf(",")), "I", 99, "V") + " " + sunidad[i];
					}
				} catch (GadirServiceException e) {
					sret = "0 B";
				}
				break;
			}
		}
		
		return sret;
	}
	
	public static boolean esExtensionFicheroSubible(String fichero) {
		fichero = fichero.toUpperCase();

		String[] listadoExcepcionesExtensiones = {"EXE", "COM", "JS", "VB", "SCR", "PIF", "BAT", "CMD", "CPL", "CSH", "SH", "INF", "JSE", "MSI", "VBE", "MSP", "REG", "VBS", "WSF", "WSC", "WSH", "XSL"};
		for(int i=0; i<listadoExcepcionesExtensiones.length; i++)
			if(fichero.indexOf("."+listadoExcepcionesExtensiones[i]) != -1)
				return false;

		String[] listadoExcepcionesInicioNombre = {".", "THUMBS.DB"};	// En Linux, los ficheros que comienzan por punto están ocultos 
		for(int i=0; i<listadoExcepcionesInicioNombre.length; i++)
			if(fichero.startsWith(listadoExcepcionesInicioNombre[i]))
				return false;
		
		return true;
	}

	public static boolean esExtensionImagen(String fichero) {
		fichero = fichero.toUpperCase();
		String[] listadoInclusiones = {"BMP", "GIF", "PNG", "BMP", "JPG", "JPEG", "PCX", "DIB", "TIF", "TIFF", "JPE", "JFIF", "ICO"};
		for(int i=0; i<listadoInclusiones.length; i++)
			if(fichero.indexOf(listadoInclusiones[i]) != -1) 
				return true;
		
		return false;
	}

	public static boolean esExtension(String fichero, String ext) {
		return fichero.toUpperCase().endsWith(ext.toUpperCase());
	}

	
	// Buscador de cadena cad2 en cad1. Busca múltiples palabras separadas por espacio (sin importar el orden ni mayúsculas/minúsculas) Cambia los caracteres "*" y "%" por " ".
	public static boolean StringLikeString(String cad1, String cad2) {
		cad2 = cad2.replace('*', ' ').replace('%', ' ');
		String[] mat = cad2.split(" ");
		for(int i = 0; i < mat.length; i++)
			if(cad1.toUpperCase().indexOf(mat[i].toUpperCase()) == -1)
				return false;
		return true;
	}
	
	public static String appendIfNotYet(final String cad, final String app) {
		if(cad != null) {
			String tmp = cad;
			if(!tmp.endsWith(app))
				tmp += app;
			return tmp;
		} else
			return app;
	}

	public static String filtraParametrosErroneoEmpty(String dato) {
		if(dato != null)
			return  dato.indexOf('@') != -1 ? "" : dato;
		else
			return null;
	}
	
	public static String codificarRowidFormatoSeguro(String rowid){
		
		if(!isEmpty(rowid)){
			rowid = rowid.replaceAll("\\+", "-");
			rowid = rowid.replaceAll("/", "|");
		}
		
		return rowid;
	}
	
	public static String decodificarRowidFormatoSeguro(String rowid){
	
		if(!isEmpty(rowid)){
			rowid = rowid.replaceAll("-", "+");
			rowid = rowid.replaceAll("\\|", "/");
		}
		
		return rowid;
	}

	public static String formateaDesdeHost(String cad) {
		String ret = cad;
		final String aux = "[[¡!]]";
		
		ret = ret.replace("Parámetro", "<span class=negr>Parámetro</span>");
		
		ret = ret.replace("ERROR", "<span class=error>ERROR</span>");
		ret = ret.replace("Exception", "<span class=error>Exception</span>");
		ret = ret.replace("Permiso denegado", "<span class=error>Permiso denegado</span>");

		ret = ret.replace("detenerScriptCMD:1", "<span class=error2>detenerScriptCMD:1</span>");

		ret = ret.replace("PostBatch.java", "<span class=info>PostBatch.java</span>");
		ret = ret.replace("Batch.java", "<span class=info>Batch.java</span>");
		ret = ret.replace("Post<span class=info>Batch.java</span>", "PostBatch.java");

		ret = ret.replace("source /home/gadir/script/lib/libGadir.sh;", "<span class=subr>source /home/gadir/script/lib/libGadir.sh;</span>");

		ret = ret.replace("lgRunPLProcNCatch", "<span class=negrsub>lgR" + aux + "unPLProcNCatch</span>");
		ret = ret.replace("lgRunPLFuncNCatch", "<span class=negrsub>lgR" + aux + "unPLFuncNCatch</span>");
		ret = ret.replace("lgRunPLFuncRetNCatch", "<spanclass=negrsub>lgR" + aux + "unPLFuncRetNCatch</span>");
		ret = ret.replace("lgRunPLProcDArrayNCatch", "<spanclass=negrsub>lgR" + aux + "unPLProcDArrayNCatch</span>");
		ret = ret.replace("lgRunPLFuncRet", "<span class=negrsub>lgR" + aux + "unPLFuncRet</span>");
		ret = ret.replace("lgRunPLProc", "<span class=negrsub>lgR" + aux + "unPLProc</span>");
		ret = ret.replace("lgRunPLFunc", "<span class=negrsub>lgR" + aux + "unPLFunc</span>");
		ret = ret.replace("lgRun ", "<span class=negrsub>lgR" + aux + "un</span>");
		ret = ret.replace("Ejecutando PL", "<span class=negrsub>Ejecutando PL</span>");
		
		ret = ret.replace("arrayTxt", "<span class=negr>arrayTxt</span>");
		ret = ret.replace("arrayTipo", "<span class=negr>arrayTipo</span>");
		ret = ret.replace("gArrayParams", "<span class=negr>gArrayParams</span>");
		ret = ret.replace("lgDescTable", "<span class=negr>lgDescTable</span>");
		ret = ret.replace("lgSendMail", "<span class=negr>lgSendMail</span>");
		ret = ret.replace("lgPurgeRecycleBin", "<span class=negr>lgPurgeRecycleBin</span>");
		ret = ret.replace("lgPurgeLogFiles", "<span class=negr>lgPurgeLogFiles</span>");
		ret = ret.replace("lgExitOK", "<span class=negr>lgExitOK</span>");
		ret = ret.replace("lgExitErrorA", "<span class=negr>lgExitErrorA</span>");
		ret = ret.replace("lgExitErrorB", "<span class=negr>lgExitErrorB</span>");
		ret = ret.replace("lgTestFile", "<span class=negr>lgTestFile</span>");
		ret = ret.replace("lgBeginArray", "<span class=negr>lgBeginArray</span>");
		ret = ret.replace("lgSetArrayValue", "<span class=negr>lgSetArrayValue</span>");
		ret = ret.replace("lgPurgeArray", "<span class=negr>lgPurgeArray</span>");
		ret = ret.replace("/opt/java16/bin/java", "<span class=negr>/opt/java16/bin/java</span>");
		ret = ret.replace("export", "<span class=negr>export</span>");

		ret = ret.replace("comun_util.Batch_setEstado", "<span class=negr>comun_util.Batch_setEstado</span>");
		ret = ret.replace("Merge finalizado", "<span class=subr>Merge finalizado</span>");

		ret = ret.replace("Terminado ", "<span class=info>Terminado </span>");
				
		ret = ret.replace(aux, "");
		ret = ret.replace("\n", "<BR/>");
		
		return ret;
	}

	public static String padRight(String s, int n) {  
		if(s == null)
			s = "";
		return String.format("%1$-" + n + "s", s);   
	}  
	
	public static String padLeft(String s, int n) {
		if(n > 0) {
			if(s == null)
				s = "";
			return String.format("%1$#" + n + "s", s);
		} else
			return "";
	}  

	public static String padRight(String s, int n, char chr) {      
		if(n > 0) {		
			return padRight(s, n).replace(' ', chr);
		} else
			return "";
	}  
	
	public static String padLeft(String s, int n, char chr) {     
		return padLeft(s, n).replace(' ', chr);   
	}  

	public static final char separadorNumerico = (Double.parseDouble("0") + "").substring(1, 2).charAt(0);
	
	public static int getParteEntera(double num) {
		return Integer.parseInt((num + "").split("\\"+separadorNumerico)[0]);
	}

	public static int getParteDecimal(double num) {
		String[] vector = (num + "").split("\\"+separadorNumerico);
		if(vector.length > 1)
			return Integer.parseInt(vector[1]);
		else
			return 0;
	}

	public static String appendIn(String str, int offset, char separator) {
		String res = str.substring(0, offset) + separator;
		if(str.length() > offset)
			res += str.substring(offset);
		return res;
	}
	
	public static String Last(String[] var) {
		return var[var.length - 1];
	}

	public static <T> T nvl(T posibleNull, T siEsNull) {
		return (posibleNull == null)?siEsNull:posibleNull;
	}
	
	public static byte[] blobToBytes(Blob fromBlob) throws GadirServiceException {
		if (fromBlob == null) return null;
		try {
			return fromBlob.getBytes(1, (int) fromBlob.length());
		} catch (SQLException e) {
			throw new GadirServiceException(e.getMessage(), e);
		}
	}

	public static String toString(Integer integer) {
		String resultado;
		if (integer == null) {
			resultado = "";
		} else {
			resultado = integer.toString();
		}
		return resultado;
	}

	public static String toString(BigDecimal bigDecimal) {
		String resultado;
		if (bigDecimal == null) {
			resultado = "";
		} else {
			resultado = bigDecimal.toString();
		}
		return resultado;
	}

	
	public static String normalizarCoModelo(String codModelo) {
		codModelo = codModelo.trim();
		return codModelo.toUpperCase();
	}
	
	public static String normalizarCoVersion(String codVersion) {
		codVersion = codVersion.trim();
		return codVersion.toUpperCase();
	}
	
	
	
	
    @SuppressWarnings("unchecked")
	private static final HashMap codigosPaises = new HashMap(){ 
        { 
            put('A', new Integer(10)); 	put('D', new Integer(13));	put('G', new Integer(16));	put('J', new Integer(19));	put('M', new Integer(22));	put('P', new Integer(25));	put('S', new Integer(28));	put('V', new Integer(31));	put('Y', new Integer(34));
            put('B', new Integer(11));	put('E', new Integer(14));	put('H', new Integer(17));	put('K', new Integer(20));	put('N', new Integer(23));	put('Q', new Integer(26));	put('T', new Integer(29));	put('W', new Integer(32)); 	put('Z', new Integer(35));
            put('C', new Integer(12));	put('F', new Integer(15));	put('I', new Integer(18));	put('L', new Integer(21));	put('O', new Integer(24));	put('R', new Integer(27));	put('U', new Integer(30));	put('X', new Integer(33));
        } 
    }; 
	
	
	

	public static int calculoDigitosIBAN(final String numero){
		char pos1 = numero.charAt(0);
		char pos2 = numero.charAt(1);
						
		int val1 = ((Integer) codigosPaises.get(pos1));
		int val2 = ((Integer) codigosPaises.get(pos2));
		
		String cuentaSinLetras = "";
		
		if(!Utilidades.isNumeric(numero.substring(4))) { //la cuenta contiene letras y tenemos que transformarlas con su valor correspondiente
			for(int i = 4; i < numero.length(); i++) {
				char vari = numero.charAt(i); 
				
				if((""+vari).matches("[A-Z]"))
					cuentaSinLetras += ((Integer) codigosPaises.get(vari));
				else
					cuentaSinLetras += vari;
			}
		}
		else 
			cuentaSinLetras = numero.substring(4);
		
		String bban = cuentaSinLetras + val1 + val2 + "00";
		BigInteger resto = new BigInteger(bban).mod(new BigInteger("97"));	
		String dc = StringUtils.leftPad(Integer.toString(98 - resto.intValue()), 2, "0");		
		int total = Integer.parseInt(dc);	
		return total;
	}
	
	public static boolean isFormatoIP(String ip){
		
		String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		
		return matcher.matches();	    	    
		    
	}
	
	/**
	 * Funcion que se encarga de llamar al proceso PL que se encarga de traducir un codigo de barras en formato
	 * correos a formato normal
	 * @param codigo es un codigo de barras en formato normal o en formato correos
	 * @return cadena es un codigo de barras en formato normal
	 */
	public static String devuelveCodigoBarras(String codigo){
		String cadena = "";
		DomicilioBO domicilioBO = (DomicilioBO) GadirConfig.getBean("domicilioBO");
		try {
			Object o = domicilioBO.ejecutaQuerySelect("SELECT PQ_CODIGO_BARRAS_AUX.DEVUELVE_CODIGO_BARRAS('"+codigo.toUpperCase()+"') FROM DUAL");
			cadena = (String)((List<?>)o).get(0);
		} catch (GadirServiceException e) {
			e.printStackTrace();
		}
		return cadena;
	}

	/*
	http://www.ibm.com/developerworks/java/library/j-coordconvert/
	public double[] convertUTMToLatLong(String UTM)
	{
	  double[] latlon = { 0.0, 0.0 };
	  String[] utm = UTM.split(" ");
	  zone = Integer.parseInt(utm[0]);
	  String latZone = utm[1];
	  easting = Double.parseDouble(utm[2]);
	  northing = Double.parseDouble(utm[3]);
	  String hemisphere = getHemisphere(latZone);
	  double latitude = 0.0;
	  double longitude = 0.0;

	  if (hemisphere.equals("S"))
	  {
	    northing = 10000000 - northing;
	  }
	  setVariables();
	  latitude = 180 * (phi1 - fact1 * (fact2 + fact3 + fact4)) / Math.PI;

	  if (zone > 0)
	  {
	    zoneCM = 6 * zone - 183.0;
	  }
	  else
	  {
	    zoneCM = 3.0;
	  }

	  longitude = zoneCM - _a3;
	  if (hemisphere.equals("S"))
	  {
	    latitude = -latitude;
	  }

	  latlon[0] = latitude;
	  latlon[1] = longitude;
	  return latlon;

	}
	protected void setVariables()
	{
	  arc = northing / k0;
	  mu = arc
	      / (a * (1 - POW(e, 2) / 4.0 - 3 * POW(e, 4) / 64.0 - 5 * POW(e, 6) / 256.0));

	  ei = (1 - POW((1 - e * e), (1 / 2.0)))
	      / (1 + POW((1 - e * e), (1 / 2.0)));

	  ca = 3 * ei / 2 - 27 * POW(ei, 3) / 32.0;

	  cb = 21 * POW(ei, 2) / 16 - 55 * POW(ei, 4) / 32;
	  cc = 151 * POW(ei, 3) / 96;
	  cd = 1097 * POW(ei, 4) / 512;
	  phi1 = mu + ca * SIN(2 * mu) + cb * SIN(4 * mu) + cc * SIN(6 * mu) + cd
	      * SIN(8 * mu);

	  n0 = a / POW((1 - POW((e * SIN(phi1)), 2)), (1 / 2.0));

	  r0 = a * (1 - e * e) / POW((1 - POW((e * SIN(phi1)), 2)), (3 / 2.0));
	  fact1 = n0 * TAN(phi1) / r0;

	  _a1 = 500000 - easting;
	  dd0 = _a1 / (n0 * k0);
	  fact2 = dd0 * dd0 / 2;

	  t0 = POW(TAN(phi1), 2);
	  Q0 = e1sq * POW(COS(phi1), 2);
	  fact3 = (5 + 3 * t0 + 10 * Q0 - 4 * Q0 * Q0 - 9 * e1sq) * POW(dd0, 4) / 24;

	  fact4 = (61 + 90 * t0 + 298 * Q0 + 45 * t0 * t0 - 252 * e1sq - 3 * Q0
	          * Q0)
	          * POW(dd0, 6) / 720;

	  lof1 = _a1 / (n0 * k0);
	  lof2 = (1 + 2 * t0 + Q0) * POW(dd0, 3) / 6.0;
	  lof3 = (5 - 2 * Q0 + 28 * t0 - 3 * POW(Q0, 2) + 8 * e1sq + 24 * POW(t0, 2))
	          * POW(dd0, 5) / 120;
	  _a2 = (lof1 - lof2 + lof3) / COS(phi1);
	  _a3 = _a2 * 180 / Math.PI;

	}*/
	

	 

	public static String  Deg2UTM(double Lat,double Lon)
	    {
		    double Easting;
		    double Northing;
		    int Zone;
		    char Letter;
		    
	        Zone= (int) Math.floor(Lon/6+31);
	         Zone=30;
	        if (Lat<-72) 
	            Letter='C';
	        else if (Lat<-64) 
	            Letter='D';
	        else if (Lat<-56)
	            Letter='E';
	        else if (Lat<-48)
	            Letter='F';
	        else if (Lat<-40)
	            Letter='G';
	        else if (Lat<-32)
	            Letter='H';
	        else if (Lat<-24)
	            Letter='J';
	        else if (Lat<-16)
	            Letter='K';
	        else if (Lat<-8) 
	            Letter='L';
	        else if (Lat<0)
	            Letter='M';
	        else if (Lat<8)  
	            Letter='N';
	        else if (Lat<16) 
	            Letter='P';
	        else if (Lat<24) 
	            Letter='Q';
	        else if (Lat<32) 
	            Letter='R';
	        else if (Lat<40) 
	            Letter='S';
	        else if (Lat<48) 
	            Letter='T';
	        else if (Lat<56) 
	            Letter='U';
	        else if (Lat<64) 
	            Letter='V';
	        else if (Lat<72) 
	            Letter='W';
	        else
	            Letter='X';
	        Letter='N';
	        Easting=0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180))/(1-Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180)))*0.9996*6399593.62/Math.pow((1+Math.pow(0.0820944379, 2)*Math.pow(Math.cos(Lat*Math.PI/180), 2)), 0.5)*(1+ Math.pow(0.0820944379,2)/2*Math.pow((0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180))/(1-Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180)))),2)*Math.pow(Math.cos(Lat*Math.PI/180),2)/3)+500000;
	        Easting=Math.round(Easting*100)*0.01;
	        Northing = (Math.atan(Math.tan(Lat*Math.PI/180)/Math.cos((Lon*Math.PI/180-(6*Zone -183)*Math.PI/180)))-Lat*Math.PI/180)*0.9996*6399593.625/Math.sqrt(1+0.006739496742*Math.pow(Math.cos(Lat*Math.PI/180),2))*(1+0.006739496742/2*Math.pow(0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin((Lon*Math.PI/180-(6*Zone -183)*Math.PI/180)))/(1-Math.cos(Lat*Math.PI/180)*Math.sin((Lon*Math.PI/180-(6*Zone -183)*Math.PI/180)))),2)*Math.pow(Math.cos(Lat*Math.PI/180),2))+0.9996*6399593.625*(Lat*Math.PI/180-0.005054622556*(Lat*Math.PI/180+Math.sin(2*Lat*Math.PI/180)/2)+4.258201531e-05*(3*(Lat*Math.PI/180+Math.sin(2*Lat*Math.PI/180)/2)+Math.sin(2*Lat*Math.PI/180)*Math.pow(Math.cos(Lat*Math.PI/180),2))/4-1.674057895e-07*(5*(3*(Lat*Math.PI/180+Math.sin(2*Lat*Math.PI/180)/2)+Math.sin(2*Lat*Math.PI/180)*Math.pow(Math.cos(Lat*Math.PI/180),2))/4+Math.sin(2*Lat*Math.PI/180)*Math.pow(Math.cos(Lat*Math.PI/180),2)*Math.pow(Math.cos(Lat*Math.PI/180),2))/3);
	        if (Letter<'M')
	            Northing = Northing + 10000000;
	        Northing=Math.round(Northing*100)*0.01;
	        
	        return String.valueOf(Easting)  +" "+  String.valueOf(Northing);
	    }
	  

	 
	
	public static String UTM2Deg(String UTM)
	    {
	        double latitude;
		    double longitude;
		    String coordenadas;
	        String[] parts=UTM.split(";");
	        int Zone=Integer.parseInt(parts[0]);
	        char Letter=parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
	        double Easting=Double.parseDouble(parts[2]);
	        double Northing=Double.parseDouble(parts[3]);           
	        double Hem;
	        if (Letter>'M')
	            Hem='N';
	        else
	            Hem='S';            
	        double north;
	        if (Hem == 'S')
	            north = Northing - 10000000;
	        else
	            north = Northing;
	        latitude = (north/6366197.724/0.9996+(1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)-0.006739496742*Math.sin(north/6366197.724/0.9996)*Math.cos(north/6366197.724/0.9996)*(Math.atan(Math.cos(Math.atan(( Math.exp((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*( 1 -  0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996 )/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996 - 0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996 )*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996)*3/2)*(Math.atan(Math.cos(Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996))*180/Math.PI;
	        latitude=Math.round(latitude*10000000);
	        latitude=latitude/10000000;
	        longitude =Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*( north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2* north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3)) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))*180/Math.PI+Zone*6-183;
	        longitude=Math.round(longitude*10000000);
	        longitude=longitude/10000000;    
	        coordenadas =  String.valueOf(latitude)+";"+String.valueOf(longitude);
	        return coordenadas;
	    }   
	 
	
	public static String  figuraFisicaJuridica  (String dni){ 
	  
		String pCaracter ;	  
	    String result = ""; 
	    
	    if (dni != "") { 
		    pCaracter = dni.substring(0, 1); 
		    if  (pCaracter.equals("A") ||  pCaracter.equals("B") || pCaracter.equals("C") || 
			        pCaracter.equals("D") || pCaracter.equals("E") || pCaracter.equals("F") || 
			        pCaracter.equals("G") || pCaracter.equals("H") || pCaracter.equals("N")|| 
			        pCaracter.equals("P") || pCaracter.equals("Q")|| pCaracter.equals("S")) { 
			      result = "Jurídica"; 
		    }else  {
			      result = "Física";
		    }
		}else {
			result = "Física"; 
		}
	    return result;
	}
	
	public static String[] separarRazonSocial(String razonSocial) {
		//TODO: Implementar una separación mas inteligente.
		String[] result = razonSocial.split(" ", 3);
		return result;
	}
}