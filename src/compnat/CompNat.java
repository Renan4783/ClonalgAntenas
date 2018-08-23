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
public class CompNat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Integer[][] matriz;
        String endereco = "C:/Users/Renan/Google Drive/Comp. Natural/CompNat/Antenas.txt";
        Leitor leitor = Leitor.getInstance();
        ArrayList<RedeAntenas> solucao;
        RedeAntenas problema = new RedeAntenas();
        matriz = leitor.lerDados(endereco);
        for (Integer[] matriz1 : matriz) {
            Antena antenaProblema = new Antena(matriz1[0], matriz1[1]);
            problema.addAntenas(antenaProblema);
        }
        problema.addPorcentagemCanaisColisoes((float) 25);
        problema.addPorcentagemCanaisColisoes((float) 25);
        problema.addPorcentagemCanaisColisoes((float) 25);
        problema.addPorcentagemCanaisColisoes((float) 25);
        problema.addPorcentagemCanaisColisoes(0);
        Clonalg clone = new Clonalg(25, 4, 1000, 5, 5, problema);
        solucao = clone.Executa();
        clone.Exibir(solucao);
        //Solucoes, cruzamentos, mutacoes, % de corte, canais, execucoes, esboco do problema
        //GeneticoBasico evolution = new GeneticoBasico(2, 2, 1, 3, 40, problema);
        //solucao = evolution.Genetico();
        //evolution.Mostrar(solucao);
    }
}
