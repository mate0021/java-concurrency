package rnd.mate00.javaconcurrency.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAsyncTest {

    @Test
    public void inWhichThreadAreWe() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Supply in " + Thread.currentThread().getName());
                    return "X";
                })
                .thenRunAsync(() -> {
                    System.out.println("I'm in " + Thread.currentThread().getName());
                })
                .thenRun(() -> {
                    System.out.println("Then in " + Thread.currentThread().getName());
                })
                .thenRun(() -> {
                    System.out.println("Another in " + Thread.currentThread().getName());
                })
                .thenRun(() -> {
                    System.out.println("Finally in " + Thread.currentThread().getName());
                });
    }

    @Test
    public void async() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName());
                    return "X";
                })
                .thenRunAsync(() -> {
                    System.out.println(Thread.currentThread().getName());
                })
                .thenRun(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
    }
}
