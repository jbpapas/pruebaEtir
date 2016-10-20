package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

import es.dipucadiz.etir.comun.dto.CruceCondicionDTO;


public class CruceCondicionVO implements Serializable {
	private static final long serialVersionUID = -5473508762211186375L;
	
	public static final String ORIGEN = "O";
	public static final String CRUCE = "C";
	
	private String conector;
	private String tipoIzq;
	private String conjuntoIzq;
	private String nuCasillaIzq;
	private String coFuncionIzq;
	private String coArgumentoIzq;
	private String constanteIzq;
	private String operador;
	private String tipoDer;
	private String conjuntoDer;
	private String nuCasillaDer;
	private String coFuncionDer;
	private String coArgumentoDer;
	private String constanteDer;

	public CruceCondicionVO() {
	}
	
	public CruceCondicionVO(CruceCondicionDTO cruceCondicionDTO, int fila) {
		if (fila > 0) {
			conector = cruceCondicionDTO.getConector();
		}
		if (cruceCondicionDTO.isBoOrigenIzquierda()) {
			tipoIzq = cruceCondicionDTO.getTipoOrigen();
			conjuntoIzq = CruceCondicionVO.ORIGEN;
			if ("S".equals(tipoIzq)) {
				nuCasillaIzq = Integer.valueOf(cruceCondicionDTO.getValorOrigen()).toString();
			} else if ("D".equals(tipoIzq)) {
				coFuncionIzq = cruceCondicionDTO.getValorOrigen();
				coArgumentoIzq = Integer.valueOf(cruceCondicionDTO.getValorAdicionalOrigen()).toString();
			} else {
				conjuntoIzq = "";
				constanteIzq = cruceCondicionDTO.getValorOrigen();
			}
			operador = cruceCondicionDTO.getOperador();
			tipoDer = cruceCondicionDTO.getTipoCruce();
			conjuntoDer = CruceCondicionVO.CRUCE;
			if ("S".equals(tipoDer)) {
				nuCasillaDer = Integer.valueOf(cruceCondicionDTO.getValorCruce()).toString();
			} else if ("D".equals(tipoDer)) {
				coFuncionDer = cruceCondicionDTO.getValorCruce();
				coArgumentoDer = Integer.valueOf(cruceCondicionDTO.getValorAdicionalCruce()).toString();
			} else {
				conjuntoDer = "";
				constanteDer = cruceCondicionDTO.getValorCruce();
			}
		} else {
			tipoIzq = cruceCondicionDTO.getTipoCruce();
			conjuntoIzq = CruceCondicionVO.CRUCE;
			if ("S".equals(tipoIzq)) {
				nuCasillaIzq = Integer.valueOf(cruceCondicionDTO.getValorCruce()).toString();
			} else if ("D".equals(tipoIzq)) {
				coFuncionIzq = cruceCondicionDTO.getValorCruce();
				coArgumentoIzq = Integer.valueOf(cruceCondicionDTO.getValorAdicionalCruce()).toString();
			} else {
				conjuntoIzq = "";
				constanteIzq = cruceCondicionDTO.getValorCruce();
			}
			operador = invertirOperador(cruceCondicionDTO.getOperador());
			tipoDer = cruceCondicionDTO.getTipoOrigen();
			conjuntoDer = CruceCondicionVO.ORIGEN;
			if ("S".equals(tipoDer)) {
				nuCasillaDer = Integer.valueOf(cruceCondicionDTO.getValorOrigen()).toString();
			} else if ("D".equals(tipoDer)) {
				coFuncionDer = cruceCondicionDTO.getValorOrigen();
				coArgumentoDer = Integer.valueOf(cruceCondicionDTO.getValorAdicionalOrigen()).toString();
			} else {
				conjuntoDer = "";
				constanteDer = cruceCondicionDTO.getValorOrigen();
			}
		}
	}
	
	public CruceCondicionDTO toCruceCondicionDTO() {
		CruceCondicionDTO cruceCondicionDTO = new CruceCondicionDTO();
		if (CruceCondicionVO.ORIGEN.equals(conjuntoIzq)) {
			cruceCondicionDTO.setTipoOrigen(tipoIzq);
			if ("S".equals(tipoIzq)) {
				cruceCondicionDTO.setValorOrigen(nuCasillaIzq);
			} else if ("D".equals(tipoIzq)) {
				cruceCondicionDTO.setValorOrigen(coFuncionIzq);
				cruceCondicionDTO.setValorAdicionalOrigen(coArgumentoIzq);
			} else if ("K".equals(tipoIzq)) {
				cruceCondicionDTO.setValorOrigen(constanteIzq);
			}
			cruceCondicionDTO.setOperador(operador);
			cruceCondicionDTO.setTipoCruce(tipoDer);
			if ("S".equals(tipoDer)) {
				cruceCondicionDTO.setValorCruce(nuCasillaDer);
			} else if ("D".equals(tipoDer)) {
				cruceCondicionDTO.setValorCruce(coFuncionDer);
				cruceCondicionDTO.setValorAdicionalCruce(coArgumentoDer);
			} else if ("K".equals(tipoDer)) {
				cruceCondicionDTO.setValorCruce(constanteDer);
			}
			cruceCondicionDTO.setBoOrigenIzquierda(true);
		} else {
			cruceCondicionDTO.setTipoOrigen(tipoDer);
			if ("S".equals(tipoDer)) {
				cruceCondicionDTO.setValorOrigen(nuCasillaDer);
			} else if ("D".equals(tipoDer)) {
				cruceCondicionDTO.setValorOrigen(coFuncionDer);
				cruceCondicionDTO.setValorAdicionalOrigen(coArgumentoDer);
			} else if ("K".equals(tipoDer)) {
				cruceCondicionDTO.setValorOrigen(constanteDer);
			}
			cruceCondicionDTO.setOperador(invertirOperador(operador));
			cruceCondicionDTO.setTipoCruce(tipoIzq);
			if ("S".equals(tipoIzq)) {
				cruceCondicionDTO.setValorCruce(nuCasillaIzq);
			} else if ("D".equals(tipoIzq)) {
				cruceCondicionDTO.setValorCruce(coFuncionIzq);
				cruceCondicionDTO.setValorAdicionalCruce(coArgumentoIzq);
			} else if ("K".equals(tipoIzq)) {
				cruceCondicionDTO.setValorCruce(constanteIzq);
			}
			cruceCondicionDTO.setBoOrigenIzquierda(false);
		}
		cruceCondicionDTO.setConector(conector);
		return cruceCondicionDTO;
	}

	private String invertirOperador(String operador) {
		String result;
		if ("<".equals(operador)) result = ">=";
		else if ("<=".equals(operador)) result = ">";
		else if (">".equals(operador)) result = "<=";
		else if (">=".equals(operador)) result = "<";
		else result = operador;
		return result;
	}

	public String getConector() {
		return conector;
	}

	public void setConector(String conector) {
		this.conector = conector;
	}

	public String getTipoIzq() {
		return tipoIzq;
	}

	public void setTipoIzq(String tipoIzq) {
		this.tipoIzq = tipoIzq;
	}

	public String getConjuntoIzq() {
		return conjuntoIzq;
	}

	public void setConjuntoIzq(String conjuntoIzq) {
		this.conjuntoIzq = conjuntoIzq;
	}

	public String getNuCasillaIzq() {
		return nuCasillaIzq;
	}

	public void setNuCasillaIzq(String nuCasillaIzq) {
		this.nuCasillaIzq = nuCasillaIzq;
	}

	public String getCoFuncionIzq() {
		return coFuncionIzq;
	}

	public void setCoFuncionIzq(String coFuncionIzq) {
		this.coFuncionIzq = coFuncionIzq;
	}

	public String getCoArgumentoIzq() {
		return coArgumentoIzq;
	}

	public void setCoArgumentoIzq(String coArgumentoIzq) {
		this.coArgumentoIzq = coArgumentoIzq;
	}

	public String getConstanteIzq() {
		return constanteIzq;
	}

	public void setConstanteIzq(String constanteIzq) {
		this.constanteIzq = constanteIzq;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getTipoDer() {
		return tipoDer;
	}

	public void setTipoDer(String tipoDer) {
		this.tipoDer = tipoDer;
	}

	public String getConjuntoDer() {
		return conjuntoDer;
	}

	public void setConjuntoDer(String conjuntoDer) {
		this.conjuntoDer = conjuntoDer;
	}

	public String getNuCasillaDer() {
		return nuCasillaDer;
	}

	public void setNuCasillaDer(String nuCasillaDer) {
		this.nuCasillaDer = nuCasillaDer;
	}

	public String getCoFuncionDer() {
		return coFuncionDer;
	}

	public void setCoFuncionDer(String coFuncionDer) {
		this.coFuncionDer = coFuncionDer;
	}

	public String getCoArgumentoDer() {
		return coArgumentoDer;
	}

	public void setCoArgumentoDer(String coArgumentoDer) {
		this.coArgumentoDer = coArgumentoDer;
	}

	public String getConstanteDer() {
		return constanteDer;
	}

	public void setConstanteDer(String constanteDer) {
		this.constanteDer = constanteDer;
	}

	
	
}
