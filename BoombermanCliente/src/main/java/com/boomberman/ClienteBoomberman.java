
package com.boomberman;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
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
            setVisible(true);
            setBounds(700, 300, 600, 450);                
            addKeyListener(cliente);
        }else{
            System.out.println("Error al crear partida\n");
        }   
    }
    Marco(String nombre) {
        BoombermanCiente cliente = new BoombermanCiente();
        if (cliente.solicitarUnirsePartida(nombre)){
            setVisible(true);
            setBounds(700, 300, 600, 450);                
            addKeyListener(cliente);
        }else{
            System.out.println("Error al crear partida\n");
        }   
    } 
}
class BoombermanCiente implements KeyListener{
    FuncionesCliente funciones;
    InformacionPartida info;
    static int TOPE_SUPERIOR = 1;
    static int TOPE_INFERIOR = 17;
    static int TOPE_DERECHO = 1;
    static int TOPE_IZQUIERDO = 19;
    
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
        ArrayList mapa = info.getMapa();
        for (Object fila : mapa) {
            
        }
    }
    public void ActualizarEstadoJuego(){
        InformacionPartida estadoJuego = funciones.obtenerEstadoJuego();
        ArrayList<Posicion> posiciones_jugadores = estadoJuego.getPosiciones_jugadores();
        for (Posicion posicion : posiciones_jugadores) {
            System.out.print("\033[38;5;5m");
            System.out.print(String.format("\u001B[%d;%dH", posicion.getX(),posicion.getY()));
            System.out.println("|o|");            
            //System.out.print("\033[H\033[2J");
            //System.out.flush();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {        
        if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
            if(info.getPosicion_cliente().getY()-1 == TOPE_SUPERIOR)return;
            Posicion p = info.getPosicion_cliente();                
            info.getPosicion_cliente().decY();
            actualizarPosicionCliente(p);
            //System.out.println("Arriba");
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            if(info.getPosicion_cliente().getY()+1 == TOPE_INFERIOR)return;
            Posicion p = info.getPosicion_cliente();   
            info.getPosicion_cliente().incY();
            actualizarPosicionCliente(p);
            //System.out.println("Abajo");            
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
            if(info.getPosicion_cliente().getX()-1 == TOPE_IZQUIERDO)return;
            Posicion p = info.getPosicion_cliente();  
            info.getPosicion_cliente().decX();  
            actualizarPosicionCliente(p);
            //System.out.println("Izquierda");            
            
        }else if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            if(info.getPosicion_cliente().getX()+1 == TOPE_DERECHO)return;
            Posicion p = info.getPosicion_cliente();  
            info.getPosicion_cliente().incX();    
            actualizarPosicionCliente(p);
            //System.out.println("Derecha");            
        }              
    }
    private void actualizarPosicionCliente(Posicion posicion_anterior){
        int prev_x = posicion_anterior.getX();
        int prev_y = posicion_anterior.getY();
        System.out.print("\033[38;5;5m");
        System.out.print(String.format("\u001B[%d;%dH",prev_x , prev_y));
        System.out.println("|-|");        
        int x = info.getPosicion_cliente().getX();
        int y = info.getPosicion_cliente().getY();
        funciones.movimientoCliente(info.getIdCliente(), x,y);
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