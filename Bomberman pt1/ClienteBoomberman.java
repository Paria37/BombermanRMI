/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angel_23
 */
public class ClienteBoomberman implements KeyListener{
    FuncionesCliente funciones;
    InformacionPartida info;
    final int TOPE_SUPERIOR;
    int TOPE_INFERIOR;
    int TOPE_DERECHO;
    final int TOPE_IZQUIERDO;
    private Semaphore mutex;
    ClienteBoomberman(String host) throws RemoteException, NotBoundException {
        this.TOPE_IZQUIERDO = 1;
        this.TOPE_SUPERIOR = 1;
        funciones = new FuncionesCliente(host);
        this.mutex = new Semaphore(1);
    }
    public boolean solicitarNuevaPartida(int n_jugadores_max,String nombre) throws RemoteException{
        InformacionInicial info = funciones.solicitarNuevaPartida(nombre,n_jugadores_max);
        this.info = new InformacionPartida(info.getPosicion(),info.getMapa(),info.getId(),info.getN_jugadores(),info.getTamanio_mapa());         
        transformar(this.info.getPosicion_cliente());
        TOPE_INFERIOR = this.info.getTamanio_mapa()+2;
        TOPE_DERECHO = ((this.info.getTamanio_mapa()+1)*3)+1;
        return (info!=null);
    }
    public boolean solicitarUnirsePartida(String nombre) throws RemoteException{
        InformacionInicial info = funciones.solicitarUnirsePartida(nombre);
        this.info = new InformacionPartida(info.getPosicion(),info.getMapa(),info.getId(),info.getN_jugadores(),info.getTamanio_mapa());
        transformar(this.info.getPosicion_cliente());
        TOPE_INFERIOR = this.info.getTamanio_mapa()+2;
        TOPE_DERECHO = ((this.info.getTamanio_mapa()+1)*3)+1;        
        return (info!=null);
    }    
    public void PintarMapa() throws InterruptedException{
        mutex.acquire();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int x = info.getPosicion_cliente().getX();
        int y =  info.getPosicion_cliente().getY();
        
        System.out.print("\033[38;5;5m");
        System.out.print(String.format("\u001B[%d;%dH", 30,1));
        System.out.println("x: "+x+" y: "+y);        
        
        System.out.print("\033[38;5;5m");
        System.out.print(String.format("\u001B[%d;%dH", y, x));
        System.out.println("|o|");
        //Pintar muros superiores
        int tamanio = info.getTamanio_mapa() +2;
        
        for (int i = 0,j=1; i < tamanio; i++,j+=3) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", 1, j));
            System.out.println("|-|");
        }
        for (int i = 1; i <= tamanio; i++) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", i, 1));
            System.out.println("|x|");
        }  
        for (int i = 0,j=1; i < tamanio; i++,j+=3) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", tamanio, j));
            System.out.println("|~|");
        }
        for (int i = 1; i <= tamanio; i++) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", i, (3*tamanio)-2));
            System.out.println("|o|");
        } 
        mutex.release();      
    }
    public void ActualizarEstadoJuego() throws InterruptedException, RemoteException{
        ArrayList<Posicion> nuevas_posiciones_jugadores = funciones.obtenerEstadoJuego().getPosiciones();
        int n_jugadores = info.getN_jugadores();
        for (int i = 0; i < n_jugadores; i++) {
            Posicion posicion_anterior = info.getPosiciones_jugadores().get(i);
            Posicion posicion_nueva = nuevas_posiciones_jugadores.get(i);
            transformar(posicion_nueva);
            actualizarPosicionJugador(posicion_anterior,posicion_nueva,1+i);
            info.setPosicionNuevaJugador(posicion_nueva,i);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {        
        if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
            if(info.getPosicion_cliente().getY()-1 == TOPE_SUPERIOR)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());                 
            info.getPosicion_cliente().decY();
            info.getPosicion_cliente2().decY();
            try {
                actualizarPosicionJugador(p,info.getPosicion_cliente(),5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                funciones.movimientoCliente(info.getIdCliente(), info.getPosicion_cliente2());
                //actualizarPosicionCliente(p);
                //System.out.println("Derecha");            
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }            
            //System.out.println("Arriba");
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            if(info.getPosicion_cliente().getY()+1 == TOPE_INFERIOR)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());    
            info.getPosicion_cliente().incY();
            info.getPosicion_cliente2().incY();
            try {
                actualizarPosicionJugador(p,info.getPosicion_cliente(),5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                funciones.movimientoCliente(info.getIdCliente(), info.getPosicion_cliente2());
                //actualizarPosicionCliente(p);
                //System.out.println("Derecha");            
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }            
            //System.out.println("Abajo");            
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
            if(info.getPosicion_cliente().getX()-3 == TOPE_IZQUIERDO)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());   
            info.getPosicion_cliente().decX();
            info.getPosicion_cliente().decX();
            info.getPosicion_cliente().decX();  
            info.getPosicion_cliente2().decX();
            try {
                actualizarPosicionJugador(p,info.getPosicion_cliente(),5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                funciones.movimientoCliente(info.getIdCliente(), info.getPosicion_cliente2());
                //actualizarPosicionCliente(p);
                //System.out.println("Derecha");            
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }            
            //System.out.println("Izquierda");            
            
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            if(info.getPosicion_cliente().getX()+3== TOPE_DERECHO)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());  
            info.getPosicion_cliente().incX();
            info.getPosicion_cliente().incX(); 
            info.getPosicion_cliente().incX();    
            info.getPosicion_cliente2().incX();
            try {
                actualizarPosicionJugador(p,info.getPosicion_cliente(),5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                funciones.movimientoCliente(info.getIdCliente(), info.getPosicion_cliente2());
                //actualizarPosicionCliente(p);
                //System.out.println("Derecha");            
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteBoomberman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }            
    }
    private void transformar(Posicion posicion){
        int x = ((posicion.getX()+1)*3)+1 ;
        posicion.setX(x);
        posicion.incY();
        posicion.incY();
    }
    private void actualizarPosicionJugador(Posicion posicion_anterior,Posicion posicion_nueva,int color) throws InterruptedException{
        int prev_x = posicion_anterior.getX();
        int prev_y = posicion_anterior.getY();
        int x = posicion_nueva.getX();
        int y = posicion_nueva.getY();     
        mutex.acquire();
        System.out.print("\033[38;5;0m");
        System.out.print(String.format("\u001B[%d;%dH",prev_y,prev_x));
        System.out.println("|-|");    
        System.out.print("\033[38;5;"+color+"m");
        System.out.print(String.format("\u001B[%d;%dH", y,x));
        System.out.println("|-|");     
        mutex.release();
    }   
    public boolean estaListaPartida() throws RemoteException{
        return funciones.estaListaPartida();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}