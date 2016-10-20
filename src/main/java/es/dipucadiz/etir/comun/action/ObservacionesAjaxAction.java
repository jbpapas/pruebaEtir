package es.dipucadiz.etir.comun.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.lob.ClobImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dipucadiz.etir.comun.bo.ObservacionesBO;
import es.dipucadiz.etir.comun.bo.ObservacionesGrupoBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.ObservacionesDTO;
import es.dipucadiz.etir.comun.dto.ObservacionesGrupoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ObservacionesAjaxAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = 8742167619700161207L;

	private ObservacionesBO observacionesBO;
	private ObservacionesGrupoBO observacionesGrupoBO;
	private String coObservacionesGrupo;
	private String nuevasObservaciones;
	private String obsTabla;
	private String obsCampos;
	private String obsId;

	public String execute() throws GadirServiceException, IOException {
		// Obtener coObservacionesGrupo
		String[] campos = obsCampos.split("\\|");
		String[] valores = obsId.split("\\|");
		String select = "SELECT CO_OBSERVACIONES_GRUPO FROM " + obsTabla + " WHERE "; 
		for (int i=0; i<campos.length; i++) {
			if (i>0) select += " AND ";
			select += campos[i]+"='"+valores[i]+"'";
		}
		@SuppressWarnings("unchecked")
		List<Object> objects = (List<Object>) observacionesBO.ejecutaQuerySelect(select);
		if (objects.get(0) == null) {
			coObservacionesGrupo = null;
		} else {
			coObservacionesGrupo = Long.toString(((BigDecimal)objects.get(0)).longValue());
		}
		
		
		if (Utilidades.isNotEmpty(nuevasObservaciones)) {
			// Guardar observaciones
			String coUsuarioActualizacion = DatosSesion.getLogin();
			Date fhActualizacion = Utilidades.getDateActual();
			if (isSinGrupo()) {
				// Crear grupo
				ObservacionesGrupoDTO observacionesGrupoDTO = new ObservacionesGrupoDTO();
				observacionesGrupoDTO.setCoUsuarioActualizacion(coUsuarioActualizacion);
				observacionesGrupoDTO.setFhActualizacion(fhActualizacion);
				observacionesGrupoBO.save(observacionesGrupoDTO);
				coObservacionesGrupo = Long.toString(observacionesGrupoDTO.getCoObservacionesGrupo());
				
				// Asignar grupo a la tabla
				String sql = "UPDATE " + obsTabla + " SET co_observaciones_grupo=" + coObservacionesGrupo + " WHERE ";
				if (campos.length == 0 || campos.length != valores.length) {
					throw new GadirServiceException("Error al poner grupo en la tabla afectada: tabla="+obsTabla+", campos="+obsCampos+", id="+obsId);
				}
				for (int i=0; i<campos.length; i++) {
					if (i>0) sql += " AND ";
					sql += campos[i]+"='"+valores[i]+"'";
				}
				observacionesBO.ejecutaQueryUpdate(sql);
			}
			
			// Las observaciones
			ObservacionesDTO observacionesDTO = new ObservacionesDTO();
			observacionesDTO.setCoUsuarioAlta(coUsuarioActualizacion);
			observacionesDTO.setFhAlta(fhActualizacion);
			observacionesDTO.setObservaciones(new ClobImpl(nuevasObservaciones));
			observacionesDTO.setObservacionesGrupoDTO(new ObservacionesGrupoDTO(Long.valueOf(coObservacionesGrupo)));
			observacionesDTO.setCoUsuarioActualizacion(coUsuarioActualizacion);
			observacionesDTO.setFhActualizacion(fhActualizacion);
			observacionesBO.save(observacionesDTO);
		}

		JSONObject obs = new JSONObject();
		String error = null;
		if (!isSinGrupo()) {
			try {
				obs.put("grupo", coObservacionesGrupo);
				List<ObservacionesDTO> observacionesDTOs = observacionesBO.findFiltered("observacionesGrupoDTO.coObservacionesGrupo", Long.valueOf(coObservacionesGrupo), "fhAlta", DAOConstant.DESC_ORDER);
				JSONArray arr = new JSONArray();
				for (ObservacionesDTO observacionesDTO : observacionesDTOs) {
					JSONObject jo = new JSONObject();
					jo.put("usuario", observacionesDTO.getCoUsuarioAlta());
					jo.put("fh", Utilidades.dateToDDMMYYYYHHMMSS(observacionesDTO.getFhAlta()));
					Clob clob = observacionesDTO.getObservaciones();

//					// CP1252
//					// UTF-8
//					// ISO-8859-1
//					System.out.println(new String(clob.getSubString(1, (int)clob.length()).getBytes(Charset.forName("UTF-8")), "UTF-8"));
//					
//					// Probar.
//					BufferedReader br = new BufferedReader(new InputStreamReader(clob.getAsciiStream(), Charset.forName("UTF-8")));
//					String lig;
//					int n = 0;
//					StringBuilder result = new StringBuilder();
//			        while ((lig = br.readLine()) != null) {
//			            if (n > 0) {
//			                result.append("\n");
//			            }
//			            result.append(lig);
//			            n++;
//			        }
			        jo.put("texto", clob.getSubString(1, (int)clob.length()).replace("\n\r", "<br>").replace("\n","<br>"));
					
					arr.put(jo);
				}
				obs.put("lista", arr);
			} catch (JSONException e) {
				LOG.error(e.getMessage(), e);
				error = e.getMessage();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
				error = e.getMessage();
			}
		}

		if (Utilidades.isNotEmpty(error)) {
			try {
				obs.put("error", error);
			} catch (JSONException e) {
				LOG.error(e.getMessage(), e);
				throw new GadirServiceException(e.getMessage(), e);
			}
		}
		
		ajaxData=obs.toString();

		return AJAX_DATA;
	}

	private boolean isSinGrupo() {
		return Utilidades.isEmpty(coObservacionesGrupo) || "0".equals(coObservacionesGrupo);
	}
	
	
	
	
	
	public ObservacionesBO getObservacionesBO() {
		return observacionesBO;
	}

	public void setObservacionesBO(ObservacionesBO observacionesBO) {
		this.observacionesBO = observacionesBO;
	}

	public String getCoObservacionesGrupo() {
		return coObservacionesGrupo;
	}

	public void setCoObservacionesGrupo(String coObservacionesGrupo) {
		this.coObservacionesGrupo = coObservacionesGrupo;
	}

	public String getNuevasObservaciones() {
		return nuevasObservaciones;
	}

	public void setNuevasObservaciones(String nuevasObservaciones) {
		this.nuevasObservaciones = nuevasObservaciones;
	}

	public ObservacionesGrupoBO getObservacionesGrupoBO() {
		return observacionesGrupoBO;
	}

	public void setObservacionesGrupoBO(ObservacionesGrupoBO observacionesGrupoBO) {
		this.observacionesGrupoBO = observacionesGrupoBO;
	}

	public String getObsTabla() {
		return obsTabla;
	}

	public void setObsTabla(String obsTabla) {
		this.obsTabla = obsTabla;
	}

	public String getObsCampos() {
		return obsCampos;
	}

	public void setObsCampos(String obsCampos) {
		this.obsCampos = obsCampos;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	

}
