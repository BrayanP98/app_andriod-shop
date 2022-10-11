package com.example.aplicacion.productos;

public class spacecraft {




    private String nombre1;
    private String valor1;
    private int imagen1;





    public spacecraft() {}
    public spacecraft(String nombre1, String valor1, int imagen1) {
        this.nombre1 = nombre1;
        this.valor1 = valor1;
        this.imagen1 = imagen1;
    }



    public String getNombre() {
        return nombre1;
    }

    public void setNombre(String nombre) {
        this.nombre1 = nombre;
    }

    public String getValor() {
        return valor1;
    }

    public void setValor(String valor) {
        this.valor1 = valor;
    }

    public int getImagen() {
        return imagen1;
    }

    public void setImagen(int imagen) {
        this.imagen1 = imagen;
    }

    public void setArrayList() {
    }





}
