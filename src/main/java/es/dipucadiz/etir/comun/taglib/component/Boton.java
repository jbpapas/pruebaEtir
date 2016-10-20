package es.dipucadiz.etir.comun.taglib.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Submit;

import com.opensymphony.xwork2.util.ValueStack;

public class Boton extends Submit {
    final private static String COMPONENT_NAME = Boton.class.getName();
    
     public static final String OPEN_TEMPLATE = "submitSDS";
     public static final String TEMPLATE = "submitSDS-closed";
     
     public static String getOPEN_TEMPLATE() {
		return OPEN_TEMPLATE;
	}


	public Boton(ValueStack stack, HttpServletRequest request,
	            HttpServletResponse response) {
	        super(stack, request, response);
	        
	    }
	  
	  
	   public String getComponentName() {
	        return COMPONENT_NAME;
	    }
	  
	   public void evaluateExtraParams() {
	        super.evaluateExtraParams();
	       }
	       
	       
	       protected String getDefaultTemplate() {
        return TEMPLATE;
    }
	      public String getDefaultOpenTemplate() {
	    	     return OPEN_TEMPLATE;
	    	    }

		public static String getCOMPONENT_NAME() {
			return COMPONENT_NAME;
		}


		public static String getTEMPLATE() {
			return TEMPLATE;
		}
    
      }