package Coding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Operations {
	
	public static int DECIMALS = 10;

	public int[] obtenerAlfabetoFrecuenciasYProporciones(String mensaje, ArrayList<Character> alfabeto,  ArrayList<BigDecimal> proporciones, int[] totalCaracteres) {
		// TODO Auto-generated method stub	
		int[] frecuencias;
		
		System.out.println("-----------------------------ALFABETO-----------------------------");
		for (int i = 0; i < mensaje.length(); i++) {
			if (mensaje.charAt(i) == '\n') {

			} else {
				if (!alfabeto.contains(mensaje.charAt(i))) {
					alfabeto.add(mensaje.charAt(i));
					System.out.print(mensaje.charAt(i) + ", ");
				}
			}
		}
		frecuencias = new int[alfabeto.size()];

		//Calculo frecuencias

		for (int i = 0; i < mensaje.length(); i++) {
			if (mensaje.charAt(i) == '\n') {
				frecuencias[alfabeto.lastIndexOf(' ')]+=2;
			}else {
				frecuencias[alfabeto.lastIndexOf(mensaje.charAt(i))]++;
			}

		}
 
		// IMPRIMIR RESULTADOS POR PANTALLA

		System.out.println("\n-----------------------------FRECUENCIAS-----------------------------");
		for (int i = 0; i < alfabeto.size(); i++) {
			System.out.print(frecuencias[i] +  ", ");
			totalCaracteres[0] += frecuencias[i];
		}
		System.out.println("\nEl total de caracteres es: " + totalCaracteres[0]);
		
		//Calculo proporciones
		
		System.out.println("-----------------------------PROPORCIONES-----------------------------");
		for(int i = 0; i < frecuencias.length; i++) {
			BigDecimal totalBD = BigDecimal.valueOf(totalCaracteres[0]);
			BigDecimal num = BigDecimal.valueOf(frecuencias[i]);
			BigDecimal div = num.divide(totalBD, DECIMALS, RoundingMode.HALF_UP);
			proporciones.add(div);
			//System.out.print(alfabeto.get(i) + " proporcion: ");
			System.out.print(proporciones.get(i) + ", ");
		}
		System.out.println();
		
		return frecuencias;
		
	}

	public void calculaIntervalosIniciales(ArrayList<BigDecimal> l, ArrayList<BigDecimal> h, int[] frecuencias,
			int[] totalCaracteres, ArrayList<Character> alfabeto) {
		// TODO Auto-generated method stub
		BigDecimal totalBD = BigDecimal.valueOf(totalCaracteres[0]);
		BigDecimal uno = BigDecimal.valueOf(1);

		BigDecimal div = uno.divide(totalBD, DECIMALS, RoundingMode.HALF_UP);
		BigDecimal actual = BigDecimal.valueOf(0);
		
		System.out.println("-----------------------------INTERVALOS EN FRACCION-----------------------------");
		
		int impr = 0;

		for (int i = 0; i < alfabeto.size(); i++) {
			l.add(actual);
			System.out.print("[" + impr + "/" + totalCaracteres[0]);
			BigDecimal frecuencia = BigDecimal.valueOf(frecuencias[i]);
			BigDecimal multi = div.multiply(frecuencia);
			actual = actual.add(multi);
			h.add(actual);
			impr += frecuencias[i];
			System.out.print("-"+impr + "/" + totalCaracteres[0] + "], ");
		}
		h.remove(alfabeto.size()-1);
		h.add(new BigDecimal(1));
		
		System.out.println();
		System.out.println("-----------------------------INTERVALOS-----------------------------");
		for (int j = 0; j < alfabeto.size(); j++) {
			System.out.print("|" + alfabeto.get(j) + "| Intervalo: [" + l.get(j) +", " + h.get(j) + ") - " );
		}

	}

	public ArrayList<Character> codifica(ArrayList<BigDecimal> l, ArrayList<BigDecimal> h, int[] frecuencias,
			ArrayList<BigDecimal> proporciones, int[] totalCaracteres, ArrayList<Character> alfabeto, String mensaje) {
		// TODO Auto-generated method stub
		int bc = 0;
		ArrayList<Character> resultado = new ArrayList<Character>();
		BigDecimal currentLow = BigDecimal.valueOf(0);
		BigDecimal currentHigh = BigDecimal.valueOf(1); //el primer intervalo es el [0,1)
		boolean last = false;
		
		for(int i = 0; i < mensaje.length(); i++) {	
			if(mensaje.charAt(i)=='\n') {
				currentLow = l.get(alfabeto.lastIndexOf(' '));
				currentHigh = h.get(alfabeto.lastIndexOf(' '));
			}else {
				currentLow = l.get(alfabeto.lastIndexOf(mensaje.charAt(i)));
				currentHigh = h.get(alfabeto.lastIndexOf(mensaje.charAt(i)));
			}

			if(i+1 == mensaje.length()) {
				last = true;
			}
			
			boolean iter = true;
			
			while(iter == true) {
				iter = false;
				if((currentLow.compareTo(new BigDecimal("0"))==0||currentLow.compareTo(new BigDecimal("0"))==1) && (currentHigh.compareTo(new BigDecimal("0.5"))==-1)) {
					resultado.add('0');
					for(int j = 0; j < bc; j++) {
						resultado.add('1');
					}
					
					bc = 0;
					currentLow = currentLow.multiply(new BigDecimal("2"));
					currentHigh = currentHigh.multiply(new BigDecimal("2"));
					iter = true;

				}else if((currentLow.compareTo(new BigDecimal("0.5"))==0||currentLow.compareTo(new BigDecimal("0.5"))==1) && (currentHigh.compareTo(new BigDecimal("1"))==-1)) {
					resultado.add('1');
					for(int j = 0; j < bc; j++) {
						resultado.add('0');
					}
					
					bc = 0;
					currentLow = currentLow.subtract(new BigDecimal("0.5"));
					currentHigh = currentHigh.subtract(new BigDecimal("0.5"));
					currentLow = currentLow.multiply(new BigDecimal("2"));
					currentHigh = currentHigh.multiply(new BigDecimal("2"));
					iter = true;

				}else if((currentLow.compareTo(new BigDecimal("0.25"))==0||currentLow.compareTo(new BigDecimal("0.25"))==1) && (currentHigh.compareTo(new BigDecimal("0.75"))==-1)) {
					bc++;
					currentLow = currentLow.subtract(new BigDecimal("0.25"));
					currentHigh = currentHigh.subtract(new BigDecimal("0.25"));
					currentLow = currentLow.multiply(new BigDecimal("2"));
					currentHigh = currentHigh.multiply(new BigDecimal("2"));
					iter = true;
				}
				
				if(iter == false) {
					recalculaIntervalos(l, h, currentLow, currentHigh, proporciones);
					if(last) {
						bc++;
						if(currentLow.compareTo(new BigDecimal("0.25"))==-1) {
							resultado.add('0');
							for(int j = 0; j < bc; j++) {
								resultado.add('1');
							}
						}else {
							resultado.add('1');
							for(int j = 0; j < bc; j++) {
								resultado.add('0');
							}
						}
					}
				}
			}
		}
		
		return resultado;
	}
	
	private void recalculaIntervalos(ArrayList<BigDecimal> l, ArrayList<BigDecimal> h, BigDecimal currentLow,
			BigDecimal currentHigh, ArrayList<BigDecimal> proporciones) {
		// TODO Auto-generated method stub
		int iter = l.size();
		l = new ArrayList<BigDecimal>();
		h = new ArrayList<BigDecimal>();
		BigDecimal range = currentHigh.subtract(currentLow);
		l.add(currentLow);
		
		for(int i = 0; i < iter-1; i++) {
			h.add(l.get(i).add(range.multiply(proporciones.get(i))));
			l.add(l.get(i).add(range.multiply(proporciones.get(i))));
		}
		
		h.add(currentHigh);
	}

}
