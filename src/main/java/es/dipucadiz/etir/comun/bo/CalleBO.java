package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.Criterion;

import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTOId;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb04.action.G4215Callejero.G4215CalleVO;
import es.dipucadiz.etir.sb07.comun.vo.ViasVO;

public interface CalleBO extends GenericBO<CalleDTO, Long>{

	int COMIENZA_POR = 0;
	int CONTIENE = 1;
	int EXACTO = 2;
	
	CalleDTO findByIdFetch(final String coCalle) throws GadirServiceException;
	
	List<CalleDTO> findByMunicipioAndLikeNombre(MunicipioDTO municipioDTO, String nombreCalle) throws GadirServiceException;
	
	List<CalleDTO> findByMunicipioAndLikeCalle(MunicipioDTO municipioDTO, String nombreCalle) throws GadirServiceException;
	
	public CalleDTO findByMunicipioSiglaNombre(MunicipioDTO municipioDTO, String sigla, String nombreCalle) throws GadirServiceException;
	
	public CalleDTO findByMunicipioSiglaNombreUbicacion(MunicipioDTO municipioDTO, String sigla, String nombreCalle, CalleUbicacionDTO calleUbicacionDTO) throws GadirServiceException;
	
	public CalleDTO findByMunicipioCoMunicipal(MunicipioDTO municipioDTO, Integer coMunicipal) throws GadirServiceException;
	
	public List<CalleDTO> findByMunicipio(MunicipioDTO municipioDTO) throws GadirServiceException;
	
	public List<CalleDTO> findByMunicipioSigla(MunicipioDTO municipioDTO, String sigla) throws GadirServiceException;

	public List<CalleDTO> findByMunicipioSiglaAndLikeNombre(MunicipioDTO municipioDTO,String sigla, String nombreCalle) throws GadirServiceException;
	
	public CalleDTO existeCalle(CalleDTO calle) throws GadirServiceException;
	
	public CalleDTO nuevaCalle(CalleDTO calle) throws GadirServiceException;

	
	public Integer findByParamsPorMunicipio(String coProvincia,String coMunicipio,String codSigla, String nombreVia, Integer pagina,
	        List<ViasVO> vias, Integer limite) throws GadirServiceException;

	public CalleDTO findCalleById(final Long coCalle) throws GadirServiceException;
	
	public Criterion getRestrictionNombreCalle(String nombreCalle, int modo, String prefijoGaCalle, boolean buscarEnSinonimos);
	public List<G4215CalleVO> getCalleVOs(List<CalleDTO> calleDTOs, String nombreCalle, GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> calleSinonimoBO) throws GadirServiceException;
}
