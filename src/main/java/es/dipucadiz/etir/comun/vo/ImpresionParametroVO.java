package es.dipucadiz.etir.comun.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.utilidades.Impresion;

public class ImpresionParametroVO {

	private int ordenTxt;
	private int ordenPdf;
	private int lineasInsertadas;
	private String etiquetaActual;
	private String ubicacionEtiqueta;
	private String ubicacionValor;
	private Map<String, String> etiquetas;
	private String[] rutas;
	private Map<String, String> etiquetasTabla;
	private List<ImpresionInformeVO> informesImprimir;
	private HashMap<String, String> etiquetasConfig;
	private String tablaUnica;
	private Map<String, String> saltoTabla;
	private Map<String, Integer> numCeldas;
	private HttpServletResponse servletResponse;
	private EjecucionDTO ejecucionDTO;
	private List<String> listaLineas;
	private List<String> listaItemsManifest;
	private boolean isBatch;
	private int paginaActual;
	private int lineasTotal;
	private int lineasPaginaActual;
	private int lineasPrimeraPagina;
	private int lineasPorPagina;
	private boolean saltarUltimaFila;
	private boolean controlSaltoPagina;
	private String nombrePdf;
	private String coBDDocumentalGrupo;
	private String coExpediente;
	private String subcarpetaTemporal;

	public ImpresionParametroVO() {
		ordenPdf = 0;
		servletResponse = null;
		informesImprimir = null;
		saltoTabla = new HashMap<String, String>();
		numCeldas = new HashMap<String, Integer>();
		paginaActual = 1;
		lineasPaginaActual = 0;
		lineasPrimeraPagina = 0;
		lineasPorPagina = 0;
		saltarUltimaFila = false;
		controlSaltoPagina = false;
		etiquetasConfig = new HashMap<String, String>();
		informesImprimir = new ArrayList<ImpresionInformeVO>();
		lineasInsertadas = 0;
		listaLineas = new ArrayList<String>();
		listaItemsManifest = new ArrayList<String>();
		nombrePdf = null;
		coBDDocumentalGrupo = null;
	}

	public void setOrdenTxt(int ordenTxt) {
		this.ordenTxt = ordenTxt;
	}

	public int getOrdenTxt() {
		return ordenTxt;
	}

	public void setEtiquetaActual(String etiquetaActual) {
		this.etiquetaActual = etiquetaActual;
	}

	public String getEtiquetaActual() {
		return etiquetaActual;
	}

	public void setUbicacionEtiqueta(String ubicacionEtiqueta) {
		this.ubicacionEtiqueta = ubicacionEtiqueta;
	}

	public String getUbicacionEtiqueta() {
		return ubicacionEtiqueta;
	}

	public void setUbicacionValor(String ubicacionValor) {
		this.ubicacionValor = ubicacionValor;
	}

	public String getUbicacionValor() {
		return ubicacionValor;
	}

	@SuppressWarnings("unchecked")
	public void setEtiquetas(HashMap<String, String> etiquetas) {
		this.etiquetas = (Map<String, String>) etiquetas.clone();
	}

	public Map<String, String> getEtiquetas() {
		return etiquetas;
	}

	public void setOrdenPdf(int ordenPdf) {
		this.ordenPdf = ordenPdf;
	}

	public int getOrdenPdf() {
		return ordenPdf;
	}

	public void setRutas(String[] rutas) {
		this.rutas = rutas;
	}

	public String[] getRutas() {
		return rutas;
	}

	@SuppressWarnings("unchecked")
	public void setEtiquetasTabla(HashMap<String, String> etiquetasTabla) {
		this.etiquetasTabla = (Map<String, String>) etiquetasTabla.clone();
	}

	public Map<String, String> getEtiquetasTabla() {
		return etiquetasTabla;
	}

	public void setInformesImprimir(List<ImpresionInformeVO> informesImprimir) {
		this.informesImprimir = informesImprimir;
	}

	public List<ImpresionInformeVO> getInformesImprimir() {
		return informesImprimir;
	}

	@SuppressWarnings("unchecked")
	public void setEtiquetasConfig(HashMap<String, String> etiquetasConfig) {
		if (etiquetasConfig != null) {
			int nlipp=0;
			int nlisa=0;
			int nlipa=0;
			this.etiquetasConfig = (HashMap<String, String>) etiquetasConfig.clone();
			for (Map.Entry<String, String> mapEntry : this.etiquetasConfig.entrySet()) {
				if (Impresion.ETIQUETA_NLIPP.equals(mapEntry.getKey())) {
					nlipp = Integer.valueOf(mapEntry.getValue());
				} else if (Impresion.ETIQUETA_NLIPA.equals(mapEntry.getKey())){
					nlipa = Integer.valueOf(mapEntry.getValue());
				} else if (Impresion.ETIQUETA_NLISA.equals(mapEntry.getKey())){
					nlisa = Integer.valueOf(mapEntry.getValue());
				}
			}
	
			controlSaltoPagina = nlipa > 0 && nlisa > 0;
			if (controlSaltoPagina) {
				if (nlipp == 0) nlipp = nlipa;
				lineasPrimeraPagina = nlipp;
				lineasPorPagina = nlipa;
				int lineasUltimaPagina = nlisa;
				
				if (lineasTotal <= lineasPrimeraPagina) {
					int lineasPrimeraPaginaUnica = lineasPrimeraPagina - (lineasPorPagina - lineasUltimaPagina);
					if (lineasTotal > lineasPrimeraPaginaUnica) {
						lineasPrimeraPagina = lineasTotal - 1;
					} else {
						lineasPrimeraPagina = lineasPrimeraPaginaUnica;
					}
				}
				
				int lineasRestoDocumento = lineasTotal - lineasPrimeraPagina;
				int lineasCalculadasEnUltimaPagina = lineasRestoDocumento % lineasPorPagina;
				if (lineasCalculadasEnUltimaPagina > lineasUltimaPagina || lineasCalculadasEnUltimaPagina == 0) {
					saltarUltimaFila = true;
				}
			}
		}
	}

	public HashMap<String, String> getEtiquetasConfig() {
		return etiquetasConfig;
	}

	public void setTablaUnica(String tablaUnica) {
		this.tablaUnica = tablaUnica;
	}

	public String getTablaUnica() {
		return tablaUnica;
	}

	public void putSaltoTabla(String nombreTabla, String valor) {
		saltoTabla.put(nombreTabla, valor);
	}

	public void putNumCeldas(String nombreTabla, Integer num) {
		numCeldas.put(nombreTabla, num);
	}

	public String getSaltoTabla(String nombreTabla) {
		return saltoTabla.get(nombreTabla);
	}

	public Integer getNumCeldas(String nombreTabla) {
		return numCeldas.get(nombreTabla);
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public int getLineasPaginaActual() {
		return lineasPaginaActual;
	}

	public void setLineasPaginaActual(int lineasPaginaActual) {
		this.lineasPaginaActual = lineasPaginaActual;
	}
	
	public void addLineasPaginaActual() {
		this.lineasPaginaActual++;
	}

	public int getLineasPrimeraPagina() {
		return lineasPrimeraPagina;
	}

	public void setLineasPrimeraPagina(int lineasPrimeraPagina) {
		this.lineasPrimeraPagina = lineasPrimeraPagina;
	}

	public int getLineasPorPagina() {
		return lineasPorPagina;
	}

	public void setLineasPorPagina(int lineasPorPagina) {
		this.lineasPorPagina = lineasPorPagina;
	}

	public boolean isSaltarUltimaFila() {
		return saltarUltimaFila;
	}

	public void setSaltarUltimaFila(boolean saltarUltimaFila) {
		this.saltarUltimaFila = saltarUltimaFila;
	}

	public boolean isControlSaltoPagina() {
		return controlSaltoPagina;
	}

	public void setControlSaltoPagina(boolean controlSaltoPagina) {
		this.controlSaltoPagina = controlSaltoPagina;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void addPaginaActual() {
		this.paginaActual++;
	}

	public void setLineasTotal(int lineasTotal) {
		this.lineasTotal = lineasTotal;
	}

	public int getLineasTotal() {
		return lineasTotal;
	}

	public void setEjecucionDTO(EjecucionDTO ejecucionDTO) {
		this.ejecucionDTO = ejecucionDTO;
	}

	public EjecucionDTO getEjecucionDTO() {
		return ejecucionDTO;
	}

	public void setLineasInsertadas(int lineasInsertadas) {
		this.lineasInsertadas = lineasInsertadas;
	}

	public int getLineasInsertadas() {
		return lineasInsertadas;
	}

	public void addLineasInsertadas() {
		lineasInsertadas++;
	}

	public void setListaLineas(List<String> listaLineas) {
		this.listaLineas = listaLineas;
	}

	public List<String> getListaLineas() {
		return listaLineas;
	}
	
	public void pushListaLineas(String linea) {
		listaLineas.add(linea);
	}
	
	public String popListaLineas() {
		return listaLineas.remove(0);
	}

	public void setListaItemsManifest(List<String> listaItemsManifest) {
		this.listaItemsManifest = listaItemsManifest;
	}

	public List<String> getListaItemsManifest() {
		return listaItemsManifest;
	}
	
	public void pushItemManifest(String valor) {
		if (!listaItemsManifest.contains(valor)) {
			listaItemsManifest.add(valor);
		}
	}
	
	public String popItemManifest() {
		return listaItemsManifest.remove(0);
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

	public boolean isBatch() {
		return isBatch;
	}

	public String getNombrePdf() {
		return nombrePdf;
	}

	public void setNombrePdf(String nombrePdf) {
		this.nombrePdf = nombrePdf;
	}

	public String getCoBDDocumentalGrupo() {
		return coBDDocumentalGrupo;
	}

	public void setCoBDDocumentalGrupo(String coBDDocumentalGrupo) {
		this.coBDDocumentalGrupo = coBDDocumentalGrupo;
	}

	public String getSubcarpetaTemporal() {
		return subcarpetaTemporal;
	}

	public void setSubcarpetaTemporal(String subcarpetaTemporal) {
		this.subcarpetaTemporal = subcarpetaTemporal;
	}

	public String getCoExpediente() {
		return coExpediente;
	}

	public void setCoExpediente(String coExpediente) {
		this.coExpediente = coExpediente;
	}

}
