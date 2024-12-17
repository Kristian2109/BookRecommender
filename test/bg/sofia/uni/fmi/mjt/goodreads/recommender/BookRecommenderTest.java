package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookRecommenderTest {
    @Mock
    private SimilarityCalculator calculatorMock;

    private BookRecommender recommender;
    private Set<Book> books;
    private Book targetBook, book2, book3, book4, book5;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        targetBook = mock(Book.class);
        book2 = mock(Book.class);
        book3 = mock(Book.class);
        book4 = mock(Book.class);
        book5 = mock(Book.class);

        books = Set.of(book2, book4, book5, targetBook, book3);

        when(calculatorMock.calculateSimilarity(targetBook, book2)).thenReturn(0.8);
        when(calculatorMock.calculateSimilarity(targetBook, book3)).thenReturn(0.6);
        when(calculatorMock.calculateSimilarity(targetBook, book4)).thenReturn(0.4);
        when(calculatorMock.calculateSimilarity(targetBook, book5)).thenReturn(0.2);

        recommender = new BookRecommender(books, calculatorMock);
    }

    @Test
    public void testRecommendBooksWithMaxNEqualsTwo() {
        SortedMap<Book, Double> recommendations = recommender.recommendBooks(targetBook, 2);

        assertEquals(2, recommendations.size(), "Invalid size of recommendations");

        Map.Entry<Book, Double> first = recommendations.pollFirstEntry();
        Map.Entry<Book, Double> second = recommendations.pollFirstEntry();

        assertEquals(book2, first.getKey(), "Invalid first book");
        assertEquals(book3, second.getKey(), "Invalid second book");
        assertEquals(0.8, first.getValue(), "Invalid first book score");
        assertEquals(0.6, second.getValue(), "Invalid second book score");
    }
//
    @Test
    public void testRecommendBooksWithMaxNGreaterThanAvailable() {
        SortedMap<Book, Double> recommendations = recommender.recommendBooks(targetBook, 10);

        assertEquals( 4, recommendations.size(), "Should return only available recommendations");
    }
}