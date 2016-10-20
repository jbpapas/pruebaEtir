package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.bo.impl.ClienteBOImpl;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.ExpedienteClienteDocDTO;
import es.dipucadiz.etir.comun.dto.ExpedienteDTO;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.sb06.G63TramitacionExpediente.C63ClienteVO;
import es.dipucadiz.etir.sb07.tributos.action.G743MantenimientoDocumentos.G743DocumentoVO;

public interface ExpedienteClienteDocBO extends GenericBO<ExpedienteClienteDocDTO, Long>{
	
	public DetachedCriteria getCriterioDocumentosEtirByExpedienteClienteDoc(ExpedienteDTO expedienteDTO, boolean ordenar, boolean limiteUno);
	public List<G743DocumentoVO> findDocumentosEtirByExpedienteClienteDoc(ExpedienteDTO expedienteDTO, Long coExpedienteSeguimiento, boolean permitirBorrarTodos);
	public GadirPaginatedList<G743DocumentoVO> findDocumentosEtirByExpedienteClienteDocPaginado(ExpedienteDTO expedienteDTO, String clienteFiltro, String estadoFiltro, Long coExpedienteSeguimiento, boolean permitirBorrarTodos,int porPagina, int page, String sort, String dir, int firstResult, int maxResults);
	public DetachedCriteria getCriterioDocumentosEtirByExpedienteClienteDocSinRownum(ExpedienteDTO expedienteDTO, String clienteFiltro, String estadoFiltro, boolean ordenar, String sort, String dir);

	
	public GadirPaginatedList<C63ClienteVO> findClienteEtirByExpedienteSubexpedientesEstadosPaginado(ExpedienteDTO expedienteDTO,String clienteFiltro, String estadoFiltro, Long coExpedienteSeguimiento, int porPagina, int page, String sort, String dir, int firstResult, int maxResults,ClienteBO clienteBO, List<KeyValue> listaEstados) ;
}