/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compnat;

import java.util.*;

/**
 *
 * @author Renan
 */
public class GeneticoBasico {

    private final int numeroSolucoes;
    private final int numeroAntenas;
    private final int cruzamentos;
    private final int mutacoes;
    private final int canais;
    private final int execucoes;
    private final RedeAntenas problema;

    public GeneticoBasico(int numeroSolucoes, int cruzamentos, int mutacoes, 
            int canais, int execucoes,  RedeAntenas problema) {
        this.numeroSolucoes = numeroSolucoes;
        this.numeroAntenas = 8;
        this.cruzamentos = cruzamentos;
        this.mutacoes = mutacoes;
        this.canais = canais;
        this.execucoes = execucoes;
        this.problema = problema;
    }

    public ArrayList<RedeAntenas> Genetico() {
        ArrayList<RedeAntenas> solucoes = new ArrayList<>();
        int t = 0;
        Inicializar(solucoes);
        Calcular(solucoes);
        while (t < this.execucoes) {
            t++;
            Cruzar(solucoes);
            Mutar(solucoes);
            Calcular(solucoes);
            solucoes = Selecionar(solucoes);
        }
        return solucoes;
    }

    private void Cruzar(ArrayList<RedeAntenas> solucoes) {
        Random rand = new Random();
        for (int i = 0; i < this.cruzamentos; i++) {
            RedeAntenas solucaoUmLinha = new RedeAntenas();
            RedeAntenas solucaoDoisLinha = new RedeAntenas();
            int aux;
            int posicaoRedeUm;
            int posicaoRedeDois;
            int posicaoAntena = rand.nextInt(((solucoes.get(0).getAntenas().size() - 1) - 0) + 1) + 0;
            do {
                posicaoRedeUm = rand.nextInt(((solucoes.size() - 1) - 0) + 1) + 0;
                posicaoRedeDois = rand.nextInt(((solucoes.size() - 1) - 0) + 1) + 0;
            } while (posicaoRedeUm == posicaoRedeDois);
            for (int j = 0; j < solucoes.get(0).getAntenas().size(); j++) {
                Antena antenaSolucaoUm = new Antena(problema.consultaAntenas(j).getPosX(), problema.consultaAntenas(j).getPosY());
                Antena antenaSolucaoDois = new Antena(problema.consultaAntenas(j).getPosX(), problema.consultaAntenas(j).getPosY());
                antenaSolucaoUm.setCanal(solucoes.get(posicaoRedeUm).consultaAntenas(j).getCanal());
                antenaSolucaoDois.setCanal(solucoes.get(posicaoRedeDois).consultaAntenas(j).getCanal());
                solucaoUmLinha.addAntenas(antenaSolucaoUm);
                solucaoDoisLinha.addAntenas(antenaSolucaoDois);
            }
            aux = solucaoUmLinha.consultaAntenas(posicaoAntena).getCanal();
            solucaoUmLinha.consultaAntenas(posicaoAntena).setCanal(solucaoDoisLinha.consultaAntenas(posicaoAntena).getCanal());
            solucaoDoisLinha.consultaAntenas(posicaoAntena).setCanal(aux);
            solucoes.add(solucaoUmLinha);
            solucoes.add(solucaoDoisLinha);
        }
    }

    private void Mutar(ArrayList<RedeAntenas> solucoes) {
        Random rand = new Random();
        for (int i = 0; i < this.mutacoes; i++) {
            RedeAntenas solucaoLinha = new RedeAntenas();
            int posicao = rand.nextInt(((solucoes.size() - 1) - 0) + 1) + 0;
            int aux;
            int antenaUm;
            int antenaDois;
            do {
                antenaUm = rand.nextInt(((solucoes.get(0).getAntenas().size() - 1) - 0) + 1) + 0;
                antenaDois = rand.nextInt(((solucoes.get(0).getAntenas().size() - 1) - 0) + 1) + 0;
            } while (antenaUm == antenaDois);
            for (int j = 0; j < solucoes.get(0).getAntenas().size(); j++) {
                Antena antenaSolucaoLinha = new Antena(problema.consultaAntenas(j).getPosX(), problema.consultaAntenas(j).getPosY());
                antenaSolucaoLinha.setCanal(solucoes.get(posicao).consultaAntenas(j).getCanal());
                solucaoLinha.addAntenas(antenaSolucaoLinha);
            }
            aux = solucaoLinha.consultaAntenas(antenaUm).getCanal();
            solucaoLinha.consultaAntenas(antenaUm).setCanal(solucoes.get(posicao).consultaAntenas(antenaDois).getCanal());
            solucaoLinha.consultaAntenas(antenaDois).setCanal(aux);
            solucoes.add(solucaoLinha);
        }

    }

    private ArrayList<RedeAntenas> Selecionar(ArrayList<RedeAntenas> solucoes) {
        ArrayList<RedeAntenas> solucoesRefinadas = new ArrayList<>();
        int j = 0;
        Collections.sort(solucoes);
        solucoesRefinadas.add(solucoes.get(0));
        for (int i = 0; i < solucoes.size(); i++) {
            if (solucoesRefinadas.get(j).getFitness() != solucoes.get(i).getFitness()){
                solucoesRefinadas.add(solucoes.get(i));
                j++;
            }
        }
        return solucoesRefinadas;
    }

    private void Inicializar(ArrayList<RedeAntenas> solucoesAleatorias) {
        Random rand = new Random();
        for (int i = 0; i < this.numeroSolucoes; i++) {
            RedeAntenas redeAntenas = new RedeAntenas();
            for (int j = 0; j < this.numeroAntenas; j++) {
                Antena antena = new Antena(problema.consultaAntenas(j).getPosX(), problema.consultaAntenas(j).getPosY());
                int canal;
                canal = rand.nextInt((this.canais - 1) + 1) + 1;
                antena.setCanal(canal);
                redeAntenas.addAntenas(antena);
            }
            solucoesAleatorias.add(redeAntenas);
        }
    }

    private void Calcular(ArrayList<RedeAntenas> solucoes) {
        for (int i = 0; i < solucoes.size(); i++) {
            int fitness1 = 10;
            int fitness2;
            RedeAntenas redeAntenas = solucoes.get(i);
            for (int j = 0; j < solucoes.get(0).getAntenas().size(); j++) {
                Antena antena = redeAntenas.consultaAntenas(j);
                for (int k = j + 1; k < solucoes.get(0).getAntenas().size(); k++) {
                    Antena antenaAdjacente = redeAntenas.consultaAntenas(k);
                    fitness1 = fitness1 + (VerificarColisoes(j, k, antena, antenaAdjacente));
                }
            }
            fitness2 = UsoCanais(solucoes.get(i));
            redeAntenas.setFitness((fitness1+fitness2)*this.canais);
        }
    }

    private int UsoCanais(RedeAntenas rede){
        int QtdeCanais = 0;
        for (int i = 0; i < this.canais; i++) {
            int contaCanais = 0;
            for (int j = 0; j < rede.getAntenas().size(); j++) {
                if (rede.consultaAntenas(j).getCanal() == (i+1)){
                    contaCanais++;
                }
            }
            if (contaCanais > QtdeCanais){
                QtdeCanais = contaCanais;
            }
        }
        return QtdeCanais;
    }
    
    private int VerificarColisoes(int posAntenaSelecionada, int posAntenaAdjacente, Antena antenaSelectionada, Antena antenaAdjacente) {
        int fitness = 0;
        switch (posAntenaSelecionada) {
            case 0:
                if (posAntenaAdjacente == 5) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness++;
                    }
                } else if (posAntenaAdjacente == 4 || posAntenaAdjacente == 5) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 2;
                    }
                } else if (posAntenaAdjacente == 1){
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 3;
                    }
                }
                break;
            case 1:
                if (posAntenaAdjacente == 4 || posAntenaAdjacente == 6) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness++;
                    }
                }
                if (posAntenaAdjacente == 5) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 2;
                    }
                } else if (posAntenaAdjacente == 2){
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 3;
                    }
                }
                break;
            case 2:
                if (posAntenaAdjacente == 5 || posAntenaAdjacente == 7) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness++;
                    }
                } else if (posAntenaAdjacente == 6) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 2;
                    }
                } else if (posAntenaAdjacente == 3){
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 3;
                    }
                }
                break;
            case 3:
                if (posAntenaAdjacente == 6) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness++;
                    }
                }
                if (posAntenaAdjacente == 7) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 2;
                    }
                }
                break;
            case 4:
                if (posAntenaAdjacente == 5) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 3;
                    }
                }
                break;
            case 5:
                if (posAntenaAdjacente == 6) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 3;
                    }
                }
                break;
            case 6:
                if (posAntenaAdjacente == 7) {
                    if (antenaSelectionada.getCanal() == antenaAdjacente.getCanal()) {
                        fitness = fitness + 3;
                    }
                }
                break;
            default:
                System.out.println("Erro no Calcular!");
                break;
        }
        return fitness;
    }

    public void Mostrar(ArrayList<RedeAntenas> solucoes) {
        for (int i = 0; i < solucoes.size(); i++) {
            System.out.println("Canais da solucao (" + (i + 1) + "): ");
            for (int j = 0; j < solucoes.get(0).getAntenas().size(); j++) {
                System.out.print(" " + solucoes.get(i).consultaAntenas(j).getCanal() + " ");
                if (j == 3) {
                    System.out.println(" ");
                }
            }
            System.out.println(" ");
            System.out.println("Fitness: " + solucoes.get(i).getFitness());
            System.out.println(" ");
        }
    }
}
