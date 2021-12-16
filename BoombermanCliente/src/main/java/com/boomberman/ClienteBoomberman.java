package com.boomberman;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
/**
 *
 * @author angel_23
 */
public class ClienteBoomberman {
    public static void main(String[] args) {
        if(args.length>1){
            if(args[0].equals("nueva_partida")){
                System.out.println("Ingresa tu nombre\n");
                String nombre ="x";
                System.out.println("Ingresa el numero maximo de jugadores\n");
                int n_max =4;                
                Marco m = new Marco(nombre,n_max);
                m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }else if(args[0].equals("unirse_partida")){
                System.out.println("Ingresa tu nombre\n");
                String nombre ="x";   
                Marco m = new Marco(nombre);
                m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
            }else{
                System.out.println("Ingrese un argumento valido: nueva_partida o unirse_partida\n");
            }
        }else{
            System.out.println("Se requiere un argumento: nueva_partida o unirse_partida\n");
        }
    }
}
class Marco extends JFrame{
    Marco(String nombre,int n_max) {
        BoombermanCiente cliente = new BoombermanCiente();
        if (cliente.solicitarNuevaPartida(n_max, nombre)){
            cliente.PintarMapa();
            setVisible(true);
            setBounds(700, 300, 600, 450);
            Thread t = new Thread(new Runnable() 
            {
                @Override
                public void run() {
                    while(true){                        
                        try {
                            cliente.ActualizarEstadoJuego();
                            Thread.sleep(1,0);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Marco.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                });
            t.start();            
            addKeyListener(cliente);
        }else{
            System.out.println("Error al crear partida\n");
        }   
    }
    Marco(String nombre) {
        BoombermanCiente cliente = new BoombermanCiente();
        if (cliente.solicitarUnirsePartida(nombre)){
            cliente.PintarMapa();
            setVisible(true);
            setBounds(700, 300, 600, 450);                
            addKeyListener(cliente);
        }else{
            System.out.println("Error al unirse a la partida\n");
        }   
    } 
}
class BoombermanCiente implements KeyListener{
    FuncionesCliente funciones;
    InformacionPartida info;
    static int TOPE_SUPERIOR = 1;
    static int TOPE_INFERIOR = 22;
    static int TOPE_DERECHO = 1;
    static int TOPE_IZQUIERDO = 22;
    
    BoombermanCiente() {
        funciones = new FuncionesCliente();
    }
    public boolean solicitarNuevaPartida(int n_jugadores_max,String nombre){
        InformacionPartida info = funciones.solicitarNuevaPartida(n_jugadores_max);
        return (info!=null)?true:false;
    }
    public boolean solicitarUnirsePartida(String nombre){
        InformacionPartida info = funciones.solicitarUnirsePartida(nombre);
        return (info!=null)?true:false;
    }    
    public void PintarMapa(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0,j=1; i < 10; i++,j+=3) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", 0, j));
            System.out.println("|-|");
        }
        for (int i = 0; i < 10; i++) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", i, 0));
            System.out.println("|x|");
        }  
        for (int i = 0,j=1; i < 10; i++,j+=3) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", 10, j));
            System.out.println("|~|");
        }
        for (int i = 0; i <= 10; i++) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", i, 3*10+1));
            System.out.println("|o|");
        }        
    }
    public void ActualizarEstadoJuego(){
        ArrayList<Posicion> nuevas_posiciones_jugadores = funciones.obtenerEstadoJuego();
        int n_jugadores = info.getN_jugadores();
        for (int i = 0; i < n_jugadores; i++) {
            Posicion posicion_anterior = info.getPosiciones_jugadores().get(i);
            Posicion posicion_nueva = nuevas_posiciones_jugadores.get(i);
            actualizarPosicionJugador(posicion_anterior,posicion_nueva);
            info.setPosicionNuevaJugador(posicion_nueva,i);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {        
        if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
            if(info.getPosicion_cliente().getY()-1 == TOPE_SUPERIOR)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());                 
            info.getPosicion_cliente().decY();
            actualizarPosicionCliente(p);
            //System.out.println("Arriba");
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            if(info.getPosicion_cliente().getY()+1 == TOPE_INFERIOR)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());    
            info.getPosicion_cliente().incY();
            actualizarPosicionCliente(p);
            //System.out.println("Abajo");            
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
            if(info.getPosicion_cliente().getX()-1 == TOPE_IZQUIERDO)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());   
            info.getPosicion_cliente().decX();
            info.getPosicion_cliente().decX();
            info.getPosicion_cliente().decX();    
            actualizarPosicionCliente(p);
            //System.out.println("Izquierda");            
            
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            if(info.getPosicion_cliente().getX()+3== TOPE_DERECHO)return;
            Posicion p = new Posicion(info.getPosicion_cliente().getX(),info.getPosicion_cliente().getY());  
            info.getPosicion_cliente().incX();
            info.getPosicion_cliente().incX(); 
            info.getPosicion_cliente().incX();     
            actualizarPosicionCliente(p);
            //System.out.println("Derecha");            
        }            
    }
    private void actualizarPosicionCliente(Posicion posicion_anterior){
        int prev_x = posicion_anterior.getX();
        int prev_y = posicion_anterior.getY();
        System.out.print("\033[38;5;0m");
        System.out.print(String.format("\u001B[%d;%dH",prev_x , prev_y));
        System.out.println("|-|");        
        int x = info.getPosicion_cliente().getX();
        int y = info.getPosicion_cliente().getY();
        funciones.movimientoCliente(info.getIdCliente(), x,y);
        System.out.print("\033[38;5;5m");
        System.out.print(String.format("\u001B[%d;%dH", x, y));
        System.out.println("|-|");             
    }
    private void actualizarPosicionJugador(Posicion posicion_anterior,Posicion posicion_nueva){
        int prev_x = posicion_anterior.getX();
        int prev_y = posicion_anterior.getY();
        System.out.print("\033[38;5;0m");
        System.out.print(String.format("\u001B[%d;%dH",prev_x , prev_y));
        System.out.println("|-|");    
        
        int x = posicion_nueva.getX();
        int y = posicion_nueva.getY();
        System.out.print("\033[38;5;5m");
        System.out.print(String.format("\u001B[%d;%dH", x, y));
        System.out.println("|-|");             
    }    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}