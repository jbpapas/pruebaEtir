package es.dipucadiz.etir.comun.displaytag;

import org.displaytag.decorator.TableDecorator;

import es.dipucadiz.etir.comun.vo.ParteCajaMesVO;


public class ParteCajaMesDecorator extends TableDecorator
{
    
	public String addRowClass()
	{
		try{
			ParteCajaMesVO elem=(ParteCajaMesVO)getCurrentRowObject();

			if (elem.getHayCobrAnul()==null || elem.getHayCobrAnul().isEmpty()){
				return "subcargo cargo"+elem.getCoCargo();
			}
		}catch(Exception e){

		}

		return "cargo";
	}

}
