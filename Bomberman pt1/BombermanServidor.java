/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DiegoRR
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.sound.sampled.SourceDataLine;

public class BombermanServidor implements Bomberman{

    private ArrayList<Jugador> jugadores;
    private int[][] mapa;
    private int tamanio_mapa;
    private int n_jugadores;
    public BombermanServidor() {
        this.tamanio_mapa = 20;
        jugadores = new ArrayList<>();
        mapa = new int[this.tamanio_mapa][this.tamanio_mapa];
    }

    @Override
    public void nuevaPartida(int n_jugadores) throws RemoteException{
        this.n_jugadores = n_jugadores;
        for(int i=0; i<n_jugadores; i++){
            jugadores.add(new Jugador(0,"nombre",i,new Posicion(i,i)));
        }
        System.out.println("¡¡Partida creada!!");
    }

    @Override
    public InformacionInicial nuevoJugador(String nombre) throws RemoteException{
        boolean flag = true;
        int cont = 0;
        while(flag && cont < jugadores.size()){
            if (jugadores.get(cont).getNombre().equals("nombre")){
                jugadores.get(cont).setNombre(nombre);
                jugadores.get(cont).setEstado(1);
                flag = false;
            }else{
                cont ++;
            }
        }
        if(cont>= jugadores.size()){
            System.out.println("¡¡Nuevo jugador fail...!!");
            return null;
        }
        else{
            switch (cont) {
                case 0:
                    jugadores.get(cont).getPosicion().setX(0);
                    jugadores.get(cont).getPosicion().setY(0);
                    break;
                case 1:
                    jugadores.get(cont).getPosicion().setX(0);
                    jugadores.get(cont).getPosicion().setY(19);
                    break;
                case 2:
                    jugadores.get(cont).getPosicion().setX(19);
                    jugadores.get(cont).getPosicion().setY(0);
                    break;
                case 3:
                    jugadores.get(cont).getPosicion().setX(19);
                    jugadores.get(cont).getPosicion().setY(19);
                    break;
            
                default:
                    break;
            }
            System.out.println(nombre+" se unio");
            return new InformacionInicial(jugadores.get(cont).getPosicion(),this.mapa,cont,this.n_jugadores,this.tamanio_mapa);
        }
     
    }
    @Override
    public boolean partidaLista() throws RemoteException{
        boolean flag = true;
        int cont = 0;

        while(flag && cont < jugadores.size()){
            if(jugadores.get(cont).getEstado() == 0){
                flag = false;
            }
            cont ++;
        }

        return flag;

    }
    @Override
    public void movimiento(int id, Posicion posicion) throws RemoteException{
        //verificar movimientos valido (paredes)
        jugadores.get(id).setPosicion(posicion);
        //verificar explosiones
        //cambiar estado
    }
    @Override
    public EstadoJuego obtenerEstado() throws RemoteException{
        ArrayList<Posicion> posiciones = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            posiciones.add(jugador.getPosicion());
        }       
        return new EstadoJuego(posiciones);
    }
    public void setMapa(int mapa[][]){
        this.mapa = mapa;
    }
    public void setJugadores(ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
    }
    public static void main(String args[]) {
        try {
            BombermanServidor obj = new BombermanServidor();
            Bomberman stub = (Bomberman) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Bomberman", stub);
            System.err.println("BombermanServer listo!");
        } catch (Exception e) {
            System.err.println("BombermanServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}