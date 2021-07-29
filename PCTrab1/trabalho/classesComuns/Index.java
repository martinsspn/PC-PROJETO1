package classesComuns;

import java.util.concurrent.atomic.AtomicInteger;

public class Index {

    private AtomicInteger index = new AtomicInteger(0);
    public void incrementIndex() {
        index.incrementAndGet();
    }
    public int getIndex() {
        return index.get();
    }
}