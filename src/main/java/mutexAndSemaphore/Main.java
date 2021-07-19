package mutexAndSemaphore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main{ //extends Application{

	public static File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
	public static ArrayList<Imagem> imagens = new ArrayList<Imagem>();

	public static int index=0;

	public static void main(String[] args) {
		String caminhoLeitura = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset_2019_1.csv";
		Leitura leitura = null;
		try {
			leitura = new Leitura(caminhoLeitura);
		} catch (
				IOException e) {
			e.printStackTrace();
		}

		while(true) {
			try {
				if (leitura.lerLinhas() == 1) break;
			} catch (IOException e) {
				e.printStackTrace();
			}
			Imagem imagem = new Imagem(leitura.getLista(), leitura.getClasse());
			imagens.add(imagem);
		}
		for(int i=0;i<7;i++){
			PCThread t = new PCThread();
			t.start();
		}
	}
}
