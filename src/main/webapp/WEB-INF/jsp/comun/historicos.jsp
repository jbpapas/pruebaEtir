<%@ include file="../taglibs.jsp"%>
<%@ include file="../parametrizacion.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<s:if test="%{!accesible}">
	<s:if test="%{listadoColumna.selecMultiple !=null &&  listadoColumna.selecMultiple != 'false'}" >
<!-- CONTENIDO VERSIÓN NO ACCESIBLE / NIFS FICTICIOS -->
		<script src="<s:url value="/js/ext/utilPaginacionCheckboxs.js" ></s:url>">
		</script>
	</s:if>
	
	<s:else>
		<script src="<s:url value="/js/ext/utilMaestroDetalle.js" ></s:url>">
		</script>
	
	</s:else>
				
<!-- CONTENIDO VERSIÓN NO ACCESIBLE / NIFS FICTICIOS -->
</s:if>

<div id="listado" style="width:100%;overflow-y:hidden;overflow-x:auto;border:0px;margin:0px;padding:0px;" class="tableContainer">
	<c:if test="${listadoColumna.titulo !=null &&  listadoColumna.titulo != 'false'}" >
	
		<h3><s:text name="%{listadoColumna.titulo}"/></h3>
	</c:if>
	
	<br>
	    	
	    	<s:hidden name="listadoColumna.respuesta" id="actionCheck"/>
	    	 <%
	    	 	es.dipucadiz.etir.comun.displaytag.GadirCheckboxTableDecorator decorator = new es.dipucadiz.etir.comun.displaytag.GadirCheckboxTableDecorator();
	    	 			      decorator.setId("rowid");
	    	 			      decorator.setFieldName("_chk");
	    	 			      pageContext.setAttribute("checkboxDecorator", decorator);
	    	 %>
	    	
	    	<s:if test="%{listadoColumna.selecMultiple !=null &&  listadoColumna.selecMultiple != 'false' && !accesible}" >
	    	   
	    	  <display:table name="${listadoColumna.nombreLista}" uid="row" pagesize="${listadoColumna.pagesize}" partialList="${listadoColumna.partialList}" size="listadoColumna.size" summary="${summary}"      
                           	requestURI="${listadoColumna.respuesta}" keepStatus="true" clearStatus="${clearStatus}"
                            cellpadding="0" cellspacing="0"  excludedParams="action:* method:* botonVolverPila"
                            decorator="checkboxDecorator" 
            				form="${listadoColumna.formulario}"
            				
                            >     
	    	
	    	<c:if test="${listadoColumna.nombreSelec!='false'}">
	                            <display:column titleKey="${listadoColumna.etiqSelec}" class="celda1 celdaSeleccion" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"> 
	                           
	                            	<c:if test="${row.rowid == listadoColumna.seleccionado}">
	                            		
	                            		<input type="radio" name="${listadoColumna.nombreSelec}" id="sel${row.rowid}" class="informeRadio" value="${row.rowid}" checked="checked" />
	                            	</c:if>
	                            	<c:if test="${row.rowid != listadoColumna.seleccionado}">
                            			<input type="radio" name="${listadoColumna.nombreSelec}"  id="sel${row.rowid}" class="informeRadio" value="${row.rowid}"  />
                            		</c:if>
                            	</display:column>
                            	</c:if>
                     		<s:iterator value="listadoColumna.listadoColumnas" status="itStatus" >
                     			
                     			<display:column titleKey="${etiqueta}"  class="celda1"
	                            		property="${propiedad}"
	                            		format="${format}"
	                            		sortable="${sortable}"
	                            		maxLength="${ancho}"
	                            		style="width:${width};text-align:${align}"
	                            		decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"/>
	                        </s:iterator>
                      
              	 		
              	 		
                   			<c:if test="${listadoColumna.selecMultiple !=null &&  listadoColumna.selecMultiple != 'false'}" >
                   				
                   				            <display:column titleKey="${listadoColumna.etiquetaSelMultiple}" property="checkbox" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"/>
                   				
                   			</c:if>
                   
		                   <c:if test="${listadoColumna.acciones!=null && listadoColumna.acciones!='false'}" >
		                   		<display:column class="celdaIconos" headerClass="celdaIconos" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator">
		                   			<c:if test="${listadoColumna.accionSeleccionar!= '' && listadoColumna.accionSeleccionar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionSeleccionar}${row.rowid}">
				          					<img src="/etir/image/iconos/16x16/view.png" title="Seleccionar" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionEjecutar!= '' && listadoColumna.accionEjecutar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionEjecutar}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_ejecutar.gif" includeParams="none" />" title="<s:text name="Ejecutar"></s:text>" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionHistorico!= '' && listadoColumna.accionHistorico!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionHistorico}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_historico.gif" includeParams="none" />" title="<s:text name="Histórico"></s:text>" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionEliminar!= '' && listadoColumna.accionEliminar!='false'}">
				                   		<a class="iconos confirmar" href="${listadoColumna.accionEliminar}${row.rowid}" 
				                   			onclick="return confirmJs('Pulse aceptar para confirmar el borrado.')">
				          					<img src="/etir/image/iconos/16x16/delete.png" title="Eliminar"/>
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionDuplicar!= '' && listadoColumna.accionDuplicar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionDuplicar}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_duplicar.gif" includeParams="none" />" title="<s:text name="Duplicar"></s:text>" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionConsultar!= '' && listadoColumna.accionConsultar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionConsultar}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_ir.gif" includeParams="none" />" title="<s:text name="Ir a"></s:text>" />
				            			</a>
			            			</c:if>
			            			
			            		</display:column>
            		</c:if>
            		  </display:table>
	    		<c:if test="${listadoColumna.metodoMarcarTodo!=null && listadoColumna.metodoMarcarTodo!='false'}" >
	    			<div class="botonera">
	    			
						<gadir2:submit id="btnmarcar" cssClass="botonNormal" 
							value="%{getText('button.marcarTodo')}" title="%{getText('button.aceptar')}" action="${listadoColumna.metodoMarcarTodo}" 
							tabindex="%{tabIndex}"/>
							
						
							
                   		
                  	</div>
				</c:if>
	    	
	    	
	    	
	    	
	    	</s:if>
	    	
	    	<s:else>
	    	
	    	  <display:table name="${listadoColumna.nombreLista}" uid="row" pagesize="${listadoColumna.pagesize}" partialList="${listadoColumna.partialList}" size="listadoColumna.size" summary="${summary}"      
                           	requestURI="${listadoColumna.respuesta}" keepStatus="true" clearStatus="${clearStatus}" 
                            cellpadding="0" cellspacing="0"  excludedParams="action:* method:* botonVolverPila"
                            decorator="checkboxDecorator" >     
	    	<c:if test="${listadoColumna.nombreSelec!='false'}">
	                            <display:column titleKey="${listadoColumna.etiqSelec}" class="celda1 celdaSeleccion" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"> 
	                           
	                            	<c:if test="${row.rowid == listadoColumna.seleccionado}">
	                            		
	                            		<input type="radio" name="${listadoColumna.nombreSelec}" id="sel${row.rowid}" class="informeRadio" value="${row.rowid}" checked="checked" />
	                            	</c:if>
	                            	<c:if test="${row.rowid != listadoColumna.seleccionado}">
                            			<input type="radio" name="${listadoColumna.nombreSelec}"  id="sel${row.rowid}" class="informeRadio" value="${row.rowid}"  />
                            		</c:if>
                            	</display:column>
                            	</c:if>
                     		<s:iterator value="listadoColumna.listadoColumnas" status="itStatus" >
                     			
                     			<display:column titleKey="${etiqueta}"  class="celda1"
	                            		property="${propiedad}"
	                            		format="${format}"
	                            		sortable="${sortable}"
	                            		maxLength="${ancho}"
	                            		style="width:${width};text-align:${align}"
	                            		decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"/>
	                        </s:iterator>
                      
              	 		
              	 		
                   			<c:if test="${listadoColumna.selecMultiple !=null &&  listadoColumna.selecMultiple != 'false'}" >
                   				
                   				            
                   				<display:column titleKey="${listadoColumna.etiquetaSelMultiple}" property="checkbox" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"/>
                   			</c:if>
                   
		                   <c:if test="${listadoColumna.acciones!=null && listadoColumna.acciones!='false'}" >
		                   		<display:column class="celdaIconos" headerClass="celdaIconos" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator">
		                   			<c:if test="${listadoColumna.accionSeleccionar!= '' && listadoColumna.accionSeleccionar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionSeleccionar}${row.rowid}">
				          					<img src="/etir/image/iconos/16x16/view.png" title="Seleccionar" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionEjecutar!= '' && listadoColumna.accionEjecutar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionEjecutar}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_ejecutar.gif" includeParams="none" />" title="<s:text name="Ejecutar"></s:text>" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionHistorico!= '' && listadoColumna.accionHistorico!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionHistorico}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_historico.gif" includeParams="none" />" title="<s:text name="Histórico"></s:text>" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionEliminar!= '' && listadoColumna.accionEliminar!='false'}">
				                   		<a class="iconos confirmar" href="${listadoColumna.accionEliminar}${row.rowid}"
				                   		onclick="return confirmJs('Pulse aceptar para confirmar el borrado.')">
				          					<img src="/etir/image/iconos/16x16/delete.png" title="Eliminar" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionDuplicar!= '' && listadoColumna.accionDuplicar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionDuplicar}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_duplicar.gif" includeParams="none" />" title="<s:text name="Duplicar"></s:text>" />
				            			</a>
			            			</c:if>
			            			<c:if test="${listadoColumna.accionConsultar!= '' && listadoColumna.accionConsultar!='false'}">
				                   		<a class="iconos" href="${listadoColumna.accionConsultar}${row.rowid}">
				          					<img width="21" height="20" src="<s:url value="/img/ic_ir.gif" includeParams="none" />" title="<s:text name="Ir a"></s:text>" />
				            			</a>
			            			</c:if>
			            		</display:column>
            		</c:if>
            		  </display:table>
            		  <c:if test="${listadoColumna.selecMultiple !=null &&  listadoColumna.selecMultiple != 'false'}" >
            		  <div class="botonera">
            		  
            		  <c:if test="${listadoColumna.metodoMarcarTodo!=null && listadoColumna.metodoMarcarTodo!='false'}" >
	            		  <gadir2:submit id="btnmarcar2" cssClass="botonNormal" 
								value="%{getText('button.marcarTodo')}" title="%{getText('button.aceptar')}" action="${listadoColumna.metodoMarcarTodo}" 
								tabindex="%{tabIndex}"/>
            		  </c:if>
            		   	<gadir2:submit id="btnmarcar2" cssClass="botonNormal" 
								value="%{getText('button.marcarSelec')}" title="%{getText('button.aceptar')}" 
								tabindex="%{tabIndex}"/>
            		  
            		  
            		  </div>
            		  
            		  </c:if>
            		  
				
	    	</s:else>
             		                                                         
                      		
            		 
					<c:if test="${listadoColumna.nombreSelec!='false'}">
                   	<div class="botonera">
						<gadir2:submit id="btndetalle:${listadoColumna.respuesta}:detalleHistorico:${listadoColumna.nombreSelec}:${listadoColumna.divFiltro}" cssClass="botonAceptar" 
							value="%{getText('button.aceptar')}" title="%{getText('button.aceptar')}" method="${listadoColumna.metodoSel}"
							tabindex="%{tabIndex}"/>
							
							
                   		
                  	</div>
				</c:if>
				
	     </div>		
		