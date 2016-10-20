package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.vo.SessionUtilVO;

public class SessionUtil {

	static private final Log LOG = LogFactory.getLog(SessionUtil.class);
	static final private String LINUX = "linux";
	static final private String OBJETO_SESION_IP_HOST = "objetoSesionIpHost";
	static private String tipoSistema = null;

	public static SessionUtilVO getIpHost(HttpServletRequest servletRequest) {
		if (tipoSistema == null) {
			tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		}

		final boolean haySesion = Utilidades.isNotEmpty(DatosSesion.getLogin());

		SessionUtilVO result = new SessionUtilVO();
		if (haySesion && DatosSesion.existeObjetoUnico(OBJETO_SESION_IP_HOST)) {
			result = (SessionUtilVO) DatosSesion.getObjetoUnico(OBJETO_SESION_IP_HOST);
		} else {
			String ip = servletRequest.getHeader("X-FORWARDED-FOR");
			if (Utilidades.isEmpty(ip)) { // Si vacío, no ha accedido mediante balanceador
				ip = servletRequest.getRemoteAddr();
			}

			List<String> hosts = new ArrayList<String>();
			String host = "";
			String mac = null;

			if (LINUX.equals(tipoSistema)) {
				try {
					Process p = Runtime.getRuntime().exec("nmblookup -A " + ip);
					p.waitFor();
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = "";
					while ((line = reader.readLine()) != null) {
						//				    String[] lines = {
						//				    		"Looking up status of 10.12.101.68",
						//				    		"        DIPUCADIZ       <00> - <GROUP> M <ACTIVE>",
						//				    		"        PC10001790      <00> -         M <ACTIVE>",
						//				    		"        PC10001790      <20> -         M <ACTIVE>",
						//				    		"        DIPUCADIZ       <1e> - <GROUP> M <ACTIVE>",
						//				    		"",
						//				    		"        MAC Address = C8-1F-66-24-15-53"
						//				    };
						//				    for (String line : lines) {
						if (Utilidades.isNotEmpty(line)) {
							if (line.contains("<ACTIVE>")) {
								String[] parts = line.split("<");
								if (parts.length > 0) {
									String tmp = parts[0].trim();
									if (!hosts.contains(tmp)) {
										hosts.add(tmp);
									}
								}
							} else if (line.contains("MAC Address")) {
								mac = line.trim();
							}
						}
					}
				} catch (IOException e) {
					LOG.error("Error (IOException) interpretando comando: nmblookup -A " + ip, e);
				} catch (InterruptedException e) {
					LOG.error("Error (InterruptedException) interpretando comando: nmblookup -A " + ip, e);
				} catch (Exception e) {
					LOG.error("Error (Exception) interpretando comando: nmblookup -A " + ip, e);
				}

				for (String tmp : hosts) {
					if (Utilidades.isNotEmpty(host)) {
						host = host + ", ";
					}
					host = host + tmp;
				}
			}

			if (host.length() > 4000) {
				host = host.substring(0, 4000);
			}
			result.setIp(ip);
			result.setHost(host);
			result.setMac(mac);
			if (haySesion) {
				DatosSesion.setObjetoUnico(OBJETO_SESION_IP_HOST, result);
			}
		}

		return result;
	}

	public static boolean permitidoMostrarDetalle(String login) {
		return login.equals("jronnols")|| login.equals("jescobar") || login.equals("jescobar2") || login.equals("fsainz") || login.equals("agarciap") || login.equals("fvazquezc")
				|| login.equals("gpenam");
	}

}
