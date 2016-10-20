package es.dipucadiz.etir.comun.displaytag;

import org.displaytag.decorator.TableDecorator;

import es.dipucadiz.etir.sb07.G771Cargos.action.G771CargoSubcargoVO;
import es.dipucadiz.etir.sb07.domiciliaciones.action.G7A2.G7A2CargoSubcargoVO;


public class CargosDecorator extends TableDecorator
{
    
	public String addRowClass()
	{
		try{
			G771CargoSubcargoVO elem=(G771CargoSubcargoVO)getCurrentRowObject();

			if (elem.getCoCargoSubcargo()!=null && !elem.getCoCargoSubcargo().isEmpty()){
				return "subcargo cargo"+elem.getCoCargo();
			}
		}catch(Exception e){

		}
		try{
			G7A2CargoSubcargoVO elem=(G7A2CargoSubcargoVO)getCurrentRowObject();

			if (elem.getCoCargoSubcargo()!=null && !elem.getCoCargoSubcargo().isEmpty()){
				return "subcargo cargo"+elem.getCoCargo();
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
