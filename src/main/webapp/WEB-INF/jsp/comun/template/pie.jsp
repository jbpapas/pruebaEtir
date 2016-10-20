<%@ include file="../../taglibs.jsp"%>

<%
String ventanaBotonLateral="";
try{
	ventanaBotonLateral=request.getParameter("ventanaBotonLateral");
	if (ventanaBotonLateral==null || ventanaBotonLateral.equals("")){
		ventanaBotonLateral=(String)request.getAttribute("ventanaBotonLateral");
	}
}catch(Exception e){
}
if (ventanaBotonLateral==null)ventanaBotonLateral="false";
%>

<%
if (!ventanaBotonLateral.equals("true")){ 
%>
<s:if test="#notificaciones3 != 'bienvenido'">
	<script type="text/javaScript">
		$(document).ready(function(){
			preparaEventos(true);
			//programacionActualizacionNumNotificaciones(false); Se deshabilita la actualización de notificaciones por rendimiento
	
		});
	</script>
</s:if>
<s:set name="notificaciones1" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNotificacionesTipo1()"/>
<s:set name="notificaciones" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNumNotificacionesTipo1()"/>
<s:set name="notificaciones2" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNotificacionesTipo2()"/>
<%
}
%>

<div id="copyright" style="position:absolute;bottom:0;left:0;background-color: white;height:22px">
	<div id="copyright-bottom">
		<div style="float:left;height:17px;overflow:hidden;">
			&copy; Desarrollado por EPICSA
		</div>
	</div>
</div>

<%
if (!ventanaBotonLateral.equals("true")){ 
%>
<div id="pie" style="position:absolute;bottom:0;right:0;background-color: white">
	
	<div id="pie-bottom">
	
		<div style="float:left;width:95%;height:17px;overflow:hidden;">
			<a id="boton-notificaciones" href="#" style="visibility:%{#miDisplay}">
				<span id="textoNotificaciones">
					Ver notificaciones
					<%--
					<s:if test="#notificaciones1==null || #notificaciones1.size==0">
						No tiene notificaciones.
						<s:set name="miDisplay" value="hidden"/>
					</s:if>
					<s:else>
						Notificaciones:
						<s:set name="miDisplay" value="visible"/>
					</s:else>
					--%>
				</span>
				<%--
				<span id="span-numero-notificaciones" style="background-color:green;color:white;width:16px;font-weight:bold;visibility:hidden">
					&nbsp;
					<span id="numero-notificaciones">
						<s:property value="#notificaciones"/>
					</span>
					&nbsp;
				</span>
				--%>
			</a>
		</div>		
		<s:if test="#notificaciones3 != 'bienvenido'">
			<s:set name="notificaciones3" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNotificacionesTipo3T()"/>
		</s:if>		
	</div>
</div>
<%
}
%>

<%
if (!ventanaBotonLateral.equals("true")) { 
%>
	<div id="div-notificaciones" style="
				background-color:#FFFFFF;
				border-color:#7B9FB7;
				border-width:1px 0px 0px 1px;
				display:none;
				overflow:hidden;
				position:absolute;
				bottom:0;
				right:0;
				width:330px;
				z-index:2;
				padding-left:4px;
				padding-bottom:5px;
				border-style:dashed dashed dashed dashed;
				color:#333333;
				direction:ltr;
	">
		<%@ include file="notificaciones.jsp" %>
	</div>
	
	<s:if test="!actionName.equals(\"bienvenido\")">	
		<div id="overlaysContainer">
			<%@ include file="notificaciones3T.jsp" %>				
		</div>
	</s:if>
		
	<s:if test="#notificaciones2!=null && #notificaciones2.size>0">	
		<%@ include file="notificaciones2.jsp" %>	
	</s:if>	
<%
}
%>
