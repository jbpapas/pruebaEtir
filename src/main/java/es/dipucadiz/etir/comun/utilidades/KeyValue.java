package es.dipucadiz.etir.comun.utilidades;

import java.io.Serializable;


public class KeyValue implements Serializable, Comparable<KeyValue> {
	private static final long serialVersionUID = -2430405363775067645L;

	private String key;
	private String value;
	
	
	public KeyValue() {
		key = "";
		value = "";
	}
	
	public KeyValue(final String key, final String value) {
		this.key = key;
		this.value = value;
	}
	
	public KeyValue(final String key) {
		this.key = key;
		this.value = "";
	}

	public String getCodigoDescripcion() {
		return key + " - " + value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object key2) {
		return key.equals(((KeyValue)key2).getKey());
	}
	
	@Override
	public int hashCode() {
		return key.hashCode() ^ value.hashCode();
	}

	public int compareTo(KeyValue o) {
		return key.compareTo(o.getKey());
	}
	
}
