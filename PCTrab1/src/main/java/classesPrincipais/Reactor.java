package classesPrincipais;

import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reactor {
    public static void main(String []args){
        long inicio = System.currentTimeMillis();
        File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        ArrayList<Imagem> imagens = LerCSV.lerCSV();
        List<File> files = Arrays.asList(folder.listFiles());
        Flux<File> flux = Flux.generate(
                ()-> 0,
                (state, sink) -> {
                    sink.next(files.get(state));
                    if(state == files.size()-1) sink.complete();
                    return state + 1;
                }
        );
        flux.subscribe(file -> System.out.println(a.KnnFunction(5, imagens, tratamento.TratamentodaImagem(file.getAbsolutePath()))),
                e -> e.printStackTrace(),
                ()-> System.out.println("Finished")
        );
        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de duracao:" + (fim - inicio ));
    }
}
