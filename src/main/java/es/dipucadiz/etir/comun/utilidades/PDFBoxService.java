package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.TransformerException;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.type.BadFieldValueException;
import org.apache.xmpbox.xml.XmpSerializer;

import com.rits.cloning.Cloner;

import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.ImpresionInformeVO;
import es.dipucadiz.etir.comun.vo.ImpresionParametroVO;

final public class PDFBoxService {
	
	static final double FACTOR = 29.49852507;
	static final double MARGEN_X = 0.6;
	static final double MARGEN_Y = 0.5;
	static final String CODBAR_LITERAL_INI = "{CODBAR";
	static final String CODBAR_LITERAL_FIN = "}";
	static final String CODBAR_LITERAL_SEPARADOR = "#";
	static final String encoding = "UTF8";

	public static List<ImpresionInformeVO> procesarT2p(String rutaT2p, String rutaPlantillaPdf, int countT2p, ImpresionParametroVO impresionParametroVO, Long coPlantilla) throws IOException, GadirServiceException {
		List<ImpresionInformeVO> informesImprimir = new ArrayList<ImpresionInformeVO>();
		try {
			final char barra = Impresion.LINUX.equals(Impresion.tipoSistema) ? '/' : '\\';
			final String rutaPdf = GadirConfig.leerParametro("ruta.informes.generados") + impresionParametroVO.getEjecucionDTO().getCoEjecucion() + barra;
			final File outDir = new File(rutaPdf); 
			if (!outDir.exists() && !outDir.mkdirs()) {
				throw new GadirServiceException("No se pudo crear la carpeta " + outDir.getName() + " desde PDFBoxService");
			}
			final String rutaPlantillas = GadirConfig.leerParametro("ruta.informes.plantillas");
			
			List<PDDocument> pdDocs = new ArrayList<PDDocument>();
			List<FDFDocument> fdfDocuments = new ArrayList<FDFDocument>();
			inicializarPlantilla(rutaPlantillaPdf, pdDocs, fdfDocuments, rutaPlantillas);
			
			final File file = new File(rutaT2p);
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), Impresion.CODIFICACION));
	
			String plantilla = rutaPlantillaPdf.substring(rutaPlantillaPdf.lastIndexOf(barra) + 1);
			impresionParametroVO.setNombrePdf(plantilla.substring(0, plantilla.lastIndexOf('.')));
	
			HashMap<String, String> etiquetas = new HashMap<String, String>();
			HashMap<String, String> etiquetasConfig = new HashMap<String, String>();
			HashMap<String, Integer> tablas = new HashMap<String, Integer>();
			
			boolean zonaConfig = false;
			String zonaTabla = null;
			String line;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				if (Impresion.ETIQUETA_CONFIG.equals(line)) {
					zonaConfig = true;
				} else if (Impresion.ETIQUETA_CABEZA.equals(line)) {
					zonaConfig = false;
				} else if (Impresion.ETIQUETA_CUERPO.equals(line)) {
					zonaConfig = false;
					zonaTabla = null;
					tablas = new HashMap<String, Integer>();
					impresionParametroVO.setEtiquetasConfig(etiquetasConfig); // Inicia cuerpo, ya podemos guardar los valores de configuración
					
					if (impresionParametroVO.getOrdenPdf() > 0) {
						informesImprimir = finalizarMerge(pdDocs, fdfDocuments, etiquetas, rutaPdf, impresionParametroVO, informesImprimir, coPlantilla);
					}
					etiquetas = new HashMap<String, String>();
					impresionParametroVO.setOrdenPdf(impresionParametroVO.getOrdenPdf() + 1);
				} else if (Impresion.ETIQUETA_FINTABLA.equals(line)) {
					zonaTabla = null;
				} else {
					String etiqueta = line.substring(0, 5);
					String valor = line.length() > 5 ? line.substring(5) : "";
					valor = LenguajilloUtilBase.parsearLenguajillo(valor);
					
					// Tratamiento de etiquetas
					if (Impresion.ETIQUETA_NOM_PDF.equals(etiqueta)) {
						impresionParametroVO.setNombrePdf(valor);
					} else if (Impresion.ETIQUETA_BDGRU.equals(etiqueta)) {
						impresionParametroVO.setCoBDDocumentalGrupo(valor); // Si procede, guardar el código de agrupación para uso en base de datos documental
					} else if (Impresion.ETIQUETA_BDEXP.equals(etiqueta)) {
						impresionParametroVO.setCoExpediente(valor); // Si procede, guardar el código de agrupación para uso en base de datos documental
					} else if (Impresion.ETIQUETA_LINEA.equals(etiqueta)) {
						zonaTabla = valor;
						if (tablas.containsKey(valor)) {
							tablas.put(valor, tablas.get(valor)+1);
						} else {
							tablas.put(valor, 1);
						}
					} else {
						if (zonaConfig) {
							if (Impresion.ETIQUETA_AUPLA.equals(etiqueta) && Utilidades.isNotEmpty(valor) && valor.toLowerCase().endsWith(".pdf")) {
								inicializarPlantilla(rutaPlantillas + valor.trim(), pdDocs, fdfDocuments, rutaPlantillas);
							} else {
								etiquetasConfig.put(etiqueta, valor);
							}
						} else {
							if (zonaTabla == null) {
								etiquetas.put(etiqueta, valor);
							} else {
								etiquetas.put(etiqueta + "-" + zonaTabla + "L" + tablas.get(zonaTabla), valor);
							}
						}
					}
				}
				
			}
			informesImprimir = finalizarMerge(pdDocs, fdfDocuments, etiquetas, rutaPdf, impresionParametroVO, informesImprimir, coPlantilla);
			for (PDDocument pdDoc : pdDocs) {
				if (pdDocs != null) pdDoc.close();
			}
		} catch (IOException e) {
			throw new GadirServiceException(e.getMessage(), e);
		} catch (BadFieldValueException e) {
			throw new GadirServiceException(e.getMessage(), e);
		} catch (TransformerException e) {
			throw new GadirServiceException(e.getMessage(), e);
		}

		return informesImprimir;
	}
	
	// https://www.pdf-online.com/osa/validate.aspx
	private static void inicializarPlantilla(String rutaPlantillaPdf, List<PDDocument> pdDocs, List<FDFDocument> fdfDocuments, String rutaPlantillas) throws IOException, BadFieldValueException, TransformerException {
		// Abrir plantilla PDF definida
		File fPlantilla = new File(rutaPlantillaPdf);
		PDDocument pdDoc = PDDocument.load(fPlantilla);
		PDDocumentCatalog pdCatalog = pdDoc.getDocumentCatalog();

		// Incluir XMP metadata para PDF/A
		PDMetadata metadata = new PDMetadata(pdDoc);
		pdCatalog.setMetadata(metadata);
		XMPMetadata xmp = XMPMetadata.createXMPMetadata();
        PDFAIdentificationSchema pdfaid = xmp.createAndAddPFAIdentificationSchema();
        pdfaid.setConformance("B");
        pdfaid.setPart(3);
        pdfaid.setAboutAsSimple("PDFBox PDFA sample");
        XmpSerializer serializer = new XmpSerializer();
        ByteArrayOutputStream metaBaos = new ByteArrayOutputStream();
        serializer.serialize(xmp, metaBaos, true);
        metadata.importXMPMetadata(metaBaos.toByteArray());

		// Incluir colores para PDF/A
		InputStream colorProfile = new FileInputStream(rutaPlantillas + "sRGB_Color_Space_Profile.icm");
		PDOutputIntent oi = new PDOutputIntent(pdDoc, colorProfile); 
		oi.setInfo("sRGB IEC61966-2.1"); 
		oi.setOutputCondition("sRGB IEC61966-2.1"); 
		oi.setOutputConditionIdentifier("sRGB IEC61966-2.1"); 
		oi.setRegistryName("http://www.color.org"); 
		pdCatalog.addOutputIntent(oi);
		
		// Terminar de agregar plantilla a la lista de plantillas
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		pdCatalog.getAcroForm().exportFDF().save(baos);

		pdDocs.add(pdDoc);
		fdfDocuments.add(FDFDocument.load(new ByteArrayInputStream(baos.toByteArray())));
	}

	private static List<ImpresionInformeVO> finalizarMerge(List<PDDocument> pdDocs, List<FDFDocument> fdfDocuments, HashMap<String, String> etiquetas, String rutaPdf, ImpresionParametroVO impresionParametroVO, List<ImpresionInformeVO> informesImprimir, Long coPlantilla) throws IOException, GadirServiceException {
		String rutaPdfFinal = crearPdf(pdDocs, fdfDocuments, impresionParametroVO.getNombrePdf(), etiquetas, rutaPdf, impresionParametroVO);

		impresionParametroVO.setEtiquetas(etiquetas);
		ImpresionInformeVO informeVO = Impresion.realizarActuaciones(rutaPdfFinal, impresionParametroVO, impresionParametroVO.getEjecucionDTO().getCoAcmUsuario());
		Impresion.altaInforme(impresionParametroVO.getEjecucionDTO(), rutaPdfFinal, coPlantilla, impresionParametroVO.getEjecucionDTO().getCoAcmUsuario());

		if (informeVO != null) informesImprimir.add(informeVO);
		return informesImprimir;
	}
	
	private static String crearPdf(List<PDDocument> pdDocsIn, List<FDFDocument> fdfDocuments, String nombrePdf, Map<String, String> etiquetas, String rutaPdf, ImpresionParametroVO impresionParametroVO) throws IOException, GadirServiceException {
		etiquetas.put(Impresion.ETIQUETA_EJECUCIO, Long.toString(impresionParametroVO.getEjecucionDTO().getCoEjecucion()));
		etiquetas.put(Impresion.ETIQUETA_ORDEN, Integer.toString(impresionParametroVO.getOrdenPdf()));

		int iPlantilla = "S".equals(etiquetas.get(Impresion.ETIQUETA_XXPLA)) ? 1 : 0;
		
		Cloner cloner = new Cloner();
		PDDocument clone = cloner.deepClone(pdDocsIn.get(iPlantilla));
		
		String rutaPdfFinal = rutaPdf + 
				Impresion.soloCaracteresSeguros(Utilidades.sustituir(nombrePdf, " ", "_"), false) + "-" + 
				impresionParametroVO.getOrdenTxt() + "_" +
				impresionParametroVO.getEjecucionDTO().getCoAcmUsuario() + "_" + 
				Utilidades.dateToYYYYMMDDHHMMSS(new Date()) + "_" + impresionParametroVO.getOrdenTxt() + "_" + impresionParametroVO.getOrdenPdf() +
				".pdf";
		PDDocumentCatalog pdCatalog = clone.getDocumentCatalog();
		
		// Etiquetas de código de barras
		List<String> listaCoordenadas = new ArrayList<String>();
		List<String> listaCodbars = new ArrayList<String>();
		Iterator<Entry<String, String>> it = etiquetas.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> etiqueta = it.next();
			if (etiqueta.getValue().startsWith(CODBAR_LITERAL_INI)) {
				String[] split = etiqueta.getValue().split(CODBAR_LITERAL_FIN, 2);
				String codigoBarras = LenguajilloUtilBase.parsearLenguajillo(split[1]).trim();
				if (Utilidades.isNotEmpty(codigoBarras)) {
					String[] posiciones = split[0].split(CODBAR_LITERAL_SEPARADOR);
					for (int i=1; i<posiciones.length; i++) {
						listaCoordenadas.add(posiciones[i]);
						listaCodbars.add(codigoBarras);
					}
				}
			}
		}
		if (!listaCodbars.isEmpty()) {
			// Colocar códigos de barras en PDF
			int paginas = pdCatalog.getPages().getCount();
			PDPageContentStream[] pageContentStream = new PDPageContentStream[paginas];
			for (int i=0; i<paginas; i++) {
				pageContentStream[i] = new PDPageContentStream(clone, pdCatalog.getPages().get(i), AppendMode.APPEND, true, true);
			}
			
			for (int i=0; i<listaCodbars.size(); i++) {
				String[] coordenadas = listaCoordenadas.get(i).split(";"); // Página, Pos X cm, Pos Y cm, Altura cm, Ancho tanto por cien %
				int pagina = Integer.valueOf(coordenadas[0].trim()) - 1;
				if (pagina > paginas) {
					throw new GadirServiceException("Página " + (pagina+1) + " no existente en plantilla al procesar código de barras: " + listaCoordenadas.get(i));
				}
				double posX = Double.valueOf(coordenadas[1].trim().replace(',', '.'));
				double posY = Double.valueOf(coordenadas[2].trim().replace(',', '.'));
				double altura = Double.valueOf(coordenadas[3].trim().replace(',', '.'));
				float factorAncho = Float.valueOf(coordenadas[4].trim().replace(',', '.'));

				try {
					Barcode barcode = BarcodeFactory.createCode128(listaCodbars.get(i));
					PDFBoxOutput box = new PDFBoxOutput(pageContentStream[pagina], Math.round((posX-MARGEN_X)*FACTOR), Math.round((posY-MARGEN_Y)*FACTOR), Math.round(altura*FACTOR), factorAncho); // dividir 29.5 (+0.6x / +0.5y)
					barcode.output(box);
				} catch (BarcodeException e) {
					throw new GadirServiceException("Error creando código barras: " + listaCodbars.get(i), e);
				} catch (OutputException e) {
					throw new GadirServiceException("Error añadiendo cuadro de código barras: " + listaCodbars.get(i), e);
				}
			}
			
			for (int i=0; i<paginas; i++) {
				pageContentStream[i].close();
			}
		}
		
		// Formulario
		PDAcroForm acroForm = pdCatalog.getAcroForm();
		acroForm.importFDF(fdfDocuments.get(iPlantilla));
		acroForm.setNeedAppearances(false);
		
		// Sustituir etiquetas en campos de formulario
		for (PDField pdField : acroForm.getFields()) {
			if (pdField instanceof PDTextField) {
				PDTextField pdtf = (PDTextField) pdField;
				String valorNuevo = sustituirEtiquetas(pdtf.getValueAsString(), etiquetas);
				pdtf.setValue(valorNuevo);
				pdtf.setReadOnly(true);
				pdtf.setDoNotScroll(true);
				pdtf.setDoNotSpellCheck(true);
			}
		}
		
		clone.save(rutaPdfFinal);
		return rutaPdfFinal;
	}

	private static String sustituirEtiquetas(String campo, Map<String, String> etiquetas) {
		String result = "";
		String etiqueta = "";
		boolean isEtiqueta = false;
		for (int i=0; i<campo.length(); i++) {
			if (campo.charAt(i) == '#') {
				isEtiqueta = !isEtiqueta;
				if (!isEtiqueta) {
					// Hemos terminado de leer el nombre de una etiqueta, vamos a sustituirla
					if (etiquetas.containsKey(etiqueta)) {
						result += etiquetas.get(etiqueta);
					} else {
						result += "#" + etiqueta + "#";
					}
					etiqueta = "";
				}
			} else {
				if (!isEtiqueta) {
					result += campo.charAt(i);
				} else {
					etiqueta += campo.charAt(i);
				}
			}
		}
		return result;
	}

}
