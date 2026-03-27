
package modelo_Dao;

import config.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
import modelo.Marca;
import modelo.Producto;


public class ProductoDaoImpl implements ProductoDao{
    
   @Override
   public List ListarNProductos(int comienzo, int categoria) throws SQLException{
       
       List<Producto> listaNproductos = new ArrayList();
       ResultSet rs = null;
       java.sql.CallableStatement cl = null;
       Connection con = null;
       
        try {
            con =  ConnectionPool.getInstance().getConnection();
            if(categoria==0){
                //Si la categoría es 0 devuelvo todos los productos
                cl = con.prepareCall("{CALL obtenerNProductos(?)}");
                cl.setInt(1, comienzo);
                rs = cl.executeQuery();
            }else{
                //Devuelvo los productos de esa categoria.
                cl = con.prepareCall("{CALL obtenerNProductosporCategoria(?,?)}");
                cl.setInt(1, comienzo);
                cl.setInt(2, categoria);
                rs = cl.executeQuery();
            }

            while (rs.next()) {
                Producto p = new Producto();
                Categoria cat = new Categoria();
                Marca m = new Marca();
                p.setIdProducto(rs.getInt(1));
                p.setModelo(rs.getString(2));
                p.setPantalla(rs.getString(3));
                p.setMemoria_ram(rs.getString(4));
                p.setMemoria_int(rs.getString(5));
                p.setBateria(rs.getString(6));
                p.setProcesador(rs.getString(7));
                p.setPrecio(rs.getDouble(8));           
                p.setFoto(rs.getString(9));
                p.setDescripcion(rs.getString(10));
                cat.setIdCategoria(rs.getInt(11));
                cat.setNom_Categoria(rs.getString(12));
                m.setIdMarca(rs.getInt(13));
                m.setNom_marca(rs.getString(14));
                p.setCategoria(cat);
                p.setMarca(m);
                 
                listaNproductos.add(p);
                
            }
            return listaNproductos;
            
        } catch (SQLException e) {
            if(e.getMessage().equals("No se pudo conectar a la base de datos")){
                throw e;
            }else{
                throw new SQLException("Error al consultar los productos");
            }
        } finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(cl!=null){
                    cl.close();
                }
                if(con!=null){
                   ConnectionPool.getInstance().closeConnection(con);
                }                
            }catch(Exception e){   
            }  
        }   
    }
      
    //Metodo para mostrar detalles de un producto 
   @Override
    public Producto obtenerProducto(int idProducto) throws SQLException{
        
        Producto p = new Producto();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;

            try {

                con =  ConnectionPool.getInstance().getConnection();
                cl = con.prepareCall("{CALL obtenerProducto(?)}");
                cl.setInt(1, idProducto);
                rs = cl.executeQuery();

                while (rs.next()) {
                    Categoria c = new Categoria();
                    Marca m = new Marca();
                    p.setIdProducto(rs.getInt(1));
                    p.setModelo(rs.getString(2));
                    p.setPantalla(rs.getString(3));
                    p.setMemoria_ram(rs.getString(4));
                    p.setMemoria_int(rs.getString(5));
                    p.setBateria(rs.getString(6));
                    p.setProcesador(rs.getString(7));
                    p.setPrecio(rs.getDouble(8));           
                    p.setFoto(rs.getString(9));
                    p.setDescripcion(rs.getString(10));
                    c.setIdCategoria(rs.getInt(11));
                    c.setNom_Categoria(rs.getString(12));
                    m.setIdMarca(rs.getInt(13));
                    m.setNom_marca(rs.getString(14));
                    p.setActivo(rs.getInt(15));
                    p.setCategoria(c);
                    p.setMarca(m);
                }

            } catch (SQLException e) {
                if(e.getMessage().equals("No se pudo conectar a la base de datos")){
                    throw e;
                }else{
                    throw new SQLException("Ocurrío un error al consultar el producto");
                }
            } finally{
                try{
                    if(rs!=null){
                        rs.close();
                    }
                    if(cl!=null){
                        cl.close();
                    }
                    if(con!=null){
                       ConnectionPool.getInstance().closeConnection(con);
                    }                
                }catch(Exception e){   
                }  
            } 
        return p;
    }

    
   @Override
    public boolean actualizarProducto(Producto p) throws SQLException{
        boolean rpta = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarProducto(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cl.setInt(1, p.getIdProducto());
            cl.setInt(2, p.getMarca().getIdMarca());
            cl.setString(3, p.getModelo());
            cl.setString(4, p.getPantalla());
            cl.setString(5, p.getProcesador());
            cl.setString(6, p.getMemoria_ram());
            cl.setString(7, p.getMemoria_int());
            cl.setString(8, p.getBateria());
            cl.setDouble(9, p.getPrecio());
            cl.setString(10,p.getDescripcion() );
            cl.setInt(11, p.getCategoria().getIdCategoria());
            cl.setString(12, p.getFoto());
            int i =cl.executeUpdate();
            
            if (i==1) {
                rpta = true;
            }
            return rpta;      
        } catch (SQLException e) {
            throw new SQLException("No se pudo actualizar el producto");
        } finally{
            try{
                if(cl!=null){
                    cl.close();
                }
                if(con!=null){
                   ConnectionPool.getInstance().closeConnection(con);
                }                
            }catch(Exception ex){    
            }
        }
        
    }

   @Override
    public boolean eliminarProducto(int idProducto) throws SQLException{
        
        boolean rpta = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;        
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL eliminarProducto(?)}");
            cl.setInt(1, idProducto);
            int i =cl.executeUpdate();
            
            if (i==1) {
                rpta = true;
            }
                  
        } catch (SQLException e) {
            throw new SQLException("No se pudo eliminar el producto");
        } finally{
            try{
                if(cl!=null){

                    cl.close();
                }
                if(con!=null){
                   ConnectionPool.getInstance().closeConnection(con);
                }                
            }catch(SQLException ex){    
            }
        }
        return rpta;
    }

    
   @Override
    public boolean agregarProducto(Producto p)throws SQLException{
        boolean rpta = false;
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        java.sql.CallableStatement cl = null;
        Connection con = null;          
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL agregarProducto(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cl.setInt(1, p.getMarca().getIdMarca());
            cl.setString(2, p.getModelo() );
            cl.setString(3, p.getPantalla());
            cl.setString(4, p.getProcesador() );
            cl.setString(5, p.getMemoria_ram());
            cl.setString(6, p.getMemoria_int());
            cl.setString(7, p.getBateria());
            cl.setDouble(8, p.getPrecio());
            cl.setString(9, p.getFoto());
            cl.setTimestamp(10, date);
            cl.setString(11,p.getDescripcion() );
            cl.setInt(12, 1);
            cl.setInt(13, p.getCategoria().getIdCategoria());
            int i =cl.executeUpdate();
            
            if (i==1) {
                rpta = true;
            }
                  
        } catch (SQLException e) {
            throw new SQLException("No se pudo agregar el producto");
        } finally{
            try{
                if(cl!=null){
                    cl.close();
                }
                if(con!=null){
                   ConnectionPool.getInstance().closeConnection(con);
                }                
            }catch(SQLException ex){    
            }
        }
        return rpta;
    }
     
    //Metodo que filtra la busqueda de productos
   @Override
    public List filtrar(int comienzo, String marca, int idCat, String ordenar, double precioDesde, double precioHasta) throws SQLException{
        List productos = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;
        
        try {
             con =  ConnectionPool.getInstance().getConnection();
            
             if (idCat==0) {
                    if (marca.equals("Todas") && ordenar.equals("0")) {
                    cl = con.prepareCall("{CALL filtrarProductosSinCategoriaSinMarcaSinOrdenar(?,?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setDouble(2, precioDesde);
                    cl.setDouble(3, precioHasta);
                    rs=cl.executeQuery();
                }else if (marca.equals("Todas") && ordenar.equals("1") ) {
                    cl = con.prepareCall("{CALL filtrarProductosSinCategoriaSinMarcaMayorMenor(?,?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setDouble(2, precioDesde);
                    cl.setDouble(3, precioHasta);
                    rs=cl.executeQuery();
                }else if(marca.equals("Todas") && ordenar.equals("2")){
                    cl = con.prepareCall("{CALL filtrarProductosSinCategoriaSinMarcaMenorMayor(?,?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setDouble(2, precioDesde);
                    cl.setDouble(3, precioHasta);
                    rs=cl.executeQuery();
                }else{
                        if (ordenar.equals("1")) {
                            cl = con.prepareCall("{CALL filtrarProductosSinCategoriaConMarcaMayorMenor(?,?,?,?)}");
                            cl.setInt(1, comienzo);
                            cl.setString(2, marca);
                            cl.setDouble(3, precioDesde);
                            cl.setDouble(4, precioHasta);
                            rs=cl.executeQuery();
                        }else if(ordenar.equals("2")){
                            cl = con.prepareCall("{CALL filtrarProductosSinCategoriaConMarcaMenorMayor(?,?,?,?)}");
                            cl.setInt(1, comienzo);
                            cl.setString(2, marca);
                            cl.setDouble(3, precioDesde);
                            cl.setDouble(4, precioHasta);
                            rs=cl.executeQuery();
                        }else{
                            cl = con.prepareCall("{CALL filtrarProductosSinCategoriaConMarcaSinOrdenar(?,?,?,?)}");
                            cl.setInt(1, comienzo);
                            cl.setString(2, marca);
                            cl.setDouble(3, precioDesde);
                            cl.setDouble(4, precioHasta);
                            rs=cl.executeQuery();
                     }
                
                }

             }else{
                    if (marca.equals("Todas") && ordenar.equals("0")) {
                    cl = con.prepareCall("{CALL filtrarProductosSinMarcaSinOrden(?,?,?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setInt(2, idCat);
                    cl.setDouble(3, precioDesde);
                    cl.setDouble(4, precioHasta);
                    rs=cl.executeQuery();

                }else if (marca.equals("Todas") && ordenar.equals("1")) {
                    cl = con.prepareCall("{CALL filtrarProductosSinMarcaMayorMenor(?,?,?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setInt(2, idCat);
                    cl.setDouble(3, precioDesde);
                    cl.setDouble(4, precioHasta);
                    rs=cl.executeQuery();

                }else if (marca.equals("Todas") && ordenar.equals("2")) {
                    cl = con.prepareCall("{CALL filtrarProductosSinMarcaMenorMayor(?,?,?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setInt(2, idCat);
                    cl.setDouble(3, precioDesde);
                    cl.setDouble(4, precioHasta);
                    rs=cl.executeQuery();
                }else{
                        if (ordenar.equals("1")) {
                            cl = con.prepareCall("{CALL filtrarProductosConMarcaMayorMenor(?,?,?,?,?)}");
                            cl.setInt(1, comienzo);
                            cl.setInt(2, idCat);
                            cl.setString(3, marca);
                            cl.setDouble(4, precioDesde);
                            cl.setDouble(5, precioHasta);
                            rs=cl.executeQuery();
                    }else if(ordenar.equals("2")){
                            cl = con.prepareCall("{CALL filtrarProductosConMarcaMenorMayor(?,?,?,?,?)}");
                            cl.setInt(1, comienzo);
                            cl.setInt(2, idCat);
                            cl.setString(3, marca);
                            cl.setDouble(4, precioDesde);
                            cl.setDouble(5, precioHasta);
                            rs=cl.executeQuery();
                    }else{
                            cl = con.prepareCall("{CALL filtrarProductosConMarcaSinOrdenar(?,?,?,?,?)}");
                            cl.setInt(1, comienzo);
                            cl.setInt(2, idCat);
                            cl.setString(3, marca);
                            cl.setDouble(4, precioDesde);
                            cl.setDouble(5, precioHasta);
                            rs=cl.executeQuery();
                     }
                }  
            }
 
             while(rs.next()){
                Producto p = new Producto();
                Categoria c = new Categoria();
                Marca m = new Marca();
                p.setIdProducto(rs.getInt(1));
                p.setModelo(rs.getString(2));
                p.setPantalla(rs.getString(3));
                p.setMemoria_ram(rs.getString(4));
                p.setMemoria_int(rs.getString(5));
                p.setBateria(rs.getString(6));
                p.setProcesador(rs.getString(7));
                p.setPrecio(rs.getDouble(8));           
                p.setFoto(rs.getString(9));
                p.setDescripcion(rs.getString(10));
                c.setIdCategoria(rs.getInt(11));
                c.setNom_Categoria(rs.getString(12));
                m.setIdMarca(rs.getInt(13));
                m.setNom_marca(rs.getString(14));
                p.setCategoria(c);
                p.setMarca(m);
                
                productos.add(p);
             }
             return productos;
        } catch (SQLException e) {
                throw new SQLException("Lo sentimos, ocurrio un error al filtrar los productos. Vuelva a intentarlo");
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(cl!=null){
                    cl.close();
                }
                if(con!=null){
                   ConnectionPool.getInstance().closeConnection(con);
                }                
            }catch(Exception e){   
            }              
        }  
        
    }
    
    //Metodo para mostrar detalles de un producto 
   @Override
    public List buscarPorTextoIngresado(String texto, int comienzo) throws SQLException{
        List productos = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarPorTextoIngresado(?,?)}");
            cl.setString(1, texto);
            cl.setInt(2, comienzo);
            rs = cl.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                Categoria c = new Categoria();
                Marca m = new Marca();
                p.setIdProducto(rs.getInt(1));
                p.setModelo(rs.getString(2));
                p.setPantalla(rs.getString(3));
                p.setMemoria_ram(rs.getString(4));
                p.setMemoria_int(rs.getString(5));
                p.setBateria(rs.getString(6));
                p.setProcesador(rs.getString(7));
                p.setPrecio(rs.getDouble(8));           
                p.setFoto(rs.getString(9));
                p.setDescripcion(rs.getString(10));
                c.setIdCategoria(rs.getInt(11));
                c.setNom_Categoria(rs.getString(12));
                m.setIdMarca(rs.getInt(13));
                m.setNom_marca(rs.getString(14));
                p.setCategoria(c);
                p.setMarca(m);
                
                productos.add(p);
            }
            return productos;
        } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos realizar la búsqueda. Vuelva a intentarlo");
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(cl!=null){
                    cl.close();
                }
                if(con!=null){
                   ConnectionPool.getInstance().closeConnection(con);
                }                
            }catch(Exception e){   
            }              
        } 
        
    }
    
    
}
