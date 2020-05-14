package rnd.mate00.javaconcurrency;

import java.util.concurrent.Callable;

public class JsonCallable implements Callable<String> {

    private JsonRetrieverLongTask task;

    public JsonCallable(JsonRetrieverLongTask task) {
        this.task = task;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Calling task");
        return task.getJson();
    }
}
