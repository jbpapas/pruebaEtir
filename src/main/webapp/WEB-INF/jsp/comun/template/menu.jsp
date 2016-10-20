<%@ include file="../../taglibs.jsp"%>

<div id="contenido-menu" style="height:95%;">

	<div class="menu">
		<script type="text/javascript">
		$(document).ready(function(){	
			var menuCargado=false;	
			$('#contenido-barra-menu').click(function() {
				if (!menuCargado){
					var first = false;

					$.ajax({
				        type: "POST",
				        url: "menu!getMenuAjax.action",
				        success: function(datos){
							$('#divMenuGadir').html(datos);

							$(function () {
								$("#contenido-menu .menu-body").tree({
									selected : "jstree_<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getCoAcmMenuActualSoloDesdeJsp().trim()%>",
									callback : {
										onselect : function (NODE) {
											if(!first) {
											    first = true;
											    return;
										  	}
										  	if ('hoja'==$(NODE).attr("rel")){
												document.location.href = $(NODE).children("a:eq(0)").attr("href");
										  	}
										}/*,
										ondblclk : function (NODE) {
										  document.location.href = $(NODE).children("a:eq(0)").attr("href");
										}*/
									},	
									rules : {
										multiple : false				
									},
									types : {
										"default" : {
											clickable : true,
											renameable : false,
											deletable : false,
											creatable : false,
											draggable : false,
											max_children : -1,
											max_depth : -1,
											valid_children : "all",
											icon : {
												image : false,
												position: false
											}
										},
										"hoja" : {
											valid_children : "none",
											max_children : 0,
											max_depth : 0
											
										}
									}
								});
							});
							
							$(function () { 
								$('#divMenuGadir').toggle();
							});
							$('#contenido-menu .menu-header').click(function() {
								var cachos = this.id.split('_', 3);
								$('#'+cachos[0]+'_'+cachos[2]).toggle();
							});
							<%String coAcmMenuActual=es.dipucadiz.etir.comun.utilidades.DatosSesion.getCoAcmMenuActualSoloDesdeJsp();
							if (coAcmMenuActual!=null && !coAcmMenuActual.equals("") && coAcmMenuActual.length()>2){ %>
								$(function () { 
									$('li#jstree_<%=coAcmMenuActual %>').closest('div').show();
								});
							<%}%>
							menuCargado=true;
							<%if (es.dipucadiz.etir.comun.utilidades.DatosSesion.isUsuarioAccesible()) {%>
							abrirMenuCompleto();
							<%
							} else {
							%>
							if ($('#divMenuGadir .menu-body').size() == 1) {
								$('#divMenuGadir .menu-body').show();
							}
							<%
							}
							%>
				      	}
					});
				}
			});
		});
			
		function abrirMenuCompleto() {
			$('.menu-body').not('#jstree_GX').show();
			$('.menu-body .closed').attr("class", "open");
		}
		</script> 
		
		<div style="display:none" class="menu-header" id="jstree_header_GX">X</div>
		<div id="jstree_GX" style="display: none;" class="menu-body">
			<ul>
				<li rel="hoja" id="jstree_">
					<a title="X" href="#"><ins style="background-position: -16px 0pt;"> </ins>X </a>
				</li>
			</ul>
		</div>
		
		<div id="divMenuGadir" style="display:none">
			<div class="limpiar" ><br/></div>
		</div>
	
	</div>
</div>





		
		
		