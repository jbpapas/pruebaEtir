<#if parameters.isCliente?default(false)>
	<input type="hidden" name="coCliente${parameters.sufijo?html}" value="<@s.property value="parameters.codigoValue"/>" id="hidden_coCliente${parameters.sufijo?html}"/>
	<input type="hidden" name="cliente${parameters.sufijo?html}" value="<@s.property value="parameters.nameValue"/>" id="hidden_cliente${parameters.sufijo?html}"/>
</#if>
<#if parameters.isDomicilio?default(false)>
	<input type="hidden" name="coDomicilio" value="<@s.property value="parameters.codigoValue"/>" id="hidden_coDomicilio"/>
	<input type="hidden" name="domicilio" value="<@s.property value="parameters.nameValue"/>" id="hidden_domicilio"/>
</#if>


<#-- jbenitac: cambio para controlar atributo salida -->
<#if parameters.salida?default(false)>

	<input type="hidden" name="nif${parameters.sufijo?html}" value="<@s.property value="parameters.nifValue"/>" id="nif_cliente${parameters.sufijo?html}" />
	<#if parameters.codigoValue??>
		<div class="campoSalida" title="<@s.property value="parameters.nifValue"/>&nbsp;-&nbsp;<@s.property value="parameters.nameValue"/>">
			<@s.property value="parameters.nifValue"/>&nbsp;-&nbsp;<@s.property value="parameters.nameValue"/>
		</div>
	<#else>
		<div class="campoSalida">&nbsp;</div>
	</#if>

<#else>

	<#if parameters.isCliente?default(false)>
		<input 
			type="text" name="nif${parameters.sufijo?html}" size="9" maxlength="9" value="<@s.property value="parameters.nifValue"/>" id="nif_cliente${parameters.sufijo?html}" style="float: left; margin-right: 5px" onkeyup="buscarCliente(this, 'hidden_coCliente${parameters.sufijo?html}', 'hidden_cliente${parameters.sufijo?html}', 'texto_cliente${parameters.sufijo?html}', '${parameters.cacheId?html}')" onblur="buscarCliente(this, 'hidden_coCliente${parameters.sufijo?html}', 'hidden_cliente${parameters.sufijo?html}', 'texto_cliente${parameters.sufijo?html}', '${parameters.cacheId?html}')" onchange="buscarCliente(this, 'hidden_coCliente${parameters.sufijo?html}', 'hidden_cliente${parameters.sufijo?html}', 'texto_cliente${parameters.sufijo?html}', '${parameters.cacheId?html}')"
			<#if hasFieldErrors>
			 	class="errorInput"
			</#if>
		/>
	</#if>
	
	<input type="button" style="background-image:  url('/etir/img/fdo_otros_botones.gif');<#rt/>
				border:0px;<#rt/>
		      	padding: 0px;<#rt/>
		      	height:20px;<#rt/>
		      	float:left;<#rt/>
		      	margin-right:5px;<#rt/>
		      	cursor: pointer;<#rt/>
		      	border: 1px solid #6ba0b8;"<#rt/>
		      	<#if parameters.disabled?default(false)>
				 disabled="disabled"<#rt/>
				</#if>
		      	
		      	value="Buscar"<#rt/>
		      	
				<#if parameters.onclick?exists>
				 onclick="mostrarVentanaSelect${parameters.id?html}('${parameters.sufijo?html}')"<#rt/>
				</#if>	
				><#rt/>
				
	<#if parameters.nameValue?default("") == ''>
		<div class="campoSalida" id="${parameters.id?html}" style="display:none"></div>
	<#else>
		<div 
			class="campoSalida" 
			id="${parameters.id?html}"
		>
			<@s.property value="parameters.nameValue"/>&nbsp;
		</div>
	</#if>
	
	<script>
	function mostrarVentanaSelect${parameters.id?html}(sufijoVar)
	{
		try{sufijo=sufijoVar;}catch(err){}
		try{sufijoBD=sufijoVar;}catch(err){}
		
		mostrarCargandoSelect${parameters.id?html}();
		
		setTimeout("${parameters.onclick?html}; dialogoCargar.hide();",500);
		
	}
	var dialogoCargar;
	function mostrarCargandoSelect${parameters.id?html}()
	{
		if (!dialogoCargar) {
			formCargando = new Ext.FormPanel(
					{
						split :true,
						labelAlign :'left',
						frame :true,
						buttonAlign :'center',
						bodyStyle :'padding:5px 5px 0',
						width :'100%',
						height :'100%',
						items : [{html:'Cargando...Espere, por favor.'}]
					});
			
			var panel = new Ext.Panel( {
				region :'center',
				split :true,
				width :200,
				collapsible :true,
				margins :'3 3 3 3',
				cmargins :'3 3 3 3'
			});
			panel.add(formCargando);
			
			dialogoCargar = new Ext.Window( {
				title :'',
				closable :false,
				modal :true,
				width :200,
				height :59,
				plain :true,
				layout :'border',
				items : [ panel ]
			});
		}
	
		dialogoCargar.show();
	}
	</script>

</#if>
