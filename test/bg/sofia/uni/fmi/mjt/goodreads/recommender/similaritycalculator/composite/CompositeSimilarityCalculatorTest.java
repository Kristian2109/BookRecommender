package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static bg.sofia.uni.fmi.mjt.goodreads.utils.MathConstants.DELTA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompositeSimilarityCalculatorTest {

    @Test
    void calculateSimilarity() {
        Book firstBook = mock(Book.class);
        Book secondBook = mock(Book.class);

        SimilarityCalculator firstCalculator = mock(SimilarityCalculator.class);
        SimilarityCalculator secondCalculator = mock(SimilarityCalculator.class);
        SimilarityCalculator thirdCalculator = mock(SimilarityCalculator.class);

        Map<SimilarityCalculator, Double> calculatorsByWeight =
            Map.of(firstCalculator, 0.4, secondCalculator, 0.3, thirdCalculator, 0.3);

        when(firstCalculator.calculateSimilarity(firstBook, secondBook)).thenReturn(5.0);
        when(secondCalculator.calculateSimilarity(firstBook, secondBook)).thenReturn(1.0);
        when(thirdCalculator.calculateSimilarity(firstBook, secondBook)).thenReturn(2.0);

        CompositeSimilarityCalculator calculator = new CompositeSimilarityCalculator(calculatorsByWeight);

        double expectedSimilarity = 2.9;

        double actualScore = calculator.calculateSimilarity(firstBook, secondBook);

        assertEquals(expectedSimilarity, actualScore, DELTA, "Invalid similarity");
    }
}