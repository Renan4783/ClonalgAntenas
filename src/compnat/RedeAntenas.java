/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compnat;

import java.util.ArrayList;

/**
 *
 * @author Renan
 */
public class RedeAntenas implements Comparable<RedeAntenas> {

    private ArrayList<Antena> antenas = new ArrayList<>();
    private ArrayList<Float> porcentagemCanaisOverlaps = new ArrayList<>();
    private int fitness;
    private float afinidade;

    /**
     * @return the antenas
     */
    public ArrayList<Antena> getAntenas() {
        return antenas;
    }

    /**
     * @param antenas the antenas to set
     */
    public void setAntenas(ArrayList<Antena> antenas) {
        this.antenas = antenas;
    }

    public void addAntenas(Antena antena) {
        this.antenas.add(antena);
    }

    public Antena consultaAntenas(int antena) {
        return this.antenas.get(antena);
    }

    public void removeAntenas(int antena) {
        this.antenas.remove(antena);
    }

    /**
     * @return the fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(RedeAntenas o) {
        if ((this.fitness < o.getFitness()) || (this.afinidade < o.getAfinidade())) {
            return -1;
        }
        if ((this.fitness > o.getFitness()) || (this.afinidade > o.getAfinidade())) {
            return 1;
        }
        return 0;
    }

    /**
     * @return the porcentagemCanaisOverlaps
     */
    public ArrayList<Float> getPorcentagemCanaisOverlaps() {
        return porcentagemCanaisOverlaps;
    }

    /**
     * @param valor the porcentagemCanaisOverlaps to set
     */
    public void addPorcentagemCanaisColisoes(float valor) {
        this.getPorcentagemCanaisOverlaps().add(valor);
    }

    /**
     * @return the afinidade
     */
    public float getAfinidade() {
        return afinidade;
    }

    /**
     * @param afinidade the afinidade to set
     */
    public void setAfinidade(float afinidade) {
        this.afinidade = afinidade;
    }

    /**
     * @param porcentagemCanaisOverlaps the porcentagemCanaisOverlaps to set
     */
    public void setPorcentagemCanaisOverlaps(ArrayList<Float> porcentagemCanaisOverlaps) {
        this.porcentagemCanaisOverlaps = porcentagemCanaisOverlaps;
    }

}
