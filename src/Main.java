import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Collection<Integer> c = printCollection(List.of(1, 2, 3, 4));
    }

    static <T> Collection<T> printCollection(Collection<T> c) {
        for (T e : c) {
            System.out.println(e);
        }
        return c;
    }
}