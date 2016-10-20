package es.dipucadiz.etir.comun.vo;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.opensymphony.xwork2.Preparable;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.ProcesoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.BDDocumentalGrupoDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.GestorTareasDTO;
import es.dipucadiz.etir.comun.dto.GestorTareasSeguimientoDTOId;
import es.dipucadiz.etir.comun.dto.HDomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class GestorTareasSeguimientoHGestorTareasVO implements Serializable {

	private GestorTareasSeguimientoDTOId idGesTarSeg;
	private Clob observacionesGesTarSeg;
	private String coUsuarioActualizacionGesTarSeg;
	private Date fhActualizacion;
	private Long nuTareaHGesTar;
	private String tituloHGesTar;
	private String tipoHGesTar;
	private String subtipoHGesTar;
	private String estadoHGesTar;
	private String poRealizadoHGesTar;
	private String prioridadHGesTar;
	private String municipioHGesTar;
	private String tipoSoporteHGesTar;
	private String organoPrimerNivelHGesTar;
	private String aplicacionHGesTar;
	private String fxEntradaHGesTar;
	private String fxPrevistoInicioHGesTar;
	private String fxInicioHGesTar;
	private String fxPrevistoFinHGesTar;
	private String fxFinHGesTar;
	private String usuarioAsignadoHGesTar;
	private String usuarioAltaHGesTar;
	private String usuarioReceptorHGesTar;	
	private String observacionesHGesTar;
	private String unidadAdministrativaDTOHGesTar;
	private String usuarioActualizacionGesTarSeg ="";
	private String usuarioActualizacionHGesTar="";
	//private String nombreEmisorHGesTar;
	private String usuarioEmisorHGesTar;
	private BDDocumentalGrupoDTO bddocGrupoDocumentacionDTOHGesTar; 
	private BDDocumentalGrupoDTO bddocGrupoFicheroDTOHGesTar; 


	public String getOrganoPrimerNivelHGesTar() {
		return organoPrimerNivelHGesTar;
	}
	public void setOrganoPrimerNivelHGesTar(String organoPrimerNivelHGesTar) {
		this.organoPrimerNivelHGesTar = organoPrimerNivelHGesTar;
	}

	public String getAplicacionHGesTar() {
		return aplicacionHGesTar;
	}
	public void setAplicacionHGesTar(String aplicacionHGesTar) {
		this.aplicacionHGesTar = aplicacionHGesTar;
	}
	public GestorTareasSeguimientoDTOId getIdGesTarSeg() {
		return idGesTarSeg;
	}
	public void setIdGesTarSeg(GestorTareasSeguimientoDTOId idGesTarSeg) {
		this.idGesTarSeg = idGesTarSeg;
	}
	public Clob getObservacionesGesTarSeg() {
		return observacionesGesTarSeg;
	}
	public void setObservacionesGesTarSeg(Clob observacionesGesTarSeg) {
		this.observacionesGesTarSeg = observacionesGesTarSeg;
	}
	public String getCoUsuarioActualizacionGesTarSeg() {
		return coUsuarioActualizacionGesTarSeg;
	}
	public void setCoUsuarioActualizacionGesTarSeg(
			String coUsuarioActualizacionGesTarSeg) {
		this.coUsuarioActualizacionGesTarSeg = coUsuarioActualizacionGesTarSeg;
	}
	public Long getNuTareaHGesTar() {
		return nuTareaHGesTar;
	}
	public void setNuTareaHGesTar(Long nuTareaHGesTar) {
		this.nuTareaHGesTar = nuTareaHGesTar;
	}
	public String getTituloHGesTar() {
		return tituloHGesTar;
	}
	public void setTituloHGesTar(String tituloHGesTar) {
		this.tituloHGesTar = tituloHGesTar;
	}

	/*public String getNombreEmisorHGesTar() {
		return nombreEmisorHGesTar;
	}
	public void setNombreEmisorHGesTar(String nombreEmisorHGesTar) {
		this.nombreEmisorHGesTar = nombreEmisorHGesTar;
	}*/
	public String getEstadoHGesTar() {
		return estadoHGesTar;
	}
	public void setEstadoHGesTar(String estadoHGesTar) {
		this.estadoHGesTar = estadoHGesTar;
	}
	public String getPoRealizadoHGesTar() {
		return poRealizadoHGesTar;
	}
	public void setPoRealizadoHGesTar(String poRealizadoHGesTar) {
		this.poRealizadoHGesTar = poRealizadoHGesTar;
	}
	public String getPrioridadHGesTar() {
		return prioridadHGesTar;
	}
	public void setPrioridadHGesTar(String prioridadHGesTar) {
		this.prioridadHGesTar = prioridadHGesTar;
	}
	public BDDocumentalGrupoDTO getBddocGrupoDocumentacionDTOHGesTar() {
		return bddocGrupoDocumentacionDTOHGesTar;
	}
	public void setBddocGrupoDocumentacionDTOHGesTar(
			BDDocumentalGrupoDTO bddocGrupoDocumentacionDTOHGesTar) {
		this.bddocGrupoDocumentacionDTOHGesTar = bddocGrupoDocumentacionDTOHGesTar;
	}
	public String getObservacionesHGesTar() {
		return observacionesHGesTar;
	}
	public void setObservacionesHGesTar(String observacionesHGesTar) {
		this.observacionesHGesTar = observacionesHGesTar;
	}
	public BDDocumentalGrupoDTO getBddocGrupoFicheroDTOHGesTar() {
		return bddocGrupoFicheroDTOHGesTar;
	}
	public void setBddocGrupoFicheroDTOHGesTar(
			BDDocumentalGrupoDTO bddocGrupoFicheroDTOHGesTar) {
		this.bddocGrupoFicheroDTOHGesTar = bddocGrupoFicheroDTOHGesTar;
	}

	public String getObservacionesString(){
		String res = "";
		if(this.observacionesGesTarSeg != null){
			try {
				res = this.observacionesGesTarSeg.getSubString(1,(int)this.observacionesGesTarSeg.length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return res;
	}


	public String getUnidadAdministrativaDTOHGesTar() {
		return unidadAdministrativaDTOHGesTar;
	}
	public void setUnidadAdministrativaDTOHGesTar(
			String unidadAdministrativaDTOHGesTar) {
		this.unidadAdministrativaDTOHGesTar = unidadAdministrativaDTOHGesTar;
	}
	public String getUsuarioActualizacionGesTarSeg() {
		return usuarioActualizacionGesTarSeg;
	}
	public void setUsuarioActualizacionGesTarSeg(
			String usuarioActualizacionGesTarSeg) {
		this.usuarioActualizacionGesTarSeg = usuarioActualizacionGesTarSeg;
	}
	public Date getFhActualizacion() {
		return fhActualizacion;
	}
	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}
	public String getUsuarioActualizacionHGesTar() {
		return usuarioActualizacionHGesTar;
	}
	public void setUsuarioActualizacionHGesTar(String usuarioActualizacionHGesTar) {
		this.usuarioActualizacionHGesTar = usuarioActualizacionHGesTar;
	}
	public String getTipoHGesTar() {
		return tipoHGesTar;
	}
	public void setTipoHGesTar(String tipoHGesTar) {
		this.tipoHGesTar = tipoHGesTar;
	}
	public String getSubtipoHGesTar() {
		//subtipoHGesTar = TablaGt.getValor("SUBTARMA", coSubtipoHGesTar, TablaGt.COLUMNA_DESCRIPCION);
		return subtipoHGesTar;
	}
	public void setSubtipoHGesTar(String subtipoHGesTar) {
		this.subtipoHGesTar = subtipoHGesTar;
	}
	public String getUsuarioAltaHGesTar() {
		return usuarioAltaHGesTar;
	}
	public void setUsuarioAltaHGesTar(String usuarioAltaHGesTar) {
		this.usuarioAltaHGesTar = usuarioAltaHGesTar;
	}

	public String getTipoSoporteHGesTar() {
		//	tipoSoporteHGesTar = TablaGt.getValor("TIPOSOPO", coTipoSoporteHGesTar,TablaGt.COLUMNA_DESCRIPCION);
		return tipoSoporteHGesTar;
	}
	public void setTipoSoporteHGesTar(String tipoSoporteHGesTar) {
		this.tipoSoporteHGesTar = tipoSoporteHGesTar;
	}

	public String getMunicipioHGesTar() {
		return municipioHGesTar;
	}
	public void setMunicipioHGesTar(String municipioHGesTar) {
		this.municipioHGesTar = municipioHGesTar;
	}
	public String getFxEntradaHGesTar() {
		return fxEntradaHGesTar;
	}
	public void setFxEntradaHGesTar(String fxEntradaHGesTar) {
		this.fxEntradaHGesTar = fxEntradaHGesTar;
	}
	public String getFxPrevistoInicioHGesTar() {
		return fxPrevistoInicioHGesTar;
	}
	public void setFxPrevistoInicioHGesTar(String fxPrevistoInicioHGesTar) {
		this.fxPrevistoInicioHGesTar = fxPrevistoInicioHGesTar;
	}
	public String getFxInicioHGesTar() {
		return fxInicioHGesTar;
	}
	public void setFxInicioHGesTar(String fxInicioHGesTar) {
		this.fxInicioHGesTar = fxInicioHGesTar;
	}
	public String getFxPrevistoFinHGesTar() {
		return fxPrevistoFinHGesTar;
	}
	public void setFxPrevistoFinHGesTar(String fxPrevistoFinHGesTar) {
		this.fxPrevistoFinHGesTar = fxPrevistoFinHGesTar;
	}
	public String getFxFinHGesTar() {
		return fxFinHGesTar;
	}
	public void setFxFinHGesTar(String fxFinHGesTar) {
		this.fxFinHGesTar = fxFinHGesTar;
	}
	public String getUsuarioAsignadoHGesTar() {
		return usuarioAsignadoHGesTar;
	}
	public void setUsuarioAsignadoHGesTar(String usuarioAsignadoHGesTar) {
		this.usuarioAsignadoHGesTar = usuarioAsignadoHGesTar;
	}
	public String getUsuarioReceptorHGesTar() {
		return usuarioReceptorHGesTar;
	}
	public void setUsuarioReceptorHGesTar(String usuarioReceptorHGesTar) {
		this.usuarioReceptorHGesTar = usuarioReceptorHGesTar;
	}
	public String getUsuarioEmisorHGesTar() {
		return usuarioEmisorHGesTar;
	}
	public void setUsuarioEmisorHGesTar(String usuarioEmisorHGesTar) {
		this.usuarioEmisorHGesTar = usuarioEmisorHGesTar;
	}
}
