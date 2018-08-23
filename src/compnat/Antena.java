/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compnat;


/**
 *
 * @author Renan
 */
public class Antena {
    private int canal;
    private final int posX;
    private final int posY;
    
    public Antena(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * @return the canal
     */
    public int getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(int canal) {
        this.canal = canal;
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

}
