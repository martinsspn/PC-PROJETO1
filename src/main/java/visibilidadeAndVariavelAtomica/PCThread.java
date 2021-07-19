package visibilidadeAndVariavelAtomica;

import java.io.File;
import java.util.concurrent.Semaphore;

public class PCThread extends Thread{
    private final Semaphore s1 = new Semaphore(1,true);
    volatile Index index = new Index();

    public void run(){
        try {
            verificarImagem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void verificarImagem() throws InterruptedException {
        TratamentoImagem tratamento = new TratamentoImagem();
        Knn a = new Chebychev();
        File[] files = Main.folder.listFiles();
        int i = index.getIndex();
        boolean first = true;
        while(i< Main.folder.listFiles().length){
            System.out.println(a.KnnFunction(5, Main.imagens, tratamento.TratamentodaImagem(files[i].getAbsolutePath())) + " " + "index: " + i);
            if(first){
                first = false;
                continue;
            }
            i = index.getIndex();
            index.incrementIndex();
        }
        System.out.println(i);
    }
}
