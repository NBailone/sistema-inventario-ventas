
package modelo_Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DetalleCompraDao {
    public ArrayList obtenerDetalleCompras( int idCompra) throws SQLException;
}
