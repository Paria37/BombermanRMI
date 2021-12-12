/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boomberman;

import java.util.ArrayList;

/**
 *
 * @author angel_23
 */
public class InformacionPartida {
    private short idCliente;
    private Posicion posicion_cliente;
    private ArrayList posiciones_jugadores;
    private ArrayList posiciones_bombas;
    private ArrayList mapa;
    public InformacionPartida(short x,short y,short[][] mapa,short tamanio_mapa) {
        this.posiciones_jugadores = new ArrayList<Posicion>();
        this.posiciones_bombas = new ArrayList<Posicion>();
        this.mapa = new ArrayList<ArrayList>();
        this.posicion_cliente = new Posicion(x,y);   
    }
    public void actualizarPosicionesJugadores(ArrayList posiciones){
        this.posiciones_jugadores = posiciones;
    }
    public void actualizarBombas(ArrayList posiciones){
        this.posiciones_bombas = posiciones;
    }
    public void actualizarPosicionCliente(Posicion posicion){
        this.posicion_cliente = posicion;
    }
    public short getIdCliente() {
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

    public ArrayList getMapa() {
        return mapa;
    }    
}
