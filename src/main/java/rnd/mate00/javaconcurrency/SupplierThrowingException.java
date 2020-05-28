package rnd.mate00.javaconcurrency;

import java.util.function.Supplier;

public class SupplierThrowingException implements Supplier<String> {

    private int argument;

    public SupplierThrowingException(int argument) {
        this.argument = argument;
    }

    @Override
    public String get() {
        if (argument < 10) {
            throw new IllegalArgumentException("Argument is to small");
        }

        return "Argument was " + argument;
    }
}
