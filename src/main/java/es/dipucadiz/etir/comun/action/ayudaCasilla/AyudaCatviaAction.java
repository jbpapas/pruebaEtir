package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTOId;
import es.dipucadiz.etir.comun.dto.CalleTramoCategoriaDTO;
import es.dipucadiz.etir.comun.dto.CalleTramoCategoriaDTOId;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class AyudaCatviaAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -365187721006541529L;

	private static final Log LOG = LogFactory.getLog(AyudaCatviaAction.class);
	
	protected GenericBO<CalleTramoCategoriaDTO, CalleTramoCategoriaDTOId> calleTramoCategoriaBO;
	protected CalleBO calleBO;
	protected GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> calleSinonimoBO;
	protected String tramoCategoria="";

	public String execute() throws GadirServiceException {

		try
		{
			String coMunicipio="";
			String coMunicipioAux="";
			String coProvincia="";
			String siglaVia="";
			String nombreVia="";
			String numVia="";
			String concepto="";
			boolean conError = false;
	
			CalleDTO calle = null;
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));
				
			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				String valor="";
				
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	coMunicipio = valor; break;
				case 2: siglaVia = valor; break;
				case 3: nombreVia = valor; break;
				case 4: numVia = valor; break;
				case 6: concepto = valor; break;
				}
			}
			
			if (Utilidades.isEmpty(coMunicipio) || coMunicipio.length()!=5){
				addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_MUNICIPIO_INEXISTENTE));
			}

			if(Utilidades.isEmpty(coMunicipio)){
				conError = true;
			}
			
			if(Utilidades.isEmpty(siglaVia)){
				conError = true;
			}
			
			if(Utilidades.isEmpty(nombreVia)){
				conError = true;
			}
			
			if(Utilidades.isEmpty(numVia)){
				conError = true;
			}
			
			if(conError)
				addActionError("Debe introducir la sigla, el nombre y el número de vía");
			
			if(!Utilidades.isEmpty(numVia) && !Utilidades.isNumeric(numVia))
				addActionError("El número de vía es incorrecto");
			
			coMunicipioAux = coMunicipio.substring(2, 5);
			coProvincia = coMunicipio.substring(0, 2); 
			
			//Buscamos la calle, si no se encuentra buscamos si es un sinonimo
			
			List<CalleDTO> calles =calleBO.findFiltered(new String[]{"sigla","nombreCalle","municipioDTO.id.coProvincia","municipioDTO.id.coMunicipio"},
			                                    new Object[]{siglaVia,nombreVia,coProvincia,coMunicipioAux});
			
			List<CalleSinonimoDTO> sinonimos = calleSinonimoBO.findByCriteria(getCriteria(siglaVia,nombreVia,coProvincia,coMunicipioAux));
			
			if(calles != null && calles.size()>0)
				calle = calles.get(0);
			else if(sinonimos != null && sinonimos.size()>0)
				calle = sinonimos.get(0).getCalleDTO();
				
			if(calle == null)
				if(!hasErrors())
					addActionError("No se encuentra la vía especificada");
				
			//Buscamos la categoria, si no se encuentra buscamos con el concepto generico
			List<CalleTramoCategoriaDTO> tramos = calleTramoCategoriaBO.findByCriteria(getCriteriaTramo(calle,concepto,numVia));
			
			if(tramos == null || tramos.size() == 0)
				tramos = calleTramoCategoriaBO.findByCriteria(getCriteriaTramo(calle,"****",numVia));
			
			if(tramos == null || tramos.size() == 0)
				addActionError("No existen categorías para la vía especificada");
			
			if(!hasErrors())
			{
				tramoCategoria = tramos.get(0).getCategoria();
			}
			
		}catch(Exception e)
		{
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de categoría de vía", e);
		}
			
		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 5:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), tramoCategoria);
					break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de ategoría de vía", e);
		}
		
		ajaxData=parseaResultado();
		
		return AJAX_DATA;
	}
	
	private DetachedCriteria getCriteria(String siglaVia, String nombreVia, String codProvincia, String codMunicipio) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleSinonimoDTO.class);
		
		criteria.createAlias("calleDTO", "calle");
	
		criteria.add(Restrictions.eq("id.sigla",siglaVia));
		criteria.add(Restrictions.eq("id.sinonimo",nombreVia));
		criteria.add(Restrictions.eq("calle.municipioDTO.id.coProvincia",codProvincia));
		criteria.add(Restrictions.eq("calle.municipioDTO.id.coMunicipio",codMunicipio));
		
		return criteria;
	}

	private DetachedCriteria getCriteriaTramo(CalleDTO calle,String concepto,String numVia)
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleTramoCategoriaDTO.class);	
		Criterion ambos = Restrictions.eq("tipo","A");
       
			
		criteria.add(Restrictions.eq("id.coCalle",calle.getCoCalle()));
		criteria.add(Restrictions.eq("id.coConcepto",concepto));
		
		if(!numVia.isEmpty())
		{
			criteria.add(Restrictions.le("id.desde",Integer.parseInt(numVia)));
			criteria.add(Restrictions.ge("hasta",Integer.parseInt(numVia)));
			
			//Vemos si el numero introducido es par o impar
			if(Integer.parseInt(numVia)%2 == 0)
			{
				Criterion par = Restrictions.eq("tipo","P");
				LogicalExpression orExp = Restrictions.or(ambos,par);
				criteria.add(orExp);
			}
			else
			{
				Criterion impar = Restrictions.eq("tipo","I");
				LogicalExpression orExp = Restrictions.or(ambos,impar);
				criteria.add(orExp);
			}
		}
		
		return criteria;
	}
	
	public GenericBO<CalleTramoCategoriaDTO, CalleTramoCategoriaDTOId> getCalleTramoCategoriaBO() {
		return calleTramoCategoriaBO;
	}

	public void setCalleTramoCategoriaBO(
			GenericBO<CalleTramoCategoriaDTO, CalleTramoCategoriaDTOId> calleTramoCategoriaBO) {
		this.calleTramoCategoriaBO = calleTramoCategoriaBO;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public String getTramoCategoria() {
		return tramoCategoria;
	}

	public void setTramoCategoria(String tramoCategoria) {
		this.tramoCategoria = tramoCategoria;
	}

	public GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> getCalleSinonimoBO() {
		return calleSinonimoBO;
	}

	public void setCalleSinonimoBO(
			GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> calleSinonimoBO) {
		this.calleSinonimoBO = calleSinonimoBO;
	}
}
