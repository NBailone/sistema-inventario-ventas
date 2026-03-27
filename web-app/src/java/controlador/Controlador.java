
package controlador;


import modelo_Dao.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import modelo.*;
import javax.servlet.http.HttpSession;




public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Recibo la acción que realiza el usuario
        String accion = request.getParameter("accion");
        
        
        switch (accion) {
            case "mostrarProductos":
                this.mostrarProductosporCategoria(request, response);
                break;
            case "agregarAlCarrito":
                this.agregarAlCarrito(request, response);
                break;
            case "eliminardelCarrito":
                this.eliminardelCarrito(request, response);
                break;
            case "paginacion":
                this.paginacion(request, response);
                break;
            case "filtrar":
                this.filtrar(request, response);
                break;
            case "buscar":
                this.buscar(request, response);
                break;
            case "registrarVenta":
                this.registrarVenta(request, response);
                break;
            case "registrarUsuario":
                this.registrarUsuario(request, response);
                break;
            case "modificarDatosUsuario":
                this.modificarDatosUsuario(request, response);
                break;
            case "compras":
                this.compras(request, response);
                break;
            case "recuperarPassword":   
                this.recuperarPassword(request, response);
                break;
            case "consulta":   
                this.consulta(request, response);
                break;                
            default:
                request.getRequestDispatcher("index.jsp").forward(request, response);
            
        }
    }

    
    private void mostrarProductosporCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                
                ProductoDao pdao = new ProductoDaoImpl();
                CategoriaDao catdao = new CategoriaDaoImpl();
                MarcaDao mdao = new MarcaDaoImpl();
                Categoria c = new Categoria();
                 
                List<Producto>productos = new ArrayList();
                List marcas= new ArrayList();
                List elementos = new ArrayList();
                
                String mensaje = null;
                int idC;
                
                try {
                     idC = Integer.parseInt(request.getParameter("idCategoria"));   
                } catch (NumberFormatException e) {
                    idC = 0;
                }
                
                try{
                    c = catdao.buscarCategoria(idC);
                    productos = pdao.ListarNProductos(0,idC);          
                }catch(SQLException e){
                    idC = -1;
                    mensaje = e.getMessage();
                }
                
                // Si hay algún error con las marcas, la página se puede mostrar igual
                try{
                    if(idC==0){
                            marcas = mdao.listarTodasLasMarcas();
                    }else{
                            marcas = mdao.listarMarcasPorCategoria(idC);
                    }
                }catch(SQLException e){
                   marcas.clear();
                }
                 

                //Agrego los productos y el nombre de la categoría a la lista elementos
                elementos.add(productos);
                if(idC==0 || c.getNom_Categoria()==null){
                   elementos.add("TODOS");
                   elementos.add(0);//agrego el idCategoria
                }else if(c.getNom_Categoria()==null){
                   elementos.add("NO HAY PRODUCTOS");
                   elementos.add(idC);
                }else{
                   elementos.add(c.getNom_Categoria());
                   elementos.add(c.getIdCategoria());
                }
                //agrego los números para hacer la paginación
                elementos.add(0);
                elementos.add(2);
                elementos.add(3);
                elementos.add("p1");
                
                //Controlo si hay más productos para mostrar en la siguiente página
                try{
                    productos = pdao.ListarNProductos(12,idC);
                }catch(SQLException ex){
                    productos.clear();;
                }
                if(productos.size()==0){
                    elementos.add("vacio");
                }else{
                    elementos.add("Hay Productos");
                }
                // Agrego las marcas para mostrar en la ventana filtro
                elementos.add(marcas);
                elementos.add("Todas");//marca
                elementos.add("0");// sin ordenar
                elementos.add("0"); // precioDesde 
                elementos.add("999999999"); //precioHasta
                elementos.add("N"); //Bandera, avisa que no se usó la barra de busqueda, sino vale "B"
                elementos.add(null);
                elementos.add(mensaje);//mensaje de error
                request.setAttribute("productos", elementos);
                request.getRequestDispatcher("WEB-INF/páginas/productos.jsp").forward(request, response);
                
    }
    
    
    //Método añadir producto al carrito
    private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        HttpSession sesion = request.getSession();
        List<DetalleCompra> carrito;
        
        ProductoDao pdao = new ProductoDaoImpl();
        Producto p = new Producto();
        DetalleCompra d = new DetalleCompra();
        
        String mensaje;
        
        //Controlo que la sesión no esté cerrada
        if(sesion.getAttribute("idUsuario")==null){
            request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
            request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
            return;
        }
        //Controlo si existe el carrito en la sesión, sino se crea
        if (sesion.getAttribute("carrito") == null) {
            carrito = new ArrayList();    
        }else{
            carrito = (ArrayList<DetalleCompra>) sesion.getAttribute("carrito");
        }
        
        try{
            //Se busca el producto por id
            p = pdao.obtenerProducto(Integer.parseInt(request.getParameter("idProducto")));
            //Se añade el producto, cantidad y subtotal a detalle compra
            d.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
            d.setProducto(p);
            d.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            d.setSubtotal(d.getCantidad()* p.getPrecio());

            int bandera = -1;
            //Se recorre el carrito, si el producto ya estaba agregado se suman las cantidades y el subtotal
            //y la bandera pasa a estar en 1
            for(int i = 0; i < carrito.size(); i++){
                DetalleCompra detCom = carrito.get(i);
                if(detCom.getIdProducto()== p.getIdProducto()){
                    detCom.setCantidad(detCom.getCantidad() + d.getCantidad());
                    detCom.setSubtotal(detCom.getSubtotal()+ d.getSubtotal());
                    carrito.remove(i);
                    carrito.add(i, detCom);
                    bandera = 1;
                    break;
                }
            }
            //Si el producto no estaba en el carrito se agrega 
            if(bandera == -1 && d.getCantidad()>0 && d.getCantidad()<99 ){
                carrito.add(d);
            }
            //Se agrega el carrito a la sesión
            sesion.setAttribute("carrito", carrito);
            request.getRequestDispatcher("WEB-INF/páginas/carrito.jsp").forward(request, response);            
        }catch(SQLException e){
            mensaje = "Lo sentimos no se pudo agregar el producto al carrito. Vuelva a intentarlo.";
            request.setAttribute("mensaje", mensaje);
            request.getRequestDispatcher("WEB-INF/páginas/carrito.jsp").forward(request, response);
        }
   
    }
    
    //Método eliminar producto del carrito
    private void eliminardelCarrito(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        HttpSession sesion = request.getSession();
        List<DetalleCompra> carrito;
        
        //Controlo que la sesión no esté cerrada
        if(sesion.getAttribute("idUsuario")==null){
            request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
            request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
            return;
        }

        //Si hay una variable carrito en la sesión la guardo en el arreglo carrito
        carrito = new ArrayList();  
        if (sesion.getAttribute("carrito") != null) {
            carrito = (ArrayList<DetalleCompra>) sesion.getAttribute("carrito");
        }
        //Busco el producto a eliminar por idProducto y lo remuevo del carrito
        for(int i = 0; i < carrito.size(); i++){
            if(carrito.get(i).getIdProducto()==(Integer.parseInt(request.getParameter("idProducto")))){
                carrito.remove(i);
            }
        }
        //Si el carrito está vacío le asigno el valor null para saber que no hay más productos.
        if(carrito.size()!=0){
            sesion.setAttribute("carrito", carrito);
        }else{
            sesion.setAttribute("carrito",null );
        }
        request.getRequestDispatcher("WEB-INF/páginas/carrito.jsp").forward(request, response);        
    }   
    
    //Paginación 
    private void paginacion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        List<Producto>productos = new ArrayList();
        List elementos = new ArrayList();
        List marcas = new ArrayList();        
        
        ProductoDao pdao = new ProductoDaoImpl();
        MarcaDao mdao = new MarcaDaoImpl();
        CategoriaDao cdao = new CategoriaDaoImpl();
        
        String nomCAt = null;
        String mensaje = null;

        try {
            int pagina = Integer.parseInt(request.getParameter("nroPagina"));//Número de página de los productos mostrados
            int idCat = Integer.parseInt(request.getParameter("idCategoria"));
            int boton = Integer.parseInt(request.getParameter("boton"));//Botón que se seleccionó en la paginación 
            String marca= request.getParameter("marca");
            String ordena= request.getParameter("ordenar");
            double precioDesde= Double.parseDouble(request.getParameter("precioDesde"));
            double precioHasta= Double.parseDouble(request.getParameter("precioHasta"));
            String texto = request.getParameter("texto");
            String tipoBusqueda = request.getParameter("tipoBusqueda"); // si la busqueda es por texto o filtro


            //Si tengo un error al buscar las marcas, la página se puede mostrar igual, solo no aparecen las marcas
            try {
                nomCAt = cdao.buscarCategoria(idCat).getNom_Categoria();
                if(idCat==0){
                    marcas = mdao.listarTodasLasMarcas();
                }else{
                    marcas = mdao.listarMarcasPorCategoria(idCat);
                }            
            } catch (SQLException e) {
                marcas.clear();
            }

            //Defino desde que producto se va a hacer la consulta sql
            int comienzo;
            comienzo = (pagina - 1)*12;

            //Si se produce un error al verificar si hay más productos para mostrar en la siguente página, vacío la lista productos
            //y se bloquea la página sigueinte.
            try {
                // si es igual a B se hace la paginación con la busqueda ingresada por texto, sino con la ingresada 
                // en la ventana de filtros
                //Se controla si hay más producto para mostrar en la siguiente página. 
                if (tipoBusqueda.equals("B")) {
                    productos = pdao.buscarPorTextoIngresado(texto,(pagina)*12);
                }else{
                    productos = pdao.filtrar((pagina)*12,marca, idCat, ordena, precioDesde, precioHasta);
                }            
            } catch (SQLException e) {
                productos.clear(); //Se vacía productos si hay algun error 
            }


            //Si no hay productos para la próxima página 
            if (productos.isEmpty()) {
                //Busco los productos para mostrar
                try {
                    if (tipoBusqueda.equals("N")) {
                        //Busqueda por filtros
                        productos = pdao.filtrar(comienzo,marca, idCat, ordena, precioDesde, precioHasta);
                    }else{
                        //Busqueda por texto ingresado
                        productos = pdao.buscarPorTextoIngresado(texto, comienzo);
                    }                    
                } catch (SQLException e) {
                     mensaje = "Lo sentimos, no pudimos mostrar los productos. Vuelva a intentarlo.";
                }

                    elementos.add(productos);//Agrego los productos a la lista elementos
                    if(tipoBusqueda.equals("B")){
                        elementos.add("RESULTADO");//agrego la palabra resultado para mostrar 
                    }else if(nomCAt==null){
                        elementos.add("TODOS");
                    }else{
                        elementos.add(nomCAt); //Agrego la categoría seleccionada 
                    }
                    elementos.add(idCat); //Agrego el id de categoría
                    
                    //Modificó los botones de la paginación
                    if (boton==1 && pagina==1 ) {
                        elementos.add(0);
                        elementos.add(pagina+1);
                        elementos.add(pagina+2);
                        elementos.add("p1");
                    }else{
                        elementos.add(pagina-1);
                        elementos.add(pagina);
                        elementos.add(pagina+1);
                        if(boton==1 && pagina>1){
                            elementos.add("p1");
                        }else if (boton==2) {
                            elementos.add("p2");
                        }else if (boton==3) {
                            elementos.add("p3");
                        }

                    }
                    elementos.add("vacio");//Indica que no hay productos en  la siguiente página 
                    elementos.add(marcas);//agrego las marcas a mostrar en el filtro

                    elementos.add(marca);//marca
                    elementos.add(ordena);// orden
                    elementos.add((int)precioDesde); // precioDesde 
                    elementos.add((int)precioHasta); //precioHasta
                    elementos.add(tipoBusqueda); // indica que se realizo una busqueda de texto
                    elementos.add(texto); // texto de la busqueda
                    elementos.add(mensaje);//Si ocurrio un error el mensaje es distinto de null y se muestra en el jsp

                    request.setAttribute("productos", elementos );
                    request.getRequestDispatcher("WEB-INF/páginas/productos.jsp").forward(request, response); 

                }else{
                    //Si hay más productos para mostrar en la siguiente página
                    try {
                        if (tipoBusqueda.equals("N")) {
                            //Busqueda por filtro
                            productos = pdao.filtrar(comienzo,marca, idCat, ordena, precioDesde, precioHasta);
                        }else{
                            //Por texto ingresado
                            productos = pdao.buscarPorTextoIngresado(texto,comienzo);
                        }                        
                    } catch (SQLException e) {
                        mensaje = "Lo sentimos, no pudimos mostrar los productos. Vuelva a intentarlo.";
                    }

                    elementos.add(productos);
                    if(tipoBusqueda.equals("B")){
                        elementos.add("RESULTADO");
                    }else if(nomCAt==null){
                        elementos.add("TODOS");
                    }else{
                        elementos.add(nomCAt);
                    }
                    elementos.add(idCat);
                    //MOdifíco los botones de la paginación
                    if (boton==1 && pagina==1 ) {
                        elementos.add(0);
                        elementos.add(pagina+1);
                        elementos.add(pagina+2);
                        elementos.add("p1");
                    }else{
                        elementos.add(pagina-1);
                        elementos.add(pagina);
                        elementos.add(pagina+1);
                        if(boton==1 && pagina>1){
                            elementos.add("p1");
                        }else if (boton==2) {
                            elementos.add("p2");
                        }else if (boton==3) {
                            elementos.add("p3");
                        }

                    }   elementos.add("Hay Productos");
                        elementos.add(marcas);

                        elementos.add(marca);//marca
                        elementos.add(ordena);// orden
                        elementos.add((int)precioDesde); // precioDesde 
                        elementos.add((int)precioHasta); //precioHasta 
                        elementos.add(tipoBusqueda); // indica que se realizo una busqueda de texto
                        elementos.add(texto); // texto de la busqueda
                        elementos.add(mensaje); //Si ocurrio un error el mensaje es distinto de null y se muestra en el jsp


                    request.setAttribute("productos", elementos );
                    request.getRequestDispatcher("WEB-INF/páginas/productos.jsp").forward(request, response); 

                }            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Lo sentimos, no pudimos mostrar los productos. Vuelva a intentarlo");
            request.getRequestDispatcher("WEB-INF/páginas/productos.jsp").forward(request, response); 
        }

   
    }
    
    //Método filtrar productos
    private void filtrar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
       
        List<Producto>productos = new ArrayList();
        List<Marca> marcas = new ArrayList();
        List elementos = new ArrayList();
        
        MarcaDao mdao = new MarcaDaoImpl();
        ProductoDao pdao = new ProductoDaoImpl();
        CategoriaDao cdao = new CategoriaDaoImpl();
        
        int idCat;
        String marca;
        String ordena;
        String texto;
             
        idCat = Integer.parseInt(request.getParameter("idCategoria"));
        marca= request.getParameter("marca");
        ordena= request.getParameter("ordenar");
        texto = request.getParameter("busqueda");
           
        String mensaje = null;
        
        //Controlo si se ingresó preciodesde y precioHasta, sino quedan con valores predeterminados
        double precioDesde=0;
        double precioHasta=999999999;
        if(!request.getParameter("precioDesde").isEmpty()){
            precioDesde= Double.parseDouble(request.getParameter("precioDesde"));
        }
        if(!request.getParameter("precioHasta").isEmpty()){
           precioHasta= Double.parseDouble(request.getParameter("precioHasta"));
        }
        
        //Si ocurre un error al buscar las marcas,se limpia el arrglo marcas 
        //Pero la página se puede mostrar igual
        try {
            //Si la categoría es igual a cero se buscan todas las marcas
            if(idCat==0){
                marcas = mdao.listarTodasLasMarcas();
            }else{
                //Se busca la categoría ingresada
                marcas = mdao.listarMarcasPorCategoria(idCat);
            }            
        } catch (SQLException e) {
            marcas.clear(); 
        }
        //Se buscan los producto
        try {
            productos = pdao.filtrar(0,marca, idCat, ordena, precioDesde, precioHasta);
        } catch (SQLException e) {
            //Si hay un error se limpia el arreglo productos y se captura el mensaje de error
            mensaje = e.getMessage();
            productos.clear();
        }
        
        elementos.add(productos);
        
        //Busco el nombre de la categoría, si es null agrego Todos, sino la categoría buscada
        try {
            String nomCAt = cdao.buscarCategoria(idCat).getNom_Categoria();
                if (nomCAt==null) {
                    elementos.add("TODOS");   
            }else{
                    elementos.add(nomCAt); 
                }      
        } catch (SQLException e) {
            //Si Se produce un error al buscar la categoría, se puede mostrar igual la página
            elementos.add("TODOS"); 
        }
        //Agrego los botones de la paginación y la categoría 
        elementos.add(idCat);
        elementos.add(0);
        elementos.add(2);
        elementos.add(3);
        elementos.add("p1");
        
        //Controlo si hay más productos para mostrar en la siguiente página
        try {
            productos = pdao.filtrar(12,marca, idCat, ordena, precioDesde, precioHasta);
        } catch (SQLException e) {
            mensaje = e.getMessage();
            //Borro el producto si hay algún error al buscar los productos de la siguiente página
            productos.clear(); 
        }
  
        if (productos.isEmpty()) {
            elementos.add("vacio");
        }else{
            elementos.add("Hay Productos");
        }
        

        elementos.add(marcas);
        elementos.add(marca);//marca
        elementos.add(ordena);// orden
        elementos.add((int)precioDesde); // precioDesde 
        elementos.add((int)precioHasta); //precioHasta 
        elementos.add("N"); // indica que no se realizó una busqueda de texto
        elementos.add(texto); // texto de la busqueda
        elementos.add(mensaje); //Mensaje de error
        

        request.setAttribute("productos", elementos );
        request.getRequestDispatcher("WEB-INF/páginas/productos.jsp").forward(request, response);
        
    } 
    
    //Buscar por texto ingresado
    private void buscar(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException{
            
            
            ProductoDao pdao = new ProductoDaoImpl();            
            List elementos = new ArrayList();
            List <Marca>marcas = new ArrayList();
            List <Producto>ps = new ArrayList();            
            String mensaje= null;
            String texto;
            

            texto = request.getParameter("busqueda");

            //Si el texto ingresado es menor a 3 carateres o vacio no se buscan productos
            if (texto!=null && texto.length()>=2) {
                try {
                    ps = pdao.buscarPorTextoIngresado(texto,0);
                } catch (SQLException e) {
                   mensaje = e.getMessage();
                   ps.clear();
                } 
            }else{
                texto="La busqueda debe tener más de tres caracteres";
            }
            
            elementos.add(ps);//Productos 
            elementos.add("RESULTADO"); //nombre categoria
            elementos.add("0"); //id categoria
            elementos.add(0); //pagina1
            elementos.add(2);//pagina2
            elementos.add(3);//pagina3
            elementos.add("p1"); //boton1
            
           
            //Controlo si hay más productos para hacer la paginación
            try {
                if (pdao.buscarPorTextoIngresado(texto, 12).isEmpty()) {
                    elementos.add("vacio");
                }else{
                    elementos.add("Hay Productos");
                }
            } catch (SQLException e) {
                elementos.add("vacio"); //Si hay algun error asigo vacío
            }

            elementos.add(marcas); //Marcas a mostrar en el filtro
            elementos.add("Todas"); //marca seleccionada
            elementos.add("0"); //sin ordenar
            elementos.add("0"); // precioDesde
            elementos.add("99999999");//precioHasta
            elementos.add("B"); // indica que se realizo una busqueda de texto
            elementos.add(texto); // texto de la busqueda
            elementos.add(mensaje);
            request.setAttribute("productos", elementos );
            request.getRequestDispatcher("WEB-INF/páginas/productos.jsp").forward(request, response);  
        }
    //Registrar venta     
    private void registrarVenta(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException{
            
            HttpSession sesion = request.getSession();
            CompraDao cDAO = new CompraDaoImpl();
            Compra comp = new Compra();
            String mensaje = null;

            //Controlo que la sesión no esté cerrada
            if(sesion.getAttribute("idUsuario")==null){
                request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                return;
            }
            //Agrego el idUsuario a la compra y el carrito a DC (detalle compra)
            comp.setIdUsuario((int)sesion.getAttribute("idUsuario"));
            ArrayList<DetalleCompra> dC = (ArrayList<DetalleCompra>) sesion.getAttribute("carrito");

            Double total = 0.0;
            //Se calcula el total de la compra 
            for (DetalleCompra detalleCompra : dC) {
                total = total + detalleCompra.getSubtotal();
            }
            comp.setTotal(total);
            
            Boolean rpta = false;
            //Registro la compra, si hay algún error se captura el mensaje
            try {
                rpta = cDAO.insertarCompra(comp,dC);
            } catch (SQLException e) {
                mensaje = e.getMessage();
            }
            //Si se puedo registrar la compra se elimina la variabe carrito de la sesion y se informa
            if (rpta) {
                request.getSession().removeAttribute("carrito");
                request.setAttribute("mensaje", "Gracias por su compra, estamos preparando el pedido");
                request.getRequestDispatcher("WEB-INF/páginas/mensajecompraRegistrada.jsp").forward(request, response);     
            }else{
                //Se informa que no se pudo registrar la compra, enviando el mensaje capturado.
                request.setAttribute("mensaje", mensaje);
                request.getRequestDispatcher("WEB-INF/páginas/mensajecompraNoRegistrada.jsp").forward(request, response);
            }
        }
    //Registrar nuevo usuario 
    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        Usuario u = new Usuario();
        UsuarioDao udao = new UsuarioDaoImpl();
            
        try {
            u.setNombre(request.getParameter("nombre")); 
            u.setApellido(request.getParameter("apellido"));
            u.setNomUSuario(request.getParameter("usuario"));
            u.setLocalidad(request.getParameter("localidad"));
            u.setCalle(request.getParameter("calle"));
            u.setNumero(request.getParameter("numero"));
            u.setPiso(request.getParameter("piso"));
            u.setDepartamento(request.getParameter("departamento"));
            u.setEmail(request.getParameter("correo"));
            u.setPassword(request.getParameter("contraseña1"));
            String Password2 = request.getParameter("contraseña2");
            u.setTelefono(request.getParameter("telefono"));
            u.setPerfil("Usuario");

            List l = new ArrayList();

            if(udao.buscarNomUsuario(u.getNomUSuario())){      
                l.add("El usuario ya está registrado");
                l.add(u);
                request.setAttribute("mensaje", l);
                request.getRequestDispatcher("WEB-INF/páginas/registrarUsuario.jsp").forward(request, response);
            }else if(udao.buscarEmail(u.getEmail())){
                l.add("El Email ya está registrado");
                l.add(u);
                request.setAttribute("mensaje", l);
                request.getRequestDispatcher("WEB-INF/páginas/registrarUsuario.jsp").forward(request, response);
            }else if(!u.getPassword().equals(Password2)){
                l.add("Las Contraseñas no son iguales");
                l.add(u);
                request.setAttribute("mensaje", l);
               request.getRequestDispatcher("WEB-INF/páginas/registrarUsuario.jsp").forward(request, response); 
            }else if(udao.registrarUsuario(u)){
                request.setAttribute("mensaje", "Se registró con éxito, inicie sesión");
             request.getRequestDispatcher("WEB-INF/páginas/mensajeUsuarioRegistrado.jsp").forward(request, response);
            }else{
                l.add("Lo sentimos, algo salio mal, vuelva a intentar");
                l.add(u);
                request.setAttribute("mensaje", l);
             request.getRequestDispatcher("WEB-INF/páginas/registrarUsuario.jsp").forward(request, response);
            }            
        } catch (Exception e) {
            List l = new ArrayList();
            l.add(e.getMessage());
            l.add(u);
            request.setAttribute("mensaje", l);
            request.getRequestDispatcher("WEB-INF/páginas/registrarUsuario.jsp").forward(request, response);            
        }
    }
        
    //Modificar datos del usuario    
    private void modificarDatosUsuario(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException{
            
        
        HttpSession sessionOK = request.getSession(); 
        Usuario u = (Usuario)sessionOK.getAttribute("Usuario");
        UsuarioDao  udao = new UsuarioDaoImpl();

        //Controlo que la sesión no esté cerrada
        if(sessionOK.getAttribute("idUsuario")==null){
            request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
            request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
            return;
        }            
        
        try {
            String opcion = request.getParameter("valor");
            switch (opcion) {
                case "Nombre":
                    String nombre = request.getParameter("nombre");
                    if(u.getNombre().equals(nombre)){
                        //Si el valor es igual al ingresado se informa 
                        request.setAttribute("mensaje", "Datos no modificados: Nombre ingresado igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);  
                    }else if(udao.actualizarNombre(nombre, u.getIdUsuario())){
                        //Si se modifica, se actualiza el nombre y se informa 
                        u.setNombre(nombre);
                        sessionOK.setAttribute("Usuario", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el nombre, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }
                    break;
                case "Apellido":
                    String apellido = request.getParameter("apellido");
                    if(u.getApellido().equals(apellido)){
                        request.setAttribute("mensaje", "Datos no modificados: Apellido ingresado igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }else if(udao.actualizarApellido(apellido, u.getIdUsuario() )){
                        u.setApellido(apellido);
                        sessionOK.setAttribute("Usuario", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }

                    break;
                case "Email":
                    String email = request.getParameter("email");
                    if(u.getEmail().equals(email)){
                        request.setAttribute("mensaje", "Datos no modificados: Email ingresado igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }else if(udao.buscarEmail(email)){
                        request.setAttribute("mensaje", "El Email ingresado ya existe");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);                    
                    }else if(udao.actualizarEmail(email, u.getIdUsuario() )){
                        u.setEmail(email);
                        sessionOK.setAttribute("Usuario", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }
                    break;
                case "Direccion":
                    String localidad = request.getParameter("localidad");
                    String calle = request.getParameter("calle");
                    String numero = request.getParameter("numero");
                    String piso = request.getParameter("piso");
                    String departamento = request.getParameter("departamento");
                    if (piso.equals("")) { 
                        piso = "NO";
                    }
                    if(departamento.equals("")){
                        departamento = "NO";
                    }
                    if(u.getLocalidad().equals(localidad) && u.getCalle().equals(calle) && u.getNumero().equals(numero) && u.getPiso().equals(piso)  && u.getDepartamento().equals(departamento) ){
                        request.setAttribute("mensaje", "Datos no modificados: Dirección ingresada igual a la actual");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);  
                    }else if(udao.actualizarDireccion(localidad, calle, numero, piso, departamento, u.getIdUsuario() )){
                        u.setLocalidad(localidad);
                        u.setCalle(calle);
                        u.setNumero(numero);
                        u.setPiso(piso);
                        u.setDepartamento(departamento);
                        sessionOK.setAttribute("Usuario", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }
                    break;
                case "Password":
                    String passwordActual = request.getParameter("contraseña1");
                    String passwordNuevo1 = request.getParameter("contraseña2");
                    String passwordNuevo2 = request.getParameter("contraseña3");
                    if(!u.getPassword().equals(passwordActual)){
                       //Contraseña actual incorrecta
                        request.setAttribute("mensaje", "Datos no modificados: Contraseña actual incorrecta");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }else if(!passwordNuevo1.equals(passwordNuevo2)){
                        //Las contraseñas nuevas no coinciden 
                        request.setAttribute("mensaje", "Datos no modificados: Las contraseñas ingresadas no coinciden");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }else if(u.getPassword().equals(passwordNuevo1)){
                        //La contraseña nueva es igual a la ingresada
                        request.setAttribute("mensaje", "Datos no modificados: La contraseña nueva es igual a la actual");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }else if(udao.actualizarPassword(passwordNuevo1, u.getIdUsuario() )){
                        u.setPassword(passwordNuevo1);
                        sessionOK.setAttribute("Usuario", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }
                    break;                
                case "Telefono":
                    String telefono = request.getParameter("telefono");
                    if(u.getTelefono().equals(telefono) && !u.getTelefono().equals("") ){
                        request.setAttribute("mensaje", "Datos no modificados: El Número de teléfono ingresado es igual a la actual");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }else if(udao.actualizarTelefono(telefono, u.getIdUsuario() )){
                        u.setTelefono(telefono);
                        sessionOK.setAttribute("Usuario", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
                    }
                    break;
            }            
        } catch (SQLException e) {
            //Se captura el mensaje de error y se lo guarda en mensajes.
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/páginas/misDatos.jsp").forward(request, response);
        }

    }
    //Paginación compras
    private void compras(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            
            DetalleCompraDao dcdao = new DetalleCompraDaoImpl();
            CompraDao comdao = new CompraDaoImpl();
            UsuarioDao udao = new UsuarioDaoImpl();
            ProductoDao pdao = new ProductoDaoImpl();
            HttpSession sessionOK = request.getSession(); 
            int id;
            
            //Controlo que la sesión no esté cerrada
            if(sessionOK.getAttribute("idUsuario")==null){
                request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
            }else{
                try {
                    //Recibo la dirección y la página en la que estoy
                    String direccion = request.getParameter("direccion");
                    int p = Integer.parseInt(request.getParameter("pagina"));
                    //Si ingreso como administrador busco el usuario con el id que recibo
                    if(request.getParameter("idUsuario")!=null){
                        id = Integer.parseInt(request.getParameter("idUsuario"));
                        Usuario u = udao.buscarUsuarioPorId(id);
                        request.setAttribute("usuario_compras", u);
                    }else{
                        id = (int)sessionOK.getAttribute("idUsuario");
                    }                
                    List<Compra> compras;
                    //Busco las compras 
                    if(direccion.equals("siguiente")){
                        compras = comdao.obtenerComprasPorUsuario(id,p*10);
                        sessionOK.setAttribute("paginaCompras", p+1);
                        //Controlo si hay más compras
                        if(comdao.obtenerComprasPorUsuario(id,(p+1)*10).size()==0){
                           request.setAttribute("HayCompras","no");
                        }
                    }else{
                        //se establece el número de la página y se buscan las compras
                        if(p-1==1 ){
                            sessionOK.setAttribute("paginaCompras", 1);
                            compras = comdao.obtenerComprasPorUsuario(id,0);                        
                        }else{
                         sessionOK.setAttribute("paginaCompras", p-1);
                         compras = comdao.obtenerComprasPorUsuario(id,((p-1)*10)-10);                       
                        }

                    }
                    List<DetalleCompra> detalleCompra = new ArrayList();
                    //Recorro las compras y busco los detalles de compras de cada idCompra
                    for (Compra comp : compras) {
                        ArrayList<DetalleCompra> detalle = new ArrayList();
                        detalleCompra = dcdao.obtenerDetalleCompras(comp.getIdCompra());
                        //Recorro detalleCompra y busco los productos
                        for (DetalleCompra dc : detalleCompra) {
                            dc.setProducto(pdao.obtenerProducto(dc.getIdProducto()));
                            detalle.add(dc);//Agrego el detalle de la compra al arreglo detalle
                        }
                        //Agrego el los detalles de compra a la compra 
                        comp.setDetalleCompra(detalle);
                    }

                    sessionOK.setAttribute("compras", compras);
                    request.getRequestDispatcher("WEB-INF/páginas/misCompras.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("mensaje", e.getMessage() );
                    request.getRequestDispatcher("WEB-INF/páginas/misCompras.jsp").forward(request, response);
                }                
            }        
            
        }        

    //Recuperar contraseña
    private void recuperarPassword(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            
            
            UsuarioDao udao = new UsuarioDaoImpl();
            Usuario u = new Usuario();
            //Recibo el usuario o Email para recuperar la contraseña +
            String usuarioOEmail = request.getParameter("usuarioOEmail");
            //Email y contraseña del correo que envía la contraseña
            String username = "nb.electrognica.nb@gmail.com";
            String password = "";
            //Se busca el usuario o email
            try {
                u = udao.recuperarPassword(usuarioOEmail);
            } catch (SQLException e) {
                request.setAttribute("mensaje", e.getMessage());
                request.getRequestDispatcher("WEB-INF/páginas/recuperarContraseña.jsp").forward(request, response);
                return;
            }
            //Si no se encuentra el usuario se informa
            if(u.getNombre().equals("NO") ){
                    request.setAttribute("mensaje", "Usuario o Email incorrecto. Vuelva a intentarlo.");
                    request.getRequestDispatcher("WEB-INF/páginas/recuperarContraseña.jsp").forward(request, response);
            }else{
                    //Propiedades para enviar un email desde una cuenta gmail
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", true);
                    props.put("mail.smtp.starttls.enable", true);
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });

                    try {

                        Message mensaje = new MimeMessage(session);
                        mensaje.setFrom(new InternetAddress("nb.electronica.nb@gmail.com"));
                        mensaje.setRecipients(Message.RecipientType.TO,InternetAddress.parse(u.getEmail()));
                        mensaje.setSubject("Recuperación de contraseña");//Asunto

                        MimeMultipart multipart = new MimeMultipart("related");
                        BodyPart textPart = new MimeBodyPart();
                        String htmlText = 
                            "<html>"
                               + "<head>"
                                + "<style>h1,b {background-color: #F1F417;padding: 15px; text-indent: 0px; text-align: center; "
                                + "font-weight: lighter; border-radius: 60px;} </style>"
                                + "<style>b { color:#f3ed17 ;background-color: #2f312e;border-radius: 60px;padding: 7px;} </style>"
                               +"</head>"
                               + "<body><h1>NB ELECTRÓNICA</h1> " 
                                    +"<h2>Hola "+ u.getNombre()+"!!</h2>"
                                    +"<h2> Tu contraseña es: <b>"+u.getPassword()+"</b></h2>" 
                               +"</body>"
                            + "</html>";
                              textPart.setContent(htmlText, "text/html");

                              multipart.addBodyPart(textPart);
                              mensaje.setContent(multipart);

                              Transport.send(mensaje);  
                              
                        request.setAttribute("mensajeOk", "Enviamos un email para recuperar su contraseña");
                        request.getRequestDispatcher("WEB-INF/páginas/recuperarContraseña.jsp").forward(request, response);                              

                    } catch (MessagingException e) {
                        request.setAttribute("mensaje","Lo sentimos, no pudimos enviar el mail con la contraseña. Vuelva a intentarlo.");
                        request.getRequestDispatcher("WEB-INF/páginas/recuperarContraseña.jsp").forward(request, response);  
                    }                
            }                  
        }         

    private void consulta(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException{
            
            HttpSession sessionOK = request.getSession();

            String correoUsuario = request.getParameter("correo");
            String nombre = request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
            String consulta = request.getParameter("consulta");
            String usuario = request.getParameter("usuario");
            
            if(telefono.equals("")){
                telefono= "Sin Número";
            }
            //Correo desde donde se envían las consultas
            String username = "consulta.nbelectronica@gmail.com";
            String password = "NBElectronica";
            //Controlo que la sesión no esté cerrada
            if(sessionOK.getAttribute("idUsuario")==null){
                request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
            }else{
                Properties props = new Properties();
                props.put("mail.smtp.auth", true);
                props.put("mail.smtp.starttls.enable", true);
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

                Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

                    try {
                        //Para hacer las consultas envío del correo consulta.nbelectrónica a nb.electronica.nb
                        Message mensaje = new MimeMessage(session);
                        mensaje.setFrom(new InternetAddress("consulta.nbelectronica@gmail.com"));
                        mensaje.setRecipients(Message.RecipientType.TO,InternetAddress.parse("nb.electronica.nb@gmail.com"));
                        mensaje.setSubject("Consulta Usuario: "+usuario);//Asunto

                        MimeMultipart multipart = new MimeMultipart("related");
                        BodyPart textPart = new MimeBodyPart();
                        String htmlText = 
                            "<html>"
                               + "<head>"
                                + "<style>h1,b {background-color: #F1F417;padding: 15px; text-indent: 0px; text-align: center; font-weight: lighter; border-radius: 60px;} </style>"
                                + "<style>b { color:#f3ed17 ;background-color: #2f312e;border-radius: 60px;padding: 7px;} </style>"
                               +"</head>"
                               + "<body><h1>Consulta Usuario: "+usuario+"</h1> " 
                                    +"<h2> Nombre: <b>"+nombre+"</b></h2>"
                                    +"<h2> Correo: <b>"+correoUsuario+"</b></h2>"
                                    +"<h2> Teléfono: <b>"+telefono+"</b></h2>"
                                    +"<h2> Consulta:<b>"+consulta+"</b> </h2>"
                               +"</body>"
                            + "</html>";
                              textPart.setContent(htmlText, "text/html");

                              multipart.addBodyPart(textPart);
                              mensaje.setContent(multipart);

                              Transport.send(mensaje);  
                              
                        request.setAttribute("mensajeOk", "Registramos su consulta, en breve estaremos respondiendo");
                        request.getRequestDispatcher("WEB-INF/páginas/contacto.jsp").forward(request, response);                              

                    } catch (MessagingException e) {
                        request.setAttribute("mensajeNo", "Lo sentimos, no pudimos enviar la consulta. Vuelva a intentarlo.");
                        request.getRequestDispatcher("WEB-INF/páginas/contacto.jsp").forward(request, response);  

                    }           
            }                 
    }             
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
