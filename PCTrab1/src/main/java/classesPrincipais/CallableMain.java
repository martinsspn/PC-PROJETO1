package classesPrincipais;

import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class CallableMain {
    public static ArrayList<Imagem> imagens = new ArrayList<Imagem>();

    public static void main(String [] args) throws ExecutionException, InterruptedException {
        long inicio = System.currentTimeMillis();
        imagens = LerCSV.lerCSV();
        verificarImagem();
        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de duracao:" + (fim - inicio ));
    }

    public static void verificarImagem() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Callable<String>> callables = new ArrayList<>();
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
        for (File file : folder.listFiles()) {
            callables.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return(a.KnnFunction(5,imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())));
                }
            });
        }
        List<Future<String>> futures = executorService.invokeAll(callables);
        for(Future<String> future : futures){
            System.out.println("future.get = " + future.get());
        }
        executorService.shutdown();
        try{
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(folder.listFiles().length);
    }
}
