package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Map;

public class CompositeSimilarityCalculator implements SimilarityCalculator {
    private final Map<SimilarityCalculator, Double> similarityCalculatorMap;

    public CompositeSimilarityCalculator(Map<SimilarityCalculator, Double> similarityCalculatorMap) {
        this.similarityCalculatorMap = similarityCalculatorMap;
    }


    @Override
    public double calculateSimilarity(Book first, Book second) {
        return similarityCalculatorMap
                .entrySet()
                .parallelStream()
                .mapToDouble((entry) -> entry.getKey().calculateSimilarity(first, second) * entry.getValue())
                .sum();
    }
}
