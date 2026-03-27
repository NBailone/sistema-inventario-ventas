
package modelo_Dao;

import java.sql.SQLException;
import java.util.List;
import modelo.Producto;

public interface ProductoDao {
    public List ListarNProductos(int comienzo, int categoria) throws SQLException;
    public Producto obtenerProducto(int idProducto) throws SQLException;
    public boolean actualizarProducto(Producto p) throws SQLException;
    public boolean eliminarProducto(int idProducto) throws SQLException;
    public boolean agregarProducto(Producto p)throws SQLException;
    public List filtrar(int comienzo, String marca, int idCat, String ordenar, double precioDesde, double precioHasta) throws SQLException;
    public List buscarPorTextoIngresado(String texto, int comienzo) throws SQLException;
}
