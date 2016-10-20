
package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.dojo.views.jsp.ui.AutocompleterTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.Combo1;

public class Combo1Tag extends AutocompleterTag {

	private static final long serialVersionUID = 2831109865215200025L;

	protected String conFooter;
	protected String conAyuda;
	protected String nombreCampoAyuda;
	protected String salida;
	protected String optionsConTitle;
	protected String autoSeleccionOpcionUnica;

	protected String coProcesoActual;

	protected String classText;
	protected String classLabel;
	protected String styleLabel;
	protected String styleText;
	protected String styleGroup;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		coProcesoActual = stack.findString("coProcesoActual");
		return new Combo1(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();

		Combo1 combo1 = (Combo1) component;

		combo1.setConFooter(conFooter);
		combo1.setConAyuda(conAyuda);
		combo1.setNombreCampoAyuda(nombreCampoAyuda);

		combo1.setCoProcesoActual(coProcesoActual);

		combo1.setSalida(salida);

		combo1.setOptionsConTitle(optionsConTitle);
		combo1.setAutoSeleccionOpcionUnica(autoSeleccionOpcionUnica);

		combo1.setClassLabel(classLabel);
		combo1.setClassText(classText);
		combo1.setStyleLabel(styleLabel);
		combo1.setStyleText(styleText);
		combo1.setStyleGroup(styleGroup);

	}

	public String getConFooter() {
		return conFooter;
	}

	public void setConFooter(String conFooter) {
		this.conFooter = conFooter;
	}

	public String getConAyuda() {
		return conAyuda;
	}

	public void setConAyuda(String conAyuda) {
		this.conAyuda = conAyuda;
	}

	public String getCoProcesoActual() {
		return coProcesoActual;
	}

	public void setCoProcesoActual(String coProcesoActual) {
		this.coProcesoActual = coProcesoActual;
	}

	public String getNombreCampoAyuda() {
		return nombreCampoAyuda;
	}

	public void setNombreCampoAyuda(String nombreCampoAyuda) {
		this.nombreCampoAyuda = nombreCampoAyuda;
	}

	public String getSalida() {
		return salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
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

	public String getStyleGroup() {
		return styleGroup;
	}

	public void setStyleGroup(String styleGroup) {
		this.styleGroup = styleGroup;
	}

	public String getOptionsConTitle() {
		return optionsConTitle;
	}

	public void setOptionsConTitle(String optionsConTitle) {
		this.optionsConTitle = optionsConTitle;
	}

	public String getAutoSeleccionOpcionUnica() {
		return autoSeleccionOpcionUnica;
	}

	public void setAutoSeleccionOpcionUnica(String autoSeleccionOpcionUnica) {
		this.autoSeleccionOpcionUnica = autoSeleccionOpcionUnica;
	}

}
