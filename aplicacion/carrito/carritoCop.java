package com.example.aplicacion.carrito;

public class carritoCop {
    private  String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

   private  String nombre;

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    private  String cant;
    private  String valor;
    public carritoCop(){};
    public carritoCop(String id, String nombre, String valor,String cant) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.cant = cant;
    }
}
