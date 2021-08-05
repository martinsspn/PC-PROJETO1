package classesPrincipais;

import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelStream {

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        ArrayList<Imagem> imagens = LerCSV.lerCSV();
        List<File> files = Arrays.asList(folder.listFiles());
        files.parallelStream().forEach(file -> System.out.println(a.KnnFunction(5, imagens, tratamento.TratamentodaImagem(file.getAbsolutePath()))));
        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de duracao:" + (fim - inicio ));
    }
}
