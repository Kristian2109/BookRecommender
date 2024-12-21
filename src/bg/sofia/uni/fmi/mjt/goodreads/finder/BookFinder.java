package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.utils.Validators.validateArgumentsNotNull;

public class BookFinder implements BookFinderAPI {
    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    public Set<Book> allBooks() {
        return Collections.unmodifiableSet(books);
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        if (authorName == null || authorName.isBlank()) {
            throw new IllegalArgumentException("Invalid authorName");
        }

        return books.stream().filter(book -> book.author().equals(authorName)).toList();
    }

    @Override
    public Set<String> allGenres() {
        return books.stream().flatMap(book -> book.genres().stream()).collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        validateArgumentsNotNull(new Object[] {genres, option});

        return books.stream().filter(book -> {
            Set<String> genresSet = new HashSet<>(book.genres());
            if (option == MatchOption.MATCH_ALL) {
                return genresSet.containsAll(genres);
            }

            genresSet.retainAll(genres);

            return genresSet.size() != 0;
        }).toList();
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        validateArgumentsNotNull(new Object[] {keywords, option});

        return books.stream().filter(book -> {
            Set<String> allWords = new HashSet<>();
            allWords.addAll(tokenizer.tokenize(book.title()));
            allWords.addAll(tokenizer.tokenize(book.description()));

            if (option == MatchOption.MATCH_ALL) {
                return allWords.containsAll(keywords);
            }

            allWords.retainAll(keywords);

            return allWords.size() != 0;

        }).toList();
    }
}
