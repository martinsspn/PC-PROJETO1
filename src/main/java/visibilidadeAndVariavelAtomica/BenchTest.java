package visibilidadeAndVariavelAtomica;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ConcurrentHashMap;

public class BenchTest {
    private static ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();

    @Benchmark
    @Fork(value = 1)
    public void init(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            String s = new String("String to intern " + i);
            String t = s.intern();
            bh.consume(t);
        }
    }

    @Benchmark
    @Fork(value = 1)
    public void concurrentInit(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            String s = new String("String to intern " + i);
            String t = map.putIfAbsent(s, s);
            bh.consume(t);
        }
    }
}



