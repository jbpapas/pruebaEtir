package es.dipucadiz.etir.comun.utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DGTService {
	private static final Log LOG = LogFactory.getLog(DGTService.class);

	public static void consultaMatricula(final String matricula) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
//		https://www.javacodegeeks.com/2014/03/signing-soap-messages-generation-of-enveloped-xml-signatures.html
		
		// Load certificate
	    String keystoreFilename = "my.keystore";
	    char[] password = "password".toCharArray();
	    String alias = "alias";
	    FileInputStream fIn = new FileInputStream(keystoreFilename);
	    KeyStore keystore = KeyStore.getInstance("JKS");
	    keystore.load(fIn, password);
	    Certificate cert = keystore.getCertificate(alias);
		
		
		
		
		Service dgtService = Service.create(new URL("xxx"), new QName("xxx", "xxx"));
////		dgtService.
//		
//		KeyStore ks = KeyStore.getInstance("JKS");
//		ks.load(param);
		
	}

	public static Log getLog() {
		return LOG;
	}

}
