package ClassesThreads;

import classesComuns.Chebychev;
import classesComuns.TratamentoImagem;
import classesPrincipais.Atomic;

import java.io.File;

public class ThreadAtomic extends Thread{


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
        File[] files = Atomic.folder.listFiles();
        int i = Atomic.idx.getAndIncrement();
        boolean first = true;
        while(i< Atomic.folder.listFiles().length){
            System.out.println(a.KnnFunction(5, Atomic.imagens, tratamento.TratamentodaImagem(files[i].getAbsolutePath())) + " " + "index: " + i);
            if(first){
                first = false;
                continue;
            }
            i = Atomic.idx.getAndIncrement();
        }
        System.out.println(i);
        return 0;
    }
}
