/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author angel_23
 */
public class InformacionPartida {
    private int idCliente;
    private int tamanio_mapa;
    private int n_jugadores;
    private Posicion posicion_cliente;
    private Posicion posicion_cliente2;
    private ArrayList<Posicion> posiciones_jugadores;
    private ArrayList<Posicion> posiciones_bombas;
    private int mapa [][];
    public InformacionPartida(Posicion posicion,int [][] mapa,int id,int n_jugadores,int tamanio_mapa) {
        this.posiciones_jugadores = new ArrayList<>();
        this.posiciones_bombas = new ArrayList<>();
        this.posicion_cliente = posicion;  
        this.posicion_cliente2 = new Posicion(posicion.getX(),posicion.getY());
        this.idCliente = id;
        this.n_jugadores = n_jugadores;
        this.mapa = mapa;
        this.tamanio_mapa = tamanio_mapa;
        init_posiciones();
    }
    public int[][] getMapa() {
        return mapa;
    }
    public void actualizarPosicionCliente(Posicion posicion){
        this.posicion_cliente = posicion;
    }
    public int getIdCliente() {
        return idCliente;
    }

    public Posicion getPosicion_cliente() {
        return posicion_cliente;
    }

    public ArrayList<Posicion> getPosiciones_jugadores() {
        return posiciones_jugadores;
    }

    public ArrayList<Posicion> getPosiciones_bombas() {
        return posiciones_bombas;
    }  
    
    void setPosicionNuevaJugador(Posicion posicion_anterior,int i) {
        posiciones_jugadores.set(i, posicion_anterior);
    }
    public int getN_jugadores() {
        return n_jugadores;
    }

    public void setN_jugadores(int n_jugadores) {
        this.n_jugadores = n_jugadores;
    }   
    public int getTamanio_mapa() {
        return tamanio_mapa;
    }    
    public Posicion getPosicion_cliente2() {
        return posicion_cliente2;
    }    

    private void init_posiciones() {
        for (int i = 0; i < this.n_jugadores; i++) {
            Posicion p = new Posicion(1,i+3);
            this.posiciones_jugadores.add(p);
        }        
    }
}
