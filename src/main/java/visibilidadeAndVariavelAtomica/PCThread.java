package visibilidadeAndVariavelAtomica;

import java.io.File;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class PCThread extends Thread{


    public void run(){
        try {
            long inicio = System.currentTimeMillis();
            verificarImagem();
            long fim  = System.currentTimeMillis();
            System.out.println("Tempo de duracao:" + (fim - inicio ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void verificarImagem() throws InterruptedException {
        TratamentoImagem tratamento = new TratamentoImagem();
        Knn a = new Chebychev();
        File[] files = Main.folder.listFiles();
        int i = Main.idx.getAndIncrement();
        boolean first = true;
        while(i< Main.folder.listFiles().length){
            System.out.println(a.KnnFunction(5, Main.imagens, tratamento.TratamentodaImagem(files[i].getAbsolutePath())) + " " + "index: " + i);
            if(first){
                first = false;
                continue;
            }
            i = Main.idx.getAndIncrement();
        }
        System.out.println(i);
    }
}
