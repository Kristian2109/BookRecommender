package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GenresOverlapSimilarityCalculatorTest {
    private GenresOverlapSimilarityCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new GenresOverlapSimilarityCalculator();
    }

    @Test
    void calculateSimilarity() {
        Book firstBook = mock(Book.class);
        Book secondBook = mock(Book.class);

        when(firstBook.genres()).thenReturn(List.of("genre1", "genre2", "genre10", "genre5", "genre11"));
        when(secondBook.genres()).thenReturn(List.of("genre2", "genre8", "genre9", "genre5"));

        double expectedScore = 0.5;

        assertEquals(expectedScore, calculator.calculateSimilarity(firstBook, secondBook), "Invalid score");
    }
}