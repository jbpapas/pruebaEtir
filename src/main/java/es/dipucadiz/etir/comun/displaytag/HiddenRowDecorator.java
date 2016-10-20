package es.dipucadiz.etir.comun.displaytag;

import org.displaytag.decorator.TableDecorator;

import es.dipucadiz.etir.sb06.vo.TramiteVO;


public class HiddenRowDecorator extends TableDecorator {
    
	public String addRowClass() {
		Object currentRow = getCurrentRowObject();
		boolean visible;
		if (currentRow instanceof TramiteVO) {
			visible = ((TramiteVO)currentRow).isVisible();
		} else {
			visible = true;
		}
		
		String result;
		if (visible) {
			result = "filaVisible";
		} else {
			result = "filaOculta x-hide-display";
		}
		return result;
	}
	
}
