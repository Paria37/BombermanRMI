/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
/**
 *
 * @author angel_23
 */
public class Posicion implements Serializable {
    private int x;
    private int y;
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void incX(){
        this.x++;
    }
    public void incY(){
        this.y++;
    }
    public void decX(){
        this.x--;
    }
    public void decY(){
        this.y--;
    }    
    
}
