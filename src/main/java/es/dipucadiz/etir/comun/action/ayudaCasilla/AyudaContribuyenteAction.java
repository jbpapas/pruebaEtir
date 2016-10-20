package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.ClienteVO;

final public class AyudaContribuyenteAction extends AyudaCasillaAction {

	private static final long serialVersionUID = 2030159311207134260L;
	private static final Log LOG = LogFactory.getLog(AyudaContribuyenteAction.class);

	private String coCliente;
	private String nombreFiltro;
	private String nifFiltro;
	private String contieneSel;

	private ClienteBO clienteBO;

	private List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
	protected GadirPaginatedList<ClienteVO> listaContribuyentes;
	private List<KeyValue> listaContiene = new ArrayList<KeyValue>();

	private String sort;
	private String dir;
	private int page = 1;
	private int porPagina = 10;

	private static final String COMIENZA = "Comienza por";

	public String execute() throws GadirServiceException
	{	
		DetachedCriteria criterio;
		int total=0;
		if(Utilidades.isNotEmpty(nifFiltro) || Utilidades.isNotEmpty(nombreFiltro)){
			criterio = getCriteria(false); 
			if(Utilidades.isNotNull(criterio))
				total = clienteBO.countByCriteria(criterio);
			criterio = getCriteria(true);
			if(Utilidades.isNotNull(criterio)){
				if(total > 0){
					lista = clienteBO.findByCriteria(criterio,(page - 1) * porPagina, porPagina);
					listaContribuyentes = new GadirPaginatedList<ClienteVO>(dtoToVo(lista), total, porPagina, page, "ayudaContribuyente", sort, dir);
				}
			}			
		}
		cargaComboContiene();
		return INPUT;
	}

	public void cargaComboContiene(){
		listaContiene.add(new KeyValue("Contiene", "Contiene"));
		listaContiene.add(new KeyValue("Comienza por", "Comienza por"));

		Collections.sort(listaContiene);

		if(Utilidades.isEmpty(contieneSel)){
			contieneSel = COMIENZA;
		}
	}
	public String botonSeleccionar() throws GadirServiceException {
		return execute();
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";

		try {
			ClienteDTO clienteDTO = clienteBO.findById(Long.parseLong(coCliente));

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));
			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){
				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), clienteDTO.getIdentificador());
					break;
				case 2:
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), clienteDTO.getRazonSocial());
					break;
				case 3:
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), clienteDTO.getCoCliente().toString());
					break;
				}
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de clientes", e);
		}
		ajaxData=parseaResultado();

		return AJAX_DATA;
	}


	private List<ClienteVO> dtoToVo(List<ClienteDTO> clienteDTOs) {
		List<ClienteVO> result = new ArrayList<ClienteVO>();
		for (ClienteDTO dto : clienteDTOs) {
			ClienteVO vo = new ClienteVO();

			vo.setCoCliente(dto.getCoCliente());
			vo.setRazonSocial(dto.getRazonSocial());
			vo.setIdentificador(dto.getIdentificador());

			result.add(vo);
		}
		return result;
	}
	public DetachedCriteria getCriteria(boolean ordenar){
		DetachedCriteria criterio = DetachedCriteria.forClass(ClienteDTO.class);

		if(Utilidades.isNotEmpty(nifFiltro)){
			criterio.add(Restrictions.ilike("identificador", nifFiltro.toUpperCase(), MatchMode.ANYWHERE));
		}
		if(Utilidades.isNotEmpty(nombreFiltro)){
			boolean contiene = COMIENZA.equals(contieneSel);
			if (!contiene) {
				criterio.add(Restrictions.sqlRestriction("translate(UPPER(this_.razon_social), 'ÁÉÍÓÚÖ', 'AEIOUO') like translate('%" + nombreFiltro.toUpperCase() + "%', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
			} else {
				criterio.add(Restrictions.sqlRestriction("translate(UPPER(this_.razon_social), 'ÁÉÍÓÚÖ', 'AEIOUO') like translate('" +  nombreFiltro.toUpperCase() + "%', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
			}		
		}

		if(Utilidades.isEmpty(sort)){
			sort = "identificador";
		}
		if(Utilidades.isEmpty(dir)){
			dir = GadirPaginatedList.ASCENDING;
		}
		if ("identificador".equals(sort)) {
			if (GadirPaginatedList.DESCENDING.equals(dir)) {
				criterio.addOrder(Order.desc("identificador"));
			} else {
				criterio.addOrder(Order.asc("identificador"));
			}
		}
		if ("razonSocial".equals(sort)) {
			if (GadirPaginatedList.DESCENDING.equals(dir)) {
				criterio.addOrder(Order.desc("razonSocial"));
			} else {
				criterio.addOrder(Order.asc("razonSocial"));
			}
		}

		return criterio;
	}


	public String getNombreFiltro() {
		return nombreFiltro;
	}

	public void setNombreFiltro(String nombreFiltro) {
		this.nombreFiltro = nombreFiltro;
	}

	public String getNifFiltro() {
		return nifFiltro;
	}

	public void setNifFiltro(String nifFiltro) {
		this.nifFiltro = nifFiltro;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPorPagina() {
		return porPagina;
	}

	public void setPorPagina(int porPagina) {
		this.porPagina = porPagina;
	}

	public String getCoCliente() {
		return coCliente;
	}

	public void setCoCliente(String coCliente) {
		this.coCliente = coCliente;
	}

	public List<ClienteDTO> getLista() {
		return lista;
	}

	public void setLista(List<ClienteDTO> lista) {
		this.lista = lista;
	}

	public GadirPaginatedList<ClienteVO> getListaContribuyentes() {
		return listaContribuyentes;
	}

	public void setListaContribuyentes(
			GadirPaginatedList<ClienteVO> listaContribuyentes) {
		this.listaContribuyentes = listaContribuyentes;
	}

	public String getContieneSel() {
		return contieneSel;
	}

	public void setContieneSel(String contieneSel) {
		this.contieneSel = contieneSel;
	}

	public List<KeyValue> getListaContiene() {
		return listaContiene;
	}

	public void setListaContiene(List<KeyValue> listaContiene) {
		this.listaContiene = listaContiene;
	}


}
