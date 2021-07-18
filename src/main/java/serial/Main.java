package serial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main{ //extends Application{

	public static void main(String[] args) {
		TratamentoImagem tratamento = new TratamentoImagem();
		//String caminhoImagem = "C:\\Users\\marti\\OneDrive\\Desktop\\Nova pasta (2)\\dataset_2019_1\\person\\0.png";
		String caminhoLeitura = "C:\\Users\\efssp\\Documents\\dataset_2019_1\\dataset_2019_1.csv";
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
		File folder = new File("C:\\Users\\efssp\\Documents\\dataset_2019_1\\dataset");
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				//System.out.println(file.getAbsolutePath());
				System.out.println(a.KnnFunction(5,imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())));
			} else {
				//findAllFilesInFolder(file);
			}
		}
		System.out.println(folder.listFiles().length);
		/*String x = a.KnnFunction(5,imagens, tratamento.TratamentodaImagem(caminhoImagem));
		System.out.println(x);*/
	}
	
	/*
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
		Scene scene = new Scene(root, 502, 676);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	 */
}
