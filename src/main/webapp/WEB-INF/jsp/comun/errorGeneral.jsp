<%@ include file="../taglibs.jsp"%>
<div class="errores">
	<div class="titulo">
		<h1><s:text name="error.titulo"/></h1>
	</div>
	<s:if test="%{!actionErrors.isEmpty()}">
		<div>
			<s:actionerror/>
		</div>
	</s:if>
	<div id="texto_excepcion">
		<p><s:property value="@es.dipucadiz.etir.comun.exception.ExceptionUtil@procesarExcepcion(exception)"/></p>
	</div>
</div>
<div><h2><s:text name="error.contacto"/></h2></div>