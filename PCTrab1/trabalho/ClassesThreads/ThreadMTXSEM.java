package ClassesThreads;

import classesComuns.Chebychev;
import classesComuns.TratamentoImagem;
import classesPrincipais.MutexSemaphore;

import java.io.File;
import java.util.concurrent.Semaphore;

public class ThreadMTXSEM extends Thread{
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

    public int verificarImagem() throws InterruptedException {
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        File[] files = MutexSemaphore.folder.listFiles();
        s1.acquire();
        int i = MutexSemaphore.index;
        MutexSemaphore.index++;
        s1.release();
        boolean first = true;
        while(i< MutexSemaphore.folder.listFiles().length){
            System.out.println(a.KnnFunction(5, MutexSemaphore.imagens, tratamento.TratamentodaImagem(files[i].getAbsolutePath())) + " " + "index: " + i);
            if(first){
                first = false;
                continue;
            }
            s1.acquire();
            i = MutexSemaphore.index;
            if(MutexSemaphore.teste.contains(i)){
                return 1;
            }
            MutexSemaphore.teste.add(i);
            MutexSemaphore.index++;
            s1.release();
        }
        System.out.println(i);
        return 0;
    }
}
