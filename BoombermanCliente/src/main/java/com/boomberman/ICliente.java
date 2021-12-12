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
    InformacionPartida solicitarUnirsePartida(String nombre);
    boolean estaListaPartida();
    void movimientoCliente(short id,int x, int y);
    void ponerBomba(short x, short y);
    InformacionPartida obtenerEstadoJuego();
    boolean eliminacion();
}
