package ClassesTeste;

import ClassesThreads.ThreadAtomic;
import ClassesThreads.ThreadMTXSEM;
import classesComuns.Chebychev;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;
import classesPrincipais.Atomic;
import classesPrincipais.MutexSemaphore;
import classesPrincipais.Serial;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BenchTest {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State{
        Serial serial = new Serial();
        ThreadMTXSEM threadMTXSEM = new ThreadMTXSEM();
        ThreadAtomic threadAtomic = new ThreadAtomic();
    }

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

}




