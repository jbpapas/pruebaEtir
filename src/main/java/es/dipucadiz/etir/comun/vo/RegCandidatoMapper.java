package es.dipucadiz.etir.comun.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.dipucadiz.etir.comun.bo.impl.BuscarCandidatosBOImpl;

/**
 * Clase responsable de inicializar, mediante los resultados obtenidos de la
 * llamada a la función PL FU_GA_BUSQUEDA_CANDIDATOS realizada en {@link BuscarCandidatosBOImpl},
 * los objetos de tipo {@link RegCandidatoVO} para su posterior tratamiento.
 * 
 * @version 1.0 22/01/2009
 * @author SDS[AGONZALEZ]
 */
public final class RegCandidatoMapper implements RowMapper {
    
    public RegCandidatoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	RegCandidatoVO candidato = new RegCandidatoVO();
    	candidato.setIdentificador(rs.getString("identificador"));
    	candidato.setRazonSocial(rs.getString("razon_social"));
    	candidato.setIndNif(rs.getBigDecimal("ind_nif"));
    	candidato.setCoMensaje(rs.getBigDecimal("co_mensaje"));
    	candidato.setUnidadUrbana(rs.getBigDecimal("unidad_urbana"));
        return candidato;
    }
}
