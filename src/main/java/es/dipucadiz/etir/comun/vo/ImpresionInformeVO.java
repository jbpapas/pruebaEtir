package es.dipucadiz.etir.comun.vo;

public class ImpresionInformeVO {
	
	private String pdf;
	private int copias;
	
	public ImpresionInformeVO(final String pdf, final int copias) {
		this.pdf = pdf;
		this.copias = copias;
	}

	public ImpresionInformeVO(final String pdf, final Integer copias) {
		this.pdf = pdf;
		this.copias = copias;
	}

	public String getPdf() {
		return pdf;
	}

	public int getCopias() {
		return copias;
	}
	
}
