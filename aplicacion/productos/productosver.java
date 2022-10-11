package com.example.aplicacion.productos;



public class productosver {
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNum_carrito() {
        return num_carrito;
    }

    public void setNum_carrito(String num_carrito) {
        this.num_carrito = num_carrito;
    }

    public  String num_carrito;
    private String cod;
  public String nombre;
    private String valor;
    private int imagen;


    public productosver(String cod) {
        this.cod = cod;
    }


        public productosver() {}
        public productosver(String nombre, String valor, int imagen) {
            this.nombre = nombre;
            this.valor = valor;
            this.imagen = imagen;

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

        public int getImagen() {
            return imagen;
        }

        public void setImagen(int imagen) {
            this.imagen = imagen;
        }

        public void setArrayList() {
        }
    }

