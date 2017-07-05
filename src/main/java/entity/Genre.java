package entity;

import java.util.ArrayList;

/**
 * Created by alexfomin on 03.07.17.
 */
public class Genre {
    private ArrayList<String> genres;

    private int genre_id;
    private String genre_name;

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(String genre) {
        genres.add(genre);
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
