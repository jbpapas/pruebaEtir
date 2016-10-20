package es.dipucadiz.etir.comun.taglib.tag;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.CampoCalendar;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;

/**
 * Tag para crear nuestros textfield personalizado con icono de select.
 * 
 * @version 1.0 25/03/2010
 * @author SDS[DSOLIS]
 */
public class CampoCalendarTag  extends CampoTexto2Tag{
	private static final long serialVersionUID = 1L;

public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		
        return new CampoCalendar(stack, req, res);
    }


protected void populateParams() {
    super.populateParams();
    
    CampoCalendar calendario = (CampoCalendar) component;
    
    Boolean accesible=DatosSesion.isConAccesibilidad();
  
    if (accesible !=null && accesible){
    	calendario.setTheme("gadirAccesible");  
    }else{
    	calendario.setTheme("gadirInaccesible");
    }
   

  
   
}



}