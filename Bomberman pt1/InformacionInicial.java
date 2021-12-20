/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;

/**
 *
 * @author angel_23
 */
public class InformacionInicial implements Serializable {

    private Posicion posicion;
    private int mapa[][];
    private int tamanio_mapa;
    private int id;
    private int n_jugadores;
    InformacionInicial(Posicion posicion,int mapa[][],int id, int n_jugadores, int tamanio_mapa){
        this.posicion = posicion;
        this.mapa = mapa;
        this.id = id;
        this.n_jugadores = n_jugadores;
        this.tamanio_mapa = tamanio_mapa;
    }
    public Posicion getPosicion() {
        return posicion;
    }
    public int[][] getMapa() {
        return mapa;
    }    
    public int getId() {
        return id;
    }

    public int getN_jugadores() {
        return n_jugadores;
    }    
    public int getTamanio_mapa() {
        return tamanio_mapa;
    }    
}
