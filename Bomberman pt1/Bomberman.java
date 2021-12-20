/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DiegoRR
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bomberman extends Remote{

    public void nuevaPartida(int N) throws RemoteException;

    public InformacionInicial nuevoJugador(String nombre) throws RemoteException;

    public boolean partidaLista() throws RemoteException;

    public void movimiento(int id, Posicion posicion) throws RemoteException;

    public EstadoJuego obtenerEstado() throws RemoteException;

}