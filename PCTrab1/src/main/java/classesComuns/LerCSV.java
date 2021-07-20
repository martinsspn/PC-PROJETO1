package classesComuns;

import java.io.IOException;
import java.util.ArrayList;

public class LerCSV {
    public static ArrayList lerCSV(){
        ArrayList<Imagem> imagens = new ArrayList<Imagem>();
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
        return imagens;
    }
}
