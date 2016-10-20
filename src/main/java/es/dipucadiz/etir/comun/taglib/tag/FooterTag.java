package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class FooterTag extends TagSupport{

	private static final long serialVersionUID = 130431477998867327L;

	public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("</span></div>");
        } catch (Exception ex) {
            throw new JspException("IO problems");
        }
        return SKIP_BODY;
    }

	
}
