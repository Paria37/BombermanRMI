/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author DiegoRR
 */
public class EstadoJuego implements Serializable{
    private ArrayList<Posicion> posiciones;
    EstadoJuego(ArrayList<Posicion> posiciones){
        this.posiciones = posiciones;
    }
    public ArrayList<Posicion> getPosiciones(){
        return this.posiciones;
    }
}
