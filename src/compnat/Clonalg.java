/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compnat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Renan
 */
public class Clonalg {

    private final int numeroAnticorpos;
    private final int canais;
    private final int geracoes;
    private final int nSelecionados;
    private final int nClones;
    private final RedeAntenas antigeno;

    public Clonalg(int numeroAnticorpos, int canais, int geracoes, int nSelecionados, int nClones, RedeAntenas antigeno) {
        this.numeroAnticorpos = numeroAnticorpos;
        this.canais = canais;
        this.geracoes = geracoes;
        this.nSelecionados = nSelecionados;
        this.nClones = nClones;
        this.antigeno = antigeno;
    }

    public ArrayList<RedeAntenas> Executa() {
        ArrayList<RedeAntenas> anticorpos = new ArrayList<>();
        ArrayList<RedeAntenas> novosAnticorpos = new ArrayList<>();
        ArrayList<RedeAntenas> selecionados = new ArrayList<>();
        ArrayList<RedeAntenas> clones = new ArrayList<>();
        Inicializar(anticorpos);
        CalculaAfinidade(anticorpos);
        for (int i = 0; i < this.geracoes; i++) {
            Seleciona(anticorpos, selecionados);
            Clona(selecionados, clones);
            Hipermuta(clones);
            CalculaAfinidade(clones);
            SelecionaNovos(clones, novosAnticorpos);
            Insere(anticorpos, novosAnticorpos);
            SubstPopulacao(anticorpos);
            novosAnticorpos.clear();
            selecionados.clear();
            clones.clear();
        }
        Collections.sort(anticorpos);
        DistEuclidiana(anticorpos);
        return anticorpos;
    }

    private void Inicializar(ArrayList<RedeAntenas> anticorpos) {
        Random rand = new Random();
        for (int i = 0; i < this.numeroAnticorpos; i++) {
            RedeAntenas redeAntenas = new RedeAntenas();
            for (int j = 0; j < antigeno.getAntenas().size(); j++) {
                Antena antena = new Antena(antigeno.consultaAntenas(j).getPosX(), antigeno.consultaAntenas(j).getPosY());
                int canal;
                canal = rand.nextInt((this.canais - 1) + 1) + 1;
                antena.setCanal(canal);
                redeAntenas.addAntenas(antena);
            }
            anticorpos.add(redeAntenas);
        }
    }

    private void Seleciona(ArrayList<RedeAntenas> anticorpos, ArrayList<RedeAntenas> selecionados) {
        int limite;
        Collections.sort(anticorpos);
        limite = anticorpos.size() - this.nSelecionados;
        for (int i = limite; i < anticorpos.size(); i++) {
            selecionados.add(anticorpos.get(i));
        }
    }

    private void SelecionaNovos(ArrayList<RedeAntenas> clones, ArrayList<RedeAntenas> novosAnticorpos) {
        Collections.sort(clones);
        for (int i = 0; i < this.nSelecionados; i++) {
            novosAnticorpos.add(clones.get(i));
        }
    }

    private void Clona(ArrayList<RedeAntenas> selecionados, ArrayList<RedeAntenas> clones) {
        for (int i = 0; i < selecionados.size(); i++) {
            RedeAntenas rede;
            rede = selecionados.get(i);
            for (int j = 0; j < this.nClones; j++) {
                RedeAntenas redeClone = new RedeAntenas();
                ArrayList<Antena> antenasSelecionandas;
                ArrayList<Antena> antenasClone = new ArrayList<>();
                antenasSelecionandas = rede.getAntenas();
                for (int k = 0; k < antenasSelecionandas.size(); k++) {
                    Antena antenaClone = new Antena(antenasSelecionandas.get(k).getPosX(), antenasSelecionandas.get(k).getPosY());
                    antenaClone.setCanal(rede.consultaAntenas(k).getCanal());
                    antenasClone.add(antenaClone);
                }
                redeClone.setAntenas(antenasClone);
                clones.add(redeClone);
            }
        }
    }

    private void Hipermuta(ArrayList<RedeAntenas> clones) {
        Random rand = new Random();
        for (int i = 0; i < clones.size(); i++) {
            RedeAntenas rede;
            rede = clones.get(i);
            int antenaUm;
            int antenaDois;
            int canalUm;
            int canalDois;
            do {
                antenaUm = rand.nextInt(((rede.getAntenas().size() - 1) - 0) + 1) + 0;
                antenaDois = rand.nextInt(((rede.getAntenas().size() - 1) - 0) + 1) + 0;
            } while (antenaUm == antenaDois);
            canalUm = rede.consultaAntenas(antenaUm).getCanal();
            canalDois = rede.consultaAntenas(antenaDois).getCanal();
            rede.consultaAntenas(antenaUm).setCanal(canalDois);
            rede.consultaAntenas(antenaDois).setCanal(canalUm);
        }
    }

    private void CalculaAfinidade(ArrayList<RedeAntenas> anticorpos) {
        UsoCanais(anticorpos);
        Sobreposição(anticorpos);
        DistEuclidiana(anticorpos);
    }

    private void UsoCanais(ArrayList<RedeAntenas> anticorpos) {
        for (int i = 0; i < anticorpos.size(); i++) {
            RedeAntenas rede;
            rede = anticorpos.get(i);
            for (int j = 0; j < this.canais; j++) {
                float porcentagemCanais;
                int contaCanais = 0;
                for (int k = 0; k < rede.getAntenas().size(); k++) {
                    if (rede.consultaAntenas(k).getCanal() == (j + 1)) {
                        contaCanais = contaCanais + 1;
                    }
                }
                porcentagemCanais = ((float) contaCanais / (float) rede.getAntenas().size()) * 100;
                rede.addPorcentagemCanaisColisoes(porcentagemCanais);
            }
        }
    }

    private void Sobreposição(ArrayList<RedeAntenas> anticorpos) {
        for (int i = 0; i < anticorpos.size(); i++) {
            RedeAntenas rede;
            float maxoverlap = 0;
            float overlap = 0;
            int calcoverlap = this.canais - 3;
            rede = anticorpos.get(i);
            for (int j = 0; j < rede.getAntenas().size(); j++) {
                Antena antenaSelecionada = rede.consultaAntenas(j);
                for (int k = j + 1; k < rede.getAntenas().size(); k++) {
                    Antena antenaAdjacente = rede.consultaAntenas(k);
                    if (antenaSelecionada.getPosX() + 1 == antenaAdjacente.getPosX()) {
                        maxoverlap = maxoverlap + 1;
                    } else if (antenaSelecionada.getPosX() - 1 == antenaAdjacente.getPosX()) {
                        maxoverlap = maxoverlap + 1;
                    } else if (antenaSelecionada.getPosY() + 1 == antenaAdjacente.getPosY()) {
                        maxoverlap = maxoverlap + 1;
                    } else if (antenaSelecionada.getPosY() - 1 == antenaAdjacente.getPosY()) {
                        maxoverlap = maxoverlap + 1;
                    }
                }
            }
            for (int j = 0; j < rede.getAntenas().size(); j++) {
                Antena antenaSelecionada = rede.consultaAntenas(j);
                for (int k = j + 1; k < rede.getAntenas().size(); k++) {
                    Antena antenaAdjacente = rede.consultaAntenas(k);
                    if (antenaSelecionada.getPosX() + 1 == antenaAdjacente.getPosX()) {
                        if ((antenaSelecionada.getCanal() == antenaAdjacente.getCanal()) ) {
                            overlap = overlap + 1;
                        }
                    } else if (antenaSelecionada.getPosX() - 1 == antenaAdjacente.getPosX()) {
                        if ((antenaSelecionada.getCanal() == antenaAdjacente.getCanal())) {
                            overlap = overlap + 1;
                        }
                    } else if (antenaSelecionada.getPosY() + 1 == antenaAdjacente.getPosY()) {
                        if ((antenaSelecionada.getCanal() == antenaAdjacente.getCanal())) {
                            overlap = overlap + 1;
                        }
                    } else if (antenaSelecionada.getPosY() - 1 == antenaAdjacente.getPosY()) {
                        if ((antenaSelecionada.getCanal() == antenaAdjacente.getCanal())) {
                            overlap = overlap + 1;
                        }
                    }
                }
            }
            rede.addPorcentagemCanaisColisoes((overlap / maxoverlap) * 100);
        }
    }

    private void DistEuclidiana(ArrayList<RedeAntenas> anticorpos) {
        for (int i = 0; i < anticorpos.size(); i++) {
            ArrayList<Float> porcentagemAnticorpo;
            ArrayList<Float> porcentagemAntigeno;
            RedeAntenas rede;
            float afinidade = 0;
            rede = anticorpos.get(i);
            porcentagemAnticorpo = rede.getPorcentagemCanaisOverlaps();
            porcentagemAntigeno = this.antigeno.getPorcentagemCanaisOverlaps();
            for (int j = 0; j < rede.getPorcentagemCanaisOverlaps().size(); j++) {
                afinidade = (float) (afinidade + (Math.pow(porcentagemAnticorpo.get(j) - porcentagemAntigeno.get(j), 2)));
            }
            afinidade = (float) Math.sqrt(afinidade);
            rede.setAfinidade(afinidade);
        }
    }

    private void Insere(ArrayList<RedeAntenas> anticorpos, ArrayList<RedeAntenas> clones) {
        for (int i = 0; i < clones.size(); i++) {
            anticorpos.add(clones.get(i));
        }
    }

    private void SubstPopulacao(ArrayList<RedeAntenas> anticorpos) {
        int limite;
        Collections.sort(anticorpos);
        limite = anticorpos.size() - (this.nSelecionados + this.nSelecionados);
        for (int i = limite; i < anticorpos.size(); i++) {
            anticorpos.remove(i);
        }
    }

    public void Exibir(ArrayList<RedeAntenas> anticorpos) {
        for (int i = 0; i < 1; i++) {
            System.out.println("Antigeno " + (i + 1));
            System.out.println("Porcentagens (Primeiros: uso do canal Ultimo: overlapping)");
            System.out.println(anticorpos.get(i).getPorcentagemCanaisOverlaps());
            System.out.println("Canais das antenas: ");
            for (int j = 0; j < anticorpos.get(i).getAntenas().size(); j++) {
                System.out.print(" Ant" + (j + 1) + ": " + anticorpos.get(i).consultaAntenas(j).getCanal() + " ");
            }
            System.out.println("");
            System.out.println("Afinidade: " + anticorpos.get(i).getAfinidade());
            System.out.println("");
        }
    }
}
