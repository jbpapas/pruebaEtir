/**
 *  Utilidades para Java 1.4.x (solo para Formato.java, GadirServiceException.java y FormatoTest.java)
 */
package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

//import org.apache.commons.lang.StringUtils;

/**
 * Clase con métodos de uso común
 */
final public class UtilidadesFormatoBackup {
	private UtilidadesFormatoBackup() {}
	
	/**
	 * Devuelve true si la cadena es nula o está vacía
	 * @param string
	 * @return
	 */
	
	public static boolean isZero(final String str) {
		if(str == null)
			return false;
		else
			return ("".equals(str.replace("0", "").replace(",","").replace("\\.","").trim()) && !isEmpty(str));	
	}
	
	public static boolean isEmptyOrZero(final String string){
		return (isEmpty(string) || isZero(string));
	}

	public static boolean isEmpty(final String string){
		return (string == null || "".equals(string.trim()));
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
	
	public static String replace(String devolver, String cad1, String cad2) {
		String[] mat = devolver.split(",");	// NOTA: No existe replace(String,String) en Java 1.4.x
		if(mat.length > 1)
			 devolver = mat[0] + mat[1];
		return devolver;
	}
	
	 /* Métodos que se encargan de comprobar si los tipos numéricos BigInteger y BigDecimal son cero
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
	 * Método que se encarga de comprobar si una String esta compuesta por
	 * numeros sin decimales. Para ello hace uso de commons-lang.
	 * 
	 * @param str
	 *            Cadena a validar.
	 * @return True si esta compuesta por numeros y false si no lo esta.
	 */
	public static boolean isNumeric(final String str) {
		return isNumericDecimal(str);
//		if (isNull(str) || str.equals("")) {
//			return false;
//		}
//		return StringUtils.isNumeric(str);
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
		
		try {
			return (new BigDecimal(str)) != null;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean contains(String str, String valor) {
		return str.indexOf(valor)!=-1;
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
	
}
