<%@page import="java.sql.SQLException"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="modelo.*" %>
<%@page import="modelo_Dao.*"%>
<%@page session="true" %>
<%@page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html class="h-100">
<head>
        <%@include file="/WEB-INF/include/head.jsp" %>
</head>

<body class="d-flex flex-column h-100">
    <!---------------------------HEADER------------------------------------------------------------------------->
	<% String home = "active"; %>
        
       <%@include file="/WEB-INF/include/header.jsp" %>

    <!---------------------------FIN HEADER--------------------------------------------------------------------->


    
    
    
    <!---------------------------CAROUSEL----------------------------------------------------------------------->
	<section>
            <div class="container-fluid">
		<div class="col-md-12">
                    <div id="carousel-Indicador" class="carousel slide carousel-1" data-ride="carousel">
			<!-- Indicadores -->
			<ol class="carousel-indicators">
                            <li data-target="#carousel-Indicador" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-Indicador" data-slide-to="1"></li>
                            <li data-target="#carousel-Indicador" data-slide-to="2"></li>
                            <li data-target="#carousel-Indicador" data-slide-to="3"></li>
			</ol>

			<!--contenedor de los slide-->
			<div class="carousel-inner">
                            <div class="carousel-item active">
                                <img class="d-block w-100 img-responsive" src="img/notebooks.png" alt="Primer slide">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block w-100 img-responsive" src="img/timthumb (4).jpg" alt="Segundo slide">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block w-100 img-responsive" src="img/timthumb (3).jpg" alt="Tercer slide" >
                            </div>
                            <div class="carousel-item">
                                <img class="d-block w-100 img-responsive" src="img/timthumb (2).jpg" alt="Tercer slide">
                            </div>
			</div>

			<!-- controles -->
			<a class="carousel-control-prev" href="#carousel-Indicador" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Anterior</span>
			</a>
			<a class="carousel-control-next" href="#carousel-Indicador" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Siguiente</span>
			</a>
                    </div>
		</div>
            </div>
	</section>
    <!---------------------------FIN CAROUSEL----------------------------------------------------------------------->

    <!---------------------------NOVEDADES----------------------------------------------------------------------->
	    <section>	
                <div class="d-flex justify-content-center">
                    <div class="col-md-12 productos nov" style="max-width: 1700px; ">
				<ol class="breadcrumb align-items-center">
			 		<h3>NOVEDADES</h3>
		  		</ol>
					

			  <div class="album py-5 al ">
			    <div class="container-fluid cont-novedades">
                                
                                  <%  
                                      String m = null;
                                      DecimalFormat formateador = new DecimalFormat("#,##0.00");
                                      ProductoDao pdao = new ProductoDaoImpl();
                                      List<Producto> productos = new ArrayList();
                                      try{
                                          productos = pdao.ListarNProductos(0,0);
                                      }catch(SQLException e){
                                          m = "NO HAY NOVEDADES";
                                      }
                                 %>                                   
                                <%
                                     if (m!=null) {
                                 %>
                                        <div class=" row justify-content-center">
                                           <h3 class="sinProductos text-center"><%=m%></h3>
                                        </div>  
                                  <%
                                       }

                                  %>

			      <div class="row">
  
                                      
                                  <%      
                                      for (Producto p: productos){
                                  %>   
                                                         
                                    <!---------Producto -->
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 coll ">
                                      <div class="card mb-3 shadow-sm">
                                        <a href="Redireccion?accion=mostrarProducto&id=<%= p.getIdProducto()%>">
                                            <img src="img/<%= p.getFoto()%>" class="w-100 img-articulo"></a>
                                        <div class="card-body">
                                          <a href="Redireccion?accion=mostrarProducto&id=<%= p.getIdProducto()%>"><h6 class="text-center"><%= p.getMarca().getNom_marca() %> <%= p.getModelo()%> <%= p.getMemoria_int()%></h6></a> 
                                          <p>Precio: <span> $<%=formateador.format(p.getPrecio())%></span></p>
                                          <div class="d-flex justify-content-center align-items-center">
                                            <div class="btn-group">
                                                <%if(sesionOK.getAttribute("perfil")!=null){
                                                    if(sesionOK.getAttribute("perfil").equals("Usuario")){
                                                %>
                                                    <form method="post" action="Controlador">
                                                        <button type="submit" class="btn btn-sm btn-comprar btn-outline-secondary"><i class="fas fa-cart-plus"></i> AGREGAR</button>
                                                        <input type="hidden" name="accion" value="agregarAlCarrito">
                                                        <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>">
                                                        <input type="hidden" name="cantidad" value="1">
                                                    </form>
                             
                                                <%}else if(sesionOK.getAttribute("perfil").equals("Administrador")){%>
                                                    <a href="Redireccion?accion=mostrarModificarProducto&id=<%= p.getIdProducto()%>" type="button" class="btn btn-sm btn-outline-secondary">MODIFICAR</a>
                                                    <a href="#" type="button" class="btn btnEliminar btn-sm btn-outline-secondary"  onclick="this.blur()" data-toggle="modal" data-target="#EliminarProducto<%=p.getIdProducto()%>">ELIMINAR</a>
                                                <%}}else{%>
                                                <a href=""  data-toggle="modal" data-target="#ventanaInicio" type="button" class="btn btn-sm btn-outline-secondary"><i class="fas fa-cart-plus"></i> AGREGAR</a>
                                               <%}%>
                                            </div>
                                           </div>
                                        </div>
                                      </div>
                                    </div>
                                    <%}%>
                                </div>
			    </div>
			  </div>
			</div>
                     </div>           
		</section>
	
    <!---------------------------FIN NOVEDADES-------------------------------------------------------------------->
    
        <!---------------------------VENTANA ELIMINAR PRODUCTO--------------------------------------------------->

    <section class="modificacionDatos">
            
        <%  
            for (Producto p : productos) {       
        %>
            <div class="modal fade"   role="dialog" id="EliminarProducto<%= p.getIdProducto() %>"  >
  		<div class="modal-dialog modal-dialog-centered"  role="document">
                    <div class="modal-content">
                        <form action="ControladorAdmin" method="post">
                            <div class="modal-header justify-content-center">
                                <h5 class="modal-tittle" id=""><i class="fas fa-exclamation-triangle"></i>  Eliminar Producto  <i class="fas fa-exclamation-triangle"></i></h5>
                            </div>

                            <div class="modal-body">
                                <div class="container">

                                    <div  class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <div class="form-group col-md-11 just text-center">
                                            
                                            <label class="just text-center">Eliminará el Producto: </label>
                                            <label class="just text-center"> <b><%=p.getMarca().getNom_marca()%>  <%=p.getModelo()%></b> </label>
                                            <input type="hidden" name="accion" value="EliminarProducto">
                                            <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>">
                                            <input type="hidden" name="marca" value="<%=p.getMarca().getNom_marca()%>">
                                            <input type="hidden" name="modelo" value="<%=p.getModelo()%>">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="reset" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary" id="categoria" >Confirmar</button>
                            </div>
                        </form>
                    </div>
 		</div>
            </div>
          <%}%>                                 
    	</section>
    <!---------------------------FIN VENTANA--------------------------------------------------------------------------------> 
    
    <!---------------------------VENTANA INICIO DE SESION------------------------------------------------------->

        <%@include file="/WEB-INF/include/ventanas/ventanaInicioSesion.jsp" %>

    <!---------------------------FIN VENTANA-------------------------------------------------------------------->

    <!---------------------FOOTER---------------------------------------------------------------------------------->

            
        <%@include file="/WEB-INF/include/footer.jsp" %>
    
    
    <!---------------------FIN FOOTER------------------------------------------------------------------------------>

        <%@include file="/WEB-INF/include/scripts.jsp" %>
        

</body>
</html>