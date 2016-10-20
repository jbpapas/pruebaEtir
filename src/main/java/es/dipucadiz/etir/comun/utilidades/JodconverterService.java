package es.dipucadiz.etir.comun.utilidades;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import es.dipucadiz.etir.comun.config.GadirConfig;

/**
 * Servicio para convertir un ODT a un PDF localmente.
 */
final public class JodconverterService {
	
	private static final Log LOG = LogFactory.getLog(JodconverterService.class);

	public static final String PARAMETER_OFFICE_PORTS = "office.ports";
	public static final String PARAMETER_OFFICE_PIPES = "office.pipes";
	public static final String PARAMETER_OFFICE_MAX_TASKS = "office.maxTasksPerProcess";
	public static final String PARAMETER_OFFICE_RETRY_TIMEOUT = "office.retryTimeout";
	public static final String PARAMETER_OFFICE_EXECUTION_TIMEOUT = "office.taskExecutionTimeout";
	public static final String PARAMETER_OFFICE_QUEUE_TIMEOUT = "office.taskQueueTimeout";
	public static final String PARAMETER_OFFICE_HOME = "office.home";
	public static final String PARAMETER_OFFICE_PROFILE = "office.profile";

	private static OfficeManager officeManager;
	private static OfficeDocumentConverter documentConverter;

	public JodconverterService() {
		create();
	}
	
	private static boolean isServicioNecesario() {
		return !PostBatch.isEjecucionBatch() && "servidoraplicaciones".equals(GadirConfig.leerParametro("metodo.crear.pdf.online"));
	}
		
	public static void create() {
		LOG.info("Iniciando JodconverterService.");
		
		if (!isServicioNecesario()) {
			LOG.warn("JodconverterService no será iniciado - no en uso.");
			return;
		}
		
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		
		// Varios puertos
		String officePortsParam = GadirConfig.leerParametro(PARAMETER_OFFICE_PORTS);
		if (officePortsParam != null) {
			List<String> portNumbers = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(officePortsParam, ",");
			while (st.hasMoreTokens()) {
				portNumbers.add(st.nextToken());
			}
			int[] intPortNumbers = new int[portNumbers.size()];
			int i=0;
			for (String portNumber : portNumbers) {
				intPortNumbers[i++] = Integer.parseInt(portNumber);
			}
			configuration.setPortNumbers(intPortNumbers);
		}
		
		// Varios pipes
		String officePipesParam = GadirConfig.leerParametro(PARAMETER_OFFICE_PIPES);
		if (officePipesParam != null) {
			List<String> pipeNames = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(officePipesParam, ",");
			while (st.hasMoreTokens()) {
				pipeNames.add(st.nextToken());
			}
			String[] stringPipeNames = new String[pipeNames.size()];
			int i=0;
			for (String pipeName : pipeNames) {
				stringPipeNames[i++] = pipeName;
			}
			configuration.setPipeNames(stringPipeNames);
		}
		
		// maxTasksPerProc
		String officeMaxTasksPerProc = GadirConfig.leerParametro(PARAMETER_OFFICE_MAX_TASKS);
		if (officeMaxTasksPerProc != null) {
			configuration.setMaxTasksPerProcess(Integer.parseInt(officeMaxTasksPerProc));
		}
		
		// retryTimeout
		String officeRetryTimeout = GadirConfig.leerParametro(PARAMETER_OFFICE_RETRY_TIMEOUT);
		if (officeRetryTimeout != null) {
			configuration.setRetryTimeout(Integer.parseInt(officeRetryTimeout));
		}
		
		// taskExecTimeout
		String officeTaskExecutionTimeout = GadirConfig.leerParametro(PARAMETER_OFFICE_EXECUTION_TIMEOUT);
		if (officeTaskExecutionTimeout != null) {
			configuration.setTaskExecutionTimeout(Integer.parseInt(officeTaskExecutionTimeout));
		}
		
		// taskQueueTimeout
		String officeTaskQueueTimeout = GadirConfig.leerParametro(PARAMETER_OFFICE_QUEUE_TIMEOUT);
		if (officeTaskQueueTimeout != null) {
			configuration.setTaskQueueTimeout(Integer.parseInt(officeTaskQueueTimeout));
		}
		
		String officeHomeParam = GadirConfig.leerParametro(PARAMETER_OFFICE_HOME);
		if (officeHomeParam != null) {
		    configuration.setOfficeHome(new File(officeHomeParam));
		}
		String officeProfileParam = GadirConfig.leerParametro(PARAMETER_OFFICE_PROFILE);
		if (officeProfileParam != null) {
		    configuration.setTemplateProfileDir(new File(officeProfileParam));
		}

		officeManager = configuration.buildOfficeManager();
		documentConverter = new OfficeDocumentConverter(officeManager);
		officeManager.start();
		
		LOG.info("JodconverterService iniciado.");
	}
	
	public static void destroy() {
		officeManager.stop();
		LOG.warn("JodconverterService ha sido parado.");
	}
	
	public static OfficeManager getOfficeManager() {
        return officeManager;
    }

	public static OfficeDocumentConverter getDocumentConverter() {
        return documentConverter;
    }

}
