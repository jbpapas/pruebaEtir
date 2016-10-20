package es.dipucadiz.etir.comun.displaytag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class EmptyCellsDecorator implements DisplaytagColumnDecorator {

	public Object decorate(final Object columnValue, final PageContext pageContext,
			final MediaTypeEnum media) throws DecoratorException {
		Object returnValue;
		if (columnValue == null || columnValue.toString().trim().equals("") ) {
			returnValue = "&nbsp;";
		}else{
			returnValue = columnValue;
		}
		return returnValue;
	}

}
