
package modelo_Dao;

import java.sql.SQLException;
import java.util.List;
import modelo.Usuario;

public interface UsuarioDao {
    public Usuario buscarUsuario(String usu, String pass) throws SQLException;
    public boolean buscarNomUsuario(String usu) throws Exception;
    public boolean buscarEmail(String email) throws SQLException;
    public boolean registrarUsuario(Usuario u) throws SQLException;
    public Usuario buscarUsuarioPorId(int id) throws SQLException;
    public boolean actualizarNombre(String nombre, int idUsuario) throws SQLException;
    public boolean actualizarApellido(String apellido, int idUsuario) throws SQLException;
    public boolean actualizarUsuario(String usuario, int idUsuario) throws SQLException;
    public boolean actualizarEmail(String email, int idUsuario) throws SQLException;
    public boolean actualizarPassword(String password, int idUsuario) throws SQLException;
    public boolean actualizarTelefono(String telefono, int idUsuario) throws SQLException;
    public boolean actualizarPerfil(String perfil, int idUsuario) throws SQLException;
    public boolean actualizarDireccion(String localidad, String calle, String numero, String piso, String departamento, int idUsuario) throws SQLException;
    public List buscarUsuarios(int comienzo, String tipo) throws SQLException;
    public List buscarUsuariosPorTexto(String texto, int comienzo, String tipo) throws SQLException;
    public List ordenarUsuarios(String orden,int comienzo, String tipo) throws SQLException;
    public int contarUsuarios() throws SQLException;
    public int contarAdministradores() throws SQLException;
    public boolean eliminarUsuario(int idUsuario) throws SQLException;
    public Usuario recuperarPassword(String usuario_Email) throws SQLException;
    
}
