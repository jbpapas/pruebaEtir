<%@ include file="../../taglibs.jsp"%>

	<div id="barraInferior" style="position:absolute;bottom:0;right:17.5%;background-color: white; width:65%;height:22px;border-left:1px dashed #E8E8E6; border-top: 1px dashed #e8e8e6;border-left: 1px dashed #e8e8e6;border-right: 1px dashed #e8e8e6;">
		<div style="width:95%;float:left">
			<form id="form-notificaciones2" action="">
				<s:set name="encontrado" value="false"/>
				<MARQUEE scrolldelay="100" >	
					<table>
						<tbody>	
							<tr>
								<td>
									<s:set var="limiteMensajes" value="3" />
									<s:iterator status="state" value="#notificaciones2" >
										<s:if test="#state.index <= #limiteMensajes">
											<span id="filaMensaje_<s:property value="#state.index"/>">
												<span id="numMensaje_<s:property value="#state.index - 1"/>">
													<s:if test="#state.index >= #limiteMensajes">
														...
													</s:if>
													<s:elseif test="#state.index > 0">
														-&nbsp;&nbsp;&nbsp;&nbsp;
													</s:elseif>
												</span>
												<s:if test="#state.index < #limiteMensajes">
													<span id="mensaje_<s:property value="coAcmUsuarioNotificacion"/>">
														<s:property value="mensaje" escape="false" />
														<s:if test="%{tipo.equals(\"Q\")}">
															<s:set name="encontrado" value="true"/>
															<input style="visibility: hidden" id="<s:property value="coAcmUsuarioNotificacion"/>" value="<s:property value="#state.index"/>" type="checkbox" checked="true" />
														</s:if>
														<s:else>
															<input style="visibility: hidden" id="<s:property value="coAcmUsuarioNotificacion"/>" value="<s:property value="#state.index"/>" type="checkbox" />
														</s:else>
													</span>
												</s:if>
											</span>
										</s:if>
									</s:iterator>
								</td>
							</tr>			
						</tbody>
					</table>
				</MARQUEE>
			</form>		
		</div>
		<s:if test="encontrado">
		<div style="width:5%;float:left;text-align:right;margin-top:2px;" >
			<a style="vertical-align:bottom;margin-right:2px" id="boton-confirmar-notificaciones2" href="#">
				<img style="vertical-align:bottom" src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
			</a>
		</div>		
		</s:if>
	</div>