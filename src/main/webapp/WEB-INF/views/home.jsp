<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<div>${tabla}</div>
<script type="text/javascript">

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
            	$("#dvVal").css("display","none");
            	$("#dvList").css("display","");
            	$("#dvList").html(response);    	
            	buscar = 1;
        	}
        	else{
            	$("#dvList").css("display","none");  
            	$("#dvVal").css("display","");  
            	buscar = 0;
        	}
        	setTimeout(
      			  function() 
      			  {
    					$('#myModal').modal('hide');
      			  }, 1500);
        },
        error : function(xhr, status, error) {
            alert(xhr.responseText);
        }
    });	

</script>

</body>
</html>
