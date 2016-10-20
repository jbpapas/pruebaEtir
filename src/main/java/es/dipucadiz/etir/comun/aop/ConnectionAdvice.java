package es.dipucadiz.etir.comun.aop;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;

import org.springframework.aop.AfterReturningAdvice;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import es.dipucadiz.etir.comun.utilidades.DatosSesion;


public class ConnectionAdvice implements AfterReturningAdvice {

	protected static final Logger LOG = LoggerFactory.getLogger(ConnectionAdvice.class);

	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		try {
			String login = DatosSesion.getLogin();
			Connection con = (Connection) returnValue;
			CallableStatement cs = con.prepareCall("begin dbms_session.set_identifier(?);end;");
			cs.setString(1, login);
			cs.execute();
			cs.close();
			LOG.trace("dbms_session.set_identifier: " + login);
		} catch (NullPointerException e) {
			LOG.trace("No puede dbms_session.set_identifier (¿sesión no iniciada?)", e);
		} catch (Exception e) {
			LOG.debug("No puede dbms_session.set_identifier", e);
		}
	}
	
}
