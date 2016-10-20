/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.PlanificacionBO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.PlanificacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;

/**
 * Ejecución de trabajos planificados.
 */
public final class Planificador {

	public static final String CO_USUARIO_PLANIFICADOR = "planifica";
	public static final String CO_USUARIO_PLANIFICADOR_PWD = "1234512345";

	private Planificador() {}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			PostBatch.setEjecucionBatch(true);
			final String[] paths = {"classpath*:/*/spring*/applicationContext*.xml"}; // Arranca el contexto necesario en modo batch
			final ApplicationContext context = new ClassPathXmlApplicationContext(paths);

			DaoAuthenticationProvider daoAuthenticationProvider = (DaoAuthenticationProvider) context.getBean("daoAuthenticationProvider");
			Authentication auth = new UsernamePasswordAuthenticationToken(CO_USUARIO_PLANIFICADOR, CO_USUARIO_PLANIFICADOR_PWD);
			Authentication result = daoAuthenticationProvider.authenticate(auth);
			SecurityContext ctx = new SecurityContextImpl();
			ctx.setAuthentication(result);
			SecurityContextHolder.setContext(ctx);

			AcmUsuarioBO acmUsuarioBO = (AcmUsuarioBO) context.getBean("acmUsuarioBO");
			final AcmUsuarioDTO acmUsuarioPlanificadorDTO = acmUsuarioBO.findById(CO_USUARIO_PLANIFICADOR);
			
			String horaInicio;
			if (args.length > 0 && Utilidades.isNotEmpty(args[0])) {
				horaInicio = args[0].trim();
			} else {
				horaInicio = "01";
			}

			lanzarProcesoAutomatico(acmUsuarioPlanificadorDTO, horaInicio);
			
		} catch (GadirServiceException e) {
			System.err.println("Comprueba la existencia del usuario " + CO_USUARIO_PLANIFICADOR);
			e.printStackTrace();
		}
	}

	private static void lanzarProcesoAutomatico(AcmUsuarioDTO acmUsuarioPlanificadorDTO, String horaInicio){
		System.out.println("Lanzando proceso tramitador automático.");

		final List<String> parametros = new ArrayList<String>();
		final AccesoPlantillaVO accesoPlantillaVO = new AccesoPlantillaVO();
		final String[] paths = {"classpath*:/*/spring*/applicationContext*.xml"}; // Arranca el contexto necesario en modo batch
		final ApplicationContext context = new ClassPathXmlApplicationContext(paths);

		DetachedCriteria dc = DetachedCriteria.forClass(PlanificacionDTO.class);		
		dc.add(Restrictions.eq("boActivo", true));
		dc.add(Restrictions.eq("hoInicio", horaInicio));
		dc.addOrder(Order.asc("coPlanificacion"));
		
		List<PlanificacionDTO> listaPlanificacion;
		try {
			PlanificacionBO planificacionBO = (PlanificacionBO) context.getBean("planificacionBO");
			listaPlanificacion = planificacionBO.findByCriteria(dc);

			for(PlanificacionDTO planificacionDTO : listaPlanificacion){			
				Batch.lanzar(planificacionDTO.getProcesoDTO().getCoProceso(), acmUsuarioPlanificadorDTO.getCoAcmUsuario(), parametros, acmUsuarioPlanificadorDTO.getImpresora(), accesoPlantillaVO);
			}
		} catch (GadirServiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
