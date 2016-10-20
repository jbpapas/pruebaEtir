package es.dipucadiz.etir.comun.struts.taglib.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class GadirModels {

	protected ValueStack stack;
	protected HttpServletRequest req;
	protected HttpServletResponse res;

	protected SampleModel sample;

	public GadirModels(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super();
		this.stack = stack;
		this.req = req;
		this.res = res;
	}

	public SampleModel getSample() {
		if (this.sample == null) {
			return new SampleModel(this.stack, this.req, this.res);
		}
		return sample;
	}
}
