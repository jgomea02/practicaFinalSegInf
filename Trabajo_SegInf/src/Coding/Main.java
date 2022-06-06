package Coding;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String mnsj_claro = "Me llamo José Manuel Gómez Álvarez, estudio Ingeniería Informática, estoy en 3er año y mi asignatura favorita es Seguridad Informática!";
		Operations operations = new Operations();
		ArrayList<Character> alfabeto = new ArrayList<Character>();
		ArrayList<BigDecimal> proporciones = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> lowInter = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> highInter = new ArrayList<BigDecimal>();
		int[] totalCaracteres = new int[1];
		
		
		int[] frecuencias = operations.obtenerAlfabetoFrecuenciasYProporciones(mnsj_claro, alfabeto, proporciones, totalCaracteres);
		operations.calculaIntervalosIniciales(lowInter, highInter, frecuencias, totalCaracteres, alfabeto);
		ArrayList<Character> mnsj_coded = operations.codifica(lowInter, highInter, frecuencias, proporciones, totalCaracteres, alfabeto, mnsj_claro);
		
		System.out.println("\nMensaje codificado:");
		int j = 0;
		for(int i = 0; i < mnsj_coded.size(); i++) {
			if(mnsj_coded.get(i)== ' ') {
				System.out.print("-" + mnsj_claro.charAt(j)+ " ");
				j++;
			}else {
				System.out.print(mnsj_coded.get(i));
			}
		}
	}

}
 