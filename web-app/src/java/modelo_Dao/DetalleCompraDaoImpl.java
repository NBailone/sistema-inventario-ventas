
package modelo_Dao;

import config.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import modelo.DetalleCompra;

public class DetalleCompraDaoImpl implements DetalleCompraDao {
    
    
    @Override
    public ArrayList obtenerDetalleCompras( int idCompra) throws SQLException{
        ArrayList<DetalleCompra> listaDC = new ArrayList();
        ResultSet rs = null;
        java.sql.CallableStatement cl = null;
        Connection con = null;   
        
        try {
            con =  ConnectionPool.getInstance().getConnection();
            cl = con.prepareCall("{CALL obtenerDetalleCompras(?)}");
            cl.setInt(1, idCompra);
            rs = cl.executeQuery();
                    
            while (rs.next()) {
                
                DetalleCompra dc = new DetalleCompra();
                dc.setIdCompra(rs.getInt(1));
                dc.setIdProducto(rs.getInt(2));
                dc.setCantidad(rs.getInt(3));
                dc.setSubtotal(rs.getDouble(4));
                        
                listaDC.add(dc);

            }
            
        } catch (SQLException e) {
            throw new SQLException("No se pudo obtener el detalle de la compra");
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
            }catch(Exception i){   
            }  
        }
        return listaDC;
    }
    
}
