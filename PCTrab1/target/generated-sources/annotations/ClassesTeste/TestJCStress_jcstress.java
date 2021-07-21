package ClassesTeste;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openjdk.jcstress.infra.runners.ForkedTestConfig;
import org.openjdk.jcstress.infra.collectors.TestResult;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.WorkerSync;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.os.AffinitySupport;
import org.openjdk.jcstress.vm.AllocProfileSupport;
import org.openjdk.jcstress.infra.runners.FootprintEstimator;
import org.openjdk.jcstress.infra.runners.VoidThread;
import org.openjdk.jcstress.infra.runners.LongThread;
import org.openjdk.jcstress.infra.runners.CounterThread;
import ClassesTeste.TestJCStress;
import org.openjdk.jcstress.infra.results.I_Result;

public final class TestJCStress_jcstress extends Runner<I_Result> {

    volatile WorkerSync workerSync;
    TestJCStress[] gs;
    I_Result[] gr;

    public TestJCStress_jcstress(ForkedTestConfig config) {
        super(config);
    }

    @Override
    public void sanityCheck(Counter<I_Result> counter) throws Throwable {
        sanityCheck_API(counter);
        sanityCheck_Footprints(counter);
    }

    private void sanityCheck_API(Counter<I_Result> counter) throws Throwable {
        final TestJCStress s = new TestJCStress();
        final I_Result r = new I_Result();
        VoidThread a0 = new VoidThread() { protected void internalRun() {
            s.testSerial(r);
        }};
        VoidThread a1 = new VoidThread() { protected void internalRun() {
            s.testMutexSemaphore(r);
        }};
        VoidThread a2 = new VoidThread() { protected void internalRun() {
            s.testAtomic(r);
        }};
        a0.start();
        a1.start();
        a2.start();
        a0.join();
        if (a0.throwable() != null) {
            throw a0.throwable();
        }
        a1.join();
        if (a1.throwable() != null) {
            throw a1.throwable();
        }
        a2.join();
        if (a2.throwable() != null) {
            throw a2.throwable();
        }
        counter.record(r);
    }

    private void sanityCheck_Footprints(Counter<I_Result> counter) throws Throwable {
        config.adjustStrideCount(new FootprintEstimator() {
          public void runWith(int size, long[] cnts) {
            long time1 = System.nanoTime();
            long alloc1 = AllocProfileSupport.getAllocatedBytes();
            TestJCStress[] ls = new TestJCStress[size];
            I_Result[] lr = new I_Result[size];
            for (int c = 0; c < size; c++) {
                TestJCStress s = new TestJCStress();
                I_Result r = new I_Result();
                lr[c] = r;
                ls[c] = s;
            }
            LongThread a0 = new LongThread() { public long internalRun() {
                long a1 = AllocProfileSupport.getAllocatedBytes();
                for (int c = 0; c < size; c++) {
                    ls[c].testSerial(lr[c]);
                }
                long a2 = AllocProfileSupport.getAllocatedBytes();
                return a2 - a1;
            }};
            LongThread a1 = new LongThread() { public long internalRun() {
                long a1 = AllocProfileSupport.getAllocatedBytes();
                for (int c = 0; c < size; c++) {
                    ls[c].testMutexSemaphore(lr[c]);
                }
                long a2 = AllocProfileSupport.getAllocatedBytes();
                return a2 - a1;
            }};
            LongThread a2 = new LongThread() { public long internalRun() {
                long a1 = AllocProfileSupport.getAllocatedBytes();
                for (int c = 0; c < size; c++) {
                    ls[c].testAtomic(lr[c]);
                }
                long a2 = AllocProfileSupport.getAllocatedBytes();
                return a2 - a1;
            }};
            a0.start();
            a1.start();
            a2.start();
            try {
                a0.join();
                cnts[0] += a0.result();
            } catch (InterruptedException e) {
            }
            try {
                a1.join();
                cnts[0] += a1.result();
            } catch (InterruptedException e) {
            }
            try {
                a2.join();
                cnts[0] += a2.result();
            } catch (InterruptedException e) {
            }
            for (int c = 0; c < size; c++) {
                counter.record(lr[c]);
            }
            long time2 = System.nanoTime();
            long alloc2 = AllocProfileSupport.getAllocatedBytes();
            cnts[0] += alloc2 - alloc1;
            cnts[1] += time2 - time1;
        }});
    }

    @Override
    public ArrayList<CounterThread<I_Result>> internalRun() {
        int len = config.strideSize * config.strideCount;
        gs = new TestJCStress[len];
        gr = new I_Result[len];
        for (int c = 0; c < len; c++) {
            gs[c] = new TestJCStress();
            gr[c] = new I_Result();
        }
        workerSync = new WorkerSync(false, 3, config.spinLoopStyle);

        control.isStopped = false;

        if (config.localAffinity) {
            try {
                AffinitySupport.tryBind();
            } catch (Exception e) {
                // Do not care
            }
        }

        ArrayList<CounterThread<I_Result>> threads = new ArrayList<>(3);
        threads.add(new CounterThread<I_Result>() { public Counter<I_Result> internalRun() {
            return task_testSerial();
        }});
        threads.add(new CounterThread<I_Result>() { public Counter<I_Result> internalRun() {
            return task_testMutexSemaphore();
        }});
        threads.add(new CounterThread<I_Result>() { public Counter<I_Result> internalRun() {
            return task_testAtomic();
        }});

        for (CounterThread<I_Result> t : threads) {
            t.start();
        }

        if (config.time > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(config.time);
            } catch (InterruptedException e) {
            }
        }

        control.isStopped = true;

        return threads;
    }

    private void jcstress_consume(Counter<I_Result> cnt, int a) {
        TestJCStress[] ls = gs;
        I_Result[] lr = gr;
        int len = config.strideSize * config.strideCount;
        int left = a * len / 3;
        int right = (a + 1) * len / 3;
        for (int c = left; c < right; c++) {
            I_Result r = lr[c];
            TestJCStress s = ls[c];
            ls[c] = new TestJCStress();
            cnt.record(r);
            r.r1 = 0;
        }
    }

    private void jcstress_sink(int v) {};
    private void jcstress_sink(short v) {};
    private void jcstress_sink(byte v) {};
    private void jcstress_sink(char v) {};
    private void jcstress_sink(long v) {};
    private void jcstress_sink(float v) {};
    private void jcstress_sink(double v) {};
    private void jcstress_sink(Object v) {};

    private Counter<I_Result> task_testSerial() {
        int len = config.strideSize * config.strideCount;
        int stride = config.strideSize;
        Counter<I_Result> counter = new Counter<>();
        if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[0]);
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }
            int check = 0;
            for (int start = 0; start < len; start += stride) {
                run_testSerial(gs, gr, start, start + stride);
                check += 3;
                sync.awaitCheckpoint(check);
            }
            jcstress_consume(counter, 0);
            if (sync.tryStartUpdate()) {
                workerSync = new WorkerSync(control.isStopped, 3, config.spinLoopStyle);
            }
            sync.postUpdate();
        }
    }

    private void run_testSerial(TestJCStress[] gs, I_Result[] gr, int start, int end) {
        TestJCStress[] ls = gs;
        I_Result[] lr = gr;
        for (int c = start; c < end; c++) {
            TestJCStress s = ls[c];
            I_Result r = lr[c];
            jcstress_sink(r.jcstress_trap);
            s.testSerial(r);
        }
    }

    private Counter<I_Result> task_testMutexSemaphore() {
        int len = config.strideSize * config.strideCount;
        int stride = config.strideSize;
        Counter<I_Result> counter = new Counter<>();
        if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[1]);
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }
            int check = 0;
            for (int start = 0; start < len; start += stride) {
                run_testMutexSemaphore(gs, gr, start, start + stride);
                check += 3;
                sync.awaitCheckpoint(check);
            }
            jcstress_consume(counter, 1);
            if (sync.tryStartUpdate()) {
                workerSync = new WorkerSync(control.isStopped, 3, config.spinLoopStyle);
            }
            sync.postUpdate();
        }
    }

    private void run_testMutexSemaphore(TestJCStress[] gs, I_Result[] gr, int start, int end) {
        TestJCStress[] ls = gs;
        I_Result[] lr = gr;
        for (int c = start; c < end; c++) {
            TestJCStress s = ls[c];
            I_Result r = lr[c];
            jcstress_sink(r.jcstress_trap);
            s.testMutexSemaphore(r);
        }
    }

    private Counter<I_Result> task_testAtomic() {
        int len = config.strideSize * config.strideCount;
        int stride = config.strideSize;
        Counter<I_Result> counter = new Counter<>();
        if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[2]);
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }
            int check = 0;
            for (int start = 0; start < len; start += stride) {
                run_testAtomic(gs, gr, start, start + stride);
                check += 3;
                sync.awaitCheckpoint(check);
            }
            jcstress_consume(counter, 2);
            if (sync.tryStartUpdate()) {
                workerSync = new WorkerSync(control.isStopped, 3, config.spinLoopStyle);
            }
            sync.postUpdate();
        }
    }

    private void run_testAtomic(TestJCStress[] gs, I_Result[] gr, int start, int end) {
        TestJCStress[] ls = gs;
        I_Result[] lr = gr;
        for (int c = start; c < end; c++) {
            TestJCStress s = ls[c];
            I_Result r = lr[c];
            jcstress_sink(r.jcstress_trap);
            s.testAtomic(r);
        }
    }

}
