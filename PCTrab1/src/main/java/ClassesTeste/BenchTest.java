package ClassesTeste;

import ClassesThreads.ThreadAtomic;
import ClassesThreads.ThreadMTXSEM;
import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;
import classesPrincipais.*;
import org.openjdk.jmh.annotations.*;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class BenchTest {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State{
        Serial serial = new Serial();
        ThreadMTXSEM threadMTXSEM = new ThreadMTXSEM();
        ThreadAtomic threadAtomic = new ThreadAtomic();
        ArrayList<Imagem> imagens = LerCSV.lerCSV();
        Chebychev a = new Chebychev();
        TratamentoImagem tratamento = new TratamentoImagem();
        List<File> files = Arrays.asList(new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset").listFiles());
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoin task = new ForkJoin(files);
    }

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testeParallelStream(State state){
        state.files.parallelStream().forEach(file -> System.out.println(state.a.KnnFunction(5, state.imagens, state.tratamento.TratamentodaImagem(file.getAbsolutePath()))));
    }

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testeForkJoin(State state){
        state.pool.invoke(state.task);
    }
    /*
    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testeExecutor(State state){
        ExecutorMain.imagens = LerCSV.lerCSV();
        ExecutorMain.verificarImagem();
    }

    /*
    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testeSerial(State state){
        Serial.imagens = LerCSV.lerCSV();
        state.serial.verificarImagem();
    }
    /*
    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testeMTXSEM(State state){
        try {
            MutexSemaphore.imagens = LerCSV.lerCSV();
            state.threadMTXSEM.verificarImagem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testeAtomic(State state){
        try {
            Atomic.imagens = LerCSV.lerCSV();
            state.threadAtomic.verificarImagem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     */
}




