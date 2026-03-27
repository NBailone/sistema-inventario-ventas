
package modelo_Dao;

import config.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;

public class CategoriaDaoImpl implements CategoriaDao{
    

    @Override
    public List<Categoria> listarCategorias() throws SQLException{
        List<Categoria> listaCategorias = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL listarCategorias()}");
            rs = cl.executeQuery();
            
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt(1));
                c.setNom_Categoria(rs.getString(2));

                // Agrego el objeto c a la lista categorias.
                listaCategorias.add(c);
            } 
            return  listaCategorias;
              
        } catch (SQLException e) {
            if(e.getMessage().equals("No se pudo conectar a la base de datos")){
                throw e;
            }else{
                throw new SQLException("Error al buscar categorías");
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
    
    @Override
    public Categoria buscarCategoria(int cat) throws SQLException{
        Categoria c = new Categoria();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarCategoria(?)}");
            cl.setInt(1, cat);
            rs = cl.executeQuery();
            
            while (rs.next()) {
                c.setIdCategoria(cat);
                c.setNom_Categoria(rs.getString(2));    
            } 
        } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos mostrar la categoría de productos. Vuelva a intentarlo.");
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
        return c;        
    }
    
    
    @Override
    public ArrayList contarProductos(List<Categoria> cats) throws SQLException{
        ArrayList cantProductos = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;         
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            for (Categoria c : cats) {
                cl = con.prepareCall("{CALL contarProductos(?)}");
                cl.setInt(1,c.getIdCategoria());
                rs = cl.executeQuery();
                
                rs.next();
                cantProductos.add(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new SQLException("No se pudieron contar los productos por categoría");
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
    public int contarProductosUnaCategoria(int idCat) throws SQLException{
        int cantProductos = -1;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;         
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarProductos(?)}");
            cl.setInt(1,idCat);
            rs = cl.executeQuery();
                
            rs.next();
            cantProductos=rs.getInt(1);

        } catch (SQLException e) {
            throw new SQLException("Error al contar los productos de la categoría");
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
    public boolean actualizarCategoria(String nombre, int idCat) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarCategoria(?,?)}");
            cl.setInt(1, idCat);
            cl.setString(2, nombre );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
        } catch (SQLException e) { 
            throw new SQLException("No se pudo actualizar la categoría");
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
    public boolean agregarCategoria(String nombre) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL agregarCategoria(?)}");
            
            cl.setString(1, nombre );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("No se pudo agregar la categoría");
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
    public boolean eliminarCategoria(int idcat) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL eliminarCategoria(?)}");
            
            cl.setInt(1, idcat );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("Error al eliminar la categoría");
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
    
    
}