<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>FHI360 - Pruebas</title>
<link
	href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value="/resources/vendor/metisMenu/metisMenu.min.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/dist/css/sb-admin-2.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/vendor/morrisjs/morris.css" />"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value="/resources/vendor/font-awesome/css/font-awesome.min.css" />"
	rel="stylesheet" type="text/css">
<style>
iframe {
	max-width: 100%;
	max-height: 100%;
	height: auto;
}
</style>
</head>

<body>

	<div id="wrapper">
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">FHI360 - Prueba Reportes</a>
			</div>
			<!-- /.navbar-header -->
			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<li ><a href="#" ><i class="fa fa-bar-chart-o fa-fw"></i>
								Reportes<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="#" >Generales</a></li>
								<li><a href="#" >Departamento</a></li>
								<li><a href="#" >Sede</a></li>
							</ul></li>

						
					</ul>
				</div>
			</div>
		</nav>


		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12" style="text-align: center;">
					<h1 class="page-header"> REPORTES DE ACOMPA&Ntilde;ANTES </h1>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> Reporte Seleccionado
						</div>
						<div class="panel-body" style="height: 775px !important;">
						<div class="row">
							<div class="column col-lg-6" style="text-align: center;">
								<h3>Departamento:</h3>
								<div class="col-lg-12">${listaDepto}</div>
							</div>
							<div class="column col-lg-6" style="text-align: center;">
								<h3>Sede:</h3>
								<div class="col-lg-12" id="divSede">
								<select id="listasede" class="form-control">
									<option value="0"> Todos</option>
								</select>
								</div>
							</div>
						</div>	
						<br>					
						<div class="row" style="text-align: center;">
							<div class="column">
								<input type="button" id="botonConsulta" value="Consultar" class="btn btn-success">
							</div>
						</div>
						<br>
						<div style="text-align: center;" class ="table-responsive" id="tablaGeneral">${tabla}</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/metisMenu/metisMenu.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/raphael/raphael.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/morrisjs/morris.min.js" />"></script>
	<script src="<c:url value="/resources/data/morris-data.js" />"></script>
	<script src="<c:url value="/resources/dist/js/sb-admin-2.js" />"></script>

	<script type="text/javascript">
	
	
	$( document ).ready(function() {
		$("#listaDepto").change(function() {
			var depto = $("#listaDepto").val();
			  obtenerLista(depto);
		}); 
		$("#botonConsulta").click(function(){
			var depto = $("#listaDepto").val();
			var sede = $("#listasede").val();
			obtenerTabla(depto,sede);
		});
	});
			
		function obtenerLista(nomCampo){
			$.ajax({
		        url: "/reporteador/Tipos",
		        data: "camp="+ nomCampo,
		        type: "GET",
		        beforeSend: function() {
					$('#myModal').modal('show');
		        },
		        success : function(response) {
		        	if(response != ""){
		            	$("#divSede").html(response);   
		        	}
		        	else{
		            	$("#divSede").html("");  
		        	}
		        },
		        error : function(xhr, status, error) {
		            alert(xhr.responseText);
		        }
		    });
		}
		
		function obtenerTabla(depto,sede){
			$.ajax({
		        url: "/reporteador/Datos",
		        data: "depto="+ depto + "&sede=" + sede,
		        type: "GET",
		        beforeSend: function() {
					$('#myModal').modal('show');
		        },
		        success : function(response) {
		        	if(response != ""){
		            	$("#tablaGeneral").html(response);   
		        	}
		        	else{
		            	$("#tablaGeneral").html("");  
		        	}
		        },
		        error : function(xhr, status, error) {
		            alert(xhr.responseText);
		        }
		    });
		}
	</script>
</body>

</html>


