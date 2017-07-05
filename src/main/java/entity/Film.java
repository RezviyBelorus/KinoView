package entity;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Created by alexfomin on 01.07.17.
 */

//todo: countries_id,genres_id - пока бесполезыне переменные

public class Film {
    private int film_id;
    private String film_name;
    private int release_year;
    private int quality_id;
    private int translation_id;
    private String length;
    private float rating;
    private LocalDateTime upload_date;
    private int status;

    private ArrayList<Integer> countries_id = new ArrayList<>();
    private ArrayList<Integer> genres_id = new ArrayList<>();

    public LocalDateTime getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(LocalDateTime upload_date) {
        this.upload_date = upload_date;
    }

    public int getQuality_id() {
        return quality_id;
    }

    public void setQuality_id(int quality_id) {
        this.quality_id = quality_id;
    }

    public int getTranslation_id() {
        return translation_id;
    }

    public void setTranslation_id(int translation_id) {
        this.translation_id = translation_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Integer> getCountries_id() {
        return countries_id;
    }

    public void setCountries_id(ArrayList<Integer> countries_id) {

        this.countries_id = countries_id;
    }

    public ArrayList<Integer> getGenres_id() {
        return genres_id;
    }

    public void setGenres_id(ArrayList<Integer> genres_id) {
        this.genres_id = genres_id;
    }
}


