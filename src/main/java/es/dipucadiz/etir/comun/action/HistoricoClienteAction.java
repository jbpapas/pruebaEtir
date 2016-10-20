package es.dipucadiz.etir.comun.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.HClienteBO;
import es.dipucadiz.etir.comun.bo.HDomicilioBO;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.HClienteDTO;
import es.dipucadiz.etir.comun.dto.HDomicilioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DomicilioUtil;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.HistoricoGenericoVO;

public class HistoricoClienteAction extends HistoricoGenericoAction {

	private static final long serialVersionUID = 618756749303505521L;

	private HClienteBO hClienteBO;
	private HDomicilioBO hDomicilioBO;
	
	
	public String execute() throws GadirServiceException {
 	
		return super.execute();
	}

	@Override
	protected void cargarTablaHistorico() throws GadirServiceException{
		
		try{
			
			// Titulos
			String titulosProperty = getText(historicoNombre + ".titulos");
			String[] titulosArray = titulosProperty.split("\\|");
			titulos = new ArrayList<String>();
			for (int i=0; i<titulosArray.length; i++) {
				if (Utilidades.isNotEmpty(titulosArray[i])) {
					titulos.add(titulosArray[i]);
				}
			}
			
			columnas = titulos.size();
			
			AcmUsuarioDTO todos_usuarios = new AcmUsuarioDTO();
			todos_usuarios.setCoAcmUsuario("Todos los usuarios");
			todos_usuarios.setRowid("Todos los usuarios");
			
			listaUsuarios = new ArrayList<AcmUsuarioDTO>();
			
			listaUsuarios.add(todos_usuarios);
			
			listaUsuarios.addAll(acmUsuarioBO.findAll("coAcmUsuario", 1));
			
			if (Utilidades.isEmpty(usuarioRowid) || usuario.equals(listaUsuarios.get(0).getCoAcmUsuario())){
				usuario = null;
				usuarioRowid = null;
			}
			else
			{
				AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findByRowid(usuarioRowid);
				usuario = acmUsuarioDTO.getCoAcmUsuario();
			}	
			
			
			int filasTotal = 0;
			
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmSS");
			
			String sql = "select razon_social, fh_actualizacion, h_tipo_movimiento, co_usuario_actualizacion, 'C' " +
				"from ga_h_cliente t where t.id in(select c.idC from (select razon_social, min(id) idC " +
				"from ga_h_cliente where co_cliente = " + historicoBusqueda;
			
			String sqlWhere = "";
			
			if (Utilidades.isNotEmpty(usuario)) {
				sqlWhere += " AND CO_USUARIO_ACTUALIZACION='" + usuario + "'";
			}
			if (Utilidades.isNotEmpty(fechaDesde)) {
				StringBuilder fecha_d = new StringBuilder( dateformat.format( fechaDesde ) );
				sqlWhere += " AND FH_ACTUALIZACION >=to_timestamp('" + fecha_d + "', 'YYYYMMDDHH24MISS')";
			}
			if (Utilidades.isNotEmpty(fechaHasta)) {
				StringBuilder fecha_h = new StringBuilder( dateformat.format( fechaHasta ) );
				sqlWhere += " AND FH_ACTUALIZACION <=to_timestamp('" + fecha_h + "', 'YYYYMMDDHH24MISS')";
			}
			
			if(Utilidades.isNotEmpty(movimientoKey)) {
				sqlWhere += " AND H_TIPO_MOVIMIENTO ='" + movimientoKey + "'";		
			}
			
			sql += sqlWhere;
			sql += " group by razon_social) c)";
	
			List<Object[]> listaCambios = (List<Object[]>)hClienteBO.ejecutaQuerySelect(sql);
			
			sql = "select co_domicilio, fh_actualizacion, h_tipo_movimiento, co_usuario_actualizacion, 'D' " +
			"from ga_h_domicilio t where t.id in(select d.idD from (select co_domicilio, min(id) idD " +
			"from ga_h_domicilio where co_cliente = " + historicoBusqueda;
			
			sql += sqlWhere;
			sql += " group by co_domicilio) d)"; 
			
			listaCambios.addAll((List<Object[]>)hDomicilioBO.ejecutaQuerySelect(sql));
			
			filasTotal = listaCambios.size();
			
			Collections.sort(listaCambios, 
					new Comparator<Object[]>() {
						public int compare(Object[] c1, Object[] c2) {
							int result = 0;
							Date c1Valor = (Date) c1[1];
							Date c2Valor = (Date) c2[1];
							if (c1Valor.before(c2Valor)) {
								result = 1;
							} else if (c1Valor.after(c2Valor)) {
								result = -1;
							} else {
								result = 0;
							}
							return result;
						}
					}
			);
			
			List<HistoricoGenericoVO> resultado = new ArrayList<HistoricoGenericoVO>();
			
			for(Object[] objeto: listaCambios){
				HistoricoGenericoVO historicoGenericoVO = new HistoricoGenericoVO();
				
				historicoGenericoVO.setUsuario((String)objeto[3]);
				historicoGenericoVO.setMovimiento((String)objeto[2]);
				Date fhActualizacion = (Date)(objeto[1]);
				historicoGenericoVO.setFecha(Utilidades.dateToDDMMYYYY(fhActualizacion));
				historicoGenericoVO.setHora(Utilidades.dateToHHMM(fhActualizacion));
				
				List<String> columnas = new ArrayList<String>();
					
				if(objeto[4].toString().equals("C")){
					columnas.add("Razón social");
					columnas.add((String)objeto[0]);
				}
				else{
					columnas.add("Domicilio");
					columnas.add(DomicilioUtil.getDescripcionDomicilio(Long.parseLong(objeto[0].toString()), true));
					
				}
				historicoGenericoVO.setColumnas(columnas);
				
				resultado.add(historicoGenericoVO);
			}
			
			if(!informe){
				if(resultado.size()>(page-1)*porPagina)
					historicoTabla = resultado.subList((page-1)*porPagina, resultado.size()<page*porPagina?resultado.size():page*porPagina);
				else
					historicoTabla = new ArrayList<HistoricoGenericoVO>();
			}
			else
				historicoTabla = resultado;
			
			for(int i=0; i<historicoTabla.size();i++)
			{
				String mov = historicoTabla.get(i).getMovimiento();
				historicoTabla.get(i).setMovimiento(TablaGt.getValor(TablaGtConstants.TABLA_MOVIMIENTOS_CENSO, mov, TablaGt.COLUMNA_DESCRIPCION));
			}
			
			if(!informe)
				listadoHistorico = new GadirPaginatedList(historicoTabla, filasTotal, porPagina, page, "historicos", sort, dir);
			else
				listadoHistorico = new GadirPaginatedList(historicoTabla, filasTotal, filasTotal, 1, "historicos", sort, dir);
			
		}catch(Exception e){
			addActionError("Error en la generación del histórico.");
		}
		
		if(usuario == null){
			usuario = listaUsuarios.get(0).getCoAcmUsuario();
			usuarioRowid = listaUsuarios.get(0).getCoAcmUsuario();
		}
		
	}
	
	public HClienteBO gethClienteBO() {
		return hClienteBO;
	}

	public void sethClienteBO(HClienteBO hClienteBO) {
		this.hClienteBO = hClienteBO;
	}

	public HDomicilioBO gethDomicilioBO() {
		return hDomicilioBO;
	}

	public void sethDomicilioBO(HDomicilioBO hDomicilioBO) {
		this.hDomicilioBO = hDomicilioBO;
	}
	
	
}
