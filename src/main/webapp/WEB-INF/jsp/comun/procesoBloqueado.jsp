<%-- 
 * Es necesario definir en el action el booleano isProcesoBloqueado y el string mensajeProcesoBloqueado.
 * Los valores de estos se calculo mediante metodos disponibles en la clase BloqueoUtil.
 * Ejemplo disponible en G743CensoDocumentosAction.java/G743DocumentosLista.jsp y también en G711Detalle.
--%>
<%@ include file="../taglibs.jsp"%>

<s:if test="procesoBloqueado">
<script type="text/javaScript">
$(document).ready(function() {
	var msg = '<s:property value="mensajeProcesoBloqueado" escape="false" />';
	infoDialog('MANTENIMIENTO BLOQUEADO', msg);
});
</script>
</s:if>
