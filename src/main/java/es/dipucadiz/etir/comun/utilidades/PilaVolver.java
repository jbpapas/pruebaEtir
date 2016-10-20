package es.dipucadiz.etir.comun.utilidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PilaVolver implements Serializable {

	private static final long serialVersionUID = 7026559914126200929L;

	private List<ItemPilaVolver> pilaVolver = new ArrayList<ItemPilaVolver>();
	private List<Map<String, Object>> prepilaVolverParametros = new ArrayList<Map<String, Object>>();
	private List<String> prepilaVolverAction = new ArrayList<String>();

	public PilaVolver() {
		super();
	}

	public void pushPilaVolver(ItemPilaVolver itemPilaVolver) {
		if (pilaVolver == null) {
			pilaVolver = new ArrayList<ItemPilaVolver>();
		}
		pilaVolver.add(pilaVolver.size(), itemPilaVolver);
	}

	public ItemPilaVolver popPilaVolver() {
		ItemPilaVolver itemPilaVolver = null;
		if (pilaVolver != null && pilaVolver.size() > 0) {
			itemPilaVolver = pilaVolver.get(pilaVolver.size() - 1);
			pilaVolver.remove(pilaVolver.size() - 1);
		}
		return itemPilaVolver;
	}

	public ItemPilaVolver topPilaVolver() {
		return topPilaVolver(0);
	}

	public ItemPilaVolver topPilaVolver(int distanceFromTop) {
		ItemPilaVolver itemPilaVolver = null;
		if (pilaVolver != null && pilaVolver.size() > 0) {
			itemPilaVolver = pilaVolver.get(pilaVolver.size() - (1 + distanceFromTop));
		}
		return itemPilaVolver;
	}

	public void vaciaPilaVolver() {
		pilaVolver = new ArrayList<ItemPilaVolver>();
	}

	public void pushPrepilaVolverParametros(Map<String, Object> parametros) {
		if (prepilaVolverParametros == null) {
			prepilaVolverParametros = new ArrayList<Map<String, Object>>();
		}
		prepilaVolverParametros.add(prepilaVolverParametros.size(), new HashMap<String, Object>(parametros));
	}

	public void pushPrepilaVolverAction(String action) {
		if (prepilaVolverAction == null) {
			prepilaVolverAction = new ArrayList<String>();
		}
		prepilaVolverAction.add(prepilaVolverAction.size(), action);
	}

	public Map<String, Object> topPrepilaVolverParametros() {
		return topPrepilaVolverParametros(0);
	}

	public Map<String, Object> topPrepilaVolverParametros(int distanceFromTop) {
		Map<String, Object> parametros = null;
		if (prepilaVolverParametros != null && prepilaVolverParametros.size() > 0) {
			parametros = prepilaVolverParametros.get(prepilaVolverParametros.size() - (1 + distanceFromTop));
		}
		return parametros;
	}

	public String topPrepilaVolverAction() {
		return topPrepilaVolverAction(0);
	}

	public String topPrepilaVolverAction(int distanceFromTop) {
		String action = null;
		if (prepilaVolverAction != null && prepilaVolverAction.size() > 0) {
			action = prepilaVolverAction.get(prepilaVolverAction.size() - (1 + distanceFromTop));
		}
		return action;
	}

	public void vaciaPrepilaVolverParametros(String tabName) {
		prepilaVolverParametros = new ArrayList<Map<String, Object>>();
	}

	public void vaciaPrepilaVolverAction(String tabName) {
		prepilaVolverAction = new ArrayList<String>();
	}

	/*public List<ItemPilaVolver> getPilaVolver(String tabName) {
		return pilasVolver.get(tabName);
	}*/

	/*public void setPilaVolver(String tabName, List<ItemPilaVolver> pilaVolver) {
		pilasVolver.put(tabName, pilaVolver);
	}*/

	/*public List<Map<String, Object>> getPrepilaVolverParametros(String tabName) {
		return prepilasVolverParametros.get(tabName);
	}*/

	/*public void setPrepilaVolverParametros(String tabName, List<Map<String, Object>> prepilaVolverParametros) {
		prepilasVolverParametros.put(tabName, prepilaVolverParametros);
	}*/

	/*public List<String> getPrepilaVolverAction(String tabName) {
		return prepilasVolverAction.get(tabName);
	}*/

	/*public void setPrepilaVolverAction(String tabName, List<String> prepilaVolverAction) {
		prepilasVolverAction.put(tabName, prepilaVolverAction);
	}*/

	/*public Map<String, List<ItemPilaVolver>> getPilasVolver() {
		return pilasVolver;
	}
	
	public void setPilasVolver(Map<String, List<ItemPilaVolver>> pilasVolver) {
		this.pilasVolver = pilasVolver;
	}
	
	public Map<String, List<Map<String, Object>>> getPrepilasVolverParametros() {
		return prepilasVolverParametros;
	}
	
	public void setPrepilasVolverParametros(Map<String, List<Map<String, Object>>> prepilasVolverParametros) {
		this.prepilasVolverParametros = prepilasVolverParametros;
	}
	
	public Map<String, List<String>> getPrepilasVolverAction() {
		return prepilasVolverAction;
	}
	
	public void setPrepilasVolverAction(Map<String, List<String>> prepilasVolverAction) {
		this.prepilasVolverAction = prepilasVolverAction;
	}*/

}
