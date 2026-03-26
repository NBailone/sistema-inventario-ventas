package modelo_Dao;

import java.sql.*;
import java.util.ArrayList;
import config.ConnectionPool;
import java.util.List;
import modelo.Compra;
import modelo.DetalleCompra;
import modelo.Usuario;



public class CompraDaoImpl implements CompraDao{
    
    @Override
    public boolean insertarCompra(Compra c, ArrayList<DetalleCompra> d ) throws SQLException{
        boolean rpta = false;
        Connection con = null;  
        java.sql.CallableStatement cl = null;
        java.sql.CallableStatement cl2 = null;
      
        try {
            con =  ConnectionPool.getInstance().getConnection();
             
            //se deshabilita el modo de confirmación automática
            con.setAutoCommit(false); 
             
            cl = con.prepareCall("{CALL registrarCompra(?,?,?,?)}");
            cl.registerOutParameter( 1, Types.INTEGER );
            cl.setInt(2, c.getIdUsuario() );
            cl.setString(3, "Pendiente");
            cl.setDouble(4, c.getTotal());
            int i = cl.executeUpdate();
                          
            int i2 = 0;
            c.setIdCompra(cl.getInt(1));
            cl2 = con.prepareCall("{CALL registrarDetalleCompra(?,?,?,?)}");
            for (DetalleCompra dC : d) {
                cl2.setInt(1,c.getIdCompra() );
                cl2.setInt(2,dC.getIdProducto() );
                cl2.setInt(3, dC.getCantidad() );
                cl2.setDouble(4, dC.getSubtotal() );
                i2 = cl2.executeUpdate();
            }
            //se indica que se deben aplicar los cambios en la base de datos
            con.commit();
            
             if (i2==1) {
                 rpta=true;
                }
        } catch (SQLException e) {
            try{
                if(con!=null){
                    con.rollback(); 
                }
                throw new SQLException("Lo sentimos, no se pudo registrar la compra. Vuleva a intentarlo.");                
            }catch(Exception n){
                throw new SQLException("Lo sentimos, no se pudo registrar la compra. Vuleva a intentarlo.");
            }

        } finally{
            try{
                if(cl2!=null){
                    cl.close();
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
        return rpta;
        
    }
    
    @Override
    public List obtenerComprasPorUsuario( int id, int comienzo) throws SQLException{
        List listaCompras = new ArrayList();

        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 

        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL obtenerComprasPorUsuario(?,?)}");
            cl.setInt(1, id);
            cl.setInt(2, comienzo);
            rs = cl.executeQuery();
            
            while (rs.next()) {
                Compra c = new Compra();
                c.setIdCompra(rs.getInt(1));
                c.setFechaCompra(rs.getTimestamp(2));
                c.setTotal(rs.getDouble(3));
                c.setEstado(rs.getString(4));

                listaCompras.add(c);
            }  
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al buscar las ventas, vuelva a intentar.");
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
        return listaCompras;
    }

    @Override
    public List obtenerTodasLasCompras(int comienzo) throws SQLException{
        List <Compra> listaCompras = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL obtenerTodasLasCompras(?)}");
            cl.setInt(1, comienzo);
            rs = cl.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                Compra c = new Compra();
                c.setIdCompra(rs.getInt(1));
                c.setFechaCompra(rs.getTimestamp(2));
                c.setTotal(rs.getDouble(3));
                c.setEstado(rs.getString(4));
                c.setIdUsuario(rs.getInt(5));

                listaCompras.add(c);      
            }
            
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al buscar las ventas, vuelva a intentar.");
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
        return listaCompras;
    } 
    
    @Override
    public List obtenerComprasPendientesoEntregadas(int comienzo, String estado) throws SQLException{
        List <Compra> listaCompras = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL obtenerComprasPendientesoEntregadas(?,?)}");
            cl.setInt(1, comienzo);
            cl.setString(2, estado);
            rs = cl.executeQuery();
            while (rs.next()) {
                Compra c = new Compra();
                c.setIdCompra(rs.getInt(1));
                c.setFechaCompra(rs.getTimestamp(2));
                c.setTotal(rs.getDouble(3));
                c.setEstado(rs.getString(4));
                c.setIdUsuario(rs.getInt(5));

                listaCompras.add(c);             
            }
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al buscar las ventas, vuelva a intentar.");
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
        return listaCompras;
    }

    @Override
    public int contarCompras( String estado) throws SQLException{
        int compras = 0;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarCompras(?)}");
            cl.setString(1, estado);
            rs = cl.executeQuery();
            while (rs.next()) {
                compras=rs.getInt(1);
            }
            
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al contar las ventas, vuelva a intentar.");
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
        return compras;
    }    
    
    @Override
    public int contarComprasPorUsuario( int id) throws SQLException{
        int compras = 0;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarComprasPorUsuario(?)}");
            cl.setInt(1, id);
            rs = cl.executeQuery();
            while (rs.next()) {
                compras=rs.getInt(1);
            }
            
        } catch (Exception e) {
            throw new SQLException("Ocurrió un error al contar las ventas, vuelva a intentar.");
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
        return compras;
    }


    @Override
    public int contarProductosPendientesUsuario(int idUsuario) throws SQLException{
        int cantProductos = -1;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarProductosPendientesUsuario(?)}");
            cl.setInt(1,idUsuario);
            rs = cl.executeQuery();
            rs.next();
            cantProductos=rs.getInt(1);


        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al contar los productos pendientes de entrega, vuelva a intentar.");
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

        return cantProductos;
        
    }

    @Override
    public boolean actualizarEstadoCompra(String estado, int idCompra) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;          
        try {
            con =  ConnectionPool.getInstance().getConnection();
            
            //se deshabilita el modo de confirmación automática
            con.setAutoCommit(false); 
            
            cl = con.prepareCall("{CALL actualizarEstadoCompra(?,?)}");
            cl.setInt(1, idCompra);
            cl.setString(2, estado );
            int i =cl.executeUpdate();
            
            con.commit();
            
            if (i==1) {
                resp = true;
            }
            
            
            } catch (SQLException e) {
                try{
                    if(con!=null){
                        con.rollback();
                    }
                    throw new SQLException("Ocurrió un error al actualizar el estado de la venta, vuelva a intentar");
                }catch(Exception n){
                    throw new SQLException("Ocurrio un error al deshacer los cambios");
                }                
            } finally{
                try{
                    if(cl!=null){
                        cl.close();
                    }
                    if(con!=null){
                       ConnectionPool.getInstance().closeConnection(con);
                    }                
                }catch(Exception e){   
                }  
            }

        return resp;
    }

    @Override
    public List buscarVentasPorFecha(int comienzo, String fechaDesde, String fechaHasta, String tipo) throws SQLException{
        
        List<Compra> lista = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        try {
            con =  ConnectionPool.getInstance().getConnection();
            if(tipo.equals("Todas")){
                cl = con.prepareCall("{CALL buscarVentasPorFecha(?,?,?)}");
                cl.setInt(1, comienzo);
                cl.setString(2, fechaDesde);
                cl.setString(3, fechaHasta);                
                rs = cl.executeQuery();
            }else{
                cl = con.prepareCall("{CALL buscarVentasPorFechaEntregadasPendientes(?,?,?,?)}");
                cl.setInt(1, comienzo);
                cl.setString(2, fechaDesde);
                cl.setString(3, fechaHasta);
                cl.setString(4, tipo);
                rs = cl.executeQuery();
            }
               while(rs.next()){
                    Compra c = new Compra();
                    c.setIdCompra(rs.getInt(1));
                    c.setFechaCompra(rs.getTimestamp(2));
                    c.setTotal(rs.getDouble(3));
                    c.setEstado(rs.getString(4));
                    c.setIdUsuario(rs.getInt(5));
                    lista.add(c);
               }
            } catch (SQLException e) {
                throw new SQLException("Ocurrió un error al buscar las ventas, vuelva a intentar");
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
        return lista;   
   }

    @Override
    public List buscarVentasPorTexto(int comienzo, String texto, String tipo) throws SQLException{
        
        List<Compra> lista = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            if(tipo.equals("Todas")){
                cl = con.prepareCall("{CALL buscarVentasPorTexto(?,?)}");
                cl.setInt(1, comienzo);
                cl.setString(2, texto);
                rs = cl.executeQuery();
            }else{
                cl = con.prepareCall("{CALL buscarVentasPorTextoEntregadasPendientes(?,?,?)}");
                cl.setInt(1, comienzo);
                cl.setString(2, texto);
                cl.setString(3, tipo);
                rs = cl.executeQuery();
            }
            while(rs.next()){
                Compra c = new Compra();
                c.setIdCompra(rs.getInt(1));
                c.setFechaCompra(rs.getTimestamp(2));
                c.setTotal(rs.getDouble(3));
                c.setEstado(rs.getString(4));
                c.setIdUsuario(rs.getInt(5));
                    
                lista.add(c);
                    
            }
  
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al buscar las ventas, vuelva a intentar");
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
        
    return lista;   
   }       
    
    
}
