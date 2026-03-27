
package controlador;

import modelo_Dao.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.*;


public class Redireccion extends HttpServlet {      
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        
        switch (accion) {
            case "mostrarProducto":
                this.mostrarProducto(request, response);
                break; 
            case "mostrarCarrito":
                this.mostrarCarrito(request, response);
                break;
            case "mostrarModificarProducto":
                this.mostrarModificarProducto(request, response);
                break;
            case "mostrarNuevoProducto":
                this.mostrarNuevoProducto(request, response);
                break;
            case "mostrarRegistrarUsuario":
                this.mostrarRegistrarUsuario(request, response);
                break;
            case "mostrarRecuperarContra":
                this.mostrarRecuperarContra(request, response);
                break;
            case "mostrarContacto":
                this.mostrarContacto(request, response);
                break;
            case "mostrarMisCompras":
                this.mostrarMisCompras(request, response);
                break;
            case "mostrarMisDatos":
                this.mostrarMisDatos(request, response);
                break;
            case "editarCategorias":
                this.editarCategorias(request, response);
                break;
            case "editarMarcas":
                this.editarMarcas(request, response);
                break;                
            case "mostrarUsuarios":
                this.mostrarUsuarios(request, response);
                break;
            case "mostrarVentas":
                this.mostrarVentas(request, response);
                break;
            default:
                request.getRequestDispatcher("index.jsp").forward(request, response); 
        }
  
    }
    //Mostrar detalle del producto       
    private void mostrarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                
                ProductoDao pdao = new ProductoDaoImpl();
                Producto p = new Producto();
                
                try {
                    //Busco el producto con el id recibido 
                    int idprod = Integer.parseInt(request.getParameter("id"));
                    p = pdao.obtenerProducto(idprod);
                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("WEB-INF/páginas/detalleProducto.jsp").forward(request, response);                      
                } catch (SQLException e) {
                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("WEB-INF/páginas/detalleProducto.jsp").forward(request, response);                                          
                }
 
    }
    //Mostrar el carrito
    private void mostrarCarrito(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                //Controlo que la sesión no esté cerrada
                HttpSession sesion = request.getSession();        
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }        
                request.getRequestDispatcher("WEB-INF/páginas/carrito.jsp").forward(request, response);    
    } 
    //Modificar un producto
    private void mostrarModificarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
                ProductoDao pdao = new ProductoDaoImpl();
                Producto p;
                MarcaDao mdao = new MarcaDaoImpl();
                List <Marca> m;
                
                //Controlo que la sesión no esté cerrada
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }
                try {
                    //Busco el producto a mostrar y las marcas
                    int idp = Integer.parseInt(request.getParameter("id"));
                    p = pdao.obtenerProducto(idp);
                    m = mdao.listarTodasLasMarcas();     
                    sesion.setAttribute("marcas", m);
                    sesion.setAttribute("productoDetalle", p);
                    request.getRequestDispatcher("WEB-INF/páginas/modificarProducto.jsp").forward(request, response); 
                    
                } catch (SQLException e) {
                    sesion.removeAttribute("productoDetalle");
                    request.setAttribute("error", e.getMessage() );
                    request.getRequestDispatcher("WEB-INF/páginas/modificarProducto.jsp").forward(request, response);
                }
 
    } 
    
    private void mostrarNuevoProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }
                MarcaDao mdao = new MarcaDaoImpl();
                List<Marca> marcas = new ArrayList();
                try {
                    //Busco las marcas
                    marcas = mdao.listarTodasLasMarcas();
                    sesion.setAttribute("marcas", marcas);
                    request.getRequestDispatcher("WEB-INF/páginas/NuevoProducto.jsp").forward(request, response); 
                } catch (SQLException e) {
                    request.setAttribute("mensaje", "Ocurrió un error al mostrar la página Agregar Producto. Vuelva a intentarlo.");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeAtualizarProd.jsp").forward(request, response);
                }   
    } 
    
    private void mostrarRegistrarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                List l = new ArrayList();
                l.add("vacio");//Si está en vacio no se muestra ninguna notificación en la página registro
                request.setAttribute("mensaje", l);
                request.getRequestDispatcher("WEB-INF/páginas/registrarUsuario.jsp").forward(request, response);
    } 
    
    private void mostrarRecuperarContra(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                request.getRequestDispatcher("WEB-INF/páginas/recuperarContraseña.jsp").forward(request, response);
    }
    
    private void mostrarContacto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                HttpSession sessionOK=request.getSession();
                if(sessionOK.getAttribute("idUsuario")==null){
                   request.setAttribute("mensaje", "Debe registrarse o iniciar sesión para enviar una consulta");
                   request.getRequestDispatcher("WEB-INF/páginas/contacto.jsp").forward(request, response);
                }else{
                    UsuarioDao udao = new UsuarioDaoImpl();
                    Usuario u = new Usuario();
                    try {
                        //Busco los datos del usuario para mostrar en el formulario de contacto 
                        u = udao.buscarUsuarioPorId((int)sessionOK.getAttribute("idUsuario"));
                        sessionOK.setAttribute("Usuario", u);
                        request.getRequestDispatcher("WEB-INF/páginas/contacto.jsp").forward(request, response);                          
                    } catch (SQLException e) {
                        request.setAttribute("mensaje", "No se pudo mostrar la página de contacto, vuelva a intentarlo.");
                        request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
                    }

                }    
    } 
    private void mostrarMisCompras(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                int id;
                HttpSession sessionOK=request.getSession();
                ProductoDao pdao = new ProductoDaoImpl();
                CompraDao cdao = new CompraDaoImpl();
                DetalleCompraDao dcdao = new DetalleCompraDaoImpl();
                UsuarioDao udao = new UsuarioDaoImpl();             
                
                if(sessionOK.getAttribute("idUsuario")==null){
                   request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                   request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);

                }else{
                    try {
                        //Si las compras las está viendo el administrador agrego el usuario 
                        if(request.getParameter("idUsuario")!=null){
                            id = Integer.parseInt(request.getParameter("idUsuario"));
                            Usuario u = udao.buscarUsuarioPorId(id);
                            sessionOK.setAttribute("usuario_compras", u);
                        }else{
                            //Si no todo el id de la sesión 
                            id = (int)sessionOK.getAttribute("idUsuario");
                        }
                        //BUsco las compras y controlo si despues hay más compras para mostrar 
                        List<Compra> compras = new ArrayList();
                        List<DetalleCompra> detalleCompra = new ArrayList();
                        compras = cdao.obtenerComprasPorUsuario(id,0);
                        if(cdao.obtenerComprasPorUsuario(id,10).size()==0){
                           request.setAttribute("HayCompras","no");
                        }
                        //Recorro las compras añadiendo los detalles de compra a cada una 
                        for (Compra comp : compras) {
                             ArrayList<DetalleCompra> detalle = new ArrayList();
                             detalleCompra = dcdao.obtenerDetalleCompras(comp.getIdCompra());
                             for (DetalleCompra dc : detalleCompra) {
                                   dc.setProducto(pdao.obtenerProducto(dc.getIdProducto()));
                                   detalle.add(dc);
                            }
                            comp.setDetalleCompra(detalle);
                        }
                        sessionOK.setAttribute("paginaCompras", 1);
                        sessionOK.setAttribute("compras", compras);
                        sessionOK.setAttribute("detalleCompra", detalleCompra);
                        request.getRequestDispatcher("WEB-INF/páginas/misCompras.jsp").forward(request, response);

                    } catch (SQLException e) {
                        request.setAttribute("mensaje", "Ocurrió un problema al mostrar las compras. Vuelva a intentarlo.");
                        request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);                        
                    }                    
                }
            } 
    private void mostrarMisDatos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                HttpSession sessionOK=request.getSession();
                if(sessionOK.getAttribute("idUsuario")==null){
                   request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                   request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                }else{
                    UsuarioDao udao = new UsuarioDaoImpl();
                    Usuario u = new Usuario();
                    try {
                        u = udao.buscarUsuarioPorId((int)sessionOK.getAttribute("idUsuario"));                    
                        sessionOK.setAttribute("Usuario", u);
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    } catch (SQLException e) {
                        request.setAttribute("mensaje", e.getMessage());
                        request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response); 
                    }
                }    
    } 
    private void editarCategorias(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                                
                HttpSession sesion=request.getSession();
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }    
                CategoriaDao catdao = new CategoriaDaoImpl();
                ArrayList cantProductos;
                try {
                    //Cuento la cantidad de productos por categoría 
                    cantProductos = catdao.contarProductos(catdao.listarCategorias());
                    sesion.setAttribute("cantProd", cantProductos);
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response); 
                } catch (SQLException e) {
                    request.setAttribute("mensaje", e.getMessage());
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);
                }
                
    }
    
    private void editarMarcas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                                
                HttpSession sesion=request.getSession();
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }        
                MarcaDao mdao = new MarcaDaoImpl();
                ArrayList cantProductos = new ArrayList();
                List<Marca> m = new ArrayList();
                try {
                    //Busco las marcas y cuento la cantidad de productos por marca 
                    m = mdao.listarTodasLasMarcas();
                    cantProductos = mdao.contarProductosPorMarca(m);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("marcas", m);
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);                     
                } catch (SQLException e) {
                    request.setAttribute("mensaje", e.getMessage());
                    sesion.setAttribute("cambio", "No modificado");
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("marcas", m);
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);                     
                }
    } 

    
    private void mostrarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                HttpSession sesion=request.getSession();
                CompraDao cdao = new CompraDaoImpl();
                UsuarioDao udao = new UsuarioDaoImpl();                
                 //Controlo que la sesión no esté cerrada
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }    
                List <Usuario> usuarios = new ArrayList();
                List compras = new ArrayList();
                String tipo = request.getParameter("tipo");
                try {
                    //Busco los usuarios dependiendo el tipo (Usuario, Administrador, Eliminado)
                    usuarios=udao.buscarUsuarios(0,tipo);
                    for (Usuario u : usuarios) {
                        compras.add(cdao.contarComprasPorUsuario(u.getIdUsuario()));
                    }
                    int cantUsuarios = udao.contarUsuarios();
                    int cantAdmins = udao.contarAdministradores();
                    sesion.setAttribute("cantUsuarios", cantUsuarios);
                    sesion.setAttribute("cantAdmins", cantAdmins );
                    sesion.setAttribute("paginaUsuarios",1);
                    sesion.setAttribute("usuarios", usuarios);
                    sesion.setAttribute("compras", compras);
                    sesion.setAttribute("ordenar", "0");
                    sesion.setAttribute("busqueda", "N");
                    sesion.setAttribute("tipo", tipo);
                    //Controlo si hay más usuarios
                    if(udao.buscarUsuarios(10,tipo).size()==0){
                        request.setAttribute("HayUsuarios", "No");
                     }
                    request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("mensaje", e.getMessage());
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
                }          
    } 
    
    private void mostrarVentas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                HttpSession sesion=request.getSession();
                CompraDao cdao = new CompraDaoImpl();
                DetalleCompraDao dcdao = new DetalleCompraDaoImpl();
                ProductoDao pdao = new ProductoDaoImpl();
                UsuarioDao udao = new UsuarioDaoImpl();
                int pendientes;
                int entregados;
                String estado = request.getParameter("estado");
                List <Compra> comps = new ArrayList();
                List <Usuario> us = new ArrayList();
                
                 //Controlo que la sesión no esté cerrada
                if(sesion.getAttribute("idUsuario")==null){
                    request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                    return;
                }   
                try {
                    //Busco todas las ventas y controlo si hay más para mostrar en la siguiente página 
                    if(estado.equals("Todas")){
                        comps = cdao.obtenerTodasLasCompras(0);  
                        if(cdao.obtenerTodasLasCompras(10).size()==0){
                            request.setAttribute("HayVentas","no");
                        }                     
                    }else{
                        //Busco las ventas por pendiente o entregadas y controlo si hay más para mostrar 
                        comps = cdao.obtenerComprasPendientesoEntregadas(0, estado);
                        if(cdao.obtenerComprasPendientesoEntregadas(10, estado).size()==0){
                            request.setAttribute("HayVentas","no");
                        }           
                    }
                    //Recorro todas las compras y añado los detalles de las compras a cada una 
                    for (Compra compra : comps) {
                       us.add(udao.buscarUsuarioPorId(compra.getIdUsuario()));
                       ArrayList<DetalleCompra> detalle = new ArrayList();
                       detalle = dcdao.obtenerDetalleCompras(compra.getIdCompra());
                       for (DetalleCompra dc : detalle) {
                                   dc.setProducto(pdao.obtenerProducto(dc.getIdProducto()));
                            }
                       compra.setDetalleCompra(detalle);
                    }
                    pendientes = cdao.contarCompras("Pendiente");
                    entregados = cdao.contarCompras("Entregado");
                    sesion.setAttribute("estado", estado);
                    sesion.setAttribute("fechaDesde",null);
                    sesion.setAttribute("fechaHasta",null);
                    sesion.setAttribute("texto",null);
                    sesion.setAttribute("pendientes",pendientes);
                    sesion.setAttribute("entregados", entregados);
                    sesion.setAttribute("usuarios", us);
                    sesion.setAttribute("compras", comps);
                    sesion.setAttribute("paginaVentas",1);
                    sesion.setAttribute("mensajeBusqueda","No");
                    request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);

                } catch (SQLException e) {
                    request.setAttribute("mensaje", e.getMessage() );
                    request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
                }                          
    }  

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
