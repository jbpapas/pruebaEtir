package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.FormulaArgumentoBO;
import es.dipucadiz.etir.comun.bo.FormulaPasoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.FormulaDTO;
import es.dipucadiz.etir.comun.dto.FormulaPasoDTO;
import es.dipucadiz.etir.comun.dto.FormulaPasoDTOId;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.FormulaPasoVO;

public class FormulaPasoBOImpl extends
AbstractGenericBOImpl<FormulaPasoDTO, FormulaPasoDTOId>
implements FormulaPasoBO {
	
	private FormulaArgumentoBO formulaArgumentoBO;
	
	/**
	 * Atributo que almacena el dao asociado a {@link FuncionArgumentoDTO}.
	 */
	private DAOBase<FormulaPasoDTO, FormulaPasoDTOId> formulaPasoDao;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<FormulaPasoDTO, FormulaPasoDTOId> getDao() {
		return this.getFormulaPasoDao();
	}

	public DAOBase<FormulaPasoDTO, FormulaPasoDTOId> getFormulaPasoDao() {
		return formulaPasoDao;
	}

	public void setFormulaPasoDao(DAOBase<FormulaPasoDTO, FormulaPasoDTOId> formulaPasoDao) {
		this.formulaPasoDao = formulaPasoDao;
	}
	
	
	public FormulaPasoDTO findByIdFecthFuncion(FormulaPasoDTOId id) throws GadirServiceException{
			final DetachedCriteria criteria = DetachedCriteria.forClass(FormulaPasoDTO.class);
		
		criteria.add(Restrictions.eq("id",id));
		criteria.setFetchMode("funcionDTO", FetchMode.JOIN);
		List<FormulaPasoDTO> lista=this.formulaPasoDao.findByCriteria(criteria);
		if (lista!= null && lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
	}
	
	
	
	
	public byte nuevoID(String coFormula) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(FormulaPasoDTO.class);
		criteria.setProjection(Projections.max("id.coPaso"));
		criteria.add(Restrictions.eq("id.coFormula",coFormula));
		List lista=this.formulaPasoDao.findByCriteria(criteria);
		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			Byte n=(Byte) lista.get(0);
			n++;
			return n;
		}
		  
		return 0;
		
		
		
	}
	
	public Integer nuevoOrden(String coFormula) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(FormulaPasoDTO.class);
		criteria.setProjection(Projections.max("orden"));
		criteria.add(Restrictions.eq("id.coFormula",coFormula));
		List lista=this.formulaPasoDao.findByCriteria(criteria);
		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			Integer n=(Integer) lista.get(0);
			n++;
			return n;
		}
		  
		return 1;
		
		
		
	}
	
	public void auditorias(FormulaPasoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public List<FormulaPasoDTO> getFormulaPasoByFormula(String coFormula){
		/*DetachedCriteria criteria = DetachedCriteria.forClass(FormulaPasoDTO.class);
		criteria.add(Restrictions.eq("id.coFormula", coFormula));
		criteria.addOrder(Order.asc("id.coPaso"));*/
		List<FormulaPasoDTO> formulas= this.getDao().findFiltered("id.coFormula", coFormula, "orden", DAOConstant.ASC_ORDER);
		return formulas;
	}
	
	private List<FormulaPasoDTO> getFormulaPasoByFormulaAndPaso(List<Byte> coPasos, String coFormula){
		DetachedCriteria criteria = DetachedCriteria.forClass(FormulaPasoDTO.class);
		criteria.add(Restrictions.eq("id.coFormula", coFormula));
		criteria.add(Restrictions.not(Restrictions.in("id.coPaso", coPasos)));
		List<FormulaPasoDTO> formulas= this.getDao().findByCriteria(criteria);
		return formulas;
	}
	
	public void copiarPaso(FormulaDTO formulaOrigen, FormulaDTO formulaACopiar) throws GadirServiceException{
		List<FormulaPasoDTO> formulasPasoOrigen = getFormulaPasoByFormula(formulaOrigen.getCoFormula());
		formulaACopiar.setBoCorrecto(false);
		
		//Eliminamos todos los argmentos de la formula a copiar.
		List<FormulaArgumentoDTO> argumentosCopia = formulaArgumentoBO.findFiltered("id.coFormula", formulaACopiar.getCoFormula());
		if(argumentosCopia.size() > 0){
			for(FormulaArgumentoDTO fo: argumentosCopia){
				formulaArgumentoBO.delete(fo.getId());
			}
		}
		
		
		//Eliminamos todos los pasos de la formula a copiar
		List<FormulaPasoDTO> formulasPasoCopia = getFormulaPasoByFormula(formulaACopiar.getCoFormula());
		if(formulasPasoCopia.size() > 0){
			for(FormulaPasoDTO fo: formulasPasoCopia){
				delete(fo.getId());
			}
		}
		
		Iterator<FormulaPasoDTO> it = formulasPasoOrigen.iterator();
		
		while(it.hasNext()){
			FormulaPasoDTO formulaPaso = it.next();
			FormulaPasoDTO formulaCopia = new FormulaPasoDTO();
			formulaCopia.setId(new FormulaPasoDTOId(formulaACopiar.getCoFormula(),formulaPaso.getId().getCoPaso()));
			formulaCopia.setFuncionDTO(formulaPaso.getFuncionDTO());
			List<FormulaArgumentoDTO> argumentosOrigen = formulaArgumentoBO.getFormulaArgumentoByFormula(formulaPaso.getId().getCoFormula(), formulaPaso.getId().getCoPaso());
			save(formulaCopia);
			
			for(FormulaArgumentoDTO f : argumentosOrigen){
				FormulaArgumentoDTO formu = new FormulaArgumentoDTO();
				formu.setId(new FormulaArgumentoDTOId());
				formu.getId().setCoArgumento(f.getId().getCoArgumento());
				formu.getId().setCoFormula(formulaCopia.getId().getCoFormula());
				formu.getId().setCoPaso(formulaCopia.getId().getCoPaso());
				formu.setPosFin(f.getPosFin());
				formu.setTipo(f.getTipo());
				formu.setValor(f.getValor());
				formulaArgumentoBO.save(formu);
			}
		}
		
	}
	
	public List<FormulaPasoVO> obtenerListado(FormulaDTO formula){
		List<FormulaPasoVO> listado = new ArrayList<FormulaPasoVO>();
		List<FormulaPasoDTO> formulaPasos = getFormulaPasoByFormula(formula.getCoFormula());
		Iterator<FormulaPasoDTO> it = formulaPasos.iterator();
		while(it.hasNext()){
			FormulaPasoDTO formu = it.next();
			Hibernate.initialize(formu.getFormulaArgumentoDTOs());
			List<FormulaArgumentoDTO> argumentos = this.getFormulaArgumentoBO().getFormulaArgumentoByFormula(formu.getId().getCoFormula(), formu.getId().getCoPaso());
			FormulaPasoVO formulaVo = new FormulaPasoVO();
			formulaVo.setRowid(formu.getRowid());
			formulaVo.setPaso(String.valueOf(formu.getId().getCoPaso()));
			if (Utilidades.isNotNull(formu.getOrden())) {
				formulaVo.setOrdenPaso(String.valueOf(formu.getOrden()));
			}
			if(formu.getFuncionDTO() != null){
				if(Utilidades.isNotEmpty(formu.getFuncionDTO().getCoFuncion())){
					formulaVo.setOperacion(formu.getFuncionDTO().getCoFuncion());
				}
			}
			Iterator<FormulaArgumentoDTO> itArg = argumentos.iterator();
			formulaVo.setArgumentos("");
			int cont = 0;
			while (itArg.hasNext()){
				FormulaArgumentoDTO argu = itArg.next();
				if(Utilidades.isNotEmpty(argu.getValor())){
					if (cont != 0) {
						if(itArg.hasNext()){
							formulaVo.setArgumentos(formulaVo.getArgumentos()+ "; ");
						}
					}
					//if(cont < 2){
						String funcion = "";
						if(cont % 2 == 1) {
							if(formulaVo.getOperacion().equalsIgnoreCase("RESTAR") || formulaVo.getOperacion().equalsIgnoreCase("RESTA"))
								funcion = " - ";
							else {
								if(formulaVo.getOperacion().equalsIgnoreCase("SUMAR") || formulaVo.getOperacion().equalsIgnoreCase("SUMA"))
									funcion = " + ";
								else {
									if(formulaVo.getOperacion().equalsIgnoreCase("PRODUCTO"))
										funcion = " * ";
									else {
										if(formulaVo.getOperacion().equalsIgnoreCase("DIVISION"))
											funcion = " / ";
										else {
											if(formulaVo.getOperacion().equalsIgnoreCase("MAYOCERO"))
												funcion = " > ";
											else {
												if(formulaVo.getOperacion().equalsIgnoreCase("MENOCERO"))
													funcion = " < ";
												else {
													if(formulaVo.getOperacion().equalsIgnoreCase("IGUAL"))
														funcion = " = ";
												}
											}
										}
									}
								}
							}
						}
						String operador = cont % 2 == 1? funcion : "";
						if("K".equals(argu.getTipo())){
							formulaVo.setArgumentos(formulaVo.getArgumentos()+ operador+"('" +argu.getValor()+"')");
						}
						if("R".equals(argu.getTipo())){
							formulaVo.setArgumentos(formulaVo.getArgumentos()+operador+ "(PASO " +argu.getValor()+")");
						}
						if("P".equals(argu.getTipo())){
							formulaVo.setArgumentos(formulaVo.getArgumentos()+ operador+"(" +argu.getValor()+")");
						}
						if("F".equals(argu.getTipo())){
							formulaVo.setArgumentos(formulaVo.getArgumentos()+ operador+"(FÓRMULA " +argu.getValor()+")");
						}
						
					/*}else{
						formulaVo.setArgumentos(formulaVo.getArgumentos()+ "...");
					}*/
					cont++;
				}
				
			}
			
			
			listado.add(formulaVo);
		}
		return listado;
	}
	
	public List<FormulaPasoDTO> obtenerPasosAnteriores(String coFormula, byte coPaso) throws GadirServiceException{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(FormulaPasoDTO.class);
		
		criteria.add(Restrictions.eq("id.coFormula", coFormula));
		criteria.add(Restrictions.lt("id.coPaso", coPaso));
		criteria.addOrder(Order.asc("id.coPaso"));

		List<FormulaPasoDTO> formulas= this.getDao().findByCriteria(criteria);
		return formulas;
		
		
	}
	
	public FormulaPasoDTO findByRowIdLazy(String id){
		FormulaPasoDTO dto = findByRowid(id);
		if(dto != null) {
			if(!Hibernate.isInitialized(dto.getFuncionDTO())){
				Hibernate.initialize(dto.getFuncionDTO());
			}
		}
		return dto;
	}

	public FormulaArgumentoBO getFormulaArgumentoBO() {
		return formulaArgumentoBO;
	}

	public void setFormulaArgumentoBO(FormulaArgumentoBO formulaArgumentoBO) {
		this.formulaArgumentoBO = formulaArgumentoBO;
	}

	
	
}