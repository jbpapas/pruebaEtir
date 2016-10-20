package es.dipucadiz.etir.comun.taglib.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.TextField;

import com.opensymphony.xwork2.util.ValueStack;


/**
 * Componente para crear nuestros textfield personalizado
 * 
 * @version 1.0 24/03/2010
 * @author SDS[DSOLIS]
 */
public class CampoTexto2 extends TextField{
	/**
     * Atributo que almacena el template al que ligamos este componente.
     */
	public static final String TEMPLATE = "textfieldSDS";
	
	/**
     * Atributo que almacena el nombre del componente.
     */
    final private static String COMPONENT_NAME = CampoTexto2.class.getName();
	
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
		
	
	
	


	public CampoTexto2(ValueStack stack, HttpServletRequest request,
	            HttpServletResponse response) {
	        super(stack, request, response);
	        
	    }
	 
	 
	 public String getComponentName() {
	        return COMPONENT_NAME;
	    }

	 
	 public void evaluateExtraParams() {
	        super.evaluateExtraParams();
	        if("true".equals(readonly)){
	        	 addParameter("cssClass","relleno");
	        	
	        }
	        
	        if (classText != null)
	        		addParameter("classText", findValue(classText, String.class));
	        
	        
	        if (classLabel != null)
	            addParameter("classLabel", findValue(classLabel, String.class));
	        if (styleLabel != null)
	            addParameter("styleLabel", findValue(styleLabel, String.class));
	        
	        if (styleText != null)
	            addParameter("styleText", findValue(styleText, String.class));
	        
	 }

	 //GETTER´s & SETTER´s
	public static String getTEMPLATE() {
		return TEMPLATE;
	}
	protected String getDefaultTemplate() {
        return TEMPLATE;
    }


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