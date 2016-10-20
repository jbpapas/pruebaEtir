package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CruceGrupoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface CruceBO extends GenericBO<CruceGrupoDTO, Long> {
	List<CruceGrupoDTO> findByMunicipio(MunicipioDTO municipio) throws GadirServiceException;
	List<CruceGrupoDTO> findByMunicipioYnombre(MunicipioDTO municipio, String nombre) throws GadirServiceException;
	List<CruceGrupoDTO> findByRowidFetchMunicipio(String rowid) throws GadirServiceException;
	List<CruceGrupoDTO> findByIdFetchMunicipio(String id) throws GadirServiceException;
	void eliminaCruce (String rowid) throws GadirServiceException;
}