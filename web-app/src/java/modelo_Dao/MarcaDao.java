
package modelo_Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Marca;


public interface MarcaDao {
    public List listarTodasLasMarcas() throws SQLException;
    public List listarMarcasPorCategoria(int cat) throws SQLException;
    public ArrayList contarProductosPorMarca(List<Marca> marcas) throws SQLException;
    public Marca buscarMarca(int marca) throws SQLException;
    public boolean actualizarMarca(String nombre, int idM) throws SQLException;
    public boolean agregarMarca(String nombre) throws SQLException;
    public int contarProductosUnaMarca(int idM) throws SQLException;
    public boolean eliminarMarca(int idM)throws SQLException;
    
}
