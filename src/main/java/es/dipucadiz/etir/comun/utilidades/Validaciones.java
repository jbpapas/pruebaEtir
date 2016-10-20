package es.dipucadiz.etir.comun.utilidades;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CargaBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ModeloVersionBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.FiltroMunicipioConceptoModeloVersionNombreVO;

/**
 * Clase responsable de proporcionar los métodos de validación que sean comunes.
 * 
 * @version 1.0 28/01/2010
 * @author SDS[FJTORRES]
 */
public final class Validaciones {

	private static final String CO_PROVINCIA = "municipioDTO.id.coProvincia";

	private static final String CO_MUNICIPIO = "municipioDTO.id.coMunicipio";

	private static final String CO_CONCEPTO = "conceptoDTO.coConcepto";

	private static final String DOCUMENTO_CO_VERSION = "id.coVersion";

	private static final String DOCUMENTO_CO_MODELO = "id.coModelo";
	
	private static final String DOCUMENTO_EJERCICIO = "ejercicio";
	
	private static final String DOCUMENTO_CO_PERIODO = "periodo";

	private static final String DOCUMENTO_CO_DOCUMENTO = "id.coDocumento";

	private static final String DOCUMENTO_ESTADO = "estado";
	

	/**
	 * Atributo que almacena el servicio asociado a {@link ModeloDTO}.
	 */
	private static ModeloBO modeloBO;

	/**
	 * Atributo que almacena el servicio asociado a {@link ModeloVersionDTO}.
	 */
	private static ModeloVersionBO modeloVersionBO;

	private static CargaBO cargaBO;
	
	/**
	 * Atributo que almacena el servicio asociado a {@link DocumentoDTO}.
	 */
	private static DocumentoBO documentoBO;

	/**
	 * Atributo que almacena el LOG de la clase.
	 */
	private static final Logger LOG = Logger.getLogger(Validaciones.class);

	/**
	 * Constructor de la clase.
	 */
	private Validaciones() {

	}

	/**
	 * Método que se encarga de validar si un Municipio-Concepto-Modelo-Version
	 * es valido y accesible por el usuario.<br/>
	 * <strong>Validaciones:</strong>
	 * <ol>
	 * <li>Si el parametro filtro es null se retorna false.</li>
	 * <li>Se comprueba si el municipio es accesible por el usuario logado.</li>
	 * <li>Se comprueba que el concepto es accesible por el usuario logado y
	 * municipio dado.</li>
	 * <li>Se comprueba si el modelo esta relacionado con el concepto dado.</li>
	 * <li>Se comprueba que la version existe para el modelo dado.</li>
	 * </ol>
	 * 
	 * @param filtro
	 *            Filtro establecido.
	 * @param errores
	 *            Lista que almacena los errores producidos, si es distinta de
	 *            null.
	 * @return Devuelve true si es un Municipio-Concepto-Modelo-Version valido y
	 *         accesible para el usuario, en caso contrario devuelve false.
	 */
	@SuppressWarnings("unchecked")
	public static boolean isMunicipioConceptoModeloVersionNombreValido(
	        final FiltroMunicipioConceptoModeloVersionNombreVO filtro,
	        final List<String> errores) {
		// Variable de control que indica si es valido o no.
		boolean valido = false;

		// Verificamos el filtro.
		if (Utilidades.isNotNull(filtro)) {
			// Código territorial del usuario.
			
			final MunicipioDTOId idMunicipio = new MunicipioDTOId(filtro
			        .getCoProvincia(), filtro.getCoMunicipio());
			final MunicipioDTO municipio = new MunicipioDTO();
			municipio.setId(idMunicipio);

			// Conceptos accesibles por el usuario para el municipio dado.
			final List<ConceptoDTO> conceptos = ControlTerritorial
			        .getConceptosUsuario(municipio.getCodigoCompleto());

			boolean conceptoAccesible = false;

			// Comprobamos si el concepto dado es accesible.
			for (final ConceptoDTO concepto : conceptos) {
				if (concepto.getCoConcepto().equals(filtro.getCoConcepto())) {
					conceptoAccesible = true;
					break;
				}
			}

			// Si el concepto es accesible comprobamos si el modelo lo es.
			if (conceptoAccesible) {

				List<ModeloDTO> modelos = Collections.EMPTY_LIST;

				try {
					// Modelos accesibles para el usuario con el concepto dado.
					modelos = modeloBO.findModelosByConcepto(filtro
					        .getCoConcepto());
				} catch (final GadirServiceException gse) {
					LOG.warn(
					        "Ocurrio un error al obtener los modelos asociados al concepto: "
					                + filtro.getCoConcepto(), gse);
				}
				boolean modeloAccesible = false;

				// Comprobamos si el modelo es accesible.
				for (final ModeloDTO modelo : modelos) {
					if (modelo.getCoModelo().equals(filtro.getCoModelo())) {
						modeloAccesible = true;
						break;
					}
				}

				// Si el modelo es accesible comprobamos la versión.
				boolean versionAccesible = false;
				if (modeloAccesible) {

					// Generamos la id de la versión
					final ModeloVersionDTOId idVersion = new ModeloVersionDTOId();
					idVersion.setCoVersion(filtro.getCoVersion());
					idVersion.setCoModelo(filtro.getCoModelo());
					try {
						versionAccesible = Utilidades.isNotNull(modeloVersionBO.findById(idVersion));
					} catch (GadirServiceException e) {
						LOG.warn("Ocurrio un error al obtener los modelos asociados a la estructura: " + filtro.getCoVersion(), e);
					}
				}
				
				List<CargaDTO> nombres = Collections.EMPTY_LIST;
				if (versionAccesible) {
					try {
						// Nombres de estructura 
						nombres = cargaBO.findFiltered(
								new String[]{"municipioDTO.id.coProvincia", "municipioDTO.id.coMunicipio", "modeloVersionDTO.id.coModelo", "modeloVersionDTO.id.coVersion", "nombre", "conceptoDTO.coConcepto"}, 
								new String[]{filtro.getCoProvincia(), filtro.getCoMunicipio(), filtro.getCoModelo(), filtro.getCoVersion(), filtro.getNombre(), filtro.getCoConcepto()});
						
						if(nombres.size() == 0)
							valido = false;
						
					} catch (final GadirServiceException gse) {
						LOG.warn("Ocurrio un error al obtener los nombres asociados a la estructura: " + filtro.getNombre(), gse);
					}
				}
				
				// Buscamos la estructura, y comprobamos si existe para dar por válido el proceso
				valido = conceptoAccesible && modeloAccesible && versionAccesible && Utilidades.isNotNull(nombres);
				
			}
		}
		return valido;
	}

	/**
	 * Método que se encarga de validar si un Municipio-Concepto-Modelo-Version
	 * es valido para documentos y accesible por el usuario.
	 * 
	 * @param coProvincia
	 *            Código de la provincia.
	 * @param coMunicipio
	 *            Código del municipio.
	 * @param coConcepto
	 *            Código del concepto.
	 * @param coModelo
	 *            Código del modelo.
	 * @param coVersion
	 *            Código de la versión.
	 * @return Devuelve true si es un Municipio-Concepto-Modelo-Version valido
	 *         para documentos y accesible para el usuario, en caso contrario
	 *         devuelve false.
	 */

	public static boolean isDocValidoByMunicConcepModVers(
	        final String coProvincia, final String coMunicipio,
	        final String coConcepto, final String coModelo,
	        final String coVersion) {

		boolean valido = false;
		try {
			final String[] propNames = { CO_PROVINCIA, CO_MUNICIPIO,
			        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION };
			final String[] filters = { coProvincia, coMunicipio, coConcepto,
			        coModelo, coVersion };
//			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO
//			        .findFiltered(propNames, filters, null,
//			                DAOConstant.ASC_ORDER);
			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO.findFiltered(propNames, filters, null, DAOConstant.ASC_ORDER, 0, 1);
			if (listaCodigosDocumentos.size() > 0) {
				valido = true;
			}
		} catch (final GadirServiceException ge) {
			LOG
			        .warn(
			                "Ocurrio un error al intentar validar los datos seleccionados.",
			                ge);
		}
		return valido;
	}
	
	
	/**
	 * Método que se encarga de validar si un Municipio-Concepto-Modelo-Version
	 * es valido para documentos y accesible por el usuario.
	 * 
	 * @param coProvincia
	 *            Código de la provincia.
	 * @param coMunicipio
	 *            Código del municipio.
	 * @param coConcepto
	 *            Código del concepto.
	 * @param coModelo
	 *            Código del modelo.
	 * @param coVersion
	 *            Código de la versión.
	 * @return Devuelve true si es un Municipio-Concepto-Modelo-Version valido
	 *         para documentos y accesible para el usuario, en caso contrario
	 *         devuelve false.
	 */

	public static boolean isDocValidoByMunicConcepModVersEjercPerio(
	        final String coProvincia, final String coMunicipio,
	        final String coConcepto, final String coModelo,
	        final String coVersion, String ejercicio, String coPeriodo) {

		boolean valido = false;
		try {
			final String[] propNames = { CO_PROVINCIA, CO_MUNICIPIO,
			        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION,
			        DOCUMENTO_EJERCICIO, DOCUMENTO_CO_PERIODO};
			final Object[] filters = { coProvincia, coMunicipio, coConcepto,
			        coModelo, coVersion, Short.parseShort(ejercicio), coPeriodo };
			
			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO.findFiltered(propNames, filters, null, DAOConstant.ASC_ORDER, 0, 1);
			if (listaCodigosDocumentos.size() > 0) {
				valido = true;
			}
		} catch (final GadirServiceException ge) {
			LOG
			        .warn(
			                "Ocurrio un error al intentar validar los datos seleccionados.",
			                ge);
		}
		return valido;
	}

	/**
	 * Método que se encarga de validar si un Municipio-Concepto-Modelo-Version
	 * es valido para documentos según un estado y accesible por el usuario.
	 * 
	 * @param coProvincia
	 *            Código de la provincia.
	 * @param coMunicipio
	 *            Código del municipio.
	 * @param coConcepto
	 *            Código del concepto.
	 * @param coModelo
	 *            Código del modelo.
	 * @param coVersion
	 *            Código de la versión.
	 * @return Devuelve true si es un Municipio-Concepto-Modelo-Version valido
	 *         para documentos según un estado y accesible para el usuario, en
	 *         caso contrario devuelve false.
	 */

	public static boolean isDocValidoByMunicConcepModVersEstado(
	        final String coProvincia, final String coMunicipio,
	        final String coConcepto, final String coModelo,
	        final String coVersion, final List<String> estado)
	        throws GadirServiceException {
		
//		try {
//			final String[] propNames = { CO_PROVINCIA, CO_MUNICIPIO,
//			        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION,
//			        DOCUMENTO_ESTADO };
//			final String[] filters = { coProvincia, coMunicipio, coConcepto,
//			        coModelo, coVersion, estado };
//			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO
//			        .findFiltered(propNames, filters, null,
//			                DAOConstant.ASC_ORDER, 0, 1); // Añadido limite, sadiel buscaba a todos los documentos.
//			if (listaCodigosDocumentos.size() > 0) {
//				valido = true;
//			}
//		} catch (final GadirServiceException ge) {
//			LOG
//			        .warn(
//			                "Ocurrio un error al intentar validar los datos seleccionados.",
//			                ge);
//		}
		
		boolean valido = false;
		
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoDTO.class);
			
			criteria.add(Restrictions.eq(CO_PROVINCIA, coProvincia));
			criteria.add(Restrictions.eq(CO_MUNICIPIO, coMunicipio));
			criteria.add(Restrictions.eq(CO_CONCEPTO, coConcepto));
			criteria.add(Restrictions.eq(DOCUMENTO_CO_MODELO, coModelo));
			criteria.add(Restrictions.eq(DOCUMENTO_CO_VERSION, coVersion));
			criteria.add(Restrictions.in(DOCUMENTO_ESTADO, estado));
			
			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO.findByCriteria(criteria, 0, 1);
			
			if(listaCodigosDocumentos.size() > 0){
				valido = true;
			}
		} catch (final GadirServiceException ge) {
			LOG
			        .warn(
			                "Ocurrio un error al intentar validar los datos seleccionados.",
			                ge);
		}	
	
		
		return valido;
	}

	public static boolean isDocValidoByMunicConcepModVersEstadoEjercPeriodo(
	        final String coProvincia, final String coMunicipio,
	        final String coConcepto, final String coModelo,
	        final String coVersion, final List<String> estado, 
	        final String ejercicio, final String periodo)
	        throws GadirServiceException {

		boolean valido = false;
		try {
			
//			String[] propNames = null;
//			
//			Object[] filters = null;
//			
//			if(Utilidades.isEmpty(ejercicio)){
//				if(Utilidades.isEmpty(periodo)){
//			
//					propNames = new String[]{ CO_PROVINCIA, CO_MUNICIPIO,
//					        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION,
//					        DOCUMENTO_ESTADO};
//					filters = new String[]{ coProvincia, coMunicipio, coConcepto,
//					        coModelo, coVersion, estado };
//				}
//				else{
//					
//					propNames = new String[]{ CO_PROVINCIA, CO_MUNICIPIO,
//					        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION,
//					        DOCUMENTO_ESTADO, DOCUMENTO_CO_PERIODO };
//					filters = new String[]{ coProvincia, coMunicipio, coConcepto,
//					        coModelo, coVersion, estado, periodo};	
//				}
//			}
//			else{
//				if(Utilidades.isEmpty(periodo)){
//					propNames = new String[]{ CO_PROVINCIA, CO_MUNICIPIO,
//					        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION,
//					        DOCUMENTO_ESTADO, DOCUMENTO_EJERCICIO};
//					filters = new Object[]{ coProvincia, coMunicipio, coConcepto,
//					        coModelo, coVersion, estado, Short.parseShort(ejercicio)};
//				}
//				else{
//					propNames = new String[]{ CO_PROVINCIA, CO_MUNICIPIO,
//					        CO_CONCEPTO, DOCUMENTO_CO_MODELO, DOCUMENTO_CO_VERSION,
//					        DOCUMENTO_ESTADO, DOCUMENTO_EJERCICIO, DOCUMENTO_CO_PERIODO};
//					filters = new Object[]{ coProvincia, coMunicipio, coConcepto,
//					        coModelo, coVersion, estado, Short.parseShort(ejercicio), periodo };
//				}
//			}
//			
//			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO
//			        .findFiltered(propNames, filters, null,
//			                DAOConstant.ASC_ORDER, 0, 1); // Añadido limite, sadiel buscaba a todos los documentos.
			
			DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoDTO.class);
			
			criteria.add(Restrictions.eq(CO_PROVINCIA, coProvincia));
			criteria.add(Restrictions.eq(CO_MUNICIPIO, coMunicipio));
			criteria.add(Restrictions.eq(CO_CONCEPTO, coConcepto));
			criteria.add(Restrictions.eq(DOCUMENTO_CO_MODELO, coModelo));
			criteria.add(Restrictions.eq(DOCUMENTO_CO_VERSION, coVersion));
			if (!ejercicio.isEmpty() && !periodo.isEmpty()) {
				criteria.add(Restrictions.eq(DOCUMENTO_EJERCICIO, Short.parseShort(ejercicio)));
				criteria.add(Restrictions.eq(DOCUMENTO_CO_PERIODO, periodo));
			}
			criteria.add(Restrictions.in(DOCUMENTO_ESTADO, estado));
			
			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO.findByCriteria(criteria, 0, 1);
			
			if (listaCodigosDocumentos.size() > 0) {
				valido = true;
			}
		} catch (final GadirServiceException ge) {
			LOG
			        .warn(
			                "Ocurrio un error al intentar validar los datos seleccionados.",
			                ge);
		}
		return valido;
	}
	
	
	
	/**
	 * Método que se encarga de validar si un Modelo-Version-Documento es valido
	 * para documentos y accesible por el usuario.
	 * 
	 * @param coProvincia
	 *            Código de la provincia.
	 * @param coMunicipio
	 *            Código del municipio.
	 * @param coConcepto
	 *            Código del concepto.
	 * @param coModelo
	 *            Código del modelo.
	 * @param coVersion
	 *            Código de la versión.
	 * @return Devuelve true si es un Modelo-Version-Documento es valido para
	 *         documentos y accesible para el usuario, en caso contrario
	 *         devuelve false.
	 */

	public static boolean isDocValidoByModVersDocEstado(final String coModelo,
	        final String coVersion, final String coDocumento,
	        final List<String> estado) {

//		boolean valido = false;
//		try {
//			final String[] propNames = { DOCUMENTO_CO_MODELO,
//			        DOCUMENTO_CO_VERSION, DOCUMENTO_CO_DOCUMENTO,
//			        DOCUMENTO_ESTADO };
//			final String[] filters = { coModelo, coVersion, coDocumento, estado };
//			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO
//			        .findFiltered(propNames, filters, null,
//			                DAOConstant.ASC_ORDER);
//			if (listaCodigosDocumentos.size() > 0) {
//				valido = true;
//			}
//		} catch (final GadirServiceException ge) {
//			LOG
//			        .warn(
//			                "Ocurrio un error al intentar validar los datos seleccionados.",
//			                ge);
//		}
		
		boolean valido = false;
		
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoDTO.class);
			
			criteria.add(Restrictions.eq(DOCUMENTO_CO_MODELO, coModelo));
			criteria.add(Restrictions.eq(DOCUMENTO_CO_VERSION, coVersion));
			criteria.add(Restrictions.eq(DOCUMENTO_CO_DOCUMENTO, coDocumento));
			criteria.add(Restrictions.in(DOCUMENTO_ESTADO, estado));
			
			final List<DocumentoDTO> listaCodigosDocumentos = documentoBO.findByCriteria(criteria);
			
			if(listaCodigosDocumentos.size() > 0){
				valido = true;
			}
		} catch (final GadirServiceException ge) {
			LOG
			        .warn(
			                "Ocurrio un error al intentar validar los datos seleccionados.",
			                ge);
		}	
		
		return valido;
	}

	/**
	 * Método que devuelve el atributo modeloVersionBO.
	 * 
	 * @return modeloVersionBO.
	 */
	public static ModeloVersionBO getModeloVersionBO() {
		return modeloVersionBO;
	}

	/**
	 * Método que establece el atributo modeloVersionBO.
	 * 
	 * @param modeloVersionBO
	 *            El modeloVersionBO.
	 */
	public void setModeloVersionBO(final ModeloVersionBO modeloVersionBO) {
		Validaciones.modeloVersionBO = modeloVersionBO;
	}

	/**
	 * Método que devuelve el atributo documentoBO.
	 * 
	 * @return documentoBO.
	 */
	public static DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	/**
	 * Método que establece el atributo documentoBO.
	 * 
	 * @param documentoBO
	 *            El documentoBO.
	 */
	public void setDocumentoBO(final DocumentoBO documentoBO) {
		Validaciones.documentoBO = documentoBO;
	}

	/**
	 * Método que devuelve el atributo modeloBO.
	 * 
	 * @return modeloBO.
	 */
	public static ModeloBO getModeloBO() {
		return modeloBO;
	}

	/**
	 * Método que establece el atributo modeloBO.
	 * 
	 * @param modeloBO
	 *            El modeloBO.
	 */
	public void setModeloBO(final ModeloBO modeloBO) {
		Validaciones.modeloBO = modeloBO;
	}

	public CargaBO getCargaBO() {
		return cargaBO;
	}

	public void setCargaBO(CargaBO cargaBO) {
		Validaciones.cargaBO = cargaBO;
	}

	public static boolean esModeloCenso(String coModelo) {
		return coModelo.startsWith("1");
	}
	
}
