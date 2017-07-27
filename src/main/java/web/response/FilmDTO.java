package web.response;

import entity.Film;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by alexfomin on 05.07.17.
 */
public class FilmDTO {
    private int id;
    private String name;
    private int releaseYear;
    private int qualityId;
    private int translationId;
    private String length;
    private float rating;
    private LocalDateTime uploadDate;
    private int status;

    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<String> countries = new ArrayList<>();


    public FilmDTO(Film film) {
        this.id = film.getId();
        this.name = film.getName();
        this.releaseYear = film.getReleaseYear();
        this.qualityId = film.getQuality_id();
        this.translationId = film.getTranslationId();
        this.length = film.getLength();
        this.rating = film.getRating();
        this.uploadDate = film.getUploadDate();
        this.status = film.getStatus();
        this.genres = film.getGenres();
        this.countries = film.getCountries();
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getQualityId() {
        return qualityId;
    }

    public void setQualityId(int qualityId) {
        this.qualityId = qualityId;
    }

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
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

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
