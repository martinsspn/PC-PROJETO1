package visibilidadeAndVariavelAtomica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Leitura {
	private FileReader arq;
	private BufferedReader lerArq;
	private ArrayList<Float> lista;
	private String classe;
	
	public Leitura(String caminho) throws FileNotFoundException, IOException {
		arq = new FileReader(caminho);
		lerArq = new BufferedReader(arq);
		lista = new ArrayList<Float>();
		lerArq.readLine();
	}
	
	public int lerLinhas() throws IOException {
		String csvDivisor = ",";
		String linha = lerArq.readLine();
		if(linha == null) {
        	return 1; 
		}
		String[] Coluna = linha.split(csvDivisor);
        for(String item : Coluna) {
        	if(Leitura.isNumeric(item)) {
        		lista.add(Float.valueOf(item));
            }else {
            	classe = item;
            }
        }
		return 0;
}
	
	public String getClasse() {
		return this.classe;
	}
	
	public ArrayList<Float> getLista(){
		ArrayList<Float> aux = lista;
		this.lista = new ArrayList<Float>();
		return aux;
	}

	public static boolean isNumeric (String s) {
	    try {
	        Float.parseFloat (s); 
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	}
}