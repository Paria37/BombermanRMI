/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
/**
 *
 * @author angel_23
 */
public class FuncionesCliente {
    private Bomberman stub;
    public FuncionesCliente(String host) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host);
        stub = (Bomberman) registry.lookup("Bomberman");
    }
    
    public InformacionInicial solicitarNuevaPartida(String nombre,int N) throws RemoteException {
        stub.nuevaPartida(N);
        
        InformacionInicial info = stub.nuevoJugador(nombre);
        System.out.println("Partida creada");
        return info;
    }
    public InformacionInicial solicitarUnirsePartida(String nombre) throws RemoteException {
        return stub.nuevoJugador(nombre);
    }    
    public boolean estaListaPartida() throws RemoteException {
        return stub.partidaLista();
    }
    public void movimientoCliente(int id, Posicion posicion) throws RemoteException {
        stub.movimiento(id, posicion);
    }
    public EstadoJuego obtenerEstadoJuego() throws RemoteException { 
        return stub.obtenerEstado();
    }    
    public void ponerBomba(short x, short y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean eliminacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
