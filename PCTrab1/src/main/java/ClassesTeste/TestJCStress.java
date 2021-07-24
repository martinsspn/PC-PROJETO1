package ClassesTeste;

import ClassesThreads.ThreadAtomic;
import ClassesThreads.ThreadMTXSEM;
import classesComuns.LerCSV;
import classesPrincipais.Atomic;
import classesPrincipais.MutexSemaphore;
import classesPrincipais.Serial;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@State
class TestState{
    Serial serial = new Serial();
    ThreadMTXSEM threadMTXSEM = new ThreadMTXSEM();
    ThreadAtomic threadAtomic = new ThreadAtomic();
}

@JCStressTest
@State
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "get back 1")
public class TestJCStress{
    TestState state = new TestState();

    @Actor
    void testSerial(I_Result r) {
        Serial.imagens = LerCSV.lerCSV();
        r.r1 = state.serial.verificarImagem();
    }

    @Actor
    void testMutexSemaphore(I_Result r){
        try {
            MutexSemaphore.imagens = LerCSV.lerCSV();
            r.r1 = state.threadMTXSEM.verificarImagem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Actor
    void testAtomic(I_Result r){
        try {
            Atomic.imagens = LerCSV.lerCSV();
            r.r1 = state.threadAtomic.verificarImagem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}