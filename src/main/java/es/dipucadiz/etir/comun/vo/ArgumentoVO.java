package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;


public class ArgumentoVO extends CruceDTO implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private boolean seleccionado;
	
	public String getOrigen(){
		
		ModeloVersionDTO modeloVersion;
		ModeloDTO modelo;
		ModeloVersionDTOId id;
		String cadena="";
		String temp=this.getConceptoDTOByCoConceptoOrigen()!=null?this.getConceptoDTOByCoConceptoOrigen().getNombre():null;
		if(temp!=null && !"".equals(temp)){
			cadena += temp;
		}
		 modeloVersion=this.getModeloVersionDTOByVermodArgcru();
	
		if(modeloVersion!=null){
			modelo=modeloVersion.getModeloDTO();
			temp=modelo!=null?modelo.getNombre():null;
		
			if(temp!=null && !"".equals(temp)){
				cadena +="-"+ temp;
			}
			id=modeloVersion.getId();
			temp=id!=null?id.getCoVersion():null;
			if(temp!=null && !"".equals(temp)){
				cadena +="-"+ temp;
			}
			
		}
		
		
		
		temp= this.getEjercicioOrigen()!=null?this.getEjercicioOrigen().toString():null;
		
		if(temp!=null && !"-".equals(temp)){
			cadena +="-"+ temp;
		}
		
		temp=this.getPeriodoOrigen();
		if(temp!=null && !"".equals(temp)){
			cadena +="-"+ temp;
		}
		if (cadena== null || "".equals(cadena)){
			cadena = " ";
			}
		return cadena;
	}
	
	
	public String getCruce(){
		
		ModeloVersionDTO modeloVersion;
		ModeloDTO modelo;
		ModeloVersionDTOId id;
		String cadena="";
		String temp=this.getConceptoDTOByCoConceptoCruce()!=null?this.getConceptoDTOByCoConceptoCruce().getNombre():null;
		if(temp!=null && !"".equals(temp)){
			cadena += temp;
		}
		 modeloVersion=this.getModeloVersionDTOByVermodArgcruCruce();
	
		if(modeloVersion!=null){
			modelo=modeloVersion.getModeloDTO();
			temp=modelo!=null?modelo.getNombre():null;
		
			if(temp!=null && !"".equals(temp)){
				cadena +="-"+ temp;
			}
			id=modeloVersion.getId();
			temp=id!=null?id.getCoVersion():null;
			if(temp!=null && !"".equals(temp)){
				cadena +="-"+ temp;
			}
			
		}
		
		
		
		temp= this.getEjercicioCruce()!=null?this.getEjercicioCruce().toString():null;
		
		if(temp!=null && !"-".equals(temp)){
			cadena +="-"+ temp;
		}
		
		temp=this.getPeriodoCruce();
		if(temp!=null && !"".equals(temp)){
			cadena +="-"+ temp;
		}
		
		if (cadena== null || "".equals(cadena)){
			cadena = " ";
			}
		
		return cadena;
	}


	public boolean isSeleccionado() {
		return seleccionado;
	}


	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	
	
	
}
