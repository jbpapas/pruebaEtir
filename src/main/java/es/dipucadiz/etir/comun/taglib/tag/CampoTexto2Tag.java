
package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.TextFieldTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.CampoTexto2;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;


/**
 * Tag para crear nuestros textfield personalizado
 * 
 * @version 1.0 24/03/2010
 * @author SDS[DSOLIS]
 */

public class CampoTexto2Tag  extends TextFieldTag{
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	
    /**
     * Atributo que almacena el attributo class para el div que alberga a nuestro input.
     */ 
	protected String classText;
	
	/**
     * Atributo que almacena el attributo class para el div que alberga a nuestro label.
     */
	
	protected String classLabel;
	/**
     * Atributo que almacena el attributo style para el div que alberga a nuestro label.
     */
	protected String styleLabel;
	
	/**
     * Atributo que almacena el attributo class para el div que alberga a nuestro input.
     */
	protected String styleText;
	
	
	
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		
        return new CampoTexto2(stack, req, res);
    }
	
	
	
	  protected void populateParams() {
	        super.populateParams();
	        
	        CampoTexto2 campoTexto = (CampoTexto2) component;
	       campoTexto.setClassLabel(classLabel);
	       campoTexto.setClassText(classText);
	       campoTexto.setStyleLabel(styleLabel);
	       campoTexto.setStyleText(styleText);
	       Boolean accesible=DatosSesion.isConAccesibilidad();
	       if (accesible !=null && accesible){
	      	 campoTexto.setTheme("gadirAccesible");  
	      }else{
	      	campoTexto.setTheme("gadirInaccesible");
	      }
	       
	      
	       

	      
	       
	    }


	  //GETTER´s & SETTER´s
	  	
	public String getClassText() {
		return classText;
	}



	public void setClassText(String classText) {
		this.classText = classText;
	}



	public String getClassLabel() {
		return classLabel;
	}



	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}



	public String getStyleLabel() {
		return styleLabel;
	}



	public void setStyleLabel(String styleLabel) {
		this.styleLabel = styleLabel;
	}



	public String getStyleText() {
		return styleText;
	}



	public void setStyleText(String styleText) {
		this.styleText = styleText;
	}
	  
	  
}