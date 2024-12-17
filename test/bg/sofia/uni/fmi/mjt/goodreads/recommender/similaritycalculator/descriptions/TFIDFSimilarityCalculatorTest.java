package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.goodreads.utils.MathConstants.DELTA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

class TFIDFSimilarityCalculatorTest {
    private TFIDFSimilarityCalculator calculator;
    private Set<Book> books;
    private Book targetBook;

    @BeforeEach
    void setUp() {
        targetBook = mock(Book.class);
        books = new HashSet<>();
        books.add(targetBook);

        TextTokenizer tokenizer = mock(TextTokenizer.class);
        when(tokenizer.tokenize(anyString()))
                .thenAnswer(invocation -> List.of(invocation.getArgument(0, String.class).split(" ")));

        calculator = spy(new TFIDFSimilarityCalculator(books, tokenizer));
    }

    @Test
    void calculateSimilarity() {
        Book firstBook = mock(Book.class);
        Book secondBook = mock(Book.class);

        Map<String, Double> firstBookTDIDF = Map.of("good", 0.2, "bad", 0.5, "brain", 0.0);
        Map<String, Double> secondBookTDIDF = Map.of("good", 0.4, "bad", 0.1, "people", 0.5);

        when(calculator.computeTFIDF(firstBook)).thenReturn(firstBookTDIDF);
        when(calculator.computeTFIDF(secondBook)).thenReturn(secondBookTDIDF);

        double expectedSimilarity = 0.3724;

        double actualSimilarity = calculator.calculateSimilarity(firstBook, secondBook);
        assertEquals(expectedSimilarity, actualSimilarity, DELTA, "Similarities aren't equal");
    }

    @Test
    void computeTFIDF() {
        Map<String, Double> tfSimilarity = Map.of("good", 0.5, "bad", 0.3, "brain", 0.2);
        Map<String, Double> idfSimilarities = Map.of("good", 0.12, "bad", 0.4, "brain", 0.0);
        Map<String, Double> expectedTFIDF = Map.of("good", 0.06, "bad", 0.12, "brain", 0.0);

        when(calculator.computeTF(targetBook)).thenReturn(tfSimilarity);
        when(calculator.computeIDF(targetBook)).thenReturn(idfSimilarities);
        assertWordSimilaritiesEqual(expectedTFIDF, calculator.computeTFIDF(targetBook));
    }

    @Test
    void computeTF() {
        Map<String, Double> expectedSimilarities = Map.of(
                "book", 0.1,
                "good", 0.3,
                "really", 0.1,
                "very", 0.1,
                "together", 0.1,
                "more", 0.3
        );
        String targetDescription = "book good really good very good more together more more";
        when(targetBook.description()).thenReturn(targetDescription);
        assertEquals(expectedSimilarities, calculator.computeTF(targetBook));
    }

    @Test
    void computeIDF() {
        List<String> mockedDescriptions = List.of(
                "cat notcat dog snoopdog animal",
                "zero cat house mouse",
                "cat cat dog minimal"
        );

        mockedDescriptions.forEach(description -> {
                    Book mockedBook = mock(Book.class);
                    when(mockedBook.description()).thenReturn(description);
                    books.add(mockedBook);
                });

        String targetDescription = "cat dog animal";
        when(targetBook.description()).thenReturn(targetDescription);

        Map<String, Double> expectedSimilarities = Map.of(
                "cat", 0.0,
                "dog", 0.1249,
                "animal", 0.3010
        );

        assertWordSimilaritiesEqual(expectedSimilarities, calculator.computeIDF(targetBook));
    }

    private void assertWordSimilaritiesEqual(Map<String, Double> first, Map<String, Double> second) {
        assertEquals(first.size(), second.size(), "Sizes are not equal");

        first.forEach((key, value) -> {
            assertTrue(second.containsKey(key), "Key isn't contained in the second");
            assertEquals(value, second.get(key), DELTA, "Values differ");
        });
    }
}