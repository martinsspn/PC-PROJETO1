package classesPrincipais;

import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorMain {

    public static ArrayList<Imagem> imagens = new ArrayList<Imagem>();

    public static void main(String [] args){
        long inicio = System.currentTimeMillis();
        imagens = LerCSV.lerCSV();
        verificarImagem();
        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de duracao:" + (fim - inicio ));
    }

    public static void verificarImagem(){
        AtomicLong count = new AtomicLong(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
        for (File file : folder.listFiles()) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (!file.isDirectory()) {
                        System.out.println(a.KnnFunction(5,imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())));
                    }
                    count.incrementAndGet();
                }
            });
        }
        executorService.shutdown();
        try{
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(folder.listFiles().length);
        System.out.println("Count = " + count);
    }

}
