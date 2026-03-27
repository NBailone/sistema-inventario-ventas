
package modelo_Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;

public interface CategoriaDao {
    
    public List<Categoria> listarCategorias() throws SQLException;
    public Categoria buscarCategoria(int cat) throws SQLException;
    public ArrayList contarProductos(List<Categoria> cats) throws SQLException;
    public int contarProductosUnaCategoria(int idCat) throws SQLException;
    public boolean actualizarCategoria(String nombre, int idCat) throws SQLException;
    public boolean agregarCategoria(String nombre) throws SQLException;
    public boolean eliminarCategoria(int idcat) throws SQLException;   
}
