<%@page import="es.dipucadiz.etir.comun.utilidades.ControlTerritorial"%>
<%@page import="es.dipucadiz.etir.comun.utilidades.Utilidades"%>
<%@page import="es.dipucadiz.etir.comun.utilidades.DatosSesion"%>
<%@ include file="../../taglibs.jsp"%>
<%@ include file="../../parametrizacion.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div id="cabecera">
	<div id="cabecera-left">
		<div id="cabecera-imagen">
			<s:url action="bienvenido" namespace="/" id="base_inicio" includeParams="none"/>
			<s:a theme="simple" href="%{#base_inicio}" title="%{getText('general.inicio')}">
				<img height="49" src="<s:url value="/image/logodipu.png"/>" title="<s:text name="general.dipu"/>" alt="<s:text name="general.dipu"/>"/>
			</s:a>
		</div>				
	</div>
</div>
<div id="linea-info" >
</div>




<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>

<s:set name="pantallaBloqueo" value="'usuarioBloqueado'.equals(actionNameTriki)"/>


	<table id="login-acceso" style="" cellpadding="0" cellspacing="0">
		<colgroup>
			<col style="width:26px" />
			<col />
			<col style="width:19px" />
			<col />
			<col style="width:29px" />
		</colgroup>
		<tbody>
			<tr>
				<td style="height:30px;background-image:url(<s:url value="/image/nube-menu/top-izq.png"/>);background-repeat:no-repeat;background-position:right"></td>
				<td style="height:30px;background-image:url(<s:url value="/image/nube-menu/top-con.png"/>);background-repeat:repeat-x;white-space:nowrap;overflow:hidden" valign="bottom" id="solapa-nube">
					<span class="label5"><s:text name="login.bienvenida"/></span>
				</td>
				<td style="height:30px;background-image:url(<s:url value="/image/nube-menu/top-der.png"/>);background-repeat:no-repeat"></td>
				<td style="height:30px;background-image:url(<s:url value="/image/nube-menu/top-sub.png"/>);background-size:100%"></td>
				<td style="height:30px"></td>
			</tr>
			<tr>
				<td style="height:13px;background-image:url(<s:url value="/image/nube-menu/mid-izq.png"/>);background-repeat:repeat-y;background-position:right"></td>
				<td style="height:13px;background-color:white" colspan="3"></td>
				<td style="height:13px;background-image:url(<s:url value="/image/nube-menu/seg-der.png"/>);background-repeat:no-repeat"></td>
			</tr>
			<tr>
				<td style="height:501px;background-image:url(<s:url value="/image/nube-menu/mid-izq.png"/>);background-repeat:repeat-y;background-position:right"></td>
				<td style="height:501px;background-color:white" colspan="3" valign="top">
<%--
					<p style="font-size:12px">
						El Servicio Provincial de Recaudación y Gestión Tributaria, es el departamento de la Diputación Provincial de Cádiz encargado de proporcionar, a los ciudadanos y entidades públicas, una infraestructura de gestión tributaria con todas las garantías jurídicas.
					</p>
--%>
					<div style="margin-top:15px">
						<span class="label5"><s:text name="login.acceso"/></span>
					</div>
					
					<div class="cajaTexto">
						<%
						if (request.getParameter("login_error") != null){
						%>
						<br/>
						<div class="errores">
							<div class="errorMessage">
								<p>
								<% if (request.getParameter("login_error").equals("4")) { %>
								Usuario ya logado
								<% } else if (request.getParameter("login_error").equals("5")){   %>
									El usuario ha sido dado de baja. Contacte con el Administrador.
								<% } else if (request.getParameter("login_error").equals("3")){ // Cualquier otro error es debido a usuario/contraseña %>
									Cuenta de usuario caducada. Contacte con el Administrador.						
								<% } else{%>
								Datos incorrectos
								<% } %>
								</p>
							</div>
						</div>
						<%
						}
						%>
						
						<%
						if (request.getParameter("fin_sesion") != null){
						%>
						<br/>
						<div class="errores">
							<div class="errorMessage">
								<p>La sesión ha expirado</p>
							</div>
						</div>
						<%
						}
						%>
		
						<%
						if (request.getParameter("logout") != null){
						%>
						<br/>
						<div class="mensajes">
							<div class="actionMessage">
								<p>Desconexión correcta</p>
							</div>
						</div>
						<%
						}
						%>
						
						<s:if test="#pantallaBloqueo">
						<br/>
						<div class="errores">
							<div class="errorMessage">
								<p>El usuario ya se encuentra activo en otra sesión. Si procede con el acceso al sistema, dicha sesión será invalidada.</p>
							</div>
						</div>
						</s:if>
		
						<form action="/etir/j_acegi_security_check">	
					
							<div style="clear:both;margin-top:5px">
								<div style="float:left;clear:left;width:90px">
									<s:label for="j_username" id="lbl_usuario" value="%{getText('login.usuario')}" theme="simple"/>
								</div>
								<s:textfield id="j_username" name="j_username" maxlength="10" size="22" theme="simple" cssStyle="float:left;width:100px" disabled="%{#pantallaBloqueo}"/>
							</div>
							
							<div style="clear:both;margin-top:5px">
								<div style="float:left;clear:left;width:90px">
									<s:label for="j_password" id="lbl_contrasena" value="%{getText('login.contrasena')}" theme="simple"/>
								</div>
								<s:password id="j_password" name='j_password' maxlength="50" size="22" theme="simple" cssStyle="float:left;width:100px" disabled="%{#pantallaBloqueo}" />
							</div>
							
							<div style="clear:both;margin-top:5px">
								<s:if test="!#pantallaBloqueo">
									<s:submit id="btn_aceptar" theme="simple" value="%{getText('login.aceptar')}" title="%{getText('login.aceptar')}"/>
								</s:if>
							</div>
						
						</form>
						
						<s:if test="#pantallaBloqueo">
							<s:form>
								<s:submit value="Proceder" method="botonProcederLogin" theme="simple" />
								<s:submit value="Cancelar" method="botonLogout" theme="simple" />
							</s:form>
						</s:if>
						
						<%
						String anterior=(String)session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY);
						if (anterior!=null && !anterior.equals("")){
						%>
						<s:if test="!#pantallaBloqueo">
							<div class="botonlogin">
							<s:form namespace="/" action="recordarContrasena" theme="simple">
							<input type="hidden" name="j_username" value="<%=anterior %>"/>
							<s:submit theme="simple" value="%{getText('button.recordar.contrasena')}" title="%{getText('button.recordar.contrasena')}" />
							</s:form>
							</div>
						</s:if>
						<%
						}
						%>
					</div>

				</td>
				<td style="height:501px;background-image:url(<s:url value="/image/nube-menu/mid-der.png"/>);background-repeat:repeat-y"></td>
			</tr>
			<tr>
				<td style="height:18px;background-image:url(<s:url value="/image/nube-menu/bot-izq.png"/>);background-repeat:no-repeat;background-position:top right"></td>
				<td style="height:18px;background-image:url(<s:url value="/image/nube-menu/bot-mid.png"/>);background-repeat:repeat-x" colspan="3"></td>
				<td style="height:18px;background-image:url(<s:url value="/image/nube-menu/bot-der.png"/>);background-repeat:no-repeat"></td>
			</tr>
		</tbody>
	</table>
	
	<div id="login-img-bienvenida" style="margin-left:5px">
		<div id="cabecera-center" align="right">
			<img src="<s:url value="/image/login.png"/>" alt="<s:text name="general.cabecera.titulo"/>"/>			
		</div>
	</div>
	
	<div id="copyright" style="position:absolute;bottom:0;left:0;background-color: white;height:22px">
		<div id="copyright-bottom">
			<div style="float:left;height:17px;overflow:hidden;">
				&copy; Desarrollado por EPICSA
			</div>
		</div>
	</div>

<script type="text/javaScript">
$(document).ready(function(){
	<% if (Utilidades.isNotEmpty(DatosSesion.getLogin()) && !"anonymous".equals(DatosSesion.getLogin()) && !ControlTerritorial.isUsuarioExperto()) { %>
	if (window.name != 'ventanagadir') {
		window.location = '/etir/';
	}
	<% } %>
	
	var solapaNube = $('#solapa-nube');
	var acnhoSolapaNube = solapaNube.width();
	var anchoNube = $('#login-acceso').width();
	var porc = acnhoSolapaNube / anchoNube; 
	if (porc > 0.6) {
		solapaNube.width('60%');
	}
});
</script>
	