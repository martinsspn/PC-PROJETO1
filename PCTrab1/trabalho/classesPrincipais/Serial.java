package classesPrincipais;

import classesComuns.*;
import java.io.File;
import java.util.ArrayList;

public class Serial{

	public static ArrayList<Imagem> imagens = new ArrayList<Imagem>();
	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		verificarImagem();
		long fim  = System.currentTimeMillis();
		System.out.println("Tempo de duracao:" + (fim - inicio ));
	}

	public static int verificarImagem(){
		TratamentoImagem tratamento = new TratamentoImagem();
		imagens = LerCSV.lerCSV();
		Chebychev a = new Chebychev();
		File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
		if(!folder.exists()){
			return -1;
		}
		int i = 0;
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				System.out.println(a.KnnFunction(5,imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())) + " Index: " + i);
				i++;
			}
		}
		System.out.println(folder.listFiles().length);
		return 0;
	}
}
