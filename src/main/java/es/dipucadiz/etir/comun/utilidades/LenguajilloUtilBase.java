package es.dipucadiz.etir.comun.utilidades;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



/**
 * Al modificar esta clase hay que pasarla también a Oracle. 
 * @author JRonnols
 *
 */
public class LenguajilloUtilBase {
	
	private static final char LENGUAJILLO_INI = '{';
	private static final char LENGUAJILLO_FIN = '}';
	private static final String PREFIJO_SWITCH = "SWITCH";
	private static final String PREFIJO_RUE = "RUE";

	public static String parsearLenguajillo(String texto) {
		try {
			String result;
			if (texto != null && texto.indexOf(LENGUAJILLO_INI) != -1 && texto.indexOf(LENGUAJILLO_FIN) != -1 && texto.indexOf(LENGUAJILLO_INI) < texto.indexOf(LENGUAJILLO_FIN)) {
				// Numero de } que aparecen en la cadena
				int totalFin = texto.length()- texto.replace("}", "").length();
				// Numero de { que aparecen en la cadena
				int totalIni = texto.length()- texto.replace("{", "").length();
				boolean isLenguajillo = false;
				String lenguajillo = new String();
				result = new String();
				
				if(totalFin!=totalIni)
					for (int i=0; i<texto.length(); i++) {
						char thisChar = texto.charAt(i);
						if (thisChar == LENGUAJILLO_INI) {
							if (!isLenguajillo) {
								lenguajillo = new String();
								isLenguajillo = true;
							}
							else
								lenguajillo += thisChar;
								
						} else if (thisChar == LENGUAJILLO_FIN) {
							totalFin--;
							if(totalFin==0){
								result += interpretaLenguajillo(lenguajillo);
								isLenguajillo = false;
							}
							else
								lenguajillo += thisChar;	
						} else if (isLenguajillo) {
							lenguajillo += thisChar;
						} else if (!isLenguajillo) {
							result += thisChar;
						}
					}
				else{
					int ini = 0;
					int fin = 0;
					for (int i=0; i<texto.length(); i++) {
						char thisChar = texto.charAt(i);
						if (thisChar == LENGUAJILLO_INI) {
							ini++;
							if (!isLenguajillo) {
								lenguajillo = new String();
								isLenguajillo = true;
							}
							else
								lenguajillo += thisChar;
						} else if (thisChar == LENGUAJILLO_FIN) {
							fin++;
							if(ini==fin){
								result += interpretaLenguajillo(lenguajillo);
								isLenguajillo = false;
							}
							else
								lenguajillo += thisChar;	
						} else if (isLenguajillo) {
							lenguajillo += thisChar;
						} else if (!isLenguajillo) {
							result += thisChar;
						}
					}
				}
			} else {
				result = texto;
			}
			return result;
		} catch (Exception e) {
			System.err.println("Error al parsear " + texto);
			e.printStackTrace();
			return texto;
		}
	}

	private static String interpretaLenguajillo(String lenguajillo) {
		String result;
		String[] wordsArray = lenguajillo.split(" ");
		if (wordsArray == null || wordsArray.length == 0) {
			result = String.valueOf(LENGUAJILLO_INI) + String.valueOf(LENGUAJILLO_FIN);
		} else {
			if (wordsArray.length>0 && wordsArray[0]!=null) {
				wordsArray[0] = wordsArray[0].trim();
			}
			if (PREFIJO_RUE.equals(wordsArray[0]) && wordsArray.length > 1) {
				Long coExpedienteSeguimiento = Long.valueOf(wordsArray[1]);
				String texto = "";
				for (int i=2; i<wordsArray.length; i++) {
					if (texto != null && !"".equals(texto)) {
						texto += " ";
					}
					texto += wordsArray[i];
				}
				result = interpretaLinkExpediente(coExpedienteSeguimiento, texto);
			} else if (wordsArray[0].startsWith(PREFIJO_SWITCH)) {
				result = interpretaSwitch(lenguajillo);
			} else {
				result = LENGUAJILLO_INI + lenguajillo + LENGUAJILLO_FIN;
			}
		}
		return result;
	}

	private static String interpretaSwitch(String lenguajillo) {
		String result = "";
		String[] parts = lenguajillo.split("##CASE");
		String[] switchedValue = parts[0].split("[ \t]+");
		String lookingFor;
		if (switchedValue.length>1) {
			lookingFor = switchedValue[1].trim();
		} else {
			lookingFor = "";
		}
		for (int i=1; i<parts.length; i++) {
			String[] casedValue = parts[i].split("##", 2);
			String caseValue;
			if (casedValue.length>0) {
				caseValue = casedValue[0].trim();
			} else {
				caseValue = "";
			}
			if (lookingFor.equals(caseValue)) {
				if (casedValue.length>1) {
					result = casedValue[1];
				} else {
					result = "";
				}
				break;
			} else if ("DEFAULT".equals(caseValue)) {
				if (casedValue.length>1) {
					result = casedValue[1].trim();
				}
			}
		}
		return result;
	}

	private static String interpretaLinkExpediente(Long coExpedienteSeguimiento, String texto) {
		Class[] parameterTypes = {Long.class, String.class};
		Object[] args = {coExpedienteSeguimiento, texto};
		String resultado = invocarMetodo("es.dipucadiz.etir.comun.utilidades.LenguajilloUtil", "interpretaLinkExpediente", parameterTypes, args);
		if (resultado == null) {
			resultado = texto;
		}
		return resultado;
	}
	
	private static String invocarMetodo(String className, String method, Class[] parameterTypes, Object[] args) {
		String result = null;
		Class c;
		Method m;
		try {
			c = Class.forName(className);
			m = c.getMethod(method, parameterTypes);
			result = (String) m.invoke(c, args);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	protected static String convertirEnLink(String texto, String link) {
		return "<a target=\"_blank\" href=\"/etir/" + link + "\" title=\"" + texto + "\" onclick=\"botoneranewwindow=window.open('/etir/" + link + "&ventanaBotonLateral=true' , 'ventanaLateral" + link.substring(0, 5) + "' , 'width=1024,height=768,scrollbars=yes,status=yes,resizable=yes,toolbar=no,location=no,menubar=no,directories=no'); botoneranewwindow.focus(); return false;\">" + texto + "</a>";
	}

}
