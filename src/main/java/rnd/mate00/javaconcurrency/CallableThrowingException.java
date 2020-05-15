package rnd.mate00.javaconcurrency;

import java.util.concurrent.Callable;

public class CallableThrowingException implements Callable<String> {

    @Override
    public String call() {
        System.out.println("Throwing exception during call");
        throw new RuntimeException("error during call");
    }
}
