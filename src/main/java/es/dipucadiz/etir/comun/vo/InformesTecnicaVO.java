package es.dipucadiz.etir.comun.vo;

import java.io.File;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class InformesTecnicaVO {
	private int indiceFichero = 0;
	private String ODT = "";
	private String ODTGenerado = "";
	private String T2P = "";
	private String contentXml = "";
	
	private boolean existeFicheroODT = false;
	private boolean existeFicheroODTGenerado = false;
	private boolean existeFicheroT2P = false;
	private boolean existeFicherocontentXml = false;
	
	public InformesTecnicaVO(int indiceFichero, String ODT, String ODTGenerado, String T2P, String contentXml) {
		this.indiceFichero = indiceFichero;
		this.ODT = ODT;
		this.ODTGenerado = ODTGenerado;
		this.T2P = T2P;
		this.contentXml = contentXml;
	}

	public int getIndiceFichero() {
		return indiceFichero;
	}

	public void setIndiceFichero(int indiceFichero) {
		this.indiceFichero = indiceFichero;
	}

	public String getODT() {
		return ODT;
	}

	public void setODT(String oDT) {
		ODT = oDT;
	}

	public String getODTGenerado() {
		return ODTGenerado;
	}

	public void setODTGenerado(String oDTGenerado) {
		ODTGenerado = oDTGenerado;
	}

	public String getT2P() {
		return T2P;
	}

	public void setT2P(String t2p) {
		T2P = t2p;
	}

	public String getContentXml() {
		return contentXml;
	}

	public void setContentXml(String contentXml) {
		this.contentXml = contentXml;
	}


	public boolean isExisteFicheroODT() {
		return existeFichero(ODT);
	}

	public boolean isExisteFicheroODTGenerado() {
		return existeFichero(ODTGenerado);
	}

	public boolean isExisteFicheroT2P() {
		return existeFichero(T2P);
	}

	public boolean isExisteFicherocontentXml() {
		return existeFichero(contentXml);
	}

	private boolean existeFichero(String nombre) {
		if(Utilidades.isEmpty(nombre))
			return false;
		else {
			try {
				return (new File(nombre).exists());
			} catch (Exception e) {
				return false;
			}
		}
	}
	
}
