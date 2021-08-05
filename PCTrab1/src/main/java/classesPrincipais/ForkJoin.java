package classesPrincipais;

import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


public class ForkJoin extends RecursiveAction {
    private static final int SEQUENTIAL_THRESHOLD = 480;
    private List<File> folder;
    private ArrayList<Imagem> imagens;
    public ForkJoin(List<File> folder) {
        this.folder = folder;
        imagens = LerCSV.lerCSV();
    }

    protected void compute() {
        if (folder.size() <= SEQUENTIAL_THRESHOLD) { // base case
            verificarImagem(folder);
        } else { // recursive case
// Calcuate new range
            int mid = folder.size() / 2;
            ForkJoin firstSubtask = new ForkJoin(folder.subList(0, mid));
            ForkJoin secondSubtask = new ForkJoin(folder.subList(mid, folder.size()));
            firstSubtask.fork(); // queue the first task
            secondSubtask.compute(); // compute the second task
            firstSubtask.join(); // wait for the first task result
// invokeAll(firstSubtask, secondSubtask);
        }
    }

    public void verificarImagem(List<File> folder){
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        for(File file: folder){
            System.out.println(a.KnnFunction(5, imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())));
        }
    }

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoin task = new ForkJoin(Arrays.asList(folder.listFiles()));
        pool.invoke(task);
        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de duracao:" + (fim - inicio ));
    } }
