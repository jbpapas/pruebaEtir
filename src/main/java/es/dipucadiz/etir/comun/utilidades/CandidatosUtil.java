package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.CalleTramoPostalBO;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.DocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.MunicipioSinonimoBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.bo.ProvinciaSinonimoBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.boStoredProcedure.CandidatoAsignarBO;
import es.dipucadiz.etir.comun.boStoredProcedure.CandidatosObtenerBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioSinonimoDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.CandidatoVO;

public class CandidatosUtil {
	@SuppressWarnings("unchecked")
	public static List<CandidatoVO> obtenerCandidatos(String coModelo, String coVersion, String coDocumento) throws GadirServiceException {
		final short HOJA_1 = 1;
		final short NU_CASILLA_NIF = 10;
		final short NU_CASILLA_NOMBRE = 11;
		final short NU_CASILLA_SIGLA = 13;
		final short NU_CASILLA_VIA = 14;
		final short NU_CASILLA_PROVINCIA = 23;
		final short NU_CASILLA_MUNICIPIO = 22;
		
		
//		final String[] paths = {"classpath:/*/spring*/applicationContext*.xml"};
//		final ApplicationContext context = new ClassPathXmlApplicationContext(paths);
//		CandidatosObtenerBO candidatosObtenerBO = ((CandidatosObtenerBO) context.getBean("candidatosObtenerBO"));
//		DocumentoCasillaValorBO documentoCasillaValorBO = ((DocumentoCasillaValorBO) context.getBean("documentoCasillaValorBO"));
//		ProvinciaBO provinciaBO = ((ProvinciaBO) context.getBean("provinciaBO"));
//		MunicipioBO municipioBO = ((MunicipioBO) context.getBean("municipioBO"));
//		ProvinciaSinonimoBO provinciaSinonimoBO = ((ProvinciaSinonimoBO) context.getBean("provinciaSinonimoBO"));
//		MunicipioSinonimoBO municipioSinonimoBO = ((MunicipioSinonimoBO) context.getBean("municipioSinonimoBO"));
//		ClienteBO clienteBO = ((ClienteBO) context.getBean("clienteBO"));
//		UnidadUrbanaBO unidadUrbanaBO = ((UnidadUrbanaBO) context.getBean("unidadUrbanaBO"));
//		CalleTramoPostalBO calleTramoPostalBO = ((CalleTramoPostalBO) context.getBean("calleTramoPostalBO"));
		CandidatosObtenerBO candidatosObtenerBO = ((CandidatosObtenerBO) GadirConfig.getBean("candidatosObtenerBO"));
		DocumentoCasillaValorBO documentoCasillaValorBO = ((DocumentoCasillaValorBO) GadirConfig.getBean("documentoCasillaValorBO"));
		ProvinciaBO provinciaBO = ((ProvinciaBO) GadirConfig.getBean("provinciaBO"));
		MunicipioBO municipioBO = ((MunicipioBO) GadirConfig.getBean("municipioBO"));
		ProvinciaSinonimoBO provinciaSinonimoBO = ((ProvinciaSinonimoBO) GadirConfig.getBean("provinciaSinonimoBO"));
		MunicipioSinonimoBO municipioSinonimoBO = ((MunicipioSinonimoBO) GadirConfig.getBean("municipioSinonimoBO"));
		ClienteBO clienteBO = ((ClienteBO) GadirConfig.getBean("clienteBO"));
		UnidadUrbanaBO unidadUrbanaBO = ((UnidadUrbanaBO) GadirConfig.getBean("unidadUrbanaBO"));
		CalleTramoPostalBO calleTramoPostalBO = ((CalleTramoPostalBO) GadirConfig.getBean("calleTramoPostalBO"));
		
		DocumentoCasillaValorDTO casillaNifDTO = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(coModelo, coVersion, coDocumento, NU_CASILLA_NIF, HOJA_1));
		DocumentoCasillaValorDTO casillaNombreDTO = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(coModelo, coVersion, coDocumento, NU_CASILLA_NOMBRE, HOJA_1));
		DocumentoCasillaValorDTO casillaSiglaDTO = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(coModelo, coVersion, coDocumento, NU_CASILLA_SIGLA, HOJA_1));
		DocumentoCasillaValorDTO casillaViaDTO = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(coModelo, coVersion, coDocumento, NU_CASILLA_VIA, HOJA_1));
		DocumentoCasillaValorDTO casillaProvinciaDTO = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(coModelo, coVersion, coDocumento, NU_CASILLA_PROVINCIA, HOJA_1));
		DocumentoCasillaValorDTO casillaMunicipioDTO = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(coModelo, coVersion, coDocumento, NU_CASILLA_MUNICIPIO, HOJA_1));
		
//		// Comprobar casillas erroneas.
//		if (casillaSiglaDTO.getBoError()) throw new GadirServiceException("Obtención de candidatos no posible, la casilla de la sigla (fiscal) (" + NU_CASILLA_SIGLA + ") tiene error.");
//		if (casillaViaDTO.getBoError()) throw new GadirServiceException("Obtención de candidatos no posible, la casilla de nombre de vía (fiscal) (" + NU_CASILLA_VIA + ") tiene error.");
//		if (casillaMunicipioDTO.getBoError()) throw new GadirServiceException("Obtención de candidatos no posible, la casilla del municipio (fiscal) (" + NU_CASILLA_MUNICIPIO + ") tiene error.");
//		if (casillaProvinciaDTO.getBoError()) throw new GadirServiceException("Obtención de candidatos no posible, la casilla de la provincia (fiscal) (" + NU_CASILLA_PROVINCIA + ") tiene error.");
		
		String Nif = casillaNifDTO == null || casillaNifDTO.getValor() == null ? "" : casillaNifDTO.getValor().trim();
		String nombre = casillaNombreDTO == null || casillaNombreDTO.getValor() == null ? "" : casillaNombreDTO.getValor().trim();
		String sigla = casillaSiglaDTO == null ? "" : casillaSiglaDTO.getValor();
		String via = casillaViaDTO == null || casillaViaDTO.getValor() == null ? "" : casillaViaDTO.getValor().trim();
		String nombreProvincia = casillaProvinciaDTO == null ? "" : casillaProvinciaDTO.getValor();
		String nombreMunicipio = casillaMunicipioDTO == null ? "" : casillaMunicipioDTO.getValor();
		String coProvincia = null;
		String coMunicipio = null;
		
		List<ProvinciaDTO> provinciaDTOs = provinciaBO.findFiltered("nombre", nombreProvincia);
		if (provinciaDTOs.size() > 0) {
			coProvincia = provinciaDTOs.get(0).getCoProvincia();
		} else {
			List<ProvinciaSinonimoDTO> sinonimoDTOs = provinciaSinonimoBO.findCodigoByNombre(nombreProvincia);
			if (sinonimoDTOs.size() > 0) coProvincia = sinonimoDTOs.get(0).getId().getCoProvincia();
		}
		if (Utilidades.isEmpty(coProvincia)) throw new GadirServiceException("Obtención de candidatos no posible, la provincia " + nombreProvincia + " no existe.");
		
		String[] propNames = {"id.coProvincia", "nombre"};
		Object[] filters = {coProvincia, nombreMunicipio};
		List<MunicipioDTO> municipioDTOs = municipioBO.findFiltered(propNames, filters);
		if (municipioDTOs.size() > 0) {
			coMunicipio = municipioDTOs.get(0).getId().getCoMunicipio();
		} else {
			List<MunicipioSinonimoDTO> sinonimoDTOs = municipioSinonimoBO.findCodigoByProvinciaNombre(coProvincia, nombreMunicipio);
			if (sinonimoDTOs.size() > 0) coMunicipio = sinonimoDTOs.get(0).getId().getCoMunicipio();
		}
		if (Utilidades.isEmpty(coMunicipio)) throw new GadirServiceException("Obtención de candidatos no posible, el municipio " + nombreMunicipio + " no existe en la provincia " + nombreProvincia + ".");
		
		List<CandidatoVO> candidatoVOs = candidatosObtenerBO.execute(Nif, nombre, sigla, via, coProvincia, coMunicipio);
		if (candidatoVOs.isEmpty()) {
			throw new GadirServiceException("No existen candidatos.");
		}
		for (CandidatoVO candidatoVO : candidatoVOs) {
			candidatoVO.setMensaje(Mensaje.getTexto(candidatoVO.getCoMensaje()));
			ClienteDTO clienteDTO = clienteBO.findById(candidatoVO.getCoCliente());
			if (clienteDTO != null) {
				candidatoVO.setIdentificador(clienteDTO.getIdentificador());
				candidatoVO.setNombre(clienteDTO.getRazonSocial());
			}
			UnidadUrbanaDTO unidadUrbanaDTO = unidadUrbanaBO.findUnidadUrbanaByCodigo(candidatoVO.getCoUnidadUrbana());
			if (unidadUrbanaDTO != null) {
				StringBuffer direccion = new StringBuffer();
				direccion.append(unidadUrbanaDTO.getCalleDTO().getSigla()).append(" ");
				direccion.append(unidadUrbanaDTO.getCalleDTO().getNombreCalle()).append(" ");
				direccion.append(unidadUrbanaDTO.getCadenaCompleta()).append(", ");
				String codigoPostal = calleTramoPostalBO.findCodigoPostalAsString(unidadUrbanaDTO.getCalleDTO().getCoCalle(), unidadUrbanaDTO.getNumero());
				direccion.append(codigoPostal).append(" ");
				direccion.append(unidadUrbanaDTO.getCalleDTO().getMunicipioDTO().getNombre()).append(", ");
				direccion.append(unidadUrbanaDTO.getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre());
				candidatoVO.setDireccion(direccion.toString());
			}
		}
		return candidatoVOs;
	}
	public static int asignarCandidato(String coModelo, String coVersion, String coDocumento, Long coCliente, String coProcesoActual) {
//		final String[] paths = {"classpath:/*/spring*/applicationContext*.xml"};
//		final ApplicationContext context = new ClassPathXmlApplicationContext(paths);
//		CandidatoAsignarBO candidatoAsignarBO = ((CandidatoAsignarBO) context.getBean("candidatoAsignarBO"));
		CandidatoAsignarBO candidatoAsignarBO = ((CandidatoAsignarBO) GadirConfig.getBean("candidatoAsignarBO"));
		Map<String, Object> result = candidatoAsignarBO.execute(coModelo, coVersion, coDocumento, coCliente, coProcesoActual);
		return ((BigDecimal) result.get("resultado")).intValue();
	}
}
