
package modelo_Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import modelo.DetalleCompra;


public interface CompraDao {
    public boolean insertarCompra(Compra c, ArrayList<DetalleCompra> d ) throws SQLException;
    public List obtenerComprasPorUsuario( int id, int comienzo) throws SQLException;
    public List obtenerTodasLasCompras(int comienzo) throws SQLException;
    public List obtenerComprasPendientesoEntregadas(int comienzo, String estado) throws SQLException;
    public int contarCompras( String estado) throws SQLException;
    public int contarComprasPorUsuario( int id) throws SQLException;
    public int contarProductosPendientesUsuario(int idUsuario) throws SQLException;
    public boolean actualizarEstadoCompra(String estado, int idCompra) throws SQLException;
    public List buscarVentasPorFecha(int comienzo, String fechaDesde, String fechaHasta, String tipo) throws SQLException;
    public List buscarVentasPorTexto(int comienzo, String texto, String tipo) throws SQLException;
}
