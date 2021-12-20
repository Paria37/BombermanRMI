/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;

/**
 *
 * @author angel_23
 */
public class Marco extends JFrame{
    Marco(String host,String nombre,int n_max) throws RemoteException, NotBoundException,InterruptedException {
        ClienteBoomberman cliente = new ClienteBoomberman(host);
        if (cliente.solicitarNuevaPartida(n_max, nombre)){
            while(!cliente.estaListaPartida()){
                System.out.println("Esperando");
                System.out.print("\033[H\033[2J");
            }
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
                            Thread.sleep(100,0);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Marco.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (RemoteException ex) {
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
    Marco(String host,String nombre) throws RemoteException, NotBoundException,InterruptedException {
        ClienteBoomberman cliente = new ClienteBoomberman(host);
        if (cliente.solicitarUnirsePartida(nombre)){
            while(!cliente.estaListaPartida())
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
                            Thread.sleep(100,0);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Marco.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (RemoteException ex) {
                            Logger.getLogger(Marco.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                });
            t.start();   
            addKeyListener(cliente);
        }else{
            System.out.println("Error al unirse a la partida\n");
        }   
    } 
}
