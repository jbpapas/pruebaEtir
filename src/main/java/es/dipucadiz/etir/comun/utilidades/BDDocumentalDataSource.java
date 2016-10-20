package es.dipucadiz.etir.comun.utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.activation.DataSource;

import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;

public class BDDocumentalDataSource implements DataSource {
	private BDDocumentalDTO bdDocumentalDTO;

	public BDDocumentalDataSource(BDDocumentalDTO bdDocumentalDTO) {
		this.bdDocumentalDTO = bdDocumentalDTO; 
	}
	
	public InputStream getInputStream() throws IOException {
		try {
			return bdDocumentalDTO.getFichero().getBinaryStream();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		return "application/pdf";
	}

	public String getName() {
		return bdDocumentalDTO.getNombre();
	}

}
