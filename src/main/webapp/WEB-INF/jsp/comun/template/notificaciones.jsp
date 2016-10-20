<%@ include file="../../taglibs.jsp"%>
	<s:set name="notificaciones" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNotificacionesTipo1()"/>
<%-- 	<s:set name="notificaciones" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNumNotificacionesTipo1()"/> --%>
	<s:set name="notificaciones2" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNotificacionesTipo2()"/>

		<div style="font-weight:bold;float:left; font-size:110%">
			Notificaciones
		</div>
		<div style="float:right; padding:2px; height:16px">
			<a id="boton-cerrar-notificaciones" href="#">
				<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
			</a>
		</div>
		
		<s:if test="#notificaciones.size>0">
			<div id="capaNotificaciones">
				<div class="tableContainer"> 
				<form id="form-notificaciones" action="">
					<table class="display-table" style="width:95%" cellpadding="0" cellspacing="0">
						<tbody>
							<s:iterator status="state" value="#notificaciones" >					
								<s:set name="css" value="%{'even'}"/>
								<s:if test="%{#state.index % 2 == 0}">
									<s:set name="css" value="%{'odd'}"/>
								</s:if>
								<tr id="tr_<s:property value="coAcmUsuarioNotificacion"/>" class="<s:property value="%{#css}"/>">
									<td>
										<s:property value="@es.dipucadiz.etir.comun.utilidades.Utilidades@dateToDDMMYYYYHHMMSS(fhActualizacion)"/>															
									</td>
									<td>
										<s:property value="mensaje" escape="false" />
										<s:if test="ejecucionDTO.coEjecucion!=null">
											
											<a 
												target="_blank" 
												href="notificaciones!verEjecucion.action?ventanaBotonLateral=true&coEjecucion=<s:property value="ejecucionDTO.coEjecucion"/>" 
												title="Ver"
												onclick="vnt=window.open('notificaciones!verEjecucion.action?ventanaBotonLateral=true&coEjecucion=<s:property value="ejecucionDTO.coEjecucion"/>' , 'ventanaBotonLateral' , 'width=1024,height=768,scrollbars=YES'); vnt.focus(); return false;" 
												>
												<img src="/etir/image/iconos/16x16/view.png" alt="Detalle" title="Detalle" />
											</a>
										</s:if>						
									</td>
									<td style="padding: 0.4em 0.4em 0.4em 0.4em">
										<input id="<s:property value="coAcmUsuarioNotificacion"/>" type="checkbox" checked="true" />								
									</td>
								</tr>			
							</s:iterator>
						</tbody>
					</table>
				</form>
		
				</div>
					
				<div class="fila separador"></div>
			
				<div class="botoneraDerecha">	
					<input id="boton-confirmar-notificaciones" type="button" value="<s:text name="button.confirmar"/>">
				</div>
				<div class="botoneraIzquierda">
					<input id="boton-confirmar-todo" type="button" value="<s:text name="button.confirmartodo"/>">
				</div>
			</div>
		</s:if>
		<s:else>
			<div class="fila">No hay notificaciones</div>
		</s:else>
