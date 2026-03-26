package modelo;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Compra {
    
    private int idCompra;
    private Timestamp fechaCompra;
    private double total;
    private String estado;
    private int idUsuario;
    private ArrayList<DetalleCompra> detalleCompra;

    public Compra() {
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Timestamp getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Timestamp fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public ArrayList<DetalleCompra> getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(ArrayList<DetalleCompra> detalleCompra) {
        this.detalleCompra = detalleCompra;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
