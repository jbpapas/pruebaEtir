<%@ include file="../../taglibs.jsp"%>

<div id="botonera-lateral">
	<s:url action="G4215Seleccion" namespace="/" id="boton1" />
	
	<br/>
	
	
	<ul>
		
		<!-- abrir nueva pagina, boton fijo -->
		<li>
			<s:url action="bienvenido" id="bienvenidoUrl"></s:url>
			<a target="_blank" 
			   href="<s:property value="bienvenidoUrl"/>"
			   title="<s:text name="nueva.ventana"/>" 
			   onclick="setCookie('tabName', '');vnt=window.open('<s:property value="bienvenidoUrl"/>' , '_blank' , 'width=1100,height=700,status=yes,toolbar=no,location=no,menubar=no,directories=no,resizable=yes,scrollbars=no'); vnt.focus(); return false;" 
			   >
				<img src="/etir/image/botones-lateral/nuevaVentana.png" title="<s:text name="nueva.ventana"/>" alt="<s:text name="nueva.ventana"/>"/>
			</a>
		</li>
		
		<!-- botones por perfil/usuario -->
		<s:iterator value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getAcmBotonsAccesibles()" status="estado">
			
			<li>
				<s:if test="procesoDTO.url.startsWith(\"http\")">
					<s:url value="%{procesoDTO.url}" id="coAcmBoton" />
				</s:if>
				<s:else>
					<s:url namespace="/" action="%{procesoDTO.url}" id="coAcmBoton" />
				</s:else>
				<a 
					target="_blank" 
					href="<s:property value="%{#coAcmBoton}"/>" 
					title="<s:property value="nombre" />"
					onclick="botoneranewwindow=window.open('<s:property value="%{#coAcmBoton}"/>?ventanaBotonLateral=true' , 'ventanaLateral<s:property value="procesoDTO.coProceso" />' , 'width=1024,height=768,scrollbars=yes,status=yes,resizable=yes,toolbar=no,location=no,menubar=no,directories=no'); botoneranewwindow.focus(); return false;" 
					>
					<img src="/etir/image/botones-lateral/<s:property value="icono" />" title="<s:property value="nombre" />" alt="<s:property value="nombre" />"/>
				</a>
			</li>
		
		</s:iterator>
		
	</ul>
</div>
