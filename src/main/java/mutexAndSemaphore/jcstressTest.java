package mutexAndSemaphore;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;


public class jcstressTest{
    @State
    public static class MyState extends PCThread {} //Classe a ser testada
    @JCStressTest
    @Description("Test description")
    @Outcome(id="0", expect = Expect.ACCEPTABLE, desc = "get back 1")
    public static class StressTest1 { //Classe de teste
        @Actor
        public void actor1(MyState myState, I_Result r) {
            try {
                r.r1 = myState.verificarImagem();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
