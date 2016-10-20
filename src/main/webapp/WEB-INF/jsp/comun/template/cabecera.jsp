<%@ include file="../../taglibs.jsp"%>

<div id="cabecera" >
	<div id="cabecera-left">
		<div id="cabecera-imagen">
			<s:url action="bienvenido" namespace="/" id="base_inicio" includeParams="none"/>
			<s:a theme="simple" href="%{#base_inicio}" title="%{getText('general.inicio')}">
				<img height="49" src="<s:url value="/image/logodipu.png"/>" title="<s:text name="general.dipu"/>" alt="<s:text name="general.dipu"/>"/>
			</s:a>
		</div>				
	</div>
	<div id="cabecera-center">
		<%
			String entornoOracle=es.dipucadiz.etir.comun.config.GadirConfig.leerParametro("entorno.oracle");
			if (entornoOracle!=null && entornoOracle.equalsIgnoreCase("desarrollo")){
		%>
			<img height="85" src="<s:url value="/image/logoetir-desarrollo.png"/>" alt="<s:text name="general.cabecera.titulo"/>"/>  
		<%
			}else if (entornoOracle!=null && entornoOracle.equalsIgnoreCase("preproduccion")){
		%>
			<img height="85" src="<s:url value="/image/logoetir-preproduccion.png"/>" alt="<s:text name="general.cabecera.titulo"/>"/>  
		<%
			}else{
		%>
			<img height="85" src="<s:url value="/image/logoetir.png"/>" alt="<s:text name="general.cabecera.titulo"/>"/>
		<%
			}
		%>
		
	</div>
	<div id="cabecera-right">
		<img height="89" src="<s:url value="%{@es.dipucadiz.etir.comun.utilidades.DatosSesion@getEscudo()}"/>" alt="<s:text name="general.cabecera.titulo"/>" title="<s:property value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getCodigoTerritorial().getNombre()"/>" />
	</div>
</div>
