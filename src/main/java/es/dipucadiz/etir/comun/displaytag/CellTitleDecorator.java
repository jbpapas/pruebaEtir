package es.dipucadiz.etir.comun.displaytag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CellTitleDecorator implements DisplaytagColumnDecorator {

	public Object decorate(final Object columnValue, final PageContext pageContext,
			final MediaTypeEnum media) throws DecoratorException {
		Object returnValue = columnValue;
		if (columnValue == null || columnValue.toString().trim().equals("") ) {
			returnValue = "&nbsp;";
		} else if (columnValue instanceof String && !((String)columnValue).contains("<")){
			String[] cadena = new String((String) columnValue).split("-",2);
			if(Utilidades.isNotEmpty(cadena[1])){
				String div="<div style=\"overflow:hidden;height:17px\" title=\"" + cadena[1].trim() + "\" >";
				div += cadena[0];
				div += "</div>";
				returnValue = div;
			}else{
				returnValue = cadena[0];
			}
		}else{
			returnValue = columnValue;
		}
		return returnValue;
	}

}