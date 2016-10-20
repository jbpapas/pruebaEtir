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
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/tablas.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/displayTag.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/header-fixed.css" includeParams="none"/>"/>
	
</head>
<body>
	<%@ include file="/WEB-INF/jsp/comun/template/mensajes.jsp"%>
   <s:form>                                    
<div style="margin-top:30px">
	<table class="display-table"  style="float:none;width:70%; margin-left:auto; margin-right:auto; " cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th colspan="2">INFORMACIÓN DE USUARIO</th>
			</tr>
		</thead>
		
		<tbody>		
			<tr class="even">
				<td>
					<s:text name="codigo.usuario" />
				</td>
				<td>
					<s:property value="codigo"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					<s:text name="nombre" />
				</td>
				<td>
					<s:property value="nombre"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					<s:text name="unidad.administrativa" />
				</td>
				<td>
					<s:property value="unidadAdministrativa"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					<s:text name="codigo.territorial" />
				</td>
				<td>
					<s:property value="codigoTerritorial"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					<s:text name="codigo.territorial.generico" />
				</td>
				<td>
					<s:property value="codigoTerritorialGen"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					<s:text name="direccion.ip" />
				</td>
				<td>
					<s:property value="direccionIP"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					<s:text name="punto.menu" />
				</td>
				<td>
					<s:property value="puntoMenu"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					<s:text name="proceso" />
				</td>
				<td>
					<s:property value="proceso"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					<s:text name="entorno" />
				</td>
				<td>
					<s:property value="entorno"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					<s:text name="base.datos" />
				</td>
				<td>
					<s:property value="baseDatos"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					<s:text name="impresora" />
				</td>
				<td>
									
					<gadir:combo 
					 
						name="coImpresora"
						list="listaImpresora"
						listKey="coImpresora"
						listValue="codigoDescripcion"
						emptyOption="false"
					 
						styleGroup="width:100%"
						styleText="width:85%"
						styleLabel="width:12%"
						cssStyle="width:95%"
					/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					<s:text name="cola.ejecucion" />
				</td>
				<td>
					<s:property value="colaEjecucion"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					<s:text name="carpeta.trabajo" />
				</td>
				<td>
					<s:property value="carpetaTrabajo"/>
				</td>
			</tr>

			
		</tbody>
	</table>
</div>



<div style="text-align:center">

<br/>
<br/>

<s:submit theme="simple"   value="Guardar" method="cambiarImpresora"/>
<s:submit theme="simple" onclick="javascript:window.close()" value="Cerrar"/>

<br/>
<br/>
<br/>
<br/>

</div>
</s:form>
<%--

INFORMACIÓN DE USUARIO                                      
       Código             GADIR11                                              
       Nombre             Javier Benita Crespo                                 
    U. Administrativa     0001       GESTION TRIBUTARIA SPRYGT                 
    C. Territorial                   SERVICIO DE RECAUDACIÓN        RE0000     
   C. Territ. Genérico               Genérico                       ******     
   Dirección I.P.         192.168.84.224                                       
    Terminal              GD10                                                 
     Programa             M45110P0                                             
     Entorno              DESARROLLO                                           
     Base de Datos        DESARROLLO        
 --%>
</body>