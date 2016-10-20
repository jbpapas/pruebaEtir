package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MCHojaVO implements Serializable {
	private static final long serialVersionUID = 4196128717092786415L;

	private short nuHoja;

	private Map<Short,MCCasillaVO> casillas;

	
	public MCHojaVO(short nuHoja) {
		super();
		this.nuHoja=nuHoja;
		casillas = new HashMap<Short,MCCasillaVO>();			
	}


	public short getNuHoja() {
		return nuHoja;
	}


	public void setNuHoja(short nuHoja) {
		this.nuHoja = nuHoja;
	}


	public Map<Short, MCCasillaVO> getCasillas() {
		return casillas;
	}


	public void setCasillas(Map<Short, MCCasillaVO> casillas) {
		this.casillas = casillas;
	}

	
	
}
