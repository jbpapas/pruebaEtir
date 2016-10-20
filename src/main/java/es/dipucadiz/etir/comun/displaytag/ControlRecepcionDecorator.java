package es.dipucadiz.etir.comun.displaytag;

import org.displaytag.decorator.TableDecorator;

import es.dipucadiz.etir.sb05.vo.ControlRecepcionVO;
import es.dipucadiz.etir.sb07.domiciliaciones.action.G7A2.G7A2CargoSubcargoVO;


public class ControlRecepcionDecorator extends TableDecorator
{
    
	public String addRowClass()
	{
		try{ 
			ControlRecepcionVO elem=(ControlRecepcionVO)getCurrentRowObject();

			if (elem.getCoCargaControlRecepcion()==null){
				return "subcargo cargo"+elem.getCoCargaControlRecepcion();
			}
		}catch(Exception e){

		}
		try{
			ControlRecepcionVO elem=(ControlRecepcionVO)getCurrentRowObject();

			if (elem.getCoCargaControlRecepcion()==null){
				return "subcargo cargo"+elem.getCoCargaControlRecepcion();
			}
		}catch(Exception e){

		}

		return "cargo";
	}
	
	/*public String addRowId() {
		G771CargoSubcargoVO elem=(G771CargoSubcargoVO)getCurrentRowObject();
		
		if (elem.getCoCargoSubcargo()!=null && !elem.getCoCargoSubcargo().isEmpty()){
			return super.addRowId();
		}
		
		return "cargo"+elem.getCoCargo();
	}*/

}
