<%-- JSP que contiene las capas que contendran los mensajes de error, validación y de información. --%>
<%@ include file="../../taglibs.jsp"%>

<%-- Mensajes de error --%>
<s:if test="%{hasActionErrorsSession() || !fieldErrors.isEmpty()}">
<div id="mensajeError" class="errores">
	<div class="titulo">
		<p><s:text name="error.titulo"/></p>
	</div>
	<div>
		<%-- <s:actionerror/> --%>
		<s:iterator value="actionErrorsSession" status="estado">
		<ul class="errorMessage">
			<li>
				<span><s:property></s:property></span>
			</li>
		</ul>
		</s:iterator>
		<s:fielderror/>
	</div>
</div>
</s:if>

<%-- Mensajes de información --%>
<s:if test="%{hasActionMessagesSession()}">
<div id="mensajeInfo" class="mensajes">
	<div class="titulo">
		<p><s:text name="mensajes.titulo"/></p>
	</div>
	<div>
		<%-- <s:actionmessage/> --%>
		<s:iterator value="actionMessagesSession" status="estado">
		<ul class="actionMessage">
			<li>
				<span><s:property></s:property></span>
			</li>
		</ul>
		</s:iterator>
	</div>
</div>
</s:if>

<div class="limpiar"></div>