
package controlador;

import modelo_Dao.*;
import java.io.File;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class ControladorAdmin extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            String accion = request.getParameter("accion");
            HttpSession sesion = request.getSession();
            //Controlo que la sesión no esté cerrada
            if(sesion.getAttribute("idUsuario")==null){
                request.setAttribute("mensaje", "La sesión expiró, por favor vuelva a ingresar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeSesionExpirada.jsp").forward(request, response);
                return;
            }   
            
            switch (accion){
            case "modificarProducto":
                this.modificarProducto(request, response);
                break;
            case "nuevoProducto": 
                this.guardarProductoNuevo(request, response);
                break;
            case "EliminarProducto":
                this.eliminarProducto(request, response);
                break;                
            case "modificarCategoria":
                this.modificarCategoria(request, response);
                break;
            case "agregarCategoria":
                this.agregarCategoria(request, response);
                break;
            case "eliminarCategoria":
                this.eliminarCategoria(request, response);
                break;
            case "modificarMarca":
                this.modificarMarca(request, response);
                break;
            case "agregarMarca":
                this.agregarMarca(request, response);
                break;
            case "eliminarMarca":
                this.eliminarMarca(request, response);
                break;                
            case "anterior":
                this.anterior(request, response); 
                break;
            case "siguiente":
                this.siguiente(request, response);
                break;
            case "ordenarUsuarios":
                this.ordenarUsuarios(request, response);
                break;
            case "editarUsuario":
                this.editarUsuario(request, response);
                break;
            case "modificarDatosUsuario":
                this.modificarDatosUsuario(request, response);
                break;
            case "buscarUsuarios":
                this.buscarUsuarios(request, response);
                break;
            case "eliminarUsuario":
                this.eliminarUsuarios(request, response);
                break;
            case "actualizarEstadoCompra":
                this.actualizarEstadoCompra(request, response);
                break;
            case "buscarVentas":
                this.buscarVentas(request, response);
                break;
            case "paginacionVentas":
                this.paginacionVentas(request, response);  
                break;
            default:
                request.getRequestDispatcher("index.jsp").forward(request, response);                
            }  
    }
    //Modificar Producto
    private void modificarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
            HttpSession sesion= request.getSession();
            Producto prod = new Producto();
            int bandera= 0;
            Categoria c = new Categoria();
            CategoriaDao cdao = new CategoriaDaoImpl();
            ProductoDao pdao = new ProductoDaoImpl();
            ArrayList<String> lista = new ArrayList<>();
            MarcaDao mdao = new MarcaDaoImpl();
            
            try {
                FileItemFactory file = new DiskFileItemFactory();
                ServletFileUpload fileUpload = new ServletFileUpload(file);
                List items  = fileUpload.parseRequest(request);
                for (int i = 0; i < items.size(); i++) {
                    FileItem fileItem = (FileItem)items.get(i);
                    //Si el item es una foto la guardo
                    if (!fileItem.isFormField()) {
                        //Si se agregó una imagen la guardo
                        if (fileItem.getSize()!=0) {
                            //localhost 
                            //File f = new File("D:/Biblioteca/Documentos/NetBeansProjects/NB_ELECTRONICA/web/img/"+fileItem.getName());
                            //Servidor
                            File f = new File(getServletContext().getRealPath("/")+"img//"+fileItem.getName()); 
                            fileItem.write(f);
                            prod.setFoto(fileItem.getName());
                            bandera=1;
                        }
                    }else{
                        //Guardo los demas datos del formulario en una lista
                        lista.add(fileItem.getString());   
                    }            

                }
      
                c.setIdCategoria(Integer.parseInt(lista.get(0)));
                prod.setCategoria(cdao.buscarCategoria(c.getIdCategoria()));
                prod.setMarca(mdao.buscarMarca(Integer.parseInt(lista.get(1))));
                prod.setIdProducto(Integer.parseInt(lista.get(2)));
                prod.setModelo(lista.get(3));
                prod.setPantalla(lista.get(4));
                prod.setProcesador(lista.get(5));
                prod.setMemoria_ram(lista.get(6));
                prod.setMemoria_int(lista.get(7));
                prod.setBateria(lista.get(8));
                prod.setPrecio(Double.parseDouble(lista.get(9)));
                prod.setDescripcion(lista.get(10));
                //Controlo si se agregó una foto nueva
                if (bandera==0) {
                    prod.setFoto(lista.get(11));
                }
                
                pdao.actualizarProducto(prod);
                request.setAttribute("mensajeOk", "Producto Actualizado");
                sesion.setAttribute("productoDetalle", prod);
            } catch (Exception e) {
                request.setAttribute("mensaje", "No se pudo actualizar el producto, vuelva a intentarlo");
            }
            request.getRequestDispatcher("WEB-INF/páginas/modificarProducto.jsp").forward(request, response);                
    }
        
    private void guardarProductoNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
   
            String mensaje="No se pudo agregar el producto";
            Categoria c = new Categoria();
            CategoriaDao cdao = new CategoriaDaoImpl();
            Producto p = new Producto(); 
            ProductoDao pdao = new ProductoDaoImpl();
            ArrayList<String> lista = new ArrayList<>();
            MarcaDao mdao = new MarcaDaoImpl();
            

            try {
                //La clase FileItemFactory procesa todos los campos en la forma de la clase multipart
                //FileItemFactory es una interfaz que se utiliza para construir una instacia de FileItem
                FileItemFactory file = new DiskFileItemFactory();
                //ServletFileUpload devuelve una colección de elementos del formulario encapsulados como FileItem en forma de Lista
                ServletFileUpload fileUpload = new ServletFileUpload(file);
                //El método parseRequest() devuelve una lista de los elementos del formulario
                List items  = fileUpload.parseRequest(request);
                
                for (int i = 0; i < items.size(); i++) {
                    FileItem fileItem = (FileItem)items.get(i);
                    //Si el item es una foto la guardo
                    if (!fileItem.isFormField()) {
                        //localhost
                        //File f = new File("D:/Biblioteca/Documentos/NetBeansProjects/NB_ELECTRONICA/web/img/"+fileItem.getName());
                        //Servidor
                        File f = new File(getServletContext().getRealPath("/")+"img//"+fileItem.getName()); 
                        fileItem.write(f);
                        p.setFoto(fileItem.getName());
                    }else{
                        //Guardo los demas datos del formulario en una lista
                        lista.add(fileItem.getString());   
                    }            

                }
                     c.setIdCategoria(Integer.parseInt(lista.get(0)));
                     p.setCategoria(cdao.buscarCategoria(c.getIdCategoria()));
                     p.setMarca(mdao.buscarMarca(Integer.parseInt(lista.get(1))));
                     p.setModelo(lista.get(2));
                     p.setPantalla(lista.get(3));
                     p.setProcesador(lista.get(4));
                     p.setMemoria_ram(lista.get(5));
                     p.setMemoria_int(lista.get(6));
                     p.setBateria(lista.get(7));
                     p.setPrecio(Double.parseDouble(lista.get(8)));
                     p.setDescripcion(lista.get(9));
                     
                     
                     if(pdao.agregarProducto(p)){
                      mensaje = "Producto: "+p.getMarca().getNom_marca()+" "+p.getModelo()+" Agregado"; 
                     }
                     request.setAttribute("mensaje", mensaje);
                     request.getRequestDispatcher("WEB-INF/páginas/mensajeAtualizarProd.jsp").forward(request, response);
            }catch(SQLException ex){
                request.setAttribute("mensaje", "No se pudo agregar el producto, vuelva a intentar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeAtualizarProd.jsp").forward(request, response);                
            }catch(FileUploadException fu){
                request.setAttribute("mensaje", "Ocurrió un error al procesar la solicitud, vuelva a intentar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeAtualizarProd.jsp").forward(request, response);
            }catch(Exception e){
                request.setAttribute("mensaje", "Ocurrió un error al guardar la imagen, vuelva a intentar");
                request.getRequestDispatcher("WEB-INF/páginas/mensajeAtualizarProd.jsp").forward(request, response);                
            }                     
    }
        
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            
            int idProd = Integer.parseInt(request.getParameter("idProducto"));
            String marca = (String)request.getParameter("marca");
            String modelo = (String)request.getParameter("modelo");
            String mensaje;
            ProductoDao pdao = new ProductoDaoImpl();
            
            try { 
                //Se elimina el producto y se informa 
                pdao.eliminarProducto(idProd);
                mensaje = "Se eliminó el producto: "+marca+" "+modelo;
                request.setAttribute("mensaje", mensaje);
                request.getRequestDispatcher("WEB-INF/páginas/mensajeAtualizarProd.jsp").forward(request, response);  
            } catch (SQLException e) {
                try {
                    HttpSession sesion = request.getSession();
                    MarcaDao mdao = new MarcaDaoImpl();
                    //Busco el producto para mostrarlo en la página con el mensaje de error
                    sesion.setAttribute("productoDetalle", pdao.obtenerProducto(idProd));
                    sesion.setAttribute("marcas", mdao.listarTodasLasMarcas());
                    mensaje = "Lo sentimos no se pudo eliminar el producto: "+marca+" "+modelo;
                    request.setAttribute("mensaje", mensaje);
                    request.getRequestDispatcher("WEB-INF/páginas/modificarProducto.jsp").forward(request, response);                    
                } catch (SQLException ex) {
                    request.setAttribute("mensaje", "No se pudo eliminar el producto");
                    request.getRequestDispatcher("WEB-INF/páginas/modificarProducto.jsp").forward(request, response); 
                }
            }        
    }
    
    private void modificarCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
            CategoriaDao catdao = new CategoriaDaoImpl();
            int idC = Integer.parseInt(request.getParameter("idCategoria"));
            String nomCategoría = request.getParameter("categoria");
            HttpSession sesion = request.getSession();
            List<Categoria> listcat;
            boolean bandera = true;
            try{
                //Busco las categorías y las recorro para ver si ya existe 
                listcat=catdao.listarCategorias();
                for (Categoria c : listcat) {
                    if(c.getNom_Categoria().equals(nomCategoría)){
                        bandera= false;
                        request.setAttribute("mensaje", "No se modificó: La categoría "+nomCategoría+" ya existe");
                        sesion.setAttribute("cambio", "No modificado");
                        request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);
                        return;
                    }
                }
                //La bandera me indica que la categoría no existe y la agrego
                if(bandera && catdao.actualizarCategoria(nomCategoría, idC)){
                    ArrayList cantProductos;
                    listcat=catdao.listarCategorias();
                    cantProductos = catdao.contarProductos(listcat);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("cambio", "modificado");
                    request.setAttribute("mensaje", "Categoría Modificada");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);
                }
            }catch(SQLException e){
                request.setAttribute("mensaje", e.getMessage());
                sesion.setAttribute("cambio", "No modificado");
                request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);                
            }
            
    }

    private void agregarCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
            boolean yaExiste = false;
            String nomCategoría = request.getParameter("categoria");
            HttpSession sesion = request.getSession();
            List<Categoria> listcat;
            CategoriaDao catdao = new CategoriaDaoImpl();
            
            try {
                //Busco las categorías y las recorro buscando si el nombre ya existe 
                listcat=catdao.listarCategorias();
                for (Categoria c : listcat) {
                    if(c.getNom_Categoria().equals(nomCategoría)){
                        yaExiste = true;
                        request.setAttribute("mensaje", "No se pudo agregar: La categoría "+nomCategoría+" ya existe");
                        sesion.setAttribute("cambio", "No modificado");
                        request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);
                        return;
                    }
                }
                //Si no existe y se pudo agregar lo informo 
                if(!yaExiste && catdao.agregarCategoria(nomCategoría)){
                    ArrayList cantProductos;
                    listcat=catdao.listarCategorias();
                    cantProductos = catdao.contarProductos(listcat);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("cambio", "modificado");
                    request.setAttribute("mensaje", "Categoría "+nomCategoría+" agregada");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);
                }else{
                    sesion.setAttribute("cambio", "No modificado");
                    request.setAttribute("mensaje", "Intente nuevamente, no se pudo agregar la categoria");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                    sesion.setAttribute("cambio", "No modificado");
                    request.setAttribute("mensaje", e.getMessage());
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);                
            }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
            int idC = Integer.parseInt(request.getParameter("idCategoria"));
            String nomCategoría = request.getParameter("categoria");
            HttpSession sesion = request.getSession();
            CategoriaDao catdao = new CategoriaDaoImpl();
            try {
                //Cuento los productos de la categoría a eliminar
                int cant=catdao.contarProductosUnaCategoria(idC);
                if(cant>0){
                    if(cant==1){
                        request.setAttribute("mensaje", "No se pueden eliminar categorias que tengan productos: La categoría "+nomCategoría+" tiene: " +cant+" producto");
                    }else{
                        request.setAttribute("mensaje", "No se pueden eliminar categorias que tengan productos: La categoría "+nomCategoría+" tiene: " +cant+" productos");
                    }
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);                
                }else if(cant==-1){
                    request.setAttribute("mensaje", "Lo sentimos no se pudo Eliminar la categoría "+nomCategoría+". Vuelva a intentarlo");
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);   
                }else if(!catdao.eliminarCategoria(idC)){
                    request.setAttribute("mensaje", "Lo sentimos no se pudo Eliminar la categoría "+nomCategoría+". Vuelva a intentarlo");
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);  
                }else{
                    ArrayList cantProductos;
                    List<Categoria> listcat;
                    listcat=catdao.listarCategorias();
                    cantProductos = catdao.contarProductos(listcat);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("cambio", "modificado");
                    request.setAttribute("mensaje", "Categoría "+nomCategoría+" eliminada");
                    request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response);

                }        
            } catch (SQLException e) {
                request.setAttribute("mensaje", "Lo sentimos no se pudo Eliminar la categoría "+nomCategoría+". Vuelva a intentarlo");
                sesion.setAttribute("cambio", "No modificado");
                request.getRequestDispatcher("WEB-INF/páginas/editarCategorias.jsp").forward(request, response); 
            }    
            
    }    

    private void modificarMarca(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
            int idM = Integer.parseInt(request.getParameter("idMarca"));
            String nomMarca = request.getParameter("marca");
            HttpSession sesion = request.getSession();
            List<Marca> listmarcas;
            boolean bandera = true;
            Marca marca;
            MarcaDao mdao = new MarcaDaoImpl();
            try {
                //Busco todas las marcas y controlo que no exista el nombre 
                listmarcas = mdao.listarTodasLasMarcas();
                for (Marca m : listmarcas) {
                    if(m.getNom_marca().equals(nomMarca)){
                        bandera= false;
                        request.setAttribute("mensaje", "No se modificó: La marca "+nomMarca+" ya existe");
                        sesion.setAttribute("cambio", "No modificado");
                        request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);
                    }
                }
                marca = mdao.buscarMarca(idM);
                //La bandera me infdica que la marca no existe 
                if(bandera && mdao.actualizarMarca(nomMarca, idM)){
                    ArrayList cantProductos;
                    //Actualizo las marcas a mostrar 
                    listmarcas=mdao.listarTodasLasMarcas();
                    cantProductos = mdao.contarProductosPorMarca(listmarcas);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("cambio", "modificado");
                    sesion.setAttribute("marcas", listmarcas);
                    request.setAttribute("mensaje", "Marca: "+marca.getNom_marca()+ " Modificada a: "+nomMarca);
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("mensaje", e.getMessage());
                sesion.setAttribute("cambio", "No modificado");
                request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);
            }            
    }

    private void agregarMarca(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            boolean yaExiste = false;
            String nomMarca = request.getParameter("marca");
            HttpSession sesion = request.getSession();
            List<Marca> listMarca;
            MarcaDao mdao = new MarcaDaoImpl();
            try {
                //Busco todas las marcas y controlo que no exista el nombre 
                listMarca=mdao.listarTodasLasMarcas();
                for (Marca m : listMarca) {
                    if(m.getNom_marca().equals(nomMarca)){
                        yaExiste = true;
                        request.setAttribute("mensaje", "No se pudo agregar: La Marca "+nomMarca+" ya existe");
                        sesion.setAttribute("cambio", "No modificado");
                        request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);
                        return;
                    }
                }
                if(!yaExiste && mdao.agregarMarca(nomMarca) ){
                    ArrayList cantProductos;
                    listMarca=mdao.listarTodasLasMarcas();
                    cantProductos = mdao.contarProductosPorMarca(listMarca);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("cambio", "modificado");
                    sesion.setAttribute("marcas", listMarca);
                    request.setAttribute("mensaje", "Marca "+nomMarca+" agregada");
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);
                }else{
                    sesion.setAttribute("cambio", "No modificado");
                    request.setAttribute("mensaje", "Intente nuevamente, no se pudo agregar la marca");
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);
                }   
            } catch (SQLException e) {
                sesion.setAttribute("cambio", "No modificado");
                request.setAttribute("mensaje", e.getMessage());
                request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);                
            }    
    }

    private void eliminarMarca(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
            int idM = Integer.parseInt(request.getParameter("idMarca"));
            String nomMarca = request.getParameter("marca");
            HttpSession sesion = request.getSession();
            MarcaDao mdao = new MarcaDaoImpl();
            
            try {
                //Cuento los productos de la marca a eliminar, si tiene productos no lo elimino 
                int cant=mdao.contarProductosUnaMarca(idM);
                if(cant>0){
                    if(cant==1){
                        request.setAttribute("mensaje", "No se pueden eliminar marcas que tengan productos: La marca "+nomMarca+" tiene: " +cant+" producto");
                    }else{
                        request.setAttribute("mensaje", "No se pueden eliminar marcas que tengan productos: La categoría "+nomMarca+" tiene: " +cant+" productos");
                    }
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);                
                }else if(cant==-1){
                    request.setAttribute("mensaje", "Lo sentimos no se pudo Eliminar la Marca "+nomMarca+". Vuelva a intentarlo");
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);   
                }else if(!mdao.eliminarMarca(idM)){
                    request.setAttribute("mensaje", "Lo sentimos no se pudo Eliminar la Marca "+nomMarca+". Vuelva a intentarlo");
                    sesion.setAttribute("cambio", "No modificado");
                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);  
                }else{
                    ArrayList cantProductos;
                    List<Marca> listmarcas;
                    listmarcas=mdao.listarTodasLasMarcas();
                    cantProductos = mdao.contarProductosPorMarca(listmarcas);
                    sesion.setAttribute("cantProd", cantProductos);
                    sesion.setAttribute("cambio", "modificado");
                    sesion.setAttribute("marcas", listmarcas);
                    request.setAttribute("mensaje", "Marca "+nomMarca+" eliminada");

                    request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response);

                }        
            } catch (SQLException e) {
                request.setAttribute("mensaje","No se pudo eliminar la marca, vuelva a intentarlo");
                sesion.setAttribute("cambio", "No modificado");
                request.getRequestDispatcher("WEB-INF/páginas/editarMarcas.jsp").forward(request, response); 
            }             
    }      
    //Paginación anterior Usuarios 
    private void anterior(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
    
        HttpSession sesion = request.getSession();
        String ordenar = (String)sesion.getAttribute("ordenar");//Recibo el orden 
        String texto = (String)sesion.getAttribute("busqueda");//Recibo la busqueda por texto
        int p = Integer.parseInt(request.getParameter("pagina"));//En que pagina estoy 
        String tipo = (String)sesion.getAttribute("tipo");//El tipo de usuario 
        UsuarioDao udao = new UsuarioDaoImpl();
        CompraDao compdao = new CompraDaoImpl();

        List <Usuario> usuarios;
        List compras = new ArrayList();
        p=p-1;
        try {
            //Busco los usuarios, por texto, orden. O sin orden ni texto
            if(!texto.equals("N")){
                usuarios = udao.buscarUsuariosPorTexto(texto, p*10-10, tipo);
            }else if(ordenar.equals("0") ){
                usuarios=udao.buscarUsuarios(p*10-10,tipo);
            }else{
                usuarios = udao.ordenarUsuarios(ordenar, p*10-10, tipo);
            }
            //Cuentos las compras de cada usuario
            for (Usuario u : usuarios) {
                compras.add(compdao.contarComprasPorUsuario(u.getIdUsuario()));
            }
            sesion.setAttribute("compras", compras);
            sesion.setAttribute("paginaUsuarios",p);
            sesion.setAttribute("usuarios", usuarios);

            request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
        }
    }       
    
    //Paginación Siguiente Usuario 
    private void siguiente(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
    
        HttpSession sesion = request.getSession();
        String ordenar = (String)sesion.getAttribute("ordenar");
        int p = Integer.parseInt(request.getParameter("pagina"));
        List <Usuario> usuarios = new ArrayList();
        List compras = new ArrayList();
        String texto = (String)sesion.getAttribute("busqueda");
        String tipo = (String)sesion.getAttribute("tipo");
        UsuarioDao udao = new UsuarioDaoImpl();
        CompraDao compdao = new CompraDaoImpl();

        try {
            //Busco los usuarios, por texto, orden. O sin orden ni texto
            if(!texto.equals("N")){
                usuarios = udao.buscarUsuariosPorTexto(texto, p*10, tipo);
            }else if(ordenar.equals("0") ){
                usuarios=udao.buscarUsuarios(p*10,tipo);
            }else{
                usuarios = udao.ordenarUsuarios(ordenar, p*10, tipo);
            }
            //Cuentos las compras de cada usuario
            for (Usuario u : usuarios) {
                compras.add(compdao.contarComprasPorUsuario(u.getIdUsuario()));
            }
            sesion.setAttribute("compras", compras);        
            p+=1;
            sesion.setAttribute("paginaUsuarios",p);
            sesion.setAttribute("usuarios", usuarios);

            //Controlo si hay más usuarios
            if(!texto.equals("N") && udao.buscarUsuariosPorTexto(texto, p*10,tipo).size()==0){
                request.setAttribute("HayUsuarios", "No");
            }else if(udao.buscarUsuarios((p+1)*10,tipo).size()==0){
                request.setAttribute("HayUsuarios", "No");
            }
            request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
        }        
    }    
    
    private void ordenarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        HttpSession sesion = request.getSession();    
        String ordena= request.getParameter("ordenar");//Recibo el orden 
        String tipo = (String)sesion.getAttribute("tipo");//El tipo de usuario 
        List <Usuario> usuarios = new ArrayList();
        List compras = new ArrayList();
        CompraDao compdao = new CompraDaoImpl();
        try {        
            UsuarioDao udao = new UsuarioDaoImpl();
            if(ordena.equals("0")){
                usuarios=udao.buscarUsuarios(0,tipo);
                for (Usuario u : usuarios) {
                    compras.add(compdao.contarComprasPorUsuario(u.getIdUsuario()));
                }
                sesion.setAttribute("compras", compras); 
                sesion.setAttribute("paginaUsuarios",1);
                sesion.setAttribute("usuarios", usuarios);
                sesion.setAttribute("ordenar", "0");
                if(udao.buscarUsuarios(10,tipo).size()==0){
                    request.setAttribute("HayUsuarios", "No");
                }
                request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);            

            }else{
                usuarios = udao.ordenarUsuarios(ordena, 0, tipo);
                for (Usuario u : usuarios) {
                    compras.add(compdao.contarComprasPorUsuario(u.getIdUsuario()));
                }
                sesion.setAttribute("compras", compras); 
                sesion.setAttribute("paginaUsuarios",1);
                sesion.setAttribute("usuarios", usuarios);
                sesion.setAttribute("ordenar", ordena);
                //Controlo si hay más Usuarios 
                if(udao.buscarUsuarios(10,tipo).size()==0){
                    request.setAttribute("HayUsuarios", "No");
                }
                request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
        }         
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        HttpSession sesion = request.getSession();
        UsuarioDao udao = new UsuarioDaoImpl();
        try {
            Usuario usu = new Usuario();
            //Busco el usuario a modificar
            usu = udao.buscarUsuarioPorId((Integer.parseInt(request.getParameter("idUsuario"))));
            sesion.setAttribute("Usuario_a_modificar", usu);
            request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);                    
        } catch (SQLException e) {
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
        }         
}


    private void modificarDatosUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            
            String opcion = request.getParameter("valor");
            HttpSession sesion = request.getSession(); 
            Usuario u = (Usuario)sesion.getAttribute("Usuario_a_modificar");
            UsuarioDao  udao = new UsuarioDaoImpl();
            CompraDao compdao = new CompraDaoImpl();
            try{
                switch (opcion) {
                case "Nombre":
                    String nombre = request.getParameter("nombre");
                    //Controlo si el nombre es igual al ingresado de lo contrario lo actualizo
                    if(u.getNombre().equals(nombre)){
                        request.setAttribute("mensaje", "Datos no modificados: Nombre ingresado igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);  
                    }else if(udao.actualizarNombre(nombre, u.getIdUsuario() )){
                        u.setNombre(nombre);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el nombre, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuarios.jsp").forward(request, response);
                    }
                    break;
                case "Apellido":
                    String apellido = request.getParameter("apellido");
                    if(u.getApellido().equals(apellido)){
                        request.setAttribute("mensaje", "Datos no modificados: Apellido ingresado igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(udao.actualizarApellido(apellido, u.getIdUsuario() )){
                        u.setApellido(apellido);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }

                    break;
                case "Email":
                    String email = request.getParameter("email");
                    if(u.getEmail().equals(email)){
                        request.setAttribute("mensaje", "Datos no modificados: Email ingresado igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(udao.buscarEmail(email)){
                        request.setAttribute("mensaje", "El Email ingresado ya existe");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);                    
                    }else if(udao.actualizarEmail(email, u.getIdUsuario() )){
                        u.setEmail(email);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
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
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);  
                    }else if(udao.actualizarDireccion(localidad, calle, numero, piso, departamento, u.getIdUsuario() )){
                        u.setLocalidad(localidad);
                        u.setCalle(calle);
                        u.setNumero(numero);
                        u.setPiso(piso);
                        u.setDepartamento(departamento);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuarios.jsp").forward(request, response);
                    }
                    break;
                case "Password":
                    String passwordActual = request.getParameter("contraseña1");
                    String passwordNuevo1 = request.getParameter("contraseña2");
                    String passwordNuevo2 = request.getParameter("contraseña3");
                    if(!u.getPassword().equals(passwordActual)){
                       //Contraseña actual incorrecta
                        request.setAttribute("mensaje", "Datos no modificados: Contraseña actual incorrecta");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(!passwordNuevo1.equals(passwordNuevo2)){
                        //Las contraseñas nuevas no coinciden 
                        request.setAttribute("mensaje", "Datos no modificados: Las contraseñas ingresadas no coinciden");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(u.getPassword().equals(passwordNuevo1)){
                        //La contraseña nueva es igual a la ingresada
                        request.setAttribute("mensaje", "Datos no modificados: La contraseña nueva es igual a la actual");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(udao.actualizarPassword(passwordNuevo1, u.getIdUsuario() )){
                        u.setPassword(passwordNuevo1);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }
                    break;                
                case "Telefono":
                    String telefono = request.getParameter("telefono");
                    if(u.getTelefono().equals(telefono) && !u.getTelefono().equals("") ){
                        request.setAttribute("mensaje", "Datos no modificados: El Número de teléfono ingresado es igual a la actual");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(udao.actualizarTelefono(telefono, u.getIdUsuario() )){
                        u.setTelefono(telefono);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else{
                        request.setAttribute("mensaje", "Datos no modificados: Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }
                    break;
                case "Perfil":
                    String perfil = request.getParameter("Perfil");
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    int cant = compdao.contarProductosPendientesUsuario(idUsuario);

                    if(u.getPerfil().equals(perfil)){
                        request.setAttribute("mensaje", "Perfil no modificado: El perfil seleccionado es igual al actual");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                    }else if(cant>0){
                        if(cant==1){
                            request.setAttribute("mensaje", "No se puede modificar el perfil a Administrador. Tiene "+cant+ " compra pendiente de entrega"  );
                        }else{
                            request.setAttribute("mensaje", "No se puede modificar el perfil a Administrador. Tiene "+cant+ " compras pendientes de entrega"  ); 
                        } 
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }else if(udao.actualizarPerfil(perfil, u.getIdUsuario())){
                        u.setPerfil(perfil);
                        sesion.setAttribute("Usuario_a_modificar", u);
                        request.setAttribute("mensaje", "Actualización realizada");
                        request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response); 
                    }
                }
            } catch (SQLException e) {
                request.setAttribute("mensaje", e.getMessage());
                request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
            } 
        }

    private void buscarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            
            HttpSession sesion = request.getSession();
            String texto = request.getParameter("busqueda");
            texto = texto.replaceAll("^0+", "");
            String tipo = (String)sesion.getAttribute("tipo");
            List <Usuario> usuarios = new ArrayList();
            List compras = new ArrayList();
            UsuarioDao udao = new UsuarioDaoImpl();
            CompraDao compdao = new CompraDaoImpl();
            try {
                //Busco usuario por texto y tipo y arrancando desde el primero
                usuarios = udao.buscarUsuariosPorTexto(texto, 0, tipo);
                for (Usuario u : usuarios) {
                    compras.add(compdao.contarComprasPorUsuario(u.getIdUsuario()));
                }
                sesion.setAttribute("compras", compras);  
                sesion.setAttribute("paginaUsuarios",1);
                sesion.setAttribute("usuarios", usuarios);
                sesion.setAttribute("busqueda", texto);

                //Controlo si hay más usuarios

                if(udao.buscarUsuariosPorTexto(texto, 10,tipo).size()==0){
                   request.setAttribute("HayUsuarios", "No");
                }
                request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);  
            } catch (SQLException e) {
                request.setAttribute("mensaje", e.getMessage());
                request.getRequestDispatcher("WEB-INF/páginas/mensajeError.jsp").forward(request, response);
            }            
        }            
    private void eliminarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            
            HttpSession sesion = request.getSession();
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            String nomUsuario = request.getParameter("nomUsuario");
            String pagina = request.getParameter("pagina");
            String tipo = (String)sesion.getAttribute("tipo");
            UsuarioDao udao = new UsuarioDaoImpl();
            CompraDao compdao = new CompraDaoImpl();
            try {
                //Cuento los productos pendientes de entrega, si es mayor a cero informo que no se puede eliminar 
                int cant = compdao.contarProductosPendientesUsuario(idUsuario);
                boolean bandera = false;
                if(cant>0){
                    if(cant==1){
                        request.setAttribute("mensaje", "No se puede eliminar el usuario:  " +nomUsuario+". Tiene "+cant+ " compra pendiente de entrega"  );
                    }else{
                        request.setAttribute("mensaje", "No se puede eliminar el usuario:  " +nomUsuario+". Tiene "+cant+ " compras pendientes de entrega"  ); 
                    }
                    bandera = true; 
                }else if(! udao.eliminarUsuario (idUsuario) ){
                    request.setAttribute("mensaje", "No se pudo eliminar a:  " +nomUsuario+".  Vuelva a intentarlo." );
                    bandera = true;
                }else{
                    request.setAttribute("mensajeok", "Usuario:  " +nomUsuario+" eliminado." );
                }
                //Defino se estoy en la página de un usuario o si eliminé el usuario desde la página de todos los usuarios 
                if (pagina.equals("editarUsuario") && bandera) {
                    request.getRequestDispatcher("WEB-INF/páginas/editarUsuario.jsp").forward(request, response);
                }else{
                    List <Usuario> usuarios = new ArrayList();
                    List compras = new ArrayList();
                    usuarios=udao.buscarUsuarios(0,tipo);

                    for (Usuario u : usuarios) {
                        compras.add(compdao.contarComprasPorUsuario(u.getIdUsuario()));
                    }            
                    int cantUsuarios = udao.contarUsuarios();
                    int cantAdmins = udao.contarAdministradores();
                    sesion.setAttribute("cantUsuarios", cantUsuarios);
                    sesion.setAttribute("cantAdmins", cantAdmins );
                    sesion.setAttribute("usuarios", usuarios);
                    sesion.setAttribute("compras", compras);  
                    sesion.setAttribute("ordenar", "0");
                    sesion.setAttribute("busqueda", "N");
                    request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);        
                }
            } catch (SQLException e) {
                request.setAttribute("mensaje", "Ocurrió un problema al eliminar el usuario:  " +nomUsuario+".  Vuelva a intentarlo." );
                request.getRequestDispatcher("WEB-INF/páginas/usuarios.jsp").forward(request, response);
            }            

        }           
    
    private void actualizarEstadoCompra(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{

         HttpSession sesion = request.getSession(); 
         String estado = request.getParameter("estado");
         int idCompra =  Integer.parseInt(request.getParameter("idCompra"));
         String jsp = request.getParameter("jsp");
         String estadoActual = request.getParameter("estadoActual");
         int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
         int p = Integer.parseInt(request.getParameter("pagina")); 
         CompraDao compdao = new CompraDaoImpl();
         
         try {
             //Controlo si hay más compras para mostrar en la siguiente página 
             try {
                if(compdao.obtenerComprasPorUsuario(idUsuario,(p)*10).size()==0){
                   sesion.setAttribute("HayCompras","no");
                }                 
             } catch (SQLException e) {
                 sesion.setAttribute("HayCompras","no");
             }
             //Si el estado de la compra es igual al ingresado lo informo
            if(estado.equals(estadoActual)){
                if(jsp.equals("ventas")){
                   request.setAttribute("mensaje", "No se pudo modificar. El Estado de la venta "+ idCompra+ " es igual al ingresado"); 
                }else{
                   request.setAttribute("mensaje", "No se pudo modificar. El Estado de la compra "+ idCompra+ " es igual al ingresado"); 
                }
            }else{
               //Actualizo el estado de la compra 
              if(compdao.actualizarEstadoCompra(estado, idCompra)){
                   int pendientes = compdao.contarCompras("Pendiente");
                   int entregados = compdao.contarCompras("Entregado");
                   sesion.setAttribute("pendientes", pendientes);
                   sesion.setAttribute("entregados", entregados);
                   //Si estoy en la página de ventas informo que la la venta, si estoy en el usuario informo de la compra 
                   if(jsp.equals("ventas")){
                        request.setAttribute("mensajeOk", "Se modificó el estado de la venta: "+ idCompra+ " a "+estado);
                     }else{
                       request.setAttribute("mensajeOk", "Se modificó el estado de la compra: "+ idCompra+ " a "+estado);
                     }               
                   //Busco la compra modificada arriba y modifico el estado en la sesión  
                   List<Compra> compras = new ArrayList();
                   compras = (List<Compra>)sesion.getAttribute("compras");
                   for (Compra compra : compras) {
                       if (compra.getIdCompra()==idCompra) {
                          compra.setEstado(estado);
                          break;
                       }

                   }
                   sesion.setAttribute("compras", compras);
               }else{
                   if(jsp.equals("ventas")){
                        request.setAttribute("mensaje", "Lo sentimos, no se pudo modificar el estado de la venta: "+idCompra);
                     }else{
                       request.setAttribute("mensaje", "Lo sentimos, no se pudo modificar el estado de la compra: "+idCompra);
                     }                 
               }  
            }
           if(jsp.equals("ventas")){
               request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);
           }else{ 
               request.getRequestDispatcher("WEB-INF/páginas/misCompras.jsp").forward(request, response);
           }
        } catch (SQLException e) {
            request.setAttribute("mensaje", "No se pudo modificar el estado de la venta, Vuelva a intentarlo.");
            if(jsp.equals("ventas")){
                request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);
            }else{ 
                request.getRequestDispatcher("WEB-INF/páginas/misCompras.jsp").forward(request, response);
            }
        }
    }

    private void paginacionVentas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                HttpSession sesion = request.getSession();
                String direccion = request.getParameter("direccion");
                int p = Integer.parseInt(request.getParameter("pagina"));
                
                String estado = (String)sesion.getAttribute("estado");
                List <Compra> comps = new ArrayList();
                List <Usuario> us = new ArrayList();

                ProductoDao pdao = new ProductoDaoImpl();
                UsuarioDao udao = new UsuarioDaoImpl();
                CompraDao compdao = new CompraDaoImpl();
                DetalleCompraDao dcdao = new DetalleCompraDaoImpl();             
                
                try {
                    //Veo se eligío la siguiete página o la anterior 
                    if(direccion.equals("siguiente")){
                        //Si se selecciono una fecha 
                        if(sesion.getAttribute("fechaDesde")!=null){
                            String fechaDesde = (String)sesion.getAttribute("fechaDesde");
                            String fechaHasta = (String)sesion.getAttribute("fechaHasta");
                            comps = compdao.buscarVentasPorFecha(p*10, fechaDesde, fechaHasta, estado);
                            if(compdao.buscarVentasPorFecha((p+1)*10, fechaDesde, fechaHasta, estado).size()==0){
                                request.setAttribute("HayVentas","no");
                            }
                        //O si se hizo una busqueda por texto
                        }else if(sesion.getAttribute("texto")!=null){
                            String texto = (String)sesion.getAttribute("texto");
                            comps = compdao.buscarVentasPorTexto(p*10, texto, estado);
                            if(compdao.buscarVentasPorTexto((p+1)*10, texto, estado).size()==0){
                                request.setAttribute("HayVentas","no");
                            }                        
                        }else{
                            //Si se seleccionaron todas las ventas 
                            if(estado.equals("Todas")){
                                comps = compdao.obtenerTodasLasCompras(p*10); 
                                if(compdao.obtenerTodasLasCompras((p+1)*10).size()==0){
                                   request.setAttribute("HayVentas","no");
                                }                        
                            }else{
                                //Pendientes o entregadas 
                                comps = compdao.obtenerComprasPendientesoEntregadas(p*10, estado);
                                if(compdao.obtenerComprasPendientesoEntregadas((p+1)*10, estado).size()==0){
                                     request.setAttribute("HayVentas","no");
                                }                          
                            }                            
                        }
                        sesion.setAttribute("paginaVentas", p+1);
                        //Si se seleccionó la página anterior 
                    }else if (direccion.equals("anterior")){
                        if(p-1==1){
                            sesion.setAttribute("paginaVentas", 1);
                            p=0;
                        }else{
                            sesion.setAttribute("paginaVentas", p-1);
                            p=((p-1)*10)-10;
                        }
                        if(sesion.getAttribute("fechaDesde")!=null){
                            String fechaDesde = (String)sesion.getAttribute("fechaDesde");
                            String fechaHasta = (String)sesion.getAttribute("fechaHasta");
                            comps = compdao.buscarVentasPorFecha(p, fechaDesde, fechaHasta, estado);

                        }else if(sesion.getAttribute("texto")!=null){
                            String texto = (String)sesion.getAttribute("texto");
                            comps = compdao.buscarVentasPorTexto(p, texto, estado);                      
                        }else{ 
                            if(estado.equals("Todas")){
                                comps = compdao.obtenerTodasLasCompras(p);                    
                            }else{
                                comps = compdao.obtenerComprasPendientesoEntregadas(p, estado);
                            }                      
                        }    
                    }
                    // se buscan todas las compras con sus detalles 
                    for (Compra compra : comps) {
                       us.add(udao.buscarUsuarioPorId(compra.getIdUsuario()));
                       ArrayList<DetalleCompra> detalle = new ArrayList();
                       detalle = dcdao.obtenerDetalleCompras(compra.getIdCompra());
                       for (DetalleCompra dc : detalle) {
                                   dc.setProducto(pdao.obtenerProducto(dc.getIdProducto()));
                            }
                       compra.setDetalleCompra(detalle);
                    }
                    sesion.setAttribute("usuarios", us);
                    sesion.setAttribute("compras", comps);
                    request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("mensaje", e.getMessage());
                    request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);
                }
    }


    private void buscarVentas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
    

            HttpSession sesion = request.getSession();
            String estado = (String)sesion.getAttribute("estado");
            List <Compra> compras = new ArrayList();
            List <Usuario> us = new ArrayList();            
            String fechaDesde = null;
            String fechaHasta = null;
            String texto = null;
            ProductoDao pdao = new ProductoDaoImpl();
            UsuarioDao udao = new UsuarioDaoImpl();
            CompraDao compdao = new CompraDaoImpl();
            DetalleCompraDao dcdao = new DetalleCompraDaoImpl();                  

            try {
                //Si se selecciono una fecha
                if((String)request.getParameter("fechaDesde")!=null){
                    fechaDesde = (String)request.getParameter("fechaDesde");
                    fechaHasta = (String)request.getParameter("fechaHasta");

                    compras = compdao.buscarVentasPorFecha(0, fechaDesde, fechaHasta, estado);
                    if(compdao.buscarVentasPorFecha(10, fechaDesde, fechaHasta, estado).size()==0){
                        request.setAttribute("HayVentas","no");
                    }

                     sesion.setAttribute("fechaDesde", fechaDesde);
                     sesion.setAttribute("fechaHasta", fechaHasta);
                     sesion.setAttribute("texto", null);
                }else{
                    //Si se hizo una busqueda por texto
                    texto = (String)request.getParameter("busqueda");
                    if(texto.length()<3){
                        request.setAttribute("mensaje", "La busqueda debe tener más de tres caracteres");
                        request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response); 
                        return;
                    }
                    compras = compdao.buscarVentasPorTexto(0, texto, estado);
                    if(compdao.buscarVentasPorTexto(10, texto, estado).size()==0){
                        request.setAttribute("HayVentas","no");
                    }
                    sesion.setAttribute("texto", texto);
                    sesion.setAttribute("fechaDesde", null);
                    sesion.setAttribute("fechaHasta", null);
                }   
                //Busco las compras y sus detalles 
                for (Compra compra : compras) {
                   us.add(udao.buscarUsuarioPorId(compra.getIdUsuario()));
                   ArrayList<DetalleCompra> detalle = new ArrayList();
                   detalle = dcdao.obtenerDetalleCompras(compra.getIdCompra());
                   for (DetalleCompra dc : detalle) {
                               dc.setProducto(pdao.obtenerProducto(dc.getIdProducto()));
                        }
                   compra.setDetalleCompra(detalle);
                }
                sesion.setAttribute("usuarios", us);
                sesion.setAttribute("compras", compras);
                sesion.setAttribute("paginaVentas",1);  
                request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);   
            } catch (SQLException e) {
                compras.clear();
                us.clear();
                sesion.setAttribute("usuarios", us);
                sesion.setAttribute("compras", compras);
                request.setAttribute("mensaje", e.getMessage());
                request.getRequestDispatcher("WEB-INF/páginas/ventas.jsp").forward(request, response);
            } 
    }


    
    
    
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
