
package com.boomberman;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author angel_23
 */
public class ClienteBoomberman {
    //InformacionPartida
    static FuncionesCliente funciones;
    static InformacionPartida info;
    public static void main(String[] args) {
        if(args.length>1){
            funciones = new FuncionesCliente();
            if(args[0].equals("nueva_partida")){
                System.out.println("Ingresa el numero de jugadores\n");
                int N =0;
                info = funciones.solicitarNuevaPartida(N);
                System.out.println("Ingresa tu nombre");
                String nombre;
                //info = nuevoJugador(nombre)
                //while(!partidaLista())
                jugar();
                
                
            
            }else if(args[0].equals("unirse_partida")){
            
            }else{
                System.out.println("Elija una opcion correcta : nueva_partida o unirse_partida");
            }
        }else{
            System.out.println("Se requiere un argumento: nueva_partida o unirse_partida");
        }
    }
    private static void jugar(){
        Thread estado_juego = new Thread(new Runnable() // Thread handles messages sent by client that just connected
        {
            @Override
            public void run() {
                while(!funciones.eliminacion()){
                    
                }
            }
        });
        estado_juego.start();
        Thread captura_movimientos = new Thread(new Runnable() // Thread handles messages sent by client that just connected
        {
            @Override
            public void run() {
                while(!funciones.eliminacion()){
                    
                }
            }
        });
        captura_movimientos.start();    
        return;
    }
    private static void pintarMapa(){
            
        
        for (int i = 0; i < 10; i++) {
            
            System.out.println("\033[38;5;"+i+"m");
            System.out.println("\033[0;"+i+"H");
        
        }

    }

}
