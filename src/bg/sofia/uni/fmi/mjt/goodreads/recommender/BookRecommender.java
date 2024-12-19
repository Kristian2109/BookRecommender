package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.utils.Validators.validateArgumentsNotNull;

public class BookRecommender implements BookRecommenderAPI {
    private final Set<Book> initialBooks;
    private final SimilarityCalculator calculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        validateArgumentsNotNull(new Object[]{initialBooks, calculator});

        this.calculator = calculator;
        this.initialBooks = initialBooks;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        validateArgumentsNotNull(new Object[] {origin, maxN});

        Map<Book, Double> booksByScore = initialBooks
                .parallelStream()
                .filter(b -> !b.equals(origin))
                .map(book -> new BookAndScore(book, calculator.calculateSimilarity(origin, book)))
                .collect(Collectors.toMap(
                        BookAndScore::book,
                        BookAndScore::score,
                        (existing, replacement) -> existing,
                        HashMap::new
                ));

        return booksByScore.entrySet().stream()
                .sorted((a, b) -> Double.compare(booksByScore.get(b.getKey()), booksByScore.get(a.getKey())))
                .limit(maxN)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        () -> new TreeMap<>((a, b) -> Double.compare(booksByScore.get(b), booksByScore.get(a)))
                ));
    }

    private record BookAndScore(Book book, Double score) {}
}
