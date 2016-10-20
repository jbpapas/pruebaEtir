package es.dipucadiz.etir.comun.displaytag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class CellOverflowHiddenDecorator implements DisplaytagColumnDecorator {

	public Object decorate(final Object columnValue, final PageContext pageContext,
			final MediaTypeEnum media) throws DecoratorException {
		Object returnValue;
		if (columnValue == null || columnValue.equals("") ) {
			returnValue = "&nbsp;";
		} else if (columnValue instanceof String && !((String)columnValue).contains("<")){
			String div="<div style=\"overflow:hidden;height:17px\" title=\"" + columnValue + "\" >";
			div += columnValue;
			div += "</div>";
			returnValue = div;
		}else{
			returnValue = columnValue;
		}
		return returnValue;
	}

}
