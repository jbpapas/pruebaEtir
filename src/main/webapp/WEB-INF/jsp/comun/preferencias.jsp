<%@ include file="../taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<tiles:importAttribute />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
	</title>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura-interior.css" includeParams="none" />"/>
	
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/general.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/css_xhtml.css" includeParams="none"/>"/>	
</head>
<body>

                                       
<div style="margin-top:30px;margin-bottom:30px;margin-left:20px;margin-right:20px;">


	<%-- Mensajes de error --%>
	<s:if test="%{hasActionErrorsSession() || !fieldErrors.isEmpty() || hasActionErrors()}">
	<div class="errores">
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
		<div>
			<%-- <s:actionerror/> --%>
			<s:iterator value="actionErrors" status="estado">
			<ul class="errorMessage">
				<li>
					<span><s:property></s:property></span>
				</li>
			</ul>
			</s:iterator>
	 
		</div>		
	</div>
	</s:if>
	
	<%-- Mensajes de información --%>
	<s:if test="%{hasActionMessagesSession()}">
	<div class="mensajes">
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

<%--
	<gadir:pestanas  
		nombre0="pestana.preferencias.usuario"
		metodo0="botonPestanaPreferencias"
		nombre1="pestana.cambio.password"
		metodo1="botonPestanaPassword"
	/>
--%>
	<gadir:pestanas  
		nombre0="pestana.cambio.password"
		metodo0="botonPestanaPassword"
	/>

	<s:if test="pestana==1">
		
		<fieldset class="pestana">
			<div id="form-container">
				<s:form validate="true" id="preferencias" name="formPreferencias">
				
					<s:hidden name="pestana" />
				
					<div class="fila">
						<s:checkbox 
							name="accesibilidad"
							label="%{getText('accesibilidad')}"
							labelposition="left"
						/>
					</div>
						
					<div class="fila">
						<gadir:combo
							id="combo-numResultados"
							label="%{getText('numero.resultados.paginacion')}"
							name="numResultados"
							list="#{'1':'1', '2':'2', '3':'3', '4':'4', '5':'5', '6':'6', '7':'7', '8':'8', '9':'9', '10':'10', '15':'15', '20':'20', '25':'25', '30':'30', '40':'40', '50':'50', '100':'100'}"
							cssStyle="width: 50px"
							emptyOption="false"
							labelposition="left"
						/>
					</div>
					
					<div class="fila separador">
						<hr class="linea_separadora"/>
					</div>
					
					<div class="fila">
						<div class="botoneraIzquierda">
							<s:submit value="%{getText('button.aceptar')}" method="botonGuardaPreferencias" theme="simple" />
						</div>		
					</div>
				</s:form>
			</div>
		</fieldset>
	
	</s:if>
	<s:else>		
	
		<fieldset class="pestana">
			<div id="form-container">
				<s:form validate="true" id="password" name="formPassword">
				
					<s:hidden name="pestana" />
				
					<div class="fila">
<%--						<s:password label="%{getText('contrasena.anterior')}" labelposition="left" name="passwordAnterior" size="22" required="true" /> 	--%>
						<div class="wwgrp" id="wwgrp_passwordAnterior">
							<span style="width: 150px;" class="wwlbl" id="wwlbl_passwordAnterior">
								<label class="label" style="font-style:normal" for="passwordAnterior" ><span class="required">* </span>Contraseña anterior:</label>
							</span> 
							<span style="width: 250px;" id="wwctrl_passwordAnterior">
			        			<s:password name="passwordAnterior" id="passwordAnterior" theme="simple" required="true" size="20" maxlength="20" showPassword="true" />
							</span> 
						</div>
					
						 
						
					</div>
				
					<div class="fila">
<%--						<s:password label="%{getText('contrasena')}" labelposition="left" name="password" size="22" required="true"/> 	--%>
						<div class="wwgrp" id="wwgrp_password">
							<span style="width: 150px;" class="wwlbl" id="wwlbl_password">
								<label class="label" style="font-style:normal" for="password" ><span class="required">* </span>Contraseña nueva:</label>
							</span> 
							<span style="width: 250px;" id="wwctrl_password">
			        			<s:password name="password" id="password" theme="simple" required="true" size="20" maxlength="20" showPassword="true" />
							</span> 
						</div>
 							
			<%--			<s:password  
							label="Contraseña" 
							labelposition="left"  
							name="password" 
							id="password"   
							required="true" 
							size="20" 
							maxlength="20" 
							showPassword="true"
							 styleGroup="width:35%"
						styleLabel="width:29%"
						styleText="width:70%"/>
					</div>	--%>
					
					<div class="fila">
<%--						<s:password label="%{getText('confirmar.contrasena')}" labelposition="left" name="passwordConfirmar" size="22" required="true"/> 	--%>
						<div class="wwgrp" id="wwgrp_passwordConfirmar">
							<span style="width: 150px;" class="wwlbl" id="wwlbl_passwordConfirmar">
								<label class="label" style="font-style:normal" for="passwordConfirmar" ><span class="required">* </span>Confirmar contraseña:</label>
							</span> 
							<span style="width: 250px;" id="wwctrl_passwordConfirmar">
			        			<s:password name="passwordConfirmar" id="passwordConfirmar" theme="simple" required="true" size="20" maxlength="20" showPassword="true" />
							</span> 
						</div>
					
					<%--		<s:password label="Confirmar contraseña" 
								labelposition="left"  
								name="passwordConfirmar" 
								id="passwordConfirmar"   
								required="true" 
								size="20" 
								maxlength="20" 
								showPassword="true" 
												styleGroup="width:35%"
						styleLabel="width:29%"
						styleText="width:70%"
						/>	--%>
					</div>
					
					<div class="fila separador">
						<hr class="linea_separadora"/>
					</div>
					
					<div class="fila">
						<div class="botoneraIzquierda">
							<s:submit value="%{getText('button.aceptar')}" method="botonGuardaPassword" theme="simple" />
						</div>		
					</div>
				</s:form>
			</div>
		</fieldset>
		
	</s:else>


	<div style="text-align:center">
	
	<br/>
	<br/>
	
	<s:submit theme="simple" onclick="javascript:window.close()" value="Cerrar"/>
	
	</div>
</div>

</body>
</html>
