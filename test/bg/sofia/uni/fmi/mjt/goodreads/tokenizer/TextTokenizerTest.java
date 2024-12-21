package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextTokenizerTest {

    private final static String STOPWORDS_INPUT = "a\nan\nthe\nof\nand\nfor\nover\n";

    @Test
    void readsProperlyStopwords() {
        Reader stopwordsReader = new StringReader(STOPWORDS_INPUT);
        TextTokenizer tokenizer = new TextTokenizer(stopwordsReader);

        Set<String> expectedStopwords = Set.of("a", "an", "the", "of", "and", "for", "over");

        assertEquals(expectedStopwords, tokenizer.stopwords(), "Invalid Stopwords");
    }

    @Test
    void tokenize() {
        Reader stopwordsReader = new StringReader(STOPWORDS_INPUT);
        TextTokenizer tokenizer = new TextTokenizer(stopwordsReader);

        List<String> expectedTokens = List.of("quick", "brown", "fox", "jumps", "lazy", "dog");

        List<String> tokens = tokenizer.tokenize("The quick, brown fox jumps over the lazy dog!");

        assertEquals(expectedTokens, tokens, "Tokens are not as expected");
    }
}