
package modelo_Dao;



import config.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Marca;


public class MarcaDaoImpl implements MarcaDao{
    
    @Override
    public List listarTodasLasMarcas() throws SQLException{
        List<Marca> listaMarcas = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            
            cl = con.prepareCall("{CALL obtenerTodasLasMarcas()}");
            rs = cl.executeQuery();
            
            while (rs.next()) {
                Marca m = new Marca();
                m.setIdMarca(rs.getInt(1));
                m.setNom_marca(rs.getString(2));
                listaMarcas.add(m);
            } 
            return listaMarcas;
        } catch (SQLException e) {
            if(e.getMessage().equals("No se pudo conectar a la base de datos")){
                throw e;
            }else{
                throw new SQLException("Ocurrió un error al consultar las marcas");
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
    public List listarMarcasPorCategoria(int cat) throws SQLException{
        List<Marca> listaMarcas = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL obtenerMarcasPorCategoria(?)}");
            cl.setInt(1, cat);
            rs = cl.executeQuery();
            
            while (rs.next()) {
                Marca m = new Marca();
                m.setIdMarca(rs.getInt(1));
                m.setNom_marca(rs.getString(2));
                listaMarcas.add(m);
            } 
        } catch (SQLException e) {
            if(e.getMessage().equals("No se pudo conectar a la base de datos")){
                throw e;
            }else{
                throw new SQLException("Error al consultar las marcas");
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
        return listaMarcas;
        
    }
    
     
    @Override
    public ArrayList contarProductosPorMarca(List<Marca> marcas) throws SQLException{
        ArrayList cantProductos = new ArrayList(); 
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;        
        try {
            con =  ConnectionPool.getInstance().getConnection();

            for (Marca m : marcas) {
                cl = con.prepareCall("{CALL contarProductosPorMarca(?)}");
                cl.setInt(1,m.getIdMarca() );
                rs = cl.executeQuery();
                
                rs.next();
                cantProductos.add(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new SQLException("Error al contar los productos por marca");
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
    public Marca buscarMarca(int marca) throws SQLException{
        Marca m = new Marca();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;  
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarMarca(?)}");
            cl.setInt(1, marca);
            rs = cl.executeQuery();
            
            while (rs.next()) {
                m.setIdMarca(marca);
                m.setNom_marca(rs.getString(2));   
            } 
        } catch (SQLException e) {
            throw new SQLException("Ocurrio un error al buscar la marca");
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
        return m;
        
    }    
    
    @Override
    public boolean actualizarMarca(String nombre, int idM) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;         
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarMarca(?,?)}");
            
            cl.setInt(1, idM);
            cl.setString(2, nombre );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) { 
                throw new SQLException("No se pudo actualizar la marca");
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
    public boolean agregarMarca(String nombre) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;        
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL agregarMarca(?)}");
            
            cl.setString(1, nombre );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("No se pudo agregar la marca");
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
    public int contarProductosUnaMarca(int idM) throws SQLException{
        int cantProductos = -1;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;  
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarProductosUnaMarca(?)}");
            cl.setInt(1,idM);
            rs = cl.executeQuery();
                
            rs.next();
            cantProductos=rs.getInt(1);
            return cantProductos;
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un error al contar los productos de la marca");
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
    public boolean eliminarMarca(int idM){
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;  
        try { 
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL eliminarMarca(?)}");
            
            cl.setInt(1, idM );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (Exception e) { 
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
