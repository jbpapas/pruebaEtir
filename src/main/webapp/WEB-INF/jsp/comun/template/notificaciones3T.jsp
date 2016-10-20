<%@ include file="../../taglibs.jsp"%>
<%--	<s:set name="notificaciones3" value="@es.dipucadiz.etir.comun.utilidades.DatosSesion@getUsuarioNotificacionesTipo3T()"/> --%>
	<s:set name="notificaciones3" value="@es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil@getUsuarioNotificacionesTipo3T()"/>
	
    <div style="z-index: 900000;background-color:#000000;opacity:0.4; filter: alpha(opacity = 40); height:100%; left:0; position:fixed; top:0; width:100%;"></div>			    
    <div style="position: fixed; z-index: 900001; top: 30%; opacity: 1; left: 30%;width: 40%;">			    	
    	<div style="background-color:#E8E8E6; border:1px solid #E16F26; text-align:left; margin:6px; padding:10px;width: 100%;">										
		<div style="width: 100%; height: 300px;">
			<div style="background-color:white; height: 290px; padding: 5px">
				<div style="float:right; padding:1px; height:1px">
					<a id="boton-cerrar-aviso" href="#">
						<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
					</a>
				</div>
				<h1 style="margin-bottom:0" >Aviso:</h1>
				<div class="fila separador">
					<hr class="linea_separadora"/>
	           	</div>
				
				<div>
					<div class="tableContainer">
						<div class="scrollContent">
							<form id="form-avisos" action="">
								<table class="display-table" style="width:95%" cellpadding="0" cellspacing="0">
									<tbody>
										<s:iterator status="state" value="#notificaciones3">
											<s:set name="fechaAct" value="%{fhActualizacion}"/>
											<s:set name="fecAct"><s:property value="@es.dipucadiz.etir.comun.utilidades.Utilidades@dateToDDMMYYYYHHMMSS(#fechaAct)"/></s:set>					
											<s:set name="css" value="%{'even'}"/>
											<s:if test="%{#state.index % 2 == 0}">
												<s:set name="css" value="%{'odd'}"/>
											</s:if>
											<tr id="tr_<s:property value="coAcmUsuarioNotificacion"/>" class="<s:property value="%{#css}"/>">
												<td width="27%">
													<s:property value="%{#fecAct}"/>						
												</td>
												<td width="67%">
													<s:property value="mensaje" escape="false" />								
												</td>
												<td style="padding: 0.4em 0.4em 0.4em 0.4em; width:1%">
													<input id="<s:property value="coAcmUsuarioNotificacion"/>" type="checkbox" checked="true" />								
												</td>
											</tr>			
										</s:iterator>
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div style="text-align: center; margin-top:10px">    
                        <input id="boton-confirmar-aviso" type="button" value="<s:text name="button.confirmar"/>">
					</div>					
				</div>
			</div>
		</div>
		</div>
     </div>

<s:if test="#notificaciones3!=null && #notificaciones3.size>0">	
	<script type="text/javaScript">
	     	$('#overlaysContainer').toggle(true);

	    	// Notificación 3
	    	eventos3();
	</script>
</s:if>
<s:else>	
	<script type="text/javaScript">
	     	$('#overlaysContainer').toggle(false);
	</script>
</s:else>
     