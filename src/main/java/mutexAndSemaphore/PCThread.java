package mutexAndSemaphore;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class PCThread extends Thread{
    private final Semaphore s1 = new Semaphore(1,true);

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
        s1.acquire();
        int i = Main.index;
        Main.index++;
        s1.release();
        boolean first = true;
        while(i<Main.folder.listFiles().length){
            System.out.println(a.KnnFunction(5, Main.imagens, tratamento.TratamentodaImagem(files[i].getAbsolutePath())) + " " + "index: " + i);
            if(first){
                first = false;
                continue;
            }
            s1.acquire();
            i = Main.index;
            Main.index++;
            s1.release();
        }
        System.out.println(i);
    }
}
