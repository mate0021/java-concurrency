package rnd.mate00.javaconcurrency;

public class JsonRetrieverLongTask {

    public String getJson() throws InterruptedException {
        Thread.sleep(1500);

        return "{ field : value, other : otherValue }";
    }
}
