package es.dipucadiz.etir.comun.displaytag;

import org.displaytag.decorator.TableDecorator;

import es.dipucadiz.etir.sb07.G7EAGestionAnulacionCobro.G7EAPagoParcialVO;


public class AnulacionesPagoDecorator extends TableDecorator
{
    
	public String addRowClass()
	{
		try{
			G7EAPagoParcialVO elem=(G7EAPagoParcialVO)getCurrentRowObject();

			if (elem.getCoCargoSubcargo()!=null && !elem.getCoCargoSubcargo().isEmpty()){
				return "subcargo cargo"+elem.getCoCargo();
			}
		}catch(Exception e){

		}
		try{
			G7EAPagoParcialVO elem=(G7EAPagoParcialVO)getCurrentRowObject();

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
