package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import static bg.sofia.uni.fmi.mjt.goodreads.utils.Validators.validateArgumentsNotNull;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {
        validateArgumentsNotNull(new Object[]{first, second});

        int similarGenresCount = (int) first.genres().stream()
                .filter(genre -> second.genres().contains(genre))
                .count();

        if (similarGenresCount == 0) return 0.0;

        return (double) similarGenresCount / Math.min(first.genres().size(), second.genres().size());
    }
    
}
