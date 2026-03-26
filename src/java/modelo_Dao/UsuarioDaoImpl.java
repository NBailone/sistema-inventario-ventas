
package modelo_Dao;

import config.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;



public class UsuarioDaoImpl implements UsuarioDao{

    
    @Override
    public Usuario buscarUsuario(String usu, String pass) throws SQLException{
        Usuario u = new Usuario();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL login(?,?)}");
            cl.setString(1, usu);
            cl.setString(2, pass);
            rs = cl.executeQuery();
            
            if(rs.next()){
                u.setIdUsuario(rs.getInt(1));
                u.setNomUSuario(rs.getString(2));
                u.setNombre(rs.getString(3));
                u.setApellido(rs.getString(4));
                u.setEmail(rs.getString(5));
                u.setPassword(rs.getString(6));
                u.setLocalidad(rs.getString(7));
                u.setCalle(rs.getString(8));
                u.setNumero(rs.getString(9));
                u.setPiso(rs.getString(10));
                u.setDepartamento(rs.getString(11));
                u.setTelefono(rs.getString(12));
                u.setPerfil(rs.getString(13));                
            }
            return u;  
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un problema al buscar el usuario, vuelva a intentarlo");
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
    public boolean buscarNomUsuario(String usu) throws SQLException{
        boolean resp = true;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarNomUsuario(?)}");
            cl.setString(1, usu);
            rs = cl.executeQuery();
               
            resp=rs.next();

        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al buscar el usuario, vuelva a intentarlo");
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
            }catch(SQLException e){   
            }  
        }      
        return resp;
    }
   
    @Override
    public boolean buscarEmail(String email) throws SQLException{
        boolean resp = true;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarEmail(?)}");
            cl.setString(1, email);
            rs = cl.executeQuery();
            resp=rs.next();
               
        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al buscar el usuario, vuelva a intentarlo");
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
       
        return resp;
    }
      
    @Override
    public boolean registrarUsuario(Usuario u) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL registrarUsuario(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cl.setString(1, u.getNombre() );
            cl.setString(2, u.getApellido());
            cl.setString(3, u.getNomUSuario() );
            cl.setString(4, u.getLocalidad() );
            cl.setString(5, u.getCalle());
            cl.setString(6, u.getNumero() );
            cl.setString(7, u.getDepartamento());
            cl.setString(8, u.getPiso());
            cl.setString(9, u.getEmail());
            cl.setString(10,u.getPassword());
            cl.setString(11,u.getTelefono());
            cl.setString(12,u.getPerfil());
            int i =cl.executeUpdate();

            if (i==1) {
                resp = true;
            }
                  
        } catch (SQLException e) {
            throw new SQLException("Lo sentimos, no pudimos registrarlo, vuleva a intentarlo");
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
    public Usuario buscarUsuarioPorId(int id) throws SQLException{
        Usuario u = new Usuario();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarUsuarioPorId(?)}");
            cl.setInt(1, id);
            rs = cl.executeQuery();
            rs.next();
            u.setIdUsuario(rs.getInt(1));
            u.setNomUSuario(rs.getString(2));
            u.setNombre(rs.getString(3));
            u.setApellido(rs.getString(4));
            u.setEmail(rs.getString(5));
            u.setPassword(rs.getString(6));
            u.setLocalidad(rs.getString(7));
            u.setCalle(rs.getString(8));
            u.setNumero(rs.getString(9));
            u.setPiso(rs.getString(10));
            u.setDepartamento(rs.getString(11));
            u.setTelefono(rs.getString(12));
            u.setPerfil(rs.getString(13));

        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al buscar el usuario, vuelva a intentarlo");
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
    
        return u;   
    }
    
    @Override
    public boolean actualizarNombre(String nombre, int idUsuario) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarNombre(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, nombre );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos modificar el nombre, vuelva a intentar");
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
    public boolean actualizarApellido(String apellido, int idUsuario) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarApellido(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, apellido );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos modificar el apellido, vuelva a intentar");                
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
    public boolean actualizarUsuario(String usuario, int idUsuario) throws SQLException{
        boolean resp = false;

        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarNomUsuario(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, usuario );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos modificar el usuario, vuelva a intentar"); 
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
    public boolean actualizarEmail(String email, int idUsuario) throws SQLException{
        boolean resp = false;
       
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarEmail(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, email );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos modificar el Email, vuelva a intentar"); 
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
    public boolean actualizarPassword(String password, int idUsuario) throws SQLException{
        boolean resp = false;
       
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarPassword(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, password );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) { 
                throw new SQLException("Lo sentimos, no pudimos modificar la contraseña, vuelva a intentar"); 
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
    public boolean actualizarTelefono(String telefono, int idUsuario) throws SQLException{
        boolean resp = false;
       
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarTelefono(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, telefono );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) { 
                throw new SQLException("Lo sentimos, no pudimos modificar el Teléfono, vuelva a intentar"); 
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
    public boolean actualizarPerfil(String perfil, int idUsuario) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarPerfil(?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, perfil );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) { 
                throw new SQLException("No se pudo modificar el perfil, vuelva a intentarlo"); 
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
    public boolean actualizarDireccion(String localidad, String calle, String numero, String piso, String departamento, int idUsuario) throws SQLException{
        boolean resp = false;
       
        java.sql.CallableStatement cl = null;
        Connection con = null;       
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL actualizarDireccion(?,?,?,?,?,?)}");
            
            cl.setInt(1, idUsuario);
            cl.setString(2, localidad );
            cl.setString(3, calle );
            cl.setString(4, numero );
            cl.setString(5, piso );
            cl.setString(6, departamento );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }
                  
            } catch (SQLException e) {
                throw new SQLException("Lo sentimos, no pudimos modificar la dirección, vuelva a intentar"); 
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
    public List buscarUsuarios(int comienzo, String tipo) throws SQLException{
        List<Usuario> lista = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;         
        try {
            con =  ConnectionPool.getInstance().getConnection();
            if(tipo.equals("Todos")){
                cl = con.prepareCall("{CALL buscarTodosLosUsuarios(?)}");
                cl.setInt(1, comienzo);
                rs = cl.executeQuery();
            }else{
                cl = con.prepareCall("{CALL buscarUsuarios(?,?)}");
                cl.setInt(1, comienzo);
                cl.setString(2, tipo);
                rs = cl.executeQuery();
            }
            while(rs.next()){
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt(1));
                u.setNomUSuario(rs.getString(2));
                u.setNombre(rs.getString(3));
                u.setApellido(rs.getString(4));
                u.setEmail(rs.getString(5));
                u.setPassword(rs.getString(6));
                u.setLocalidad(rs.getString(7));
                u.setCalle(rs.getString(8));
                u.setNumero(rs.getString(9));
                u.setPiso(rs.getString(10));
                u.setDepartamento(rs.getString(11));
                u.setTelefono(rs.getString(12));
                u.setPerfil(rs.getString(13));
                    
                lista.add(u);     
            }    
        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al buscar los usuarios, vuelva a intentarlo");
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
    public List buscarUsuariosPorTexto(String texto, int comienzo, String tipo) throws SQLException{
        List<Usuario> lista = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null; 
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            if(tipo.equals("Todos")){
                cl = con.prepareCall("{CALL buscarUsuarioPorTextoIngresado(?,?)}");
                cl.setString(1, texto);
                cl.setInt(2, comienzo);
                rs = cl.executeQuery();
            }else{
                cl = con.prepareCall("{CALL buscarUsuarioPorTextoIngresadoyTipo(?,?,?)}");
                cl.setString(1, texto);
                cl.setInt(2, comienzo);
                cl.setString(3, tipo);
                rs = cl.executeQuery();                
            }
            while(rs.next()){
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt(1));
                u.setNomUSuario(rs.getString(2));
                u.setNombre(rs.getString(3));
                u.setApellido(rs.getString(4));
                u.setEmail(rs.getString(5));
                u.setPassword(rs.getString(6));
                u.setLocalidad(rs.getString(7));
                u.setCalle(rs.getString(8));
                u.setNumero(rs.getString(9));
                u.setPiso(rs.getString(10));
                u.setDepartamento(rs.getString(11));
                u.setTelefono(rs.getString(12));
                u.setPerfil(rs.getString(13));
                    
                lista.add(u);      
            }
        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al buscar los usuarios, vuelva a intentarlo");
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
    public List ordenarUsuarios(String orden,int comienzo, String tipo) throws SQLException{
        List<Usuario> lista = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            if( orden.equals("1") ) {
                if(!tipo.equals("Todos")){
                    cl = con.prepareCall("{CALL ordenarPorId(?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setString(2, tipo);
                    rs = cl.executeQuery();                      
                }else{
                    cl = con.prepareCall("{CALL ordenarTodosPorId(?)}");
                    cl.setInt(1, comienzo);
                    rs = cl.executeQuery();                      
                } 
            }else if(orden.equals("2")){
                if(!tipo.equals("Todos")){
                    cl = con.prepareCall("{CALL ordenarPorNombre(?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setString(2, tipo);
                    rs = cl.executeQuery(); 
                }else{
                    cl = con.prepareCall("{CALL ordenarTodosPorNombre(?)}");
                    cl.setInt(1, comienzo);
                    rs = cl.executeQuery();                     
                }
            }else if(orden.equals("3")){
                if(!tipo.equals("Todos")){
                    cl = con.prepareCall("{CALL ordenarPorEmail(?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setString(2, tipo);
                    rs = cl.executeQuery();                     
                }else{
                    cl = con.prepareCall("{CALL ordenarTodosPorEmail(?)}");
                    cl.setInt(1, comienzo);
                    rs = cl.executeQuery();                    
                }
            }else if(orden.equals("4")){
                    cl = con.prepareCall("{CALL ordenarPorPrivilegio(?)}");
                    cl.setInt(1, comienzo);
                    rs = cl.executeQuery();                     

            }else if(orden.equals("5")){
                if(!tipo.equals("Todos")){
                    cl = con.prepareCall("{CALL ordenarPorCompras(?,?)}");
                    cl.setInt(1, comienzo);
                    cl.setString(2, tipo);
                    rs = cl.executeQuery();                     
                }else{
                    cl = con.prepareCall("{CALL ordenarTodosPorCompras(?)}");
                    cl.setInt(1, comienzo);
                    rs = cl.executeQuery();                    
                }
            }   

            while(rs.next()){
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt(1));
                u.setNomUSuario(rs.getString(2));
                u.setNombre(rs.getString(3));
                u.setApellido(rs.getString(4));
                u.setEmail(rs.getString(5));
                u.setPassword(rs.getString(6));
                u.setLocalidad(rs.getString(7));
                u.setCalle(rs.getString(8));
                u.setNumero(rs.getString(9));
                u.setPiso(rs.getString(10));
                u.setDepartamento(rs.getString(11));
                u.setTelefono(rs.getString(12));
                u.setPerfil(rs.getString(13));
                    
                lista.add(u);     
            } 
        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al ordenar los usuarios, vuelva a intentarlo");
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
    public int contarUsuarios() throws SQLException{
        
        int cantUsuarios = 0;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;  
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarUsuarios()}");
            rs = cl.executeQuery();                
            
            while(rs.next()){
                cantUsuarios = rs.getInt(1);                   
            }

        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al contar los usuarios, vuelva a intentarlo");
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
        return cantUsuarios;   
    }
    
    
    @Override
    public int contarAdministradores() throws SQLException{
        int cantAdministradores = 0;
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL contarAdministradores()}");
            rs = cl.executeQuery();                
            
            while(rs.next()){
                cantAdministradores = rs.getInt(1);                   
            }
        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al contar los usuarios, vuelva a intentarlo");
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
        return cantAdministradores;   
    }
    
    
    @Override
    public boolean eliminarUsuario(int idUsuario) throws SQLException{
        boolean resp = false;
        java.sql.CallableStatement cl = null;
        Connection con = null;         
        try {   
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL eliminarUsuario(?)}");
            cl.setInt(1, idUsuario );
            int i =cl.executeUpdate();
            
            if (i==1) {
                resp = true;
            }         
        } catch (SQLException e) {
            throw  new SQLException("Ocurrió un problema al eliminar el usuario, vuelva a intentarlo");
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
    public Usuario recuperarPassword(String usuario_Email) throws SQLException{
        Usuario u = new Usuario();
        u.setNombre("NO");
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL buscarEmailUsuario(?)}");
            cl.setString(1, usuario_Email);
            rs = cl.executeQuery();

            while(rs.next()){
                u.setNombre(rs.getString(1));
                u.setPassword(rs.getString(2));
                u.setEmail(rs.getString(3));
            }
        } catch (SQLException e) {
            throw new SQLException("Ocurrió un problema al intentar recuperar la contraseña, vuelva a intentarlo");
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
        return u;   
    }       

    
}
