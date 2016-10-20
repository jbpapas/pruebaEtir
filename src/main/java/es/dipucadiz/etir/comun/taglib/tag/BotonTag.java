package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.SubmitTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.Boton;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;

public class BotonTag  extends SubmitTag
{
	
	
	



	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		
        return new Boton(stack, req, res);
    }
    
    
      protected void populateParams() {
	        super.populateParams();
	        Boolean accesible=DatosSesion.isConAccesibilidad();
			 Boton boton = (Boton) component;
			 if (accesible !=null && accesible){
				 boton.setTheme("gadirAccesible");  
		    }else{
		    	boton.setTheme("gadirInaccesible");
		    }

      }



}
	