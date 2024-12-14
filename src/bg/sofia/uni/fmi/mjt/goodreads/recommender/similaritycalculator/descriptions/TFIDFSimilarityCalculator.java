package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.*;
import java.util.stream.Collectors;


public class TFIDFSimilarityCalculator implements SimilarityCalculator {
    private final TextTokenizer tokenizer;
    private final Set<Book> books;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.books = books;
    }

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        Map<String, Double> tfScores = computeTF(book);
        Map<String, Double> idfScores = computeIDF(book);

        return tfScores.entrySet().stream()
                .filter(entry -> idfScores.containsKey(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (tfEntry) -> tfEntry.getValue() * idfScores.get(tfEntry.getKey())
                ));
    }

    public Map<String, Double> computeTF(Book book) {
        List<String> tokens = tokenizer.tokenize(book.description());
        Map<String, Integer> wordsCounts = new HashMap<>();
        tokens.forEach(token -> {
            Integer currentCount = wordsCounts.getOrDefault(token, 0);
            currentCount++;
            wordsCounts.put(token, currentCount);
        });

        return wordsCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / tokens.size()
                ));
    }

    public Map<String, Double> computeIDF(Book book) {
        Map<String, Set<Book>> booksContainingEveryWords = new HashMap<>();
        books.forEach(b -> {
            List<String> bookTokens = tokenizer.tokenize(b.description());
            bookTokens.forEach(token -> {
                booksContainingEveryWords.putIfAbsent(token, new HashSet<>());
                booksContainingEveryWords.get(token).add(b);
            });
        });

        return tokenizer.tokenize(book.description()).stream()
                .collect(Collectors.toMap(
                        token -> token,
                        (token) -> {
                            int booksContainingTokenCount = booksContainingEveryWords.get(token).size();
                            return Math.log((double) books.size() / booksContainingTokenCount);
                        }
                ));
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
                .mapToDouble(word -> first.get(word) * second.get(word))
                .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
                .map(v -> v * v)
                .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}
