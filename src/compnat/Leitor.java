/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compnat;

import java.io.*;
import java.util.*;
/**
 *
 * @author Renan
 */
public class Leitor {
	
	private static Leitor singleton = new Leitor();
	private int rows = 2;
	
	private Leitor(){
		
	}
	
	public static Leitor getInstance(){
		return singleton;
	}
	
	public Integer[][] lerDados(String arquivo){
		Integer[][] array;
		List<Integer> dados = new ArrayList<>();
		int i = 0;
		FileReader file = null;
		try {
			file = new FileReader(arquivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(file);
		Scanner scanner = scan.useDelimiter("\\s+");
		while (scanner.hasNext()){
			String dadotxt;
			Integer dado = null;
			dadotxt = scanner.next();
			dado = Integer.valueOf(dadotxt);
//			System.out.print("   " + dado +  "   ");
			if (i % 2 == 1){
//				System.out.print("\n");
			}
			dados.add(dado);
			i++;
		}
//		System.out.println("\n");
		array = listToArray(dados, i);
		return array;
	}
	
	private Integer[][] listToArray(List<Integer> list, int size){
		Integer[][] array = new Integer[size/rows][rows];
		for (int i = 0; i<size/rows; i++){
			for (int j = 0; j<rows; j++){
				array[i][j] = list.get(j+(i*rows));
//				System.out.print("   " + array[i][j] + "   ");
			}
//			System.out.println("\n");
		}
		return array;
	}
}
