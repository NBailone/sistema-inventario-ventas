package modelo;

import java.sql.Timestamp;

public class Producto {
   private int idProducto;
   private String modelo;
   private String pantalla;
   private String memoria_ram;       
   private String memoria_int;       
   private String bateria;       
   private String procesador;
   private double precio;
   private Timestamp fecha; //ver si va datetime 
   private String foto;
   private String descripcion;
   private int activo;
   private Categoria categoria;
   private Marca marca;
   
   // Constructor
    public Producto() {}

    public Producto(int idProducto, String modelo, String pantalla, String memoria_ram, String memoria_int, String bateria, String procesador, double precio, Timestamp fecha, String foto, String descripcion, int activo, Categoria categoria, Marca marca) {
        this.idProducto = idProducto;
        this.modelo = modelo;
        this.pantalla = pantalla;
        this.memoria_ram = memoria_ram;
        this.memoria_int = memoria_int;
        this.bateria = bateria;
        this.procesador = procesador;
        this.precio = precio;
        this.fecha = fecha;
        this.foto = foto;
        this.descripcion = descripcion;
        this.activo = activo;
        this.categoria = categoria;
        this.marca = marca;
        
    }

    public Producto(int idProducto, String marca, String modelo, String pantalla,String procesador, String memoria_ram, String memoria_int, String bateria,  double precio, String descripcion, Categoria categoria) {
        this.idProducto = idProducto;
        this.modelo = modelo;
        this.pantalla = pantalla;
        this.memoria_ram = memoria_ram;
        this.memoria_int = memoria_int;
        this.bateria = bateria;
        this.procesador = procesador;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    
    //Metodos 

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getMemoria_ram() {
        return memoria_ram;
    }

    public void setMemoria_ram(String memoria_ram) {
        this.memoria_ram = memoria_ram;
    }

    public String getMemoria_int() {
        return memoria_int;
    }

    public void setMemoria_int(String memoria_int) {
        this.memoria_int = memoria_int;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
   
}
