package es.dipucadiz.etir.comun.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.gob.afirma.core.misc.Base64;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.signers.pades.AOPDFSigner;
import es.gob.afirma.signers.tsp.pkcs7.TsaParams;

final public class FirmaPdf {

	private static final Log LOG = LogFactory.getLog(FirmaPdf.class);

	private static PrivateKeyEntry obtenerKeyParaFirma() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableEntryException {
		InputStream is = new FileInputStream(GadirConfig.leerParametro("firma.keystore.ruta"));
		KeyStore ks = KeyStore.getInstance(GadirConfig.leerParametro("firma.keystore.tipo"));
		char[] password = GadirConfig.leerParametro("firma.keystore.cert.password").toCharArray();
		ks.load(is, password);
		PrivateKeyEntry pke = (PrivateKeyEntry) ks.getEntry(GadirConfig.leerParametro("firma.keystore.cert.alias"), new KeyStore.PasswordProtection(password));
		return pke;
	}

	private static String obtenerAlmacenEnB64(String ruta) throws IOException {
		InputStream is = new FileInputStream(ruta);
		byte[] almacenEnBytes = IOUtils.toByteArray(is);
		String almacenEnB64 = Base64.encode(almacenEnBytes);
		return almacenEnB64;
	}

	private static String obtenerStringEnB64(String input) throws IOException {
		InputStream is = IOUtils.toInputStream(input, "UTF-8");
		byte[] almacenEnBytes = IOUtils.toByteArray(is);
		String almacenEnB64 = Base64.encode(almacenEnBytes);
		return almacenEnB64;
	}

	public static byte[] firmar(InputStream inputStream) throws GadirServiceException {
		byte[] result = null;
		try {
			PrivateKeyEntry pke = obtenerKeyParaFirma();

			byte[] bytes = IOUtils.toByteArray(inputStream);

			final AOSigner signer = new AOPDFSigner();

			final Properties extraParams = new Properties();
			// ********* TSA CATCERT
			//extraParams.put("tsaURL", "http://psis.catcert.net/psis/catcert/tsp");
			//extraParams.put("tsaPolicy", "0.4.0.2023.1.1");
			//extraParams.put("tsaRequireCert", Boolean.FALSE);
			//extraParams.put("tsaHashAlgorithm", "SHA-512");
			//extraParams.put("tsType", TsaParams.TS_SIGN_DOC);

			//********** TSA AFIRMA ********************************************************************
			extraParams.put("tsaURL", GadirConfig.leerParametro("tsa.url"));
			extraParams.put("tsType", TsaParams.TS_SIGN_DOC);
			extraParams.put("tsaPolicy", GadirConfig.leerParametro("tsa.policy"));
			extraParams.put("tsaRequireCert", "true".equalsIgnoreCase(GadirConfig.leerParametro("tsa.require.cert")) ? Boolean.TRUE : Boolean.FALSE);
			extraParams.put("tsaHashAlgorithm", GadirConfig.leerParametro("tsa.hash.alg"));
			//extraParams.put("tsaUsr", GadirConfig.leerParametro("tsa.usuario"));
			//extraParams.put("tsaPwd", GadirConfig.leerParametro("tsa.password"));
			extraParams.put("tsaSslKeyStore", obtenerAlmacenEnB64(GadirConfig.leerParametro("tsa.keystore.ruta")));
			extraParams.put("tsaSslKeyStorePassword", GadirConfig.leerParametro("tsa.keystore.password"));
			extraParams.put("tsaSslKeyStoreType", GadirConfig.leerParametro("tsa.keystore.tipo"));
			extraParams.put("tsaSslTrustStore", obtenerAlmacenEnB64(GadirConfig.leerParametro("tsa.truststore.ruta")));
			extraParams.put("tsaSslTrustStorePassword", GadirConfig.leerParametro("tsa.truststore.password"));
			extraParams.put("tsaSslTrustStoreType", GadirConfig.leerParametro("tsa.truststore.tipo"));
			extraParams.put("verifyHostname", Boolean.FALSE);
			extraParams.put("tsaExtensionOid", GadirConfig.leerParametro("tsa.extension.oid"));
			extraParams.put("tsaExtensionValueBase64", obtenerStringEnB64(GadirConfig.leerParametro("tsa.extension.value")));

			extraParams.put("certificationLevel", "1");

			result = signer.sign(bytes, "SHA512withRSA", pke.getPrivateKey(), pke.getCertificateChain(), extraParams);
			//System.out.println(signer.isSign(result));

		} catch (KeyStoreException e) {
			LOG.error("Error en FirmaPdf.firmar", e);
			throw new GadirServiceException("Error en FirmaPdf.firmar", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Error en FirmaPdf.firmar", e);
			throw new GadirServiceException("Error en FirmaPdf.firmar", e);
		} catch (CertificateException e) {
			LOG.error("Error en FirmaPdf.firmar", e);
			throw new GadirServiceException("Error en FirmaPdf.firmar", e);
		} catch (UnrecoverableEntryException e) {
			LOG.error("Error en FirmaPdf.firmar", e);
			throw new GadirServiceException("Error en FirmaPdf.firmar", e);
		} catch (IOException e) {
			LOG.error("Error en FirmaPdf.firmar", e);
			throw new GadirServiceException("Error en FirmaPdf.firmar", e);
		}

		return result;
	}

	public static void prueba() {
		try {
			File pdfEntrada = new File("c:/etir/prueba.pdf");
			FileInputStream fileInputStream = new FileInputStream(pdfEntrada);
			byte[] result = firmar(fileInputStream);
			final File saveFile = File.createTempFile("TSA-", ".pdf");
			final OutputStream os = new FileOutputStream(saveFile);
			os.write(result);
			os.flush();
			os.close();
			System.out.println("Temporal para comprobacion manual: " + saveFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
