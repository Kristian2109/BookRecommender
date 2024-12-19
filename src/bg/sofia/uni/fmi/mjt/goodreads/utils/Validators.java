package bg.sofia.uni.fmi.mjt.goodreads.utils;

public class Validators {
    public static void validateArgumentsNotNull(Object[] args) {
        for (var arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("Null Argument Provided");
            }
        }
    }
}
