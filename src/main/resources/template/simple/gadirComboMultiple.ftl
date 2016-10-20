<#--
/*
 * $Id: select.ftl 804072 2009-08-14 03:16:35Z musachy $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<div 
<#if parameters.styleContent??>
	style="<@s.property value="parameters.styleContent"/>"
</#if>>
	<div class="wwlbl" 
	<#if parameters.styleLabel??>
		style="<@s.property value="parameters.styleLabel"/>"
	</#if>> 
		<#if parameters.label??>
			<label class="label"><@s.property value="parameters.label"/>:</label>
		</#if>
	</div>
		<div id="<@s.property value="parameters.id"/>" class="listaOpcionesMultiple">
			<div id="<@s.property value="parameters.id"/>_listaOpcionesTxt" class="listaOpcionesTxt">
				<table>
					<tbody id="<@s.property value="parameters.id"/>_tablaListaMultiple">
						
						<@s.iterator value="parameters.list">
				            <#if stack.findValue(parameters.listKey)??>
				              <#assign itemKey = stack.findValue(parameters.listKey)/>
				              <#assign itemKeyStr = stack.findString(parameters.listKey)/>
				            <#else>
				              <#assign itemKey = ''/>
				              <#assign itemKeyStr = ''/>
				            </#if>
				            
				            <#if stack.findString(parameters.listValue)??>
				              <#assign itemValue = stack.findString(parameters.listValue)/>
				            <#else>
				              <#assign itemValue = ''/>
				            </#if>

						    <tr class="fila">
								<td>
									<a href="#" id="${itemKeyStr?html}_Multiple" onclick="addOptionSelected('${itemKeyStr?html}', '${itemValue?html}', true);">${itemValue?html}</a>
								</td>
							</tr>
						</@s.iterator>
					</tbody>
				</table>
			</div>

			<div class="divBotonCerrarListaMultiple">
				<a id="<@s.property value="parameters.id"/>_boton-cerrar-listaMultiple" href="#"> Cerrar </a>
			</div>
		</div>					
		<div>	
			<img id="<@s.property value="parameters.id"/>_addSituacionSelect" class="filterAdd"
					alt="<@s.property value="parameters.title"/>" title="<@s.property value="parameters.title"/>"
					src="/etir/image/iconos/16x16/add_blue.png">

			<div class="filterSelected">
				<div id="<@s.property value="parameters.id"/>_selected" class="tagsSelecteds">
					<input id="<@s.property value="parameters.id"/>_hiddenSelected" type="hidden"
						name="<@s.property value="parameters.name"/>"  
						<#if stack.findValue(parameters.name)??>
			              <#assign itemName = stack.findValue(parameters.name)/>
			            <#else>
			              <#assign itemName = ''/>
			            </#if>
			            value="${itemName?html}"/>
				</div>
			</div>
		</div>
</div>
		
<script type="text/javascript">
	$(document).ready(function() {			    
		
		$(window).click(function() {
			$('#<@s.property value="parameters.id"/>').toggle(false);
		});
		
		$('#<@s.property value="parameters.id"/>_addSituacionSelect').click(function(e) {
			
			e.stopPropagation();
			
			if($('#<@s.property value="parameters.id"/>').css('display') == 'none'){						
				var $target = $(e.target);
				
				$('#<@s.property value="parameters.id"/>').css("left", e.pageX+10);
		      	$('#<@s.property value="parameters.id"/>').css("top", e.pageY-8);
				$('#<@s.property value="parameters.id"/>').toggle(true);
			}else{
				$('#<@s.property value="parameters.id"/>').toggle(false);
			}
			
		});
		
		$('#<@s.property value="parameters.id"/>_boton-cerrar-listaMultiple').click(function() {
			$('#<@s.property value="parameters.id"/>').toggle(false);
		});
		
	    if( $("#<@s.property value="parameters.id"/>_hiddenSelected") != undefined ){
	    	var optionSelects =  $("#<@s.property value="parameters.id"/>_hiddenSelected").val();
	    	var arrayOptionSelects = optionSelects.split(",");
	    	
	    	$.each(arrayOptionSelects, function( index, value ) {
	    		  var text = $('#' + value + '_Multiple').text();
	    		  if(text != ''){
	    			  addOptionSelected(value, text, false);  
	    		  }			    		  
	    		});
	    }
    	
	});
	
	function addOptionSelected(value, text, addHidden){		
    	var optionSelected;
    	
    	if(isMsie7()){
    		var widthText = $.fn.textWidth(text, 'Arial') + 17;
    		optionSelected = '<div class="tagSelected" style="width:' + widthText  + 'px"><span id="textSelect">' + text + '</span><span id="valueSelect" class="oculto">' + value + '</span><span class="tagRemove">×</span></div>'
    	}else{
    		optionSelected = '<div class="tagSelected"><span id="textSelect">' + text + '</span><span id="valueSelect" class="oculto">' + value + '</span><span class="tagRemove">×</span></div>'
    	}
    	
    	$('#<@s.property value="parameters.id"/>_selected').append(optionSelected);
    	
    	if( addHidden ){
    		var hiddenSelected = $("#<@s.property value="parameters.id"/>_hiddenSelected").val();
	    	if( hiddenSelected != undefined && hiddenSelected != ""){
	    		hiddenSelected = hiddenSelected + ',' + value
	    	}else{
	    		hiddenSelected = hiddenSelected + value
	    	}
	    	$("#<@s.property value="parameters.id"/>_hiddenSelected").val(hiddenSelected);
    	}		    	
    	
    	//se elimina opción del select
    	$('#' + value + '_Multiple').parent().parent().remove();
    	
    	//se eliminan los eventos onclick para evitar que se añadan más de una vez
    	$(".tagRemove").unbind('click');
    	
    	$('#<@s.property value="parameters.id"/>').toggle(false);
    	
    	$(".tagRemove").click(function(){				    	
	    	removeOptionSelected(this);
	    });
	}
	
	function removeOptionSelected(tagSelected){
		var option = $(tagSelected).parent().find("#textSelect").text();
    	var value = $(tagSelected).parent().find("#valueSelect").text();
    	
    	$("#<@s.property value="parameters.id"/>_tablaListaMultiple").append('<tr class="fila"><td><a href="#" id="' + value + '_Multiple" onclick="addOptionSelected(\'' + value + '\', \'' + option + '\', true);">' + option + '</a></td></tr>');
    	
    	
    	if( $("#<@s.property value="parameters.id"/>_hiddenSelected") != undefined ){
	    	var optionSelects =  $("#<@s.property value="parameters.id"/>_hiddenSelected").val();
	    	var arrayOptionSelects = optionSelects.split(",");
	    	var result = '';
	    	
	    	arrayOptionSelects = $.grep(arrayOptionSelects, function(valorBuscado) {
	    		  return valorBuscado != value;
	    		});
	    	
	    	
	    	$("#<@s.property value="parameters.id"/>_hiddenSelected").val(arrayOptionSelects);
	    }
    	
    	$(tagSelected).parent().remove();
	}
	
	function isMsie7() 
	{
	    var ua = window.navigator.userAgent;
	    var msie = ua.indexOf("MSIE ");
	
	    if (msie > 0) // If Internet Explorer, return version number
	    {
	    	if(parseInt(ua.substring(msie + 5, ua.indexOf(".", msie))) < 8){
	    		//alert(parseInt(ua.substring(msie + 5, ua.indexOf(".", msie))));
	        	return true;
	    	}
	        
	    }
	    else  // If another browser, return false
	    {	        
	        return false;
	    }	    
	}
	
	$.fn.textWidth = function(text, font) {
	    if (!$.fn.textWidth.fakeEl){
	    	$.fn.textWidth.fakeEl = $('<span>').hide().appendTo(document.body);
	    }
	    
	    $.fn.textWidth.fakeEl.text(text || this.val() || this.text()).css('font-family', font || this.css('font-family'));
	    
	    return $.fn.textWidth.fakeEl.width();
	};
</script>

