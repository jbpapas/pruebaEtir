package es.dipucadiz.etir.comun.vo;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpSession;

public class ActiveSessionVO implements Comparable<ActiveSessionVO>, Serializable {
	private static final long serialVersionUID = -8963011220724712627L;

	private String user;
	private Date date;
	private String coProceso;
	private String ip;
	private String host;
	private String ipServidor;
	private String sessionId;
	private HttpSession session;
	private boolean isUsuarioInvalidable;
	
	private String hora;
	private String programa;
	private String nombre;
	private String unidadAdministrativa;
	
	
	public ActiveSessionVO(HttpSession session) {
		this.session = session;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCoProceso() {
		return coProceso;
	}
	public void setCoProceso(String coProceso) {
		this.coProceso = coProceso;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public int compareTo(ActiveSessionVO other) {
		int result;
		if (this.getUser() == null) {
			result = -1;
		} else {
			result = this.getUser().compareTo(other.getUser());
			if (result == 0 && this.getIp() != null) {
				result = this.getIp().compareTo(other.getIp());
			}
		}
		return result;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public boolean isUsuarioInvalidable() {
		return isUsuarioInvalidable;
	}
	public void setUsuarioInvalidable(boolean isUsuarioInvalidable) {
		this.isUsuarioInvalidable = isUsuarioInvalidable;
	}
	public String getIpServidor() {
		return ipServidor;
	}
	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
