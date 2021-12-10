/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boomberman;
import com.boomberman.InformacionPartida;
/**
 *
 * @author angel_23
 */
public interface ICliente {
    InformacionPartida solicitarNuevaPartida(int N);
    boolean solicitarNuevoJugador(String nombre);
    boolean estaListaPartida();
    void movimientoCLiente(short id,short x, short y);
    void ponerBomba(short x, short y);
    InformacionPartida obtenerEstadoJuego();
    boolean eliminacion();
}
