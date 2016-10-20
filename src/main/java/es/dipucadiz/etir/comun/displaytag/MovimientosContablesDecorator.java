package es.dipucadiz.etir.comun.displaytag;

import org.displaytag.decorator.TableDecorator;

import es.dipucadiz.etir.sb07.comun.vo.MovimientoContableVO;


public class MovimientosContablesDecorator extends TableDecorator
{
    
	public String addRowClass()
	{
		try{
			MovimientoContableVO elem=(MovimientoContableVO)getCurrentRowObject();

			if (elem.getCoCargoSubcargo()!=null && !elem.getCoCargoSubcargo().isEmpty()){
				return "subcargo cargo"+elem.getCoCargo();
			}
		}catch(Exception e){

		}
		try{
			MovimientoContableVO elem=(MovimientoContableVO)getCurrentRowObject();

			if (elem.getCoCargoSubcargo()!=null && !elem.getCoCargoSubcargo().isEmpty()){
				return "subcargo cargo"+elem.getCoCargo();
			}
		}catch(Exception e){

		}

		return "cargo";
	}

}
