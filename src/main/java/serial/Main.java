package serial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main{ //extends Application{

	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		TratamentoImagem tratamento = new TratamentoImagem();
		String caminhoLeitura = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset_2019_1.csv";
		Leitura leitura = null;
		try {
			leitura = new Leitura(caminhoLeitura);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Imagem> imagens = new ArrayList<Imagem>();
		while(true) {
			try {
				if (leitura.lerLinhas() == 1) break;
			} catch (IOException e) {
				e.printStackTrace();
			}
			Imagem imagem = new Imagem(leitura.getLista(), leitura.getClasse());
			imagens.add(imagem);
		}
		Knn a = new Chebychev();
		File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
		int i = 0;
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				System.out.println(a.KnnFunction(5,imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())) + " Index: " + i);
				i++;
			}
		}
		System.out.println(folder.listFiles().length);
		long fim  = System.currentTimeMillis();
		System.out.println("Tempo de duracao:" + (fim - inicio ));
	}
}
