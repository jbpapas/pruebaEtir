package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;

/**
 * @author aolivares
 *
 */
public class SessionUtilVO implements Serializable {
	private static final long serialVersionUID = -3061826093465139900L;

	String ip;
	String host;
	String mac;

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
}
