package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.List;

public record Book(String ID, String title, String author, String description, List<String> genres, double rating,
                   int ratingCount, String URL) {

    private static final int TOKENS_COUNT = 8;
    private static final int ID_POSITION = 0;
    private static final int TITLE_POSITION = 1;
    private static final int AUTHOR_POSITION = 2;
    private static final int DESCRIPTION_POSITION = 3;
    private static final int GENRES_POSITION = 4;
    private static final int RATING_POSITION = 5;
    private static final int RATING_COUNT_POSITION = 6;
    private static final int URL_POSITION = 7;

    public static Book of(String[] tokens) {
        if (tokens == null || tokens.length != TOKENS_COUNT) {
            throw new IllegalArgumentException("Invalid tokens array");
        }

        try {
            String id = tokens[ID_POSITION];
            String title = tokens[TITLE_POSITION];
            String author = tokens[AUTHOR_POSITION];
            String description = tokens[DESCRIPTION_POSITION];
            List<String> genres = List.of(tokens[GENRES_POSITION].replace("[", "").replace("]", "").replace("'", "").split(",\\s*"));
            double rating = Double.parseDouble(tokens[RATING_POSITION]);
            int ratingCount = Integer.parseInt(tokens[RATING_COUNT_POSITION].replace(",", ""));
            String url = tokens[URL_POSITION];

            return new Book(id, title, author, description, genres, rating, ratingCount, url);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid tokens provided", e);
        }
    }
}
