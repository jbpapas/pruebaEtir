package es.dipucadiz.etir.comun.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dipucadiz.etir.comun.bo.LiquidatorioPeriodoBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ModeloVersionBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase ayudante para la obtenci&oacute;n de listas desplegables
 * @author SDS[janavarro]
 *
 */
@SuppressWarnings("unchecked")
public class ListasDesplegablesHelper implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	/*
	 * Métodos de obtenci&oacute;n de listas para las versiones no accesibles - ajax
	 */

	/**
	 * M&eacute;todo encargado de obtener una lista de conceptos a partir de un municipio
	 * compatible para un componente autocompletable
	 * @return List Lista de objetos de tipo KeyValue
	 * @throws GadirServiceException
	 */
	public List<KeyValue> getConceptosAutocompleterOptionsAjax(List<ConceptoDTO> lista)
		throws GadirServiceException
	{
		List<KeyValue> resultado = new ArrayList<KeyValue>();
		for(ConceptoDTO concepto : lista)
		{
			resultado.add(new KeyValue(concepto.getCoConcepto(), concepto.getNombre()));
		}

		return resultado;
	}	
	
	/**
	 * M&eacute;todo encargado de obtener una lista de modelos a partir de un concepto
	 * compatible para un componente autocompletable
	 * @return List Lista de objetos de tipo KeyValue
	 * @throws GadirServiceException
	 */
	public List<KeyValue> getModelosAutocompleterOptionsAjax(List<ModeloDTO> lista)
		throws GadirServiceException
	{
		List<KeyValue> resultado = new ArrayList<KeyValue>();
		for(ModeloDTO modelo : lista)
		{
			resultado.add(new KeyValue(modelo.getCoModelo(), modelo.getNombre()));
		}

		return resultado;
	}
	
	/**
	 * M&eacute;todo encargado de obtener una lista de versiones a partir de un modelo
	 * compatible para un componente autocompletable
	 * @return List Lista de objetos de tipo KeyValue
	 * @throws GadirServiceException
	 */
	public List<KeyValue> getVersionesAutocompleterOptionsAjax(List<ModeloVersionDTO> lista)
	throws GadirServiceException
	{
		List<KeyValue> resultado = new ArrayList<KeyValue>();
		for(ModeloVersionDTO version : lista)
		{
			resultado.add(new KeyValue(version.getId().getCoVersion(), version.getId().getCoVersion()));
		}

		return resultado;
	}
	
	/**
	 * M&eacute;todo encargado de obtener una lista de versiones a partir de un modelo
	 * compatible para un componente autocompletable
	 * @return List Lista de objetos de tipo KeyValue
	 * @throws GadirServiceException
	 */
	public List<KeyValue> getPeriodosAutocompleterOptionsAjax(List<String> lista)
		throws GadirServiceException
	{
		List<KeyValue> resultado = new ArrayList<KeyValue>();
		for(String periodo : lista)
		{
			resultado.add(new KeyValue(periodo, TablaGt.getCodigoDescripcion(TablaGt.TABLA_PERIODO, periodo).getValue()));
		}

		return resultado;
	}	
	
	/*
	 * M&eacute;todos de obtenci&oacute;n de listas gen&eacute;ricas
	 */	
	
	/**
	 * M&eacute;todo encargado de obtener una lista de conceptos a partir de un municipio
	 * @param String C&oacute;digo de municipio
	 * @param MunicipioBO Servicio de obtenci&oacute;n de los conceptos
	 * @return List Lista de objetos de tipo ConceptoDTO
	 * @throws GadirServiceException
	 */
	public List<ConceptoDTO> getListaConceptos(String codMunicipio, MunicipioBO servicio)
		throws GadirServiceException
	{
		List<ConceptoDTO> resultado = Collections.EMPTY_LIST;	
		if (!Utilidades.isEmpty(codMunicipio)) 
		{
			final MunicipioDTOId municipioFiltroId = new MunicipioDTOId();
			municipioFiltroId.setCoMunicipio(codMunicipio);
			if(!ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO.equals(codMunicipio)){
				municipioFiltroId.setCoProvincia(ValoresParametrosConfig.VALOR_PROVINCIA);
			}
			else{
				municipioFiltroId.setCoProvincia(ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA);
			}
			MunicipioDTO municipio = servicio.findById(municipioFiltroId);
			resultado = ControlTerritorial.getConceptosUsuario(municipio.getCodigoCompleto());
		}

		return resultado;
	}

	/**
	 * M&eacute;todo encargado de obtener una lista de modelos a partir de un concepto
	 * @param String C&oacute;digo de concepto
	 * @param ModeloBO Servicio de obtenci&oacute;n de los modelos
	 * @return List Lista de objetos de tipo ModeloDTO
	 * @throws GadirServiceException
	 */
	public List<ModeloDTO> getListaModelos(String codConcepto, ModeloBO servicio)
		throws GadirServiceException
	{
		List<ModeloDTO> resultado = Collections.EMPTY_LIST;
		if (!Utilidades.isEmpty(codConcepto))
		{
			resultado = servicio.findModelosByConcepto(codConcepto);
		}
		
		return resultado;
	}
	
	/**
	 * M&eacute;todo encargado de obtener una lista de modelos a partir de un concepto
	 * aplicando filtro por tipo y subtipo de modelo
	 * <br>Filtro aplicado: tipo=T y (subtipo=L o subtipo=F)
	 * @param String C&oacute;digo de concepto
	 * @param ModeloBO Servicio de obtenci&oacute;n de los modelos
	 * @return List Lista de objetos de tipo ModeloDTO
	 * @throws GadirServiceException
	 */	
	public List<ModeloDTO> getListaModelosFiltradaTLF(String codConcepto, ModeloBO servicio)
		throws GadirServiceException
	{
		List<ModeloDTO> lista = this.getListaModelos(codConcepto, servicio);
		List<ModeloDTO> resultado = new ArrayList<ModeloDTO>();
		
		for(ModeloDTO modelo : lista)
		{			
			if("T".equals(modelo.getTipo()) && ("L".equals(modelo.getSubtipo()) || "F".equals(modelo.getSubtipo()))){
				resultado.add(modelo);
			}

		}
		return resultado;
	}
	
	/**
	 * M&eacute;todo encargado de obtener una lista de modelos a partir de un concepto
	 * aplicando filtro por tipo y subtipo de modelo
	 * <br>Filtro aplicado: tipo=T y subtipo=N
	 * @param String C&oacute;digo de concepto
	 * @param ModeloBO Servicio de obtenci&oacute;n de los modelos
	 * @return List Lista de objetos de tipo ModeloDTO
	 * @throws GadirServiceException
	 */	
	public List<ModeloDTO> getListaModelosFiltradaTN(String codConcepto, ModeloBO servicio)
		throws GadirServiceException
	{
		List<ModeloDTO> lista = this.getListaModelos(codConcepto, servicio);
		List<ModeloDTO> resultado = new ArrayList<ModeloDTO>();
		
		for(ModeloDTO modelo : lista)
		{			
			if("T".equals(modelo.getTipo()) && "N".equals(modelo.getSubtipo())){
				resultado.add(modelo);
			}

		}
		return resultado;
	}
	
	/**
	 * M&eacute;todo encargado de obtener una lista de versiones a partir de un modelo
	 * @param String C&oacute;digo de modelo
	 * @param  ModeloVersionBO Servicio de obtenci&oacute;n de las versiones
	 * @return List Lista de objetos de tipo ModeloVersionDTO
	 * @throws GadirServiceException
	 */
	public List<ModeloVersionDTO> getListaVersiones(String codModelo, ModeloVersionBO servicio)
		throws GadirServiceException
	{
		List<ModeloVersionDTO> resultado = Collections.EMPTY_LIST;
		if (!Utilidades.isEmpty(codModelo))
		{
			resultado = servicio.findVersionesByModelo(codModelo);
		}		
		return resultado;
	}
	
	/**
	 * M&eacute;todo encargado de obtener una lista de periodos a partir de un concepto,
	 * un modelo y un ejercicio
	 * @param String C&oacute;digo de concepto
	 * @param String C&oacute;digo de modelo
	 * @param String Ejercicio
	 * @param  LiquidatorioPeriodoBO Servicio de obtenci&oacute;n de los periodos
	 * @return List Lista de objetos de tipo String
	 * @throws GadirServiceException
	 */
	public List<String> getListaPeriodos(String codMunicipio,
										 String codConcepto, 
										 String codModelo, 
										 String ejercicio,
										 LiquidatorioPeriodoBO servicio)
		throws GadirServiceException
	{
		List<String> resultado = Collections.EMPTY_LIST;
		if (!Utilidades.isEmpty(ejercicio) && 
			!Utilidades.isEmpty(codMunicipio) && 
			!Utilidades.isEmpty(codConcepto) && 
			!Utilidades.isEmpty(codModelo) &&
			Utilidades.esNumerico(ejercicio))
		{
			if(!ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO.equals(codMunicipio)){
				resultado = servicio.findByMunicipioConceptoModeloEjercicio(ValoresParametrosConfig.VALOR_PROVINCIA, 
																		codMunicipio, 
																		codConcepto, 
																		codModelo,
																		ejercicio);
			}
			else{
				resultado = servicio.findByMunicipioConceptoModeloEjercicio(ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA, 
						codMunicipio, 
						codConcepto, 
						codModelo,
						ejercicio);
			}
		}
		return resultado;
	}

}
